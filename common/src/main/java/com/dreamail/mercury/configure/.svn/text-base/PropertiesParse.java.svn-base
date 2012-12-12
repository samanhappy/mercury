package com.dreamail.mercury.configure;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class PropertiesParse {
	
	public static Map<String, String> parseProperties(String properties) {
		Map<String, String> hm = new ConcurrentHashMap<String, String>();
		InputStream inputStream = PropertiesParse.class.getClassLoader()
				.getResourceAsStream(properties);
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Set<Map.Entry<Object, Object>> set = p.entrySet();
		Iterator<Entry<Object, Object>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<Object, Object> e = it.next();
			hm.put(e.getKey().toString(), e.getValue().toString());
		}
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return hm;
	}
}
