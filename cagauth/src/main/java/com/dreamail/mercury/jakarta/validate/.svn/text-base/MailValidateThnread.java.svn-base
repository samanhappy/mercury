package com.dreamail.mercury.jakarta.validate;

import java.util.Date;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.jakarta.web.AsyncContextManager;
import com.dreamail.mercury.mail.validate.impl.MailValidator;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.util.CAGConstants;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.JMSTypes;

public class MailValidateThnread extends Thread {

	private String uid;

	private String username;

	private String password;

	private String inpath;

	private String outpath;

	private String token;
	
	private String ptid;

	private CAGParserObject CAGObject;

	private boolean newmailbox_flag;

	private static Logger logger = LoggerFactory
			.getLogger(MailValidateThnread.class);

	public MailValidateThnread(String uid, String username, 
			String inpath, String outpath, CAGParserObject CAGObject,
			boolean newmailbox_flag) {
		this.uid = uid;
		this.username = username;
		this.password = CAGObject.getPassword();
		this.inpath = inpath;
		this.outpath = outpath;
		this.CAGObject = CAGObject;
		this.newmailbox_flag = newmailbox_flag;
		this.token = CAGObject.getToken();
		this.ptid = CAGObject.getPtid();
	}

	public Clickoo_mail_account getRegisterAccount(String token, String password, String ptid) {
		Clickoo_mail_account account = new Clickoo_mail_account();
		account.setName(username);
		account.setUuid(uid);
		account.setInPath(inpath);
		account.setOutPath(outpath);

		JSONObject json = new JSONObject();
		json.put(Constant.LOGINID, username);
		if(token != null) {
			if (ptid != null) {
				// 此处使用base64加密
				json.put(Constant.TOKEN, token);
				json.put(Constant.PTID, ptid);
			} else {
				
			}
		} else {
			// 此处使用base64加密
			json.put(Constant.PWD, EmailUtils.changeByteToBase64(password
					.getBytes()));
		}
		
		// 后面可能要支持别名
		json.put(Constant.ALIAS, "");

		account.setInCert(json.toString());
		account.setOutCert(json.toString());
		account.setRegistrationDate(new Date());

		// 设置账号类型
		account.updateAccountType();

		// 保存此种邮箱后缀名到配置表
		if (newmailbox_flag) {
			account.setIsnewmailbox(true);
			account
					.setSuffix(username
							.substring(username.indexOf('@') + 1));
		}
		return account;
	}
	
	@Override
	public void run() {
		
		Clickoo_mail_account account = null;
		
		if (token != null && ptid != null) {
			account = getRegisterAccount(token, null, ptid);
		} else if (MailValidator.validateAccountConnection(username, password,
				inpath, outpath, newmailbox_flag, CAGObject)) {
			account = getRegisterAccount(null, password, null);
		} else {
			CAGObject.setCode(CAGConstants.CAG_AUTHENTICATION_FAIL_CODE);
			CAGObject.setStatus("Authentication Fail");
		}
		
		if (account != null) {
			// 增加一个账号
			long aid = new AccountDao().addMailAccount(account);
			if (aid != -1) {
				CAGObject.setCode(CAGConstants.CAG_SUCCESS_CODE);
				CAGObject.setStatus("Success");

				if (newmailbox_flag) {
					// 通知各模块更新配置
					JmsSender.sendTopicMsg(Constant.CLICKOO_MAIL_SERVER,
							JMSTypes.CACHEAUPDATE_TOPIC);
					logger.info("notice other modules to update configs...");
				}
			} else {
				CAGObject.setCode(CAGConstants.CAG_SERVER_ERROR_CODE);
				CAGObject.setStatus("Server ERROR");
			}
		} 

		AsyncContextManager.getInstance().reponseCAGMessage(CAGObject);
	}
}
