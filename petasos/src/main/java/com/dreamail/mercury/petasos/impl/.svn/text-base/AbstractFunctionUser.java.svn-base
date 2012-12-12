package com.dreamail.mercury.petasos.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.jms.JmsSender;
import com.dreamail.jms.MessageSender;
import com.dreamail.mercury.cache.ReceiveServerCacheManager;
import com.dreamail.mercury.cache.RoleCacheManager;
import com.dreamail.mercury.cache.SendServerCacheManager;
import com.dreamail.mercury.config.PProperties;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.dal.dao.ReceiveServerDao;
import com.dreamail.mercury.dal.dao.SendServerDao;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.TimeoutException;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebUser;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.mail.receiver.ISupport;
import com.dreamail.mercury.mail.receiver.ImapSupport;
import com.dreamail.mercury.mail.receiver.Pop3Support;
import com.dreamail.mercury.mail.validate.IMailValidate;
import com.dreamail.mercury.mail.validate.impl.EWSMailValidateImpl;
import com.dreamail.mercury.mail.validate.impl.ImapMailValidateImpl;
import com.dreamail.mercury.mail.validate.impl.Pop3MailValidateImpl;
import com.dreamail.mercury.mail.validate.impl.SendMailValidateImpl;
import com.dreamail.mercury.petasos.IUserController;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_mail_receive_server;
import com.dreamail.mercury.pojo.Clickoo_mail_send_server;
import com.dreamail.mercury.pojo.Clickoo_role;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.pojo.User_role;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.JsonUtil;

public abstract class AbstractFunctionUser implements IUserController {

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(AbstractFunctionUser.class);

	private static final Pattern usernamePattern = Pattern
			.compile("[a-zA-Z0-9_]{4,60}");
	private static final Pattern passwordPattern = Pattern
			.compile("[a-zA-Z0-9_!@#$%^&*.-]{6,60}");

	/**
	 * 处理用户名密码.
	 * 
	 * @param wUser
	 *            用户对象.
	 */
	protected void handleUsernamePassword(WebUser wUser) {
		// 用户名密码base64解码
		if (wUser.getName() != null) {
			wUser.setName(new String(EmailUtils.base64TochangeByte(wUser
					.getName())));
		}
		if (wUser.getPasswd() != null) {
			wUser.setPasswd(new String(EmailUtils.base64TochangeByte(wUser
					.getPasswd())));
		}
	}

	/**
	 * 验证用户名密码格式.
	 * 
	 * @param user
	 *            用户对象.
	 * @return
	 */
	protected boolean validateUsernamePassword(WebUser user) {
		if (user.getName() != null
				&& !usernamePattern.matcher(user.getName()).matches()) {
			logger.info("username pattern is error");
			return false;
		}
		if (user.getPasswd() != null
				&& !passwordPattern.matcher(user.getPasswd()).matches()) {
			logger.info("password pattern is error");
			return false;
		}
		return true;
	}

	/**
	 * 添加邮箱帐号
	 * 
	 * @param account
	 * @param status
	 * @return Status
	 */
	public List<Object> addMailAccount(WebAccount accountIn,
			WebAccount accountOut, Status status, long uid) {
		List<Object> objects = new ArrayList<Object>();
		if (accountIn != null) {
			Clickoo_mail_account mailaccount = getMailAccountByWebAccount(accountIn);
			objects = validateAddAccount(mailaccount, accountOut, status, uid);
		} else {
			logger.warn("For less than the server");
			status.setCode(ErrorCode.CODE_EmailAccount_NoSvr);
			status.setMessage("For less than the server");
			objects.add(status);
		}
		return objects;
	}

	/**
	 * 根据注册的帐号名获取发送邮件服务器列表
	 * 
	 * @param account
	 * @param name
	 * @return List<Clickoo_mail_account>
	 */
	public Clickoo_mail_account loadsendServerCFG(WebAccount account,
			Clickoo_mail_send_server sendservers) {
		String outPath = sendservers.getOutPath();
		Clickoo_mail_account sendaccount = new Clickoo_mail_account();
		sendaccount.setOutPath(outPath);
		account.setStatus(String.valueOf(sendservers.getStatus()));
		return sendaccount;
	}

