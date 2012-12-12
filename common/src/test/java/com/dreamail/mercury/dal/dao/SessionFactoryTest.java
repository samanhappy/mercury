package com.dreamail.mercury.dal.dao;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.domain.IDTypes;
import com.dreamail.mercury.pojo.Clickoo_ids;

public class SessionFactoryTest {
	@Test
	public void sessionTest() throws SQLException{
		SqlSession session = SessionFactory.getSession();
		Clickoo_ids result = (Clickoo_ids)session.selectOne("com.clickoo.mercury.domain.IDsMapper.selectIDs",IDTypes.ACCOUNT_ID);
		System.out.println(result.getName());
		SessionFactory.closeSession();
	}
}
