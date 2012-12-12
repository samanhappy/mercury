package com.dreamail.mercury.util;

public class Constant {
	public final static String SENDBOX_FORWORD = "3";

	public final static String INBOX_FORWORD = "2";

	public final static String REPLAY_MAIL = "1";

	public final static String COMMON_MAIL = "0";

	public final static String GET_DOWNLOAD_MAIL = "2";

	public final static String GET_NEW_MAIL = "0";

	public final static String GET_OLD_MAIL = "1";

	public final static String PUSH_TIME_ON = "1";

	public final static String PUSH_TIME_OFF = "0";

	public final static String PUSH_ON = "1";

	public final static String PUSH_OFF = "0";

	public final static String POP3 = "pop";

	public final static String IMAP = "imap";

	public final static String HTTP = "http";

	public final static String LOGINID = "loginID";

	public final static String PWD = "pwd";

	public final static String TOKEN = "token";

	public final static String PTID = "ptid";

	public final static String HOST = "host";

	public final static String SSL = "ssl";

	public final static String SSL_VALUE = "SSL";

	public final static String TLS_VALUE = "TLS";

	public final static String TYPE = "type";

	public final static String SSL_TYPE = "1";

	public final static String PROTOCOLTYPE = "protocoltype";

	public final static String COMPOSITOR = "compositor";

	public final static String SUPPORTALLUID = "supportalluid";

	public final static String SEARCHSIZE = "searchsize";

	public final static String RECEIVEPORT = "receivePort";

	public final static String INTERVAL_FOR_TIMER = "intervalForTimer";

	public final static String INTERVAL_FOR_RETRY = "intervalForRetry";

	public final static String KEEP_ALIVE_TIME = "keepAliveTime";

	public final static String CREATE_SIZE = "createSize";

	public final static String MIN_POOL_SIZE = "minPoolSize";

	public final static String MAX_POOL_SIZE = "maxPoolSize";

	public final static String KEEP_BA_ALIVE_TIME = "keepBigAttachAliveTime";

	public final static String CREATE_BA_SIZE = "createBigAttachSize";

	public final static String MIN_BA_POOL_SIZE = "minBigAttachPoolSize";

	public final static String MAX_BA_POOL_SIZE = "maxBigAttachPoolSize";
	
	//UPE JMS线程池
	public final static String UPE_KEEP_ALIVE_TIME = "upeKeepAliveTime";

	public final static String UPE_CREATE_SIZE = "upeCreateSize";

	public final static String UPE_MIN_POOL_SIZE = "upeMinPoolSize";

	public final static String UPE_MAX_POOL_SIZE = "upeMaxPoolSize";

	public final static String DELETE = "Delete";

	public final static String ALIAS = "alias";

	public final static String SMTPSERVER = "smtpServer";

	public final static String SENDPORT = "sendPort";

	public final static String SMTP = "smtp";

	/******* JMS接受消息类型 *********/

	public final static String FINDMASTER = "findMaster";

	public final static String MASTERNOTICE = "masterNotice";

	public final static String NOTICEACCOUNT = "noticeAccount";

	public final static String STOPLOOP = "stopLoop";

	public final static String CACHEACCOUNT = "cacheAccount";

	public final static String CONTROLLERNOTICE = "controllerNotice";

	public final static String NOTICELOOP = "noticeLoop";

	/******** 服务器类型 *********/

	public final static String SERVER_MASTER = "master";

	public final static String SERVER_SECOND = "second";

	public final static String SERVER_COMMON = "common";

	/******** attachment json对象相关key *********/
	public final static String ATTACHMENT_ID = "id";

	public final static String ATTACHMENT_NAME = "name";

	public final static String ATTACHMENT_TYPE = "type";

	public final static String ATTACHMENT_LENGTH = "length";

	public final static String ATTACHMENT_PATH = "path";

	public final static String ATTACHMENT_VOLUME_ID = "volume_id";

	public final static String ATTACHMENT_PARENT = "parent";

