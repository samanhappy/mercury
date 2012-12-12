package com.dreamail.mercury.petasos.impl.email;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.cache.domain.SeqNoInfo;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.service.AttachmentService;
import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.EmailCacheObject;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailattachment;
import com.dreamail.mercury.domain.WebEmailhead;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.memcached.EmailListCacheManager;
import com.dreamail.mercury.petasos.impl.AbstractFunctionEmail;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.pojo.User_role;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.MethodName;

public class EmailList extends AbstractFunctionEmail {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(EmailList.class);

	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private final static String IMEI_KEY = "IMEI";

	public final static String TIMEDATE_KEY = "TimeDate";

	static {
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	}

	private String uid;

	private User_role userRole;

	HashMap<String, String> meta = null;

	private String oldMailNum;

	private String rangeday;

	private String aidtime;

	private String timeDate;

	private String oTime;

	private int index = 0;

	private int seqno = -1;

	private int subdatano = 0;

	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail) {

		Status status = new Status();

		// 验证请求UID是否存在
		if (pushMail.getCred().getUuid() == null) {
			status = getExpet(status, ErrorCode.CODE_ReceiveEmail_);
			qwertCmd.setObjects(new Status[] { status });
			logger.info("Does not provide access to e-mail users");
			return qwertCmd;
		}

		uid = pushMail.getCred().getUuid();
		UserService userService = new UserService();
		userRole = userService.getUserRoleById(Long.parseLong(uid));

		// 验证用户是否存在
		if (userRole == null || userRole.getUser() == null) {
			status = getExpet(status, ErrorCode.CODE_ReceiveEmail_);
			qwertCmd.setObjects(new Status[] { status });
			logger.info("Does not provide access to e-mail users");
			return qwertCmd;
		}

		// 验证角色是否合法
		if (userRole.getRole() == null
				|| Constant.DISABLE.equals(userRole.getRole().getTitle())) {
			status.setCode(ErrorCode.CODE_USER_ROLE_FAIL);
			status.setMessage("User has been Disabled");
			Object[] objectss = new Object[1];
			objectss[0] = status;
			qwertCmd.setObjects(new Status[] { (Status) objectss[0] });
			return qwertCmd;
		}

		meta = qwertCmd.getMeta();
		status.setCode("0");
		status.setMessage("success");
		Clickoo_user user = userRole.getUser();
		if (meta != null) {
			// 提取meta参数
			String IMEI = (String) meta.get(IMEI_KEY);
			timeDate = meta.get(TIMEDATE_KEY);

			// 验证IMEI是否合法（是否强制下线）
			if (IMEI == null || timeDate == null
					|| !validateIMEI(user, IMEI, status)) {
				Object[] objectss = new Object[1];
				objectss[0] = status;
				qwertCmd.setObjects(new Status[] { (Status) objectss[0] });
				return qwertCmd;
			}
			meta.remove(IMEI_KEY);
			
			// 处理TimeDate
			handleTimeDate();

			// 分包处理获取新邮件列表
			List<Object> objects = getMessagesObject();
			if (objects != null) {
				qwertCmd.setObjects(objects.toArray());
			}
		} else {
			logger.error("meta is null.");
		}
		return qwertCmd;
	}

	/*
	 * 处理TimeDate
	 */
	private void handleTimeDate() {
		String userTimeDate = null;
		if ((userTimeDate = userRole.getUser().getTimedate()) != null) {
			try {
				Date clientTimeDate = formatter.parse(timeDate);
				Date serverTimeDate = formatter.parse(userTimeDate);
				if (clientTimeDate.before(serverTimeDate)) {
					timeDate = userTimeDate;
				}
			} catch (ParseException e) {
			}
		}
	}


	/**
	 * 版本2.0
	 * 
	 * client提供dateTime，服务器检索新邮件，传送给client
	 * 
	 * @param dateTime
	 * @return
	 */
	public List<Object> getMessagesObject() {
		List<Object> results = new ArrayList<Object>();
		MessageService messageService = new MessageService();
		List<Clickoo_message> mailMsgs = null;
		mailMsgs = selectMailList(messageService);
		loadMailHead(results, messageService, mailMsgs, meta,
				EmailUtils.MAX_DATA_LENGTH);
		return results;
	}

	/**
	 * 载入maillist每个包的具体内容
	 * 
	 * @param results
	 *            载入到它
	 * @param messageService
	 *            提供查询具体每封邮件的头信息
	 * @param mailMsgs
	 *            新邮件列表
	 * @param start
	 *            载入开始的index
	 * @param maxMailSize
	 *            邮件列表总大小限制
	 * @return -1为全部载入完毕
	 */
	public void loadMailHead(List<Object> results,
			MessageService messageService, List<Clickoo_message> mailMsgs,
			HashMap<String, String> meta, int maxMailSize) {
		EmailCacheObject mailCache = null;
		WebEmail webEmail = null;
		Date lastDate = null;
		int totalSize = 0;
		int singleSize = 0;
		for (int i = 0; i < mailMsgs.size(); i++) {
			Clickoo_message msg = mailMsgs.get(i);
			mailCache = messageService.getEmailCacheObject(String.valueOf(msg
					.getId()), false);
			if (mailCache != null) {
				webEmail = new WebEmail();
				webEmail.setHead(mailCache.getWebMailHead(String.valueOf(msg
						.getId())));
				webEmail.setAttach(getAttachments(msg.getId()));

				singleSize = EmailUtils.calcEmailPojoSize(webEmail);
				// 如果单封邮件过大，则不处理直接跳过该封邮件
				if (singleSize > maxMailSize) {
					lastDate = msg.getIntime();
					logger.info("jump one big email...");
					continue;
				}

				totalSize += singleSize;
				// 不超过单包最大限制
				if (totalSize < maxMailSize) {
					results.add(webEmail);
					lastDate = msg.getIntime();
				} else {
					break;
				}
			}
		}

		// 处理TimeDate
		if (lastDate != null) {
			meta.put(TIMEDATE_KEY, formatter.format(lastDate));
		}
	}

	private List<Clickoo_message> selectMailList(MessageService messageService) {
		oldMailNum = userRole.getRole().getObjfunction().getLatestEmailNumber();
		rangeday = userRole.getRole().getObjfunction().getAutoCleanupPeriod();
		List<Clickoo_message> mailMsgs = messageService.getAllMessagesByProc(
				uid, oldMailNum, timeDate, rangeday);
		return mailMsgs;
	}

	@Override
	public String getMethodName() {
		return MethodName.EMAIL_LIST;
	}

	/**
	 * 准备参数
	 */
	private void prepareParameters() {
		oldMailNum = userRole.getRole().getObjfunction().getLatestEmailNumber();

		rangeday = userRole.getRole().getObjfunction().getAutoCleanupPeriod();
		if (Constant.ROLE_AUTOCLEANUPPERIOD_NEVER.equals(rangeday)) {
			rangeday = String.valueOf(Constant.Role_IMAP_DEFAULT_DAY);
		}

		timeDate = updateSelectDateByEmailId(timeDate, timeDate);
		timeDate = updateSelectDateByDatetime(userRole.getUser().getTimedate(),
				timeDate);

		if (aidtime != null) {
			index = Integer.parseInt(aidtime.split(";")[0]);
			oTime = aidtime.split(";")[1];
		}

		if (aidtime != null) {
			SeqNoInfo info = EmailListCacheManager.getSeqNoInfo(uid);
			if (info != null && aidtime.equals(info.getAidTime())) {
				seqno = info.getSeqNo();
				logger.info("get seqno from memcached");
			}
		}
	}

	/**
	 * 分包处理获取新邮件列表
	 * 
	 * @param 邮件列表
	 */
	public List<Object> getMessagesObject_Old(Status status) {

		// 为下面的请求准备参数
		prepareParameters();

		// 查询存储过程获得新邮件类别
		List<Clickoo_message> messages = loadMailList(oldMailNum, rangeday,
				uid, timeDate, seqno);

		// 如果没有新邮件，直接返回
		if (messages == null || messages.size() == 0) {
			seqno = finishMailList(meta, oTime, timeDate);
			if (timeDate != null) {
				meta.put(TIMEDATE_KEY, timeDate);
			} else {
				meta.put(TIMEDATE_KEY, userRole.getUser().getTimedate());
			}
			seqno = 0;
			meta.put("SeqNo", String.valueOf(seqno));
			return null;
		}

		LinkedHashMap<Clickoo_message, EmailCacheObject> emailCaches = getEmailCacheObjectsByMessages(
				uid, messages);
		// 如果没有取到邮件，直接返回
		if (emailCaches.size() == 0) {
			if (timeDate != null) {
				meta.put(TIMEDATE_KEY, timeDate);
			} else {
				meta.put(TIMEDATE_KEY, userRole.getUser().getTimedate());
			}
			seqno = 0;
			meta.put("SeqNo", String.valueOf(seqno));
			return null;
		}

		List<Object> objects = new ArrayList<Object>();
		int emailsize = 0;
		index += messages.size() - emailCaches.size();
		Iterator<Entry<Clickoo_message, EmailCacheObject>> iter = emailCaches
				.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Clickoo_message, EmailCacheObject> entry = iter.next();
			Clickoo_message message = (Clickoo_message) entry.getKey();
			// 解析得到WebMail对象
			WebEmail wemail = parseWebEmail(message, (EmailCacheObject) entry
					.getValue());

			// 进行分包处理
			emailsize += EmailUtils.calcEmailPojoSize(wemail);

			// 如果大小没有超过限制，那么直接添加
			if (emailsize < EmailUtils.MAX_DATA_LENGTH) {
				objects.add(wemail);
				index++;
				String aidTimeDate = index + ";"
						+ formatter.format(message.getIntime());
				meta.put("AidTimeDate", aidTimeDate);
			} else {
				// 单封大邮件(包含多附件)的分包
				if (objects.size() == 0) {
					boolean packageDone = EmailUtils.limitEmailPojoSize(wemail,
							subdatano + 1);
					// 判断分包是否结束
					if (packageDone) {
						objects.add(wemail);
						index++;
						String aidTimeDate = index + ";"
								+ formatter.format(message.getIntime());
						meta.put("AidTimeDate", aidTimeDate);
						meta.put("SeqNo", String.valueOf(seqno));
						meta.put("SubDataNo", "0");
						saveSeqNoIntoMemcached(uid, meta.get("AidTimeDate"),
								seqno);
						return objects;
					} else {
						objects.add(wemail);
						// 如果不是第一个分包，基本头信息不再返回
						if (subdatano != 0) {
							wemail.getHead().setSubject(null);
							wemail.getHead().setFrom(null);
							wemail.getHead().setTo(null);
							wemail.getHead().setCc(null);
							wemail.getHead().setBcc(null);
							wemail.getHead().setReceiveTime(null);
						}
						meta.put("SeqNo", String.valueOf(seqno));
						meta.put("SubDataNo", String.valueOf(subdatano + 1));

						if (meta.containsKey("AidTimeDate")
								&& meta.containsKey("SeqNo")) {
							saveSeqNoIntoMemcached(uid,
									meta.get("AidTimeDate"), seqno);
						}
						return objects;
					}
				}
			}
		}

		if (meta.containsKey("AidTimeDate") && meta.containsKey("SeqNo")) {
			saveSeqNoIntoMemcached(uid, meta.get("AidTimeDate"), seqno);
		}

		return objects;
	}

	/**
	 * 解析得到WebEmail对象.
	 * 
	 * @param message
	 * @param emailCache
	 * @return
	 */
	private WebEmail parseWebEmail(Clickoo_message message,
			EmailCacheObject emailCache) {
		WebEmail wemail = new WebEmail();
		WebEmailhead head = new WebEmailhead();
		head.setMessageId(String.valueOf(message.getId()));
		head.setAid(emailCache.getAid());
		try {
			byte[] subject = new byte[0];
			if (emailCache.getSubject() != null) {
				subject = (emailCache.getSubject()).getBytes("UTF-8");
			}
			head.setSubject(EmailUtils.changeByteToBase64(subject));
			head.setFrom(getAccountAddress(emailCache.getEmail_from()));
			head.setTo(getAccountAddress(emailCache.getEmail_to()));
			head.setCc(getAccountAddress(emailCache.getCc()));
			head.setBcc(getAccountAddress(emailCache.getBcc()));

		} catch (UnsupportedEncodingException e) {
			logger.error("exception:", e);
		} catch (Exception e) {
			logger.error("exception:", e);
		}

		if (emailCache.getMail_date() != null) {
			head.setReceiveTime(formatter.format(emailCache.getMail_date()));
		}
		WebEmailattachment[] attachments = getAttachments(message.getId());
		wemail.setHead(head);
		wemail.setAttach(attachments);
		return wemail;
	}

	private int finishMailList(HashMap<String, String> meta, String oTime,
			String selectDate) {
		int seqno;
		if (selectDate != null) {
			Date ntime = null;
			Date otime = null;
			try {
				otime = formatter.parse(selectDate);
				if (oTime != null) {
					ntime = formatter.parse(oTime);
					if (ntime.after(otime)) {
						meta.put(TIMEDATE_KEY, oTime);
						updateTimeDate(userRole.getUser(), oTime);
					} else {
						meta.put(TIMEDATE_KEY, selectDate);
					}
					meta.remove("AidTimeDate");
				} else {
					meta.put(TIMEDATE_KEY, selectDate);
				}
			} catch (ParseException e) {
				logger.error("exception:", e);
			}
		} else {
			if (oTime != null) {
				meta.put(TIMEDATE_KEY, oTime);
				updateTimeDate(userRole.getUser(), oTime);
				meta.remove("AidTimeDate");
			} else {
				meta.put(TIMEDATE_KEY, userRole.getUser().getTimedate());
			}
		}
		seqno = 0;
		meta.put("SeqNo", String.valueOf(seqno));
		return seqno;
	}

	private List<Clickoo_message> loadMailList(String oldMailNum,
			String rangeday, String uid, String selectDate, int seqno) {
		List<Clickoo_message> messages = new ArrayList<Clickoo_message>();
		MessageService messageService = new MessageService();
		if (seqno == 1) {
			messages = messageService.getAllMessagesByProc(uid, oldMailNum,
					selectDate, rangeday);
			EmailListCacheManager.addEmailList(uid, messages);

		} else {
			List<Clickoo_message> msgs = new ArrayList<Clickoo_message>();
			msgs = EmailListCacheManager.getEmailList(uid);
			if (msgs != null) {
				for (int i = 0; i < msgs.size(); i++) {
					messages.add(msgs.get(i));
				}
			}
		}
		return messages;
	}

	private String updateSelectDateByDatetime(String userTimeDate,
			String selectDate) {
		try {
			if (formatter.parse(userTimeDate)
					.after(formatter.parse(selectDate))) {
				selectDate = userTimeDate;
			}
		} catch (ParseException e) {
			logger.warn("failed to parse", e);
		}
		return selectDate;
	}

	private String updateSelectDateByEmailId(String timeDate, String selectDate) {
		if (timeDate.contains(";")) {
			logger.info("introduce mid handle process for:" + timeDate);
			String[] strs = timeDate.split(";");
			selectDate = strs[0];
			String mid = strs[1];
			if (!"".equals(mid) && !"0".equals(mid)) {
				Date intime = new MessageDao().getIntimeByMessageId(mid);
				try {
					if (intime != null
							&& intime.after(formatter.parse(selectDate))) {
						selectDate = formatter.format(intime);
					}
				} catch (ParseException e) {
					logger.warn("failed to parse", e);
				}
			}
		}
		return selectDate;
	}

	private void saveSeqNoIntoMemcached(String uid, String aidTimeDate,
			int seqno) {
		SeqNoInfo info = new SeqNoInfo();
		info.setAidTime(aidTimeDate);
		info.setUid(uid);
		info.setSeqNo(1 + seqno);
		EmailListCacheManager.saveSeqNoInfo(uid, info);
		logger.info("save seqno into memcached...");
	}

	private LinkedHashMap<Clickoo_message, EmailCacheObject> getEmailCacheObjectsByMessages(
			String uid, List<Clickoo_message> messages) {
		LinkedHashMap<Clickoo_message, EmailCacheObject> emailCaches = new LinkedHashMap<Clickoo_message, EmailCacheObject>();
		String mids = "";
		for (int i = 0; i < messages.size(); i++) {
			String mid = String.valueOf(messages.get(i).getId());
			MessageService messageService = new MessageService();
			EmailCacheObject emailCache = null;
			try {
				emailCache = messageService.getEmailCacheObject(mid, false);
			} catch (Exception e) {
				logger.error("mid " + mid + "emailCache is error", e);
				continue;
			}
			if (emailCache != null && emailCache.getAid() != null) {
				emailCaches.put(messages.get(i), emailCache);
			} else {
				logger.error("failed to get message by id:" + mid);
			}
		}
		logger.info(mids);
		if (!"".equals(mids)) {
			JmsSender.sendQueueMsg(mids, "receiveEmailList");
		}
		return emailCaches;
	}

	/**
	 * 去除发送人，收件人，抄送人，密送人中的别名,"<",">"
	 * 
	 * @param 地址
	 * @throws Exception
	 */
	private String getAccountAddress(String add) throws Exception {
		String Add = "";
		StringBuffer sbAdd = new StringBuffer();
		if (add != null) {
			String[] adds = add.split(",");
			for (int x = 0; x < adds.length; x++) {
				if (adds[x] != null && !"".equals(adds[x])) {
					if (adds[x].contains("<") && adds[x].contains(">")) {
						adds[x] = adds[x].substring(adds[x].indexOf("<") + 1,
								adds[x].indexOf(">"));
						sbAdd.append(",");
						sbAdd.append(adds[x]);
					}
				}
			}
			Add = sbAdd.toString();
			if (!"".equals(Add)) {
				Add = Add.substring(1);
			}
		}
		return Add;
	}

	/**
	 * 获取邮件中的附件列表
	 * 
	 * @param 附件列表
	 */
	public WebEmailattachment[] getAttachments(Long mid) {
		ArrayList<WebEmailattachment> attachments = new ArrayList<WebEmailattachment>();
		AttachmentService attachmentService = new AttachmentService();
		List<Clickoo_message_attachment> atts = attachmentService
				.selectAttachmentListByMid(mid);
		if (atts != null) {
			for (int j = 0; j < atts.size(); j++) {
				WebEmailattachment attachment = new WebEmailattachment();
				attachment.setAttid(String.valueOf(atts.get(j).getId()));
				Long prantid = atts.get(j).getParent();
				if (prantid != 0) {
					attachment.setParentid(String.valueOf(prantid));
				}
				byte[] name = new byte[0];
				if (atts.get(j).getName() != null) {
					try {
						name = (atts.get(j).getName()).getBytes("UTF-8");
					} catch (UnsupportedEncodingException e) {
						logger.error("exception:", e);
					}
				}
				attachment.setName(EmailUtils.changeByteToBase64(name));
				attachment.setSize(atts.get(j).getLength());
				attachment.setType(atts.get(j).getType());
				if (atts.get(j).getPath() != null
						&& !"".equals(atts.get(j).getPath())) {
					attachment.setIspath("0");
				} else {
					attachment.setIspath("1");
				}
				attachments.add(attachment);
			}
		}
		WebEmailattachment[] attachs = new WebEmailattachment[attachments
				.size()];
		if (attachments.size() != 0) {
			for (int k = 0; k < attachments.size(); k++) {
				attachs[k] = attachments.get(k);
			}
		}
		return attachs;
	}

	public void updateTimeDate(Clickoo_user user, String selectDate) {
		UserService userService = new UserService();
		user.setTimedate(selectDate);
		userService.updateUser(user);
	}

	/**
	 * EmailContetnt等验证专用（在第一包调用）
	 * 
	 * @param uid
	 * @param IMEI
	 * @param status
	 * @return
	 */
	public boolean validateIMEI(Clickoo_user user, String IMEI, Status status) {
		boolean flag = false;
		if (!IMEI.equalsIgnoreCase(user.getIMEI())) {
			status.setCode(ErrorCode.CODE_USER_IMEI_FAIL);
			status.setMessage("the user has been offline");
		} else {
			flag = true;
		}
		return flag;
	}

}
