package com.dreamail.mercury.receiver.mail.threadpool.largemail;

import EDU.oswego.cs.dl.util.concurrent.LinkedQueue;
import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.receiver.mail.threadpool.ReceiveMailThreadPool;
import com.dreamail.mercury.util.Constant;

/**
 * BigAttachThreadPoolImpl 线程池配置
 * @author haisheng.wang
 */
public class LargeMailThreadPoolImpl implements ReceiveMailThreadPool{
	
	private static PooledExecutor pooledExecutor = new PooledExecutor(new LinkedQueue());

	static {
		pooledExecutor.setKeepAliveTime(Integer.parseInt(
				PropertiesDeploy.getConfigureMap().get(Constant.KEEP_BA_ALIVE_TIME)));
		pooledExecutor.createThreads(Integer.parseInt(
				PropertiesDeploy.getConfigureMap().get(Constant.CREATE_BA_SIZE)));
		pooledExecutor.setMinimumPoolSize(Integer.parseInt(
				PropertiesDeploy.getConfigureMap().get(Constant.MIN_BA_POOL_SIZE)));
		pooledExecutor.setMaximumPoolSize(Integer.parseInt(
				PropertiesDeploy.getConfigureMap().get(Constant.MAX_BA_POOL_SIZE)));
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
