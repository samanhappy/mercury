package com.dreamail.mercury.util;

//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.HashMap;
//import org.junit.Test;
//import com.dreamail.mercury.configure.PropertiesDeploy;
//import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
//import com.dreamail.mercury.mail.receiver.attachment.impl.ImageFormatImpl;
//import com.dreamail.mercury.pojo.Clickoo_message_attachment;

public class ImageFormatImplTestCase {
//	@Test
//	public void ImageFormatImpl() throws Exception {
//		PropertiesDeploy p = new PropertiesDeploy();
//		p.init();
//
//		AbstractAttachmentFormat imgF = new ImageFormatImpl();
//		Clickoo_message_attachment attachment = new Clickoo_message_attachment();
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("uid", "1");
//		map.put("name", "lin.fu@163.com");
//		map.put("mid", "100003");
//		map.put("attName", "test1");
//		map.put("attachmentID", "2277");
//		map.put("deviceModelList", "100,50#240,180");
//		map.put("type", "gif");
//		InputStream in = new FileInputStream(new File("D:\\testimage\\timemb.GIF"));
//
//		byte by[] = InputStreamToByte(in);
//
//		attachment.setIn(by);
//
//		attachment.setName("aaaaaa");
//		attachment.setType("gif");
//
//		// 数据模拟
//		imgF.format(attachment, map);
//		in.close();
//	}
//
//	public byte[] InputStreamToByte(InputStream iStrm) throws IOException {
//		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
//		int ch;
//		while ((ch = iStrm.read()) != -1) {
//			bytestream.write(ch);
//		}
//		byte imgdata[] = bytestream.toByteArray();
//
//		bytestream.close();
//		return imgdata;
//	}
}
