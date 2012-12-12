/**
 * 
 */
package com.dreamail.mercury.util;

import org.junit.Test;

import com.dreamail.mercury.util.UUIDHexGenerator;

/**
 * @author meng.sun
 * 
 */
public class UUIDHexGeneratorTest {

	@Test
	public void testGeneratot() {
		for (int i = 0; i <= 10; i++) {
			System.out.println(UUIDHexGenerator.getUUIDHex());
		}
	}

}
