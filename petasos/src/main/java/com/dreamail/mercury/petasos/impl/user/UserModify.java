package com.dreamail.mercury.petasos.impl.user;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.jms.MessageSender;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.WebUser;
import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.impl.AbstractFunctionUser;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.pojo.User_role;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.MethodName;

public class UserModify extends AbstractFunctionUser {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(UserModify.class);

	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		Status status = new Status();
		HashMap<String, String> meta = qwertCmd.getMeta();
		status.setCode("0");
		status.setMessage("success");
		boolean flag = false;
		WebUser wUser = null;
		Clickoo_user user = null;
		Object[] objects = qwertCmd.getObjects();
		Cred cred = pushMail.getCred();
		String uid = null;
		if (cred != null) {
			uid = cred.getUuid();
		}
		flag = validateUser(uid);
		if (!flag) {
			status.setCode(ErrorCode.CODE_TransferAccount_NoUUID);
			status.setMessage("This user does not exist!");
			objects[0] = status;
			qwertCmd.setObjects(new Status[] { (Status) objects[0] });
			return qwertCmd;
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
		for (int j = 0; j < objects.length; j++) {
			if (objects[j] instanceof WebUser) {
				wUser = (WebUser) objects[j];
				// base64解码
				handleUsernamePassword(wUser);
				if (!validateUsernamePassword(wUser)) {
					logger.error("modify user message Failed password pattern is error!");
					status.setCode(ErrorCode.CODE_EmailAccount_UpdateUsr);
					status.setMessage("modify user message Failed password pattern is error");
				} else {
					user = userService.getUserById(Long.valueOf(uid));
					if (wUser.getPasswd() != null) {
						user.setPassword(wUser.getPasswd());
					}
					user.setStatus(0);
					if (wUser.getSignature() != null) {
						byte[] signature = EmailUtils.base64TochangeByte(wUser
								.getSignature());
						try {
							user.setSignature(new String(signature, "UTF-8"));
						} catch (UnsupportedEncodingException e) {
							logger.error("attach name base64 error", e);
						}
					}

					flag = updataUser(user);
					if (flag) {
						logger.info("modify user message Success!");
						status.setCode("0");
						status.setMessage("Modify user information successfully");
					} else {
						logger.error("modify user message Failed !");
						status.setCode(ErrorCode.CODE_EmailAccount_UpdateUsr);
						status.setMessage("Modify user information error");
					}
				}
			}
			objects[j] = status;
			qwertCmd.setObjects(new Status[] { (Status) objects[j] });
		}
		return qwertCmd;
	}

	private boolean updataUser(Clickoo_user userpojo) {
		UserService userDao = new UserService();
		boolean flag = false;
		flag = userDao.updateUser(userpojo);
		if(userpojo.getDevicemodel()!=null)
			//通知设备变更
			MessageSender.sendModifyDeviceMessage(userpojo);
		return flag;
	}

	@Override
	public String getMethodName() {
		return MethodName.USER_MODIFY;
	}

}
