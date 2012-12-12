/**
 * 
 */
package com.dreamail.mercury.dal.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.dal.service.VolumeService;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;

/**
 * @author meng.sun
 * 
 */
public class AttachmentDao {

	/**
	 * 数据库操作实例.
	 */
	private SqlSession sqlSession;

	/**
	 * logger实例.
	 */
	private static Logger logger = LoggerFactory.getLogger(AttachmentDao.class);

	/**
	 * ibatis映射路径.
	 */
	private static String namespace = "com.clickoo.mercury.domain.AttachmentMapper";

	/**
	 * 根据id查询邮件正文.
	 * 
	 * @param id
	 *            attachment id
	 * @return Clickoo_message_data
	 */
	public Clickoo_message_attachment selectAttachmentById(long id) {
		Clickoo_message_attachment attachment = null;
		try {
			sqlSession = SessionFactory.getSession();
			attachment = (Clickoo_message_attachment) sqlSession.selectOne(
					namespace + ".selectAttachmentById", id);
			if (attachment != null) {
				attachment.setVolume(new VolumeService()
						.getVolumeById(attachment.getVolume_id()));
			}
		} catch (Exception e) {
			logger.error("failed to selectAttachmentById", e);
		} finally {
			SessionFactory.closeSession();
		}
		return attachment;
	}

	/**
	 * 根据id查询邮件正文.
	 * 
	 * @param id
	 *            attachment id
	 * @return Clickoo_message_data
	 */
	public long getNextAttachmentId() {
		Clickoo_message_attachment attachment = new Clickoo_message_attachment();
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.insert(namespace + ".getNextAttachmentId", attachment);
			sqlSession.commit();
		} catch (Exception e) {
			logger.error("failed to selectAttachmentById", e);
			sqlSession.rollback();
			return -1;
		} finally {
			SessionFactory.closeSession();
		}
		return attachment.getId();
	}

	/**
	 * 根据message id查询附件列表.
	 * 
	 * @param id
	 *            attachment id
	 * @return Clickoo_message_data
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_message_attachment> selectAttachmentListByMid(long mid) {
		List<Clickoo_message_attachment> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_message_attachment>) sqlSession.selectList(
					namespace + ".selectAttachmentListByMid", mid);
		} catch (Exception e) {
			logger.error("failed to selectAttachmentListByMid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 根据message id查询原始附件列表.
	 * 
	 * @param id
	 *            attachment id
	 * @return Clickoo_message_data
	 */
	@SuppressWarnings("unchecked")
	public List<Clickoo_message_attachment> selectOriginalAttachmentListByMid(
			long mid) {
		List<Clickoo_message_attachment> list = null;
		try {
			sqlSession = SessionFactory.getSession();
			list = (List<Clickoo_message_attachment>) sqlSession.selectList(
					namespace + ".selectOriginalAttachmentListByMid", mid);
		} catch (Exception e) {
			logger.error("failed to selectAttachmentListByMid", e);
		} finally {
			SessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * 根据邮件id删除附件.
	 */
	public boolean deleteAttachmentByMid(long id) {
		try {
			sqlSession = SessionFactory.getSession();
			sqlSession.delete(namespace + ".deleteAttachmentByMid", id);
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			logger.error("failed to selectDeviceByAttachId", e);
			return false;
		} finally {
			SessionFactory.closeSession();
		}
		return true;

	}

	/**
	 * 根据邮件mids删除附件.
	 */
	public boolean deleteAttachmentByMids(Long[] mids) {
		if (mids != null && mids.length > 0) {
			try {
				sqlSession = SessionFactory.getSession();
				if (sqlSession.delete(namespace + ".deleteAttachmentByMids",
						mids) == 0) {
					logger.info("failed to deleteAttachmentByMids");
				}
				sqlSession.commit();
			} catch (Exception e) {
				if (sqlSession != null) {
					sqlSession.rollback();
				}
				logger.error("failed to deleteAttachmentByMids", e);
				return false;
			} finally {
				SessionFactory.closeSession();
			}
			return true;
		}
		return false;
	}
}
