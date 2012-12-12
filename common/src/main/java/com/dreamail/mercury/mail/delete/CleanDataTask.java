package com.dreamail.mercury.mail.delete;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cache.RoleCacheManager;
import com.dreamail.mercury.cache.VolumeCacheManager;
import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.dao.SendMessageDao;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.pojo.Clickoo_role;
import com.dreamail.mercury.pojo.Clickoo_send_message;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JsonUtil;

public class CleanDataTask {

	private static Logger logger = LoggerFactory.getLogger(CleanDataTask.class);

	private static Date lastDate = null;
	private static long maxEmailNum = -1;

	/**
	 * 删除message相关数据
	 * 
	 * @param accountList
	 */
	public static void dataClean(List<Clickoo_mail_account> accountList) {
		for (Clickoo_mail_account account : accountList) {
			long aid = account.getId();
			logger.info("aid:" + aid);
			int rolelevel = new AccountDao().getMaxRoleLevelByAid(aid);
			logger.info("rolevel:" + rolelevel);
			setCleanPosition(rolelevel, aid);
			logger.info("lastDate:" + lastDate);
			logger.info("maxEmailNum:" + maxEmailNum);
			if (lastDate != null && maxEmailNum != -1) {
				List<Clickoo_message_attachment> attachmentList = new MessageDao()
						.getAllAttachmentByProc(String.valueOf(aid),
								lastDate.toString(),
								String.valueOf(maxEmailNum), "1");
				removeAttachmentFromNFS(attachmentList);
				logger.info("[CleanDataTask]----------------"
						+ account.getName());

				List<Clickoo_send_message> sendList = new SendMessageDao()
						.getAllSendMessageByProc(lastDate.toString(),
								String.valueOf(aid));
				removeSendEmlFromNFS(sendList);

			}
		}
	}

	/**
	 * 获得当前时间一周前时间
	 */
	public static String getOneHoursAgoTime() {
		String oneHoursAgoTime = "";
		Calendar cal = Calendar.getInstance();
		long currentMills = cal.getTimeInMillis() - (7 * 24 * 60 * 60 * 1000);
		oneHoursAgoTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(currentMills);
		return oneHoursAgoTime;
	}

	/**
	 * 获得邮件过期参数
	 * 
	 * @param userList
	 */
	public static void setCleanPosition(int rolelevel, long aid) {
		if (rolelevel == -1) {
			lastDate = new Date();
			maxEmailNum = 0;
		} else {
			lastDate = new Date();
			Clickoo_role role = RoleCacheManager.getRoleByLevel(rolelevel);
			String function = role.getFunction();
			String emailNum = JsonUtil.getInstance().parserFunction(function)
					.getLatestEmailNumber();
			if ("OD".equals(emailNum)) {
				maxEmailNum = Long.parseLong(emailNum);
			} else {
				maxEmailNum = 50;
			}
			String saveDay = JsonUtil.getInstance().parserFunction(function)
					.getAutoCleanupPeriod();
			if (!"NEVER".equals(saveDay)) {
				lastDate = new Date(lastDate.getTime() - 24 * 3600 * 1000
						* Long.parseLong(saveDay));
				logger.info("lastdate:" + lastDate);
			} else {
				lastDate = null;
				logger.info("auto clean period is nevel...");
			}
		}
	}

	/**
	 * 将附件从NFS删除
	 * 
	 * @param userList
	 */
	public static void removeAttachmentFromNFS(
			List<Clickoo_message_attachment> attachmentList) {
		for (Clickoo_message_attachment attachment : attachmentList) {
			String nfsPath = VolumeCacheManager.getVolumePojoById(
					attachment.getVolume_id()).getPath();
			String path = nfsPath + attachment.getPath();
			logger.info("trying to delete for path:" + path);
			File file = new File(path);
			if (file.exists()) {
				if (!file.delete()) {
					logger.warn("fail to delete file");
				}
			} else {
				logger.warn("file not exist");
			}
		}
	}

	/**
	 * 将发送邮件的eml文件从NFS删除
	 * 
	 * @param userList
	 */
	public static void removeSendEmlFromNFS(List<Clickoo_send_message> sendList) {
		for (Clickoo_send_message clickoo_send_message : sendList) {
			String nfsPath = VolumeCacheManager.getVolumePojoById(
					clickoo_send_message.getVolumeid()).getPath();
			String path = new StringBuffer(nfsPath)
					.append(clickoo_send_message.getPath())
					.append(File.separator)
					.append(clickoo_send_message.getId()).append(".eml")
					.toString();
			logger.info("trying to delete for path:" + path);
			File file = new File(path);
			if (file.exists()) {
				if (!file.delete()) {
					logger.warn("fail to delete file");
				}
			} else {
				logger.warn("file not exist");
			}
		}
	}
	
	/**
	 *  删除所有状态为待删除的message相关数据包括data、attachment表及NFS上的附件.
	 */
	public static void cleanDeleteStatusMessages(){
		MessageDao messageDao = new MessageDao();
		//删除所有要删除的messages对应NFS上的附件
		List<Clickoo_message_attachment> attList = messageDao
				.getToDeleteAttachments(Constant.MESSAGE_DELETE_STATUS);
		removeAttachmentFromNFS(attList);
		//删除message、data、attachment对应数据
		messageDao.deleteToDeleteMessages(Constant.MESSAGE_DELETE_STATUS);
	}
}
