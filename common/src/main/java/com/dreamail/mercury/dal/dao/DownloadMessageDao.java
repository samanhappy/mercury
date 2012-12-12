package com.dreamail.mercury.dal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_download_message;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.IdMap;

public class DownloadMessageDao {
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(DownloadMessageDao.class);

	private static final String messageNamespace = "com.clickoo.mercury.domain.DownloadMessageDao";

	/**
	 * 向数据库添加一条Clickoo_download_message记录.
	 * 
	 * @param doMessage
	 * @return long
	 */
	public long saveDownloadMessage(Clickoo_download_message doMessage) {
		if (doMessage == null) {
			return -1;
		}
		try {
			doMessage.setStatus(1);
			logger.info("saveDownloadMessage id is:" + doMessage.getId() + "");
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(messageNamespace + ".insertDownloadMessage",
					doMessage);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to insert doMessage", e);
			return -1;
		} finally {
			SessionFactory.closeSession();
		}
		return doMessage.getId();
	}

	/**
	 * 根据id数组删除邮件.
	 * 
	 * @param ids
	 *            message id
	 * @return boolean
	 */
	public boolean deleteDownloadMessageByDids(IdMap idMap) {
		try {
			sqlSession = SessionFactory.getSession();
			if (sqlSession.delete(messageNamespace
					+ ".deleteDownloadMessageByDids", idMap) == 0) {
				logger.info("failed to deleteDMessageByDids");
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to deleteDownloadMessageByDids", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 得到指定uid对应的状态为0邮件.
	 * 
	 * @param uid
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_message> getDownloadedMessages(long uid) {
		List<Clickoo_message> list = null;
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("uid", String.valueOf(uid));
		// map.put("aid", String.valueOf(aid));
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_message>) sqlSession.selectList(
					messageNamespace + ".getDownloadedMessages", uid);
		} catch (Exception e) {
			logger.error(
					"failed to getDownloadedMessages for" + " uid: " + uid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 根据mid更新Clickoo_download_message的状态为0.
	 * 
	 * @param mids
	 * @return boolean
	 */
	public boolean updateDownloadMessageStatus(Long mids[]) {
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("mid", mids);
		// map.put("status", String.valueOf(status));
		try {
			sqlSession = SessionFactory.getSession();
			if (sqlSession.update(messageNamespace
					+ ".updateDownloadMessageStatus", mids) == 0) {
				StringBuffer sb = new StringBuffer();
				for (Long mid : mids) {
					sb.append(mid + ",");
				}
				logger.info("no message by id:" + sb.toString() + " state to update");
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to updateDownloadMessageStatus", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 根据mid更新Clickoo_download_message的状态为1.
	 * 
	 * @param mids
	 * @return boolean
	 */
	public boolean updateDownloadedMessageStatus(Long mids[]) {
		try {
			sqlSession = SessionFactory.getSession();
			if (sqlSession.update(messageNamespace
					+ ".updateDownloadedMessageStatus", mids) == 0) {
				StringBuffer sb = new StringBuffer();
				for (Long mid : mids) {
					sb.append(mid + ",");
				}
				logger.info("no message by id:" + sb.toString()
						+ " state to update");
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to updateDownloadedMessageStatus", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 根据mid更新Clickoo_download_message的状态为2.
	 * 
	 * @param mid
	 * @return boolean
	 */
	/*
	 * public boolean updateDownloadFailMessageStatus(Long mids[]){ try {
	 * sqlSession = SessionFactory.getSession(); if
	 * (sqlSession.update(messageNamespace + ".updateDownloadFailMessageStatus",
	 * mids) == 0) { logger.info("no message by id:" + mids +
	 * " state to update"); } sqlSession.commit(); } catch (Exception e) { if
	 * (sqlSession != null) { sqlSession.rollback(); }
	 * logger.error("failed to updateDownloadedMessageStatus", e); return false;
	 * } finally { SessionFactory.closeSession(); } return true; }
	 */
	/**
	 * 获得所有需要重新下载的mid.
	 * 
	 * @param intime
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> getDownloadMessages(String intime) {
		List<String> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<String>) sqlSession.selectList(messageNamespace
					+ ".getDownloadMessages", intime);
		} catch (Exception e) {
			logger.error("failed to ", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 根据uid、mid查询id.
	 * 
	 * @param uid
	 * @param mid
	 * @return String
	 */
	public String selectDownloadMessageByUid(Long uid, Long mid) {
		String id = null;
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("uid", uid);
		map.put("mid", mid);
		try {
			sqlSession = SessionFactory.getSession();
			id = (String) sqlSession.selectOne(messageNamespace
					+ ".selectDownloadMessageByUid", map);
		} catch (Exception e) {
			logger.error("failed to selectDownloadMessageByUid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return id;
	}

	/**
	 * 根据mid查询IMEI.
	 * 
	 * @param mid
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectIMEIByMid(Long mid) {
		List<String> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<String>) sqlSession.selectList(messageNamespace
					+ ".selectIMEIByMid", mid);
		} catch (Exception e) {
			logger.error("failed to selectIMEIByMid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 根据uid删除邮件.
	 * 
	 * @param uid
	 * 
	 * @return boolean
	 */
	public boolean deleteDownloadMessageByUid(String uid) {
		try {
			sqlSession = SessionFactory.getSession();
			if (sqlSession.delete(messageNamespace
					+ ".deleteDownloadMessageByUid", uid) == 0) {
				logger.info("no message to deleteDownloadMessageByUid");
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to deleteDownloadMessageByUid", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 根据主键id数组删除邮件.
	 * 
	 * @param ids
	 *            message id
	 * @return boolean
	 */
	public boolean deleteDownloadMessageByids(Long ids[]) {
		try {
			sqlSession = SessionFactory.getSession();
			if (sqlSession.delete(messageNamespace
					+ ".deleteDownloadMessageByids", ids) == 0) {
				logger.info("failed to deleteDownloadMessageByids");
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to deleteDownloadMessageByids", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}
}
