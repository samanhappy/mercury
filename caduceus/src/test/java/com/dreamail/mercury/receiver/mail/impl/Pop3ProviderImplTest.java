package com.dreamail.mercury.receiver.mail.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;

import org.junit.Test;

import com.dreamail.mercury.mail.receiver.AbstractPop3Provide;
import com.sun.mail.pop3.POP3Folder;

public class Pop3ProviderImplTest {
	@Test
	public void receiveMail() throws MessagingException{
		String server = "pop.gmail.com";
		String user = "wpk1902@gmail.com";
		String password = "8611218773";
		String port = "995";
		Store store = null;
		POP3Folder inbox = null;
		
		Properties props = new Properties();
		props.setProperty("mail.pop3.port", port);
		props.setProperty("mail.pop3.connectiontimeout", "30000");
		props.setProperty("mail.pop3.disabletop", "true");
		Session session = Session.getDefaultInstance(props);
		
		store = session.getStore("pop3s");
		store.connect(server, user, password);
		inbox = (POP3Folder) store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);
		FetchProfile profile = new FetchProfile();
		profile.add(UIDFolder.FetchProfileItem.UID);
		profile.add(FetchProfile.Item.FLAGS);
		Message[] msgs = inbox.getMessages();
//		System.out.println(msgs.length);
		inbox.fetch(msgs, profile);
		for (Message message : msgs) {
			System.out.println(inbox.getUID(message));
			System.out.println(message.getSentDate());
			System.out.println(message.getSubject());
		}
	}
	
	@Test
	public void dateTest(){
		AbstractPop3Provide pop = new  Pop3ProviderImpl();
		System.out.println(pop.isOldMessage(new Date(), descRegisterDate()));
	}
	
	public static Date descRegisterDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}
	
}
