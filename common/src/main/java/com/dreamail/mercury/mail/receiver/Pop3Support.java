package com.dreamail.mercury.mail.receiver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.TimeoutException;
import com.dreamail.mercury.domain.WebAccount;
import com.sun.mail.pop3.POP3Folder;

public class Pop3Support extends AbstractPop3Provide implements ISupport {

	/**
	 * 判断账号是否支持getAlluid.
	 * @param account
	 * @return WebAccount
	 */
	@Override
	public WebAccount isSupportAllUid(WebAccount account,Folder inbox) {
		boolean isSupport = true;
		try {
			getAllUidMap((POP3Folder)inbox);
		} catch (MessagingException e) {
			logger.info(e.getMessage());
			if (e.getMessage().contains("error getting all UIDL")) {
				logger.info("account:"+account.getName()+"is not support getAllUid.");
			}
			isSupport = false;
		}
		if (isSupport) {
			account.setSupportalluid("0");
		}
		return account;
	}

	public static void main(String[] args) {
		WebAccount account = new WebAccount();
		account.setName("115196532@qq.com");
		account.setPassword("Freedom");
		account.setReceivePort("110");
		account.setReceiveTs("");
		account.setInpathList(new String[] { "pop.qq.com" });
		// new Pop3Support().isSupportAllUid(account);
//		System.out.println(new Pop3Support().isSupportCompositor(account)
//				.getCompositor());
//		System.out.println(new Pop3Support().isSupportAllUid(account).getSupportalluid());
		
	}
	
	/**
	 * 判断账号是否支持排序.
	 * @param account
	 * @return WebAccount
	 */
	@Override
	public WebAccount isSupportCompositor(WebAccount account,Folder inbox) {
		int compositor = -1;
		try {
			Message[] messages = inbox.getMessages();
			List<Date> list = new ArrayList<Date>();
			if (messages.length >= 10) {
				for (int i = 0; i < messages.length; i++) {
					if (list.size() == 10) {
						break;
					}
					if (messages[i].getSentDate() != null) {
						list.add(messages[i].getSentDate());
					}
				}
				if (list.size() > 2) {
					if (list.get(0).before(list.get(1))) {
						compositor = 0;
					} else {
						compositor = 1;
					}
					if (compositor == 0) {
						for (int i = 0; i < list.size(); i++) {
							if (i == list.size() - 1) {
								break;
							}
							if (list.get(i).after(list.get(i + 1))) {
								compositor = -1;
								break;
							}
						}
					} else {
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).before(list.get(i + 1))) {
								compositor = -1;
								break;
							}
						}
					}
				}

			}
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}
		if (compositor != -1) {
			account.setCompositor(String.valueOf(compositor));
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
	public WebAccount isSupportIDLE(WebAccount account) {
		// TODO Auto-generated method stub
		return null;
	}

	public WebAccount getAllSupport(WebAccount account) throws MessagingException{
		POP3Folder inbox = null;
		try {
			inbox = getFolder(account);
		} catch (MessagingException e1) {
			logger
					.warn("[account]:" + account.getName()
							+ " open folder fail.");
			throw new MessagingException();
		}
		isSupportAllUid(account,inbox);
		isSupportCompositor(account,inbox);
		return account;
	}
}