	/**
	 * 根据注册的帐号名获取接收邮件服务器列表
	 * 
	 * @param account
	 * @param name
	 * @return List<Clickoo_mail_account>
	 */
	public Clickoo_mail_account loadreceiveServerCFG(
			Clickoo_mail_receive_server receiveservers) {
		Clickoo_mail_account receiveaccounts = new Clickoo_mail_account();
		receiveaccounts.setInPath(receiveservers.getInPath());
		return receiveaccounts;
	}

	/**
	 * 在线校验邮箱帐号
	 * 
	 * @param accountIn
	 * @return boolean
	 * @throws Exception
	 */
	public boolean validateEmailAccountServerAttribute(WebAccount accountIn,
			WebAccount receiveaccounts, WebAccount sendaccounts)
			throws Exception {
		boolean flag = false;
		IMailValidate validate = new SendMailValidateImpl();

		flag = validateEmailAccountReceiveServerAttribute(accountIn,
				receiveaccounts);
		if (flag) {
			flag = validateEmailAccountSendServerAttribute(accountIn,
					sendaccounts, validate);

			// 测试代理发送
			if (!flag) {
				flag = validateEmailAccountProxySend(accountIn, validate);
			}
		}
		return flag;
	}

	/**
	 * 校验邮箱账号：校验receiveServer
	 * 
	 * @param accountIn
	 * @param receiveaccounts
	 * @param validate
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws TimeoutException
	 */
	public boolean validateEmailAccountReceiveServerAttribute(
			WebAccount accountIn, WebAccount receiveaccount)
			throws UnsupportedEncodingException, MessagingException,
			TimeoutException {
		boolean flag = false;
		logger.info("E-mail account to receive messages on the check");
		flag = validateReceiveConfig(receiveaccount);
		if (flag) {
			logger.info("Receive e-mail verification passed");
			accountIn.setReceiveHost(receiveaccount.getReceiveHost());
			accountIn.setReceivePort(receiveaccount.getReceivePort());
			accountIn.setReceiveProtocolType(receiveaccount
					.getReceiveProtocolType());
			accountIn.setReceiveTs(receiveaccount.getReceiveTs());
			accountIn.setCompositor(receiveaccount.getCompositor());
			accountIn.setSupportalluid(receiveaccount.getSupportalluid());
		}
		return flag;
	}

	/**
	 * 校验邮箱账号：校验sendServer
	 * 
	 * @param accountIn
	 * @param receiveaccounts
	 * @param validate
	 * @return
	 * @throws TimeoutException
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public boolean validateEmailAccountSendServerAttribute(
			WebAccount accountIn, WebAccount sendaccount,
			IMailValidate validate) throws UnsupportedEncodingException,
			MessagingException, TimeoutException {
		boolean flag = true;
		// validate = new SendMailValidateImpl();
		logger.info("E-mail account to send messages on the check");
		accountIn.setSendHost(sendaccount.getSendHost());
		accountIn.setSendPort(sendaccount.getSendPort());
		accountIn.setSendProtocolType(sendaccount.getSendProtocolType());
		accountIn.setSendTs(sendaccount.getSendTs());
		return flag;
	}

	/**
	 * 校验邮箱账号：测试代理发送
	 * 
	 * @param accountIn
	 * @param validate
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws TimeoutException
	 */
	public boolean validateEmailAccountProxySend(WebAccount accountIn,
			IMailValidate validate) throws UnsupportedEncodingException,
			MessagingException, TimeoutException {
		boolean flag = false;
		WebAccount proxy = new WebAccount();
		proxy.setName(accountIn.getName());
		proxy.setPassword(accountIn.getPassword());
		proxy.setProxyName(PropertiesDeploy.getConfigureMap().get("proxyName"));
		proxy.setProxyPassword(PropertiesDeploy.getConfigureMap().get(
				"proxyPassword"));
		proxy.setSendHost(PropertiesDeploy.getConfigureMap().get(
				"proxySmtpServer"));
		proxy.setSendPort(PropertiesDeploy.getConfigureMap().get(
				"proxySendPort"));
		proxy.setSendTs(PropertiesDeploy.getConfigureMap().get("proxyType"));
		flag = validate.validate(proxy);
		if (flag) {
			logger.info("success to use proxy send email validate......");
		} else {
			logger.info("failed to use proxy send email validate......");
		}
		return flag;
	}

