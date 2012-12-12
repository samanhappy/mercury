package com.dreamail.mercury.yahooSNP;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSNPProvide implements SNPProvide {
	public static final Logger logger = LoggerFactory
			.getLogger(AbstractSNPProvide.class);

	/**
	 * 获取HttpClient实体
	 */
	public DefaultHttpClient getClient() {
		return new DefaultHttpClient();
	}

	/**
	 * 获得响应实体的内容
	 * 
	 * @return
	 */
	public String getResponseContent(HttpEntity responseEntity) {
		try {
			return EntityUtils.toString(responseEntity);
		} catch (ParseException e) {
			logger.error("getMessage err....", e);
		} catch (IOException e) {
			logger.error("getMessage err....", e);
		}
		return null;
	}

	/**
	 * 构造请求所需的BasicHttpContext实例
	 * 
	 * @param cookieStore
	 * @return
	 */
	@Override
	public BasicHttpContext getLocalContext(CookieStore cookieStore) {
		BasicHttpContext localContext = new BasicHttpContext();
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		return localContext;
	}

	/**
	 * 关闭httpClient
	 * 
	 * @param httpClient
	 */
	public void closeResource(DefaultHttpClient httpClient) {
		httpClient.getConnectionManager().shutdown();
	}
}
