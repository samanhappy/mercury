package com.dreamail.mercury.timerpush.server;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.timerpush.cache.ServerCacheMap;
import com.dreamail.mercury.timerpush.cache.ServerStateCache;
import com.dreamail.mercury.timerpush.cache.TimerInfoManager;
import com.dreamail.mercury.timerpush.config.TimerPushProperties;
import com.dreamail.mercury.timerpush.domain.Constants;
import com.dreamail.mercury.timerpush.domain.Notice;
import com.dreamail.mercury.timerpush.domain.ServerInfo;
import com.dreamail.mercury.timerpush.serverstate.Tools;
import com.dreamail.mercury.timerpush.timer.TimerOperator;

public class ServerSecond implements IServerHandel {

	private static Logger logger = LoggerFactory.getLogger(ServerSecond.class);
	private static String masterIP = "";
	private static Timer timer = new Timer(false);
	private static String currentIP = Tools.getLocalServerIpKey();

	/**
	 * Second处理
	 * 
	 * @param msg
	 *            JMS信息
	 */
	public void handleMsg(Notice notice) {
		ServerStateCache ssc = new ServerStateCache();
		// 将Master Server 记录到本地缓存
		if (notice.getMsg().equals(Constants.MASTERNOTICE)) {
			ServerInfo serverInfo = new ServerInfo();
			serverInfo.setIp(notice.getIp());
			serverInfo.setServerType(Constants.T_SERVER_MASTER);
			ServerCacheMap.addTimerServer(notice.getIp(), serverInfo);
		}
		logger.info("-----[" + currentIP + "]receive[" + notice.getMsg()
				+ "]request");
		ServerInfo currentinfo = (ServerInfo) ssc.getServerInfoByIP(currentIP);
		logger.info("currentinfo"+currentinfo);
		String serverType = currentinfo.getServerType();
		
		// 判断自己是否为second，从缓存服务器上判断，本地是second还是common
		ServerInfo locationInfo = ServerCacheMap.selectTimerServer()
				.get(currentIP);
		if (serverType.equals(Constants.T_SERVER_SECOND)
				&& Constants.T_SERVER_COMMON.equals(locationInfo
						.getServerType())) {
			logger.info("-----Second Server Change-----[" + currentIP
					+ "] is second");
			// 更改自己本地缓存，因为一开始不知道server类型都设置为了common
			currentinfo.setIp(currentIP);
			currentinfo.setServerType(Constants.T_SERVER_SECOND);
			ServerCacheMap.modifyTimerServer(currentIP, currentinfo);
			masterIP = notice.getIp();
			// 监控master
			if (!masterIP.equals("")) {
				String strInterval = TimerPushProperties.getConfigureMap()
						.get("listener_time");
				if (strInterval != null) {
					long interval = Integer.parseInt(strInterval);
					timer.schedule(new MasterListener(), 0,
							interval * 60 * 1000);
				} else {
					logger.info("timer param read failure.....");
				}
			} else {
				logger.info("cache wrong!!!!!!!!!!!!");
			}
		}
	}

	// 内部类，用于启动定时监控master任务
	public static class MasterListener extends TimerTask {
		/**
		 * 启动定时器
		 */
		@Override
		public void run() {
			ServerStateCache ssc = new ServerStateCache();
			// 监控master
			logger.info("------------ready listener[" + masterIP + "]");
			if (ssc.getServerInfoByIP(masterIP) == null) {
				logger.info("--------threre is no master value");
				return;
			}
			ServerInfo mastInfo = (ServerInfo) ssc
					.getServerInfoByIP(masterIP);
			String deadTime = TimerPushProperties.getConfigureMap().get(
					"dead_time");
			if (!Tools.isFailState(mastInfo.getDate(), Tools
					.getCurrentDate(), Long.parseLong(deadTime))) {
				logger.info("------master change stop master listener...");
				// 取消定时器
				timer.cancel();
				// 删除缓存中master信息
				ssc.deleteMasterList(masterIP);
				// 更新缓存信息
				ServerInfo newMasterServer = new ServerInfo();
				newMasterServer.setServerType(Constants.T_SERVER_MASTER);
				newMasterServer.setIp(currentIP);
				newMasterServer.setDate(Tools.getCurrentDate());
				ssc.addServerList(currentIP, newMasterServer.getServerType());
				
				// 启动定时器在DB维护自己的生存状态,只需要修改本地缓存即可
				ServerCacheMap.removeTimerServer(masterIP, null);
				ServerCacheMap.addTimerServer(currentIP, newMasterServer);
				logger.info("-----change cache success!-----");
				Notice notice = new Notice();
				notice.setIp(currentIP);
				notice.setMsg("[change master]");
				ServerMaster serverMaster = new ServerMaster();
				serverMaster.handleMsg(notice);
				
				//TimerInfoManager数据初始化并启动timerpush
				TimerInfoManager.init();
				TimerOperator.timerPush();
			}
		}
	}
}
