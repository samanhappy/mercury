package com.dreamail.mercury.receiver.mail.encode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeUtility;

import com.beetstra.jutf7.CharsetProvider;

/**
 * 处理邮件中出现的"utf-7"编码
 * 
 * @author pengkai.wang
 */
public class EncodeHandler {

	/**
	 * 解析以utf-7编码的邮件头信息
	 * @param msg
	 * @return String
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public String parseMsgSubject(Message msg) throws MessagingException, UnsupportedEncodingException {
		String subject = msg.getSubject();
		if (msg.getSubject().toLowerCase().indexOf("utf-7") != -1) {
			subject = UTF7T0Str(subject.toLowerCase().substring(subject.indexOf("?Q?") + 3, subject.length() - 2));
		} else {
			subject = MimeUtility.decodeText(subject);
		}
		return subject;
	}
	
	/**
	 * 解析以utf-7编码的邮件体信息
	 * @param bodyPart
	 * @return String
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String parseMsgContent(Part bodyPart) throws MessagingException, IOException {
		String content = "";
		byte[] dataByte = new byte[bodyPart.getInputStream().available()];
		bodyPart.getInputStream().read(dataByte);
		String mimeType = bodyPart.getContentType();
		if (mimeType != null) {
			if (mimeType.toLowerCase().indexOf("utf-7") != -1) {
				content += UTF7T0Str(new String(dataByte));
			} else {
				content += new String(dataByte, "utf-8");
			}
		}
		return content;
	}
	
	/**
	 * 解析utf-7编码的字符串
	 * @param utf7Str
	 * @return String
	 */
	public String UTF7T0Str(String utf7Str) {
		String retuStr = "";
		CharsetProvider charsetProvider = new CharsetProvider();
		Charset charset = charsetProvider.charsetForName("utf-7");
		ByteBuffer byteBuffer = ByteBuffer.wrap(utf7Str.getBytes());
		retuStr = charset.decode(byteBuffer).toString();
		return retuStr;
	}
}
