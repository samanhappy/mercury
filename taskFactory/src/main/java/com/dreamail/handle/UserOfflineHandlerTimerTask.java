package com.dreamail.handle;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.config.TaskProperties;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.dal.service.UARelationService;
import com.dreamail.talaria.memcached.UPECacheManager;
import com.dreamail.talaria.memcached.UPEMapping;

public class UserOfflineHandlerTimerTask extends TimerTask {

	private static Logger logger = LoggerFactory
			.getLogger(UserOfflineHandlerTimerTask.class);

	private static long timeout = Long.valueOf(TaskProperties.getConfigureMap()
			.get("juage_timeout"));

	@Override
	public void run() {
		logger.info("start user offline handle process...");

		long request_ts = 0;
		long now_ts = System.currentTimeMillis();
		UPEMapping mapping = null;
		String IMEI = null;
		List<String> offlineUids = new ArrayList<String>();
		List<String> IMEIList = new UserDao().getAllOnlineIMEI();

		UPEMapping[] mappings = UPECacheManager.getMappingObjectArray(IMEIList);
		if(mappings != null){
			for (int i = 0; i < mappings.length; i++) {
				mapping = mappings[i];
				IMEI = mappings[i].getIMEI();
				if (mapping == null) {
					long uid = new UserDao().getUidByIMEI(IMEI);
					if (uid > 0) {
						offlineUids.add(String.valueOf(uid));
					}
				} else if (mapping != null && mapping.getUid() != null) {
					request_ts = Long.parseLong(mapping.getRequest_ts());
					// 判断是否下线
					if ((now_ts - request_ts) > timeout) {
						logger.info("\nuser as IMEI " + IMEI + " is offline...\n");
						offlineUids.add(mapping.getUid());
						// 删除关系映射缓存
						UPECacheManager.getMappingLock(IMEI);
						UPECacheManager.deleteMappingObject(IMEI);
						UPECacheManager.removeMappingLock(IMEI);
					}
				} else {
					logger.info("mapping object is null or uid is null");
				}
			}
		}
		new UARelationService().setUserAutoOffline(offlineUids);
		logger.info("end user offline handle process...");

	}
}
