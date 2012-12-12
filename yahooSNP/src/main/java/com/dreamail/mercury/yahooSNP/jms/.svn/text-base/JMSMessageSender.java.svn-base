package com.dreamail.mercury.yahooSNP.jms;

import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.JMSTypes;

public class JMSMessageSender {

	private static Logger logger = LoggerFactory
			.getLogger(JMSMessageSender.class);

	// 发送消息到C工程
	public static void sendNewMailMessage(long aid, String uuid) {

		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_YAHOOSNP_TYPE);
		json.put(JMSConstans.JMS_AID_KEY, String.valueOf(aid));
		json.put(JMSConstans.JMS_UUID_KEY, uuid);

		JmsSender.sendQueueMsg(json.toString(), JMSTypes.YAHOO_QUEUE);
	}

	// 发送消息到C工程
	public static void sendNewMailMessages(long aid, long beginuuid,
			long enduuid) {
		if (beginuuid >= enduuid) {
			logger.info("no uuid to send, do nothing");
		}
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_YAHOOSNP_TYPE);
		json.put(JMSConstans.JMS_AID_KEY, String.valueOf(aid));
		StringBuffer sb = new StringBuffer();
		for (long i = beginuuid; i < enduuid; i++) {
			sb.append(i).append(",");
		}
		json.put(JMSConstans.JMS_UUID_KEY, sb.substring(0, sb.length() - 1));
		JmsSender.sendQueueMsg(json.toString(), JMSTypes.YAHOO_QUEUE);
	}

	// 发送消息到C工程
	public static void sendNewMailMessage(long aid, List<String> uuids) {
		if (uuids != null && uuids.size() > 0) {
			JSONObject json = new JSONObject();
			json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_YAHOOSNP_TYPE);
			json.put(JMSConstans.JMS_AID_KEY, String.valueOf(aid));
			StringBuffer sb = new StringBuffer();
			for (String uuid : uuids) {
				sb.append(uuid).append(",");
			}
			json.put(JMSConstans.JMS_UUID_KEY, sb.substring(0, sb.length() - 1));
			JmsSender.sendQueueMsg(json.toString(), JMSTypes.YAHOO_QUEUE);
		}
	}

	// 发送消息到IMAP-IDLE工程
	public static void sendDeleteMailMessage(long aid, String uuid) {
		/*
		 * JSONObject json = new JSONObject();
		 * json.put(JMSConstans.JMS_TYPE_KEY, "");
		 * json.put(JMSConstans.JMS_AID_KEY, String.valueOf(aid));
		 * json.put(JMSConstans.JMS_UUID_KEY, uuid);
		 * 
		 * JmsSender.sendQueueMsg(json.toString(), "");
		 */
	}
}
