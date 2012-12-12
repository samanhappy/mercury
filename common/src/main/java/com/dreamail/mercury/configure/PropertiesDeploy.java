package com.dreamail.mercury.configure;

import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import com.dreamail.mercury.cache.MemCachedManager;
import com.dreamail.mercury.cache.ReceiveServerCacheManager;
import com.dreamail.mercury.cache.RoleCacheManager;
import com.dreamail.mercury.cache.SendServerCacheManager;
import com.dreamail.mercury.cache.VolumeCacheManager;

public class PropertiesDeploy {
	public static final Map<String, String> ConfigureMap = new ConcurrentHashMap<String, String>();

	public void init() {

		ConfigureMap
				.putAll(PropertiesParse.parseProperties("timer.properties"));

		ConfigureMap.putAll(PropertiesParse.parseProperties("jms.properties"));

		ConfigureMap.putAll(PropertiesParse
				.parseProperties("sendemailproxy.properties"));

		ConfigureMap.putAll(PropertiesParse
				.parseProperties("attachmentsupport.properties"));

		ConfigureMap
				.putAll(PropertiesParse.parseProperties("scale.properties"));

		ConfigureMap.putAll(PropertiesParse
				.parseProperties("edcrypt.properties"));

		ConfigureMap.putAll(PropertiesParse
				.parseProperties("messageDataMaxSaveLength.properties"));

		ConfigureMap.putAll(PropertiesParse
				.parseProperties("sendemail.properties"));

		ConfigureMap.putAll(PropertiesParse.parseProperties("snp.properties"));

		ConfigureMap.putAll(PropertiesParse
				.parseProperties("mailbox.properties"));

		ConfigureMap.putAll(PropertiesParse
				.parseProperties("connectioncontrol.properties"));

		ConfigureMap.putAll(PropertiesParse
				.parseProperties("system_mailbox_parameters.properties"));
		
		ConfigureMap.putAll(PropertiesParse
				.parseProperties("upeThreadPool.properties"));

		ReceiveServerCacheManager.init();
		SendServerCacheManager.init();
		new VolumeCacheManager().init();
		MemCachedManager.getInstance().init();
		new RoleCacheManager().init();
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		// 获取服务器IP
		ServerManager.init();
	}

	public static Map<String, String> getConfigureMap() {
		return ConfigureMap;
	}
}
