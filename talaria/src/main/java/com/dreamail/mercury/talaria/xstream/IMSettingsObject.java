package com.dreamail.mercury.talaria.xstream;

import java.util.List;

import com.dreamail.mercury.cag.AccountSettingsObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class IMSettingsObject {

	@XStreamAlias("accounts")
	private List<AccountSettingsObject> accounts;

	public List<AccountSettingsObject> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountSettingsObject> accounts) {
		this.accounts = accounts;
	}
	
}
