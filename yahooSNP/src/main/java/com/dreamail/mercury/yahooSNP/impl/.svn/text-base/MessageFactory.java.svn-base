package com.dreamail.mercury.yahooSNP.impl;

public class MessageFactory {
	StringBuffer sb = new StringBuffer();

	/**
	 * subscription request:subscription
	 * 
	 * @param wssid
	 * @param pid
	 * @param timestamp
	 * @param notifyURL
	 * @return
	 */
	public String message_subscribe(String wssid, String pid, String timestamp,
			String notifyURL, String eventgroup, long aid) {
		sb.append("<?xml version='1.0'?>");
		sb
				.append("<s:request xmlns:s='urn:yahoo:mob:alert:subscription' wssid='"
						+ wssid
						+ "' ver='2.1' pid='"
						+ pid
						+ "' ts='"
						+ timestamp + "'>");
		sb.append("<s:mailsubscription>");
		sb.append("<s:subscribe notifyURL='" + notifyURL + "' notifyInfo='"
				+ aid + "'>");
		sb.append("<s:events>");
		sb.append("<s:mailevent eventgroup='" + eventgroup + "' " + "devid=''/>");
		sb.append("</s:events>");
		sb.append("</s:subscribe>");
		sb.append("</s:mailsubscription>");
		sb.append("</s:request>");
		return sb.toString();
	}

	/**
	 * unsubscription request:unsubscription
	 * 
	 * @param pid
	 * @param timestamp
	 * @param userId
	 * @param subId
	 * @return
	 */
	public String message_unsubscribe(String pid, String timestamp,
			String userId, long subId) {
		sb.append("<?xml version='1.0'?>");
		sb
				.append("<s:request xmlns:s='urn:yahoo:mob:alert:subscription' ver='2.1' pid='"
						+ pid + "' ts='" + timestamp + "'>");
		sb.append("<s:mailsubscription tag='tag'>");
		sb.append("<s:unsubscribe userId='" + userId + "' subId='" + subId
				+ "'/>");
		sb.append("</s:mailsubscription>");
		sb.append("</s:request>");
		return sb.toString();
	}
}
