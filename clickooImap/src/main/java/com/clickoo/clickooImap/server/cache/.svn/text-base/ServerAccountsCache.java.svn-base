package com.clickoo.clickooImap.server.cache;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.clickoo.clickooImap.domain.IdleMessage;
import com.clickoo.clickooImap.domain.Notice;
import com.clickoo.clickooImap.domain.ServerInfo;
import com.clickoo.clickooImap.jms.IdleMessageSender;
import com.clickoo.clickooImap.netty.server.MsgServerHandler;
import com.clickoo.clickooImap.utils.CIConstants;
import com.clickoo.clickooImap.utils.CITools;

public class ServerAccountsCache {

	public static final Logger logger = LoggerFactory
			.getLogger(ServerAccountsCache.class);
	public static ConcurrentHashMap<String, ConcurrentHashMap<String, IdleMessage>> accountsCache = new ConcurrentHashMap<String, ConcurrentHashMap<String, IdleMessage>>();

	/**
	 * 根据IP向服务器本地缓存中添加该服务器上的账户信息
	 * 
	 * @param Ip
	 * @param idleMessage
	 */
	public static void addServerAccounts(IdleMessage idleMessage) {
		String keyOfServerAccounts = "AIDS" + idleMessage.getFromIp();
		ConcurrentHashMap<String, IdleMessage> map = getAllAccountByServerIp(idleMessage
				.getFromIp());
		if (map == null) {
			map = new ConcurrentHashMap<String, IdleMessage>();
		}
		map.put(idleMessage.getAccountName(), idleMessage);
		accountsCache.put(keyOfServerAccounts, map);
		logger.info("Server["+CITools.getLocalServerIp()+"] addServerAccounts---------------" + accountsCache);
	}

	/**
	 * 根据Ip向服务器本地缓存中添加该服务器IP
	 * 
	 * @param ip
	 */
	public static void addServer(String ip) {
		String keyOfServerAccounts = "AIDS" + ip;
		ConcurrentHashMap<String, IdleMessage> map = new ConcurrentHashMap<String, IdleMessage>();
		if (!accountsCache.containsKey(keyOfServerAccounts)) {
			accountsCache.put(keyOfServerAccounts, map);
			logger.info("Add ["+keyOfServerAccounts+"] into Server ["+CITools.getLocalServerIp()+"],then ["+accountsCache+"]");
		}else{
			logger.error("Server ["+CITools.getLocalServerIp()+"] has had ["+keyOfServerAccounts+"],accountsCache ["+accountsCache+"]");
		}
	}

	/**
	 * 根据Ip从服务器本地缓存中取得该服务器上的所有的账户
	 * 
	 * @param Ip
	 * @return
	 */
	public static ConcurrentHashMap<String, IdleMessage> getAllAccountByServerIp(
			String Ip) {
		String keyOfServerAccounts = "AIDS" + Ip;
		ConcurrentHashMap<String, IdleMessage> accounts = null;
		if (accountsCache.containsKey(keyOfServerAccounts)) {
			accounts = (ConcurrentHashMap<String, IdleMessage>) accountsCache
					.get(keyOfServerAccounts);
		}else{
			logger.error("Server ["+CITools.getLocalServerIp()+"] does not have ["+keyOfServerAccounts+"],accountsCache ["+accountsCache+"]");
		}
		return accounts;
	}

