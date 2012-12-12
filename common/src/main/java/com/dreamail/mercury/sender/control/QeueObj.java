package com.dreamail.mercury.sender.control;

public class QeueObj {
	private String uid;
	private String mid;
	private String sendMailType;
	private String oldmid;
	private String hid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getSendMailType() {
		return sendMailType;
	}

	public void setSendMailType(String sendMailType) {
		this.sendMailType = sendMailType;
	}

	public String getOldmid() {
		return oldmid;
	}

	public void setOldmid(String oldmid) {
		this.oldmid = oldmid;
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public QeueObj(String uid, String mid, String sendMailType, String oldmid,
			String hid) {
		this.uid = uid;
		this.mid = mid;
		this.sendMailType = sendMailType;
		this.oldmid = oldmid;
		this.hid = hid;
	}
}
