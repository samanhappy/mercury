package com.dreamail.mercury.yahooSNP.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.yahooSNP.SNPContext;
import com.dreamail.mercury.yahooSNP.SNPUtil;
import com.dreamail.mercury.yahooSNP.Token;
import com.dreamail.mercury.yahooSNP.service.EmailSubscribeService;

public class EmailSubscribeImpl implements EmailSubscribeService {
	
	private static Logger logger = LoggerFactory.getLogger(EmailSubscribeImpl.class);
	
	public static final EmailSubscribeImpl instance = new EmailSubscribeImpl();
	
	public static EmailSubscribeImpl getInstance() {
		return instance;
	}

	@Override
	public boolean subscribe(SNPContext context) {
		
		/*
		 * 所有操作重试两次
		 */
		
		String timestamp = SNPUtil.getTimestamp();
		if (context.getToken() != null) {
			logger.info("token has been retrieved");
		} else if (!new Token().setToken(context, timestamp)) {
			if (!new Token().setToken(context, timestamp)) {
				if (!new Token().setToken(context, timestamp)) {
					return false;
				}
			}
		}
		
		if (!new SNPLogin().executeLogin(context, timestamp)) {
			if (!new SNPLogin().executeLogin(context, timestamp)) {
				if (!new SNPLogin().executeLogin(context, timestamp)) {
					return false;
				} else {
					return new Subscription().executeSubscribe(context, timestamp);
				}
			} else {
				return new Subscription().executeSubscribe(context, timestamp);
			}
		}
		return new Subscription().executeSubscribe(context, timestamp);
	}


	@Override
	public boolean unsubscribe(SNPContext context) {
		/*
		 * 所有操作重试两次
		 */
		String timestamp = SNPUtil.getTimestamp();
		UnSubscription unsub = new UnSubscription();
		if (!unsub.executeunSubscribe(context, timestamp)) {
			if (!unsub.executeunSubscribe(context, timestamp)) {
				return unsub.executeunSubscribe(context, timestamp);
			}
		}
		return true;
	}

}
