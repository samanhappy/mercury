package com.dreamail.mercury.memcached;

import com.dreamail.mercury.cache.MemCachedManager;
/**
 * 保存account的邮件数量,为yahoophone的noop命令服务.
 * @author pengkai.wang
 *
 */
public class IdleCacheManager {
	
	public static boolean setMessageCount(long aid,long count){
		return MemCachedManager.getMcc().set("idle_"+aid, count);
	}
	
	public static String getMessageCount(long aid){
		return (String) MemCachedManager.getMcc().get("idle_"+aid);
	}
	
	public static boolean deleteMessageCount(long aid){
		return  MemCachedManager.getMcc().delete("idle_"+aid);
	}
}
