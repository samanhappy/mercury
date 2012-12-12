package com.dreamail.mercury.petasos.impl.email;

import java.util.HashMap;
import java.util.List;

import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.Clickoo_send_message;

public interface IEmailSend {
	/**
	 * 回复邮件
	 */
	public void replyMail(WebEmail email, WebAccount account,
			Clickoo_mail_account mail_account, Cred cred,List<Object> objects);

	/**
	 * 发送邮件
	 */
	public void sendMail(WebEmail email, WebAccount account,
			Clickoo_mail_account mail_account, Cred cred, List<Object> objects);

	/**
	 * 发件箱邮件转发
	 */
	public void sendBoxForWord(Clickoo_send_message message,WebAccount account, WebEmail email, Cred cred,
			Clickoo_mail_account mail_account, Status s,List<Object> objects,HashMap<String, String> meta);

	/**
	 * 收件箱邮件转发
	 */
	public void inBoxForWord(Clickoo_message message,WebAccount account, WebEmail email, Cred cred,
			Clickoo_mail_account mail_account, Status s,List<Object> objects);

}
