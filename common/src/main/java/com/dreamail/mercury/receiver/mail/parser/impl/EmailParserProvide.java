package com.dreamail.mercury.receiver.mail.parser.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.domain.EmailSupport;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.receiver.attachment.impl.AttachmentFormatControl;
import com.dreamail.mercury.mail.receiver.parser.EmailParser;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.EDcryptionUtil;
import com.dreamail.mercury.util.ExceSupport;
import com.dreamail.mercury.util.ZipUtil;

public class EmailParserProvide {
	public static final Logger logger = LoggerFactory.getLogger(EmailParserProvide.class);
	
	public static String key;
	
	static{
		try {
			key = PropertiesDeploy.getConfigureMap().get(Constant.KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 封装一个Email对象
	 * 
	 * @param accountName
	 * @param message
	 * @param uuid
	 * @return Email
	 * @throws MessagingException 
	 */
	public Email getEmail(String accountName,Message message,String uuid) throws MessagingException {
		String messageId = null;
		String subject = null;
		String from = null;
		String to = null;
		String cc = null;
		String bcc = null;
		Date receiveTime = new Date();
		Date sendTime = null;
		byte[] body = null;
		int size = 0;
		List<Clickoo_message_attachment> emailList = null;
		EmailParser parser = EmailParserImpl.getInstance();
		try {
			messageId = parser.getMessageId(message);
			subject = parser.getSubject(message);
			from = parser.getFrom(message);
			to = parser.getMailAddress(message, "TO");
			cc = parser.getMailAddress(message, "CC");
			bcc = parser.getMailAddress(message, "BCC");
			sendTime = parser.getSendDate(message);
			body = parser.getBodyText(message).getBytes();
			size = parser.getMessageSize(message);
			emailList = parser.getAttachList(message);
		} catch (UnsupportedEncodingException e) {
			ExceSupport.execHandle(accountName, e, this.getClass(), uuid);
		} catch (MessagingException e) {
			if(e.getMessage().indexOf(("No inputstream from datasource"))!=-1){
				logger.warn("No inputstream from datasource,connect again.");
			}
			ExceSupport.execHandle(accountName, e, this.getClass(), uuid);
		} catch (IOException e) {
			ExceSupport.execHandle(accountName, e, this.getClass(), uuid);
		} 
		Email email = new Email();
		email.setUuid(uuid);
		email.setMessageId(messageId);
		email.setSubject(subject);
		email.setFrom(from);
		email.setTo(to);
		email.setCc(cc);
		email.setBcc(bcc);
		email.setSendTime(sendTime);
		email.setReceiveTime(receiveTime);
		email.setBody(body);
		email.setAttachList(emailList);
		email.setSize(size);
		return email;
	}
	
	/**
	 * 封装一个Email对象,供重新下载调用.
	 * 
	 * @param accountName
	 * @param message
	 * @param uuid
	 * @param exitAtt
	 * @param eSupport
	 * @return Email
	 * @throws MessagingException 
	 */
	public Email getEmail(String accountName,Message message,String uuid,List<Clickoo_message_attachment> exitAtt,EmailSupport eSupport,boolean dlBody) throws MessagingException {
		String messageId = null;
		String subject = null;
		String from = null;
		String to = null;
		String cc = null;
		String bcc = null;
		Date receiveTime = new Date();
		Date sendTime = null;
		byte[] body = null;
		int size = 0;
		List<Clickoo_message_attachment> emailList = null;
		EmailParserImpl parser = EmailParserImpl.getInstance();
		try {
			messageId = parser.getMessageId(message);
			subject = parser.getSubject(message);
			from = parser.getFrom(message);
			to = parser.getMailAddress(message, "TO");
			cc = parser.getMailAddress(message, "CC");
			bcc = parser.getMailAddress(message, "BCC");
			sendTime = parser.getSendDate(message);
			if(dlBody){
				body = parser.getBodyText(message).getBytes();
			}
			emailList = parser.getDownAttachList(message, exitAtt, eSupport);
			size = parser.getMessageSize(message);
		} catch (UnsupportedEncodingException e) {
			ExceSupport.execHandle(accountName, e, this.getClass(), uuid);
		} catch (MessagingException e) {
			if(e.getMessage().indexOf(("No inputstream from datasource"))!=-1){
				logger.warn("No inputstream from datasource,connect again.");
			}
			ExceSupport.execHandle(accountName, e, this.getClass(), uuid);
		} catch (IOException e) {
			ExceSupport.execHandle(accountName, e, this.getClass(), uuid);
		} 
		Email email = new Email();
		email.setUuid(uuid);
		email.setMessageId(messageId);
		email.setSubject(subject);
		email.setFrom(from);
		email.setTo(to);
		email.setCc(cc);
		email.setBcc(bcc);
		email.setSendTime(sendTime);
		email.setReceiveTime(receiveTime);
		email.setBody(body);
		email.setAttachList(emailList);
		email.setSize(size);
		return email;
	}
	
	public Email getEmail(String accountName, Message message,int uuid) throws MessagingException{
		return getEmail(accountName,message,Integer.toString(uuid));
	}
	
	public void dealAttachment(WebAccount account,String mid,Email email){
		Context context = new Context();
		List<String> deviceList = new MessageDao().getDeviceByMid(mid);
		StringBuffer sb = new StringBuffer();
		if (deviceList != null) {
			for (int i = 0; i < deviceList.size(); i++) {
				sb.append(deviceList.get(i) + "#");
			}
		}
		context.setDeviceModelList(sb.toString());
		Map<String, Email> map = new HashMap<String, Email>();
		map.put(mid, email);
		context.setAccountId(account.getId());
		context.setLoginName(account.getName());
		context.setEmailList(map);
		if (context.getLoginName() != null) {
			new AttachmentFormatControl().doProces(context);
		}
	}
	
	/**
	 * 根据eml文件 解析.
	 * @param accountName
	 * @param path
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException 
	 */
	public Email getEmail(String accountName,String path) throws MessagingException{
		Email email = null;
		FileInputStream fis = null;
		try {
			logger.info("email path is" + path);
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			logger.error("eml can't be found.",e);
			return email;
		}
		Properties props = System.getProperties();  
        props.put("mail.smtp.auth", "true");  
        Session session = Session.getDefaultInstance(props, null);  
		MimeMessage msg = null;
		msg = new MimeMessage(session, fis);
		email = new EmailParserProvide().getEmail(accountName, msg, null);
		email.setSubject(email.getSubject());
		email.setBody(email.getBody());
		if(email.getAttachList()!=null && email.getAttachList().size()>0){
			List<Clickoo_message_attachment> orginAttList = new ArrayList<Clickoo_message_attachment>();
			for(Clickoo_message_attachment att:email.getAttachList()){
				Clickoo_message_attachment orginAtt = new Clickoo_message_attachment();
				orginAtt.setName(EDcryptionUtil.EDcrype(att.getName(),key));
				if(att.getIn()!=null){
					orginAtt.setIn(EDcryptionUtil.EDcrype(att.getIn(),key));
				}
				orginAtt.setType(att.getType());
				orginAtt.setLength(att.getLength());
				orginAttList.add(orginAtt);
			}
			email.setAttachList(orginAttList);
		}
		return email;
	}
	
	/**
	 * 根据已发送eml文件 解析.
	 * @param accountName
	 * @param path
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException 
	 */
	public Email getSendEmail(String accountName,String path) throws MessagingException{
		Email email = null;
		InputStream fis = null;
		try {
			logger.info("email paht is" + path);
			fis = ZipUtil.readZipFile(path);
		} catch (FileNotFoundException e) {
			logger.error("eml can't be found.",e);
			return email;
		} catch (IOException e) {
			logger.error("eml can't be found.",e);
			return email;
		}
		Properties props = System.getProperties();  
        props.put("mail.smtp.auth", "true");  
        Session session = Session.getDefaultInstance(props, null);  
		MimeMessage msg = null;
		msg = new MimeMessage(session, fis);
		email = new EmailParserProvide().getEmail(accountName, msg, null);
		email.setSubject(email.getSubject());
		System.out.println(email.getSubject());
		email.setBody(email.getBody());
		if(email.getAttachList()!=null && email.getAttachList().size()>0){
			List<Clickoo_message_attachment> orginAttList = new ArrayList<Clickoo_message_attachment>();
			for(Clickoo_message_attachment att:email.getAttachList()){
				Clickoo_message_attachment orginAtt = new Clickoo_message_attachment();
				orginAtt.setName(EDcryptionUtil.EDcrype(att.getName(),key));
				if(att.getIn()!=null){
					orginAtt.setIn(EDcryptionUtil.EDcrype(att.getIn(),key));
				}
				orginAtt.setType(att.getType());
				orginAtt.setLength(att.getLength());
				orginAttList.add(orginAtt);
			}
			email.setAttachList(orginAttList);
		}
		return email;
	}
	
}
