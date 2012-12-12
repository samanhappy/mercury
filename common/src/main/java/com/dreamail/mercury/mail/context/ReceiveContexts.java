package com.dreamail.mercury.mail.context;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.util.AccountConver2ContextHelper;

public class ReceiveContexts {
	
	private static Logger logger = LoggerFactory
	.getLogger(ReceiveContexts.class);
	
	/**
	 * 获得待收取邮箱列表
	 * 
	 * @return List<Context> context集合
	 */
	public static List<Context> getPrePushMailContexts(Map<String,Long> map) {
		List<Context> contextList = new LinkedList<Context>();
		AccountService accountService = new AccountService();
		List<Clickoo_mail_account> list = accountService.getAllValidAccountList(map);
		if (list != null) {
			for (Clickoo_mail_account account:list) {
				Context context = AccountConver2ContextHelper.changeAccount2Context(account);
				if (context != null) {
					contextList.add(context);
				}
			}
		}
		logger.info("there are [" + contextList.size()
				+ "] account ready...............");
		return contextList;
	}
}
