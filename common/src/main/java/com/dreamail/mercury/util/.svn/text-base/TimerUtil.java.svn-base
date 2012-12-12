/*package com.dreamail.mercury.util;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.pojo.Clickoo_user_limittimes;

public class TimerUtil {

	private static Logger logger = LoggerFactory.getLogger(TimerUtil.class);

	*//**
	 * 根据一个timer list判断是否在定时范围之内.
	 * 
	 * @param date
	 * @param timerList
	 * @param offset
	 * @return
	 *//*
	public static boolean judge(List<Clickoo_user_limittimes> timerList,
			long offset) {

		// 如果不存在任何的定时设置，直接返回true
		if (timerList == null || timerList.size() == 0) {
			return true;
		}

		Calendar cal = Calendar.getInstance();
		// 加上偏移量
		cal.setTimeInMillis(System.currentTimeMillis() + offset);
		// 得到星期几
		int weekday = (cal.get(Calendar.DAY_OF_WEEK) + 6) % 7;

		// 默认返回true
		boolean flag = true;

		for (Clickoo_user_limittimes timer : timerList) {

			if (timer.getTimetype() == UPEConstants.USER_OFF_TIMER) {
				// 如果用户PUSHOFF设置，直接返回false
				logger.info("user push is off, return false");
				return false;
			} else if (timer.getTimetype() == UPEConstants.ACCOUNT_OFF_TIMER) {
				// 如果有账号PUSHOFF设置，直接返回false
				logger.info("account push is off, return false");
				return false;
			} else if (timer.getTimetype() == UPEConstants.ACCOUNT_COMMON_TIMER) {
				// 原则上要求同一天不可以有多个定时设置
				if (timer.getWeekdays().contains(String.valueOf(weekday))) {
					flag = judgeTime(cal, timer);
				} else {
					// 如果没有找到当天的定时设置，返回false，不推送
					flag = false;
				}
			}
		}

		return flag;
	}

	*//**
	 * 根据一个普通定时设置判断是否在定时范围之内.
	 * 
	 * @param date
	 * @param timer
	 * @return
	 *//*
	private static boolean judgeTime(Calendar date,
			Clickoo_user_limittimes timer) {
		int hour = date.get(Calendar.HOUR_OF_DAY);
		int minute = date.get(Calendar.MINUTE);
		if (timer.getStarthour() == null || timer.getEndhour() == null
				|| timer.getStartminute() == null
				|| timer.getEndminute() == null) {
			logger.error("timer's setting is not complete...");
			return false;
		} else {

			boolean afterStartTime = timer.getStarthour() < hour
					|| (timer.getStarthour() == hour && timer.getStartminute() <= minute);
			boolean beforeEndTime = hour < timer.getEndhour()
					|| (timer.getEndhour() == hour && timer.getEndminute() > minute);

			return afterStartTime && beforeEndTime;
		}
	}

	*//**
	 * 判断用户是否关闭push.
	 *//*
	public static boolean isUserPushOff(List<Clickoo_user_limittimes> timerList) {
		if (timerList != null && timerList.size() > 0) {
			for (Clickoo_user_limittimes timer : timerList) {
				if (timer.getTimetype() == UPEConstants.USER_OFF_TIMER) {
					return true;
				}
			}
		}
		return false;
	}

}
*/