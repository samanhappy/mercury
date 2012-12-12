package com.clickoo.clickooImap.server.state;

import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.clickoo.clickooImap.config.ClickooImapProperties;
import com.clickoo.clickooImap.domain.ServerInfo;
import com.clickoo.clickooImap.server.cache.ServerAccountsCache;
import com.clickoo.clickooImap.server.cache.ServerCacheMap;
import com.clickoo.clickooImap.utils.CIConstants;
import com.clickoo.clickooImap.utils.CITools;
import com.clickoo.mercury.cache.MemCachedManager;

public class ClickooImapState extends StateChange implements Runnable {
	private static Logger logger = LoggerFactory
			.getLogger(ClickooImapState.class);
	private static Timer timer = new Timer(false);
	
	@Override
	public void run() {
		start();
	}
	
	/**
	 * 启动维护服务器状态定时器
	 */
	public void start() {
		String strInterval = ClickooImapProperties.getConfigureMap().get("heart_beat");
		if (strInterval != null) {
			long interval = Integer.parseInt(strInterval);
			timer.schedule(new keepState(), 0, interval*60*1000);
		} else {
			logger.info("\n--------timer param read failure.....");
		}
	}
	
	public void cancel() {
		timer.cancel();
	}
	
	public class keepState extends TimerTask {
		/**
		 * 更新服务器存活时间
		 */
		@Override
		public void run() {
			// 更新服务器时间，写入到缓存中去
			CITools tools = new CITools();
			String serverType = ServerCacheMap.selectCIServer().get(key).getServerType();
			String locationServer = serverType;
			if (CITools.getAllServerInfo() != null && CITools.getAllServerInfo().containsKey(key)) {
				ServerInfo memCache = (ServerInfo) tools.getServerInfoByIP(key);
				if (memCache.getServerType().equals(CIConstants.ServerType.CI_SERVER_SECOND)) {
					serverType = CIConstants.ServerType.CI_SERVER_SECOND;// 状态维护前服务器的类型
				}
			}
			ServerInfo value = new ServerInfo();
			value.setIp(key);
			value.setServerType(serverType);
			value.setDate(CITools.getCurrentDate());
			//更新缓存
			MemCachedManager.getMcc().set(key, value.getDate());
			
			logger.info("\n---------------server[" + key + "]new state time：" + value.getDate());
			logger.info("\n---------------server[" + key + "]state：" + value.getServerType());
			// 如果是master监控其他服务器信息
			if (CIConstants.ServerType.CI_SERVER_MASTER.equals(serverType)) {
				logger.info("\n---------master [" + key + "] listener........-----------");
				String failedServerIP = isAlive();
				if (!failedServerIP.equals("") ? true : false) {
					findNewSecond(failedServerIP);// 是否为second
					tools.deleteMasterList(failedServerIP);
					ServerCacheMap.removeCIServer(failedServerIP, null);// 更新本地缓存
					logger.info("\n-----------master server find server stop ,notice---------");
					// 此时取得挂掉服务器上的账号进行重新分配
					ServerAccountsCache.loadBalancing(failedServerIP);
				}
			}else if(CIConstants.ServerType.CI_SERVER_SECOND.equals(serverType) 
					&& CIConstants.ServerType.CI_SERVER_COMMON.equals(locationServer)){
				//Change Master
				changeMaster();
			}
		}
	}
}
