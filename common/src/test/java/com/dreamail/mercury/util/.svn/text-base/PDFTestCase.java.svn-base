package com.dreamail.mercury.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
import com.dreamail.mercury.mail.receiver.attachment.impl.PDFFormatImpl;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;

public class PDFTestCase {
	@Test
	public void PDFFormatImplTest() {
		PropertiesDeploy pd = new PropertiesDeploy();
		pd.init();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", "102");
		map.put("name", "si.li@163.com");
		map.put("mid", "100003");
		map.put("attName", "test");
		map.put("deviceModelList", "100,50");
		map.put("attachmentID", "2277");
		InputStream input = null;
		try {
			input = new FileInputStream("D://testpdf//SoapUI的安装与使用.pdf");
			byte[] str = inputStreamToByte(input);
			input.close();
			AbstractAttachmentFormat f = new PDFFormatImpl();
			Clickoo_message_attachment attachment = new Clickoo_message_attachment();
			attachment.setName("test");
			attachment.setType("pdf");
			attachment.setIn(str);
			List<Clickoo_message_attachment> list = f.format(attachment, map);
			System.out.println(attachment.getPath());
			System.out.println(attachment.getType());
			System.out.println("------------------------------");
			for (Clickoo_message_attachment attach : list) {
				System.out.println(attach.getType());
				System.out.println(attach.getPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public byte[] inputStreamToByte(InputStream iStrm) throws IOException {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = iStrm.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}
}
