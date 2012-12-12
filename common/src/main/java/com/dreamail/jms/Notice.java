package com.dreamail.jms;

import java.io.Serializable;

public class Notice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//消息类型1  FindMaster,MasterNotice,StopLoop,NoticeAccount 
	public String msg;
	//服务器地址
	public String IP;

	public String getMsg() {
		return msg;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
