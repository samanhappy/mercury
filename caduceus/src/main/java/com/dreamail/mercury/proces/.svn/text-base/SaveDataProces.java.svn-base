/**
 * 
 */
package com.dreamail.mercury.proces;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.dal.dao.AttachmentDao;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.domain.EmailCacheObject;
import com.dreamail.mercury.memcached.EmailCacheManager;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.pojo.Clickoo_message_data;
import com.dreamail.mercury.util.JsonUtil;

/**
 * 数据新增处理.
 * 
 * @author meng.sun
 */
public class SaveDataProces implements IProces {

	/**
	 * logger instance.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(SaveDataProces.class);

	@Override
	public void doProces(Context context) {
		if (context.isState()) {
			logger.info("larger Message save Date!!!!!!!!!!!!!!!!!!!!");
		}
		logger.info("start to save data process");
		// 查询数据库账号对应的uid，更新到context中
		logger.info("account:" + context.getLoginName() + " status is "
				+ context.getStatus());
		// 判断是否有普通邮件
		if (context.getEmailList() == null || context.getEmailList().size() < 1) {
			logger.info("there is no email to save");
		} else {
			for (String mid : context.getEmailList().keySet()) {
				Email email = context.getEmailList().get(mid);
				try {
					Clickoo_message message = parseMessage(context, email);
					message.setId(Long.parseLong(mid));
					Clickoo_message_data data = parseData(email);
					if (data.getData() == null) {
						data.setSize(0);
					}
					parseAttachChildID(email);
					List<Clickoo_message_attachment> attachList = parseAttachList(email);
					message.setAttachList(attachList);
					message.setMessageData(data);

					logger.info("-----------mid:" + mid + "-----------subject:"
							+ email.getSubject());
					String aid = String.valueOf(context.getAccountId());
					EmailCacheObject emailCache = JsonUtil.getInstance()
							.parseEmail(aid, email);
					EmailCacheManager.addEmail(String.valueOf(mid), emailCache,
							-1);

					MessageDao messageDao = new MessageDao();
					// 更新message信息,保存消息体以及attachList信息
					logger.info("----------- insert message:" + aid);
					messageDao.createMessageById(message);
					logger.info("----------- insert message end:" + aid);
				} catch (Exception e) {
					logger.error(
							"failed to save message and body data and attachlist",
							e);
				}
			}
		}
		logger.info("end to save data process");
	}

	private void parseAttachChildID(Email email) throws Exception {
		if (email.getnAttachList() != null && email.getnAttachList().size() > 0) {
			for (int i = 0; i < email.getAttachList().size(); i++) {
				if (email.getAttachList().get(i).getChildList() != null
						&& email.getAttachList().get(i).getChildList().size() > 0) {
					for (int j = 0; j < email.getAttachList().get(i)
							.getChildList().size(); j++) {
						email.getAttachList()
								.get(i)
								.getChildList()
								.get(j)
								.setId(new AttachmentDao()
										.getNextAttachmentId());
					}
				}
			}
		}
	}

	/**
	 * 解析得到Clickoo_message对象.
	 * 
	 * @param context
	 *            上下文对象
	 * @param email
	 *            邮件对象
	 * @return message instance
	 */
	public Clickoo_message parseMessage(Context context, Email email) {
		Clickoo_message message = new Clickoo_message();
		message.setIntime(email.getReceiveTime());
		message.setAid(String.valueOf(context.getAccountId()));
		message.setUuid(email.getUuid());
		try {
			message.setSubject(getMaxLengthSubject(email.getSubject()));
			message.setEmailFrom(getMaxLengthEmailFrom(email.getFrom()));
			message.setEmailTo(getMaxLengthEmailTo(email.getTo()));
			message.setCc(getMaxLengthCc(email.getCc()));
			message.setBcc(getMaxLengthBcc(email.getBcc()));
		} catch (UnsupportedEncodingException e) {
			logger.warn("parse length exception:", e);
		}
		message.setUuid(email.getUuid());
		message.setIntime(new Date());
		message.setSendtime(email.getSendTime());
		return message;
	}

