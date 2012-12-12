package com.dreamail.mercury.mail.validate;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.junit.Test;

import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.validate.IMailValidate;
import com.dreamail.mercury.mail.validate.impl.ImapMailValidateImpl;
import com.dreamail.mercury.mail.validate.impl.Pop3MailValidateImpl;

public class MailValidateTest {
	@Test
	public void imap4mailValidate(){
		IMailValidate v = new ImapMailValidateImpl();
		WebAccount c = new WebAccount();
		c.setName("kai_li_nj@yahoo.cn");
		c.setPassword("85122971");
		c.setReceiveHost("imap.mail.yahoo.com");
		c.setReceivePort("993");
		c.setReceiveTs("ssl");
//		c.setReceiveProtocolType("imap");
		try {
			System.out.println(v.validate(c));
		} catch (Exception e) {
//			if(e instanceof javax.mail.MessagingException){
//				System.out.println(e.getMessage());
//			}
			try {
//				ExceSupport s = new ExceSupport();
//				System.out.println(s.execHandle(e));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@Test
	public void pop3MailValidate(){
		IMailValidate v = new Pop3MailValidateImpl();
		WebAccount c = new WebAccount();
		c.setName("wpk1902@hotmail.com");
		c.setPassword("8611218773");
		c.setReceiveHost("pop3.live.com");
		c.setReceivePort("995");
		c.setReceiveTs("ssl");
		c.setReceiveProtocolType("pop");
		try {
			System.out.println(v.validate(c));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void msgTest(){
		Store store = null;
		Folder inbox = null;
		Session session = Session.getInstance(getProperties("ssl", "995"));
		try {
			store = session.getStore("pop3s");
			store.connect("pop.gmail.com", "kai.li.mind.1@gmail.com", "archermind");
			inbox =  store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			Message[] msgs = inbox.getMessages();
			System.out.println(msgs.length);
			for(Message s:msgs){
				System.out.println(s.getSubject());
				System.out.println(s.getSentDate());
			}
			
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	
	public Properties getProperties(String receiveTs, String port) {
		Properties props = new Properties();
		/*props.setProperty("mail.pop3.port", port);
		props.setProperty("mail.pop3.connectiontimeout", "30000");
		if (receiveTs != null && "ssl".equalsIgnoreCase(receiveTs)) {
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			props.setProperty("mail.pop3.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.pop3.socketFactory.fallback", "false");
			props.setProperty("mail.pop3.socketFactory.port", port);
		} else if (receiveTs != null && "tls".equalsIgnoreCase(receiveTs)) {
			props.setProperty("mail.pop3.starttls.enable", "true");
		}*/
		return props;
	}
	
	@Test
	public void testName(){
		String name = "test01_yahoo.com@yahoo.com";
		System.out.println(name.endsWith("@yahoo.cn"));
	}
	
}
