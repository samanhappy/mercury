package com.dreamail.mercury.receiver.mail.parse.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.event.FolderAdapter;
import javax.mail.event.FolderEvent;
import javax.mail.event.MessageCountAdapter;
import javax.mail.internet.InternetAddress;

import org.junit.AfterClass;
import org.junit.Test;

import com.dreamail.mercury.mail.receiver.parser.EmailParser;
import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserImpl;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

public class EmailParserImplTest {

	public static Date descRegisterDate() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH) - 1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	@Test
	public void setup1() throws Exception {
		IMAPStore store = null;
		IMAPFolder inbox = null;
		try {
			Properties props = System.getProperties();
			// props.setProperty("mail.imap.port", "143");
			props.put("mail.smtp.auth", "true");
			props.setProperty("mail.imap.port", "993");
			// props.setProperty("mail.imap.class",
			// "com.sun.mail.imap.IMAPStore");
			// props.setProperty("mail.imap.partialfetch", "false");
			// MyAuthenticator auth = new
			// MyAuthenticator("kai_li_mind_1@yahoo.com", "archermind");
			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);
			// store = (IMAPStore) session.getStore("imap");
			store = (IMAPStore) session.getStore("imaps");
			((IMAPStore) store)
					.SetIDCommand("ID (\"v3endor\" \"Zimbra\" \"os\" \"Windows XP\" \"os-version\" \"5.1\" \"guid\" \"4062-5711-9195-4050\")");

			try {
				// store.connect("imap.qq.com", "694150471@qq.com",
				// "8611218773 ");
				// store.connect("imap.gmail.com", "wpk1901@gmail.com",
				// "   8611218773  ");
				store.connect("imap.mail.yahoo.com", "kai_li_mind_1@yahoo.com",
						"archermind");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			inbox = (IMAPFolder) store.getFolder("inbox");
			inbox.open(Folder.READ_WRITE);
			Message[] message = inbox.getMessages();
			System.out.println(EmailParserImpl.getInstance().getBodyText(message[message.length - 1]));
			/*inbox.delete(true);
			if (!inbox.exists()) {
				inbox.create(Folder.HOLDS_MESSAGES);
			}
			inbox = (IMAPFolder) store.getDefaultFolder();
			inbox = (IMAPFolder) store.getFolder("[Gmail]");
			for (Folder f : inbox.list()) {
				System.out.println("===========" + f.getName());
			}

			inbox = (IMAPFolder) store.getFolder("d");
			inbox.open(Folder.READ_WRITE);
			Message[] message = inbox.getMessages();
			System.out.println(message[message.length - 1].getSubject() + "==="
					+ inbox.getUID(message[message.length - 1]));
			System.out.println("message.length:" + message.length);
			for (int i = 0; i < message.length; i++) {
				System.out.println("uuid:" + i + "==="
						+ inbox.getUID(message[i]));
			}
			IMAPFolder inbox1 = (IMAPFolder) store.getFolder("b");
			inbox1.open(Folder.READ_WRITE);
			inbox1.appendUIDMessages(message);
			inbox.copyMessages(message, inbox1);
			inbox1.close(false);
			inbox1.delete(true);
			inbox.close(false);
			inbox.renameTo(inbox1);
			Message[] message1 = inbox1.getMessages();
			System.out.println("共有" + inbox1.getMessageCount());
			System.out.println(message1[message1.length - 1].getSubject()
					+ "===" + inbox1.getUID(message1[message1.length - 1]));
			for (Message m : message1) {
				System.out.println(inbox1.getUID(m));
			}*/
			/*
			 * EmailParser p = EmailParserImpl.getInstance(); int sendNum = 0;
			 * int inboxNum = 0; for(Message msg : message){
			 * System.out.println(msg.getSubject());
			 * if(isSendMsg(msg,"jin123855@gmail.com",p)){ sendNum =sendNum+1; }
			 * else{ inboxNum=inboxNum+1; } }
			 */
			// System.out.println("发件箱的有"+sendNum);
			// System.out.println("收件箱的有"+inboxNum);

			// inbox.addMessageCountListener(new MessageCountAdapter() {
			// public void messagesAdded(javax.mail.event.MessageCountEvent e) {
			// System.out.println(e.getMessages().length);
			// }
			//
			// public void messagesRemoved(javax.mail.event.MessageCountEvent e)
			// {
			// System.out.println(e.getMessages().length);
			// }
			// });
			// store.addFolderListener(new FolderAdapter() {
			//
			// @Override
			// public void folderCreated(FolderEvent e) {
			// System.out.println("folder create:"
			// + e.getFolder().getFullName());
			//
			// }
			//
			// @Override
			// public void folderDeleted(FolderEvent e) {
			// // TODO Auto-generated method stub
			// System.out.println("folder delete:"
			// + e.getFolder().getFullName());
			// }
			//
			// @Override
			// public void folderRenamed(FolderEvent e) {
			// System.out.println("folder rename:"
			// + e.getFolder().getFullName());
			//
			// }
			// });
			// // store.idle();
			// inbox.idle();

			// AndTerm term= new AndTerm(new SearchTerm[] { new
			// Recei(ComparisonTerm.GT, descRegisterDate()) });
			// Message[] msg = inbox.getMessages();
			// System.out.println(msg.length);
			// message = inbox.getMessageByUID(316);
			// int msgLength = msg.length;
			// System.out.println("message number:"+msgLength);
			// System.out.println("msgId:"+inbox.getUID(msg[msgLength-1]));
			// message = (MimeMessage)msg[msgLength-1];
			// IMAPProtocol protocol = new IMAPProtocol("imap",
			// "imap.mail.yahoo.com", 143,
			// true,
			// session.getDebugOut(),
			// session.getProperties(),false);
			// protocol.command("ID (\"v3endor\" \"Zimbra\" \"os\" \"Windows XP\" \"os-version\" \"5.1\" \"guid\" \"4062-5711-9195-4050\")",
			// null);
			// protocol.command("LOGIN kai_li_mind_2@yahoo.com archermind",
			// null);
			// protocol.command("SELECT INBOX", null);
			// int[] pro = protocol.search(term);
			// System.out.println(pro.length+":"+pro[msgLength-1]);
			// protocol.command("FETCH "+pro[msgLength-1]+" UID", null);
			// Response[] r =
			// protocol.command("FETCH "+pro[msgLength-1]+" BODYSTRUCTURE",
			// null);
			// Response re = new Response(protocol);
			// System.out.println(re.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean isSendMsg(Message msg, String accountName, EmailParser parser)
			throws Exception {
		boolean sendFlag = false;
		InternetAddress address[] = (InternetAddress[]) msg.getFrom();
		String from = address[0].getAddress();
		// accountName = accountName.trim().substring(7);
		if (!"".equals(from) && from != null) {
			if (from.indexOf(accountName) != -1) {
				String to = parser.getMailAddress(msg, "TO");
				String cc = parser.getMailAddress(msg, "CC");
				String bcc = parser.getMailAddress(msg, "BCC");
				if (to.indexOf(accountName) == -1
						&& cc.indexOf(accountName) == -1
						&& bcc.indexOf(accountName) == -1) {
					sendFlag = true;
				}
			}
		}
		return sendFlag;
	}

	@AfterClass
	public static void tearDown() throws Exception {

	}

	@Test
	public void testGetMessageId() throws Exception {
		// System.out.println("MessageId:"+parser.getMessageId(message));
		// assertNotNull("error", parser.getMessageId(message));
	}

	@Test
	public void testGetFrom() throws Exception {
		// System.out.println("From:" + parser.getFrom(message));
		// assertNotNull("error", parser.getFrom(message));
	}

	@Test
	public void testGetMailAddress() throws Exception {
		// System.out.println("TO:" + parser.getMailAddress(message, "To"));
		// System.out.println("CC:" + parser.getMailAddress(message, "CC"));
		// System.out.println("BCC:" + parser.getMailAddress(message, "BCC"));
		// assertNotNull("error", parser.getMailAddress(message, "To"));
		// assertNotNull("error", parser.getMailAddress(message, "CC"));
		// assertNotNull("error", parser.getMailAddress(message, "BCC"));
	}

	@Test
	public void testGetSubject() throws Exception {
		// System.out.println("Subject:" + parser.getSubject(message));
		// assertNotNull("error", parser.getSubject(message));
	}

	@Test
	public void testGetSendDate() throws Exception {
		// System.out.println("SendDate:" + parser.getSendDate(message));
		// assertNotNull("error", parser.getSendDate(message));
	}

	@Test
	public void testGetBodyText() throws Exception {
		// System.out.println("Body:" + "\n" + parser.getBodyText(message));
	}

	@Test
	public void testGetReplySign() throws Exception {
		// System.out.println("Reply:" + parser.getReplySign(message));
		// assertNotNull("error", parser.getReplySign(message));
	}

	@Test
	public void testGetAttachList() throws Exception {
		// System.out.println("--------------");
		// List<Clickoo_message_attachment> attachList = parser
		// .getAttachList(message);
		// System.out.println("AttachNum:" + (attachList.size()));
		// if (attachList.size() > 0) {
		// for (int i = 0; i < attachList.size(); i++) {
		// System.out.println("name:" + attachList.get(i).getName());
		// System.out.println("length:" + attachList.get(i).getLength());
		// System.out.println("type:" + attachList.get(i).getType());
		// }
		// }
		// assertNotNull("error", attachList);
	}

}
