package com.dreamail.mercury.sendMail.email.impl;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.MessageSender;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.dao.AttachmentDao;
import com.dreamail.mercury.dal.dao.SendFailedMessageDao;
import com.dreamail.mercury.dal.dao.SendMessageDao;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.dal.service.AttachmentService;
import com.dreamail.mercury.dal.service.DataService;
import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.domain.EmailCacheObject;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailattachment;
import com.dreamail.mercury.domain.WebEmailbody;
import com.dreamail.mercury.domain.WebEmailhead;
import com.dreamail.mercury.mail.receiver.attachment.picture.IPictureHandle;
import com.dreamail.mercury.mail.receiver.attachment.picture.impl.PictureHandleImpl;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.pojo.Clickoo_message_data;
import com.dreamail.mercury.pojo.Clickoo_send_failed_message;
import com.dreamail.mercury.pojo.Clickoo_send_message;
import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserProvide;
import com.dreamail.mercury.sendMail.email.IEmailSend;
import com.dreamail.mercury.sendMail.sender.TransmitOperation;
import com.dreamail.mercury.store.impl.HandleStorePathImpl;
import com.dreamail.mercury.util.AttachmentSupport;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.JsonUtil;
import com.dreamail.mercury.util.MailBoxDispatcher;
import com.dreamail.mercury.util.StreamUtil;
import com.dreamail.mercury.util.WebAccountUtil;
import com.microsoft.ews.client.EWSSendClient;

public class EmailSend implements IEmailSend {
	private static Logger logger = LoggerFactory.getLogger(EmailSend.class);

	/**
	 * 发送邮件入口
	 */
	public void emailSendEntrance(String uid, String mid, String sendMailType,
			String oldmid, String hid) {
		logger.info("\nEmailSend :uid [" + uid + "],mid [" + mid
				+ "],sendMailType [" + sendMailType + "],oldmid [" + oldmid
				+ "],hid [" + hid + "]--------------");
		// 发送邮件类型：0,发送邮件；1,回复邮件；2,收件箱邮件转发；3,发件箱邮件转发；
		if (Constant.COMMON_MAIL.equals(sendMailType)) {
			commonMail(uid, mid, oldmid, hid, Constant.COMMON_MAIL);
		} else if (Constant.REPLAY_MAIL.equals(sendMailType)) {
			replyMail(uid, mid, oldmid, hid, Constant.REPLAY_MAIL);
		} else if (Constant.INBOX_FORWORD.equals(sendMailType)) {
			inBoxForWord(uid, mid, oldmid, hid, Constant.INBOX_FORWORD);
		} else if (Constant.SENDBOX_FORWORD.equals(sendMailType)) {
			sendBoxForWord(uid, mid, oldmid, hid, Constant.SENDBOX_FORWORD);
		} else {
			logger.error("wrong send type!!!");
		}
	}

	@Override
	public void commonMail(String uid, String mid, String oldmid, String hid,
			String type) {
		logger.info("Into EmailSend commonMail furnction!!");
		Clickoo_send_message clickooMessage = getSendMsgByMid(mid);
		if (clickooMessage == null) {
			logger.error("Get Clickoo_message by mid:" + mid + "failed!!");
			return;
		}
		EmailParserProvide emailParserProvide = new EmailParserProvide();
		AccountDao accountDao = new AccountDao();
		Clickoo_mail_account clickooMailAccount = accountDao
				.getAccountById(Long.parseLong(clickooMessage.getAid()));
		if (clickooMailAccount == null) {
			logger.error("Get clickooMailAccount by aid:"
					+ clickooMessage.getAid() + "failed!!");
			return;
		}
		try {
			Email email = emailParserProvide.getEmail(clickooMailAccount
					.getName(), TransmitOperation.getEmlPath(
					clickooMessage.getPath(),
					String.valueOf(clickooMessage.getId())));
			logger.info("CommonMail mid:" + mid + "eml:" + email.toString());
			WebEmail webEmail = transformToWenEmail(email, clickooMailAccount,
					clickooMessage, mid, oldmid);
			checkSendType(webEmail, clickooMessage, mid, uid,
					clickooMessage.getAid(), oldmid, hid, type);
		} catch (MessagingException e) {
			logger.error("Fail to get email from volume!");
		}
	}

