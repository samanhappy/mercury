package com.dreamail.mercury.receiver.attachment.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.junit.Test;

import com.dreamail.mercury.cache.VolumeCacheManager;
import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
import com.dreamail.mercury.mail.receiver.attachment.impl.TextFormatImpl;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;

public class TextTestCase {
	@Test
	public void textTest(){
		new VolumeCacheManager().init();
		HashMap<String, String> map = new HashMap<String, String> ();
		map.put("uid", "1005");
		map.put("name", "kai.li@163.com");
		map.put("mid", "100002");
		map.put("attName", "likai");
		map.put("image_state", "new");
		map.put("attachmentID", "2");
		InputStream input = null;
		
		try {
			input = new FileInputStream("D://test.txt");
			byte[] str = inputStreamToByte(input);
			input.close();
			AbstractAttachmentFormat f = new TextFormatImpl();
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
	    while ((ch = iStrm.read()) != -1)
	    {
	       bytestream.write(ch);
	    }
	    byte imgdata[]=bytestream.toByteArray();
	    bytestream.close();
	    return imgdata;
	  } 

	}
	

