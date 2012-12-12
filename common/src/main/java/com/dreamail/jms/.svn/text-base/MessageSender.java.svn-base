package com.dreamail.jms;

import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.UARelationDao;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_sender_filter;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.JMSTypes;

public class MessageSender {

	private static Logger logger = LoggerFactory.getLogger(MessageSender.class);

	/**
	 * 发送下线消息.
	 * 
	 * @param list
	 */
	public static void sendOfflineMessage(List<Clickoo_mail_account> list) {
		if (list != null && list.size() > 0) {
			for (Clickoo_mail_account account : list) {
				sendOfflineMessage(account.getId(), account.getType(),
						account.getInCert(), JMSConstans.JMS_OFFLINE_VALUE);
			}
		} else {
			logger.info("there is no offline aid");
		}
	}

	/**
	 * 发送上线消息.
	 * 
	 * @param list
	 */
	public static void sendOnlineMessage(List<Clickoo_mail_account> list) {
		if (list != null && list.size() > 0) {
			for (Clickoo_mail_account account : list) {
				sendOnlineMessage(account.getId(), account.getType(),
						account.getInCert(), JMSConstans.JMS_ONLINE_VALUE);
			}
		} else {
			logger.info("there is no online aid");
		}
	}

	/**
	 * 发送注销消息.
	 * 
	 * @param list
	 */
	public static void sendDeletelineMessage(List<Clickoo_mail_account> list) {
		if (list != null && list.size() > 0) {
			for (Clickoo_mail_account account : list) {
				sendOfflineMessage(account.getId(), account.getType(),
						account.getInCert(), JMSConstans.JMS_DELETELINE_VALUE);
			}
		} else {
			logger.info("there is no offline aid");
		}
	}

	/**
	 * 发送注销消息.
	 * 
	 * @param list
	 */
	public static void sendDeletelineMessage(Clickoo_mail_account account) {
		sendOfflineMessage(account.getId(), account.getType(),
				account.getInCert(), JMSConstans.JMS_DELETELINE_VALUE);
	}

	/**
	 * 发送下线消息.
	 * 
	 * @param list
	 */
	public static void sendOfflineMessage(Clickoo_mail_account account) {
		sendOfflineMessage(account.getId(), account.getType(),
				account.getInCert(), JMSConstans.JMS_OFFLINE_VALUE);
	}

	/**
	 * 发送注册消息.
	 * 
	 * @param list
	 */
	public static void sendAddlineMessage(List<Clickoo_mail_account> list) {
		if (list != null && list.size() > 0) {
			for (Clickoo_mail_account account : list) {
				sendOnlineMessage(account.getId(), account.getType(),
						account.getInCert(), JMSConstans.JMS_ADDLINE_VALUE);
			}
		} else {
			logger.info("there is no offline aid");
		}
	}

	/**
	 * 发送修改消息.
	 * 
	 * @param list
	 */
	public static void sendModifylineMessage(Clickoo_mail_account account) {
		sendModifyMessage(account.getId(), account.getType(),
				account.getInCert(), JMSConstans.JMS_MODIFYLINE_VALUE);
	}

	/**
	 * 针对YahooSNP和IMAP-IDLE发送收取老邮件消息.
	 * 
	 * @param list
	 */
	public static void sendReceiveOldMailsOnlineMessage(
			List<Clickoo_mail_account> list) {
		if (list != null && list.size() > 0) {
			for (Clickoo_mail_account account : list) {
				if (account.getId() == 0) {
					logger.error("aid is 0");
					continue;
				}
				/**
				 * 暂时只处理yahooSNP账号
				 */
				if (account.getType() == Constant.ACCOUNT_YAHOOSNP_TYPE) {

					JSONObject json = new JSONObject();
					json.put(JMSConstans.JMS_TYPE_KEY,
							JMSConstans.JMS_RECEIVE_OLDEMAIL_TYPE);
					json.put(JMSConstans.JMS_AID_KEY, account.getId());
					JmsSender.sendQueueMsg(json.toString(),
							JMSTypes.YAHOO_QUEUE);
				} else {
					logger.info("account is common type, do nothing");
				}
			}
			logger.info("send receive old mail message...");
		} else {
			logger.info("there is no offline aid");
		}
	}

