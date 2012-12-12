package com.dreamail.mercury.talaria.jms;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.talaria.memcached.UPECacheManager;
import com.dreamail.talaria.memcached.UPECacheObject;

public class TimerReceiver {

	private Logger logger = LoggerFactory.getLogger(TimerReceiver.class);

	public void onMessage(Object msg) {
		if (msg instanceof String) {
			String msgStr = (String) msg;
			logger.info("timer message str is:" + msgStr);

			JSONObject json = null;
			try {
				json = JSONObject.fromObject(msgStr);
			} catch (Exception e) {
				logger.info("jms message is error!!!!!!");
				return;
			}

			String type = json.getString(JMSConstans.JMS_TYPE_KEY);

			if (JMSConstans.JMS_ADD_ACCOUNT_TYPE.equalsIgnoreCase(type)) {
				String uid = json.getString(JMSConstans.JMS_UID_KEY);
				String aid = json.getString(JMSConstans.JMS_AID_KEY);
				UPECacheManager.getLock(uid);
				UPECacheObject cache = UPECacheManager.getCacheObject(uid);
				if (cache != null) {
					cache.addAid(aid);
					UPECacheManager.setCacheObject(cache);
					logger.info("add a account by aid:" + aid + " for uid:"
							+ uid);
				} else {
					logger.info("cache for is null,do nothing...");
				}
				UPECacheManager.removeLock(uid);

			} else if (JMSConstans.JMS_REMOVE_ACCOUNT_TYPE.equals(type)) {
				String uid = json.getString(JMSConstans.JMS_UID_KEY);
				String aid = json.getString(JMSConstans.JMS_AID_KEY);

				UPECacheManager.getLock(uid);
				UPECacheObject cache = UPECacheManager.getCacheObject(uid);
				if (cache != null) {
					cache.removeAid(aid);
					logger.info("remove a account by aid:" + aid + " for uid:"
							+ uid);
					UPECacheManager.setCacheObject(cache);
				} else {
					logger.info("cache for is null,do nothing...");
				}
				
				UPECacheManager.removeLock(uid);
			} else if (JMSConstans.JMS_DISABLE_USER_TYPE.equals(type)) {
				String uid = json.getString(JMSConstans.JMS_UID_KEY);
				
				UPECacheManager.getLock(uid);
				UPECacheObject cache = UPECacheManager.getCacheObject(uid);

				// 判断uid是否一致
				if (cache == null || !uid.equals(cache.getUid())) {
					logger
							.info("cache object is null or uids are not same, do nothing...");
					UPECacheManager.removeLock(uid);
					return;
				}
				cache.setDisable(true);
				UPECacheManager.setCacheObject(cache);
				UPECacheManager.removeLock(uid);

			} else if (JMSConstans.JMS_ENABLE_USER_TYPE.equals(type)) {
				String uid = json.getString(JMSConstans.JMS_UID_KEY);

				UPECacheManager.getLock(uid);
				UPECacheObject cache = UPECacheManager.getCacheObject(uid);

				// 判断uid是否一致
				if (cache == null || !uid.equals(cache.getUid())) {
					logger
							.info("cache object is null or uids are not same, do nothing...");
					UPECacheManager.removeLock(uid);
					return;
				}

				cache.setDisable(false);
				UPECacheManager.setCacheObject(cache);
				UPECacheManager.removeLock(uid);
			}
		}
	}

}
