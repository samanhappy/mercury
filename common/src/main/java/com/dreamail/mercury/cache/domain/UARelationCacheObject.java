package com.dreamail.mercury.cache.domain;

import java.io.Serializable;
import java.util.Date;

public class UARelationCacheObject implements Serializable {
	private static final long serialVersionUID = 7708890966175783939L;
	private long aid;// 
	private int status;
	private Date registerDate;

	public UARelationCacheObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UARelationCacheObject(long aid, int status,Date registerDate) {
		super();
		this.aid = aid;
		this.status = status;
		this.registerDate = registerDate;
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

}