	/**
	 * 在线校验james邮箱帐号
	 * 
	 * @param accountOut
	 * @return boolean
	 * @throws Exception
	 */
	public boolean validateJamesEmailAccountServerAttribute(
			WebAccount accountIn, WebAccount accountOut,
			WebAccount receiveaccount, WebAccount sendaccount)
			throws Exception {
		boolean flag = false;
		IMailValidate validate = new SendMailValidateImpl();
		flag = validateEmailAccountReceiveServerAttribute(accountIn,
				receiveaccount);
		if (flag) {
			flag = validateEmailAccountSendServerAttribute(accountIn,
					sendaccount, validate);
		}
		return flag;
	}

	/**
	 * 获取james帐号发送邮件属性列表
	 * 
	 * @param account
	 * @return List<WebAccount>
	 */
	public WebAccount loadJamesSendConfig(WebAccount account,
			Clickoo_mail_send_server sendservers) {
		if (account.getSendHost() == null || "".equals(account.getSendHost())) {
			Clickoo_mail_account send_account = loadsendServerCFG(account,
					sendservers);
			String name = account.getName().split("@")[0];
			account.setName(name);
			account = account.getAccountBySendServer(send_account);
		}
		return account;
	}

	/**
	 * 获取james帐号接收邮件属性列表
	 * 
	 * @param account
	 * @return List<WebAccount>
	 */
	public WebAccount loadJamesReceiveConfig(WebAccount account,
			Clickoo_mail_receive_server receiveservers) {
		if (account.getReceiveHost() == null
				|| "".equals(account.getReceiveHost())) {
			Clickoo_mail_account receive_account = loadreceiveServerCFG(receiveservers);
			String name = account.getName().split("@")[0];
			account.setName(name);
			account = account.getAccountByReceiveServer(receive_account);
		}
		return account;
	}

	/**
	 * 获取帐号发送邮件属性列表
	 * 
	 * @param account
	 * @return List<WebAccount>
	 */
	public WebAccount loadSendConfig(WebAccount account,
			Clickoo_mail_send_server sendservers) {
		if (account.getSendHost() == null || "".equals(account.getSendHost())) {
			Clickoo_mail_account send_account = loadsendServerCFG(account,
					sendservers);
			account = account.getAccountBySendServer(send_account);
		}
		return account;
	}

	/**
	 * 获取帐号接收邮件属性列表
	 * 
	 * @param account
	 * @return List<WebAccount>
	 */
	public WebAccount loadReceiveConfig(WebAccount account,
			Clickoo_mail_receive_server receiveservers) {
		if (account.getReceiveHost() == null
				|| "".equals(account.getReceiveHost())) {
			Clickoo_mail_account receive_account = loadreceiveServerCFG(receiveservers);
			account = account.getAccountByReceiveServer(receive_account);
		}
		return account;
	}

	/**
	 * 更新用户时间偏移量.
	 * 
	 * @param ts
	 * @param user
	 */
	public void updateTsOffset(String ts, Clickoo_user user, String IMEI) {

		// 过渡代码，如果协议中没有时间戳，则不处理
		if (ts == null) {
			return;
		}
		long offset = getOffset(ts);
		user.setTsoffset(offset);
		if (new UserDao().updateUserTsOffset(user)) {
			// 发送更新用户时间偏移量Topic消息
			MessageSender.sendUpdateOffsetMessage(
					String.valueOf(user.getUid()), offset, IMEI);
		}
	}

	protected static long getOffset(String ts) {
		// 增加3秒钟延迟处理
		// 测试发现，手机带过去的时间戳是子午分界线的时间，所以此处应减去8个小时的时间（系统采用GMT+8时区）
		// 注意：如果默认时区改了，此处也要修改
		// long offset = client_ts + 3000 - server_ts - 8 * 3600 * 1000;
		Calendar client_cal = Calendar.getInstance();
		client_cal.setTimeInMillis(Long.parseLong(ts));
		Calendar server_cal = Calendar.getInstance();
		return client_cal.get(Calendar.HOUR_OF_DAY) * 60
				+ client_cal.get(Calendar.MINUTE)
				- server_cal.get(Calendar.HOUR_OF_DAY) * 60
				- server_cal.get(Calendar.MINUTE) - 8 * 60;

	}

