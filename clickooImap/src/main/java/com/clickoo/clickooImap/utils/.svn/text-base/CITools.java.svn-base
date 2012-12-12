package com.clickoo.clickooImap.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clickoo.clickooImap.domain.ServerInfo;
import com.clickoo.clickooImap.server.cache.ServerCacheMap;
import com.clickoo.mercury.cache.MemCachedManager;
import com.clickoo.mercury.util.Constant;
import com.clickoo.mercury.util.SystemMessageUtil;

public class CITools {
	
	private static final Logger logger = LoggerFactory.getLogger(CITools.class);
	private static ConcurrentHashMap<String, String> serverList = new ConcurrentHashMap<String, String>();

	/**
	 * 获得服务器IP地址
	 * 
	 * @return CI_IP地址
	 */
	public static String getCurrentServerIP() {
		String ip ="";
//		ip = SystemMessageUtil.getLocalMac();
		ip = SystemMessageUtil.getLocalIp();
		return "CI_"+ip;
	}
	/**
	 * 获得服务器IP地址
	 * @return
	 */
	public static String getLocalServerIp(){
		return SystemMessageUtil.getLocalIp();
	}
	/**
	 * 获得服务器时间
	 */
	public static String getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(cal.getTime());
	}

	/**
	 * 计算时间差
	 * 
	 * @param beginTime
	 * @param endTime
	 * @param keepTime
	 * @return true：存活 false：死亡
	 */
	public static boolean isFailState(String beginTime, String endTime,
			long keepTime) {
		boolean flag = true;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date beginDate;
		Date endDate;
		try {
			long result;
			beginDate = dateFormat.parse(beginTime);
			endDate = dateFormat.parse(endTime);
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(beginDate);
			c2.setTime(endDate);
			logger.info("------"+beginTime+"------"+endTime);
			result = c2.getTimeInMillis() - c1.getTimeInMillis();
			logger.info("----------time result"+result);
			if (result > keepTime*60*1000) {
				flag = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 获取缓存服务器中所有服务器信息
	 * @param CIIP
	 */
	@SuppressWarnings("unchecked")
	public static ConcurrentHashMap<String, String> getAllServerInfo(){
		if(MemCachedManager.getMcc().keyExists(CIConstants.SERVER_LIST)){
			serverList =  (ConcurrentHashMap<String, String>) MemCachedManager.getMcc().get(CIConstants.SERVER_LIST);
		}
		logger.info("-----------current map size ---------------"+serverList.size());
		return serverList;
	}
	
	/**
	 * 缓存服务器中添加服务器信息
	 */
	@SuppressWarnings("unchecked")
	public void addServerList(String IP,String serverType){
		logger.info("come in............");
		if(MemCachedManager.getMcc().keyExists(CIConstants.SERVER_LIST)){
			serverList =  (ConcurrentHashMap<String, String>) MemCachedManager.getMcc().get(CIConstants.SERVER_LIST);
			serverList.put(IP, serverType);
			logger.info("update cache............" +IP+"-------"+serverType);
			MemCachedManager.getMcc().set(CIConstants.SERVER_LIST, serverList);
			logger.info("-----------------------"+serverList.size());
		}else{
			serverList.put(IP, serverType);
			logger.info("add cache............" +IP+"-------"+serverType);
			MemCachedManager.getMcc().set(CIConstants.SERVER_LIST, serverList);
		}
	}
	
	/**
	 * 缓存服务器中删除服务器信息
	 * @param CIIP
	 */
	@SuppressWarnings("unchecked")
	public void deleteMasterList(String IP){
		System.out.println(IP);
		if(MemCachedManager.getMcc().keyExists(CIConstants.SERVER_LIST)){
			serverList =  (ConcurrentHashMap<String, String>) MemCachedManager.getMcc().get(CIConstants.SERVER_LIST);
			if(serverList.size() > 0){
				if(serverList.containsKey(IP)){
					serverList.remove(IP);
					MemCachedManager.getMcc().set(CIConstants.SERVER_LIST, serverList);
				}
			}
		}
		logger.info("----------delete--------"+IP);
	}
	
	/**
	 * 从本地缓存中判断当前服务器是否为Master
	 * @param CIIP
	 */
	public static boolean CurrentIsMaster(){
		boolean flag = false;
		String key = CITools.getCurrentServerIP();
		if(ServerCacheMap.selectCIServer().containsKey(key)){
			ServerInfo serverInfo = ServerCacheMap.selectCIServer().get(key);
			if(serverInfo != null && serverInfo.getServerType().equals(Constant.SERVER_MASTER)){
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 获取缓存服务器中服务器信息
	 * @param CIIP
	 */
	public ServerInfo getServerInfoByIP(String CIIP){
		ServerInfo serverInfo = new ServerInfo();
		serverList = getAllServerInfo();
		if(serverList.containsKey(CIIP)){
			String serverType = serverList.get(CIIP);
			serverInfo.setServerType(serverType);
			serverInfo.setIp(CIIP);
			if(MemCachedManager.getMcc().keyExists(CIIP)){
				String date = (String) MemCachedManager.getMcc().get(CIIP);
				serverInfo.setDate(date);
			}
		}
		return serverInfo;
	}
	

	// @SuppressWarnings("unchecked")
	// public static void main(String args[]) {
	// ClickooImapProperties pd = new ClickooImapProperties();
	// pd.init();
	// CITools tools = new CITools();
	// serverList = tools.getAllServerInfo();
	// System.out.println(serverList.size());
	// Iterator itor = serverList.keySet().iterator();
	// while(itor.hasNext()){
	// String key = (String) itor.next();
	// String type = serverList.get(key);
	// System.out.println(key+"---------------"+type);
	// }
	// System.out.println("---------------"+MemCachedManager.getMcc().get("CI_192.168.20.201"));
	// System.out.println("---------------"+MemCachedManager.getMcc().get("CI_192.168.20.210"));
	// System.out.println("---------------"+MemCachedManager.getMcc().get("CI_192.168.20.47"));
	// // MemCachedManager.getMcc().delete(CIConstants.SERVER_LIST);
	// // MemCachedManager.getMcc().delete("CI_192.168.20.201");
	// // MemCachedManager.getMcc().delete("CI_192.168.20.210");
	// // MemCachedManager.getMcc().delete("CI_192.168.20.47");
	// }
}
