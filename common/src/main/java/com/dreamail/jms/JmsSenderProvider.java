package com.dreamail.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsSenderProvider {
	public static final Logger logger = LoggerFactory.getLogger(JmsSenderProvider.class);
	private static JmsSenderProvider provider = new JmsSenderProvider();
	
	private JmsSenderProvider(){}
	
	public static JmsSenderProvider getInstance(){
		if(provider==null){
			provider = new JmsSenderProvider();
		}
		return provider;
	}
	/**
	 * 获取发送消息实现类的名称.
	 * @param business
	 * @return 
	 */
	private String getClassName(String destination){
		StringBuffer sb = new StringBuffer("com.dreamail.jms.impl.");
		sb.append(destination.substring(0, 1).toUpperCase() + destination.substring(1));
		sb.append("JmsSender");
		return sb.toString();
	}
	
	/**
	 * 根据业务名称、消息名称、自定义参数发送消息.
	 * @param business
	 * @param destination
	 * @param content
	 * @throws Exception
	 */
	public void sendMessage(String business,String destination,String... content) throws Exception{
		IJmsSender sender = (IJmsSender) Class.forName(getClassName(destination)).newInstance();
		sender.sendMessage(business,destination, content);
	}
	
}
