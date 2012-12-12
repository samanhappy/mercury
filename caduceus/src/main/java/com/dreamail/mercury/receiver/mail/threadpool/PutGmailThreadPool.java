package com.dreamail.mercury.receiver.mail.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EDU.oswego.cs.dl.util.concurrent.Callable;
import EDU.oswego.cs.dl.util.concurrent.FutureResult;

import com.dreamail.mercury.mail.jms.ReceiveMain;
import com.dreamail.mercury.receiver.mail.threadpool.impl.GmailMailThreadPoolImpl;

public class PutGmailThreadPool {
	public static final Logger logger = LoggerFactory
			.getLogger(PutHotmailThreadPool.class);
			public static ReceiveMailThreadPool gmailMailThreadPool = null;
		    static{
		    	gmailMailThreadPool = new GmailMailThreadPoolImpl();
		    	logger.error("gmail pool provide....");
		    }
			
			/**
			 * 普通邮件任务
			 * @param context  上下文对象
			 */
			public static void putPool(Object msg) {
				Callable callable = null;
				if(ReceiveMain.idleControl){
					callable = new ReceiveGmailTaskCallable(msg);
				}else{
					callable = new ReceiveTaskCallable(msg);
				}
				try {
					gmailMailThreadPool.execute(new FutureResult().setter(callable));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			public static void shutDown(){
				gmailMailThreadPool.shutdown();
			}
}
