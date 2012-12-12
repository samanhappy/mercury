package com.dreamail.taskfactory.msghandle;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.config.TaskProperties;
import com.dreamail.jms.Notice;
import com.dreamail.mercury.util.Constant;
import com.dreamail.taskfactory.msg.ServerCacheMap;
import com.dreamail.taskfactory.msg.ServerInfo;
import com.dreamail.utils.TFTools;

public class ServerSecond implements IServerHandel{
	
	private static Logger logger = LoggerFactory.getLogger(ServerSecond.class);
	private static String masterIP = "";
	private static Timer timer = new Timer(false);
	private static String currentIP = TFTools.getCurrentServerIP();
	/**
	 * Second处理
	 * @param msg JMS信息
	 */
	public void handleMsg(Notice notice) {
		TFTools tools = new TFTools();
		//将Master Server 记录到本地缓存
		if(notice.getMsg().equals(Constant.MASTERNOTICE)){
			ServerInfo taskFactoryInfo = new ServerInfo();
			taskFactoryInfo.setIP(notice.getIP());
			taskFactoryInfo.setServerType(Constant.SERVER_MASTER);
			ServerCacheMap.addTF(notice.getIP(), taskFactoryInfo);
		}
		logger.info("-----["+currentIP+"]receive["+notice.getMsg()+"]request");
		System.out.println("---------------------"+currentIP);
		ServerInfo taskFactoryInfo = (ServerInfo) tools.getServerInfoByIP(currentIP);
		String serverType = taskFactoryInfo.getServerType();
		// 判断自己是否为second，从缓存服务器上判断，本地server还是common
		ServerInfo locationInfo = ServerCacheMap.selectTF().get(currentIP);
		if (serverType.equals(Constant.SERVER_SECOND) && 
				Constant.SERVER_COMMON.equals(locationInfo.getServerType())) {
			logger.info("-----Second Server Change-----["+currentIP+"] is second");
			// 更改自己本地缓存，因为一开始不知道server类型都设置为了common
			taskFactoryInfo.setIP(currentIP);
			taskFactoryInfo.setServerType(Constant.SERVER_SECOND);
			ServerCacheMap.modifyTF(currentIP, taskFactoryInfo);
			masterIP = notice.getIP();
			//监控master
			if (!masterIP.equals("")) {
				String strInterval = TaskProperties.getConfigureMap().get("listener_time");
				if (strInterval != null) {
					long interval = Integer.parseInt(strInterval);
					timer.schedule(new MasterListener(), 0, interval*60*1000);
				} else {
					logger.info("timer param read failure.....");
				}
			} else {
				logger.info("cache wrong!!!!!!!!!!!!");
			}
		}
	}
    //内部类，用于启动定时监控master任务
	public static class MasterListener extends TimerTask {
		/**
		 * 启动定时器
		 */
		@Override
		public void run() {
			TFTools tools = new TFTools();
			// 监控master
			logger.info("------------ready listener["+masterIP+"]");
			if(tools.getServerInfoByIP(masterIP) == null){
			   logger.info("--------threre is no master value");
			   return;
			}
			ServerInfo mastInfo = (ServerInfo) tools.getServerInfoByIP(masterIP);
			String deadTime = TaskProperties.getConfigureMap().get("dead_time");
			if(!TFTools.isFailState(mastInfo.getDate(),TFTools.getCurrentDate(),
					Long.parseLong(deadTime))){
				logger.info("------master change stop master listener...");
				//取消定时器
				timer.cancel();
				//删除缓存中master信息
				//------hysen------
				//tools.deleteServerInfo(masterIP);
				//tools.deleteServerInfo(currentIP);
				tools.deleteMasterList(masterIP);
				//更新缓存信息
				ServerInfo newMasterServer = new ServerInfo();
				newMasterServer.setServerType(Constant.SERVER_MASTER);
				newMasterServer.setIP(currentIP);
				newMasterServer.setDate(TFTools.getCurrentDate());
				//-----hysen-----
				//tools.addServerInfo(currentIP, newMasterServer);
				tools.addMasterList(currentIP, newMasterServer.getServerType());
				//启动定时器在Memcache维护自己的生存状态,只需要修改本地缓存即可
				ServerCacheMap.removeTF(masterIP, null);
				ServerCacheMap.addTF(currentIP, newMasterServer);
				logger.info("-----change cache success!-----");
				Notice notice = new Notice();
				notice.setIP(currentIP);
				notice.setMsg("[change master]");
				ServerMaster serverMaster = new ServerMaster();
				serverMaster.handleMsg(notice);
			}
		}
	}
}
