package com.dreamail.mercury.receiver.attachment.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class ImageFormatImplTestCase {
	static {
		System.setProperty("jmagick.systemclassloader", "no");
	}

	@Test
	// public void ImageFormatImpl() throws Exception {
	// Long start = System.nanoTime();
	// Long s1 = System.currentTimeMillis();
	// PropertiesDeploy p = new PropertiesDeploy();
	// p.init();
	//
	// AbstractAttachmentFormat imgF = new ImageFormatImpl();
	// Clickoo_message_attachment attachment = new Clickoo_message_attachment();
	// HashMap<String, String> map = new HashMap<String, String>();
	// map.put("uid", "1");
	// map.put("name", "lin.fu@163.com");
	// map.put("mid", "100003");
	// map.put("attName", "test1");
	// map.put("attachmentID", "2277");
	// InputStream in = new FileInputStream(new File("c:/test1.jpeg"));
	//
	// byte by[] = InputStreamToByte(in);
	//
	// attachment.setIn(by);
	//
	// attachment.setName("aaaaaa");
	// attachment.setType("gif");
	//
	// // 数据模拟
	// imgF.format(attachment, map);
	//
	// in.close();
	// Long end = System.nanoTime();
	// Long e1 = System.currentTimeMillis();
	// Long time = end - start;
	// Long t1 = e1 - s1;
	// System.out.println("time=====" + time + "ns");
	// System.out.println("t1=====" + t1 + "ms");
	// }
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
