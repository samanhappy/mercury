package com.dreamail.mercury.mail.truepush.impl;

public class Ping implements IASResponse {
	public final static String HEARTBEAT_EXPIRED = "1";
	public final static String CHANGES_OCCURRED = "2";
	public final static String NEED_RETRY = "8";

	public String getName() {
		return "Ping";
	}

	private Integer heartbeatInterval;
	private Folders folders;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getHeartbeatInterval() {
		return heartbeatInterval;
	}

	public void setHeartbeatInterval(Integer heartbeatInterval) {
		this.heartbeatInterval = heartbeatInterval;
	}

	public Folders getFolders() {
		return folders;
	}

	public void setFolders(Folders folders) {
		this.folders = folders;
	}
}
