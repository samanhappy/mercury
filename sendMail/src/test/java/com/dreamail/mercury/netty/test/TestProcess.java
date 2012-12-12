package com.dreamail.mercury.netty.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.dreamail.mercury.smtp.SmtpSession;
import com.dreamail.mercury.smtp.impl.AbstractProcess;
import com.dreamail.mercury.smtp.impl.AuthLoginProcess;
import com.dreamail.mercury.smtp.impl.DataProcess;
import com.dreamail.mercury.smtp.impl.RcptProcess;
import com.dreamail.mercury.smtp.utils.SmtpEmail;
import com.dreamail.mercury.smtp.utils.Tools;
import com.dreamail.mercury.smtp.utils.User;
import com.dreamail.mercury.util.EmailUtils;

public class TestProcess {
	
	@Test
	public void testBase64() throws UnsupportedEncodingException{
		
		String str = "YXJjaGVybWluZDE2M0AxNg==";
//		String qpStr = "=E5=A4=8D=E5=90=88=E8=82=A5=E5=A4=A9=E8=8A=B1=E6=9D=BF";
//		System.out.println( Tools.changeQPToString(qpStr));
		
		byte[]byArray = EmailUtils.sunChangeBase64ToByte(str);
		StringBuilder sb = new StringBuilder();
		for(byte b : byArray){
			char c= (char)b;
			sb.append(c);
		}
		System.out.println(sb.toString());
		System.out.println(new String(byArray));
		
	}
	
