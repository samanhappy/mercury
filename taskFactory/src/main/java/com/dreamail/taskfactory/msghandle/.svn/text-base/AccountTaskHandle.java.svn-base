package com.dreamail.taskfactory.msghandle;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.config.TaskProperties;
import com.dreamail.data.AccountMessage;
import com.dreamail.jms.CacheAccountMessage;
import com.dreamail.jms.loop.LoopReceiveHandle;
import com.dreamail.mercury.cache.ReceiveServerCacheManager;
import com.dreamail.mercury.cache.RoleCacheManager;
import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_mail_receive_server;
import com.dreamail.mercury.pojo.Clickoo_role;
import com.dreamail.mercury.util.Constant;

public class AccountTaskHandle {
	public static final Logger logger = LoggerFactory
			.getLogger(AccountTaskHandle.class);

	private static long defaultIntervalTimer = Long.valueOf(TaskProperties
			.getConfigureMap().get("defaultIntervalTimer"));

	public void handleMsg(CacheAccountMessage cacheAccountMessage) {
		String allaid = cacheAccountMessage.getAid();
		String[] aids = allaid.split(",");
		logger.info("----------" + allaid + "-----------");
		if (Constant.ACCOUNT_ADD.equals(cacheAccountMessage.getState())) {
			for (String currentaid : aids) {
				long aid = Long.parseLong(currentaid);
				AccountMessage am = getLoopTimerByAid(aid);
				if (am.getInertval() > 0) {
					StringBuffer tm = new StringBuffer(Constant.NOTICELOOP);
					tm.append(",").append(aid).append("-").append(am.getInertval()).append("-").append(cacheAccountMessage.getType());
					LoopReceiveHandle.handelMsg(tm.toString());
				} else {
					logger.info("-----------------there is no param!!!!!!!------------------");
				}
			}
		} else if (Constant.ACCOUNT_REMOVE.equals(cacheAccountMessage
				.getState())) {
			LoopReceiveHandle.handleContextMap(String.valueOf(allaid));
			logger.info("---send delete notice---[" + allaid + "]");
		}
	}
	
	/**
	 * 获得账号循环周期
	 * @return long
	 */
	public static AccountMessage getLoopTimerByAid(long aid) {
		AccountService accountService = new AccountService();
		Clickoo_mail_account account = accountService.getAccountToCacheByAid(aid);
		String accountType = account.getName().split("@")[1];
		int roleLevel = accountService.getMaxRoleLevelRoleByAid(aid);
		Clickoo_mail_receive_server receiveServer = ReceiveServerCacheManager
				.getCacheObject(accountType);
		long userfulTimer = 0;
		long serverTimer = 0;
		if (receiveServer.getIntervaltime() > 0) {
			serverTimer = receiveServer.getIntervaltime();
		} 
		if(serverTimer > 0){
			Clickoo_role clickooRole = RoleCacheManager.getRoleByLevel(roleLevel);
			JSONObject json = JSONObject.fromObject(clickooRole.getObjfunction());
			String funcationParam = json.get("retrievalEmailInterval").toString();
			// LOW MED ASAP
			if ("LOW".equals(funcationParam)) {
				userfulTimer = serverTimer + defaultIntervalTimer * 2;
			} else if ("MED".equals(funcationParam)) {
				userfulTimer = serverTimer + defaultIntervalTimer;
			} else if ("ASAP".equals(funcationParam)) {
				userfulTimer = serverTimer;
			}
		}else{
			userfulTimer = defaultIntervalTimer;
		}
		logger.info("---send register notice---["+ account.getName() + "]");
		return new AccountMessage(userfulTimer, account.getType());
	}
}
