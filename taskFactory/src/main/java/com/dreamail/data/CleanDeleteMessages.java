package com.dreamail.data;

import java.io.File;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.config.TaskProperties;
import com.dreamail.mercury.cache.VolumeCacheManager;
import com.dreamail.mercury.cache.domain.VolumeCacheObject;
import com.dreamail.mercury.dal.dao.VolumeDao;
import com.dreamail.mercury.mail.delete.CleanDataTask;

/**
 * 启动守护进程定时删除做了删除标记的数据.
 */
public class CleanDeleteMessages implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(CleanDeleteMessages.class);

	/**
	 * 删除做了删除标记的数据
	 */
	@Override
	public void run() {
		boolean sendflag = true;
		logger.info("remove delete status message..............");
		// 更新硬盘信息
		logger.info("disk space ..................");
		ConcurrentHashMap<Long, VolumeCacheObject> volumeCache = VolumeCacheManager
				.getAllVolumeConfig();
		Set<Entry<Long, VolumeCacheObject>> entryseSet = volumeCache.entrySet();
		VolumeDao volumeDao = new VolumeDao();
		for (Entry<Long, VolumeCacheObject> entity : entryseSet) {
			VolumeCacheObject volumeCacheObject = (VolumeCacheObject) entity
					.getValue();
			String location = volumeCacheObject.getPath();
			File file = new File(location);
			long space = (file.getFreeSpace()) / 1024 / 1024 / 1024;
			volumeDao.updateDiskState(VolumeCacheManager
					.parseToPojo(volumeCacheObject));
			Integer yellowSpace = Integer.parseInt(TaskProperties
					.getConfigureMap().get("yellowspace"));
			if (space < yellowSpace && sendflag) {
				// 发送邮件给管理员
				logger.info("disk full..............");
				sendflag = false;
			}
		}

		CleanDataTask.cleanDeleteStatusMessages();
	}
}
