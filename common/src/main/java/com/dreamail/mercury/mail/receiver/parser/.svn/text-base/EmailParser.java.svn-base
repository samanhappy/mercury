package com.dreamail.mercury.mail.receiver.parser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;

import com.dreamail.mercury.pojo.Clickoo_message_attachment;

/**
 * parse email interface.
 * 
 * @author pengkai.wang
 */
public interface EmailParser {
	/**
	 * get the from address.
	 * @param message javax.mail.Message
	 * @return from address
	 * @throws MessagingException Messaging
	 * @throws UnsupportedEncodingException UnsupportedEncoding
	 */
	String getFrom(Message message) throws MessagingException, UnsupportedEncodingException;
	
	/**
	 * get the to、cc、bcc address by the type.
	 * @param message javax.mail.Message
	 * @param type 
	 * @return to、cc、bcc address
	 * @throws UnsupportedEncodingException UnsupportedEncoding
	 * @throws MessagingException Messaging
	 */
	String getMailAddress(Message message, String type) throws UnsupportedEncodingException, MessagingException;
	
	/**
	 * get subject.
	 * @param message javax.mail.Message
	 * @return sbuject
	 * @throws UnsupportedEncodingException UnsupportedEncoding
	 * @throws MessagingException Messaging
	 */
	String getSubject(Message message) throws UnsupportedEncodingException, MessagingException;
	
	/**
	 * get sendDate.
	 * @param message javax.mail.Message
	 * @return sendDate
	 * @throws MessagingException Messaging
	 */
	Date getSendDate(Message message) throws MessagingException;
	
	/**
	 * get body text.
	 * @param message javax.mail.Message
	 * @return body text
	 * @throws IOException IO
	 * @throws MessagingException Messaging
	 */
	String getBodyText(Message message) throws  IOException, MessagingException;
	
	/**
	 * get replySign.
	 * @param message javax.mail.Message
	 * @return replySign
	 * @throws MessagingException Messaging
	 */
	boolean getReplySign(Message message) throws MessagingException;
	
	/**
	 * get messageId.
	 * @param message javax.mail.Message
	 * @return messageId
	 * @throws MessagingException Messaging
	 */
	String getMessageId(Message message) throws MessagingException;
	
	/**
	 * get all attachment.
	 * @param message javax.mail.Message
	 * @return all attachment
	 * @throws MessagingException Messaging
	 * @throws IOException IO
	 */
	List<Clickoo_message_attachment> getAttachList(Message message) throws MessagingException, IOException;
	
	/**
	 * get References.
	 * @param message javax.mail.Message
	 * @return references
	 * @throws MessagingException Messaging
	 */
	String getReferences(Message message)throws MessagingException;
	
	/**
	 * get In-Reply_to.
	 * @param message javax.mail.Message
	 * @return In-Reply_to
	 * @throws MessagingException Messaging
	 */
	String getInReplyTo(Message message)throws MessagingException;
	/**
	 * get message size.
	 * @param message
	 * @return
	 * @throws MessagingException
	 */
	int getMessageSize(Message message)throws MessagingException;
}
