package com.dreamail.mercury.memcached;

import java.util.List;

import com.dreamail.mercury.cache.MemCachedManager;
import com.dreamail.mercury.cache.domain.SeqNoInfo;
import com.dreamail.mercury.pojo.Clickoo_message;

public class EmailListCacheManager {
	/**
	 * 向memcached中添加emaillist.
	 * 
	 * @param uid
	 * @param emailList
	 * @return
	 */
	public static boolean addEmailList(String uid,
			List<Clickoo_message> emailList) {
		return MemCachedManager.getMcc().set("emaillist_" + uid, emailList);
	}

	/**
	 * 从memcached中移除emaillist.
	 * 
	 * @param uid
	 * @return
	 */
	public static boolean removeEmailList(String uid) {
		return MemCachedManager.getMcc().delete("emaillist_" + uid);
	}

	/**
	 * 从memcached中得到emaillist.
	 * 
	 * @param uid
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public static List<Clickoo_message> getEmailList(String uid) {
		return (List<Clickoo_message>) MemCachedManager.getMcc().get(
				"emaillist_" + uid);
	}

	public static boolean saveSeqNoInfo(String uid, SeqNoInfo info) {
		return MemCachedManager.getMcc().set("seqnoinfo_" + uid, info);
	}
	
	public static boolean removeSeqNoInfo(String uid) {
		return MemCachedManager.getMcc().delete("seqnoinfo_" + uid);
	}
	
	public static SeqNoInfo getSeqNoInfo(String uid) {
		return (SeqNoInfo) MemCachedManager.getMcc().get("seqnoinfo_" + uid);
	}
}
