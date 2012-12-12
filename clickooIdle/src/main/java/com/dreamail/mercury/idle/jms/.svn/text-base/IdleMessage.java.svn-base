package com.dreamail.mercury.idle.jms;

import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.Start;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.util.JMSConstans;


public class IdleMessage {
	private static Logger logger = LoggerFactory
	.getLogger(IdleMessage.class);
	
	/**
	 * 接受新邮件，和删除邮件
	 * @param msg
	 */
	public void onMessage(Object msg){
		// 判断消息类型
		if (!(msg instanceof String)) {
			logger.error("\nmsg instance is " + msg.getClass());
			return;
		}

		String msgStr = (String) msg;
		logger.info("message jms str is:" + msgStr);


			JSONObject json = null;
			try {
				json = JSONObject.fromObject(msgStr);
			} catch (Exception e) {
				logger.error("messge str is not in json format");
				return;
			}

			if (json != null) {
				String type = json.getString(JMSConstans.JMS_TYPE_KEY);
				 if (JMSConstans.JMS_NEWMAIL_TYPE.equals(type)) {
					/**
					 * 处理新邮件通知JMS消息
					 */
					String aid = json.getString(JMSConstans.JMS_AID_KEY);
					String accountName = json.getString(JMSConstans.JMS_ACCOUNT_NAME_KEY);
					if(aid==null || "".equals(aid)){
						logger.error("aid is null.........");
						return;
					}
					if(accountName==null || "".equals(accountName)){
						logger.info("accountName is null......");
						return;
					}
					if(Start.session.containsKey(accountName)){
						CopyOnWriteArrayList<ImapSession> list = Start.session.get(accountName);
						MessageDao dao = new MessageDao();
						Clickoo_message message = dao.getMessageCountByAID(Long.parseLong(aid));
						for(ImapSession session : list){
							if(session.getContext().getChannel().isConnected()){
//								session.updateMessageList();
								logger.info("idle return message: "+message.getMsgCount()+" EXISTS");
								session.getContext().getChannel().write("* "+message.getMsgCount()+" EXISTS"+"\r\n");
							}else{
								logger.info("Channel is colsed userName is:"+session.getUser().getUserName());
							}
						}
					}else{
						logger.info("no "+accountName+"idle in the here");
					}
				} else {
					logger.error("not handle process for jms message type");
				}
			}
	}
}
