package com.dreamail.mercury.netty.test;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.util.EmailUtils;

public class FetchTest {
	@Test
	public void testTime(){
		//Thu, 24 Mar 2011 01:36:03 -0700
		Date date = new Date();
		System.out.println(date);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy",Locale.ENGLISH);
		System.out.println(sdf.format(date));
	}
	@Test
	public void testUTF8ToGBK(){
		byte [] b;
		String utf8_value = "kasfj士大夫是";
		System.out.println(utf8_value+"---------------utf-8");
		try {
			b = utf8_value.getBytes("UTF-8");
			String name = new String(b, "GB2312"); //转换成GB2312字符
			System.out.println(name+"-----------GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
	}
	@Test
	public void testSplit(){
		String s = "";
		String[] str = s.split(";");
		System.out.println(str[0].toString());
	}
	@Test
	public void testMessageDao(){
		Clickoo_message message = (Clickoo_message) new MessageDao().getMsgByUuidAid("1", "13");
		System.out.println(message);
	}
	@Test
	public void testString(){
		String s = "aaaaaa";
		try {
			System.out.println(EmailUtils.changeByteToBase64(s.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testReplace(){
		String s = " (((\"TEXT\" \"PLAIN\" (\"CHARSET\" \"UTF-8\")";
		System.out.println(s);
		System.out.println(s.replace("\"", ""));
	}
	@Test
	public void testSubString(){
		String str = "/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEB";
		long length = str.length();
		while (length > 0) {
			System.err.println(str.substring(0,7));
			str = str.substring(7);
			length -= 7;
		}
	}
	@Test
	public void testDate(){
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));;
		System.out.println(new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z (z)", Locale.US).format(new Date()));
	}
	@Test
	public void testLength(){
		String[] strs ={"Received: by 10.101.64.11 with SMTP id r11cs32607ank; Mon, 18 Apr 2011"
							," 02:28:04 -0700 (PDT)"
							,"Received: by 10.236.184.102 with SMTP id r66mr3208019yhm.25.1303118884046;"
							," Mon, 18 Apr 2011 02:28:04 -0700 (PDT)"
							,"Received: from nm21-vm0.bullet.mail.sp2.yahoo.com"
							," (nm21-vm0.bullet.mail.sp2.yahoo.com [98.139.91.220]) by mx.google.com with"
							," SMTP id 26si10886224ano.139.2011.04.18.02.28.03; Mon, 18 Apr 2011 02:28:04"
							," -0700 (PDT)"
							,"Received: from [98.139.91.61] by nm21.bullet.mail.sp2.yahoo.com with NNFMP; 18"
							," Apr 2011 09:28:03 -0000"
							,"Received: from [98.136.185.46] by tm1.bullet.mail.sp2.yahoo.com with NNFMP; 18"
							," Apr 2011 09:28:03 -0000"
							,"Received: from [127.0.0.1] by smtp107.mail.gq1.yahoo.com with NNFMP; 18 Apr"
							," 2011 09:28:03 -0000"
							,"Received: from [10.107.73.123] (kai_li_mind_2@117.136.19.136 with plain) by"
							," smtp107.mail.gq1.yahoo.com with SMTP; 18 Apr 2011 02:28:01 -0700 PDT"
							,"Date: Fri, 18 Apr 2011 17:27:01 +0800"
							,"From: kai_li_mind_2@yahoo.com"
							,"Subject: =?UTF-8?B?aWlpaWk=?="
							,"X-Priority: 3"
							,"\n" 
							,	")" 
							, "5 OK Success"
						};
		int length = 0;
		for (String string : strs) {
			System.out.println(string.length()+"-----------------------------------");
			length+=string.length();
		}
		System.out.println(length);
		System.out.println("sadfsdafsd"+"Content-Type: text/plain; charset=UTF-8Content-Transfer-Encoding: base64".length());
	}
	@Test
	public void testValidate(){
//		System.out.println(MailValidator.validateAccountConnection("kai_li_mind_3@yahoo.com", "archermind", "{\"host\":\"124.108.115.241,212.82.96.94,212.82.111.223,imap.mail.yahoo.com\",\"type\":\"SSL\",\"protocoltype\":\"imap\",\"receivePort\":\"993\"}", null));
	}
	@Test
	public void testSubstringBetween(){
//		System.out.println(4<2+1);
//		System.out.println("iiiij".substring(0, 5));
//		System.out.println("BODY.PEEK[3]<0.20480>".split("<")[1].replace(">","").split("[.]")[1]);
//		System.out.println(StringUtils.substringBetween("BODY.PEEK[3]<0.20480>", "<", ">").split("[.]")[0]);
		String s = "4 UID FETCH 1:* (UID FLAGS BODYSTRUCTURE BODY.PEEK[HEADER.FIELDS (Received Date Subject From Priority X-Priority X-MSMail-Priority Importance)])";
		System.out.println(s.split("4 UID FETCH 1:[*] ")[1]);
	}
	@Test
	public void testMatches(){
		if("1:*".matches("^[1-9][0-9]*[:][*]$")){
			System.out.println("11111111111111111111");
		}
	}
}
