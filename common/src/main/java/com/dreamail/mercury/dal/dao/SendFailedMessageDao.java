package com.dreamail.mercury.dal.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_send_failed_message;

public class SendFailedMessageDao {
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(SendFailedMessageDao.class);

	private static final String messageNamespace = "com.clickoo.mercury.domain.SendFailedMessageDao";

	/**
	 * 向数据库添加一条Clickoo_send_failed_message记录.
	 * 
	 * @param sMessage
	 * @return long
	 */
	public long saveSendFailedMessage(Clickoo_send_failed_message message) {
		if (message == null) {
			return -1;
		}
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(messageNamespace + ".insertSendFailedMessage",
					message);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to insert Clickoo_send_failed_message", e);
			return -1;
		} finally {
			SessionFactory.closeSession();
		}
		return message.getId();
	}

	/**
	 * 根据mid数组删除邮件.
	 * 
	 * @param ids
	 *            message id
	 * @return boolean
	 */
	public boolean deleteSendFailedMessageByMids(Long[] mids) {
		if (mids != null && mids.length > 0) {
			try {
				sqlSession = SessionFactory.getSession();
				if (sqlSession.delete(messageNamespace
						+ ".deleteSendFailedMessageByMids", mids) == 0) {
					logger.info("failed to deleteSendFailedMessageByMids");
				}
				sqlSession.commit();
			} catch (Exception e) {
				if (sqlSession != null) {
					sqlSession.rollback();
				}
				logger.error("failed to deleteSendFailedMessageByMids", e);
				return false;
			} finally {
				SessionFactory.closeSession();
			}
			return true;
		}
		return false;
	}

	/**
	 * 根据mid删除邮件
	 * 
	 * @param mid
	 * @return
	 */
	public boolean deleteSendFailedMessageByMid(String mid) {
		if (mid != null && !"".equals(mid)) {
			try {
				sqlSession = SessionFactory.getSession();
				if (sqlSession.delete(messageNamespace
						+ ".deleteSendFailedMessageByMid", mid) == 0) {
					logger.info("deleteSendFailedMessageByMid failed :mid ["
							+ mid + "]");
				} else {
					logger.info("deleteSendFailedMessageByMid success :mid ["
							+ mid + "]");
				}
				sqlSession.commit();
			} catch (Exception e) {
				if (sqlSession != null) {
					sqlSession.rollback();
				}
				logger.error("failed to deleteSendFailedMessageByMid", e);
				return false;
			} finally {
				SessionFactory.closeSession();
			}
			return true;
		}
		return false;
	}

	/**
	 * 根据mid取得邮件
	 * 
	 * @param mid
	 * @return
	 */
	public Clickoo_send_failed_message selectSendFailedMessageByMd(String mid) {
		Clickoo_send_failed_message smessage = null;
		try {
			sqlSession = SessionFactory.getSession();
			smessage = (Clickoo_send_failed_message) sqlSession.selectOne(
					messageNamespace + ".selectSendFailedMessageByMid", mid);
		} catch (Exception e) {
			logger.error("failed to selectSendFailedMessageById", e);
		} finally {
			SessionFactory.closeSession();
		}
		return smessage;
	}

	/**
	 * 获取所有的邮件
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_send_failed_message> getAllSendFailedMessage() {
		List<Clickoo_send_failed_message> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_send_failed_message>) sqlSession
					.selectList(messageNamespace + ".getAllSendFailedMessage");
		} catch (Exception e) {
			logger.error("failed to getAllSendFailedMessage", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 更新邮件重新发送次数
	 * 
	 * @param id
	 * @param retrycount
	 * @return
	 */
	public boolean updateSentFailedMessageCount(long id, int retrycount) {
		Clickoo_send_failed_message message = new Clickoo_send_failed_message();
		message.setId(id);
		message.setRetrycount(retrycount);
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(messageNamespace
					+ ".updateSentFailedMessageCount", message);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to updateSentFailedMessageCount", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

}
