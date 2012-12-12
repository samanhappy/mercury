package com.dreamail.mercury.petasos.impl.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.mercury.cache.ReceiveServerCacheManager;
import com.dreamail.mercury.cache.SendServerCacheManager;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.impl.AbstractFunctionUser;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_mail_receive_server;
import com.dreamail.mercury.pojo.Clickoo_mail_send_server;
import com.dreamail.mercury.pojo.User_role;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.MethodName;
import com.sun.mail.smtp.SMTPSendFailedException;

public class AccountAdd extends AbstractFunctionUser {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(AccountAdd.class);

	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		logger.info("Into account the method of adding===============");
		Cred cred = pushMail.getCred();
		HashMap<String, String> meta = qwertCmd.getMeta();
		String IMEI = (String) meta.get("IMEI");
		String uid = null;
		boolean hasUid = true;
		if (cred != null && cred.getUuid() != null) {
			uid = cred.getUuid();
		} else {
			/*
			 * 如果没有用户，那么生成一个默认用户
			 */
			logger.info("default user has no uid, generate new one...");
			hasUid = false;
			uid = String.valueOf(new UserDao().createDefaultUser(IMEI));
			cred = new Cred();
			cred.setUuid(String.valueOf(uid));
			pushMail.setCred(cred);
		}
		Status status = new Status();
		/*
		 * 验证角色
		 */
		User_role ur = new UserService().getUserRoleById(Long.parseLong(uid));
		if (hasUid && !validateUserRole(ur)) {
			status.setCode(ErrorCode.CODE_USER_ROLE_FAIL);
			status.setMessage("User has been Disabled");
			Object[] objectss = new Object[1];
			objectss[0] = status;
			qwertCmd.setObjects(new Status[] { (Status) objectss[0] });
			return qwertCmd;
		}
		/*
		 * 验证IMEI，判断是否用户是否在线
		 */
		if (hasUid && !validateUserIMEI(ur, IMEI)) {
			status.setCode(ErrorCode.CODE_USER_IMEI_FAIL);
			status.setMessage("the user has been offline");
			Object[] objectss = new Object[1];
			objectss[0] = status;
			qwertCmd.setObjects(new Status[] { (Status) objectss[0] });
			return qwertCmd;
		} else {
			meta.remove("IMEI");
		}
		WebAccount account = createWebAccount(qwertCmd);
		List<Object> objs = new ArrayList<Object>();
		/*
		 * 验证是否有账号
		 */
		if (account == null) {
			logger.error("didn't account message");
			status.setCode(ErrorCode.CODE_EmailAccount_NoObj);
			status.setMessage("Add the account did not provide information!");
			return createQwertCmd(qwertCmd, account, status, pushMail, objs);
		} else {
			/*
			 * 验证账号名是否合法
			 */
			if (!validateAccountName(account)) {
				logger.info(account.getName()
						+ "Account name is outside the law");
				status.setCode(ErrorCode.CODE_EmailAccount_NameError);
				status.setMessage("Account name is outside the law");
				return createQwertCmd(qwertCmd, account, status, pushMail, objs);
			}
			/*
			 * 暂时交由客户端验证 验证用户账号是否为企业邮
			 */
			// if (!validateBusinessEmail(account,ur)) {
			// logger.info(account.getName() +
			// "Free user can not register business email");
			// status.setCode(ErrorCode.CODE_EmailAccount_Free_User);
			// status.setMessage("Free user can not register business email");
			// return createQwertCmd(qwertCmd, account, status, pushMail, objs);
			// }
			/*
			 * 验证用户账号个数限制
			 */
			// if (!validateUserEmailAccount(ur, Long.parseLong(uid))) {
			// logger.info(account.getName()
			// + "Email account number already meet the max");
			// status.setCode(ErrorCode.CODE_EmailAccount_Out_Range);
			// status.setMessage("Email account count out of range");
			// return createQwertCmd(qwertCmd, account, status, pushMail, objs);
			// }
			return execute(qwertCmd, account, status, pushMail, objs, uid);
		}
	}

	public QwertCmd execute(QwertCmd qwertCmd, WebAccount accountIn,
			Status status, PushMail pushMail, List<Object> objs, String uid) {
		WebAccount accountOut = new WebAccount();
		accountOut.setId(accountIn.getId());
		accountOut.setName(accountIn.getName());

		try {
			if (accountIn.getToken() != null && accountIn.getPtid() != null) {
				// yahoosnp账号不需要再验证
				logger.info("yahoo snp account....");
				String[] names = accountIn.getName().split("@");
				Clickoo_mail_account cma = new Clickoo_mail_account();
				cma.setInPath(ReceiveServerCacheManager
						.getCacheObject(names[1]).getInPath());
				cma.setOutPath(SendServerCacheManager.getCacheObject(names[1])
						.getOutPath());
				cma.loadReceiveConfig(accountIn);
				cma.loadSendConfig(accountIn);
			} else {
				try {
					long result = onlineValidateEmailAccount(accountIn,
							accountOut);
					if (result != 0) {
						logger.info(accountIn.getName()
								+ "Account Connection timed out");
						if (-1 == result) {
							status.setCode(ErrorCode.CODE_EmailAccount_Validate);
							status.setMessage("E-mail account connection times out, please check the account password is wrong!");
						} else if (1 == result) {
							status.setCode(ErrorCode.CODE_EmailAccount_NoSvrNoHost);
							status.setMessage("E-mail account is not Server configuration information!");
						}
						return createQwertCmd(qwertCmd, accountOut, status,
								pushMail, objs);
					}
				} catch (SMTPSendFailedException e) {
					logger.error(accountIn.getName()
							+ "Account can not be detected by", e);
					status.setCode(ErrorCode.CODE_EmailAccount_Locked);
					status.setMessage("sended mails more than the ceiling, E-mail account locked!");
					return createQwertCmd(qwertCmd, accountOut, status,
							pushMail, objs);
				} catch (Exception e) {
					logger.error(accountIn.getName()
							+ "Account can not be detected by", e);
					status.setCode(ErrorCode.CODE_EmailAccount_Validate);
					status.setMessage("E-mail account connection times out, please check the account password is wrong!");
					return createQwertCmd(qwertCmd, accountOut, status,
							pushMail, objs);
				}
			}
			status.setCode("0");
			status.setMessage("success");
			objs = addMailAccount(accountIn, accountOut, status,
					Long.valueOf(uid));
			return createQwertCmd(qwertCmd, pushMail, objs);
		} catch (Exception e) {
			logger.error("E-mail account to add failure!", e);
			status.setCode(ErrorCode.CODE_EmailAccount_Add);
			status.setMessage("E-mail account to add failure!");
			return createQwertCmd(qwertCmd, accountOut, status, pushMail, objs);
		}

	}

	public Status onlineValidateAccount(long result, Status status,
			WebAccount account) {
		if (-1 == result) {
			logger.info(account.getName() + "Account Connection timed out");
			status.setCode(ErrorCode.CODE_EmailAccount_Validate);
			status.setMessage("E-mail account connection times out, please check the account password is wrong!");

		} else if (1 == result) {
			logger.info(account.getName() + "Account Connection timed out");
			status.setCode(ErrorCode.CODE_EmailAccount_NoSvrNoHost);
			status.setMessage("E-mail account is not Server configuration information!");
		}
		return status;
	}

	public WebAccount createWebAccount(QwertCmd qc) {
		WebAccount account = null;
		Object[] objects = qc.getObjects();
		for (int j = 0; j < objects.length; j++) {
			if (objects[j] instanceof WebAccount) {
				account = (WebAccount) objects[j];
				break;
			}
		}
		return account;
	}

	public QwertCmd createQwertCmd(QwertCmd qc, WebAccount account,
			Status status, PushMail pushMail, List<Object> objs) {
		if (account != null) {
			WebAccount newAccount = new WebAccount();
			newAccount.setName(account.getName());
			objs.add(newAccount);
		}
		objs.add(status);
		qc.setObjects(objs.toArray());
		return qc;
	}

	public QwertCmd createQwertCmd(QwertCmd qc, PushMail pushMail,
			List<Object> objs) {
		qc.setObjects(objs.toArray());
		return qc;
	}

	@Override
	public String getMethodName() {
		return MethodName.ACCOUNT_ADD;
	}

}
