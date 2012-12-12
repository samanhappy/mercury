package com.dreamail.mercury.domain;

import java.util.Date;
import java.util.List;

import com.dreamail.mercury.pojo.Clickoo_message_attachment;

/**
 * 邮件类
 * 
 * @author pengkai.wang
 */
public class Email {
	// 邮件唯一标识
	private String uuid;
	private String messageId;
	private String subject;
	private String from;
	private String to;
	private String cc;
	private String bcc;
	private Date sendTime;
	private Date receiveTime;
	private byte[] body;
	private int size;
	private List<Clickoo_message_attachment> attachList;
	private List<Clickoo_message_attachment> nAttachList;
	
	public List<Clickoo_message_attachment> getnAttachList() {
		return nAttachList;
	}

	public void setnAttachList(List<Clickoo_message_attachment> nAttachList) {
		this.nAttachList = nAttachList;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
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

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public List<Clickoo_message_attachment> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<Clickoo_message_attachment> attachList) {
		this.attachList = attachList;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
