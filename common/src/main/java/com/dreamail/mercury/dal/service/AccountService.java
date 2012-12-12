/**
 * 
 */
package com.dreamail.mercury.dal.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.CacheAccountMessage;
import com.dreamail.jms.JmsSender;
import com.dreamail.jms.MessageSender;
import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.dao.UARelationDao;
import com.dreamail.mercury.mail.validate.impl.MailValidator;
import com.dreamail.mercury.memcached.EmailCacheManager;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.Clickoo_user_account;
import com.dreamail.mercury.util.CAGConstants;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.JMSTypes;

/**
 * 账号Dao操作类.
 * 
 * @author meng.sun
 * 
 */
public class AccountService {

	private static Logger logger = LoggerFactory
			.getLogger(AccountService.class);

	/**
	 * 添加单独的新账户.
	 * 
	 * @param account
	 *            pojo instance
	 * @return true if success else false
	 */
	public long createAccount(Clickoo_mail_account account, long uid) {
		long aid = -1;
		Date registerDate = new Date();
		Clickoo_user_account uaRelation = null;
		AccountDao accountDao = new AccountDao();
		UARelationDao uaDao = new UARelationDao();
		account.updateAccountType();
		Clickoo_mail_account acc = accountDao.getAccountByName(account
				.getName());
		// 账号第一次注册.
		if (account.getName() != null && acc == null) {
			account.setStatus(Constant.NEW_REGISTER_ACCOUNT_STATUS);
			account.setRegistrationDate(registerDate);
			// 保存账号类型到数据库
			account.updateAccountType();
			aid = new AccountDao().createAccount(account);
			if (aid != -1) {
				uaRelation = new Clickoo_user_account();
				uaRelation.setStatus(Constant.PUSH_OLDMAIL_ACCOUNT_STATUS);
				uaRelation.setAid(aid);
				uaRelation.setUid(uid);
				uaRelation.setRegisterDate(registerDate);
			}
			if (uaDao.createUARelation(uaRelation) == -1) {
				return -1;
			}
			// 账号已被注册.
		} else if ((uaRelation = uaDao.selectUAByAid(acc.getId())) != null) {
			aid = acc.getId();
			long oldUid = uaRelation.getUid();
			uaRelation.setStatus(Constant.PUSH_OLDMAIL_ACCOUNT_STATUS);
			uaRelation.setUid(uid);
			uaRelation.setRegisterDate(registerDate);
			uaRelation.setValidation(0);
			uaRelation.setOfflineDate(null);
			if (new UARelationDao().updateUAByAid(uaRelation)
					&& new MessageDao().deleteMessageLimitByAid(aid)) {
				// 通知upe
				MessageSender.sendRemoveAccountMessage(String.valueOf(oldUid),
						String.valueOf(aid));
				MessageSender.sendDeleteAccount(String.valueOf(oldUid), String
						.valueOf(aid));
			}
		}
		if (aid != -1) {
			MessageSender.sendAddAccountMessage2Task(account.getType(), String
					.valueOf(aid));
		if(account.getType() == Constant.ACCOUNT_YAHOOSNP_TYPE)
			MessageSender.sendYahooAccount2SNPMessage(aid, account.getType(),
					account.getInCert(),JMSConstans.JMS_ADDLINE_VALUE);
		}
		return aid;
	}

	/**
	 * 得到所有有效的邮箱账户列表(针对任务工厂，只获得type为0的账号).
	 * 
	 * @return a list of account pojos
	 */
	public List<Clickoo_mail_account> getAllValidAccountList(
			Map<String, Long> map) {
		return new AccountDao().getAllAccount(map);
	}

	/**
	 * 根据name查询得到一个account.
	 * 
	 * @param name
	 *            account's name
	 * @return account pojo instance
	 */
	public Clickoo_mail_account getAccountByAid(long aid) {
		return new AccountDao().getAccountById(aid);
	}

	/**
	 * 根据uid和account name判断指定的account是否存在.
	 * 
	 * @param uid
	 *            user id
	 * @param name
	 *            account name
	 * @return true or false
	 */
	public boolean isExist(long uid, long aid) {
		return new UARelationDao().selectUARelation(new Clickoo_user_account(
				uid, aid));
	}

	/**
	 * 根据account的name字段删除account以及所有相关的邮件信息.
	 * 
	 * @param name
	 *            account name
	 * @return true or false
	 */
	public boolean deleteAllInfoByUidAndAid(long uid, long aid) {

		Clickoo_user_account ua = new Clickoo_user_account();
		ua.setAid(aid);
		ua.setUid(uid);
		// 删除账号的所有信息（此处不删除账号表和邮件信息表的记录）.
		Clickoo_mail_account account = new AccountDao()
				.removeAllInfoByUidAndAid(ua);

		if (account.getType() != -1) {
			account.setId(aid);
			if (account.getStatus() == 0) {
				// 发送注销消息给各模块
				MessageSender.sendDeletelineMessage(account);
			} else if (account.getStatus() == 1) {
				// 发送下线消息
				MessageSender.sendOfflineMessage(account);
			} else {
				logger.error("account status is error");
			}
		}

		// 通知UPE
		MessageSender.sendRemoveAccountMessage(String.valueOf(uid), String
				.valueOf(aid));

		return true;
	}

