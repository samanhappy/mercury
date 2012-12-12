package com.dreamail.mercury.timerpush.serverstate;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.TaskFactoryDao;
import com.dreamail.mercury.pojo.Clickoo_task_factory;
import com.dreamail.mercury.timerpush.cache.ServerCacheMap;
import com.dreamail.mercury.timerpush.cache.ServerStateCache;
import com.dreamail.mercury.timerpush.config.TimerPushProperties;
import com.dreamail.mercury.timerpush.domain.Constants;
import com.dreamail.mercury.timerpush.domain.ServerInfo;

public class TimerPushState extends StateChange implements Runnable {
	private static Logger logger = LoggerFactory
			.getLogger(TimerPushState.class);
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
		String strInterval = TimerPushProperties.getConfigureMap().get("heart_beat");
		if (strInterval != null) {
			long interval = Integer.parseInt(strInterval);
			timer.schedule(new KeepState(), 0, interval*60*1000);
		} else {
			logger.info("\n--------timer param read failure.....");
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
			ServerStateCache ssc = new ServerStateCache();
			String serverType = ServerCacheMap.selectTimerServer().get(key).getServerType();
			String locationServer = serverType;
			ConcurrentHashMap<String, String> allServerInfo = ServerStateCache.getAllServerFromDB();
			if (allServerInfo != null && allServerInfo.containsKey(key)) {
				ServerInfo memCache = (ServerInfo) ssc.getServerInfoByIP(key);
				if (memCache.getServerType().equals(Constants.T_SERVER_SECOND)) {
					serverType = Constants.T_SERVER_SECOND;// 状态维护前服务器的类型
				}
			}
			ServerInfo value = new ServerInfo();
			value.setIp(key);
			value.setServerType(serverType);
			value.setDate(Tools.getCurrentDate());

			//更新DB
			Clickoo_task_factory clickoo_task_factory = new Clickoo_task_factory();
			TaskFactoryDao taskFactoryDao = new TaskFactoryDao();
			clickoo_task_factory.setMctype("2");
			clickoo_task_factory.setMckey(key);
			clickoo_task_factory.setMcvalue(value.getDate());
			if(!updateflag){
				taskFactoryDao.addTaskParameter(clickoo_task_factory);
				updateflag = true;
			}else{
				taskFactoryDao.updateTaskParameterByMCKey(clickoo_task_factory);
			}
			
			logger.info("\n---------------server[" + key + "]new state time：" + value.getDate());
			logger.info("\n---------------server[" + key + "]state：" + value.getServerType());
			// 如果是master监控其他服务器信息
			if (Constants.T_SERVER_MASTER.equals(serverType)) {
				logger.info("\n---------master [" + key + "] listener........-----------");
				String failedServerIP = isAlive();
				if (!failedServerIP.equals("") ? true : false) {
					findNewSecond(failedServerIP);// 是否为second
					ssc.deleteMasterList(failedServerIP);
					ServerCacheMap.removeTimerServer(failedServerIP, null);// 更新本地缓存
					logger.info("\n-----------master server find server stop ,notice---------");
				}
			}else if(Constants.T_SERVER_SECOND.equals(serverType) 
					&& Constants.T_SERVER_COMMON.equals(locationServer)){
				//Change Master
				changeMaster();
			}
		}
	}
}
