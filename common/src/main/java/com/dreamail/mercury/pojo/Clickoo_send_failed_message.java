package com.dreamail.mercury.pojo;

public class Clickoo_send_failed_message {

	private long id;// 主键MID

	private long mid;

	private long oldmid;

	private long uid;

	private long hid;

	private int sendtype;

	private int retrycount;
	
	private String qeuetype;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	public long getOldmid() {
		return oldmid;
	}

	public void setOldmid(long oldmid) {
		this.oldmid = oldmid;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getHid() {
		return hid;
	}

	public void setHid(long hid) {
		this.hid = hid;
	}

	public int getSendtype() {
		return sendtype;
	}

	public void setSendtype(int sendtype) {
		this.sendtype = sendtype;
	}

	public void setRetrycount(int retrycount) {
		this.retrycount = retrycount;
	}

	public int getRetrycount() {
		return retrycount;
	}

	public void setQeuetype(String qeuetype) {
		this.qeuetype = qeuetype;
	}

	public String getQeuetype() {
		return qeuetype;
	}

}
