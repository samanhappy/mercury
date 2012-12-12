package com.dreamail.mercury.petasos.impl.email;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.dal.service.AttachmentService;
import com.dreamail.mercury.dal.service.DataService;
import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.EmailCacheObject;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailbody;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.impl.AbstractFunctionEmail;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.pojo.Clickoo_message_data;
import com.dreamail.mercury.pojo.User_role;
import com.dreamail.mercury.store.impl.HandleStorePathImpl;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.MethodName;
import com.dreamail.mercury.util.StreamUtil;
import com.dreamail.mercury.util.WebAccountUtil;

public class EmailContent extends AbstractFunctionEmail implements
		IEmailContent {

	@Override
	public String getMethodName() {
		return MethodName.EMAIL_CONTENT;
	}

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(EmailContent.class);

	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		String uid = pushMail.getCred().getUuid();
		HashMap<String, String> meta = qwertCmd.getMeta();
		Object[] emails = qwertCmd.getObjects();
		ArrayList<WebEmail> wemails = new ArrayList<WebEmail>();
		WebEmail wemail = null;
		WebAccount account = null;
		List<Object> objects = new ArrayList<Object>();
		Status status = new Status();
		status.setCode("0");
		status.setMessage("success");
		String IMEI = (String) meta.get("IMEI");
		int seqno = Integer.parseInt((String) meta.get("SeqNo"));
		if (seqno == 1) {
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
		}
		meta.remove("IMEI");
		if (emails == null || emails.length < 1) {
			logger.error("null message");
			status.setCode(ErrorCode.CODE_ReceiveEmail_);
			status.setMessage("you give wrong message...");
			objects.add(status);
		} else {
			for (int i = 0; i < emails.length; i++) {
				if (emails[i] instanceof WebAccount) {
					account = (WebAccount) emails[i];
				}
				if (emails[i] instanceof WebEmail) {
					wemail = (WebEmail) emails[i];
					wemails.add(wemail);
				}
			}
			WebAccount acc = new WebAccount();
			acc.setId(account.getId());
			objects.add(acc);

			AccountService accountService = new AccountService();
			Clickoo_mail_account mail_account = accountService
					.getAccountByAid(account.getId());
			if (mail_account == null) {
				logger.error("account is not exist");
				status.setCode(ErrorCode.CODE_EmailAccount_No_Exist);
				status.setMessage("account is not exist...");
				objects.add(status);
				qwertCmd.setObjects(objects.toArray());
				pushMail.setCred(null);
				return qwertCmd;
			}
			account = WebAccountUtil.getAccountByContext(mail_account, account);
			account.setName(mail_account.getName());
			try {
				objects = putBodyContent(account, wemails, uid, status,
						objects, meta);
			} catch (UnsupportedEncodingException e) {
				logger.error("Convert byte stream error", e);
			}
		}
		qwertCmd.setObjects(objects.toArray());
		pushMail.setCred(null);
		return qwertCmd;
	}

	@Override
	public String getAttachmentBody(long parseLong, String uid,
			EmailCacheObject emailCache) {
		AttachmentService attachmentService = new AttachmentService();
		StringBuffer sbData = new StringBuffer();
		HandleStorePathImpl handle = new HandleStorePathImpl();
		List<Clickoo_message_attachment> attachments = attachmentService
				.selectAttachmentListByMid(parseLong, emailCache);
		if (attachments != null) {
			for (Clickoo_message_attachment att : attachments) {
				if ("txt".equalsIgnoreCase(att.getType())) {
					sbData.append(Constant.SPILIT_LINE);
					sbData.append("The following is the content of Attachment \"");
					String encoding = "";
					String txt = "";
					if (att.getParent() == 0) {
						sbData.append(att.getName() + "." + att.getType());
					} else {
						Clickoo_message_attachment attachment = attachmentService
								.selectAttachmentById(emailCache,
										String.valueOf(parseLong),
										att.getParent());
						sbData.append(attachment.getName() + "."
								+ attachment.getType());
					}
					String path = handle.getPathById(att.getId(), uid,
							String.valueOf(parseLong));
					try {
						if (!"".equals(encoding)) {
							txt = new String(StreamUtil.readFile(path),
									encoding);
						} else {
							txt = StreamUtil.getString(StreamUtil
									.readFile(path));
						}
						sbData.append("\"\n").append(txt);
					} catch (UnsupportedEncodingException e) {
						logger.error("unsupport encode", e);
					}
					return sbData.toString();
				}
			}
		}
		return "";
	}

	@Override
	public List<Object> putBodyContent(WebAccount account,
			ArrayList<WebEmail> wemails, String uid, Status status,
			List<Object> objects, HashMap<String, String> meta)
			throws UnsupportedEncodingException {
		int seqno = Integer.parseInt((String) meta.get("SeqNo"));
		int maxsize = 0;
		for (int i = 0; i < wemails.size(); i++) {
			String id = wemails.get(i).getHead().getMessageId();
			if (wemails.get(i).getHead() != null && id != null) {
				String text = null;
				Clickoo_message_data data = null;
				WebEmail webEmail = new WebEmail();
				WebEmailbody webBody = new WebEmailbody();
				String attachStr = null;
				// 根据邮件主键去emailCache中获取该邮件
				MessageService messageService = new MessageService();
				EmailCacheObject emailCache = null;
				try {
					emailCache = messageService
							.getEmailCacheObject(id, true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 根据获得的EmailCacheObject是否为空判断缓存是否失效
				if (emailCache != null) {
					// 在缓存未失效时不下载,直接获取正文内容
					logger.info("Get emailCacheObject successfully!Then get emailbody from emailCacheObject!");
					data = new DataService().selectDataById(Long.parseLong(id),
							emailCache);
					attachStr = getAttachmentBody(Long.parseLong(id), uid,
							emailCache);
					text = data.getData();
				}
				int size = 0;
				String datastr = "";
				if (text != null) {
					String content = text + attachStr;
					byte[] body = content.getBytes("UTF-8");
					if (body.length > EmailUtils.MAX_BODY_LENGTH) {
						String str = new String(body, 0, EmailUtils.MAX_BODY_LENGTH, "UTF-8");
						String hintmessage = "\n[Wow! This email is too long for your PushMail. You can view original mail on your computer.]";
						datastr = str.substring(0,
								str.length() - hintmessage.length())
								+ hintmessage;
						size = (int) datastr.length();
					} else {
						datastr = text + attachStr;
						size = (int) datastr.length();
					}
				} else {
					status = new Status();
					webBody.setBodyid(id);
					webEmail.setBody(webBody);
					objects.add(webEmail);
					logger.error("emailbody'id is error,couldn't found body when id is "
							+ id);
					status.setCode(ErrorCode.CODE_ReceiveEmail_NoBody);
					status.setMessage("emailbody'id is error,couldn't found body when id is "
							+ id);
					objects.add(status);
				}
				int max_data_length = EmailUtils.MTK_DATA_LENGTH;
				logger.info(""+text);
				logger.info(""+attachStr);
				logger.info(""+datastr);
				logger.info("seqno is "+seqno +" ;max data size is "+max_data_length +"size is "+size);
				if (size == 0) {
					webBody.setBodyid(id);
					webBody.setData(datastr);
					webEmail.setBody(webBody);
					objects.add(webEmail);
				} else {
					maxsize += size;
					logger.info("seqno is "+seqno +" ;max data size is "+max_data_length +"size is "+maxsize);
					if (maxsize > (seqno - 1) * max_data_length
							&& maxsize <= seqno * max_data_length) {
						webBody.setBodyid(id);
						int n = maxsize - (seqno - 1) * max_data_length;
						if (size > n) {
							byte[] newbody = datastr.substring(size - n, size)
									.getBytes("UTF-8");
							String str = EmailUtils.changeByteToBase64(newbody);
							webBody.setData(str);
						} else {
							byte[] newbody = datastr.getBytes("UTF-8");
							String str = EmailUtils.changeByteToBase64(newbody);
							logger.info("base:"+str);
							webBody.setData(str);
						}
						webEmail.setBody(webBody);
						objects.add(webEmail);
					} else if (maxsize > seqno * max_data_length) {
						webBody.setBodyid(id);
						int n = maxsize - seqno * max_data_length;
						if ((size - n) > max_data_length) {
							int srcPos = (size - n) - max_data_length;
							byte[] newbody = datastr.substring(srcPos,
									srcPos + max_data_length).getBytes("UTF-8");

							String str = EmailUtils.changeByteToBase64(newbody);
							webBody.setData(str);
						} else {
							byte[] newbody = datastr.substring(0, size - n)
									.getBytes("UTF-8");
							String str = EmailUtils.changeByteToBase64(newbody);
							webBody.setData(str);
						}
						webEmail.setBody(webBody);
						objects.add(webEmail);
						objects.add(status);
						return objects;
					}
				}

			} else {
				logger.error("null body message");
				status.setCode(ErrorCode.CODE_ReceiveEmail_NoBody);
				status.setMessage("you give wrong body message...");
				objects.add(status);
			}
		}
		seqno = 0;
		meta.put("SeqNo", String.valueOf(seqno));
		objects.add(status);
		return objects;
	}
}
