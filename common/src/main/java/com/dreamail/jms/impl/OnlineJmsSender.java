package com.dreamail.jms.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.IJmsSender;
import com.dreamail.jms.JmsSender;

public class OnlineJmsSender implements IJmsSender{
	public static final Logger logger = LoggerFactory.getLogger(OnlineJmsSender.class);
	@Override
	public void sendMessage(String business, String destination, String... str) {
		logger.info("sendMsg:"+str[0]);
		JmsSender.sendTopicMsg(str[0], destination);
	}
}
