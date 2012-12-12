package com.dreamail.mercury.util;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;
import com.sun.messaging.Topic;

public class SyncData {
	private static final String ACTION_PROPERTY = "action";
	private static final String CACHE_NAME_PROPERTY = "cacheName";
	private static final String KEY_PROPERTY = "key";
	private static final String MIME_TYPE_PROPERTY = "mimeType";
	private static final String EhcacheTopicDest = "EhcacheTopicDest";
	private static SyncData syncData = new SyncData();
	
	private static ConcurrentHashMap<String, ConnectionFactory> hashMap = 
			new ConcurrentHashMap<String, ConnectionFactory>();
	private SyncData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static final Logger logger = LoggerFactory
			.getLogger(SyncData.class);

	public void cacheOperator(String key,Object obj,String targetUrl,String cacheObject,String operator) throws Exception {
		if (!hashMap.containsKey(EhcacheTopicDest)) {
			initConnectionFactory(EhcacheTopicDest,targetUrl);
		}
		ConnectionFactory connectionFactory = hashMap.get(EhcacheTopicDest);
		if (connectionFactory == null) {
			initConnectionFactory(EhcacheTopicDest,targetUrl);
			connectionFactory = hashMap.get(EhcacheTopicDest);
		}
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		MessageProducer messageProducer = session.createProducer(new Topic(EhcacheTopicDest));
		
		ObjectMessage objectMessage = session.createObjectMessage((Serializable)obj);
		objectMessage.setStringProperty(ACTION_PROPERTY, operator);
		objectMessage.setStringProperty(CACHE_NAME_PROPERTY, cacheObject);
		objectMessage.setStringProperty(MIME_TYPE_PROPERTY, "application/octet-stream");
		objectMessage.setStringProperty(KEY_PROPERTY, key);
		messageProducer.send(objectMessage);
		logger.info("---------send sucess!!!---------------");
		session.close();
		connection.close();
	}

	private void initConnectionFactory(String target,String targetUrl) throws JMSException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setProperty(ConnectionConfiguration.imqAddressList,targetUrl);
		connectionFactory.setProperty(ConnectionConfiguration.imqReconnectEnabled, "true");
		hashMap.put(target, connectionFactory);
	}
	
	public static SyncData getInstance(){
		return syncData;
	}

	public static void main(String[] args) throws Exception {
//		SyncData sender = new SyncData();
//		AccountCacheObject account = new AccountCacheObject();
//		account.setName("wanghysen");
//		account.setStatus(2);
//		Object object = new Object();
//		object = account;
//		sender.cacheOperator("1",object,"mq://192.168.20.201:7676",Constant.ACCOUNTLIST,Constant.OPERATOR_PUT);
	}
}
