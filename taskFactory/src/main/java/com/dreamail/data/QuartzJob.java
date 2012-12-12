package com.dreamail.data;

public class QuartzJob {
	/**
	 * 定时任务删除垃圾数据
	 */
	public void work() {
		// 删除email数据
		Thread mailThread = new Thread(new CleanDeleteMessages());
		mailThread.start();
	}
}
