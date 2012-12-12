package com.dreamail.mercury.timerpush.jms;

import javax.jms.Connection;
import javax.jms.JMSException;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;

public class MqConnectionFactory implements javax.jms.ConnectionFactory{
  
  private ConnectionFactory connectionFactory;

  public MqConnectionFactory(String brokerAddress) throws JMSException {
      connectionFactory = new ConnectionFactory();
      connectionFactory.setProperty(ConnectionConfiguration.imqAddressList, brokerAddress);
  }

  public Connection createConnection() throws JMSException {
      return connectionFactory.createConnection();
  }

  public Connection createConnection(String userName, String password) throws JMSException {
      return connectionFactory.createConnection(userName, password);
  }
  
}