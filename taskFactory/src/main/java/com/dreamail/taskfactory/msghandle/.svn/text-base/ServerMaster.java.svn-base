package com.dreamail.taskfactory.msghandle;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dreamail.jms.JmsSender;
import com.dreamail.jms.Notice;
import com.dreamail.jms.controller.ControllerAccount;
import com.dreamail.jms.loop.LoopReceiveHandle;
import com.dreamail.mercury.util.Constant;
import com.dreamail.taskfactory.msg.ServerCacheMap;
import com.dreamail.taskfactory.msg.ServerInfo;
import com.dreamail.utils.TFTools;

public class ServerMaster implements IServerHandel {

	public static final Logger logger = LoggerFactory
			.getLogger(ServerMaster.class);
	public static TFTools tools = new TFTools();
	public static String currentIP  = TFTools.getCurrentServerIP();

	public void handleMsg(Notice notice) {
		String commonIP = "";// 普通common服务器
		boolean isSecond = false;// 是否存在second服务器
		logger.info("--------" + notice.getMsg() + "---------");
		// 当前服务器类型
		ServerInfo taskFactoryInfo = ServerCacheMap.selectTF().get(currentIP);
		// 只有Master记录taskFactory信息,自己发送的JMS信息不做处理
		if ((!currentIP.equals(notice.getIP()) 
				&& taskFactoryInfo.getServerType().equals(Constant.SERVER_MASTER))
				|| "[change master]".equals(notice.getMsg())) {
			logger.info("------You Are Master-----");
			// 记录所有服务器
			ServerInfo noticeInfo = new ServerInfo();
			noticeInfo.setIP(notice.getIP());
			if ("[change master]".equals(notice.getMsg())) {
				noticeInfo.setServerType(Constant.SERVER_MASTER);
			} else {
				noticeInfo.setServerType(Constant.SERVER_COMMON);
			}
			ServerCacheMap.addTF(notice.getIP(), noticeInfo);
			if ("[change master]".equals(notice.getMsg())) {
				// second升级为master从缓存服务器中判断
				isSecond = "".equals(getServerFromServerCache(Constant.SERVER_SECOND)) ? false: true;
				// 普通服务器
				commonIP = getServerFromServerCache(Constant.SERVER_COMMON);
			} else {
				// master从本地缓存中判断是否存在second server
				isSecond = "".equals(getServerFromLocationCache(Constant.SERVER_SECOND)) ? false: true;
				// 普通服务器
				commonIP = getServerFromLocationCache(Constant.SERVER_COMMON);
			}
			//master 已经确定了  1.change mster  2.master监控
			if (isSecond) {
				//second 存在
				logger.info("-----------------second server still live ");
				// 更新本地缓存
				ServerInfo secondServer = new ServerInfo();
				secondServer.setServerType(Constant.SERVER_COMMON);
				ServerCacheMap.addTF(notice.getIP(), secondServer);
				TFTools.getAllServerInfo();
				tools.addMasterList(notice.getIP(), Constant.SERVER_COMMON);
			} else {
				//设置second
				if (!commonIP.equals("")) {
					logger.info("--------master[" + currentIP + "]set["+ commonIP + "] is second");
					// 更新缓存服务器
					handleCache(commonIP);
				} 
				else if (commonIP.equals("") && currentIP.equals(notice.getIP())) {
//					logger.info("--------master[" + currentIP + "]set["+ notice.getIP() + "] is second");
//					handleCache(notice.getIP());
					logger.info("------Master Server only one!-------");
				} 
				else {
					logger.info("------Master Server only two!-------");
				}
			}
			//停止自己
			Notice sendStopLoopObj = new Notice();
			sendStopLoopObj.setMsg(Constant.STOPLOOP);
			sendStopLoopObj.setIP("");
			LoopReceiveHandle.handelMsg(sendStopLoopObj);
			//提前设置
			TFTools.setSyncNumber();
			// 通知所有服务器停止邮件收取
			sendStopLoopObj.setIP(currentIP);
			JmsSender.sendTopicMsg(sendStopLoopObj, "TaskFactoryTopic");
			logger.info("-----------notice Common Sever stop receive mail!");
			boolean flag = true;
			long beginMills = System.currentTimeMillis();
			long endMills = 0;
			while(flag){
				if(TFTools.isContinue()){
					// 通知所有服务器谁是master服务器
					Notice sendObj = new Notice();
					sendObj.setIP(currentIP);
					sendObj.setMsg(Constant.MASTERNOTICE);
					JmsSender.sendTopicMsg(sendObj, "TaskFactoryTopic");
					logger.info("-----------notice Common Sever[" + currentIP+ "]is Master server");
					if ("[change master]".equals(notice.getMsg())) {
						//守护进程
						new ClassPathXmlApplicationContext("quartz-spring.xml");
					}
					TFTools.remarkSyncNumber();
					flag = false;
					
					//启动任务工厂Master主线程任务
//					MasterThreads.start();
					new ControllerAccount().start();
					
					/*Map<String, String> maps = TaskProperties.getConfigureMap();
					WebAccount account = new WebAccount();
					account.setName(maps.get("name"));
					account.setPassword(maps.get("password"));
					account.setSendHost(maps.get("sendhost"));
					account.setSendPort(maps.get("sendport"));
					SendOperator operator = new SendOperator();
					WebEmail email = new WebEmail();
					email.setAccount(account);
					try {
						operator.sendMailWithoutSave(email,maps.get("master_change_subject")
								,maps.get("master_change_content") + currentIP);
						logger.info("master change send mail ");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						logger.info("master change send mail but UnsupportedEncodingException");
					} catch (AuthenticationFailedException e) {
						e.printStackTrace();
						logger.info("master change send mail but AuthenticationFailedException");
					} catch (MessagingException e) {
						e.printStackTrace();
						logger.info("master change send mail but MessagingException");
					}*/
				}else{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				endMills = System.currentTimeMillis();
				if((endMills - beginMills) > 1000 * 60 * 20){
					logger.info("server fail when wait for stop notice..");
					break;
				}
			}
		}
	}

	/**
	 * 根据serverType获取缓存服务器IP
	 */
	public String getServerFromServerCache(String serverType) {
		String serverIP = "";
		Iterator<String> itor = (Iterator<String>) TFTools.getAllServerInfo()
				.keySet().iterator();
		while (itor.hasNext()) {
			ServerInfo tfInfo = tools.getServerInfoByIP(itor.next());
			if (tfInfo.getServerType().equals(serverType)
					&& !tfInfo.getIP().equals(currentIP)) {
				serverIP = tfInfo.getIP();
				break;
			}
		}
		return serverIP;
	}

	/**
	 * 根据serverType获取本地服务器IP
	 */
	public String getServerFromLocationCache(String serverType) {
		String serverIP = "";
		Iterator<String> itor = (Iterator<String>) ServerCacheMap.selectTF()
				.keySet().iterator();
		while (itor.hasNext()) {
			ServerInfo tfInfo = ServerCacheMap.selectTF().get(itor.next());
			if (tfInfo != null && tfInfo.getServerType().equals(serverType)
					&& !tfInfo.getIP().equals(currentIP)) {
				serverIP = tfInfo.getIP();
				break;
			}
		}
		return serverIP;
	}

	/**
	 * 更新缓存服务器和本地缓存
	 */
	public void handleCache(String targetIP) {
		// 更新缓存服务器
		ServerInfo secondServer = new ServerInfo();
		//-----hysen-------
		//tools.deleteServerInfo(targetIP);
		secondServer.setIP(targetIP);
		secondServer.setServerType(Constant.SERVER_SECOND);
		secondServer.setDate(TFTools.getCurrentDate());
		//------hysen----
		//tools.addServerInfo(targetIP, secondServer);
		tools.addMasterList(targetIP, secondServer.getServerType());
		// 更新本地缓存
		ServerCacheMap.addTF(targetIP, secondServer);
	}
}
