package com.dreamail.mercury.imapIdle.test;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.event.MessageCountAdapter;

import org.junit.Test;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

public class ImapIdleTest {

	public static void main(String[] args) {
		IMAPStore store = null;
		IMAPFolder inbox = null;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		// props.setProperty("mail.imap.port", "8080");
		props.setProperty("mail.imap.port", "993");
		Session session = Session.getInstance(props);
		session.setDebug(true);
		try {
			// store = (IMAPStore) session.getStore("imap");
			// store.connect("127.0.0.1", "wpk1901@gmail.com","8611218773");
			store = (IMAPStore) session.getStore("imaps");
			store.connect("imap.gmail.com", "wpk1901@gmail.com", "8611218773");
			inbox = (IMAPFolder) store.getFolder("inbox");
			inbox.open(Folder.READ_WRITE);

			// 查看检索新邮件的协议
			// Message[] msgs = inbox.search(new AndTerm(
			// new SearchTerm[] { new ReceivedDateTerm(ComparisonTerm.GT,
			// descRegisterDate()) }));
			// Message[] msgs = inbox.getMessages();

			// System.out.println("共有："+msgs.length);
			// for(Message msg:msgs){
			// // msg.getContent();
			// msg.setFlag(Flag.DELETED, true);
			// }

			inbox.addMessageCountListener(new MessageCountAdapter() {
				public void messagesAdded(javax.mail.event.MessageCountEvent e) {
					System.out.println(e.getMessages().length);
				}

				public void messagesRemoved(javax.mail.event.MessageCountEvent e) {
					System.out.println(e.getMessages().length);
				}
			});
			inbox.idle();
			// store.idle();
			// inbox.close(false);
			// Message[] msgs = inbox.search(new AndTerm(
			// new SearchTerm[] { new ReceivedDateTerm(ComparisonTerm.GT,
			// descRegisterDate()) }));
		} catch (javax.mail.AuthenticationFailedException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			try {
				if (store != null) {
					store.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void showHostIP() {
//		InetAddress[] inetAddress = null;
//		try {
//			inetAddress = InetAddress.getAllByName("imap.gmail.com");
//		} catch (Exception e) {
//		}
//		for (InetAddress connHost : inetAddress) {
//			System.out.println(connHost);
//		}
	}

	public static Date descRegisterDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH) - 1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

}