	/**
	 * 校验是否存在此用户
	 * 
	 * @param uid
	 * @return
	 */
	public boolean validateUser(String uid) {
		UserService userService = new UserService();
		boolean flag = true;
		if (null == uid || uid.equalsIgnoreCase("null")
				|| "".equalsIgnoreCase(uid)) {
			flag = false;
		} else {
			boolean exist = userService.isExist(Long.valueOf(uid));
			if (!exist) {
				logger.info("Does not exist UID: " + uid);
				flag = false;
			}
			logger.info("Find this UID:" + uid);
		}
		return flag;
	}

	/**
	 * 根据webAccount帐号获取邮箱帐号
	 * 
	 * @param account
	 * @return Clickoo_mail_account
	 */
	public Clickoo_mail_account getMailAccountByWebAccount(WebAccount account) {
		logger
				.info("int the WebAaccount conversion MailAccount =================》");
		Clickoo_mail_account mailaccount = new Clickoo_mail_account();
		String inCert = null;
		String inPath = null;
		String outCert = null;
		String outPath = null;
		String LoginID = null;
		String name = null;
		if (account.getName().contains(";")) {
			LoginID = account.getName().split(";")[1];
			logger.info(LoginID);
			name = account.getName().split(";")[0];
			logger.info(name);
		} else {
			LoginID = account.getName();
			name = account.getName();
		}
		// 接收的配置
		JSONObject inobj = new JSONObject();
		inobj.put(Constant.LOGINID, LoginID);
		
		if(account.getToken() != null && !"".equals(account.getToken().trim()) 
				&& account.getPtid() != null && !"".equals(account.getPtid().trim())) {
			inobj.put(Constant.TOKEN, account.getToken());
			inobj.put(Constant.PTID, account.getPtid());
		} else {
			//此处已是base64
			inobj.put(Constant.PWD, account.getPassword());
		}
		JSONObject inpathobj = new JSONObject();
		inpathobj.put(Constant.HOST, account.getReceiveHost());
		String receiveTs = "";
		if (account.getReceiveTs() != null) {
			receiveTs = account.getReceiveTs();
		}
		inpathobj.put(Constant.TYPE, receiveTs);
		inpathobj.put(Constant.PROTOCOLTYPE, account.getReceiveProtocolType());
		inpathobj.put(Constant.RECEIVEPORT, account.getReceivePort());
		inpathobj.put(Constant.COMPOSITOR, account.getCompositor());
		inpathobj.put(Constant.SUPPORTALLUID, account.getSupportalluid());
		inPath = inpathobj.toString();
		inCert = inobj.toString();
		// 发送的配置
		JSONObject outobj = new JSONObject();
		if (account.getProxyName() != null
				&& account.getProxyPassword() != null) {
			outobj.put(Constant.LOGINID, account.getProxyName());
			outobj.put(Constant.PWD, account.getProxyPassword());
		} else {
			outobj.put(Constant.LOGINID, LoginID);
			if(account.getToken() != null && account.getPtid() != null) {
				outobj.put(Constant.TOKEN, account.getToken());
				outobj.put(Constant.PTID, account.getPtid());
			} else {
				//此处已是base64
				outobj.put(Constant.PWD,account.getPassword());
			}
		}
		outobj.put(Constant.ALIAS, account.getAlias());
		JSONObject outpathobj = new JSONObject();
		outpathobj.put(Constant.SMTPSERVER, account.getSendHost());
		// outpathobj.put(Constant.HOST, account.getSendHost());
		String sendTs = "";
		if (account.getSendTs() != null) {
			sendTs = account.getSendTs();
		}
		outpathobj.put(Constant.TYPE, sendTs);
		outpathobj.put(Constant.SENDPORT, account.getSendPort());
		if (account.getSendProtocolType().equalsIgnoreCase(Constant.HTTP)) {
			outpathobj
					.put(Constant.PROTOCOLTYPE, account.getSendProtocolType());
		} else {
			outpathobj.put(Constant.PROTOCOLTYPE, Constant.SMTP);
			account.setSendProtocolType(Constant.SMTP);
		}
		outPath = outpathobj.toString();
		outCert = outobj.toString();

		// if (account.getReceiveHost().toLowerCase().contains(
		// Constant.POP3.toLowerCase())) {
		// account.setReceiveProtocolType(Constant.POP3);
		// } else if (account.getReceiveHost().toLowerCase().contains(
		// Constant.IMAP.toLowerCase())) {
		// account.setReceiveProtocolType(Constant.IMAP);
		// }
		String rpt = account.getReceiveProtocolType();
		account.setReceiveProtocolType(rpt);
		// mailaccount.setUid(Long.valueOf(account.getUid()));
		mailaccount.setName(name);
		mailaccount.setInCert(inCert);
		mailaccount.setInPath(inPath);
		mailaccount.setOutCert(outCert);
		mailaccount.setOutPath(outPath);
		// mailaccount.setStatus(2);
		return mailaccount;
	}

