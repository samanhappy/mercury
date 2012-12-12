package com.dreamail.mercury.yahooSNP;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;

public class YahooXStreamParser {
	
	private static XStream xstream = new XStream(new Dom4JDriver());
	
	private static Logger logger = LoggerFactory.getLogger(YahooXStreamParser.class);

	static {
		xstream.processAnnotations(YahooRequest.class);
		xstream.processAnnotations(YahooResponse.class);
		xstream.processAnnotations(YahooMailEvent.class);
		xstream.processAnnotations(YahooSubscribe.class);
		xstream.processAnnotations(YahooUnSubscribe.class);
		xstream.processAnnotations(YahooMailSubscription.class);
		
		xstream.processAnnotations(YahooNoticeRequest.class);
		xstream.processAnnotations(YahooMailNotification.class);
		xstream.processAnnotations(YahooMessageInsert.class);
		xstream.processAnnotations(YahooMessageRead.class);
		xstream.processAnnotations(YahooMessageDelete.class);
		xstream.processAnnotations(YahooMessage.class);
	}

	/**
	 * 把xml字符串转换成YahooResponse对象.
	 * 
	 * @param xml
	 * @return
	 */
	public static YahooResponse xml2YahooResponseMessage(String xml) {
		try {
			YahooResponse obj = (YahooResponse) xstream.fromXML(xml);
			return obj;
		} catch (Exception e) {
			logger.error("failed to parse xml to object", e);
			return null;
		}
		
	}
	
	/**
	 * 把xml字符串转换成YahooRequest对象.
	 * 
	 * @param xml
	 * @return
	 */
	public static YahooNoticeRequest xml2YahooNoticeRequestMessage(String xml) {
		try {
			YahooNoticeRequest obj = (YahooNoticeRequest) xstream.fromXML(xml);
			return obj;
		} catch (Exception e) {
			logger.error("failed to parse xml to object", e);
			return null;
		}
		
	}

	/**
	 * 把YahooRequest对象转换成xml字符串.
	 * 
	 * @param obj
	 * @return
	 */
	public static String yahooRequestMessage2Xml(YahooRequest obj) {
		return replaceBlank(xstream.toXML(obj));
	}

	/**
	 * 去掉标签之间的控制字符.
	 * 
	 * @param str
	 * @return
	 */
	private static String replaceBlank(String str) {
		Pattern p = Pattern.compile(">(\\s*|\t|\r|\n)<");
		Matcher m = p.matcher(str);
		return m.replaceAll("><");
	}
	
}
