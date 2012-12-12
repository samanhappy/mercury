package com.clickoo.clickooImap.server.handler.impl;

import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.clickoo.clickooImap.config.ClickooImapProperties;
import com.clickoo.clickooImap.domain.Notice;
import com.clickoo.clickooImap.domain.ServerInfo;
import com.clickoo.clickooImap.server.cache.ServerAccountsCache;
import com.clickoo.clickooImap.server.cache.ServerCacheMap;
import com.clickoo.clickooImap.server.handler.IServerHandel;
import com.clickoo.clickooImap.utils.CIConstants;
import com.clickoo.clickooImap.utils.CITools;

public class ServerSecond implements IServerHandel {

	private static Logger logger = LoggerFactory.getLogger(ServerSecond.class);
	private static String masterIP = "";
	private static Timer timer = new Timer(false);
	private static String currentIP = CITools.getCurrentServerIP();

	/**
	 * Second处理
	 * 
	 * @param msg
	 *            JMS信息
	 */
	public void handleMsg(Notice notice) {
		CITools tools = new CITools();
		// 将Master Server 记录到本地缓存
		if (notice.getMsg().equals(CIConstants.NoticeType.CI_MASTERNOTICE)) {
			ServerInfo serverInfo = new ServerInfo();
			serverInfo.setIp(notice.getIp());
			serverInfo.setServerType(CIConstants.ServerType.CI_SERVER_MASTER);
			ServerCacheMap.addCIServer(notice.getIp(), serverInfo);
		}
		logger.info("-----[" + currentIP + "]receive[" + notice.getMsg()
				+ "]request");
		ServerInfo currentinfo = (ServerInfo) tools.getServerInfoByIP(currentIP);
		logger.info("currentinfo"+currentinfo);
		String serverType = currentinfo.getServerType();
		
		// 判断自己是否为second，从缓存服务器上判断，本地是second还是common
		ServerInfo locationInfo = ServerCacheMap.selectCIServer()
				.get(currentIP);
		if (serverType.equals(CIConstants.ServerType.CI_SERVER_SECOND)
				&& CIConstants.ServerType.CI_SERVER_COMMON.equals(locationInfo
						.getServerType())) {
			logger.info("-----Second Server Change-----[" + currentIP
					+ "] is second");
			// 更改自己本地缓存，因为一开始不知道server类型都设置为了common
			currentinfo.setIp(currentIP);
			currentinfo.setServerType(CIConstants.ServerType.CI_SERVER_SECOND);
			ServerCacheMap.modifyCIServer(currentIP, currentinfo);
			masterIP = notice.getIp();
			// 监控master
			if (!masterIP.equals("")) {
				String strInterval = ClickooImapProperties.getConfigureMap()
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
	public class MasterListener extends TimerTask {
		/**
		 * 启动定时器
		 */
		@Override
		public void run() {
			CITools tools = new CITools();
			// 监控master
			logger.info("------------ready listener[" + masterIP + "]");
			if (tools.getServerInfoByIP(masterIP) == null) {
				logger.info("--------threre is no master value");
				return;
			}
			ServerInfo mastInfo = (ServerInfo) tools
					.getServerInfoByIP(masterIP);
			String deadTime = ClickooImapProperties.getConfigureMap().get(
					"dead_time");
			if (!CITools.isFailState(mastInfo.getDate(), CITools
					.getCurrentDate(), Long.parseLong(deadTime))) {
				logger.info("------master change stop master listener...");
				// 取消定时器
				timer.cancel();
				// 删除缓存中master信息
				tools.deleteMasterList(masterIP);
				// 更新缓存信息
				ServerInfo newMasterServer = new ServerInfo();
				newMasterServer.setServerType(CIConstants.ServerType.CI_SERVER_MASTER);
				newMasterServer.setIp(currentIP);
				newMasterServer.setDate(CITools.getCurrentDate());
				tools.addServerList(currentIP, newMasterServer.getServerType());
				
				// 启动定时器在Memcache维护自己的生存状态,只需要修改本地缓存即可
				ServerCacheMap.removeCIServer(masterIP, null);
				ServerCacheMap.addCIServer(currentIP, newMasterServer);
				logger.info("-----change cache success!-----");
				Notice notice = new Notice();
				notice.setIp(currentIP);
				notice.setMsg("[change master]");
				ServerMaster serverMaster = new ServerMaster();
				serverMaster.handleMsg(notice);
				
				//此处添加将原先master上的账户分配到运行正常的服务器上
				ServerAccountsCache.loadBalancing(masterIP);
			}
		}
	}
}
