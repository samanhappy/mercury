package com.dreamail.mercury.mail.receiver;

import java.net.InetAddress;
import java.security.Security;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.Store;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.JmsSender;
import com.dreamail.jms.JmsSenderProvider;
import com.dreamail.mercury.cache.MsgCacheManager;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.mail.connection.CloseConnectionImpl;
import com.dreamail.mercury.mail.connection.ICloseConnection;
import com.dreamail.mercury.memcached.AccountFailTimeCacheManager;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.JMSTypes;

public abstract class AbstractProvide implements IProvide {
	public static final int NEW_ACCOUNT_STATUS = 1;
	public static final int TEMP_ACCOUNT_COMMONMESSAGE_STATUS = 3;
	public static final int COMMON_ACCOUNT_STATUS = 0;
	public static final int RECEIVE_OLD_MAIL_NUM = 10;
	public static final int OLD_MAIL_DAY = 30; 
	public String server;
	public String user;
	public String password;
	public String port;
	public static final Logger logger = LoggerFactory
			.getLogger(AbstractProvide.class);

	@SuppressWarnings("restriction")
	public Properties getProperties(String receiveTs, String port) {
		Properties props = new Properties();
		props.setProperty("mail.pop3.port", port);
		props.setProperty("mail.pop3.connectiontimeout", "30000");
		if (receiveTs != null && "ssl".equalsIgnoreCase(receiveTs)) {
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			props.setProperty("mail.pop3.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.pop3.socketFactory.fallback", "false");
			props.setProperty("mail.pop3.socketFactory.port", port);
		} else if (receiveTs != null && "tls".equalsIgnoreCase(receiveTs)) {
			props.setProperty("mail.pop3.starttls.enable", "true");
			java.security.Security
					.setProperty("ssl.SocketFactory.provider",
							"com.dreamail.mercury.sender.hotmail.DummySSLSocketFactory");
		}
		return props;
	}

	public boolean isLargeMsg(int msgSize) throws NumberFormatException,
			MessagingException {
		boolean flag = false;
		Map<String, String> map = PropertiesDeploy.getConfigureMap();
		String size = map.get("largeMailSize");
		if (msgSize > Integer.parseInt(size)) {
			flag = true;
		}
		return flag;
	}

	public boolean isTimeout(Context context) {
		boolean flag = false;
		long endMill = System.currentTimeMillis();
		long time = (endMill - context.getStartMill());
		if (!context.isState()) {
			long shortTimeOut = context.getTimeout();
			if (time > shortTimeOut) {
				logger.info("AbstractProvide time out ...............");
				closeConnection(context);
				flag = true;
			}
		}
		return flag;
	}

	public void closeConnection(Context context) {
		if (!context.isState()) {
			if (context.getLargeMessageList() == null
					|| context.getLargeMessageList().size() <= 0) {
				logger.info("account[" + context.getLoginName()
						+ "]without LargeMessage，Connection close!");
				ICloseConnection closeConnection = new CloseConnectionImpl();
				closeConnection.closeConnection(context.getStore(), context
						.getFolder());
			}
		} else {
			logger.info("account[" + context.getLoginName()
					+ "]LargeMessage Connection close!");
			ICloseConnection closeConnection = new CloseConnectionImpl();
			closeConnection.closeConnection(context.getStore(), context
					.getFolder());
		}
	}

