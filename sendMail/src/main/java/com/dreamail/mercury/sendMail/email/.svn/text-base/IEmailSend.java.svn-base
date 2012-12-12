package com.dreamail.mercury.sendMail.email;

import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_send_message;

public interface IEmailSend {
	/**
	 * 发送普通邮件
	 * 
	 * @param uid
	 * @param mid
	 * @param oldmid
	 * @param hid
	 */
	public void commonMail(String uid, String mid, String oldmid, String hid,
			String type);

	/**
	 * 回复邮件
	 * 
	 * @param uid
	 * @param mid
	 * @param oldmid
	 * @param hid
	 */
	public void replyMail(String uid, String mid, String oldmid, String hid,
			String type);

	/**
	 * 发件箱邮件转发
	 * 
	 * @param uid
	 * @param mid
	 * @param oldmid
	 * @param hid
	 */
	public void sendBoxForWord(String uid, String mid, String oldmid,
			String hid, String type);

	/**
	 * 收件箱邮件转发
	 * 
	 * @param uid
	 * @param mid
	 * @param oldmid
	 * @param hid
	 */
	public void inBoxForWord(String uid, String mid, String oldmid, String hid,
			String type);

	/**
	 * 判断邮件发送方式
	 * 
	 * @param email
	 * @param uid
	 * @return
	 */
	public boolean checkSendType(WebEmail email,
			Clickoo_send_message clickooMessage, String mid, String uid,
			String aid, String oldmid, String hid, String type);

	/**
	 * 转换WebEmail
	 * 
	 * @param email
	 * @param clickooMailAccount
	 * @param clickooMessage
	 * @return
	 */
	public WebEmail transformToWenEmail(Email email,
			Clickoo_mail_account clickooMailAccount,
			Clickoo_send_message clickooMessage, String newmid, String oldmid);
}
