package com.dreamail.mercury.receiver.mail.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EDU.oswego.cs.dl.util.concurrent.Callable;
import EDU.oswego.cs.dl.util.concurrent.FutureResult;

import com.dreamail.mercury.mail.jms.ReceiveMain;
import com.dreamail.mercury.receiver.mail.threadpool.impl.YahooMailThreadPoolImpl;

public class PutYahooThreadPool {
	public static final Logger logger = LoggerFactory
	.getLogger(PutYahooThreadPool.class);
	public static ReceiveMailThreadPool yahooMailThreadPool = null;
    static{
    	yahooMailThreadPool = new YahooMailThreadPoolImpl();
    	logger.error("yahoo pool provide....");
    }
	
	/**
	 * 普通邮件任务
	 * @param context  上下文对象
	 */
	public static void putPool(Object msg) {
		Callable callable = null;
		if(ReceiveMain.snpControl){
			callable = new ReceiveYahooTaskCallable(msg);
		}else{
			callable = new ReceiveTaskCallable(msg);
		}
		try {
			yahooMailThreadPool.execute(new FutureResult().setter(callable));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void shutDown(){
		yahooMailThreadPool.shutdown();
	}
}
