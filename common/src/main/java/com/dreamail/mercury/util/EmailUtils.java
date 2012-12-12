package com.dreamail.mercury.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;

import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailattachment;
import com.dreamail.mercury.domain.WebEmailbody;
import com.dreamail.mercury.domain.WebEmailhead;
import com.sun.jndi.toolkit.chars.BASE64Encoder;

@SuppressWarnings("restriction")
public class EmailUtils {
	public static final int MAX_DATA_LENGTH = 10240;
	public static final int MAX_DATA_LENGTHTOTAL = 8192;
	public static final int MAX_ATTACHMENTS = 10;
	
	public static final int MTK_DATA_LENGTH = 2*1024;
	public static final int MTK_ATTACHMENT_LENGTH = 2*1024;
	
	public static final int MAX_BODY_LENGTH = 15360;
	
	public static int calcEmailPojoSize(WebEmail email) {
		int size = 13 + 17 + 17;
		size += calcEmailheadSize(email.getHead());
		WebEmailattachment[] attachments = email.getAttach();
		if (attachments != null) {
			for (int i = 0; i < attachments.length; i++) {
				size += calcAttachmentHead(attachments[i]);
			}
		}
		return size;
	}

	public static int calcEmailbodySize(WebEmailbody body) {
		int size = 0;
		size += String.valueOf(body.getSize()).length() + 23;
		return size;
	}

	public static int calcAttachmentHead(WebEmailattachment attachment) {
		int size = 12 + 13;
		size += attachment.getAttid().length() + 15;
		size += String.valueOf(attachment.getSize()).length() + 13;
		if (attachment.getName() != null) {
			size += attachment.getName().length() + 13;
		}
		if (attachment.getType() != null) {
			size += attachment.getType().length() + 13;
		}
		size += attachment.getIspath().length() + 15;

		return size;
	}

	public static int calcEmailheadSize(WebEmailhead head) {

		int size = 0;
		size += String.valueOf(head.getMessageId()).length() + 23;

		if (head.getSubject() != null)
			size += head.getSubject().length() + 19;

		if (head.getFrom() != null)
			size += head.getFrom().length() + 13;

		if (head.getTo() != null)
			size += head.getTo().length() + 9;

		if (head.getCc() != null)
			size += head.getCc().length() + 9;

		if (head.getBcc() != null)
			size += head.getBcc().length() + 11;

		if (head.getReceiveTime() != null)
			size += head.getReceiveTime().length() + 27;

		return size;

	}

	/**
	 * 处理多附件的分包，每包限制最多是个附件.
	 * 
	 * @param email
	 * @param subPackageNo
	 * @return boolean true/false表示分包是否结束.
	 */
	public static boolean limitEmailPojoSize(WebEmail email, int subPackageNo) {

		WebEmailattachment[] attachments = email.getAttach();
		List<WebEmailattachment> atts = new ArrayList<WebEmailattachment>();

		if (attachments.length > MAX_ATTACHMENTS * subPackageNo) {
			for (int i = MAX_ATTACHMENTS * (subPackageNo - 1); i < MAX_ATTACHMENTS
					* subPackageNo; i++) {
				atts.add(attachments[i]);
			}
			WebEmailattachment[] attachs = new WebEmailattachment[atts.size()];
			for (int j = 0; j < atts.size(); j++) {
				attachs[j] = atts.get(j);
			}
			email.setAttach(attachs);
			return false;
		} else if (MAX_ATTACHMENTS * (subPackageNo - 1) < attachments.length
				&& MAX_ATTACHMENTS * subPackageNo >= attachments.length) {
			for (int i = MAX_ATTACHMENTS * (subPackageNo - 1); i < attachments.length; i++) {
				atts.add(attachments[i]);
			}
			WebEmailattachment[] attachs = new WebEmailattachment[atts.size()];
			for (int j = 0; j < atts.size(); j++) {
				attachs[j] = atts.get(j);
			}
			email.setAttach(attachs);
			return true;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param multiEmail
	 * @param allowlength
	 * @return
	 */
	public static String limitEmails(String multiEmail, int allowlength) {
		String retuEmails = multiEmail;
		if (allowlength < 0)
			return "";
		retuEmails.split(",;");
		String toEmails[] = parseString(retuEmails, ",;");
		int totalLength = 0;
		StringBuffer sbuffer = new StringBuffer();
		for (int ii = 0; ii < toEmails.length; ii++) {
			String strEmail = toEmails[ii].trim();
			totalLength += strEmail.length();
			if (totalLength > allowlength)
				break;
			sbuffer.append(strEmail).append(";");
		}
		retuEmails = sbuffer.toString();
		return retuEmails;

	}

	public static String[] parseString(String oldString, String delimiters) {
		StringTokenizer ddd = new StringTokenizer(oldString, delimiters);
		int counts = ddd.countTokens();
		String[] _biiAccounts = new String[counts];
		for (int i = 0; i < _biiAccounts.length; i++) {
			_biiAccounts[i] = (String) ddd.nextElement();
		}
		return _biiAccounts;
	}

	public static int xmlHeadSize() {
		return 0;
	}

	public static String changeByteToBase64(byte[] bytes) {
		String encryptedtext = "";
		if (bytes != null) {
			byte[] b = Base64.encodeBase64(bytes);
			try {
				encryptedtext = new String(b, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return encryptedtext;
	}

	public static byte[] base64TochangeByte(String s) {
		byte[] b = new byte[0];
		if (s != null) {
			try {
				b = Base64.decodeBase64(s.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return b;
	}

	public static byte[] sunChangeBase64ToByte(String s) {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b = new byte[0];
		if (s != null) {
			try {
				b = decoder.decodeBuffer(s);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return b;
	}

}
