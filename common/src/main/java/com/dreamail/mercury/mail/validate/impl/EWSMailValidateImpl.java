package com.dreamail.mercury.mail.validate.impl;

import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.validate.IMailValidate;
import com.microsoft.ews.client.EWSReceiveClient;

public class EWSMailValidateImpl implements IMailValidate {

	@Override
	public boolean validate(WebAccount account) {
		return new EWSReceiveClient().validate(account);
	}
}
