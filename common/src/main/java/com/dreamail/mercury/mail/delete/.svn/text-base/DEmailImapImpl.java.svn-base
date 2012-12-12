package com.dreamail.mercury.mail.delete;

import java.util.List;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.TimeoutException;
import com.dreamail.mercury.mail.receiver.AbstractImapProvide;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.sun.mail.imap.IMAPFolder;

public class DEmailImapImpl extends AbstractImapProvide implements IDProvide {

	@Override
	public void receiveLargeMail(Context context) throws MessagingException {
	}

	@Override
	public void receiveMail(Context context) throws MessagingException,
			TimeoutException {
	}

	@Override
	public boolean dEmail(Clickoo_mail_account account, List<String> uuidList) {
		user = account.getName();

		password = account.getInCert_obj().getPwd();
		server = account.getInPath_obj().getInhost();
		port = account.getInPath_obj().getReceivePort();

		Store store = null;
		IMAPFolder inbox = null;
		Session session = Session.getInstance(super.getProperties(account
				.getInPath_obj().getReceiveTs(), port));

		String[] servers = server.split(",");
		int error = 0;
		try {
			for (int i = 0; i < servers.length; i++) {
				store = session.getStore(super.getImap4SSLStore(account
						.getInPath_obj().getReceiveTs()));
				yahooIDCommand(store, user);
				store.connect(servers[i], Integer.parseInt(port), user,
						password);
				inbox = (IMAPFolder) store.getFolder("INBOX");
				inbox.open(IMAPFolder.READ_WRITE);
				for (String uuid : uuidList) {
					Message msg;
					msg = inbox.getMessageByUID(Long.parseLong(uuid));
					msg.setFlag(Flags.Flag.DELETED, true);
				}
				inbox.close(true);
				store.close();
			}
		} catch (NumberFormatException e) {
			logger.error("failed to delete all email.", e);
			error++;
			if (error == servers.length) {
				return false;
			}
		} catch (MessagingException e) {
			logger.error("failed to delete all email.", e);
			error++;
			if (error == servers.length) {
				return false;
			}

		}
		return true;
	}

}
