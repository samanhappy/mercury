package com.dreamail.mercury.timerpush.timer;

import java.util.Date;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.service.UARelationService;
import com.dreamail.mercury.pojo.Clickoo_user_limittimes;
import com.dreamail.mercury.timerpush.cache.TimerInfoManager;
import com.dreamail.mercury.timerpush.serverstate.Tools;

public class TimerOperator {
	private static final Logger logger = LoggerFactory
			.getLogger(TimerOperator.class);
	public static Timer timer;

	public static synchronized void timerCancle() {
		if (timer != null) {
			timer.cancel();
		}
	}

	public static synchronized void timerRun(long period) {
		ScheduleTask scheduleTask = new ScheduleTask();
		logger.info(" Next timer is " + period + " seconds later!");
		timer = new Timer();
		timer.schedule(scheduleTask, period * 1000);
	}

	public static void timerPush() {
		logger.info("New timer start!");
		if (Tools.getNowMinutes() == TimerInfoManager.LASTMINUTES) {
			try {
				Thread.sleep(61 * 1000);
			} catch (InterruptedException e) {
				logger.error("timerPush", e);
			}
			TimerInfoManager.init();
			timerPush();
		}
		if (TimerInfoManager.getFirstMinutes() != TimerInfoManager.LASTMINUTES
				&& TimerInfoManager.getFirstMinutes() * 60 < Tools
						.getNowSeconds()) {
			timerRun(0);
		} else {
			timerRun(TimerInfoManager.getFirstMinutes() * 60
					- Tools.getNowSeconds());
		}
	}

	/**
	 * 对定时队列内容进行修改
	 * 
	 * @param userLimittimes
	 */
	public static void timerPushUpdate(Clickoo_user_limittimes limittimes,
			Clickoo_user_limittimes oldlimittimes, int offset) {
		// 每次Timer队列发生修改时候，Timer都取消
		timerCancle();

		if (oldlimittimes != null
				&& oldlimittimes.getWeekdays().contains(
						String.valueOf(Tools.getWeekday()))) {
			TimerInfoManager.removeTimer(oldlimittimes.getUid(), oldlimittimes
					.getAid(), oldlimittimes.getStarthour() * 60
					+ oldlimittimes.getStartminute() + offset,
					TimerInfoManager.ON_TYPE);
			TimerInfoManager.removeTimer(oldlimittimes.getUid(), oldlimittimes
					.getAid(), oldlimittimes.getEndhour() * 60
					+ oldlimittimes.getEndminute() + offset,
					TimerInfoManager.OFF_TYPE);
		}

		if (limittimes != null) {
			if (limittimes.getWeekdays().contains(
					String.valueOf(Tools.getWeekday()))) {
				TimerInfoManager.add(limittimes, new Date());
			} else {
				if (limittimes.getAid() == 0) {
					new UARelationService().setUserScheduleOffline(limittimes
							.getUid());
				} else {
					new UARelationService().setAccountScheduleOffline(limittimes
							.getUid(), limittimes.getAid());
				}
				logger.info(" Uid [" + limittimes.getUid() + "] Aid ["
						+ limittimes.getAid() + "] is offline!");
			}
		}

		timerPush();
	}
}
