package com.dreamail.mercury.sendMail.threadpool.impl;

import net.sf.json.JSONObject;
import EDU.oswego.cs.dl.util.concurrent.LinkedQueue;
import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.sendMail.threadpool.SendMailThreadPool;
import com.dreamail.mercury.util.Constant;

public class YahooSenderThreadPoolImpl implements SendMailThreadPool {
	private static PooledExecutor yahooPooledExecutor = new PooledExecutor(
			new LinkedQueue());

	static {
		JSONObject json = JSONObject.fromObject(PropertiesDeploy
				.getConfigureMap().get(Constant.YAHOO_HOST_NAME));
		yahooPooledExecutor.setKeepAliveTime(Integer.parseInt(json
				.getString(Constant.KEEP_ALIVE_TIME)));
		yahooPooledExecutor.createThreads(Integer.parseInt(json
				.getString(Constant.CREATE_SIZE)));
		yahooPooledExecutor.setMinimumPoolSize(Integer.parseInt(json
				.getString(Constant.MIN_POOL_SIZE)));
		yahooPooledExecutor.setMaximumPoolSize(Integer.parseInt(json
				.getString(Constant.MAX_POOL_SIZE)));
		yahooPooledExecutor.abortWhenBlocked();
	}

	@Override
	public void execute(Runnable r) throws InterruptedException {
		yahooPooledExecutor.execute(r);
	}

	@Override
	public int getThreadSize() {
		return yahooPooledExecutor.getPoolSize();
	}

	@Override
	public void shutdown() {
		yahooPooledExecutor.shutdownAfterProcessingCurrentlyQueuedTasks();
		yahooPooledExecutor = null;
	}

}
