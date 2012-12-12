package com.dreamail.mercury.mail.receiver.attachment.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.dal.dao.AttachmentDao;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.proces.IProces;
import com.dreamail.mercury.util.AttachmentSupport;
import com.dreamail.mercury.util.UUIDHexGenerator;

/**
 * 附件读取的入口类.
 * 
 * @author 000830
 */
public class AttachmentFormatControl implements IProces {
	private static final Logger logger = LoggerFactory
			.getLogger(AttachmentFormatControl.class);

	/**
	 * 循环遍历context里的每一封邮件的附件.
	 * 
	 * @param Context
	 *            context
	 */
	@Override
	public void doProces(Context context) {
		HashMap<String, String> map = new HashMap<String, String>();
		/*
		 * 虽以uid为key，实际上存放的是帐号ID，WTF
		 */
		map.put("uid", String.valueOf(context.getAccountId()));
		map.put("name", context.getLoginName());
		if (context.getEmailList() == null
				|| context.getEmailList().size() == 0) {
			return;
		}
		for (String mid : context.getEmailList().keySet()) {
			Email email = context.getEmailList().get(mid);
			map.put("mid", UUIDHexGenerator.getUUIDHex().toUpperCase());
			if (email.getAttachList() != null) {
				List<Clickoo_message_attachment> NattachList = new ArrayList<Clickoo_message_attachment>();
				for (Clickoo_message_attachment att : email.getAttachList()) {
					if (att.getIn() != null) {
						try {
							// 给附件名称添加唯一uuid后缀
							String suffixName = UUIDHexGenerator.getUUIDHex();
							String name = att.getName();
							long attachmentID = new AttachmentDao()
									.getNextAttachmentId();
							att.setId(attachmentID);
							if (name.contains(".")) {
								name = name.substring(0, name.lastIndexOf("."));
							}
							att.setName(name);
							String type = att.getType();
							map.put("attachmentID",
									String.valueOf(attachmentID));
							map.put("type", att.getType());
							map.put("attName", suffixName);
							map.put("deviceModelList",
									context.getDeviceModelList());
							AbstractAttachmentFormat format = this
									.factory(type);
							List<Clickoo_message_attachment> a = format.format(
									att, map);
							if (a != null) {
								att.setChildList(a);
								NattachList.add(att);
							}
						} catch (Exception e) {
							logger.error("account[" + context.getLoginName()
									+ "]messageID[" + email.getMessageId()
									+ "]", e);
						}
					}
				}
				// 关闭数据库连接
				SessionFactory.closeSession();
				email.setnAttachList(NattachList);
				email.setBody(email.getBody());
			} else {
				logger.info("Email AttachList is null!!");
			}
		}

	}

	/**
	 * 根据不同的Type,返回相应的读取方法！.
	 * 
	 * @param String
	 *            type 附件类型
	 * @return AbstractAttachmentFormat 具体附件处理类实例
	 */
	public AbstractAttachmentFormat factory(String type) {
		if (AttachmentSupport.isImageType(type)) {
			return new ImageFormatImpl();
		} else if (AttachmentSupport.isDOCType(type)) {
			return new DOCFormatImpl();
		} else if (AttachmentSupport.isPDFType(type)) {
			return new PDFFormatImpl();
		} else if (AttachmentSupport.isTXTType(type)) {
			return new TextFormatImpl();
		} else if (AttachmentSupport.isXLSType(type)) {
			return new XLSFormatImpl();
		} else if (AttachmentSupport.isPPTType(type)) {
			return new PPTFormatImpl();
		} else {
			return new NoSupportFormatImpl();
		}
	}

	/**
	 * 根据原附件获得目标附件
	 */
	public Clickoo_message_attachment getSourceAttachment(
			Clickoo_message_attachment att, String aid, String deviceModel) {
		List<Clickoo_message_attachment> attachmentList = new ArrayList<Clickoo_message_attachment>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", aid);
		try {
			String suffixName = UUIDHexGenerator.getUUIDHex();
			String name = att.getName();
			long attachmentID = new AttachmentDao().getNextAttachmentId();
			att.setId(attachmentID);
			if (name.contains(".")) {
				name = name.substring(0, name.lastIndexOf("."));
			}
			att.setName(name);
			String type = att.getType();
			map.put("attachmentID", String.valueOf(attachmentID));
			map.put("type", att.getType());
			map.put("attName", suffixName);
			map.put("deviceModelList", deviceModel);
			AbstractAttachmentFormat format = this.factory(type);
			attachmentList = format.format(att, map);
			if (attachmentList.size() == 0) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		att.setChildList(attachmentList);
		return attachmentList.get(0);
	}
}
