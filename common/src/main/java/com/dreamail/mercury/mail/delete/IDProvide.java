package com.dreamail.mercury.mail.delete;

import java.util.List;

import com.dreamail.mercury.pojo.Clickoo_mail_account;

public interface IDProvide {
	/**
	 * 删除邮件
	 * 
	 * @param account
	 * @param uuidList
	 * @return
	 */
	public boolean dEmail(Clickoo_mail_account account, List<String> uuidList);

}
