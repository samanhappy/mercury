package com.dreamail.mercury.receiver.attachment.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.junit.Test;

public class AttachmentPathTestCase {
	@Test
	public void AttachmentPathTest(){
//		String str = null;
		HashMap<String, String> map = new HashMap<String, String> ();
		map.put("uid", "105");
		map.put("name", "wu.wang@163.com");
		map.put("mid", "100002");
		map.put("attName", "wangwu");
		InputStream inputStrem; 
		
		try {
			File file = new File("D:\\test.doc");
            System.out.println(file.exists());
			inputStrem = new FileInputStream(file);
//			WordExtractor extractor = new WordExtractor(inputStrem);
			inputStrem.close();
//		    str = extractor.getText();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		
		try {
//			ap.getAttachmentPath(str, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
