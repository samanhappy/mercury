/**
 * 
 */
package com.dreamail.mercury.dal.dao;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.pojo.Clickoo_message;

/**
 * @author meng.sun
 * 
 */
public class MessageDaoTest extends TestCase {

	@Test
	public void testCreateMessage() {
		// Clickoo_message message = new Clickoo_message(0, "subject",
		// "emailfrom", "emailto", "cc", "bcc", new Date(), new Date(), 0, "1",
		// "1", 0, "path",
		// "threadid", "uuid");
		// Clickoo_message_data data = new Clickoo_message_data(0, "hello你好吗",
		// 0);
		// Clickoo_message_attachment attachment = new
		// Clickoo_message_attachment(
		// 0, "附件.txt", "txt", 1024, "c://", 1, 1);
		// List<Clickoo_message_attachment> list = new
		// ArrayList<Clickoo_message_attachment>();
		// list.add(attachment);
		// message.setMessageData(data);
		// message.setAttachList(list);
		// MessageService dao = new MessageService();
		// long mid = dao.saveMessage(message);
		// assertTrue(mid != -1);
		// int size = dao.getAllReceivedMsgIdByAccountId(9l).size();
		// for(int i=0;i<size;i++){
		// System.out.println(dao.getAllReceivedMsgIdByAccountId(9l).get(i));
		// }
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
		// System.out.println(new MessageDao().getNewMessages(18l,
		// calendar.getTime()).size());
	}

	/*
	 * @Test public void testGetLatestInTimeByUid() { MessageService dao = new
	 * MessageService(); Date d = dao.getLatestInTimeByUid(0);
	 * System.out.println(new SimpleDateFormat().format(d)); }
	 */

	@Test
	public void testselectMessageByUid() {
//		MessageService dao = new MessageService();
//		Long id = Long.parseLong("98");
	}

	@Test
	public void testSaveMessage() {

		// Clickoo_message_data data = new Clickoo_message_data();
		// data.setData("你好");
		//		
		// Clickoo_message_attachment attach = new Clickoo_message_attachment();
		// attach.setName("附件");
		//		
		// List<Clickoo_message_attachment> attachList = new
		// ArrayList<Clickoo_message_attachment>();
		// attachList.add(attach);
		//		
		// Clickoo_message message = new Clickoo_message();
		// message.setBcc("a@archermind.com");
		// message.setMessageData(data);
		// message.setAttachList(attachList);
		// // message.setUid(23);
		// MessageService dao = new MessageService();
		// message.setAid("aid");
		// dao.saveMessage(message);
		// message.setAid(null);
		// dao.saveMessage(message);
		// message.setAid("aid");
		// dao.saveMessage(message);
	}

	@Test
	public void testUpdateMessageState() {
		Clickoo_message message = new Clickoo_message();
		message.setStatus(1);
		MessageService dao = new MessageService();
		dao.updateMessageState(message);
		message.setId(1);
		dao.updateMessageState(message);
	}

	@Test
	public void testGetReferencesByMessageId() {
		// MessageService dao = new MessageService();
		// System.out.println(dao.getReferencesByMessageId("<201007211451011692991@archermind.com>","1"));
	}

	/*
	 * @Test public void testGetAllReceivedMessageUUId() { MessageService dao =
	 * new MessageService();
	 * System.out.println(dao.getAllReceivedMessageId().size()); }
	 */

	@Test
	public void testGetAllReceivedMessageUUIdByAccountId() {
		MessageService dao = new MessageService();
		System.out.println(dao.getAllReceivedMessageIdByAccountId(1).size());
	}

	 @Test
	 public void testGetMessageListByAid() {
	 // MessageService dao = new MessageService();
	 // System.out.println(dao.getMessageListByAid("1").size());
	 }

	@Test
	public void testGetSentMessageListByAccountName() {
		// MessageService dao = new MessageService();
	}

	// @Test
	// public void testDeleteMessageByIds() {
	// MessageService dao = new MessageService();
	// dao.deleteMessagesByIds(new long[]{540,541});
	// }

	// @Test
	// public void testChangeUserId() {
	// new MessageDao().updateMessageState(163, 2, 1);
	// }

	// @Test
	// public void testContainsUuid(){
	// System.out.println(new MessageDao().containsUuid("22",
	// "GmailId12b142fc754f5e3d","wuli7388668@gmail.com"));
	// }

	@Test
	public void testGetAllUuid() {
		// System.out.println(new MessageDao().getUuidList("32",
		// "zm000979@gmail.com"));
	}

	@Test
	public void testGetFlagsByUuid() {
		System.out.println(new MessageDao().getFlagsByUuid("4",
				"GmailId12ee16d80231b3d4"));
	}

	@Test
	public void testGetEmailByUuidAid() {
		// Clickoo_message cm = new MessageDao().getEmailById(2);
		// System.out.println(cm.getAttachList());
		// Clickoo_message message = new
		// MessageDao().getMsgByUuidAid("GmailId12ef0acff401d7ac", "3");
		// System.out.println(message.getEmailFrom()+"--------------------------------");
		// List<Clickoo_message> messageList = (List<Clickoo_message>) new
		// MessageDao().getEmailByUuidAid("GmailId12ef0acff401d7ac", "3");
		// for (Clickoo_message clickooMessage : messageList) {
		// System.out.println(clickooMessage.getAid());
		// }
		Clickoo_message message = new MessageDao().getMsgByUuidAid(
				"GmailId12ef0acff401d7ac", "3");
		// Clickoo_message message = new
		// MessageDao().getMsgByUuidAid("GmailId12efb69252fd861e", "8");
		System.out.println(message.getAttachList().size()
				+ "--------------------------------");
	}
}
