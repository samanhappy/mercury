package com.dreamail.mercury.talaria.xstream;

import com.dreamail.mercury.cag.CAGSettingObject;

public class CAGParserObject {

	private String uuid;
	
	private String notification;
	
	private String account;
	
	private String code;
	
	private String status;
	
	private CAGSettingObject settings;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CAGSettingObject getSettings() {
		return settings;
	}

	public void setSettings(CAGSettingObject settings) {
		this.settings = settings;
	}
	
	
}
