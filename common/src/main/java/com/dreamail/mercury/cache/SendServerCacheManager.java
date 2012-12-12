package com.dreamail.mercury.cache;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.SendServerDao;
import com.dreamail.mercury.pojo.Clickoo_mail_send_server;

public class SendServerCacheManager {
	
	private static Logger logger = LoggerFactory
		.getLogger(SendServerCacheManager.class);
	
	private static ConcurrentHashMap<String, Clickoo_mail_send_server> sendServercache;
	
	public static ConcurrentHashMap<String, Clickoo_mail_send_server> getServerConfig(){
		if(sendServercache != null){
			return sendServercache;
		}else{
			init();
			return sendServercache;
		}
	}
	
	public static void init() {
		sendServercache = getSendServercache();
		SendServerDao dao = new SendServerDao();
		List<Clickoo_mail_send_server> servers = (List<Clickoo_mail_send_server>) dao.getAllSendServer();
		for(Clickoo_mail_send_server r:servers){
			sendServercache.put(r.getName(), r);
			logger.info("sync receive data--------------" + r.getOutPath());
		}
		logger.info("ReceiveServerCacheManager init end......");
	}
	
	public static Clickoo_mail_send_server get(String name){
		Clickoo_mail_send_server config = getServerConfig().get(name);
		if(config == null){
			config = getServerConfig().get(getRegName(name));
		}
		return config;
	}
	
	public static String getRegName(String name){
		return name.substring(0, name.indexOf(".") + 1) + "*";
	}
		
	public static Clickoo_mail_send_server getCacheObject(String name) {
		return get(name);
	}
	
	public static void addSendServerCacheObject(Clickoo_mail_send_server sendServer) {
		String name = getRegName(sendServer.getName());
		if(sendServercache.get(name)!=null){
			//说明有XXX.*存在，无需添加
			return;
		}
		sendServercache.putIfAbsent(sendServer.getName(), sendServer);
	}
	
	private static ConcurrentHashMap<String, Clickoo_mail_send_server> getSendServercache(){
		if(sendServercache == null){
			sendServercache = new ConcurrentHashMap<String, Clickoo_mail_send_server>();
		}
		return sendServercache;
	}
	
	public static void main(String[] args) {
		new SendServerCacheManager().init();
	}
}
