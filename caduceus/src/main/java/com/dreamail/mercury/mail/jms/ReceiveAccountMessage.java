package com.dreamail.mercury.mail.jms;

import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.CacheAccountMessage;
import com.dreamail.mercury.cache.RoleCacheManager;
import com.dreamail.mercury.dal.dao.UARelationDao;
import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.mail.truepush.impl.HotmailTruepush;
import com.dreamail.mercury.mail.util.EmailConstant;
import com.dreamail.mercury.mail.util.EmailReceiveUtil;
import com.dreamail.mercury.pojo.Clickoo_role;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.JMSConstans;

public class ReceiveAccountMessage {
	public static final Logger logger = LoggerFactory
			.getLogger(ReceiveAccountMessage.class);

	public void onMessage(Object msg) {
		try {
			logger.info("receive:" + msg);
			if (msg instanceof String) {
				String message = (String) msg;
				if (message.contains(JMSConstans.JMS_MODIFYLINE_VALUE)) {
					JSONObject accountJson = JSONObject.fromObject(message);
					EmailReceiveUtil.updateContext(accountJson);
					String password = new String(
							EmailUtils.base64TochangeByte(accountJson
									.getString(JMSConstans.JMS_PASSWORD_KEY)));
					Long aid = Long.parseLong(accountJson
							.getString(JMSConstans.JMS_AID_KEY));
					HotmailTruepush.getInstance().updateAccount(aid, password);
				} else if (message.contains(JMSConstans.JMS_UID_KEY)) {
					JSONObject accountJson = JSONObject.fromObject(message);
					String uid = accountJson.getString(JMSConstans.JMS_UID_KEY);
					if (message.contains(JMSConstans.JMS_USER_DEVICE_MODIFY)) {
						// 查询用户表devicemode
						EmailReceiveUtil.updateContext(
								JMSConstans.JMS_USER_DEVICE_MODIFY, uid);
					} else if (message
							.contains(JMSConstans.JMS_USER_ADD_FILTER)
							|| message
									.contains(JMSConstans.JMS_USER_SET_FILTER)
							|| message
									.contains(JMSConstans.JMS_USER_DELETE_FILTER)) {
						// 查询黑白名单
						EmailReceiveUtil.updateContext(message, uid);
					}
				} else if (message.contains(Constant.ROLE_CHANGE)) {
					// 用户角色改变 roleChange:1
					// 根据uid查询出aid 发送topic信息
					String userID = message.split(":")[1];
					roleChange(userID, false);
				} else if (message.contains(Constant.ROLE_FUNCATION)) {
					// RoleFuncation,title,retrievalEmailInterval
					new RoleCacheManager().init();
					logger.info("---role cache update------");
					if (message.contains(Constant.RETRIEVAL_EMAIL_INTERVAL)) {
						String roleTitle = message.split(",")[1];
						UserService userService = new UserService();
						List<Clickoo_user> userList = userService
								.getUserByTitle(roleTitle);
						for (Clickoo_user user : userList) {
							roleChange(String.valueOf(user.getUid()), true);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error receiveMSG.", e);
		}
	}

	public static void roleChange(String userID, boolean flag) {
		StringBuffer aids = new StringBuffer();
		UARelationDao dao = new UARelationDao();
		AccountService accountService = new AccountService();
		List<String> list = dao.selectAllTaskAid(Long.parseLong(userID));
		for (String aid : list) {
			// 如果是多用户使用同一账号 则按照高级别来设置
			// 有默认值就不发送了
			if (flag) {
				int rolelevel = accountService.getMaxRoleLevelRoleByAid(Long
						.valueOf(aid));
				Clickoo_role clickooRole = RoleCacheManager
						.getRoleByLevel(rolelevel);
				JSONObject json = JSONObject.fromObject(clickooRole
						.getObjfunction());
				if (json.get(Constant.RETRIEVAL_EMAIL_INTERVAL) != null) {
					aids.append(aid).append(",");
				}
			} else {
				aids.append(aid).append(",");
			}
		}
		// 根据aids更新contextMap的角色
	}

}
