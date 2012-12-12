package com.dreamail.mercury.dal.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_delete_message;

public class DeleteMessageDao {
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory.getLogger(DeleteMessageDao.class);
	
	private static final String messageNamespace = "com.clickoo.mercury.domain.DeleteMessageMapper";

	public long saveDeleteMessage(Clickoo_delete_message deMessage) {
		try {
			logger.info(deMessage.getId() + "");
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(messageNamespace + ".insertDeleteMessage", deMessage);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger
					.error(
							"failed to insert deMessage",
							e);
			return -1;
		} finally {
			SessionFactory.closeSession();
		}
		return deMessage.getId();
	}
	
	@SuppressWarnings("unchecked")
	public List<Clickoo_delete_message> selectDeleteMessageByAccname(String accname) {
		List<Clickoo_delete_message> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_delete_message>) sqlSession.selectList(messageNamespace
					+ ".selectDeleteMessageByAccname", accname);
		} catch (Exception e) {
			logger.error("failed to selectDeleteMessageByAccname", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}
	
	/**
	 * 根据id数组删除邮件.
	 * 
	 * @param ids
	 *            message id
	 * @return boolean
	 */
	public boolean deleteDMessageByDids(Long[] dids) {
		if(dids!=null && dids.length>0){
			try {
				sqlSession = SessionFactory.getSession();
				if (sqlSession.delete(messageNamespace + ".deleteDMessageByDids",
						dids) == 0) {
					logger.info("failed to deleteDMessageByDids");
				}
				sqlSession.commit();
			} catch (Exception e) {
				if (sqlSession != null) {
					sqlSession.rollback();
				}
				logger.error("failed to deleteDMessageByDids", e);
				return false;
			} finally {
				SessionFactory.closeSession();
			}
			return true;
		}
		return false;
	}
	/**
	 * 获得所有需要重新删除的mid.
	 * @param intime
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> getDeleteMessages(String intime){
		List<String> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<String>) sqlSession.selectList(messageNamespace
					+ ".getDeleteMessages",intime);
		} catch (Exception e) {
			logger.error("failed to getDownloadMessages"
					, e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}
}
