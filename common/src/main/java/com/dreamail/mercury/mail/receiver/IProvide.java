package com.dreamail.mercury.mail.receiver;

import java.util.Properties;

import javax.mail.MessagingException;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.TimeoutException;

public interface IProvide {
	
	/**
	 * 收取正常邮件
	 * @param context
	 * @throws MessagingException
	 * @throws TimeoutException
	 */
	public void receiveMail(Context context)throws MessagingException, TimeoutException;
	
	/**
	 * 收取大邮件
	 * @param context
	 * @throws MessagingException
	 * @throws TimeoutException
	 */
	public void receiveLargeMail(Context context) throws MessagingException;
	
	/**
	 * 设置端口等
	 * @param receiveTs
	 * @param port
	 * @return
	 */
	public Properties getProperties(String receiveTs,String port);
	
	/**
	 * 超时判断
	 * 
	 * @throws TimeoutException
	 */
	public boolean isTimeout(Context context) throws TimeoutException;
	
	/**
	 * 判断大邮件
	 * @param msg
	 * @return boolean
	 * @throws NumberFormatException
	 * @throws MessagingException
	 */
	public boolean isLargeMsg(int msgSize) throws NumberFormatException,MessagingException;
	
	/**
	 * 关闭连接
	 * @param context
	 */
	public void closeConnection(Context context);
}
