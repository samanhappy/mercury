package com.dreamail.mercury.dal.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_yahoosnp_message;

public class YahoosnpMessageDao {

	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(YahoosnpMessageDao.class);
	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.YahoosnpMessageMapper";

	public Clickoo_yahoosnp_message selectByAid(long aid) {
		Clickoo_yahoosnp_message msg = null;

		try {
			sqlSession = SessionFactory.getSession();
			msg = (Clickoo_yahoosnp_message) sqlSession.selectOne(namespace
					+ ".selectByAid", aid);

		} catch (SQLException e) {
			logger.error("failed to get yahoosnp message by aid", e);
		} finally {
			SessionFactory.closeSession();
		}

		return msg;
	}

	@SuppressWarnings("unchecked")
	public List<Clickoo_yahoosnp_message> selectAllSNPMessage() {
		List<Clickoo_yahoosnp_message> list = null;

		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_yahoosnp_message>) sqlSession
					.selectList(namespace + ".selectAllSNPMessage");
		} catch (SQLException e) {
			logger.error("failed to selectAllSNPMessage", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Long> selectAllSNPAid() {
		List<Long> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Long>) sqlSession.selectList(namespace
					+ ".selectAllSNPAid");
		} catch (SQLException e) {
			logger.error("failed to selectAllSNPAid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	public boolean updateMessage(Clickoo_yahoosnp_message msg) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateMessage", msg);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to update yahoosnp message", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	public boolean updateMaxUuid(Clickoo_yahoosnp_message msg) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateMaxUuid", msg);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to update yahoosnp message", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 更新用户状态.
	 * 
	 * @param msg
	 * @return
	 */
	public boolean updateMessageStatus(Clickoo_yahoosnp_message msg) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateMessageStatus", msg);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to update yahoosnp message", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	public boolean insertMessage(Clickoo_yahoosnp_message msg) {
		int i = -1;

		try {
			sqlSession = SessionFactory.getSession();
			i = sqlSession.insert(namespace + ".insertMessage", msg);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to insert message", e);
		} finally {
			SessionFactory.closeSession();
		}
		return i == 1;
	}

	/**
	 * 根据aid删除所有的记录.
	 * 
	 * @param aid
	 * @return
	 */
	public boolean deleteAllInfoByAid(long aid) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.delete(namespace + ".deleteMessageByAid", aid);
			sqlSession
					.delete("com.clickoo.mercury.domain.YahoosnpIdsMapper.deleteAllUuidByAid",
							aid);
			sqlSession.commit();
		} catch (SQLException e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to delete yahoo snp message", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}
}
