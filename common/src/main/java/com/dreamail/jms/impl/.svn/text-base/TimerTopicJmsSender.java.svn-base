package com.dreamail.jms.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.IJmsSender;
import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.pojo.Clickoo_user;

public class TimerTopicJmsSender implements IJmsSender{
	private static Logger logger = LoggerFactory.getLogger(TimerTopicJmsSender.class);
	/**
	 * 用户添加、删除账号对upe的通知.
	 * @param business 
	 * @param destination 消息名称
	 * @param str[0]:uid,str[1]:aid
	 */
	@Override
	public void sendMessage(String business, String destination, String... str) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer(business);
		Clickoo_user user = new UserService().getUserById(Long.parseLong(str[0]));
		if(user!=null){
			String IMEI = user.getIMEI();
			sb.append(",");
			sb.append(IMEI);
			sb.append(",");
			sb.append(str[1]);
			logger.info("sendMsg:"+sb.toString());
			JmsSender.sendTopicMsg(sb.toString(), destination);
		}
	}

}
