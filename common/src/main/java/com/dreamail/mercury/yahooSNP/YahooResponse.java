package com.dreamail.mercury.yahooSNP;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("response")
public class YahooResponse {

	@XStreamAlias("xmlns:s")
	@XStreamAsAttribute
	private String xmlns;
	
	@XStreamAsAttribute
	private String ver;
	
	@XStreamAsAttribute
	private String retCode;
	
	@XStreamAsAttribute
	private String message;

	private YahooMailSubscription mailsubscription;
	
	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getRetCode() {
		return retCode;
	}

	public YahooMailSubscription getSubscription() {
		return mailsubscription;
	}

	public void setSubscription(YahooMailSubscription subscription) {
		this.mailsubscription = subscription;
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
	
	
}
