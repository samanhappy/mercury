package com.dreamail.mercury.receiver.mail.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cache.MsgCacheManager;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.mail.receiver.AbstractPop3Provide;
import com.dreamail.mercury.mail.receiver.IProvide;
import com.dreamail.mercury.mail.receiver.attachment.impl.AttachmentFormatControl;
import com.dreamail.mercury.proces.SaveDataProces;
import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserImpl;
import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserProvide;
import com.dreamail.mercury.util.Constant;
import com.sun.mail.pop3.POP3Folder;

public class Pop3ProviderImpl extends AbstractPop3Provide implements IProvide {
	public static final int NUMFORINBOX = -1;
	public static final Logger logger = LoggerFactory
			.getLogger(Pop3ProviderImpl.class);

	@Override
	public void receiveMail(Context context) throws MessagingException {
//   若账号状态为0且有序，现在用的缓存中的注册日期，需要每次重新查询最新邮件的日期与注册
//      日期中的最大值.
		if(context.getRegistrationDate()==null){
			logger.info("account:"+context.getLoginName()+" registerDate is null, return.....");
			return;
		}
		init(context.getAccountId(), context.getLoginName());
		// 判断是否gmail账号,如是则添加recent:前缀
		String isGmailBox = "0";
		if (context.getLoginName().indexOf("@") != -1
				&& "gmail.com".equalsIgnoreCase(context.getLoginName().split(
						"@")[1])) {
			user = "recent:" + context.getLoginName().trim();
			context.setLoginName(user);
			isGmailBox = "1";
		}
		POP3Folder inbox = null;
		// 账号连接失败处理.
		
		Map<String, Message> largeMsgList = new HashMap<String, Message>();
		EmailParserProvide emailParser = new EmailParserProvide();
		Map<String, Email> emailList = new HashMap<String, Email>();

		String compositor = context.getCompositor();
		String supportAllUid = context.getSupportAllUid();
		String accountName = context.getLoginName();
		receiveMailControl(context,inbox,emailParser,emailList,
				largeMsgList,compositor,supportAllUid,isGmailBox,accountName);
		context.setLargeMessageList(largeMsgList);
		context.setEmailList(emailList);
		closeConnection(context);
	}
	
	protected void receiveMailControl(Context context,POP3Folder inbox,EmailParserProvide emailParser,Map<String, Email> emailList,
			Map<String, Message> largeMsgList,String compositor,String supportAllUid,String isGmailBox,String accountName) throws MessagingException{
		// 老邮件收取控制.
		try {
			inbox = getFolder(context);
		} catch (Exception e) {
			inbox = getFolder(context);
		}
//		context.setConnectStatus(EmailConstant.ACCOUNT_CONNECT);
		Message[] msgs = inbox.getMessages();
				if (String.valueOf(NEW_ACCOUNT_STATUS).equals(context.getStatus())) {
					AccountService accountService = new AccountService();
					logger.info("[new user:" + user+ "] receive old message begin,registerDate is "+ context.getRegistrationDate() +
							" oldMailNum is "+ context.getAccount().getOldMailNum() + " autoCleanupPeriod is "+ context.getAccount().getAutoCleanupPeriod());
					logger.info("oldMailNum is " + context.getAccount().getOldMailNum());
					if (receiveOldMessage(context, inbox, emailList, largeMsgList,
							emailParser, RECEIVE_OLD_MAIL_NUM, context.getAccount()
									.getAutoCleanupPeriod())) {
						logger.info("set status 0.");
						context.setStatus(String.valueOf(COMMON_ACCOUNT_STATUS));
						context.getAccount().setStatus(COMMON_ACCOUNT_STATUS);
						// 老邮件收完后将账号状态更改为0;
						accountService.updateAccountState(context.getAccount(), context
								.getRegistrationDate());
					}
					return;
				}
				// 新邮件收取控制.
				logger.info("[user:" + user
						+ "] receive message begin,registerDate is "
						+ context.getRegistrationDate());
				if ("0".equals(compositor) || "1".equals(compositor)) {
					if(context.getAccount().getRecentMessageDate()!=null && context.getAccount().getRecentMessageDate().after(context.getAccount().getRegistrationDate())){
						context.setRegistrationDate(context.getAccount().getRecentMessageDate());
					}
					logger.info("[" + context.getLoginName() + "]"
							+ "receive mail by comositor recentMessageDate is "+context.getRegistrationDate());
					receiveMailByCompositor(context, inbox, msgs, isGmailBox,
							emailList, largeMsgList, emailParser, accountName);
				} else if ("0".equals(supportAllUid)) {
					logger.info("[" + context.getLoginName() + "]"
							+ "receive mail by alluid");
					receiveMailByAllUid(context, isGmailBox, emailList, largeMsgList,
							emailParser, accountName);
				} else {
					logger.info("[" + context.getLoginName() + "]"
							+ "receive mail no special");
					receiveMail(context, msgs, isGmailBox, emailList, largeMsgList,
							emailParser, 0, msgs.length, accountName);
				}
	}
	

