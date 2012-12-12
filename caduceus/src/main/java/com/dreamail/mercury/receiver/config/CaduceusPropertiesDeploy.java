package com.dreamail.mercury.receiver.config;

import java.util.Map;
import java.util.TimeZone;

import com.dreamail.mercury.cache.MsgCacheManager;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.configure.PropertiesParse;

public final class CaduceusPropertiesDeploy extends PropertiesDeploy {
	public void init() {
		super.init();
		new MsgCacheManager().init();
		Map<String, String> timer = PropertiesParse
				.parseProperties("timer.properties");
		ConfigureMap.putAll(timer);
		Map<String, String> mail = PropertiesParse
				.parseProperties("mail.properties");
		ConfigureMap.putAll(mail);
		Map<String, String> mailpool = PropertiesParse
				.parseProperties("mailpool.properties");
		ConfigureMap.putAll(mailpool);
		Map<String, String> bigmailpool = PropertiesParse
				.parseProperties("bigmailpool.properties");
		ConfigureMap.putAll(bigmailpool);
		Map<String, String> searchsize = PropertiesParse
				.parseProperties("searchsize.properties");
		ConfigureMap.putAll(searchsize);
		Map<String, String> messageDataMaxSaveLength = PropertiesParse
				.parseProperties("messageDataMaxSaveLength.properties");
		ConfigureMap.putAll(messageDataMaxSaveLength);
		Map<String, String> accountQueue = PropertiesParse
				.parseProperties("accountqueue.properties");
		ConfigureMap.putAll(accountQueue);
		Map<String, String> threadpool = PropertiesParse
		.parseProperties("threadpool.properties");
		ConfigureMap.putAll(threadpool);
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
	}
}
