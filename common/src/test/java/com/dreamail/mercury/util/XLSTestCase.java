package com.dreamail.mercury.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.junit.Test;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
import com.dreamail.mercury.mail.receiver.attachment.impl.XLSFormatImpl;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.util.UUIDHexGenerator;

public class XLSTestCase {
	@Test
	public void XLSFormatImplTest() {
		PropertiesDeploy pd = new PropertiesDeploy();
		pd.init();
		String suffixName = UUIDHexGenerator.getUUIDHex();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", "102");
		map.put("name", "si.li@163.com");
		map.put("mid", "100003");
		map.put("attName", suffixName);
		map.put("attachmentID", "2277");
		map.put("deviceModelList", "240,320#100,50");
		InputStream input = null;
		try {
			input = new FileInputStream("D://testxls//手机话费跟踪.xls");
			byte[] str = inputStreamToByte(input);
			input.close();
			AbstractAttachmentFormat f = new XLSFormatImpl();
			Clickoo_message_attachment attachment = new Clickoo_message_attachment();
			attachment.setName("test");
			attachment.setType("xls");
			attachment.setIn(str);

			f.format(attachment, map);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static byte[] inputStreamToByte(InputStream iStrm)
			throws IOException {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = iStrm.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}

	// public static void main(String args[]) throws IOException{
	// PropertiesDeploy p = new PropertiesDeploy();
	// p.init();
	//		
	// AttachmentFormatControl attachmentFormatControl = new
	// AttachmentFormatControl();
	// Attachment att = new Attachment();
	// att.setName("1.jpg");
	// att.setType("jpg");
	// att.setLength(120);
	// InputStream in = new FileInputStream(new File("c:/1.jpg"));
	// byte by[] = InputStreamToByte(in);
	// att.setIn(by);
	// attachmentFormatControl.getSourceAttachment(att, "1", "100,50");
	// }
}
