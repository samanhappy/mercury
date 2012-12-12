package com.dreamail.state;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.config.TaskProperties;
import com.dreamail.jms.JmsSender;
import com.dreamail.jms.Notice;
import com.dreamail.jms.controller.ControllerAccount;
import com.dreamail.jms.loop.LoopReceiveHandle;
import com.dreamail.mercury.dal.dao.TaskFactoryDao;
import com.dreamail.mercury.pojo.Clickoo_task_factory;
import com.dreamail.mercury.util.Constant;
import com.dreamail.taskfactory.msg.ServerCacheMap;
import com.dreamail.taskfactory.msg.ServerInfo;
import com.dreamail.utils.TFTools;

public class TaskFactoryState extends StateChange implements Runnable {
	private static Logger logger = LoggerFactory
			.getLogger(TaskFactoryState.class);
	private static Timer timer = new Timer(false);
	private static boolean updateflag = false;
	
	@Override
	public void run() {
		start();
	}
	
	/**
	 * 启动维护服务器状态定时器
	 */
	public void start() {
		String strInterval = TaskProperties.getConfigureMap().get("heart_beat");
		if (strInterval != null) {
			long interval = Integer.parseInt(strInterval);
			timer.schedule(new KeepState(), 0, interval*60*1000);
		} else {
			logger.info("timer param read failure.....");
		}
	}
	
	public void cancel() {
		timer.cancel();
	}
	
	public class KeepState extends TimerTask {
		/**
		 * 更新服务器存活时间
		 */
		@Override
		public void run() {
			// 更新服务器时间，写入到缓存中去
			TFTools tools = new TFTools();
			String serverType = ServerCacheMap.selectTF().get(key).getServerType();
			String locationServer = serverType;
			if (TFTools.getAllServerInfo() != null && TFTools.getAllServerInfo().containsKey(key)) {
				ServerInfo memCache = (ServerInfo) tools.getServerInfoByIP(key);
				if (memCache.getServerType().equals(Constant.SERVER_SECOND)) {
					serverType = Constant.SERVER_SECOND;// 状态维护前服务器的类型
				}
			}
			ServerInfo value = new ServerInfo();
			value.setIP(key);
			value.setServerType(serverType);
			value.setDate(TFTools.getCurrentDate());
			//更新缓存
			//-------hysen-------
			//tools.deleteServerInfo(key);
			//tools.addServerInfo(key, value);
//			MemCachedManager.getMcc().set(key, value.getDate());
			
			//更新DB
			Clickoo_task_factory clickoo_task_factory = new Clickoo_task_factory();
			TaskFactoryDao taskFactoryDao = new TaskFactoryDao();
			clickoo_task_factory.setMctype("1");
			clickoo_task_factory.setMckey(key);
			clickoo_task_factory.setMcvalue(value.getDate());
			if(!updateflag){
				taskFactoryDao.addTaskParameter(clickoo_task_factory);
				updateflag = true;
			}else{
				taskFactoryDao.updateTaskParameterByMCKey(clickoo_task_factory);
			}
			logger.info("server[" + key + "]new state time：" + value.getDate());
			logger.info("server[" + key + "]state：" + value.getServerType());
			// 如果是master监控其他服务器信息
			if (Constant.SERVER_MASTER.equals(serverType)) {
				logger.info("---------master [" + key + "] listener........-----------");
				String failedServerIP = isAlive();
				if (!failedServerIP.equals("")?true:false) {
					findNewSecond(failedServerIP);//是否为second
					//--------hysen---------
					//tools.deleteServerInfo(failedServerIP);// 更新服务器缓存，
					tools.deleteMasterList(failedServerIP);
					ServerCacheMap.removeTF(failedServerIP, null);// 更新本地缓存
					logger.info("-----------master server find server stop ,notice---------");
//					// 停止MasterLoop
					Notice sendStopLoopObj = new Notice();
					sendStopLoopObj.setMsg(Constant.STOPLOOP);
					sendStopLoopObj.setIP("");
					LoopReceiveHandle.handelMsg(sendStopLoopObj);
					//提前设置
					TFTools.setSyncNumber();
					// 通知所有服务器停止邮件收取
					sendStopLoopObj.setIP(key);
					JmsSender.sendTopicMsg(sendStopLoopObj, "TaskFactoryTopic");
					logger.info("-----------notice Common Sever stop receive mail!");
					boolean flag = true;
					long beginMills = System.currentTimeMillis();
					long endMills = 0;
					while(flag){
						logger.info("ServerMaster-----------------"+flag);
						if(TFTools.isContinue()){
							// 通知所有服务器谁是master服务器
							Notice sendObj = new Notice();
							sendObj.setIP(key);
							sendObj.setMsg(Constant.MASTERNOTICE);
							JmsSender.sendTopicMsg(sendObj, "TaskFactoryTopic");
							logger.info("-----------notice Common Sever[" + key+ "]is Master server");
							// 定时任务
							new ControllerAccount().start();
							TFTools.remarkSyncNumber();
							flag = false;
						}else{
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						endMills = System.currentTimeMillis();
						if((endMills - beginMills) > 1000 * 60 * 20){
							logger.info("server fail when wait for stop notice..");
							break;
						}
					}
				}
			}else if(Constant.SERVER_SECOND.equals(serverType) 
					&& Constant.SERVER_COMMON.equals(locationServer)){
				//Change Master
				changeMaster();
			}
		}
	}
}
