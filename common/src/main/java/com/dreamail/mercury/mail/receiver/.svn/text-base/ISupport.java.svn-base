package com.dreamail.mercury.mail.receiver;

import javax.mail.Folder;
import javax.mail.MessagingException;

import com.dreamail.mercury.domain.WebAccount;

public interface ISupport {
	/**
	 * 判断账号是否支持getAlluid.
	 * @param account
	 * @return WebAccount
	 */
	WebAccount isSupportAllUid(WebAccount account,Folder inbox);
	/**
	 * 判断账号是否支持排序.
	 * @param account
	 * @return WebAccount
	 */
	WebAccount isSupportCompositor(WebAccount account,Folder inbox);
	/**
	 * 判断imap账号是否支持IDLE.
	 * @param account
	 * @return
	 */
	WebAccount isSupportIDLE(WebAccount account);
	/**
	 * 获取账号所有支持的特性.
	 * @param account
	 * @return
	 * @throws MessagingException
	 */
	WebAccount getAllSupport(WebAccount account) throws MessagingException;
}
