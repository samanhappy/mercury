package com.dreamail.mercury.configure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.util.SystemMessageUtil;

public class ServerManager {

	private static Logger logger = LoggerFactory.getLogger(ServerManager.class);
	
	private static String IP = null;
	
	public static void init() {
		IP = SystemMessageUtil.getLocalIp();
		logger.info("server local ip is:" + IP);
	}
	
	public static String getLocalIP() {
		return IP;
	}
	
}
