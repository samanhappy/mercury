package com.dreamail.mercury.receiver.attachment.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.junit.Test;

import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
import com.dreamail.mercury.mail.receiver.attachment.impl.NoSupportFormatImpl;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;

public class NoSupportTestCase {

	@Test
	public void noSupportFormatImplTest() {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", "104");
		map.put("name", "honwei.pang@163.com");
		map.put("mid", "100002");
		map.put("attName", "likai");
		map.put("type", "ppt");
		InputStream input = null;
		System.out.println("panghongwei");

		try {
			input = new FileInputStream("D://test.ppt");
			byte[] str = inputStreamToByte(input);
			input.close();
			AbstractAttachmentFormat f = new NoSupportFormatImpl();
			Clickoo_message_attachment attachment = new Clickoo_message_attachment();
			attachment.setIn(str);

			f.format(attachment, map);
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
