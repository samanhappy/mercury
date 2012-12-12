package com.dreamail.mercury.receiver.mail.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.domain.TimeoutException;
import com.dreamail.mercury.mail.receiver.AbstractImapProvide;
import com.dreamail.mercury.mail.receiver.attachment.impl.AttachmentFormatControl;
import com.dreamail.mercury.pojo.Clickoo_folder;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.proces.SaveDataProces;
import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserImpl;
import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserProvide;
import com.dreamail.mercury.util.Constant;
import com.sun.mail.imap.IMAPFolder;

public class IMAP4ProviderImpl extends AbstractImapProvide {

	@Override
	public void receiveMail(Context context) throws MessagingException,TimeoutException {
		if(context.getRegistrationDate()==null){
			return;
		}
		init(context.getAccountId(),context.getLoginName());
		IMAPFolder inbox = null;
		Map<String,Message> largeMsgList = new HashMap<String,Message>();
		EmailParserProvide emailParser = new EmailParserProvide();
		Map<String,Email> emailList = new HashMap<String,Email>();
		
		Clickoo_mail_account account = context.getAccount();
		List<Clickoo_folder> folderList = account.getFolderList();
		int cyclingTimes = 0;
		if(folderList!=null){
			cyclingTimes = folderList.size();
		}
		for(int i=-1;i<cyclingTimes;i++){
			String folderName = null;
			if(i==-1){
				//账号连接失败处理.
				try {
					inbox = getFolder(context,"INBOX");
				} catch (Exception e) {
					return;
				}
				
			}else{
				//根目录下的folder直接打开.多层路径的?getFullName() 返回全路径.
				folderName = folderList.get(i).getName();
				inbox = getFolder(context,folderName);
				context.setRegistrationDate(folderList.get(i).getRegisterDate());
				context.setStatus(String.valueOf(folderList.get(i).getReceiveStatus()));
			}
			//老邮件收取控制.
			if (String.valueOf(NEW_ACCOUNT_STATUS).equals(context.getStatus())) {
				AccountService accountService = new AccountService();
				if(account.getAutoCleanupPeriod()==Constant.ROLE_AUTOCLEANUPPERIOD_NEVER_DAY)
					account.setAutoCleanupPeriod(Constant.Role_IMAP_DEFAULT_DAY);
				logger.info("[new user:"+user+"] receive old message begin,registerDate is "+
					context.getRegistrationDate()+" oldMailNum is "+account.getOldMailNum()+" folderName is:"+folderName+
					" autoCleanupPeriod is "+account.getAutoCleanupPeriod());
				if (receiveOldMessage(context, inbox, emailList, largeMsgList,
					emailParser,RECEIVE_OLD_MAIL_NUM,folderName,account.getAutoCleanupPeriod())) {
					context.setStatus(String.valueOf(COMMON_ACCOUNT_STATUS));
					account.setStatus(COMMON_ACCOUNT_STATUS);
					accountService.updateAccountState(account,context.getRegistrationDate());
				}
				continue;
			}else{
				//收取普通邮件.
				logger.info("[user:"+user+"] receive message begin,registerDate is "+context.getRegistrationDate());
				receiveCommonMail(context, inbox, emailList, largeMsgList,
					emailParser,folderName);
			}
		}
	}
	/**
	 * 收取普通邮件.
	 * 
	 * @param context
	 * @throws MessagingException 
	 */
	protected void receiveCommonMail(Context context,IMAPFolder inbox,Map<String,Email> emailList,
			Map<String,Message> largeMsgList,EmailParserProvide emailParser,String folderName) throws MessagingException{
		Message[] msgs = null;
		String mid = null;
		if (inbox != null) {
			msgs = inbox.search(new AndTerm(
					new SearchTerm[] { new ReceivedDateTerm(ComparisonTerm.GT,
							descRegisterDate()) }));
		}
		if (msgs != null) {
			for (Message message : msgs) {
				// 超时跳出循环
				if (isTimeout(context)) {
					break;
				}
				String uuid = null;
				try {
					uuid = Long.toString(inbox.getUID(message));
					int msgSize = message.getSize();
					if (uuid == null || "".equals(uuid)) {
						logger
								.info("uuid is null2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					}
					if (uuid == null || "".equals(uuid)) {
						continue;
					} else if (containsUuid(uuid, context.getAccountId())) {
						continue;
					} else if (message.getSentDate() == null
							|| super.isOldMessage(message.getSentDate(),
									context.getRegistrationDate())) {
						//黑白名单过滤
						String from = EmailParserImpl.getInstance().getFrom(message);
						if(isFilter(context, from))
							continue;
						mid = String.valueOf(new MessageDao().getNextMessageId(String.valueOf(context.getAccountId()),uuid));
						addUidCache(uuid, Integer.parseInt(mid),context.getAccountId());
						updateMaxUuid(context,uuid);
						if (super.isLargeMsg(msgSize)) {
							logger.info("is LargeMsg uuid[" + uuid + "]");
							largeMsgList.put(mid, message);
						} else {
							logger.info("new Message uuid[" + uuid + "]");
							try {
								Email mail = emailParser.getEmail(user,
										message, uuid);
								emailList.put(String.valueOf(mid), mail);
							} catch (Exception e) {
								e.printStackTrace();
								logger.error("account["
										+ context.getLoginName()
										+ "]MessageID[" + uuid + "]", e);
							}
						}
						message.setFlag(Flags.Flag.SEEN, false);
					}
				} catch (Exception e) {
					if (containsUuid(uuid, context.getAccountId())) {
						new MessageDao().deleteMessageById(mid);
						removeUidCache(uuid, context.getAccountId());
					}
					logger.error("account[" + context.getLoginName()
							+ "]messageID[" + message.getMessageNumber() + "]",
							e);
				}
			}
		}
		context.setLargeMessageList(largeMsgList);
		context.setEmailList(emailList);
		closeConnection(context);
	}
	
	@Override
	public void receiveLargeMail(Context context) throws MessagingException {
		logger.info("account:" + context.getLoginName()
				+ " receive large Msgs,size is "
				+ context.getLargeMessageList().size());
		if (context.getLargeMessageList() != null
				&& context.getLargeMessageList().size() > 0) {
			Map<String,Message> msgs = context.getLargeMessageList();
			EmailParserProvide emailParser = new EmailParserProvide();
			IMAPFolder inbox = (IMAPFolder) context.getFolder();
			for (String mid : msgs.keySet()) {
				Message msg = msgs.get(mid);
				Map<String,Email> emailList = new HashMap<String,Email>();
				String uuid = null;
				try {
					if (!inbox.isOpen()) {
						inbox.open(Folder.READ_WRITE);
					}
					uuid = Long.toString(inbox.getUID(msg));
					if (uuid == null || "".equals(uuid)) {
						logger
								.info("uuid is null!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					}
					Email mail = emailParser.getEmail(context.getLoginName(),
							msg, uuid);
					
					if (mail != null) {
						emailList.put(mid,mail);
						context.setEmailList(emailList);
						new AttachmentFormatControl().doProces(context);
						new SaveDataProces().doProces(context);
						
						sendMessage(context);
					}
				} catch (Exception e) {
					if (containsUuid(uuid, context.getAccountId())) {
						new MessageDao().deleteMessageById(mid);
						removeUidCache(uuid, context.getAccountId());
					}
					logger.error("account[" + context.getLoginName()
							+ "]MessageID[" + msg.getMessageNumber() + "]", e);
				}
			}
			context.setEmailList(null);
		} else {
			logger.info("largerMessage is null!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		closeConnection(context);
	}

	public static void main(String args[]) throws Exception {
		Context context = new Context();
		Map<String,Message> largeMsgList = new HashMap<String,Message>();
		Map<String,Email> emailList = new HashMap<String,Email>();
		EmailParserProvide emailParser = new EmailParserProvide();
		context.setLoginName("kai_li_mind_2@yahoo.com");
		context.setPassword("archermind");
		context.setInpathList(new String[] { "imap.mail.yahoo.com",
				"124.108.115.241", "203.188.201.254" });
		context.setRegistrationDate(new Date());
		System.out.println(new Date());
		context.setReceiveTs("SSL");
		context.setReceiveProtocoltype("imaps");
		context.setPort("993");
		IMAPFolder inbox = new IMAP4ProviderImpl().getFolder(context,"INBOX");
		new IMAP4ProviderImpl().receiveOldMessage(context, inbox, emailList,
				largeMsgList, emailParser,30,"INBOX",30);
	}

	public static Date descRegisterDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH) - 1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
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
	public boolean receiveOldMessage(Context context, IMAPFolder inbox,
			Map<String,Email> emailList, Map<String,Message> largeMsgList,
			EmailParserProvide emailParser,int oldMailNum,String folderName,int mailDay) throws MessagingException {
		Message[] msg = null;
		String mid = null;
		Date registerDate = context.getRegistrationDate();
		Date mailTime = getMailTime(registerDate,OLD_MAIL_DAY);
		boolean receiveOk = false;
		boolean isTimeOut = false;
		msg = inbox.search(new AndTerm(new SearchTerm[] { new ReceivedDateTerm(
				ComparisonTerm.GT, mailTime) }));
		Map<String,String> uuidList = getUidCache(context.getAccountId());
		int num = 0;
		if(uuidList!=null){
			num = getUidCache(context.getAccountId()).size();
		}
		
		for (int j = msg.length - 1; j > -1; j--) {
			if (num >= oldMailNum) {
				receiveOk = true;
				break;
			} else if (isTimeout(context)) {
				logger.info("this time receive " + emailList.size()
						+ "old msgs and" + largeMsgList.size() + " large msgs");
				isTimeOut = true;
				break;
			}
			//此处多发几次新邮件通知
			if (num != 0 && num % 10 == 0) {
				sendMessage(context);
			}
			String uuid = String.valueOf(inbox.getUID(msg[j]));
			if (uuid == null || "".equals(uuid)) {
				continue;
			} else if (containsUuid(uuid, context.getAccountId())) {
				continue;
			}
			
			int msgSize = msg[j].getSize();
			Date sentDate = msg[j].getSentDate();
			try {
				if (sentDate != null
						&& super.isOldMessage(sentDate, registerDate)) {
					continue;
				} else {
					//黑白名单过滤
					String from = EmailParserImpl.getInstance().getFrom(msg[j]);
					if(isFilter(context, from))
						continue;
					mid = String.valueOf(new MessageDao().getNextMessageId(String.valueOf(context.getAccountId()),uuid));
					addUidCache(uuid, Integer.parseInt(mid),context.getAccountId());
					updateMaxUuid(context,uuid);
					num = num + 1;
					if (super.isLargeMsg(msgSize)) {
						largeMsgList.put(String.valueOf(mid),msg[j]);
					} else {
						Email mail = emailParser.getEmail(user, msg[j], String
								.valueOf(inbox.getUID(msg[j])));
						Map<String,Email> emailMap = new HashMap<String,Email>();
						emailMap.put(mid, mail);
						context.setEmailList(emailMap);
						new AttachmentFormatControl().doProces(context);
						new SaveDataProces().doProces(context);
						
					}
				}
			} catch (Exception e) {
				num = num - 1;
				if (containsUuid(uuid, context.getAccountId())) {
					new MessageDao().deleteMessageById(mid);
					removeUidCache(uuid, context.getAccountId());
				}
				logger
						.error("[account:" + user + "]receive old mail error.",
								e);
			}
		}
		logger
				.info("[new user:" + context.getLoginName()
						+ "] receive old message end,emailList size is "
						+ emailList.size() + " largeMsg size is "
						+ largeMsgList.size());
		if(isTimeOut){
			receiveOk = false;
		}else{
			receiveOk = true;
		}
		sendMessage(context);
		context.setLargeMessageList(largeMsgList);
		context.setEmailList(null);
		closeConnection(context);
		return receiveOk;
	}

	protected void updateMaxUuid(Context context,String uuid){
		Clickoo_mail_account account = context.getAccount();
		long maxuuid = 0;
		try {
			maxuuid = Long.parseLong(account.getMaxuuid());
		} catch (NumberFormatException e) {
			logger.error("",e);
		}
		long newuuid = 0 ;
		try {
			newuuid = Long.parseLong(uuid);
		} catch (NumberFormatException e) {
			logger.error("",e);
		}
		logger.info("maxuuid is :"+maxuuid +"uuid is :"+uuid);
		if(newuuid == 0)
			return;
		if(maxuuid < newuuid){
			account.setMaxuuid(uuid);
			new AccountDao().updateMaxUuid(context.getAccount());
		}
	}
}
