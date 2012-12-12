package com.dreamail.mercury.yahooSNP;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class YahooArithmetic {

	private final static String Y64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "abcdefghijklmnopqrstuvwxyz" + "0123456789._";

	protected static MessageDigest md5Obj;

	static {
		try {
			md5Obj = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String yahoo64(byte[] buffer) {
		int limit = buffer.length - (buffer.length % 3);
		StringBuffer sb = new StringBuffer();
		// String out = "";
		int[] buff = new int[buffer.length];

		for (int i = 0; i < buffer.length; i++)
			buff[i] = (int) buffer[i] & 0xff;

		for (int i = 0; i < limit; i += 3) {
			sb.append(Y64.charAt(buff[i] >> 2));
			sb.append(Y64.charAt(((buff[i] << 4) & 0x30) | (buff[i + 1] >> 4)));
			sb.append(Y64.charAt(((buff[i + 1] << 2) & 0x3c)
					| (buff[i + 2] >> 6)));
			sb.append(Y64.charAt(buff[i + 2] & 0x3f));
			// out = out + Y64.charAt(buff[i] >> 2);
			// out = out
			// + Y64.charAt(((buff[i] << 4) & 0x30) | (buff[i + 1] >> 4));
			// out = out
			// + Y64.charAt(((buff[i + 1] << 2) & 0x3c)
			// | (buff[i + 2] >> 6));
			// out = out + Y64.charAt(buff[i + 2] & 0x3f);
		}
		int i = limit;
		switch (buff.length - i) {
		case 1:
			sb.append(Y64.charAt(buff[i] >> 2));
			sb.append(Y64.charAt(((buff[i] << 4) & 0x30)));
			sb.append("--");
			// out = out + Y64.charAt(buff[i] >> 2);
			// out = out + Y64.charAt(((buff[i] << 4) & 0x30));
			// out = out + "--";
			break;
		case 2:
			sb.append(Y64.charAt(buff[i] >> 2));
			sb.append(Y64.charAt(((buff[i] << 4) & 0x30) | (buff[i + 1] >> 4)));
			sb.append(Y64.charAt(((buff[i + 1] << 2) & 0x3c)));
			sb.append("-");
			// out = out + Y64.charAt(buff[i] >> 2);
			// out = out
			// + Y64.charAt(((buff[i] << 4) & 0x30) | (buff[i + 1] >> 4));
			// out = out + Y64.charAt(((buff[i + 1] << 2) & 0x3c));
			// out = out + "-";
			break;
		}

		return sb.toString();
	}

	public static byte[] md5(String s) throws NoSuchAlgorithmException {
		return md5(s.getBytes());
	}

	public static byte[] md5(byte[] buff) throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("MD5").digest(buff);
	}

	public static byte[] md5Singleton(byte[] buff)
			throws NoSuchAlgorithmException {
		md5Obj.reset();
		return md5Obj.digest(buff);
	}
}
