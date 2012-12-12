package com.dreamail.mercury.dal.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_volume_meta;


public class VolumeMetaDao {
	
	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory.getLogger(VolumeMetaDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.VolumeMetaMapper.";
	
	
	/**
	 * 得到所有的volume meta 信息.
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_volume_meta> getAllVolumeMeta() {
		List<Clickoo_volume_meta> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_volume_meta>) sqlSession.selectList(namespace + "getAllVolumeMeta");
		} catch (Exception e) {
			logger.error("failed to get all volume meta", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}
	
}
