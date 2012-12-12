package com.dreamail.mercury.mail.receiver;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.receiver.parser.EmailParser;
import com.dreamail.mercury.util.Constant;
import com.sun.mail.pop3.POP3Folder;

public abstract class AbstractPop3Provide extends AbstractProvide {
	/**
	 * 获取Store
	 * @param receiveTs
	 * @return
	 */
	public String getPop3SSLStore(String receiveTs){
		if(receiveTs!=null &&"ssl".equalsIgnoreCase(receiveTs) ||"tls".equalsIgnoreCase(receiveTs)){
			return "pop3s";
		}else{
			return "pop3";
		}
	}
	
	/**
	 * 获取降序排序的邮件的结束标记.
	 * @param inbox
	 * @param msg
	 * @param registerDate
	 * @param searchSize
	 * @return int
	 */
	public int searchSuffixDesc(POP3Folder inbox, Message[] msg, Date registerDate,int searchSize,long accountId) throws Exception {
		int startSuffix = 0, endSuffix = msg.length;
		if (!inbox.isOpen()) {
			inbox.open(Folder.READ_WRITE);
		}
		do {
//			log.info(" startSuffix = " + startSuffix + " endSuffix = " + endSuffix);
			endSuffix = searchMsgSuffixDesc(inbox, msg, registerDate, searchSize, startSuffix, endSuffix,accountId);
			startSuffix = endSuffix - searchSize < 0 ? 0 : endSuffix - searchSize;
			if (searchSize == 1) {
				break;
			}
			searchSize = searchSize / 2 < 1 ? 1 : searchSize / 2;
		} while (searchSize >= 1);
		if (isNewMsg(registerDate, msg[startSuffix].getSentDate(), inbox.getUID(msg[startSuffix]),accountId)) {
			return endSuffix;
		} else {
			return startSuffix;
		}
	}
	/**
	 * 获取升序排序的邮件的结束标记.
	 * @param inbox
	 * @param msg
	 * @param registerDate
	 * @param searchSize
	 * @return int
	 */
	public int searchSuffixAsc(POP3Folder inbox, Message[] msg, Date registerDate,int searchSize,long accountId) throws Exception {
		int startSuffix = 0, endSuffix = msg.length, msgLength = msg.length;
		do {
			startSuffix = searchMsgSuffixAsc(inbox, msg, registerDate,  searchSize, startSuffix, endSuffix,accountId);
			endSuffix = startSuffix + searchSize > msgLength ? msgLength : startSuffix + searchSize;
			if (searchSize == 1) {
				break;
			}
			searchSize = searchSize / 2 < 1 ? 1 : searchSize / 2;
		} while (searchSize >= 1);
		if (isNewMsg(registerDate, msg[startSuffix].getSentDate(), inbox.getUID(msg[startSuffix]),accountId)) {
			return startSuffix;
		} else {
			return endSuffix;
		}
	}
	/**
	 * 获取降序排序的邮件的结束标记.
	 * @param inbox
	 * @param msg
	 * @param registerDate
	 * @param searchSize
	 * @param startSuffix
	 * @param endSuffix
	 * @return int
	 */
	private int searchMsgSuffixDesc(POP3Folder inbox, Message[] msg, Date registerDate, int searchSize, int startSuffix, int endSuffix,long accountId)
			throws Exception {
		Date sentDate = null;
		boolean newMsgFlag = true;
		int copyStartSuffix = startSuffix;
		if (startSuffix + searchSize < endSuffix) {
			do {
				startSuffix += searchSize;
				if (startSuffix < endSuffix) {
					sentDate = msg[startSuffix].getSentDate();
					if (sentDate == null) {
						startSuffix++;
						if (startSuffix < endSuffix) {
							sentDate = msg[startSuffix].getSentDate();
							if (sentDate == null) {
								startSuffix = startSuffix - 2;
								if (startSuffix < endSuffix && startSuffix >= copyStartSuffix) {
									sentDate = msg[startSuffix].getSentDate();
								} else {
									startSuffix = endSuffix;
								}
							}
						} else {
							startSuffix = endSuffix;
						}
					}
					if (!inbox.isOpen()) {
						inbox.open(Folder.READ_WRITE);
					}
					newMsgFlag = isNewMsg(registerDate, sentDate, inbox.getUID(msg[startSuffix]),accountId);
				} else {
					startSuffix = endSuffix;
				}
			} while (newMsgFlag && startSuffix != endSuffix);
		} else {
			startSuffix = endSuffix;
		}
		return startSuffix;
	}

