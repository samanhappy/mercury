package com.dreamail.jms;

public interface IJmsSender {
	/**
	 * 发送消息，可以传多个参数在方法中封装消息 ，实现类要求以destination+JmsSender命名.
	 * @param business 业务名称
	 * @param destination 消息名称
	 * @param str 多个参数，根据需求传入
	 */
	void sendMessage(String business,String destination,String... str);
}