	/**
	 * 获得当前时间的前一天
	 * 
	 * @return
	 */
	public static Date descRegisterDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH) - 1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public void init(long accountId, String accountName) {
		Map<String, String> uuidMap = getUidCache(accountId);
		int begin = 0;
		if (uuidMap != null) {
			// 同步Clickoo_message表中的uuid.
			setCache(uuidMap, begin, accountId);
		} else {
			logger.info("account:" + accountName
					+ " first time init uuid cache.");
			uuidMap = new LinkedHashMap<String, String>();
			// 同步Clickoo_delete_message表中的uuid.
			/*List<Clickoo_delete_message> deMessageList = new DeleteMessageService()
					.selectDeleteMessageByAccname(accountName);
			if (deMessageList != null) {
				logger.info("sych deletemessage " + deMessageList.size()
						+ " begin.");
				for (Clickoo_delete_message deMessage : deMessageList) {
					uuidMap.put("remove:" + deMessage.getUuid(), "0");
				}
			}*/
			// 同步Clickoo_message表中的uuid.
			setCache(uuidMap, begin, accountId);
		}
		logger.info("account: uuidMap size is "+uuidMap.size());
	}

	private void setCache(Map<String, String> uuidMap, int begin, long accountId) {
		Set<String> se = uuidMap.keySet();
		for (int i = se.size() - 1; i > -1; i--) {
			String uuid = (String) se.toArray()[i];
			if (uuid == null || uuid.startsWith("send:")) {
				continue;
			} else {
				begin = Integer.parseInt(uuidMap.get(uuid));
				break;
			}
		}

		List<Clickoo_message> uuidList = new MessageDao().getUuidListById(
				accountId, begin);
		Map<String, String> newUuidMap = new LinkedHashMap<String, String>();
		if (uuidList != null) {
			for (Clickoo_message msg : uuidList) {
				newUuidMap.put(msg.getUuid(), String.valueOf(msg.getId()));
			}
		}
		MsgCacheManager.addMID(accountId, uuidMap, newUuidMap);
	}

	public Map<String, String> getUidCache(long accountId) {
		return MsgCacheManager.getMIDCache(accountId);
	}

	public void addUidCache(String uuid, long id, long accountId) {
		Map<String, String> uuidList = getUidCache(accountId);
		if (uuidList == null) {
			uuidList = new LinkedHashMap<String, String>();
		}
		Collections.synchronizedMap(uuidList).put(uuid, String.valueOf(id));
		logger.info("adduidCache");
		MsgCacheManager.updateMid(accountId, Collections
				.synchronizedMap(uuidList));
	}

	public void removeUidCache(String uuid, long accountId) {
		Map<String, String> uuidList = getUidCache(accountId);
		if (uuidList != null) {
			Collections.synchronizedMap(uuidList).remove(uuid);
			logger.info("removeuidCache");
			MsgCacheManager.updateMid(accountId, Collections
					.synchronizedMap(uuidList));
		}
	}

	public boolean containsUuid(String uuid, long accountId) {
		Map<String, String> uuidList = getUidCache(accountId);
		if (uuidList == null || uuid == null) {
			return false;
		} else {
			return uuidList.containsKey(uuid);
		}
	}

	/**
	 * 判断邮件发送时间是否在注册时间之后
	 * 
	 * @param SentDate
	 * @param registrationDate
	 * @return
	 */
	public boolean isOldMessage(Date SentDate, Date registrationDate) {
		return SentDate.after(registrationDate);
	}
	/**
	 * 判断邮件是否是一个月内的邮件.
	 * @param SentDate
	 * @return
	 */
	public boolean isOneMonthMessage(Date SentDate){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH) - 30);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return SentDate.after(calendar.getTime());
	}

	public boolean connect(String user, String password, Store store,
			String[] serverColection) throws MessagingException {
		boolean flag = false;
		String hostName = user.indexOf("@") != -1 ? user.split("@")[1] : null;
		if (hostName != null) {
			for (int i = 0; i < serverColection.length; i++) {
				InetAddress[] inetAddress = null;
				try {
					inetAddress = InetAddress.getAllByName(serverColection[i]);
				} catch (Exception e) {
					logger.error("receiveMail/IMAP4Provider/Exception: ["
							+ user + "] [" + server
							+ "] [parsing host failure]", e);
				}
				if (inetAddress != null && inetAddress.length > 0) {
					for (InetAddress connHost : inetAddress) {
						try {
							store.connect(connHost.getHostAddress(), user,
									password);
							flag = true;
							logger.info("receiveMail/IMAP4Provider/Success: ["
									+ user + "] [" + connHost.getHostAddress()
									+ "]");
							return flag;
						} catch (MessagingException e) {
							logger
									.warn("receiveMail/IMAP4Provider/Exception: ["
											+ user
											+ "] ["
											+ connHost.getHostAddress()
											+ "]"
											+ e);
						}
					}
				}
			}
		}
		return flag;
	}

	public String convertName(String accountName) {
		if (accountName.startsWith("recent:")) {
			return accountName.substring(7);
		}
		return accountName;
	}

	/**
	 * 发送新邮件JMS消息给UPE.
	 * @param context
	 */
	public void sendMessage(Context context) {
		try {
			JSONObject json = new JSONObject();
			json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_NEWMAIL_TYPE);
			json.put(JMSConstans.JMS_AID_KEY, context.getAccountId());
			json.put(JMSConstans.JMS_ACCOUNT_NAME_KEY, context.getAccount().getName());
			JmsSender.sendTopicMsg(json.toString(), JMSTypes.MESSAGE_TOPIC);
		} catch (Exception e) {
			logger.error("sendTopicMsg messageTopic error...", e);
		}
	}

	/**
	 * 账号连接失败更新数据库对应账号失败次数,失败到达3次发消息通知 用户.
	 * 
	 * @param account
	 */
	protected void failConnect(Clickoo_mail_account account){
		String aid = String.valueOf(account.getId());
		String time = AccountFailTimeCacheManager.get(aid);
		int fail = 0;
		if(time!=null)
			fail = Integer.parseInt(time);
		if(fail==2){
			logger.info("[account:"+account.getName()+"] fail connect 3 times,sende message accountInvalid,"+account.getId());
			try {
				JmsSenderProvider.getInstance().sendMessage("accountInvalid", "messageTopic", String.valueOf(account.getId()));
			} catch (Exception e1) {
				logger.error("send msg accountInvalid fail with aid "+account.getId());
			}
		}
		if(fail<=3){
			fail++;
			logger.info("Account["+account.getName()+ "]connect fail "+fail +"times.");
			AccountFailTimeCacheManager.addFailTime(aid, String.valueOf(fail));
		}
	}
	/**
	 * 账号连接成功时若账号连接失败次数不为0将失败次数还原为0.
	 * 
	 * @param account
	 */
	protected void successConnect(Clickoo_mail_account account){
		AccountFailTimeCacheManager.addFailTime(String.valueOf(account.getId()), String.valueOf(0));
	}
	/**
	 * 获取注册日期前指定日期.
	 * @param registerDate
	 * @param day
	 * @return
	 */
	public Date getMailTime(Date registerDate,int day){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(registerDate);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - day);
		return calendar.getTime();
	}
	
	/**
	 * 
	 * @param context
	 * @param from
	 * @return
	 */
	protected boolean isFilter(Context context,String from){
		boolean isFilter = false;
		String sign = context.getSign();
		if(Constant.BLACK_SENDER_FILTER.equals(sign) && from!=null ){
			Map<String,List<String>> filterMap = context.getFilterMap();
			List<String> filterList = filterMap.get(sign);
			if(filterList!=null){
				for(String filter:filterList){
					if(from.contains(filter)){
						isFilter = true;
						break;
					}
				}
			}
		}else if(Constant.WHITE_SENDER_FILTER.equals(sign) && from!=null){
			Map<String,List<String>> filterMap = context.getFilterMap();
			List<String> filterList = filterMap.get(sign);
			if(filterList!=null){
				for(String filter:filterList){
					if(from.contains(filter)){
						break;
					}
				}
			}
		}
		return isFilter;
	}
}