	/**
	 * 发送下线消息.
	 * 
	 * @param aidList
	 */
	private static void sendOfflineMessage(long aid, int type, String incert,
			String lineValue) {
		if (aid != 0) {
			String jmsMessage = null;
			if (Constant.ACCOUNT_YAHOOSNP_TYPE == type) {
				JSONObject json = new JSONObject();
				json.put(JMSConstans.JMS_TYPE_KEY,
						JMSConstans.JMS_YAHOOSNP_TYPE);
				json.put(JMSConstans.JMS_AID_KEY, aid);
				json.put(JMSConstans.JMS_LINE_KEY, lineValue);
				// 针对yahooSNP的注销同时发送用户名和密码
				if (lineValue.equals(JMSConstans.JMS_DELETELINE_VALUE)
						&& incert != null) {
					JSONObject incert_json = JSONObject.fromObject(incert);
					json.put(JMSConstans.JMS_LOGINID_KEY,
							incert_json.getString(JMSConstans.JMS_LOGINID_KEY));
					if (json.containsKey(JMSConstans.JMS_PASSWORD_KEY)) {
						json.put(JMSConstans.JMS_PASSWORD_KEY, incert_json
								.getString(JMSConstans.JMS_PASSWORD_KEY));
					} else if (json.containsKey(JMSConstans.JMS_TOKEN_KEY)) {
						json.put(JMSConstans.JMS_TOKEN_KEY, incert_json
								.getString(JMSConstans.JMS_TOKEN_KEY));
					}
				}
				jmsMessage = json.toString();
				JmsSender.sendQueueMsg(jmsMessage, JMSTypes.YAHOO_SNP_QUEUE);
			} 
/*			else if (Constant.ACCOUNT_IMAPIDLE_TYPE == type) {
				JSONObject json = new JSONObject();
				json.put(JMSConstans.JMS_TYPE_KEY,
						JMSConstans.JMS_IMAP_IDLE_TYPE);
				json.put(JMSConstans.JMS_AID_KEY, aid);
				json.put(JMSConstans.JMS_LINE_KEY, lineValue);
				jmsMessage = json.toString();
				JmsSender.sendQueueMsg(jmsMessage, JMSTypes.IDLE_QUEUE);
			} */
			else {
				JSONObject json = new JSONObject();
				json.put(JMSConstans.JMS_TYPE_KEY,
						JMSConstans.JMS_ACCOUNT_OFF_LINE);
				json.put(JMSConstans.JMS_AID_KEY, aid);
				json.put(JMSConstans.JMS_ACCOUNT_TYPE_KEY, type);
				jmsMessage = json.toString();
				JmsSender.sendTopicMsg(jmsMessage, JMSTypes.CACHEACCOUNT_TOPIC);
			}
		} else {
			logger.error("aid is 0");
		}
	}
	
	
	public static void sendSetSenderFilter(Clickoo_user user){
		sendUserModify(user,JMSConstans.JMS_USER_SET_FILTER);
	}
	
	public static void sendModifySenderFilter(String value,Clickoo_sender_filter senderFilter,String uid){
		if(senderFilter!=null){
			JSONObject json = new JSONObject();
			json.put(JMSConstans.JMS_TYPE_KEY,value);
			json.put(JMSConstans.JMS_UID_KEY, uid);
			json.put(JMSConstans.JMS_USER_FILTER_SIGN, senderFilter.getSign());
			json.put(JMSConstans.JMS_USER_FILTER_NAME, senderFilter.getName());
			String jmsMessage = json.toString();
			JmsSender.sendTopicMsg(jmsMessage, JMSTypes.ACCOUNT_PROPERTY_MODIFY);
		}
	}
	
	/**
	 * 发送修改消息.
	 * 
	 * @param list
	 */
	public static void sendModifyDeviceMessage(Clickoo_user user) {
		sendUserModify(user,JMSConstans.JMS_USER_DEVICE_MODIFY);
	}
	/**
	 * 
	 * @param user
	 * @param value
	 */
	private static void sendUserModify(Clickoo_user user,String value){
		if(user!=null){
			if(JMSConstans.JMS_USER_DEVICE_MODIFY.equals(value)){
				JSONObject json = new JSONObject();
				json.put(JMSConstans.JMS_TYPE_KEY,
						JMSConstans.JMS_USER_DEVICE_MODIFY);
				json.put(JMSConstans.JMS_UID_KEY, user.getUid());
//				json.put(JMSConstans.JMS_USER_DEVICE, user.getDevicemodel());
				String jmsMessage = json.toString();
				JmsSender.sendTopicMsg(jmsMessage, JMSTypes.ACCOUNT_PROPERTY_MODIFY);
			}else if(JMSConstans.JMS_USER_SET_FILTER.equals(value)){
				JSONObject json = new JSONObject();
				json.put(JMSConstans.JMS_TYPE_KEY,
						JMSConstans.JMS_USER_SET_FILTER);
				json.put(JMSConstans.JMS_USER_FILTER_SIGN, user.getSign());
				json.put(JMSConstans.JMS_UID_KEY, user.getUid());
				String jmsMessage = json.toString();
				JmsSender.sendTopicMsg(jmsMessage, JMSTypes.ACCOUNT_PROPERTY_MODIFY);
			}
		}
	}
	
