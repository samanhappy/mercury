package com.dreamail.mercury.mail.truepush.impl;

import java.util.List;

public class Commands {
	private List<Add> adds;
	private List<Change> changes;
	private List<Delete> deletes;
	private List<Fetch> fetchs;
	private List<SoftDelete> softDeletes;

	public List<SoftDelete> getSoftDeletes() {
		return softDeletes;
	}

	public void setSoftDeletes(List<SoftDelete> softDeletes) {
		this.softDeletes = softDeletes;
	}

	public List<Fetch> getFetchs() {
		return fetchs;
	}

	public void setFetchs(List<Fetch> fetchs) {
		this.fetchs = fetchs;
	}

	public List<Delete> getDeletes() {
		return deletes;
	}

	public void setDeletes(List<Delete> deletes) {
		this.deletes = deletes;
	}

	public List<Change> getChanges() {
		return changes;
	}

	public void setChanges(List<Change> changes) {
		this.changes = changes;
	}

	public List<Add> getAdds() {
		return adds;
	}

	public void setAdds(List<Add> adds) {
		this.adds = adds;
	}
}
