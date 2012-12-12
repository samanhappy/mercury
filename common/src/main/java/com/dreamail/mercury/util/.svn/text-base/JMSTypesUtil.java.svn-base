package com.dreamail.mercury.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSTypesUtil {

	private static Logger logger = LoggerFactory.getLogger(JMSTypesUtil.class);
	
	public static String getTargetByJmsType(int type) {
		String target = null;
		if (Constant.ACCOUNT_GMAIL_TYPE == type) {
			target = JMSTypes.GMAIL_RECEIVE_MAIL_QUEUE;
		} else if (Constant.ACCOUNT_YAHOOSNP_TYPE == type) {
			target = JMSTypes.YAHOO_RECEIVE_MAIL_QUEUE;
//			logger.error("task factory should not handle yahoo message");
		} else if (Constant.ACCOUNT_HOTMAIL_TYPE == type) {
			target = JMSTypes.HOTMAIL_RECEIVE_MAIL_QUEUE;
		} else {
			target = JMSTypes.OTHER_RECEIVE_MAIL_QUEUE;
		}
		return target;
	}
	
	public static String getHotmailPingTarget() {
		return JMSTypes.HOTMAIL_PING_QUEUE;
	}
}
