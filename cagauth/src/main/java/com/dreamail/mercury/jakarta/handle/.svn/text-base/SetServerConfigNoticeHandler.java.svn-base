package com.dreamail.mercury.jakarta.handle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dreamail.jms.MessageSender;
import com.dreamail.mercury.cag.AccountSettingsObject;
import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.cag.COSSettingsObject;
import com.dreamail.mercury.cag.COSSettingsParser;
import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.dao.RoleDao;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.dal.service.UARelationService;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_role;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.util.CAGConstants;
import com.dreamail.mercury.util.RoleUtil;

public class SetServerConfigNoticeHandler extends INotificationHandler {

	protected SetServerConfigNoticeHandler(CAGParserObject CAGObject) {
		super(CAGObject);
	}

	@Override
	public boolean handle() {

		logger.info("handle for SetServerConfig notice ...");

		if (CAGObject.getCoslevel() != null && CAGObject.getUuid() == null
				&& CAGObject.getSettings() != null
				&& CAGObject.getSettings().getCos() != null) {

			// 验证角色名
			if (!validateRoleTitle(CAGObject.getCoslevel())) {
				return true;
			}

			// 设置COS等级配置
			RoleDao roleDao = new RoleDao();
			Clickoo_role role = roleDao.selectRoleByTitle(CAGObject
					.getCoslevel());

			if (role != null && role.getFunction() != null) {

				COSSettingsObject settings = CAGObject.getSettings().getCos();

				if (!validateCOSSettings(settings)) {
					logger.info("COS settings is error");
					CAGObject.setCode(CAGConstants.CAG_BAD_REQUEST_CODE);
					CAGObject.setStatus("Bad Request");
					return true;
				}

				boolean retrievalIntervalChanged = false;
				String oldRetrievalInterval = role.getObjfunction()
						.getRetrievalEmailInterval();
				String newRetrievalInterval = settings
						.getBackendEmailRetrievalPeriod();
				if (newRetrievalInterval != null
						&& !newRetrievalInterval
								.equalsIgnoreCase(oldRetrievalInterval)) {
					retrievalIntervalChanged = true;
				}

				// 更新角色json字符串
				role.setFunction(COSSettingsParser.objectUpdate2json(settings,
						role.getFunction()));
				if (roleDao.updateRole(role)) {
					// JMS消息通知任务工厂
					StringBuffer message = new StringBuffer("RoleFuncation,")
							.append(CAGObject.getCoslevel());
					if (retrievalIntervalChanged) {
						message.append(",retrievalEmailInterval");
					}
					MessageSender.sendJMSTopicMessage2TaskFactory(message
							.toString());

					CAGObject.setSettings(null);
					CAGObject.setCode(CAGConstants.CAG_SUCCESS_CODE);
					CAGObject.setStatus("Success");
				} else {
					CAGObject.setSettings(null);
					CAGObject.setCode(CAGConstants.CAG_SERVER_ERROR_CODE);
					CAGObject.setStatus("Server Error");
				}
			}
		} else if (CAGObject.getCoslevel() == null
				&& CAGObject.getUuid() != null
				&& CAGObject.getSettings() != null) {

			// 验证uid
			if (!validateUid()) {
				return true;
			}

			// 先验证
			if (CAGObject.getSettings().getCos() != null
					&& CAGObject.getSettings().getCos().getLevel() != null) {
				// 验证角色名
				if (!validateRoleTitle(CAGObject.getSettings().getCos()
						.getLevel())) {
					return true;
				}
			}

			Map<String, String> map = null;
			// 先验证
			if (CAGObject.getSettings().getAccounts() != null
					&& CAGObject.getSettings().getAccounts().size() > 0) {
				// 如果得不到正确的配置参数，返回Bad Request响应
				map = getConfigMap(CAGObject.getSettings().getAccounts(), uid);
				if (map == null) {
					CAGObject.setSettings(null);
					CAGObject.setCode(CAGConstants.CAG_BAD_REQUEST_CODE);
					CAGObject.setStatus("Bad Request");
					return true;
				}
			}

			/**
			 *以下两个if处理操作数据库可能会引发错误，应该使用同一个事务处理
			 */

			// 设置用户角色配置
			if (CAGObject.getSettings().getCos() != null
					&& CAGObject.getSettings().getCos().getLevel() != null) {

				String roleTitle = CAGObject.getSettings().getCos().getLevel();
				int newRoleLevel = RoleUtil.getRoleLevelByTitle(roleTitle);
				int oldRoleLevel = new UserDao().getRolelevelByUid(Long
						.valueOf(uid));
				if (newRoleLevel != oldRoleLevel) {
					Clickoo_user user = new Clickoo_user();
					user.setUid(Long.valueOf(uid));
					user.setRolelevel(newRoleLevel);
					if (!new UserDao().updateRolelevel(user)) {
						CAGObject.setSettings(null);
						CAGObject.setCode(CAGConstants.CAG_SERVER_ERROR_CODE);
						CAGObject.setStatus("Server Error");
						return true;
					} else {
						// JMS消息通知任务工厂
						StringBuffer message = new StringBuffer("RoleChange:")
								.append(uid);
						MessageSender.sendJMSTopicMessage2TaskFactory(message
								.toString());

						int disable_rolelevel = RoleUtil
								.getRoleLevelByTitle(CAGConstants.COS_TITLE_DISABLE);
						// 发送用户失效与生效消息
						if (newRoleLevel == disable_rolelevel) {
							MessageSender.sendDisableUserMessage(uid);

							new UARelationService().setUserPushOffline(Long.parseLong(uid));

						} else if (oldRoleLevel == disable_rolelevel) {
							MessageSender.sendEnableUserMessage(uid);

							new UARelationService().setUserPushOnline(Long.parseLong(uid));
						}
					}
				}
			}

			// 设置账号配置
			if (CAGObject.getSettings().getAccounts() != null
					&& CAGObject.getSettings().getAccounts().size() > 0) {
				// 设置用户账号配置
				if (map != null) {
					List<Clickoo_mail_account> accountList = new AccountDao()
							.setAccountConfig(map);
					// 通知各模块上线消息.
					MessageSender.sendRegisterMessage2OtherModules(uid,
							accountList);
				} else {
					CAGObject.setSettings(null);
					CAGObject.setCode(CAGConstants.CAG_SERVER_ERROR_CODE);
					CAGObject.setStatus("Server Error");
					return true;
				}
			}

			CAGObject.setSettings(null);
			CAGObject.setCode(CAGConstants.CAG_SUCCESS_CODE);
			CAGObject.setStatus("Success");

		} else {
			CAGObject.setSettings(null);
			CAGObject.setCode(CAGConstants.CAG_BAD_REQUEST_CODE);
			CAGObject.setStatus("Bad Request");
		}
		return true;
	}

