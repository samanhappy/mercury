package com.dreamail.talaria.memcached;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danga.MemCached.MemCachedClient;
import com.dreamail.mercury.cache.MemCachedManager;
import com.dreamail.mercury.util.UPEConstants;

public class UPECacheManager extends AbstractCacheManager {

	private static Logger logger = LoggerFactory
			.getLogger(UPECacheManager.class);

	private final static String OBJECT_KEY_PREFIX = "user_id_";

	private final static String MAPPING_KEY_PREFIX = "handset_imei_";

	private final static String OBJECT_LOCK_PREFIX = "lock_id_";

	private final static String MAPPING_LOCK_PREFIX = "lock_imei_";

	/**
	 * 查询缓存.
	 * 
	 * @param uid
	 * @return
	 */
	public static UPECacheObject getCacheObject(String uid) {
		MemCachedClient client = MemCachedManager.getMcc();
		Object object = client.get(OBJECT_KEY_PREFIX + uid);
		if (object != null) {
			return (UPECacheObject) object;
		} else {
			logger.info("not find cache object for uid:" + uid);
			return null;
		}
	}

	/**
	 * 查询缓存.
	 * 
	 * @param IMEI
	 * @return
	 */
	public static UPEMapping getMappingObject(String IMEI) {
		MemCachedClient client = MemCachedManager.getMcc();
		Object object = client.get(MAPPING_KEY_PREFIX + IMEI);
		if (object != null) {
			return (UPEMapping) object;
		} else {
			logger.info("not find cache object for IMEI:" + IMEI);
			return null;
		}
	}

	public static int getLength(Object[] arr) {
		int notNullLength = 0;
		for(int i = 0; i < arr.length; i++){
			if(arr[i] != null){
				notNullLength ++;
			}
		}
		return notNullLength;
	}
	
	/**
	 * 查询缓存.
	 * 
	 * @param IMEI
	 * @return
	 */
	public static UPEMapping[] getMappingObjectArray(List<String> IMEIList) {

		String[] keys = new String[IMEIList.size()];
		for (int i = 0; i < keys.length; i++) {
			keys[i] = MAPPING_KEY_PREFIX + IMEIList.get(i);
		}

		MemCachedClient client = MemCachedManager.getMcc();
		Object[] arr = client.getMultiArray(keys);
		int j = 0;
		if (arr != null && arr.length > 0) {
			UPEMapping result[] = new UPEMapping[getLength(arr)];
			for(int i = 0; i < arr.length; i++){
				if(arr[i] != null){
					result[j] = (UPEMapping)arr[i];
					j++;
				}
			}
			if(result.length > 0){
				return (UPEMapping[]) result;
			}else{
				logger.info("find null cache object");
				return null;
			}
		} else {
			logger.info("not find cache object");
			return null;
		}
	}
	
	/**
	 * 删除缓存.
	 * 
	 * @param uid
	 * @return
	 */
	public static boolean deleteCacheObject(String uid) {
		return MemCachedManager.getMcc().delete(OBJECT_KEY_PREFIX + uid);
	}

	/**
	 * 删除缓存.
	 * 
	 * @param uid
	 * @return
	 */
	public static boolean deleteMappingObject(String IMEI) {
		return MemCachedManager.getMcc().delete(MAPPING_KEY_PREFIX + IMEI);
	}

	/**
	 * 更新缓存.
	 * 
	 * @param object
	 * @return
	 */
	public static boolean setCacheObject(UPECacheObject object) {
		if (object != null && object.getUid() != null) {
			boolean success = MemCachedManager.getMcc().set(
					OBJECT_KEY_PREFIX + object.getUid(), object);
			if (!success) {
				logger.error("failed to update memecache by " + object.getUid());
			}
			return success;
		} else {
			logger.info("object is null or uid is null, do nothing...");
			return false;
		}

	}

	/**
	 * 更新缓存.
	 * 
	 * @param mapping
	 * @return
	 */
	public static boolean setMappingObject(UPEMapping mapping) {
		if (mapping != null && mapping.getIMEI() != null) {
			boolean success = MemCachedManager.getMcc().set(
					MAPPING_KEY_PREFIX + mapping.getIMEI(), mapping);
			if (!success) {
				logger.error("failed to update memecache by "
						+ mapping.getIMEI());
			}
			return success;
		} else {
			logger.info("object is null or IMEI is null, do nothing...");
			return false;
		}

	}

	/**
	 * 增加新邮件标记.
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 */
	public static boolean addNewMessageFlag(String uid, String aid) {
		MemCachedClient client = MemCachedManager.getMcc();
		Object object = client.get(OBJECT_KEY_PREFIX + uid);
		UPECacheObject cache = null;
		if (object != null && object instanceof UPECacheObject) {
			cache = (UPECacheObject) object;
		} else {
			cache = new UPECacheObject();
		}
		addNewMessageFlag4String(cache, aid);
		return client.set(OBJECT_KEY_PREFIX + uid, cache);
	}

	/**
	 * 增加非法账号aid.
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 */
	public static boolean addInvalidAid(String uid, String aid) {
		MemCachedClient client = MemCachedManager.getMcc();
		Object object = client.get(OBJECT_KEY_PREFIX + uid);
		UPECacheObject cache = null;
		if (object != null && object instanceof UPECacheObject) {
			cache = (UPECacheObject) object;
		} else {
			cache = new UPECacheObject();
		}
		cache.addInvalidAid(aid);
		return client.set(OBJECT_KEY_PREFIX + uid, cache);
	}

