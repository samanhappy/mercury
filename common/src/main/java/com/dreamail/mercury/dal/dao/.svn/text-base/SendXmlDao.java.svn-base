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
import com.dreamail.mercury.pojo.Clickoo_send_xml;

/**
 * @author meng.sun
 *
 */
public class SendXmlDao {

	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory.getLogger(SendXmlDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.SendXmlMapper";
	
	/**
	 * 插入Clickoo_send_xml记录到数据库.
	 * 
	 * @param message Clickoo_message
	 * @return 失败返回-1.
	 */
	public boolean addSendXml(Clickoo_send_xml clickoo_send_xml) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(namespace + ".addSendXml", clickoo_send_xml);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to insert addSendXml",e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}
	
	/**
	 * 更新Clickoo_send_xml
	 * @return
	 */
	public boolean updateSendXmlBysxKey(Clickoo_send_xml clickoo_send_xml) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.update(namespace + ".updateSendXmlBysxKey",clickoo_send_xml);
			sqlSession.commit();
		} catch (Exception e) {
			logger.error("failed to Clickoo_send_xml", e);
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
	 * 根据sxKey得到Clickoo_send_xml
	 * @param mckey
	 * @return Clickoo_send_xml
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_send_xml> getSenXmlBysxKey(String sxKey) {
		List<Clickoo_send_xml> sendxmls = null;
		try {
			sqlSession = SessionFactory.getSession();
			sendxmls = (List<Clickoo_send_xml>) sqlSession.selectList(
					namespace+ ".getSenXmlBysxKey", sxKey);
		} catch (Exception e) {
			logger.error("failed to Clickoo_send_xml", e);
		} finally {
			SessionFactory.closeSession();
		}
		return sendxmls;
	}
	
	/**
	 * 删除
	 * @return true or false
	 */
	public boolean deleteSendXmlBysxKey(String sxKey) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.delete(namespace + ".deleteSendXmlBysxKey", sxKey);
			sqlSession.commit();
		} catch (SQLException e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to delete SendXml", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}
	
	/**
	 * 删除
	 * @return true or false
	 */
	public boolean deleteSendXmlByTime(String sxDate) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.delete(namespace + ".deleteSendXmlByTime", sxDate);
			sqlSession.commit();
		} catch (SQLException e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to delete SendXml", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}
	
//	public static void main(String[] args) {
//		SendXmlService sendXmlDao = new SendXmlService();
//		String a = "\"sex\"";
//	    String xml = "xml version=\"1.0\" encoding=\"UTF-8\"?><PushMail><Header><Version>PushMail1.0</Version><Cred><UUID>48</UUID></Cred></Header><Body><Target name=\"Email\" isAtom=\"true\"><Cmd name=\"EmailList\"><Meta size=\"4\"><Item name=\"IMEI\">355629040000033_9460012926281479_</Item><Item name=\"AidTimeDate\">1;2011-04-25 15:17:11</Item><Item name=\"TimeDate\">2011-04-25 14:39:45</Item><Item name=\"SeqNo\">2</Item></Meta></Cmd></Target></Body></PushMail>";
//		sendXmlDao.addXml("1", xml);
//		
//	}
}
