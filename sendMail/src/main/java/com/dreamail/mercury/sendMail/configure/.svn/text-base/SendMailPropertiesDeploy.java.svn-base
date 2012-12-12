package com.dreamail.mercury.sendMail.configure;

import java.util.Map;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.configure.PropertiesParse;

public final class SendMailPropertiesDeploy extends PropertiesDeploy {
	@SuppressWarnings("static-access")
	public void init() {
		super.init();
		Map<String, String> sendmail = new PropertiesParse()
				.parseProperties("sendmail.properties");
		ConfigureMap.putAll(sendmail);
		Map<String, String> threadpool = PropertiesParse
				.parseProperties("threadpool.properties");
		ConfigureMap.putAll(threadpool);
	}
}
