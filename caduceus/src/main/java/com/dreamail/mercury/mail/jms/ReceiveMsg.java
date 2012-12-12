package com.dreamail.mercury.mail.jms;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.dao.UARelationDao;
import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.dal.service.DeleteMessageService;
import com.dreamail.mercury.mail.delete.EmailDeleteControl;
import com.dreamail.mercury.pojo.Clickoo_delete_message;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_message;
@Deprecated
public class ReceiveMsg {

	public static final Logger logger = LoggerFactory
			.getLogger(ReceiveMsg.class);

	public void onMessage(Object msg) {
		if (msg instanceof String) {
			String message = (String) msg;
			logger.info("!!!!!!----------receiveMsg[" + message
					+ "]request---------");
			// 接受收取邮件account信息通知
			if (!"".equals(message) && message.contains(",")) {
				String[] mids = message.split(",");
				DeleteEmails(mids);
			}
		}
	}

	private void DeleteEmails(String[] mids) {
		logger.info("-------------into DeleteEmails fuction!");
		AccountService accountService = new AccountService();
		List<String> aids = new ArrayList<String>();
		List<Clickoo_message> messages = null;
		List<Long> reveiceids = new ArrayList<Long>();
		List<Long> sendids = new ArrayList<Long>();
		List<Long> dmids = new ArrayList<Long>();
		MessageDao messageDao = new MessageDao();
		EmailDeleteControl emailDelet = new EmailDeleteControl();
		if (mids != null) {
			messages = messageDao.getMessagesByIds(mids);
			Iterator<Clickoo_message> it = messages.iterator();
			while (it.hasNext()) {
				Clickoo_message message = (Clickoo_message) it.next();
				if (message.getUuid() != null) {
					reveiceids.add(message.getId());
					if (!aids.contains(message.getAid())) {
						aids.add(message.getAid());
					}
				} else {
					sendids.add(message.getId());
					it.remove();
				}
			}
			for (int j = 0; j < aids.size(); j++) {
				Clickoo_mail_account mail_account = null;
				DeleteMessageService deleteMessageDao = new DeleteMessageService();
				List<String> uids = new UARelationDao().selectAllUid(Long
						.parseLong(aids.get(j)));
				if (uids.size() == 1) {
					List<String> uuids = new ArrayList<String>();
					mail_account = accountService.getAccountByAid(Long
							.parseLong(aids.get(j)));
					if (messages != null) {
						for (int k = 0; k < messages.size(); k++) {
							Clickoo_delete_message dm = new Clickoo_delete_message();
							dm.setAccname(mail_account.getName());
							dm.setMid(String.valueOf(messages.get(k).getId()));
							dm.setUuid(messages.get(k).getUuid());
							dm.setIntime(new Date());
							Long dmid = deleteMessageDao.saveDeleteMessage(dm);
							dmids.add(dmid);
							if (aids.get(j).contains(messages.get(k).getAid())) {
								uuids.add(messages.get(k).getUuid());
							}
						}
					}
					if (!emailDelet.dEmail(mail_account, uuids)) {
						if (sendids.size() != 0) {
							Long[] sids = new Long[sendids.size()];
							for (int n = 0; n < sendids.size(); n++) {
								sids[n] = sendids.get(n);
							}
							if (messageDao.deleteMessagesByIds(sids)) {
								logger
										.info("receviceemail delete fail,sendemail delete success");
							} else {
								logger.info("email delete fail");
							}
						}
					} else {
						Long[] dids = new Long[dmids.size()];
						for (int x = 0; x < dmids.size(); x++) {
							dids[x] = dmids.get(x);
						}

						if (deleteMessageDao.deleteDMessageByDids(dids)) {
							logger.info("deletMessages delete success");
							Long[] ids = new Long[mids.length];
							for (int m = 0; m < mids.length; m++) {
								ids[m] = Long.parseLong(mids[m]);
							}
						} else {
							logger.info("deletMessages delete fail");
						}
					}
				}
			}
			logger.info("-------------DeleteEmails fuction end! ");
		}
	}

}
