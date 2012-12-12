package com.dreamail.mercury.memcached;

import com.dreamail.mercury.cache.MemCachedManager;

public class SendMailXmlCacheManager {
	public static boolean addXml(String uid,String xml){
		return MemCachedManager.getMcc().set("cutdata_"+uid,xml);
	}
	
	public static boolean updateXml(String uid,String xml){
		if(xml!=null &&!"".equals(xml)){
			String sb = getXml(uid);
			if(sb!=null){
				sb += xml;
				return MemCachedManager.getMcc().set("cutdata_"+uid,sb);
			}
		}
		return false;
	}
	
	public static String getXml(String uid){
		return (String) MemCachedManager.getMcc().get("cutdata_"+uid);
	}
	
	public static boolean emptyXml(String uid){
		return  MemCachedManager.getMcc().delete("cutdata_"+uid);
	}
	
	public static boolean containsUid(String uid){
		return MemCachedManager.getMcc().keyExists("cutdata_"+uid);
	}
}
