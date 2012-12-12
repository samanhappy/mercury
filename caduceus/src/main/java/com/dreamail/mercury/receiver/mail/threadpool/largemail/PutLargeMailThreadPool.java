package com.dreamail.mercury.receiver.mail.threadpool.largemail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EDU.oswego.cs.dl.util.concurrent.FutureResult;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.receiver.mail.threadpool.ReceiveMailThreadPool;
import com.dreamail.mercury.receiver.mail.threadpool.TaskCallable;

/**
 * PutBigAttachThreadPool 装载大邮件任务
 * @author haisheng.wang
 */
public class PutLargeMailThreadPool {
	public static ReceiveMailThreadPool receiveMailThreadPool = null;
    static{
    	receiveMailThreadPool = new LargeMailThreadPoolImpl();
    }
	private static final Logger logger = LoggerFactory.getLogger(PutLargeMailThreadPool.class);
	
	/**
	 * 装载邮件任务
	 * @param context  上下文对象
	 */
	public static void putPool(Context context) {
		TaskCallable callable = null;
		context.setState(true);//设置大邮件标记戳
		try {
			logger.info("larger Message start......");
			callable = new TaskCallable(context);
			receiveMailThreadPool.execute(new FutureResult().setter(callable));
			logger.info("[put threadpool] [" + context.getLoginName() + "] [" + receiveMailThreadPool.getThreadSize() + "]");
		} catch (Exception e) {
			logger.error("addThreadPool/PutBigAttachThreadPool/Exception: [" + context.getLoginName() + "]", e);
		}
	}
	public static void shutDown(){
		receiveMailThreadPool.shutdown();
	}
}
