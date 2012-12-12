package com.dreamail.mercury.xml.interfaces;

import java.io.UnsupportedEncodingException;

import org.jibx.runtime.JiBXException;

import com.dreamail.mercury.domain.qwert.PushMail;

public interface XMLDocumentDAO {
	/**
	 * Parse Xml Request and Get Objects
	 * 将XML文件转换成PushMail类
	 * @throws JiBXException 
	 * @throws UnsupportedEncodingException 
	 */
	public PushMail getObjectList(String xml);
	/**
	 * Parse Xml Response and Put Objects
	 * 将PushMail转换成XML文件
	 * @throws JiBXException 
	 */
	public String getResponseMessage(PushMail pushMail);
}
