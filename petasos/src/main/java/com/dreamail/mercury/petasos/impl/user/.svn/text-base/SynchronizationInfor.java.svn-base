package com.dreamail.mercury.petasos.impl.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.dao.RoleDao;
import com.dreamail.mercury.dal.dao.UserLimitTimesDao;
import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.TimePush;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebRoleFunction;
import com.dreamail.mercury.domain.WebUser;
import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.impl.AbstractFunctionUser;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_role;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.pojo.Clickoo_user_limittimes;
import com.dreamail.mercury.pojo.Function;
import com.dreamail.mercury.util.CAGConstants;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.MethodName;
import com.dreamail.mercury.util.UPEConstants;

public class SynchronizationInfor extends AbstractFunctionUser {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(SynchronizationInfor.class);

	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		HashMap<String, String> meta = qwertCmd.getMeta();
		Cred cred = pushMail.getCred();
		String uid = cred.getUuid();
		List<Object> objs = new ArrayList<Object>();
		UserService userService = new UserService();
		UserLimitTimesDao ulTimesDao = new UserLimitTimesDao();
		AccountService accountService = new AccountService();
		List<Clickoo_user_limittimes> timerList = null;
		String iSsynchronization = meta.get("iSsynchronization");
		int allowedAccoutNumber = 0;
		try {
			Clickoo_user user = userService.getUserById(Long.parseLong(uid));

			// 获得COS角色配置信息
			Clickoo_role role = new RoleDao().selectRoleByPriority(user
					.getRolelevel());
			WebRoleFunction roleFunction = new WebRoleFunction();
			if (role.getObjfunction() != null) {
				Function function = role.getObjfunction();
				roleFunction.setAccountNumber(function.getAccountNumber());
				// 保存账号数量
				allowedAccoutNumber = Integer.parseInt(function
						.getAccountNumber());
				roleFunction.setStorageOption(function.getStorageOption());
				roleFunction
						.setEncryptionOption(function.getEncryptionOption());
				roleFunction.setSynchronizeOption(function
						.getSynchronizeOption());

				roleFunction.setAllowAttachmentNumber(function
						.getAllowAttachmentNumber());
				roleFunction.setAutoCleanupPeriod(function
						.getAutoCleanupPeriod());
				roleFunction.setSaveOriginalAttachmentOption(function
						.getSaveOriginalAttachmentOption());
				roleFunction.setScheduledPushOption(function
						.getScheduledPushOption());
				roleFunction.setUseMailAccountOption(function
						.getUseMailAccountOption());

				objs.add(roleFunction);
			}

			// 获得请求时间戳
			if (user.getTimedate() != null) {
				meta.put("TimeDate", user.getTimedate());
			}

			// 判断客户端是同步所有信息还是同步COS信息
			if (iSsynchronization.equals("1")) {

				// 获得用户的信息
				WebUser wUser = new WebUser();
				timerList = ulTimesDao
						.getAllTimerInfoByUid(Long.parseLong(uid));
				if (isUserPushOff(timerList)) {
					wUser.setIsPushemail(Constant.PUSH_OFF);
				} else {
					wUser.setIsPushemail(Constant.PUSH_ON);
				}

				wUser.setSignature(EmailUtils.changeByteToBase64(user
						.getSignature().getBytes("UTF-8")));
				objs.add(wUser);

				// 删除用户级别定时设置
				if (timerList != null) {
					Clickoo_user_limittimes delete = null;
					for (Clickoo_user_limittimes timer : timerList) {
						if (timer.getTimetype() == UPEConstants.USER_OFF_TIMER
								|| timer.getTimetype() == UPEConstants.USER_ON_TIMER) {
							delete = timer;
							break;
						} else if (timer.getTimetype() == UPEConstants.USER_COMMON_TIMER) {
							logger.info("get schedule push data....");
							//新版定时，可能只有这部分有效
							TimePush timePush = new TimePush();
							timePush.setDay(timer.getWeekdays());
							timePush.setScheduledPush(Constant.PUSH_ON);
							timePush.setStarttime(timer.getStarthour() + ":" + timer.getStartminute());
							timePush.setEndtime(timer.getEndhour() + ":" + timer.getEndminute());
							objs.add(timePush);
						}
					}
					timerList.remove(delete);
				}

				// 获得所有账号的信息
				List<Clickoo_mail_account> mail_accounts = accountService
						.getAllAccountsByUid(uid);
				// 控制修改用户等级后的账号数量
				for (int i = 0; i < mail_accounts.size()
						&& i < allowedAccoutNumber; i++) {
					Clickoo_mail_account a = mail_accounts.get(i);

					// 过渡代码，如果账号没有没注册过，同步信息时则不包含此账号
					if (a == null || a.getOutCert_obj() == null) {
						continue;
					}

					WebAccount account = new WebAccount();
					a.loadReceiveConfig(account);
					a.loadSendConfig(account);
					account.setId(a.getId());
					account.setAlias(a.getOutCert_obj().getAlias());
					account.setName(a.getName());

					List<Clickoo_user_limittimes> accountTimers = getTimerListByAid(
							timerList, a.getId());
					if (accountTimers != null && accountTimers.size() > 0) {
						boolean accountPush = false;
						boolean schedulePush = false;
						// 获得账号的定时设置信息
						for (Clickoo_user_limittimes times : accountTimers) {
							if (times.getTimetype() == UPEConstants.ACCOUNT_ON_TIMER) {
								account.setIsPush(Constant.PUSH_ON);
								accountPush = true;
							} else if (times.getTimetype() == UPEConstants.ACCOUNT_OFF_TIMER) {
								accountPush = true;
								account.setIsPush(Constant.PUSH_OFF);
							} else if (times.getTimetype() == UPEConstants.ACCOUNT_COMMON_TIMER) {
								account.setScheduledPush(Constant.PUSH_TIME_ON);
								schedulePush = true;
								String starttime = String.valueOf(times
										.getStarthour())
										+ ":"
										+ String.valueOf(times.getStartminute());
								account.setStarttime(starttime);
								String endtime = String.valueOf(times
										.getEndhour())
										+ ":"
										+ String.valueOf(times.getEndminute());
								account.setEndtime(endtime);
								account.setDay(times.getWeekdays());
							}
						}
						if (!accountPush) {
							account.setIsPush(Constant.PUSH_ON);
						}
						if (!schedulePush) {
							account.setScheduledPush(Constant.PUSH_TIME_OFF);
						}
					} else {
						account.setIsPush(Constant.PUSH_ON);
						account.setScheduledPush(Constant.PUSH_TIME_OFF);
					}

					objs.add(account);
				}
				
			} else if (iSsynchronization.equals("2")) {
				// 更新用户状态
				user.setStatus(0);
				userService.updateUser(user);

				// 获得CAG账号启用信息
				List<Clickoo_mail_account> mail_accounts = new AccountDao()
						.getAccountConfigByUidWithoutCUKI(uid);
				for (Clickoo_mail_account a : mail_accounts) {
					WebAccount account = new WebAccount();
					account.setId(a.getId());
					account.setName(a.getName());
					if (a.getValidation() == CAGConstants.ACCOUNT_INVALID_STATE) {
						a.setValidation(CAGConstants.ACCOUNT_VALIDATION_OK_STATE);
					}
					/**
					 * 此处默认认证通过，由CAG认证流程过于复杂导致，可能会产生一些安全性问题.
					 */
					// account.setServiceActivation(a.getValidation());
					account.setServiceActivation(CAGConstants.ACCOUNT_VALIDATION_OK_STATE);
					objs.add(account);
				}
			} else {
				logger.error("iSsynchronization type is error");
			}

			Status status = new Status();
			status.setCode("0");
			status.setMessage("Synchronization information success");
			objs.add(status);
		} catch (Exception e) {
			logger.error("Synchronization information exception", e);
			Status status = new Status();
			status.setCode(ErrorCode.CODE_USER_SYNCHRONIZED_INFOR);
			status.setMessage("Synchronization information exception");
			Object[] objects = new Object[1];
			objects[0] = status;
			qwertCmd.setObjects(objects);
			return qwertCmd;
		}
		qwertCmd.setObjects(objs.toArray());
		return qwertCmd;
	}

	@Override
	public String getMethodName() {
		return MethodName.SYNCHRONIZATION_INFOR;
	}

	/**
	 * 得到账号的定时设置.
	 * 
	 * @param timerList
	 * @param aid
	 * @return
	 */
	private List<Clickoo_user_limittimes> getTimerListByAid(
			List<Clickoo_user_limittimes> timerList, long aid) {
		if (timerList != null && timerList.size() > 0) {
			List<Clickoo_user_limittimes> returnList = new ArrayList<Clickoo_user_limittimes>();
			for (Clickoo_user_limittimes timer : timerList) {
				if (timer.getAid() == aid) {
					returnList.add(timer);
				}
			}
			return returnList;
		} else {
			return null;
		}
	}

	/**
	 * 判断是否有用户级别的定时关闭.
	 * 
	 * @param timerList
	 * @return
	 */
	private boolean isUserPushOff(List<Clickoo_user_limittimes> timerList) {
		if (timerList != null && timerList.size() > 0) {
			for (Clickoo_user_limittimes timer : timerList) {
				if (timer.getTimetype() == UPEConstants.USER_OFF_TIMER) {
					return true;
				}
			}
		}
		return false;
	}

}
