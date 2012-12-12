package com.dreamail.jms.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.config.TaskProperties;
import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.cache.ReceiveServerCacheManager;
import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.mail.context.ReceiveContexts;
import com.dreamail.mercury.pojo.Clickoo_mail_receive_server;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JMSTypes;

public class ControllerAccount {
	public static final Logger logger = LoggerFactory
			.getLogger(ControllerAccount.class);

	public void start() {
		// 当前服务器个数
		int sendCount = Integer.parseInt(
				TaskProperties.getConfigureMap().get("send_count"));
		// 当前账号个数
		int accountCount = new AccountDao().getAllValidAccountSize();
		long size = 0;
		if (accountCount > sendCount) {
			// 服务器加载账号个数
			size = accountCount / sendCount;
		} else {
			sendCount = accountCount;
			size = 1;
		}
		Map<String, Long> map = new HashMap<String, Long>();
		for (int i = 0; i < sendCount; i++) {
			long first = size * (i);
			map.put("first", first);
			if (i == sendCount) {
				map.put("second", size + sendCount);
			} else {
				map.put("second", size);
			}
			LinkedList<Context> contextList = (LinkedList<Context>) 
						ReceiveContexts.getPrePushMailContexts(map);
			StringBuffer tm = new StringBuffer(Constant.NOTICELOOP);
			for (Context context : contextList) {
				// 获取aid的定时频率
				String accountType = context.getLoginName().split("@")[1];
				System.out.println(accountType);
				Clickoo_mail_receive_server receiveServer = 
					ReceiveServerCacheManager.getCacheObject(accountType);
				if (receiveServer.getIntervaltime() > 0) {
					tm.append(",").append(context.getAccountId()).append("-")
					.append(receiveServer.getIntervaltime())
					.append("-").append(context.getAccountType());
				} else {
					logger.error("no server config found for:"+ context.getLoginName());
				}
			}
			JmsSender.sendQueueMsg(tm.toString(), JMSTypes.TASK_QUEUE);
			logger.info("---Controller send---[" + tm.toString() + "]");
			logger.info("controller [" + contextList.size()+ "] account sender success!");
		}
	}
}
