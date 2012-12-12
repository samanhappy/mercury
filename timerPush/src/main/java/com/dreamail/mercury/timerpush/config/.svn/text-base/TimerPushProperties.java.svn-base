package com.dreamail.mercury.timerpush.config;

import java.util.Map;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.configure.PropertiesParse;

public class TimerPushProperties extends PropertiesDeploy {
	@SuppressWarnings("static-access")
	public void init() {
		super.init();
		Map<String, String> clickooimap = new PropertiesParse().parseProperties("timerpush.properties");
		ConfigureMap.putAll(clickooimap);
	}
}
