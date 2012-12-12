package com.clickoo.clickooImap.threadpool.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.clickoo.clickooImap.domain.IdleMessage;
import com.clickoo.clickooImap.server.idle.ImapIdleConnect;

/**
 * imap idle 连接调用类
 * @author 001211
 */    
public class ConnectCallable implements Runnable{
	
	private static final Logger logger = LoggerFactory.getLogger(ConnectCallable.class);
	
	private IdleMessage idleMessage = null;
	
	public ConnectCallable(IdleMessage idleMessage){
		this.idleMessage = idleMessage;
	}

	@Override
	public void run() {
		try {
			if(idleMessage != null){
				ImapIdleConnect.doIdleConnect(idleMessage);
			}
		} catch (Exception e) {
			logger.error("account["+idleMessage.getAccountName()+"]",e);
		}
	}

}
