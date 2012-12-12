package com.microsoft.ews.client;

import java.util.Date;
import java.util.HashMap;

import org.junit.Test;

import com.dreamail.mercury.cache.MsgCacheManager;
import com.dreamail.mercury.domain.Body;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.domain.WebAccount;

public class TestEWSValidate {

	@Test
	public void testValidate() {
		
		WebAccount account = new WebAccount();
		account.setName("mengsun@pushmail.com");
		account.setPassword("honey@123");
		account.setReceiveHost("admin-h1li7jsv2");
		
		EWSReceiveClient client = new EWSReceiveClient();
		System.out.println(client.validate(account));
	}
	
	@Test
	public void testReceiveMail() {
		new MsgCacheManager().init();
		
		Context context = new Context();
		context.setAccountId(49);
		context.setLoginName("mengsun@pushmail.com");
		context.setPassword("honey@123");
		context.setReceiveHost("admin-h1li7jsv2");
		
		context.setStatus("0");
		context.setStartMill(System.currentTimeMillis());
		context.setTimeout(60*1000);
		
		context.setRegistrationDate(new Date());
		
		context.setEmailList(new HashMap<String, Email>());
		
		new EWSReceiveClient().receiveMail(context);
		
		System.out.println(context.getEmailList().size());
	}
	
	
	@Test
	public void testReceiveMailByUUid() {
		WebAccount account = new WebAccount();
		account.setName("danwang@pushmail.com");
		account.setPassword("honey.123");
		account.setReceiveHost("admin-h1li7jsv2");
		
		String uuid = "CQAAABYAAAC/97XzMW91ToObN/fvJGhhAF5sgth4,AAAUAGRhbndhbmdAcHVzaG1haWwuY29tAEYAAAAAAIk7lpntgBtBri4PD9SovIgHAL/3tfMxb3VOg5s39+8kaGEAAALmMs8AAL/3tfMxb3VOg5s39+8kaGEAXmyC1AYAAA==";
		
		Email email = new EWSReceiveClient().getEmailByUuid(account, uuid, null, null);
		
		System.out.println(email.getReceiveTime());
	}
	
	@Test
	public void testGetMailBodyByUUid() {
		WebAccount account = new WebAccount();
		account.setName("mengsun@pushmail.com");
		account.setPassword("honey@123");
		account.setReceiveHost("admin-h1li7jsv2");
		
		String uuid = "CQAAABYAAAC/97XzMW91ToObN/fvJGhhAF5sgt2c,AAAUAG1lbmdzdW5AcHVzaG1haWwuY29tAEYAAAAAAAd2OK+PrA9Fp/RpwD5n8IgHAL/3tfMxb3VOg5s39+8kaGEAAALmXq0AAL/3tfMxb3VOg5s39+8kaGEAXmyCsXQAAA==";
		
		Body body = new EWSReceiveClient().getEmailBodyByUuid(account, uuid);
		
		System.out.println(body.getData());
	}
	
	@Test
	public void testGetAttByUuid() {
		
			WebAccount account = new WebAccount();
		account.setName("mengsun@pushmail.com");
		account.setPassword("honey@123");
		account.setReceiveHost("admin-h1li7jsv2");
		
//		String uuid = "CQAAABYAAAC/97XzMW91ToObN/fvJGhhAF5sgs7l,AAAUAG1lbmdzdW5AcHVzaG1haWwuY29tAEYAAAAAAAd2OK+PrA9Fp/RpwD5n8IgHAL/3tfMxb3VOg5s39+8kaGEAAALmXq0AAL/3tfMxb3VOg5s39+8kaGEAXmyCsW4AAA==";
		
//		Attachment att = new EWSReceiveClient().getAttachmentByUuid(account, uuid, "附件测试", 12);
//		System.out.println(att.getName() + att.getType() + new String(att.getIn()));
	}
}
