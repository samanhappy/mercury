package com.dreamail.mercury.mail.jms;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.mail.truepush.impl.HotmailTruepush;
import com.dreamail.mercury.util.JMSConstans;

public class RemoveAccountMessage {
	public static final Logger logger = LoggerFactory
			.getLogger(RemoveAccountMessage.class);

	public void onMessage(Object msg) {
		logger.info("removeAccount:" + msg);
		try {
			if (msg instanceof String) {
				JSONObject removeAccount = JSONObject.fromObject(msg);
				String aid = removeAccount.getString(JMSConstans.JMS_AID_KEY);
				if (ReceiveMain.contextMap.containsKey(aid)) {
					ReceiveMain.contextMap.get(aid).setAccount(null);
					ReceiveMain.contextMap.remove(aid);
				}
				HotmailTruepush.getInstance()
						.removeAccount(Long.parseLong(aid));
			} else {
				logger.error("msg class:" + msg.getClass());
			}
		} catch (Exception e) {
			logger.error("Error receiveMSG.", e);
		}
	}
}