	@Test
	public void testDateProcess(){

		List<String> list = new ArrayList<String>();
//		list.add("EHLO [10.144.237.148]");
//		list.add("250-AUTH LOGIN PLAIN XYMCOOKIE");
//		list.add("AUTH PLAIN AGthaV9saV9taW5kXzJAeWFob28uY29tAGFyY2hlcm1pbmQ=");
//		list.add("MAIL FROM:<kai_li_mind_2@yahoo.com>");
//		list.add("RCPT TO:<archermind163@163.com>");
//		list.add("DATA");
//		list.add("Date: Mon, 12 May 2011 16:07:04 +0800");
//		list.add("From: kai_li_mind_2@yahoo.com");
////		list.add("To: =?UTF-8?B?YXJjaGVybWluZDE2M0AxNg==?= <archermind163@163.com>");
//		list.add("To: =?UTF-8?B?YXJjaGVybWluZDE2M0AxNg==?= <archermind163@163.com>,");
//		list.add("  =?UTF-8?B?YXJjaGVybWluZDE2M0AxNg==?= <archermind163@163.com>");
//		list.add("Subject: =?UTF-8?B?aGVsbG8=?=");
//		list.add("Message-ID: <20110512160704.GI129@10.117.49.201>");
//		list.add("boundary=\"----jTKB3U072OBlFpCg\"");
//		list.add("------jTKB3U072OBlFpCg");
//		list.add("Content-Type: text/plain; charset=UTF-8");
//		list.add("Content-Transfer-Encoding: 8bit");
//		list.add("dhhfhfhhff");
//		list.add("------jTKB3U072OBlFpCg");
//		list.add("Content-Type: text/plain;");
//		list.add("	name=\"=?UTF-8?B?MTI5NF/nrKzkuIDmnKzlv6vkuZDlv4PnkIblraYudHh0?=\"");
//		list.add("Content-Transfer-Encoding: base64");
//		list.add("	filename=\"=?UTF-8?B?MTI5NF/nrKzkuIDmnKzlv6vkuZDlv4PnkIblraYudHh0?=\"");
//		list.add("77+977+977+916Hvv73vv73Vvu+/ve+/vda377+977+9aHR0cDovL3IuYm9vazExOC5jb23vv73v");
//		list.add("v71odHRwOi8vd3d3LmJvb2sxMTguY29tDQoNCjzvv73vv73Su++/ve+/ve+/ve+/ve+/ve+/ve+/");
//		list.add("------jTKB3U072OBlFpCg--");
//		list.add(".");
		
		
		list.add("EHLO [10.144.237.148]");
		list.add("250-AUTH LOGIN PLAIN XYMCOOKIE");
		list.add("AUTH PLAIN AGthaV9saV9taW5kXzJAeWFob28uY29tAGFyY2hlcm1pbmQ=");
		list.add("MAIL FROM:<kai_li_mind_2@yahoo.com>");
		list.add("RCPT TO:<archermind163@163.com>");
		list.add("DATA");
		list.add("Date: Mon, 12 May 2011 16:07:04 +0800");
		list.add("From: kai_li_mind_2@yahoo.com");
//		list.add("To: =?UTF-8?B?YXJjaGVybWluZDE2M0AxNg==?= <archermind163@163.com>");
		list.add("To: archermind163@163.com,archermind163@163.com");
//		list.add("  archermind163@163.com");
		list.add("Subject: =?UTF-8?B?aGVsbG8=?=");
		list.add("Message-ID: <20110512160704.GI129@10.117.49.201>");
		list.add("boundary=\"----jTKB3U072OBlFpCg\"");
		list.add("------jTKB3U072OBlFpCg");
		list.add("Content-Type: text/plain; charset=UTF-8");
		list.add(" Content-Transfer-Encoding: quoted-printable");
		list.add(" This is a MIME Message");
		list.add(" =E5=A4=8D=E5=90=88=E8=82=A5=E5=A4=A9=E8=8A=B1=E6=9D=BF");
		list.add("------jTKB3U072OBlFpCg");
		list.add("Content-Type: text/plain;");
		list.add("	name=\"=?UTF-8?B?MTI5NF/nrKzkuIDmnKzlv6vkuZDlv4PnkIblraYudHh0?=\"");
		list.add("Content-Transfer-Encoding: base64");
		list.add("	filename=\"sky.jpg\"");
		list.add("77+977+977+916Hvv73vv73Vvu+/ve+/vda377+977+9aHR0cDovL3IuYm9vazExOC5jb23vv73v");
		list.add("v71odHRwOi8vd3d3LmJvb2sxMTguY29tDQoNCjzvv73vv73Su++/ve+/ve+/ve+/ve+/ve+/ve+/");
		list.add("------jTKB3U072OBlFpCg--");
		list.add(".");
		
//		DataProcess dp = new DataProcess();
//		SmtpSession ss = new SmtpSession();
//		User user = new User();
//		user.setUserName("likai_01@yahoo.comarchermin");
//		ss.setUser(user);
//		SmtpEmail se= dp.parseCommandList(list,ss);
//		System.out.println("---"+se.getSubject());
//		System.out.println("---"+se.getTo());
//		System.out.println("----"+se.getBody().getDatatype());
//		System.out.println(se.getAttach().get(0).getName());
//		String s = "=E5=A4=8D=E5=90=88=E8=82=A5=E5=A4=A9=E8=8A=B1=E6=9D=BF";
		System.out.println(Tools.changeQPToString("=E5=A4=8D=E5=90=88=E8=82=A5=E5=A4=A9=E8=8A=B1=E6=9D=BF"));
		DataProcess dp = new DataProcess();
		SmtpSession ss = new SmtpSession();
		User user = new User();
		user.setUserName("likai_01@yahoo.comarchermin");
		ss.setUser(user);
		SmtpEmail se = new SmtpEmail();
		se.setCc("archermind163@163.com,archermind163@163.com");
		ss.setSmtpEmail(se);
		for(String command:list){
			
			if(dp.tag(command, ss)){
				try {
					dp.parser(command);
					dp.proces(command, ss);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
	}
	@Test
	public void testStringParser(){
		String s1 = "	name=\"=?UTF-8?B?MTI5NF/nrKzkuIDmnKzlv6vkuZDlv4PnkIblraYudHh0?=\"";
		String s2 = StringUtils.substringBetween(s1, "\"", "\"");
		String[] s3 = s2.split("[?]");
		String s4 =  new AbstractProcess().changeBase64ToString(s3[3]);
		Map<String, String> map = new HashMap<String, String>();
		System.out.println(s3[3]);
		map.put("aa", s4);
		System.out.println(map.get("aa"));
	}
	
	@Test
	public void testUserName(){
		String str = "AGthaV9saV9taW5kXzJAeWFob28uY29tAGFyY2hlcm1pbmQ=";
		User user = new User();
		byte[]byArray = EmailUtils.sunChangeBase64ToByte(str);
		String userNameUTF8 = new String(byArray);
		if(userNameUTF8.contains("@yahoo.com")){
			String[] type = userNameUTF8.split("@yahoo.com");
			user.setUserName(type[0].trim()+"@yahoo.com");
			user.setSuffix("yahoo.com");
			user.setPassword(type[1].trim());
			}
		System.out.println(user.getUserName());
		System.out.println(user.getPassword());
		System.out.println(user.getSuffix());
	}
	
	@Test
	public void testAuthLogin(){
		List<String> list = new ArrayList<String>();
		list.add("AUTH LOGIN");
		list.add("a2FpX2xpX21pbmRfMkB5YWhvby5jb20=");
		list.add("YXJjaGVybWluZA==");
		list.add(" From: kai_li_mind_2@yahoo.com");
		AuthLoginProcess ap = new AuthLoginProcess();
		SmtpSession ss = new SmtpSession();
		User user = new User();
		user.setUserName("likai_01@yahoo.comarchermin");
		ss.setUser(user);
		for(String command:list){
			
			if(ap.tag(command, ss)){
				try {
					ap.parser(command);
					ap.proces(command, ss);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
	}
	
	@Test
	public void testRCPT(){
		List<String> list = new ArrayList<String>();
		list.add(" RCPT TO:<archermind163@163.com>");
		list.add(" RCPT TO:<archermind126@126.com>");
		list.add(" RCPT TO:<lwl1988614@163.com>");
		list.add(" RCPT TO:<archermind2011@gmail.com>");
		RcptProcess rp = new RcptProcess();
		SmtpSession ss = new SmtpSession();
		User user = new User();
		user.setUserName("likai_01@yahoo.comarchermin");
		ss.setUser(user);
		for(String command:list){
			
			if(rp.tag(command, ss)){
				try {
					rp.parser(command);
					rp.proces(command, ss);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
	}
}
