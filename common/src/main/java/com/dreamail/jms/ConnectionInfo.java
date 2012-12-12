package com.dreamail.jms;

import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;

public class ConnectionInfo implements ExceptionListener {

	public static final Logger log = LoggerFactory
			.getLogger(ConnectionInfo.class);
	private boolean flag = false;
	private ConnectionFactory connectionFactory;
	private Connection connection;

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public boolean getFlag() {
		return flag;
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public Connection getConnection() {
		return connection;
	}

	public void reconnect() throws JMSException {
		connection = connectionFactory.createConnection();
		connection.setExceptionListener(this);
		flag = true;
	}

	public void initConnection(String[] targ, int count) throws JMSException {
		connectionFactory = new ConnectionFactory();
		String strTarget = "";
		String current = "";
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < targ.length; j++) {
			if (count == j){
				current = targ[j];
			}else{
				sb.append(",");
				sb.append(targ[j]);
			}
		}
		strTarget = current + sb.toString();
		System.out.println(strTarget);
		connectionFactory.setProperty(ConnectionConfiguration.imqAddressList,
				strTarget);
		connectionFactory.setProperty(
				ConnectionConfiguration.imqReconnectEnabled, "true");
		connectionFactory.setProperty(
				ConnectionConfiguration.imqReconnectInterval, "1000");
		connection = connectionFactory.createConnection();
		connection.setExceptionListener(this);
		flag = true;
	}

	public void onException(JMSException e) {
		this.flag = false;
		log.error("[ConnectionInfo] [JMSException]", e);
	}

}
