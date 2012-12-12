package com.dreamail.mercury.timerpush.timer;

import java.util.Iterator;
import java.util.TimerTask;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.service.UARelationService;
import com.dreamail.mercury.timerpush.cache.TimerInfoManager;

public class ScheduleTask extends TimerTask {

	private static final Logger logger = LoggerFactory
			.getLogger(ScheduleTask.class);

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		logger.info("ScheduleTask run .........");
		String timerinfo = TimerInfoManager.getFirstTimerInfos();
		if (timerinfo != null) {
			JSONObject json = JSONObject.fromObject(timerinfo);
			String userinfo = null;
			String[] ids = null;
			JSONArray array = null;
			// 处理上线定时
			Object obj = json.get(TimerInfoManager.ON_TYPE);
			if (obj != null) {
				if (obj instanceof String) {
					userinfo = (String) obj;
					ids = userinfo.split(TimerInfoManager.SPILIT);
					handleOnSchedule(ids[0], ids[1]);
				} else {
					array = (JSONArray) obj;
					Iterator<String> it = array.iterator();
					while (it.hasNext()) {
						userinfo = it.next();
						ids = userinfo.split(TimerInfoManager.SPILIT);
						handleOnSchedule(ids[0], ids[1]);
					}
				}
			}

			// 处理下线定时
			obj = json.get(TimerInfoManager.OFF_TYPE);
			if (obj != null) {
				if (obj instanceof String) {
					userinfo = (String) obj;
					ids = userinfo.split(TimerInfoManager.SPILIT);
					handleOffSchedule(ids[0], ids[1]);
				} else {
					array = (JSONArray) obj;
					Iterator<String> it = array.iterator();
					while (it.hasNext()) {
						userinfo = it.next();
						ids = userinfo.split(TimerInfoManager.SPILIT);
						handleOffSchedule(ids[0], ids[1]);
					}
				}
			}
			// 删除最早时间点的项
			TimerInfoManager.removeFirstItem();
			// 关闭Timer
			TimerOperator.timerCancle();
			// 重新启动Timer
			TimerOperator.timerPush();
		}
	}

	/**
	 * 处理上线定时
	 * 
	 * @param uid
	 * @param aid
	 */
	private void handleOnSchedule(String uid, String aid) {
		if (Long.valueOf(aid) == 0) {
			new UARelationService().setUserScheduleOnline(Long.parseLong(uid));
		} else {
			new UARelationService().setAccountScheduleOnline(Long.parseLong(uid),
					Long.parseLong(aid));
		}
		logger.info("uid [" + uid + "] aid [" + aid + "] handleOnSchedule!");
	}

	/**
	 * 处理下线定时
	 * 
	 * @param uid
	 * @param aid
	 */
	private void handleOffSchedule(String uid, String aid) {
		new UARelationService().setAccountScheduleOffline(Long.parseLong(uid),
				Long.parseLong(aid));
		logger.info("uid [" + uid + "] aid [" + aid + "] handleOffSchedule!");
	}

}
