package com.dreamail.mercury.sendMail.email.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cache.MemCachedManager;
import com.dreamail.mercury.cache.ReceiveServerCacheManager;
import com.dreamail.mercury.cache.RoleCacheManager;
import com.dreamail.mercury.cache.SendServerCacheManager;
import com.dreamail.mercury.cache.VolumeCacheManager;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserProvide;

public class TestEmailSend {
	private static Logger logger = LoggerFactory.getLogger(TestEmailSend.class);

	public Email getEmalFromVolume() throws MessagingException {
		Email email = null;
		FileInputStream fis = null;
		StringBuffer wholePath = new StringBuffer("E:\\test");
		wholePath.append(File.separator);
		wholePath.append("lwl.eml");
		try {
			fis = new FileInputStream(wholePath.toString());
		} catch (FileNotFoundException e) {
			logger.info("eml can't be found.");
			return email;
		}
		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage msg = null;
		msg = new MimeMessage(session, fis);
		email = new EmailParserProvide().getEmail("weiliang.liu2010@gmail.com",
				msg, null);
		email.setUuid("Gmailtestbylwl");
		System.out.println(email.getFrom());
		return email;
	}

	// @Test
	// public void testUpdateEml() throws MessagingException{
	// FileInputStream fis = null;
	// // StringBuffer wholePath = new StringBuffer("E:\\test");
	// StringBuffer wholePath = new
	// StringBuffer("src/test/resources/com/clickoo/mercury/sendMail/sender");
	// wholePath.append(File.separator);
	// wholePath.append("lwl.eml");
	// try {
	// fis = new FileInputStream(wholePath.toString());
	// } catch (FileNotFoundException e) {
	// logger.info("eml can't be found.");
	// }
	// Properties props = System.getProperties();
	// props.put("mail.smtp.auth", "true");
	// Session session = Session.getDefaultInstance(props, null);
	// MimeMessage msg = null;
	// msg = new MimeMessage(session, fis);
	//
	// StringBuffer newpath = new StringBuffer("E:\\test1");
	// // StringBuffer newpath = new StringBuffer("E:/test1"); //两种写法都行
	// File f = new File(newpath.toString());
	// if (!f.exists()) {
	// f.mkdirs();
	// }
	// newpath.append(File.separator);
	// newpath.append("lwl1.eml");
	// System.out.println(newpath.toString()+"----------------------");
	// File f1 = new File(newpath.toString());
	// try {
	// msg.writeTo(new FileOutputStream(f1));
	// } catch (FileNotFoundException e) {
	// logger.error("file is not found.");
	// } catch (IOException e) {
	// logger.error("write message to eml fail.");
	// } catch (MessagingException e) {
	// logger.error("", e);
	// }
	// }
	@Test
	public void testInBoxForWord() {
		MemCachedManager.getInstance().init();
		new RoleCacheManager().init();
		new ReceiveServerCacheManager().init();
		new SendServerCacheManager().init();
		new PropertiesDeploy().init();
		MemCachedManager.getInstance().init();
		new VolumeCacheManager().init();
		EmailSend emailSend = new EmailSend();
		emailSend.emailSendEntrance("6", "233", "1", "214", "");

	}

}
