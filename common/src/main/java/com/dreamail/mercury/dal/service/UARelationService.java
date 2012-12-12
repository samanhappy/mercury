package com.dreamail.mercury.dal.service;

import java.util.List;

import com.dreamail.jms.MessageSender;
import com.dreamail.mercury.cache.MemCachedManager;
import com.dreamail.mercury.dal.dao.UARelationDao;
import com.dreamail.mercury.pojo.Clickoo_mail_account;

public class UARelationService {

	public final static int USER_SCHEDULE_OFFLINE_STATUS = Integer.parseInt(
			"100000", 2);

	public final static int USER_AUTO_OFFLINE_STATUS = Integer.parseInt(
			"010000", 2);

	public final static int USER_PUSH_OFFLINE_STATUS = Integer.parseInt(
			"001000", 2);

	public final static int ACCOUNT_PUSH_OFFLINE_STATUS = Integer.parseInt(
			"000100", 2);

	public final static int ACCOUNT_SCHEDULE_OFFLINE_STATUS = Integer.parseInt(
			"000010", 2);

	public final static int USER_SCHEDULE_ONLINE_STATUS = Integer.parseInt(
			"011111", 2);

	public final static int USER_AUTO_ONLINE_STATUS = Integer.parseInt(
			"101111", 2);

	public final static int USER_PUSH_ONLINE_STATUS = Integer.parseInt(
			"110111", 2);

	public final static int ACCOUNT_PUSH_ONLINE_STATUS = Integer.parseInt(
			"111011", 2);

	public final static int ACCOUNT_SCHEDULE_ONLINE_STATUS = Integer.parseInt(
			"111101", 2);

	/**
	 * 设置用户自动下线并且发送下线消息.
	 * 
	 * @param uid
	 */
	public void setUserAutoOffline(long uid) {
		List<Clickoo_mail_account> list = new UARelationDao().setOffline(uid,
				USER_AUTO_OFFLINE_STATUS, -1, null);
		MessageSender.sendOfflineMessage(list);
	}

	/**
	 * 设置多用户自动下线并且发送下线消息.
	 * 
	 * @param uids
	 */
	public void setUserAutoOffline(List<String> uids) {
		List<Clickoo_mail_account> list = new UARelationDao()
				.setAutoOfflineUsers(uids, USER_AUTO_OFFLINE_STATUS);
		MessageSender.sendOfflineMessage(list);
	}

	/**
	 * 设置用户自动上线并且发送上线消息.
	 * 
	 * @param uid
	 */
	public void setUserAutoOnline(long uid) {
		List<Clickoo_mail_account> list = new UARelationDao().setOnline(uid,
				USER_AUTO_ONLINE_STATUS, -1, null);
		MessageSender.sendOnlineMessage(list);
		if (list != null && list.size() > 0
				&& MemCachedManager.isMemcachedLive()) {
			MessageSender.sendNewMailNotice(uid);
		}
	}

	/**
	 * 设置用户Push下线并且发送下线消息.
	 * 
	 * @param uid
	 */
	public void setUserPushOffline(long uid) {
		List<Clickoo_mail_account> list = new UARelationDao().setOffline(uid,
				USER_PUSH_OFFLINE_STATUS, -1, null);
		MessageSender.sendOfflineMessage(list);
	}

	/**
	 * 设置用户Push上线并且发送上线消息.
	 * 
	 * @param uid
	 */
	public void setUserPushOnline(long uid) {
		List<Clickoo_mail_account> list = new UARelationDao().setOnline(uid,
				USER_PUSH_ONLINE_STATUS, -1, null);
		MessageSender.sendOnlineMessage(list);
		MessageSender.sendNewMailNotice(uid);
	}

	/**
	 * 设置账号Push下线并且发送下线消息.
	 * 
	 * @param uid
	 * @param aid
	 */
	public void setAccountPushOffline(long uid, long aid) {
		List<Clickoo_mail_account> list = new UARelationDao().setOffline(uid,
				ACCOUNT_PUSH_OFFLINE_STATUS, aid, null);
		MessageSender.sendOfflineMessage(list);
	}

	/**
	 * 设置账号Push上线并且发送上线消息.
	 * 
	 * @param uid
	 * @param aid
	 */
	public void setAccountPushOnline(long uid, long aid) {
		List<Clickoo_mail_account> list = new UARelationDao().setOnline(uid,
				ACCOUNT_PUSH_ONLINE_STATUS, aid, null);
		MessageSender.sendOnlineMessage(list);
		MessageSender.sendNewMailNotice(uid);
	}

	/**
	 * 设置账号Schedule下线并且发送下线消息.
	 * 
	 * @param uid
	 * @param aid
	 */
	public void setAccountScheduleOffline(long uid, long aid) {
		List<Clickoo_mail_account> list = new UARelationDao().setOffline(uid,
				ACCOUNT_SCHEDULE_OFFLINE_STATUS, aid, null);
		MessageSender.sendOfflineMessage(list);
	}

	/**
	 * 设置账号Schedule下线并且发送下线消息.
	 * 
	 * @param uid
	 * @param aid
	 */
	public void setUserScheduleOffline(long uid) {
		List<Clickoo_mail_account> list = new UARelationDao().setOffline(uid,
				USER_SCHEDULE_OFFLINE_STATUS, -1, null);
		MessageSender.sendOfflineMessage(list);
	}

	/**
	 * 设置账号Schedule上线并且发送上线消息.
	 * 
	 * @param uid
	 * @param aid
	 */
	public void setAccountScheduleOnline(long uid, long aid) {
		List<Clickoo_mail_account> list = new UARelationDao().setOnline(uid,
				ACCOUNT_SCHEDULE_ONLINE_STATUS, aid, null);
		MessageSender.sendOnlineMessage(list);
		MessageSender.sendNewMailNotice(uid);
	}

	/**
	 * 设置账号Schedule上线并且发送上线消息.
	 * 
	 * @param uid
	 * @param aid
	 */
	public void setUserScheduleOnline(long uid) {
		List<Clickoo_mail_account> list = new UARelationDao().setOnline(uid,
				USER_SCHEDULE_ONLINE_STATUS, -1, null);
		MessageSender.sendOnlineMessage(list);
		MessageSender.sendNewMailNotice(uid);
	}

}
