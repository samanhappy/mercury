package com.dreamail.mercury.talaria.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EDU.oswego.cs.dl.util.concurrent.FutureResult;

import com.dreamail.mercury.talaria.threadpool.JMSHandleCallable;
import com.dreamail.mercury.talaria.threadpool.JMSHandleThreadPool;

public class MessageReceiver {

	Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

	public void onMessage(Object msg) {
		try {
			JMSHandleThreadPool.execute(new FutureResult().setter(new JMSHandleCallable(msg)));
		} catch (InterruptedException e) {
			logger.error("thread pool error",e);
		}
	}

}
