/**
 * 
 */
package com.dreamail.mercury.dal.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_volume;

/**
 * @author meng.sun
 *
 */
public class VolumeDao {

	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory.getLogger(VolumeDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.VolumeMapper";
	
	
	/**
	 * 获得所有Volume.
	 * @return Volume List 类型
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_volume> getAllVolume() {
		List<Clickoo_volume> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_volume>) sqlSession.selectList(namespace +".getAllVolume");
		} catch (Exception e) {
			logger.error("failed to get all volume", e);
		}finally {
			SessionFactory.closeSession();
		}
		return list;
	}
	
	/**
	 * 更新硬盘空间信息
	 * @return
	 */
	public boolean updateDiskState(Clickoo_volume clickoo_volume) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateDiskState",clickoo_volume);
			sqlSession.commit();
		} catch (Exception e) {
			logger.error("failed to updateDiskState", e);
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}
}
