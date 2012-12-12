package com.dreamail.mercury.sendMail.sender;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.dreamail.mercury.util.JMSConstans;

public class TestJMS {

	@Test
	public void testSendJmsMessage() {
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_SENDMAIL_TYPE);
		json.put(JMSConstans.JMS_UID_KEY, "1");
		json.put(JMSConstans.JMS_MID_KEY, "1");
		json.put(JMSConstans.JMS_SENDMAIL_STATE_KEY,
				JMSConstans.JMS_SENDMAIL_SUCCESS_VALUE);
		// json.put(JMSConstans.JMS_SENDMAIL_STATE_KEY,
		// JMSConstans.JMS_SENDMAIL_FAIL_VALUE);
		String message = json.toString();
		System.out.println(message);

		// new JmsSender().sendTopicMsg(message, JMSConstans.TOPIC_OF_MESSAGE,
		// "topicUrl");
	}

	@Test
	public void test() {
		String s = "afdasf?as?df";
		String[] args = s.split("[?]");
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}

	}
}
