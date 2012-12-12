package com.dreamail.mercury.receiver.mail.threadpool;

/**
 * ReceiveMailThreadPoolImpl 线程池接口
 * @author haisheng.wang
 */
public interface ReceiveMailThreadPool {
	
	/**
	 * 执行一个线程任务
	 * @param Runnable  任务对象
	 */
	public void execute(final Runnable r) throws InterruptedException;

	/**
	 * 结束一个线程任务
	 */
	public void shutdown();

	/**
	 * 获取线程池线程数量
	 * @return  int 
	 */
	public int getThreadSize();
}
