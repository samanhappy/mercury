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

public class UnSubscription {

	private static Logger logger = LoggerFactory
			.getLogger(UnSubscription.class);

	/**
	 * 获取请求URL
	 * 
	 * @return
	 */
	public String createUrl(SNPContext context, String timestamp) {
		return "http://api.snpedit.mobile.yahoo.com/snp/" + "alcatel"
				+ "/V2/unsubscribe?sign=" + createSignature(context, timestamp);

	}

	/**
	 * 获取订阅消息的xml内容
	 * 
	 * @return
	 */
	public String getUnSubMsg(SNPContext context, String timestamp) {
		MessageFactory msgFactory = new MessageFactory();
		return msgFactory.message_unsubscribe(PropertiesDeploy
				.getConfigureMap().get("partnerId"), timestamp, context
				.getUserId(), context.getSubId());
	}

	/**
	 * 创建请求所需的HttpEntity对象
	 * 
	 * @param subMsg
	 * @return
	 */
	public HttpEntity createEntity(SNPContext context, String timestamp) {
		return new PostProvideImpl().createRequestEntity(getUnSubMsg(context,
				timestamp));
	}

	/**
	 * 获取签名
	 * 
	 * @return
	 */
	public String createSignature(SNPContext context, String timestamp) {
		String signature = null;
		try {
			signature = YahooArithmetic.yahoo64(YahooArithmetic
					.md5(getUnSubMsg(context, timestamp)
							+ PropertiesDeploy.getConfigureMap()
									.get("shareKey")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return signature;

	}

	/**
	 * 获取subscription request返回的内容
	 * 
	 * @param cookieStore
	 * @return
	 */
	public boolean executeunSubscribe(SNPContext context, String timestamp) {
		HttpResponse response = new PostProvideImpl().executeMethod(
				createUrl(context, timestamp), context,
				createEntity(context, timestamp));
		if (response != null
				&& response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String responseStr = new PostProvideImpl()
					.getResponseContent(response.getEntity());
			YahooResponse yahooResponse = YahooXStreamParser
					.xml2YahooResponseMessage(responseStr);
			if (yahooResponse != null
					&& YahooSNPConstants.MESSAGE_OK.equals(yahooResponse
							.getSubscription().getUnsubscribe().getMessage())) {
				logger.info("success to unsubscrbe");
				return true;
			} else {
				if (yahooResponse != null) {
					logger.error(yahooResponse.getSubscription()
							.getUnsubscribe().getMessage());
				} else {
					logger.error("response is null");
				}
			}
		}

		return false;
	}

}
