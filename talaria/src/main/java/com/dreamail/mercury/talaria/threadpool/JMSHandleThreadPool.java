package com.dreamail.mercury.talaria.threadpool;

import EDU.oswego.cs.dl.util.concurrent.LinkedQueue;
import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.util.Constant;

public class JMSHandleThreadPool {

	private static PooledExecutor pooledExecutor;

	public static void init() {
		pooledExecutor = new PooledExecutor(new LinkedQueue());
		pooledExecutor.setKeepAliveTime(Integer.parseInt(
				PropertiesDeploy.getConfigureMap().get(Constant.UPE_KEEP_ALIVE_TIME)));
		pooledExecutor.createThreads(Integer.parseInt(
				PropertiesDeploy.getConfigureMap().get(Constant.UPE_CREATE_SIZE)));
		pooledExecutor.setMinimumPoolSize(Integer.parseInt(
				PropertiesDeploy.getConfigureMap().get(Constant.UPE_MIN_POOL_SIZE)));
		pooledExecutor.setMaximumPoolSize(Integer.parseInt(
				PropertiesDeploy.getConfigureMap().get(Constant.UPE_MAX_POOL_SIZE)));
		pooledExecutor.abortWhenBlocked();
	}
	
	/**
	 * 执行一个线程任务
	 * @param Runnable  任务对象
	 */
	public static void execute(Runnable r) throws InterruptedException {
		pooledExecutor.execute(r);
	}

	/**
	 * 获取线程池线程数量
	 */
	public static int getThreadSize() {
		return pooledExecutor.getPoolSize();
	}

	/**
	 * 结束线程任务
	 */
	public static void shutdown() {
		pooledExecutor.shutdownAfterProcessingCurrentlyQueuedTasks();
		pooledExecutor = null;
	}
}
