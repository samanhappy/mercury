package com.dreamail.mercury.yahooSNP.snp;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;
import com.sun.messaging.Queue;
import com.sun.messaging.Topic;

public class JmsSender {
	private static ConcurrentHashMap<String, ConnectionFactory> hashMap = new ConcurrentHashMap<String, ConnectionFactory>();
	public static final Logger logger = LoggerFactory
			.getLogger(JmsSender.class);

	private void sent(String target, Object obj, String type, String url)
			throws Exception {
		if (!hashMap.containsKey(target)) {
			initConnectionFactory(target, url);
		}
		ConnectionFactory connectionFactory = hashMap.get(target);
		if (connectionFactory == null) {
			initConnectionFactory(target, url);
			connectionFactory = hashMap.get(target);
			if (connectionFactory == null) {
				return;
			}
		}
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		MessageProducer messageProducer = session.createProducer(build(type,
				target));
		messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT); // 持久订阅
		connection.start();
		ObjectMessage objectMessage = session
				.createObjectMessage((Serializable) obj);
		logger.info("send object.");
		messageProducer.send(objectMessage);
		logger.info("send complete.");
		session.close();
		connection.close();
	}

	public void sendTopicMsg(Object obj, String target, String url) {
		try {
			sent(target, obj, "topic", url);
		} catch (Exception e) {
			logger.error("sendTopicMsg error.", e);
		}
	}

	public void sendQueueMsg(Object obj, String target, String url) {
		try {
			sent(target, obj, "Queue", url);
		} catch (Exception e) {
			logger.error("sendTopicMsg error.", e);
		}
	}

	private void initConnectionFactory(String target, String url)
			throws JMSException {
		String targetUrl = url;
		// "mq://192.168.20.35:7676";
		// PropertiesDeploy.getConfigureMap().get(target+".url");
		logger.info("target.url" + target);
		logger.info("configuremap contains target:"
				+ PropertiesDeploy.getConfigureMap().keySet().contains(
						target + ".url"));
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setProperty(ConnectionConfiguration.imqAddressList,
				targetUrl);
		connectionFactory.setProperty(
				ConnectionConfiguration.imqReconnectEnabled, "true");
		hashMap.put(target, connectionFactory);
	}

	private Destination build(String type, String target) throws JMSException {
		if ("topic".equalsIgnoreCase(type)) {
			return new Topic(target);
		} else {
			return new Queue(target);
		}
	}

}
