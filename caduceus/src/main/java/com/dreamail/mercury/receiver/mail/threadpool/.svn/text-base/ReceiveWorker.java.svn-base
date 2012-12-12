package com.dreamail.mercury.receiver.mail.threadpool;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.mail.jms.ReceiveMain;
import com.dreamail.mercury.mail.truepush.idle.impl.IdleThread;
import com.dreamail.mercury.mail.util.EmailConstant;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.receiver.mail.impl.IMAP4ProviderImpl;
import com.dreamail.mercury.util.AccountConver2ContextHelper;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JMSConstans;

public class ReceiveWorker {
	
	public static final Logger logger = LoggerFactory
	.getLogger(ReceiveWorker.class);
	
	public static ConcurrentHashMap<String, String> mailBox = new ConcurrentHashMap<String, String>();
	public final static String YAHOO_SERVER = "yahooSNPMailBoxs";
	public final static String GMAIL_SERVER = "gmailMailBoxs";
	public final static String HOTMAIL_SERVER = "hotmailMailBoxs";
	public final static String OTHER_SERVER = "otherMailBoxs";
	/*static {
		mailBox.put(YAHOO_SERVER, PropertiesDeploy.getConfigureMap().get(
				YAHOO_SERVER));
		mailBox.put(GMAIL_SERVER, PropertiesDeploy.getConfigureMap().get(
				GMAIL_SERVER));
		mailBox.put(HOTMAIL_SERVER, PropertiesDeploy.getConfigureMap().get(
				HOTMAIL_SERVER));
	}*/
	/**
	 * 启动非yahoo账号的邮件的收取
	 * 
	 * @param accounts
	 */
	public Context startCommonAccountTask(String msg) {
		logger.info("common account start----");
		Context context = null;
		JSONObject commonJson = JSONObject.fromObject(msg);
		String aid = commonJson.getString(JMSConstans.JMS_AID_KEY);
		if(ReceiveMain.contextMap!=null && ReceiveMain.contextMap.containsKey(aid)){
			context = ReceiveMain.contextMap.get(aid);
			if(context.getAccountType() == Constant.ACCOUNT_GMAIL_TYPE)
				context.setLoginName(context.getAccount().getInCert_obj().getLoginID());
		}else{
			AccountService accountService = new AccountService();
			Clickoo_mail_account account = accountService
					.getAccountToCacheByAidForC(Long.parseLong(aid));
			if (account == null || account.getName() == null) {
				logger.info("-----thread is no cache data find !!!-----");
				return null;
			}
			context = AccountConver2ContextHelper
					.changeAccount2Context(account);
			context.setDisconnectTime(System.currentTimeMillis());
			context.setConnectStatus(EmailConstant.ACCOUNT_DISCONNECT);
			ReceiveMain.contextMap.put(aid, context);
					
		}
		logger.info("----------" + context.getLoginName() + "maxuuid:"
				+ context.getAccount().getMaxuuid() + " aid:"+" interval:"+context.getIntervalTime());
		long time = context.getIntervalTime() * 60 * 1000;
		context.setTimeout(time);
		try {
			long startMill = System.currentTimeMillis();
			context.setStartMill(startMill);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return context;
	}

	/**
	 * 启动yahoo账号的邮件的收取
	 * 
	 * @param accounts
	 */
	public Context startYahooAccountTask(String accountMessage) {
		logger.info("Yahoo account start----");
		Context context = null;
		JSONObject json = JSONObject.fromObject(accountMessage);
		AccountService accountService = new AccountService();
		Clickoo_mail_account account = accountService
				.getAccountToCacheByAidForC(Long.parseLong(json
						.getString(JMSConstans.JMS_AID_KEY)));
		if (account == null || account.getName() == null ) {
			logger.info("-----thread is no cache data find !!!-----");
			return null;
		}
		try {
			context = AccountConver2ContextHelper
					.changeAccount2Context(account);
			context.setHostName(Constant.YAHOO_HOST_NAME);
			// 放uuid
			try {
				logger.info("json:" + json.toString() + " uuid:"
						+ json.getString("uuid"));
				context.setUuid(json.getString("uuid"));
			} catch (Exception e) {
				logger.info(accountMessage + ":not contains uuid");
			}
			logger.info("----------" + context.getLoginName() + "maxuuid:"
					+ account.getMaxuuid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return context;
	}
	
	/**
	 * 启动Gmail、Hotmail账号的邮件的收取
	 * 
	 * @param accounts
	 */
	public Context startAccountTask(String msg,String hostName) {
		logger.info(hostName+" account start----");
		Context context = null;
		JSONObject commonJson = JSONObject.fromObject(msg);
		String aid = commonJson.getString(JMSConstans.JMS_AID_KEY);
		AccountService accountService = new AccountService();
		Clickoo_mail_account account = accountService
				.getAccountToCacheByAidForC(Long.parseLong(aid));
		if (account == null || account.getName() == null) {
			logger.info("-----thread is no cache data find !!!-----");
			return null;
		}
		context = AccountConver2ContextHelper
				.changeAccount2Context(account);
		context.setHostName(hostName);
		try {
			context.setMailType(commonJson.getString(JMSConstans.JMS_TYPE_KEY));
			logger.info( "jmsType:"+commonJson.getString(JMSConstans.JMS_TYPE_KEY));
		} catch (Exception e) {
			logger.info(msg + ":not contains jmsType");
		}
		if(Constant.GMAIL_HOST_NAME.equals(hostName)){
			if(!EmailConstant.JMSConstant.JMS_GMAIL_NEWMAIL.
					equalsIgnoreCase(context.getMailType())){
				new Thread(new IdleThread(context)).start();
			}
		}
		logger.info("----------" + context.getLoginName() +" interval:"+context.getIntervalTime());
		return context;
	}
	
	/**
	 * 获取邮件服务器名称.
	 * 
	 * @param serverName
	 * @return
	 */
	public static String getServer(String serverName) {
		String server = serverName.substring(0, serverName.indexOf("."));
		Set<String> serverSet = mailBox.keySet();
		for (String s : serverSet) {
			if (s != null && mailBox.get(s) != null
					&& mailBox.get(s).contains(server)) {
				return s;
			}
		}
		return serverName;
	}
	
}
