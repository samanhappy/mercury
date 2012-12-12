package com.dreamail.mercury.mail.receiver;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.yahooSNP.impl.SNPLogin;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

public abstract class AbstractImapProvide extends AbstractProvide {

	/**
	 * 爆雅虎的菊花.
	 * 
	 * @param store
	 * @param accountName
	 */
	public void yahooIDCommand(Store store, String accountName) {
		((IMAPStore) store)
				.SetIDCommand("ID (\"vendor\" \"Zimbra\" \"os\" \"Windows XP\" \"os-version\" \"5.1\" \"guid\" \"4062-5711-9195-4050\")");
	}

	/**
	 * 获取Store.
	 * 
	 * @param receiveTs
	 * @return
	 */
	public String getImap4SSLStore(String receiveTs) {
		if (receiveTs != null && "ssl".equalsIgnoreCase(receiveTs)) {
			return "imaps";
		} else {
			return "imap";
		}
	}

	/**
	 * 根据webaccount对象和uuid下载邮件.
	 * 
	 * @param account
	 * @param uuid
	 * @return Message
	 * @throws MessagingException
	 */
	public Message getIMAPMessageByUuid(WebAccount account, String uuid)
			throws MessagingException {
		user = account.getName();
		password = account.getPassword();
		port = account.getReceivePort();
		String ts = account.getReceiveTs();
		Store store = null;
		IMAPFolder inbox = null;
		Session session = Session.getInstance(getProperties(ts, port), null);
		store = session.getStore(getImap4SSLStore(ts));
		yahooIDCommand(store, user);
		String[] serverColection = account.getInpathList();
		connect(user, password, store, serverColection);
		Message[] msg = null;
		Message message = null;
		try {
			inbox = (IMAPFolder) store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);
			msg = inbox.getMessages();
		} catch (Exception e) {
			if (!store.isConnected()) {
				logger.warn("store or inbox is closed!!!!!!! wait for the next.");
			}
		}
		if (msg != null) {
			for (Message mes : msg) {
				if (uuid != null
						&& uuid.equals(Long.toString(inbox.getUID(mes)))) {
					message = mes;
				}
			}
		}
		if (message == null) {
			DLEmailException exception = new DLEmailException();
			exception.setMessage(Constant.GET_MAIL_FAIL);
			throw exception;
		}
		account.setStore(store);
		account.setFolder(inbox);
		return message;
	}

	@Override
	public Properties getProperties(String receiveTs, String port) {
		Properties props = new Properties();
		if (receiveTs != null && "ssl".equalsIgnoreCase(receiveTs)) {
			logger.info("imaps------------------imaps");
			props.setProperty("mail.store.protocol", "imaps");
			props.setProperty("mail.imaps.partialfetch", "false");
		} else {
			logger.info("imap------------------imap");
			props.setProperty("mail.store.protocol", "imap");
			props.setProperty("mail.imap.partialfetch", "false");
		}
		return props;
	}

	/**
	 * 根据WebAccount获取收件箱.
	 * 
	 * @param context
	 * @return IMAPFolder
	 * @throws MessagingException
	 */
	protected IMAPFolder getFolder(Context context, String folderName)
			throws MessagingException {
		user = context.getLoginName();
		password = context.getPassword();
		server = context.getReceiveHost();
		port = context.getPort();
		Store store = null;
		IMAPFolder inbox = null;
		Session session = Session.getInstance(
				getProperties(context.getReceiveTs(), port), null);
		store = session.getStore(getImap4SSLStore(context.getReceiveTs()));
		if (context.getAccountType() == Constant.ACCOUNT_YAHOOSNP_TYPE)
			yahooIDCommand(store, context.getLoginName());
		String[] serverColection = context.getInpathList();
		logger.info("account " + user + " connect begin by folder:"
				+ (folderName == null ? "INBOX" : folderName));
		if (context.getToken() != null) {
			SNPLogin login = new SNPLogin();
			String cookie = login.getReceiveCookie(login
					.getLoginContext(context.getToken()));
			if (!connect("TOKEN:" + user, cookie, store, serverColection)
					&& context.getPassword() != null)
				connect(user, password, store, serverColection);
		} else {
			connect(user, password, store, serverColection);
		}
		// try {
		inbox = (IMAPFolder) store.getFolder(folderName == null ? "INBOX"
				: folderName);
		inbox.open(Folder.READ_WRITE);
		// } catch (Exception e) {
		// if(!store.isConnected()){
		// logger.warn("store or inbox is closed!!!!!!! wait for the next.");
		// connect(server, user, password, store,serverColection);
		// inbox = (IMAPFolder) store.getFolder("INBOX");
		// inbox.open(Folder.READ_WRITE);
		// msg = inbox.search(new AndTerm(
		// new SearchTerm[] { new ReceivedDateTerm(ComparisonTerm.GT,
		// descRegisterDate()) }));
		// }

		// }
		context.setFolder(inbox);
		context.setStore(store);
		return inbox;
	}

	protected IMAPStore getStore(WebAccount account) {
		user = account.getName();
		password = account.getPassword();
		port = account.getReceivePort();
		IMAPStore store = null;
		Session session = Session.getInstance(
				getProperties(account.getReceiveTs(), port), null);
		session.setDebug(true);
		try {
			store = (IMAPStore) session.getStore(getImap4SSLStore(account
					.getReceiveTs()));
			yahooIDCommand(store, user);
			connect(user, password, store, account.getInpathList());
		} catch (MessagingException e) {
			logger.warn("get IMAPStore fail.", e);
		}
		return store;
	}

}
