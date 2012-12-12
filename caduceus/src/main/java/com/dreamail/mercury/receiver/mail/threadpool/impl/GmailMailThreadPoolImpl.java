package com.dreamail.mercury.receiver.mail.threadpool.impl;

import net.sf.json.JSONObject;
import EDU.oswego.cs.dl.util.concurrent.LinkedQueue;
import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.receiver.mail.threadpool.ReceiveMailThreadPool;
import com.dreamail.mercury.util.Constant;

public class GmailMailThreadPoolImpl implements ReceiveMailThreadPool {
	private static PooledExecutor pooledExecutor = new PooledExecutor(new LinkedQueue());

	static {
		JSONObject json = JSONObject.fromObject(PropertiesDeploy.getConfigureMap().get(Constant.GMAIL_HOST_NAME));
		pooledExecutor.setKeepAliveTime(Integer.parseInt(json.getString(Constant.KEEP_ALIVE_TIME)));
		pooledExecutor.createThreads(Integer.parseInt(json.getString(Constant.CREATE_SIZE)));
		pooledExecutor.setMinimumPoolSize(Integer.parseInt(json.getString(Constant.MIN_POOL_SIZE)));
		pooledExecutor.setMaximumPoolSize(Integer.parseInt(json.getString(Constant.MAX_POOL_SIZE)));
		pooledExecutor.abortWhenBlocked();
	}
	
	/**
	 * 执行一个线程任务
	 * @param Runnable  任务对象
	 */
	public void execute(Runnable r) throws InterruptedException {
		// TODO Auto-generated method stub
		pooledExecutor.execute(r);
	}

	/**
	 * 获取线程池线程数量
	 */
	public int getThreadSize() {
		// TODO Auto-generated method stub
		return pooledExecutor.getPoolSize();
	}

	/**
	 * 结束线程任务
	 */
	public void shutdown() {
		// TODO Auto-generated method stub
		pooledExecutor.shutdownAfterProcessingCurrentlyQueuedTasks();
		pooledExecutor = null;
	}

}
