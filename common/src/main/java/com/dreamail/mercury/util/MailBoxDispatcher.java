package com.dreamail.mercury.util;

import java.util.ArrayList;
import java.util.List;

import com.dreamail.mercury.configure.PropertiesDeploy;

public class MailBoxDispatcher {

	public static List<String> yahooSNPSupportMailBoxList = new ArrayList<String>();

	public static List<String> gmailSupportMailBoxList = new ArrayList<String>();

	public static List<String> hotmailSupportMailBoxList = new ArrayList<String>();

	/**
	 * 加载配置信息.
	 */
	static {
		String supportMailBoxs = PropertiesDeploy.getConfigureMap().get(
				"yahooSNPMailBoxs");
		String[] strs = supportMailBoxs.split(",");
		for (String str : strs) {
			yahooSNPSupportMailBoxList.add("@" + str + ".");
		}

		supportMailBoxs = PropertiesDeploy.getConfigureMap().get(
				"gmailMailBoxs");
		strs = supportMailBoxs.split(",");
		for (String str : strs) {
			gmailSupportMailBoxList.add("@" + str + ".");
		}

		supportMailBoxs = PropertiesDeploy.getConfigureMap().get(
				"hotmailMailBoxs");
		strs = supportMailBoxs.split(",");
		for (String str : strs) {
			hotmailSupportMailBoxList.add("@" + str + ".");
		}
	}

	/**
	 * 判断是否支持yahooSNP.
	 * 
	 * @param username
	 * @return
	 */
	public static boolean isYahooSNPSupport(String username) {
		for (String str : yahooSNPSupportMailBoxList) {
			if (username.contains(str)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否为Gmail邮箱.
	 * 
	 * @param username
	 * @return
	 */
	public static boolean isGmailSupport(String username) {
		for (String str : gmailSupportMailBoxList) {
			if (username.contains(str)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否为hotmail邮箱.
	 * 
	 * @param username
	 * @return
	 */
	public static boolean isHotmailSupport(String username) {
		for (String str : hotmailSupportMailBoxList) {
			if (username.contains(str)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据用户名得到发送邮件的Queue.
	 * 
	 * @param username
	 * @return
	 */
	public static String getSendMailQueueByType(String username) {
		if (isGmailSupport(username)) {
			return JMSTypes.GMAIL_SEND_MAIL_QUEUE;
		} else if (isYahooSNPSupport(username)) {
			return JMSTypes.YAHOO_SEND_MAIL_QUEUE;
		} else if (isHotmailSupport(username)) {
			return JMSTypes.HOTMAIL_SEND_MAIL_QUEUE;
		} else {
			return JMSTypes.OTHER_SEND_MAIL_QUEUE;
		}
	}
}
