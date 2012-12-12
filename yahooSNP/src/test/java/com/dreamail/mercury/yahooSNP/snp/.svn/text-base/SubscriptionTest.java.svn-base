package com.dreamail.mercury.yahooSNP.snp;

import java.util.Calendar;

import org.junit.Test;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.yahooSNP.SNPContext;
import com.dreamail.mercury.yahooSNP.impl.EmailSubscribeImpl;
import com.dreamail.mercury.yahooSNP.impl.UnSubscription;

public class SubscriptionTest {

	@Test
	public void subscribe() {
		new PropertiesDeploy().init();
		SNPContext user = new SNPContext();
		user.setLoginId("ss_102698@yahoo.com");
		user.setPassword("000000");
		user.setAid(14);

		new EmailSubscribeImpl().subscribe(user);
		
		
		System.out.println(user.getSubId());
		System.out.println(user.getToken());
		System.out.println(user.getUserId());

	}

	@Test
	public void unscribe() {
		new PropertiesDeploy().init();

		String timestamp = String.valueOf(Calendar.getInstance()
				.getTimeInMillis() / 1000);
		SNPContext user = new SNPContext();
		user.setAid(14);
		user.setSubId(12);
		user.setUserId("5vbvNuuPZVf1fwlH.2x51g--");
		new UnSubscription().executeunSubscribe(user, timestamp);
	}
}
