package com.dreamail.mercury.util;

import net.sf.json.JSONObject;

import org.junit.Test;

public class JsonPluginTest {
	
	
	/**
	 * this is imitate in the account table inpath data
	 */
	@Test
	public void jsonInpath(){
		JSONObject obj = new JSONObject();
		obj.put("host", "imap.mail.yahoo.com");//根据host中的pop字符或imap字符来判断类型
		obj.put("type", "SSL");//或者是TLS,如果没有任何加密方式该属性为""
		obj.put("receivePort", "993");//或者是TLS的Port ,如果没有任何加密方式该属性为""
		System.out.println(obj.toString());
	}
	
	@Test
	public void jsonIncert(){
		JSONObject obj = new JSONObject();
		obj.put("loginID", "kai_li_mind@yahoo.com");
		obj.put("pwd", "85122971");
		System.out.println(obj.toString());
	}
	
	@Test
	public void jsonOutcert(){
		JSONObject obj = new JSONObject();
		obj.put("loginID", "kai_li_mind@yahoo.com");
		obj.put("pwd", "85122971");
		obj.put("byname", "kai.li");//别名
		System.out.println(obj.toString());
	}
	
	@Test
	public void jsonOutpath(){
		JSONObject obj = new JSONObject();
		obj.put("smtpServer", "mail.archermind.com");
		obj.put("tpye", "SSL");//或者是TLS,如果没有任何加密方式该属性为""
		obj.put("sendPort", "993");//或者是TLS的Port ,如果没有任何加密方式该属性为""
		System.out.println(obj.toString());
	}
	
	@Test
	public void receive_serverJsonInpath(){
		JSONObject obj = new JSONObject();
		obj.put("pop3host", "pop.mail.yahoo.com");//pop和imap二选一
		obj.put("imaphost", "imap.mail.yahoo.com");
//		obj.put("smtpPort", "");
		obj.put("pop3Port", "");//pop和imap端口号二选一
		obj.put("imapPort", "");
		obj.put("imapPort_ssl", "993");//收邮件的ssl端口号和tls二选一
		obj.put("imapPort_tls", "993");
		obj.put("popPort_ssl", "");
		obj.put("popPort_tls", "");
//		obj.put("smtp_ssl", "993");//二选一
//		obj.put("smtp_tls", "");
		System.out.println(obj.toString());
	}
}