	public final static String ATTACHMENT_MID = "mid";

	/*************** JMS String Object *****************/
	public final static String CLICKOO_MAIL_SERVER = "Clickoo_mail_server:";

	public final static String INTERVAL_TIMER = "IntervalTimer,";

	public final static String CLICKOO_MAIL_RECEIVE_TIMER = "Clickoo_mail_receive_timer,";

	public final static String CLICKOO_VOLUME = "clickoo_volume";

	public final static String ROLE_CHANGE = "RoleChange:";

	public final static String ROLE_FUNCATION = "RoleFuncation,";

	public final static String RETRIEVAL_EMAIL_INTERVAL = "retrievalEmailInterval";

	public final static String USER_ONLINE = "Online:";

	public final static String USER_OFFLINE = "Offline:";

	public final static String ACCOUNT_ONTIMER = "Ontimer:";

	public final static String ACCOUNT_OFFTIMER = "Offtimer:";

	public final static String NOTICE = "Notice:";

	public final static String TASK_MESSAGE = "TaskMessage:";

	public final static String CACHE_ACCOUNT_MESSAGE = "CacheAccountMessage:";

	public final static String SEND_MAILS = "SendMail:";

	public final static String ACCOUNT_ADD = "insert";

	public final static String ACCOUNT_REMOVE = "delete";

	/******** 错误邮件信息 *********/
	public final static String GET_MAIL_FAIL = "doload mail fail.";

	public final static String PARSE_MAIL_FAIL = "parse mail fail.";

	public final static String ACCOUNT_CONNECT_ERROR = "account connect error.";

	public final static String WRONG_STATUS = "status is 3";

	/******** 下载邮件相关 *********/
	public final static String DOWNLOAD_EMAIL = "download_email";

	public final static String DOWNLOAD_EMAIL_NODEAL = "download_email_nodeal";

	public final static String DOWNLOAD_BODY = "download_body";

	public final static String DOWNLOAD_ATT = "download_att";

	public final static String DOWNLOAD_ALL_ATT = "download_all_att";

	public final static String DOWNLOAD_ALL_ATT_NODEAL = "download_all_att_nodeal";

	/******** 发送邮件相关 *********/
	public final static String KEY = "key";

	/******** 附件大小限制 *********/
	public final static String ATTACHMENT_SIZE_LIMIT = "attachment_size_limit";

	/******** 账号状态定义 *********/
	/* 此处如果更改，需要同时更改数据操作和存储过程中的sql语句 */
	public final static int ACCOUNT_COMMON_STATE = 0;

	public final static int ACCOUNT_NONRECOGNITION_STATE = 1;

	public final static int ACCOUNT_NEWREGISTERED_STATE = 2;
	/******** 特殊邮件服务器名称 *********/
	public final static String YAHOO_HOST_NAME = "yahoo";

	public final static String GMAIL_HOST_NAME = "gmail";

	public final static String HOTMAIL_HOST_NAME = "hotmail";

	public final static String OTHERS_HOST_NAME = "others";
	/******** 账号处理类型定义 ***************/
	public final static int ACCOUNT_COMMON_TYPE = 0;

	/****
	 * 改变SNP和IDLE类型需要更改AccountMapper的getAllValidAccountSize,
	 * getAllAccountToCache查询
	 *****/
	public final static int ACCOUNT_YAHOOSNP_TYPE = 1;

	public final static int ACCOUNT_IMAPIDLE_TYPE = 2;

	public final static int ACCOUNT_GMAIL_TYPE = 3;

	public final static int ACCOUNT_HOTMAIL_TYPE = 4;

	public final static String USE_SSL = "1";

	public final static String NOT_USE_SSL = "0";

	/******** 角色相关参数 ***************/
	public final static String ROLE_AUTOCLEANUPPERIOD_NEVER = "NEVER";

	public final static int ROLE_AUTOCLEANUPPERIOD_NEVER_DAY = -1;

	public final static int Role_IMAP_DEFAULT_DAY = 30;

