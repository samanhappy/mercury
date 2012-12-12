package com.clickoo.clickooImap;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.clickoo.clickooImap.config.ClickooImapProperties;
import com.clickoo.clickooImap.controller.ControllerAccount;
import com.clickoo.clickooImap.domain.IdleMessage;
import com.clickoo.clickooImap.domain.Notice;
import com.clickoo.clickooImap.domain.ServerInfo;
import com.clickoo.clickooImap.netty.client.MsgClient;
import com.clickoo.clickooImap.netty.server.MsgServer;
import com.clickoo.clickooImap.server.cache.ServerCacheMap;
import com.clickoo.clickooImap.server.state.ClickooImapState;
import com.clickoo.clickooImap.utils.CIConstants;
import com.clickoo.clickooImap.utils.CITools;
import com.clickoo.jms.JmsSender;
import com.clickoo.mercury.cache.MemCachedManager;
import com.clickoo.mercury.util.Constant;
import com.clickoo.mercury.util.JMSTypes;

public class Start {
	private static final Logger logger = LoggerFactory.getLogger(Start.class);
	
	
	public static void main(String[] args) {
		String flag = args[0].toString();
//		String flag = "true";
		ClickooImapProperties pd = new ClickooImapProperties();
		pd.init();
		ServerInfo clickooImapInfo = new ServerInfo();
		String currentIP = CITools.getCurrentServerIP();
		clickooImapInfo.setIp(currentIP);
		// 判断是否为Master,问题是服务器的启动必须确认死亡周期
		if (Boolean.parseBoolean(flag)) {
//			if(MemCachedManager.getMcc().keyExists(CIConstants.SERVER_LIST)){
//				MemCachedManager.getMcc().get(CIConstants.SERVER_LIST);
//			}
			if(new Start().ServerLive(currentIP,Constant.SERVER_MASTER)){return;}
			logger.info("------------Master server["+currentIP+"] start---------");
			// 维护服务器自己的信息列表
			clickooImapInfo.setServerType(CIConstants.ServerType.CI_SERVER_MASTER);
			ServerCacheMap.addCIServer(currentIP, clickooImapInfo);
			new CITools().addServerList(currentIP, CIConstants.ServerType.CI_SERVER_MASTER);
			// 启动定时器在Memcache维护自己的生存状态
			Thread stateThread = new Thread(new ClickooImapState());
			stateThread.start();
			//jms接收端
			new ClassPathXmlApplicationContext(new String[] {"spring-jms.xml" });
			
			//下发任务
			logger.info("--------Master control accounts-------");
			new ControllerAccount().start();
			
			//服务器启动时候启动Netty的Server和Client端
			new MsgServer().start();
			new MsgClient().start(CITools.getLocalServerIp());
			
		} else {
			if(new Start().ServerLive(currentIP,CIConstants.ServerType.CI_SERVER_COMMON)){return;}
			logger.info("------------common server["+currentIP+"] start---------");
			// 维护服务器自己的信息列表
			clickooImapInfo.setServerType(CIConstants.ServerType.CI_SERVER_COMMON);
			ServerCacheMap.addCIServer(currentIP, clickooImapInfo);
			
			// 启动定时器在Memcache维护自己的生存状态
			Thread stateThread = new Thread(new ClickooImapState());
			stateThread.start();
			
			// 消息监听
			new ClassPathXmlApplicationContext(new String[] { "spring-jms.xml" });
			
			//服务器启动时候启动Netty的Server和Client端
			new MsgServer().start();
			new MsgClient().start(CITools.getLocalServerIp());
			
			//通知所有服务器Netty的Client端连接该服务器的服务端，同时让该客户端连接所有服务器的服务端
			IdleMessage message = new IdleMessage();
			message.setFromIp(currentIP);
			message.setMsg(CIConstants.NoticeType.CI_NETTY_CLIENT_CONNECT);
			logger.info("Notice all all server to connect server["+currentIP+"] and the messagetype ["+message.getMsg()+"]");
			JmsSender.sendTopicMsg(message, JMSTypes.CLICKOO_IMAP_TOPIC);
			
			// 询问所有服务器谁是master
			logger.info("--------Sever[" + currentIP+ "]ask that who is master Master---------");
			Notice sendObj = new Notice();
			sendObj.setIp(currentIP);
			sendObj.setMsg(CIConstants.NoticeType.CI_FINDMASTER);
			JmsSender.sendTopicMsg(sendObj, JMSTypes.CLICKOO_IMAP_TOPIC);
		}
		logger.info("Server["+currentIP+"]..............."+ServerCacheMap.selectCIServer());
		
	}
	/**
	 * 服务器启动前的安全检查
	 */
	public boolean ServerLive(String currentIP,String serverType){
		boolean boolflag = false;
		CITools tools = new CITools();
		if(MemCachedManager.getMcc().keyExists(CIConstants.SERVER_LIST)){
			String deadTime = ClickooImapProperties.getConfigureMap().get("dead_time");
			ConcurrentHashMap<String, String> masterMap = CITools.getAllServerInfo();
			if(masterMap.containsKey(currentIP)){
				ServerInfo serverInfo = tools.getServerInfoByIP(currentIP);
				//false 当前服务器已经死亡 
				if(!CITools.isFailState(serverInfo.getDate(),CITools.getCurrentDate(),Long.parseLong(deadTime))){
					MemCachedManager.getMcc().delete(serverInfo.getIp());
					tools.deleteMasterList(serverInfo.getIp());
				}else{
					logger.info("------Listenering current ["+serverType+"] server is runing---------");
					boolflag = true;
				}
			}
		}
		return boolflag;
	}

}
