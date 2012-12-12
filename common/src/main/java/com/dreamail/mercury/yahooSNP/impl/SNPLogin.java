package com.dreamail.mercury.yahooSNP.impl;

import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.yahooSNP.GetProvideImpl;
import com.dreamail.mercury.yahooSNP.SNPContext;
import com.dreamail.mercury.yahooSNP.SNPUtil;
import com.dreamail.mercury.yahooSNP.YahooArithmetic;


public class SNPLogin {
	
	private static Logger logger = LoggerFactory.getLogger(SNPLogin.class);

	/**
	 * 创建URL.
	 * 
	 * @return
	 */
	public String createUrl(SNPContext context, String timestamp) {
		return "https://" + PropertiesDeploy.getConfigureMap().get("mailServer")
				+ PropertiesDeploy.getConfigureMap().get("configPath")
				+ "/pwtoken_login?src="
				+ PropertiesDeploy.getConfigureMap().get("partnerId") + "&ts="
				+ timestamp + "&token=" + context.getToken() + "&sig="
				+ createHmac(context, timestamp);
	}

	public boolean executeLogin(SNPContext context, String timestamp) {
		HttpResponse response = new GetProvideImpl().executeMethod(createUrl(
				context, timestamp), context, null);
		String str = new GetProvideImpl().getResponseContent(response
				.getEntity());
		logger.info("pwt_login str:" + str);
		if (str.split("\r\n").length < 2) {
			logger.error("error to get wssid when pwt_login for user["
					+ context.getAid() + "," + context.getLoginId() + "]");
			return false;
		}
		
		context.setWssid(str.split("\r\n")[1].replace("crumb=", ""));
		logger.info("wssid:" + str.split("\r\n")[1]);
		return true;
	}

	public String createHmac(SNPContext context, String timestamp) {
		String loginStr = PropertiesDeploy.getConfigureMap().get("partnerId")
				+ timestamp + context.getToken()
				+ PropertiesDeploy.getConfigureMap().get("secretKey");
		String hmac = "";
		try {
			hmac = YahooArithmetic.yahoo64(YahooArithmetic.md5(loginStr));
		} catch (NoSuchAlgorithmException e) {
			logger
					.error("error to parse string when get hmac for pwt_token",
							e);
		}
		return hmac;
	}
	
	/**
	 * 获取带Cookie的Context.
	 * 
	 * @param token
	 * @return
	 */
	public SNPContext getLoginContext(String token) {
		SNPContext context = new SNPContext();
		context.setToken(token);
		executeLogin(context, SNPUtil.getTimestamp());
		return context;
	}
	
	/**
	 * 封装接收邮件登录请求中完成的cookie.
	 * @param context
	 * @return String
	 */
	public String getReceiveCookie(SNPContext context){
		StringBuffer cookie = new StringBuffer("Y=");
		cookie.append(context.getY_cookie());
		cookie.append(" ");
		cookie.append("T=");
		cookie.append(context.getT_cookie());
		cookie.append(" ");
		cookie.append("ts=");
		cookie.append(SNPUtil.getTimestamp());
		cookie.append(" ");
		cookie.append("src=");
		cookie.append(PropertiesDeploy.getConfigureMap().get("partnerId"));
		cookie.append(" ");
		cookie.append("cid=");
		return EmailUtils.changeByteToBase64(cookie.toString().getBytes());
	}
}
