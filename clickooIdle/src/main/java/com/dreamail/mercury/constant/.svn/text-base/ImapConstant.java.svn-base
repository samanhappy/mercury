package com.dreamail.mercury.constant;

public interface ImapConstant {

	public interface Login {

		public final static String CAPABILITY = "CAPABILITY";

		public final static String LOGIN = "LOGIN";

		public final static String LOGOUT = "LOGOUT";

		public final static String SELECT = "SELECT";

		public final static String IDLE = "IDLE";

		public final static String NOOP = "NOOP";

		public final static String A0_OK_Thats_all_she_wrote = "OK Thats all she wrote";
		
		public final static String DONE = "DONE";
		
	}

	public interface Search {
		public static final String COMMAND_SEARCH = "SEARCH";
		public static final String COMMAND_UID_SEARCH = "UID SEARCH";

		public static final String CONDITION_ALL = "ALL";
		public static final String CONDITION_PART = "PART";
		public static final String CONDITION_BEFORE = "BEFORE";
		public static final String CONDITION_SINCE = "SINCE";
		public static final String CONDITION_ON = "ON";
		public static final String CONDITION_SUBJECT = "SUBJECT";
		public static final String CONDITION_FROM = "FROM";
		public static final String CONDITION_TO = "TO";
		public static final String CONDITION_CC = "CC";
		public static final String CONDITION_BCC = "BCC";
		public static final String CONDITION_BODY = "BODY";
		public static final String CONDITION_TEXT = "TEXT";

		public static final String CONTENT_ALL = "ALL";
		public static final String CONTENT_SEPARATOR = ":";
		public static final String CONTENT_BLANK = " ";

		public static final String REPLY_SEARCH = "* SEARCH";
		public static final String REPLY_SEARCH_COMPLETE = " OK SEARCH completed";
		public static final String REPLY_UID_SEARCH_COMPLETE = " OK UID SEARCH completed";

		public static final String FLAGS = "FLAGS";
		public static final String FLAGS_NOT_UNDELETED = "NOT UNDELETED";
	}

	public interface Fetch {

		public static final String FETCH_COMMAND_NAME = "FETCH";

		public static final String FETCH_BODY = "BODY"; // 只返回邮件体文本格式和大小的摘要信息。
		
		public static final String FETCH_BODY_PEEK = "BODY.PEEK";

		public static final String FETCH_ALL = "ALL";// 只返回按照一定格式的邮件摘要，包括邮件标志、RFC822.SIZE、自身的时间和信封信息。
		// IMAP客户机能够将标准邮件解析成这些信息并显示出来。
		public static final String FETCH_FAST = "FAST";// 只返回邮件的一些摘要，包括邮件标志、RFC822.SIZE、和自身的时间。

		public static final String FETCH_BODY_STRUCTURE = "BODYSTRUCTURE";// 是邮件的[MIME-IMB]的体结构。这是服务器通过解析[RFC-2822]头中的[MIME-IMB]各字段和[MIME-IMB]头信息得出来的。
		// 包括的内容有：邮件正文的类型、字符集、编码方式等和各附件的类型、字符集、编码方式、文件名称等等。
		public static final String FETCH_FLAGS = "FLAGS";// 此邮件的标志

		public static final String FETCH_RFC822_SIZE = "RFC822.SIZE";// 邮件的[RFC-2822]大小

		public static final String FETCH_UID = "UID";// 返回邮件的UID号，UID号是唯一标识邮件的一个号码。

		public static final String FETCH_ENVELOPE = "ENVELOPE";

	}

	public interface Other {

	}

	public interface List {
		public static final String LIST_COMMAND_NAME = "LIST";// LIST 命令

		public static final String LIST_HASNOCHILDREN = "HasNoChildren"; // 无子文件夹

		public static final String MAILBOX_INBOX = "INBOX";// 收件箱

		public static final String CONTENT_BLANK = " ";// 空格

		public static final String REPLY_LIST = "* LIST";

		public static final String REPLY_LIST_COMPLETE = "OK LIST completed";// LIST命令成功返回

		public static final String REPLY_LIST_FAIL = "BAD [CLIENTBUG] LIST Command unknown or arguments invalid";// LIST命令失败返回
		
		public static final String LIST_COMMAND_LASTCHAR="{\'*\',\'%\'}";//LIST命令最后一个字符窜 如“*”或 “%”

	}

	public interface Folder {
		public static final String FOLDER_FLAGS_ALL = "FLAGS (\\Answered \\Flagged \\Draft \\Deleted \\Seen)";

		public static final String FOLDER_RECENT = "0 RECENT";

		public static final String FOLDER_READ_ONLY = "READ-ONLY";

		public static final String FOLDER_READ_WRITE = "READ-WRITE";

		public static final String REPLY_FOLDER = "*";

	}
	
	public interface Status{
		public static final String STATUS_UIDVALIDITY = "UIDVALIDITY";
		public static final String STATUS_UNSEEN = "UNSEEN";
		public static final String STATUS_MESSAGES = "MESSAGES" ;
		public static final String STATUS_UIDNEXT = "UIDNEXT" ;
		
		public static final String CONTENT_BLANK = " ";
		
		public static final String REPLY_STATUS = "* STATUS \"INBOX\" (";
		public static final String REPLY_COMPLETED = " OK STATUS completed";
	}
}
