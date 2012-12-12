package com.dreamail.mercury.dal.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_mail_account;


public class AccountDaoTest {
	private SqlSession sqlSession;
	private static String namespace = "com.clickoo.mercury.domain.AccountMapper";
	/**
	 * 根据name查询得到一个account.
	 * 
	 * @param name
	 *            account's name
	 * @return account pojo instance
	 */
	@Test
	public void getAccountByName() {
		Clickoo_mail_account account = null;
		try {
			sqlSession = SessionFactory.getSession();
			account = (Clickoo_mail_account) sqlSession.selectOne(namespace
					+ ".getAccountByName", "wpk1901@gmail.com");
			System.out.println(account.getInPath_obj().getInhost());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SessionFactory.closeSession();
		}
	}
	@Test
	public void insertAccount() throws Exception{
		try {
//			aid = IDGenerator.nextID(IDTypes.ACCOUNT_ID);
//			account.setId(aid);
			Clickoo_mail_account account = new Clickoo_mail_account();
			account.setId(1);
			account.setDeviceModel("300*110");
			account.setInCert("{\"host\":\"mail.archermind.com\",\"type\":\"\",\"protocoltype\":\"pop\",\"receivePort\":\"110\",\"supportalluid\":\"0\",\"compositor\":\"0\"}");
			account.setInPath("{\"host\":\"mail.archermind.com\",\"type\":\"\",\"protocoltype\":\"pop\",\"receivePort\":\"110\",\"supportalluid\":\"0\",\"compositor\":\"0\"}");
			account.setName("12312@archermind.com");
			account.setOutCert("{\"host\":\"mail.archermind.com\",\"type\":\"\",\"protocoltype\":\"pop\",\"receivePort\":\"110\",\"supportalluid\":\"0\",\"compositor\":\"0\"}");
			account.setOutPath("{\"host\":\"mail.archermind.com\",\"type\":\"\",\"protocoltype\":\"pop\",\"receivePort\":\"110\",\"supportalluid\":\"0\",\"compositor\":\"0\"}");
			account.setRegistrationDate(new Date());
//			account.setRoleId(1);
			account.setStatus(0);
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(namespace + ".insertAccount", account);
			sqlSession.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally {
				SessionFactory.closeSession();
			}
	}
	
	@Test
	public void getMessage(){
		try {
			sqlSession = SessionFactory.getSession();
//			account = (Clickoo_mail_account) sqlSession.selectOne(namespace
//					+ ".getAccountByName", "wpk1901@gmail.com");
//			System.out.println(account.getInPath_obj().getInhost());
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("aid", "1");
			map.put("mid","1");
			Clickoo_mail_account a = (Clickoo_mail_account)sqlSession.selectOne(namespace + ".getMessage", map);
			System.out.println(a.getClickoo_message().size());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SessionFactory.closeSession();
		}		
		
	}
	
	
}
