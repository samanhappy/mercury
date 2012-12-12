package com.dreamail.mercury.mail.truepush.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EDU.oswego.cs.dl.util.concurrent.Callable;

import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.mail.receiver.IProvide;
import com.dreamail.mercury.mail.truepush.impl.mail.ActiveSyncProvideImpl;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.util.AccountConver2ContextHelper;

public class ReceiveActiveSyncTaskCallable implements Callable {
	public static final Logger logger = LoggerFactory
			.getLogger(ReceiveActiveSyncTaskCallable.class);
	private Object msg;

	public ReceiveActiveSyncTaskCallable(Object msg) {
		this.msg = msg;
	}

	@Override
	public Object call() throws Exception {
		String aid = (String)msg;
		Context context = getContext(aid);
		IProvide iProvide = new ActiveSyncProvideImpl();
		if(context != null)
			iProvide.receiveMail(context);
		return null;
	}
	
	/**
	 * 查询账号信息，封装成Context.
	 * @param aid
	 * @return
	 */
	protected Context getContext(String aid){
		Context context = null;
		AccountService accountService = new AccountService();
		Clickoo_mail_account account = accountService
				.getAccountToCacheByAidForC(Long.parseLong(aid));
		if (account == null || account.getName() == null) {
			logger.info("-----thread is no cache data find !!!-----");
			return null;
		}
		context = AccountConver2ContextHelper
				.changeAccount2Context(account);
		context.setSyncKey(account.getMaxuuid());
		return context;
	}

}
