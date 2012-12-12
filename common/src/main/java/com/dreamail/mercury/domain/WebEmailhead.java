package com.dreamail.mercury.domain;


public class WebEmailhead {
	private String emailHeadID;//邮件串号
	private String messageId;//邮件主键
	private String aid;// 邮件唯一标识
	private String subject;// 标题
	private String from;// 发件人
	private String to;// 收件人
	private String cc;// 抄送
	private String bcc;// 秘送
	private String mid;// 邮件唯一编号
	private String forward;//转发"0"代表不是转发邮件，"1"代表是转发邮件
	private String sendTime;// 邮件时间
	private String receiveTime;// 接收时间
	private String messageId_old;
	private String messageId_new;
	
	public String getMessageId_old() {
		return messageId_old;
	}

	public void setMessageId_old(String messageIdOld) {
		messageId_old = messageIdOld;
	}

	public String getMessageId_new() {
		return messageId_new;
	}

	public void setMessageId_new(String messageIdNew) {
		messageId_new = messageIdNew;
	}

	public String getEmailHeadID() {
		return emailHeadID;
	}

	public void setEmailHeadID(String emailHeadID) {
		this.emailHeadID = emailHeadID;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	
	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
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
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

}
