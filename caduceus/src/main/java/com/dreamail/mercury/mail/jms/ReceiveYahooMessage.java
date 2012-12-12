package com.dreamail.mercury.mail.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.receiver.mail.threadpool.PutYahooThreadPool;

public class ReceiveYahooMessage {
	public static final Logger logger = LoggerFactory
			.getLogger(ReceiveYahooMessage.class);

	public void onMessage(Object msg) {
		try {
			if (msg instanceof String) {
				String message = (String) msg;
				logger.info("----------ReceiveYahooMessage[" + message
						+ "]request---------");
				// 接受收取邮件account信息通知
				if (!"".equals(message)) {
					PutYahooThreadPool.putPool(msg);
				}
			}
		} catch (Exception e) {
			logger.error("Error receiveMSG.", e);
		}
	}
}
