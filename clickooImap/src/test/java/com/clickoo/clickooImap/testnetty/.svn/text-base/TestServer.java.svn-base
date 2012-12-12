package com.clickoo.clickooImap.testnetty;

import org.junit.Test;

import com.clickoo.clickooImap.netty.server.MsgServer;
import com.clickoo.clickooImap.netty.server.MsgServerHandler;

public class TestServer {
//	public static void main(String[] args) throws InterruptedException {
//		new Thread(new MsgServer()).start();
//		
//		Thread.currentThread().sleep(10000);
//		tetsMessageMessageSent("aaaaaaaaaaaa");
//		tetsMessageMessageSent("bbbbbbbbbbbb");
//		tetsMessageMessageSent("cccccccccccc");
//		
//		System.out.println("-------------");
//	}
	@Test
	public static void tetsMessageMessageSent(String msg){
		String SocketIp = "aaaaa";
		try {
			MsgServerHandler.messageSent(SocketIp, msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new MsgServer().start();
	}
}
