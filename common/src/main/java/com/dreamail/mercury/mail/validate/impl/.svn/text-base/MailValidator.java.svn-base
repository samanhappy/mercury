package com.dreamail.mercury.mail.validate.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.validate.IMailValidate;
import com.dreamail.mercury.pojo.InPath;
import com.dreamail.mercury.pojo.OutPath;
import com.dreamail.mercury.util.CAGConstants;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JsonUtil;
import com.dreamail.mercury.util.MailBoxDispatcher;
import com.dreamail.mercury.yahooSNP.TokenRetriever;

public class MailValidator {

	public static Logger logger = LoggerFactory.getLogger(MailValidator.class);

	/**
	 * 邮箱账号连接测试.
	 * 
	 * 此方法只做接收测试，不做发送测试
	 * 
	 * @param username
	 * @param password
	 * @param inpath
	 * @return
	 */
	public static boolean validateAccountConnection(String username,
			String password, String inpath, String outpath, boolean isNewMailBox,
			CAGParserObject CAGObject) {

		// 如果是yahoo账号，那么获取token
		if (MailBoxDispatcher.isYahooSNPSupport(username)) {
			logger.info("emailbox is yahoo user, get token...");
			String token = TokenRetriever.retriveToken(username, password);
			if (token != null) {
				if (CAGObject != null) {
					CAGObject.setToken(token);
					CAGObject.setCode(CAGConstants.CAG_SUCCESS_CODE);
					CAGObject.setStatus("Success");
				}
				return true;
			} else {
				if (CAGObject != null) {
					CAGObject
							.setCode(CAGConstants.CAG_AUTHENTICATION_FAIL_CODE);
					CAGObject.setStatus("Authentication Fail");
				}
				return false;
			}
		} else {
			return validateReceiveAndSendEmail(username, password, inpath,
					outpath, isNewMailBox);
		}
	}

	/**
	 * 验证接收和发送.
	 * 
	 * @param username
	 * @param password
	 * @param inpath
	 * @param outpath
	 * @return
	 */
	private static boolean validateReceiveAndSendEmail(String username,
			String password, String inpath, String outpath, boolean isNewMailBox) {
		logger.info(inpath);
		logger.info(outpath);
		InPath path = JsonUtil.getInstance().parserInPath(inpath);
		WebAccount account = new WebAccount();
		account.setAlias(null);
		account.setName(username);
		account.setPassword(password);
		account.setReceiveHost(path.getInhost());
		account.setReceivePort(path.getReceivePort());
		account.setCompositor(path.getCompositor());
		account.setReceiveProtocolType(path.getProtocolType());
		account.setReceiveTs(path.getReceiveTs());
		account.setSupportalluid(path.getSupportalluid());
		if (Constant.NOT_USE_SSL.equals(path.getSsl())) {
			account.setSsl(false);
		} else if (Constant.USE_SSL.equals(path.getSsl())) {
			account.setSsl(true);
		}
		OutPath outPath = JsonUtil.getInstance().parserOutPath(outpath);
		account.setSendHost(outPath.getSmtpServer());
		account.setSendPort(outPath.getSendPort());
		account.setSendProtocolType(Constant.SMTP);

		IMailValidate validator = null;
		boolean isHttpServer = false;
		if (Constant.IMAP.equals(path.getProtocolType())) {
			validator = new ImapMailValidateImpl();
		} else if (Constant.POP3.equals(path.getProtocolType())) {
			validator = new Pop3MailValidateImpl();
		} else if (Constant.HTTP.equals(path.getProtocolType())) {
			validator = new EWSMailValidateImpl();
			isHttpServer = true;
		} else {
			return false;
		}

		IMailValidate outValidator = new SendMailValidateImpl();

		try {
			if (validator.validate(account)) {
				logger.info("success to through receive mail validation");
				if (isNewMailBox && !isHttpServer) {
					return outValidator.validate(account);
				} else {
					return true;
				}
			}
		} catch (Exception e) {
			logger.info("validate account failed", e);
		}
		return false;
	}

}
