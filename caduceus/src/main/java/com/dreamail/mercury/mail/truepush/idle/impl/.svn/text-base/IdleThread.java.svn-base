package com.dreamail.mercury.mail.truepush.idle.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.mail.truepush.IdleTruepush;

public class IdleThread implements Runnable {

	public static final Logger logger = LoggerFactory
			.getLogger(IdleThread.class);
	private Context context;

	public IdleThread(Context context) {
		this.context = context;
	}

	@Override
	public void run() {
		IdleTruepush idlePush = new IdleImpl();
		idlePush.idle(context);
	}

}
