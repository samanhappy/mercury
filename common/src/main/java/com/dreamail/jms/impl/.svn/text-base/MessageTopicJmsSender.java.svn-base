package com.dreamail.jms.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.IJmsSender;
import com.dreamail.jms.JmsSender;

public class MessageTopicJmsSender implements IJmsSender {
	public static final Logger logger = LoggerFactory.getLogger(MessageTopicJmsSender.class);
	/**
	 * 新邮件通知.
	 * @param business 
	 * @param destination 消息名称
	 * @param str 此处传入accountId
	 */
	@Override
	public void sendMessage(String business,String destination, String... str) {
		// TODO Auto-generated method stub
		StringBuffer messageSb = new StringBuffer(business);
		messageSb.append(",");
		messageSb.append(str[0]);
		logger.info("send message:"+messageSb.toString());
		JmsSender.sendTopicMsg(messageSb.toString(), destination);
	}
}
