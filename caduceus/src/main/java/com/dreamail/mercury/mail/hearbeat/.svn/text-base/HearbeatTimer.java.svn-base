package com.dreamail.mercury.mail.hearbeat;

import java.util.Timer;

public class HearbeatTimer implements Runnable{
	private static long intervalTime = 2000;
	public void run() {
		Timer timer = new Timer(false);
		timer.schedule(new HearbeatTimerTask(), 0, intervalTime);
	}

}
