package com.dreamail.mercury.timer;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.dal.dao.UserLimitTimesDao;
import com.dreamail.mercury.dal.service.UARelationService;
import com.dreamail.mercury.pojo.Clickoo_user_limittimes;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.JMSTypes;
import com.dreamail.mercury.util.UPEConstants;

public class TimerManager {

	private static Logger logger = LoggerFactory.getLogger(TimerManager.class);

	/**
	 * 开启用户PUSH.
	 * 
	 * @param uid
	 */
	public static void addUserOnTimer(String uid) {
		// 操作数据库
		if (new UserLimitTimesDao().addOnTimerForUser(uid)) {
			JSONObject json = new JSONObject();
			json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMER_TYPE);
			json.put(JMSConstans.JMS_UID_KEY, uid);
			json
					.put(JMSConstans.JMS_TIMER_TYPE_KEY,
							UPEConstants.USER_ON_TIMER);
			JmsSender.sendTopicMsg(json.toString(), JMSTypes.TIMER_TOPIC);
			logger.info("addOnTimerForUser.................");

			new UARelationService().setUserPushOnline(Long.parseLong(uid));

		}
	}

	/**
	 * 关闭用户PUSH.
	 * 
	 * @param uid
	 */
	public static void addUserOffTimer(String uid) {
		if (new UserLimitTimesDao().addOffTimerForUser(uid)) {
			JSONObject json = new JSONObject();
			json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMER_TYPE);
			json.put(JMSConstans.JMS_UID_KEY, uid);
			json.put(JMSConstans.JMS_TIMER_TYPE_KEY,
					UPEConstants.USER_OFF_TIMER);
			JmsSender.sendTopicMsg(json.toString(), JMSTypes.TIMER_TOPIC);
			logger.info("addUserOffTimer.................");

			new UARelationService().setUserPushOffline(Long.parseLong(uid));
		}
	}

	/**
	 * 开启账号PUSH.
	 * 
	 * @param uid
	 * @param aid
	 */
	public static void addAccountOnTimer(String uid, String aid) {
		if (new UserLimitTimesDao().addOnTimerForAccount(uid, aid)) {
			JSONObject json = new JSONObject();
			json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMER_TYPE);
			json.put(JMSConstans.JMS_UID_KEY, uid);
			json.put(JMSConstans.JMS_AID_KEY, aid);
			json.put(JMSConstans.JMS_TIMER_TYPE_KEY,
					UPEConstants.ACCOUNT_ON_TIMER);
			JmsSender.sendTopicMsg(json.toString(), JMSTypes.TIMER_TOPIC);
			logger.info("addAccountOnTimer.................");

			List<String> aidList = new ArrayList<String>();
			aidList.add(aid);

			new UARelationService().setAccountPushOnline(Long.parseLong(uid),
					Long.parseLong(aid));
		}

	}

	/**
	 * 关闭账号PUSH.
	 * 
	 * @param uid
	 * @param aid
	 */
	public static void addAccountOffTimer(String uid, String aid) {
		if (new UserLimitTimesDao().addOffTimerForAccount(uid, aid)) {
			JSONObject json = new JSONObject();
			json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMER_TYPE);
			json.put(JMSConstans.JMS_UID_KEY, uid);
			json.put(JMSConstans.JMS_AID_KEY, aid);
			json.put(JMSConstans.JMS_TIMER_TYPE_KEY,
					UPEConstants.ACCOUNT_OFF_TIMER);
			JmsSender.sendTopicMsg(json.toString(), JMSTypes.TIMER_TOPIC);
			logger.info("addAccountOffTimer.................");

			new UARelationService().setAccountPushOffline(Long.parseLong(uid),
					Long.parseLong(aid));
		}

	}

	/**
	 * 增加一个普通定时器.
	 * 
	 * @param timer
	 */
	public static void addTimerForAccount(Clickoo_user_limittimes timer) {
		Clickoo_user_limittimes oldTimer = new UserLimitTimesDao()
				.insertUserLimittimes(timer);
		long offset = new UserDao().getUserOffsetById(timer.getUid());
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMER_TYPE);
		json.put(JMSConstans.JMS_UID_KEY, timer.getUid());
		json.put(JMSConstans.JMS_AID_KEY, timer.getAid());
		json.put(JMSConstans.JMS_OFFSET_KEY, offset);
		json.put(JMSConstans.JMS_TIMER_TYPE_KEY, timer.getTimetype());
		json.put(JMSConstans.JMS_TIMER_WEEKDAYS_KEY, timer.getWeekdays());
		json.put(JMSConstans.JMS_TIMER_STARTHOUR_KEY, timer.getStarthour());
		json.put(JMSConstans.JMS_TIMER_STARTMINUTE_KEY, timer.getStartminute());
		json.put(JMSConstans.JMS_TIMER_ENDHOUR_KEY, timer.getEndhour());
		json.put(JMSConstans.JMS_TIMER_ENDMINUTE_KEY, timer.getEndminute());
		JmsSender.sendTopicMsg(json.toString(), JMSTypes.TIMER_TOPIC);

		if (oldTimer != null) {
			json.put(JMSConstans.JMS_TIMER_OLD_WEEKDAYS_KEY, oldTimer
					.getWeekdays());
			json.put(JMSConstans.JMS_TIMER_OLD_STARTHOUR_KEY, oldTimer
					.getStarthour());
			json.put(JMSConstans.JMS_TIMER_OLD_STARTMINUTE_KEY, oldTimer
					.getStartminute());
			json.put(JMSConstans.JMS_TIMER_OLD_ENDHOUR_KEY, oldTimer
					.getEndhour());
			json.put(JMSConstans.JMS_TIMER_OLD_ENDMINUTE_KEY, oldTimer
					.getEndminute());

			json.put(JMSConstans.JMS_TIMER_OPERATE_KEY,
					JMSConstans.JMS_MODIFYLINE_VALUE);
		} else {
			json.put(JMSConstans.JMS_TIMER_OPERATE_KEY,
					JMSConstans.JMS_ADDLINE_VALUE);
		}

		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMERPUSH_TYPE);
		JmsSender.sendTopicMsg(json.toString(),
				JMSTypes.CLICKOO_TIMERPUSH_TOPIC);

		logger.info("addTimerForAccount.................");
	}

	/**
	 * 增加一个普通定时器.
	 * 
	 * @param timer
	 */
	public static void addTimerForUser(Clickoo_user_limittimes timer) {
		logger.info("add a common timer for user.................");
		Clickoo_user_limittimes oldTimer = new UserLimitTimesDao()
				.insertUserCommonLimittimes(timer);
		long offset = new UserDao().getUserOffsetById(timer.getUid());
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMER_TYPE);
		json.put(JMSConstans.JMS_UID_KEY, timer.getUid());
		json.put(JMSConstans.JMS_AID_KEY, timer.getAid());
		json.put(JMSConstans.JMS_OFFSET_KEY, offset);
		json.put(JMSConstans.JMS_TIMER_TYPE_KEY, timer.getTimetype());
		json.put(JMSConstans.JMS_TIMER_WEEKDAYS_KEY, timer.getWeekdays());
		json.put(JMSConstans.JMS_TIMER_STARTHOUR_KEY, timer.getStarthour());
		json.put(JMSConstans.JMS_TIMER_STARTMINUTE_KEY, timer.getStartminute());
		json.put(JMSConstans.JMS_TIMER_ENDHOUR_KEY, timer.getEndhour());
		json.put(JMSConstans.JMS_TIMER_ENDMINUTE_KEY, timer.getEndminute());
		if (oldTimer != null) {
			json.put(JMSConstans.JMS_TIMER_OLD_WEEKDAYS_KEY, oldTimer
					.getWeekdays());
			json.put(JMSConstans.JMS_TIMER_OLD_STARTHOUR_KEY, oldTimer
					.getStarthour());
			json.put(JMSConstans.JMS_TIMER_OLD_STARTMINUTE_KEY, oldTimer
					.getStartminute());
			json.put(JMSConstans.JMS_TIMER_OLD_ENDHOUR_KEY, oldTimer
					.getEndhour());
			json.put(JMSConstans.JMS_TIMER_OLD_ENDMINUTE_KEY, oldTimer
					.getEndminute());

			json.put(JMSConstans.JMS_TIMER_OPERATE_KEY,
					JMSConstans.JMS_MODIFYLINE_VALUE);
		} else {
			json.put(JMSConstans.JMS_TIMER_OPERATE_KEY,
					JMSConstans.JMS_ADDLINE_VALUE);
		}

		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMERPUSH_TYPE);
		JmsSender.sendTopicMsg(json.toString(),
				JMSTypes.CLICKOO_TIMERPUSH_TOPIC);
	}

	/**
	 * 关闭账号SCHEDULE PUSH.
	 * 
	 * @param timer
	 */
	public static void schedulePushOffForAccount(String uid, String aid) {

		Clickoo_user_limittimes oldTimer = new UserLimitTimesDao()
				.addSchedulePushOffForAccount(uid, aid);

		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMER_TYPE);
		json.put(JMSConstans.JMS_UID_KEY, uid);
		json.put(JMSConstans.JMS_AID_KEY, aid);

		if (oldTimer != null) {
			json.put(JMSConstans.JMS_TIMER_OLD_WEEKDAYS_KEY, oldTimer
					.getWeekdays());
			json.put(JMSConstans.JMS_TIMER_OLD_STARTHOUR_KEY, oldTimer
					.getStarthour());
			json.put(JMSConstans.JMS_TIMER_OLD_STARTMINUTE_KEY, oldTimer
					.getStartminute());
			json.put(JMSConstans.JMS_TIMER_OLD_ENDHOUR_KEY, oldTimer
					.getEndhour());
			json.put(JMSConstans.JMS_TIMER_OLD_ENDMINUTE_KEY, oldTimer
					.getEndminute());
			json.put(JMSConstans.JMS_TIMER_OPERATE_KEY,
					JMSConstans.JMS_DELETELINE_VALUE);
			json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMERPUSH_TYPE);
			JmsSender.sendTopicMsg(json.toString(),
					JMSTypes.CLICKOO_TIMERPUSH_TOPIC);
		}

		new UARelationService().setAccountScheduleOnline(Long.parseLong(uid),
				Long.parseLong(aid));
		logger.info("turn schedule push to off.................");
	}

	/**
	 * 关闭用户SCHEDULE PUSH.
	 * 
	 * @param timer
	 */
	public static void schedulePushOffForUser(String uid) {
		logger.info("turn schedule push to off.................");
		Clickoo_user_limittimes oldTimer = new UserLimitTimesDao()
				.addSchedulePushOffForUser(uid);
		long offset = new UserDao().getUserOffsetById(Long.valueOf(uid));
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMER_TYPE);
		json.put(JMSConstans.JMS_UID_KEY, uid);
		json.put(JMSConstans.JMS_AID_KEY, 0);
		json.put(JMSConstans.JMS_OFFSET_KEY, offset);
		if (oldTimer != null) {
			json.put(JMSConstans.JMS_TIMER_OLD_WEEKDAYS_KEY, oldTimer
					.getWeekdays());
			json.put(JMSConstans.JMS_TIMER_OLD_STARTHOUR_KEY, oldTimer
					.getStarthour());
			json.put(JMSConstans.JMS_TIMER_OLD_STARTMINUTE_KEY, oldTimer
					.getStartminute());
			json.put(JMSConstans.JMS_TIMER_OLD_ENDHOUR_KEY, oldTimer
					.getEndhour());
			json.put(JMSConstans.JMS_TIMER_OLD_ENDMINUTE_KEY, oldTimer
					.getEndminute());
			json.put(JMSConstans.JMS_TIMER_TYPE_KEY, oldTimer.getTimetype());
			json.put(JMSConstans.JMS_TIMER_OPERATE_KEY,
					JMSConstans.JMS_DELETELINE_VALUE);
			json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMERPUSH_TYPE);
			JmsSender.sendTopicMsg(json.toString(),
					JMSTypes.CLICKOO_TIMERPUSH_TOPIC);
		}
		new UARelationService().setUserScheduleOnline(Long.parseLong(uid));
	}

}
