package com.dreamail.mercury.pojo;

import java.util.Date;

public class Clickoo_user_account {
	private long id;// 主键ID
	private long uid;
	private long aid;
	private int status;
	private Date registerDate;
	private int validation;
	private Date offlineDate;
	private int receivedNum;

	public int getValidation() {
		return validation;
	}

	public void setValidation(int validation) {
		this.validation = validation;
	}

	public Clickoo_user_account(long uid, long aid,int status,Date registerDate) {
		super();
		this.uid = uid;
		this.aid = aid;
		this.status = status;
		this.registerDate = registerDate;
	}
	
	public Clickoo_user_account(long uid, long aid) {
		super();
		this.uid = uid;
		this.aid = aid;
	}
	
	public Clickoo_user_account() {
		super();
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public long getAid() {
		return aid;
	}
	public void setAid(long aid) {
		this.aid = aid;
	}

	public Date getOfflineDate() {
		return offlineDate;
	}

	public void setOfflineDate(Date offlineDate) {
		this.offlineDate = offlineDate;
	}

	public int getReceivedNum() {
		return receivedNum;
	}

	public void setReceivedNum(int receivedNum) {
		this.receivedNum = receivedNum;
	}
	
	
}
