package com.dreamail.handle;

import java.util.Timer;

import com.dreamail.config.TaskProperties;

public class UserOfflineHandlerTimer {
	private static long intervalTime = Long.valueOf(TaskProperties
			.getConfigureMap().get("process_interval"));

	public void start() {
		Timer timer = new Timer(false);
		timer.schedule(new UserOfflineHandlerTimerTask(), 0, intervalTime);
	}
}
