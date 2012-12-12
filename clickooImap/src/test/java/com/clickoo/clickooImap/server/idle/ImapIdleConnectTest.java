package com.clickoo.clickooImap.server.idle;

import org.junit.Test;
import com.clickoo.clickooImap.domain.IdleMessage;

public class ImapIdleConnectTest {
	@Test
	 public void testDoIdleConnect(){
		 IdleMessage idleMessage = new IdleMessage();
		 idleMessage.setAccountName("wpk1901@gmail.com");
		 idleMessage.setAccountPwd("8611218773");
		 ImapIdleConnect.doIdleConnect(idleMessage);
	 }

}
