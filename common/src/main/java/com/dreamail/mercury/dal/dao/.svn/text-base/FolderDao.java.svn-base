package com.dreamail.mercury.dal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_folder;

/**
 * 文件夹数据库操作.
 * 
 * @author pengkai.wang
 * 
 */
public class FolderDao {
	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory.getLogger(FolderDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.FolderMapper";

	/**
	 * 根据aid查出账号所有的文件夹.
	 * @param aid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_folder> selectFoldersByAid(long aid){
		List<Clickoo_folder> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_folder>) sqlSession.selectList(
					namespace + ".selectFoldersByAid", aid);
		} catch (Exception e) {
			logger.error("failed to selectFoldersByAid,aid:"+aid, e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}
	
	/**
	 * 向数据库添加一条folder的记录.
	 * 
	 * @param folder
	 * @return
	 */
	public long createFolder(Clickoo_folder folder) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(namespace + ".insertFolder", folder);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to setId for Clickoo_folder", e);
			return -1;
		} finally {
			SessionFactory.closeSession();
		}
		return folder.getId();
	}
	/**
	 * 根据aid和文件夹名称查出文件夹.
	 * @param aid
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_folder> selectFoldersByAidAndName(long aid,String name){
		List<Clickoo_folder> list = null;
		Map<String,String> map = new HashMap<String,String>();
		map.put("aid", String.valueOf(aid));
		map.put("name", name);
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_folder>) sqlSession.selectList(
					namespace + ".selectFoldersByAidAndName", map);
		} catch (Exception e) {
			logger.error("failed to selectFoldersByAidAndName,aid:"+aid+",name:"+name, e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}
	
	public static void main(String[] args) {
//		List<Clickoo_folder> list = new FolderDao().selectFoldersByAid(1l);
		List<Clickoo_folder> list = new FolderDao().selectFoldersByAidAndName(1l, "c");
		System.out.println(list);
		for(Clickoo_folder folder:list){
			System.out.println(folder.getReceiveStatus()+" "+folder.getRegisterDate()+" "+folder.getId());
		}
	}
}
