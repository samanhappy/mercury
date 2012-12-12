package com.dreamail.mercury.mail.truepush.idle.impl;

import java.util.Properties;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.event.MessageCountAdapter;

import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.mail.truepush.IdleTruepush;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.JMSTypes;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

public class IdleImpl implements IdleTruepush{
	
	private static final Logger logger = (Logger) LoggerFactory
	.getLogger(IdleImpl.class);
	private String account = null;
	private String pwd = null;
	
	@Override
	public void idle(Context context) {
		account = context.getAccount().getName();
		pwd = context.getPassword();
		IMAPStore store = null;
		IMAPFolder inbox = null;
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.setProperty("mail.imap.port", "993");
		Session session = Session.getInstance(props);
		session.setDebug(true);
		final long aid = context.getAccountId();
		try {
			store = (IMAPStore) session.getStore("imaps");
			store.connect("imap.gmail.com", account, pwd);
			inbox = (IMAPFolder) store.getFolder("inbox");
			inbox.open(Folder.READ_WRITE);
			inbox.addMessageCountListener(new MessageCountAdapter() {
				long startTimer = System.currentTimeMillis();
				public void messagesAdded(javax.mail.event.MessageCountEvent e) {
					logger.info(account+"have a new message");
//					while(System.currentTimeMillis() - startTimer < 1*60*1000){
//						;
//					}
					long endTimer = System.currentTimeMillis();
					if(endTimer - startTimer <1*60*1000){
						logger.info(account+"sleep 1 min...");
						try {
							Thread.sleep(1*60*1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					sendNewMailNotice(aid);
					startTimer = System.currentTimeMillis();
					//发送消息
					
				}
			});
			inbox.idle();
		} catch (javax.mail.AuthenticationFailedException e) {
			logger.error(account+" idle err...",e);
		} catch (NoSuchProviderException e) {
			logger.error(account+" idle err...",e);
		} catch (MessagingException e) {
			logger.error(account+" idle err...",e);
		} finally {
			try {
				if (store != null) {
					store.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 下发收邮件任务.
	 * @param aid
	 */
	protected void sendNewMailNotice(long aid){
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_NEWMAIL_TYPE);
		json.put(JMSConstans.JMS_AID_KEY, aid);
		logger.info("sendMsg:" + json.toString());
		JmsSender.sendQueueMsg(json.toString(), JMSTypes.GMAIL_QUEUE);
	}
	
}
