package com.dreamail.mercury.yahooSNP.service;

import com.dreamail.mercury.yahooSNP.SNPContext;

public interface EmailSubscribeService {
	/**
	 * 发送订阅请求的接口
	 * 
	 * @param context
	 */
	public boolean subscribe(SNPContext context);

	/**
	 * 取消订阅
	 * 
	 * @param context
	 */
	public boolean unsubscribe(SNPContext context);
}
