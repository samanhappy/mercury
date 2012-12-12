package com.dreamail.mercury.mail.validate.impl;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.TimeoutException;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.connection.CloseConnectionImpl;
import com.dreamail.mercury.mail.connection.ICloseConnection;
import com.dreamail.mercury.mail.receiver.AbstractImapProvide;
import com.dreamail.mercury.mail.validate.IMailValidate;
import com.dreamail.mercury.util.MailBoxDispatcher;
import com.sun.mail.imap.IMAPFolder;

public class ImapMailValidateImpl extends AbstractImapProvide implements IMailValidate{
	private static final Logger logger = LoggerFactory.getLogger(ImapMailValidateImpl.class);
	@Override
	public boolean validate(WebAccount account){
		user = account.getName();
		password = account.getPassword();
		String[] serverColection = account.getReceiveHost().split(",");
		server = serverColection[0];
		port = account.getReceivePort();
		Store store = null;
		IMAPFolder inbox = null;
		Session session = Session.getInstance(super.getProperties(
				account.getReceiveTs(), port));
		boolean flag = false;
		try {
			store = session.getStore(super.getImap4SSLStore(account.getReceiveTs()));
			if(MailBoxDispatcher.isYahooSNPSupport(user))
				yahooIDCommand(store, account.getName());
			flag = connect(user, password,store,serverColection);
		} catch (NoSuchProviderException e) {
			logger.warn("account["+account.getName()+"]IMAP Validate failure...",e);
			return false;
		} catch (MessagingException e) {
			logger.warn("account["+account.getName()+"]IMAP Validate failure...",e);
			return false;
		} finally{
			ICloseConnection connection = new CloseConnectionImpl();
			connection.closeConnection(store, inbox);
		}
		return flag;
	}
	
	@Override
	public void receiveLargeMail(Context context) throws MessagingException{
		
	}
	@Override
	public void receiveMail(Context context) throws MessagingException,
			TimeoutException {
	}
}
