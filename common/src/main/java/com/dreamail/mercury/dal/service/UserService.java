/**
 * 
 */
package com.dreamail.mercury.dal.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cache.domain.AccountCacheObject;
import com.dreamail.mercury.dal.dao.UARelationDao;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.pojo.User_role;

/**
 * @author meng.sun
 * 
 */
public class UserService {

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	/**
	 * 注册新用户和设备 . 返回注册成功的uid，如果返回-1代表出错
	 * 
	 * @param user
	 *            Clickoo_user
	 * @return long user id
	 */
	public long createUser(Clickoo_user user) {
		return new UserDao().createUser(user);
	}

	/**
	 * 更新用户.
	 * 
	 * @param user
	 *            Clickoo_user
	 * @return boolean
	 */
	public boolean updateUser(Clickoo_user user) {
		return new UserDao().updateUser(user);
	}

	/**
	 * 根据uid删除用户信息以及user_account表相关信息.
	 * 
	 * @param uid
	 *            user id
	 * @return boolean
	 */
	public boolean deleteAllInfoByUid(long uid) {
		boolean delSuc = false;
		List<String> aidList = new UARelationDao().selectAllAid(uid);
		if (aidList != null && aidList.size() != 0) {
			logger.info("Account id size : " + aidList.size());
//			StringBuffer sb = new StringBuffer("accountRemove");
//			Clickoo_user user = new UserService().getUserById(uid);
//			if (user == null) {
//				logger.info("uid: " + uid + " dont exit");
//				return delSuc;
//			}
//			sb.append(",");
//			sb.append(user.getIMEI());
			for (String id : aidList) {
				long aid = Long.parseLong(id);
				if (new AccountService().deleteAllInfoByUidAndAid(uid, aid)) {
					delSuc = true;
//					sb.append("," + id);
				} else {
					delSuc = false;
				}
			}
//			logger.info("sendMsg:"+sb.toString());
//			new JmsSender().sendTopicMsg(sb.toString(), "timerTopic",
//					PropertiesDeploy.getConfigureMap().get("topic.url"));
		} else {
			logger.info("this use have not account");
			delSuc = true;
		}
		if (delSuc) {
			if (!new UserDao().deleteAllInfoByUid(uid)) {
				delSuc = false;
			}
		}
		return delSuc;
	}

	public static void main(String[] args) {
		System.out.println(new UserService().deleteAllInfoByUid(2l));
	}

	/**
	 * 根据uid验证用户是否存在.
	 * 
	 * @param uid
	 *            user id
	 * @return boolean
	 */
	public boolean isExist(Long uid) {
		return new UserDao().selectUser(uid);
	}

	/**
	 * 根据username验证数据库中是否存在此用户
	 * 
	 * @param username
	 * @return boolean
	 * 
	 */
	public boolean isEsixtUsername(String username) {
		Clickoo_user users = new UserDao().getUsersByUsername(username);
		boolean succ = false;
		if (users != null) {
			succ = true;
		}
		return succ;
	}

	/**
	 * 根据uid得到用户
	 * 
	 * @param uid
	 * @return
	 */
	public Clickoo_user getUserById(Long uid) {
		return new UserDao().getUserById(uid);
	}

	public User_role getUserRoleById(Long uid) {
		return new UserDao().getUserRoleById(uid);
	}
	
	public String getDeviceModelByUid(String uid) {
		return new UserDao().getDeviceModelByUid(uid);
	}

	public boolean updateUARelationStatus(long uid, long aid, int status) {
		return new UARelationDao().updateStatusByUid(uid, aid, status);
	}

	@SuppressWarnings("unused")
	private static Clickoo_mail_account parseToPojoObject(
			AccountCacheObject cache) {
		Clickoo_mail_account account = new Clickoo_mail_account();
		account.setId(cache.getId());
		account.setInCert(cache.getInCert());
		account.setInPath(cache.getInPath());
		account.setName(cache.getName());
		account.setOutCert(cache.getOutCert());
		account.setOutPath(cache.getOutPath());
		// account.setRegistrationDate(cache.getRegistrationDate());
		account.setStatus(cache.getStatus());
		// account.setUid(cache.getUid());

		return account;
	}

	/**
	public long validateUserExist(String username, String password) {
		Clickoo_user users = new UserDao().getUsersByUsername(username);
		long i = 0;
		if (users != null) {
				if (password.equalsIgnoreCase(users.getPassword())) {
					i = users.getUid();
				} else {
					i = -1;
				}
		}
		return i;
	}
	 */
	
	/**
	 * -1：没有该user
	 * -2：密码不正确
	 * 1: 不需要同步,不需要T人，也不需要更新数据库
	 * 2：需要更新角色,用户验证通过且IMEI一样，不需要T人，也不需要更新数据库
	 * 3：IMEI不一样，但用户验证通过,需要同步全部信息，该用户可能从另一个手机登录,需要T另外一个手机下线,需要更细数据库
	 * 4：不能将该IMEI捆的用户T下线,要同步全部信息，因为自己即将在该IMEI上登录，需要更新数据库，如果upe上线后会主动通知该IMEI收邮件则不需要发送假消息给upe
	 */
	public int validateUserSynchroinzation(String username,String password, String IMEI,Clickoo_user user) {
		int res = -1;
		if (user != null) //代表没有该user
		{
				if (password.equals(user.getPassword())) 
				{
					if(user.getIMEI()==null || "".equals(user.getIMEI()))//IMEI为空表示该用户注册登录后，被其他用户T下线
					{
						//不能将该IMEI捆的用户T下线,因为自己即将在该IMEI上登录，需要更新数据库，如果upe上线后会主动通知该IMEI收邮件则不需要发送假消息给upe
						res = 4;
					}
					else if (user.getIMEI().equals(IMEI)) 
					{
						if(user.getStatus() == 0)
						{
							res = 1;//不需要同步？，用户验证通过且IMEI一样,不需要T人，也不需要更新数据库
						}
						else
						{
							res = 2;//需要更新角色？，用户验证通过且IMEI一样，不需要T人，也不需要更新数据库
						}
					}
					else
					{
						res = 3;//IMEI不一样，但用户验证通过,需要同步全部信息，该用户可能从另一个手机登录,需要T另外一个手机下线,需要更细数据库
					}
				}
				else
				{
					res = -2;//密码不正确
				}
			}
		return res;
	}



	public boolean validateUserIMEI(String uid, String IMEI) {
		boolean flag = false;
		UserDao userDao = new UserDao();
		Clickoo_user user = userDao.getUserById(Long.parseLong(uid));
		if (user != null) {
			if (IMEI.equalsIgnoreCase(user.getIMEI())) {
				flag = true;
			}
		}
		return flag;
	}
	//	
	// public static void main(String[] args) {
	// if(new UserService().isEsixtIMEI("1")){
	// System.out.println("存在");
	// }else{
	// System.out.println("不存在");
	// }
	// }
	
	/**
	 * 得到某级别下所有用户
	 * @return
	 */
	public List<Clickoo_user> getUserByTitle(String title) {
		return new UserDao().getUserByTitle(title);
	}
}
