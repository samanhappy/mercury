package com.dreamail.mercury.sendMail.threadpool;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EDU.oswego.cs.dl.util.concurrent.FutureResult;

import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.dao.SendFailedMessageDao;
import com.dreamail.mercury.sendMail.threadpool.impl.GmailSenderThreadPoolImpl;
import com.dreamail.mercury.sendMail.threadpool.impl.HotmailSenderThreadPoolImpl;
import com.dreamail.mercury.sendMail.threadpool.impl.OthersSenderThreadPoolImpl;
import com.dreamail.mercury.sendMail.threadpool.impl.YahooSenderThreadPoolImpl;
import com.dreamail.mercury.util.JMSConstans;

public class PutThreadPool {
	private static final Logger logger = LoggerFactory
			.getLogger(PutThreadPool.class);

	public static SendMailThreadPool yahoothreadpool = null;
	public static SendMailThreadPool gmailthreadpool = null;
	public static SendMailThreadPool hotmailthreadpool = null;
	public static SendMailThreadPool othersthreadpool = null;

	static {
		yahoothreadpool = new YahooSenderThreadPoolImpl();
		logger.info("yahoo pool provide....");
		gmailthreadpool = new GmailSenderThreadPoolImpl();
		logger.info("gmail pool provide....");
		hotmailthreadpool = new HotmailSenderThreadPoolImpl();
		logger.info("hotmail pool provide....");
		othersthreadpool = new OthersSenderThreadPoolImpl();
		logger.info("others pool provide....");
	}

	public static void putPool(Object msg) {
		JSONObject json = JSONObject.fromObject(msg);
		logger.info("json----------" + json);
		// 处理发送邮件消息
		if (json == null
				|| !JMSConstans.JMS_SENDMAIL_TYPE.equals(json
						.get(JMSConstans.JMS_TYPE_KEY))) {
			logger.info("not send mail type message, do nothing...");
			return;
		}
		String uid = json.getString(JMSConstans.JMS_UID_KEY);
		String mid = json.getString(JMSConstans.JMS_MID_KEY);
		String sendMailType = json.getString(JMSConstans.JMS_SENDMAIL_VALUE);
		String oldmid = json.getString(JMSConstans.JMS_OLD_MID_KEY);
		String hid = json.getString(JMSConstans.JMS_HID_KEY);
		SendMailTask callable = new SendMailTask(uid, mid, sendMailType,
				oldmid, hid);
		String name = new AccountDao().getAccountNameByMid(mid);
		if (name == null || "".equals(name)) {
			logger.info("not find account by mid, delete this message...");
			new SendFailedMessageDao().deleteSendFailedMessageByMid(mid);
			return;
		}
		name = name.split("@")[1].split("[.]")[0];
		logger.info("current server type is [" + name + "]");
		try {
			if (("yahoo").equals(name) || ("ymail").equals(name)
					|| ("rocketmail").equals(name)) {
				yahoothreadpool.execute(new FutureResult().setter(callable));
			} else if (("gmail").equals(name)) {
				gmailthreadpool.execute(new FutureResult().setter(callable));
			} else if (("hotmail").equals(name) || ("live").equals(name)
					|| ("msn").equals(name)) {
				hotmailthreadpool.execute(new FutureResult().setter(callable));
			} else {
				othersthreadpool.execute(new FutureResult().setter(callable));
			}
		} catch (InterruptedException e) {
			logger.error("putPool InterruptedException" + e);
		}
	}

	public static void shutdown() {
		yahoothreadpool.shutdown();
		gmailthreadpool.shutdown();
		hotmailthreadpool.shutdown();
		othersthreadpool.shutdown();
	}
}