	@Override
	public void inBoxForWord(String uid, String mid, String oldmid, String hid,
			String type) {
		logger.info("\ninto EmailSend inBoxForWord function!!");
		// 收件箱转发去读取转发的邮件，添加附件
		Clickoo_send_message clickooMessage = getSendMsgByMid(mid);
		if (clickooMessage == null) {
			logger.error("\nget Clickoo_message by mid:" + mid + "failed!!");
			return;
		}
		EmailParserProvide emailParserProvide = new EmailParserProvide();
		AccountDao accountDao = new AccountDao();
		Clickoo_mail_account clickooMailAccount = accountDao
				.getAccountById(Long.parseLong(clickooMessage.getAid()));
		if (clickooMailAccount == null) {
			logger.error("\nget clickooMailAccount by aid:"
					+ clickooMessage.getAid() + "failed!!");
			return;
		}
		// 从volume中取出eml
		try {
			Email email = emailParserProvide.getEmail(clickooMailAccount
					.getName(), TransmitOperation.getEmlPath(
					clickooMessage.getPath(),
					String.valueOf(clickooMessage.getId())));
			logger.info("\ninBoxForWord new mid:" + mid + "eml:"
					+ email.toString());
			/*
			 * 此处不再调用dealBody(emailCache, email, uid, mid, oldmid)处理正文
			 */

			int m = 0;
			// 协议中添加了几个附件
			if (email.getAttachList() != null) {
				m = email.getAttachList().size();
			}
			List<Clickoo_message_attachment> atts = null;
			WebEmail webEmail = transformToWenEmail(email, clickooMailAccount,
					clickooMessage, mid, oldmid);
			WebEmailattachment[] webAttachments = null;
			EmailCacheObject emailCache = new MessageService()
					.getEmailCacheObject(oldmid, false);

			if (emailCache != null) {
				// 获取缓存中邮件附件
				logger.info("emailCache is no null............");
				atts = new AttachmentService()
						.selectOriginalAttachmentListByMid(emailCache,
								Long.parseLong(oldmid));
			} else {
				logger.error("email is not exist!!!!!");
			}
			if (atts != null && atts.size() != 0) {
				webAttachments = new WebEmailattachment[atts.size() + m];
				for (int i = 0; i < atts.size(); i++) {
					if (atts.get(i) != null) {
						webAttachments[i] = retriveOldAtt(atts.get(i), uid,
								oldmid);
					}
				}
				for (int j = 0; j < m; j++) {
					webAttachments[atts.size() + j] = webEmail.getAttach()[j];
				}
			} else {
				webAttachments = new WebEmailattachment[m];
				for (int j = 0; j < m; j++) {
					webAttachments[j] = webEmail.getAttach()[j];
				}
			}

			webEmail.setAttach(webAttachments);
			checkSendType(webEmail, clickooMessage, mid, uid,
					clickooMessage.getAid(), oldmid, hid, type);
		} catch (MessagingException e) {
			logger.error("\nfail to get " + mid + ".eml from volume!");
		}

	}

