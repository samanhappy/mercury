/**
 * 
 */
package com.dreamail.mercury.dal.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.pojo.User_role;
import com.dreamail.mercury.util.CAGConstants;
import com.dreamail.mercury.util.RoleUtil;

/**
 * @author meng.sun
 * 
 */
public class UserDao {

	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory.getLogger(UserDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.UserMapper";

	/**
	 * 注册新用户和设备 . 返回注册成功的uid，如果返回-1代表出错
	 * 
	 * @param user
	 *            Clickoo_user
	 * @return long user id
	 */
	public long createUser(Clickoo_user user) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(namespace + ".insertUser", user);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to setId for user or device", e);
			return -1;
		} finally {
			SessionFactory.closeSession();
		}
		return user.getUid();
	}

	/**
	 * 注册新用户和设备 . 返回注册成功的uid，如果返回-1代表出错
	 * 
	 * @param user
	 *            Clickoo_user
	 * @return long user id
	 */
	public long createDefaultUser(String IMEI) {
		Clickoo_user user = new Clickoo_user();
		user.setIMEI(IMEI);
		user.setStatus(0);
		user.setRolelevel(RoleUtil
				.getRoleLevelByTitle(CAGConstants.COS_TITLE_FREE));
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		user.setTimedate(formatter.format(new Date()));

		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(namespace + ".insertUser", user);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to setId for user or device", e);
			return -1;
		} finally {
			SessionFactory.closeSession();
		}
		return user.getUid();
	}

	/**
	 * 根据uid得到用户.
	 * 
	 * @param uid
	 *            user id
	 * @return Clickoo_user
	 */
	public Clickoo_user getUserById(long uid) {
		Clickoo_user user = null;
		try {
			sqlSession = SessionFactory.getSession();
			user = (Clickoo_user) sqlSession.selectOne(
					"com.clickoo.mercury.domail.UserMapper.getUserById", uid);
		} catch (SQLException e) {
			logger.error("failed to get user by id", e);
		} finally {
			SessionFactory.closeSession();
		}
		return user;
	}

	/**
	 * 根据uid得到用户.
	 * 
	 * @param uid
	 *            user id
	 * @return Clickoo_user
	 */
	public User_role getUserRoleById(long uid) {
		User_role ur = null;
		try {
			sqlSession = SessionFactory.getSession();
			ur = (User_role) sqlSession.selectOne(
					"com.clickoo.mercury.domail.UserMapper.getUserRoleById",
					uid);
		} catch (SQLException e) {
			logger.error("failed to get user by id", e);
		} finally {
			SessionFactory.closeSession();
		}
		return ur;
	}

	/**
	 * 根据uid查询offset
	 * 
	 * @param uid
	 * @return
	 */
	public long getUserOffsetById(long uid) {
		long offset = -1;
		try {
			sqlSession = SessionFactory.getSession();
			offset = (Long) sqlSession.selectOne(
					"com.clickoo.mercury.domail.UserMapper.getUserOffsetById",
					uid);
		} catch (SQLException e) {
			logger.error("failed to getUserNameById", e);
		} finally {
			SessionFactory.closeSession();
		}
		return offset;
	}

	/**
	 * 根据uid得到用户.
	 * 
	 * @param uid
	 *            user id
	 * @return Clickoo_user
	 */
	public String getUserNameById(long uid) {
		String user = null;
		try {
			sqlSession = SessionFactory.getSession();
			user = (String) sqlSession.selectOne(
					"com.clickoo.mercury.domail.UserMapper.getUserNameById",
					uid);
		} catch (SQLException e) {
			logger.error("failed to getUserNameById", e);
		} finally {
			SessionFactory.closeSession();
		}
		return user;
	}

	/**
	 * 更新用户.
	 * 
	 * @param user
	 *            Clickoo_user
	 * @return boolean
	 */
	public boolean updateUser(Clickoo_user user) {
		int i = -1;
		try {
			sqlSession = SessionFactory.getSession();
			i = sqlSession.update(namespace + ".updateUser", user);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to update user", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return i == 1;
	}

	/**
	 * 根据uid删除用户信息以及所有相关的设备，账号和邮件信息.
	 * 
	 * @param uid
	 *            user id
	 * @return boolean
	 */
	public boolean deleteAllInfoByUid(long uid) {
		Clickoo_user user = null;
		try {
			sqlSession = SessionFactory.getSession();
			user = (Clickoo_user) sqlSession.selectOne(
					"com.clickoo.mercury.domail.UserMapper.getUserById", uid);
			if (user == null) {
				logger.info("there is no info by user id:" + uid);
				return false;
			}
			sqlSession.delete(namespace + ".deleteUserByUid", uid);
			sqlSession.commit();

		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to delete info by user id:" + uid, e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 根据uid验证用户是否存在.
	 * 
	 * @param uid
	 *            user id
	 * @return boolean
	 */
	public boolean selectUser(Long uid) {
		int count = 0;
		boolean exist = false;
		try {
			sqlSession = SessionFactory.getSession();
			count = (Integer) sqlSession.selectOne(namespace + ".selectUser",
					uid);
			if (count == 1) {
				exist = true;
			}
		} catch (SQLException e) {
			logger.error("failed to select User by uid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return exist;
	}

	/**
	 * 得到所有用户id.
	 * 
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getAllUserID() {
		List<Long> idList = null;
		try {
			sqlSession = SessionFactory.getSession();
			idList = (List<Long>) sqlSession.selectList(namespace
					+ ".getAllUserId");
		} catch (SQLException e) {
			logger.error("failed to get all user id", e);
		} finally {
			SessionFactory.closeSession();
		}
		return idList;
	}

	/**
	 * 得到用户deviceModel.
	 */
	public String getDeviceModelByUid(String uid) {
		String deviceModel = null;
		try {
			sqlSession = SessionFactory.getSession();
			deviceModel = (String) sqlSession.selectOne(namespace
					+ ".getDeviceModelByUid", uid);
		} catch (SQLException e) {
			logger.error("failed to get getDeviceModelByUid:" + uid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return deviceModel;
	}

	public Clickoo_user getUsersByUsername(String username) {
		Clickoo_user user = null;
		try {
			sqlSession = SessionFactory.getSession();
			user = (Clickoo_user) sqlSession.selectOne(
					"com.clickoo.mercury.domail.UserMapper.isExistUsername",
					username);
		} catch (SQLException e) {
			logger.error("failed to get user by username", e);
		} finally {
			SessionFactory.closeSession();
		}
		return user;
	}

	/**
	 * 根据IMEI得到用户id
	 * 
	 * @param IMEI
	 * @return
	 */
	public long getUidByIMEI(String IMEI) {
		long uid = -1;
		try {
			sqlSession = SessionFactory.getSession();
			Object obj = sqlSession
					.selectOne(namespace + ".getUidByIMEI", IMEI);
			if (obj != null) {
				uid = (Long) obj;
			} else {
				logger.error("not find uid for IMEI:" + IMEI);
			}
		} catch (SQLException e) {
			logger.error("failed to getUidByIMEI:" + IMEI, e);
		} finally {
			SessionFactory.closeSession();
		}
		return uid;
	}

	/**
	 * 根据name得到用户id
	 * 
	 * @param IMEI
	 * @return
	 */
	public long getUidByName(String name) {
		long uid = -1;
		try {
			sqlSession = SessionFactory.getSession();
			Object obj = sqlSession
					.selectOne(namespace + ".getUidByName", name);
			if (obj != null) {
				uid = (Long) obj;
			} else {
				logger.info("not find uid for name:" + name);
			}
		} catch (SQLException e) {
			logger.error("failed to getUidByIMEI:" + name, e);
		} finally {
			SessionFactory.closeSession();
		}
		return uid;
	}

	public Clickoo_user getUsersByIMEI(String IMEI) {
		Clickoo_user user = null;
		try {
			sqlSession = SessionFactory.getSession();
			user = (Clickoo_user) sqlSession.selectOne(
					"com.clickoo.mercury.domail.UserMapper.isExistIMEI", IMEI);
		} catch (SQLException e) {
			logger.error("failed to get user by IMEI", e);
		} finally {
			SessionFactory.closeSession();
		}
		return user;
	}

	/**
	 * 根据uid获得IMEI.
	 * 
	 * @param uid
	 * @return
	 */
	public String getIMEIByUid(long uid) {
		String IMEI = null;
		try {
			sqlSession = SessionFactory.getSession();
			IMEI = (String) sqlSession.selectOne(namespace + ".getIMEIByUid",
					uid);
		} catch (SQLException e) {
			logger.error("not find IMEI for uid:" + uid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return IMEI;
	}

	/**
	 * 根据username获得IMEI.
	 * 
	 * @param uid
	 * @return
	 */
	public String getIMEIByUsername(String username) {
		String IMEI = null;
		try {
			sqlSession = SessionFactory.getSession();
			IMEI = (String) sqlSession.selectOne(namespace + ".getIMEIByName",
					username);
		} catch (SQLException e) {
			logger.error("not find IMEI for username:" + username, e);
		} finally {
			SessionFactory.closeSession();
		}
		return IMEI;
	}

	/**
	 * 根据uid获得角色名.
	 * 
	 * @param uid
	 * @return
	 */
	public int getRolelevelByUid(long uid) {
		int rolelevel = -1;
		try {
			sqlSession = SessionFactory.getSession();
			Object obj = sqlSession.selectOne(namespace + ".getRolelevelByUid",
					uid);
			if (obj != null && obj instanceof Integer) {
				rolelevel = (Integer) obj;
			}
		} catch (SQLException e) {
			logger.error("failed to getRolelevelByUid by uid:" + uid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return rolelevel;
	}

	/**
	 * 设置用户等级.
	 * 
	 * @param uid
	 * @return
	 */
	public boolean updateRolelevel(Clickoo_user user) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateRolelevel", user);
			sqlSession.commit();
		} catch (SQLException e) {
			sqlSession.rollback();
			logger
					.error("failed to updateRolelevel by uid:" + user.getUid(),
							e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 更新用户时间偏移量.
	 * 
	 * @param user
	 * @return
	 */
	public boolean updateUserTsOffset(Clickoo_user user) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateUserTsOffset", user);
			sqlSession.commit();
		} catch (SQLException e) {
			sqlSession.rollback();
			logger.error("failed to updateUserTsOffset", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 得到所有用户的时间偏移量.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_user> getAllUserOffsets() {
		List<Clickoo_user> userList = null;
		try {
			sqlSession = SessionFactory.getSession();
			userList = sqlSession.selectList(namespace + ".getAllUserOffsets");
		} catch (Exception e) {
			logger.error("failed to getAllUserOffsets", e);
		} finally {
			SessionFactory.closeSession();
		}
		return userList;
	}

	/**
	 * 得到某级别下所有用户
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_user> getUserByTitle(String title) {
		List<Clickoo_user> userList = null;
		try {
			sqlSession = SessionFactory.getSession();
			userList = sqlSession.selectList(namespace + ".getUserByTitle",
					title);
		} catch (Exception e) {
			logger.error("failed to getUserByTitle", e);
		} finally {
			SessionFactory.closeSession();
		}
		return userList;
	}


	/**
	 * 得到所有上线过的用户IMEI和ID.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllOnlineIMEI() {
		List<String> IMEIList = null;
		try {
			sqlSession = SessionFactory.getSession();
			IMEIList = sqlSession.selectList(namespace + ".getAllOnlineIMEI");
		} catch (Exception e) {
			logger.error("failed to getAllOnlineUidAndIMEI", e);
		} finally {
			SessionFactory.closeSession();
		}
		return IMEIList;
	}
	
	/**
	 * 更新用户黑白名单设置.
	 * @param user
	 * @return
	 */
	public boolean updateUserSign(Clickoo_user user) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateUserSign", user);
			sqlSession.commit();
		} catch (SQLException e) {
			sqlSession.rollback();
			logger.error("failed to updateUserSign", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}
	/**
	 * 用户更换设备、收邮件服务器根据uid查出aid、device.
	 * @param uid
	 * @return
	 */
	public Clickoo_user getAidAndDevice(long uid) {
		Clickoo_user user = null;
		try {
			sqlSession = SessionFactory.getSession();
			user = (Clickoo_user) sqlSession.selectOne(namespace + ".getAidAndDevice", uid);
			sqlSession.commit();
		} catch (SQLException e) {
			sqlSession.rollback();
			logger.error("failed to getAidAndDevice", e);
		} finally {
			SessionFactory.closeSession();
		}
		return user;
	}
	/**
	 * 用户更换设备、收邮件服务器根据uid查出aid、黑白名单.
	 * @param uid
	 * @return
	 */
	public Clickoo_user getAidAndFilter(long uid) {
		Clickoo_user user = null;
		try {
			sqlSession = SessionFactory.getSession();
			user = (Clickoo_user) sqlSession.selectOne(namespace + ".getAidAndFilter", uid);
			sqlSession.commit();
		} catch (SQLException e) {
			sqlSession.rollback();
			logger.error("failed to getAidAndFilter", e);
		} finally {
			SessionFactory.closeSession();
		}
		return user;
	}
	
}
