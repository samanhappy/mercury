package com.dreamail.mercury.sendMail.threadpool.impl;

import net.sf.json.JSONObject;
import EDU.oswego.cs.dl.util.concurrent.LinkedQueue;
import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.sendMail.threadpool.SendMailThreadPool;
import com.dreamail.mercury.util.Constant;

public class OthersSenderThreadPoolImpl implements SendMailThreadPool {
	private static PooledExecutor othersPooledExecutor = new PooledExecutor(
			new LinkedQueue());

	static {
		JSONObject json = JSONObject.fromObject(PropertiesDeploy
				.getConfigureMap().get(Constant.OTHERS_HOST_NAME));
		othersPooledExecutor.setKeepAliveTime(Integer.parseInt(json
				.getString(Constant.KEEP_ALIVE_TIME)));
		othersPooledExecutor.createThreads(Integer.parseInt(json
				.getString(Constant.CREATE_SIZE)));
		othersPooledExecutor.setMinimumPoolSize(Integer.parseInt(json
				.getString(Constant.MIN_POOL_SIZE)));
		othersPooledExecutor.setMaximumPoolSize(Integer.parseInt(json
				.getString(Constant.MAX_POOL_SIZE)));
		othersPooledExecutor.abortWhenBlocked();
	}

	@Override
	public void execute(Runnable r) throws InterruptedException {
		othersPooledExecutor.execute(r);
	}

	@Override
	public int getThreadSize() {
		return othersPooledExecutor.getPoolSize();
	}

	@Override
	public void shutdown() {
		othersPooledExecutor.shutdownAfterProcessingCurrentlyQueuedTasks();
		othersPooledExecutor = null;
	}

}
