package com.dreamail.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.configure.PropertiesDeploy;

public class ConnectionManager {

	public static final Logger log = LoggerFactory
			.getLogger(ConnectionManager.class);
	private static Map<String, Map<Integer, ConnectionInfo>> mapsInfo = new HashMap<String, Map<Integer, ConnectionInfo>>();
	private static int count = 0;

	/**
	 * 创建JMS连接
	 * @param target JMS 目标地址
	 */
	public static synchronized Connection getConnection(String target)
			throws Exception {
		//第一次发送消息
		if (!mapsInfo.containsKey(target)) {
			initConnectionMap(target);
		}
		//获取资源
		Map<Integer, ConnectionInfo> map = mapsInfo.get(target);
		if (map == null || map.size() < 1) {
			initConnectionMap(target);
			map = mapsInfo.get(target);
		}
		if (count >= map.size()) {
			count = 0;
		}
		ConnectionInfo connInfo = map.get(count);
		count++;
		Connection con = null;
		if (!connInfo.getFlag()) {
			log.error("[ connection cloesd ]");
			for (int i = 0; i < map.size(); i++) {
				connInfo = map.get(i);
				if (!connInfo.getFlag()) {
					connInfo.reconnect();
					break;
				}
			}
			log.info("[ reconnect success ]");
			con = connInfo.getConnection();
		} else {
			con = connInfo.getConnection();
		}
		return con;
	}

	/**
	 * 初始化JMS连接Map
	 * @param target JMS 目标地址
	 */
	public static void initConnectionMap(String target) throws Exception {
		Map<Integer, ConnectionInfo> mapConnInfo = new HashMap<Integer, ConnectionInfo>();
		int index = 0;
		String targetUrl = PropertiesDeploy.getConfigureMap().get("topic.url");
		String[] targ = targetUrl.split(",");
		for (int i = 0; i < targ.length; i++) {
			ConnectionInfo connInfo = new ConnectionInfo();
			connInfo.initConnection(targ, i);
			mapConnInfo.put(index++, connInfo);
		}
		mapsInfo.put(target, mapConnInfo);
	}

}
