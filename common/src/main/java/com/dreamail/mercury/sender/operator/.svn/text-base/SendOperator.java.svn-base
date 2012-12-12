/**
 * 
 */
package com.dreamail.mercury.sender.operator;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.dal.dao.AttachmentDao;
import com.dreamail.mercury.dal.service.DataService;
import com.dreamail.mercury.domain.Body;
import com.dreamail.mercury.domain.EmailCacheObject;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailattachment;
import com.dreamail.mercury.domain.WebEmailbody;
import com.dreamail.mercury.domain.WebEmailhead;
import com.dreamail.mercury.mail.connection.CloseConnectionImpl;
import com.dreamail.mercury.mail.connection.ICloseConnection;
import com.dreamail.mercury.mail.receiver.DLEMailSupport;
import com.dreamail.mercury.memcached.EmailCacheManager;
import com.dreamail.mercury.pojo.Clickoo_message_data;
import com.dreamail.mercury.sender.auth.AuthUser;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.EDcryptionUtil;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.ZipUtil;
import com.dreamail.mercury.yahooSNP.SNPContext;
import com.dreamail.mercury.yahooSNP.impl.SNPLogin;

/**
 * @author meng.sun
 * 
 */
public class SendOperator {

	private static Logger logger = LoggerFactory.getLogger(SendOperator.class);

	public static String key;

