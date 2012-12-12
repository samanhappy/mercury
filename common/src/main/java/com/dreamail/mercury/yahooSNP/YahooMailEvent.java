package com.dreamail.mercury.yahooSNP;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("s:mailevent")
public class YahooMailEvent {

	@XStreamAsAttribute
	private String eventgroup;
	
	@XStreamAsAttribute
	private String devid;

	public String getEventgroup() {
		return eventgroup;
	}

	public void setEventgroup(String eventgroup) {
		this.eventgroup = eventgroup;
	}

	public String getDevid() {
		return devid;
	}

	public void setDevid(String devid) {
		this.devid = devid;
	}
	
	
}
