package com.clickoo.clickooImap.testnetty;

import com.clickoo.clickooImap.netty.client.MsgClient;

public class TestClient {
	public static void main(String[] args) {
		new MsgClient().start("localhost");
	}

}
