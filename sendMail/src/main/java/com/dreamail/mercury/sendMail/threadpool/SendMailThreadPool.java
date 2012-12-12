package com.dreamail.mercury.sendMail.threadpool;

public interface SendMailThreadPool {
	/**
	 * 执行一个线程任务
	 * 
	 * @param Runnable
	 *            任务对象
	 */
	public void execute(final Runnable r) throws InterruptedException;

	/**
	 * 结束一个线程任务
	 */
	public void shutdown();

	/**
	 * 获取线程池线程数量
	 * 
	 * @return int
	 */
	public int getThreadSize();
}
