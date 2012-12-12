package com.dreamail.mercury.receiver.mail.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EDU.oswego.cs.dl.util.concurrent.Callable;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.proces.MailProces;

/**
 * 任务调用类
 * @author haisheng.wang
 */
public class TaskCallable implements Callable {

	private Context context = null;

	private static final Logger logger = LoggerFactory
	        .getLogger(TaskCallable.class);

	/**
	 * 邮件收取任务
	 * @param context  上下文对象
	 */
	public TaskCallable(Context context) {
		this.context = context;
	}


	/**
	 * 收取邮件
	 */
	public Object call() {
		logger.info("----------------"+context.getLoginName());
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
