package com.dreamail.mercury.timerpush.serverstate;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.timerpush.cache.ServerCacheMap;
import com.dreamail.mercury.timerpush.cache.ServerStateCache;
import com.dreamail.mercury.timerpush.config.TimerPushProperties;
import com.dreamail.mercury.timerpush.domain.Constants;
import com.dreamail.mercury.timerpush.domain.Notice;
import com.dreamail.mercury.timerpush.domain.ServerInfo;
import com.dreamail.mercury.timerpush.server.ServerSecond;

public abstract class StateChange {
	private static Logger logger = LoggerFactory.getLogger(StateChange.class);

	public static String key = Tools.getLocalServerIpKey();

	/**
	 * Master监控所有服务器
	 */
	@SuppressWarnings({ "static-access" })
	public String isAlive() {
		ServerStateCache ssc = new ServerStateCache();
		String failedServerIP = "";
		Iterator<String> itor = (ssc.getAllServerFromDB()).keySet().iterator();
		while (itor.hasNext()) {
			String ipAddress = itor.next();
			logger.info("----" + key + "-----" + ipAddress + "-----");
			if (!key.equals(ipAddress)) {
				ServerInfo serverInfo = (ServerInfo) ssc
						.getServerInfoByIP(ipAddress);
				String serverDate = serverInfo.getDate().toString();
				// true 存活
				boolean flag = Tools.isFailState(serverDate, Tools
						.getCurrentDate(), Long.parseLong(TimerPushProperties
						.getConfigureMap().get("dead_time")));
				logger.info("-----Server:" + ipAddress + ",flag:" + flag);
				if (!flag) {
					logger.info("----------server failed!!");
					flag = false;
					failedServerIP = ipAddress;
					break;
				}
			}
		}
		return failedServerIP;
	}

	/**
	 * Second服务器设置
	 */
	public void findNewSecond(String failedServerIP) {
		// 并判断是不是second挂了，second挂了，重新寻找second
		ServerStateCache ssc = new ServerStateCache();
		ServerInfo serverInfo = (ServerInfo) ssc
				.getServerInfoByIP(failedServerIP);
		logger.info("---failed server type--" + serverInfo.getServerType()
				+ "----");
		if (serverInfo.getServerType().equals(Constants.T_SERVER_SECOND)) {
			logger.info("-------master server find [" + failedServerIP
					+ "]second server failed---------");
			ConcurrentHashMap<String, ServerInfo> hashMap = ServerCacheMap
					.selectTimerServer();
			Iterator<String> setSecondItor = (Iterator<String>) hashMap
					.keySet().iterator();
			logger.info("-------master server set second server----------");
			logger.info("-------------------------hashMap:" + hashMap.size());
			logger.info("" + hashMap);
			while (setSecondItor.hasNext()) {
				String currentMapIP = setSecondItor.next();
				ServerInfo commonTFInfo = hashMap.get(currentMapIP);
				commonTFInfo.setIp(currentMapIP);
				logger.info("--------------SERVER:"
						+ commonTFInfo.getServerType());
				if (commonTFInfo.getServerType().equals(
						Constants.T_SERVER_COMMON)) {
					logger.info("new second server :" + currentMapIP);
					commonTFInfo.setServerType(Constants.T_SERVER_SECOND);
					// 重新更新了时间
					commonTFInfo.setDate(Tools.getCurrentDate());

					ssc.addServerList(currentMapIP, Constants.T_SERVER_SECOND);
					// 更新本地缓存
					ServerCacheMap.addTimerServer(currentMapIP, commonTFInfo);
					// 启动second server
					break;
				}
			}
		} else {
			logger.info("-------master server find [" + failedServerIP
					+ "]common server failed---------");
		}
	}

	public void changeMaster() {
		String masterIP = "";
		ServerSecond secondServer = new ServerSecond();
		Notice notice = new Notice();
		// 从本地缓存中获取当前Mster Server IP
		Iterator<String> itor = (Iterator<String>) ServerCacheMap
				.selectTimerServer().keySet().iterator();
		while (itor.hasNext()) {
			ServerInfo tfInfo = ServerCacheMap.selectTimerServer().get(
					itor.next());
			if (tfInfo != null
					&& Constants.T_SERVER_MASTER.equals(tfInfo.getServerType())) {
				masterIP = tfInfo.getIp();
			}
		}
		if (!"".equals(masterIP)) {
			notice.setIp(masterIP);
			notice.setMsg("[change second]");
			secondServer.handleMsg(notice);
			logger.info("---------------[" + masterIP + "]change[" + key
					+ "]to second server-----");
		}
	}
}
