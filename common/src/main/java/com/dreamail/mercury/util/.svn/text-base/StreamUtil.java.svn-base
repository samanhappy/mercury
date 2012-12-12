package com.dreamail.mercury.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.domain.TrunOn;

import cpdetector.io.ASCIIDetector;
import cpdetector.io.CodepageDetectorProxy;
import cpdetector.io.JChardetFacade;
import cpdetector.io.ParsingDetector;
import cpdetector.io.UnicodeDetector;

public class StreamUtil {
	public static final Logger logger = LoggerFactory
			.getLogger(StreamUtil.class);
	
	/**
	 * 将流转化成byte[]
	 * @param in
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] getByteByInputStream(InputStream is)
			throws IOException {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		byte[] data = null;
		try {
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			data = bytestream.toByteArray();
		} catch (Exception e) {
			logger.error("ByteByInputStream err!!", e);
			e.printStackTrace();
		} finally {
			if (bytestream != null) {
				bytestream.close();
				bytestream = null;
			}
			if (is != null) {
				is.close();
				is = null;
			}
		}
		return data;
	}

	/**
	 * 将地址转化成byte[]
	 * @param in
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] readFile(String path) {
		FileChannel fiChannel = null;
		byte[] bytes = null;
		ByteBuffer mBuf = null;
		try {
			fiChannel = new FileInputStream(path).getChannel();
			long size = fiChannel.size();
			mBuf = ByteBuffer.allocate((int) size);
			fiChannel.read(mBuf);
			bytes = mBuf.array();
		} catch (Exception e1) {
			logger.error("readFile err!!", e1);
			e1.printStackTrace();
		}finally{
			try {
				if(mBuf!=null){
					mBuf.clear();
				}
				if(fiChannel!=null){
					fiChannel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	/**
	 * 把byte数组按照合适的编码方式转化成字符串
	 * 
	 * @param bytes
	 *            byte array
	 * @return string
	 */
	public static String getString(byte[] bytes) {

		if (bytes == null)
			return null;
		if (bytes.length == 0)
			return "";
		String encoding = "UTF-8";
		InputStream is = new ByteArrayInputStream(bytes);
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(JChardetFacade.getInstance());
		detector.add(new ParsingDetector(false));
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		Charset charset = null;
		try {
			charset = detector.detectCodepage(is, Integer.MAX_VALUE);
		} catch (Exception e) {
			logger.error("error to get string from byte array\n", e);
		} finally {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (charset != null) {
			// encoding根据统计学获得编码方式
			encoding = charset.name();
			// gbk包含简体中文和繁体中文
			if (encoding.toUpperCase().equals("big5".toUpperCase())) {
				encoding = "gbk";
			}
			// 是一种用来表示日语汉字的编码方法 等同于 GB2312
			else if (encoding.toUpperCase().equals("EUC-JP".toUpperCase())) {
				encoding = "GB2312";
			}
			else if (encoding.toUpperCase().equals("UTF-8".toUpperCase())) {
				encoding = "UTF-8";
			}
			else if (encoding.toUpperCase().equals("GB2312".toUpperCase())) {
				encoding = "GB2312";
			}
			else{
				encoding = "gbk";
			}
			logger.info("detect encode is " + encoding);
		}
		String str = null;
		try {
			str = new String(bytes, encoding);
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncoding " + encoding);
		}
		return str;
	}

	
	/**
	 * 获得第N包bytes
	 * @param seqNo 次数
	 * @param path 文件
	 * @return TrunOn 
	 */
	public static TrunOn getFileTrunOn(int seqNo, String path) {
		ByteBuffer buffer = null;
		FileInputStream input = null;
		FileChannel channel = null;
		TrunOn obj = new TrunOn();
		int size = 0;
		long limit = EmailUtils.MTK_ATTACHMENT_LENGTH;
		long end = limit;
		long begin = 0;
		if (seqNo != 1) {
			begin = (seqNo * limit)-limit;
			end = seqNo * limit;
		}
		try {
			input = new FileInputStream(path);
			channel = input.getChannel();
			long fileSize = channel.size();
			if (fileSize < begin) {
				return null;
			}
			if(fileSize <= end){
				end = fileSize;
				obj.setEndFlag(true);
			}
			size = (int) (end-begin);
			buffer = ByteBuffer.allocate(size);
			channel.read(buffer, begin);
			obj.setBody(buffer.array());
			return obj;
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(channel!=null && channel.isOpen()){
				try {
					channel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(buffer!=null){
				buffer.clear();
			}
		}
		return null;
	}

	public static void main(String[] args) {
//		String path = "C:\\clickoo_push_mail详细设计文档.doc";
//		File file = new File(path);
//		byte[] bytes = new byte[(int) file.length()];
//		bytes = readFile(path);
//		int seqNo = 1;
//		int position = 0;
//		while(true){
//			TrunOn obj = getFileTrunOn(seqNo,path);
//			if (seqNo != 1) {
//				position = (seqNo * 10240)-10240;
//			}
//			System.arraycopy(obj.getBody(), 0, bytes, position, obj.getBody().length);
//			obj.getBody();
//			if(obj.isEndFlag()){
//				break;
//			}
//			++ seqNo;
//		}
//		//将数据的索引1开始的3个数据复制到目标的索引为0的位置上
//		//System.arraycopy(ids, 1, ids2, 0, 3);
//		System.out.println(seqNo);
//		try {
//			FileOutputStream fo = new FileOutputStream("c:\\2clickoo_push_mail详细设计文档.doc");
//			fo.write(bytes);
//			fo.flush();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
		String result = getString(readFile("D:\\testtxt\\新建 文本文档.txt"));
		System.out.println(result);
	}
}
