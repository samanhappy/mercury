package com.dreamail.mercury.receiver.mail.parser.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.domain.EmailSupport;
import com.dreamail.mercury.mail.receiver.parser.EmailParser;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.receiver.mail.encode.EncodeHandler;
import com.dreamail.mercury.receiver.mail.utils.HTMLHelper;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.StreamUtil;

/**
 * EmailParser's Implementation class.
 * 
 * @author pengkai.wang
 */
public class EmailParserImpl implements EmailParser {
	public static final Logger logger = LoggerFactory
			.getLogger(EmailParserImpl.class);
	public static long ATT_LIMIT_SIZE;

	static {
		try {
			ATT_LIMIT_SIZE = 
				Long.parseLong(PropertiesDeploy.getConfigureMap()
					.get(Constant.ATTACHMENT_SIZE_LIMIT));
		} catch (NumberFormatException e) {
			logger.error("", e);
		}
	}

	private EmailParserImpl() {
	}

	private static EmailParserImpl parser = new EmailParserImpl();

	public static EmailParserImpl getInstance() {
		if (parser == null) {
			parser = new EmailParserImpl();
		}
		return parser;
	}

	/**
	 * 获取发件人信息 返回"别名<地址>"格式的字符串
	 * 
	 * @param message
	 * @return String
	 * @throws MessagingException
	 */
	@Override
	public String getFrom(Message message) throws MessagingException {
		InternetAddress address[] = (InternetAddress[]) message.getFrom();
		String from = address[0].getAddress();
		StringBuffer fromaddr = new StringBuffer();
		if(address[0].getPersonal() != null)
			fromaddr.append(address[0].getPersonal());
		fromaddr.append("<");
		fromaddr.append(from);
		fromaddr.append(">");
		return fromaddr.toString();
	}

	/**
	 * 根据传入参数"TO"、"CC"、"BCC"获取收件人信息 返回"别名<地址>"格式的字符串
	 * 
	 * @param message
	 * @param type
	 *            传入参数
	 * @return String
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public String getMailAddress(Message message, String type)
			throws MessagingException, UnsupportedEncodingException {
		StringBuffer mailAddr = new StringBuffer();
		String addtype = type.toUpperCase();
		InternetAddress[] address = null;
		try {
			if (addtype.equals("TO") || addtype.equals("CC")
					|| addtype.equals("BCC")) {
				if (addtype.equals("TO")) {
					address = (InternetAddress[]) message
							.getRecipients(Message.RecipientType.TO);
				} else if (addtype.equals("CC")) {
					address = (InternetAddress[]) message
							.getRecipients(Message.RecipientType.CC);
				} else {
					address = (InternetAddress[]) message
							.getRecipients(Message.RecipientType.BCC);
				}
				if (address != null) {
					for (int i = 0; i < address.length; i++) {
						String email = address[i].getAddress();
						if (email != null) {
							email = MimeUtility.decodeText(email);
						}
						String personal = address[i].getPersonal();
						if (personal != null) {
							personal = MimeUtility.decodeText(personal);
						}
						mailAddr.append(",");
						if(address[0].getPersonal() != null)
							mailAddr.append(address[0].getPersonal());
						mailAddr.append("<");
						mailAddr.append(email);
						mailAddr.append(">");
					}
					if (!"".equals(mailAddr.toString()) && mailAddr.toString().startsWith(",")) {
						return mailAddr.toString().substring(1);
					}
				}
			}
		} catch (AddressException e) {
			if (e.getMessage().contains("Missing local name")) {
				logger.info("Missing local name");
			} else if (e.getMessage().contains("Missing domain")) {
				logger.info("Missing domain");
			}
		}
		return mailAddr.toString();
	}

	/**
	 * 获取邮件主题
	 * 
	 * @param message
	 * @return String
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public String getSubject(Message message)
			throws UnsupportedEncodingException, MessagingException {
		String subject = null;
		try {
			if (message.getSubject() != null) {
				subject = MimeUtility.decodeText(message.getSubject());
			}
		} catch (Exception e) {
			if (message.getSubject().toLowerCase().indexOf("utf-7") != -1) {
				subject = new EncodeHandler().parseMsgSubject(message);
			} else {
				if (e.getMessage().startsWith("Unknown encoding")) {
					message.removeHeader("content-transfer-encoding");
					subject = message.getSubject() == null ? "" : MimeUtility
							.decodeText(message.getSubject());
				}
			}
		}
		return subject;
	}

	/**
	 * 根据传入日期类型如"yy-MM-dd HH:mm"获取邮件发送日期
	 * 
	 * @param message
	 * @param dateFormat
	 * @return String
	 * @throws MessagingException
	 */
	@Override
	public Date getSendDate(Message message) throws MessagingException {
		Date sentdate = message.getSentDate();
		return sentdate;
	}

