package com.dreamail.mercury.dal.service;

import org.junit.Test;

import com.dreamail.mercury.dal.service.MessageService;

public class MessageServiceTest {
	@Test
	public void testProduceEmailCacheObject() {
		System.out.println(new MessageService().produceEmailCacheObject("18")
				.getMail_date());
	}
}
