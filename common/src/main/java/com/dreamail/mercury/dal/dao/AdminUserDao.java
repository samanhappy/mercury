package com.dreamail.mercury.dal.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.AdminUser;

public class AdminUserDao {

	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory.getLogger(AdminUserDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.dreamail.mercury.domain.AdminUserMapper";

	public String getAuthorityByNameAndPassword(AdminUser user) {
		String authority = null;
		try {
			sqlSession = SessionFactory.getSession();
			authority = (String) sqlSession.selectOne(namespace
					+ ".getAuthorityByNameAndPassword", user);
		} catch (Exception e) {
			logger.error("failed to getAuthorityByNameAndPassword", e);
		} finally {
			SessionFactory.closeSession();
		}
		return authority;
	}
	
	public static void main(String[] args) {
		AdminUser user = new AdminUser();
		user.setUsername("1");
		user.setPassword("1");
		System.out.println(new AdminUserDao().getAuthorityByNameAndPassword(user));
	}

}
