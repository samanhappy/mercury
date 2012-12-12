package com.dreamail.mercury.receiver.mail.parser.impl;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;

import org.junit.Test;

import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserImpl;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

public class EmailParserImaplTest {
	@Test
	public void testGetFrom() throws Exception{
		IMAPStore store = null;
		IMAPFolder inbox = null;
			Properties props = System.getProperties();
			props.put("mail.smtp.auth", "true");
			props.setProperty("mail.imap.port", "993");
			Session session = Session.getDefaultInstance(props, null);
//			session.setDebug(true);
			store = (IMAPStore) session.getStore("imaps");

			try {
				store.connect("imap.gmail.com", "wpk1902@gmail.com",
						"   8611218773  ");
			} catch (Exception e) {
				e.printStackTrace();
			}
			inbox = (IMAPFolder) store.getFolder("inbox");
			inbox.open(Folder.READ_WRITE);
			Message[] messages = inbox.getMessages();
			Message message = messages[messages.length-1];
		System.out.println(EmailParserImpl.getInstance().getFrom(message));
	}
	@Test
	public void testGetMailAddress() throws Exception{
		IMAPStore store = null;
		IMAPFolder inbox = null;
			Properties props = System.getProperties();
			props.put("mail.smtp.auth", "true");
			props.setProperty("mail.imap.port", "993");
			Session session = Session.getDefaultInstance(props, null);
//			session.setDebug(true);
			store = (IMAPStore) session.getStore("imaps");

			try {
				store.connect("imap.gmail.com", "wpk1902@gmail.com",
						"   8611218773  ");
			} catch (Exception e) {
				e.printStackTrace();
			}
			inbox = (IMAPFolder) store.getFolder("inbox");
			inbox.open(Folder.READ_WRITE);
			Message[] messages = inbox.getMessages();
			Message message = messages[messages.length-1];
		System.out.println(EmailParserImpl.getInstance().getMailAddress(message,"to"));
	}
}