	/**
	 * 获取升序排序的邮件的结束标记.
	 * @param inbox
	 * @param msg
	 * @param registerDate
	 * @param searchSize
	 * @param startSuffix
	 * @param endSuffix
	 * @return int
	 */
	private int searchMsgSuffixAsc(POP3Folder inbox, Message[] msg, Date registerDate, int searchSize, int startSuffix, int endSuffix,long accountId)
			throws Exception {
		Date sentDate = null;
		int copyEndSuffix = endSuffix;
		boolean newMsgFlag = true;
		if (endSuffix > searchSize) {
			do {
				endSuffix -= searchSize;
				if (endSuffix >= startSuffix) {
					sentDate = msg[endSuffix].getSentDate();
					if (sentDate == null) {
						endSuffix--;
						if (endSuffix >= startSuffix) {
							sentDate = msg[endSuffix].getSentDate();
							if (sentDate == null) {
								endSuffix = endSuffix + 2;
								if (endSuffix >= startSuffix && endSuffix < copyEndSuffix) {
									sentDate = msg[endSuffix].getSentDate();
								} else {
									endSuffix = startSuffix;
								}
							}
						} else {
							endSuffix = startSuffix;
						}
					}
					if (!inbox.isOpen()) {
						inbox.open(Folder.READ_WRITE);
					}
					newMsgFlag = isNewMsg(registerDate, sentDate,inbox.getUID(msg[endSuffix]),accountId);
				} else {
					endSuffix = startSuffix;
				}
			} while (newMsgFlag && endSuffix != startSuffix);
		} else {
			endSuffix = startSuffix;
		}
		return endSuffix;
	}
	/**
	 * 判断是否新邮件.
	 * @param registerDate
	 * @param sentDate
	 * @param msgID
	 * @return boolean
	 */
	private boolean isNewMsg(Date registerDate, Date sentDate, String msgID,long accountId) throws Exception {
		boolean newMsgFlag = true;
		if (containsUuid(msgID,accountId)) {
			newMsgFlag = false;
		} else {
			if (sentDate != null) {
				if (!isOldMessage(sentDate, registerDate)) {
					newMsgFlag = false;
				}
			}
		}
		return newMsgFlag;
	}
	
