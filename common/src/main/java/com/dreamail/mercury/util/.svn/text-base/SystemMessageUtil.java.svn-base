package com.dreamail.mercury.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemMessageUtil {

	final static Logger logger = LoggerFactory
			.getLogger(SystemMessageUtil.class);
	
	private static String MAC = null;
	
	private static String IP = null;

	/**
	 * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
	 */
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	/**
	 * <p>
	 * 方法 getLocalIp
	 * </p>
	 * 获取本机ip地址
	 * 
	 * @return
	 */
	public static String getLocalIp() {
		if (IP != null) {
			return IP;
		}
		String localIp = null;
		try {
			localIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			logger.info("fail to get IP address", e);
		}
		IP = localIp;
		return localIp;
	}

	/**
	 * <p>
	 * 方法 getLocalMac
	 * </p>
	 * 获取本机MAC地址
	 * 
	 * @return
	 */
	public static String getLocalMac() {
		if (MAC != null) {
			return MAC;
		}
		String os = getOSName();
		String mac = null;
		try {
			if (os.startsWith("windows")) {
				// 本地是windows
				mac = getWindowsMACAddress();
			} else {
				// 本地是非windows系统 一般就是unix
				mac = getUnixMACAddress();
			}
		} catch (Exception e) {
			logger.error("fail to get mac address", e);
		}
		
		if (mac == null) {
			mac = "ts_" + new Date().getTime();
		}
		logger.info("mac address for the machine is " + mac);
		MAC = mac;
		return mac;
	}

	/**
	 * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取.如果有特殊系统请继续扩充新的取mac地址方法.
	 * 
	 * @return mac地址
	 */
	public static String getUnixMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ifconfig eth0");// linux下的命令，一般取eth0作为本地主网卡
																	// 显示信息中包含有mac地址信息
			bufferedReader = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("hwaddr");// 寻找标示字符串[hwaddr]
				if (index >= 0) {// 找到了
					mac = line.substring(index + "hwaddr".length() + 1).trim();// 取出mac地址并去除2边空格
					break;
				}
			}
		} catch (IOException e) {
			logger.error("fail to get linux mac address", e);
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				logger.error("fail to get linux mac address", e1);
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 获取widnows网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getWindowsMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ipconfig /all");// windows下的命令，显示信息中包含有mac地址信息
			bufferedReader = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("physical address");// 寻找标示字符串[physical
																		// address]
				if (index >= 0) {// 找到了
					index = line.indexOf(":");// 寻找":"的位置
					if (index >= 0) {
						mac = line.substring(index + 1).trim();// 取出mac地址并去除2边空格
					}
					break;
				}
			}
		} catch (IOException e) {
			logger.error("fail to get windows mac address", e);
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				logger.error("fail to get windows mac address", e1);
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

}
