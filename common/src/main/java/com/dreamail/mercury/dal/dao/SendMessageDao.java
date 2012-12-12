package com.dreamail.mercury.dal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_send_message;

public class SendMessageDao {
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(SendMessageDao.class);

	private static final String messageNamespace = "com.clickoo.mercury.domain.SendMessageDao";

	public static void main(String[] args) {
		new UserDao().selectUser(1l);
	}
	
	/**
	 * 向数据库添加一条Clickoo_send_message记录.
	 * 
	 * @param sMessage
	 * @return long
	 */
	public long saveSendMessage(Clickoo_send_message sMessage) {
		if (sMessage == null) {
			return -1;
		}
		try {
			logger.info("saveDownloadMessage id is:" + sMessage.getId() + "");
			sqlSession = SessionFactory.getSession();
			sqlSession
					.insert(messageNamespace + ".insertSendMessage", sMessage);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to insert sMessage", e);
			return -1;
		} finally {
			SessionFactory.closeSession();
		}
		return sMessage.getId();
	}

	/**
	 * 根据mid更新Clickoo_send_message
	 * 
	 * @param mid
	 * @return boolean
	 */
	public boolean updateSendMessage(Clickoo_send_message smessage) {
		try {
			sqlSession = SessionFactory.getSession();
			if (sqlSession.update(messageNamespace + ".updateSendMessage",
					smessage) == 0) {
				logger.info("no message by id:" + smessage.getId()
						+ " state to update");
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to updateSendMessage", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 根据id得到邮件.
	 * 
	 * @param id
	 *            message id
	 * @return Clickoo_message
	 */
	public Clickoo_send_message selectSendMessageById(Long sid) {
		Clickoo_send_message smessage = null;
		try {
			sqlSession = SessionFactory.getSession();
			smessage = (Clickoo_send_message) sqlSession.selectOne(
					messageNamespace + ".selectSendMessageById", sid);
		} catch (Exception e) {
			logger.error("failed to selectMessageById", e);
		} finally {
			SessionFactory.closeSession();
		}
		return smessage;
	}

	/**
	 * 根据id数组删除邮件.
	 * 
	 * @param ids
	 *            message id
	 * @return boolean
	 */
	public boolean deleteSendMessageBySids(Long[] sids) {
		if (sids != null && sids.length > 0) {
			try {
				sqlSession = SessionFactory.getSession();
				if (sqlSession.delete(messageNamespace
						+ ".deleteSendMessageBySids", sids) == 0) {
					logger.info("failed to deleteSendMessageBySids");
				}
				sqlSession.commit();
			} catch (Exception e) {
				if (sqlSession != null) {
					sqlSession.rollback();
				}
				logger.error("failed to deleteSendMessageBySids", e);
				return false;
			} finally {
				SessionFactory.closeSession();
			}
			return true;
		}
		return false;
	}

	/**
	 * 更新发送邮件数据库状态字段.
	 * 
	 * @param mid
	 * @param status
	 * @return
	 */
	public boolean updateSentMessageStatus(long mid, int status) {
		Clickoo_send_message message = new Clickoo_send_message();
		message.setId(mid);
		message.setStatus(status);
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(messageNamespace + ".updateSentMessageStatus",
					message);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to updateSentMessageStatus", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 根据用户id得到所有需要处理的邮件发送状态.
	 * 
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_send_message> getAllNeedHandleSentMessagesByUid(long uid) {
		List<Clickoo_send_message> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_send_message>) sqlSession.selectList(
					messageNamespace + ".getAllNeedHandleSentMessagesByUid",
					uid);
		} catch (Exception e) {
			logger.error("failed to getAllNeedHandleSentMessagesByUid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}
	
	/**
	 * 调用存储过程删除发送邮件.
	 * 
	 * @param aid
	 * @param lasttime
	 * @param maxEmailNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_send_message> getAllSendMessageByProc(
			String lasttime,String aid) {
		List<Clickoo_send_message> list = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("lastTime", lasttime);
		map.put("aid", aid);
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_send_message>) sqlSession.selectList(
					messageNamespace + ".getAllSendMessageByProc", map);
		} catch (Exception e) {
			logger.error("failed to getAllSendMessageByProc", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}
	
	/**
	 * 得到所有可以刪除的发送邮件信息并删除.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_send_message> getAllSendDoneMessage() {
		List<Clickoo_send_message> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_send_message>) sqlSession.selectList(
					messageNamespace + ".getAllSendDoneMessage");
			sqlSession.delete(messageNamespace + ".deleteAllSendDoneMessage");
			sqlSession.commit();
		} catch (Exception e) {
			logger.error("failed to getAllSendDoneMessage", e);
			sqlSession.rollback();
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}
}
