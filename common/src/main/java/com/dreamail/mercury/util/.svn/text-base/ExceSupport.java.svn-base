package com.dreamail.mercury.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceSupport {
	public static void execHandle(String accountName,Exception e,Class<?> obj) {
		Logger logger = LoggerFactory.getLogger(obj);
		logger.error("account:["+accountName+"]", e);
	}
	
	public static void execHandle(String accountName,Exception e,Class<?> obj,String msgId) {
		Logger logger = LoggerFactory.getLogger(obj);
		logger.error("account:["+accountName+"]messageID:["+msgId+"]", e);
	}
	
	public static String excHandle(Exception e){
		String exceptionName = e.getClass().getSimpleName();
		String message = e.getMessage();
		String exceptionMessage = exceptionName+message;
		StringBuffer s = new StringBuffer();
		s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(
		"<PushMail>").append("<Header>").append(
		"<Version>PushMail1.0</Version>").append("<Status>").append("<Code>")
		.append(ErrorCode.CODE_SYSTEM_).append("</Code>").append("<Message>")
		.append(exceptionMessage).append("</Message>").append("</Status>")
		.append("</Header>").append("</PushMail>");
		return s.toString();	
	}
}
