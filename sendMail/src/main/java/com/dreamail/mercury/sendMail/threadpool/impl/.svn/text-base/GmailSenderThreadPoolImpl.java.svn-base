package com.dreamail.mercury.sendMail.threadpool.impl;

import net.sf.json.JSONObject;
import EDU.oswego.cs.dl.util.concurrent.LinkedQueue;
import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.sendMail.threadpool.SendMailThreadPool;
import com.dreamail.mercury.util.Constant;

public class GmailSenderThreadPoolImpl implements SendMailThreadPool {
	private static PooledExecutor gmailPooledExecutor = new PooledExecutor(
			new LinkedQueue());

	static {
		JSONObject json = JSONObject.fromObject(PropertiesDeploy
				.getConfigureMap().get(Constant.GMAIL_HOST_NAME));
		gmailPooledExecutor.setKeepAliveTime(Integer.parseInt(json
				.getString(Constant.KEEP_ALIVE_TIME)));
		gmailPooledExecutor.createThreads(Integer.parseInt(json
				.getString(Constant.CREATE_SIZE)));
		gmailPooledExecutor.setMinimumPoolSize(Integer.parseInt(json
				.getString(Constant.MIN_POOL_SIZE)));
		gmailPooledExecutor.setMaximumPoolSize(Integer.parseInt(json
				.getString(Constant.MAX_POOL_SIZE)));
		gmailPooledExecutor.abortWhenBlocked();
	}

	@Override
	public void execute(Runnable r) throws InterruptedException {
		gmailPooledExecutor.execute(r);
	}

	@Override
	public int getThreadSize() {
		return gmailPooledExecutor.getPoolSize();
	}

	@Override
	public void shutdown() {
		gmailPooledExecutor.shutdownAfterProcessingCurrentlyQueuedTasks();
		gmailPooledExecutor = null;
	}

}
