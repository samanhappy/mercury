package com.dreamail.mercury.mail.jms;

import javax.mail.MessagingException;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.service.DownloadMessageService;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.receiver.DLEMailSupport;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.WebAccountUtil;
@Deprecated
public class ReceiveMessageList {
	public static final Logger logger = LoggerFactory
			.getLogger(ReceiveMessageList.class);

	public void onMessage(Object msg) {
		if (msg instanceof String) {
			String message = (String) msg;
			logger.info("!!!!!!----------receiveMessageList[" + message
					+ "]request---------");
			// 接受收取邮件account信息通知
			if (!"".equals(message) && message.contains(",")) {
				String[] mids = message.split(",");
				if (mids != null) {
					receiveEmails(mids);
				}
			}
		}
	}

	/**
	 * 
	 * @param mids
	 */
	private void receiveEmails(String[] mids) {
		for (String mid : mids) {
			String messageId = mid;
			Clickoo_mail_account account = new AccountDao()
					.selectAccountAndUUIdByMid(Long.parseLong(messageId));
			WebAccount webAcc = new WebAccount();
			webAcc.setId((int) account.getId());
			WebAccount weAccount = WebAccountUtil.getAccountByContext(account,
					webAcc);
			Object obj = null;
			try {
				obj = new DLEMailSupport().dlMail(null,
						Constant.DOWNLOAD_EMAIL, null, weAccount, null, null,
						messageId);
			} catch (MessagingException e) {
				logger.error("download mail fail.", e);
			}
			logger.info("messageId:" + messageId + " download obj is:" + obj);
			if (obj != null) {
				// 此处把message状态改为0.
				new MessageDao().updateMessageState(new Clickoo_message(Integer
						.parseInt(mid), 0));
				new DownloadMessageService().updateDownloadMessageStatus(
						new Long[] { Long.parseLong(messageId) }, 0);
				logger.info("receiveMsgList for mid:" + mid + "success");
				JSONObject json = new JSONObject();
				json
						.put(JMSConstans.JMS_TYPE_KEY,
								JMSConstans.JMS_NEWMAIL_TYPE);
				json.put(JMSConstans.JMS_AID_KEY, account.getId());
				logger.info("sendMsg:" + json.toString());
				JmsSender.sendTopicMsg(json.toString(), "messageTopic");
			}
		}
	}

}
