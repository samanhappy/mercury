/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamail.mercury.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dreamail.mercury.pojo.Clickoo_ids;

/**
 * 
 * @author tiger
 */
public class IDGeneratorTest {

	public IDGeneratorTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of nextID method, of class IDGenerator.
	 */
	@Test
	public void testNextID() throws SQLException {
		String resources = "ibatis.ids.cfg.xml";
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resources);
			SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder()
					.build(reader);
			reader.close();
			SqlSession session = sqlMapper.openSession();
			session.getConnection().setAutoCommit(false);
			// Clickoo_ids result = new Clickoo_ids();
			Clickoo_ids result = (Clickoo_ids) session.selectOne(
					"com.clickoo.mercury.domain.IDsMapper.selectIDs", "1");
			// result.setName(IDTypes.ACCOUNT_ID);
			// session.insert("com.clickoo.mercury.domain.IDsMapper.insertIDs",
			// result);
			Logger.getAnonymousLogger().log(
					Level.INFO,
					"sssssssssss" + "|" + "{0}|{1}|{2}",
					new Object[] { result.getName(), result.getNonce(),
							result.getSteps() });
			session.commit();
			session.close();
		} catch (IOException iOException) {
			Logger.getAnonymousLogger().log(Level.SEVERE,
					iOException.getMessage());
		}
	}

	/**
	 * Test get nextID method, of class IDGenerator.
	 */
	@Test
	public void testNextIDS() {
	}
}
