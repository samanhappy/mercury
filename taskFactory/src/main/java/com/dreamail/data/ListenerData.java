package com.dreamail.data;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.config.TaskProperties;
import com.dreamail.mercury.cache.VolumeCacheManager;
import com.dreamail.mercury.cache.domain.VolumeCacheObject;
import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.dao.VolumeDao;
import com.dreamail.mercury.dal.service.DeleteMessageService;
import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.mail.delete.CleanDataTask;
import com.dreamail.mercury.pojo.Clickoo_mail_account;

/**
 * 启动守护进程定时删除脏数据
 */
public class ListenerData implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(ListenerData.class);

	/**
	 * 删除脏数据
	 */
	@Override
	public void run() {
		boolean sendflag = true;
		String strInterval = TaskProperties.getConfigureMap().get(
				"listener_mail_data");
		logger.info("remove rubbish data..............");
		// 删除message
		MessageService MessageService = new MessageService();
		MessageService.deleteDirty();

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

		// 删除mail
		new DeleteMessageService().getDeleteMessages(Integer
				.parseInt(strInterval));
		logger.info("---------delete email ---------");

		List<Clickoo_mail_account> accountList = new AccountDao()
				.getAllAccounts();
		CleanDataTask.dataClean(accountList);
	}
}