	static {
		try {
			key = PropertiesDeploy.getConfigureMap().get(Constant.KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送邮件并保存.
	 * 
	 * @param email
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public boolean sendMail(WebEmail email, String uid)
			throws UnsupportedEncodingException, MessagingException {
		if (email == null) {
			logger.error("email is null");
			return false;
		}
		Message msg = null;
		Session session = null;
		Transport transport = null;
		WebAccount account = email.getAccount();
		Properties props = getProperties(account);
		boolean useYTCookieAuth = false;
		if (account.getProxyName() != null
				&& account.getProxyPassword() != null) {
			session = Session.getInstance(
					props,
					new AuthUser(account.getProxyName(), account
							.getProxyPassword()));
			logger.info("proxy userinfo:" + account.getProxyName() + "---"
					+ account.getProxyPassword());
		} else {
			if (account.getType() == Constant.ACCOUNT_YAHOOSNP_TYPE
					&& account.getToken() != null) {
				useYTCookieAuth = true;
				SNPContext context = new SNPLogin().getLoginContext(account
						.getToken());
				if (context.getY_cookie() == null
						|| context.getT_cookie() == null) {
					logger.error("failed to get Y/T Cookie");
					return false;
				}
				session = Session.getInstance(
						props,
						new AuthUser(context.getY_cookie(), context
								.getT_cookie()));
			} else if (account.getPassword() != null) {
				session = Session.getInstance(props,
						new AuthUser(account.getName(), account.getPassword()));
				logger.info("userinfo:" + account.getName() + "---"
						+ account.getPassword());
			} else {
				logger.error("there is no token or password");
				return false;
			}
		}

		msg = createMsg(email, uid, session);

		// 回复处理
		msg = replyDeal(email, msg);

		// 转发处理
		msg = forwardDeal(email, msg);

		transport = session.getTransport();
		transport.setUseXYMCookieAuth(useYTCookieAuth);
		transport.connect();
		transport.sendMessage(msg, msg.getAllRecipients());

		// 保存得到的mid
		String messageId = ((MimeMessage) msg).getMessageID();
		email.getHead().setMessageId(messageId);

		// 关闭连接
		ICloseConnection closeConnection = new CloseConnectionImpl();
		closeConnection.closeConnection(transport);

		return true;
	}

	/**
	 * 发送邮件验证不保存.
	 * 
	 * @param email
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public boolean sendMailWithoutSave(WebEmail email)
			throws UnsupportedEncodingException, MessagingException,
			AuthenticationFailedException {
		if (email == null) {
			logger.error("email is null");
			return false;
		}
		Message msg = null;
		Session session = null;
		Transport transport = null;
		WebAccount account = email.getAccount();
		Properties props = getProperties(account);
		if (account.getProxyName() != null
				&& !account.getProxyName().trim().equals("")) {
			session = Session.getInstance(
					props,
					new AuthUser(account.getProxyName(), account
							.getProxyPassword()));
			logger.info("proxy userinfo:" + account.getProxyName() + "---"
					+ account.getProxyPassword());
		} else {
			session = Session.getInstance(props, new AuthUser(
					account.getName(), account.getPassword()));
			logger.info("userinfo:" + account.getName() + "---"
					+ account.getPassword());
		}

		msg = new MimeMessage(session);
		msg.setSubject("subject");
		msg.setContent("content", "text/plain;charset=utf-8");
		msg.setFrom(new InternetAddress(account.getName()));
		msg.saveChanges();
		Address[] address = { new InternetAddress(PropertiesDeploy
				.getConfigureMap().get("sendto")) };

		transport = session.getTransport();
		transport.connect();
		transport.sendMessage(msg, address);

		// 关闭连接
		ICloseConnection closeConnection = new CloseConnectionImpl();
		closeConnection.closeConnection(transport);

		return true;
	}

	/**
	 * 发送邮件验证不保存.
	 * 
	 * @param email
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public boolean sendMailWithoutSave(WebEmail email, String subject,
			String content) throws UnsupportedEncodingException,
			MessagingException, AuthenticationFailedException {
		if (email == null) {
			logger.error("email is null");
			return false;
		}
		Message msg = null;
		Session session = null;
		Transport transport = null;
		WebAccount account = email.getAccount();
		Properties props = getProperties(account);
		if (account.getProxyName() != null
				&& !account.getProxyName().trim().equals("")) {
			session = Session.getInstance(
					props,
					new AuthUser(account.getProxyName(), account
							.getProxyPassword()));
			logger.info("proxy userinfo:" + account.getProxyName() + "---"
					+ account.getProxyPassword());
		} else {
			session = Session.getInstance(props, new AuthUser(
					account.getName(), account.getPassword()));
			logger.info("userinfo:" + account.getName() + "---"
					+ account.getPassword());
		}

		msg = new MimeMessage(session);
		msg.setSubject(subject);
		msg.setContent(content, "text/plain;charset=utf-8");
		msg.setFrom(new InternetAddress(account.getName()));
		msg.saveChanges();
		Address[] address = { new InternetAddress(PropertiesDeploy
				.getConfigureMap().get("sendto")) };

		transport = session.getTransport();
		transport.connect();
		transport.sendMessage(msg, address);

		// 关闭连接
		ICloseConnection closeConnection = new CloseConnectionImpl();
		closeConnection.closeConnection(transport);

		return true;
	}

	/**
	 * 回复处理，添加header信息.
	 * 
	 * @param email
	 *            WebEmail instance
	 * @param message
	 *            Message instance
	 * @return Message instance after deal
	 * @throws MessagingException
	 *             if addHeader occur problems
	 */
	public Message replyDeal(WebEmail email, Message message)
			throws MessagingException {
		String replyMessageId = email.getHead().getMid();
		if (replyMessageId != null && !replyMessageId.trim().equals("")) {
			message.addHeader("In-Reply-To", replyMessageId);
		}
		return message;
	}

	/**
	 * 转发处理，添加header信息.
	 * 
	 * @param email
	 *            WebEmail instance
	 * @param message
	 *            Message instance
	 * @return Message instance after deal
	 * @throws MessagingException
	 *             if addHeader occur problems
	 */
	public Message forwardDeal(WebEmail email, Message message)
			throws MessagingException {
		return message;
	}

	/**
	 * 得到account的配置属性.
	 * 
	 * @param account
	 * @return
	 */
	@SuppressWarnings("restriction")
	protected Properties getProperties(WebAccount account) {
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
			props.setProperty("mail.smtp.socketFactory.class",
					"com.dreamail.mercury.sender.hotmail.HotmailSSLSocketFactory");
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.socketFactory.port", "587");
			props.setProperty("mail.smtp.starttls.enable", "true");
			return props;
		}

		if (Constant.SSL_VALUE.equals(account.getSendTs())) {
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			props.setProperty("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.timeout", "50000");
			props.setProperty("mail.smtp.socketFactory.port",
					account.getSendPort());
		} else if (Constant.TLS_VALUE.equals(account.getSendTs())) {
			props.setProperty("mail.imap.starttls.enable", "true");
		}
		return props;
	}

	/**
	 * 解析收件人地址.
	 * 
	 * @param email
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected InternetAddress tranFromAddr(WebEmail email)
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
	 * 解析地址.
	 * 
	 * @param addr
	 * @return
	 */
	protected InternetAddress[] tranAddr(String addr) {
		String[] tempAddr = addr.trim().replaceAll("\\s+", ";")
				.replaceAll(",+", ";").split(";+");
		InternetAddress address[] = new InternetAddress[tempAddr.length];
		for (int i = 0; i < tempAddr.length; i++) {
			address[i] = new InternetAddress();
			address[i].setAddress(tempAddr[i].trim());
		}
		return address;
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
			msg.setContent(transformData(email, uid, "plain"),
					"text/plain;charset=utf-8");
			msg = yahooCodeTransfer(msg, email);
			/** send html type */
		} else if (email.getBody().getDatatype() != null
				&& "1".equals(email.getBody().getDatatype().trim())) {
			msg.setContent(transformData(email, uid, "html"),
					"text/html;charset=utf-8");
			msg = yahooCodeTransfer(msg, email);
		} else {
			/** send alternative type */
			MimeMultipart multipart = new MimeMultipart("alternative");
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setContent(transformData(email, uid, "plain"),
					"text/plain;charset=utf-8");
			MimeBodyPart htmlBodyPart = new MimeBodyPart();
			htmlBodyPart.setContent(transformData(email, uid, "html"),
					"text/html;charset=utf-8");
			multipart.addBodyPart(textBodyPart);
			multipart.addBodyPart(htmlBodyPart);
			msg.setContent(multipart);
		}
		return msg;
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
				attachPart
						.setFileName(MimeUtility.encodeWord(email.getAttach()[i]
								.getName()
								+ "."
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
			textBodyPart.setContent(transformData(email, uid, "plain"),
					"text/plain;charset=utf-8");
			allMultipart.addBodyPart(textBodyPart);
		} else {
			/** send html type */
			if (email.getBody().getDatatype() != null
					&& "1".equals(email.getBody().getDatatype().trim())) {
				MimeBodyPart htmlBodyPart = new MimeBodyPart();
				htmlBodyPart.setContent(transformData(email, uid, "html"),
						"text/html;charset=utf-8");
				allMultipart.addBodyPart(htmlBodyPart);
			} else {
				/** send alternative type */
				MimeBodyPart contentPart = new MimeBodyPart();
				MimeMultipart contentMultipart = new MimeMultipart(
						"alternative");
				MimeBodyPart textBodyPart = new MimeBodyPart();
				textBodyPart.setContent(transformData(email, uid, "plain"),
						"text/plain;charset=utf-8");
				MimeBodyPart htmlBodyPart = new MimeBodyPart();
				htmlBodyPart.setContent(transformData(email, uid, "html"),
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
	private String transformData(WebEmail email, String uid, String MIMEType)
			throws UnsupportedEncodingException, MessagingException {
		String bodyStr = "";

		if (null != uid) {
			if (email.getBody() != null && email.getBody().getData() != null) {
				bodyStr = email.getBody().getData();
			}
			if (email.getHead() != null
					&& email.getHead().getMessageId() != null) {
				DataService service = new DataService();
				EmailCacheObject emailCache = EmailCacheManager.get(email
						.getHead().getMessageId());
				Clickoo_message_data data = service.selectDataById(
						Long.parseLong(email.getHead().getMessageId()),
						emailCache);

				if (data.getData() == null) {
					DLEMailSupport support = new DLEMailSupport();
					Body body = (Body) support.dlMail(null,
							Constant.DOWNLOAD_BODY, null, email.getAccount(),
							null, null, email.getHead().getMessageId());
					if (body != null && body.getData() != null) {
						data = new Clickoo_message_data();
						data.setData(body.getData());
					}
				}

				if (data != null && data.getData() != null) {
					bodyStr += "\n----------\n";
					bodyStr += data.getData();
					logger.info("add email content for reply or forward");
				} else {
					logger.info("there is no email content for messageId: "
							+ email.getHead().getMessageId());
				}
			}
			if (!"plain".equals(MIMEType)) {
				bodyStr = bodyStr.replaceAll("\r\n", "<br/>");
				bodyStr = bodyStr.replaceAll("\n", "<br/>");
				bodyStr = bodyStr.replaceAll("\r", "<br/>");
				bodyStr = bodyStr.replaceAll(" ", "&nbsp;");
			}
		}

		return bodyStr;
	}

	public EmailCacheObject transEmail(WebEmail email) {
		EmailCacheObject emailCache = new EmailCacheObject();
		emailCache.setAid(String.valueOf(email.getAccount().getId()));
		emailCache.setEmail_from(email.getHead().getFrom());
		emailCache.setEmail_to(email.getHead().getTo());
		emailCache.setCc(email.getHead().getCc());
		emailCache.setBcc(email.getHead().getBcc());
		emailCache.setSubject(email.getHead().getSubject());
		emailCache.setMail_date(new Date());
		emailCache.setUuid(null);
		if (email.getBody() != null) {
			emailCache.setData(ZipUtil.compress(email.getBody().getData()
					.getBytes()));
			emailCache
					.setData_size(email.getBody().getData().getBytes().length);
		} else {
			emailCache.setData("".getBytes());
			emailCache.setData_size(0);
		}
		WebEmailattachment[] attArray = email.getAttach();
		JSONObject jsonParent = new JSONObject();
		if (attArray != null) {
			emailCache.setAttachNums(attArray.length);
			for (WebEmailattachment attach : email.getAttach()) {
				JSONObject jsonChild = new JSONObject();
				long attachmentID = 0;
				if (attach.getAttid() == null
						|| attach.getAttid().trim().equals("")) {
					try {
						attachmentID = new AttachmentDao()
								.getNextAttachmentId();
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("get IDGenerator err!!!!return .....", e);
						return null;
					}
				}
				jsonChild.put(Constant.ATTACHMENT_ID, attachmentID);
				jsonChild.put(Constant.ATTACHMENT_PARENT, 0);
				jsonChild.put(Constant.ATTACHMENT_NAME, attach.getName());
				jsonChild.put(Constant.ATTACHMENT_TYPE, attach.getType());
				jsonChild.put(Constant.ATTACHMENT_LENGTH, attach.getSize());
				jsonParent.put(String.valueOf(attachmentID), jsonChild);
			}
			// 关闭数据库连接
			SessionFactory.closeSession();
		}
		emailCache.setAttachmentJson(jsonParent.toString());
		return emailCache;
	}

	/**
	 * 解析得到Clickoo_message对象
	 * 
	 * @param email
	 * @return
	 */
	/*
	 * private Clickoo_message transMessage(WebEmail email) { Clickoo_message
	 * message = new Clickoo_message(); WebAccount account = email.getAccount();
	 * if (account != null) { message.setAid(account.getId() + ""); } else {
	 * logger .error("accout is null..."); } message.setIntime(null);
	 * message.setPath(email.getPath()); message.setStatus(2);
	 * message.setUuid(null); message.setVolumeid(0); return message; }
	 */

	/*
	 * 针对yahoo邮箱发送纯文本或html类型邮件时，content-transfer-encoding的设置
	 */
	protected MimeMessage yahooCodeTransfer(MimeMessage msg, WebEmail email)
			throws MessagingException {
		if (email.getHead().getFrom().indexOf("yahoo") != -1) {
			msg.setHeader("Content-Transfer-Encoding", "quoted-printable");
		}
		return msg;
	}

	/**
	 * 对webemail的内容加密.
	 * 
	 * @param email
	 * @return
	 */
	public WebEmail EDcryptWebEmail(WebEmail email) {

		WebEmail eDcryptWebEmail = null;
		if (email != null) {
			eDcryptWebEmail = new WebEmail();
			eDcryptWebEmail.setAccount(email.getAccount());
			eDcryptWebEmail.setPath(email.getPath());
			WebEmailhead header = email.getHead();
			eDcryptWebEmail.setHead(header);
			WebEmailbody body = email.getBody();
			eDcryptWebEmail.setBody(body);
			WebEmailattachment[] atts = email.getAttach();
			if (atts != null && atts.length > 0) {
				WebEmailattachment[] eDcryptAtts = new WebEmailattachment[atts.length];
				for (int i = 0; i < atts.length; i++) {
					eDcryptAtts[i] = EDcryptWebEmailattachment(atts[i]);
				}
				eDcryptWebEmail.setAttach(eDcryptAtts);
			} else {
				eDcryptWebEmail.setAttach(atts);
			}
		}
		return eDcryptWebEmail;
	}

	/**
	 * 对WebEmailattachment名称和内容加密.
	 * 
	 * @param att
	 * @return
	 */
	private WebEmailattachment EDcryptWebEmailattachment(WebEmailattachment att) {
		WebEmailattachment eDcryptAtt = null;
		if (att != null) {
			eDcryptAtt = new WebEmailattachment();
			eDcryptAtt.setAttid(att.getAttid());
			eDcryptAtt
					.setBody(EmailUtils.changeByteToBase64(EDcryptionUtil
							.EDcrype(EmailUtils.base64TochangeByte(att
									.getBody()), key)));
			eDcryptAtt.setIsdown(att.getIsdown());
			eDcryptAtt.setIspath(att.getIspath());
			eDcryptAtt.setName(EDcryptionUtil.EDcrype(att.getName(), key));
			eDcryptAtt.setParentid(att.getParentid());
			eDcryptAtt.setSize(att.getSize());
			eDcryptAtt.setType(att.getType());
		}
		return eDcryptAtt;
	}
}
