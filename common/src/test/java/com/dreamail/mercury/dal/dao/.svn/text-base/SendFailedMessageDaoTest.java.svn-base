package com.dreamail.mercury.dal.dao;

import java.util.List;

import org.junit.Test;

import com.dreamail.mercury.dal.dao.SendFailedMessageDao;
import com.dreamail.mercury.pojo.Clickoo_send_failed_message;

public class SendFailedMessageDaoTest {
	@Test
	public void testSaveSendFailedMessage() {
		Clickoo_send_failed_message message = new Clickoo_send_failed_message();
		for (int i = 0; i < 10; i++) {
			message.setMid(i);
			message.setOldmid(i);
			message.setHid(i);
			message.setRetrycount(0);
			message.setSendtype(0);
			message.setUid(i);
			System.out.println(new SendFailedMessageDao()
					.saveSendFailedMessage(message)
					+ "---------------------");
		}
	}

	@Test
	public void testDeleteSendFailedMessageByMids() {
		Long[] mids = { 1l, 2l, 3l, 4l, 5l };
		new SendFailedMessageDao().deleteSendFailedMessageByMids(mids);

	}

	@Test
	public void testDeleteSendFailedMessageByMid() {
		System.out.println(new SendFailedMessageDao()
				.deleteSendFailedMessageByMid("0"));
	}

	@Test
	public void testGetAllSendFailedMessage() {
		List<Clickoo_send_failed_message> list = new SendFailedMessageDao()
				.getAllSendFailedMessage();
		for (Clickoo_send_failed_message clickooSendFailedMessage : list) {
			System.out.println(clickooSendFailedMessage.getId());
		}
	}

	@Test
	public void testSelectSendFailedMessageByMd() {
		System.out.println(new SendFailedMessageDao()
				.selectSendFailedMessageByMd("8").getMid());
	}

	@Test
	public void testUpdateSentFailedMessageCount() {
		new SendFailedMessageDao().updateSentFailedMessageCount(50, 9);
	}

}
