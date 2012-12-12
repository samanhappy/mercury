package com.dreamail.taskfactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dreamail.config.TaskProperties;
import com.dreamail.jms.JmsSender;
import com.dreamail.jms.Notice;
import com.dreamail.mercury.dal.dao.TaskFactoryDao;
import com.dreamail.mercury.pojo.Clickoo_task_factory;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JMSTypes;
import com.dreamail.mercury.util.JsonUtil;
import com.dreamail.state.TaskFactoryState;
import com.dreamail.taskfactory.msg.ServerCacheMap;
import com.dreamail.taskfactory.msg.ServerInfo;
import com.dreamail.utils.TFTools;

public class TFManager {
	public static final Logger logger = LoggerFactory
			.getLogger(TFManager.class);

	public static void main(String args[]) {
		// 配置文件加载
		TaskProperties pd = new TaskProperties();
		pd.init();

		ServerInfo taskFactoryInfo = new ServerInfo();
		String currentIP = TFTools.getCurrentServerIP();
		taskFactoryInfo.setIP(currentIP);
		// 判断是否为Master,问题是服务器的启动必须确认死亡周期
		String serverType = SecurityCheck(currentIP);
		logger.info("服务启动类型为：" + serverType);
		if (Constant.SERVER_MASTER.equals(serverType)) {
			// jms接收端
			new ClassPathXmlApplicationContext(new String[] {
					"quartz-spring.xml", "taskspring-jms.xml" });
			logger.info("------------master server start---------");
			// 维护服务器自己的信息列表
			taskFactoryInfo.setServerType(Constant.SERVER_MASTER);
			ServerCacheMap.addTF(currentIP, taskFactoryInfo);
			new TFTools().addMasterList(currentIP, Constant.SERVER_MASTER);
			// 启动定时器在Memcache维护自己的生存状态
			Thread stateThread = new Thread(new TaskFactoryState());
			stateThread.start();
			// 启动任务工厂Master主线程任务
			MasterThreads.start();
		} else if (Constant.SERVER_COMMON.equals(serverType)) {
			logger.info("------------common server start---------");
			// 维护服务器自己的信息列表
			taskFactoryInfo.setServerType(Constant.SERVER_COMMON);
			ServerCacheMap.addTF(currentIP, taskFactoryInfo);
			// 启动定时器在Memcache维护自己的生存状态
			Thread stateThread = new Thread(new TaskFactoryState());
			stateThread.start();
			// 消息监听
			new ClassPathXmlApplicationContext(
					new String[] { "taskspring-jms.xml" });
			// 询问所有服务器谁是master
			logger.info("--------Sever[" + currentIP
					+ "]who is master Master---------");
			Notice sendObj = new Notice();
			sendObj.setIP(currentIP);
			sendObj.setMsg(Constant.FINDMASTER);
			JmsSender.sendTopicMsg(sendObj, JMSTypes.TASKFACTORY_TOPIC);
			// 发送失败jms消息
			JmsSender.sendFailQueue();
		} else {
			logger.info("------Listenering current [" + currentIP
					+ "] server is runing---------");
		}
	}
	
	/**
	 * 服务器启动前安全检查
	 * @param currentIP 服务器地址
	 * @param String 启动类型
	 */
	public static String SecurityCheck(String currentIP) {
		String serverFlag = "";
		// Map记录服务器信息
		ConcurrentHashMap<String, String> serverInfoMap = new ConcurrentHashMap<String, String>();
		// Map记录服务器存活时间
		Map<String, String> serverTimerMap = new HashMap<String, String>();
		long deadTime = Long.parseLong(TaskProperties.getConfigureMap().get(Constant.TASK_FACTORY_DEAD_TIME));
		long listenerTime = Long.parseLong(TaskProperties.getConfigureMap().get(Constant.TASK_FACTORY_LISTENER_TIME));
		try {
			Thread.sleep(listenerTime * 60 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 获取服务器信息
		TaskFactoryDao taskFactoryDao = new TaskFactoryDao();
		List<Clickoo_task_factory> taskFactoryList = taskFactoryDao.getTaskParameterByType(Constant.TASK_FACTORY_DELETE_FLAG);
		Iterator<Clickoo_task_factory> itor = taskFactoryList.iterator();
		while (itor.hasNext()) {
			Clickoo_task_factory taskFactory = (Clickoo_task_factory) itor.next();
			if (taskFactory.getMckey().equals(Constant.TASK_FACTORY_KEY)) {
				serverInfoMap = (ConcurrentHashMap<String, String>) JsonUtil.stringToMap(taskFactory.getMcvalue());
			}
			serverTimerMap.put(taskFactory.getMckey(), taskFactory.getMcvalue());
		}
		// 局域网内存在服务器
		if (serverInfoMap.size() > 0) {
			String ipKey = TFTools.getServerIP(serverInfoMap, Constant.SERVER_MASTER);
			String ipServerTime = serverTimerMap.get(ipKey);
			String currentTime = TFTools.getCurrentDate();
			// master死亡
			if (!TFTools.isFailState(ipServerTime, currentTime, deadTime)) {
				if (serverInfoMap.containsValue(Constant.SERVER_SECOND)) {
					ipKey = TFTools.getServerIP(serverInfoMap, Constant.SERVER_SECOND);;
					ipServerTime = serverTimerMap.get(ipKey);
					currentTime = TFTools.getCurrentDate();
					// second死亡
					if (!TFTools.isFailState(ipServerTime, currentTime, deadTime)) {
						// 数据全部删掉
						serverFlag = Constant.SERVER_MASTER;
						TFTools.startDelete(Constant.TASK_FACTORY_DELETE_FLAG, "");
					} else {
						// second存活
						try {
							Thread.sleep(deadTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						// 是否为second
						if (ipKey.equals(currentIP)) {
							serverFlag = "";
						} else {
							serverFlag = Constant.SERVER_COMMON;
							serverInfoMap.remove(currentIP);
							String mapStr = JsonUtil.mapToString(serverInfoMap);
							TFTools.startDelete(Constant.TASK_FACTORY_FLAG, mapStr);
						}
					}
				}
				// 数据全部删掉
				else {
					serverFlag = Constant.SERVER_MASTER;
					TFTools.startDelete(Constant.TASK_FACTORY_DELETE_FLAG, "");
				}
			} else {
				// 是否为master
				if (ipKey.equals(currentIP)) {
					serverFlag = "";
				} else {
					Clickoo_task_factory clickooTaskFactory = taskFactoryDao.getTaskParameterByMCKey(currentIP);
					if (clickooTaskFactory != null) {
						ipServerTime = clickooTaskFactory.getMcvalue();
						currentTime = TFTools.getCurrentDate();
						if (!TFTools.isFailState(ipServerTime, currentTime,deadTime)) {
							// 删除自己数据
							serverFlag = Constant.SERVER_COMMON;
							serverInfoMap.remove(currentIP);
							String mapStr = JsonUtil.mapToString(serverInfoMap);
							TFTools.startDelete(Constant.TASK_FACTORY_FLAG, mapStr);
						} else {
							serverFlag = "";
						}
					} else {
						// 新服务器启动
						serverFlag = Constant.SERVER_COMMON;
					}
				}
			}
		} else {
			serverFlag = Constant.SERVER_MASTER;
		}
		return serverFlag;
	}
}