	/**
	 * 收取pop大邮件.
	 * 
	 * @param context
	 */
	@Override
	public void receiveLargeMail(Context context) throws MessagingException {
		if (context.getLargeMessageList() != null
				&& context.getLargeMessageList().size() > 0) {
			String accountName = context.getLoginName();
			Map<String, Message> msgs = context.getLargeMessageList();
			EmailParserProvide emailParser = new EmailParserProvide();
			POP3Folder inbox = (POP3Folder) context.getFolder();
			logger.info("large message begin..."+inbox);
			for (String mid : msgs.keySet()) {
				Message msg = msgs.get(mid);
				logger.info("large message size..."+msg.getSize());
				Map<String, Email> emailList = new HashMap<String, Email>();
				String uuid = null;
				try {
					try {
						uuid = inbox.getUID(msg);
					} catch (MessagingException e) {
						reHandle(inbox);
						uuid = inbox.getUID(msg);
					} catch (IllegalStateException e) {
						reHandle(inbox);
						uuid = inbox.getUID(msg);
					}
					Email mail = emailParser.getEmail(accountName, msg, uuid);

					if (mail != null) {
						emailList.put(mid, mail);
						context.setEmailList(emailList);
						new AttachmentFormatControl().doProces(context);
						new SaveDataProces().doProces(context);
						// 大邮件收取解析时间较长 每完成一个大邮件的收取 发消息通知用户.
						sendMessage(context);
					}
				} catch (Exception e) {
					// 若收取异常,从缓存中移除出错的uuid,等待下次收取.
					if (containsUuid(uuid, context.getAccountId())) {
						new MessageDao().deleteMessageById(mid);
						removeUidCache(uuid, context.getAccountId());
					}
					logger.error("account[" + context.getLoginName()
							+ "]MessageID[" + msg.getMessageNumber() + "]", e);
				}
			}
			context.setEmailList(null);
			context.setLargeMessageList(null);
		}
		closeConnection(context);
	}

	/**
	 * 以下仅供单元测试使用
	 * 
	 * @param args
	 * @throws Exception
	 */

	public static void main(String args[]) throws Exception {
		System.out.println(new Pop3ProviderImpl().isOneMonthMessage(new Date()));

	}

