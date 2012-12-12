package com.clickoo.clickooImap.config;

import java.util.Map;

import com.clickoo.mercury.configure.PropertiesDeploy;
import com.clickoo.mercury.configure.PropertiesParse;

public class ClickooImapProperties extends PropertiesDeploy {
	public void init() {
		super.init();
		Map<String, String> clickooimap = PropertiesParse
				.parseProperties("clickooimap.properties");
		ConfigureMap.putAll(clickooimap);
	}
}
