package com.dreamail.mercury.mail.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.receiver.mail.threadpool.PutThreadPool;

public class ReceiveMessage {

	public static final Logger logger = LoggerFactory
			.getLogger(ReceiveMessage.class);

	public void onMessage(Object msg) {
		logger.info("receive message:"+msg);
		try {
			if (msg instanceof String) {
				PutThreadPool.putPool(msg);
			}else{
				logger.error("msg class:"+msg.getClass());
			}
		} catch (Exception e) {
			logger.error("Error receiveMSG.", e);
		}
	}
}
