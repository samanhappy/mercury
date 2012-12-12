package com.dreamail.mercury.memcached;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danga.MemCached.MemCachedClient;
import com.dreamail.mercury.cache.MemCachedManager;
import com.dreamail.mercury.domain.EmailCacheObject;

public class EmailCacheManager {
	public static final int HOUR = 48;
	
	private static Logger logger = LoggerFactory.getLogger(EmailCacheManager.class);
	
	public static boolean addEmail(String mid,EmailCacheObject emailCache,int hour) {
		boolean flag = false;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		if(hour!=-1){
			calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + hour);
		}else{
			calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + HOUR);
		}
		flag = MemCachedManager.getMcc().set("emailCacheObject_"+mid, emailCache,calendar.getTime());
		return flag;
	}
	
	public static boolean removeEmail(String mid){
		boolean flag = false;
		flag = MemCachedManager.getMcc().delete("emailCacheObject_"+mid);
		return flag;
	}
	
	public static EmailCacheObject get(String mid){
		return (EmailCacheObject)(MemCachedManager.getMcc().get("emailCacheObject_"+mid));
	}
	
	/**
	 * 获得IMEI的缓存对象锁.
	 * 
	 * @param IMEI
	 */
	public static void getLock(String mid) {
		MemCachedClient client = MemCachedManager.getMcc();
		int count = 0;
		while (!client.add("lock_emailCacheObject" + mid, "")) {

			// 如果等待过长时间，那么强制解除已有的对象锁.
			if (count == 5) {
				client.delete("lock_emailCacheObject" + mid);
			}
			logger.info("wait 3 seconds for get lock on IMEI:" + mid);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				logger.error("failed to call thread sleep", e);
			}
			count++;
		}
	}

	/**
	 * 删除对象锁.
	 * 
	 * @param IMEI
	 */
	public static void removeLock(String mid) {
		MemCachedClient client = MemCachedManager.getMcc();
		client.delete("lock_emailCacheObject" + mid);
	}
	
}
