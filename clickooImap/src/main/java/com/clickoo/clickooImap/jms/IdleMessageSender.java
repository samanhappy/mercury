package com.clickoo.clickooImap.jms;


import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.clickoo.clickooImap.domain.IdleMessage;
import com.clickoo.clickooImap.domain.Notice;
import com.clickoo.clickooImap.utils.CITools;
import com.clickoo.jms.JmsSender;
import com.clickoo.jms.JmsSenderProvider;
import com.clickoo.mercury.util.JMSConstans;
import com.clickoo.mercury.util.JMSTypes;

public class IdleMessageSender {

	private static Logger logger = LoggerFactory.getLogger(IdleMessageSender.class);
	/**
	 * 通知C工程收邮件
	 */
	public static void sendMsgToCAD(String uuid,long aid){
		JSONObject message = new JSONObject();
		message.put(JMSConstans.JMS_TYPE_KEY,"imapidle");
		message.put(JMSConstans.JMS_UUID_KEY, uuid);
		message.put(JMSConstans.JMS_AID_KEY, aid);
		try {
			JmsSenderProvider.getInstance().sendMessage(null,JMSTypes.YAHOO_QUEUE, message.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("---sendMsgToCAD---------[ "+uuid+" ]" );
	}
	/**
	 * 将账号分配到正常运行的服务器上
	 * @param idleMessage
	 */
	public static void sendAccountToIdle(IdleMessage idleMessage){
		JmsSender.sendQueueMsg(idleMessage, JMSTypes.IDLE_QUEUE);
		logger.info("---sendAccountToIdle---["+idleMessage.getMsg()+"]["+idleMessage.getAid()+"]["+idleMessage.getAccountName()+"]["+idleMessage.getAccountPwd()+"]");
	}
	/**
	 * 对服务器缓存中的账号进行处理
	 * @param idleMessage
	 */
	public static void processAccount(IdleMessage idleMessage){
		IdleMessage message = new IdleMessage();
		//每次创建新idle连接后通知所有服务器更新本地缓存账户信息
		message.setAid(idleMessage.getAid());
		message.setAccountName(idleMessage.getAccountName());
		message.setAccountPwd(idleMessage.getAccountPwd());
		message.setMsg(idleMessage.getMsg());
		message.setFromIp(CITools.getCurrentServerIP());
		message.setFlag(idleMessage.getFlag());
		message.setType(idleMessage.getType());
		JmsSender.sendTopicMsg(message, JMSTypes.CLICKOO_IMAP_TOPIC);
		logger.info("ProcessAccount---processAccount---["+message.getMsg()+"]["+message.getAid()+"]["+message.getAccountName()+"]["+message.getAccountPwd()+"]");
	}
	/**
	 * 通知各个服务器删除本地缓存中保存的挂掉的服务器的账号信息
	 * @param failedIp
	 */
	public static void syncServerAccounts(Notice notice){
		JmsSender.sendTopicMsg(notice, JMSTypes.CLICKOO_IMAP_TOPIC);
		logger.info("Server ["+notice.getIp()+"] has failed , message type ["+notice.getMsg()+"],plase remove from accountsCache");
	}
}
