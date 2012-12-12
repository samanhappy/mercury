package com.dreamail.mercury.dal;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取Session
 * 
 * @author kai.li
 */
public class SessionFactory {

	private static final String resourcesMain = "ibatismain.ids.cfg.xml";
	
	private static SqlSessionFactory sqlMapperMain;
	
	private static  ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();
	
	private static Logger logger = LoggerFactory.getLogger(SessionFactory.class);

	private SessionFactory() {
	}
	
	static {
		try {
			Reader reader = Resources.getResourceAsReader(resourcesMain);
			sqlMapperMain = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
		} catch (IOException e) {
			logger.error("failed to build SqlSessionFactory instance", e);
		}
	}

	/**
	 * 从pool中获取可用session
	 * @return SqlSession
	 * @throws SQLException
	 */
	public static SqlSession getSession() throws SQLException {
		/**
		 * 此处应加入超时判断，防止有连接未关闭，再次获得该链接时，超时错误的发生
		 */
		SqlSession session = (SqlSession) threadLocal.get();
		if (session == null || session.getConnection().isClosed()) {
			try {
				session = sqlMapperMain.openSession();
			} catch (Exception e) {
				/**
				 * 如果出现连接异常，重试一次;
				 */
				logger.info("failed to open session, try again...");
				try {
					session = sqlMapperMain.openSession();
				} catch (Exception exe) {
					/**
					 * 如果出现连接异常，重试一次;
					 */
					logger.info("failed to open session, try again...");
					session = sqlMapperMain.openSession();
				}
			}
			threadLocal.set(session);
		}
		return session;
	}
	/**
	 * 将session还回pool
	 * @throws SQLException
	 */
	public static void closeSession() {
		SqlSession session = (SqlSession) threadLocal.get();
		threadLocal.set(null);
		if (session != null) {
			session.close();
		}
	}
}