	/**
	 * 根据邮件排序获取开始或结束标记.
	 * @param registerDate
	 * @param sentDate
	 * @param msgID
	 * @return boolean
	 * @throws Exception 
	 */
	protected int[] getMarker(String compositor,Date registerDate,POP3Folder inbox,Message[] msgs,int start,int end,long accountId) throws Exception{
		int[] sign = new int[2];
		int searchsize = Integer.parseInt(
				PropertiesDeploy.getConfigureMap().get(Constant.SEARCHSIZE));
		if(compositor!=null){
			if("0".equals(compositor)){
				start = searchSuffixAsc(inbox, msgs, registerDate, searchsize,accountId);
			}else if("1".equals(compositor)){
				end = searchSuffixDesc(inbox, msgs, registerDate, searchsize,accountId);
			}else{
				;
			}
			logger.info("account: " + accountId +" compositor:"+compositor+" start :"+start+" end : "+
					end+" searchsize:"+searchsize+" registerDate: "+registerDate+
					" msgs.length: "+msgs.length);
		}
		sign[0] = start;
		sign[1] = end;
		return sign;
	}
	/**
	 * 获取所有邮件uuid 以key：value，msgNum：uuid形式.
	 * @param inbox
	 * @return TreeMap<String,String>
	 * @throws MessagingException 
	 */	
	public TreeMap<String,String> getAllUidMap(POP3Folder inbox) throws MessagingException{
		TreeMap<String,String> treeMap = null;
		try {
			treeMap = inbox.getAllUID();
		} catch (MessagingException e) {
			if (!inbox.isOpen()) {
				inbox.open(Folder.READ_WRITE);
			}
			treeMap = inbox.getAllUID();
		}catch (IllegalStateException e) {
			if (!inbox.isOpen()) {
				inbox.open(Folder.READ_WRITE);
			}
			treeMap = inbox.getAllUID();
		}
        return treeMap;
	}
	/**
	 * 获取所有邮件uuid 以key：msgNum，value：uuid形式.
	 * @param inbox
	 * @return TreeMap<String,String>
	 * @throws MessagingException 
	 */	
	public TreeMap<String,String> getAllUidMapDec(POP3Folder inbox) throws MessagingException{
		TreeMap<String,String> treeMap = null;
		try {
			treeMap = inbox.getAllUIDDec();
		} catch (MessagingException e) {
			if (!inbox.isOpen()) {
				inbox.open(Folder.READ_WRITE);
			}
			treeMap = inbox.getAllUIDDec();
		}catch (IllegalStateException e) {
			if (!inbox.isOpen()) {
				inbox.open(Folder.READ_WRITE);
			}
			treeMap = inbox.getAllUIDDec();
		}
        return treeMap;
	}
	/**
	 * 根据context获取收件箱.
	 * @param context
	 * @return POP3Folder
	 * @throws MessagingException 
	 */
	public POP3Folder getFolder(Context context) throws MessagingException{
		user = context.getLoginName();
		password = context.getPassword();
		port = context.getPort();
		Store store = null;
		POP3Folder inbox = null;
		Session session = Session.getInstance(getProperties(context.getReceiveTs(), port));
		store = session.getStore(getPop3SSLStore(context.getReceiveTs()));
//		try {
			try {
				connect(user,password,store,context.getInpathList());
				inbox = (POP3Folder) store.getFolder("INBOX");
				inbox.open(Folder.READ_ONLY);
			} catch (MessagingException e) {
				failConnect(context.getAccount());
				logger.error("", e);
				throw new MessagingException();
			}
			
//		} catch (Exception e1) {
//			if(user!=null && user.endsWith("@hotmail.com")){
//				connect(user,password,store,context.getInpathList());
//			}
//		}
//		try {
			
//		} catch (AuthenticationFailedException e) {
//			if(e.getMessage().indexOf(("Username and password not accepted"))!=-1){
//				logger.warn("Username and password not accepted:connect next time...");
//				if(!store.isConnected()){
//					store.connect(server, user, password);
//				}
//				inbox = (POP3Folder) store.getFolder("INBOX");
//			}
//		}
		successConnect(context.getAccount());
		context.setFolder(inbox);
		context.setStore(store);
		return inbox;
	}
	/**
	 * 根据webaccount对象和uuid下载邮件.
	 * @param account
	 * @param uuid
	 * @return Message
	 * @throws MessagingException 
	 */
	public Message getPop3MessageByUuid(POP3Folder inbox,String supportAllUid,String uuid) throws MessagingException{
		Message message = null;
		if(uuid==null){
			return message;
		}
			if ("0".equals(supportAllUid)) {
			TreeMap<String, String> treeMap = null;
			try {
				treeMap = getAllUidMap(inbox);
			} catch (MessagingException e2) {
				logger.warn("account[" + user
						+ "]get all uid error.", e2);
			}
			if(treeMap!=null){
				String msgNum = treeMap.get(uuid);
				if(msgNum!=null){
					int num = Integer.parseInt(msgNum);
					try {
						message = inbox.getMessage(num);
					} catch (IllegalStateException e) {
						reHandle(inbox);
						message = inbox.getMessage(num);
					} catch (MessagingException e) {
						reHandle(inbox);
						message = inbox.getMessage(num);
					}
				}
			}
		} else {
			try {
				message = getPop3Message(inbox,message,uuid);
			} catch (IllegalStateException e) {
				reHandle(inbox);
				message = getPop3Message(inbox,message,uuid);
			} catch (MessagingException e) {
				reHandle(inbox);
				message = getPop3Message(inbox,message,uuid);
			}
		}
		if(message==null){
			logger.error("message is null.uuid is "+uuid);
			DLEmailException exception = new DLEmailException();
			exception.setMessage(Constant.GET_MAIL_FAIL);
			throw exception;	
		}
		return message;
	}
	
