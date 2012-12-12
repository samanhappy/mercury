package com.dreamail.mercury.smtp.impl;

import com.dreamail.mercury.util.EmailUtils;

public class AbstractProcess {

	public String changeBase64ToString(String str) {

		byte[] byArray = EmailUtils.sunChangeBase64ToByte(str);

		return new String(byArray);

	}

	

}
