package com.dreamail.mercury.smtp.utils;

public class EmlIdGenerator {
	private static int emlid = 0;

	public static synchronized int getEmlid() {
		if (emlid == 1000000) {
			emlid = 0;
		}
		return emlid++;
	}
}
