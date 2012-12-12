package com.dreamail.mercury.receiver.mail.parse;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

import org.junit.BeforeClass;
import org.junit.Test;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.mail.receiver.parser.EmailParser;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserImpl;
import com.sun.mail.pop3.POP3Folder;

import cpdetector.io.CodepageDetectorProxy;
import cpdetector.io.JChardetFacade;

public class POP3ParserImplTest {
	private static Message message;
	private EmailParser parser = EmailParserImpl.getInstance();

	@BeforeClass
	public static void setUp() throws Exception {
		new PropertiesDeploy().init();
		// System.out.println(EmailParserImpl.getInstance());
		System.out.println(EmailParserImpl.ATT_LIMIT_SIZE);
		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, null);
		// session.setDebug(true);
		URLName urln = new URLName("pop3s", "pop3.live.com", 995, null,
				"wpk1901@hotmail.com", "8611218773");
		Store store = session.getStore(urln);
		try {
			store.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		POP3Folder folder = (POP3Folder) store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY); // 默认关闭
		Message messages[] = folder.getMessages();
		// message = messages[messages.length-1];
		// System.out.println(messages.length);
		// System.out.println(folder.getUID(message));
		// for(int i=0;i<messages.length;i++){
		// if("GmailId12d73bb1f14bdff1".equals(folder.getUID(messages[i]))){
		// System.out.println("============="+i);
		// message = messages[i];
		// }
		// }
		message = messages[messages.length - 4];
		for (int i = 0; i < messages.length; i++) {
			System.out.println(EmailParserImpl.getInstance().getMailAddress(
					messages[i], "to"));
		}
		// TreeMap<String,String> map = new
		// Pop3ProviderImpl().getAllUidMap((POP3Folder)folder);
		/*
		 * for(Message msg : messages){
		 * if(folder.getUID(msg).equalsIgnoreCase("GmailId12c9be7a831319cd")){
		 * try{ // System.out.println(parser.getSubject(msg)); //
		 * System.out.println(parser.getBodyText(msg));
		 * System.out.println(msg.getContentType());
		 * System.out.println(msg.getSubject()); if(msg.getContentType()==null){
		 * System.out.println("conentType is null!!"); }
		 * System.out.println(getString(msg.getContentType().getBytes())); Part
		 * p = (Part)msg.getContent(); if(msg.getContent() instanceof
		 * InputStream){ System.out.println("is InputStream"); }else
		 * if(p.getContent() instanceof String){
		 * System.out.println(getString(msg.getContentType().getBytes())); }
		 * 
		 * 
		 * // String subject = null; // try { // if(msg.getSubject()!=null){ //
		 * subject = MimeUtility.decodeText(msg.getSubject()); // } // } catch
		 * (Exception e) { // if
		 * (msg.getSubject().toLowerCase().indexOf("utf-7") != -1) { // subject
		 * = new EncodeHandler().parseMsgSubject(msg); // } else { // if
		 * (e.getMessage().startsWith("Unknown encoding")) { //
		 * msg.removeHeader("content-transfer-encoding"); // subject =
		 * msg.getSubject() == null ? "" : MimeUtility //
		 * .decodeText(msg.getSubject()); // } // } // }
		 * 
		 * 
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } }
		 */
		// }
	}

	public static String getString(byte[] bytes) {

		if (bytes == null)
			return null;
		if (bytes.length == 0)
			return "";
		String encoding = "UTF-8";
		InputStream is = new ByteArrayInputStream(bytes);
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(JChardetFacade.getInstance());
		Charset charset = null;
		try {
			charset = detector.detectCodepage(is, Integer.MAX_VALUE);
		} catch (Exception e) {
		}
		if (charset != null) {
			encoding = charset.name();
		}
		System.out.println(charset);
		String str = null;
		try {
			str = new String(bytes, encoding);
		} catch (UnsupportedEncodingException e) {
		}
		return str;
	}

	public static void tearDown() throws Exception {

	}

	public void testGetFrom() throws Exception {
		System.out.println("from:"
				+ new String(parser.getFrom(message).getBytes("iso8859-1"),
						"utf-8"));
	}

	public void testGetMailAddress() throws Exception {
		System.out.println(parser.getMailAddress(message, "to"));
		System.out.println(parser.getMailAddress(message, "cc"));
		System.out.println(parser.getMailAddress(message, "bcc"));
	}

	@Test
	public void testGetSubject() throws Exception {
		System.out.println("Subject=========:" + parser.getSubject(message));
	}

	public void testGetReplySign() throws Exception {
	}

	public void testGetMessageId() throws Exception {
	}

	@Test
	public void testGetBodyText() throws Exception {
		String body = parser.getBodyText(message);
		System.out.println("Sleep:==============");
		// Thread.sleep(10000l);
		List<Clickoo_message_attachment> attachList = parser
				.getAttachList(message);
		System.out.println("AttachNum:" + (attachList.size()));
		if (attachList.size() > 0) {
			for (int i = 0; i < attachList.size(); i++) {
				String name = attachList.get(i).getName();
				System.out.println("name:" + name);
				System.out.println("length:" + attachList.get(i).getLength());
				// System.out.println("========"+new
				// String(attachList.get(i).getIn(),"gbk"));
			}
		}
		System.out.println("Body==========:" + "\n" + body);
		assertNotNull("error", body);

	}

	@Test
	public void testGetAttachList() throws Exception {
		List<Clickoo_message_attachment> attachList = parser
				.getAttachList(message);
		System.out.println("AttachNum:" + (attachList.size()));
		if (attachList.size() > 0) {
			for (int i = 0; i < attachList.size(); i++) {
				String name = attachList.get(i).getName();
				System.out.println("name:" + name);
				System.out.println("length:" + attachList.get(i).getLength());
				// System.out.println(attachList.get(i).getIn());
				// System.out.println("========"+new
				// String(attachList.get(i).getIn(),"gbk"));
			}
		}

		List<Clickoo_message_attachment> attachList1 = parser
				.getAttachList(message);
		System.out.println("AttachNum:" + (attachList1.size()));
		if (attachList.size() > 0) {
			for (int i = 0; i < attachList1.size(); i++) {
				String name = attachList1.get(i).getName();
				System.out.println("name:" + name);
				System.out.println("length:" + attachList1.get(i).getLength());
				// System.out.println("========"+new
				// String(attachList.get(i).getIn(),"gbk"));
			}
		}
		assertNotNull("error", attachList);
	}

	@Test
	public void testGetSendDate() throws Exception {
		System.out.println("senddate=============="
				+ parser.getSendDate(message));
	}

}
