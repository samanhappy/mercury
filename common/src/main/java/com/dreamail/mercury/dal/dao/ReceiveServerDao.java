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
import com.dreamail.mercury.pojo.Clickoo_mail_receive_server;

/**
 * 接收服务器Dao对象.
 * @author meng.sun
 * 
 */
public class ReceiveServerDao {

	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ReceiveServerDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.ReceiveServerMapper";

	/**
	 * 根据name查询得到接收服务器对象.
	 * @param name server name
	 * @return Clickoo_mail_receive_server
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_receive_server> getReceiveServerByName(String name) {
		List<Clickoo_mail_receive_server> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_mail_receive_server>) sqlSession
					.selectList(namespace + ".getReceiveServerByName", name);
		} catch (SQLException e) {
			logger.error("failed to get receiveserver by name", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 添加一条receive server记录.
	 * @param server Clickoo_mail_receive_server instance
	 * @return boolean
	 */
	public boolean createReceiveServer(Clickoo_mail_receive_server server) {
		boolean flag = true;
		try {
			sqlSession = SessionFactory.getSession();
			String name = server.getName().substring(0, server.getName().indexOf(".") + 1) + "*";
			long idSpecial = Long.parseLong((String) sqlSession.selectOne(namespace + ".getReceiveCountByName", name));
			if(idSpecial>0){
				//说明有xxx.*存在，无需插入
				return true;
			}
			long id = Long.parseLong((String) sqlSession.selectOne(namespace + ".getReceiveCountByName", server.getName()));
			if(id>0){
				//说明有该配置存在无序插入
				return true;
			}
			sqlSession.insert(namespace + ".insertReceiveServer", server);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to insert a receive server", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return flag;
	}
	
	public static void main(String[] args) throws SQLException {
		Clickoo_mail_receive_server server = new Clickoo_mail_receive_server();
		server.setName("@yahoo.com.cn");
		System.out.println(new ReceiveServerDao().createReceiveServer(server));
	}
	
	/**
	 * 得到所有的接收邮箱配置信息.
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_receive_server> getAllReceiveServer() {
		List<Clickoo_mail_receive_server> serverList = null;
		try {
			sqlSession = SessionFactory.getSession();
			serverList = sqlSession.selectList(namespace + ".getAllReceiveServer");
		} catch (Exception e) {
			logger.error("failed to get all receive server", e);
		} finally {
			SessionFactory.closeSession();
		}
		return serverList;
	}

	/**
	 * 根据状态获得邮箱配置信息.
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_mail_receive_server> getReceiveServerByStatus(String status) {
		List<Clickoo_mail_receive_server> serverList = null;
		try {
			sqlSession = SessionFactory.getSession();
			serverList = sqlSession.selectList(namespace + ".getReceiveServerByStatus",status);
		} catch (Exception e) {
			logger.error("failed to get all receive server by status", e);
		} finally {
			SessionFactory.closeSession();
		}
		return serverList;
	}
	
	/**
	 * 根据serverID接收邮箱配置信息.
	 * @return list
	 */
	public Clickoo_mail_receive_server getReceiveServerByID(long serverID) {
		Clickoo_mail_receive_server serverInfo = null;
		try {
			sqlSession = SessionFactory.getSession();
			serverInfo = (Clickoo_mail_receive_server) sqlSession.selectOne(namespace + ".getReceiveServerByID",serverID);
		} catch (Exception e) {
			logger.error("failed to get receive server info", e);
		} finally {
			SessionFactory.closeSession();
		}
		return serverInfo;
	}
	
	/**
	 * 添加一条receive server记录.
	 * @param server Clickoo_mail_receive_server instance
	 * @return boolean
	 */
	public boolean createReceiveServerAndSendServer(Clickoo_mail_receive_server server) {
		int i = -1;
		try {
			sqlSession = SessionFactory.getSession();
			int j = sqlSession.insert(namespace + ".insertReceiveServer", server);
			int k = sqlSession.insert(namespace + ".insertSendServer", server);
			if(j == 1 && k == 1){
				i = 1;
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to insert a receive server and a send server", e);
		} finally {
			SessionFactory.closeSession();
		}
		return i == 1;
	}
}
