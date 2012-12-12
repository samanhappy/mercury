package com.dreamail.jms.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.IJmsSender;
import com.dreamail.jms.JmsSender;

public class AccountQueueJmsSender implements IJmsSender {
	private static Logger logger = LoggerFactory.getLogger(AccountQueueJmsSender.class);
	/**
	 * Master 分配 Account.
	 * @param business 
	 * @param destination 消息名称
	 * @param str 此处传入accountId
	 */
	@Override
	public void sendMessage(String business,String destination, String... str) {
		//accountQueue,1,3
		StringBuffer messageSb = new StringBuffer(business);
		messageSb.append(",");
		messageSb.append(str[0]);
		logger.info("sendMsg:"+messageSb.toString());
		JmsSender.sendTopicMsg(messageSb.toString(), destination);
	}
}
