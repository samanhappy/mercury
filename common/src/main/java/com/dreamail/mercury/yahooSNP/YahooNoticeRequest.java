package com.dreamail.mercury.yahooSNP;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("request")
public class YahooNoticeRequest {

	@XStreamAsAttribute
	@XStreamAlias("xmlns:n")
	private String xmlns;
	
	@XStreamAsAttribute
	private String wssid;
	
	@XStreamAsAttribute
	private String ver;

	@XStreamAsAttribute
	private String pid;

	@XStreamAsAttribute
	private String ts;

	private YahooMailNotification mailnotification;
	
	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getXmlns() {
		return xmlns;
	}

	public String getWssid() {
		return wssid;
	}

	public void setWssid(String wssid) {
		this.wssid = wssid;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public YahooMailNotification getMailnotification() {
		return mailnotification;
	}

	public void setMailnotification(YahooMailNotification mailnotification) {
		this.mailnotification = mailnotification;
	}

}
