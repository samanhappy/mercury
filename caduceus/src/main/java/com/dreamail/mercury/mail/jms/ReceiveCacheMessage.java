package com.dreamail.mercury.mail.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cache.ReceiveServerCacheManager;
import com.dreamail.mercury.cache.SendServerCacheManager;
import com.dreamail.mercury.cache.VolumeCacheManager;
import com.dreamail.mercury.util.Constant;

public class ReceiveCacheMessage {
	public static final Logger logger = LoggerFactory
			.getLogger(ReceiveCacheMessage.class);

	public void onMessage(Object msg) {
		try {
			if (msg instanceof String) {
				String message = (String) msg;
				if (!"".equals(message) && message.contains(":")) {
					/**
					 * 更新send和receive
					 */
					if (message.contains(Constant.CLICKOO_MAIL_SERVER)) {
						new ReceiveServerCacheManager().init();
						new SendServerCacheManager().init();
						logger.info("-----[" + message + "]cache update-----");
					}
					/**
					 * 更新volume
					 */
					if (Constant.CLICKOO_VOLUME.equals(message)) {
						new VolumeCacheManager().init();
						logger.info("-----[" + message + "]cache update-----");
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error receiveMSG.", e);
		}
	}
}
