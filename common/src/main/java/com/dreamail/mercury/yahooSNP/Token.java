package com.dreamail.mercury.yahooSNP;

import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.configure.PropertiesDeploy;

public class Token {
	private static Logger logger = LoggerFactory.getLogger(Token.class);

	/**
	 */
	public boolean setToken(SNPContext context, String timestamp) {
		HttpResponse response = new GetProvideImpl().executeMethod(createUrl(
				context, timestamp), null, null);

		logger.info("token url:" + createUrl(context, timestamp));
		logger.info("emailaccoutinfo:" + context.getLoginId() + ","
				+ context.getPassword());
		if (response != null && response.getEntity() != null) {
			String content = new GetProvideImpl().getResponseContent(response
					.getEntity());
			if (content != null) {
				String[] temp = content.split("\r\n");
				if (temp.length > 2) {
					context.setToken(temp[1]);
					context.setUserId(temp[2].replace("partnerid=", ""));
					logger.info("pwt_token:" + temp[1]);
					return true;
				}
			}
		}
		logger.info("failed to retrieve token for user[" + context.getAid()
				+ "," + context.getLoginId() + "].");
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public String createUrl(SNPContext context, String timestamp) {
		return "https://"
				+ PropertiesDeploy.getConfigureMap().get("mailServer")
				+ PropertiesDeploy.getConfigureMap().get("configPath")
				+ "/pwtoken_get?src="
				+ PropertiesDeploy.getConfigureMap().get("partnerId") + "&ts="
				+ timestamp + "&login=" + context.getLoginId() + "&passwd="
				+ context.getPassword() + "&sig="
				+ createHmac(context, timestamp);

	}

	public String createHmac(SNPContext context, String timestamp) {
		String tokenStr = PropertiesDeploy.getConfigureMap().get("partnerId")
				+ timestamp + context.getLoginId() + context.getPassword()
				+ PropertiesDeploy.getConfigureMap().get("secretKey");
		String hmac = "";
		try {
			hmac = YahooArithmetic.yahoo64(YahooArithmetic.md5(tokenStr));
		} catch (NoSuchAlgorithmException e) {
			logger
					.error("error to parse string when get hmac for pwt_token",
							e);
		}
		return hmac;
	}
}
