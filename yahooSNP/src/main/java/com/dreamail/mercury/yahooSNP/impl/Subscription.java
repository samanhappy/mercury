package com.dreamail.mercury.yahooSNP.impl;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.util.YahooSNPConstants;
import com.dreamail.mercury.yahooSNP.PostProvideImpl;
import com.dreamail.mercury.yahooSNP.SNPContext;
import com.dreamail.mercury.yahooSNP.YahooArithmetic;
import com.dreamail.mercury.yahooSNP.YahooResponse;
import com.dreamail.mercury.yahooSNP.YahooXStreamParser;

public class Subscription {
	private static Logger logger = LoggerFactory.getLogger(Subscription.class);

	/**
	 * 创建请求的URL.
	 * 
	 * @return
	 */
	public String createUrl(SNPContext context, String timestamp) {
		return "http://api.snpedit.mobile.yahoo.com/snp/" + "alcatel"
				+ "/V2/subscribe?sign=" + createSignature(context, timestamp);

	}

	/**
	 * 创建请求的内容.
	 * 
	 * @return
	 */
	public String getSubMsg(SNPContext context, String timestamp) {
		MessageFactory msgFactory = new MessageFactory();
		return msgFactory.message_subscribe(context.getWssid(), PropertiesDeploy
				.getConfigureMap().get("partnerId"), timestamp, PropertiesDeploy
				.getConfigureMap().get("notifyUrl"), PropertiesDeploy
				.getConfigureMap().get("eventgroup"), context.getAid());
	}

	/**
	 * 创建请求实体.
	 * 
	 * @param subMsg
	 * @return
	 */
	public HttpEntity createEntity(SNPContext context, String timestamp) {
		return new PostProvideImpl().createRequestEntity(getSubMsg(context,
				timestamp));
	}

	/**
	 * 创建请求的签名.
	 * 
	 * @return
	 */
	public String createSignature(SNPContext context, String timestamp) {
		String signature = null;
		try {
			signature = YahooArithmetic.yahoo64(YahooArithmetic.md5(getSubMsg(
					context, timestamp)
					+ PropertiesDeploy.getConfigureMap().get("shareKey")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return signature;

	}

	/**
	 * 执行订阅请求.
	 * 
	 * @param cookieStore
	 * @return
	 */
	public boolean executeSubscribe(SNPContext context, String timestamp) {
		HttpResponse response = new PostProvideImpl().executeMethod(createUrl(
				context, timestamp), context, createEntity(context, timestamp));

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String responseStr = new PostProvideImpl()
					.getResponseContent(response.getEntity());
			logger.info(responseStr);
			YahooResponse yahooResponse = YahooXStreamParser
					.xml2YahooResponseMessage(responseStr);
			if (yahooResponse != null
					&& YahooSNPConstants.MESSAGE_OK.equals(yahooResponse
							.getSubscription().getSubscribe().getMessage())) {
				context.setSubId(Long.valueOf(yahooResponse.getSubscription()
						.getSubscribe().getSubId()));
				logger.info("success to subscrbe");
				return true;
			} else {
				if (yahooResponse != null) {
					logger.warn(yahooResponse.getSubscription().getSubscribe()
							.getMessage());
				} else {
					logger.error("response is null");
				}

			}
		}

		return false;
	}

}