	public static void sendYahooAccount2SNPMessage(long aid, int type, String incert,
			String lineValue) {
		sendOnlineMessage(aid, type, incert,lineValue);
	}
	
	/**
	 * 发送上线消息.
	 * 
	 * @param aidList
	 */
	private static void sendOnlineMessage(long aid, int type, String incert,
			String lineValue) {
		if (aid != 0 && incert != null) {
			String jmsMessage = null;
			if (Constant.ACCOUNT_YAHOOSNP_TYPE == type) {
				JSONObject incert_json = JSONObject.fromObject(incert);
				JSONObject json = new JSONObject();
				json.put(JMSConstans.JMS_TYPE_KEY,
						JMSConstans.JMS_YAHOOSNP_TYPE);
				json.put(JMSConstans.JMS_AID_KEY, aid);
				json.put(JMSConstans.JMS_LINE_KEY, lineValue);
				json.put(JMSConstans.JMS_LOGINID_KEY,
						incert_json.getString(JMSConstans.JMS_LOGINID_KEY));
				if (incert_json.containsKey(Constant.PWD)) {
					json.put(JMSConstans.JMS_PASSWORD_KEY,
							incert_json.getString(Constant.PWD));
				} else if (incert_json.containsKey(Constant.TOKEN)
						&& incert_json.containsKey(Constant.PTID)) {
					json.put(JMSConstans.JMS_TOKEN_KEY,
							incert_json.getString(Constant.TOKEN));
					json.put(JMSConstans.JMS_PTID_KEY,
							incert_json.getString(Constant.PTID));
				} else {
					logger.error("error account info");
					return;
				}
				jmsMessage = json.toString();
				JmsSender.sendQueueMsg(jmsMessage, JMSTypes.YAHOO_SNP_QUEUE);
			} else if (Constant.ACCOUNT_IMAPIDLE_TYPE == type) {
				JSONObject incert_json = JSONObject.fromObject(incert);
				JSONObject json = new JSONObject();
				json.put(JMSConstans.JMS_TYPE_KEY,
						JMSConstans.JMS_IMAP_IDLE_TYPE);
				json.put(JMSConstans.JMS_AID_KEY, aid);
				json.put(JMSConstans.JMS_LINE_KEY, lineValue);
				json.put(JMSConstans.JMS_LOGINID_KEY,
						incert_json.getString(JMSConstans.JMS_LOGINID_KEY));
				json.put(JMSConstans.JMS_PASSWORD_KEY,
						incert_json.getString(JMSConstans.JMS_PASSWORD_KEY));
				jmsMessage = json.toString();
				JmsSender.sendQueueMsg(jmsMessage, JMSTypes.IDLE_QUEUE);
			} else {
				if (JMSConstans.JMS_ADDLINE_VALUE.equals(lineValue)
					|| JMSConstans.JMS_ONLINE_VALUE.equals(lineValue)) {
				JSONObject json = new JSONObject();
				json.put(JMSConstans.JMS_TYPE_KEY,
						JMSConstans.JMS_ACCOUNT_ON_LINE);
				json.put(JMSConstans.JMS_AID_KEY, aid);
				json.put(JMSConstans.JMS_ACCOUNT_TYPE_KEY, type);
				jmsMessage = json.toString();
				JmsSender.sendTopicMsg(jmsMessage, JMSTypes.CACHEACCOUNT_TOPIC);
			}
			}
		}else {
			logger.error("aid is 0 or incert is null");
		}
	}
	
