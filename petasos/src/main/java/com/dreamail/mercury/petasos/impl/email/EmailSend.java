package com.dreamail.mercury.petasos.impl.email;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.cache.VolumeCacheManager;
import com.dreamail.mercury.cache.domain.VolumeCacheObject;
import com.dreamail.mercury.dal.dao.AttachmentDao;
import com.dreamail.mercury.dal.dao.SendMessageDao;
import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.dal.service.VolumeService;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailattachment;
import com.dreamail.mercury.domain.WebEmailhead;
import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.impl.AbstractFunctionEmail;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.pojo.Clickoo_send_message;
import com.dreamail.mercury.pojo.Clickoo_volume;
import com.dreamail.mercury.pojo.InCert;
import com.dreamail.mercury.pojo.InPath;
import com.dreamail.mercury.pojo.OutCert;
import com.dreamail.mercury.pojo.User_role;
import com.dreamail.mercury.sender.auth.AuthUser;
import com.dreamail.mercury.sender.operator.SendOperator;
import com.dreamail.mercury.store.Volume;
import com.dreamail.mercury.store.Volumes;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.MailBoxDispatcher;
import com.dreamail.mercury.util.MethodName;
import com.dreamail.mercury.util.StreamUtil;
import com.dreamail.mercury.util.WebAccountUtil;

public class EmailSend extends AbstractFunctionEmail implements IEmailSend {

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(EmailSend.class);

	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		Status status = new Status();
		HashMap<String, String> meta = qwertCmd.getMeta();
		Cred cred = null;
		String uid = null;
		if (pushMail.getCred() != null) {
			cred = pushMail.getCred();
		}
		if (cred != null) {
			uid = cred.getUuid();
		}
		// 判断用户绑定的IMEI是否和数据库匹配。
		String IMEI = (String) meta.get("IMEI");
		UserService userService = new UserService();
		User_role ur = userService.getUserRoleById(Long.parseLong(uid));
		if (!validateUserRole(ur)) {
			status.setCode(ErrorCode.CODE_USER_ROLE_FAIL);
			status.setMessage("User has been Disabled");
			Object[] objectss = new Object[1];
			objectss[0] = status;
			qwertCmd.setObjects(new Status[] { (Status) objectss[0] });
			return qwertCmd;
		}
		if (!validateUserIMEI(ur, IMEI)) {
			status.setCode(ErrorCode.CODE_USER_IMEI_FAIL);
			status.setMessage("the user has been offline");
			Object[] objectss = new Object[1];
			objectss[0] = status;
			qwertCmd.setObjects(new Status[] { (Status) objectss[0] });
			return qwertCmd;
		} else {
			meta.remove("IMEI");
		}
		Object[] emails = qwertCmd.getObjects();
		status.setCode("0");
		status.setMessage("success");
		List<Object> objects = null;
		ArrayList<WebEmail> wemails = new ArrayList<WebEmail>();
		WebEmail wemail = null;
		WebAccount account = null;
		for (int i = 0; i < emails.length; i++) {
			if (emails[i] instanceof WebAccount) {
				account = (WebAccount) emails[i];
				continue;
			}
			if (emails[i] instanceof WebEmail) {
				wemail = (WebEmail) emails[i];
				//自动下载附件内容
				autoDownloadAtt(wemail);
				wemails.add(wemail);
			}
		}
		// 查询account帐号用于以后下载失效的附件和正文，用于发送邮件
		AccountService accountService = new AccountService();
		Clickoo_mail_account mail_account = accountService
				.getAccountByAid(account.getId());
		if (null == mail_account) {
			status.setCode(ErrorCode.CODE_EmailAccount_NoAct);
			status.setMessage("This email-account does not exist!");
			Object[] objs = new Object[1];
			objs[0] = status;
			qwertCmd.setObjects(new Status[] { (Status) objs[0] });
			pushMail.setCred(null);
			return qwertCmd;
		}
		String name = mail_account.getName();
		InPath inpath = mail_account.getInPath_obj();
		InCert incert = mail_account.getInCert_obj();
		OutCert outcert = mail_account.getOutCert_obj();
		String sendName = incert.getLoginID();
		String receiveName = outcert.getLoginID();
		if (!sendName.equals(receiveName)) {
			account.setProxyPassword(outcert.getPwd());
			account.setProxyName(outcert.getLoginID());
		}
		account.setPassword(incert.getPwd());
		account = WebAccountUtil.getAccountByContext(mail_account, account);
		account.setName(name);
		String host = inpath.getInhost();
		String[] inpathList = host.split(",");
		account.setInpathList(inpathList);

		// 调用发送邮件的方法，返回状态
		objects = mailSend(account, wemails, cred, mail_account, meta);

