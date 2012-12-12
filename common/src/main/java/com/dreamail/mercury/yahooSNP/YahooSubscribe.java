package com.dreamail.mercury.yahooSNP;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("s:subscribe")
public class YahooSubscribe {

	@XStreamAsAttribute
	private String subId;
	
	@XStreamAsAttribute
	private String email;
	
	@XStreamAsAttribute
	private String fullname;
	
	@XStreamAsAttribute
	private String retCode;
	
	@XStreamAsAttribute
	private String message;
	
	@XStreamAsAttribute
	private String notifyURL;
	
	@XStreamAsAttribute
	private String notifyInfo="opaque-data";
	
	@XStreamAlias("s:events")
	private List<YahooMailEvent> events;

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public String getNotifyInfo() {
		return notifyInfo;
	}

	public void setNotifyInfo(String notifyInfo) {
		this.notifyInfo = notifyInfo;
	}

	public List<YahooMailEvent> getEvents() {
		return events;
	}

	public void setEvents(List<YahooMailEvent> events) {
		this.events = events;
	}
	
}
