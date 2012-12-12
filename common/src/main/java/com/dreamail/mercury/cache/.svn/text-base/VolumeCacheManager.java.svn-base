package com.dreamail.mercury.cache;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cache.domain.VolumeCacheObject;
import com.dreamail.mercury.dal.dao.VolumeDao;
import com.dreamail.mercury.dal.dao.VolumeMetaDao;
import com.dreamail.mercury.pojo.Clickoo_volume;
import com.dreamail.mercury.pojo.Clickoo_volume_meta;

public class VolumeCacheManager {
	private static Logger logger = LoggerFactory
			.getLogger(VolumeCacheManager.class);
	private static ConcurrentHashMap<Long, VolumeCacheObject> volumeCache;
	private static ConcurrentHashMap<String, String> volumeMetaCache;

	/**
	 * 初始化Volume信息.
	 */
	public void init() {
		volumeCache = getVolumeCache();
		volumeMetaCache = getVolumeMetaCache();
		List<Clickoo_volume> volumeList = new VolumeDao().getAllVolume();
		for (Clickoo_volume volume : volumeList) {
			VolumeCacheObject cache = parseToCache(volume);
			volumeCache.put(cache.getId(), cache);
		}
		List<Clickoo_volume_meta> metaList = new VolumeMetaDao()
				.getAllVolumeMeta();
		for (Clickoo_volume_meta meta : metaList) {
			volumeMetaCache.put(meta.getName(), meta.getVid());
		}
		logger.info("volume cache init......");
	}

	/**
	 * Clickoo_volume to VolumeCacheObject
	 * 
	 * @param Clickoo_volume
	 *            volume
	 * @return VolumeCacheObject
	 */
	private VolumeCacheObject parseToCache(Clickoo_volume volume) {
		VolumeCacheObject cache = new VolumeCacheObject();
		cache.setFile_bits(volume.getFile_bits());
		cache.setId(volume.getId());
		cache.setName(volume.getName());
		cache.setPath(volume.getPath());
		cache.setType(volume.getType());
		cache.setSpace(volume.getSpace());
		return cache;
	}

	/**
	 * Clickoo_volume volume
	 * 
	 * @param Clickoo_volume
	 *            to VolumeCacheObject
	 * @return Clickoo_volume
	 */
	public static Clickoo_volume parseToPojo(VolumeCacheObject cache) {
		Clickoo_volume volume = new Clickoo_volume();
		volume.setFile_bits(cache.getFile_bits());
		volume.setId(cache.getId());
		volume.setName(cache.getName());
		volume.setPath(cache.getPath());
		volume.setType(cache.getType());
		volume.setSpace(cache.getSpace());
		return volume;
	}

	/**
	 * 获得当前有效的Volume.
	 * 
	 * @param long id
	 * @return Volume 类型
	 */
	private static VolumeCacheObject getVolumeById(long id) {
		VolumeCacheObject element = (VolumeCacheObject) volumeCache.get(id);
		if (element != null) {
			return (VolumeCacheObject) element;
		} else {
			logger.error("There is no volume Id by id:" + id);
			return null;
		}
	}

	/**
	 * 获得当前有效的Volume.
	 * 
	 * @param String
	 *            name
	 * @return VolumeCacheObject
	 */
	public static VolumeCacheObject getCurrentVolumeByMetaName(String name) {
		String element = volumeMetaCache.get(name);
		long space = 0;
		long currentVolumeID = 0;
		if (element != null) {
			String[] ids = element.split(",");
			for (String id : ids) {
				VolumeCacheObject volumeCacheObject = getVolumeById(Long
						.parseLong(id));
				long currentSpace = volumeCacheObject.getSpace();
				if (currentSpace > space) {
					space = currentSpace;
					currentVolumeID = Long.parseLong(id);
				}
			}
		}
		return getVolumeById(currentVolumeID);
	}

	public static Clickoo_volume getVolumePojoById(long id) {
		return parseToPojo(getVolumeById(id));
	}

	private static ConcurrentHashMap<Long, VolumeCacheObject> getVolumeCache() {
		if (volumeCache == null) {
			volumeCache = new ConcurrentHashMap<Long, VolumeCacheObject>();
		}
		return volumeCache;
	}

	private static ConcurrentHashMap<String, String> getVolumeMetaCache() {
		if (volumeMetaCache == null) {
			volumeMetaCache = new ConcurrentHashMap<String, String>();
		}
		return volumeMetaCache;
	}

	/**
	 * 获得所有有效的Volume.
	 * 
	 * @param long id
	 * @return Volume 类型
	 */
	public static ConcurrentHashMap<Long, VolumeCacheObject> getAllVolumeConfig() {
		return volumeCache;
	}
}
