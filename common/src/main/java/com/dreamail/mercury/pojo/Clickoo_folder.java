package com.dreamail.mercury.pojo;

import java.util.Date;

/**
 * 邮箱账号下的文件夹对象.
 * 
 * @author pengkai.wang
 * 
 */
public class Clickoo_folder {

	private long id;// 主键id.

	private long uid;// 用户id.

	private long aid;// 账号id.

	private String name;// 文件夹名称.

	private Date addDate;// 文件夹添加到服务器的日期.

	private Date registerDate;// 账号下的某文件夹第一次被添加到服务器的日期.

	private int receiveStatus;// 控制收取新老邮件的状态.

	private int listStatus;// 控制邮件列表收取新老邮件的状态.

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public int getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(int receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public int getListStatus() {
		return listStatus;
	}

	public void setListStatus(int listStatus) {
		this.listStatus = listStatus;
	}

}
