package com.dreamail.mercury.sendMail.sender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.dao.SendFailedMessageDao;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.mail.connection.CloseConnectionImpl;
import com.dreamail.mercury.mail.connection.ICloseConnection;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.Clickoo_message_data;
import com.dreamail.mercury.sender.auth.AuthUser;
import com.dreamail.mercury.sender.operator.SendOperator;
import com.dreamail.mercury.store.Volume;
import com.dreamail.mercury.store.Volumes;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.JMSTypes;
import com.dreamail.mercury.yahooSNP.SNPContext;
import com.dreamail.mercury.yahooSNP.impl.SNPLogin;

public class TransmitOperation extends SendOperator {

	private static Logger logger = LoggerFactory
			.getLogger(TransmitOperation.class);

	/**
	 * 发送邮件并保存.
	 * 
	 * @param email
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public boolean sendMail(WebEmail email, String uid, String type) {
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
		if (account.getType() == Constant.ACCOUNT_YAHOOSNP_TYPE
				&& account.getToken() != null) {
			useYTCookieAuth = true;
			SNPContext context = new SNPLogin().getLoginContext(account
					.getToken());
			session = Session.getInstance(
					props,
					new AuthUser(EmailUtils.changeByteToBase64(context
							.getY_cookie().getBytes()), EmailUtils
							.changeByteToBase64(context.getT_cookie()
									.getBytes())));
		} else if (account.getPassword() != null) {
			session = Session.getInstance(props, new AuthUser(
					account.getName(), account.getPassword()));
			logger.info("userinfo:" + account.getName());
		} else {
			logger.error("there is no token or password");
			return false;
		}
		try {
			session.setDebug(false);
			msg = createMsg(email, uid, session);
			transport = session.getTransport();
			transport.setUseXYMCookieAuth(useYTCookieAuth);
			transport.connect();
			transport.sendMessage(msg, msg.getAllRecipients());
			// 更新eml
			email = new SendOperator().EDcryptWebEmail(email);
			Message message = createMsg(email, uid, session);
			updateEml(message, email.getPath(), email.getHead().getMessageId());
			// 删除send_failed_message那个表里数据
			new SendFailedMessageDao().deleteSendFailedMessageByMid(email
					.getHead().getMessageId());
		} catch (MessagingException e) {
			logger.error("Sendmessage Exception:", e);
			return false;
		} catch (Exception e) {
			logger.error("Sendmessage Exception:", e);
			return false;
		} finally {
			// 关闭连接
			ICloseConnection closeConnection = new CloseConnectionImpl();
			closeConnection.closeConnection(transport);
		}

		return true;
	}

	/**
	 * 发送系统退信 不保存.
	 * 
	 * @param email
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public void sendSystemMailWithoutSave(WebEmail email, String aid) {
		logger.info(">>>>>>>>>>>>>>sendSystemMailWithoutSave<<<<<<<<<<<<<<<<<<<<<");

		// 1.insert message into db
		Clickoo_message message = new Clickoo_message();
		Date date = new Date();
		message.setIntime(date);
		message.setAid(aid);
		message.setUuid(null);
		message.setSendtime(date);
		message.setEmailFrom("<System@Pushmail.com>");
		message.setEmailTo("<" + email.getHead().getFrom() + ">");
		message.setSubject("Mail Send Failure Notice with Subject "
				+ email.getHead().getSubject() + " at "
				+ email.getHead().getSendTime());

		Clickoo_message_data data = new Clickoo_message_data();

		StringBuffer body = new StringBuffer();
		body.append(
				"Sorry to inform you a send mail failure contains below infomation:")
				.append(Constant.SPILIT_NEXT_LINT);
		if (email != null && email.getHead() != null) {
			body.append("From:<").append(email.getHead().getFrom()).append(">")
					.append(Constant.SPILIT_NEXT_LINT);
			body.append("To:<").append(email.getHead().getTo()).append(">")
					.append(Constant.SPILIT_NEXT_LINT);
			if (email.getHead().getCc() != null
					&& !email.getHead().getCc().trim().equals("")) {
				body.append("CC:<").append(email.getHead().getCc()).append(">")
						.append(Constant.SPILIT_NEXT_LINT);
			}
			if (email.getHead().getSendTime() != null) {
				body.append("Time:").append(email.getHead().getSendTime())
						.append(Constant.SPILIT_NEXT_LINT);
			}
			body.append("Subject:").append(email.getHead().getSubject())
					.append(Constant.SPILIT_NEXT_LINT);
		}
		body.append("Body:").append(Constant.SPILIT_NEXT_LINT)
				.append(email.getBody().getData());

		data.setData(body.toString(), true);
		data.setSize(body.toString().getBytes().length);
		message.setMessageData(data);

		new MessageDao().saveSystemMessage(message);

		// 2.notice the client new message
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_NEWMAIL_TYPE);
		json.put(JMSConstans.JMS_AID_KEY, aid);
		json.put(JMSConstans.JMS_ACCOUNT_NAME_KEY, email.getHead().getFrom());
		JmsSender.sendTopicMsg(json.toString(), JMSTypes.MESSAGE_TOPIC);
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
	 * 将组合后的eml重新保存到硬盘
	 * 
	 * @param message
	 * @param clickooMessage
	 */
	public void updateEml(Message message, String path, String messageid) {
		logger.info("\nwrite message to eml!!");
		String fpath = getEmlPath(path, messageid);
		File f1 = new File(fpath);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f1);
			message.writeTo(out);
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
	}

	/**
	 * 根据clickoo_message表中的path和id组装详细地址
	 * 
	 * @param path
	 * @param messageid
	 * @return
	 */
	public static String getEmlPath(String path, String messageid) {
		Volumes volumeService = new Volumes();
		Volume volume = volumeService
				.getVolumeByMeta(Volumes.MetaEnum.CURRENT_MESSAGE_VOLUME);
		StringBuffer emlPath = new StringBuffer(volume.getPath());
		emlPath.append(path);
		File f = new File(emlPath.toString());
		if (!f.exists()) {
			logger.error("\ntthe folder path of eml[" + messageid + ".eml]:"
					+ emlPath.toString() + "is not exsit!!!");
			try {
				f.mkdirs();
			} catch (Exception e) {
				logger.error("Exception when mkdirs!", e);
			}
		}
		emlPath.append(File.separator);
		emlPath.append(messageid);
		emlPath.append(".eml");
		return emlPath.toString();
	}

	public static String getEmlZIPPath(String path, String messageid) {
		Volumes volumeService = new Volumes();
		Volume volume = volumeService
				.getVolumeByMeta(Volumes.MetaEnum.CURRENT_MESSAGE_VOLUME);
		StringBuffer emlPath = new StringBuffer(volume.getPath());
		emlPath.append(path);
		File f = new File(emlPath.toString());
		if (!f.exists()) {
			logger.error("\ntthe folder path of eml[" + messageid + ".eml]:"
					+ emlPath.toString() + "is not exsit!!!");
			try {
				f.mkdirs();
			} catch (Exception e) {
				logger.error("Exception when mkdirs!", e);
			}
		}
		emlPath.append(File.separator);
		emlPath.append(messageid);
		emlPath.append(".zip");
		return emlPath.toString();
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
		String bodyStr = email.getBody().getData();
		if (null != uid) {
			if (!"plain".equals(MIMEType)) {
				bodyStr = bodyStr.replaceAll("\r\n", "<br/>");
				bodyStr = bodyStr.replaceAll("\n", "<br/>");
				bodyStr = bodyStr.replaceAll("\r", "<br/>");
				bodyStr = bodyStr.replaceAll(" ", "&nbsp;");
			}
		}
		return bodyStr;
	}
}
