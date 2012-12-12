package com.dreamail.mercury.mail.truepush;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TruepushTest {
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext(new String[] { "cspring-jms.xml" });
	}
}