	/**
	 * 处理bcc长度
	 * 
	 * @param bcc
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getMaxLengthBcc(String bcc)
			throws UnsupportedEncodingException {
		int length = Integer.parseInt(PropertiesDeploy.getConfigureMap().get(
				"bccLength"));
		if (bcc == null || bcc.getBytes(HTTP.UTF_8).length < length) {
			return bcc;
		} else {
			String str = new String(bcc.getBytes(HTTP.UTF_8), 0, length,
					HTTP.UTF_8);
			return str.substring(0, str.length() - 1) + "......";
		}
	}

	/**
	 * 处理email_from长度
	 * 
	 * @param emailFrom
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getMaxLengthEmailFrom(String emailFrom)
			throws UnsupportedEncodingException {
		int length = Integer.parseInt(PropertiesDeploy.getConfigureMap().get(
				"emailFromLength"));
		if (emailFrom == null || emailFrom.getBytes(HTTP.UTF_8).length < length) {
			return emailFrom;
		} else {
			String str = new String(emailFrom.getBytes(HTTP.UTF_8), 0, length,
					HTTP.UTF_8);
			return str.substring(0, str.length() - 1) + "......";
		}
	}

	/**
	 * 处理emailTo长度
	 * 
	 * @param emailTo
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getMaxLengthEmailTo(String emailTo)
			throws UnsupportedEncodingException {
		int length = Integer.parseInt(PropertiesDeploy.getConfigureMap().get(
				"emailToLength"));
		if (emailTo == null || emailTo.getBytes(HTTP.UTF_8).length < length) {
			return emailTo;
		} else {
			String str = new String(emailTo.getBytes(HTTP.UTF_8), 0, length,
					HTTP.UTF_8);
			return str.substring(0, str.length() - 1) + "......";
		}
	}

	/**
	 * 处理cc长度
	 * 
	 * @param bcc
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getMaxLengthCc(String cc)
			throws UnsupportedEncodingException {
		int length = Integer.parseInt(PropertiesDeploy.getConfigureMap().get(
				"ccLength"));
		if (cc == null || cc.getBytes(HTTP.UTF_8).length < length) {
			return cc;
		} else {
			String str = new String(cc.getBytes(HTTP.UTF_8), 0, length,
					HTTP.UTF_8);
			return str.substring(0, str.length() - 1) + "......";
		}
	}
	
	/**
	 * 处理subject长度
	 * 
	 * @param subject
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getMaxLengthSubject(String subject)
			throws UnsupportedEncodingException {
		int length = Integer.parseInt(PropertiesDeploy.getConfigureMap().get(
				"subjectLength"));
		if (subject == null || subject.getBytes(HTTP.UTF_8).length < length) {
			return subject;
		} else {
			String str = new String(subject.getBytes(HTTP.UTF_8), 0, length,
					HTTP.UTF_8);
			return str.substring(0, str.length() - 1) + "......";
		}
	}

	/**
	 * 处理subject长度
	 * 
	 * @param subject
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getMaxLengthAttName(String attName)
			throws UnsupportedEncodingException {
		int length = Integer.parseInt(PropertiesDeploy.getConfigureMap().get(
				"attNameLength"));
		if (attName == null || attName.getBytes(HTTP.UTF_8).length < length) {
			return attName;
		} else {
			String str = new String(attName.getBytes(HTTP.UTF_8), 0, length,
					HTTP.UTF_8);
			return str.substring(0, str.length() - 1) + "......";
		}
	}

	/**
	 * 解析得到Clickoo_message_data对象.
	 * 
	 * @param email
	 *            邮件对象
	 * @return data instance
	 */
	public Clickoo_message_data parseData(Email email) {
		Clickoo_message_data data = new Clickoo_message_data();

		int length = Integer.parseInt(PropertiesDeploy.getConfigureMap().get(
				"dataLength"));
		try {
			if (email.getBody() == null)
				email.setBody("".getBytes());
			if (email.getBody().length < length) {
				data.setData(new String(email.getBody(), HTTP.UTF_8), true);
				data.setSize(email.getBody().length);
			} else {
				String str = new String(email.getBody(), 0, length, HTTP.UTF_8);
				String content = str.substring(0, str.length() - 1) + "......";
				data.setData(content, true);
				data.setSize(content.getBytes(HTTP.UTF_8).length);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("not support Encoding UTF-8:\n", e);
		}
		logger.info("message data result : ----------------" + data.getData());
		return data;
	}

	/**
	 * 解析得到Clickoo_message_attachment对象.
	 * 
	 * @param email
	 *            邮件对象
	 * @return a list of attachment
	 * @throws Exception
	 */
	public List<Clickoo_message_attachment> parseAttachList(Email email)
			throws Exception {
		List<Clickoo_message_attachment> attachList = new ArrayList<Clickoo_message_attachment>();
		if (email.getnAttachList() != null && email.getnAttachList().size() > 0) {
			for (Clickoo_message_attachment attach : email.getnAttachList()) {
				Clickoo_message_attachment attachment = new Clickoo_message_attachment();
				// 添加id
				attachment.setId(attach.getId());
				attachment.setLength(attach.getLength());
				attachment.setName(getMaxLengthAttName(attach.getName()));
				attachment.setPath(attach.getPath());
				attachment.setType(attach.getType());
				attachment.setVolume_id(attach.getVolume_id());

				List<Clickoo_message_attachment> childList = attach
						.getChildList();
				List<Clickoo_message_attachment> pojoChildList = new ArrayList<Clickoo_message_attachment>();
				if (childList != null && childList.size() > 0) {
					for (Clickoo_message_attachment childAttach : childList) {
						Clickoo_message_attachment childAttachment = new Clickoo_message_attachment();
						childAttachment.setId(childAttach.getId());
						childAttachment.setLength(childAttach.getLength());
						childAttachment.setName(getMaxLengthAttName(attach
								.getName()));
						childAttachment.setPath(childAttach.getPath());
						childAttachment.setType(childAttach.getType());
						childAttachment
								.setVolume_id(childAttach.getVolume_id());
						pojoChildList.add(childAttachment);
					}
				}
				attachment.setChildList(pojoChildList);
				attachList.add(attachment);
			}
		}
		return attachList;
	}

}
