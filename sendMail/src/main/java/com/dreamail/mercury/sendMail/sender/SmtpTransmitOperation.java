package com.dreamail.mercury.sendMail.sender;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.SendServerDao;
import com.dreamail.mercury.mail.connection.CloseConnectionImpl;
import com.dreamail.mercury.mail.connection.ICloseConnection;
import com.dreamail.mercury.pojo.Clickoo_mail_send_server;
import com.dreamail.mercury.pojo.OutPath;
import com.dreamail.mercury.sender.auth.AuthUser;
import com.dreamail.mercury.sender.control.ConnectionControl;
import com.dreamail.mercury.sender.control.SmtpQeueObj;
import com.dreamail.mercury.smtp.utils.EmlIdGenerator;
import com.dreamail.mercury.smtp.utils.SmtpEmail;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.JsonUtil;

public class SmtpTransmitOperation {
	private static final Logger logger = LoggerFactory
			.getLogger(SmtpTransmitOperation.class);

	/**
	 * 从硬盘中取出邮件并发送
	 * 
	 * @param smtpQeueObj
	 * @throws MessagingException
	 * @throws MessagingException
	 */
	public void sendMail() throws MessagingException {
		if (ConnectionControl.smtpSendersQeue.size() == 0) {
			return;
		}
		int yahooConnectsMax = ConnectionControl.connectionMaxs
				.get(Constant.YAHOO_HOST_NAME);
		if (ConnectionControl.yahooCurrentConnects == yahooConnectsMax) {
			return;
		}
		SmtpQeueObj smtpQeueObj = ConnectionControl.smtpSendersQeue.poll();
		logger.info("===after poll===smtpSendersQeue"
				+ ConnectionControl.smtpSendersQeue.size());
		if (smtpQeueObj == null || smtpQeueObj.getUsername() == null
				|| "".equals(smtpQeueObj.getUsername())) {
			return;
		}
		String emailtype = smtpQeueObj.getUsername().substring(
				smtpQeueObj.getUsername().lastIndexOf("@") + 1);
		if (emailtype.startsWith("yahoo") || emailtype.startsWith("ymail")
				|| emailtype.startsWith("rocketmail")) {
			ConnectionControl.yahooAdd();
			logger.info("Start to send Email from [ "
					+ smtpQeueObj.getUsername() + " ]");
			Message message = null;
			Session session = null;
			Transport transport = null;
			Properties props = getSmtpProperties();
			session = Session.getInstance(props, new AuthUser(smtpQeueObj
					.getUsername(), smtpQeueObj.getPassword()));
			message = getMessageFromEml(smtpQeueObj.getPath(), session);
			transport = session.getTransport();
			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			ICloseConnection closeConnection = new CloseConnectionImpl();
			closeConnection.closeConnection(transport);

			File f = new File(smtpQeueObj.getPath());
			if (f.exists()) {
				try {
					f.delete();
				} catch (Exception e) {
					logger.error("Exception when delete!" + e);
				}
			}
			ConnectionControl.yahooReduce();
		}
	}

