package com.dreamail.mercury.mail.delete;

import java.util.List;
import java.util.TreeMap;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.TimeoutException;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.receiver.AbstractPop3Provide;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.sun.mail.pop3.POP3Folder;

public class DEmailPop3Impl extends AbstractPop3Provide implements IDProvide {

	@Override
	public void receiveLargeMail(Context context) throws MessagingException {
		logger.info("not support");
	}

	@Override
	public void receiveMail(Context context) throws MessagingException,
			TimeoutException {
		logger.info("not support");
	}

	@Override
	public boolean dEmail(Clickoo_mail_account account, List<String> uuidList) {
		boolean supportAllUUid = true;
		try {

			if (account.getName().indexOf("@") != -1
					&& "gmail.com".equalsIgnoreCase(account.getName()
							.split("@")[1])) {
				account.setName("recent:" + account.getName());
			}
			WebAccount webAccount = new WebAccount();
			webAccount.setName(account.getName());
			webAccount.setPassword(account.getInCert_obj().getPwd());
			webAccount.setReceivePort(account.getInPath_obj().getReceivePort());
			POP3Folder inbox = getFolder(webAccount);

			if (account.getInPath_obj().getSupportalluid() != null
					&& "0".equalsIgnoreCase(account.getInPath_obj()
							.getSupportalluid())) {
				TreeMap<String, String> treeMap = null;
				try {
					treeMap = getAllUidMap(inbox);
				} catch (MessagingException e2) {
					logger.warn("account[" + user + "]get all uid error.", e2);
					supportAllUUid = false;
					dEmail(uuidList, inbox);
				}
				if (supportAllUUid == true) {
					dEmail(uuidList, treeMap, inbox);
				}
			} else {
				dEmail(uuidList, inbox);
			}
			inbox.close(true);
			inbox.getStore().close();
		} catch (MessagingException e) {
			logger.info("failed to delete all email.");
			return false;
		}
		return true;
	}

	public boolean dEmail(List<String> uuidList,
			TreeMap<String, String> treeMap, POP3Folder inbox) {
		if (treeMap == null) {
			return dEmail(uuidList, inbox);
		}
		try {
			for (String uuid : uuidList) {
				String msgNum = treeMap.get(uuid);
				if (msgNum != null) {
					Message message;
					int num = Integer.parseInt(msgNum);
					try {
						message = inbox.getMessage(num);
					} catch (IllegalStateException e) {
						reHandle(inbox);
						message = inbox.getMessage(num);
					} catch (MessagingException e) {
						reHandle(inbox);
						message = inbox.getMessage(num);
					}
					if (message != null) {
						message.setFlag(Flags.Flag.DELETED, true);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("delete Email failed....", e);
			return false;
		}
		return true;
	}

	public boolean dEmail(List<String> uuidList, POP3Folder inbox) {
		Message msgAll[] = null;
		try {
			try {
				msgAll = inbox.getMessages();
			} catch (Exception e) {
				reHandle(inbox);
				msgAll = inbox.getMessages();
			}
			if (msgAll == null) {
				logger.warn("inbox is null....");
				return false;
			}
			for (Message msg : msgAll) {
				if (!inbox.isOpen()) {
					reHandle(inbox);
				}
				String inboxUUID = inbox.getUID(msg);
				for (String uuid : uuidList) {
					if (uuid.equalsIgnoreCase(inboxUUID)) {
						if (!msg.isSet(Flags.Flag.DELETED)) {
							msg.setFlag(Flags.Flag.DELETED, true);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warn("delete Email failed....", e);
			return false;
		}
		return true;
	}
}
