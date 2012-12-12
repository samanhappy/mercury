package com.dreamail.mercury.receiver.mail.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dreamail.mercury.domain.Context;

public class YahooIMAP4ProviderImplTest {

	private Context c;
	private IMAP4ProviderImpl imap;
	
	@Before
	public void setUp() throws Exception {
		c = new Context();
		c.setLoginName("kai_li_mind_1@yahoo.com");
		c.setPassword("archermind");
		c.setReceiveHost("imap.mail.yahoo.com");
		c.setPort("143");
//		c.setUid(0);
		c.setReceiveTs("");
		imap = new IMAP4ProviderImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReceiveMail() throws Exception {
		imap.receiveMail(c);
		if(c.getEmailList()!=null && c.getEmailList().size()>0){
			System.out.println("账户"+c.getLoginName()+"收取邮件："+c.getEmailList().size());
		}
	}

}
