package com.dreamail.mercury.petasos.impl;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;

public class AbstractFunctionUserTest {
	AbstractFunctionUser functionUser = new AbstractFunctionUser() {

		@Override
		public String getMethodName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
				throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
	};

//	@Test
//	public void testLoadSendConfig() {
//		String protocolType = "test";
//		WebAccount account = new WebAccount();
//		account.setReceiveProtocolType(protocolType);
//		account.setName("test@test.com");
//		ReceiveServerCacheObject receiveServerCacheObject = new ReceiveServerCacheObject();
//		receiveServerCacheObject
//				.setInPath("{\"host\":\"10.11.1.188\",\"type\":\"\",\"protocoltype\":\"pop\",\"receivePort\":\"110\",\"supportalluid\":\"0\"}");
//		functionUser
//				.loadReceiveConfig(
//						account,
//						Arrays.asList(new ReceiveServerCacheObject[] { receiveServerCacheObject }));
//		assertEquals(protocolType, account.getReceiveProtocolType());
//	}

	@Test
	public void testLoadReceiveConfig() {
		fail("Not yet implemented");
	}

}
