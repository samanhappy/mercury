package com.dreamail.mercury.yahooSNP.snp;

import java.util.Calendar;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.yahooSNP.SNPContext;
import com.dreamail.mercury.yahooSNP.Token;

public class TokenTest {

	public static void main(String[] args) {
		new PropertiesDeploy().init();

		String timestamp = String.valueOf(Calendar.getInstance()
				.getTimeInMillis() / 1000);
		SNPContext user = new SNPContext();
		user.setLoginId("ss_102698@yahoo.cn");
		user.setPassword("123456");
		user.setAid(7);

		Token test = new Token();
		test.setToken(user, timestamp);
		System.out.println("token:" + user.getToken());

	}

}
