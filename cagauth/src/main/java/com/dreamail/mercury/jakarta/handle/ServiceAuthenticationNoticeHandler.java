package com.dreamail.mercury.jakarta.handle;

import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.jakarta.validate.MailValidateThnread;
import com.dreamail.mercury.util.CAGConstants;
import com.dreamail.mercury.util.Constant;

public class ServiceAuthenticationNoticeHandler extends INotificationHandler {

	// 验证邮箱名格式
	private static Pattern emailPattern = Pattern.compile("\\S+@\\S+(\\.\\S+)+");
	
	protected ServiceAuthenticationNoticeHandler(CAGParserObject CAGObject) {
		super(CAGObject);
	}

	@Override
	public boolean handle() {

		logger.info("handle for ServiceAuthentication notice ...");

		// 判断uid合法性
		if (!validateUid()) {
			return true;
		}

		// 判断username和password参数
		if (!validateMailAccount(CAGObject.getUsername(), CAGObject
				.getPassword(), CAGObject.getToken())) {
			CAGObject.setCode(CAGConstants.CAG_AUTHENTICATION_FAIL_CODE);
			CAGObject.setStatus("Authentication Fail");
			return true;
		}

		String user = CAGObject.getUuid();
		// 邮箱账号统一转化为小写格式处理
		String username = CAGObject.getUsername().toLowerCase();

		int state = -1;
		String inpath = null;
		String outpath = null;

		// 新的邮箱后缀
		boolean newmailbox_flag = false;

		if (CAGObject.getSendProtocolType() != null) {
			if (CAGObject.getSendProtocolType().equals(Constant.HTTP)
					&& validateHttpAccountSettings(CAGObject)) {
				JSONObject json = new JSONObject();
				json.put(Constant.PROTOCOLTYPE, Constant.HTTP);
				json.put(Constant.HOST, CAGObject.getReceiveHost());
				json.put(Constant.SSL, CAGObject.getSSL());

				inpath = json.toString();
				outpath = inpath;
				state = 0;
				newmailbox_flag = true;
			} else if (validateOtherAccountSettings(CAGObject)) {
				JSONObject json = new JSONObject();
				json.put(Constant.PROTOCOLTYPE, CAGObject
						.getReceiveProtocolType());
				json.put(Constant.HOST, CAGObject.getReceiveHost());
				json.put(Constant.RECEIVEPORT, CAGObject.getReceivePort());
				if (Constant.SSL_TYPE.equals(CAGObject.getSendTs())) {
					json.put(Constant.TYPE, Constant.SSL_VALUE);
				} else {
					/*
					 * 此处有疑问，默认为空还是"TLS"
					 */
					json.put(Constant.TYPE, "");
				}
				inpath = json.toString();

				json = new JSONObject();
				json
						.put(Constant.PROTOCOLTYPE, CAGObject
								.getSendProtocolType());
				json.put(Constant.HOST, CAGObject.getSendHost());
				json.put(Constant.SENDPORT, CAGObject.getSendPort());
				if (Constant.SSL_TYPE.equals(CAGObject.getSendTs())) {
					json.put(Constant.TYPE, Constant.SSL_VALUE);
				} else {
					/*
					 * 此处有疑问，默认为空还是"TLS"
					 */
					json.put(Constant.TYPE, "");
				}
				outpath = json.toString();

				state = 0;

				newmailbox_flag = true;

			} else {
				state = 4;
			}
		} else {
			// 获取账号类型的配置
			Map<String, Object> map = new AccountDao().getMailBoxConfig(user,
					username);
			state = (Integer) map.get("out_state");
			inpath = (String) map.get("out_inpath");
			outpath = (String) map.get("out_outpath");
		}

		if (state == 0) {
			// 启动邮箱验证线程
			new MailValidateThnread(uid, username, inpath, outpath,
					CAGObject, newmailbox_flag).start();
			return false;
		} else if (state == 1) {
			// 不识别
			CAGObject.setCode(CAGConstants.CAG_MAILBOX_NONRECOGNITION_CODE);
			CAGObject.setStatus("Mailbox Nonrecognition");
		} else if (state == 3) {
			// 已经注册
			CAGObject.setCode(CAGConstants.CAG_ACCOUNT_ALREADY_EXIST_CODE);
			CAGObject.setStatus("Account Already Exist");
		} else if (state == 2) {
			CAGObject.setCode(CAGConstants.CAG_USER_NOTFOUND_CODE);
			CAGObject.setStatus("User Not Found");
		} else if (state == 4) {
			CAGObject.setCode(CAGConstants.CAG_BAD_REQUEST_CODE);
			CAGObject.setStatus("Bad Request");
		} else {
			CAGObject.setCode(CAGConstants.CAG_SERVER_ERROR_CODE);
			CAGObject.setStatus("Server ERROR");
		}

		return true;
	}

	/**
	 * 验证email账号的用户名和密码.
	 * 
	 * @param username
	 * @param password
	 */
	private boolean validateMailAccount(String username, String password,
			String token) {
		if (username == null || "".equals(username.trim())) {
			return false;
		}

		if ((password == null || "".equals(password.trim()))
				&& (token == null || "".equals(token))) {
			return false;
		}
		return emailPattern.matcher(username).matches();
	}

	/**
	 * 验证HTTP类型账号属性.
	 * 
	 * @param obj
	 * @return
	 */
	private boolean validateHttpAccountSettings(CAGParserObject obj) {
		if (obj.getSendProtocolType() == null || obj.getSSL() == null) {
			return false;
		}

		if (!obj.getSSL().equals("1") && !obj.getSSL().equals("0")) {
			return false;
		}

		return true;
	}

	/**
	 * 验证其他类型账号属性.
	 * 
	 * @param obj
	 * @return
	 */
	private boolean validateOtherAccountSettings(CAGParserObject obj) {
		if (!obj.getReceiveProtocolType().equals(Constant.IMAP)
				&& !obj.getReceiveProtocolType().equals(Constant.POP3)) {
			return false;
		}

		if (!obj.getSendProtocolType().equals(Constant.SMTP)) {
			return false;
		}

		if (obj.getReceiveHost() == null || obj.getReceivePort() == null
				|| obj.getSendHost() == null || obj.getSendPort() == null) {
			return false;
		}

		return true;
	}
}
