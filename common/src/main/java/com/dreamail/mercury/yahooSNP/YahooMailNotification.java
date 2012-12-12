package com.dreamail.mercury.yahooSNP;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("mailnotification")
public class YahooMailNotification {

	@XStreamAsAttribute
	private String tag;
	
	@XStreamAsAttribute
	private String subId;
	
	@XStreamAsAttribute
	private String notifyInfo;
	
	private YahooMessageInsert message_insert;
	
	private YahooMessageRead message_read;
	
	private YahooMessageDelete message_delete;
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getNotifyInfo() {
		return notifyInfo;
	}

	public void setNotifyInfo(String notifyInfo) {
		this.notifyInfo = notifyInfo;
	}

	public YahooMessageInsert getMessage_insert() {
		return message_insert;
	}

	public void setMessage_insert(YahooMessageInsert messageInsert) {
		message_insert = messageInsert;
	}

	public YahooMessageRead getMessage_read() {
		return message_read;
	}

	public void setMessage_read(YahooMessageRead messageRead) {
		message_read = messageRead;
	}

	public YahooMessageDelete getMessage_delete() {
		return message_delete;
	}

	public void setMessage_delete(YahooMessageDelete messageDelete) {
		message_delete = messageDelete;
	}


}
