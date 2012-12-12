package com.dreamail.mercury.dal.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.DownloadMessageDao;
import com.dreamail.mercury.pojo.Clickoo_download_message;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.IdMap;

public class DownloadMessageService {
	private static Logger logger = LoggerFactory
			.getLogger(DownloadMessageService.class);

	public long saveDownloadMessage(Clickoo_download_message doMessage) {
		return new DownloadMessageDao().saveDownloadMessage(doMessage);
	}

	public long saveDownloadingMessage(Clickoo_download_message doMessage) {
		if (doMessage != null
				&& new DownloadMessageDao().selectDownloadMessageByUid(
						doMessage.getUid(), doMessage.getMid()) == null) {
			return new DownloadMessageDao().saveDownloadMessage(doMessage);
		}

		if (doMessage == null) {
			logger.info("[uid:" + null + "] }[mid:" + null
					+ "] has already in db.");
		} else {
			logger.info("[uid:" + doMessage.getUid() + "] }[mid:"
					+ doMessage.getMid() + "] has already in db.");
		}
		return -1;
	}

	/**
	 * 根据id数组删除邮件.
	 * 
	 * @param ids
	 *            message id
	 * @return boolean
	 */
	public boolean deleteDownloadMessageByDids(long uid, Long[] dids) {
		if (dids != null && dids.length > 0) {
			IdMap idMap = new IdMap();
			idMap.setUid(uid);
			idMap.setMid(dids);
			return new DownloadMessageDao().deleteDownloadMessageByDids(idMap);
		}
		return false;
	}

	/**
	 * 得到指定uid对应的状态为0邮件.
	 * 
	 * @param uid
	 * @return List<String>
	 */
	public List<Clickoo_message> getDownloadedMessages(long uid) {
		return new DownloadMessageDao().getDownloadedMessages(uid);
	}

	/**
	 * 根据mid和status更新Clickoo_download_message的状态为0或1.
	 * 
	 * @param mids
	 * @param status
	 * @return boolean
	 */
	public boolean updateDownloadMessageStatus(Long mids[], int status) {
		if (mids == null || mids.length == 0) {
			return false;
		}
		if (status == 0) {
			logger.info("updateDownloadMessageStatus  status to 0.");
			return new DownloadMessageDao().updateDownloadMessageStatus(mids);
		} else if (status == 1) {
			logger.info("updateDownloadMessageStatus  status to 1.");
			return new DownloadMessageDao().updateDownloadedMessageStatus(mids);
		}
		// else if(status==2){
		// logger.info("updateDownloadFailMessageStatus"+mids+"  status to 2.");
		// return new
		// DownloadMessageDao().updateDownloadFailMessageStatus(mids);
		// }
		logger.info("updateDownloadMessageStatus with error status.");
		return false;
	}

	/**
	 * 获得所有需要重新下载的mid.
	 * 
	 * @return List<String>
	 */
	public List<String> getDownloadMessages(int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - num);
		String intime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal
				.getTime());
		return new DownloadMessageDao().getDownloadMessages(intime);
	}

	/**
	 * 根据mid查询IMEI.
	 * 
	 * @param mid
	 * @return List<String>
	 */
	public List<String> selectIMEIByMid(Long mid) {
		return new DownloadMessageDao().selectIMEIByMid(mid);
	}

	/**
	 * 根据uid删除邮件.
	 * 
	 * @param uid
	 * 
	 * @return boolean
	 */
	public boolean deleteDownloadMessageByUid(String uid) {
		return new DownloadMessageDao().deleteDownloadMessageByUid(uid);
	}

	/**
	 * 根据主键id数组删除邮件.
	 * 
	 * @param ids
	 *            message id
	 * @return boolean
	 */
	public boolean deleteDownloadMessageByids(Long ids[]) {
		return new DownloadMessageDao().deleteDownloadMessageByids(ids);
	}

	public static void main(String[] args) {
		Clickoo_download_message doMessage = new Clickoo_download_message();
		doMessage.setAid(2);
		doMessage.setUid(3);
		doMessage.setMid(7);
		doMessage.setIntime(new Date());
		doMessage.setStatus(0);
		// System.out.println(new
		// DownloadMessageService().saveDownloadingMessage(doMessage));
		// System.out.println(new
		// DownloadMessageService().deleteDownloadMessageByDids(1l, new
		// Long[]{1l}));
		// System.out.println(new
		// DownloadMessageService().updateDownloadMessageStatus(new
		// Long[]{5l,6l,7l}, 0));
		// System.out.println(new
		// DownloadMessageService().getDownloadedMessages(3l).get(0).getUuid());
		// System.out.println(new
		// DownloadMessageService().getDownloadMessages(0));
		// System.out.println(new DownloadMessageService().selectIMEIByMid(4l));
		System.out.println(new DownloadMessageService()
				.deleteDownloadMessageByids(new Long[] { 70l }));
	}
}
