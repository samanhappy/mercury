package com.dreamail.mercury.jakarta.handle;

import java.util.ArrayList;
import java.util.List;

import com.dreamail.mercury.cag.AccountSettingsObject;
import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.cag.CAGSettingObject;
import com.dreamail.mercury.cag.COSSettingsObject;
import com.dreamail.mercury.cag.COSSettingsParser;
import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.dao.RoleDao;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_role;
import com.dreamail.mercury.util.CAGConstants;
import com.dreamail.mercury.util.RoleUtil;

public class GetServerConfigNoticeHandler extends INotificationHandler {

	protected GetServerConfigNoticeHandler(CAGParserObject CAGObject) {
		super(CAGObject);
	}

	@Override
	public boolean handle() {
		logger.info("handle for GetServerConfig notice ...");
		if (CAGObject.getCoslevel() != null && CAGObject.getUuid() == null) {
			// 验证角色名
			if (!validateRoleTitle(CAGObject.getCoslevel())) {
				return true;
			}
			// 获取COS等级配置
			Clickoo_role role = new RoleDao().selectRoleByTitle(CAGObject
					.getCoslevel());
			if (role != null && role.getFunction() != null) {
				CAGSettingObject CAGSettings = new CAGSettingObject();
				CAGSettings.setCos(COSSettingsParser.json2Object(role
						.getFunction()));
				CAGObject.setSettings(CAGSettings);
				CAGObject.setCode(CAGConstants.CAG_SUCCESS_CODE);
				CAGObject.setStatus("Success");
			}
		} else if (CAGObject.getCoslevel() == null
				&& CAGObject.getUuid() != null) {
			// 判断uid是否合法
			if (!validateUid()) {
				return true;
			}
			// 获取用户等级COS配置
			int rolelevel = new UserDao().getRolelevelByUid(Long.valueOf(uid));
			String roletitle = RoleUtil.getRoleTitleByLevel(rolelevel);
			COSSettingsObject COSSettings = new COSSettingsObject();
			COSSettings.setLevel(roletitle);

			// 获取用户账号配置
			List<AccountSettingsObject> accountsSetting = null;
			List<Clickoo_mail_account> accounts = new AccountDao()
					.getAccountConfigByUid(uid);
			if (accounts != null && accounts.size() > 0) {
				accountsSetting = new ArrayList<AccountSettingsObject>();
				for (Clickoo_mail_account account : accounts) {
					AccountSettingsObject setting = new AccountSettingsObject();
					setting.setName(account.getName());
					setting.setCuki(account.getCuki());
					if (account.getValidation() == CAGConstants.ACCOUNT_VALIDATION_FAIL_STATE) {
						setting
								.setServiceActivation(CAGConstants.ACCOUNT_VALIDATION_FAIL);
					} else if (account.getValidation() == CAGConstants.ACCOUNT_VALIDATION_OK_STATE) {
						setting
								.setServiceActivation(CAGConstants.ACCOUNT_VALIDATION_OK);
					} else {
						logger.error("account validation is error");
						setting
								.setServiceActivation(CAGConstants.ACCOUNT_VALIDATION_FAIL);
					}
					accountsSetting.add(setting);
				}
			}

			CAGSettingObject CAGSettings = new CAGSettingObject();
			CAGSettings.setCos(COSSettings);
			CAGSettings.setAccounts(accountsSetting);

			CAGObject.setSettings(CAGSettings);
			CAGObject.setCode(CAGConstants.CAG_SUCCESS_CODE);
			CAGObject.setStatus("Success");
		} else {
			CAGObject.setCode(CAGConstants.CAG_BAD_REQUEST_CODE);
			CAGObject.setStatus("Bad Request");
		}
		return true;
	}

}
