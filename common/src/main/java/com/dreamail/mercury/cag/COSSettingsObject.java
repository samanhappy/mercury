package com.dreamail.mercury.cag;

public class COSSettingsObject {

	private String level;

	private String AllowedEmailAccount;

	private String StorageLocationOption;

	private String NumberOfLatestEmailRetrieval;

	private String BackendEmailRetrievalPeriod;

	private String EncryptionSettingOption;

	private String EmailFolderSyncOption;

	private String SaveOriginalAttachmentOption;
	
	private String UseExchangeServerOption;
	
	private String ScheduledPushOption;
	
	private String AllowAttachmentNumber;
	
	private String AutoCleanupPeriod;
	
	private String UseMailAccountOption;

	public String getLevel() {
		return level;
	}

	public String getSaveOriginalAttachmentOption() {
		return SaveOriginalAttachmentOption;
	}

	public void setSaveOriginalAttachmentOption(String saveOriginalAttachmentOption) {
		SaveOriginalAttachmentOption = saveOriginalAttachmentOption;
	}

	public String getUseExchangeServerOption() {
		return UseExchangeServerOption;
	}

	public void setUseExchangeServerOption(String useExchangeServerOption) {
		UseExchangeServerOption = useExchangeServerOption;
	}

	public String getScheduledPushOption() {
		return ScheduledPushOption;
	}

	public void setScheduledPushOption(String scheduledPushOption) {
		ScheduledPushOption = scheduledPushOption;
	}

	public String getAllowAttachmentNumber() {
		return AllowAttachmentNumber;
	}

	public void setAllowAttachmentNumber(String allowAttachmentNumber) {
		AllowAttachmentNumber = allowAttachmentNumber;
	}

	public String getAutoCleanupPeriod() {
		return AutoCleanupPeriod;
	}

	public void setAutoCleanupPeriod(String autoCleanupPeriod) {
		AutoCleanupPeriod = autoCleanupPeriod;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getAllowedEmailAccount() {
		return AllowedEmailAccount;
	}

	public void setAllowedEmailAccount(String allowedEmailAccount) {
		AllowedEmailAccount = allowedEmailAccount;
	}

	public String getStorageLocationOption() {
		return StorageLocationOption;
	}

	public void setStorageLocationOption(String storageLocationOption) {
		StorageLocationOption = storageLocationOption;
	}

	public String getNumberOfLatestEmailRetrieval() {
		return NumberOfLatestEmailRetrieval;
	}

	public void setNumberOfLatestEmailRetrieval(
			String numberOfLatestEmailRetrieval) {
		NumberOfLatestEmailRetrieval = numberOfLatestEmailRetrieval;
	}

	public String getBackendEmailRetrievalPeriod() {
		return BackendEmailRetrievalPeriod;
	}

	public void setBackendEmailRetrievalPeriod(
			String backendEmailRetrievalPeriod) {
		BackendEmailRetrievalPeriod = backendEmailRetrievalPeriod;
	}

	public String getEncryptionSettingOption() {
		return EncryptionSettingOption;
	}

	public void setEncryptionSettingOption(String encryptionSettingOption) {
		EncryptionSettingOption = encryptionSettingOption;
	}

	public String getEmailFolderSyncOption() {
		return EmailFolderSyncOption;
	}

	public void setEmailFolderSyncOption(String emailFolderSyncOption) {
		EmailFolderSyncOption = emailFolderSyncOption;
	}

	public String getUseMailAccountOption() {
		return UseMailAccountOption;
	}

	public void setUseMailAccountOption(String useMailAccountOption) {
		UseMailAccountOption = useMailAccountOption;
	}

}
