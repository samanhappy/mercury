package com.dreamail.taskfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.data.ListenerMemoryData;
import com.dreamail.handle.SendMailFailHandler;
import com.dreamail.handle.UserOfflineHandlerTimer;
import com.dreamail.jms.JmsSender;
import com.dreamail.jms.controller.ControllerAccount;

public class MasterThreads {

	private static Logger logger = LoggerFactory.getLogger(MasterThreads.class);

	public static void start() {
		// 下发任务
		logger.info("--------task factory master process start-------");
		new ControllerAccount().start();

		// 发送失败jms消息
		JmsSender.sendFailQueue();

		// 用户下线账号处理
		new UserOfflineHandlerTimer().start();

		// 删除内存表数据
		new ListenerMemoryData().start();

		// 失败邮件重发任务
		SendMailFailHandler.startSenderTimer();
		
		logger.info("--------task factory master process end-------");
	}

}
