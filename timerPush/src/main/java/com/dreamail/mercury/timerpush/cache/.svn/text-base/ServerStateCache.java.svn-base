package com.dreamail.mercury.timerpush.cache;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.TaskFactoryDao;
import com.dreamail.mercury.pojo.Clickoo_task_factory;
import com.dreamail.mercury.timerpush.config.TimerPushProperties;
import com.dreamail.mercury.timerpush.domain.Constants;
import com.dreamail.mercury.timerpush.domain.ServerInfo;
import com.dreamail.mercury.timerpush.serverstate.Tools;
import com.dreamail.mercury.util.JsonUtil;

public class ServerStateCache {
	private static final Logger logger = LoggerFactory.getLogger(ServerStateCache.class);
	
	/**
	 * 从DB中获取缓存服务器中所有服务器信息
	 * @param TFIP
	 */
	public static ConcurrentHashMap<String, String> getAllServerFromDB(){
		ConcurrentHashMap<String, String> masterList = new ConcurrentHashMap<String, String>();
		TaskFactoryDao taskFactoryDao = new TaskFactoryDao();
		Clickoo_task_factory factory = taskFactoryDao.getTaskParameterByMCKey(Constants.TIMERSERVERS);
		if(factory != null){
			String mcvalue = taskFactoryDao.getTaskParameterByMCKey(Constants.TIMERSERVERS).getMcvalue();
			masterList =  (ConcurrentHashMap<String, String>) JsonUtil.stringToMap(mcvalue);
		}
		return masterList;
	}
	/**
	 * 缓存服务器中添加服务器信息
	 */
	public void addServerList(String IP,String serverType){
		String mapStr = "";
		Clickoo_task_factory clickoo_task_factory = new Clickoo_task_factory();
		TaskFactoryDao taskFactoryDao = new TaskFactoryDao();
		ConcurrentHashMap<String, String> masterList = getAllServerFromDB();
		if(masterList.size() > 0){
			masterList.put(IP, serverType);
			mapStr = JsonUtil.mapToString(masterList);
			clickoo_task_factory.setMckey(Constants.TIMERSERVERS);
			clickoo_task_factory.setMcvalue(mapStr);
			taskFactoryDao.updateTaskParameterByMCKey(clickoo_task_factory);
			logger.info("update master list ---------------");
		}else{
			masterList.put(IP, serverType);
			mapStr = JsonUtil.mapToString(masterList);
			clickoo_task_factory.setMckey(Constants.TIMERSERVERS);
			clickoo_task_factory.setMcvalue(mapStr);
			clickoo_task_factory.setMctype("2");
			taskFactoryDao.addTaskParameter(clickoo_task_factory);
			logger.info("insert master list ------------------");
		}
	}
	
	/**
	 * 缓存服务器中删除服务器信息
	 * 
	 */
	public void deleteMasterList(String IP){
		String mapStr = "";
		Clickoo_task_factory clickoo_task_factory = new Clickoo_task_factory();
		TaskFactoryDao taskFactoryDao = new TaskFactoryDao();
		ConcurrentHashMap<String, String> masterList = getAllServerFromDB();
		masterList.remove(IP);
		logger.info("where is my way ----------------"+masterList.size());
		if(masterList.size() > 0){
			mapStr = JsonUtil.mapToString(masterList);
			clickoo_task_factory.setMckey(Constants.TIMERSERVERS);
			clickoo_task_factory.setMcvalue(mapStr);
			taskFactoryDao.updateTaskParameterByMCKey(clickoo_task_factory);
		}else{
			taskFactoryDao.deleteTaskParameterByMCKey(Constants.TIMERSERVERS);
		}
		taskFactoryDao.deleteTaskParameterByMCKey(IP);
	}
	
	/**
	 * 从本地缓存中判断当前服务器是否为Master
	 * 
	 */
//	public static boolean currentIsMaster(){
//		boolean flag = false;
//		String key = Tools.getLocalServerIpKey();
//		if(ServerCacheMap.selectTimerServer().containsKey(key)){
//			ServerInfo serverInfo = ServerCacheMap.selectTimerServer().get(key);
//			if(serverInfo != null && serverInfo.getServerType().equals(Constant.SERVER_MASTER)){
//				flag = true;
//			}
//		}
//		return flag;
//	}
	
	/**
	 * 获取缓存服务器中服务器信息
	 * @param 
	 */
	public ServerInfo getServerInfoByIP(String IP){
		ServerInfo serverInfo = new ServerInfo();
		ConcurrentHashMap<String, String> serverList = getAllServerFromDB();
		if(serverList.containsKey(IP)){
			String serverType = serverList.get(IP);
			serverInfo.setServerType(serverType);
			serverInfo.setIp(IP);
			Clickoo_task_factory factory = new TaskFactoryDao().getTaskParameterByMCKey(IP);
			if(factory != null){
				String date = factory.getMcvalue();
				serverInfo.setDate(date);
			}
		}
		return serverInfo;
	}
	
	/**
	 * 服务器启动前的安全检查
	 */
	public boolean serverLive(String currentIP,String serverType){
		boolean boolflag = false;
		ConcurrentHashMap<String, String> masterMap = ServerStateCache.getAllServerFromDB();
		if(masterMap!=null){
			String deadTime = TimerPushProperties.getConfigureMap().get("dead_time");
			if(masterMap.containsKey(currentIP)){
				ServerInfo serverInfo = getServerInfoByIP(currentIP);
				//false 当前服务器已经死亡 
				if (!Tools.isFailState(serverInfo.getDate(), Tools.getCurrentDate(), Long.parseLong(deadTime))) {
					deleteMasterList(serverInfo.getIp());
				} else {
					logger.info("------Listenering current [" + serverType
							+ "] server is runing---------");
					boolflag = true;
				}
			}
		}
		return boolflag;
	}
}
