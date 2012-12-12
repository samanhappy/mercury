package com.dreamail.mercury.talaria.http.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BADRequestHandler extends IRequestHandler{

	
	public BADRequestHandler(HttpServletRequest request,
			HttpServletResponse response, String requestStr) {
		super(request, response, requestStr);
	}
	
	@Override
	public void handle() {
		setResponseContent("protocol is error");
	}
}
