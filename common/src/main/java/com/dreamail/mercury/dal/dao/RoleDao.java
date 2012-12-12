package com.dreamail.mercury.dal.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_role;

public class RoleDao {
	private SqlSession sqlSession;
	private static Logger logger = LoggerFactory.getLogger(RoleDao.class);

	/**
	 * 获取多有角色配置.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_role> selectAllRoles() {
		List<Clickoo_role> roleList = null;
		try {
			sqlSession = SessionFactory.getSession();
			roleList = (List<Clickoo_role>) sqlSession
					.selectList("com.clickoo.mercury.domain.RoleMapper.selectAllRoles");
		} catch (Exception e) {
			logger.error("failed to selectAllRoles", e);
		} finally {
			SessionFactory.closeSession();
		}
		return roleList;
	}

	/**
	 * 根据角色名获取配置.
	 */
	public Clickoo_role selectRoleByTitle(String title) {
		Clickoo_role role = null;
		try {
			sqlSession = SessionFactory.getSession();
			role = (Clickoo_role) sqlSession
					.selectOne("com.clickoo.mercury.domain.RoleMapper.selectRoleByTitle", title);
		} catch (Exception e) {
			logger.error("failed to selectRoleByTitle", e);
		} finally {
			SessionFactory.closeSession();
		}
		return role;
	}
	
	/**
	 * 更新角色配置.
	 * @param role
	 * @return
	 */
	public boolean updateRole(Clickoo_role role) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update("com.clickoo.mercury.domain.RoleMapper.updateRole", role);
			sqlSession.commit();
		} catch (Exception e) {
			logger.error("failed to updateRole", e);
			sqlSession.rollback();
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}
	
	/**
	 * 根据角色级别查询角色.
	 * @param priority
	 * @return
	 */
	public Clickoo_role selectRoleByPriority(int priority){
		Clickoo_role role = null;
		try {
			sqlSession = SessionFactory.getSession();
			role = (Clickoo_role) sqlSession
					.selectOne("com.clickoo.mercury.domain.RoleMapper.selectRoleByPriority", priority);
		} catch (Exception e) {
			logger.error("failed to selectRoleByPriority", e);
		} finally {
			SessionFactory.closeSession();
		}
		return role;
	}
}