	/**
	 * 根据邮箱后缀名获取属性
	 * 
	 * @param emailFrom
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("restriction")
	private Properties getSmtpProperties(SmtpEmail email) {
		Properties props = new Properties();
		List<Clickoo_mail_send_server> list = new SendServerDao()
				.getSendServerByName(email.getUser().getSuffix());
		if (list.size() != 1) {
			logger.error("Does non't exist this emailbox["
					+ email.getUser().getSuffix() + "]");
			return null;
		}
		OutPath outpath = JsonUtil.getInstance().parserOutPath(
				list.get(0).getOutPath());
		email.setSendHost(outpath.getSmtpServer());
		email.setSendPort(outpath.getSendPort());
		email.setSendProtocolType(outpath.getType());

		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", email.getSendHost());
		props.setProperty("mail.smtp.port", email.getSendPort());
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.connectiontimeout", "30000");
		if (Constant.SSL_VALUE.equals(email.getSendProtocolType())) {
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			props.setProperty("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.timeout", "50000");
			props.setProperty("mail.smtp.socketFactory.port", email
					.getSendPort());
		} else if (Constant.TLS_VALUE.equals(email.getSendProtocolType())) {
			props.setProperty("mail.imap.starttls.enable", "true");
		}
		return props;
	}

	@SuppressWarnings("restriction")
	private Properties getSmtpProperties() {
		Properties props = new Properties();

		List<Clickoo_mail_send_server> list = new SendServerDao()
				.getSendServerByName("yahoo.*");
		if (list.size() != 1) {
			logger.error("Does non't exist this emailbox!");
			return null;
		}
		OutPath outpath = JsonUtil.getInstance().parserOutPath(
				list.get(0).getOutPath());
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", outpath.getSmtpServer());
		props.setProperty("mail.smtp.port", outpath.getSendPort());
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.connectiontimeout", "30000");
		if (Constant.SSL_VALUE.equals(outpath.getType())) {
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			props.setProperty("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.timeout", "50000");
			props.setProperty("mail.smtp.socketFactory.port", outpath
					.getSendPort());
		} else if (Constant.TLS_VALUE.equals(outpath.getType())) {
			props.setProperty("mail.imap.starttls.enable", "true");
		}
		return props;
	}

	/**
	 * 创建message
	 * 
	 * @param email
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	private MimeMessage createMsg(SmtpEmail email, Session session)
			throws UnsupportedEncodingException, MessagingException {
		MimeMessage msg = new MimeMessage(session);
		/** Set the FROM,TO,CC,BCC and subject fields */
		msg.setFrom(tranFromAddr(email));
		msg.setSentDate(new Date());
		if (email.getTo() != null && !"".equals(email.getTo().trim())) {
			msg.setRecipients(Message.RecipientType.TO, tranAddr(email.getTo()
					.trim()));
		}
		if (email.getCc() != null && !"".equals(email.getCc().trim())) {
			msg.setRecipients(Message.RecipientType.CC, tranAddr(email.getCc()
					.trim()));
		}
		String subject = email.getSubject();
		if (subject != null) {
			if (subject.length() > 250) {
				subject = subject.substring(0, 245) + "...";
			}
		}
		msg.setSubject(subject, "UTF-8");

