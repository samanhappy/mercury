/**
 * 
 */
package com.dreamail.mercury.dal.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.pojo.Clickoo_user_account;

/**
 * 账号Dao操作类.
 * 
 * @author meng.sun
 * 
 */
public class AccountDao {

	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory.getLogger(AccountDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.AccountMapper";

	/**
	 * 添加单独的新账户.
	 * 
	 * @param account
	 *            pojo instance
	 * @return true if success else false
	 */
	public long createAccount(Clickoo_mail_account account) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(namespace + ".insertAccount", account);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error(
					"failed to insert account for name:" + account.getName(), e);
			return -1;
		} finally {
			SessionFactory.closeSession();
		}
		return account.getId();
	}

	/**
	 * 得到所有有效的邮箱账户列表.
	 * 
	 * @return a list of account pojos
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_account> getAllValidAccountList() {
		List<Clickoo_mail_account> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = sqlSession.selectList(namespace + ".selectAllValidAccounts");
		} catch (SQLException e) {
			logger.error("failed to get all valid account list", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 根据name查询得到一个account.
	 * 
	 * @param name
	 *            account's name
	 * @return account pojo instance
	 */
	public Clickoo_mail_account getAccountByName(String name) {
		Clickoo_mail_account account = null;
		try {
			sqlSession = SessionFactory.getSession();
			account = (Clickoo_mail_account) sqlSession.selectOne(namespace
					+ ".getAccountByName", name);
		} catch (SQLException e) {
			logger.error("failed to get account by name", e);
		} finally {
			SessionFactory.closeSession();
		}
		return account;
	}

	/**
	 * 根据name查询得到一个account.
	 * 
	 * @param name
	 *            account's name
	 * @return account pojo instance
	 */
	public Clickoo_mail_account getAccountByNameIdle(String name) {
		Clickoo_mail_account account = null;
		try {
			sqlSession = SessionFactory.getSession();
			account = (Clickoo_mail_account) sqlSession.selectOne(namespace
					+ ".getAccountByName", name);
		} catch (SQLException e) {
			logger.error("failed to get account by name", e);
		} finally {
			SessionFactory.closeSession();
		}
		return account;
	}

	/**
	 * 根据id查询得到一个account.
	 * 
	 * @param name
	 *            account's name
	 * @return account pojo instance
	 */
	public Clickoo_mail_account getAccountById(long aid) {
		Clickoo_mail_account account = null;
		try {
			sqlSession = SessionFactory.getSession();
			account = (Clickoo_mail_account) sqlSession.selectOne(namespace
					+ ".getAccountById", aid);
		} catch (SQLException e) {
			logger.error("failed to get account by id", e);
		} finally {
			SessionFactory.closeSession();
		}
		return account;
	}

	/**
	 * 更新账号.
	 * 
	 * @param account
	 *            account pojo instance
	 * @return true or false
	 */
	public boolean updateAccount(Clickoo_mail_account account) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateAccount", account);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to update account", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 查询账户是否存在.
	 * 
	 * @param account
	 *            name、password
	 * @return true or false
	 */
	public boolean isAccountExit(String name, String password) {
		String in_cert = "{\"loginID\":\"" + name + "\",\"pwd\":\"" + password
				+ "\"}";
		Long id = null;
		try {
			sqlSession = SessionFactory.getSession();
			id = (Long) sqlSession.selectOne(namespace + ".isAccountExit",
					in_cert);
		} catch (SQLException e) {
			logger.error("failed to get account by uid and inCert", e);
		} finally {
			SessionFactory.closeSession();
		}
		return id != null;
	}

	/**
	 * 得到所有账号
	 * 
	 * @return list of AccountCacheObject.
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_account> getAllAccount(Map<String, Long> map) {
		List<Clickoo_mail_account> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_mail_account>) sqlSession.selectList(namespace
					+ ".getAllAccountToCache", map);
		} catch (SQLException e) {
			logger.error("failed to get account by uid and inCert", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 删除指定id对应的账号.
	 * 
	 * @param aid
	 *            account id
	 * @return true or false
	 */
	public boolean deleteAccountById(long aid) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.delete(namespace + ".deleteAccountById", aid);
			sqlSession.commit();
		} catch (SQLException e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to get account by uid and inCert", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 更新账号.
	 * 
	 * @param account
	 *            account pojo instance
	 * @return true or false
	 */
	public boolean updateAccountState(Clickoo_mail_account account) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateAccountState", account);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to updateAccountState", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 根据aid得到所属用户最高权限的角色id.
	 * 
	 * @param aid
	 * @return
	 */
	public int getMaxRoleLevelByAid(long aid) {
		int rolelevel = -1;
		try {
			sqlSession = SessionFactory.getSession();
			Object obj = sqlSession.selectOne(namespace
					+ ".getMaxRoleLevelByAid", aid);
			if (obj != null) {
				rolelevel = (Integer) obj;
			} else {
				logger.info("not find user for this account");
			}
		} catch (Exception e) {
			logger.error("failed to getMaxLevelRoleIdByAid by aid " + aid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return rolelevel;
	}

	/**
	 * 根据用户id和账号name得到账号id.
	 * 
	 * @param uid
	 * @param name
	 * @return
	 */
	public long getAccountIdByName(long uid, String name) {
		long id = -1;
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", String.valueOf(uid));
		map.put("name", name);
		try {
			sqlSession = SessionFactory.getSession();
			Object obj = sqlSession.selectOne(
					namespace + ".getAccountIdByName", map);
			if (obj != null) {
				id = (Long) obj;
			}
		} catch (Exception e) {
			logger.error("failed to getAccountIdByName", e);
			return id;
		} finally {
			SessionFactory.closeSession();
		}
		return id;
	}

	/**
	 * 更新账号临时状态.
	 * 
	 * @return
	 */
	public boolean updateTmpState() {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateTmpState");
			sqlSession.commit();
		} catch (Exception e) {
			logger.error("failed to updateTmpState", e);
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 根据账号id得到账号的信息.
	 * 
	 * @param aid
	 * @return
	 */
	public Clickoo_mail_account getAccountToCacheByAid(long aid) {
		Clickoo_mail_account accountCache = null;
		try {
			sqlSession = SessionFactory.getSession();
			accountCache = (Clickoo_mail_account) sqlSession.selectOne(
					namespace + ".getAccountToCacheByAid", aid);
		} catch (Exception e) {
			logger.error("failed to getAccountToCacheByAid " + aid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return accountCache;
	}

	/**
	 * 根据邮件id得到uuid,账号的信息以及最新注册时间.
	 * 
	 * @param aid
	 * @return
	 */
	public Clickoo_mail_account selectAccountAndUUIdByMid(long mid) {
		Clickoo_mail_account accountCache = null;
		try {
			sqlSession = SessionFactory.getSession();
			accountCache = (Clickoo_mail_account) sqlSession.selectOne(
					namespace + ".selectAccountAndUUIdByMid", mid);
		} catch (Exception e) {
			logger.error("failed to selectAccountAndUUIdByMid " + mid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return accountCache;
	}

	/**
	 * 根据aid查询uid和IMEI的list.
	 * 
	 * @param aid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_user> selectUidAndIMEIByAid(long aid) {
		List<Clickoo_user> userList = null;
		try {
			sqlSession = SessionFactory.getSession();
			userList = (List<Clickoo_user>) sqlSession.selectList(namespace
					+ ".selectUidAndIMEIByAid", aid);
		} catch (SQLException e) {
			logger.error("failed to selectUidAndIMEIByAid:" + aid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return userList;
	}

	public Clickoo_mail_account getAccountAndMessage(String aid, String mid) {
		Clickoo_mail_account account = null;
		try {
			sqlSession = SessionFactory.getSession();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("aid", aid);
			map.put("mid", mid);
			account = (Clickoo_mail_account) sqlSession.selectOne(namespace
					+ ".getMessage", map);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SessionFactory.closeSession();
		}
		return account;
	}

	public boolean updateFailTime(Clickoo_mail_account account) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateFailTime", account);
			sqlSession.commit();
		} catch (Exception e) {
			logger.error("failed to updateFailTime", e);
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 得到该用户所有账号CAG配置信息.
	 * 
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_account> getAccountConfigByUid(String uid) {
		List<Clickoo_mail_account> accounts = null;
		try {
			sqlSession = SessionFactory.getSession();
			accounts = sqlSession.selectList(namespace
					+ ".getAccountConfigByUid", uid);
		} catch (Exception e) {
			logger.error("failed to getAccountConfigByUid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return accounts;

	}

	/**
	 * 得到该用户所有账号CAG配置信息(不包含cuki).
	 * 
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_account> getAccountConfigByUidWithoutCUKI(
			String uid) {
		List<Clickoo_mail_account> accounts = null;
		try {
			sqlSession = SessionFactory.getSession();
			accounts = sqlSession.selectList(namespace
					+ ".getAccountConfigByUidWithoutCUKI", uid);
		} catch (Exception e) {
			logger.error("failed to getAccountConfigByUidWithoutCUKI", e);
		} finally {
			SessionFactory.closeSession();
		}
		return accounts;

	}

	/**
	 * 设置账号的CAG配置.
	 * 
	 * @param accounts
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_account> setAccountConfig(
			Map<String, String> configMap) {

		List<Clickoo_mail_account> list = null;

		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_mail_account>) sqlSession.selectList(namespace
					+ ".setAccountConfig", configMap);
			sqlSession.commit();
		} catch (Exception e) {
			logger.error("failed to setAccountConfig", e);
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			return null;
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 判断账号是否属于指定的用户.
	 * 
	 * @param uid
	 * @param accountName
	 * @return
	 */
	public boolean validateUidAndAccountName(String uid, String accountName) {
		Clickoo_mail_account account = new Clickoo_mail_account();
		account.setUuid(uid);
		account.setName(accountName);
		try {
			sqlSession = SessionFactory.getSession();
			return sqlSession.selectOne(namespace
					+ ".validateUidAndAccountName", account) != null;
		} catch (Exception e) {
			logger.error("failed to validateUidAndAccountName", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
	}

	/**
	 * 得到某一stmp所有账号
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_account> getAccountBySTMP() {
		List<Clickoo_mail_account> accounts = null;
		try {
			sqlSession = SessionFactory.getSession();
			accounts = sqlSession.selectList(namespace + ".getAccountBySTMP");
		} catch (Exception e) {
			logger.error("failed to getAccountBySTMP", e);
		} finally {
			SessionFactory.closeSession();
		}
		return accounts;

	}

	/**
	 * 根据邮箱名获取验证配置.
	 * 
	 * 返回map中参数：
	 * 
	 * out_state 状态Integer类型(0成功，1不识别，2非法用户，3已经注册)
	 * 
	 * out_inpath 接收配置
	 * 
	 * out_outpath 发送配置
	 * 
	 * @param uname
	 * @param aname
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMailBoxConfig(String uname, String aname) {
		if (aname == null) {
			return null;
		}
		String suffix = aname.substring(aname.indexOf('@') + 1);
		String second_suffix = suffix.substring(0, suffix.indexOf(".") + 1)
				+ "*";
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("in_uuid", uname);
		parameterMap.put("in_name", aname);
		parameterMap.put("in_suffix", suffix);
		parameterMap.put("in_second_suffix", second_suffix);

		Map<String, Object> resultMap = null;
		try {
			sqlSession = SessionFactory.getSession();
			resultMap = (Map<String, Object>) sqlSession.selectOne(namespace
					+ ".getMailBoxConfig", parameterMap);
		} catch (Exception e) {
			logger.error("failed to getMailBoxConfig", e);
		} finally {
			SessionFactory.closeSession();
		}
		return resultMap;
	}

	/**
	 * 添加一个账号.
	 * 
	 * @param account
	 * @return
	 */
	public long addMailAccount(Clickoo_mail_account account) {

		long aid = -1;
		try {
			sqlSession = SessionFactory.getSession();
			aid = (Long) sqlSession.selectOne(namespace + ".addMailAccount",
					account);
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();
			logger.error("failed to addMailAccount", e);
		} finally {
			SessionFactory.closeSession();
		}
		return aid;
	}

	/**
	 * 根据账号类型获取有效账号 getAllAccount
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_account> getAllValidAccountListByType(int type) {
		List<Clickoo_mail_account> accounts = null;
		try {
			sqlSession = SessionFactory.getSession();
			accounts = sqlSession.selectList(namespace
					+ ".getAllValidAccountListByType", type);
		} catch (Exception e) {
			logger.error("failed to getAccountByYahoo", e);
		} finally {
			SessionFactory.closeSession();
		}
		return accounts;
	}

	/**
	 * 得到所有账号
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_account> getAllAccounts() {
		List<Clickoo_mail_account> accounts = null;
		try {
			sqlSession = SessionFactory.getSession();
			accounts = sqlSession.selectList(namespace + ".getAllAccount");
		} catch (Exception e) {
			logger.error("failed to getAllAccounts", e);
		} finally {
			SessionFactory.closeSession();
		}
		return accounts;
	}

	/**
	 * 删除账号的所有信息（此处不删除账号表和邮件信息表的记录）.
	 * 
	 * @param account
	 *            account pojo instance
	 * @return true or false
	 */
	public Clickoo_mail_account removeAllInfoByUidAndAid(Clickoo_user_account ua) {

		Clickoo_mail_account account = null;
		try {
			sqlSession = SessionFactory.getSession();
			account = (Clickoo_mail_account) sqlSession.selectOne(namespace
					+ ".removeAllAccountInfoByUidAndAid", ua);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to updateAccountState", e);
		} finally {
			SessionFactory.closeSession();
		}
		return account;
	}

	public boolean updateMaxUuid(Clickoo_mail_account account) {

		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateMaxUuid", account);
			sqlSession.commit();
		} catch (Exception e) {
			logger.error("failed to updateMaxUuid", e);
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/**
	 * 有效账号size
	 * 
	 * @param aid
	 * @return
	 */
	public int getAllValidAccountSize() {
		int rolelevel = 0;
		try {
			sqlSession = SessionFactory.getSession();
			rolelevel = (Integer) sqlSession.selectOne(namespace
					+ ".getAllValidAccountSize");
		} catch (Exception e) {
			logger.error("failed to getAllValidAccountSize", e);
		} finally {
			SessionFactory.closeSession();
		}
		return rolelevel;
	}

	/**
	 * 根据mid关联查询帐号名称（发送邮件判断邮箱类型用）
	 * 
	 * @return
	 */
	public String getAccountNameByMid(String mid) {
		String name = null;
		try {
			sqlSession = SessionFactory.getSession();
			name = (String) sqlSession.selectOne(namespace
					+ ".getAccountNameById", mid);
		} catch (Exception e) {
			logger.error("failed to getAccountNameByMid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return name;
	}
	
	/**
	 * 得到所有在线用户账号
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_account> getAllMailAccount() {
		List<Clickoo_mail_account> accounts = null;
		try {
			sqlSession = SessionFactory.getSession();
			accounts = sqlSession.selectList(namespace + ".getAllMailAccount");
		} catch (Exception e) {
			logger.error("failed to getAllAccounts", e);
		} finally {
			SessionFactory.closeSession();
		}
		return accounts;
	}
}