	private static void sendModifyMessage(long aid, int type, String incert,String lineValue) {
		String jmsMessage = null;
		if(JMSConstans.JMS_MODIFYLINE_VALUE.equals(lineValue)){
			JSONObject json = new JSONObject();
			JSONObject incert_json = JSONObject.fromObject(incert);
			json.put(JMSConstans.JMS_TYPE_KEY,
				JMSConstans.JMS_MODIFYLINE_VALUE);
			json.put(JMSConstans.JMS_AID_KEY, aid);
			json.put(JMSConstans.JMS_ACCOUNT_TYPE_KEY, type);
			json.put(JMSConstans.JMS_LOGINID_KEY,
				incert_json.getString(JMSConstans.JMS_LOGINID_KEY));
			if (incert_json.containsKey(JMSConstans.JMS_PASSWORD_KEY)){
				json.put(JMSConstans.JMS_PASSWORD_KEY,
						incert_json.getString(JMSConstans.JMS_PASSWORD_KEY));
			}
			if (incert_json.containsKey(JMSConstans.JMS_TOKEN_KEY)){
				json.put(JMSConstans.JMS_TOKEN_KEY,
						incert_json.getString(JMSConstans.JMS_TOKEN_KEY));
			}
			if (incert_json.containsKey(JMSConstans.JMS_PTID_KEY)){
				json.put(JMSConstans.JMS_PTID_KEY,
						incert_json.getString(JMSConstans.JMS_PTID_KEY));
			}
			jmsMessage = json.toString();
			JmsSender.sendTopicMsg(jmsMessage, JMSTypes.ACCOUNT_PROPERTY_MODIFY);
		}
	}

	/**
	 * 发送topic消息给UPE.
	 * 
	 * @param message
	 */
	public static void sendJMSTopicMessage2UPE(String message) {
		logger.info("send jms topic message:" + message);
		try {
			JmsSender.sendTopicMsg(message, JMSTypes.CAG_TOPIC);
		} catch (Exception e) {
			logger.error("failed to send jms topic message:", e);
		}
	}

	/**
	 * 发送topic消息给任务工厂.
	 * 
	 * @param message
	 */
	public static void sendJMSTopicMessage2TaskFactory(String message) {
		try {
			JmsSender.sendTopicMsg(message, JMSTypes.CACHEAUPDATE_TOPIC);
		} catch (Exception e) {
			logger.error("failed to send jms topic message:", e);
		}
	}

	/**
	 * 发送CAG Jms消息.
	 * 
	 * @param message
	 */
	public static void sendCAGResponseMessage(String message) {
		try {
			JmsSender.sendTopicMsg(message, JMSTypes.CAG_AUTH_TOPIC);
		} catch (Exception e) {
			logger.error("failed to send jms queue message:", e);
		}
	}

	/**
	 * 发送IM Jms消息.
	 * 
	 * @param message
	 */
	public static void sendIMQueueMessage(String message) {
		try {
			JmsSender.sendQueueMsg(message, JMSTypes.IM_QUEUE);
		} catch (Exception e) {
			logger.error("failed to send jms queue message:", e);
		}
	}

	/**
	 * 发送邮件发送状态通知.
	 * 
	 * @param uid
	 * @param messageid
	 * @param result
	 * @param hid
	 */
	public static void sendMsgToUPE(String uid, String messageid,
			String result, String hid) {
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_SENDMAIL_TYPE);
		json.put(JMSConstans.JMS_UID_KEY, uid);
		json.put(JMSConstans.JMS_MID_KEY, messageid);
		json.put(JMSConstans.JMS_SENDMAIL_STATE_KEY, result);
		json.put(JMSConstans.JMS_HID_KEY, hid);
		String message = json.toString();
		JmsSender.sendTopicMsg(message, JMSTypes.MESSAGE_TOPIC);
		logger.info("send sendmail message to UPE:" + message);
	}

	/**
	 * 发送更新用户时间偏移量Topic消息.
	 * 
	 * @param uid
	 * @param offset
	 */
	public static void sendUpdateOffsetMessage(String uid, long offset,
			String IMEI) {

		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY,
				JMSConstans.JMS_UPDATE_USERTS_OFFSET_TYPE);
		json.put(JMSConstans.JMS_UID_KEY, uid);
		json.put(JMSConstans.JMS_IMEI_KEY, IMEI);
		json.put(JMSConstans.JMS_OFFSET_KEY, String.valueOf(offset));

		JmsSender.sendTopicMsg(json.toString(), JMSTypes.TIMER_TOPIC);
		logger.info("send update offset message:" + json.toString());
	}

	/**
	 * 发送添加账号消息.
	 * 
	 * @param list
	 */
	public static void sendAddAccountMessage(String uid,
			List<Clickoo_mail_account> list) {
		if (list != null && list.size() > 0) {
			for (Clickoo_mail_account account : list) {
				sendAddAccountMessage(uid, String.valueOf(account.getId()));
			}
		} else {
			logger.info("there is no added aid");
		}
	}

	/**
	 * 账号注册时通知其他模块.
	 * 
	 * @param list
	 */
	public static void sendRegisterMessage2OtherModules(String uid,
			List<Clickoo_mail_account> list) {
		if (list != null && list.size() > 0) {
			for (Clickoo_mail_account account : list) {

				if (!new UARelationDao().isAccountOnline(Long.valueOf(uid),
						account.getId())) {
					// 发送添加账号消息给任务工厂和YahooSNP
					sendOnlineMessage(account.getId(), account.getType(),
							account.getInCert(), JMSConstans.JMS_ADDLINE_VALUE);
				} else {
					logger.info("account is already online, do nothing");
				}

				// 如果是yahoo账号，发送收老邮件消息
				if (account.getType() == Constant.ACCOUNT_YAHOOSNP_TYPE) {
					JSONObject json = new JSONObject();
					json.put(JMSConstans.JMS_TYPE_KEY,
							JMSConstans.JMS_RECEIVE_OLDEMAIL_TYPE);
					json.put(JMSConstans.JMS_AID_KEY, account.getId());
					json.put(JMSConstans.JMS_ACCOUNT_TYPE_KEY,
							Constant.ACCOUNT_YAHOOSNP_TYPE);
					JmsSender.sendQueueMsg(json.toString(),
							JMSTypes.YAHOO_QUEUE);
				}

				// 发送增加账号消息给UPE
				sendAddAccountMessage(uid, String.valueOf(account.getId()));
			}
		} else {
			logger.info("there is no account to handle");
		}
	}

	/**
	 * 发送添加账号消息.
	 * 
	 * @param list
	 */
	public static void sendAddAccountMessage(String uid, String aid) {
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_ADD_ACCOUNT_TYPE);
		json.put(JMSConstans.JMS_UID_KEY, uid);
		json.put(JMSConstans.JMS_AID_KEY, aid);
		JmsSender.sendQueueMsg(json.toString(), JMSTypes.TIMER_QUEUE);
	}
	/**
	 * 发送增加账号信息给任务工厂.
	 * @param uid
	 * @param aid
	 */
	public static void sendAddAccountMessage2Task(int type, String aid) {
		
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_ADD_ACCOUNT_TYPE);
		json.put(JMSConstans.JMS_AID_KEY, aid);
		json.put(JMSConstans.JMS_ACCOUNT_TYPE_KEY, type);
		JmsSender.sendTopicMsg(json.toString(), JMSTypes.CACHEACCOUNT_TOPIC);
		
