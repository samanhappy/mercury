package com.dreamail.mercury.domain;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.mercury.util.EmailUtils;

public class EmailCacheObject implements Serializable {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(EmailCacheObject.class);
	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static final long serialVersionUID = 1L;
	private String aid;
	private String email_from;
	private String email_to;
	private String cc;
	private String bcc;
	private String subject;
	private Date mail_date;
	private String uuid;
	private long data_size;
	private byte[] data;
	private String attachmentJson;
	private int attachNums;
	private String folderName;

	public int getAttachNums() {
		return attachNums;
	}

	public void setAttachNums(int attachNums) {
		this.attachNums = attachNums;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getEmail_from() {
		return email_from;
	}

	public void setEmail_from(String emailFrom) {
		email_from = emailFrom;
	}

	public String getEmail_to() {
		return email_to;
	}

	public void setEmail_to(String emailTo) {
		email_to = emailTo;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getMail_date() {
		return mail_date;
	}

	public void setMail_date(Date mailDate) {
		mail_date = mailDate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getData_size() {
		return data_size;
	}

	public void setData_size(long dataSize) {
		data_size = dataSize;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getAttachmentJson() {
		return attachmentJson;
	}

	public void setAttachmentJson(String attachmentJson) {
		this.attachmentJson = attachmentJson;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public WebEmailhead getWebMailHead(String msgId) {
		WebEmailhead head = new WebEmailhead();
		head.setMessageId(msgId);
		head.setAid(getAid());
		try {
			byte[] subject = new byte[0];
			if (getSubject() != null) {
				subject = (getSubject()).getBytes("UTF-8");
			}
			head.setSubject(EmailUtils.changeByteToBase64(subject));
			head.setFrom(getAccountAddress(getEmail_from()));
			head.setTo(getAccountAddress(getEmail_to()));
			head.setCc(getAccountAddress(getCc()));
			head.setBcc(getAccountAddress(getBcc()));

		} catch (UnsupportedEncodingException e) {
			logger.error("exception:", e);
		} catch (Exception e) {
			logger.error("exception:", e);
		}

		if (getMail_date() != null) {
			head.setReceiveTime(formatter.format(getMail_date()));
		}
		return head;
	}

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
}
