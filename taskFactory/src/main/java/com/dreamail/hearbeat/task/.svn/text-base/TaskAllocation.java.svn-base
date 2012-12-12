package com.dreamail.hearbeat.task;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.hearbeat.server.NameNodeServer;
import com.dreamail.jms.JmsSender;
import com.dreamail.jms.SendMessageThread;
import com.dreamail.jms.controller.ControllerAccount;
import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.dao.TaskFactoryDao;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_task_factory;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.JMSTypes;

public class TaskAllocation {

	public static final Logger logger = LoggerFactory.getLogger(ControllerAccount.class);
	
	private static ConcurrentHashMap<String, String> accountMap = new ConcurrentHashMap<String, String>();
	
	/**
	 * 获取本机IP.
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static String initLocalIp() {
		String ip = "";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			logger.error("fail to get IP address", e);
		}
		return ip;
	}

	/**
	 * 服务器启动初始化任务
	 */	
	public static void allAccountTask() {
		
		//服务器信息
		TaskFactoryDao taskFactoryDao = new TaskFactoryDao();
		Clickoo_task_factory clickoo_task_factory = new Clickoo_task_factory();
		clickoo_task_factory.setMckey("5555");
		clickoo_task_factory.setMcvalue(initLocalIp());
		
		if(taskFactoryDao.getTaskParameterByType("3").size() == 1){
			taskFactoryDao.updateTaskParameterByMCKey(clickoo_task_factory);
		}else{
			clickoo_task_factory.setMctype("3");
			taskFactoryDao.addTaskParameter(clickoo_task_factory);
		}
		
		List<Clickoo_mail_account> accountList = new AccountDao().getAllMailAccount();
		
		for(Clickoo_mail_account account:accountList){
			accountMap.put(String.valueOf(account.getId()), String.valueOf(account.getType()));
		}
		logger.info("accountMap size :"+accountMap.size());
	}
	/**
	 * 服务器下发任务
	 */
	public static void taskReload() {
		Iterator<String> itor = accountMap.keySet().iterator();
		JSONObject allMessages = new JSONObject();
		while (itor.hasNext()) {
			String aID = itor.next();
			String aType = accountMap.get(aID);
			JSONObject json = new JSONObject();
			json.put(JMSConstans.JMS_TYPE_KEY,JMSConstans.JMS_RECEIVEMAIL_TYPE);
			json.put(JMSConstans.JMS_AID_KEY, aID);
			json.put(JMSConstans.JMS_ACCOUNT_TYPE_KEY, aType);
			allMessages.accumulate(aType,json.toString());
		}
		@SuppressWarnings("unchecked")
		Iterator<String> it = allMessages.keys();
		while (it.hasNext()) {
			String key = it.next();
			Object value = allMessages.get(key);
			new SendMessageThread(key, value).start();
		}
	}
	
	/**
	 * 账号添加账号上线
	 * @param aid   账号id
	 * @param aType 账号类型
	 */
	public static void addOrOnlineAccount(String aid,String aType) {
		if(!accountMap.containsKey(aid)){
			logger.info("---------addOrOnlineAccount-------"+aid+","+aType);
			accountMap.put(aid, aType);
			JSONObject json = new JSONObject();
			json.put(JMSConstans.JMS_TYPE_KEY,JMSConstans.JMS_RECEIVEMAIL_TYPE);
			json.put(JMSConstans.JMS_AID_KEY, aid);
			json.put(JMSConstans.JMS_ACCOUNT_TYPE_KEY, aType);
			new SendMessageThread(aType, json.toString()).start();
		}else{
			logger.info("account[" + aid + "] ");
		}
	}
	
	/**
	 * 账号删除账号下线
	 * @param aid   账号id
	 */
	public static void removeOrOfflineAccount(String aid) {
		if(accountMap.containsKey(aid)){
			logger.info("---------removeOrOfflineAccount-------"+aid);
			accountMap.remove(aid);
			JSONObject json = new JSONObject();
			json.put(JMSConstans.JMS_TYPE_KEY,JMSConstans.JMS_REMOVEACCOUNT_TYPE);
			json.put(JMSConstans.JMS_AID_KEY, aid);
			JmsSender.sendTopicMsg(json.toString(), JMSTypes.REMOVE_TOPIC);
		}else{
			logger.info("account[" + aid + "] ");
		}
	}
}
