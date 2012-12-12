package com.dreamail.jms;

import java.io.Serializable;

public class TaskMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private long aid;
	
	private long intervaltimer;
	
	private String msg;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public long getAid() {
		return aid;
	}
	public void setAid(long aid) {
		this.aid = aid;
	}
	public long getIntervaltimer() {
		return intervaltimer;
	}
	public void setIntervaltimer(long intervaltimer) {
		this.intervaltimer = intervaltimer;
	}
}
