package com.dreamail.mercury.yahooSNP.snp;

import java.util.Calendar;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.yahooSNP.SNPContext;
import com.dreamail.mercury.yahooSNP.Token;
import com.dreamail.mercury.yahooSNP.impl.SNPLogin;

public class LoginTest {
	public static void main(String[] args) {
		new PropertiesDeploy().init();

		String timestamp = String.valueOf(Calendar.getInstance()
				.getTimeInMillis() / 1000);
		SNPContext user = new SNPContext();
		user.setLoginId("ss_102698@yahoo.cn");
		user.setPassword("123456");
		user.setAid(10);

		Token pwtoken_get = new Token();
		pwtoken_get.setToken(user, timestamp);

		SNPLogin login = new SNPLogin();
		login.executeLogin(user, timestamp);

		System.out.println("login:\r\n cookiestore" + user.getCookieStore());
		System.out.println("crumb:" + user.getWssid());

	}
}
