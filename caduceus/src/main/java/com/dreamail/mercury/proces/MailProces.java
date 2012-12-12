package com.dreamail.mercury.proces;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.mail.jms.ReceiveMain;
import com.dreamail.mercury.mail.util.EmailConstant;
import com.dreamail.mercury.mail.util.EmailReceiveUtil;
import com.dreamail.mercury.receiver.mail.threadpool.largemail.PutLargeMailThreadPool;
import com.dreamail.mercury.util.JMSConstans;

public class MailProces  {
	private static final Logger logger = LoggerFactory
			.getLogger(MailProces.class);

	private static List<String> proces = new ArrayList<String>();
	
	static{
		proces.add("com.dreamail.mercury.proces.EmailReceiverProces");
		//proces.add("com.dreamail.mercury.proces.EmailParserProces");
		proces.add("com.dreamail.mercury.mail.receiver.attachment.impl.AttachmentFormatControl");
		proces.add("com.dreamail.mercury.proces.SaveDataProces");
	}
	
	public void doProces(Context context) throws Exception{
		
		try {
			Context con = null;
			for(String className:proces){
				IProces proces = (IProces) Class.forName(className).newInstance();
				try {
					proces.doProces(context);
				} catch (Exception e) {
					logger.info("className:"+className + " excute err.");
				}
				if("com.dreamail.mercury.proces.EmailReceiverProces".equals(className)){
					if(context.getLargeMessageList() == null || context.getLargeMessageList().size()==0){
						//context包含list对象.
						con = EmailReceiveUtil.changeContext(context);
						con.setDisconnectTime(System.currentTimeMillis());
						con.setConnectStatus(EmailConstant.ACCOUNT_DISCONNECT);
						logger.info("common:"+context.getLargeMessageList()+"  distime:"+con.getDisconnectTime());
					}else{
						con = EmailReceiveUtil.changeContext(context);
						con.setConnectStatus(EmailConstant.ACCOUNT_CONNECT);
						logger.info("large:"+context.getLargeMessageList().size()+"  distime:"+con.getDisconnectTime());
					}
					if(ReceiveMain.contextMap.containsKey(String.valueOf(context.getAccountId()))){
						logger.info("map exist:"+context.getAccountId());
						ReceiveMain.contextMap.put(String.valueOf(context.getAccountId()), con);
					}
					
				}
			}
		} catch (Exception e1) {
			logger.error("e",e1);
		}
		logger.info("accounut：" + context.getLoginName() + "logger:");
		if (context.getEmailList() != null) {
			for (String mid : context.getEmailList().keySet()) {
				Email e = context.getEmailList().get(mid);
				if (e.getnAttachList() != null) {
					logger.info("att number：" + e.getnAttachList().size());
				}
				logger.info("Message ID：" + e.getUuid());
				logger.info("message subject：" + e.getSubject());
				logger.info("message date====================");
				if (e.getBody() != null) {
					logger.info(new String(e.getBody()));
				} else {
					logger.info("body is null");
				}
				
				logger.info("message date end=====================");
			}
			if(context.getEmailList().size()>0){
				try{
					JSONObject json = new JSONObject();
					json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_NEWMAIL_TYPE);
					json.put(JMSConstans.JMS_AID_KEY, context.getAccountId());
					json.put(JMSConstans.JMS_ACCOUNT_NAME_KEY, context.getAccount().getName());
					logger.info("sendMsg:" + json.toString());
					JmsSender.sendTopicMsg(json.toString(), "messageTopic");
				}
				catch (Exception e) {
					
					logger.error("sendTopicMsg  messageTopic error...",e);
				}
			}
		}else{
			logger.info("account[" + context.getLoginName() +"]not have new Message!");
		}
		if (context.getLargeMessageList() != null
				&& context.getLargeMessageList().size() > 0
				&& !context.isState()) {
			PutLargeMailThreadPool.putPool(context);
		}
	}
	
}
