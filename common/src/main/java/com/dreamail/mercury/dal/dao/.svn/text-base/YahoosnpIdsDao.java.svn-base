package com.dreamail.mercury.dal.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_yahoosnp_ids;

public class YahoosnpIdsDao {

	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(YahoosnpIdsDao.class);
	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.YahoosnpIdsMapper";

	/*
	 * 根据aid查询所有的uuid，并在查询后删除.
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllUuidByAid(long aid) {
		List<String> uuids = null;
		try {
			sqlSession = SessionFactory.getSession();
			uuids = (List<String>) sqlSession.selectList(namespace
					+ ".selectAllUuidByAid", aid);
			sqlSession.delete(namespace + ".deleteAllUuidByAid", aid);
			sqlSession.commit();
		} catch (SQLException e) {
			logger.error("failed to getAllUuidByAid and deleteAllUuidByAid", e);
			sqlSession.rollback();
		} finally {
			SessionFactory.closeSession();
		}
		return uuids;
	}

	/*
	 * 插入一条uuid记录.
	 */
	public boolean insertUuid(Clickoo_yahoosnp_ids cyi) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(namespace + ".insertUuid", cyi);
			sqlSession.commit();
		} catch (SQLException e) {
			logger.error("failed to insertUuid", e);
			sqlSession.rollback();
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}

	/*
	 * 插入多条uuid记录.
	 */
	public boolean insertUuids(long aid, long beginuuid, long enduuid) {
		if (beginuuid >= enduuid) {
			logger.info("no uuid to save, do nothing");
			return true;
		}
		Clickoo_yahoosnp_ids cyi = new Clickoo_yahoosnp_ids();
		cyi.setAid(aid);
		try {
			sqlSession = SessionFactory.getSession();
			for (long i = beginuuid; i < enduuid; i++) {
				cyi.setUuid(String.valueOf(i));
				sqlSession.insert(namespace + ".insertUuid", cyi);
			}
			sqlSession.commit();
		} catch (SQLException e) {
			logger.error("failed to insertUuids", e);
			sqlSession.rollback();
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}
}
