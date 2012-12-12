package com.dreamail.mercury.mail.validate.impl;

import javax.mail.AuthenticationFailedException;
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
import com.dreamail.mercury.mail.receiver.AbstractPop3Provide;
import com.dreamail.mercury.mail.validate.IMailValidate;
import com.sun.mail.pop3.POP3Folder;

public class Pop3MailValidateImpl extends AbstractPop3Provide implements IMailValidate{
	private static final Logger logger = LoggerFactory.getLogger(Pop3MailValidateImpl.class);
	
	@Override
	public boolean validate(WebAccount account){
		user = account.getName();
		password = account.getPassword();
		server = account.getReceiveHost();
		port = account.getReceivePort();
		Store store = null;
		POP3Folder inbox = null;
		Session session = Session.getInstance(super.getProperties(
				account.getReceiveTs(), port));
		try {
			store = session.getStore(super.getPop3SSLStore(account.getReceiveTs()));
			store.connect(server, user, password);
		} catch(AuthenticationFailedException e){
			logger.warn("account["+account.getName()+"]POP3 receive  validate failure...",e);
			return false;
		}catch (NoSuchProviderException e) {
			logger.warn("account["+account.getName()+"]POP3 receive  validate is false...",e);
			return false;
		} catch (MessagingException e) {
			logger.warn("account["+account.getName()+"]POP3 receive  validate failure...",e);
			return false;
		}finally{
			ICloseConnection connection = new CloseConnectionImpl();
			connection.closeConnection(store, inbox);
		}
		return true;
	}

	@Override
	public void receiveLargeMail(Context context) throws MessagingException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveMail(Context context) throws MessagingException,
			TimeoutException {
		// TODO Auto-generated method stub
		
	}

	
	
}
