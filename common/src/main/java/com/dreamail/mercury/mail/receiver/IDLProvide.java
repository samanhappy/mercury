package com.dreamail.mercury.mail.receiver;

import java.util.List;

import javax.mail.MessagingException;

import com.dreamail.mercury.domain.Body;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.domain.EmailSupport;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;

public interface IDLProvide extends IProvide{
	 /**
	  * 根据传入参数下载邮件.
	  * @param account
	  * @param uuid
	  * @return Email
	  */
	public Email dlEmail(WebAccount account,String uuid,List<Clickoo_message_attachment> exitAtt,EmailSupport eSupport,boolean dlBody) throws MessagingException;
	/**
	  * 根据传入参数下载邮件正文.
	  * @param account
	  * @param uuid
	  * @param attName
	  * param size
	  * @return Attachment
	  */
	public Body dlData(WebAccount account,String uuid) throws MessagingException;
	 /**
	  * 根据传入参数下载邮件附件.
	  * @param account
	  * @param uuid
	  * @return Body
	  */
	public Clickoo_message_attachment dlAttachment(WebAccount account,String uuid,String attName,int size,EmailSupport eSupport) throws MessagingException;

	/**
	  * 根据传入参数下载邮件所有缺失的附件.
	  * @param account
	  * @param uuid
	  * @return Body
	  */
	public List<Clickoo_message_attachment> dlAllAttachment(WebAccount account,String uuid,List<Clickoo_message_attachment> exitAtt,EmailSupport eSupport) throws MessagingException;
}
