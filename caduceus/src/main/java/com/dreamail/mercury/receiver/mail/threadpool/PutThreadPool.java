package com.dreamail.mercury.receiver.mail.threadpool;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EDU.oswego.cs.dl.util.concurrent.FutureResult;

import com.dreamail.mercury.receiver.mail.threadpool.impl.GmailMailThreadPoolImpl;
import com.dreamail.mercury.receiver.mail.threadpool.impl.HotmailMailThreadPoolImpl;
import com.dreamail.mercury.receiver.mail.threadpool.impl.OthersMailThreadPoolImpl;
import com.dreamail.mercury.receiver.mail.threadpool.impl.YahooMailThreadPoolImpl;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JMSConstans;

/**
 * PutThreadPool 装载邮件任务
 * @author haisheng.wang
 */
public class PutThreadPool {
	public static final Logger logger = LoggerFactory
	.getLogger(PutThreadPool.class);
	public static ReceiveMailThreadPool gmailMailThreadPool = null;
	public static ReceiveMailThreadPool hotmailMailThreadPool = null;
	public static ReceiveMailThreadPool othersMailThreadPool = null;
	public static ReceiveMailThreadPool yahooMailThreadPool = null;
    static{
    	gmailMailThreadPool = new GmailMailThreadPoolImpl();
    	logger.error("gmail pool provide....");
    	hotmailMailThreadPool = new HotmailMailThreadPoolImpl();
    	logger.error("hotmail pool provide....");
    	othersMailThreadPool = new OthersMailThreadPoolImpl();
    	logger.error("others pool provide....");
    	yahooMailThreadPool = new YahooMailThreadPoolImpl();
    	logger.error("yahoo pool provide....");
    }
	
	/**
	 * 普通邮件任务
	 * @param context  上下文对象
	 */
	public static void putPool(Object msg) {
		logger.info("put pool:"+msg);
		ReceiveTaskCallable callable = new ReceiveTaskCallable(msg);
		JSONObject accountJSON = JSONObject.fromObject(msg);
		int type = Integer.parseInt(accountJSON.getString(JMSConstans.JMS_ACCOUNT_TYPE_KEY));
		try {
		if(type == Constant.ACCOUNT_COMMON_TYPE)
			othersMailThreadPool.execute(new FutureResult().setter(callable));
		else if(type == Constant.ACCOUNT_GMAIL_TYPE)
			gmailMailThreadPool.execute(new FutureResult().setter(callable));
		else if(type == Constant.ACCOUNT_HOTMAIL_TYPE)
			hotmailMailThreadPool.execute(new FutureResult().setter(callable));
		else if(type == Constant.ACCOUNT_YAHOOSNP_TYPE)
			yahooMailThreadPool.execute(new FutureResult().setter(callable));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void shutDown(){
		gmailMailThreadPool.shutdown();
		hotmailMailThreadPool.shutdown();
		othersMailThreadPool.shutdown();
		yahooMailThreadPool.shutdown();
	}
}