	/**
	 * 获取邮件正文
	 * 
	 * @param message
	 * @return String
	 * @throws MessagingException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public String getBodyText(Message message) throws IOException,
			MessagingException {
		StringBuffer bodyText = new StringBuffer();
		getMailContent((Part) message, bodyText);
		return bodyText.toString();
	}

	/**
	 * 将获取的邮件正文内容存入bodyText中
	 * 
	 * @param part
	 * @throws Exception
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	private void getMailContent(Part part, StringBuffer bodyText)
			throws IOException, MessagingException {
		String contenttype = part.getContentType();
		if (part.isMimeType("text/plain") && !isNotBody(contenttype, part)) {
			handleTxtPart(part, bodyText);
		} else if (part.isMimeType("text/html")
				&& !isNotBody(contenttype, part)) {
			handleHtmlPart(part, bodyText);
		} else if (part.isMimeType("multipart/alternative")) {
			Multipart multipart = (Multipart) part.getContent();
			int counts = multipart.getCount();
			boolean isGo = true;
			for (int i = 0; i < counts; i++) {
				logger.info("part contenttype:"+multipart.getBodyPart(i).getContentType());
				if (multipart.getBodyPart(i).isMimeType("text/plain")
						&& !isNotBody(contenttype, part) && isGo ) {
					//暂时注释掉一下判断，此处可能会引起中文乱码，王鹏凯存有疑问.
					//&& !multipart.getBodyPart(i).getContentType().toUpperCase().contains("ISO")
					handleTxtPart(multipart.getBodyPart(i), bodyText);
					isGo = false;
				} else if (multipart.getBodyPart(i).isMimeType("text/html")
						&& !isNotBody(contenttype, part) && isGo) {
					handleHtmlPart(multipart.getBodyPart(i), bodyText);
					isGo = false;
				} else if (multipart.getBodyPart(i).isMimeType("multipart/*")) {
					getMailContent(multipart.getBodyPart(i), bodyText);
				}
			}
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int counts = multipart.getCount();
			for (int i = 0; i < counts; i++) {
				getMailContent(multipart.getBodyPart(i), bodyText);
			}
		} else if (part.isMimeType("message/rfc822")) {
			getMailContent((Part) part.getContent(), bodyText);
		} else if (!isNotBody(contenttype, part)) {
			if (contenttype.indexOf("text/html") != -1) {
				handleHtmlPart(part, bodyText);
			} else if (contenttype.indexOf("text/plain") != -1) {
				handleTxtPart(part, bodyText);
			} else {
				try {
					handleTxtPart(part, bodyText);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void handleTxtPart(Part part, StringBuffer bodyText)
			throws MessagingException, IOException {
		logger.info("handle txt.................................");
		String content = null;
		if (part.getContentType() != null
				&& part.getContentType().toLowerCase().indexOf("utf-7") != -1) {
			content = new EncodeHandler().parseMsgContent(part);
		} else {
			content = part.getContent() instanceof InputStream ? new String(
					StreamUtil.getByteByInputStream((InputStream) part
							.getContent()), "utf-8") : (String) part
					.getContent();

		}
		bodyText.append(content);
	}

	private void handleHtmlPart(Part part, StringBuffer bodyText)
			throws MessagingException, IOException {
		logger.info("handle html.................................");
		String content = null;
		if (part.getContentType() != null
				&& part.getContentType().toLowerCase().indexOf("utf-7") != -1) {
			content = new EncodeHandler().parseMsgContent(part);
		} else {
			content = part.getContent() instanceof InputStream ? new String(
					StreamUtil.getByteByInputStream((InputStream) part
							.getContent()), "utf-8") : (String) part
					.getContent();
		}
		bodyText.append(HTMLHelper.getInstance().htmToTxt(content));
	}

	private boolean isNotBody(String type, Part part) throws MessagingException {
		boolean boo = false;
		String disposition = null;
		try {
			disposition = part.getDisposition();
		} catch (Exception e) {
			logger.info("part not have disposition.");
		}
		if ((type.indexOf("name") != -1)
				|| (Part.ATTACHMENT.equalsIgnoreCase(disposition))) {
			boo = true;
		}
		return boo;
	}

	/**
	 * 判断邮件是否回执，是则返回"true"，反之返回"false"
	 * 
	 * @param message
	 * @return boolean
	 * @throws MessagingException
	 */
	@Override
	public boolean getReplySign(Message message) throws MessagingException {
		boolean replysign = false;
		String needreply[] = message.getHeader("Disposition-Notification-To");
		if (needreply != null) {
			replysign = true;
		}
		return replysign;
	}

