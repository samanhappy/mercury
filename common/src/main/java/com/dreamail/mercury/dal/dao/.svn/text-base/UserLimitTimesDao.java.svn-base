/**
 * 
 */
package com.dreamail.mercury.dal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_user_limittimes;
import com.dreamail.mercury.util.UPEConstants;

/**
 * 收邮件限制时间Dao操作类.
 * 
 * @author 001211
 * 
 */
public class UserLimitTimesDao {

	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(UserLimitTimesDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.TimerMapper";

	/**
	 * 获取clickoo_user_limittimes中的所有数据
	 * 
	 * @param datetime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_user_limittimes> queryUserLimittimes() {
		List<Clickoo_user_limittimes> userLimittimes = null;
		try {
			sqlSession = SessionFactory.getSession();
			userLimittimes = (List<Clickoo_user_limittimes>) sqlSession
					.selectList(namespace + ".queryUserLimittimes");
		} catch (Exception e) {
			logger.error("failed to queryUserLimittimes", e);
		} finally {
			SessionFactory.closeSession();
		}
		return userLimittimes;
	}

	/**
	 * 新增UserLimittimes
	 * 
	 * @param cul
	 * @return
	 */
	public Clickoo_user_limittimes insertUserLimittimes(
			Clickoo_user_limittimes cul) {
		Clickoo_user_limittimes oldTimer = null;
		Map<String, String> idMap = new HashMap<String, String>();
		idMap.put("uid", String.valueOf(cul.getUid()));
		idMap.put("aid", String.valueOf(cul.getAid()));
		try {
			sqlSession = SessionFactory.getSession();
			// 删除账号级别的可能存在的普通定时
			Object obj = sqlSession.selectOne(namespace
					+ ".getCommonTimerInfoByUidAndAid", idMap);
			if (obj != null) {
				oldTimer = (Clickoo_user_limittimes) obj;
			}
			sqlSession.delete(namespace + ".deleteCommonTimerByUidAndAid",
					idMap);
			sqlSession.insert(namespace + ".insertUserLimittimes", cul);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to insert UserLimittimes", e);
		} finally {
			SessionFactory.closeSession();
		}
		return oldTimer;
	}

	/**
	 * 新增用户级别UserLimittimes
	 * 
	 * @param cul
	 * @return
	 */
	public Clickoo_user_limittimes insertUserCommonLimittimes(
			Clickoo_user_limittimes cul) {
		Clickoo_user_limittimes oldTimer = null;
		try {
			sqlSession = SessionFactory.getSession();
			// 删除账号级别的可能存在的普通定时
			Object obj = sqlSession.selectOne(namespace
					+ ".getCommonTimerInfoByUid", cul.getUid());
			if (obj != null) {
				oldTimer = (Clickoo_user_limittimes) obj;
			}
			sqlSession.delete(namespace + ".deleteCommonTimerByUid", cul
					.getUid());
			sqlSession.insert(namespace + ".insertUserLimittimes", cul);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to insert UserLimittimes", e);
		} finally {
			SessionFactory.closeSession();
		}
		return oldTimer;
	}

	/**
	 * 得到所有的定时状态信息.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_user_limittimes> getAllTimerInfo() {
		List<Clickoo_user_limittimes> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_user_limittimes>) sqlSession
					.selectList(namespace + ".getAllTimerInfo");
		} catch (Exception e) {
			logger.error("failed to getAllTimerInfo", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 得到所有普通的定时状态信息.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_user_limittimes> getAllCommonTimerInfo() {
		List<Clickoo_user_limittimes> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_user_limittimes>) sqlSession
					.selectList(namespace + ".getAllCommonTimerInfo");
		} catch (Exception e) {
			logger.error("failed to getAllTimerInfo", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 得到用户的所有定时设置.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_user_limittimes> getAllTimerInfoByUid(long uid) {
		List<Clickoo_user_limittimes> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_user_limittimes>) sqlSession.selectList(
					namespace + ".selectAllTimerByUid", uid);
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to getAllTimerInfo", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 开启账号push
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 */
	public boolean addOnTimerForAccount(String uid, String aid) {
		Map<String, String> idMap = new HashMap<String, String>();
		idMap.put("uid", uid);
		idMap.put("aid", aid);
		try {
			sqlSession = SessionFactory.getSession();
			// 只删除账号级别的非普通定时
			sqlSession.delete(namespace + ".deleteTimerByUidAndAid", idMap);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to getAllTimerInfo", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 关闭账号push
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 */
	public boolean addOffTimerForAccount(String uid, String aid) {
		Map<String, String> idMap = new HashMap<String, String>();
		idMap.put("uid", uid);
		idMap.put("aid", aid);
		idMap.put("timetype", String.valueOf(UPEConstants.ACCOUNT_OFF_TIMER));
		try {
			sqlSession = SessionFactory.getSession();
			// 只删除账号级别的非普通定时
			sqlSession.delete(namespace + ".deleteTimerByUidAndAid", idMap);
			sqlSession.insert(namespace + ".addOffTimerForUidAndAid", idMap);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to getAllTimerInfo", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 关闭账号SCHEDULE PUSH.
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 */
	public Clickoo_user_limittimes addSchedulePushOffForAccount(String uid,
			String aid) {
		Clickoo_user_limittimes oldTimer = null;
		Map<String, String> idMap = new HashMap<String, String>();
		idMap.put("uid", uid);
		idMap.put("aid", aid);
		try {
			sqlSession = SessionFactory.getSession();
			// 删除账号级别的可能存在的普通定时
			Object obj = sqlSession.selectOne(namespace
					+ ".getCommonTimerInfoByUidAndAid", idMap);
			if (obj != null) {
				oldTimer = (Clickoo_user_limittimes) obj;
			}
			// 删除账号级别的普通定时
			sqlSession.delete(namespace + ".deleteCommonTimerByUidAndAid",
					idMap);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to getAllTimerInfo", e);
		} finally {
			SessionFactory.closeSession();
		}
		return oldTimer;
	}

	/**
	 * 关闭用户SCHEDULE PUSH.
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 */
	public Clickoo_user_limittimes addSchedulePushOffForUser(String uid) {
		Clickoo_user_limittimes oldTimer = null;
		try {
			sqlSession = SessionFactory.getSession();
			// 删除账号级别的可能存在的普通定时
			Object obj = sqlSession.selectOne(namespace
					+ ".getCommonTimerInfoByUid", uid);
			if (obj != null) {
				oldTimer = (Clickoo_user_limittimes) obj;
			}
			// 删除账号级别的普通定时
			sqlSession.delete(namespace + ".deleteCommonTimerByUid", uid);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to add SchedulePushOffForUser", e);
		} finally {
			SessionFactory.closeSession();
		}
		return oldTimer;
	}

	/**
	 * 开启用户push.
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 */
	public boolean addOnTimerForUser(String uid) {
		try {
			sqlSession = SessionFactory.getSession();
			// 只删除用户级别的定时器
			sqlSession.delete(namespace + ".deleteTimerByUid", uid);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to getAllTimerInfo", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 关闭用户push.
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 */
	public boolean addOffTimerForUser(String uid) {
		Map<String, String> idMap = new HashMap<String, String>();
		idMap.put("uid", uid);
		idMap.put("timetype", String.valueOf(UPEConstants.USER_OFF_TIMER));
		try {
			sqlSession = SessionFactory.getSession();
			// 只删除用户级别的定时器
			sqlSession.delete(namespace + ".deleteTimerByUid", uid);
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(namespace + ".addOffTimerForUid", idMap);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to addOffTimerForUser", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 关闭用户push.
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 */
	public boolean schedulePushOffForAccount(String uid, String aid) {
		Map<String, String> idMap = new HashMap<String, String>();
		idMap.put("uid", uid);
		idMap.put("timetype", String.valueOf(UPEConstants.USER_OFF_TIMER));
		try {
			sqlSession = SessionFactory.getSession();
			// 只删除用户级别的定时器
			sqlSession.delete(namespace + ".deleteTimerByUid", uid);
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(namespace + ".addOffTimerForUid", idMap);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to addOffTimerForUser", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 删除账号的所有定时设置.
	 * 
	 * @param aid
	 * @return
	 */
	public boolean deleteTimerByAid(long aid) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.delete(namespace + ".deleteTimerByAid", aid);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to deleteTimerByAid", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 判断用户是否PushOff.
	 * 
	 * @param uid
	 * @return
	 */
	public boolean isUserTimerOff(long uid) {
		try {
			sqlSession = SessionFactory.getSession();
			Object obj = sqlSession.selectOne(namespace
					+ ".selectUserOffTimerByUid", uid);
			return obj != null;
		} catch (Exception e) {
			logger.error("failed to isUserTimerOff", e);
		} finally {
			SessionFactory.closeSession();
		}
		return false;
	}

}
