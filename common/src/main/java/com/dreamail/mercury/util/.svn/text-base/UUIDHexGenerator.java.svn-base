/**
 * 
 */
package com.dreamail.mercury.util;


/**
 * 
 * 
 * @author meng.sun
 * 
 */
public class UUIDHexGenerator {

	private static short counter = (short) 0;

	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	protected synchronized short getCount() {
		if (counter < 0 || counter == Short.MAX_VALUE) {
			counter = 0;
		}
		return counter++;
	}

	protected int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	public String generate() {
		return new StringBuffer().append(format(getLoTime())).append(
						format(getCount())).toString();
	}

	/**
	 * 生成UUID
	 * 
	 * @return UUID
	 */
	public static String getUUIDHex() {
		UUIDHexGenerator uuid = new UUIDHexGenerator();
		return uuid.generate();
	}

}
