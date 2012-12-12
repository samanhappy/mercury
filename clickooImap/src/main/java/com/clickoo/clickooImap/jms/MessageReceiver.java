package com.clickoo.clickooImap.jms;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.clickoo.clickooImap.domain.IdleMessage;
import com.clickoo.clickooImap.domain.Notice;
import com.clickoo.clickooImap.netty.client.MsgClient;
import com.clickoo.clickooImap.server.cache.ServerAccountsCache;
import com.clickoo.clickooImap.server.handler.impl.ServerMaster;
import com.clickoo.clickooImap.server.handler.impl.ServerSecond;
import com.clickoo.clickooImap.server.idle.ImapIdleConnect;
import com.clickoo.clickooImap.threadpool.impl.ConnectController;
import com.clickoo.clickooImap.utils.CIConstants;
import com.clickoo.mercury.util.EmailUtils;
import com.clickoo.mercury.util.JMSConstans;

public class MessageReceiver {

	private static Logger logger = LoggerFactory
			.getLogger(MessageReceiver.class);

	public void onMessage(Object msg) {
		if (msg instanceof Notice) {
			Notice notice = (Notice) msg;
			String message = notice.getMsg();
			logger.info("------------------receive[" + message + "] request ["+notice+"]");
			//接受寻找master请求通知
			if (CIConstants.NoticeType.CI_FINDMASTER.equals(message)) {
				logger.info("---------server[" + notice.getIp()+ "]start，who is Master?");
				// 监听server，记录server info，设置Second，通知服务器谁是Master
				ServerMaster serverMaster = new ServerMaster();
				serverMaster.handleMsg(notice);
			}
			//接受master的通知
			else if (CIConstants.NoticeType.CI_MASTERNOTICE.equals(message)) {
				// second设置
				ServerSecond serverSecond = new ServerSecond();
				serverSecond.handleMsg(notice);
			} 
		}else if(msg instanceof IdleMessage){
			IdleMessage notice = (IdleMessage) msg;
			String message = notice.getMsg();
			logger.info("------------------receive[" + message + "]request");
			//将收到的账户加到线程池建立idle连接（第一次启动master服务器时候使用）
			if(CIConstants.NoticeType.CI_NOTICEIDLE.equals(message)){
				ConnectController.addIdleConnect(notice);
			}
			//对各个服务器本地缓存上的账号增加
			else if (CIConstants.NoticeType.CI_SERVER_ADD_ACCOUNT.equals(message)){
				ServerAccountsCache.addServerAccounts(notice);
			}
			//对各个服务器本地缓存上的账号删除
			else if (CIConstants.NoticeType.CI_SERVER_DEL_ALL_ACCOUNT.equals(message)){
				ServerAccountsCache.removeServerAccountsByIp(notice.getFromIp());
			}
			//各个服务器的Clinet去连接该IP地址服务器的Server,同时将该IP加入本地账号缓存
			else if(CIConstants.NoticeType.CI_NETTY_CLIENT_CONNECT.equals(message)){
				new MsgClient().start(notice.getFromIp().split("_")[1]);
//				ServerAccountsCache.addServer(notice.getFromIp());
			}
			//将账号从本地缓存中移除
			else if(CIConstants.NoticeType.CI_SERVER_DEL_ACCOUNT.equals(message)){
				ServerAccountsCache.removeOfflineAccount(notice.getAccountName());
			}
		}else if(msg instanceof String){
			String message = (String) msg;
			JSONObject msgObject = JSONObject.fromObject(message);
			if(JMSConstans.JMS_IMAP_IDLE_TYPE.equals(msgObject.get(JMSConstans.JMS_TYPE_KEY))){
				logger.info("-------------receive[" + message + "] request ["+msgObject.get(JMSConstans.JMS_LINE_KEY)+"]");
				//用户上线、注册
				if (JMSConstans.JMS_ONLINE_VALUE.equals(msgObject.get(JMSConstans.JMS_LINE_KEY))
						|| JMSConstans.JMS_ADDLINE_VALUE.equals(msgObject.get(JMSConstans.JMS_LINE_KEY))) {
					IdleMessage idleMessage = new IdleMessage();
					idleMessage.setAid(msgObject.getLong(JMSConstans.JMS_AID_KEY));
					idleMessage.setAccountName(msgObject.getString(JMSConstans.JMS_LOGINID_KEY));
					//base64解码
					idleMessage.setAccountPwd(new String(EmailUtils.base64TochangeByte(msgObject
							.getString(JMSConstans.JMS_PASSWORD_KEY))));
					
					ServerAccountsCache.loadBalancing(idleMessage);
				}
				// 用户下线、注销
				else if (JMSConstans.JMS_OFFLINE_VALUE.equals(msgObject.get(JMSConstans.JMS_LINE_KEY))
						|| JMSConstans.JMS_DELETELINE_VALUE.equals(msgObject.get(JMSConstans.JMS_LINE_KEY))) {
					String accountName = msgObject.getString(JMSConstans.JMS_LOGINID_KEY);
					// 将该账号从各服务器本地缓存中删掉并结束idle连接
					ServerAccountsCache.removeOfflineAccount(accountName);
					ImapIdleConnect.closeIdleConnection(accountName);
				}
				//修改
				else if(JMSConstans.JMS_MODIFYLINE_VALUE.equals(msgObject.get(JMSConstans.JMS_LINE_KEY))){
					IdleMessage idleMessage = new IdleMessage();
					idleMessage.setAid(msgObject.getLong(JMSConstans.JMS_AID_KEY));
					idleMessage.setAccountName(msgObject.getString(JMSConstans.JMS_LOGINID_KEY));
					//base64解码
					idleMessage.setAccountPwd(new String(EmailUtils.base64TochangeByte(msgObject
							.getString(JMSConstans.JMS_PASSWORD_KEY))));
					ImapIdleConnect.closeIdleConnection(idleMessage.getAccountName());
					ServerAccountsCache.loadBalancing(idleMessage);
				}
			}
		}
	}
}