	/**
	 * 检验添加邮箱帐号MailAccount
	 * 
	 * @param flag
	 * @param mail_account
	 * @param status
	 * @return
	 */
	public List<Object> validateAddAccount(Clickoo_mail_account mail_account,
			WebAccount accountOut, Status status, long uid) {
		List<Object> objects = new ArrayList<Object>();
		AccountService accountDao = new AccountService();
		long aid = accountDao.createAccount(mail_account, uid);
		accountOut.setId(aid);
		if (aid != -1) {
			logger.info("E-mail Account Add successfully");
			status.setMessage("E-mail Account Add successfully");
			objects.add(status);
			objects.add(accountOut);
		} else {
			logger.info("Email account can not add ，" + mail_account.getName()
					+ "has already been used");
			status.setCode(ErrorCode.CODE_EmailAccount_Add);
			status.setMessage("E-mail Account Add failed!");
			objects.add(status);
			objects.add(accountOut);
		}
		return objects;
	}

	public boolean onlineValidateUpdataEmailAccount(WebAccount account)
			throws Exception {

		logger.info("start to validate account:" + account.getName() + ","
				+ account.getPassword());

		IMailValidate validate = new SendMailValidateImpl();
		if (account.getReceiveProtocolType().equalsIgnoreCase(Constant.IMAP)) {
			logger.info("ReceiveProtocolType is imap");
			validate = new ImapMailValidateImpl();
		} else if (account.getReceiveProtocolType().equalsIgnoreCase(
				Constant.POP3)) {
			logger.info("ReceiveProtocolType is pop3");
			validate = new Pop3MailValidateImpl();
		} else if (account.getReceiveProtocolType().equalsIgnoreCase(
				Constant.HTTP)) {
			logger.info("ReceiveProtocolType is http");
			validate = new EWSMailValidateImpl();
			logger.info("Start to validate account for http...");
			if (validate.validate(account)) {
				logger.info("success to validate account for http...");
				return true;
			} else {
				logger.info("failed to validate account for http...");
				return false;
			}
		} else {
			logger.error("there is no such receive protocol type: "
					+ account.getReceiveProtocolType());
			return false;
		}

		logger.info("E-mail account to receive messages on the check");
		boolean flag = validate.validate(account);
		if (flag) {
			logger.info("Receive e-mail verification passed");
		} else {
			logger.info("Receive e-mail verification failed");
			return false;
		}

		if (flag) {
			logger.info("E-mail account to send messages on the check");
			validate = new SendMailValidateImpl();
			flag = validate.validate(account);
			if (flag) {
				logger.info("Send e-mail verification passed");
			}
		}

		// 测试代理发送
		if (!flag) {
			flag = validateEmailAccountProxySend(account, validate);
		}
		return flag;
	}

	public boolean validateAccountName(WebAccount account) {
		if (account.getName() != null
				&& account.getName().trim().split("@").length == 2) {
			return true;
		}
		return false;
	}

	public boolean validateAccountServerAttribute(WebAccount account)
			throws TimeoutException, UnsupportedEncodingException,
			MessagingException {
		account.setPassword(new String(EmailUtils.base64TochangeByte(account
				.getPassword())));
		boolean flag = validateReceiveConfig(account);
		if (flag) {
			logger.info("Receive e-mail verification passed");
			logger.info("E-mail account to send messages on the check");
			flag = new SendMailValidateImpl().validate(account);
			if (flag)
				logger.info("Send e-mail verification passed");
		}
		return flag;
	}