	/**
	 * 获取邮件的messageId
	 * 
	 * @param message
	 * @return String
	 * @throws MessagingException
	 */
	@Override
	public String getMessageId(Message message) throws MessagingException {
		if (message != null) {
			return ((MimeMessage) message).getMessageID();
		}
		return null;
	}

	/**
	 * 将附件名保存在attachName、及将附件内容保存在attachIn中
	 * 
	 * @param part
	 * @throws IOException
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	private void saveAttachMent(Part part,List<Clickoo_message_attachment> attachList)
			throws MessagingException, IOException {
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = null;
				try {
					disposition = mpart.getDisposition();
				} catch (Exception e) {
					logger.info("save att,part not have disposition.");
				}
				if (mpart.isMimeType("multipart/*")) {
					saveAttachMent((Part) mpart,attachList);
				} else {
					String contype = mpart.getContentType();
					if ((disposition == null) && (contype.toLowerCase().indexOf("application") != -1 
							|| contype.toLowerCase().indexOf("name") != -1)
							 ) {
						Clickoo_message_attachment att = new Clickoo_message_attachment();
						if (!isLimitedAtt(mpart.getSize()))
							att.setIn(StreamUtil.getByteByInputStream(mpart.getInputStream()));
						att.setLength(mpart.getSize());
						att.setName(MimeUtility.decodeText(MimeUtility.decodeText(mpart
								.getFileName())));
						attachList.add(att);
					} 
				}
				if ((disposition != null)
						&& ((Part.ATTACHMENT.equalsIgnoreCase(disposition)))) {
					Clickoo_message_attachment att = new Clickoo_message_attachment();
					if (!isLimitedAtt(mpart.getSize()))
						att.setIn(StreamUtil.getByteByInputStream(mpart.getInputStream()));
					att.setLength(mpart.getSize());
					att.setName(MimeUtility.decodeText(MimeUtility.decodeText(mpart
							.getFileName())));
					attachList.add(att);
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachMent((Part) part.getContent(),attachList);
		}
	}

	/**
	 * 获取附件集合
	 * 
	 * @param message
	 * @return List<Attachment>
	 * @throws IOException
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@Override
	public List<Clickoo_message_attachment> getAttachList(Message message)
			throws UnsupportedEncodingException, MessagingException,
			IOException {
		List<Clickoo_message_attachment> attachList = new ArrayList<Clickoo_message_attachment>();
		saveAttachMent((Part) message,attachList);
		List<Clickoo_message_attachment> reattachList = new ArrayList<Clickoo_message_attachment>();
		boolean boo = message.getHeader("From")[0].indexOf("?utf-7?") != -1 ? true
				: false;
		for (int i = 0; i < attachList.size(); i++) {
			String name = boo ? new EncodeHandler()
					.UTF7T0Str(attachList.get(i).getName()) : attachList.get(i).getName();
			String type = null;
			if (name.indexOf(".") != -1) {
				type = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
				name = name.substring(0, name.lastIndexOf("."));
			}

			Clickoo_message_attachment attach = new Clickoo_message_attachment();
			attach.setName(name);
			attach.setType(type);
//			attach.setLength(attachList.get(i).getLength());
			attach.setIn(attachList.get(i).getIn());
			attach.setLength(attachList.get(i).getIn().length);
			logger.info("attach name:"+attach.getName()+"  attatch length:"+attach.getLength());
			reattachList.add(attach);
		}
		return reattachList;
	}

	/**
	 * 获取In-Reply-To
	 * 
	 * @param message
	 * @return String
	 * @throws MessagingException
	 */
	@Override
	public String getInReplyTo(Message message) throws MessagingException {
		String inReplyTo = null;
		if ((message.getHeader("In-Reply-To") != null
				&& message.getHeader("In-Reply-To").length > 0 && message
				.getHeader("In-Reply-To")[0] != null)) {
			inReplyTo = message.getHeader("In-Reply-To")[0];

		}
		return inReplyTo;
	}

