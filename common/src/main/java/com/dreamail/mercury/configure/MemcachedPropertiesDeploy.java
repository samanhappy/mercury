package com.dreamail.mercury.configure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemcachedPropertiesDeploy {

	private static final Map<String, String> ConfigureMap = new ConcurrentHashMap<String, String>();

	private static List<String> servers = new ArrayList<String>();

	private static List<Integer> weights = new ArrayList<Integer>();

	public static void init() {
		ConfigureMap.putAll(PropertiesParse
				.parseProperties("memcached.properties"));

		Iterator<Map.Entry<String, String>> iter = ConfigureMap.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
					.next();
			servers.add(entry.getKey());
			weights.add(Integer.valueOf(entry.getValue()));
		}

	}

	public static String[] getServers() {
		String[] strs = new String[servers.size()];
		return servers.toArray(strs);
	}

	public static Integer[] getWeights() {
		Integer[] ints = new Integer[weights.size()];
		return weights.toArray(ints);
	}

}
