package com.dreamail.jms;

import java.io.Serializable;

public class CacheAccountMessage implements Serializable{
	
	private static final long serialVersionUID = -2358959159177132021L;

	private String state;
	
	private String aid;
	
	private int type;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
