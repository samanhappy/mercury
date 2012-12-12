package com.dreamail.mercury.cache;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.ReceiveServerDao;
import com.dreamail.mercury.pojo.Clickoo_mail_receive_server;

public class ReceiveServerCacheManager {
	
	private static Logger logger = LoggerFactory
		.getLogger(ReceiveServerCacheManager.class);
	
	private static ConcurrentHashMap<String, Clickoo_mail_receive_server> receiveServercache;
	
	public static ConcurrentHashMap<String, Clickoo_mail_receive_server> getServerConfig(){
		if(receiveServercache!=null){
			return receiveServercache;
		}else{
			init();
			return receiveServercache;
		}
	}
	
	public static void init() {
		receiveServercache = getReceiveServercache();
		ReceiveServerDao dao = new ReceiveServerDao();
		List<Clickoo_mail_receive_server> servers = (List<Clickoo_mail_receive_server>) dao.getAllReceiveServer();
		for(Clickoo_mail_receive_server r:servers){
			receiveServercache.put(r.getName(), r);
			logger.info("sync receive data--------------" + r.getInPath());
		}
		logger.info("ReceiveServerCacheManager init end......");
	}
	
	public static Clickoo_mail_receive_server getCacheObject(String name) {
		return get(name);
	}
	
	public static ConcurrentHashMap<String, Clickoo_mail_receive_server> getAllCacheObject() {
		return getServerConfig();
	}
	
	public static void addReceiveServerCacheObject(Clickoo_mail_receive_server receiveServer){
		String name = getRegName(receiveServer.getName());
		if(receiveServercache.get(name)!=null){
			//说明有XXX.*存在，无需添加
			return;
		}
		receiveServercache.putIfAbsent(receiveServer.getName(), receiveServer);
	}
	
	private static ConcurrentHashMap<String, Clickoo_mail_receive_server> getReceiveServercache(){
		if(receiveServercache == null){
			receiveServercache = new ConcurrentHashMap<String, Clickoo_mail_receive_server>();
		}
		return receiveServercache;
	}
	
	public static Clickoo_mail_receive_server get(String name){
		Clickoo_mail_receive_server config = getServerConfig().get(name);
		if(config == null){
			config = getServerConfig().get(getRegName(name));
		}
		return config;
	}
	
	public static String getRegName(String name){
		return name.substring(0, name.indexOf(".") + 1) + "*";
	}
	
	public static void main(String[] args) {
//		ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();
//		map.put("1", "1");
//		System.out.println(map.putIfAbsent("1", "2"));
//		System.out.println(map.get("1"));
		String name = "yahoo.com.cn";
		System.out.println(name.substring(0, name.indexOf(".") + 1) + "*");
		
	}
}

