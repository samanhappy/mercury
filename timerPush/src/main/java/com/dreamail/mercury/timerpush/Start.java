package com.dreamail.mercury.timerpush;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.timerpush.cache.ServerCacheMap;
import com.dreamail.mercury.timerpush.cache.ServerStateCache;
import com.dreamail.mercury.timerpush.cache.TimerInfoManager;
import com.dreamail.mercury.timerpush.config.TimerPushProperties;
import com.dreamail.mercury.timerpush.domain.Constants;
import com.dreamail.mercury.timerpush.domain.Notice;
import com.dreamail.mercury.timerpush.domain.ServerInfo;
import com.dreamail.mercury.timerpush.serverstate.TimerPushState;
import com.dreamail.mercury.timerpush.serverstate.Tools;
import com.dreamail.mercury.timerpush.timer.TimerOperator;
import com.dreamail.mercury.util.JMSTypes;

public class Start {
	private static final Logger logger = LoggerFactory.getLogger(Start.class);

	public static void main(String[] args) {
		String flag = args[0].toString();
		new TimerPushProperties().init();
		ServerInfo timerPushInfo = new ServerInfo();
		String currentIP = Tools.getLocalServerIpKey();
		timerPushInfo.setIp(currentIP);

		if (Boolean.parseBoolean(flag)) {
			if (new ServerStateCache().serverLive(currentIP,
					Constants.T_SERVER_MASTER)) {
				return;
			}
			logger.info("------------Master server[" + currentIP
					+ "] start---------");

			// 维护服务器自己的信息列表
			timerPushInfo.setServerType(Constants.T_SERVER_MASTER);
			ServerCacheMap.addTimerServer(currentIP, timerPushInfo);

			new ServerStateCache().addServerList(currentIP,
					Constants.T_SERVER_MASTER);

			Thread stateThread = new Thread(new TimerPushState());
			stateThread.start();

			// jms接收端
			new ClassPathXmlApplicationContext(
					new String[] { "spring-jms.xml" });

			// 定时数据缓存初始化
			TimerInfoManager.init();

			TimerOperator.timerPush();
		} else {
			if (new ServerStateCache().serverLive(currentIP,
					Constants.T_SERVER_COMMON)) {
				return;
			}
			logger.info("------------common server[" + currentIP
					+ "] start---------");

			// 维护服务器自己的信息列表
			timerPushInfo.setServerType(Constants.T_SERVER_COMMON);
			ServerCacheMap.addTimerServer(currentIP, timerPushInfo);

			Thread stateThread = new Thread(new TimerPushState());
			stateThread.start();

			// jms接收端
			new ClassPathXmlApplicationContext(
					new String[] { "spring-jms.xml" });

			// 询问所有服务器谁是master
			logger.info("--------Sever[" + currentIP
					+ "]ask that who is master Master---------");
			Notice sendObj = new Notice();
			sendObj.setIp(currentIP);
			sendObj.setMsg(Constants.FINDMASTER);
			JmsSender.sendTopicMsg(sendObj, JMSTypes.CLICKOO_TIMERPUSH_TOPIC);

		}
	}

}
