package com.dreamail.mercury.yahooSNP;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;


public interface SNPProvide {
	/**
	 * 获取HttpClient实例
	 * 
	 * @return
	 */
	public DefaultHttpClient getClient();

	/**
	 * 获取get/post 请求方式
	 * 
	 * @param uri
	 * @return
	 */
	public HttpUriRequest getMethod(String uri);

	/**
	 * 执行http请求
	 * 
	 * @param requestMethod
	 * @param httpContext
	 * @return
	 */
	public HttpResponse executeMethod(String uri, SNPContext context,
			HttpEntity entity);

	/**
	 * 获得响应实体的内容
	 * 
	 * @return
	 */
	public String getResponseContent(HttpEntity httpEntity);

	/**
	 * 关闭httpClient
	 * 
	 * @param httpClient
	 */
	public void closeResource(DefaultHttpClient httpClient);

	/**
	 * 构造请求所需的BasicHttpContext实例
	 * 
	 * @param cookieStore
	 * @return
	 */
	public BasicHttpContext getLocalContext(CookieStore cookieStore);
}
