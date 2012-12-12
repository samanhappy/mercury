package com.clickoo.clickooImap.threadpool.impl;

import com.clickoo.clickooImap.domain.IdleMessage;
import com.clickoo.clickooImap.jms.IdleMessageSender;
import com.clickoo.clickooImap.utils.CIConstants;

public class ConnectController {
	/**
	 * 新增Idle连接
	 * @param idleMessage
	 */
	public static void addIdleConnect(IdleMessage idleMessage){
		new Thread(new ConnectCallable(idleMessage)).start();
		//每次创建新idle加入线程池前通知所有服务器更新本地缓存账户信息
		idleMessage.setMsg(CIConstants.NoticeType.CI_SERVER_ADD_ACCOUNT);
		IdleMessageSender.processAccount(idleMessage);
	}
	/**
	 * 移除Idle连接
	 * @param idleMessage
	 */
	public static void removeIdleConnect(IdleMessage idleMessage){
		
	}
}
