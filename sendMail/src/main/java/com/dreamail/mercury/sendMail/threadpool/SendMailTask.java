package com.dreamail.mercury.sendMail.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EDU.oswego.cs.dl.util.concurrent.Callable;

import com.dreamail.mercury.sendMail.email.impl.EmailSend;

public class SendMailTask implements Callable {
	private static final Logger logger = LoggerFactory
			.getLogger(SendMailTask.class);
	private String uid;
	private String mid;
	private String sendMailType;
	private String oldmid;
	private String hid;

	public SendMailTask(String uid, String mid, String sendMailType,
			String oldmid, String hid) {
		this.uid = uid;
		this.mid = mid;
		this.sendMailType = sendMailType;
		this.oldmid = oldmid;
		this.hid = hid;
	}

	@Override
	public Object call() throws Exception {
		logger.info("uid [" + uid + "] mid [" + mid + "] sendMailType ["
				+ sendMailType + "] oldmid [" + oldmid + "] hid [" + hid + "]");
		new EmailSend().emailSendEntrance(uid, mid, sendMailType, oldmid, hid);
		return null;
	}

}
