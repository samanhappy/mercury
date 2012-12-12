package com.dreamail.mercury.receiver.mail.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EDU.oswego.cs.dl.util.concurrent.Callable;
import EDU.oswego.cs.dl.util.concurrent.FutureResult;

import com.dreamail.mercury.domain.Context;

/**
 * 任务调用类
 * 
 * @author haisheng.wang
 */
public class CreateThreadTask implements Callable {

	private Context context = null;

	private static final Logger logger = LoggerFactory
			.getLogger(TaskCallable.class);

	public CreateThreadTask(Context context) {
		this.context = context;
	}

	/**
	 * 装载执行任务线程
	 */
	public Object call() {
		Thread thread = null;
		TaskCallable taskCallable = null;
		FutureResult futureResult = null;
		try {
			long startMill = System.currentTimeMillis();
			context.setStartMill(startMill);
			taskCallable = new TaskCallable(context);
			futureResult = new FutureResult();
			thread = new Thread(futureResult.setter(taskCallable));
			logger.info("[start] [" + context.getLoginName() + "]["
					+ thread.getName() + "] ");
			thread.start();
			logger.info("[end] [" + context.getLoginName() + "] ["
					+ thread.getName() + "]");
		} catch (Exception e) {
			if (context != null) {
				logger.error(" [" + context.getLoginName() + "] ["
						+ thread.getName() + "]", e);
			} else {
				logger.error(" [" + null + "] [" + null + "]", e);
			}
		}
		return null;
	}
}
