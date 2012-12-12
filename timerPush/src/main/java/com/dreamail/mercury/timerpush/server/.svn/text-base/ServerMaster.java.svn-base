package com.dreamail.mercury.timerpush.server;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.timerpush.cache.ServerCacheMap;
import com.dreamail.mercury.timerpush.cache.ServerStateCache;
import com.dreamail.mercury.timerpush.domain.Constants;
import com.dreamail.mercury.timerpush.domain.Notice;
import com.dreamail.mercury.timerpush.domain.ServerInfo;
import com.dreamail.mercury.timerpush.serverstate.Tools;
import com.dreamail.mercury.util.JMSTypes;

public class ServerMaster implements IServerHandel {

	private static final Logger logger = LoggerFactory
			.getLogger(ServerMaster.class);
	ServerStateCache ssc = new ServerStateCache();
	private static String currentIP = Tools.getLocalServerIpKey();

	public void handleMsg(Notice notice) {
		String commonIP = "";// 普通common服务器
		boolean isSecond = false;// 是否存在second服务器
		logger.info("--------" + notice.getMsg() + "---------");
		// 当前服务器类型
		ServerInfo currentServerInfo = ServerCacheMap.selectTimerServer().get(
				currentIP);
		// 只有Master记录server信息,自己发送的JMS信息不做处理
		if ((!currentIP.equals(notice.getIp()) && currentServerInfo
				.getServerType().equals(Constants.T_SERVER_MASTER))
				|| "[change master]".equals(notice.getMsg())) {
			logger.info("------You Are Master-----");

			// 记录所有服务器
			ServerInfo noticeInfo = new ServerInfo();
			noticeInfo.setIp(notice.getIp());
			if ("[change master]".equals(notice.getMsg())) {
				noticeInfo.setServerType(Constants.T_SERVER_MASTER);
			} else {
				noticeInfo.setServerType(Constants.T_SERVER_COMMON);
			}
			ServerCacheMap.addTimerServer(notice.getIp(), noticeInfo);
			if ("[change master]".equals(notice.getMsg())) {
				// second升级为master从缓存服务器中判断
				isSecond = ""
						.equals(getServerFromServerCache(Constants.T_SERVER_SECOND)) ? false
						: true;
				// 普通服务器
				commonIP = getServerFromServerCache(Constants.T_SERVER_COMMON);
			} else {
				// master从本地缓存中判断是否存在second server
				isSecond = ""
						.equals(getServerFromLocationCache(Constants.T_SERVER_SECOND)) ? false
						: true;
				// 普通服务器
				commonIP = getServerFromLocationCache(Constants.T_SERVER_COMMON);
			}
			// master 已经确定了 1.change mster 2.master监控
			if (isSecond) {
				// second 存在
				logger.info("-----------------second server still live ");
				// 更新本地缓存
				ServerInfo secondServer = new ServerInfo();
				secondServer.setServerType(Constants.T_SERVER_COMMON);
				ServerCacheMap.addTimerServer(notice.getIp(), secondServer);
				ServerStateCache.getAllServerFromDB();
				ssc.addServerList(notice.getIp(), Constants.T_SERVER_COMMON);
			} else {
				// 设置second
				if (!commonIP.equals("")) {
					logger.info("--------master[" + currentIP + "]set["
							+ commonIP + "] is second");
					// 更新缓存服务器
					handleCache(commonIP);
				} else if (commonIP.equals("")
						&& currentIP.equals(notice.getIp())) {
					logger.info("------Master Server only one!-------");
				} else {
					logger.info("------Master Server only two!-------");
				}
			}
			// 通知所有服务器谁是master服务器
			Notice sendObj = new Notice();
			sendObj.setIp(currentIP);
			sendObj.setMsg(Constants.MASTERNOTICE);
			JmsSender.sendTopicMsg(sendObj, JMSTypes.CLICKOO_TIMERPUSH_TOPIC);
			logger.info("-----------notice Common Sever[" + currentIP
					+ "]is Master server");
		}
	}

	/**
	 * 根据serverType获取缓存服务器IP
	 */
	public String getServerFromServerCache(String serverType) {
		String serverIP = "";
		Iterator<String> itor = (Iterator<String>) ServerStateCache
				.getAllServerFromDB().keySet().iterator();
		while (itor.hasNext()) {
			ServerInfo tfInfo = ssc.getServerInfoByIP(itor.next());
			if (tfInfo.getServerType().equals(serverType)
					&& !tfInfo.getIp().equals(currentIP)) {
				serverIP = tfInfo.getIp();
				break;
			}
		}
		return serverIP;
	}

	/**
	 * 根据serverType获取本地服务器IP
	 */
	public String getServerFromLocationCache(String serverType) {
		String serverIP = "";
		Iterator<String> itor = (Iterator<String>) ServerCacheMap
				.selectTimerServer().keySet().iterator();
		while (itor.hasNext()) {
			ServerInfo tfInfo = ServerCacheMap.selectTimerServer().get(
					itor.next());
			if (tfInfo != null && serverType.equals(tfInfo.getServerType())
					&& !tfInfo.getIp().equals(currentIP)) {
				serverIP = tfInfo.getIp();
				break;
			}
		}
		return serverIP;
	}

	/**
	 * 更新缓存服务器和本地缓存
	 */
	public void handleCache(String targetIP) {
		// 更新缓存服务器
		ServerInfo secondServer = new ServerInfo();
		secondServer.setIp(targetIP);
		secondServer.setServerType(Constants.T_SERVER_SECOND);
		secondServer.setDate(Tools.getCurrentDate());
		ssc.addServerList(targetIP, secondServer.getServerType());
		// 更新本地缓存
		ServerCacheMap.addTimerServer(targetIP, secondServer);
	}

}
