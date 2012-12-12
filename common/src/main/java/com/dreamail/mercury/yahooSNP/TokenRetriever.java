package com.dreamail.mercury.yahooSNP;

import java.util.Calendar;

public class TokenRetriever {

	public static String retriveToken(String username, String password) {

		String timestamp = String.valueOf(Calendar.getInstance()
				.getTimeInMillis() / 1000);
		SNPContext context = new SNPContext();

		context.setLoginId(username);
		context.setPassword(password);

		Token token = new Token();
		// 获取token
		token.setToken(context, timestamp);
		return context.getToken();
	}
	
	

}
