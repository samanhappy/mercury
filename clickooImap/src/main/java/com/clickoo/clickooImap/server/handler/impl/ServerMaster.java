package com.clickoo.clickooImap.server.handler.impl;

import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.clickoo.clickooImap.domain.Notice;
import com.clickoo.clickooImap.domain.ServerInfo;
import com.clickoo.clickooImap.server.cache.ServerCacheMap;
import com.clickoo.clickooImap.server.handler.IServerHandel;
import com.clickoo.clickooImap.utils.CIConstants;
import com.clickoo.clickooImap.utils.CITools;
import com.clickoo.jms.JmsSender;
import com.clickoo.mercury.util.JMSTypes;

public class ServerMaster implements IServerHandel {

	public static final Logger logger = LoggerFactory
			.getLogger(ServerMaster.class);
	public static CITools tools = new CITools();
	public static String currentIP = CITools.getCurrentServerIP();

	public void handleMsg(Notice notice) {
		String commonIP = "";// 普通common服务器
		boolean isSecond = false;// 是否存在second服务器
		logger.info("--------" + notice.getMsg() + "---------");
		// 当前服务器类型
		ServerInfo currentServerInfo = ServerCacheMap.selectCIServer().get(
				currentIP);
		// 只有Master记录server信息,自己发送的JMS信息不做处理
		if ((!currentIP.equals(notice.getIp()) && currentServerInfo
				.getServerType()
				.equals(CIConstants.ServerType.CI_SERVER_MASTER))
				|| "[change master]".equals(notice.getMsg())) {
			logger.info("------You Are Master-----");

			// 记录所有服务器
			ServerInfo noticeInfo = new ServerInfo();
			noticeInfo.setIp(notice.getIp());
			if ("[change master]".equals(notice.getMsg())) {
				noticeInfo
						.setServerType(CIConstants.ServerType.CI_SERVER_MASTER);
			} else {
				noticeInfo
						.setServerType(CIConstants.ServerType.CI_SERVER_COMMON);
			}
			ServerCacheMap.addCIServer(notice.getIp(), noticeInfo);
			if ("[change master]".equals(notice.getMsg())) {
				// second升级为master从缓存服务器中判断
				isSecond = ""
						.equals(getServerFromServerCache(CIConstants.ServerType.CI_SERVER_SECOND)) ? false
						: true;
				// 普通服务器
				commonIP = getServerFromServerCache(CIConstants.ServerType.CI_SERVER_COMMON);
			} else {
				// master从本地缓存中判断是否存在second server
				isSecond = ""
						.equals(getServerFromLocationCache(CIConstants.ServerType.CI_SERVER_SECOND)) ? false
						: true;
				// 普通服务器
				commonIP = getServerFromLocationCache(CIConstants.ServerType.CI_SERVER_COMMON);
			}
			// master 已经确定了 1.change mster 2.master监控
			if (isSecond) {
				// second 存在
				logger.info("-----------------second server still live ");
				// 更新本地缓存
				ServerInfo secondServer = new ServerInfo();
				secondServer
						.setServerType(CIConstants.ServerType.CI_SERVER_COMMON);
				ServerCacheMap.addCIServer(notice.getIp(), secondServer);
				CITools.getAllServerInfo();
				tools.addServerList(notice.getIp(),
						CIConstants.ServerType.CI_SERVER_COMMON);
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
			sendObj.setMsg(CIConstants.NoticeType.CI_MASTERNOTICE);
			JmsSender.sendTopicMsg(sendObj, JMSTypes.CLICKOO_IMAP_TOPIC);
			logger.info("-----------notice Common Sever[" + currentIP
					+ "]is Master server");
		}
	}

	/**
	 * 根据serverType获取缓存服务器IP
	 */
	public String getServerFromServerCache(String serverType) {
		String serverIP = "";
		Iterator<String> itor = (Iterator<String>) CITools.getAllServerInfo()
				.keySet().iterator();
		while (itor.hasNext()) {
			ServerInfo tfInfo = tools.getServerInfoByIP(itor.next());
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
				.selectCIServer().keySet().iterator();
		while (itor.hasNext()) {
			ServerInfo tfInfo = ServerCacheMap.selectCIServer()
					.get(itor.next());
			if (tfInfo.getServerType().equals(serverType)
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
		secondServer.setServerType(CIConstants.ServerType.CI_SERVER_SECOND);
		secondServer.setDate(CITools.getCurrentDate());
		tools.addServerList(targetIP, secondServer.getServerType());
		// 更新本地缓存
		ServerCacheMap.addCIServer(targetIP, secondServer);
	}
}
