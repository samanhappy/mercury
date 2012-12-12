/*package com.dreamail.talaria.memcached;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.dal.dao.UserLimitTimesDao;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.pojo.Clickoo_user_limittimes;
import com.dreamail.mercury.util.UPEConstants;

public class TimerCacheManager {

	private static Logger logger = LoggerFactory
			.getLogger(TimerCacheManager.class);

	private static ConcurrentHashMap<String, CopyOnWriteArrayList<Clickoo_user_limittimes>> timerCache = new ConcurrentHashMap<String, CopyOnWriteArrayList<Clickoo_user_limittimes>>();

	private static ConcurrentHashMap<String, Long> offsetCache = new ConcurrentHashMap<String, Long>();

	*//**
	 * 初始化.
	 *//*
	public static void init() {
		logger.info("timer cache init......");
		UserLimitTimesDao dao = new UserLimitTimesDao();
		List<Clickoo_user_limittimes> timerList = dao.getAllTimerInfo();
		if (timerList != null && timerList.size() > 0) {
			for (Clickoo_user_limittimes timer : timerList) {
				String key = null;
				if (timer.getAid() != 0) {
					key = new StringBuffer("uid_").append(timer.getUid())
							.append("-aid_").append(timer.getAid()).toString();
				} else {
					key = new StringBuffer("uid_").append(timer.getUid())
							.toString();
				}
				CopyOnWriteArrayList<Clickoo_user_limittimes> oldTimerList = timerCache
						.get(key);
				if (oldTimerList == null) {
					oldTimerList = new CopyOnWriteArrayList<Clickoo_user_limittimes>();
				}
				oldTimerList.add(timer);
				timerCache.put(key, oldTimerList);
			}
		}
		logger.info("timer cache init success......");

		logger.info("user offsest cache init state......");
		List<Clickoo_user> offsetList = new UserDao().getAllUserOffsets();
		if (offsetList != null && offsetList.size() > 0) {
			for (Clickoo_user offset : offsetList) {
				offsetCache.put(new StringBuffer("offset_").append(
						offset.getUid()).toString(), offset.getTsoffset());
			}
		}
		logger.info("user offsest cache init success......");

	}

	*//**
	 * 增加一条账号级别的普通定时信息.
	 * 
	 * @param timer
	 * @return
	 *//*
	public static boolean addTimer(Clickoo_user_limittimes timer) {
		String key = new StringBuffer("uid_").append(timer.getUid()).append(
				"-aid_").append(timer.getAid()).toString();
		CopyOnWriteArrayList<Clickoo_user_limittimes> timerList = timerCache
				.get(key);
		// 删除账号级别的普通定时器
		removeCommonTimer(timerList);
		if (timerList == null) {
			timerList = new CopyOnWriteArrayList<Clickoo_user_limittimes>();
		}
		timerList.add(timer);
		timerCache.put(key, timerList);
		return true;
	}

	*//**
	 * 查找账号定时信息.
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 *//*
	public static List<Clickoo_user_limittimes> getTimerByUidAndAid(String uid,
			String aid) {

		CopyOnWriteArrayList<Clickoo_user_limittimes> timerList = new CopyOnWriteArrayList<Clickoo_user_limittimes>();

		// 得到账号级别的定时设置
		String account_key = new StringBuffer("uid_").append(uid).append(
				"-aid_").append(aid).toString();
		CopyOnWriteArrayList<Clickoo_user_limittimes> tmpList = timerCache.get(account_key);
		if (tmpList != null && tmpList.size() > 0) {
			timerList.addAll(tmpList);
		}

		// 得到用户级别的定时设置
		String user_key = new StringBuffer("uid_").append(uid).toString();
		tmpList = timerCache.get(user_key);
		if (tmpList != null && tmpList.size() > 0) {
			timerList.addAll(tmpList);
		}
		
		return timerList;
	}
	
	*//**
	 * 查找用户定时信息.
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 *//*
	public static boolean isPushOffForUser(String uid) {
		List<Clickoo_user_limittimes> timerList = null;
		// 得到用户级别的定时设置
		String user_key = new StringBuffer("uid_").append(uid).toString();
		if (timerCache.get(user_key) != null) {
			timerList = timerCache.get(user_key);
		}

		if (timerList != null && timerList.size() == 1) {
			if (timerList.get(0).getTimetype() == UPEConstants.USER_OFF_TIMER) {
				return true;
			} else {
				logger.info("user timer is not push off timer!!!");
			}
		} else if (timerList != null && timerList.size() > 1){
			logger.error("user has more than one timer!!!");
		}
		return false;
	}

	*//**
	 * 关闭账号的推送.
	 * 
	 * @param uid
	 * @param aid
	 *//*
	public static void addAccountOffTimer(String uid, String aid) {
		Clickoo_user_limittimes timer = new Clickoo_user_limittimes();
		timer.setTimetype(UPEConstants.ACCOUNT_OFF_TIMER);
		timer.setAid(Long.parseLong(aid));
		timer.setUid(Long.parseLong(uid));
		String key = new StringBuffer("uid_").append(uid).append("-aid_")
				.append(aid).toString();
		CopyOnWriteArrayList<Clickoo_user_limittimes> timerList = timerCache
				.get(key);
		// 删除账号级别的非普通定时器.
		removeNonCommonTimer(timerList);
		if (timerList == null) {
			timerList = new CopyOnWriteArrayList<Clickoo_user_limittimes>();
		}
		timerList.add(timer);
		timerCache.put(key, timerList);
	}

	*//**
	 * 开启账号的推送.
	 * 
	 * @param uid
	 * @param aid
	 *//*
	public static void addAccountOnTimer(String uid, String aid) {
		String key = new StringBuffer("uid_").append(uid).append("-aid_")
				.append(aid).toString();
		CopyOnWriteArrayList<Clickoo_user_limittimes> timerList = timerCache
				.get(key);
		// 删除账号级别的非普通定时器
		removeNonCommonTimer(timerList);
		if (timerList == null || timerList.size() == 0) {
			timerCache.remove(key);
		} else {
			timerCache.put(key, timerList);
		}
	}

	*//**
	 * 关闭账号的定时PUSH设置.
	 * 
	 * @param uid
	 * @param aid
	 *//*
	public static void addSchedulePushOffForAccount(String uid, String aid) {
		String key = new StringBuffer("uid_").append(uid).append("-aid_")
				.append(aid).toString();
		CopyOnWriteArrayList<Clickoo_user_limittimes> timerList = timerCache
				.get(key);
		// 删除账号级别的普通定时器
		removeCommonTimer(timerList);
		if (timerList == null || timerList.size() == 0) {
			timerCache.remove(key);
		} else {
			timerCache.put(key, timerList);
		}
	}

	*//**
	 * 关闭用户的推送.
	 * 
	 * @param uid
	 * @param aid
	 *//*
	public static void addUserOffTimer(String uid) {
		Clickoo_user_limittimes timer = new Clickoo_user_limittimes();
		timer.setTimetype(UPEConstants.USER_OFF_TIMER);
		timer.setUid(Long.parseLong(uid));
		CopyOnWriteArrayList<Clickoo_user_limittimes> timerList = new CopyOnWriteArrayList<Clickoo_user_limittimes>();
		timerList.add(timer);
		String key = new StringBuffer("uid_").append(uid).toString();
		timerCache.put(key, timerList);
	}

	*//**
	 * 开启用户的推送.
	 * 
	 * @param uid
	 * @param aid
	 *//*
	public static void addUserOnTimer(String uid) {
		String key = new StringBuffer("uid_").append(uid).toString();
		timerCache.remove(key);
	}

	*//**
	 * 查询用户的时间偏移量.
	 * 
	 * @param uid
	 * @return
	 *//*
	public static long getTsOffset(String uid) {
		String key = new StringBuffer("offset_").append(uid).toString();
		long offset = 0;
		Long cache = offsetCache.get(key);
		if (cache != null) {
			offset = cache;
		}
		return offset;
	}

	*//**
	 * 更新用户的时间偏移量.
	 * 
	 * @param uid
	 * @param offset
	 *//*
	public static void updateTsOffset(String uid, long offset) {
		String key = new StringBuffer("offset_").append(uid).toString();
		offsetCache.put(key, offset);
	}

	*//**
	 * 删除账号级别的普通定时器.
	 * 
	 * @param timerList
	 *//*
	private static void removeCommonTimer(
			List<Clickoo_user_limittimes> timerList) {
		if (timerList != null && timerList.size() > 0) {
			Clickoo_user_limittimes timerToRemove = null;
			for (Clickoo_user_limittimes timer : timerList) {
				if (timer.getTimetype() == UPEConstants.ACCOUNT_COMMON_TIMER) {
					timerToRemove = timer;
					break;
				}
			}
			timerList.remove(timerToRemove);
		}
	}

	*//**
	 * 删除账号级别的非普通定时器.
	 * 
	 * @param timerList
	 *//*
	private static void removeNonCommonTimer(
			List<Clickoo_user_limittimes> timerList) {
		if (timerList != null && timerList.size() > 0) {
			Clickoo_user_limittimes timerToRemove = null;
			for (Clickoo_user_limittimes timer : timerList) {
				if (timer.getTimetype() != UPEConstants.ACCOUNT_COMMON_TIMER) {
					timerToRemove = timer;
					break;
				}
			}
			timerList.remove(timerToRemove);
		}
	}
	
	*//**
	 * 根据uid和aid删除定时设置.
	 * @param uid
	 * @param aid
	 *//*
	public static void deleteTimerByUidAndAid(String uid, String aid) {
		String key = new StringBuffer("uid_").append(uid).append("-aid_")
				.append(aid).toString();
		timerCache.remove(key);
	}

}
*/