	/**
	 * 删除非法账号aid.
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 */
	public static boolean removeInvalidAid(String uid, String aid) {
		MemCachedClient client = MemCachedManager.getMcc();
		Object object = client.get(OBJECT_KEY_PREFIX + uid);
		UPECacheObject cache = null;
		if (object != null && object instanceof UPECacheObject) {
			cache = (UPECacheObject) object;
			cache.removeInvalidAid(aid);
			return client.set(OBJECT_KEY_PREFIX + uid, cache);
		} else {
			return false;
		}

	}

	/**
	 * 添加新邮件通知时间戳到缓存.
	 * 
	 * @param uid
	 * @param timestamp
	 * @return
	 */
	public static boolean addTimestamp(String uid, String timestamp) {
		MemCachedClient client = MemCachedManager.getMcc();
		Object object = client.get(OBJECT_KEY_PREFIX + uid);
		UPECacheObject cache = null;
		if (object != null) {
			cache = (UPECacheObject) object;
		} else {
			cache = new UPECacheObject();
		}
		cache.setLast_notice_ts(timestamp);
		cache.setLast_notice_state(UPEConstants.UPE_NEWMAIL_STATE);
		return client.set(OBJECT_KEY_PREFIX + uid, cache);
	}

	/**
	 * 获得uid的缓存对象锁.
	 * 
	 * @param uid
	 */
	public static void getLock(String uid) {
		MemCachedClient client = MemCachedManager.getMcc();
		int count = 0;
		while (!client.add(OBJECT_LOCK_PREFIX + uid, "")) {
			// 如果等待过长时间，那么强制解除已有的对象锁.
			if (count == 2) {
				break;
			}
			logger.info("wait 3 seconds for get lock on IMEI:" + uid);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				logger.error("failed to call thread sleep", e);
			}
			count++;
		}
	}

	/**
	 * 获得uid的缓存对象锁.
	 * 
	 * @param uid
	 */
	public static void getSimpleLock(String uid) {
		MemCachedManager.getMcc().add(OBJECT_LOCK_PREFIX + uid, "");
	}

	/**
	 * 删除对象锁.
	 * 
	 * @param uid
	 */
	public static void removeLock(String uid) {
		MemCachedClient client = MemCachedManager.getMcc();
		client.delete(OBJECT_LOCK_PREFIX + uid);
	}

	/**
	 * 获得IMEI的缓存对象锁.
	 * 
	 * @param IMEI
	 */
	public static void getMappingLock(String IMEI) {
		MemCachedClient client = MemCachedManager.getMcc();
		int count = 0;
		while (!client.add(MAPPING_LOCK_PREFIX + IMEI, "")) {

			// 如果等待过长时间，那么强制解除已有的对象锁.
			if (count == 2) {
				break;
			}
			logger.info("wait 3 seconds for get lock on IMEI:" + IMEI);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				logger.error("failed to call thread sleep", e);
			}
			count++;
		}
	}

	/**
	 * 获得IMEI的缓存对象锁.
	 * 
	 * @param IMEI
	 */
	public static void getSimpleMappingLock(String IMEI) {
		MemCachedManager.getMcc().add(MAPPING_LOCK_PREFIX + IMEI, "");
	}

	/**
	 * 删除对象锁.
	 * 
	 * @param IMEI
	 */
	public static void removeMappingLock(String IMEI) {
		MemCachedClient client = MemCachedManager.getMcc();
		client.delete(MAPPING_LOCK_PREFIX + IMEI);
	}

	/**
	 * 判断IMEI的缓存是否存在.
	 * 
	 * @param uid
	 * @return
	 */
	public static boolean isCacheExist(String uid) {
		return MemCachedManager.getMcc().get(OBJECT_KEY_PREFIX + uid) != null;
	}
	
	/**
	 * 清除通知删除账号的信息
	 * @param uid
	 *//*
	public static void removeDeleteAccountFlag(String uid){
		MemCachedClient client = MemCachedManager.getMcc();
		Object object = client.get(OBJECT_KEY_PREFIX + uid);
		UPECacheObject cache = null;
		if (object != null && object instanceof UPECacheObject) {
			cache = (UPECacheObject) object;
			cache.setDelete_account_map(null);
			client.set(OBJECT_KEY_PREFIX + uid, cache);
		}
	}*/
	
	/**
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 */
	public static boolean addDeleteAccountFlag(String uid, String aid) {
		MemCachedClient client = MemCachedManager.getMcc();
		Object object = client.get(OBJECT_KEY_PREFIX + uid);
		UPECacheObject cache = null;
		if (object != null && object instanceof UPECacheObject) {
			cache = (UPECacheObject) object;
		} else {
			cache = new UPECacheObject();
		}
		logger.info("delete  cache0:"+cache.getDelete_account_map());
		String deleteAccoungMap = cache.getDelete_account_map();
		if(deleteAccoungMap != null && !deleteAccoungMap.trim().equals("")){
			deleteAccoungMap = deleteAccoungMap + "," + aid;
		}else{
			deleteAccoungMap = aid;
		}
		logger.info("delete  cache1:"+deleteAccoungMap);
		cache.setDelete_account_map(deleteAccoungMap);
		logger.info("delete  cache2:"+deleteAccoungMap);
		boolean isSuc = client.set(OBJECT_KEY_PREFIX + uid, cache);
		logger.info("delete  cache3:"+((UPECacheObject)client.get(OBJECT_KEY_PREFIX + uid)).getDelete_account_map());
		return isSuc;
	}
	/**
	 * 
	 * @param uid
	 * @param aid
	 * @return
	 */
	public static boolean hasDeleteAccountFlag(UPECacheObject cache){
		boolean flag = false;
		String deleteAccoungMap = cache.getDelete_account_map();
		logger.info("uid:"+cache.getUid() +" deleteAccountMap:"+deleteAccoungMap);
		if(deleteAccoungMap != null && !deleteAccoungMap.trim().equals("")){
			flag = true;
		}
		return flag;
	}
	
}