	private boolean validateReceiveConfig(WebAccount accountIn)
			throws MessagingException, TimeoutException,
			UnsupportedEncodingException {
		boolean flag = false;
		IMailValidate validate = null;
		logger.info("E-mail account to receive messages on the check");
		logger.info("validateAccountServerAttribute ReceiveProtocolType is "
				+ accountIn.getReceiveProtocolType());
		if (accountIn.getReceiveProtocolType().equalsIgnoreCase(Constant.IMAP)) {
			logger.info("ReceiveProtocolType is imap");
			validate = new ImapMailValidateImpl();
		} else if (accountIn.getReceiveProtocolType().equalsIgnoreCase(
				Constant.POP3)) {
			logger.info("ReceiveProtocolType is pop3");
			validate = new Pop3MailValidateImpl();
		} else if (accountIn.getReceiveProtocolType().equalsIgnoreCase(
				Constant.HTTP)) {
			validate = new EWSMailValidateImpl();
		} else {
			logger.error("there is no such receive protocol type: "
					+ accountIn.getReceiveProtocolType());
			return false;
		}

		flag = validate.validate(accountIn);
		return flag;
	}

	public long onlineValidateEmailAccount(WebAccount accountIn,
			WebAccount accountOut) throws Exception {
		String[] names = accountIn.getName().split("@");
		if (null == accountIn.getReceiveHost()
				&& null == accountIn.getSendHost()) {
			return validatePublicAccount(accountIn, accountOut, names[1]);
		} else {
			return validateEnterpriseAccount(accountIn, accountOut, names[1]);
		}
	}

	private long validateEnterpriseAccount(WebAccount accountIn,
			WebAccount accountOut, String domain) throws TimeoutException,
			UnsupportedEncodingException, MessagingException {
		if (validateAccountServerAttribute(accountIn)) {
			String[] inpathList = new String[1];
			inpathList[0] = accountIn.getReceiveHost();
			accountIn.setInpathList(inpathList);
			if (accountIn.getReceiveProtocolType().equalsIgnoreCase(
					Constant.IMAP)) {
				ISupport iSupport = new ImapSupport();
				accountIn = iSupport.isSupportIDLE(accountIn);
			}
			if (accountIn.getReceiveProtocolType().equalsIgnoreCase(
					Constant.POP3)) {
				ISupport iSupport = new Pop3Support();
				accountIn = iSupport.getAllSupport(accountIn);
			}
			accountIn.setPassword(EmailUtils.changeByteToBase64(accountIn.getPassword().getBytes()));
			logger.info("account password:::"+accountIn.getPassword());
			if (saveServerInformation(accountIn, domain)) {
				return 0;
			} else {
				return -1;
			}
		} else {
			return 1;
		}
	}

	private long validatePublicAccount(WebAccount accountIn,
			WebAccount accountOut, String domain) throws Exception {
		boolean succ = false;
		Clickoo_mail_receive_server receiveservers = ReceiveServerCacheManager
				.getCacheObject(domain);
		Clickoo_mail_send_server sendservers = SendServerCacheManager
				.getCacheObject(domain);
		if (receiveservers == null && sendservers == null) {
			accountOut.setReceiveHost("pop." + domain);
			accountOut.setReceivePort("110");
			accountOut.setSendHost("smtp." + domain);
			accountOut.setSendPort("25");
			return 1;
		}
		succ = validateEmailAccountServerAttribute(accountIn,
				loadReceiveConfig(accountIn, receiveservers), loadSendConfig(
						accountIn, sendservers));
		if (!succ) {
			succ = validateJamesEmailAccountServerAttribute(accountIn,
					accountOut, loadJamesReceiveConfig(accountIn,
							receiveservers), loadJamesSendConfig(accountIn,
							sendservers));
		}
		if (succ) {
			accountOut.setReceiveHost(accountIn.getReceiveHost());
			accountOut.setReceivePort(accountIn.getReceivePort());
			accountOut.setSendHost(accountIn.getSendHost());
			accountOut.setSendPort(accountIn.getSendPort());
			return 0;
		} else {
			return -1;
		}
	}

//	private boolean isNotPublicConfig(
//			Clickoo_mail_receive_server receiveservers,
//			Clickoo_mail_send_server sendservers) {
//		return 0 == receiveservers.size() && 0 == sendservers.size();
//	}

