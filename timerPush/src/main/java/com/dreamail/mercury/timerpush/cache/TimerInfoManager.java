package com.dreamail.mercury.timerpush.cache;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.UserLimitTimesDao;
import com.dreamail.mercury.dal.service.UARelationService;
import com.dreamail.mercury.pojo.Clickoo_user_limittimes;
import com.dreamail.mercury.timerpush.serverstate.Tools;

public class TimerInfoManager {

	private static Logger logger = LoggerFactory
			.getLogger(TimerInfoManager.class);

	public static SortedMap<Integer, String> timerMap = Collections
			.synchronizedSortedMap(new TreeMap<Integer, String>());

	public static final String ON_TYPE = "O";

	public static final String OFF_TYPE = "F";

	public static final String SPILIT = "-";

	public static final int LASTMINUTES = 23 * 60 + 59;

	/**
	 * 初始化.
	 */
	public static void init() {
		logger.info("timer info manager init start..........");
		List<Clickoo_user_limittimes> timerList = new UserLimitTimesDao()
				.getAllCommonTimerInfo();
		if (timerList != null && timerList.size() > 0) {
			Date date = new Date();
			for (Clickoo_user_limittimes timer : timerList) {
				if (timer.getWeekdays() != null
						&& timer.getWeekdays().contains(
								String.valueOf(Tools.getWeekday()))) {
					add(timer, date);
				} else {
					new UARelationService().setAccountScheduleOffline(timer
							.getUid(), timer.getAid());
					logger.info(" Uid [" + timer.getUid() + "] Aid ["
							+ timer.getAid() + "] is offline!");
				}
			}
		}
		logger.info("timerMap----------" + timerMap);
		logger.info("timer info manager init end..........");
	}

	/**
	 * 处理一条用户普通定时信息.
	 * 
	 * @param timer
	 */
	@SuppressWarnings("deprecation")
	public static void add(Clickoo_user_limittimes timer, Date date) {
		if (date.getHours() * 60 + date.getMinutes() < timer.getStarthour()
				* 60 + timer.getStartminute()) {
			// 加入timerMap并发送下线消息
			addTimer(timer.getUid(), timer.getAid(), timer.getStarthour() * 60
					+ timer.getStartminute(), ON_TYPE);
			addTimer(timer.getUid(), timer.getAid(), timer.getEndhour() * 60
					+ timer.getEndminute(), OFF_TYPE);

			if (timer.getAid() == 0) {
				new UARelationService().setUserScheduleOffline(timer.getUid());
			} else {
				new UARelationService().setAccountScheduleOffline(timer
						.getUid(), timer.getAid());
			}

			logger.info(" Uid [" + timer.getUid() + "] Aid [" + timer.getAid()
					+ "] is offline!");
		} else if (date.getHours() * 60 + date.getMinutes() > timer
				.getEndhour()
				* 60 + timer.getEndminute()) {
			if (timer.getAid() == 0) {
				// 不加入timerMap并发送下线消息
				new UARelationService().setUserScheduleOffline(timer.getUid());
			} else {
				// 不加入timerMap并发送下线消息
				new UARelationService().setAccountScheduleOffline(timer
						.getUid(), timer.getAid());
			}
			logger.info(" Uid [" + timer.getUid() + "] Aid [" + timer.getAid()
					+ "] is offline!");
		} else {
			// 将End加入timerMap并发送上线消息
			addTimer(timer.getUid(), timer.getAid(), timer.getEndhour() * 60
					+ timer.getEndminute(), OFF_TYPE);
			
			if (timer.getAid() == 0) {
				// 不加入timerMap并发送下线消息
				new UARelationService().setUserScheduleOnline(timer.getUid());
			} else {
				// 不加入timerMap并发送下线消息
				new UARelationService().setAccountScheduleOnline(timer.getUid(),
						timer.getAid());
			}
			logger.info(" Uid [" + timer.getUid() + "] Aid [" + timer.getAid()
					+ "] is online!");
		}
	}

	/**
	 * 增加一条timer info.
	 * 
	 * @param uid
	 * @param aid
	 * @param minutes
	 * @param type
	 */
	public static void addTimer(long uid, long aid, int minutes, String type) {
		String timer = timerMap.get(minutes);
		JSONObject json = null;
		if (timer != null) {
			json = JSONObject.fromObject(timer);
		} else {
			json = new JSONObject();
		}
		String info = new StringBuffer().append(uid).append(SPILIT).append(aid)
				.toString();
		Object obj = json.get(type);
		if (obj == null) {
			json.accumulate(type, info);
		} else if (obj instanceof String) {
			if (!info.equals(obj)) {
				json.accumulate(type, info);
			}
		} else if (obj instanceof JSONArray) {
			JSONArray array = (JSONArray) obj;
			if (!array.contains(info)) {
				array.add(info);
			}
			json.element(type, array.toString());
		}
		timerMap.put(minutes, json.toString());
		logger.info("timerMap after addTimer----------" + timerMap);
	}

	/**
	 * 删除一条timer info.
	 * 
	 * @param uid
	 * @param aid
	 * @param minutes
	 * @param type
	 */
	public static void removeTimer(long uid, long aid, int minutes, String type) {
		String timer = timerMap.get(minutes);
		JSONObject json = null;
		if (timer != null) {
			json = JSONObject.fromObject(timer);
			String info = new StringBuffer().append(uid).append(SPILIT).append(
					aid).toString();
			Object obj = json.get(type);
			if (obj != null) {
				if (obj instanceof String) {
					if (info.equals(obj)) {
						json.remove(type);
					}
				} else if (obj instanceof JSONArray) {
					JSONArray array = (JSONArray) obj;
					if (array.contains(info)) {
						array.remove(info);
					}
					json.element(type, array.toString());
				}
			}
		}
		if (json != null && !json.isEmpty()) {
			timerMap.put(minutes, json.toString());
		} else {
			timerMap.remove(minutes);
		}
		logger.info("timerMap after removeTimer----------" + timerMap);
	}

	/**
	 * 得到当前最早的时间点.
	 * 
	 * @return
	 */
	public static int getFirstMinutes() {
		if (timerMap.isEmpty()) {
			return LASTMINUTES;
		} else {
			return Integer.valueOf(timerMap.firstKey());
		}
	}

	/**
	 * 得到最早时间点的所有定时信息.
	 * 
	 * @return
	 */
	public static String getFirstTimerInfos() {
		if (timerMap.isEmpty()) {
			return null;
		} else {
			return timerMap.get(timerMap.firstKey());
		}
	}

	/**
	 * 删除最早时间点的项.
	 */
	public static void removeFirstItem() {
		if (!timerMap.isEmpty()) {
			timerMap.remove(timerMap.firstKey());
		}
	}
}
