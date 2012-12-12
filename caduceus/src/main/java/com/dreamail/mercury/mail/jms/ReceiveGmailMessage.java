package com.dreamail.mercury.mail.jms;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.receiver.mail.threadpool.PutGmailThreadPool;

public class ReceiveGmailMessage {
	public static final Logger logger = LoggerFactory
			.getLogger(ReceiveGmailMessage.class);

	public void onMessage(Object msg) {
		logger.info("receive gmailmessage:"+msg);
		try {
			if (msg instanceof String) {
				PutGmailThreadPool.putPool(msg);
			}else{
				logger.error("msg class:"+msg.getClass());
			}
		} catch (Exception e) {
			logger.error("Error receiveMSG.", e);
		}
	}
}
