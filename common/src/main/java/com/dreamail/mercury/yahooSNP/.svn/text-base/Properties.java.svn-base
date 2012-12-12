package com.dreamail.mercury.yahooSNP;

import java.util.HashMap;

public final class Properties {
	private static final HashMap<String, String> ConfigureMap = new HashMap<String, String>();

	public void init() {
		ConfigureMap.putAll(new PropertiesParse()
		.parseProperties("snp.properties"));
	}

	public static HashMap<String, String> getConfigureMap() {
		return ConfigureMap;
	}
}
