package com.dreamail.mercury.dal.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_user_account;
import com.dreamail.mercury.util.Constant;

public class UARelationDao {
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory.getLogger(UARelationDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.UARelationMapper";

	/**
	 * 创建一条user和account关系的记录.
	 * 
	 * @param uaRelation
	 * @return long
	 */
	public long createUARelation(Clickoo_user_account uaRelation) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(namespace + ".insertUARelation", uaRelation);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to setId for Clickoo_user_account", e);
			return -1;
		} finally {
			SessionFactory.closeSession();
		}

		return uaRelation.getId();
	}

	/**
	 * 根据uid查询所有aid.
	 * 
	 * @param uid
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectAllAid(long uid) {
		List<String> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = sqlSession.selectList(namespace + ".selectAllAid", uid);
		} catch (SQLException e) {
			logger.error("failed to get all aid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 根据uid查询所有任务工厂需要处理的aid.
	 * 
	 * @param uid
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectAllTaskAid(long uid) {
		List<String> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = sqlSession.selectList(namespace + ".selectAllTaskAid", uid);
		} catch (SQLException e) {
			logger.error("failed to get all task aid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 根据IMEI查询所有aid.
	 * 
	 * @param uid
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectAllAidByIMEI(String IMEI) {
		List<String> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = sqlSession.selectList(namespace + ".selectAllAidByIMEI",
					IMEI);
		} catch (SQLException e) {
			logger.error("failed to selectAllAidByIMEI", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 根据uid和aid删除一条记录.
	 * 
	 * @param uaRelation
	 * @return true or false
	 */
	public boolean deleteUARelation(Clickoo_user_account uaRelation) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.delete(namespace + ".deleteUARelation", uaRelation);
			sqlSession.commit();
		} catch (SQLException e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to deleteUARelation", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 根据uid删除所有记录.
	 * 
	 * @param uid
	 * @return true or false
	 */
	public boolean deleteUARelationByUid(long uid) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.delete(namespace + ".deleteUARelationByUid", uid);
			sqlSession.commit();
		} catch (SQLException e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to deleteUARelationByUid", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 根据aid查询所有uid.
	 * 
	 * @param uid
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectAllUid(long aid) {
		List<String> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = sqlSession.selectList(namespace + ".selectAllUid", aid);
		} catch (SQLException e) {
			logger.error("failed to get all uid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 根据uid和aid改变状态.
	 * 
	 * @param uid
	 * @param aid
	 * @param status
	 * @return
	 */
	public boolean updateStatusByUid(long uid, long aid, int status) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", uid + "");
		map.put("aid", aid + "");
		map.put("status", String.valueOf(status));
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateStatusByUid", map);
			sqlSession.commit();
		} catch (SQLException e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to updateStatusByUid", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 判断对应用户-账号关系是否存在.
	 * 
	 * @param uaRelation
	 * @return
	 */
	public boolean selectUARelation(Clickoo_user_account uaRelation) {
		try {
			sqlSession = SessionFactory.getSession();
			if (sqlSession.selectOne(namespace + ".selectUARelation",
					uaRelation) != null) {
				return true;
			}
		} catch (Exception e) {
			logger.error("failed to selectUARelation", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return false;
	}

	/**
	 * 查询出对应用户-账号关系.
	 * 
	 * @param uaRelation
	 * @return
	 */
	public Clickoo_user_account selectUARelationExist(
			Clickoo_user_account uaRelation) {
		Clickoo_user_account ua = null;
		try {
			sqlSession = SessionFactory.getSession();
			ua = (Clickoo_user_account) sqlSession.selectOne(namespace
					+ ".selectUARelation", uaRelation);
		} catch (Exception e) {
			logger.error("failed to selectUARelation", e);
			return ua;
		} finally {
			SessionFactory.closeSession();
		}
		return ua;
	}

	/**
	 * 查询所有的用户账号关系.
	 * 
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_user_account> selectAllUARelation(long uid) {
		List<Clickoo_user_account> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = sqlSession.selectList(namespace + ".selectAllUARelation",
					uid);
		} catch (SQLException e) {
			logger.error("failed to get all Clickoo_user_account", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 查询用户账号信息(id,name,out_cert).
	 * 
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_account> selectAllAccountByUid(long uid) {
		List<Clickoo_mail_account> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = sqlSession.selectList(namespace + ".selectAllAccountByUid",
					uid);
		} catch (SQLException e) {
			logger.error("failed to get all Clickoo_user_account", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Clickoo_user_account> getOldAccountId(long uid) {
		List<Clickoo_user_account> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_user_account>) sqlSession.selectList(namespace
					+ ".getOldAccountId", uid);
		} catch (SQLException e) {
			logger.error("failed to getOldAccountId", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Clickoo_user_account> getNewAccountId(long uid) {
		List<Clickoo_user_account> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_user_account>) sqlSession.selectList(namespace
					+ ".getNewAccountId", uid);
		} catch (SQLException e) {
			logger.error("failed to getNewAccountId", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 用户自动下线时改变数据库状态，并得到所有完全下线的aid.
	 * 
	 * @author meng.sun
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_account> setAutoOfflineUsers(List<String> uidlist,
			int status) {
		List<Clickoo_mail_account> list = null;
		if (uidlist != null && uidlist.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (String uid : uidlist) {
				sb.append(uid).append(",");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Constant.MAP_KEY_UIDS, sb.substring(0, sb.length() - 1));
			map.put(Constant.MAP_KEY_STATUS, status);

			try {
				sqlSession = SessionFactory.getSession();
				list = (List<Clickoo_mail_account>) sqlSession.selectList(
						namespace + ".SetOfflineForUsers", map);
				sqlSession.commit();
			} catch (Exception e) {
				if (sqlSession != null) {
					sqlSession.rollback();
				}
				logger.error("failed to setAutoOfflineUsers", e);
			} finally {
				SessionFactory.closeSession();
			}
		}
		return list;
	}

	/**
	 * 用户上线时改变数据库状态，并得到所有完全下线的aid.
	 * 
	 * @author meng.sun
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_account> setOnline(long uid, int status, long aid,
			List<String> aids) {
		List<Clickoo_mail_account> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.MAP_KEY_UID, uid);
		map.put(Constant.MAP_KEY_STATUS, status);
		if (aid != -1) {
			map.put(Constant.MAP_KEY_AIDS, aid);
		} else if (aids != null && aids.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (String str : aids) {
				sb.append(str);
			}
			map.put(Constant.MAP_KEY_AIDS, sb.toString());
		}

		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_mail_account>) sqlSession.selectList(namespace
					+ ".SetOnline", map);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to setOnline", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 用户上线时改变数据库状态，并得到所有完全下线的账号.
	 * 
	 * @author meng.sun
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_account> setOffline(long uid, int status,
			long aid, List<String> aids) {
		List<Clickoo_mail_account> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.MAP_KEY_UID, uid);
		map.put(Constant.MAP_KEY_STATUS, status);
		if (aid != -1) {
			map.put(Constant.MAP_KEY_AIDS, aid);
		} else if (aids != null && aids.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (String str : aids) {
				sb.append(str).append(",");
			}
			map.put(Constant.MAP_KEY_AIDS, sb.substring(0, sb.length() - 1));
		}

		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_mail_account>) sqlSession.selectList(namespace
					+ ".SetOffline", map);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to setOnline", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/*
	 * 更新关系表.
	 */
	public boolean updateUARelation(Clickoo_user_account ua) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateUARelation", ua);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to getAbsoluteOffAidsAfterChangeOffStatus", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 根据uid把所有已收老邮件的账号状态改为0.
	 * 
	 * @param uid
	 * @return
	 */
	public boolean updateUARelationByUid(String uid) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateUARelationByUid", uid);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to updateUARelationByUid", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 更新关系表validation字段.
	 */
	public boolean updateValidationByUidAndAid(Clickoo_user_account ua) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateValidationByUidAndAid", ua);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to updateValidationByUidAndAid", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 更新关系表validation字段.
	 */
	public boolean updateValidationByUid(Clickoo_user_account ua) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateValidationByUid", ua);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to updateValidationByUidAndAid", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 根据用户id选择所有有异常的账号.
	 * 
	 * @param ua
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectAidsAndUpdateValidationToNormalByUidAndValidation(
			Clickoo_user_account ua) {
		List<String> aids = null;
		try {
			sqlSession = SessionFactory.getSession();
			aids = (List<String>) sqlSession.selectList(namespace
					+ ".selectAidsByUidAndValidation", ua);
			sqlSession.update(namespace + ".updateValidationToNormalByUid", ua);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to selectAidsByUidAndValidation", e);
		} finally {
			SessionFactory.closeSession();
		}
		return aids;
	}

	/**
	 * 判断数据库中是否有idle用户使用pushmail（会有2个帐号在关联表,其中idle用户的uid为0，pushmail用户的uid不为0）
	 * 
	 * @param aid
	 * @return boolean 如果有其他用户（pushmail用户）正在使用这个帐号，返回true
	 */
	@SuppressWarnings("unchecked")
	public boolean isOtherAccount(long aid) {
		boolean flag = false;
		try {
			sqlSession = SessionFactory.getSession();
			List<Clickoo_user_account> user_account = (List<Clickoo_user_account>) sqlSession
					.selectList(namespace + ".is2Account", aid);
			if (user_account != null) {
				for (Clickoo_user_account account : user_account) {
					if (account.getUid() != 0) {
						flag = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("failed to getIdsByAccountName", e);
		} finally {
			SessionFactory.closeSession();
		}
		return flag;
	}


	/**
	 * 判读此帐号是否还有其他用户已经在线.
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isAccountOnline(long uid, long aid) {
		Clickoo_user_account ua = new Clickoo_user_account();
		ua.setUid(uid);
		ua.setAid(aid);
		try {
			sqlSession = SessionFactory.getSession();
			List<String> list = (List<String>) sqlSession.selectList(namespace
					+ ".selectOnlineAccount", ua);
			if (list != null && list.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			logger.error("failed to isAccountOnline", e);
		} finally {
			SessionFactory.closeSession();
		}
		return false;
	}
	
	/**
	 * 根据aid查处一条关系记录.
	 * @param aid
	 * @return
	 */
	public Clickoo_user_account selectUAByAid(long aid){
		Clickoo_user_account ua = null;
		try {
			sqlSession = SessionFactory.getSession();
			ua = (Clickoo_user_account) sqlSession.selectOne(namespace
					+ ".selectUAByAid", aid);
		} catch (Exception e) {
			logger.error("failed to Clickoo_user_account by "+aid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return ua;
	}
	/**
	 * 更新aid对应关系.
	 * @param ua
	 * @return
	 */
	public boolean updateUAByAid(Clickoo_user_account ua){
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateUAByAid", ua);
			sqlSession.commit();
		} catch (SQLException e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to updateUAByAid", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}
	
	/**
	 * 用户邮箱个数
	 * @param ua
	 * @return
	 */
	public int selectUserEmailAccount(long uid){
		int accountCount = 0;
		try {
			sqlSession = SessionFactory.getSession();
			Object obj = sqlSession.selectOne(namespace + ".selectUserEmailAccount", uid);
			if (obj != null) {
				accountCount = Integer.parseInt(obj.toString());
			}
		} catch (Exception e) {
			logger.error("failed to selectUserEmailAccount by uid " + uid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return accountCount;
	}
}
