package com.dreamail.mercury.receiver.mail.impl;

import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;

import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.mail.util.EmailConstant;
import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserProvide;
import com.sun.mail.pop3.POP3Folder;

public class HotmailProvideImpl extends Pop3ProviderImpl{
	@Override
	public void receiveMail(Context context) throws MessagingException {
//   若账号状态为0且有序，现在用的缓存中的注册日期，需要每次重新查询最新邮件的日期与注册
//      日期中的最大值.
		super.receiveMail(context);
		/*boolean isPutFolder = false;
		if(ConnectUtil.connectMap.get(context.getAccountId())!=null){
			logger.error("aid:"+context.getAccountId()+" folder still connect");
			inbox = (POP3Folder) ConnectUtil.connectMap.get(context.getAccountId()).getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
		}else{
			try {
				inbox = getFolder(context);
				isPutFolder = true;
			} catch (Exception e) {
				failConnect(account);
				logger.error("", e);
				return;
			}
		}
		context.setConnectStatus(EmailConstant.ACCOUNT_CONNECT);*/
		/*if(isPutFolder){
			ConnectUtil.connectMap.put(context.getAccountId(), inbox.getStore());
			logger.error("put folder ..size is "+ConnectUtil.connectMap.size());
			ConnectUtil.timeMap.put(context.getAccountId(), System.currentTimeMillis());
		}*/
	}
	
	@Override
	public boolean isTimeout(Context context) {
		return false;
	}
	@Override
	public void receiveMailControl(Context context,POP3Folder inbox,EmailParserProvide emailParser,Map<String, Email> emailList,
			Map<String, Message> largeMsgList,String compositor,String supportAllUid,String isGmailBox,String accountName) throws MessagingException{
		// 新邮件收取控制.
		if((context.getMailType()!=null && EmailConstant.JMSConstant.JMS_HOTMAIL_NEWMAIL.equalsIgnoreCase(context.getMailType()))){
			try {
				inbox = getFolder(context);
			} catch (Exception e) {
				return;
			}
			logger.info("[user:" + user
					+ "] receive message begin,registerDate is "
					+ context.getRegistrationDate());
			if(context.getAccount().getRecentMessageDate()!=null && context.getAccount().getRecentMessageDate().after(context.getAccount().getRegistrationDate())){
				context.setRegistrationDate(context.getAccount().getRecentMessageDate());
			}
			logger.info("[" + context.getLoginName() + "]"
						+ "receive mail by comositor recentMessageDate is "+context.getRegistrationDate());
			Message[] msgs  = inbox.getMessages();
			receiveMailByCompositor(context, inbox, msgs, isGmailBox,
						emailList, largeMsgList, emailParser, accountName);
			// 老邮件收取控制.
		}else if (String.valueOf(NEW_ACCOUNT_STATUS).equals(context.getStatus())) {
			try {
				inbox = getFolder(context);
			} catch (Exception e) {                                                                                                                                                                                                   
				return;
			}
			AccountService accountService = new AccountService();
			logger.info("[new user:" + user
					+ "] receive old message begin,registerDate is "
					+ context.getRegistrationDate() + " oldMailNum is "
					+ context.getAccount().getOldMailNum() + " autoCleanupPeriod is "
					+ context.getAccount().getAutoCleanupPeriod());
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
	}
}
