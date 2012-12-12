/**
 * 
 */
package com.dreamail.mercury.dal.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_task_factory;

/**
 * @author meng.sun
 *
 */
public class TaskFactoryDao {

	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory.getLogger(TaskFactoryDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.TaskFactoryMapper";
	
	/**
	 * 插入TaskParameter记录到数据库.
	 * 
	 * @param message Clickoo_message
	 * @return 失败返回-1.
	 */
	public long addTaskParameter(Clickoo_task_factory clickoo_task_factory) {
		long mid = 0;
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(namespace + ".addTaskParameter", clickoo_task_factory);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to insert addTaskParameter",e);
			return -1;
		} finally {
			SessionFactory.closeSession();
		}
		return mid;
	}
	
	
	/**
	 * 获得所有Clickoo_task_factory.
	 * @return Clickoo_task_factory List 类型
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_task_factory> getTaskParameterByType(String mctype) {
		List<Clickoo_task_factory> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_task_factory>) sqlSession.selectList(namespace +".getTaskParameterByType",mctype);
		} catch (Exception e) {
			logger.error("failed to get all getTaskParameterByType", e);
		}finally {
			SessionFactory.closeSession();
		}
		return list;
	}
	
	/**
	 * 更新clickoo_task_factory
	 * @return
	 */
	public boolean updateTaskParameterByMCKey(Clickoo_task_factory clickoo_task_factory) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateTaskParameterByMCKey",clickoo_task_factory);
			sqlSession.commit();
		} catch (Exception e) {
			logger.error("failed to clickoo_task_factory", e);
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
	 * 根据mckey得到Clickoo_task_factory
	 * @param mckey
	 * @return Clickoo_task_factory
	 */
	public Clickoo_task_factory getTaskParameterByMCKey(String mckey) {
		Clickoo_task_factory message = null;
		try {
			sqlSession = SessionFactory.getSession();
			message = (Clickoo_task_factory) sqlSession.selectOne(
					namespace+ ".getTaskParameterByMCKey", mckey);
		} catch (Exception e) {
			logger.error("failed to Clickoo_task_factory", e);
		} finally {
			SessionFactory.closeSession();
		}
		return message;
	}
	
	/**
	 * 删除
	 * @return true or false
	 */
	public boolean deleteTaskParameterByMCKey(String key) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.delete(namespace + ".deleteTaskParameterByMCKey", key);
			sqlSession.commit();
		} catch (SQLException e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to delete TaskParameterByMCKey", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}
	
	/**
	 * 根据mctype删除
	 * @return true or false
	 */
	public boolean deleteTaskParameterByMCType(String type) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.delete(namespace + ".deleteTaskParameterByMCType", type);
			sqlSession.commit();
		} catch (SQLException e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to delete deleteTaskParameterByMCType", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}
}
