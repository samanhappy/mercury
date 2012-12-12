package com.dreamail.mercury.mail.truepush.impl;

public class GetItemEstimate implements IASResponse {
	public String getName() {
		return GETITEMESTIMATE;
	}

	private Collections collections;
	private Response response;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public Collections getCollections() {
		return collections;
	}

	public void setCollections(Collections collections) {
		this.collections = collections;
	}
}
