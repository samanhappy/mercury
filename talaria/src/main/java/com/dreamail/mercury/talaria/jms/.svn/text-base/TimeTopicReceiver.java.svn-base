package com.dreamail.mercury.talaria.jms;
/*package com.dreamail.mercury.talaria.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.talaria.http.AsyncContextManager;
import com.dreamail.mercury.talaria.http.handler.IRequestHandler;
import com.dreamail.talaria.memcached.UPECacheManager;
import com.dreamail.talaria.memcached.UPEMapping;

public class TimeTopicReceiver {

	private Logger logger = LoggerFactory.getLogger(TimeTopicReceiver.class);

	public void onMessage(Object msg) {
		if (msg instanceof String) {
			logger.info(msg);
		}
			String msgStr = (String) msg;
			logger.info("timer message str is:" + msgStr);

			JSONObject json = null;
			try {
				json = JSONObject.fromObject(msgStr);
			} catch (Exception e) {
				logger.info("jms message is error!!!!!!");
				return;
			}

			String type = json.getString(JMSConstans.JMS_TYPE_KEY);

			if (type.equalsIgnoreCase(JMSConstans.JMS_TIMER_TYPE)) {
				// 处理定时类型的消息
				int timerType = Integer.valueOf(json
						.getString(JMSConstans.JMS_TIMER_TYPE_KEY));
				String uid = json.getString(JMSConstans.JMS_UID_KEY);

				if (timerType == UPEConstants.ACCOUNT_COMMON_TIMER) {
					// 处理普通定时器
					Clickoo_user_limittimes timer = new Clickoo_user_limittimes();
					timer.setUid(Long.parseLong(uid));
					timer.setAid(Long.parseLong(json
							.getString(JMSConstans.JMS_AID_KEY)));
					timer.setWeekdays(json
							.getString(JMSConstans.JMS_TIMER_WEEKDAYS_KEY));
					timer.setStarthour(Integer.parseInt(json
							.getString(JMSConstans.JMS_TIMER_STARTHOUR_KEY)));
					timer.setEndhour(Integer.parseInt(json
							.getString(JMSConstans.JMS_TIMER_ENDHOUR_KEY)));
					timer.setStartminute(Integer.parseInt(json
							.getString(JMSConstans.JMS_TIMER_STARTMINUTE_KEY)));
					timer.setEndminute(Integer.parseInt(json
							.getString(JMSConstans.JMS_TIMER_ENDMINUTE_KEY)));
					timer.setTimetype(timerType);
					TimerCacheManager.addTimer(timer);
					logger.info("add a common timer......");
					
					//立即发送普通响应给客户端，以让定时生效
					AsyncContextManager.getInstance().sendCommonNotice(uid);
				} else if (timerType == UPEConstants.USER_OFF_TIMER) {
					// 处理用户关闭PUSH
					TimerCacheManager.addUserOffTimer(uid);
					logger.info("add a user off timer......");
				} else if (timerType == UPEConstants.USER_ON_TIMER) {
					// 处理用户开启PUSH
					TimerCacheManager.addUserOnTimer(uid);
					// 即时发送新邮件通知
					sendNewMailNotice(uid);
					logger.info("add a user on timer......");
				} else if (timerType == UPEConstants.ACCOUNT_OFF_TIMER) {
					// 处理账号关闭PUSH
					TimerCacheManager.addAccountOffTimer(uid, json
							.getString(JMSConstans.JMS_AID_KEY));
					logger.info("add a account off timer...");
				} else if (timerType == UPEConstants.ACCOUNT_ON_TIMER) {
					// 处理账号开启PUSH
					TimerCacheManager.addAccountOnTimer(uid, json
							.getString(JMSConstans.JMS_AID_KEY));
					// 即时发送新邮件通知
					sendNewMailNotice(uid);
					logger.info("add a account on timer...");
				} else if (timerType == UPEConstants.ACCOUNT_SCHEDULE_OFF_TIMER) {
					// 去除账号Schedule Push
					TimerCacheManager.addSchedulePushOffForAccount(uid, json
							.getString(JMSConstans.JMS_AID_KEY));
					logger.info("account schedule push is off...");
				} else {
					logger.error("timer type is error");
				}
			} else if (type.equals(JMSConstans.JMS_UPDATE_USERTS_OFFSET_TYPE)) {
				// 处理用户登录和时间戳
				String uid = json.getString(JMSConstans.JMS_UID_KEY);
				long offset = Long.valueOf(json
						.getString(JMSConstans.JMS_OFFSET_KEY));
				String IMEI = json.getString(JMSConstans.JMS_IMEI_KEY);
				TimerCacheManager.updateTsOffset(uid, offset);
				logger.info("update user ts offset...");

				// 删除用户登录前的缓存
				UPECacheManager.getLock(IMEI);
				UPECacheManager.deleteCacheObject(IMEI);
				UPECacheManager.removeLock(IMEI);
			} else if (JMSConstans.JMS_REMOVE_ACCOUNT_TYPE.equals(type)) {
				String uid = json.getString(JMSConstans.JMS_UID_KEY);
				String aid = json.getString(JMSConstans.JMS_AID_KEY);
				// 删除定时信息.
				TimerCacheManager.deleteTimerByUidAndAid(uid, aid);
			} else {
				logger
						.error("not find process for this type of jms message!!!");
			}
		} else {
			logger.error("message object type is not string");
		}
	}

	*//**
	 * 发送新邮件通知.
	 * 
	 * @param uid
	 *//*
	private void sendNewMailNotice(String uid) {
		String IMEI = new UserDao().getIMEIByUid(Long.valueOf(uid));
		// 判断IMEI是否存在
		if (IMEI == null || "".equals(IMEI.trim())) {
			logger.info("there is no IMEI by uid:" + uid + " do nothing...");
			return;
		}

		UPEMapping mapping = UPECacheManager.getMappingObject(IMEI);

		// 判断缓存是否存在以及是否挂载在当前主机
		if (mapping == null || !IRequestHandler.mac.equals(mapping.getMac())) {
			logger.info("no cache exists or mac is not local, do nothing...");
			return;
		}

		// 判断uid是否一致
		if (!String.valueOf(uid).equals(mapping.getUid())) {
			logger.info("uids for IMEI are not the same, do nothing...");
			return;
		}
		// 发送新邮件通知
		AsyncContextManager.getInstance().sendNewMailNotice(IMEI,
				mapping.getUid(), null);

	}

}
*/