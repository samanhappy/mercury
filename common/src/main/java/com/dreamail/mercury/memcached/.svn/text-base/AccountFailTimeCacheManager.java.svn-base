package com.dreamail.mercury.memcached;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cache.MemCachedManager;

public class AccountFailTimeCacheManager {
	
	private static Logger logger = LoggerFactory.getLogger(AccountFailTimeCacheManager.class);
	
	public static boolean addFailTime(String aid,String time) {
		boolean flag = false;
		flag = MemCachedManager.getMcc().set("accountFailTime_"+aid,time);
		return flag;
	}
	
	public static boolean removeFailTime(String aid){
		boolean flag = false;
		flag = MemCachedManager.getMcc().delete("accountFailTime_"+aid);
		return flag;
	}
	
	public static String get(String aid){
		return (String)(MemCachedManager.getMcc().get("accountFailTime_"+aid));
	}
	
}
