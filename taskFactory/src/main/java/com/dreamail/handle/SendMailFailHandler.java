package com.dreamail.handle;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.dal.dao.SendFailedMessageDao;
import com.dreamail.mercury.pojo.Clickoo_send_failed_message;
import com.dreamail.mercury.util.JMSConstans;

public class SendMailFailHandler {
	
	private static Logger logger = LoggerFactory.getLogger(SendMailFailHandler.class);

	/**
	 * 失败邮件重试
	 */
	public static void startSenderTimer() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				List<Clickoo_send_failed_message> failedMsgList = new SendFailedMessageDao()
						.getAllSendFailedMessage();
				for (Clickoo_send_failed_message csfm : failedMsgList) {
					JSONObject json = new JSONObject();
					json.put(JMSConstans.JMS_TYPE_KEY,
							JMSConstans.JMS_SENDMAIL_TYPE);
					json.put(JMSConstans.JMS_UID_KEY,
							String.valueOf(csfm.getUid()));
					json.put(JMSConstans.JMS_MID_KEY,
							String.valueOf(csfm.getMid()));
					json.put(JMSConstans.JMS_SENDMAIL_VALUE,
							String.valueOf(csfm.getSendtype()));
					json.put(JMSConstans.JMS_OLD_MID_KEY,
							String.valueOf(csfm.getOldmid()));
					json.put(JMSConstans.JMS_HID_KEY,
							String.valueOf(csfm.getHid()));
					logger.info("sendJMSMessage------" + json.toString()
							+ "--------" + csfm.getQeuetype());
					JmsSender.sendQueueMsg(json, csfm.getQeuetype());
				}
			}
		}, 0, Long.parseLong(PropertiesDeploy.getConfigureMap().get(
				"retry_minute")) * 60 * 1000);
	}
}
