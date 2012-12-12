package com.dreamail.mercury.cache;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_message;

public class MsgCacheManager {
	
	private static Logger logger = LoggerFactory
			.getLogger(MsgCacheManager.class);
	
	private static ConcurrentHashMap<String, Map<String,String>> msgcache;

	public void init() {
		AccountDao accountDao = new AccountDao();
		MessageService msgDao = new MessageService();
		List<Clickoo_message> msgList = null;
		msgcache = getMsgcache();
		List<Clickoo_mail_account> accountList = accountDao
				.getAllValidAccountList();
		for (Clickoo_mail_account account : accountList) {
			Map<String,String> map = new LinkedHashMap<String,String>(); 
			msgList = msgDao
					.getAllReceivedMessageIdByAccountId(account.getId());
			Long accountID = account.getId();
			if (msgList != null) {
				for(Clickoo_message clickooMessage:msgList){
					if(clickooMessage==null){
						continue;
					}
					map.put(clickooMessage.getUuid(), String.valueOf(clickooMessage.getId()));
				}
				msgcache.put(String.valueOf(accountID), map);
			}
		}
		logger.info("MsgCacheManager init......");
	}

	public static Map<String,String> getMIDCache(Long accountID) {
		Map<String,String> element = null;
		if(msgcache.get(String.valueOf(accountID))!=null){
			 element = (Map<String,String>)(msgcache.get(String.valueOf(accountID)));
		}
		return element;
	}
	/**
	 * init方法调用，同步数据库的uuid.
	 * @param accountID
	 * @param list
	 * @param newuuidList
	 */
	public static void addMID(Long accountID, Map<String,String> list,
			Map<String,String> newuuidList) {
		if (list != null && newuuidList!=null) {
			list.putAll(newuuidList);
			msgcache.put(String.valueOf(accountID), list);
		}
	}
	
	public static void updateMid(Long accountID, Map<String,String> list) {
		if (list != null) {
			msgcache.put(String.valueOf(accountID), list);
		}
	}
	
	/**
	 * 判断uuid是否在缓存中.
	 * @param accountID
	 * @param uuid
	 * @return
	 */
	public static boolean containsUuid(Long accountID, String uuid) {
		Map<String,String> element = (Map<String,String>) msgcache.get(String.valueOf(accountID));
		if (element != null) {
			return element.containsKey(uuid);
		}
		return false;
	}
	
	private static ConcurrentHashMap<String, Map<String,String>> getMsgcache(){
		if(msgcache == null){
			msgcache = new ConcurrentHashMap<String, Map<String,String>>();
		}
		return msgcache;
	}

}
