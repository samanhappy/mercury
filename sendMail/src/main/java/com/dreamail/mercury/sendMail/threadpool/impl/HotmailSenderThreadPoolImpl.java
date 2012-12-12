package com.dreamail.mercury.sendMail.threadpool.impl;

import net.sf.json.JSONObject;
import EDU.oswego.cs.dl.util.concurrent.LinkedQueue;
import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.sendMail.threadpool.SendMailThreadPool;
import com.dreamail.mercury.util.Constant;

public class HotmailSenderThreadPoolImpl implements SendMailThreadPool {
	private static PooledExecutor hotmailPooledExecutor = new PooledExecutor(
			new LinkedQueue());

	static {
		JSONObject json = JSONObject.fromObject(PropertiesDeploy
				.getConfigureMap().get(Constant.HOTMAIL_HOST_NAME));
		hotmailPooledExecutor.setKeepAliveTime(Integer.parseInt(json
				.getString(Constant.KEEP_ALIVE_TIME)));
		hotmailPooledExecutor.createThreads(Integer.parseInt(json
				.getString(Constant.CREATE_SIZE)));
		hotmailPooledExecutor.setMinimumPoolSize(Integer.parseInt(json
				.getString(Constant.MIN_POOL_SIZE)));
		hotmailPooledExecutor.setMaximumPoolSize(Integer.parseInt(json
				.getString(Constant.MAX_POOL_SIZE)));
		hotmailPooledExecutor.abortWhenBlocked();
	}

	@Override
	public void execute(Runnable r) throws InterruptedException {
		hotmailPooledExecutor.execute(r);
	}

	@Override
	public int getThreadSize() {
		return hotmailPooledExecutor.getPoolSize();
	}

	@Override
	public void shutdown() {
		hotmailPooledExecutor.shutdownAfterProcessingCurrentlyQueuedTasks();
		hotmailPooledExecutor = null;
	}

}
