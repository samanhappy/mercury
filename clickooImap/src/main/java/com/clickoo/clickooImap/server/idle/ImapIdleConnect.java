package com.clickoo.clickooImap.server.idle;

import java.util.HashMap;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.event.MessageCountAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.clickoo.clickooImap.domain.IdleMessage;
import com.clickoo.clickooImap.jms.IdleMessageSender;
import com.clickoo.clickooImap.utils.CIConstants;
import com.clickoo.clickooImap.utils.CITools;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

public class ImapIdleConnect {
	private static final Logger logger = LoggerFactory.getLogger(ImapIdleConnect.class);
	static HashMap<String, IMAPStore> storeMap = new HashMap<String, IMAPStore>();
	/**
	 * 利用javaMail中对imap idle的支持来保持连接，监听账户邮箱变化
	 * 目前只有Gmail服务器支持idle
	 * @param connectionInfo
	 */
	public static void doIdleConnect(final IdleMessage idleMessage) {
		IMAPStore store = null;
		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		props.setProperty("mail.imap.port", "993");
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);
		try {
			store = (IMAPStore) session.getStore("imaps");
			logger.info("\n-----------imap idle connect :["+idleMessage.getAccountName()+"]----------------");
			store.connect("imap.gmail.com", idleMessage.getAccountName(),idleMessage.getAccountPwd());
			storeMap.put(idleMessage.getAccountName(), store);
			final IMAPFolder inbox = (IMAPFolder) store.getFolder("inbox");
			inbox.open(Folder.READ_ONLY);
			inbox.addMessageCountListener(new MessageCountAdapter() {
				public void messagesAdded(javax.mail.event.MessageCountEvent e) {
					Message[] message = e.getMessages();
					for (Message msg : message) {
						//每当有新邮件时候通知C工程收新邮件
						try {
							if(idleMessage.getFlag()==1){
								IdleMessageSender.sendMsgToCAD(String.valueOf(inbox.getUID(msg)),idleMessage.getAid());
							}else if(idleMessage.getFlag()==2){
								//通知idle手机有新邮件来了
								//.....................
							}else if(idleMessage.getFlag()==3){
								IdleMessageSender.sendMsgToCAD(String.valueOf(inbox.getUID(msg)),idleMessage.getAid());
								//通知idle手机有新邮件来了
								//.....................
							}
							logger.info("\n----------Account :[ "+idleMessage.getAid()+" ]["+idleMessage.getAccountName()+"] has new message");
						} catch (MessagingException e1) {
							logger.error("fail to notice cad");
						}
					}
				}
				public void messagesRemoved(javax.mail.event.MessageCountEvent e) {
				}
			});
			inbox.idle();
		} catch (MessagingException e) {
			idleMessage.setMsg(CIConstants.NoticeType.CI_SERVER_DEL_ACCOUNT);
			idleMessage.setFromIp(CITools.getCurrentServerIP());
			IdleMessageSender.processAccount(idleMessage);
			//通知客户端连接不上
			
			logger.error("Imap idle connecting failed : AccountName ["+idleMessage.getAccountName()+"]");
		}
	}
	/**
	 * 关闭账号连接（用户下线，密码修改，删除账号）
	 * @param AccountName
	 */
	public static void closeIdleConnection(String AccountName){
		if(storeMap.containsKey(AccountName)){
			IMAPStore store = storeMap.get(AccountName);
			try {
				store.close();
				storeMap.remove(AccountName);
			} catch (MessagingException e) {
				logger.info("Fail to closeIdleConnection :["+AccountName+"]");
			}
		}
	}
}
