package com.dreamail.state;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.config.TaskProperties;
import com.dreamail.jms.Notice;
import com.dreamail.mercury.util.Constant;
import com.dreamail.taskfactory.msg.ServerCacheMap;
import com.dreamail.taskfactory.msg.ServerInfo;
import com.dreamail.taskfactory.msghandle.ServerSecond;
import com.dreamail.utils.TFTools;

public abstract class StateChange {
	private static Logger logger = LoggerFactory.getLogger(StateChange.class);

	public static String key = TFTools.getCurrentServerIP();

	/**
	 * Master监控所有服务器
	 */
	public String isAlive() {
		TFTools tools = new TFTools();
		String failedServerIP = "";
		ConcurrentHashMap<String, String> MCMap = TFTools.getAllServerInfo();
		Iterator<String> itor = MCMap.keySet().iterator();
		while (itor.hasNext()) {
			String ipAddress = itor.next();
			logger.info("----" + key + "-----" + ipAddress + "-----");
			if (!key.equals(ipAddress)) {
				ServerInfo serverInfo = (ServerInfo) tools
						.getServerInfoByIP(ipAddress);
				String serverDate = serverInfo.getDate().toString();
				// true 存活
				boolean flag = TFTools.isFailState(
						serverDate,
						TFTools.getCurrentDate(),
						Long.parseLong(TaskProperties.getConfigureMap().get(
								"dead_time")));
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
		TFTools tools = new TFTools();
		ServerInfo serverInfo = (ServerInfo) tools
				.getServerInfoByIP(failedServerIP);
		logger.info("---failed server type--" + serverInfo.getServerType()
				+ "----");
		if (serverInfo.getServerType().equals(Constant.SERVER_SECOND)) {
			logger.info("-------master server find [" + failedServerIP
					+ "]second server failed---------");
			ConcurrentHashMap<String, ServerInfo> hashMap = ServerCacheMap
					.selectTF();
			Iterator<String> setSecondItor = (Iterator<String>) hashMap
					.keySet().iterator();
			logger.info("-------master server set second server----------");
			logger.info("-------------------------hashMap:" + hashMap.size());
			logger.info("" + hashMap);
			while (setSecondItor.hasNext()) {
				String currentMapIP = setSecondItor.next();
				ServerInfo commonTFInfo = hashMap.get(currentMapIP);
				if (commonTFInfo != null) {
					commonTFInfo.setIP(currentMapIP);
					logger.info("--------------SERVER:"
							+ commonTFInfo.getServerType());
					if (commonTFInfo.getServerType().equals(
							Constant.SERVER_COMMON)) {
						logger.info("new second server :" + currentMapIP);
						commonTFInfo.setServerType(Constant.SERVER_SECOND);
						// 重新更新了时间
						commonTFInfo.setDate(TFTools.getCurrentDate());
						// --------hysen---------
						// tools.deleteServerInfo(commonTFInfo.getIP());
						// tools.addServerInfo(commonTFInfo.getIP(),
						// commonTFInfo);
						tools.deleteMasterList(commonTFInfo.getIP());
						tools.addMasterList(currentMapIP,
								Constant.SERVER_SECOND);
						// 更新本地缓存
						ServerCacheMap.addTF(currentMapIP, commonTFInfo);
						// 启动second server
						break;
					}
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
		Iterator<String> itor = (Iterator<String>) ServerCacheMap.selectTF()
				.keySet().iterator();
		while (itor.hasNext()) {
			ServerInfo tfInfo = ServerCacheMap.selectTF().get(itor.next());
			if (tfInfo != null
					&& tfInfo.getServerType().equals(Constant.SERVER_MASTER)) {
				masterIP = tfInfo.getIP();
			}
		}
		if (!"".equals(masterIP)) {
			notice.setIP(masterIP);
			notice.setMsg("[change second]");
			secondServer.handleMsg(notice);
			logger.info("----[" + masterIP + "]change[" + key
					+ "]to second server-----");
		}
	}
}
