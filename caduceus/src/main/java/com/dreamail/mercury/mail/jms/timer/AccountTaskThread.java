package com.dreamail.mercury.mail.jms.timer;

import java.util.Timer;

public class AccountTaskThread implements Runnable{
	private static long intervalTime = 2*60*1000;
	public void run() {
		Timer timer = new Timer(false);
		timer.schedule(new AccountTimer(), 0, intervalTime);
	}

}