		if (email.getAttach() == null || email.getAttach().size() < 1) {
			msg = createMsg(msg, email);
		} else {
			msg = createMsgAttach(msg, email);
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
	private MimeMessage createMsg(MimeMessage msg, SmtpEmail email)
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

	/**
	 * 创建有附件的邮件
	 * 
	 * @param msg
	 * @param email
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	private MimeMessage createMsgAttach(MimeMessage msg, SmtpEmail email)
			throws UnsupportedEncodingException, MessagingException {
		byte[] attach = null;
		MimeBodyPart attachPart = null;
		ByteArrayDataSource byteArrayDataSource = null;
		MimeMultipart allMultipart = new MimeMultipart("mixed");
		if (email.getAttach() != null && email.getAttach().size() > 0) {
			for (int i = 0; i < email.getAttach().size(); i++) {
				attach = EmailUtils.base64TochangeByte(email.getAttach().get(i)
						.getBody());
				if (attach == null) {
					attach = "".getBytes("UTF-8");
				}
				byteArrayDataSource = new ByteArrayDataSource(attach,
						"application/octet-stream");
				attachPart = new MimeBodyPart();
				attachPart.setDataHandler(new DataHandler(byteArrayDataSource));
				attachPart.setFileName(MimeUtility.encodeWord(email.getAttach()
						.get(i).getName()));
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
	 * 解析收件人地址.
	 * 
	 * @param email
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private InternetAddress tranFromAddr(SmtpEmail email)
			throws UnsupportedEncodingException {
		InternetAddress address = new InternetAddress();
		address.setAddress(email.getFrom().trim());
		// 添加别名处理
		return address;
	}

	/**
	 * 解析地址.
	 * 
	 * @param addr
	 * @return
	 */
	private InternetAddress[] tranAddr(String addr) {
		// logger.info("tranAddress :" + addr);
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
	 * 处理邮件正文内容
	 * 
	 * @param email
	 * @param MIMEType
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	private String transformData(SmtpEmail email, String MIMEType)
			throws UnsupportedEncodingException, MessagingException {
		String bodyStr = email.getBody().getData();
		if (!"plain".equals(MIMEType)) {
			bodyStr = bodyStr.replaceAll("\r\n", "<br/>");
			bodyStr = bodyStr.replaceAll("\n", "<br/>");
			bodyStr = bodyStr.replaceAll("\r", "<br/>");
			bodyStr = bodyStr.replaceAll(" ", "&nbsp;");
		}
		return bodyStr;
	}

	/**
	 * 针对yahoo邮箱发送纯文本或html类型邮件时，content-transfer-encoding的设置
	 * 
	 * @param msg
	 * @param email
	 * @return
	 * @throws MessagingException
	 */
	private MimeMessage yahooCodeTransfer(MimeMessage msg, SmtpEmail email)
			throws MessagingException {
		if (email.getFrom().indexOf(Constant.YAHOO_HOST_NAME) != -1) {
			msg.setHeader("Content-Transfer-Encoding", "quoted-printable");
		}
		return msg;
	}

	/**
	 * 创建邮件并将将邮件保存到硬盘，并加入到发送等待队列
	 * 
	 * @param message
	 * @param session
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public boolean createMsgAndSaveToEml(SmtpEmail email)
			throws UnsupportedEncodingException, MessagingException {
		boolean flag = false;
		if (email == null) {
			return false;
		}
		Message msg = null;
		Session session = null;
		Properties props = getSmtpProperties(email);
		if (props == null) {
			logger.error("get properties failed!!");
			return flag;
		}
		session = Session.getInstance(props, new AuthUser(email.getFrom(),
				email.getUser().getPassword()));
		msg = createMsg(email, session);

		int yahooConnectsMax = ConnectionControl.connectionMaxs
				.get(Constant.YAHOO_HOST_NAME);
		if (ConnectionControl.yahooCurrentConnects < yahooConnectsMax) {
			Transport transport = null;
			String emailtype = email.getFrom().substring(
					email.getFrom().lastIndexOf("@") + 1);
			if (emailtype.startsWith(Constant.YAHOO_HOST_NAME)
					|| emailtype.startsWith("ymail")
					|| emailtype.startsWith("rocketmail")) {
				ConnectionControl.yahooAdd();
				logger.info("Start to send Email from [ " + email.getFrom()
						+ " ]");
				transport = session.getTransport();
				transport.connect();
				transport.sendMessage(msg, msg.getAllRecipients());
				ICloseConnection closeConnection = new CloseConnectionImpl();
				closeConnection.closeConnection(transport);
				ConnectionControl.yahooReduce();
				flag = true;
			}
		} else {
			String path = saveMsgToEml(msg);
			if (path != null && !"".equals(path)) {
				SmtpQeueObj obj = new SmtpQeueObj(email.getFrom(), email
						.getUser().getPassword(), path);
				if (ConnectionControl.smtpSendersQeue.offer(obj)) {
					logger.info("===after offer===smtpSendersQeue size : "
							+ ConnectionControl.smtpSendersQeue.size());
					flag = true;
				} else {
					logger.info("The smtpSendersQeue is alerdy full!");
				}
			}
		}
		return flag;
	}

	private String saveMsgToEml(Message msg) {
		String path = null;
		StringBuffer vPath = new StringBuffer("/tmp/testsendmail");
		StringBuffer mPath = new StringBuffer(File.separator);
		mPath.append("smtpeml");

		File f = new File(vPath.append(mPath).toString());
		if (!f.exists()) {
			try {
				f.mkdirs();
			} catch (Exception e) {
				logger.error("Exception when mkdirs!" + e);
			}
		}
		vPath.append(File.separator);
		int emlid = EmlIdGenerator.getEmlid();
		vPath.append(emlid);
		vPath.append(".eml");
		logger.info("eml save path : " + vPath.toString());
		File f1 = new File(vPath.toString());
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f1);
			msg.writeTo(out);
			path = vPath.toString();
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
		return path;
	}

	/**
	 * 根据路径从硬盘上取得邮件文件并解析成Message
	 * 
	 * @param path
	 * @return
	 * @throws MessagingException
	 */
	private MimeMessage getMessageFromEml(String path, Session session) {
		FileInputStream fis = null;
		try {
			logger.info("email path is : " + path);
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			logger.error("eml can't be found.", e);
		}
		MimeMessage msg = null;
		try {
			msg = new MimeMessage(session, fis);
		} catch (MessagingException e) {
			logger.error("fail to get Message from eml!!" + e);
		}
		return msg;
	}
}