		Object[] objs = objects.toArray();
		qwertCmd.setObjects(objs);
		pushMail.setCred(null);
		return qwertCmd;

	}
	
	/**
	 * 自动下载没有body的附件.
	 * 
	 * @param email
	 */
	private void autoDownloadAtt(WebEmail email) {
		if (email != null && email.getAttach() != null
				&& email.getAttach().length > 0) {
			logger.info("start handle atts...");
			for (WebEmailattachment att : email.getAttach()) {
				if (att.getAttid() != null && att.getBody() == null) {
					logger.info("load att from database for att:"
							+ att.getAttid());
					Clickoo_message_attachment cma = new AttachmentDao()
							.selectAttachmentById(Long.valueOf(att.getAttid()));
					if (cma != null && cma.getVolume() != null) {
						att.setAttid(String.valueOf(cma.getId()));
						att.setName(EmailUtils.changeByteToBase64(cma.getName().getBytes()));
						att.setSize(cma.getLength());
						att.setType(cma.getType());
						att.setBody(EmailUtils.changeByteToBase64(StreamUtil
								.readFile(cma.getVolume().getPath()
										+ cma.getPath())));
					} else {
						logger.error("cann't get this att...");
					}
				} else {
					logger.info("att for name " + att.getName() + " is ok...");
				}
			}
		}
	}

	@Override
	public String getMethodName() {
		return MethodName.EMAIL_SEND;
	}

	/**
	 * 发送邮件的入口
	 * 
	 * @param account
	 * @param wemails
	 * @param statuss
	 * @param cred
	 * @return
	 */
	public List<Object> mailSend(WebAccount account, List<WebEmail> wemails,
			Cred cred, Clickoo_mail_account mail_account,
			HashMap<String, String> meta) {
		List<Object> objects = new ArrayList<Object>();
		Status s = new Status();
		for (int i = 0; i < wemails.size(); i++) {
			WebEmail email = wemails.get(i);
			WebEmailhead head = email.getHead();
			String mid = head.getMessageId();
			if (head.getMessageId() != null) {
				// 转发收件箱的邮件
				if (null != head.getForward() && "1".equals(head.getForward())) {
					logger
							.info("into .................... inBoxForWord function");
					MessageService messageService = new MessageService();
					Clickoo_message message = messageService
							.selectMessageById(Long.parseLong(mid));
					inBoxForWord(message, account, email, cred, mail_account,
							s, objects);
				} else if (null != head.getForward()
						&& "2".equals(head.getForward())) {
					// 转发发件箱的邮件
					logger
							.info("into .................... sendBoxForWord function");
					SendMessageDao sendmessageDao = new SendMessageDao();
					Clickoo_send_message sendmessage = sendmessageDao
							.selectSendMessageById(Long.parseLong(mid));
					sendBoxForWord(sendmessage, account, email, cred,
							mail_account, s, objects, meta);
				} else if (head.getForward() != null
						&& head.getForward().equals("0")) {
					// 回复收件箱的邮件
					logger.info("into .................... replyMail function");
					replyMail(email, account, mail_account, cred, objects);
				}
			} else {
				// 发送普通邮件
				logger.info("into .................... sendMail function");
				sendMail(email, account, mail_account, cred, objects);
			}
		}
		return objects;
	}

	/**
	 * 使用代理账号发送邮件
	 * 
	 * @param email
	 *            web email
	 * @return boolean
	 * @throws Exception
	 *             fail
	 */
	// private boolean sendEmailUseProxy(WebEmail email, String uid)
	// throws Exception {
	// if (email.getAccount() == null) {
	// email.setAccount(new WebAccount());
	// }
	// email.getAccount().setProxyName(
	// PropertiesDeploy.getConfigureMap().get("proxyName"));
	// email.getAccount().setProxyPassword(
	// PropertiesDeploy.getConfigureMap().get("proxyPassword"));
	// email.getAccount().setSendHost(
	// PropertiesDeploy.getConfigureMap().get("proxySmtpServer"));
	// email.getAccount().setSendPort(
	// PropertiesDeploy.getConfigureMap().get("proxySendPort"));
	// email.getAccount().setSendTs(
	// PropertiesDeploy.getConfigureMap().get("proxyType"));
	// return new SendOperator().sendMail(email, uid);
	// }

	@Override
	public void inBoxForWord(Clickoo_message message, WebAccount account,
			WebEmail email, Cred cred, Clickoo_mail_account mail_account,
			Status status, List<Object> objects) {
		String uid = cred.getUuid();
		long mid = 0;
		String oldmid = email.getHead().getMessageId();
		try {
			tranFromHeadAndAttach(email, account, mail_account, status, objects);
			Session session = null;
			Properties props = getProperties(account);
			session = Session.getInstance(props, new AuthUser(
					account.getName(), account.getPassword()));
			logger.info("userinfo:" + account.getName() + "---"
					+ account.getPassword());
			mid = saveEmail(email, cred.getUuid());
			email.getHead().setMessageId(String.valueOf(mid));
			WebEmail edEmail = new SendOperator().EDcryptWebEmail(email);
			Message edmsg = createMsg(edEmail, cred.getUuid(), session);
			String path = saveToEml(edEmail, edmsg, mid);
			email.setPath(path);
			updateEmail(email, cred.getUuid());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		sendJMSMessage(uid, String.valueOf(mid), Constant.INBOX_FORWORD,
				oldmid, email.getHead().getEmailHeadID(), account.getName());
		logger.info("Send e-mail success");
		status.setCode("0");
		status.setMessage("Send e-mail success");
		objects.add(status);
		WebEmail mail = new WebEmail();
		WebEmailhead mailhead = new WebEmailhead();
		mailhead.setEmailHeadID(email.getHead().getEmailHeadID());
		String messageid = String.valueOf(mid);
		mailhead.setMessageId(messageid);
		mail.setHead(mailhead);
		objects.add(mail);
	}

	@Override
	public void replyMail(WebEmail email, WebAccount account,
			Clickoo_mail_account mail_account, Cred cred, List<Object> objects) {
		String uid = cred.getUuid();
		Status status = new Status();
		long mid = 0;
		String oldmid = email.getHead().getMessageId();
		try {
			tranFromHeadAndAttach(email, account, mail_account, status, objects);
			// Message msg = null;
			Session session = null;
			// Transport transport = null;
			Properties props = getProperties(account);
			session = Session.getInstance(props, new AuthUser(
					account.getName(), account.getPassword()));
			logger.info("userinfo:" + account.getName() + "---"
					+ account.getPassword());
			mid = saveEmail(email, cred.getUuid());
			email.getHead().setMessageId(String.valueOf(mid));
			WebEmail edEmail = new SendOperator().EDcryptWebEmail(email);
			Message edmsg = createMsg(edEmail, cred.getUuid(), session);
			String path = saveToEml(edEmail, edmsg, mid);
			email.setPath(path);
			updateEmail(email, cred.getUuid());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		sendJMSMessage(uid, String.valueOf(mid), Constant.REPLAY_MAIL, oldmid,
				email.getHead().getEmailHeadID(), account.getName());
		logger.info("Send e-mail success");
		status.setCode("0");
		status.setMessage("Send e-mail success");
		objects.add(status);
		WebEmail mail = new WebEmail();
		WebEmailhead mailhead = new WebEmailhead();
		mailhead.setEmailHeadID(email.getHead().getEmailHeadID());
		String messageid = String.valueOf(mid);
		mailhead.setMessageId(messageid);
		mail.setHead(mailhead);
		objects.add(mail);
	}

	@Override
	public void sendBoxForWord(Clickoo_send_message message,
			WebAccount account, WebEmail email, Cred cred,
			Clickoo_mail_account mail_account, Status status,
			List<Object> objects, HashMap<String, String> meta) {
		String uid = cred.getUuid();
		String state = meta.get("State");
		long mid = 0;
		String oldmid = email.getHead().getMessageId();
		Clickoo_volume volume = new VolumeService().getVolumeById(message
				.getVolumeid());
		try {
			tranFromHeadAndAttach(email, account, mail_account, status, objects);
			Session session = null;
			Properties props = getProperties(account);
			session = Session.getInstance(props, new AuthUser(
					account.getName(), account.getPassword()));
			logger.info("userinfo:" + account.getName() + "---"
					+ account.getPassword());
			if (state.equals("0")) {
				mid = saveEmail(email, cred.getUuid());
				email.getHead().setMessageId(String.valueOf(mid));
				WebEmail edEmail = new SendOperator().EDcryptWebEmail(email);
				Message edmsg = createMsg(edEmail, cred.getUuid(), session);
				String path = saveToEml(edEmail, edmsg, mid);
				email.setPath(path);
				updateEmail(email, cred.getUuid());
			} else if (state.equals("1")) {
				mid = Long.parseLong(meta.get("MessageNewId"));
				WebEmail edEmail = new SendOperator().EDcryptWebEmail(email);
				Message edmsg = createMsg(edEmail, cred.getUuid(), session);
				updateToEml(email, edmsg, message, volume);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		if (state.equals("0")) {
			String filepath = null;
			if (!message.getPath().equals("") && message.getPath() != null) {
				StringBuffer path = new StringBuffer();
				path.append(message.getPath());
				path.append(File.separator);
				path.append(oldmid);
				path.append(".eml");
				filepath = volume.getPath() + path.toString();
			}
			if (filepath == null) {
				logger.error("filepath is null.");
			} else if (!new File(filepath).exists()) {
				meta.put("State", "0");
				status.setCode("0");
				status.setMessage("Send e-mail success");
				objects.add(status);
				WebEmail mail = new WebEmail();
				WebEmailhead mailhead = new WebEmailhead();
				mailhead.setEmailHeadID(email.getHead().getEmailHeadID());
				mailhead.setMessageId_old(oldmid);
				mailhead.setMessageId_new(String.valueOf(mid));
				mail.setHead(mailhead);
				objects.add(mail);
				return;
			}
		}
		sendJMSMessage(uid, String.valueOf(mid), Constant.SENDBOX_FORWORD,
				oldmid, email.getHead().getEmailHeadID(), account.getName());
		logger.info("Send e-mail success");
		meta.put("State", "1");
		status.setCode("0");
		status.setMessage("Send e-mail success");
		objects.add(status);
		WebEmail mail = new WebEmail();
		WebEmailhead mailhead = new WebEmailhead();
		mailhead.setEmailHeadID(email.getHead().getEmailHeadID());
		String messageid = String.valueOf(mid);
		mailhead.setMessageId(messageid);
		mail.setHead(mailhead);
		objects.add(mail);
	}

	private void updateToEml(WebEmail email, Message msg,
			Clickoo_send_message pojo, Clickoo_volume volume) {
		StringBuffer emlPath = new StringBuffer(volume.getPath());
		emlPath.append(File.separator);
		emlPath.append(pojo.getPath());
		emlPath.append(File.separator);
		emlPath.append(pojo.getId());
		emlPath.append(".eml");
		File f = new File(emlPath.toString());
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f);
			msg.writeTo(out);
		} catch (FileNotFoundException e) {
			logger.error("file is not found");
		} catch (IOException e) {
			logger.error("write message to eml fail");
		} catch (MessagingException e) {
			logger.error("", e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void sendMail(WebEmail email, WebAccount account,
			Clickoo_mail_account mail_account, Cred cred, List<Object> objects) {

		String uid = cred.getUuid();
		Status status = new Status();
		long mid = 0;
		try {
			tranFromHeadAndAttach(email, account, mail_account, status, objects);
			Session session = null;
			Properties props = getProperties(account);
			session = Session.getInstance(props, new AuthUser(
					account.getName(), account.getPassword()));
			logger.info("userinfo:" + account.getName() + "---"
					+ account.getPassword());
			mid = saveEmail(email, cred.getUuid());
			email.getHead().setMessageId(String.valueOf(mid));
			WebEmail edEmail = new SendOperator().EDcryptWebEmail(email);
			Message edmsg = createMsg(edEmail, cred.getUuid(), session);
			String path = saveToEml(edEmail, edmsg, mid);
			email.setPath(path);
			updateEmail(email, cred.getUuid());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		sendJMSMessage(uid, String.valueOf(mid), Constant.COMMON_MAIL, "",
				email.getHead().getEmailHeadID(), account.getName());
		logger.info("Send e-mail success");
		status.setCode("0");
		status.setMessage("Send e-mail success");
		objects.add(status);
		WebEmail mail = new WebEmail();
		WebEmailhead mailhead = new WebEmailhead();
		mailhead.setEmailHeadID(email.getHead().getEmailHeadID());
		String messageid = String.valueOf(mid);
		mailhead.setMessageId(messageid);
		mail.setHead(mailhead);
		objects.add(mail);

		// Status status = new Status();
		// boolean state = false;
		// long mid = 0;
		// try {
		// boolean flag = tranFromHeadAndAttach(email, account, mail_account,
		// status, objects);
		// addUserSignature(email, cred.getUuid());
		// // 如果自己发给自己且不是http账号类型，则使用代理账号发送
		// if ((email.getAccount().getName().trim().equals(
		// email.getHead().getTo().trim()) && !account
		// .getSendProtocolType().equalsIgnoreCase(Constant.HTTP))
		// || flag) {
		// logger
		// .info("use proxy to send because this email is to userself");
		// state = sendEmailUseProxy(email, cred.getUuid());
		// } else {
		// if (email.getAccount().getSendProtocolType().equalsIgnoreCase(
		// Constant.SMTP)) {
		// logger.info("use smtp protocol to send email...");
		// state = new SendOperator().sendMail(email, cred.getUuid());
		// } else if (email.getAccount().getSendProtocolType()
		// .equalsIgnoreCase(Constant.HTTP)) {
		// logger.info("use http protocol to send email...");
		// state = new EWSSendClient()
		// .sendEmail(email, cred.getUuid());
		// }
		// }
		//
		// } catch (Exception e) {
		// logger.warn("failed to send email"
		// + "by itself username and password", e);
		// try {
		// state = sendEmailUseProxy(email, cred.getUuid());
		// } catch (Exception e1) {
		// logger.error("Send e-mail fail "
		// + "use itself username and password", e);
		// logger.error("Send e-mail fail "
		// + "use proxy username and password", e1);
		// status.setCode(ErrorCode.CODE_SendEmail_FAIL);
		// status.setMessage("Send e-mail fail");
		// objects.add(status);
		// WebEmail mail = new WebEmail();
		// WebEmailhead mailhead = new WebEmailhead();
		// mailhead.setEmailHeadID(email.getHead().getEmailHeadID());
		// String messageid = email.getHead().getMessageId();
		// mailhead.setMessageId(messageid);
		// mail.setHead(mailhead);
		// objects.add(mail);
		// }
		// }
		//
		// if (state) {
		//
		// /*
		// * 暂时不处理http方式发布的邮件
		// */
		// if (!Constant.HTTP.equals(account.getSendProtocolType())) {
		// Session session = null;
		// Properties props = getProperties(account);
		// if (account.getProxyName() != null
		// && account.getProxyPassword() != null) {
		// session = Session.getInstance(props, new AuthUser(account
		// .getProxyName(), account.getProxyPassword()));
		// logger.info("proxy userinfo:" + account.getProxyName()
		// + "---" + account.getProxyPassword());
		// } else {
		// session = Session.getInstance(props, new AuthUser(account
		// .getName(), account.getPassword()));
		// logger.info("userinfo:" + account.getName() + "---"
		// + account.getPassword());
		// }
		// try {
		// mid = saveEmail(email, cred.getUuid());
		// email.getHead().setMessageId(String.valueOf(mid));
		// WebEmail edEmail = new SendOperator()
		// .EDcryptWebEmail(email);
		// Message edmsg = createMsg(edEmail, cred.getUuid(), session);
		// String path = saveToEml(edEmail, edmsg, mid);
		// email.setPath(path);
		// updateEmail(email, cred.getUuid());
		// ZipUtil.ZIPFile(path, String.valueOf(mid));
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// } catch (MessagingException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		//
		// logger.info("Send e-mail success");
		// status.setCode("0");
		// status.setMessage("Send e-mail success");
		// objects.add(status);
		// WebEmail mail = new WebEmail();
		// WebEmailhead mailhead = new WebEmailhead();
		// mailhead.setEmailHeadID(email.getHead().getEmailHeadID());
		// String messageid = String.valueOf(mid);
		// mailhead.setMessageId(messageid);
		// mail.setHead(mailhead);
		// objects.add(mail);
		// } else {
		// logger.error("Send e-mail fail "
		// + "use itself username and password");
		// status.setCode(ErrorCode.CODE_SendEmail_FAIL);
		// status.setMessage("Send e-mail fail");
		// objects.add(status);
		// WebEmail mail = new WebEmail();
		// WebEmailhead mailhead = new WebEmailhead();
		// mailhead.setEmailHeadID(email.getHead().getEmailHeadID());
		// mail.setHead(mailhead);
		// objects.add(mail);
		// }

	}

	private boolean tranFromHeadAndAttach(WebEmail email, WebAccount account,
			Clickoo_mail_account mail_account, Status status,
			List<Object> objects) throws UnsupportedEncodingException {
		boolean succ = false;
		email.setAccount(account);
		if (email.getHead() != null) {

			if (email.getAttach() != null) {
				for (int k = 0; k < email.getAttach().length; k++) {
					if (email.getAttach()[k].getBody() != null) {
						byte[] b = EmailUtils.sunChangeBase64ToByte(email
								.getAttach()[k].getBody());
						email.getAttach()[k].setBody(EmailUtils
								.changeByteToBase64(b));
					}
					if (email.getAttach()[k].getName() != null) {
						byte[] b = EmailUtils.base64TochangeByte(email
								.getAttach()[k].getName());
						try {
							email.getAttach()[k]
									.setName(new String(b, "UTF-8"));
						} catch (UnsupportedEncodingException e) {
							logger.error("attach name base64 error", e);
						}
					}
				}
			}

			String str = email.getHead().getSubject();
			if (str != null) {
				byte[] b = EmailUtils.sunChangeBase64ToByte(str);
				try {
					email.getHead().setSubject(new String(b, "UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String content = email.getBody().getData();
			if (content != null && !"".equals(content)) {
				byte[] b = EmailUtils.sunChangeBase64ToByte(content);
				try {
					logger
							.info("content is ------------------------------------"
									+ new String(b, "UTF-8"));
					email.getBody().setData(new String(b, "UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.error("Send e-mail are not subject");
			status.setCode(ErrorCode.CODE_EmailAccount_Validate);
			status.setMessage("Send e-mail are not subject");
		}
		return succ;
	}

	private void updateEmail(WebEmail email, String uid) {
		try {
			Clickoo_send_message message = transMessage(email, uid);
			message.setId(Long.parseLong(email.getHead().getMessageId()));
			logger.info("message id :" + message.getId());
			// 保存message信息和消息体以及attachList信息
			SendMessageDao dao = new SendMessageDao();
			boolean succ = dao.updateSendMessage(message);
			if (succ) {
				logger.info("success to update email path");
			} else {
				logger.error("failed to update email path");
			}
		} catch (Exception e) {
			logger.error("failed to update email path", e);
		}

	}

	/**
	 * 得到account的配置属性.
	 * 
	 * @param account
	 * @return
	 */
	@SuppressWarnings("restriction")
	private Properties getProperties(WebAccount account) {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", account.getSendHost());
		props.setProperty("mail.smtp.port", account.getSendPort());
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.connectiontimeout", "30000");

		// 判断是否为hotmail邮箱
		if (account.getProxyName() == null
				&& (account.getName().indexOf("@hotmail") != -1 || account
						.getName().indexOf("@live") != -1)) {
			logger.info(account.getName() + " is a hotmail emailbox");
			props
					.setProperty("mail.smtp.socketFactory.class",
							"com.dreamail.mercury.sender.hotmail.HotmailSSLSocketFactory");
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.socketFactory.port", "587");
			props.setProperty("mail.smtp.starttls.enable", "true");
			return props;
		}

		if ("ssl".equals(account.getSendTs())) {
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			props.setProperty("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.timeout", "50000");
			props.setProperty("mail.smtp.socketFactory.port", account
					.getSendPort());
		} else if ("tls".equals(account.getSendTs())) {
			props.setProperty("mail.imap.starttls.enable", "true");
		}
		return props;
	}

	/**
	 * 创建邮件.
	 * 
	 * @param email
	 * @param session
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	private MimeMessage createMsg(WebEmail email, String uid, Session session)
			throws MessagingException, UnsupportedEncodingException {

		MimeMessage msg = new MimeMessage(session);
		/** Set the FROM,TO,CC,BCC and subject fields */
		msg.setFrom(tranFromAddr(email));
		msg.setSentDate(new Date());
		if (email.getHead().getTo() != null
				&& !"".equals(email.getHead().getTo().trim())) {
			msg.setRecipients(Message.RecipientType.TO, tranAddr(email
					.getHead().getTo().trim()));
		}
		if (email.getHead().getCc() != null
				&& !"".equals(email.getHead().getCc().trim())) {
			msg.setRecipients(Message.RecipientType.CC, tranAddr(email
					.getHead().getCc().trim()));
		}
		if (email.getHead().getBcc() != null
				&& !"".equals(email.getHead().getBcc().trim())) {
			msg.setRecipients(Message.RecipientType.BCC, tranAddr(email
					.getHead().getBcc().trim()));
		}
		String subject = email.getHead().getSubject();
		if (subject != null) {
			if (subject.length() > 250) {
				subject = subject.substring(0, 245) + "...";
			}
		}
		msg.setSubject(subject, "UTF-8");

		if (email.getAttach() == null || email.getAttach().length < 1) {
			msg = createMsg(msg, uid, email);
		} else {
			msg = createMsgAttach(msg, uid, email);
		}
		msg.saveChanges();
		return msg;
	}

	/**
	 * 解析地址.
	 * 
	 * @param addr
	 * @return
	 */
	private InternetAddress[] tranAddr(String addr) {
		String[] tempAddr = addr.trim().replaceAll("\\s+", ";").replaceAll(
				",+", ";").split(";+");
		InternetAddress address[] = new InternetAddress[tempAddr.length];
		for (int i = 0; i < tempAddr.length; i++) {
			address[i] = new InternetAddress();
			address[i].setAddress(tempAddr[i].trim());
		}
		return address;
	}

	/**
	 * 解析收件人地址.
	 * 
	 * @param email
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private InternetAddress tranFromAddr(WebEmail email)
			throws UnsupportedEncodingException {
		InternetAddress address = new InternetAddress();
		address.setAddress(email.getHead().getFrom().trim());
		if (email.getAccount().getAlias() != null
				&& !"".equals(email.getAccount().getAlias())) {
			email.getAccount().setAlias(
					email.getAccount().getAlias().replaceAll(";", " "));
			address.setPersonal(email.getAccount().getAlias().trim());
		}
		return address;
	}

	/**
	 * 创建有附件的邮件
	 * 
	 * @param msg
	 * @param email
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	private MimeMessage createMsgAttach(MimeMessage msg, String uid,
			WebEmail email) throws UnsupportedEncodingException,
			MessagingException {
		byte[] attach = null;
		MimeBodyPart attachPart = null;
		ByteArrayDataSource byteArrayDataSource = null;
		MimeMultipart allMultipart = new MimeMultipart("mixed");
		if (email.getAttach() != null && email.getAttach().length > 0) {
			for (int i = 0; i < email.getAttach().length; i++) {
				attach = EmailUtils.base64TochangeByte(email.getAttach()[i]
						.getBody());
				if (attach == null) {
					attach = "".getBytes("UTF-8");
				}
				byteArrayDataSource = new ByteArrayDataSource(attach,
						"application/octet-stream");
				attachPart = new MimeBodyPart();
				attachPart.setDataHandler(new DataHandler(byteArrayDataSource));
				attachPart.setFileName(MimeUtility
						.encodeWord(email.getAttach()[i].getName() + "."
								+ email.getAttach()[i].getType()));
				allMultipart.addBodyPart(attachPart);
			}
		}

		/** send text type */
		if (email.getBody() == null) {
			msg.setContent(allMultipart);
			return msg;
		}
		if (email.getBody().getDatatype() != null
				&& "0".equals(email.getBody().getDatatype().trim())) {
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setContent(transformData(email, "plain"),
					"text/plain;charset=utf-8");
			allMultipart.addBodyPart(textBodyPart);
		} else {
			/** send html type */
			if (email.getBody().getDatatype() != null
					&& "1".equals(email.getBody().getDatatype().trim())) {
				MimeBodyPart htmlBodyPart = new MimeBodyPart();
				htmlBodyPart.setContent(transformData(email, "html"),
						"text/html;charset=utf-8");
				allMultipart.addBodyPart(htmlBodyPart);
			} else {
				/** send alternative type */
				MimeBodyPart contentPart = new MimeBodyPart();
				MimeMultipart contentMultipart = new MimeMultipart(
						"alternative");
				MimeBodyPart textBodyPart = new MimeBodyPart();
				textBodyPart.setContent(transformData(email, "plain"),
						"text/plain;charset=utf-8");
				MimeBodyPart htmlBodyPart = new MimeBodyPart();
				htmlBodyPart.setContent(transformData(email, "html"),
						"text/html;charset=utf-8");
				contentMultipart.addBodyPart(textBodyPart);
				contentMultipart.addBodyPart(htmlBodyPart);
				contentPart.setContent(contentMultipart);
				allMultipart.addBodyPart(contentPart);
			}
		}
		msg.setContent(allMultipart);
		return msg;
	}

	/**
	 * 处理邮件内容
	 * 
	 * @param email
	 * @param MIMEType
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	private String transformData(WebEmail email, String MIMEType)
			throws UnsupportedEncodingException, MessagingException {
		String bodyStr = "";
		if (email.getBody() != null && email.getBody().getData() != null) {
			bodyStr = email.getBody().getData();
		}
		if (!"plain".equals(MIMEType)) {
			bodyStr = bodyStr.replaceAll("\r\n", "<br/>");
			bodyStr = bodyStr.replaceAll("\n", "<br/>");
			bodyStr = bodyStr.replaceAll("\r", "<br/>");
			bodyStr = bodyStr.replaceAll(" ", "&nbsp;");
		}
		return bodyStr;
	}

	/**
	 * 创建没有附件的邮件
	 * 
	 * @param msg
	 * @param email
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	private MimeMessage createMsg(MimeMessage msg, String uid, WebEmail email)
			throws MessagingException, UnsupportedEncodingException {
		/** send text type */
		if (email.getBody().getDatatype() != null
				&& "0".equals(email.getBody().getDatatype().trim())) {
			msg.setContent(transformData(email, "plain"),
					"text/plain;charset=utf-8");
			msg = yahooCodeTransfer(msg, email);
			/** send html type */
		} else if (email.getBody().getDatatype() != null
				&& "1".equals(email.getBody().getDatatype().trim())) {
			msg.setContent(transformData(email, "html"),
					"text/html;charset=utf-8");
			msg = yahooCodeTransfer(msg, email);
		} else {
			/** send alternative type */
			MimeMultipart multipart = new MimeMultipart("alternative");
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setContent(transformData(email, "plain"),
					"text/plain;charset=utf-8");
			MimeBodyPart htmlBodyPart = new MimeBodyPart();
			htmlBodyPart.setContent(transformData(email, "html"),
					"text/html;charset=utf-8");
			multipart.addBodyPart(textBodyPart);
			multipart.addBodyPart(htmlBodyPart);
			msg.setContent(multipart);
		}
		return msg;
	}

	/*
	 * 针对yahoo邮箱发送纯文本或html类型邮件时，content-transfer-encoding的设置
	 */
	private MimeMessage yahooCodeTransfer(MimeMessage msg, WebEmail email)
			throws MessagingException {
		if (email.getHead().getFrom().indexOf("yahoo") != -1) {
			msg.setHeader("Content-Transfer-Encoding", "quoted-printable");
		}
		return msg;
	}

	private String saveToEml(WebEmail email, Message msg, long mid) {
		long accountId = email.getAccount().getId();
		Volumes volumeService = new Volumes();
		Volume volume = volumeService
				.getVolumeByMeta(Volumes.MetaEnum.CURRENT_MESSAGE_VOLUME);
		StringBuffer vPath = new StringBuffer(
		// "D:");
				volume.getPath());

		StringBuffer mPath = new StringBuffer(File.separator);
		mPath.append("eml");
		mPath.append(File.separator);
		mPath.append(accountId);

		File f = new File(vPath.append(mPath).toString());
		if (!f.exists()) {
			try {
				f.mkdirs();
			} catch (Exception e) {
				logger.error("" + e);
			}
		}
		vPath.append(File.separator);
		vPath.append(mid);
		vPath.append(".eml");
		File f1 = new File(vPath.toString());
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f1);
			msg.writeTo(out);
		} catch (FileNotFoundException e) {
			logger.error("file is not found.");
		} catch (IOException e) {
			logger.error("write message to eml fail.");
		} catch (MessagingException e) {
			logger.error("", e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return mPath.toString();
	}

	/**
	 * 保存发送的邮件到数据库
	 * 
	 * @param email
	 */
	private long saveEmail(WebEmail email, String uid) {
		long mid = 0;
		try {
			Clickoo_send_message message = transMessage(email, uid);

			// 保存message信息和消息体以及attachList信息
			SendMessageDao sendMessageDao = new SendMessageDao();
			mid = sendMessageDao.saveSendMessage(message);
			logger.error("mid:" + mid);
			email.getHead().setMessageId(String.valueOf(mid));
			if (mid != -1) {
				logger
						.info("success to save send message and body data and attachlist");
			} else {
				logger
						.error("failed to save send message and body data and attachlist");
			}
		} catch (Exception e) {
			logger.error(
					"failed to save send message and body data and attachlist",
					e);
		}
		return mid;
	}

	/**
	 * 解析得到Clickoo_message对象
	 * 
	 * @param email
	 * @return
	 */
	private Clickoo_send_message transMessage(WebEmail email, String uid) {
		Clickoo_send_message message = new Clickoo_send_message();
		WebAccount account = email.getAccount();
		if (account != null) {
			message.setAid(String.valueOf(account.getId()));
		} else {
			logger.error("there is no account....................");
			return null;
		}
		message.setUid(Long.valueOf(uid));
		message.setHid(Long.valueOf(email.getHead().getEmailHeadID()));
		message.setIntime(new Date());
		message.setStatus(Constant.MAIL_SENT_COMMON_STATUS);
		message.setPath(email.getPath());
		VolumeCacheObject volume = VolumeCacheManager
				.getCurrentVolumeByMetaName("CURRENT_MESSAGE_VOLUME");
		message.setVolumeid(volume.getId());
		return message;
	}

	/**
	 * 添加用户签名，目前此方法已不再调用.
	 * 
	 * @param email
	 * @param uid
	 *
	private void addUserSignature(WebEmail email, String uid) {
		UserService userService = new UserService();
		String bodyStr = email.getBody().getData();
		if (null != uid) {
			Clickoo_user user = userService.getUserById(Long.valueOf(uid));
			if (email.getBody() != null && email.getBody().getData() != null) {
				bodyStr = email.getBody().getData();
				if (user != null && user.getSignature() != null
						&& user.getSignature().length() > 0) {
					bodyStr = bodyStr + Constant.SPILIT_LINE
							+ user.getSignature();
				}
			}
		}
		email.getBody().setData(bodyStr);
	}
	/

	/**
	 * 发送Queue消息.
	 * 
	 * @param msg
	 * @param accountName
	 */
	private void sendJMSMessage(String uid, String mid, String sendMailType,
			String oldmid, String hid, String accountName) {
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_SENDMAIL_TYPE);
		json.put(JMSConstans.JMS_UID_KEY, uid);
		json.put(JMSConstans.JMS_MID_KEY, mid);
		json.put(JMSConstans.JMS_SENDMAIL_VALUE, sendMailType);
		json.put(JMSConstans.JMS_OLD_MID_KEY, oldmid);
		json.put(JMSConstans.JMS_HID_KEY, hid);
		logger.info("sendJMSMessage------" + json.toString());
		JmsSender.sendQueueMsg(json, MailBoxDispatcher
				.getSendMailQueueByType(accountName));
	}
}
