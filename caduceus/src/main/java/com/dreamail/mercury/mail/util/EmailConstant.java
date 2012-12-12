package com.dreamail.mercury.mail.util;

public interface EmailConstant {
	public static final long DISCONNECT_TIME = 15*60*1000;
	
	public static final String ACCOUNT_DISCONNECT = "disconnect";
	
	public static final String ACCOUNT_CONNECT = "connect";
	
	public static interface JMSConstant{
		public static final String JMS_HOTMAIL_NEWMAIL = "newmail";
		
		public static final String JMS_GMAIL_NEWMAIL = "newmail";
	}
	
	public static interface StatusConstant {
		public static final String STATUS_RUN = "run";
		
		public static final String STATUS_RELOAD = "reload";
	}
	
	public static interface TaskFactoryConstant {
		public static final String MCTYPE = "3";
		
		public static final int QUERY_INTEVEL = 5*1000;
	}
	
	public static interface ActiveSycn{
		public static final String REQUEST_SYNC = "Sync";
		public static final String REQUEST_GET_ATTACHMENT = "GetAttachment";
		public static final String COLLECTION_CLASS_NAME = "Email";
		
		public static final String REQUEST_FILTER_TYPE = "5";//只收1月以内的邮件.
		public static final String REQUEST_WINDOW_SIZE = "10";
		public static final String REQUEST_MIME_SUPPORT = "0";
		public static final int REQUEST_MIME_SIZE = 10*1024*1024;
		
		public static final String INIT_SYNCKEY = "0";
		public static final String RESPONSE_RIGHT_STATUS = "1";
	}
}
