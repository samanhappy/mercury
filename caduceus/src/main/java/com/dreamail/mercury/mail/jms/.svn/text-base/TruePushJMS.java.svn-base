package com.dreamail.mercury.mail.jms;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.truepush.impl.HotmailTruepush;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.util.JMSConstans;

public class TruePushJMS {
	public static final Logger logger = LoggerFactory
			.getLogger(TruePushJMS.class);

	public void onMessage(Object msg) {
		logger.info("receive message:" + msg);
		try {
			if (msg instanceof String) {
				JSONObject commonJson = JSONObject.fromObject(msg);
				String aid = commonJson.getString(JMSConstans.JMS_AID_KEY);
				Clickoo_mail_account mailAccount = new AccountService()
						.getAccountByAid(Long.parseLong(aid));
				if (mailAccount == null) {
					logger.error("mailAccount == null,can't find account from DB by aid!");
					return;
				}

				WebAccount wbAccount = mailAccount.getWebAccount4Ping();
				HotmailTruepush.getInstance().addAccount(wbAccount);
			} else {
				logger.error("msg class:" + msg.getClass());
			}
		} catch (Exception e) {
			logger.error("Error receiveMSG.", e);
		}
	}

}
