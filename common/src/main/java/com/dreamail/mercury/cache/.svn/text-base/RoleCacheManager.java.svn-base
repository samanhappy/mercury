package com.dreamail.mercury.cache;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.RoleDao;
import com.dreamail.mercury.pojo.Clickoo_role;

public class RoleCacheManager {

	private static Logger logger = LoggerFactory
			.getLogger(RoleCacheManager.class);

	private static ConcurrentHashMap<Integer, Clickoo_role> roleServercache;

	public void init() {
		roleServercache = getRoleCache();
		RoleDao roleDao = new RoleDao();
		List<Clickoo_role> roleList = roleDao.selectAllRoles();
		for (Clickoo_role role : roleList) {
			roleServercache.put(role.getPriority(), role);
		}
		logger.info("roleServercache init end......");
	}
	
	private static ConcurrentHashMap<Integer, Clickoo_role> getRoleCache(){
		if(roleServercache == null){
			roleServercache = new ConcurrentHashMap<Integer, Clickoo_role>();
		}
		return roleServercache;
	}

	/**
	 * 获得角色等级获得角色信息
	 * 
	 */
	public static Clickoo_role getRoleByLevel(int level) {
		Clickoo_role clickooRole = roleServercache.get(level);
		return clickooRole;
	}
}
