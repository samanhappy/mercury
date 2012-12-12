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
import com.dreamail.mercury.pojo.Clickoo_mail_send_server;

/**
 * @author meng.sun
 *
 */
public class SendServerDao {

	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(SendServerDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.SendServerMapper";

	
	/**
	 * 根据name查询得到发送服务器对象.
	 * @param name server name
	 * @return Clickoo_mail_send_server
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_send_server> getSendServerByName(String name) {
		List<Clickoo_mail_send_server> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_mail_send_server>) sqlSession
					.selectList(namespace + ".getSendServerByName", name);
		} catch (SQLException e) {
			logger.error("failed to get sendserver by name", e);
		}  finally {
			SessionFactory.closeSession();
		}
		return list;
	}
	
	/**
	 * 添加一条receive server记录.
	 * @param server send server instance
	 * @return boolean
	 */
	public boolean createSendServer(Clickoo_mail_send_server server) {
		boolean flag = true;
		try {
			sqlSession = SessionFactory.getSession();
			String name = server.getName().substring(0, server.getName().indexOf(".") + 1) + "*";
			long idSpecial = Long.parseLong((String) sqlSession.selectOne(namespace + ".getSendServerCountByName", name));
			if(idSpecial>0){
				//说明有xxx.*存在，无需插入
				return true;
			}
			long id = Long.parseLong((String) sqlSession.selectOne(namespace + ".getSendServerCountByName", server.getName()));
			if(id>0){
				//说明有该配置存在无需插入
				return true;
			}
			sqlSession.insert(namespace + ".insertSendServer", server);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to insert a send server", e);
		} finally {
			SessionFactory.closeSession();
		}
		return flag;
	}
	
	/**
	 * 得到所有的发送邮箱配置信息.
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_send_server> getAllSendServer() {
		List<Clickoo_mail_send_server> serverList = null;
		try {
			sqlSession = SessionFactory.getSession();
			serverList = sqlSession.selectList(namespace + ".getAllSendServer");
		} catch (Exception e) {
			logger.error("failed to get all send server", e);
		} finally {
			SessionFactory.closeSession();
		}
		return serverList;
	}
}
