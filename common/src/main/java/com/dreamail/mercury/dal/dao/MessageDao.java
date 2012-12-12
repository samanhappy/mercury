/**
 * 
 */
package com.dreamail.mercury.dal.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_imap_message;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.pojo.Clickoo_message_data;

/**
 * @author meng.sun
 * 
 */
public class MessageDao {

	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory.getLogger(MessageDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static final String messageNamespace = "com.clickoo.mercury.domain.MessageMapper";
	private static final String dataNamespace = "com.clickoo.mercury.domain.DataMapper";
	private static final String attachmentNamespace = "com.clickoo.mercury.domain.AttachmentMapper";

	/**
	 * 插入一条message到数据库.
	 * 
	 * @param message
	 *            Clickoo_message
	 * @return 生成的messageid,失败返回-1.
	 */
	public long saveMessage(Clickoo_message message) {
		try {
			logger.info(message.getId() + "");
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(messageNamespace + ".insertMessage", message);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger
					.error(
							"failed to insert message and body data and attachment list",
							e);
			return -1;
		} finally {
			SessionFactory.closeSession();
		}
		return message.getId();
	}

	/**
	 * 插入一条message和对应的正文记录到数据库.
	 * 
	 * @param message
	 *            Clickoo_message
	 * @return 生成的messageid,失败返回-1.
	 */
	public long saveSystemMessage(Clickoo_message message) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(messageNamespace + ".insertMessage", message);

			message.getMessageData().setId(message.getId());
			sqlSession.insert(dataNamespace + ".insertData", message
					.getMessageData());

			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger
					.error(
							"failed to insert message and body data and attachment list",
							e);
			return -1;
		} finally {
			SessionFactory.closeSession();
		}
		return message.getId();
	}

	/**
	 * 根据accountid得到所有的新邮件list.
	 * 
	 * @param aid
	 *            account id
	 * @return list of Clickoo_message
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_message> getMessageListByAid(String aid) {
		List<Clickoo_message> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_message>) sqlSession.selectList(
					messageNamespace + ".selectMessagesByAid", aid);
		} catch (Exception e) {
			logger.error("failed to getMessageListByAid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 根据uid获取所有新邮件
	 * 
	 * @param uid
	 * @return lwl
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_message> getMessageListByUid(long uid) {
		List<Clickoo_message> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_message>) sqlSession.selectList(
					messageNamespace + ".queryMessagesByUid", uid);
		} catch (Exception e) {
			logger.error("failed to getMessageListByUid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 根据id得到邮件.
	 * 
	 * @param id
	 *            message id
	 * @return Clickoo_message
	 */
	public Clickoo_message selectMessageById(long id) {
		Clickoo_message message = null;
		try {
			sqlSession = SessionFactory.getSession();
			message = (Clickoo_message) sqlSession.selectOne(messageNamespace
					+ ".selectMessageById", id);
		} catch (Exception e) {
			logger.error("failed to selectMessageById", e);
		} finally {
			SessionFactory.closeSession();
		}
		return message;
	}

	/**
	 * 更新邮件状态.
	 * 
	 * @param message
	 *            Clickoo_message
	 * @return true or false
	 */
	public boolean updateMessageState(Clickoo_message message) {
		try {
			sqlSession = SessionFactory.getSession();
			if (sqlSession.update(messageNamespace + ".updateMessageState",
					message) == 0) {
				logger.info("no message by id:" + message.getId()
						+ " state to update");
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to updateMessageState", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 得到account所有已经收取的message id.
	 * 
	 * @return id list
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_message> getAllReceivedMessageIdByAccountId(
			long accountId) {
		List<Clickoo_message> idList = null;
		try {
			sqlSession = SessionFactory.getSession();
			idList = (List<Clickoo_message>) sqlSession.selectList(
					messageNamespace + ".getAllReceivedMessageIdByAccountId",
					accountId);
		} catch (Exception e) {
			logger.error(
					"failed to select all received messages id for account id: "
							+ accountId, e);
		} finally {
			SessionFactory.closeSession();
		}
		return idList;
	}

	/**
	 * 根据id数组删除邮件.
	 * 
	 * @param ids
	 *            message id
	 * @return boolean
	 */
	public boolean deleteMessagesByIds(Long[] ids) {
		try {
			sqlSession = SessionFactory.getSession();
			if (sqlSession
					.delete(messageNamespace + ".deleteMessageByIds", ids) == 0) {
				logger.info("no messages by these ids to delete");
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to deleteMessagesByIds", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	public void deleteMessagesByuuid(List<String> uuids) {
		try {
			sqlSession = SessionFactory.getSession();
			for (String uuid : uuids) {
				sqlSession.delete(messageNamespace + ".deleteMessageByuuids",
						uuid);
			}
			sqlSession.commit();
		} catch (SQLException e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("idle deleteMessage err!!!", e);
		} finally {
			SessionFactory.closeSession();
		}
	}

	/**
	 * 根据aid删除邮件.
	 * 
	 * @param aid
	 *            account id
	 * @return boolean true or false
	 */
	public boolean deleteMessageByAid(long aid) {
		try {

			sqlSession = SessionFactory.getSession();
			sqlSession.delete(messageNamespace + ".deleteMessageByAid", aid);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to deleteMessagesByIds", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 根据账号名和邮件uuid查询邮件id.
	 * 
	 * @param uid
	 * @param uuid
	 * @param accountName
	 * @return id
	 */
	public boolean containsUuid(String uid, String uuid, String accountName) {
		Clickoo_message message = new Clickoo_message();
		String id = null;
		String aid = null;
		if (accountName.startsWith("recent:")) {
			accountName = accountName.substring(7);
		}
		try {
			sqlSession = SessionFactory.getSession();
			aid = String.valueOf(new AccountDao().getAccountByName(accountName)
					.getId());
			message.setAid(aid);
			message.setUuid(uuid);
			id = (String) sqlSession.selectOne(
					messageNamespace + ".selectUuid", message);
			logger.info("this message id is " + id);
		} catch (Exception e) {
			logger.error("failed to selectUuid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return !(id == null);
	}

	/**
	 * 根据账号名查询账号下所有邮件的uuid
	 * 
	 * @param uid
	 * @param accountName
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> getUuidList(String uid, String accountName) {
		List<String> uuidList = null;
		String aid = null;
		if (accountName.startsWith("recent:")) {
			accountName = accountName.substring(7);
		}
		try {
			sqlSession = SessionFactory.getSession();
			aid = String.valueOf(new AccountDao().getAccountByName(accountName)
					.getId());
			uuidList = (List<String>) sqlSession.selectList(messageNamespace
					+ ".getUuidList", aid);
		} catch (Exception e) {
			logger.error(
					"failed to select all received messages id for account name: "
							+ accountName, e);
		} finally {
			SessionFactory.closeSession();
		}
		return uuidList;
	}

	@SuppressWarnings("unchecked")
	public List<Clickoo_message> getUuidListById(long accountId, long id) {
		List<Clickoo_message> uuidList = null;
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("accountId", accountId);
		map.put("id", id);
		try {
			sqlSession = SessionFactory.getSession();
			uuidList = (List<Clickoo_message>) sqlSession.selectList(
					messageNamespace + ".getUuidListById", map);
		} catch (Exception e) {
			logger.error("failed to select all received messages id by id "
					+ id + " for account name: " + accountId, e);
		} finally {
			SessionFactory.closeSession();
		}
		return uuidList;
	}

	/**
	 * 根据邮件发送时间按降序排列查询所有接收的邮件
	 * 
	 * @param accountid
	 * @param uid
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllReceivedMsgIdByAccountId(long accountId) {
		List<String> idList = null;
		try {
			sqlSession = SessionFactory.getSession();
			idList = (List<String>) sqlSession.selectList(messageNamespace
					+ ".getAllReceivedMsgIdByAccountId", accountId);
		} catch (Exception e) {
			logger.error(
					"failed to select all received messages id for account id: "
							+ accountId, e);
		} finally {
			SessionFactory.closeSession();
		}
		return idList;
	}

	@SuppressWarnings("unchecked")
	public List<Clickoo_message> getOldMessages(long aid, String registerDate,
			String oldMailNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aid", aid);
		map.put("registerDate", registerDate);
		map.put("oldMailNum", oldMailNum);
		List<Clickoo_message> msgList = null;
		try {
			sqlSession = SessionFactory.getSession();
			msgList = (List<Clickoo_message>) sqlSession.selectList(
					messageNamespace + ".getOldMessages", map);
		} catch (Exception e) {
			logger.error("failed to select old messages id for account id: "
					+ aid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return msgList;
	}

	@SuppressWarnings("unchecked")
	public List<Clickoo_message> getOldSeqMessages(long uid, long aid,
			String selectDate, String oldMailNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("aid", aid);
		map.put("selectDate", selectDate);
		map.put("oldMailNum", oldMailNum);
		List<Clickoo_message> msgList = null;
		try {
			sqlSession = SessionFactory.getSession();
			msgList = (List<Clickoo_message>) sqlSession.selectList(
					messageNamespace + ".getOldSeqMessages", map);
		} catch (Exception e) {
			logger.error("failed to select old messages id for account id: "
					+ aid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return msgList;
	}

	@SuppressWarnings("unchecked")
	public List<Clickoo_message> getNewMessages(long aid, String selectDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aid", aid);
		map.put("selectDate", selectDate);
		List<Clickoo_message> msgList = null;
		try {
			sqlSession = SessionFactory.getSession();
			msgList = (List<Clickoo_message>) sqlSession.selectList(
					messageNamespace + ".getNewMessages", map);
		} catch (Exception e) {
			logger.error("failed to select new messages id for account id: "
					+ aid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return msgList;
	}

	/**
	 * 更新message保存message子信息
	 * 
	 * @param Clickoo_message
	 *            message
	 * @return
	 */
	public boolean createMessageById(Clickoo_message message) {
		try {
			logger.info("sendtime:" + message.getSendtime());
			sqlSession = SessionFactory.getSession();
			logger.info("----------- insert message:" + message.getId());
			if (sqlSession.update(messageNamespace + ".createMessageById",
					message) == 0) {
				logger.info("no message by id:" + message.getId()
						+ " to update");
			}
			logger.info("----------- insert message end:" + message.getId());
			Clickoo_message_data content = message.getMessageData();
			if (content != null) {
				content.setId(message.getId());
				sqlSession.insert(dataNamespace + ".insertData", message
						.getMessageData());
			}
			List<Clickoo_message_attachment> attachmentList = message
					.getAttachList();
			if (attachmentList != null && attachmentList.size() > 0) {
				for (Clickoo_message_attachment attach : attachmentList) {
					attach.setMid(message.getId());
					sqlSession.update(
							attachmentNamespace + ".insertAttachment", attach);
					List<Clickoo_message_attachment> childList = attach
							.getChildList();
					if (childList != null && childList.size() > 0) {
						for (Clickoo_message_attachment child : childList) {
							if (child.getLength() != 0) {
								child.setParent(attach.getId());
								// child.setId(IDGenerator.nextID(IDTypes.ATTACHEMENT_ID));
								child.setMid(message.getId());
								sqlSession.update(attachmentNamespace
										+ ".insertAttachment", child);
							}
						}
					}
				}
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to selectMessageById", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 根据id查询邮件正文.
	 * 
	 * @param id
	 *            attachment id
	 * @return Clickoo_message_data
	 */
	public long getNextMessageId(String aid,String uuid) {
		Clickoo_message message = new Clickoo_message();
		message.setAid(aid);
		message.setUuid(uuid);
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(messageNamespace + ".getNextMessageId", message);
			sqlSession.commit();
		} catch (Exception e) {
			logger.error("failed to getNextMessageId", e);
			sqlSession.rollback();
			return -1;
		} finally {
			SessionFactory.closeSession();
		}
		return message.getId();
	}

	public String getIdByUuid(String aid, String uuid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("aid", aid);
		map.put("uuid", uuid);
		String id = null;
		try {
			sqlSession = SessionFactory.getSession();
			id = (String) sqlSession.selectOne(messageNamespace
					+ ".getIdByUuid", map);
		} catch (Exception e) {
			logger.error("failed to select id for account id: " + aid
					+ " by uuid " + uuid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return id;
	}

	/**
	 * 根据uuid和aid获取邮件标记flags
	 * 
	 * @param aid
	 * @param uuid
	 * @return
	 */
	public String getFlagsByUuid(String aid, String uuid) {
		return null;
	}

	public boolean deleteMessageById(String id) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.delete(messageNamespace + ".deleteMessageById", id);
			sqlSession.commit();
			return true;
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to deleteMessageById");
			return false;
		} finally {
			SessionFactory.closeSession();
		}
	}

	public boolean deleteDirty() {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.delete(messageNamespace + ".deleteDirty");
			sqlSession.commit();
			return true;
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to deleteDirty");
			return false;
		} finally {
			SessionFactory.closeSession();
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getDeviceByMid(String mid) {
		List<String> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<String>) sqlSession.selectList(messageNamespace
					+ ".getDeviceByMid", mid);
		} catch (Exception e) {
			logger.error("failed to getDeviceByMid ", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Clickoo_message> getMessagesByIds(String[] mids) {
		List<Clickoo_message> messages = null;
		Long[] ids = new Long[mids.length];
		for (int i = 0; i < mids.length; i++) {
			ids[i] = Long.parseLong(mids[i]);
		}
		try {
			sqlSession = SessionFactory.getSession();
			messages = (List<Clickoo_message>) sqlSession.selectList(
					messageNamespace + ".getMessagesByIds", ids);
		} catch (Exception e) {
			logger.error("failed to getMessagesByIds", e);
		} finally {
			SessionFactory.closeSession();
		}
		return messages;
	}

	@SuppressWarnings("unchecked")
	public List<Clickoo_message> getDownloadMessage(long uid) {
		List<Clickoo_message> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_message>) sqlSession.selectList(
					messageNamespace + ".getDownloadMessage", uid);
		} catch (Exception e) {
			logger.error("failed to getDownloadMessage", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 调用存储过程查询所有用户要获取的邮件.
	 * 
	 * @param uid
	 * @param limitnum
	 * @param receiveTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_message> getAllMessagesByProc(String uid,
			String limitnum, String receiveTime, String rangeday) {
		List<Clickoo_message> list = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", uid);
		map.put("limitnum", limitnum);
		map.put("receiveTime", receiveTime);
		map.put("rangeday", rangeday);
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_message>) sqlSession.selectList(
					messageNamespace + ".getAllMessagesByProc", map);
		} catch (Exception e) {
			logger.error("failed to getDownloadMessage", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 根据uid得到用户.
	 * 
	 * @param uid
	 *            user id
	 * @return Clickoo_user
	 */
	public Clickoo_message getEmailById(long mid) {
		Clickoo_message clickoo_message = null;
		try {
			sqlSession = SessionFactory.getSession();
			clickoo_message = (Clickoo_message) sqlSession.selectOne(
					"com.clickoo.mercury.domail.MessageMapper.getEmailById",
					mid);
		} catch (SQLException e) {
			logger.error("failed to get user by id", e);
		} finally {
			SessionFactory.closeSession();
		}
		return clickoo_message;
	}

	/**
	 * 调用存储过程删除邮件.
	 * 
	 * @param aid
	 * @param lasttime
	 * @param maxEmailNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_message_attachment> getAllAttachmentByProc(String aid,
			String lasttime, String maxEmailNum, String validdatemax) {
		List<Clickoo_message_attachment> list = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("aid", aid);
		map.put("lastTime", lasttime);
		map.put("maxEmailNum", maxEmailNum);
		map.put("validdatemax", validdatemax);
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_message_attachment>) sqlSession.selectList(
					messageNamespace + ".getAllAttachmentByProc", map);
		} catch (Exception e) {
			logger.error("failed to getAllAttachmentByProc", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 根据账号名得到所有imap_message 只含uid.
	 * 
	 * @param name
	 * @return List<String>
	 */
	public List<Clickoo_imap_message> getUidsByAccountId(long aid) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Clickoo_imap_message> getSubjectByAccountId(long aid) {
		List<Clickoo_imap_message> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_imap_message>) sqlSession.selectList(
					messageNamespace + ".getSubjectByAccountId", aid);
		} catch (Exception e) {
			logger.error("failed to getSubjectByAccountId", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	public List<Clickoo_imap_message> getFlagsByAccountId(long aid) {
		return null;
	}

	/**
	 * 根据uuid和aid获取邮件
	 * 
	 * @param uuid
	 * @param aid
	 * @return
	 */
	public Clickoo_message getMsgByUuidAid(String uuid, String aid) {
		Clickoo_message message = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("uuid", uuid);
		map.put("aid", aid);
		logger.info("uuid------------" + uuid + "----------aid----------------"
				+ aid);
		try {
			sqlSession = SessionFactory.getSession();
			message = (Clickoo_message) sqlSession
					.selectOne(
							"com.clickoo.mercury.domail.MessageMapper.getMsgByUuidAndAid",
							map);
		} catch (SQLException e) {
			logger.error("failed to get email by uuid and aid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return message;
	}

	public void updateFlagsByUuid(Clickoo_message message) {
	}

	public Clickoo_message getMessageCountByAID(long aid) {
		return null;
	}

	public Clickoo_message getMaxUUIDByAID(long aid) {
		Clickoo_message message = null;
		try {
			sqlSession = SessionFactory.getSession();
			message = (Clickoo_message) sqlSession.selectOne(messageNamespace
					+ ".getMessageCountByAID", aid);
			long maxuuid = Long.parseLong(sqlSession.selectOne(
					messageNamespace + ".getMaxuuid", aid).toString());
			message.setMaxUUid(maxuuid + 1);
		} catch (Exception e) {
			logger.error("failed to getIdsByAccountName", e);
		} finally {
			SessionFactory.closeSession();
		}
		return message;
	}

	/**
	 * 根据邮件ID查询intime.
	 * 
	 * @param mid
	 * @return
	 */
	public Date getIntimeByMessageId(String mid) {
		Date intime = null;
		try {
			sqlSession = SessionFactory.getSession();
			Object obj = sqlSession.selectOne(messageNamespace
					+ ".getIntimeByMessageId", mid);
			if (obj != null) {
				intime = (Date) obj;
			}
		} catch (Exception e) {
			logger.error("failed to getIntimeByMessageId", e);
		} finally {
			SessionFactory.closeSession();
		}
		return intime;
	}

	/**
	 * 根据aid删除邮件并保留10封.
	 * 
	 * @param aid
	 *            account id
	 * @return boolean true or false
	 */
	public boolean deleteMessageLimitByAid(long aid) {
		try {

			sqlSession = SessionFactory.getSession();
			sqlSession.delete(messageNamespace + ".deleteMessageLimitByAid",
					aid);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to deleteMessageLimitByAid", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 当客户端提交删除邮件的请求时，在upe模块先设置待删除标记.
	 * 
	 * @param ids
	 * @return
	 */
	public boolean setDeleteMessageSigns(List<String> ids) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(messageNamespace + ".setDeleteMessageSigns", ids);
			sqlSession.commit();
		} catch (SQLException e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to setDeleteMessageSigns", e);
			return false;
		}
		return true;
	}

	/**
	 * 获取待删除的邮件的所有附件.
	 * 
	 * @param deleteMessageStatus
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_message_attachment> getToDeleteAttachments(
			int deleteMessageStatus) {
		List<Clickoo_message_attachment> attList = null;
		try {
			sqlSession = SessionFactory.getSession();
			attList = (List<Clickoo_message_attachment>) sqlSession.selectList(
					messageNamespace + ".getToDeleteAttachments",
					deleteMessageStatus);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("failed to getToDeleteAttachments", e);
		}
		return attList;
	}
	/**
	 * 删除所有待删除的邮件的相关db数据.
	 * @param deleteMessageStatus
	 * @return
	 */
	public boolean deleteToDeleteMessages(int deleteMessageStatus){
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.delete(messageNamespace + ".deleteToDeleteMessages",
					deleteMessageStatus);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to deleteToDeleteMessages", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}
}
