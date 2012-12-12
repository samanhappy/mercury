package com.dreamail.mercury.dal.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_sender_filter;

public class SenderFilterDao {
	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory.getLogger(SenderFilterDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.SenderFilterMapper";
	/**
	 * 插入一条Clickoo_sender_filter.
	 * @param senderFilter
	 * @return
	 */
	public long saveSenderFilter(Clickoo_sender_filter senderFilter) {
		if (senderFilter == null) {
			return -1;
		}
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(namespace + ".insertSenderFilter",
					senderFilter);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to insert Clickoo_sender_filter", e);
			return -1;
		} finally {
//			SessionFactory.closeSession();
		}
		return senderFilter.getId();
	}
	/**
	 * 根据uid、name查询Clickoo_sender_filter.
	 * @param filter
	 * @return
	 */
	public Clickoo_sender_filter selectSenderFilterByName(Clickoo_sender_filter filter){
		Clickoo_sender_filter SenderFilter = null;
		try {
			sqlSession = SessionFactory.getSession();
			SenderFilter = (Clickoo_sender_filter) sqlSession.selectOne(namespace 
					+ ".selectSenderFilterByName",filter);
		} catch (Exception e) {
			logger.error("failed to selectSenderFilterByName", e);
		} finally {
			SessionFactory.closeSession();
		}
		return SenderFilter;
	}
	
	/**
	 * 根据uid、name查询Clickoo_sender_filter.
	 * @param filter
	 * @return
	 */
	public boolean deleteSenderFilterByName(Clickoo_sender_filter filter){
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.delete(namespace + ".deleteSenderFilterByName",filter);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to deleteSenderFilterByName", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;
	}
	
	/**
	 * 根据uid、sign查处所有黑、白名单.
	 * @param filter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_sender_filter> selectSenderFilterBySign(Clickoo_sender_filter filter){
		List<Clickoo_sender_filter> filterList = null;
		try {
			sqlSession = SessionFactory.getSession();
			filterList = (List<Clickoo_sender_filter>) sqlSession.selectList(namespace 
					+ ".selectSenderFilterBySign",filter);
		} catch (Exception e) {
			logger.error("failed to selectSenderFilterByName", e);
		} finally {
			SessionFactory.closeSession();
		}
		return filterList;
	} 
	/**
	 * 查询所有的黑白名单.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_sender_filter> selectAllSenderFilter(){
		List<Clickoo_sender_filter> filterList = null;
		try {
			sqlSession = SessionFactory.getSession();
			filterList = (List<Clickoo_sender_filter>) sqlSession.selectList(namespace 
					+ ".selectSenderFilterBySign");
		} catch (Exception e) {
			logger.error("failed to selectSenderFilterByName", e);
		} finally {
			SessionFactory.closeSession();
		}
		return filterList;
	} 
}
