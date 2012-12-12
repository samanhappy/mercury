package com.dreamail.taskfactory.msg;

import java.io.Serializable;

public class ServerInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// server IP 地址
	private String IP;
	
	// 服务器类别 master，second，common
	private String serverType;
	
	// 状态维护时间
	private String date;

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
