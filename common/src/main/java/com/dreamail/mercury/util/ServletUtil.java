package com.dreamail.mercury.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletUtil {
	public static final Logger logger = LoggerFactory
			.getLogger(ServletUtil.class);

	/**
	 * 将stream的数据流解压缩
	 * 
	 * @param in
	 * @return 解压后的String
	 * @throws IOException
	 */
	public static String parse(InputStream in) {
		logger.info("parse from text...");
		StringBuffer strBuf = null;
		BufferedReader br = null;
		try {
			strBuf = new StringBuffer();
			byte[] data = new byte[0];
			byte[] buffer = new byte[1024];
			int l = in.read(buffer);
			while (l != -1) {
				data = getByte(data, buffer, l);
				l = in.read(buffer);
			}
			br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(data)));
			String str = br.readLine();
			while (str != null) {
				strBuf.append(str);
				str = br.readLine();
			}
		} catch (Exception e) {
			logger.error("parse error...", e);
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("close BufferedReader err!!", e);
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close in err!!", e);
					e.printStackTrace();
				}
			}
		}
		return strBuf.toString();
	}

	/**
	 * 将stream的数据流解压缩
	 * 
	 * @param in
	 * @return 解压后的gzip String
	 * @throws IOException
	 */
	public static String parseGzip(InputStream in) {
		logger.info("parse from gzip...");
		StringBuffer strBuf = null;
		BufferedReader br = null;
		try {
			strBuf = new StringBuffer();
			byte[] data = new byte[0];
			byte[] buffer = new byte[1024];
			int l = in.read(buffer);
			while (l != -1) {
				data = getByte(data, buffer, l);
				l = in.read(buffer);
			}
			br = new BufferedReader(new InputStreamReader(new GZIPInputStream(
					new ByteArrayInputStream(data))));
			String str = br.readLine();
			while (str != null) {
				strBuf.append(str);
				str = br.readLine();
			}
		} catch (Exception e) {
			logger.error("parse gzip error...", e);
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("close BufferedReader err!!", e);
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close in err!!", e);
					e.printStackTrace();
				}
			}
		}
		return strBuf.toString();
	}

	/**
	 * 将stream的lzw数据流解压缩
	 * 
	 * @param in
	 * @return 解压后的String
	 * @throws IOException
	 */
	public static String parseLzw(InputStream in) {
		logger.info("parse from lzw...");
		String str = null;
		try {
			str = replaceHttpBlank(new LzwDecompression().expand(in));
		} catch (IOException e) {
			logger.info("error parse to lzw...", e);
		}
		return str;
	}

	private static byte[] getByte(byte[] data, byte[] buffer, int l) {
		byte[] all = new byte[data.length + l];
		for (int i = 0; i < data.length; i++) {
			all[i] = data[i];
		}
		for (int i = 0; i < l; i++) {
			all[data.length + i] = buffer[i];
		}
		return all;
	}

	public static String replaceHttpBlank(String str) 
	{ 
	   return Pattern.compile("\r|\n").matcher(str).replaceAll(""); 
	} 
	
	public static void main(String[] args) throws IOException {
		System.out.println(ServletUtil.parse(new ByteArrayInputStream("hello"
				.getBytes())));

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		BufferedOutputStream bufos = null;
		bufos = new BufferedOutputStream(new GZIPOutputStream(bos));
		bufos.write("hello".getBytes());
		bufos.close();
		System.out.println(ServletUtil.parseGzip(new ByteArrayInputStream(bos
				.toByteArray())));

		System.out.println(ServletUtil.parseLzw(new ByteArrayInputStream(
				new LzwCompression().compress("hello"))));
	}

}
