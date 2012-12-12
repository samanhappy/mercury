/**
 * 
 */
package com.dreamail.mercury.dal.service;

import com.dreamail.mercury.cache.VolumeCacheManager;
import com.dreamail.mercury.cache.domain.VolumeCacheObject;
import com.dreamail.mercury.pojo.Clickoo_volume;

/**
 * @author meng.sun
 * 
 */
public class VolumeService {

	/**
	 * 获得当前有效的Volume.
	 * @param String type
	 * @return Volume 类型
	 */
	public VolumeCacheObject getCurrentVolumeByName(String name) {
		return VolumeCacheManager.getCurrentVolumeByMetaName(name);
	}
	
	/**
	 * 根据id获得Volume.
	 * @param long type
	 * @return Clickoo_volume 类型
	 */
	public Clickoo_volume getVolumeById(long id) {
		return VolumeCacheManager.getVolumePojoById(id);
	}
}