	/**
	 * 获取References
	 * 
	 * @param message
	 * @return String
	 * @throws MessagingException
	 */
	@Override
	public String getReferences(Message message) throws MessagingException {
		String references = null;
		if ((message.getHeader("References") != null
				&& message.getHeader("References").length > 0 && message
				.getHeader("References")[0] != null)) {
			references = message.getHeader("References")[0];
		}
		return references;
	}

	/**
	 * 重新下载单封附件.
	 * 
	 * @param part
	 * @param attach
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void doloadAttach(Part part, Clickoo_message_attachment attach,
			EmailSupport eSupport) throws MessagingException, IOException {
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = mpart.getDisposition();
				if (mpart.isMimeType("multipart/*")) {
					doloadAttach((Part) mpart, attach, eSupport);
				} else {
					String contype = mpart.getContentType();
					if (contype.toLowerCase().indexOf("application") != -1
							&& (disposition == null)) {
						eSupport.setAttNum(eSupport.getAttNum() + 1);
						if (attach.getName() != null
								&& attach.getName().equals(
										MimeUtility.decodeText(mpart
												.getFileName()))
								&& attach.getLength() == mpart.getSize()) {
							attach.setIn(StreamUtil.getByteByInputStream(mpart
									.getInputStream()));
							return;
						}
					} else if (contype.toLowerCase().indexOf("name") != -1
							&& (disposition == null)) {
						eSupport.setAttNum(eSupport.getAttNum() + 1);
						if (attach.getName() != null
								&& attach.getName().equals(
										MimeUtility.decodeText(mpart
												.getFileName()))
								&& attach.getLength() == mpart.getSize()) {
							attach.setIn(StreamUtil.getByteByInputStream(mpart
									.getInputStream()));
							return;
						}
					}
					if ((disposition != null)
							&& ((Part.ATTACHMENT.equalsIgnoreCase(disposition)))) {
						eSupport.setAttNum(eSupport.getAttNum() + 1);
						if (attach.getName() != null
								&& attach.getName().equals(
										MimeUtility.decodeText(mpart
												.getFileName()))
								&& attach.getLength() == mpart.getSize()) {
							attach.setIn(StreamUtil.getByteByInputStream(mpart
									.getInputStream()));
							return;
						}
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			doloadAttach((Part) part.getContent(), attach, eSupport);
		}
	}

	/**
	 * 下载exitAtt以外的附件.
	 * 
	 * @param part
	 * @param exitAtt
	 * @param attachIn
	 * @param attachName
	 * @param attachSize
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void doloadAttachs(Part part,
			List<Clickoo_message_attachment> exitAtt,
			List<InputStream> attachIn, List<String> attachName,
			List<String> attachSize, EmailSupport eSupport)
			throws MessagingException, IOException {
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = mpart.getDisposition();
				if (mpart.isMimeType("multipart/*")) {
					doloadAttachs((Part) mpart, exitAtt, attachIn, attachName,
							attachSize, eSupport);
				} else {
					String contype = mpart.getContentType();
					if (contype.toLowerCase().indexOf("application") != -1
							&& (disposition == null)) {
						eSupport.setAttNum(eSupport.getAttNum() + 1);
						if (!containsAtt(exitAtt, MimeUtility.decodeText(mpart
								.getFileName()), mpart.getSize())) {
							if (!isLimitedAtt(mpart.getSize())) {
								attachIn.add(mpart.getInputStream());
							} else {
								attachIn.add(null);
							}
							attachName.add(MimeUtility.decodeText(mpart
									.getFileName()));
							attachSize.add(String.valueOf(mpart.getSize()));
						}
					} else if (contype.toLowerCase().indexOf("name") != -1
							&& (disposition == null)) {
						eSupport.setAttNum(eSupport.getAttNum() + 1);
						if (!containsAtt(exitAtt, MimeUtility.decodeText(mpart
								.getFileName()), mpart.getSize())) {
							if (!isLimitedAtt(mpart.getSize())) {
								attachIn.add(mpart.getInputStream());
							} else {
								attachIn.add(null);
							}
							attachName.add(MimeUtility.decodeText(mpart
									.getFileName()));
							attachSize.add(String.valueOf(mpart.getSize()));
						}
					}
					if ((disposition != null)
							&& ((Part.ATTACHMENT.equalsIgnoreCase(disposition)))) {
						eSupport.setAttNum(eSupport.getAttNum() + 1);
						if (!containsAtt(exitAtt, MimeUtility.decodeText(mpart
								.getFileName()), mpart.getSize())) {
							if (!isLimitedAtt(mpart.getSize())) {
								attachIn.add(mpart.getInputStream());
							} else {
								attachIn.add(null);
							}
							attachName.add(MimeUtility.decodeText(mpart
									.getFileName()));
							attachSize.add(String.valueOf(mpart.getSize()));
						}
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			doloadAttachs((Part) part.getContent(), exitAtt, attachIn,
					attachName, attachSize, eSupport);
		}
	}

	/**
	 * 封装attachment.
	 * 
	 * @param message
	 * @param exitAtt
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws IOException
	 */
	public List<Clickoo_message_attachment> getDownAttachList(Message message,
			List<Clickoo_message_attachment> exitAtt, EmailSupport eSupport)
			throws UnsupportedEncodingException, MessagingException,
			IOException {
		List<InputStream> attachIn = new ArrayList<InputStream>();
		List<String> attachName = new ArrayList<String>();
		List<String> attachSize = new ArrayList<String>();
		doloadAttachs((Part) message, exitAtt, attachIn, attachName,
				attachSize, eSupport);
		List<Clickoo_message_attachment> attachList = null;
		if (attachSize.size() > 0) {
			attachList = new ArrayList<Clickoo_message_attachment>();
		}
		boolean boo = message.getHeader("From")[0].indexOf("?utf-7?") != -1 ? true
				: false;
		for (int i = 0; i < attachName.size(); i++) {
			String name = boo ? new EncodeHandler()
					.UTF7T0Str(attachName.get(i)) : attachName.get(i);
			String type = null;
			Clickoo_message_attachment attach = new Clickoo_message_attachment();
			if (name.indexOf(".") != -1) {
				type = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
				name = name.substring(0, name.lastIndexOf("."));
			}
			attach.setName(name);
			attach.setType(type);
			attach.setLength(attachSize.get(i) == null ? 0 : Integer
					.parseInt(attachSize.get(i)));
			if (attachIn.get(i) != null) {
				byte[] b = StreamUtil.getByteByInputStream(attachIn.get(i));
				attach.setIn(b);
			}
			attachList.add(attach);
		}
		return attachList;
	}

	/**
	 * 根据名称大小判断 附件是否已被包含.
	 * 
	 * @param exitAtt
	 * @param attName
	 * @param size
	 * @return
	 */
	private boolean containsAtt(List<Clickoo_message_attachment> exitAtt,
			String attName, int size) {
		boolean bool = false;
		if (exitAtt != null) {
			for (Clickoo_message_attachment att : exitAtt) {
				if (att.getName() != null
						&& ((att.getName() + "." + att.getType())
								.equals(attName) && att.getLength() == size)) {
					bool = true;
				}
			}
		}
		return bool;
	}

	/**
	 * 判断附件是否超出限定大小来选择下载还是不下载.
	 * 
	 * @param size
	 * @return boolean
	 */
	private boolean isLimitedAtt(long size) {

		if (size > ATT_LIMIT_SIZE) {
			return true;
		}
		logger.info("ATT_LIMIT_SIZE:" + ATT_LIMIT_SIZE + " size:" + size);
		return false;
	}

	@Override
	public int getMessageSize(Message message) throws MessagingException {
		// TODO Auto-generated method stub
		return message.getSize();
	}
}