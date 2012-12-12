package com.dreamail.mercury.cag;

import net.sf.json.JSONObject;

import com.dreamail.mercury.util.CAGConstants;

public class COSSettingsParser {

	public static COSSettingsObject json2Object(String roleStr) {
		JSONObject json = JSONObject.fromObject(roleStr);
		COSSettingsObject settings = new COSSettingsObject();
		settings.setAllowedEmailAccount(json
				.getString(CAGConstants.COS_ACOUNT_NUMBER));
		settings.setBackendEmailRetrievalPeriod(json
				.getString(CAGConstants.COS_RETRIEVAL_EMAIL_INTERVAL));
		settings.setEmailFolderSyncOption(json
				.getString(CAGConstants.COS_SYNCHRONIZE_OPTION));
		settings.setEncryptionSettingOption(json
				.getString(CAGConstants.COS_ENCRYPTION_OPTION));
		settings.setNumberOfLatestEmailRetrieval(json
				.getString(CAGConstants.COS_LATESTEMAILNUMBER));
		settings.setStorageLocationOption(json
				.getString(CAGConstants.COS_STORAGE_OPTION));
		settings.setAllowAttachmentNumber(json
				.getString(CAGConstants.COS_ATTCHMENT_NUMBER));
		settings.setAutoCleanupPeriod(json
				.getString(CAGConstants.COS_AUTOCLEANUP_PERIOD));
		settings.setSaveOriginalAttachmentOption(json
				.getString(CAGConstants.COS_SAVEATTAHMENT_OPTION));
		settings.setScheduledPushOption(json
				.getString(CAGConstants.COS_SCHEDULEPUSH_OPTION));
		settings.setUseMailAccountOption(json
				.getString(CAGConstants.COS_MAILACCOUNT_OPTION));
		return settings;
	}

	public static String objectUpdate2json(COSSettingsObject settings,
			String roleStr) {
		JSONObject json = JSONObject.fromObject(roleStr);
		if (settings.getAllowedEmailAccount() != null) {
			json.put(CAGConstants.COS_ACOUNT_NUMBER, settings
					.getAllowedEmailAccount());
		}
		if (settings.getBackendEmailRetrievalPeriod() != null) {
			json.put(CAGConstants.COS_RETRIEVAL_EMAIL_INTERVAL, settings
					.getBackendEmailRetrievalPeriod());
		}
		if (settings.getEmailFolderSyncOption() != null) {
			json.put(CAGConstants.COS_SYNCHRONIZE_OPTION, settings
					.getEmailFolderSyncOption());
		}
		if (settings.getEncryptionSettingOption() != null) {
			json.put(CAGConstants.COS_ENCRYPTION_OPTION, settings
					.getEncryptionSettingOption());
		}
		if (settings.getNumberOfLatestEmailRetrieval() != null) {
			json.put(CAGConstants.COS_LATESTEMAILNUMBER, settings
					.getNumberOfLatestEmailRetrieval());
		}
		if (settings.getStorageLocationOption() != null) {
			json.put(CAGConstants.COS_STORAGE_OPTION, settings
					.getStorageLocationOption());
		}
		if (settings.getAllowAttachmentNumber() != null) {
			json.put(CAGConstants.COS_ATTCHMENT_NUMBER, settings
					.getAllowAttachmentNumber());
		}
		if (settings.getAutoCleanupPeriod() != null) {
			json.put(CAGConstants.COS_AUTOCLEANUP_PERIOD, settings
					.getAutoCleanupPeriod());
		}
		if (settings.getSaveOriginalAttachmentOption() != null) {
			json.put(CAGConstants.COS_SAVEATTAHMENT_OPTION, settings
					.getSaveOriginalAttachmentOption());
		}
		if (settings.getScheduledPushOption() != null) {
			json.put(CAGConstants.COS_SCHEDULEPUSH_OPTION, settings
					.getScheduledPushOption());
		}
		if (settings.getUseMailAccountOption() != null) {
			json.put(CAGConstants.COS_MAILACCOUNT_OPTION, settings
					.getUseMailAccountOption());
		}
		return json.toString();
	}

}
