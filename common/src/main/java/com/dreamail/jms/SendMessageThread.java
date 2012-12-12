package com.dreamail.jms;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JMSTypesUtil;

public class SendMessageThread extends Thread {

	public static Logger logger = LoggerFactory.getLogger(SendMessageThread.class);

	private String typeStr;
	private Object messages;

	public SendMessageThread(String typeStr, Object messages) {
		this.typeStr = typeStr;
		this.messages = messages;
	}

	@Override
	public void run() {
		// System.out.println("start:" + System.currentTimeMillis());
		int type = Integer.parseInt(typeStr);
		logger.info("SendMessageThread jms type:"+type);
		String target = JMSTypesUtil.getTargetByJmsType(type);
		// System.out.println("end:" + System.currentTimeMillis());
		sendTask(target,messages);
		if(Constant.ACCOUNT_HOTMAIL_TYPE == type){
			sendTask(JMSTypesUtil.getHotmailPingTarget(),messages);
		}
	}
	
	public static void sendTask(String target,Object messages) {
		if (target != null) {
			logger.info("target != null...................");
			Connection connection = null;
			Session session = null;
			MessageProducer messageProducer = null;
			try {
				connection = ConnectionManager.getConnection(target);
				session = connection.createSession(false,
						Session.AUTO_ACKNOWLEDGE);
				messageProducer = session
						.createProducer(new com.sun.messaging.Queue(target));
				messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

				if (messages instanceof String) {
					logger.info("target != null.....first.............." + messages);
					// 持久订阅
					ObjectMessage objectMessage = session
							.createObjectMessage((Serializable) messages);
					messageProducer.send(objectMessage);
				} else if (messages instanceof JSONObject) {
					logger.info("target != null......second............." + messages);
					JSONObject json = (JSONObject) messages;
					// 持久订阅
					ObjectMessage objectMessage = session
							.createObjectMessage((Serializable) json.toString());
					messageProducer.send(objectMessage);
				} else {
					logger.info("target != null...........JSONArray");
					JSONArray array = (JSONArray) messages;
					logger.info("[array size ]"+array.size());
					for (int i = 0; i < array.size(); i++) {
						// 持久订阅
						ObjectMessage objectMessage = session
								.createObjectMessage((Serializable) array
										.get(i).toString());
						messageProducer.send(objectMessage);
						logger.info("one jms send success "+target);
					}
				}
				logger.info("send jms messges:" + messages.toString() + " to " + target);
			} catch (Exception e) {
				logger.error("[JmsSender] jms session exception", e);
			} finally {
				try {
					if (messageProducer != null) {
						messageProducer.close();
					}
					if (session != null) {
						session.close();
					}
				} catch (JMSException e) {
					logger.error("error", e);
				}
			}
		}
	}
}
