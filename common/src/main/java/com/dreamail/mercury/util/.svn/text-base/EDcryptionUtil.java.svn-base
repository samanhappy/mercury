package com.dreamail.mercury.util;


public class EDcryptionUtil {

	/**
	 * 简单可逆加密算法实现.
	 * 
	 * @param plaintext 明文
	 * @param key 密钥
	 * @return
	 */
	public static String EDcrype(String plaintext, String key) {
		
		StringBuffer ciphertext = new StringBuffer();
		int ctr = 0;
		for (int i = 0; i < plaintext.length(); i++) {
			if (ctr == key.length()) {
				ctr = 0;
			} 
			int c = plaintext.charAt(i) ^ key.charAt(ctr);
			ciphertext.append((char) c);
			ctr++;
		}
		return ciphertext.toString();
	}
	
	public static byte[] EDcrype(byte[] plaintext, String key) {
		
//		StringBuffer ciphertext = new StringBuffer();
		byte[] b = new byte[plaintext.length];
		int ctr = 0;
		for (int i = 0; i < plaintext.length; i++) {
			if (ctr == key.length()) {
				ctr = 0;
			} 
			int c = plaintext[i] ^ key.charAt(ctr);
			b[i]= (byte)c;
			ctr++;
		}
		return b;
	}
	
	
	public static void main(String[] args) throws Exception {
		String t = EDcrype("aaa","test");
		
		System.out.println(t);
		System.out.println(new String(EDcrype(t.getBytes(),"test")));
	}
}
