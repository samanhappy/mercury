package com.dreamail.mercury.sendMail.jms;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cache.ReceiveServerCacheManager;
import com.dreamail.mercury.cache.SendServerCacheManager;
import com.dreamail.mercury.cache.VolumeCacheManager;
import com.dreamail.mercury.sendMail.threadpool.PutThreadPool;
import com.dreamail.mercury.util.Constant;

public class MessageReceiver {

	private static Logger logger = LoggerFactory
			.getLogger(MessageReceiver.class);

	public void onMessage(Object msg) {
		logger.info("receive JMS message!!!" + msg.toString());
		if (msg instanceof JSONObject) {
			PutThreadPool.putPool(msg);
		} else if (msg instanceof String) {
			String msgStr = (String) msg;
			logger.info("message jms str is:" + msgStr);
			/**
			 * 更新send和receive
			 */
			if (msgStr.contains(Constant.CLICKOO_MAIL_SERVER)) {
				ReceiveServerCacheManager.init();
				SendServerCacheManager.init();
				logger.info("-----[" + msgStr + "]cache update-----");
			}
			/**
			 * 更新volume
			 */
			else if (Constant.CLICKOO_VOLUME.equals(msgStr)) {
				new VolumeCacheManager().init();
				logger.info("-----[" + msgStr + "]cache update-----");
			} else {
				logger.error("there is no handle process for this message:"
						+ msgStr);
			}
		} else {
			logger.error("error msg type is " + msg.getClass());
		}
	}
}
