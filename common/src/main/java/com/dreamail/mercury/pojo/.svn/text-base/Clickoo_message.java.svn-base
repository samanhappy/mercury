/**
 * 
 */
package com.dreamail.mercury.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author meng.sun
 * 
 */
public class Clickoo_message implements Comparable<Clickoo_message>,
		Serializable {

	private static final long serialVersionUID = -5214923825100834603L;

	private long id;// 主键ID

	private Date intime;// 接收时间

	private int status;// 状态

	private String aid;// Account ID.

	private long volumeid;// 附件表ID

	private String path;// 附件路径

	private String uuid;// 邮件唯一编号

	private String folderName;// imap邮件所属文件夹名称

	private long downloadid;

	private Clickoo_message_data messageData;

	private List<Clickoo_message_attachment> attachList;

	private String emailFrom;// 发件人

	private String emailTo;// 收件人

	private String cc;// 抄送

	private String bcc;// 秘送

	private String subject;// 标题

	private Date sendtime;

	private long msgCount;

	public long getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(long msgCount) {
		this.msgCount = msgCount;
	}

	public long getMaxUUid() {
		return maxUUid;
	}

	public void setMaxUUid(long maxUUid) {
		this.maxUUid = maxUUid;
	}

	private long maxUUid;

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
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

	public Clickoo_message() {
	}

	/**
	 * @param id
	 * @param subject
	 * @param emailFrom
	 * @param emailTo
	 * @param cc
	 * @param bcc
	 * @param intime
	 * @param mailDate
	 * @param status
	 * @param uid
	 * @param aid
	 * @param mid
	 * @param volumeid
	 * @param path
	 * @param threadid
	 * @param uuid
	 */
	public Clickoo_message(long id, Date intime, int status, String aid,
			long volumeid, String path, String uuid) {
		super();
		this.id = id;
		this.intime = intime;
		this.status = status;
		this.aid = aid;
		this.volumeid = volumeid;
		this.path = path;
		this.uuid = uuid;
	}

	public Clickoo_message(String uuid, Date intime, String folderName) {
		this.uuid = uuid;
		this.intime = intime;
		this.folderName = folderName;
	}

	public Clickoo_message(String uuid, Date intime) {
		this.uuid = uuid;
		this.intime = intime;
	}

	public Clickoo_message(int id, int status) {
		this.id = id;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getIntime() {
		return intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public long getVolumeid() {
		return volumeid;
	}

	public void setVolumeid(long volumeid) {
		this.volumeid = volumeid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public Clickoo_message_data getMessageData() {
		return messageData;
	}

	public void setMessageData(Clickoo_message_data messageData) {
		this.messageData = messageData;
	}

	public List<Clickoo_message_attachment> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<Clickoo_message_attachment> attachList) {
		this.attachList = attachList;
	}

	public long getDownloadid() {
		return downloadid;
	}

	public void setDownloadid(long downloadid) {
		this.downloadid = downloadid;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	@Override
	public int compareTo(Clickoo_message o) {
		if (intime.after(o.getIntime())) {
			return 1;
		} else if (intime.equals(o.getIntime())) {
			return 0;
		}
		return -1;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
