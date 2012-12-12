package com.dreamail.mercury.yahooSNP;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetProvideImpl extends AbstractSNPProvide {

	public static final Logger logger = LoggerFactory
			.getLogger(GetProvideImpl.class);

	private static final String Y_COOKIE_NAME = "Y";

	private static final String T_COOKIE_NAME = "T";

	@Override
	public HttpResponse executeMethod(String uri, SNPContext context,
			HttpEntity entity) {
		HttpResponse response = null;
		DefaultHttpClient httpClient = null;
		HttpGet getMethod = null;
		try {
			getMethod = (HttpGet) getMethod(uri);
			httpClient = getClient();
			response = httpClient.execute(getMethod);
			if (httpClient.getCookieStore().getCookies().size() > 0) {
				logger.info("cookieStore is available :"
						+ httpClient.getCookieStore());
				context.setCookieStore(httpClient.getCookieStore());
				setCookie(context, httpClient.getCookieStore());
			}
		} catch (ClientProtocolException e) {
			logger.error("failed to get instance for httpclient.");
		} catch (IOException e) {
			logger.error("error when execute method for httpclient.", e);
		} finally {
			getMethod.abort();
			closeResource(httpClient);
		}
		return response;
	}

	@Override
	public HttpUriRequest getMethod(String uri) {

		return new HttpGet(uri);
	}

	/**
	 * 设定Y/T Cookie.
	 * 
	 * @param context
	 * @param cookieStroe
	 */
	private void setCookie(SNPContext context, CookieStore cookieStroe) {
		if (cookieStroe != null) {
			List<Cookie> cookieList = cookieStroe.getCookies();
			for (Cookie cookie : cookieList) {
				if (Y_COOKIE_NAME.equals(cookie.getName())) {
					context.setY_cookie(cookie.getValue());
				} else if (T_COOKIE_NAME.equals(cookie.getName())) {
					context.setT_cookie(cookie.getValue());
				}
			}
		}
	}
}
