package com.dreamail.mercury.receiver.mail.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EDU.oswego.cs.dl.util.concurrent.Callable;
import EDU.oswego.cs.dl.util.concurrent.FutureResult;

import com.dreamail.mercury.mail.jms.ReceiveMain;
import com.dreamail.mercury.receiver.mail.threadpool.impl.HotmailMailThreadPoolImpl;

public class PutHotmailThreadPool {
	public static final Logger logger = LoggerFactory
			.getLogger(PutHotmailThreadPool.class);
			public static ReceiveMailThreadPool hotmailMailThreadPool = null;
		    static{
		    	hotmailMailThreadPool = new HotmailMailThreadPoolImpl();
		    	logger.error("hotmail pool provide....");
		    }
			
			/**
			 * 普通邮件任务
			 * @param context  上下文对象
			 */
			public static void putPool(Object msg) {
				Callable callable = null;
				if(ReceiveMain.asControl){
					callable = new ReceiveHotmailTaskCallable(msg);
				}else{
					callable = new ReceiveTaskCallable(msg);
				}
				try {
					hotmailMailThreadPool.execute(new FutureResult().setter(callable));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			public static void shutDown(){
				hotmailMailThreadPool.shutdown();
			}
}
