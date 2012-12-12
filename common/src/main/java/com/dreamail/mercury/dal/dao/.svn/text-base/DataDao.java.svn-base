/**
 * 
 */
package com.dreamail.mercury.dal.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_message_data;

/**
 * @author meng.sun
 * 
 */
public class DataDao {

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
	private static String namespace = "com.clickoo.mercury.domain.DataMapper";

	
	
	
	/**
	 * 根据id查询邮件正文.
	 * @param id data id
	 * @return Clickoo_message_data
	 */
	public Clickoo_message_data selectDataById(Long id) {
		Clickoo_message_data data = null;
		try {
			sqlSession = SessionFactory.getSession();
			data = (Clickoo_message_data) sqlSession.selectOne(namespace + ".selectDataById", id);
		} catch (Exception e) {
			logger.error("failed to selectDataById", e);
		} finally {
			SessionFactory.closeSession();
		}
		return data;
	}
	
	/**
	 * 根据邮件mids删除邮件正文.
	 */
	public boolean deleteDataByMids(Long[] mids) {
		if(mids!=null && mids.length>0){
			try {
				sqlSession = SessionFactory.getSession();
				if (sqlSession.delete(namespace + ".deleteDataByMids",
						mids) == 0) {
					logger.info("failed to deleteDataByMids");
				}
				sqlSession.commit();
			} catch (Exception e) {
				if (sqlSession != null) {
					sqlSession.rollback();
				}
				logger.error("failed to deleteDataByMids", e);
				return false;
			} finally {
				SessionFactory.closeSession();
			}
			return true;
		}
		return false;
	}
}
