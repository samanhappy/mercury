package com.dreamail.mercury.talaria.http.handler;

public class IMHandler {

	/**
	 * 得到IM的响应内容.
	 * 
	 * @param IMEI
	 * @param xml
	 * @return
	 */
	public static String getIMResponseStr(String IMEI, String xml) {
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<UPE><IMEI>").append(IMEI).append("</IMEI>");
		sb.append(xml);
		sb.append("</UPE>");
		return sb.toString();
	}
	
	/**
	 * 得到错误的JMS响应内容.
	 * 
	 * @return
	 */
	public static String getIMJmsErrorResponseStr() {
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<im><code>500</code><status>Parse Error</status></im>");
		return sb.toString();
	}
}