	/**
	 * 自动下载没有body的附件.
	 * 
	 * @param email
	 */
	public void autoDownloadAtt(WebEmail email) {
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
						att.setName(cma.getName());
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

	/**
	 * 获取转发时的附件.
	 * 
	 * @return
	 */
	private WebEmailattachment retriveOldAtt(Clickoo_message_attachment att,
			String uid, String oldmid) {
		byte[] body = null;
		HandleStorePathImpl handle = new HandleStorePathImpl();
		WebEmailattachment webAttachment = new WebEmailattachment();
		webAttachment.setAttid(String.valueOf(att.getId()));
		webAttachment.setName(att.getName());
		webAttachment.setSize(att.getLength());
		webAttachment.setType(att.getType());
		String path = handle.getPathById(
				Long.valueOf(webAttachment.getAttid()), uid, oldmid);
		logger.info("\naid:" + webAttachment.getAttid() + "-----uid:" + uid
				+ "----mid:" + oldmid + "------path:" + path);
		if (path != null) {
			if (AttachmentSupport.isImageType(webAttachment.getType())
					&& att.getPath() != null) {
				body = readAttachmentPic(webAttachment.getAttid(), uid, path);
			} else if (att.getPath() != null) {
				body = StreamUtil.readFile(path);
			}
		} else {
			logger.error("can't find file when attachment'id is " + att.getId());
		}
		if (body != null) {
			webAttachment.setBody(EmailUtils.changeByteToBase64(body));
		} else {
			logger.error("can't find file when attachment'id is " + att.getId());
		}
		return webAttachment;
	}

	@Override
	public void replyMail(String uid, String mid, String oldmid, String hid,
			String type) {
		// 回复邮件去emcache中取旧邮件，去volume中取新邮件
		logger.info("\ninto EmailSend replyMail function!!");
		Clickoo_send_message clickooMessage = getSendMsgByMid(mid);
		EmailParserProvide emailParserProvide = new EmailParserProvide();
		AccountDao accountDao = new AccountDao();
		Clickoo_mail_account clickooMailAccount = accountDao
				.getAccountById(Long.parseLong(clickooMessage.getAid()));

		Email email = null;
		try {
			email = emailParserProvide.getEmail(clickooMailAccount.getName(),
					TransmitOperation.getEmlPath(clickooMessage.getPath(),
							String.valueOf(clickooMessage.getId())));

			// 从缓存中取旧邮件
			EmailCacheObject emailCacheObject = new MessageService()
					.getEmailCacheObject(oldmid, false);
			try {
				dealBody(emailCacheObject, email, uid, mid, oldmid);
			} catch (UnsupportedEncodingException e1) {
				logger.error("failed to deal body", e1);
			}

			// 发送
			WebEmail webEmail = transformToWenEmail(email, clickooMailAccount,
					clickooMessage, mid, oldmid);
			checkSendType(webEmail, clickooMessage, mid, uid,
					clickooMessage.getAid(), oldmid, hid, type);
		} catch (MessagingException e) {
			logger.error("\nexception when get email from eml :" + e);
		}

	}

	@Override
	public void sendBoxForWord(String uid, String mid, String oldmid,
			String hid, String type) {
		logger.info("\ninto EmailSend sendBoxForWord function!!");
		// 根据mid和oldmid分别去硬盘取出
		Email email = null;
		Email oldEmail = null;
		Clickoo_send_message clickooMessage = getSendMsgByMid(mid);
		Clickoo_send_message oldClickooMessage = getSendMsgByMid(oldmid);
		Clickoo_mail_account clickooMailAccount = new AccountDao()
				.getAccountById(Long.parseLong(clickooMessage.getAid()));
		EmailParserProvide emailParserProvide = new EmailParserProvide();
		try {
			email = emailParserProvide.getEmail(clickooMailAccount.getName(),
					TransmitOperation.getEmlPath(clickooMessage.getPath(),
							String.valueOf(clickooMessage.getId())));

			oldEmail = emailParserProvide.getEmail(
					clickooMailAccount.getName(), TransmitOperation.getEmlPath(
							oldClickooMessage.getPath(),
							String.valueOf(oldClickooMessage.getId())));

			EmailCacheObject emailCacheObject = JsonUtil.getInstance()
					.parseEmail(String.valueOf(clickooMailAccount.getId()),
							email);
			// 对邮件进行整合
			// 邮件正文处理
			try {
				dealBody(emailCacheObject, email, uid, mid, oldmid);
			} catch (UnsupportedEncodingException e1) {
				logger.error("failed to deal body", e1);
			}

			List<Clickoo_message_attachment> oldlist = oldEmail.getAttachList();
			List<Clickoo_message_attachment> list = email.getAttachList();
			if (oldlist != null) {
				for (int i = 0; i < oldlist.size(); i++) {
					list.add(oldlist.get(i));
				}
			}
			email.setAttachList(list);
		} catch (MessagingException e) {
			logger.error("\nGet email from volume exception:" + e);
			return;
		}
		WebEmail webEmail = transformToWenEmail(email, clickooMailAccount,
				oldClickooMessage, mid, oldmid);
		checkSendType(webEmail, clickooMessage, mid, uid,
				clickooMessage.getAid(), oldmid, hid, type);
	}

	/**
	 * 判断邮件发送方式
	 * 
	 * @param email
	 * @param uid
	 */
	@Override
	public boolean checkSendType(WebEmail email,
			Clickoo_send_message clickooMessage, String mid, String uid,
			String aid, String oldmid, String hid, String type) {
		boolean state = false;
		if (email.getHead() != null && email.getHead().getMessageId() != null
				&& email.getHead().getMessageId().trim().equals("")) {
			email.getHead().setMessageId(mid);
			logger.info("save mid to web email");
		}
		if (email.getAccount().getSendProtocolType()
				.equalsIgnoreCase(Constant.SMTP)) {
			logger.info("use smtp protocol to send email...");
			state = new TransmitOperation().sendMail(email, uid, type);
		} else if (email.getAccount().getSendProtocolType()
				.equalsIgnoreCase(Constant.HTTP)) {
			logger.info("use http protocol to send email...");
			state = new EWSSendClient().sendEmail(email, uid);
		}
		if (state) {
			logger.info("Send email success");
			clickooMessage.setIntime(new Date());
			clickooMessage.setStatus(Constant.MAIL_SENT_SUCCEESS_STATUS);
			new SendMessageDao().updateSendMessage(clickooMessage);
			MessageSender.sendMsgToUPE(uid, mid,
					JMSConstans.JMS_SENDMAIL_SUCCESS_VALUE, hid);
		} else {
			logger.info(">>>>>>>>>>>>>>>>>>Fail to send email<<<<<<<<<<<<<<<<<");
			SendFailedMessageDao sfmo = new SendFailedMessageDao();
			Clickoo_send_failed_message failedMessage = sfmo
					.selectSendFailedMessageByMd(mid);
			if (failedMessage == null) {
				// 保存失败邮件的jms信息到数据库以便重发
				logger.info("Save failed message into db!");
				failedMessage = new Clickoo_send_failed_message();
				failedMessage.setMid(Long.parseLong(mid));
				if (oldmid != null && !"".equals(oldmid)) {
					failedMessage.setOldmid(Long.parseLong(oldmid));
				}
				failedMessage.setUid(Long.parseLong(uid));
				failedMessage.setSendtype(Integer.parseInt(type));
				failedMessage.setHid(Long.parseLong(hid));
				failedMessage.setQeuetype(MailBoxDispatcher
						.getSendMailQueueByType(email.getHead().getFrom()));
				sfmo.saveSendFailedMessage(failedMessage);
			} else {
				int retrycount = failedMessage.getRetrycount() + 1;
				logger.info(">>>>>Retry<<<<<now retrycount is ["
						+ retrycount
						+ "] and retry_times is ["
						+ Integer.parseInt(PropertiesDeploy.getConfigureMap()
								.get("retry_times")) + "]");
				if (retrycount >= Integer.parseInt(PropertiesDeploy
						.getConfigureMap().get("retry_times"))) {
					logger.info("After retry " + retrycount
							+ " times , also failed to send mail to : "
							+ email.getHead().getFrom());
					sfmo.deleteSendFailedMessageByMid(mid);

					// 通知UPE发送失败
					clickooMessage.setIntime(new Date());
					clickooMessage.setStatus(Constant.MAIL_SENT_FAIL_STATUS);
					new SendMessageDao().updateSendMessage(clickooMessage);
					MessageSender.sendMsgToUPE(uid, mid,
							JMSConstans.JMS_SENDMAIL_FAIL_VALUE, hid);
				} else {
					// 更新重发次数
					logger.info("Update Retry send mail times :" + retrycount
							+ " times!");
					failedMessage.setRetrycount(retrycount);
					sfmo.updateSentFailedMessageCount(failedMessage.getId(),
							retrycount);
				}
			}
		}
		return state;
	}

	/**
	 * 正文处理
	 * 
	 * @param emailCacheObject
	 * @param email新的email
	 * @param uid
	 * @param mid
	 * @param oldmid
	 * @throws UnsupportedEncodingException
	 */
	private void dealBody(EmailCacheObject emailCacheObject, Email email,
			String uid, String mid, String oldmid)
			throws UnsupportedEncodingException {
		String text = null;
		Clickoo_message_data data = null;
		if (emailCacheObject != null) {
			// 在缓存未失效时不下载,直接获取正文内容
			DataService dataDao = new DataService();
			logger.info("\nget body from emailCacheObject!");
			data = dataDao.selectDataById(Long.parseLong(oldmid),
					emailCacheObject);
			if (data != null) {
				text = data.getData();
			} else {
				logger.error("data in database is null");
			}
		} else {
			logger.error("email is not exist!!!!!");
		}

		// 邮件正文处理
		StringBuffer bodyStr = new StringBuffer();
		if (uid != null) {
			if (email.getBody().length > 0 && email.getBody() != null) {
				bodyStr.append(new String(email.getBody(), HTTP.UTF_8));
			}
			if (text != null) {
				bodyStr.append(Constant.SPILIT_LINE);
				if (emailCacheObject != null) {
					bodyStr.append("From: ")
							.append(emailCacheObject.getEmail_from())
							.append(Constant.SPILIT_NEXT_LINT);
					bodyStr.append("To: ")
							.append(emailCacheObject.getEmail_to())
							.append(Constant.SPILIT_NEXT_LINT);
					if (emailCacheObject.getCc() != null
							&& !emailCacheObject.getCc().trim().equals("")) {
						bodyStr.append("CC: ").append(emailCacheObject.getCc())
								.append(Constant.SPILIT_NEXT_LINT);
					}
					if (emailCacheObject.getMail_date() != null) {
						bodyStr.append(
								"Time: "
										+ new SimpleDateFormat(
												"yyyy-MM-dd HH:mm")
												.format(emailCacheObject
														.getMail_date()))
								.append(Constant.SPILIT_NEXT_LINT);
					}
					bodyStr.append("Subject: ").append(
							emailCacheObject.getSubject());
				}
				bodyStr.append(Constant.SPILIT_LINE);
				bodyStr.append(text);
				logger.info("add email content for reply");
			} else {
				logger.info("there is no email content for messageId: "
						+ email.getMessageId());
			}
		} else {
			logger.error("\nuid is null!");
		}
		email.setBody(bodyStr.toString().getBytes(HTTP.UTF_8));
	}

	/**
	 * 将email转化成WebEmail
	 * 
	 * @param email
	 * @param clickooMailAccount
	 * @param clickooMessage
	 * @return
	 */
	@Override
	public WebEmail transformToWenEmail(Email email,
			Clickoo_mail_account clickooMailAccount,
			Clickoo_send_message clickooMessage, String newmid, String oldmid) {
		WebEmail webEmail = new WebEmail();
		WebAccount webAccount = new WebAccount();
		webAccount.setId(clickooMailAccount.getId());
		webAccount = WebAccountUtil.getAccountByContext(clickooMailAccount,
				webAccount);
		webEmail.setAccount(webAccount);
		WebEmailhead head = new WebEmailhead();// 邮件主体
		head.setMessageId(newmid);
		head.setMessageId_new(newmid);
		head.setMessageId_old(oldmid);
		head.setSubject(email.getSubject()); // 标题
		head.setFrom(dealString(email.getFrom())); // 发件人
		head.setTo(dealString(email.getTo())); // 收件人
		head.setCc(dealString(email.getCc())); // 抄送人
		head.setBcc(dealString(email.getBcc())); // 密送人
		head.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(email
				.getSendTime()));
		head.setMessageId_new(String.valueOf(clickooMessage.getId()));
		webEmail.setHead(head);

		WebEmailbody body = new WebEmailbody();// 邮件正文
		String bodyStr = null;
		try {
			bodyStr = new String(email.getBody(), HTTP.UTF_8);
			body.setData(bodyStr); // 文章内容
			body.setSize(bodyStr.length()); // 文章长度
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		webEmail.setBody(body);
		// 邮件附件
		List<Clickoo_message_attachment> list = email.getAttachList();
		WebEmailattachment[] attach = new WebEmailattachment[list.size()];

		String attBodyString = null;
		logger.info("start handle atts...");
		for (int i = 0; i < list.size(); i++) {
			attach[i] = new WebEmailattachment();
			Clickoo_message_attachment attachment = list.get(i);
			if (attachment.getIn() == null && attachment.getId() > 0) {
				// 下载有ID而没有内容的附件
				logger.info("load att from database for att:"
						+ attachment.getId());
				Clickoo_message_attachment cma = new AttachmentDao()
						.selectAttachmentById(attachment.getId());
				if (cma != null && cma.getVolume() != null) {
					attach[i].setAttid(String.valueOf(cma.getId()));
					attach[i].setName(cma.getName());
					attach[i].setSize(cma.getLength());
					attach[i].setType(cma.getType());
					attach[i]
							.setBody(EmailUtils.changeByteToBase64(StreamUtil
									.readFile(cma.getVolume().getPath()
											+ cma.getPath())));
				} else {
					logger.error("cann't get this att...");
				}
			} else {
				attach[i].setName(attachment.getName()); // 附件名字
				attach[i].setType(attachment.getType()); // 附件类型
				attach[i].setSize(attachment.getLength()); // 附件大小
				if (attBodyString != null) {
					attBodyString = null;
				}
				attBodyString = EmailUtils.changeByteToBase64(attachment
						.getIn());
				attach[i].setBody(attBodyString); // 附件内容
				logger.info("att for name " + attachment.getName()
						+ " is ok...");
			}
		}
		webEmail.setAttach(attach);
		webEmail.setPath(clickooMessage.getPath());
		return webEmail;
	}

	private String dealString(String str) {
		str = str.replaceAll("<", "");
		str = str.replaceAll(">", "");
		return str;
	}

	public byte[] readAttachmentPic(String id, String uid, String path) {
		IPictureHandle picHandle = new PictureHandleImpl();
		String model = new UserDao().getDeviceModelByUid(uid);
		double[] size = new double[2];
		byte[] pic = null;
		if (model != null) {
			size[0] = Double.parseDouble(model.split(",")[0]);
			size[1] = Double.parseDouble(model.split(",")[1]);
			try {
				pic = picHandle.handelPicutre(path, size, 1);
			} catch (Exception e) {
				logger.error("can't find file when attachment'id is " + id, e);
			}
		} else {
			logger.warn("no device opposite attachmentId:" + id);
		}
		return pic;
	}

	/**
	 * 
	 * @param mid
	 * @return
	 */
	public Clickoo_send_message getSendMsgByMid(String mid) {
		return new SendMessageDao().selectSendMessageById(Long.parseLong(mid));
	}
}