	private boolean saveServerInformation(WebAccount account, String name) {
		boolean succ = false;
		ReceiveServerDao receiveServerDao = new ReceiveServerDao();
		SendServerDao sendServerDao = new SendServerDao();
		Clickoo_mail_receive_server receiveServer = new Clickoo_mail_receive_server();
		Clickoo_mail_send_server sendServer = new Clickoo_mail_send_server();
		JSONObject inpathobj = new JSONObject();
		inpathobj.put(Constant.HOST, account.getReceiveHost());
		inpathobj.put(Constant.TYPE, account.getReceiveTs());
		inpathobj.put(Constant.PROTOCOLTYPE, account.getReceiveProtocolType());
		inpathobj.put(Constant.RECEIVEPORT, account.getReceivePort());
		if (account.getCompositor() != null) {
			inpathobj.put(Constant.COMPOSITOR, account.getCompositor());
		}
		if (account.getSupportalluid() != null) {
			inpathobj.put(Constant.SUPPORTALLUID, account.getSupportalluid());
		}
		receiveServer.setName(name);
		receiveServer.setInPath(inpathobj.toString());
		receiveServer.setStatus(1);
		int defaultIntervalTimer = Integer.valueOf(PProperties
				.getConfigureMap().get("defaultIntervalTimer"));
		receiveServer.setIntervaltime(defaultIntervalTimer);
		receiveServer.setRetrytime(0);
		JSONObject outpathobj = new JSONObject();
		outpathobj.put(Constant.SMTPSERVER, account.getSendHost());
		String sendTs = "";
		if (account.getSendTs() != null) {
			sendTs = account.getSendTs();
		}
		outpathobj.put(Constant.TYPE, sendTs);
		outpathobj.put(Constant.SENDPORT, account.getSendPort());
		sendServer.setName(name);
		sendServer.setOutPath(outpathobj.toString());
		sendServer.setStatus(0);

		if (receiveServerDao.createReceiveServer(receiveServer)
				&& sendServerDao.createSendServer(sendServer)) {
			ReceiveServerCacheManager
					.addReceiveServerCacheObject(receiveServer);
			SendServerCacheManager.addSendServerCacheObject(sendServer);
			JmsSender.sendTopicMsg(Constant.CLICKOO_MAIL_SERVER, "cacheUpdate");
			succ = true;
		}
		return succ;
	}

	public boolean validateUserIMEI(User_role ur, String IMEI) {
		boolean flag = false;
		if (ur != null) {
			Clickoo_user user = ur.getUser();
			if (user != null) {
				if (IMEI.equalsIgnoreCase(user.getIMEI())) {
					logger.info("Find this UID:" + user.getUid());
					flag = true;
				} else {
					logger.info("user changer IMEI");
					flag = false;
				}
			}
		} else {
			logger.info("user is null");
		}
		return flag;
	}

	@Override
	public boolean validateUserRole(User_role ur) {
		boolean flag = true;
		if (ur != null) {
			Clickoo_role role = ur.getRole();
			if (Constant.DISABLE.equalsIgnoreCase(role.getTitle())) {
				flag = false;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 检验添用户邮箱数量
	 * 
	 * @param uid
	 * @return boolean false over
	 */
	public boolean validateUserEmailAccount(User_role user_role,long uid) {
		boolean flag = false;
		int level = user_role.getUser().getRolelevel();
		String funcation = RoleCacheManager.getRoleByLevel(level).getFunction();
		int currentEmailCount = user_role.getUser().getEmailcount();
		int emailCount = Integer.parseInt(JsonUtil.getInstance()
				.getJsonValueByKey(funcation, Constant.ACCOUNT_NUMBER));
		if (currentEmailCount <= emailCount) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 检验邮箱是否为企业邮
	 * @param uid
	 * @return boolean false 
	 */
	public boolean validateBusinessEmail(WebAccount account,User_role user_role) {
		boolean isBusinessEmail = false,flag = true;
		String accountServerName = account.getName();
		Clickoo_mail_receive_server receiveServer = ReceiveServerCacheManager.getCacheObject(accountServerName);
		if(receiveServer != null){
			if("1".equals(receiveServer.getStatus())){
				isBusinessEmail = true;
			}
		}
		else{
			isBusinessEmail = true;
		}
		if(isBusinessEmail){
			Clickoo_role role = user_role.getRole();
			if (Constant.USER_ROLE_FREE.equalsIgnoreCase(role.getTitle())) {
				flag = false;
			}
		}
		return flag;
	}
}
