package com.dreamail.jms.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.IJmsSender;
import com.dreamail.jms.JmsSender;

public class CacheAccountJmsSender implements IJmsSender{
	private static Logger logger = LoggerFactory.getLogger(CacheAccountJmsSender.class);
	/**
	 * 用户增加、删除账号对tashfactory的通知.
	 * @param business 
	 * @param destination 消息名称
	 * @param str[0]:aid
	 */
	@Override
	public void sendMessage(String business, String destination, String... str) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer(business);
		sb.append(",");
		sb.append(str[0]);
		logger.info("sendMsg:"+sb.toString());
		JmsSender.sendTopicMsg(sb.toString(), destination);
	}

}
