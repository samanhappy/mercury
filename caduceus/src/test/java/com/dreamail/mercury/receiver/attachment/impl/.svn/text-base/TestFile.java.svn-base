package com.dreamail.mercury.receiver.attachment.impl;

import java.io.File;
import java.io.RandomAccessFile;

import org.junit.Test;

public class TestFile {
	@Test
	public void t(){
		String str = "aaaaaaaaaaaaaaaaaa";
		File filepath;
		filepath = new File(System.getProperty("java.io.tmpdir")
				+ File.separator + "pushMail" + File.separator + "ttttttttttttttttttttt"
				+ File.separator + "55555555555555");
		filepath.mkdirs();
		RandomAccessFile dos = null;
		try {
			File txt = new File(filepath, File.separator + "aaa.txt");
			dos = new RandomAccessFile(txt, "rw");
			dos.seek(dos.length());
			dos.write(str.getBytes());
			//获取存储后的文件path和文件length
			System.out.println(filepath.getPath());
			System.out.println(getFileSizes(txt));
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	
	
	public long getFileSizes(File file) throws Exception {
		long length = 0;
		if (file.exists()) {
			length = file.length();
		} else {
			file.createNewFile();
		}

		return length;
	}
}
