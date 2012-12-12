package com.dreamail.mercury.mail.truepush.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.domain.WebAccount;

public class HotmailPingTest {
	@Test
	public void testStart() {
	}

	@Test
	public void testSendJMS() {
		new PropertiesDeploy().init();
		JmsSender.sendQueueMsg("{aid:45}", "hotmailPingQueue");

		// JmsSender.sendTopicMsg("{aid:45}", "removeTopic");
	}

	@Test
	public void testCalculateNextRetryTime() {
		WebAccount wbAccount = new WebAccount();
		wbAccount.setName("test");
		HotmailPing hp = new HotmailPing(wbAccount, null);
		long nowTime = System.currentTimeMillis();
		int nri = hp.getNextRetryInterval();
		hp.calculateNextRetryTime(nowTime);
		assertEquals(nowTime + nri * 1000, hp.getNextRetryTime());

		hp.calculateNextRetryTime(nowTime);
		assertEquals(nowTime + nri * 1000 * 2, hp.getNextRetryTime());

		hp.calculateNextRetryTime(nowTime);
		assertEquals(nowTime + nri * 1000 * 4, hp.getNextRetryTime());

		hp.calculateNextRetryTime(nowTime);
		assertEquals(nowTime + nri * 1000 * 8, hp.getNextRetryTime());

		hp.calculateNextRetryTime(nowTime);
		assertEquals(nowTime + nri * 1000 * 12, hp.getNextRetryTime());
	}

	public static void main(String[] args) {
		WebAccount wbAccount = new WebAccount();
		wbAccount.setName("pmsl_bmiv@hotmail.com");
		wbAccount.setPassword("198309300815");
		HotmailTruepush.getInstance().addAccount(wbAccount);
		System.out.println("over");
	}
}
