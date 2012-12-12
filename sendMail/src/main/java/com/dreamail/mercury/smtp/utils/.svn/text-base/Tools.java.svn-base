package com.dreamail.mercury.smtp.utils;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tools {
	private static final Logger logger = LoggerFactory.getLogger(Tools.class);

	/**
	 * quoted-printable的java解码
	 * 
	 * @param str
	 * @return
	 */
	public static String changeQPToString(String str) {
		if (str == null) {
			return "";
		}
		try {
			str = str.replaceAll("=\n", "");
			byte[] bytes = str.getBytes("UTF-8");
			
			if (bytes == null) {
				return "";
			}
			
			for (int i = 0; i < bytes.length; i++) {
				byte b = bytes[i];
				if (b != 95) {
					bytes[i] = b;
				} else {
					bytes[i] = 32;
				}
			}

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			for (int i = 0; i < bytes.length; i++) {
				int b = bytes[i];
				if (b == '=') {
					try {
						int u = Character.digit((char) bytes[++i], 16);
						int l = Character.digit((char) bytes[++i], 16);
						if (u == -1 || l == -1) {
							continue;
						}
						buffer.write((char) ((u << 4) + l));
					} catch (ArrayIndexOutOfBoundsException e) {
						e.printStackTrace();
					}
				} else {
					buffer.write(b);
				}
			}
			return new String(buffer.toByteArray(), "UTF-8");
		} catch (Exception e) {
			logger.error("changeQPToString Exception : " + e);
			return "";
		}
	}

}
