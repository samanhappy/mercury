package com.dreamail.mercury.receiver.mail.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;

import net.sf.json.JSONObject;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserImpl;
import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserProvide;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.JMSTypes;
import com.sun.mail.imap.IMAPFolder;

public class YahooProviderImpl extends IMAP4ProviderImpl {

	public void receiveMail(Context context) throws MessagingException {
		if (context.getRegistrationDate() == null) {
			return;
		}
		init(context.getAccountId(), context.getLoginName());
		Clickoo_mail_account account = context.getAccount();
		String folderName = context.getFolderName();
		IMAPFolder inbox = null;

		if ("INBOX".equalsIgnoreCase(folderName)) {
			// 账号连接失败处理根据收件箱连接失败次数控制.
			try {
				inbox = getFolder(context, "INBOX");
			} catch (Exception e) {
				return;
			}
		} else {
			try {
				inbox = getFolder(context, folderName);
			} catch (Exception e) {
				logger.error("account:" + account.getName()
						+ " getFolder fail.", e);
				return;
			}
		}

		Map<String, Message> largeMsgList = new HashMap<String, Message>();
		EmailParserProvide emailParser = new EmailParserProvide();
		Map<String, Email> emailList = new HashMap<String, Email>();
		String uuid = context.getUuid();
		logger.info("uuid:"
				+ (String.valueOf(NEW_ACCOUNT_STATUS).equals(context
						.getStatus())));

		if (uuid != null) {
			// 收取新邮件.
			logger.info("Yahoo[new user:" + user
					+ "] receive snp message,uuid is " + uuid);
			String uuidArr[] = uuid.split(",");
			for (int i = 0; i < uuidArr.length; i++) {
				if (uuidArr[i] == null || "".equals(uuidArr[i])) {
					continue;
				} else if (containsUuid(uuidArr[i], context.getAccountId())) {
					continue;
				}

				Message[] messages = inbox.getMessages();
				logger.info("messages:" + messages.length);
				try {
					// 调试
					Message message = inbox.getMessageByUID(Long
							.parseLong(uuidArr[i]));
					updateMaxUuid(context, uuidArr[i]);
					//黑白名单过滤
					String from = EmailParserImpl.getInstance().getFrom(message);
					if(isFilter(context, from))
						continue;
					String mid = String.valueOf(new MessageDao()
							.getNextMessageId(String.valueOf(context.getAccountId()),uuidArr[i]));
					Email mail = emailParser
							.getEmail(user, message, uuidArr[i]);
					emailList.put(String.valueOf(mid), mail);
				} catch (Exception e) {
					logger.error("account[" + context.getLoginName()
							+ "]MessageID[" + uuidArr[i] + "]", e);
					if (e.getMessage() != null
							&& e.getMessage()
									.contains(
											"A3 NO [INUSE] SELECT Mailbox in use. Please try again later")) {
						// 重发snp消息
						sendNewMailMessage(context.getAccountId(), uuidArr[i]);
					}
				}
			}
			context.setEmailList(emailList);
		}else if (String.valueOf(NEW_ACCOUNT_STATUS).equals(context.getStatus())) {
			// 收取老邮件.
			AccountService accountService = new AccountService();
			if (account.getAutoCleanupPeriod() == Constant.ROLE_AUTOCLEANUPPERIOD_NEVER_DAY)
				account.setAutoCleanupPeriod(Constant.Role_IMAP_DEFAULT_DAY);
			logger.info("Yahoo[new user:" + user
					+ "] receive old message begin,registerDate is "
					+ context.getRegistrationDate() + " oldMailNum is "
					+ account.getOldMailNum() + " autoCleanupPeriod is "
					+ account.getAutoCleanupPeriod());
			if (receiveOldMessage(context, inbox, emailList, largeMsgList,
					emailParser, RECEIVE_OLD_MAIL_NUM, "INBOX",
					account.getAutoCleanupPeriod())) {
				context.setStatus(String.valueOf(COMMON_ACCOUNT_STATUS));
				account.setStatus(COMMON_ACCOUNT_STATUS);
				accountService.updateAccountState(account,
						context.getRegistrationDate());
			}
		}
		closeConnection(context);
	}
	
	@Override
	public boolean isTimeout(Context context) {
		return false;
	}
	
	public static void sendNewMailMessage(long aid, String uuid) {

		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_YAHOOSNP_TYPE);
		json.put(JMSConstans.JMS_AID_KEY, String.valueOf(aid));
		json.put(JMSConstans.JMS_UUID_KEY, uuid);

		JmsSender.sendQueueMsg(json.toString(), JMSTypes.YAHOO_QUEUE);
	}
}
