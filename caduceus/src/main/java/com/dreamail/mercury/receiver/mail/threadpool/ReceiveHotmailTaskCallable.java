package com.dreamail.mercury.receiver.mail.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EDU.oswego.cs.dl.util.concurrent.Callable;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.proces.MailProces;
import com.dreamail.mercury.util.Constant;

public class ReceiveHotmailTaskCallable implements Callable {
	public static final Logger logger = LoggerFactory
	.getLogger(ReceiveHotmailTaskCallable.class);
	private Object msg;
	public ReceiveHotmailTaskCallable(Object msg) {
		this.msg = msg;
	}

	@Override
	public Object call() throws Exception {
		Context context = new ReceiveWorker().startAccountTask((String)msg,Constant.HOTMAIL_HOST_NAME);
		MailProces mp = new MailProces();
		try {
			if(context != null){
				mp.doProces(context);	
			}
		} catch (Exception e) {
			logger.error("account["+context.getLoginName()+"]",e);
		}
		return null;
	}

}