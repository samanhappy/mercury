package com.dreamail.mercury.mail.validate;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.dreamail.mercury.domain.TimeoutException;
import com.dreamail.mercury.domain.WebAccount;

public interface IMailValidate {
	/**
	 * 用户邮箱注册验证接口
	 * @param webContext
	 * @return
	 */
	public boolean validate(WebAccount account)throws MessagingException, TimeoutException, UnsupportedEncodingException;
}