	public Message getPop3MessageByUuid(WebAccount account,String uuid) throws MessagingException{
		POP3Folder inbox = getFolder(account);
		String supportAllUid = account.getSupportalluid();
		return getPop3MessageByUuid(inbox,supportAllUid,uuid);
	}
	/**
	 * 根据传入compositor做排序或普通下载邮件.
	 * @param compositor
	 * @param inbox
	 * @param message
	 * @param uuid
	 * @param registerDate
	 * @return Message
	 * @throws MessagingException 
	 */
//	private Message getPop3Message(String compositor,POP3Folder inbox,Message message,String uuid,Date registerDate,long accountId) throws MessagingException {
	protected Message getPop3Message(POP3Folder inbox,Message message,String uuid) throws MessagingException {
		Message[] msgs = inbox.getMessages();
		int start = 0;
		int end = msgs.length;
//		try {
//			if(end>0 && ("0".equals(compositor) || "1".equals(compositor))){
//				int[] marker = getMarker(compositor,registerDate,inbox, msgs, start, end,accountId);
//				start = marker[0];
//				end = marker[1];
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		for(int i=start;i<end;i++){
			if(uuid.equals(inbox.getUID(msgs[i]))){
				message =  msgs[i];
				break;
			}
		}
		return message;
	}
	/**
	 * 根据WebAccount获取收件箱.
	 * @param account
	 * @return POP3Folder
	 * @throws MessagingException 
	 */	
	public POP3Folder getFolder(WebAccount account) throws MessagingException{
		user = account.getName();
		password = account.getPassword();
		port = account.getReceivePort();
		Store store = null;
		POP3Folder inbox = null;
		Session session = Session.getInstance(getProperties(account.getReceiveTs(), port));
		store = session.getStore(getPop3SSLStore(account.getReceiveTs()));
		connect(user,password,store,account.getInpathList());
//		try {
			inbox = (POP3Folder) store.getFolder("INBOX");
//		} catch (AuthenticationFailedException e) {
//			if(e.getMessage().indexOf(("Username and password not accepted"))!=-1){
//				logger.warn("Username and password not accepted:reset connect...");
//				if(!store.isConnected()){
//					store.connect(server, user, password);
//				}
//				inbox = (POP3Folder) store.getFolder("INBOX");
//			}
//		}
		inbox.open(Folder.READ_WRITE);
		account.setStore(store);
		account.setFolder(inbox);
		return inbox;
	}
	/**
	 * 重新打开收件箱.
	 * @param inbox
	 * @throws MessagingException 
	 */	
	protected void reHandle(POP3Folder inbox) throws MessagingException{
		if (!inbox.isOpen()) {
			try {
				inbox.open(Folder.READ_WRITE);
			} catch (MessagingException e) {
//				if(e.getMessage().indexOf("open failed")!=-1){
//					getFolder(context);
//				}
				logger.warn("open folder failed.wait for next time.");
			}
		}
	}
	
	/**
	 * 判断是否发件箱中的邮件.
	 * @param msg message对象
	 * @param accountName 账户名称
	 * @param parser 邮件解析对象
	 * @throws Exception
	 */
	public boolean isSendMsg(Message msg, String accountName,
			EmailParser parser) throws Exception {
		boolean sendFlag = false;
		InternetAddress address[] = (InternetAddress[]) msg.getFrom();
		String from = address[0].getAddress();
		accountName = accountName.trim().substring(7);
		if (!"".equals(from) && from != null) {
			if (from.indexOf(accountName) != -1) {
				String to = parser.getMailAddress(msg, "TO");
				String cc = parser.getMailAddress(msg, "CC");
				String bcc = parser.getMailAddress(msg, "BCC");
				if (to.indexOf(accountName) == -1
						&& cc.indexOf(accountName) == -1
						&& bcc.indexOf(accountName) == -1) {
					sendFlag = true;
				}
			}
		}
		return sendFlag;
	}
	
	/**
	 * 得到缓存中收件箱中的uuid的数量.
	 * @param accountId 账号id
	 * @return int
	 */
	public int getReceiveMsgNum(long accountId){
		int num = 0;
		Map<String,String> map = getUidCache(accountId);
		if(map!=null){
			for(String uuid:map.keySet()){
				if(uuid!=null&& (uuid.startsWith("send:")||uuid.startsWith("remove:"))){
					continue;
				}
				num++;
			}
		}
		return num;
	}
	/**
	 * 判断邮件是否在指定日期之后.
	 * @param sentDate
	 * @param mailTime
	 * @return
	 */
	public boolean afterMailTime(Date sentDate,Date mailTime){
		return sentDate.after(mailTime);
	}
}
