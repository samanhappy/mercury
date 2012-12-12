package com.clickoo.clickooImap.server.state;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clickoo.clickooImap.config.ClickooImapProperties;
import com.clickoo.clickooImap.domain.Notice;
import com.clickoo.clickooImap.domain.ServerInfo;
import com.clickoo.clickooImap.server.cache.ServerCacheMap;
import com.clickoo.clickooImap.server.handler.impl.ServerSecond;
import com.clickoo.clickooImap.utils.CIConstants;
import com.clickoo.clickooImap.utils.CITools;

public abstract class StateChange {
	private static Logger logger = LoggerFactory.getLogger(StateChange.class);
	
	public static String key = CITools.getCurrentServerIP();
	/**
	 * Master监控所有服务器
	 */
	public String isAlive(){
		CITools tools = new CITools();
		String failedServerIP = "";
		Iterator<String> itor = (CITools.getAllServerInfo()).keySet().iterator();
		while(itor.hasNext()){
			String ipAddress = itor.next();
			logger.info("----"+key+"-----"+ipAddress+"-----");
			if (!key.equals(ipAddress)) {
				ServerInfo serverInfo = (ServerInfo) tools.getServerInfoByIP(ipAddress);
				String serverDate = serverInfo.getDate().toString();
				// true 存活
				boolean flag = CITools.isFailState(serverDate, CITools.getCurrentDate(), Long.parseLong(ClickooImapProperties.getConfigureMap().get("dead_time")));
				logger.info("-----Server:" + ipAddress + ",flag:"+ flag);
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
	public void findNewSecond(String failedServerIP){
		// 并判断是不是second挂了，second挂了，重新寻找second
		CITools tools = new CITools();
		ServerInfo serverInfo = (ServerInfo) tools.getServerInfoByIP(failedServerIP);
		logger.info("---failed server type--"+ serverInfo.getServerType() + "----");
		if (serverInfo.getServerType().equals(CIConstants.ServerType.CI_SERVER_SECOND)) {
			logger.info("-------master server find ["+ failedServerIP+ "]second server failed---------");
			ConcurrentHashMap<String, ServerInfo>  hashMap = ServerCacheMap.selectCIServer();
			Iterator<String> setSecondItor = (Iterator<String>) hashMap.keySet().iterator();
			logger.info("-------master server set second server----------");
			logger.info("-------------------------hashMap:"+hashMap.size());
			logger.info(""+hashMap);
			while (setSecondItor.hasNext()) {
				String currentMapIP = setSecondItor.next();
				ServerInfo commonTFInfo = hashMap.get(currentMapIP);
				commonTFInfo.setIp(currentMapIP);
				logger.info("--------------SERVER:"+commonTFInfo.getServerType());
				if (commonTFInfo.getServerType().equals(CIConstants.ServerType.CI_SERVER_COMMON)) {
					logger.info("new second server :"+currentMapIP);
					commonTFInfo.setServerType(CIConstants.ServerType.CI_SERVER_SECOND);
					// 重新更新了时间
					commonTFInfo.setDate(CITools.getCurrentDate());
					
					tools.addServerList(currentMapIP,CIConstants.ServerType.CI_SERVER_SECOND);
					// 更新本地缓存
					ServerCacheMap.addCIServer(currentMapIP,commonTFInfo);
					//启动second server
					break;
				}
			}
		} else {
			logger.info("-------master server find ["+ failedServerIP+ "]common server failed---------");
		}
	}
	
	public void changeMaster(){
		String masterIP = "";
		ServerSecond secondServer = new ServerSecond();
		Notice notice = new Notice();
		//从本地缓存中获取当前Mster Server IP
		Iterator<String> itor = (Iterator<String>) ServerCacheMap.selectCIServer().keySet().iterator();
		while (itor.hasNext()) {
			ServerInfo tfInfo = ServerCacheMap.selectCIServer().get(itor.next());
				if(tfInfo.getServerType().equals(CIConstants.ServerType.CI_SERVER_MASTER)){
					masterIP = tfInfo.getIp();
				}
			}
		if(!"".equals(masterIP)){
			notice.setIp(masterIP);
			notice.setMsg("[change second]");
			secondServer.handleMsg(notice);
			logger.info("---------------["+masterIP+"]change["+key+"]to second server-----");
		}
	}
}
