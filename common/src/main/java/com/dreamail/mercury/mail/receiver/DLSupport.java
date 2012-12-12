package com.dreamail.mercury.mail.receiver;

import com.dreamail.mercury.domain.EmailCacheObject;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.pojo.Clickoo_message;

public interface DLSupport {
	/**
	 * 根据constant下载邮件、正文、附件等.
	 * @param emailCache
	 * @param constant
	 * @param account
	 * @param webEmail
	 * @param uid
	 * @param mid
	 * @return
	 */
	Object dlMail(EmailCacheObject emailCache,String constant,Clickoo_message message,WebAccount account, WebEmail webEmail,String uid,String mid)throws Exception;
}
