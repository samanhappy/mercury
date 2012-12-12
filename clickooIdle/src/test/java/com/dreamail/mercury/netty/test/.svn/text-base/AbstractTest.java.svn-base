package com.dreamail.mercury.netty.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.dreamail.mercury.imap.impl.AbstractProcess;

public class AbstractTest {
	@Test
	public void testSetFlags(){
		List<String> uuid = new ArrayList<String>();
		uuid.add("2");
		uuid.add("3");
		uuid.add("4");
		AbstractProcess ap = new AbstractProcess();
		HashMap<String,Integer>isSucess = ap.setFlags(Long.parseLong("3"), uuid, 8, 1);
		System.out.println(isSucess+"-------------------------------");
	}

}
