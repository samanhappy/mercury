package com.dreamail.mercury.cag;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class CAGSettingObject {

	private String firmware;
	
	private COSSettingsObject cos;
	
	@XStreamAlias("accounts")
	private List<AccountSettingsObject> accounts;

	public List<AccountSettingsObject> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountSettingsObject> accounts) {
		this.accounts = accounts;
	}

	public COSSettingsObject getCos() {
		return cos;
	}

	public void setCos(COSSettingsObject cos) {
		this.cos = cos;
	}

	public String getFirmware() {
		return firmware;
	}

	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}

}


