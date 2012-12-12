package com.dreamail.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.config.TaskProperties;
import com.dreamail.mercury.cache.MemCachedManager;
import com.dreamail.mercury.dal.dao.TaskFactoryDao;
import com.dreamail.mercury.pojo.Clickoo_task_factory;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JsonUtil;
import com.dreamail.mercury.util.SystemMessageUtil;
import com.dreamail.taskfactory.msg.ServerCacheMap;
import com.dreamail.taskfactory.msg.ServerInfo;

public class TFTools {
	public static final Logger logger = LoggerFactory
		.getLogger(TFTools.class);
	
	public static final String MASTER_LIST = "MasterList";
	/**
	 * 获得服务器IP地址
	 * 
	 * @return IP地址
	 */
	public static String getCurrentServerIP() {
		String ip ="";
		ip = SystemMessageUtil.getLocalMac();
		return "TF_"+ip;
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
	 * @param TFIP
	 */
	public static ConcurrentHashMap<String, String> getAllServerInfo(){
		return getAllServerFromDB();
	}
	
	/**
	 * 从DB中获取缓存服务器中所有服务器信息
	 * @param TFIP
	 */
	public static ConcurrentHashMap<String, String> getAllServerFromDB(){
		ConcurrentHashMap<String, String> masterList = new ConcurrentHashMap<String, String>();
		TaskFactoryDao taskFactoryDao = new TaskFactoryDao();
		Clickoo_task_factory factory = taskFactoryDao.getTaskParameterByMCKey(MASTER_LIST);
		if(factory != null){
			String mcvalue = taskFactoryDao.getTaskParameterByMCKey(MASTER_LIST).getMcvalue();
			masterList =  (ConcurrentHashMap<String, String>) JsonUtil.stringToMap(mcvalue);
		}
		return masterList;
	}
	
	/**
	 * 缓存服务器中添加服务器信息
	 */
	public void addMasterList(String IP,String serverType){
		String mapStr = "";
		Clickoo_task_factory clickoo_task_factory = new Clickoo_task_factory();
		TaskFactoryDao taskFactoryDao = new TaskFactoryDao();
		ConcurrentHashMap<String, String> masterList = getAllServerFromDB();
		if(masterList.size() > 0){
			masterList.put(IP, serverType);
			mapStr = JsonUtil.mapToString(masterList);
			clickoo_task_factory.setMckey(MASTER_LIST);
			clickoo_task_factory.setMcvalue(mapStr);
			taskFactoryDao.updateTaskParameterByMCKey(clickoo_task_factory);
			logger.info("update master list ---------------");
		}else{
			masterList.put(IP, serverType);
			mapStr = JsonUtil.mapToString(masterList);
			clickoo_task_factory.setMckey(MASTER_LIST);
			clickoo_task_factory.setMcvalue(mapStr);
			clickoo_task_factory.setMctype("1");
			taskFactoryDao.addTaskParameter(clickoo_task_factory);
			logger.info("insert master list ------------------");
		}
	}
	
	/**
	 * 缓存服务器中删除服务器信息
	 * @param TFIP
	 */
	public void deleteMasterList(String IP){
		String mapStr = "";
		Clickoo_task_factory clickoo_task_factory = new Clickoo_task_factory();
		TaskFactoryDao taskFactoryDao = new TaskFactoryDao();
		ConcurrentHashMap<String, String> masterList = getAllServerFromDB();
		masterList.remove(IP);
		if(masterList.size() > 0){
			mapStr = JsonUtil.mapToString(masterList);
			clickoo_task_factory.setMckey(MASTER_LIST);
			clickoo_task_factory.setMcvalue(mapStr);
			taskFactoryDao.updateTaskParameterByMCKey(clickoo_task_factory);
		}else{
			taskFactoryDao.deleteTaskParameterByMCKey(MASTER_LIST);
		}
		taskFactoryDao.deleteTaskParameterByMCKey(IP);
	}
	
	/**
	 * 根據serverType获取服务IP
	 * @param ConcurrentHashMap<String, String> map
	 * @param String serverType
	 */
	public static String getServerIP(ConcurrentHashMap<String, String> map, String serverType) 
	{
		String serverIP = "";
		Iterator<String> itor = map.keySet().iterator();
		while(itor.hasNext())
		{
			String ipKey = (String) itor.next();
			String ipValue = map.get(ipKey);
			if(ipValue.equals(serverType))
			{
				serverIP = ipKey;
				break;
			}
		}
		return serverIP;
	}
	
	/**
	 * 缓存服务器中删除服务器信息
	 * @param TFIP
	 */
	public static void startDelete(String type,String mapStr){
		if("1".equals(type)){
			new TaskFactoryDao().deleteTaskParameterByMCType("1");
		}
		else{
			Clickoo_task_factory clickoo_task_factory = new Clickoo_task_factory();
			clickoo_task_factory.setMckey(MASTER_LIST);
			clickoo_task_factory.setMcvalue(mapStr);
			new TaskFactoryDao().updateTaskParameterByMCKey(clickoo_task_factory);
		}
	}
	
	
	/**
	 * 从本地缓存中判断当前服务器是否为Master
	 * @param TFIP
	 */
	public static boolean currentIsMaster(){
		boolean flag = false;
		String key = getCurrentServerIP();
		if(ServerCacheMap.selectTF().containsKey(key)){
			ServerInfo tfInfo = ServerCacheMap.selectTF().get(key);
			if(tfInfo != null && tfInfo.getServerType().equals(Constant.SERVER_MASTER)){
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 获取缓存服务器中服务器信息
	 * @param TFIP
	 */
	public ServerInfo getServerInfoByIP(String TFIP){
		ServerInfo serverInfo = new ServerInfo();
		ConcurrentHashMap<String, String> masterList = getAllServerInfo();
		if(masterList.containsKey(TFIP)){
			String serverType = masterList.get(TFIP);
			serverInfo.setServerType(serverType);
			serverInfo.setIP(TFIP);
			Clickoo_task_factory factory = new TaskFactoryDao().getTaskParameterByMCKey(TFIP);
			if(factory != null){
				String date = factory.getMcvalue();
				serverInfo.setDate(date);
			}
		}
		return serverInfo;
	}
	
	/**
	 * 设置同步标识
	 */
	public static void setSyncNumber(){
		String numberKey = getCurrentServerIP()+"SYNC";
		MemCachedManager.getMcc().set(numberKey, 1);
	}
	
	/**
	 * 重置同步标识
	 */
	public static void remarkSyncNumber(){
		ConcurrentHashMap<String, String> masterList = getAllServerInfo();
		Iterator<String> itor = masterList.keySet().iterator();
		while(itor.hasNext()){
			String key = (String) itor.next()+"SYNC";
			MemCachedManager.getMcc().set(key, 0);
		}
	}
	
	/**
	 * 判断stoploop通知
	 */
	public static boolean isContinue(){
		boolean flag = true;
		int i = 0 ;
		int currentNumber = 0;
		Iterator<String> itor = getAllServerInfo().keySet().iterator();
		while(itor.hasNext()){
			String key = itor.next() + "SYNC";
			if(MemCachedManager.getMcc().get(key) == null){
				flag = false;
				break;
			}
			Integer ipNumber =  (Integer) MemCachedManager.getMcc().get(key);
			if(i == 0 ){
				currentNumber = ipNumber;
			}else{
				logger.info("currentNumber:"+currentNumber +",ipNumber:"+ipNumber);
				if(currentNumber != ipNumber){
					flag = false;
					break;
				}
			}
			i++;
		}
		return flag;
	}
	
	/**
	 * 设置同步标识
	 */
	public static int serverCount(){
		int serverCount = 0;
		serverCount = TFTools.getAllServerInfo().size();
		if(serverCount ==0){
			serverCount = 1;
		}
		return serverCount;
	}
	
	
	public static void main(String args[]) {
		TaskProperties pd = new TaskProperties();
		pd.init();
		
		
//		ConcurrentHashMap<String, String> masterList = getAllServerInfo();
//		System.out.println(masterList.size());
//		Iterator itor = masterList.keySet().iterator();
//		while(itor.hasNext()){
//			String key = (String) itor.next();
//			String type = masterList.get(key);
//			System.out.println(key+"---------------"+type);
//		}
//		System.out.println("---------------"+MemCachedManager.getMcc().get("TF_00:14:78:36:BC:AC"));
//		System.out.println("---------------"+MemCachedManager.getMcc().get("TF_90:fb:a6:03:db:9c"));
//		System.out.println("---------------"+MemCachedManager.getMcc().get("TF_00:25:11:3a:ae:6d"));
//		System.out.println("---------------"+MemCachedManager.getMcc().get("TF_90-FB-A6-15-31-54"));
//		System.out.println("---------------"+MemCachedManager.getMcc().get("TF_00:16:ec:a9:60:aa"));
//		
//		MemCachedManager.getMcc().delete("MasterList");
//		MemCachedManager.getMcc().delete("TF_00:14:78:36:BC:AC");
//		MemCachedManager.getMcc().delete("TF_90:fb:a6:03:db:9c");
//		MemCachedManager.getMcc().delete("TF_00:25:11:3a:ae:6d");
//		MemCachedManager.getMcc().delete("TF_90-FB-A6-15-31-54");
//		MemCachedManager.getMcc().delete("TF_00:16:ec:a9:60:aa");
//
//		MemCachedManager.getMcc().delete("TF_00:14:78:36:BC:AC"+"SYNC");
//		MemCachedManager.getMcc().delete("TF_90:fb:a6:03:db:9c"+"SYNC");
//		MemCachedManager.getMcc().delete("TF_00:25:11:3a:ae:6d"+"SYNC");
//		MemCachedManager.getMcc().delete("TF_90-FB-A6-15-31-54"+"SYNC");
//		MemCachedManager.getMcc().delete("TF_00:16:ec:a9:60:aa"+"SYNC");
//		
//		System.out.println(serverCount());
	}
}