	/**
	 * 收取单封邮件.
	 * 
	 * @param context
	 *            上下文对象
	 * @param uuid
	 *            邮件唯一标识
	 * @param msg
	 *            message对象
	 * @param num
	 *            邮件在收件箱中的位置
	 * @param isGmailBox
	 *            是否gmail邮箱
	 * @param emailList
	 *            普通邮件集合
	 * @param largeMsgList
	 *            大邮件集合
	 * @param emailParser
	 *            邮件解析对象
	 */
	protected void receiveMailByOne(Context context, String uuid, Message msg,
			int num, String isGmailBox, Map<String, Email> emailList,
			Map<String, Message> largeMsgList, EmailParserProvide emailParser,
			String accountName) {
		String mid = null;
		try {
			if (uuid == null || uuid.equals("")) {
				return;
			} else if (containsUuid(uuid, context.getAccountId())
					|| ("1".equals(isGmailBox) && containsUuid("send:" + uuid,
							context.getAccountId()))) {
				return;
			} else {
				if (num != NUMFORINBOX) {
					try {
						msg = context.getFolder().getMessage(num);
					} catch (IllegalStateException e) {
						reHandle((POP3Folder) context.getFolder());
						msg = context.getFolder().getMessage(num);
					} catch (MessagingException e) {
						reHandle((POP3Folder) context.getFolder());
						msg = context.getFolder().getMessage(num);
					}
				}
				
				// 若是gmail账号 判断是否为发件箱的邮件.
				if ("1".equals(isGmailBox)) {
					boolean isSendMsg = false;
					try {
						isSendMsg = isSendMsg(msg, context.getLoginName(),
								EmailParserImpl.getInstance());
					} catch (IllegalStateException e) {
						reHandle((POP3Folder) context.getFolder());
						isSendMsg = isSendMsg(msg, context.getLoginName(),
								EmailParserImpl.getInstance());
					} catch (MessagingException e) {
						reHandle((POP3Folder) context.getFolder());
						isSendMsg = isSendMsg(msg, context.getLoginName(),
								EmailParserImpl.getInstance());
					}
					if (isSendMsg) {
						addUidCache("send:" + uuid, -1, context.getAccountId());
						return;
					}
				}
				Date mailDate = null;
				int msgSize = 0;
				try {
					mailDate = msg.getSentDate();
				} catch (IllegalStateException e) {
					reHandle((POP3Folder) context.getFolder());
					mailDate = msg.getSentDate();
				} catch (MessagingException e) {
					reHandle((POP3Folder) context.getFolder());
					mailDate = msg.getSentDate();
				}
				try {
					msgSize = msg.getSize();
				} catch (IllegalStateException e) {
					reHandle((POP3Folder) context.getFolder());
					msgSize = msg.getSize();
				} catch (MessagingException e) {
					reHandle((POP3Folder) context.getFolder());
					msgSize = msg.getSize();
				}

				if (mailDate == null
						|| super.isOldMessage(mailDate, context
								.getRegistrationDate())) {
					mid = String.valueOf(new MessageDao().getNextMessageId(String.valueOf(context.getAccountId()),uuid));
					addUidCache(uuid, Integer.parseInt(mid), context
							.getAccountId());
					//黑白名单过滤
					String from = null;
					try {
						from = EmailParserImpl.getInstance().getFrom(msg);
					} catch (MessagingException e) {
						reHandle((POP3Folder) context.getFolder());
						from = EmailParserImpl.getInstance().getFrom(msg);
					} catch (IllegalStateException e) {
						reHandle((POP3Folder) context.getFolder());
						from = EmailParserImpl.getInstance().getFrom(msg);
					}
					if(isFilter(context, from))
						return;
					if (super.isLargeMsg(msgSize)) {
						logger.info("is LargeMsg uuid[" + uuid + "]");
						largeMsgList.put(String.valueOf(mid), msg);
					} else {
						logger.info("new Message uuid[" + uuid + "]");
						Email mail = emailParser.getEmail(accountName, msg,
								uuid);
						emailList.put(String.valueOf(mid), mail);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("account[" + context.getLoginName() + "]" + uuid
					+ " UnsupportedEncodingException", e);
		} catch (Exception e) {
			if (containsUuid(uuid, context.getAccountId())) {
				new MessageDao().deleteMessageById(mid);
				removeUidCache(uuid, context.getAccountId());
			}
			e.printStackTrace();
			logger.error("account[" + context.getLoginName() + "]MessageID["
					+ uuid + "]", e);
		}
	}

	/**
	 * 根据排序算法收取邮件.
	 * 
	 * @param context
	 *            上下文对象
	 * @param inbox
	 *            收件箱
	 * @param msgs
	 *            message数组
	 * @param isGmailBox
	 *            是否gmail邮箱
	 * @param emailList
	 *            普通邮件集合
	 * @param largeMsgList
	 *            大邮件集合
	 * @param emailParser
	 *            邮件解析对象
	 * @throws MessagingException
	 */
	protected void receiveMailByCompositor(Context context, POP3Folder inbox,
			Message[] msgs, String isGmailBox, Map<String, Email> emailList,
			Map<String, Message> largeMsgList, EmailParserProvide emailParser,
			String accountName) throws MessagingException {
		int start = 0;
		int end = msgs.length;
		try {
			if (msgs.length > 0) {
				int[] sign = super.getMarker(context.getCompositor(), context
						.getRegistrationDate(), inbox, msgs, start, end,
						context.getAccountId());
				start = sign[0];
				end = sign[1];
			}
		} catch (Exception e1) {
			logger.error("account: " + context.getLoginName()
					+ " searchSuffix by compositor errot.", e1);
		}
		receiveMail(context, msgs, isGmailBox, emailList, largeMsgList,
				emailParser, start, end, accountName);
	}

	/**
	 * 普通遍历收取邮件.
	 * 
	 * @param context
	 *            上下文对象
	 * @param msgs
	 *            message数组
	 * @param isGmailBox
	 *            是否gmail邮箱
	 * @param emailList
	 *            普通邮件集合
	 * @param largeMsgList
	 *            大邮件集合
	 * @param emailParser
	 *            邮件解析对象
	 * @param start
	 *            循环起始标记
	 * @param end
	 *            循环结束标记
	 * @throws MessagingException
	 */
	protected void receiveMail(Context context, Message[] msgs,
			String isGmailBox, Map<String, Email> emailList,
			Map<String, Message> largeMsgList, EmailParserProvide emailParser,
			int start, int end, String accountName) throws MessagingException {
		for (int i = start; i < end; i++) {
			if (isTimeout(context)) {
				break;
			}
			// 超时跳出循环
			Message msg = msgs[i];
			String uuid = null;
			try {
				uuid = ((POP3Folder) context.getFolder()).getUID(msgs[i]);
			} catch (IllegalStateException e) {
				reHandle((POP3Folder) context.getFolder());
				uuid = ((POP3Folder) context.getFolder()).getUID(msgs[i]);
			} catch (MessagingException e) {
				reHandle((POP3Folder) context.getFolder());
				uuid = ((POP3Folder) context.getFolder()).getUID(msgs[i]);
			}
			receiveMailByOne(context, uuid, msg, NUMFORINBOX, isGmailBox,
					emailList, largeMsgList, emailParser, accountName);
		}

	}

	/**
	 * 根据getalluid收取邮件.
	 * 
	 * @param context
	 *            上下文对象
	 * @param isGmailBox
	 *            是否gmail邮箱
	 * @param emailList
	 *            普通邮件集合
	 * @param largeMsgList
	 *            大邮件集合
	 * @param emailParser
	 *            邮件解析对象
	 * @throws MessagingException
	 */
	protected void receiveMailByAllUid(Context context, String isGmailBox,
			Map<String, Email> emailList, Map<String, Message> largeMsgList,
			EmailParserProvide emailParser, String accountName)
			throws MessagingException {
		TreeMap<String, String> treeMap = null;
		try {
			treeMap = getAllUidMap((POP3Folder) context.getFolder());
		} catch (MessagingException e2) {
			logger.warn("account[" + context.getLoginName()
					+ "]get all uid error.", e2);
		}
		if (treeMap == null) {
			return;
		}
		Set<String> set = treeMap.keySet();
		for (String uuid : set) {
			if (isTimeout(context)) {
				break;
			}
			int num = Integer.parseInt(treeMap.get(uuid));
			receiveMailByOne(context, uuid, null, num, isGmailBox, emailList,
					largeMsgList, emailParser, accountName);
		}
	}

	/**
	 * 收取老邮件.
	 * 
	 * @param context
	 *            上下文对象
	 * @param inbox
	 *            收件箱
	 * @param emailList
	 *            普通邮件集合
	 * @param largeMsgList
	 *            大邮件集合
	 * @param emailParser
	 *            邮件解析对象
	 * @throws MessagingException
	 */
	public boolean receiveOldMessage(Context context, POP3Folder inbox,
			Map<String, Email> emailList, Map<String, Message> largeMsgList,
			EmailParserProvide emailParser, int oldMailNum, int mailDay)
			throws MessagingException {
		boolean receiveOk = false;
		boolean isTimeOut = false;
		Message[] msg = inbox.getMessages();
		System.out.println(msg.length);
		String compositor = context.getCompositor();
		int cacheSize = 0;
		int start = 1;
		int end = msg.length;
		Map<String, String> uuidList = getUidCache(context.getAccountId());
		if (uuidList != null) {
			cacheSize = uuidList.size();

		}
		int num = getReceiveMsgNum(context.getAccountId());
		logger.info("receive cache===================" + num);
		logger.info("cache        ===================" + cacheSize);
		TreeMap<String, String> treeMap = null;
		if ("0".equals(context.getSupportAllUid())) {
			treeMap = getAllUidMapDec(inbox);
		}
		String uuid = null;
		if ("1".equals(compositor)) {
			start = start + cacheSize;
			for (; start < end + 1; start++) {
				if (num >= oldMailNum) {
					break;
				} else if (isTimeout(context)) {
					logger.info("this time receive " + emailList.size()
							+ "old msgs and" + largeMsgList.size()
							+ " large msgs");
					isTimeOut = true;
					break;
				}
				// 此处多发几次新邮件通知
				if (num != 0 && num % 10 == 0) {
					sendMessage(context);
				}
				if (treeMap != null) {
					uuid = treeMap.get(String.valueOf(start));
				}
				if (handleOneMessage(context, inbox, start, uuid, emailList,
						largeMsgList, emailParser, mailDay)) {
					num = num + 1;
				}
			}
			if (!isTimeOut) {
				receiveOk = true;
			}

		} else {
			end = end - cacheSize;
			for (; end > start - 1; end--) {
				if (num >= oldMailNum) {
					break;
				} else if (isTimeout(context)) {
					logger.info("this time receive " + emailList.size()
							+ "old msgs and" + largeMsgList.size()
							+ " large msgs");
					isTimeOut = true;
					break;
				}
				// 此处多发几次新邮件通知
				if (num != 0 && num % 10 == 0) {
					sendMessage(context);
				}
				if (treeMap != null) {
					uuid = treeMap.get(String.valueOf(end));
				}
				if (handleOneMessage(context, inbox, end, uuid, emailList,
						largeMsgList, emailParser, mailDay)) {
					num = num + 1;
				}
			}
			if (!isTimeOut) {
				receiveOk = true;
			}
		}
		sendMessage(context);
		context.setLargeMessageList(largeMsgList);
		context.setEmailList(null);
		closeConnection(context);
//		if(context.getLargeMessageList()==null)
//			ReceiveMain.minusCurrentConnections(context.getServer());
		return receiveOk;
	}

	/**
	 * 老邮件的单封收取.
	 * 
	 * @param context
	 *            上下文对象
	 * @param inbox
	 *            收件箱
	 * @param msgnum
	 *            邮件序号
	 * @param emailList
	 *            普通邮件集合
	 * @param largeMsgList
	 *            大邮件集合
	 * @param emailParser
	 *            邮件解析对象
	 * @return boolean
	 * @throws MessagingException
	 */
	public boolean handleOneMessage(Context context, POP3Folder inbox,
			int msgnum, String uuid, Map<String, Email> emailList,
			Map<String, Message> largeMsgList, EmailParserProvide emailParser,
			int mailDay) throws MessagingException {
		boolean handleOk = false;
		Message message = null;
		boolean isGmail = false;
		if (context.getAccountType() == Constant.ACCOUNT_GMAIL_TYPE) {
			isGmail = true;
		}
		try {
			message = inbox.getMessage(msgnum);
		} catch (MessagingException e) {
			reHandle(inbox);
			message = inbox.getMessage(msgnum);
		} catch (IllegalStateException e) {
			reHandle(inbox);
			message = inbox.getMessage(msgnum);
		}
		if (uuid == null) {
			try {
				uuid = inbox.getUID(message);
			} catch (MessagingException e) {
				reHandle(inbox);
				uuid = inbox.getUID(message);
			} catch (IllegalStateException e) {
				reHandle(inbox);
				uuid = inbox.getUID(message);
			}
		}
		if (uuid == null || uuid.equals("")) {
			return handleOk;
		} else if (containsUuid(uuid, context.getAccountId())
				|| (isGmail && containsUuid("send:" + uuid, context
						.getAccountId()))) {
			return handleOk;
		}

		Date registerDate = context.getRegistrationDate();
		String mid = null;

		Date sentDate = null;
		try {
			sentDate = message.getSentDate();
		} catch (MessagingException e) {
			reHandle(inbox);
			sentDate = message.getSentDate();
		} catch (IllegalStateException e) {
			reHandle(inbox);
			sentDate = message.getSentDate();
		}
		int msgSize = 0;
		try {
			msgSize = message.getSize();
		} catch (MessagingException e) {
			reHandle(inbox);
			msgSize = message.getSize();
		} catch (IllegalStateException e) {
			reHandle(inbox);
			msgSize = message.getSize();
		}

		try {
			if (sentDate != null && super.isOldMessage(sentDate, registerDate) && isOneMonthMessage(sentDate)) {
				return handleOk;
			}
			// else if(mailDay!=Constant.ROLE_AUTOCLEANUPPERIOD_NEVER_DAY &&
			// !afterMailTime(sentDate,getMailTime(registerDate,mailDay))){
			// return handleOk;
			// }
			else {
				if (isGmail) {
					boolean isSendMsg = false;
					try {
						isSendMsg = isSendMsg(message, context.getLoginName(),
								EmailParserImpl.getInstance());
					} catch (MessagingException e) {
						reHandle(inbox);
						isSendMsg = isSendMsg(message, context.getLoginName(),
								EmailParserImpl.getInstance());
					} catch (IllegalStateException e) {
						reHandle(inbox);
						isSendMsg = isSendMsg(message, context.getLoginName(),
								EmailParserImpl.getInstance());
					}
					if (isSendMsg) {
						addUidCache("send:" + uuid, -1, context.getAccountId());
						logger.info("account:" + context.getLoginName()
								+ " is send msg.");
						return handleOk;
					}
				}
				//黑白名单过滤
				String from = null;
				try {
					from = EmailParserImpl.getInstance().getFrom(message);
				} catch (MessagingException e) {
					reHandle(inbox);
					from = EmailParserImpl.getInstance().getFrom(message);
				} catch (IllegalStateException e) {
					reHandle(inbox);
					from = EmailParserImpl.getInstance().getFrom(message);
				}
				if(isFilter(context, from))
					return handleOk;
				
				mid = String.valueOf(new MessageDao().getNextMessageId(String.valueOf(context.getAccountId()),uuid));
				addUidCache(uuid, Integer.parseInt(mid), context.getAccountId());
				if (super.isLargeMsg(msgSize)) {
					logger.info("account:" + context.getLoginName() + " mid:"
							+ mid + " is large msg.");
					largeMsgList.put(String.valueOf(mid), message);
					handleOk = true;
				} else {
					logger.info("account:" + context.getLoginName() + " mid:"
							+ mid + " is common msg.");
					Email email = emailParser.getEmail(context.getLoginName(),
							message, inbox.getUID(message));
					Map<String, Email> emailMap = new HashMap<String, Email>();
					emailMap.put(mid, email);
					context.setEmailList(emailMap);
					new AttachmentFormatControl().doProces(context);
					new SaveDataProces().doProces(context);

					handleOk = true;
				}
			}
		} catch (UnsupportedEncodingException e) {
//			new MessageDao().deleteMessageById(mid);
			logger.error("account[" + context.getLoginName() + "]" + uuid
					+ " UnsupportedEncodingException", e);
		} catch (Exception e) {
			if (containsUuid(uuid, context.getAccountId())) {
				new MessageDao().deleteMessageById(mid);
				removeUidCache(uuid, context.getAccountId());
			}
			logger.error("account[" + context.getLoginName()
					+ "] receive old mail error.", e);
		}
		return handleOk;
	}
	
}
