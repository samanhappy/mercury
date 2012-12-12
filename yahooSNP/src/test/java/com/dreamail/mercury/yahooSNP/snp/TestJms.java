package com.dreamail.mercury.yahooSNP.snp;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.dreamail.mercury.util.JMSConstans;

public class TestJms {
	@Test
	public void deal() {

		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_YAHOOSNP_TYPE);
		json.put(JMSConstans.JMS_AID_KEY, "1");
		json.put("uname", "ss_102698@yahoo.com");
		json.put("psw", "123456");
		
//		new com.dreamail.mercury.yahooSNP.snp.JmsSender().sendTopicMsg(json.toString(),
//				"yahooTopic", "mq://192.168.20.201:7676");
		// new JmsSender().sendTopicMsg(json, "yahooTopic",
		// "mq://192.168.20.201:7676");
		// JmsSender.sendTopicMsg(json, "yahooTopic");
		System.out.println(json.toString());
	}

}
