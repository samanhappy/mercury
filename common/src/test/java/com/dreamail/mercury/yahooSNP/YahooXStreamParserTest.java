package com.dreamail.mercury.yahooSNP;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dreamail.mercury.yahooSNP.YahooMailEvent;
import com.dreamail.mercury.yahooSNP.YahooMailSubscription;
import com.dreamail.mercury.yahooSNP.YahooNoticeRequest;
import com.dreamail.mercury.yahooSNP.YahooRequest;
import com.dreamail.mercury.yahooSNP.YahooResponse;
import com.dreamail.mercury.yahooSNP.YahooSubscribe;
import com.dreamail.mercury.yahooSNP.YahooXStreamParser;

public class YahooXStreamParserTest {

	@Test
	public void testXml2YahooResponseMessage() {
		String message = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><s:response   xmlns:s=\"urn:yahoo:mob:alert:subscription\"   ver=\"2.1\"   retCode=\"0\"   message=\"OK\" ><s:mailsubscription   tag=\"\"><s:subscribe   subId=\"3\"   email=\"kai_li_mind_2@yahoo.com\"   fullname=\"U3VuIE1lbmc-\"   retCode=\"0\"   message=\"OK\"/></s:mailsubscription></s:response><!-- snp-sub3.mobile.sp2.yahoo.com uncompressed/chunked Tue Mar 22 02:51:28 GMT 2011 -->";
		YahooResponse yrs = YahooXStreamParser
				.xml2YahooResponseMessage(message);

		System.out.println(yrs.getSubscription().getSubscribe().getMessage());
	}
	
	@Test
	public void testXml2YahooNoticeRequestMessage() {
		
		String message = "<?xml version=\"1.0\"?><n:request xmlns:n=\"urn:yahoo:mob:alert:notification\" ver=\"2.1\" pid=\"yahoo\" ts=\"1300763471\"><n:mailnotification tag=\"1\" subId=\"11\" notifyInfo=\"16\"><n:message_insert><n:msg uid=\"301\" folder=\"Inbox\"/></n:message_insert></n:mailnotification></n:request>";
		YahooNoticeRequest yrs = YahooXStreamParser
				.xml2YahooNoticeRequestMessage(message);

		System.out.println(yrs.getMailnotification().getMessage_insert().getMsg().getFolder());
	}

	@Test
	public void testYahooRequestMessage2Xml() {
		YahooRequest yr = new YahooRequest();
		yr.setPid("pid");
		yr.setTs("ts");
		yr.setVer("ver");
		yr.setWssid("wssid");
		yr.setXmlns("xmlns");

		YahooMailSubscription yms = new YahooMailSubscription();
		yms.setTag("tag");

		YahooSubscribe ys = new YahooSubscribe();
		ys.setNotifyURL("notifyURL");
		ys.setEmail("email");
		ys.setFullname("fullname");
		ys.setMessage("message");
		ys.setRetCode("retCode");
		ys.setSubId("subId");

		List<YahooMailEvent> list = new ArrayList<YahooMailEvent>();

		YahooMailEvent yme = new YahooMailEvent();
		yme.setDevid("devid");
		yme.setEventgroup("eventgroup");

		list.add(yme);
		ys.setEvents(list);
		yms.setSubscribe(ys);
		yr.setMailsubscription(yms);

		System.out.println(YahooXStreamParser.yahooRequestMessage2Xml(yr));
	}

}
