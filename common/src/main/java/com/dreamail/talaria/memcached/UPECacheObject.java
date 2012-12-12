package com.dreamail.talaria.memcached;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.dreamail.mercury.dal.dao.SendMessageDao;
import com.dreamail.mercury.dal.dao.UARelationDao;
import com.dreamail.mercury.dal.service.AccountService;

public class UPECacheObject implements Serializable {

	private static final long serialVersionUID = -8844467050317451287L;

	private String uid; // 用户id

	private String CAGNotice; // CAG通知

	private String CAGAccount; // CAG通知账号名

	private boolean isDisable = false; // 用户失效

	private String last_notice_ts;// 客户端上一次收到非普通通知的时间戳

	private String last_notice_state; // 客户端上一次收到非普通通知状态

	private String last_invalid_aid; // 客户端上一次收到的非法aid

	private String last_mailsent_mid; // 客户端上一次收到的邮件发送状态通知mid

	private String last_mailsent_hid; // 客户端上一次收到的邮件发送状态通知hid

	private String timer_status_map;// 定时状态标记

	private String aid_list; // 账号列表

	private String invalid_aid_list; // 非法账号列表

	private String new_msg_flag_map; // 新邮件状态标记

	private String mail_sent_state_map; // 邮件发送状态
	
	private String delete_account_map; //被其他用户注册的账号id

	public String getDelete_account_map() {
		return delete_account_map;
	}

	public void setDelete_account_map(String delete_account_map) {
		this.delete_account_map = delete_account_map;
	}

	public String getLast_invalid_aid() {
		return last_invalid_aid;
	}

	public void setLast_invalid_aid(String lastInvalidAid) {
		last_invalid_aid = lastInvalidAid;
	}

	public String getCAGNotice() {
		return CAGNotice;
	}

	public void setCAGNotice(String cAGNotice) {
		CAGNotice = cAGNotice;
	}

	public String getLast_mailsent_mid() {
		return last_mailsent_mid;
	}

	public void setLast_mailsent_mid(String lastMailsentMid) {
		last_mailsent_mid = lastMailsentMid;
	}

	public String getLast_mailsent_hid() {
		return last_mailsent_hid;
	}

	public void setLast_mailsent_hid(String lastMailsentHid) {
		last_mailsent_hid = lastMailsentHid;
	}

	public boolean isDisable() {
		return isDisable;
	}

	public void setDisable(boolean isDisable) {
		this.isDisable = isDisable;
	}

	public String getCAGAccount() {
		return CAGAccount;
	}

	public void setCAGAccount(String cAGAccount) {
		CAGAccount = cAGAccount;
	}

	public String getNew_msg_flag_map() {
		return new_msg_flag_map;
	}

	public void setNew_msg_flag_map(String newMsgFlagMap) {
		new_msg_flag_map = newMsgFlagMap;
	}

	public String getMail_sent_state_map() {
		return mail_sent_state_map;
	}

	public void setMail_sent_state_map(String mailSentStateMap) {
		mail_sent_state_map = mailSentStateMap;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getLast_notice_ts() {
		return last_notice_ts;
	}

	public void setLast_notice_ts(String lastNoticeTs) {
		last_notice_ts = lastNoticeTs;
	}

	public String getLast_notice_state() {
		return last_notice_state;
	}

	public void setLast_notice_state(String lastNoticeState) {
		last_notice_state = lastNoticeState;
	}

	public String getTimer_status_map() {
		return timer_status_map;
	}

	public void setTimer_status_map(String timerStatusMap) {
		timer_status_map = timerStatusMap;
	}

	/**
	 * 更新账号列表.
	 * 
	 */
	public void updateAidList() {
		List<String> aids = new UARelationDao().selectAllAid(Long
				.parseLong(uid));
		JSONArray array = new JSONArray();
		for (String aid : aids) {
			array.add(aid);
		}
		aid_list = array.toString();
	}

	/**
	 * 得到账号列表.
	 * 
	 * @return
	 */
	public List<String> getAidList() {
		if (aid_list == null) {
			updateAidList();
		}
		JSONArray array = JSONArray.fromObject(aid_list);
		List<String> aidList = new ArrayList<String>();
		for (int i = 0; i < array.size(); i++) {
			aidList.add(array.getString(i));
		}
		return aidList;
	}

	/**
	 * 增加一个账号.
	 * 
	 * @param aid
	 */
	public void addAid(String aid) {
		JSONArray array = null;
		if (aid_list == null) {
			array = new JSONArray();
		} else {
			array = JSONArray.fromObject(aid_list);
		}
		if (!array.contains(aid)) {
			array.add(aid);
		}
		aid_list = array.toString();
	}

	/**
	 * 删除一个账号.
	 * 
	 * @param aid
	 */
	public void removeAid(String aid) {
		if (aid_list != null) {
			JSONArray array = JSONArray.fromObject(aid_list);
			array.remove(aid);
			aid_list = array.toString();
		}

	}

	/**
	 * 得到非法账号列表.
	 * 
	 * @return
	 */
	public List<String> getInvalidAidSet() {
		List<String> aidList = null;
		if (invalid_aid_list != null) {
			JSONArray array = JSONArray.fromObject(invalid_aid_list);
			aidList = new ArrayList<String>();
			for (int i = 0; i < array.size(); i++) {
				aidList.add(array.getString(i));
			}
		}
		return aidList;
	}

	/**
	 * 增加一个非法账号.
	 * 
	 * @param aid
	 */
	public void addInvalidAid(String aid) {
		JSONArray array = null;
		if (invalid_aid_list == null) {
			array = new JSONArray();
		} else {
			array = JSONArray.fromObject(invalid_aid_list);
		}
		if (!array.contains(aid)) {
			array.add(aid);
		}
		invalid_aid_list = array.toString();
	}

	/**
	 * 删除一个非法账号.
	 * 
	 * @param aid
	 */
	public void removeInvalidAid(String aid) {
		if (invalid_aid_list != null) {
			JSONArray array = JSONArray.fromObject(invalid_aid_list);
			array.remove(aid);
			invalid_aid_list = array.toString();
		}

	}

	/**
	 * 从数据库里更新用户的异常账号信息.
	 */
	public void updateInvalidAids() {
		List<String> invalidAids = new AccountService().handleInvalidAids(Long
				.valueOf(this.uid));
		if (invalidAids != null && invalidAids.size() > 0) {
			for (String aid : invalidAids) {
				addInvalidAid(aid);
			}
		}
	}

	/**
	 * 从数据库里更行用户还未处理的邮件发送状态通知.
	 */
	public void updateMailSentNotices() {
		AbstractCacheManager.addMailSentFlag(this, new SendMessageDao()
				.getAllNeedHandleSentMessagesByUid(Long.valueOf(this.uid)));
	}

}
