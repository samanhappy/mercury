package com.clickoo.clickooImap.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.clickoo.clickooImap.domain.IdleMessage;
import com.clickoo.clickooImap.jms.IdleMessageSender;
import com.clickoo.clickooImap.utils.CIConstants;
import com.clickoo.mercury.dal.dao.AccountDao;
import com.clickoo.mercury.pojo.Clickoo_mail_account;

/**
 * 
 * @author 001211
 * 
 */
public class ControllerAccount {
	private static final Logger logger = LoggerFactory
			.getLogger(ControllerAccount.class);

	/**
	 * 第一次启动时候初始化获取所有符合要求的账户 ********账号处理类型定义*************** public final static
	 * int ACCOUNT_COMMON_TYPE = 0; public final static int
	 * ACCOUNT_YAHOOSNP_TYPE = 1; public final static int ACCOUNT_IMAPIDLE_TYPE
	 * = 2;
	 */

	public void start() {
		List<Clickoo_mail_account> list = new AccountDao()
				.getAllValidAccountListByType(0);
		if (list != null) {
			for (Clickoo_mail_account account : list) {
				if ("gmail.com".equals(account.getName().split("@")[1])) {
					IdleMessage idleMessage = new IdleMessage();
					idleMessage.setMsg(CIConstants.NoticeType.CI_NOTICEIDLE);
					idleMessage.setAid(account.getId());
					idleMessage.setAccountName(account.getName());
					idleMessage.setAccountPwd(account.getInCert_obj().getPwd());
					idleMessage.setType(2);
					idleMessage.setFlag(1);
					IdleMessageSender.sendAccountToIdle(idleMessage);
				}
			}
		}
		int size = 0;
		if (list != null) {
			size = list.size();
		}
		logger.info("there are [" + size + "] account ready...............");

	}
}
