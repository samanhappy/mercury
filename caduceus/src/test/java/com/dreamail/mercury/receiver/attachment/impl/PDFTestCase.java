package com.dreamail.mercury.receiver.attachment.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.junit.Test;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
import com.dreamail.mercury.mail.receiver.attachment.impl.PDFFormatImpl;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;

public class PDFTestCase {
	@Test
	public void PDFFormatImplTest() {
		Long start = System.nanoTime();
		Long s1 = System.currentTimeMillis();
		PropertiesDeploy pd = new PropertiesDeploy();
		pd.init();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", "102");
		map.put("name", "si.li@163.com");
		map.put("mid", "100003");
		map.put("attName", "test");
		map.put("deviceModel", "100,50");
		map.put("attachmentID", "2277");
		InputStream input = null;

		try {
			input = new FileInputStream("C://test.pdf");
			byte[] str = inputStreamToByte(input);
			input.close();
			AbstractAttachmentFormat f = new PDFFormatImpl();
			Clickoo_message_attachment attachment = new Clickoo_message_attachment();
			attachment.setName("test");
			attachment.setType("pdf");
			attachment.setIn(str);

			f.format(attachment, map);
			Long end = System.nanoTime();
			Long e1 = System.currentTimeMillis();
			Long time = end - start;
			Long t1 = e1 - s1;
			System.out.println("time=====" + time + "ns");
			System.out.println("t1=====" + t1 + "ms");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
