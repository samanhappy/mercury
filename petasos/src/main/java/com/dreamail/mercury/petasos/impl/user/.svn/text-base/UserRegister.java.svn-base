package com.dreamail.mercury.petasos.impl.user;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebUser;
import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.impl.AbstractFunctionUser;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.util.CAGConstants;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.MethodName;
import com.dreamail.mercury.util.RoleUtil;
import com.dreamail.mercury.util.SystemMailBoxUtil;

public class UserRegister extends AbstractFunctionUser {

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(UserRegister.class);

	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		List<Object> objs = new ArrayList<Object>();
		Status status = new Status();
		Object[] objects = qwertCmd.getObjects();
		WebUser wUser = null;
		WebAccount account = null;
		status.setCode("0");
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] instanceof WebUser) {
				wUser = (WebUser) objects[i];
				// base64解码
				handleUsernamePassword(wUser);
			}
		}

		if (!validateUsernamePassword(wUser)) {
			status.setCode(ErrorCode.CODE_USERID_ERR);
			status.setMessage("User information IMEI error");
			objs.add(status);
		} else if (wUser.getDevicemodel() == null) {
			status.setCode(ErrorCode.CODE_USER_NO_PHONE);
			status.setMessage("Device parameters can not be registered without a mobile phone");
			objs.add(status);
		} else {
			objs = isAccountAdd(account, status, wUser, pushMail, qwertCmd);
		}
		Object[] objectss = objs.toArray();
		qwertCmd.setObjects(objectss);
		return qwertCmd;
	}

	@Override
	public String getMethodName() {
		return MethodName.USER_REGISTER;
	}

	public List<Object> isAccountAdd(WebAccount account, Status status,
			WebUser wUser, PushMail pushMail, QwertCmd qwertCmd) {
		List<Object> objs = new ArrayList<Object>();
		if (account != null) {
			// objs = isExistMailAccount(account, status, wUser, pushMail);
		} else {
			boolean hasUUID = pushMail.getCred() != null
					&& pushMail.getCred().getUuid() != null;
			if (hasUUID) {
				wUser.setUid(Long.valueOf(pushMail.getCred().getUuid()));
			}
			long uid = registerUser(wUser, hasUUID);
			if (uid > 0) {

				logger.info("Users registered success");
				status.setMessage("Users registered success");

				// 返回yahoo snp keys
				HashMap<String, String> meta = new HashMap<String, String>();
				meta.put("partnerId",
						PropertiesDeploy.getConfigureMap().get("partnerId"));
				meta.put("secretKey",
						PropertiesDeploy.getConfigureMap().get("secretKey"));
				qwertCmd.setMeta(meta);
				objs.add(status);
				Cred cred = new Cred();
				cred.setUuid(String.valueOf(uid));
				pushMail.setCred(cred);

				// 如果用户注册成功，那么为用户自动添加一个系统邮箱账号（暂时屏蔽）
				// addSystemMailBoxAccount(wUser, uid, objs);
			} else if (uid == -2) {
				logger.info("Users registered fail");
				status.setCode(ErrorCode.CODE_USER_EXIST);
				status.setMessage("user name is exist ");
				objs.add(status);
			} else if (uid == -3) {
				logger.info("Users registered fail");
				status.setCode(ErrorCode.CODE_USERID_ERR);
				status.setMessage("User information IMEI error");
				objs.add(status);
			}

		}
		return objs;
	}

	/**
	 * 为注册用户添加一个系统邮箱的账号.
	 * 
	 * @param webUser
	 * @param uid
	 * @param objs
	 */
	private void addSystemMailBoxAccount(WebUser webUser, long uid,
			List<Object> objs) {

		// 此处应先调用注册系统邮箱账号的接口，暂时未实现
		registerSystemMailBoxAccount(webUser.getName(), webUser.getPasswd());

		// 得到系统邮箱账号
		Clickoo_mail_account account = SystemMailBoxUtil.getMailBoxAccount(
				webUser.getName(), webUser.getPasswd());
		long aid = new AccountService().createAccount(account, uid);
		if (aid != -1) {
			// 如果数据库添加账号成功，那么将该账号返回给客户端
			WebAccount webAccount = SystemMailBoxUtil.getMailBoxWebAccount(aid,
					webUser.getName());
			objs.add(webAccount);
		}
	}

	/**
	 * 注册系统邮箱账号的接口，暂时未实现
	 */
	private void registerSystemMailBoxAccount(String username, String password) {
	}

	public long registerUser(WebUser wUser, boolean hasUUID) {
		Clickoo_user user = new Clickoo_user();
		long uid = 0;
		UserService userDao = new UserService();
		if (wUser != null) {
			if (!userDao.isEsixtUsername(wUser.getName())) {
				user.setDevicemodel(wUser.getDevicemodel());
				if (wUser.getName() != null
						&& !"".equals(wUser.getName().trim())) {
					user.setName(wUser.getName());
				} else {
					logger.error("User information name error");
					uid = -5;
					return uid;
				}
				if (wUser.getPasswd() != null
						&& !"".equals(wUser.getPasswd().trim())) {
					user.setPassword(wUser.getPasswd());
				} else {
					logger.error("User information password error");
					uid = -4;
					return uid;
				}
				if (wUser.getIMEI() != null
						&& !"".equals(wUser.getIMEI().trim())) {
					user.setIMEI(wUser.getIMEI());
					Clickoo_user users1 = new UserDao().getUsersByIMEI(wUser
							.getIMEI());
					if (users1 != null) {
						Long id = users1.getUid();
						users1.setUid(id);
						users1.setIMEI("");
						userDao.updateUser(users1);
					}
				} else {
					logger.error("User information IMEI error");
					uid = -3;
					return uid;
				}
				user.setStatus(0);
				byte[] signature = EmailUtils.base64TochangeByte(wUser
						.getSignature());
				try {
					user.setSignature(new String(signature, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					logger.error("attach name base64 error", e);
				}
				user.setRolelevel(RoleUtil
						.getRoleLevelByTitle(CAGConstants.COS_TITLE_FREE));
				SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
				user.setTimedate(formatter.format(new Date()));

				if (wUser.getTimestamp() != null
						&& !"".equals(wUser.getTimestamp())) {
					user.setTsoffset(getOffset(wUser.getTimestamp()));
				}

				try {
					if (hasUUID) {
						user.setUid(wUser.getUid());
						userDao.updateUser(user);
						return wUser.getUid();
					} else {
						uid = userDao.createUser(user);
					}
				} catch (Exception ex) {
					logger.error("User information stored error!", ex);
					uid = 0;
					return uid;
				}
			} else {
				uid = -2;
			}
		}
		return uid;
	}
}