//		CacheAccountMessage account = new CacheAccountMessage();
//		account.setState(Constant.ACCOUNT_ADD);
//		account.setAid(aid);
//		account.setType(type);
//		JmsSender.sendTopicMsg(account, JMSTypes.CACHEACCOUNT_TOPIC);
	}

	/**
	 * 发送删除账号消息.
	 * 
	 * @param list
	 */
	public static void sendRemoveAccountMessage(String uid, String aid) {
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_REMOVE_ACCOUNT_TYPE);
		json.put(JMSConstans.JMS_UID_KEY, uid);
		json.put(JMSConstans.JMS_AID_KEY, aid);

		JmsSender.sendQueueMsg(json.toString(), JMSTypes.TIMER_QUEUE);

		JmsSender.sendTopicMsg(json.toString(), JMSTypes.TIMER_TOPIC);
	}

	/**
	 * 发送Disable用户消息.
	 * 
	 * @param list
	 */
	public static void sendDisableUserMessage(String uid) {
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_DISABLE_USER_TYPE);
		json.put(JMSConstans.JMS_UID_KEY, uid);
		JmsSender.sendQueueMsg(json.toString(), JMSTypes.TIMER_QUEUE);
	}

	/**
	 * 发送Enable用户消息.
	 * 
	 * @param list
	 */
	public static void sendEnableUserMessage(String uid) {
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_ENABLE_USER_TYPE);
		json.put(JMSConstans.JMS_UID_KEY, uid);
		JmsSender.sendQueueMsg(json.toString(), JMSTypes.TIMER_QUEUE);
	}

	/**
	 * 发送新邮件通知.
	 * 
	 * @param uid
	 * @param aid
	 */
	public static void sendNewMailNotice(long uid) {
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_NEWMAIL_TYPE);
		json.put(JMSConstans.JMS_UID_KEY, String.valueOf(uid));
		JmsSender.sendTopicMsg(json.toString(), JMSTypes.MESSAGE_TOPIC);
	}
	
	public static void sendDeleteAccount(String uid, String aid) {
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_DELETE_ACCOUNT_TYPE);
		json.put(JMSConstans.JMS_UID_KEY, String.valueOf(uid));
		json.put(JMSConstans.JMS_AID_KEY, aid);
		logger.info("send delete msg:"+json.toString());
		JmsSender.sendTopicMsg(json.toString(), JMSTypes.MESSAGE_TOPIC);
	}
}
