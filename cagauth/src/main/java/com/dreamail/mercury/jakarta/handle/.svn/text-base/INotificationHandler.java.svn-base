package com.dreamail.mercury.jakarta.handle;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.MessageSender;
import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.cag.COSSettingsObject;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.util.CAGConstants;
import com.dreamail.mercury.util.JMSConstans;

public abstract class INotificationHandler {

	protected static Logger logger = LoggerFactory.getLogger(INotificationHandler.class);
	
	protected CAGParserObject CAGObject;
	
	protected String uid;

	protected INotificationHandler(CAGParserObject CAGObject) {
		this.CAGObject = CAGObject;
	}
	/**
	 * 抽象处理方法.
	 * @return
	 */
	public abstract boolean handle();
	
	/**
	 * 判断用户等级名合法性.
	 * 
	 * @param roleTitle
	 * @return
	 */
	protected boolean validateRoleTitle(String roleTitle) {
		if (CAGConstants.COS_TITLE_DISABLE.equals(roleTitle)
				|| CAGConstants.COS_TITLE_FREE.equals(roleTitle)
				|| CAGConstants.COS_TITLE_CUSTOMER.equals(roleTitle)
				|| CAGConstants.COS_TITLE_CUSTOMER_PLUS.equals(roleTitle)
				|| CAGConstants.COS_TITLE_PREMIUM.equals(roleTitle)
				|| CAGConstants.COS_TITLE_PLMN.equals(roleTitle)) {
			return true;
		}
		logger.info("role title is error");
		CAGObject.setSettings(null);
		CAGObject.setCode(CAGConstants.CAG_BAD_REQUEST_CODE);
		CAGObject.setStatus("Bad Request");
		return false;
	}
	
	/**
	 * 判断uid是否合法.
	 * 
	 * @param uid
	 * @return
	 */
	protected boolean validateUid() {
		// 判断uid是否合法
		if (CAGObject.getUuid() == null
				|| "".equals(CAGObject.getUuid().trim())) {
			logger.error("uid is error");
			CAGObject.setSettings(null);
			CAGObject.setCode(CAGConstants.CAG_BAD_REQUEST_CODE);
			CAGObject.setStatus("Bad Request");
			return false;
		}
		long id = new UserDao().getUidByName(CAGObject.getUuid());
		if (id == -1) {
			logger.error("uid not exist");
			CAGObject.setSettings(null);
			CAGObject.setCode(CAGConstants.CAG_USER_NOTFOUND_CODE);
			CAGObject.setStatus("User Not Found");
			return false;
		}
		uid = String.valueOf(id);
		return true;
	}

	/**
	 * 判断用户是否登录.
	 * 
	 * @return
	 */
	protected boolean validateUserLogin() {
		// 判断用户是否登录
		String IMEI = new UserDao().getIMEIByUid(Long.valueOf(uid));
		if (IMEI == null || "".equals(IMEI.trim())) {
			logger.info("user by uid[" + uid + "] is not log in");
			CAGObject.setCode(CAGConstants.CAG_CLIENT_UNREACHABLE_CODE);
			CAGObject.setStatus("Client Unreachable");
			return false;
		}
		return true;
	}

	/**
	 * 验证COS设置内容合法性.
	 * 
	 * @param settings
	 * @return
	 */
	protected boolean validateCOSSettings(COSSettingsObject settings) {
		if (settings.getAllowedEmailAccount() != null
				&& !validateInt(settings.getAllowedEmailAccount())) {
			return false;
		}
		if (settings.getBackendEmailRetrievalPeriod() != null
				&& !CAGConstants.COS_OPTION_LOW.equals(settings
						.getAllowedEmailAccount())
				&& !CAGConstants.COS_OPTION_MED.equals(settings
						.getAllowedEmailAccount())
				&& !CAGConstants.COS_OPTION_ASAP.equals(settings
						.getAllowedEmailAccount())) {
			return false;
		}
		if (settings.getEmailFolderSyncOption() != null
				&& !CAGConstants.COS_OPTION_YES.equals(settings
						.getAllowedEmailAccount())
				&& !CAGConstants.COS_OPTION_NO.equals(settings
						.getAllowedEmailAccount())) {
			return false;
		}
		if (settings.getEncryptionSettingOption() != null
				&& !CAGConstants.COS_OPTION_YES.equals(settings
						.getAllowedEmailAccount())
				&& !CAGConstants.COS_OPTION_NO.equals(settings
						.getAllowedEmailAccount())
				&& !CAGConstants.COS_OPTION_OD.equals(settings
						.getAllowedEmailAccount())) {
			return false;
		}
		if (settings.getNumberOfLatestEmailRetrieval() != null
				&& !validateInt(settings.getAllowedEmailAccount())
				&& !CAGConstants.COS_OPTION_OD.equals(settings
						.getAllowedEmailAccount())) {
			return false;
		}
		if (settings.getStorageLocationOption() != null
				&& !CAGConstants.COS_OPTION_YES.equals(settings
						.getAllowedEmailAccount())
				&& !CAGConstants.COS_OPTION_NO.equals(settings
						.getAllowedEmailAccount())) {
			return false;
		}
		if (settings.getAllowAttachmentNumber() != null
				&& !validateInt(settings.getAllowedEmailAccount())
				&& !CAGConstants.COS_OPTION_OD.equals(settings
						.getAllowedEmailAccount())) {
			return false;
		}
		if (settings.getAutoCleanupPeriod() != null
				&& !validateInt(settings.getAllowedEmailAccount())
				&& !CAGConstants.COS_OPTION_NEVER.equals(settings
						.getAllowedEmailAccount())) {
			return false;
		}
		if (settings.getSaveOriginalAttachmentOption() != null
				&& !CAGConstants.COS_OPTION_YES.equals(settings
						.getAllowedEmailAccount())
				&& !CAGConstants.COS_OPTION_NO.equals(settings
						.getAllowedEmailAccount())
				&& !CAGConstants.COS_OPTION_OD.equals(settings
						.getAllowedEmailAccount())) {
			return false;
		}
		if (settings.getScheduledPushOption() != null
				&& !CAGConstants.COS_OPTION_YES.equals(settings
						.getAllowedEmailAccount())
				&& !CAGConstants.COS_OPTION_NO.equals(settings
						.getAllowedEmailAccount())) {
			return false;
		}
		if (settings.getUseExchangeServerOption() != null
				&& !CAGConstants.COS_OPTION_YES.equals(settings
						.getAllowedEmailAccount())
				&& !CAGConstants.COS_OPTION_NO.equals(settings
						.getAllowedEmailAccount())) {
			return false;
		}
		if (settings.getUseMailAccountOption() != null
				&& !validateInt(settings.getUseMailAccountOption())) {
			return false;
		}
		return true;
	}

	/**
	 * 验证int数字.
	 * 
	 * @param str
	 * @return
	 */
	private boolean validateInt(String str) {
		try {
			Integer.valueOf(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 处理需要和客户端交互的通知.
	 * 
	 * 
	 */
	protected void handleNoticeAboutClient() {
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, CAGObject.getNotification());
		json.put(JMSConstans.JMS_UID_KEY, uid);
		json.put(JMSConstans.JMS_TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
		String message = json.toString();
		// 发送JMS消息
		MessageSender.sendJMSTopicMessage2UPE(message);
	}
}
