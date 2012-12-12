package com.dreamail.mercury.mail.truepush.threadpool;

import EDU.oswego.cs.dl.util.concurrent.Callable;
import EDU.oswego.cs.dl.util.concurrent.FutureResult;

import com.dreamail.mercury.receiver.mail.threadpool.PutHotmailThreadPool;

public class PutActiveSyncThreadPool extends PutHotmailThreadPool{
	
	/**
	 * 向线程池发放任务.
	 * @param aid
	 */
	public static void putAcpool(String aid) {
		Callable callable = new ReceiveActiveSyncTaskCallable(aid);
		try {
			hotmailMailThreadPool.execute(new FutureResult().setter(callable));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
