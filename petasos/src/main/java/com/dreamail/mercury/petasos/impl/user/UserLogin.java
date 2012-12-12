package com.dreamail.mercury.petasos.impl.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.WebUser;
import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.impl.AbstractFunctionUser;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.MethodName;

public class UserLogin extends AbstractFunctionUser {

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(UserLogin.class);

	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		WebUser wUser = null;
		Object[] objects = qwertCmd.getObjects();
		for (int j = 0; j < objects.length; j++) {
			if (objects[j] instanceof WebUser) {
				wUser = (WebUser) objects[j];
				// base64解码
				handleUsernamePassword(wUser);
				validateUserExist(wUser, qwertCmd, pushMail);
			}
		}
		return qwertCmd;
	}

	private void validateUserExist(WebUser wUser, QwertCmd qwertCmd,
			PushMail pushMail) {

		List<Status> objs = new ArrayList<Status>();
		Status status = new Status();

		if (!validateUsernamePassword(wUser)) {
			status.setCode("00012");
			status.setMessage("user password fail");
			objs.add(status);
			qwertCmd.setObjects(objs.toArray());
			return;
		}

		int res = 0;
		HashMap<String, String> meta = new HashMap<String, String>();
		UserService userService = new UserService();
		String username = wUser.getName();
		String password = wUser.getPasswd();
		String IMEI = wUser.getIMEI();// 这是用户即将登录的IMEI
		String OffIMEI = "";
		Clickoo_user user = new UserDao().getUsersByUsername(username);// 该user的IMEI可能是在别的手机上
		Clickoo_user imeiuser = null;
		if (IMEI == null || IMEI.equals("")) {
			res = -3;
		} else {
			imeiuser = new UserDao().getUsersByIMEI(IMEI);
			res = userService.validateUserSynchroinzation(username, password,
					IMEI, user);
		}
		if (res == -1) {
			status.setCode("00011");
			status.setMessage("user name is not exist");
			objs.add(status);
			qwertCmd.setObjects(objs.toArray());
			return;
		} else if (res == -2) {
			status.setCode("00012");
			status.setMessage("user password fail");
			objs.add(status);
			qwertCmd.setObjects(objs.toArray());
			return;
		} else if (res == -3) {
			status.setCode("00013");
			status.setMessage("without providea mobile phone IMEI");
			objs.add(status);
			qwertCmd.setObjects(objs.toArray());
			return;
		} else if (res == 1) {
			logger.info("user no synchronization");
			// 默认同步
			meta.put("iSsynchronization", "1");
			// meta.put("iSsynchronization", "0");
		} else if (res == 2) {
			logger.info("user changer role");
			meta.put("iSsynchronization", "2");
		} else if (res == 3) {
			logger.info("prompt the user offline");
			meta.put("iSsynchronization", "1");
			// 这里两个update应该在同一个事务里
			OffIMEI = user.getIMEI();
			user.setIMEI(IMEI);
			userService.updateUser(user);
			if (imeiuser != null) {
				imeiuser.setIMEI("");
				userService.updateUser(imeiuser);
			}
			if (user != null) {
				JSONObject json = new JSONObject();
				json.put(JMSConstans.JMS_TYPE_KEY,
						JMSConstans.JMS_USERFORCEOFF_TYPE);
				json.put(JMSConstans.JMS_UID_KEY, user.getUid());
				json.put(JMSConstans.JMS_IMEI_KEY, OffIMEI);
				JmsSender.sendTopicMsg(json.toString(), "messageTopic");
			}
		} else if (res == 4) {
			meta.put("iSsynchronization", "1");
			user.setIMEI(IMEI);
			userService.updateUser(user);
			if (imeiuser != null) {
				imeiuser.setIMEI("");
				userService.updateUser(imeiuser);
			}
		}
		if (user == null) {
			logger.error("error to get user from DB.");
			return;
		}
		// 更新用户时间偏移量
		updateTsOffset(wUser.getTimestamp(), user, IMEI);

		status.setCode("0");
		status.setMessage("user login success");
		objs.add(status);
		qwertCmd.setObjects(objs.toArray());
		//返回yahoo snp keys
		meta.put("partnerId", PropertiesDeploy.getConfigureMap().get("partnerId"));
		meta.put("secretKey", PropertiesDeploy.getConfigureMap().get("secretKey"));
		qwertCmd.setMeta(meta);
		Cred cred = new Cred();
		cred.setUuid(String.valueOf(user.getUid()));
		pushMail.setCred(cred);
	}

	@Override
	public String getMethodName() {
		return MethodName.USER_LOGIN;
	}


}
