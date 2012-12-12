package com.dreamail.mercury.petasos.impl.email;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.domain.EmailCacheObject;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailattachment;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.util.EmailUtils;

public class EmailListTest {

	@Test
	public void testLoadMailHead() throws UnsupportedEncodingException {
		List<Object> results = new ArrayList<Object>();
		List<Clickoo_message> mailMsgs = new ArrayList<Clickoo_message>();
		//收邮件时间
		Calendar c = Calendar.getInstance();
		c.set(2011, 1, 1, 0, 0, 0);
		Clickoo_message cMsg = new Clickoo_message();
		cMsg.setIntime(c.getTime());
		mailMsgs.add(cMsg);
		cMsg = new Clickoo_message();
		c.add(Calendar.SECOND, 1);
		cMsg.setIntime(c.getTime());
		mailMsgs.add(cMsg);
		EmailList eailList = new EmailList() {
			@Override
			public WebEmailattachment[] getAttachments(Long mid) {
				return null;
			}
		};
		MessageService messageService = new MessageService() {
			@Override
			public EmailCacheObject getEmailCacheObject(String mid, boolean b) {
				EmailCacheObject cache = new EmailCacheObject();
				cache.setSubject("test");
				return cache;
			}
		};

		HashMap<String, String> meta = new HashMap<String, String>();

		eailList.loadMailHead(results, messageService, mailMsgs, meta, 10240);
		assertEquals(2, results.size());
		assertEquals(EmailUtils.changeByteToBase64("test".getBytes("UTF-8")),
				((WebEmail) results.get(0)).getHead().getSubject());
		assertEquals("2011-02-01 00:00:01", meta.get(EmailList.TIMEDATE_KEY));

		// 列表过大
		results.clear();
		meta = new HashMap<String, String>();
		eailList.loadMailHead(results, messageService, mailMsgs, meta, 141);
		assertEquals(1, results.size());
		assertEquals(EmailUtils.changeByteToBase64("test".getBytes("UTF-8")),
				((WebEmail) results.get(0)).getHead().getSubject());
		assertEquals("2011-02-01 00:00:00", meta.get(EmailList.TIMEDATE_KEY));
		
		//单封邮件过大
		results.clear();
		meta = new HashMap<String, String>();
		eailList.loadMailHead(results, messageService, mailMsgs, meta, 140);
		assertEquals(0, results.size());
	}
}
