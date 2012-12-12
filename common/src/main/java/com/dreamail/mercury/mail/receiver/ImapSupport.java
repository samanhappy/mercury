package com.dreamail.mercury.mail.receiver;

import javax.mail.Folder;
import javax.mail.MessagingException;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.TimeoutException;
import com.dreamail.mercury.domain.WebAccount;
import com.sun.mail.imap.IMAPStore;

public class ImapSupport extends AbstractImapProvide  implements ISupport {

	@Override
	public WebAccount isSupportAllUid(WebAccount account,Folder inbox) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebAccount isSupportCompositor(WebAccount account,Folder inbox) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 判断imap账号是否支持IDLE.
	 * @param account
	 * @return
	 * @throws Exception 
	 */
	@Override
	public WebAccount isSupportIDLE(WebAccount account) {
		IMAPStore store = getStore(account);
		try {
			if(store!=null && store.hasCapability("IDLE")){
				account.setSupportIDLE("0");
			}
		} catch (MessagingException e) {
			logger.info("account:"+account.getName()+" judge support IDLE fail.",e);
			e.printStackTrace();
		}
			return account;
	}

	@Override
	public void receiveLargeMail(Context context) throws MessagingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveMail(Context context) throws MessagingException,
			TimeoutException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WebAccount getAllSupport(WebAccount account)
			throws MessagingException {
		// TODO Auto-generated method stub
		return null;
	}

}
