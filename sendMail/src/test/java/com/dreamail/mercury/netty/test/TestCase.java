package com.dreamail.mercury.netty.test;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.smtp.utils.User;
import com.dreamail.mercury.util.EmailUtils;

public class TestCase {

	@Test
	public void testMap() {
		Map<Integer, String> map = new ConcurrentHashMap<Integer, String>();
		map.put(5, "12312");
		map.put(2, "dsaf");
		map.put(8, "1dfd");
		map.put(1, "23dfd");
		map.put(9, "2dfd");
		System.out.println(map);

		SortedMap<Integer, String> timerMap = Collections
				.synchronizedSortedMap(new TreeMap<Integer, String>());
		timerMap.put(5, "12312");
		timerMap.put(2, "dsaf");
		timerMap.put(8, "1dfd");
		timerMap.put(1, "23dfd");
		timerMap.put(9, "2dfd");
		System.out.println(timerMap);

		System.out.println(System.currentTimeMillis());
		try {
			Thread.sleep(4 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis());
	}

	@Test
	public void testSunString() {
		String s = "kai_li_mind_2@yahoo.com";
		System.out.println(s.substring(s.lastIndexOf("@") + 1));
		System.out.println(s.substring(s.lastIndexOf("@") + 1).split("[.]")[0]);
	}

	@Test
	public void testGetAccountNameByMid() {
		String name = new AccountDao().getAccountNameByMid("1").split("@")[1]
				.split("[.]")[0];
		System.out.println(name);

		User user = new User();
		user.setUserName("kai_li_mind_2@yahoo.com");
		user.setPassword("arhermind");
		try {
			String str = testAddrToBase64(user.getUserName()
					+ user.getPassword());
			System.out.println(str);
			testUserName(str);

			System.err.println(testAddrToBase64("archermind0101@yahoo.com"));
			System.err.println(testAddrToBase64("123456"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String testAddrToBase64(String str)
			throws UnsupportedEncodingException {
		byte[] b = str.getBytes("UTF-8");
		return EmailUtils.changeByteToBase64(b);
	}

	public void testUserName(String str) {
		User user = new User();
		byte[] byArray = EmailUtils.sunChangeBase64ToByte(str);
		String userNameUTF8 = new String(byArray);
		System.out.println(userNameUTF8);
		if (userNameUTF8.contains("@yahoo.com")) {
			String[] type = userNameUTF8.split("@yahoo.com");
			user.setUserName(type[0].trim() + "@yahoo.com");
			user.setPassword(type[1].trim());
		}
		System.out.println(user.getUserName());
		System.out.println(user.getPassword());
		System.out.println(user.getSuffix());
	}

	@Test
	public void testFor() {
		for (int i = 0; i < 50; i++) {
			if (i == 26)
				continue;
			if (i == 30)
				break;
			System.out.println(i);
		}
	}
}
