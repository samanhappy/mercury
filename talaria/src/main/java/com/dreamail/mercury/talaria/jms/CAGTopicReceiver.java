package com.dreamail.mercury.talaria.jms;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.MessageSender;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.talaria.http.AsyncContextManager;
import com.dreamail.mercury.talaria.http.handler.IRequestHandler;
import com.dreamail.mercury.util.CAGConstants;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.UPEConstants;
import com.dreamail.talaria.memcached.UPECacheManager;
import com.dreamail.talaria.memcached.UPEMapping;

public class CAGTopicReceiver {

	private static Logger logger = LoggerFactory
			.getLogger(CAGTopicReceiver.class);

	public void onMessage(Object msg) {

		// 判断消息类型
		if (!(msg instanceof String)) {
			logger.error("\nmsg instance is " + msg.getClass());
			return;
		}

		String msgStr = (String) msg;
		logger.info("message jms str is:" + msgStr);

		JSONObject json = JSONObject.fromObject(msgStr);
		String notification = json.getString(JMSConstans.JMS_TYPE_KEY);
		String uid = json.getString(JMSConstans.JMS_UID_KEY);
		String IMEI = new UserDao().getIMEIByUid(Long.valueOf(uid));

		// 判断IMEI是否存在
		if (IMEI == null || "".equals(IMEI.trim())) {
			logger.info("there is no IMEI by uid:" + uid + " do nothing...");
			json.put(JMSConstans.CAG_RESPONSE_CODE,
					CAGConstants.CAG_CLIENT_UNREACHABLE_CODE);
			json.put(JMSConstans.CAG_RESPONSE_STATUS, "Client Unreachable");
			MessageSender.sendCAGResponseMessage(json.toString());
			return;
		}

		if (CAGConstants.CAG_GETCLIENTCONFIG_NOTIF
				.equalsIgnoreCase(notification)) {
			logger.info("handle for notice:" + notification + " by uid[" + uid
					+ "]...");
			boolean isUserConected = AsyncContextManager.contains(IMEI);
			UPEMapping mapping = UPECacheManager.getMappingObject(IMEI);
			if ((mapping == null && !isUserConected)
					|| (mapping != null && !uid.equals(mapping.getUid()))) {
				logger.info("user connection by uid[" + uid
						+ "] is not here...");
				json.put(JMSConstans.CAG_RESPONSE_CODE,
						CAGConstants.CAG_CLIENT_UNREACHABLE_CODE);
				json.put(JMSConstans.CAG_RESPONSE_STATUS, "Client Unreachable");
				MessageSender.sendCAGResponseMessage(json.toString());
				return;
			}

			String notice = UPEConstants.UPE_GETCLIENTCONFIG_STATE;
			if (isUserConected
					|| (mapping.getMac() != null && mapping.getMac().equals(
							IRequestHandler.mac))) {
				AsyncContextManager.getInstance().sendCAGNotice(IMEI, uid,
						notice, notification);
			} else {
				logger.info("upe link is not local, do nothing...");
			}
		} else if (CAGConstants.CAG_UPDATECONFIG_NOTIF
				.equalsIgnoreCase(notification)) {
			logger.info("handle for notice:" + notification + " by uid[" + uid
					+ "]...");
			boolean isUserConected = AsyncContextManager.contains(IMEI);
			UPEMapping mapping = UPECacheManager.getMappingObject(IMEI);
			if ((mapping == null && !isUserConected)
					|| (mapping != null && !uid.equals(mapping.getUid()))) {
				logger.info("user by uid[" + uid + "] is not log in");
				json.put(JMSConstans.CAG_RESPONSE_CODE,
						CAGConstants.CAG_CLIENT_UNREACHABLE_CODE);
				json.put(JMSConstans.CAG_RESPONSE_STATUS, "Client Unreachable");
				json.remove(JMSConstans.JMS_IMEI_KEY);
				MessageSender.sendCAGResponseMessage(json.toString());
				return;
			}

			String notice = UPEConstants.UPE_UPDATECONFIG_STATE;
			if (isUserConected
					|| (mapping.getMac() != null && mapping.getMac().equals(
							IRequestHandler.mac))) {
				AsyncContextManager.getInstance().sendCAGNotice(IMEI, uid,
						notice, notification);
			} else {
				logger.info("upe link is not local, do nothing...");
			}
		} else {
			logger.info("no handle process for CAG API");
		}

	}
}
