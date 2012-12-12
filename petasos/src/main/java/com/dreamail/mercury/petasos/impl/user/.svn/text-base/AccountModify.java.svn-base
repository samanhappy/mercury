package com.dreamail.mercury.petasos.impl.user;

import java.util.HashMap;

import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.jms.MessageSender;
import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.impl.AbstractFunctionUser;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.User_role;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.MethodName;
import com.dreamail.mercury.util.WebAccountUtil;
import com.sun.mail.smtp.SMTPSendFailedException;

public class AccountModify extends AbstractFunctionUser {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(AccountModify.class);

	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		logger.info("in the Midify account=================");
		Status status = new Status();
		Cred cred = pushMail.getCred();
		HashMap<String, String> meta = qwertCmd.getMeta();
		String uid = null;
		if (cred != null) {
			uid = cred.getUuid();
			String IMEI = (String) meta.get("IMEI");
			UserService userService = new UserService();
			User_role ur = userService.getUserRoleById(Long.parseLong(uid));
			if (!validateUserRole(ur)) {
				status.setCode(ErrorCode.CODE_USER_ROLE_FAIL);
				status.setMessage("User has been Disabled");
				Object[] objectss = new Object[1];
				objectss[0] = status;
				qwertCmd.setObjects(new Status[] { (Status) objectss[0] });
				return qwertCmd;
			}
			if (!validateUserIMEI(ur, IMEI)) {
				status.setCode(ErrorCode.CODE_USER_IMEI_FAIL);
				status.setMessage("the user has been offline");
				Object[] objectss = new Object[1];
				objectss[0] = status;
				qwertCmd.setObjects(new Status[] { (Status) objectss[0] });
				return qwertCmd;
			} else {
				meta.remove("IMEI");
			}
		}
		status.setCode("0");
		status.setMessage("success");
		WebAccount account = null;
		Object[] objects = qwertCmd.getObjects();
		for (int j = 0; j < objects.length; j++) {
			long ID = 0;
			if (objects[j] instanceof WebAccount) {
				account = (WebAccount) objects[j];
				ID = account.getId();
				status = isAccountModify(account, status);
			}
			objects[j] = status;
			Object[] objs = new Object[2];
			objs[0] = objects[j];
			if (ID != 0) {
				WebAccount newAccount = new WebAccount();
				newAccount.setId(ID);
				objs[1] = newAccount;
			}
			qwertCmd.setObjects(objs);
		}
		pushMail.setCred(null);
		return qwertCmd;
	}

	private Status updataMailAccount(Clickoo_mail_account mail_account,
			WebAccount account, Status status) throws Exception {
		AccountService accountDao = new AccountService();
		boolean flag = false;
		String inCert = null;
		String outCert = null;
		String name = account.getName();
		String alias = account.getAlias();
		if (account.getToken() != null && account.getPtid() != null) {
			logger.info("yahoo snp account......");
		} else {
			account = WebAccountUtil.getAccountByContext(mail_account, account);
			try {
				flag = onlineValidateUpdataEmailAccount(account);
				if (!flag) {
					logger.info(account.getName() + " Connection time-out!");
					status.setCode(ErrorCode.CODE_EmailAccount_Validate);
					status.setMessage("E-mail account connection times out, please check the account password is wrong!");
					return status;
				}
			} catch (SMTPSendFailedException e) {
				logger.error(account.getName()
						+ "Account can not be detected by", e);
				status.setCode(ErrorCode.CODE_EmailAccount_Locked);
				status.setMessage("sended mails more than the ceiling, E-mail account locked!");
				return status;
			} catch (Exception e) {
				logger.error(account.getName()
						+ "Account can not be detected by", e);
				status.setCode(ErrorCode.CODE_EmailAccount_Validate);
				status.setMessage("E-mail account connection times out, please check the account password is wrong!");
				return status;
			}
		}

		JSONObject inobj = new JSONObject();
		JSONObject outobj = new JSONObject();
		inobj.put(Constant.LOGINID, account.getName());
		outobj.put(Constant.LOGINID, account.getName());
		inobj.put(Constant.ALIAS, alias);
		outobj.put(Constant.ALIAS, alias);
		if (account.getPassword() != null) {
			inobj.put(Constant.PWD, EmailUtils.changeByteToBase64(account
					.getPassword().getBytes()));
			inobj.put(Constant.PWD, EmailUtils.changeByteToBase64(account
					.getPassword().getBytes()));
		}
		if (account.getToken() != null) {
			inobj.put(Constant.TOKEN, account.getToken());
			outobj.put(Constant.TOKEN, account.getToken());
		}
		if (account.getPtid() != null) {
			inobj.put(Constant.PTID, account.getPtid());
			outobj.put(Constant.PTID, account.getPtid());
		}
		inCert = inobj.toString();
		outCert = outobj.toString();
		mail_account.setName(name);
		mail_account.setInCert(inCert);
		mail_account.setOutCert(outCert);
		mail_account.setStatus(0);
		if (flag) {
			flag = accountDao.updateAccount(mail_account);
		}
		if (!flag) {
			logger.error("Midify" + account.getName() + "failure!");
			status.setCode(ErrorCode.CODE_EmailAccount_Modify);
			status.setMessage("E-mail account has been changed failed!");
		} else {
			// 通知各模块修改消息
			MessageSender.sendModifylineMessage(mail_account);
		}
		return status;
	}

	@Override
	public String getMethodName() {
		return MethodName.ACCOUNT_MODIFY;
	}

	public Status isAccountModify(WebAccount account, Status status) {
		Clickoo_mail_account mail_account = null;
		if (account != null) {
			AccountService accountDao = new AccountService();
			mail_account = accountDao.getAccountByAid(account.getId());
			if (mail_account != null) {
				logger.info("in the Midify account Name:" + account.getName());
				try {
					status = updataMailAccount(mail_account, account, status);

				} catch (Exception e) {
					logger.error("account Midify failure!", e);
					status.setCode(ErrorCode.CODE_EmailAccount_Modify);
					status.setMessage("E-mail account has been changed failed!");
				}
				if (status.getCode().equals("0")) {
					status.setMessage("E-mail account has been changed successfully!");
				}
			} else {
				logger.error("Without the mailbox account number name!");
				status.setCode(ErrorCode.CODE_EmailAccount_NoObj);
				status.setMessage("Without this mail account name!");
			}
		} else {
			logger.error("No need to modify the information !");
			status.setCode(ErrorCode.CODE_EmailAccount_NoObj);
			status.setMessage("There is no need to amend the information provided!");
		}
		return status;
	}

}
