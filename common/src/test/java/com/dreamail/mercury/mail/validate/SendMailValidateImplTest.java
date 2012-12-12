package com.dreamail.mercury.mail.validate;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dreamail.mercury.domain.TimeoutException;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.validate.impl.SendMailValidateImpl;

public class SendMailValidateImplTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidate() throws TimeoutException, UnsupportedEncodingException, MessagingException {
		WebAccount account = new WebAccount();
		account.setName("wpk1902@hotmail.com");
		account.setPassword("8611218773");
		account.setSendHost("smtp.live.com");
		account.setSendPort("587");
//		account.setSendTs("ssl");
		System.out.println(new SendMailValidateImpl().validate(account));
	}

}