	/**
	 * 把设置信息类封装成POJO类.
	 * 
	 * @param accountSettings
	 * @param uid
	 * @return
	 */
	private Map<String, String> getConfigMap(
			List<AccountSettingsObject> accountSettings, String uid) {
		Map<String, String> configMap = new HashMap<String, String>();
		StringBuffer names = new StringBuffer();
		StringBuffer validations = new StringBuffer();
		StringBuffer cukis = new StringBuffer();
		for (AccountSettingsObject accountSetting : accountSettings) {
			if (accountSetting.getName() == null
					|| accountSetting.getServiceActivation() == null
					|| accountSetting.getCuki() == null) {
				logger.error("account setting is error");
				return null;
			}
			// 邮箱账号统一转化为小写格式处理
			names.append(accountSetting.getName().toLowerCase()).append(",");
			cukis.append(accountSetting.getCuki()).append(",");
			if (CAGConstants.ACCOUNT_VALIDATION_OK
					.equalsIgnoreCase(accountSetting.getServiceActivation())) {
				validations.append(CAGConstants.ACCOUNT_VALIDATION_OK_STATE)
						.append(",");
			} else if (CAGConstants.ACCOUNT_VALIDATION_FAIL
					.equalsIgnoreCase(accountSetting.getServiceActivation())) {
				validations.append(CAGConstants.ACCOUNT_VALIDATION_FAIL_STATE)
						.append(",");
			} else if (CAGConstants.ACCOUNT_VALIDATION_REMOVE
					.equalsIgnoreCase(accountSetting.getServiceActivation())) {
				validations
						.append(CAGConstants.ACCOUNT_VALIDATION_REMOVE_STATE)
						.append(",");
			} else {
				logger.error("service activation is error");
				return null;
			}
		}
		configMap.put("names", names.toString());
		configMap.put("cukis", cukis.toString());
		configMap.put("validations", validations.toString());
		configMap.put("uid", uid);
		return configMap;
	}
}