	/**
	 * 根据Ip从服务器本地缓存中移除该服务器上的所有账户
	 * 
	 * @param Ip
	 */
	public static void removeServerAccountsByIp(String Ip) {
		String keyOfServerAccounts = "AIDS" + Ip;
		if (accountsCache.containsKey(keyOfServerAccounts)) {
			accountsCache.remove(keyOfServerAccounts);
			logger.info("Server["+CITools.getLocalServerIp()+"] removeServerAccountsByIp["+Ip+"]----------------------" + accountsCache);
		}else{
			logger.error("Server ["+CITools.getLocalServerIp()+"] does not have ["+keyOfServerAccounts+"],accountsCache ["+accountsCache+"]");
		}
	}
	/**
	 * 将账号从本地缓存中移除
	 * @param Aid
	 */
	public static void removeOfflineAccount(String accountName){
		Iterator<String> it = accountsCache.keySet().iterator();
		logger.info("Remove account ["+accountName+"] from Server ["+CITools.getLocalServerIp()+"]");
		while (it.hasNext()) {
			String keyOfServerAccounts = (String) it.next();
			ConcurrentHashMap<String, IdleMessage> tmpMap = accountsCache.get(keyOfServerAccounts);
			if(tmpMap.containsKey(accountName)){
				tmpMap.remove(accountName);
				logger.info("Server["+CITools.getLocalServerIp()+"] removeOfflineAccount["+accountName+"]------------" + accountsCache);
			}
		}
	}
	/**
	 * 均衡分配挂掉服务器上的账号
	 * 
	 * @param failedServerIP
	 */
	@SuppressWarnings("unchecked")
	public static void loadBalancing(String failedServerIP) {
		String failedKey = "AIDS" + failedServerIP;
		logger.info("Server ["+CITools.getLocalServerIp()+"]accountsCache["+accountsCache+"]");
		if (!accountsCache.containsKey(failedKey)) {
			logger.error("Server["+CITools.getLocalServerIp()+"] accountCache does not have key :[ " + failedKey+ "]");
			return;
		} else {
			ConcurrentHashMap<String, IdleMessage> failedMap = accountsCache.get(failedKey);
			logger.info("The accouts on failed server["+failedServerIP+"] is ["+failedMap.toString()+"]");
			int total = 0;
			int fail = failedMap.size();
			if (fail == 0) {
				logger.info("fail server [" + failedServerIP+ "] does not have accouts on it!");
				return;
			}
			Iterator<String> it = accountsCache.keySet().iterator();
			JSONObject json = null;
			while (it.hasNext()) {
				String key = (String) it.next();// "AIDS"+服务器IP做主键
				ConcurrentHashMap<String, IdleMessage> accountsMap = accountsCache
						.get(key);
				int num = accountsMap.size();// 服务器上的账户数目
				total += num;
				if (json == null) {
					json = new JSONObject();
				}
				if (!(key.equals(failedKey) || key .equalsIgnoreCase(failedKey))) {
					json.put(key, num);
				}
			}
			int servercount = json.size();
			logger.info("total :[" + total + "],fail: [" + fail	+ "],servercount: [" + servercount + "]");
			int average = 0;
			if (servercount == 0) {
				return;
			} else {
				average = total / servercount;
				Iterator<String> it2 = json.keySet().iterator();
				while (it2.hasNext()) {
					String jsonkey = (String) it2.next();// "AIDS"+服务器IP做主键
					JSONObject message = null;
					int differential = average - json.getInt(jsonkey);
					logger.info("differential ------------------------["+differential+"]");
					while (differential > 0) {
						Iterator<String> it3 = failedMap.keySet().iterator();
						while (it3.hasNext()) {
							String string = (String) it3.next();
							IdleMessage idleMessage = failedMap.get(string);
							logger.info("["+string+"]---------idleMessage--------["+idleMessage.getAid()+"] ["+idleMessage.getAccountName()+"] ["+idleMessage.getAccountPwd()+"]");
							if (message == null) {
								message = new JSONObject();
							}
							message.put(idleMessage.getAccountName(), idleMessage);
							failedMap.remove(string);
							differential--;
						}

					}
					// 指定服务器发送消息
					String ip = jsonkey.split("_")[1];
					try {
						MsgServerHandler.messageSent(ip, message.toString());
					} catch (Exception e) {
						logger.error("Fail to messageSent :" + e.getMessage());
					}
				}
			}
			// JMS通知各个服务器删掉对应账号
			Notice notice = new Notice();
			notice.setMsg(CIConstants.NoticeType.CI_SERVER_DEL_ALL_ACCOUNT);
			notice.setIp(failedServerIP);
			IdleMessageSender.syncServerAccounts(notice);
		}

	}
	/**
	 * 均衡分配新增账号
	 * @param idleMessage
	 */
	public static void loadBalancing(IdleMessage idleMessage) {
		ServerInfo currentServerInfo = ServerCacheMap.selectCIServer().get(
				CITools.getCurrentServerIP());
		// 只有Master分配
		if (currentServerInfo.getServerType().equals(CIConstants.ServerType.CI_SERVER_MASTER)) {
			Iterator<String> it = accountsCache.keySet().iterator();
			Integer min = null;
			String ip = null;
			while (it.hasNext()) {
				String key = (String) it.next();// "AIDS"+服务器IP做主键
				ConcurrentHashMap<String, IdleMessage> accountsMap = accountsCache.get(key);
				int num = accountsMap.size();// 服务器上的账户数目
				if (min == null) {
					min = num;
					ip = key.split("_")[1];
				}
				if (min > num) {
					min = num;
					ip = key.split("_")[1];
				}
			}
			JSONObject message = new JSONObject();
			message.put(idleMessage.getAccountName(), idleMessage);
			try {
				MsgServerHandler.messageSent(ip, message.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Fail to messageSent :" + e.getMessage());
			}
		} else {
			logger.info("current server [" + currentServerInfo.getIp()
					+ "] type is[" + currentServerInfo.getServerType() + "]");
		}

	}
}
