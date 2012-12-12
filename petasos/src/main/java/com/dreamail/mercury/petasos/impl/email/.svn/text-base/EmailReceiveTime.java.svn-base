package com.dreamail.mercury.petasos.impl.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.TimePush;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.impl.AbstractFunctionEmail;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_user_limittimes;
import com.dreamail.mercury.pojo.User_role;
import com.dreamail.mercury.timer.TimerManager;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.MethodName;
import com.dreamail.mercury.util.UPEConstants;

public class EmailReceiveTime extends AbstractFunctionEmail {

	private static Logger logger = LoggerFactory
			.getLogger(EmailReceiveTime.class);

	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		List<Object> objs = new ArrayList<Object>();
		HashMap<String, String> meta = qwertCmd.getMeta();
		Status status = new Status();
		Cred cred = null;
		String uid = null;
		if (pushMail.getCred() != null) {
			cred = pushMail.getCred();
		}
		if (cred != null) {
			uid = cred.getUuid();
		}
		if (uid == null) {
			logger.info("Does not provide access to e-mail users");
			status.setCode(ErrorCode.CODE_ReceiveEmail_);
			status.setMessage("Does not provide access to e-mail users");
			Object[] objectss = new Object[1];
			objectss[0] = status;
			qwertCmd.setObjects(new Status[] { (Status) objectss[0] });
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
		
		Object[] emails = qwertCmd.getObjects();
		if (emails == null) {
			userPushOn(uid);
			logger.info("user push on");
		} else {
			for (int i = 0; i < emails.length; i++) {
				if (emails[i] instanceof TimePush) {
					TimePush timePush = (TimePush) emails[i];
					if (Constant.PUSH_ON.equalsIgnoreCase(timePush
							.getScheduledPush())) {
						String stime = timePush.getStarttime();
						String etime = timePush.getEndtime();
						String days = timePush.getDay();
						userPushTime(uid, stime, etime, days);
						logger.info("account schedule push on");
					} else if (Constant.PUSH_OFF.equalsIgnoreCase(timePush
							.getScheduledPush())) {
						userSchedulePushOff(uid);
					}
				} else if (emails[i] instanceof WebAccount) {
					WebAccount account = (WebAccount) emails[i];
					if (account != null) {
						String isAccountPush = account.getIsPush();
						String aid = String.valueOf(account.getId());
						AccountService accountService = new AccountService();
						Clickoo_mail_account mail_account = accountService
								.getAccountByAid(account.getId());
						if (null == mail_account) {
							status.setCode(ErrorCode.CODE_EmailAccount_NoAct);
							status
									.setMessage("This email-account does not exist!");
							Object[] objects = new Object[1];
							objects[0] = status;
							qwertCmd
									.setObjects(new Status[] { (Status) objects[0] });
							pushMail.setCred(null);
							return qwertCmd;
						}
						if (isAccountPush.equalsIgnoreCase(Constant.PUSH_ON)) {

							if (account.getScheduledPush() == null) {
								accountPushOn(uid, aid);
								logger.info("account push on");

								/*
								 * 此处由于客户端的bug导致 涉及<ScheduledPush>标签
								 */
								accountSchedulePushOff(uid, aid);
								logger.info("account schedule push off");
							} else {
								String isAccountPushTime = account
										.getScheduledPush();
								if (isAccountPushTime
										.equalsIgnoreCase(Constant.PUSH_TIME_ON)) {
									String stime = account.getStarttime();
									String etime = account.getEndtime();
									String days = account.getDay();
									accountPushTime(uid, aid, stime, etime,
											days);
									logger.info("account schedule push on");
								} else if (isAccountPushTime
										.equalsIgnoreCase(Constant.PUSH_TIME_OFF)) {
									accountSchedulePushOff(uid, aid);
									logger.info("account schedule push off");
								}
							}
						} else if (isAccountPush
								.equalsIgnoreCase(Constant.PUSH_OFF)) {
							accountPushOff(uid, aid);
							logger.info("account schedule push off");
						}
					}
				}
			}
		}
		status.setCode("0");
		status.setMessage("emails receive time set success");
		objs.add(status);
		Object[] objects = objs.toArray();
		qwertCmd.setObjects(objects);
		return qwertCmd;
	}

	/**
	 * 帐号设置PUSH时间
	 */
	private void accountPushTime(String uid, String aid, String stime,
			String etime, String days) {
		Clickoo_user_limittimes timer = new Clickoo_user_limittimes();
		timer.setUid(Long.parseLong(uid));
		timer.setAid(Long.parseLong(aid));
		timer.setTimetype(UPEConstants.ACCOUNT_COMMON_TIMER);
		timer.setWeekdays(days);

		String[] strs = stime.split(":");
		if (strs.length == 2) {
			timer.setStarthour(Integer.parseInt(strs[0]));
			timer.setStartminute(Integer.parseInt(strs[1]));
		}

		strs = etime.split(":");
		if (strs.length == 2) {
			int endHour = Integer.parseInt(strs[0]);
			int endMinute = Integer.parseInt(strs[1]);
			if (endHour == 0 && endMinute == 0) {
				timer.setEndhour(23);
				timer.setEndminute(59);
			} else {
				timer.setEndhour(endHour);
				timer.setEndminute(endMinute);
			}
		}
		TimerManager.addTimerForAccount(timer);
	}

	/**
	 * 用户设置PUSH时间
	 */
	private void userPushTime(String uid, String stime, String etime,
			String days) {
		Clickoo_user_limittimes timer = new Clickoo_user_limittimes();
		timer.setUid(Long.parseLong(uid));
		timer.setTimetype(UPEConstants.USER_COMMON_TIMER);
		timer.setWeekdays(days);

		String[] strs = stime.split(":");
		if (strs.length == 2) {
			timer.setStarthour(Integer.parseInt(strs[0]));
			timer.setStartminute(Integer.parseInt(strs[1]));
		}

		strs = etime.split(":");
		if (strs.length == 2) {
			int endHour = Integer.parseInt(strs[0]);
			int endMinute = Integer.parseInt(strs[1]);
			if (endHour == 0 && endMinute == 0) {
				timer.setEndhour(23);
				timer.setEndminute(59);
			} else {
				timer.setEndhour(endHour);
				timer.setEndminute(endMinute);
			}
		}
		TimerManager.addTimerForUser(timer);
	}

	/**
	 * 账号取消SCHEDULE PUSH
	 */
	private void accountSchedulePushOff(String uid, String aid) {
		TimerManager.schedulePushOffForAccount(uid, aid);
	}

	/**
	 * 用户取消SCHEDULE PUSH
	 */
	private void userSchedulePushOff(String uid) {
		TimerManager.schedulePushOffForUser(uid);
	}

	/**
	 * 帐号控制PUSH开
	 */
	private void accountPushOn(String uid, String aid) {
		TimerManager.addAccountOnTimer(uid, aid);
	}

	/**
	 * 帐号控制PUSH关
	 */
	private void accountPushOff(String uid, String aid) {
		TimerManager.addAccountOffTimer(uid, aid);
	}

	/**
	 * 用户控制PUSH开
	 */
	private void userPushOn(String uid) {
		TimerManager.addUserOnTimer(uid);
	}

	/**
	 * 用户控制PUSH关
	 */
	public void userPushOff(String uid) {
		TimerManager.addUserOffTimer(uid);
	}

	@Override
	public String getMethodName() {
		return MethodName.EMAIL_RECEIVETIME;
	}
}
