package com.dreamail.mercury.petasos.impl.user;

import java.util.HashMap;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.impl.AbstractFunctionUser;
import com.dreamail.mercury.pojo.User_role;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.MethodName;

/**
 * 删除邮箱账号/用户
 * 
 * @author haisheng.wang
 */
public class AccountRemove extends AbstractFunctionUser {

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(AccountRemove.class);

	/**
	 * TODO 删除邮箱
	 * 
	 * @param command
	 *            解析后的命令请求
	 * @param cred
	 *            用户PoJo
	 * @param QwertCmd
	 *            处理结果
	 */
	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		Object[] objects = qwertCmd.getObjects();
		Status status = new Status();
		HashMap<String, String> meta = qwertCmd.getMeta();
		Cred cred = pushMail.getCred();
		String uid = null;
		if (cred != null) {
			uid = cred.getUuid();
		}
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
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				long ID = 0;
				if (objects[i] instanceof WebAccount) {
					WebAccount account = (WebAccount) objects[i];
					ID = account.getId();
					status = handelMailAccount(account, cred);
					qwertCmd.setMethodName(MethodName.ACCOUNT_REMOVE);
					objects[i] = status;
					Object[] objs = new Object[2];
					objs[0] = objects[i];
					if (ID != 0) {
						WebAccount newAccount = new WebAccount();
						newAccount.setId(ID);
						objs[1] = newAccount;
					}
					qwertCmd.setObjects(objs);
					pushMail.setCred(null);
				} else {
					status.setCode(ErrorCode.CODE_SYSTEM_NODATA);
					status.setMessage("system no data!");
					objects[0] = status;
					qwertCmd.setObjects(new Status[] { (Status) objects[0] });
					pushMail.setCred(null);
					return qwertCmd;
				}
			}
		} else if (uid != null) {
			status = removeUserAccount(uid, status);
			qwertCmd.setMethodName(MethodName.ACCOUNT_REMOVE);
			objects = new Object[1];
			objects[0] = status;
			qwertCmd.setObjects(new Status[] { (Status) objects[0] });
			return qwertCmd;
		}
		return qwertCmd;
	}

	/**
	 * TODO 邮箱处理操作
	 * 
	 * @param obj
	 * @param cred
	 * @return status
	 */
	public Status handelMailAccount(WebAccount account, Cred cred) {
		Status status = new Status();
		if (account != null) {
			logger.info("Delete Email：" + "[" + account.getId() + "]");
			long aid = account.getId();
			status = removeMailAccount(Long.parseLong(cred.getUuid()), aid,
					status);
		}
		return status;
	}

	/**
	 * TODO 删除邮箱及其相关信息
	 * 
	 * @param name
	 *            邮箱信息
	 * @param status
	 *            操作返回信息
	 */
	private Status removeMailAccount(Long uid, long aid, Status status) {
		AccountService accountDao = new AccountService();
		accountDao.deleteAllInfoByUidAndAid(uid, aid);
		status.setCode("0");
		status.setMessage("E-mail account deleted successfully!");
		logger.info("E-mail account deleted successfully!");
		return status;
	}

	/**
	 * TODO 删除用户及其相关信息
	 * 
	 * @param uId
	 *            用户ID
	 * @param status
	 *            操作返回信息
	 */
	private Status removeUserAccount(String uId, Status status) {
		UserService userDao = new UserService();
		if (userDao.deleteAllInfoByUid(Long.parseLong(uId))) {
			status.setCode("0");
			logger.info("User successfully deleted!");
			status.setMessage("User successfully deleted!");
		} else {
			status.setCode(ErrorCode.CODE_EmailAccount_Delete);
			status.setMessage("Users Delete failed!");
			logger.info("Users Delete failed!");
		}
		return status;
	}

	@Override
	public String getMethodName() {
		return MethodName.ACCOUNT_REMOVE;
	}
}
