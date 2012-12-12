package com.dreamail.mercury.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import com.dreamail.mercury.cache.ReceiveServerCacheManager;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.dal.dao.SenderFilterDao;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_mail_receive_server;
import com.dreamail.mercury.pojo.Clickoo_sender_filter;
import com.dreamail.mercury.pojo.InCert;
import com.dreamail.mercury.pojo.InPath;

public class AccountConver2ContextHelper {
	
//	public static String key;
//
//	static {
//		try {
//			key = PropertiesDeploy.getConfigureMap().get(Constant.KEY);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 将账号缓存信息包装为context信息
	 * @param  AccountCacheObject1 account
	 * @return context集合
	 */
	public static Context changeAccount2Context(Clickoo_mail_account account){
		String function = account.getFunction();
		Context context = new Context();
		String serverName = account.getName().split("@")[1];
		String serverType = null;
		if(serverName.contains(".")){
			serverType = serverName.substring(0, serverName.indexOf("."));
		}else{
			serverType = serverName;
		}
		//yahoo不需要超时时间.
		if(function!=null){
			JSONObject obj = JSONObject.fromObject(function);
			String role = obj.getString(Constant.ROLE_INTERVAL_KEY);
			ConcurrentHashMap<String, Clickoo_mail_receive_server> receiveMap = ReceiveServerCacheManager.getAllCacheObject();
			Set<Entry<String,Clickoo_mail_receive_server>> set = receiveMap.entrySet();
			int intervalTime = 1;
			for(Entry<String,Clickoo_mail_receive_server> en:set){
				String key = en.getKey();
				if(key!=null && ((key.contains(".") && key.substring(0, key.indexOf(".")).equalsIgnoreCase(serverType))
						|| key.equalsIgnoreCase(serverType))){
					Clickoo_mail_receive_server server =  en.getValue();
					intervalTime = server.getIntervaltime();
					break;
				}
			}
			/*int add = Integer.parseInt(PropertiesDeploy.getConfigureMap()
					.get(Constant.INTERVAL_ADD_KEY));
			if(Constant.RETRIVAL_EMAIL_INTERVAL_MED.equalsIgnoreCase(role)){
				intervalTime = intervalTime + add;
			}else if(Constant.RETRIVAL_EMAIL_INTERVAL_LOW.equalsIgnoreCase(role)){
				intervalTime = intervalTime + add + add;
			}*/
			context.setIntervalTime(intervalTime);
		}
		InCert inCert = account.getInCert_obj();
		InPath inPath = account.getInPath_obj();
		context.setAccount(account);
		context.setDeviceModel(account.getDeviceModel());
		StringBuffer modelString = new StringBuffer();
		List<String> modelList = account.getDeviceModelList();
		for(String model : modelList){
			if(model != null && !model.equals("")){
				modelString.append(model);
				modelString.append("#");
			}
		}
		context.setDeviceModelList(modelString.toString());
		context.setRegistrationDate(account.getRegistrationDate());
		context.setAccountId(account.getId());
		context.setStatus(String.valueOf(account.getStatus()));
//		context.setRoleLevel(account.getRoleLevel());
		context.setLoginName(inCert.getLoginID());
		if(inCert.getPwd()!=null)
			context.setPassword(new String(EmailUtils.base64TochangeByte(inCert.getPwd())));
		context.setToken(inCert.getToken());
		context.setReceiveHost(inPath.getInhost());
		context.setReceiveTs(inPath.getReceiveTs());
		context.setPort(inPath.getReceivePort());
		context.setReceiveProtocoltype(inPath.getProtocolType());
		context.setCompositor(inPath.getCompositor());
		context.setSupportAllUid(inPath.getSupportalluid());
		
		JSONObject pjson = JSONObject.fromObject(account.getInPath());
		String[] hosts = pjson.get(Constant.HOST).toString().split(",");
		context.setInpathList(hosts);
		
		if (Constant.USE_SSL.equals(inPath.getSsl())) {
			context.setSSL(true);
		} else if (Constant.NOT_USE_SSL.equals(inPath.getSsl())) {
			context.setSSL(false);
		}
		context.setAccountType(account.getType()); 
		String sign = account.getSign();
		List<Clickoo_sender_filter> filterList = null;
		if(Constant.BLACK_SENDER_FILTER.equals(sign) || Constant.WHITE_SENDER_FILTER.equals(sign)){
			filterList = new SenderFilterDao().selectAllSenderFilter();
			List<String> blackList = new ArrayList<String>();
			List<String> whiteList = new ArrayList<String>();
			if(filterList!=null && filterList.size()>0){
				for(Clickoo_sender_filter filter:filterList){
					if(Constant.BLACK_SENDER_FILTER.equals(String.valueOf(filter.getSign()))){
						blackList.add(filter.getName());
					}else if(Constant.WHITE_SENDER_FILTER.equals(String.valueOf(filter.getSign()))){
						whiteList.add(filter.getName());
					}
				}
				Map<String,List<String>> filterMap = new HashMap<String,List<String>>();
				filterMap.put(Constant.BLACK_SENDER_FILTER, blackList);
				filterMap.put(Constant.WHITE_SENDER_FILTER, whiteList);
			}
		}
		return context;
	}

}
