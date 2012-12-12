package com.dreamail.mercury.yahooSNP;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("message_delete")
public class YahooMessageDelete {

	private YahooMessage msg;

	public YahooMessage getMsg() {
		return msg;
	}

	public void setMsg(YahooMessage msg) {
		this.msg = msg;
	}
	
}
