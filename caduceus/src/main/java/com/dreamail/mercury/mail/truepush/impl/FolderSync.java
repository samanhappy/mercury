package com.dreamail.mercury.mail.truepush.impl;

public class FolderSync implements IASResponse {
	private String status;
	private String syncKey;
	private Changes changes;

	public Changes getChanges() {
		return changes;
	}

	public void setChanges(Changes changes) {
		this.changes = changes;
	}

	public String getSyncKey() {
		return syncKey;
	}

	public void setSyncKey(String syncKey) {
		this.syncKey = syncKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public final static String cmdName = "FolderSync";

	public String getName() {
		return FOLDERSYNC;
	}
}