	public final static String DISABLE = "Disable";
	/******** 邮件头信息相关 ***************/

	public final static String MESSAGE_SIZE = "size";

	/******** 邮件发送状态参数 ***************/
	public final static int MAIL_SENT_COMMON_STATUS = 0;

	public final static int MAIL_SENT_SUCCEESS_STATUS = 1;

	public final static int MAIL_SENT_FAIL_STATUS = 2;

	// 所以处理已完成状态，可以删除
	public final static int MAIL_SENT_DONE_STATUS = 3;

	/******** 账号状态参数 ***************/
	public final static String MAP_KEY_STATUS = "status";

	public final static String MAP_KEY_UIDS = "uids";

	public final static String MAP_KEY_UID = "uid";

	public final static String MAP_KEY_AIDS = "aids";

	/******** 签名、回复、转发分割线（长度35） ***************/
	public final static String SPILIT_LINE = "\n-----------------------------------\n";

	public final static String SPILIT_NEXT_LINT = "\r\n";

	/******** imap 文件夹开关 ***************/
	public static final boolean OPEN_FOLDER = false;

	/********************** 系统退信 **************************/

	public final static String SYSTEM_ADMIN = "system_admin";

	public final static String SYSTEM_NAME = "name";

	public final static String SYSTEM_PASSWORD = "password";

	public final static String SYSTEM_SENDHOST = "sendHost";

	public final static String SYSTEM_SENDPORT = "sendPort";

	public final static String SYSTEM_SENDTS = "sendTs";

	public final static String SYSTEM_SUBJECT = "subject";

	public final static String SYSTEM_CONTENT = "content";

	/********************** 任务工厂标识 **************************/
	public final static String TASK_FACTORY_KEY = "MasterList";

	public final static String TASK_FACTORY_DELETE_FLAG = "1";

	public final static String TASK_FACTORY_FLAG = "0";

	public final static String TASK_FACTORY_DEAD_TIME = "dead_time";

	public final static String TASK_FACTORY_LISTENER_TIME = "listener_time";

	/***************** 收邮件相关账号及关系表状态 ********************/
	public final static int NEW_REGISTER_ACCOUNT_STATUS = 1;

	public final static int PUSH_OLDMAIL_ACCOUNT_STATUS = 1;

	public final static int PUSH_NEWMAIL_ACCOUNT_STATUS = 0;

	/********************** 邮件相关状态 **************************/
	public final static int MESSAGE_COMMON_STATUS = 0;
	
	public final static int MESSAGE_DELETE_STATUS = 1;

	public final static int MESSAGE_READ_STATUS = 2;
	
	/********************** 用户角色title **************************/
	public final static String USER_ROLE_FREE = "Free";
	
	/********************** 用户角色function **************************/
	public final static String ACCOUNT_NUMBER = "accountNumber";
	
	/********************** 黑白名单 **************************/
	public final static String NO_SENDER_FILTER = "0";
	
	public final static String BLACK_SENDER_FILTER = "1";

	public final static String WHITE_SENDER_FILTER = "2";
	
	public final static String SET_BLACK_SENDER_FILTER = "1";
	
	public final static String SET_WHITE_SENDER_FILTER = "2";
	
	public final static String CANCEL_SET_SENDER_FILTER = "3";
	
	public final static String NOT_SET_SENDER_FILTER = "0";
	
	public final static String ADD_SENDER_FILTER = "1";
	
	public final static String REMOVE_SENDER_FILTER = "2";
	
	/********************** 角色retrievalEmailInterval **************************/
	public final static String RETRIVAL_EMAIL_INTERVAL_LOW = "LOW";
	
	public final static String RETRIVAL_EMAIL_INTERVAL_MED = "MED";
	
	public final static String RETRIVAL_EMAIL_INTERVAL_ASAP = "ASAP";
	
	public final static String INTERVAL_ADD_KEY = "addInterval";
	
	public final static String ROLE_INTERVAL_KEY = "retrievalEmailInterval";
}
