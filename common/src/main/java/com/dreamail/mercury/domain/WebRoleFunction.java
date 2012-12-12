package com.dreamail.mercury.domain;

public class WebRoleFunction {
	private String accountNumber;
	private String storageOption;
	private String latestEmailNumber;
	private String retrievalEmailInterval;
	private String encryptionOption;
	private String synchronizeOption;
	private String saveOriginalAttachmentOption;
	private String scheduledPushOption;
	private String allowAttachmentNumber;
	private String autoCleanupPeriod;
	
	private String useExchangeServerOption;
	private String useMailAccountOption;
	
	public String getSaveOriginalAttachmentOption() {
		return saveOriginalAttachmentOption;
	}
	public void setSaveOriginalAttachmentOption(String saveOriginalAttachmentOption) {
		this.saveOriginalAttachmentOption = saveOriginalAttachmentOption;
	}
	public String getUseExchangeServerOption() {
		return useExchangeServerOption;
	}
	public void setUseExchangeServerOption(String useExchangeServerOption) {
		this.useExchangeServerOption = useExchangeServerOption;
	}
	public String getScheduledPushOption() {
		return scheduledPushOption;
	}
	public void setScheduledPushOption(String scheduledPushOption) {
		this.scheduledPushOption = scheduledPushOption;
	}
	public String getAllowAttachmentNumber() {
		return allowAttachmentNumber;
	}
	public void setAllowAttachmentNumber(String allowAttachmentNumber) {
		this.allowAttachmentNumber = allowAttachmentNumber;
	}
	public String getAutoCleanupPeriod() {
		return autoCleanupPeriod;
	}
	public void setAutoCleanupPeriod(String autoCleanupPeriod) {
		this.autoCleanupPeriod = autoCleanupPeriod;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getStorageOption() {
		return storageOption;
	}
	public void setStorageOption(String storageOption) {
		this.storageOption = storageOption;
	}
	public String getLatestEmailNumber() {
		return latestEmailNumber;
	}
	public void setLatestEmailNumber(String latestEmailNumber) {
		this.latestEmailNumber = latestEmailNumber;
	}
	public String getRetrievalEmailInterval() {
		return retrievalEmailInterval;
	}
	public void setRetrievalEmailInterval(String retrievalEmailInterval) {
		this.retrievalEmailInterval = retrievalEmailInterval;
	}
	public String getEncryptionOption() {
		return encryptionOption;
	}
	public void setEncryptionOption(String encryptionOption) {
		this.encryptionOption = encryptionOption;
	}
	public String getSynchronizeOption() {
		return synchronizeOption;
	}
	public void setSynchronizeOption(String synchronizeOption) {
		this.synchronizeOption = synchronizeOption;
	}
	public String getUseMailAccountOption() {
		return useMailAccountOption;
	}
	public void setUseMailAccountOption(String useMailAccountOption) {
		this.useMailAccountOption = useMailAccountOption;
	}
	
}
