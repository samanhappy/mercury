/**
 * 
 */
package com.dreamail.mercury.dal.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.dao.UARelationDao;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.domain.EmailCacheObject;
import com.dreamail.mercury.memcached.EmailCacheManager;
import com.dreamail.mercury.pojo.Clickoo_imap_message;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.Clickoo_user_account;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JsonUtil;

/**
 * @author meng.sun
 * 
 */
public class MessageService {
	private static Logger logger = LoggerFactory
			.getLogger(MessageService.class);

	/**
	 * 插入一条message和对应的正文记录到数据库.
	 * 
	 * @param message
	 *            Clickoo_message
	 * @return true or false
	 */
	public long saveMessage(Clickoo_message message) {
		return new MessageDao().saveMessage(message);
	}

	// 优化
	public List<Clickoo_message> getMessageListByUid(long uid, int status) {
		return new MessageDao().getMessageListByUid(uid);
	}

	/**
	 * 根据id得到邮件.
	 * 
	 * @param id
	 *            message id
	 * @return Clickoo_message
	 */

	public Clickoo_message selectMessageById(Long id) {
		return new MessageDao().selectMessageById(id);
	}

	/**
	 * 更新邮件状态.
	 * 
	 * @param message
	 *            Clickoo_message
	 * @return true or false
	 */
	public boolean updateMessageState(Clickoo_message message) {
		return new MessageDao().updateMessageState(message);
	}

	/**
	 * 得到account所有已经收取的message id.
	 * 
	 * @return id list
	 */
	public List<Clickoo_message> getAllReceivedMessageIdByAccountId(
			long accountId) {
		return new MessageDao().getAllReceivedMessageIdByAccountId(accountId);
	}

	public List<Clickoo_message> getSeqMessages(long uid, String selectDate) {
		List<Clickoo_user_account> list = new UARelationDao()
				.selectAllUARelation(uid);
		List<Clickoo_message> msgList = new ArrayList<Clickoo_message>();
		if (list != null) {
			for (Clickoo_user_account ua : list) {
				List<Clickoo_message> mesList = null;
				mesList = new MessageDao().getNewMessages(ua.getAid(),
						selectDate);
				if (mesList != null) {
					msgList.addAll(mesList);
				}
			}
		}
		return msgList;
	}

	public List<Clickoo_message> getOldMessages(long aid, String selectDate,
			String oldMailNum) {
		List<Clickoo_message> list = new MessageDao().getOldMessages(aid,
				selectDate, oldMailNum);
		if (list != null) {
			logger.info("first time getOldMessages" + list.size());
		}
		return list;
	}

	public List<Clickoo_message> getOldSeqMessages(long uid, long aid,
			String selectDate, String oldMailNum) {
		List<Clickoo_message> list = new MessageDao().getOldSeqMessages(uid,
				aid, selectDate, oldMailNum);
		if (list != null) {
			logger.info("first time getOldSeqMessages" + list.size());
		}
		return list;
	}

	public List<Clickoo_message> getNewMessages(long uid, String selectDate)
			throws ParseException {
		List<Clickoo_user_account> list = new UARelationDao()
				.getNewAccountId(uid);
		List<Clickoo_message> msgList = new ArrayList<Clickoo_message>();
		String sDate = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (list != null && list.size() > 0) {
			for (Clickoo_user_account ua : list) {
				if (selectDate == null
						|| ua.getRegisterDate().after(format.parse(selectDate))) {
					sDate = format.format(ua.getRegisterDate());
				} else {
					sDate = selectDate;
				}
				List<Clickoo_message> mesList = new MessageDao()
						.getNewMessages(ua.getAid(), sDate);
				if (mesList != null) {
					msgList.addAll(mesList);
				}
				if (ua.getStatus() == 2) {
					new UARelationDao().updateStatusByUid(uid, ua.getAid(), 0);
				}
			}
		}
		return msgList;
	}

	public boolean createMessageById(Clickoo_message message) {
		return new MessageDao().createMessageById(message);
	}

	public boolean deleteMessageById(String id) {
		return new MessageDao().deleteMessageById(id);
	}

	public boolean deleteDirty() {
		return new MessageDao().deleteDirty();
	}

	public List<Clickoo_message> selectDownloadMessage(long uid) {
		return new MessageDao().getDownloadMessage(uid);
	}

	public boolean isExitDownloadMessage(long uid) {
		boolean bool = false;
		List<Clickoo_message> list = new MessageDao().getDownloadMessage(uid);
		if (list == null || list.size() == 0) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 调用存储过程查询所有用户要获取的邮件.
	 * 
	 * @param uid
	 * @param limitnum
	 * @param receiveTime
	 * @return
	 */
	public List<Clickoo_message> getAllMessagesByProc(String uid,
			String limitnum, String receiveTime, String rangeday) {
		return new MessageDao().getAllMessagesByProc(uid, limitnum,
				receiveTime, rangeday);
	}

	/**
	 * 根据mid获得emailcache对象
	 * 
	 * @param mid
	 * @return EmailCacheObject
	 */
	public EmailCacheObject produceEmailCacheObject(String mid) {
		EmailCacheObject emailCacheObject = null;
		MessageDao messageDao = new MessageDao();
		Clickoo_message email = messageDao.getEmailById(Long.parseLong(mid));
		String aid = messageDao.selectMessageById(Long.parseLong(mid)).getAid();
		Email sourceemail = JsonUtil.getInstance().parseEmail(email);
		emailCacheObject = JsonUtil.getInstance().parseDatabaseEmail(aid,
				sourceemail);
		return emailCacheObject;
	}

	/**
	 * 在缓存中获取邮件emailcache对象,如果没有就从数据库取，并缓存。
	 * 
	 * @param mid
	 * @return
	 */
	public EmailCacheObject getEmailCacheObject(String mid, boolean isRetrievingBody) {
		EmailCacheObject emailCache = EmailCacheManager.get(mid);
		if (emailCache == null) {
			logger
					.info("mid " + mid
							+ " emailCache is null, in add emailCache");
			emailCache = produceEmailCacheObject(mid);
			EmailCacheManager.addEmail(String.valueOf(mid), emailCache, -1);
		}

		if (isRetrievingBody) {
			// 获取正文后将邮件状态设为已读.
			Clickoo_message message = new Clickoo_message();
			message.setId(Long.valueOf(mid));
			message.setStatus(Constant.MESSAGE_READ_STATUS);
			new MessageDao().updateMessageState(message);
		}
		
		return emailCache;
	}

	/**
	 * 根据账号名得到所有message.
	 * 
	 * @param name
	 * @return List<String>
	 */
	public List<Clickoo_imap_message> getUidsByAccountId(long aid) {
		return new MessageDao().getUidsByAccountId(aid);
	}

	public List<Clickoo_imap_message> getContentByAccountId(long aid,
			String type) {
		if ("SUBJECT".equals(type))
			return new MessageDao().getSubjectByAccountId(aid);
		if ("FLAGS".equals(type))
			return new MessageDao().getFlagsByAccountId(aid);
		return null;
	}

	public static void main(String[] args) {
		Clickoo_message m = new MessageDao()
				.getEmailById(Long.parseLong("416"));
		System.out.println(m.getUuid());
	}
}
