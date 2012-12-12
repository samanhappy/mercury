package com.dreamail.jms.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.IJmsSender;
import com.dreamail.jms.JmsSender;

public class TaskFactoryTopicJmsSender implements IJmsSender {
	public static final Logger logger = LoggerFactory.getLogger(TaskFactoryTopicJmsSender.class);
	/**
	 * 任务工厂内部消息通知
	 * 	通知一：通知所有服务器停止邮件收取
	 * 	通知二：通知所有服务器谁是master服务器
	 * 	通知三：询问所有服务器谁是master
	 * @param business 业务标识
	 * @param destination 消息名称
	 * @param str 具体业务参数
	 */
	@Override
	public void sendMessage(String business,String destination, String... str) {
		// TODO Auto-generated method stub
		//findMaster:findMaster,mac
		StringBuffer messageSb = new StringBuffer(business);
		messageSb.append(":");
		messageSb.append(str[0]);
		logger.info("sendMsg:"+messageSb.toString());
		JmsSender.sendTopicMsg(messageSb.toString(), destination);
	}
}
