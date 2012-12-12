package com.dreamail.mercury.cache.domain;

import java.io.Serializable;
import java.util.Date;

public class AccountCacheObject implements Serializable{

	private static final long serialVersionUID = 7708890966175783939L;

	private long id;// 主键ID

	private String name;// 名称

//	private long uid;// 用户ID

	private int status;// 状态，是否启用，默认0有效，1无效

	private String inCert;// 收邮件的用户的用户名密码

	private String inPath;// 收邮件的用户pop3和imap的地址

	private String outCert;// 发送邮件的用户帐号和密码和别名

	private String outPath;// 发送邮件的用户smtp地址
	
	private Date registrationDate;// 注册时间
	
	private String deviceModel; //设备分辨率
	
	private int roleId;
	
	public int getRoleid() {
		return roleId;
	}

	public void setRoleid(int roleId) {
		this.roleId = roleId;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

/*	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}*/

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getInCert() {
		return inCert;
	}

	public void setInCert(String inCert) {
		this.inCert = inCert;
	}

	public String getInPath() {
		return inPath;
	}

	public void setInPath(String inPath) {
		this.inPath = inPath;
	}

	public String getOutCert() {
		return outCert;
	}

	public void setOutCert(String outCert) {
		this.outCert = outCert;
	}

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	
}
