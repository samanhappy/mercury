package com.dreamail.config;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.configure.PropertiesParse;


public class TaskProperties extends PropertiesDeploy {
	/**
	 * 加载配置文件
	 */
	public void init() {
		super.init();
		ConfigureMap.putAll(PropertiesParse
				.parseProperties("taskfactory.properties"));
		ConfigureMap.putAll(PropertiesParse
				.parseProperties("offlinehandle.properties"));
		ConfigureMap.putAll(PropertiesParse
				.parseProperties("cleandata.properties"));
		ConfigureMap.putAll(PropertiesParse
				.parseProperties("tfthreadpool.properties"));
		ConfigureMap.putAll(PropertiesParse
				.parseProperties("heartbeat.properties"));		
	}
}
