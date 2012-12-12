package com.dreamail.jms;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.task.SendTimerMsgTask;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.sun.messaging.Queue;
import com.sun.messaging.Topic;

public class JmsSender {
	private static final Logger logger = LoggerFactory
			.getLogger(JmsSender.class);
	public static Map<String, Object> failureMsg = new HashMap<String, Object>();
	private static Timer queueSenderTimer = null;

	private static ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

	/**
	 * 从pool中获取可用session
	 * 
	 * @return SqlSession
	 * @throws Exception
	 * @throws SQLException
	 */
	public static Session getSession(String target) throws Exception {
		/**
		 * 此处应加入超时判断，防止有连接未关闭，再次获得该链接时，超时错误的发生
		 */
		Session session = (Session) threadLocal.get();
		if (session == null) {
			logger.error("create session");
			Connection connection = ConnectionManager.getConnection(target);
			session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			threadLocal.set(session);
		}
		return session;
	}

	/**
	 * 将session还回pool
	 * 
	 * @throws JMSException
	 * @throws SQLException
	 */
	public static void closeSession() throws JMSException {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);
		if (session != null) {
			session.close();
		}
	}

	public static void sendFailQueue() {
		queueSenderTimer = new Timer();
		queueSenderTimer.schedule(new SendTimerMsgTask(), 1000 * 60,
				1000 * 2 * Integer.parseInt(PropertiesDeploy.getConfigureMap()
						.get("fail.timer")));
		logger.info("QueueSenderTimer start work");
	}

	public static MessageProducer getProducer(String target, Object obj,
			String type, Session session) throws JMSException {
		MessageProducer messageProducer = session.createProducer(build(type,
				target));
		messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT); // 持久订阅
		return messageProducer;
	}

	/**
	 * JMS Sender oo
	 * 
	 * @param target
	 *            JMS 目标地址
	 * @param obj
	 *            JMS 传递对象
	 * @param type
	 *            JMS 发送类型
	 */
	public static void sent(String target, Object obj, String type)
			throws Exception {
		Session session = null;
		try {
			Connection connection = ConnectionManager.getConnection(target);
			session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = session.createProducer(build(
					type, target));
			messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
			// 持久订阅
			ObjectMessage objectMessage = session
					.createObjectMessage((Serializable) obj);
			messageProducer.send(objectMessage);
		} catch (Exception e) {
			logger.error("[JmsSender] jms session exception", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 生成JMS类型
	 * 
	 * @param type
	 *            类型
	 * @param target
	 *            目标地址
	 */
	private static Destination build(String type, String target)
			throws JMSException {
		if ("topic".equalsIgnoreCase(type)) {
			return new Topic(target);
		} else {
			return new Queue(target);
		}
	}

	/**
	 * JMS Topic Sender
	 * 
	 * @param obj
	 *            发送地响
	 * @param target
	 *            发送地址
	 */
	public static void sendTopicMsg(Object obj, String target) {
		try {
			sent(target, obj, "topic");
			logger.info("send topic message:" + obj + " to " + target);
		} catch (Exception e) {
			failureMsg.put(target + "-" + new Random().nextInt(999999)
					+ "-topic", obj);
			logger.error("sendTopicMsg error.", e);
		}
	}

	/**
	 * JMS Queue Sender
	 * 
	 * @param obj
	 *            发送地响
	 * @param target
	 *            发送地址
	 */
	public static void sendQueueMsg(Object obj, String target) {
		try {
			sent(target, obj, "queue");
			logger.info("send queue message:" + obj + " to " + target);
		} catch (Exception e) {
			failureMsg.put(target + "-" + new Random().nextInt(999999)
					+ "-queue", obj);
			logger.error("sendTopicMsg error.", e);
		}
	}
	
	public static void main(String[] args) {
		new PropertiesDeploy().init();
		JmsSender.sendQueueMsg("hello", "samanQueue");
		JmsSender.sendQueueMsg("hello", "testQueue");
		JmsSender.sendQueueMsg("hello", "gmailReceiveQueue");
	}

}
