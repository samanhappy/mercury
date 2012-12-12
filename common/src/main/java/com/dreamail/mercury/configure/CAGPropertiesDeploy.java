package com.dreamail.mercury.configure;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CAGPropertiesDeploy {

	public static final Map<String, String> ConfigureMap = new ConcurrentHashMap<String, String>();

	public static void init() {
		ConfigureMap.putAll(PropertiesParse.parseProperties("jms.properties"));
	}

	public static Map<String, String> getConfigureMap() {
		return ConfigureMap;
	}
}
