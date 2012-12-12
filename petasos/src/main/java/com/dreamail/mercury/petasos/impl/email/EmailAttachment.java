package com.dreamail.mercury.petasos.impl.email;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.mercury.dal.service.AttachmentService;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.TrunOn;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailattachment;
import com.dreamail.mercury.domain.WebEmailhead;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.impl.AbstractFunctionEmail;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.pojo.User_role;
import com.dreamail.mercury.store.impl.HandleStorePathImpl;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.MethodName;
import com.dreamail.mercury.util.StreamUtil;

public class EmailAttachment extends AbstractFunctionEmail implements
		IEmailAttachment {

	@Override
	public String getMethodName() {
		return MethodName.EMAIL_ATTACHMENT;
	}

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(EmailAttachment.class);

	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail) {
		String uid = pushMail.getCred().getUuid();
		HashMap<String, String> meta = qwertCmd.getMeta();
		WebEmail wemail = null;
		WebAccount account = null;
		Object[] emails = qwertCmd.getObjects();
		List<Object> objects = new ArrayList<Object>();
		ArrayList<WebEmail> mails = new ArrayList<WebEmail>();
		Status status = new Status();
		status.setCode("0");
		status.setMessage("success");
		if (uid == null) {
			logger.info("Does not provide access to e-mail users");
			status.setCode(ErrorCode.CODE_ReceiveEmail_);
			status.setMessage("Does not provide access to e-mail users");
			Object[] objectss = new Object[1];
			objectss[0] = status;
			qwertCmd.setObjects(new Status[] { (Status) objectss[0] });
			return qwertCmd;
		}
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
		if (emails == null || emails.length < 1) {
			logger.error("null message");
			status.setCode(ErrorCode.CODE_ReceiveEmail_);
			logger.error("you give wrong message...");
		} else {
			for (int i = 0; i < emails.length; i++) {
				if (emails[i] instanceof WebAccount) {
					account = (WebAccount) emails[i];
				}
				if (emails[i] instanceof WebEmail) {
					wemail = (WebEmail) emails[i];
					mails.add(wemail);
				}
			}
			if (meta != null && account != null) {
				putAttachment(account, mails, uid, status, objects, meta, true);
			}
		}
		objects.add(account);
		qwertCmd.setObjects(objects.toArray());
		pushMail.setCred(null);
		return qwertCmd;
	}

	private void putAttachment(WebAccount account, ArrayList<WebEmail> emails,
			String uid, Status status, List<Object> objects,
			HashMap<String, String> meta, boolean isTxt) {
		for (WebEmail email : emails) {
			if (email.getAttach() != null && email.getAttach().length > 0
					&& email.getAttach()[0].getAttid() != null
					&& email.getAttach()[0].getType() != null) {
				String type = email.getAttach()[0].getType();
				if (email.getAttach()[0].getName() != null) {
					byte[] b = EmailUtils
							.base64TochangeByte(email.getAttach()[0].getName());
					try {
						email.getAttach()[0].setName(new String(b, "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						logger.error("attach name base64 error", e);
					}
				}
//				if (!"".equals(type) && "txt".equalsIgnoreCase(type)) {
//					readTxTAttachment(email, status, objects, meta);
//				} else {
					readPicAttachment(account, email, uid, status, objects,
							meta);
//				}
			}
			break;
		}
	}

	public String subText(byte[] body) {
		String bodystr = null;
		try {
			String str = new String(body, 0, EmailUtils.MTK_ATTACHMENT_LENGTH, "UTF-8");
			str = str.substring(0, str.length() - 1);
			str = str
					+ "\r\n -------------------------------- \r\n As the contents of the annex is too large, please go to the official website for browser...";
			byte[] b = str.getBytes("UTF-8");
			bodystr = EmailUtils.changeByteToBase64(b);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return bodystr;
	}

	@Override
	public void readPicAttachment(WebAccount account, WebEmail email,
			String uid, Status status, List<Object> objects,
			HashMap<String, String> meta) {
		AttachmentService attachmentService = new AttachmentService();
		int seqno = Integer.parseInt((String) meta.get("SeqNo"));
		WebEmail webEmail = new WebEmail();
		WebEmailattachment webAttachment = new WebEmailattachment();
		WebEmailhead head = new WebEmailhead();
		if (email.getAttach() != null && email.getAttach().length > 0
				&& email.getAttach()[0].getAttid() != null) {
			String id = email.getAttach()[0].getAttid();
			String mid = email.getHead().getMessageId();
			Clickoo_message_attachment attachment = null;
			attachment = attachmentService.selectAttachmentById(mid, Long
					.parseLong(id));
			HandleStorePathImpl handle = new HandleStorePathImpl();
			if (attachment != null) {
				String path = handle.getPathById(attachment);
				attachment.setPath(path);
			} else {
				webEmail.setAttach(new WebEmailattachment[] { webAttachment });
				getExpet(status, ErrorCode.CODE_ReceiveEmail_NoAtt,
						"get Attachment path filed....!", objects, webEmail);
				return;
			}
			head.setMessageId(mid);
			webEmail.setHead(head);
			webAttachment.setAttid(id);
			webAttachment.setName(attachment.getName());
			webAttachment.setType(attachment.getType());
			String path = attachment.getPath();
			TrunOn obj = null;
			obj = readAttachmentPic(mid, uid, path, status, seqno);
			if (obj != null) {
				if (!obj.isEndFlag()) {// 说明没有传完
					long length = obj.getBody().length;
					String bodyStr = EmailUtils.changeByteToBase64(obj
							.getBody());
					webAttachment.setBody(bodyStr);
					webAttachment.setSize(length);
					webEmail
							.setAttach(new WebEmailattachment[] { webAttachment });
					objects.add(webEmail);
					objects.add(status);
					return;
				} else {
					long length = obj.getBody().length;
					String bodyStr = EmailUtils.changeByteToBase64(obj
							.getBody());
					webAttachment.setBody(bodyStr);
					webAttachment.setSize(length);
					webEmail
							.setAttach(new WebEmailattachment[] { webAttachment });
					objects.add(webEmail);
					objects.add(status);
				}
			}
		} else {
			webEmail.setAttach(new WebEmailattachment[] { webAttachment });
			objects.add(webEmail);
			status = new Status();
			logger.error("email attachment'path is error ");
			status.setCode(ErrorCode.CODE_ReceiveEmail_NoAtt);
			status.setMessage("email attachment'path is error");
			objects.add(status);
			return;
		}
		seqno = 0;
		meta.put("SeqNo", String.valueOf(seqno));
		objects.add(status);
		return;

	}

	@Override
	public void readTxTAttachment(WebEmail email, Status status,
			List<Object> objects, HashMap<String, String> meta) {
		AttachmentService attachmentService = new AttachmentService();
		WebEmail webEmail = new WebEmail();
		WebEmailattachment webAttachment = new WebEmailattachment();
		String mid = email.getHead().getMessageId();
		String id = email.getAttach()[0].getAttid();
		Clickoo_message_attachment attachment = null;
		attachment = attachmentService.selectAttachmentById(mid, Long
				.parseLong(id));
		long size = email.getAttach()[0].getSize();
		HandleStorePathImpl handle = new HandleStorePathImpl();
		if (attachment != null) {
			String path = handle.getPathById(attachment);
			attachment.setPath(path);
		} else {
			webEmail.setAttach(new WebEmailattachment[] { webAttachment });
			getExpet(status, ErrorCode.CODE_ReceiveEmail_NoAtt,
					"get Attachment path filed....!", objects, webEmail);
			return;
		}
		String filePath = attachment.getPath();
		byte[] body = null;
		if (new File(filePath).exists()) {
			try {
				String encoding = "";
				String txt = "";
				if (0 == attachment.getParent()) {
					logger.info("encoding is:" + encoding);
				}
				if (!"".equals(encoding)) {
					txt = new String(StreamUtil.readFile(filePath), encoding);
				} else {
					txt = StreamUtil.getString(StreamUtil.readFile(filePath));
				}
				body = txt.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (null == body) {
				webEmail.setAttach(new WebEmailattachment[] { webAttachment });
				getExpet(status, ErrorCode.CODE_ReceiveEmail_NoAtt,
						"can't find file when attachment'id is " + id, objects,
						webEmail);
				return;
			}
		} else {
			webEmail.setAttach(new WebEmailattachment[] { webAttachment });
			getExpet(status, ErrorCode.CODE_ReceiveEmail_NoAtt,
					"can't find file when attachment'id is " + id, objects,
					webEmail);
			return;
		}
		if (body != null) {
			String bodystr = null;
			if (body.length > EmailUtils.MTK_ATTACHMENT_LENGTH) {
				bodystr = subText(body); // 获取10KB的数据
			} else {
				bodystr = EmailUtils.changeByteToBase64(body);
			}
			size = body.length;
			WebEmailhead head = new WebEmailhead();
			head.setMessageId(mid);
			webEmail.setHead(head);
			webAttachment.setAttid(id);
			webAttachment.setName(attachment.getName());
			webAttachment.setType(attachment.getType());
			webAttachment.setBody(bodystr);
			webAttachment.setSize(size);
			webEmail.setAttach(new WebEmailattachment[] { webAttachment });
			objects.add(webEmail);
			objects.add(status);
			meta.put("SeqNo", "0");
		}
	}
}
