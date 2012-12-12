package com.dreamail.mercury.yahooSNP;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("s:mailsubscription")
public class YahooMailSubscription {

	@XStreamAsAttribute
	private String tag;
	
	@XStreamAlias("s:subscribe")
	private YahooSubscribe subscribe;
	
	@XStreamAlias("s:unsubscribe")
	private YahooUnSubscribe unsubscribe;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public YahooSubscribe getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(YahooSubscribe subscribe) {
		this.subscribe = subscribe;
	}

	public YahooUnSubscribe getUnsubscribe() {
		return unsubscribe;
	}

	public void setUnsubscribe(YahooUnSubscribe unsubscribe) {
		this.unsubscribe = unsubscribe;
	}
	
	
}
