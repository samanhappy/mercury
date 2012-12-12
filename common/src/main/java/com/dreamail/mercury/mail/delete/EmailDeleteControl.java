package com.dreamail.mercury.mail.delete;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.pojo.Clickoo_mail_account;

public class EmailDeleteControl implements IDProvide {

	public static final Logger logger = LoggerFactory
			.getLogger(EmailDeleteControl.class);

	@Override
	public boolean dEmail(Clickoo_mail_account account, List<String> uuidList) {
		String receiveProtocolType = account.getInPath_obj().getProtocolType();
		if ("imap".equalsIgnoreCase(receiveProtocolType)) {
			return new DEmailImapImpl().dEmail(account, uuidList);
		}
		if ("pop".equalsIgnoreCase(receiveProtocolType)) {
			return new DEmailPop3Impl().dEmail(account, uuidList);
		}
		if ("http".equalsIgnoreCase(receiveProtocolType)) {
			return new DEmailEWSImpl().dEmail(account, uuidList);
		}
		logger.warn("no support for this protocol type.");
		return false;
	}

}