	/**
	 * 删除邮件缓存.
	 * 
	 * @param aid
	 * @param msgList
	 */
	public void deleteEmailCacheObjectbyAid(long aid,
			List<Clickoo_message> msgList) {
		if (msgList != null) {
			for (Clickoo_message message : msgList) {
				if (message != null) {
					String mid = String.valueOf(message.getId());
					EmailCacheManager.removeEmail(mid);
				}
			}
		}
	}

	/**
	 * 更新账号状态.
	 * 
	 * @param account
	 *            account pojo instance
	 * @return true or false
	 */
	public boolean updateAccountState(Clickoo_mail_account account,
			Date registrationDate) {
		return new AccountDao().updateAccountState(account);
	}

	/**
	 * 更新账号信息.
	 * 
	 * @param account
	 *            account pojo instance
	 * @return true or false
	 */
	public boolean updateAccount(Clickoo_mail_account account) {
		return new AccountDao().updateAccount(account);
	}

	/**
	 * 查询账户是否被该用户注册过.
	 * 
	 * @param account
	 *            name、password
	 * @return true or false
	 */
	public boolean isAccountExit(long uid, String name) {
		boolean exit = false;
		if (getAccountIdByName(uid, name) != -1) {
			exit = true;
		}
		return exit;
	}

	public long getAccountIdByName(long uid, String name) {
		return new AccountDao().getAccountIdByName(uid, name);
	}

	/**
	 * 根据uid得到所有账号的id,name,alias.
	 * 
	 * @param uid
	 * @return
	 */
	public List<Clickoo_mail_account> getAllAccountsByUid(String uid) {
		return new UARelationDao().selectAllAccountByUid(Long.parseLong(uid));
	}

	/**
	 * 根据aid查询clickoo_mail_account.
	 * 
	 * @param aid
	 * @return
	 */
	public Clickoo_mail_account getAccountToCacheByAid(long aid) {
		Clickoo_mail_account account = new AccountDao()
				.getAccountToCacheByAid(aid);
		return account;
	}

	/**
	 * 根据aid查询clickoo_mail_account 并setOldMailNum、autoCleanupPeriod.
	 * 
	 * @param aid
	 * @return
	 */
	public Clickoo_mail_account getAccountToCacheByAidForC(long aid) {
		Clickoo_mail_account account = new AccountDao()
				.getAccountToCacheByAid(aid);
		return account;
	}

	public boolean updateFailTime(Clickoo_mail_account account) {
		return new AccountDao().updateFailTime(account);
	}

	/**
	 * 得到某一stmp所有账号
	 * 
	 * @return
	 */
	public List<Clickoo_mail_account> getAccountBySTMP() {
		return new AccountDao().getAccountBySTMP();
	}

	/**
	 * 根据aid得到所属用户最高权限的角色id.
	 * 
	 * @param aid
	 * @return
	 */
	public int getMaxRoleLevelRoleByAid(long aid) {
		return new AccountDao().getMaxRoleLevelByAid(aid);
	}

	/**
	 * 添加一个新的Idle用户.
	 */
	public long addNewIdleAccount(String username, String password) {

		// IDLE暂时只支持yahoo账号
		if (!username.contains("@yahoo.")) {
			return -1;
		}

		// 获取账号类型的配置
		Map<String, Object> map = new AccountDao().getMailBoxConfig(null,
				username);
		int state = (Integer) map.get("out_state");
		if (state == 1) {
			return -1;
		}

		long aid = -1;

		String inpath = (String) map.get("out_inpath");
		String outpath = (String) map.get("out_outpath");

		if (MailValidator.validateAccountConnection(username, password, inpath,
				outpath, false, null)) {
			Clickoo_mail_account account = new Clickoo_mail_account();
			account.setName(username);
			// 针对Idle账号，用户id设为0
			account.setUuid("0");
			account.setInPath(inpath);
			account.setOutPath(outpath);

			JSONObject json = new JSONObject();
			json.put(Constant.LOGINID, username);
			json.put(Constant.PWD, password);

			account.setInCert(json.toString());
			account.setOutCert(json.toString());
			account.setRegistrationDate(new Date());

			// 设置账号类型
			account.updateAccountType();

			// 增加一个账号
			aid = new AccountDao().addMailAccount(account);
			if (aid != -1) {
				// 此处可能需要（1）完整关系表数据，（2）通知其他模块
				CacheAccountMessage accMsg = new CacheAccountMessage();
				accMsg.setState(Constant.ACCOUNT_ADD);
				accMsg.setAid(String.valueOf(aid));
				JmsSender.sendQueueMsg(accMsg, JMSTypes.TASK_QUEUE);
			}
		}
		return aid;
	}

	/**
	 * 保存异常账号.
	 */
	public void saveInvalidAid(long uid, long aid) {
		Clickoo_user_account ua = new Clickoo_user_account();
		ua.setUid(uid);
		ua.setAid(aid);
		ua.setValidation(CAGConstants.ACCOUNT_INVALID_STATE);
		new UARelationDao().updateValidationByUidAndAid(ua);
	}

	/**
	 * 得到所有异常的账号.
	 * 
	 * @param uid
	 * @return
	 */
	public List<String> handleInvalidAids(long uid) {
		Clickoo_user_account ua = new Clickoo_user_account();
		ua.setUid(uid);
		ua.setValidation(CAGConstants.ACCOUNT_INVALID_STATE);
		return new UARelationDao()
				.selectAidsAndUpdateValidationToNormalByUidAndValidation(ua);
	}
	
}
