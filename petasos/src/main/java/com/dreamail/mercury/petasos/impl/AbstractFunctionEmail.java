package com.dreamail.mercury.petasos.impl;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.TrunOn;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailattachment;
import com.dreamail.mercury.domain.WebEmailbody;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.mail.receiver.DLEmailException;
import com.dreamail.mercury.mail.receiver.attachment.picture.IPictureHandle;
import com.dreamail.mercury.mail.receiver.attachment.picture.ScaleByJMagick;
import com.dreamail.mercury.mail.receiver.attachment.picture.impl.PictureHandleImpl;
import com.dreamail.mercury.petasos.IEmailController;
import com.dreamail.mercury.pojo.Clickoo_role;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.pojo.User_role;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.StreamUtil;

public abstract class AbstractFunctionEmail implements IEmailController {

	private static Logger logger = LoggerFactory
			.getLogger(AbstractFunctionEmail.class);

	/**
	 * 校验用户id
	 * 
	 * @param uid
	 * @return boolean
	 */
	public boolean validateUser(String uid) {
		UserService userService = new UserService();
		boolean flag = true;
		if (null == uid || uid.equalsIgnoreCase("null") || "".equals(uid)) {
			flag = false;
		} else {
			boolean exist = userService.isExist(Long.valueOf(uid));
			if (!exist) {
				logger.info("Does not exist UID: " + uid);
				flag = false;
			}
			logger.info("Find this UID:" + uid);
		}
		return flag;
	}

	public boolean validateUserIMEI(User_role ur, String IMEI) {
		boolean flag = false;
		if (ur != null) {
			Clickoo_user user = ur.getUser();
			if (user != null) {
				if (IMEI.equalsIgnoreCase(user.getIMEI())) {
					logger.info("Find this UID:" + user.getUid());
					flag = true;
				} else {
					logger.info("user changer IMEI");
					flag = false;
				}
			}
		} else {
			logger.info("user is null");
		}
		return flag;
	}

	/**
	 * EmailContetnt等验证专用（在第一包调用）
	 * 
	 * @param uid
	 * @param IMEI
	 * @param status
	 * @return
	 */
	public boolean validateUserIMEI(String uid, String IMEI, Status status) {
		UserDao userDao = new UserDao();
		boolean flag = false;
		if (null == uid || uid.equals("null") || "".equals(uid)) {
			status.setCode(ErrorCode.CODE_ReceiveEmail_);
			status.setMessage("Does not provide access to e-mail users");
		} else {
			Clickoo_user user = userDao.getUserById(Long.parseLong(uid));
			if (user != null) {
				if (!IMEI.equalsIgnoreCase(user.getIMEI())) {
					status.setCode(ErrorCode.CODE_USER_IMEI_FAIL);
					status.setMessage("the user has been offline");
				} else {
					flag = true;
				}
			} else {
				status.setCode(ErrorCode.CODE_TransferAccount_NoUUID);
				status.setMessage("This user does not exist!");
			}
		}
		return flag;
	}

	public byte[] readAttachmentPic(String id, String uid, String path,
			Status status) {
		IPictureHandle picHandle = new PictureHandleImpl();
		String model = new UserDao().getDeviceModelByUid(uid);
		double[] size = new double[2];
		byte[] pic = null;
		if (model != null) {
			size[0] = Double.parseDouble(model.split(",")[0]);
			size[1] = Double.parseDouble(model.split(",")[1]);
			try {
				pic = picHandle.handelPicutre(path, size, 1);
			} catch (Exception e) {
				logger.error("can't find file when attachment'id is " + id, e);
				status.setCode(ErrorCode.CODE_ReceiveEmail_NoAtt);
				status.setMessage("no file...");
			}
		} else {
			logger.warn("no device opposite attachmentId:" + id);
			status.setCode(ErrorCode.CODE_ReceiveEmail_NoAtt);
			status.setMessage("attachment id is erro...");
		}
		return pic;
	}

	public TrunOn readAttachmentPic(String id, String uid, String path,
			Status status, int seqNo) {
		if (seqNo == 1) {
			String model = new UserDao().getDeviceModelByUid(uid);
			double[] size = new double[2];
			if (model != null) {
				size[0] = Double.parseDouble(model.split(",")[0]);
				size[1] = Double.parseDouble(model.split(",")[1]);
				String treated = File.separator + "Treated" + File.separator;
				// 获取处理后图片
				if (path.contains(treated)) {
					String currentS = path.replace(File.separator, "@#");
					String[] filebits = currentS.split("@#");
					if (filebits.length - 3 > 0) {
						String oldDeviceCode = filebits[filebits.length - 3];
						// 宽 高
						double oldDeviceHeight = 0;
						double oldDeviceWidth = 0;
						if (oldDeviceCode.contains(",")) {
							String[] hw = oldDeviceCode.split(",");
							oldDeviceWidth = Double.parseDouble(hw[0]);
							oldDeviceHeight = Double.parseDouble(hw[1]);
							logger.info("----------------------------------");
							logger.info(size[0] + "-----" + oldDeviceWidth
									+ "--|||--" + size[1] + "-----"
									+ oldDeviceHeight);
							if (size[0] != oldDeviceWidth
									|| size[1] != oldDeviceHeight) {
								logger.info(" device change........");
								String oldPath = path.replace(File.separator
										+ "Treated", "");
								String[] imagePath = new String[] { oldPath,
										path };
								try {
									ScaleByJMagick.scale(imagePath, size, 1);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						} else {
							logger.info(" There is no device find .........");
						}
					} else {
						logger.info(" There is no image find .........");
					}
				}
			}
		}
		return StreamUtil.getFileTrunOn(seqNo, path);
	}

	public List<Object> getExpet(Status status, String errCode, String message,
			List<Object> objects, WebEmail webEmail) {
		objects.add(webEmail);
		logger.error(message);
		status.setCode(errCode);
		status.setMessage(message);
		objects.add(status);
		return objects;
	}

	public Status getExpet(Status status, String errCode) {
		status.setCode(errCode);
		if (errCode.equals(ErrorCode.CODE_ReceiveEmail_)) {
			status.setMessage("Does not provide access to e-mail users");
		} else if (errCode.equals(ErrorCode.CODE_TransferAccount_NoUUID)) {
			status.setMessage("This user does not exist!");
		} else if (errCode.equals(ErrorCode.CODE_USER_IMEI_FAIL)) {
			status.setMessage("the user has been offline!");
		}
		return status;
	}

	public List<Object> getExpet(DLEmailException ex, WebEmail webEmail,
			WebEmailattachment webAttachment, List<Object> objects,
			Status status) {
		if (ex.getMessage().equalsIgnoreCase(Constant.GET_MAIL_FAIL)) {
			webEmail.setAttach(new WebEmailattachment[] { webAttachment });
			objects.add(webEmail);
			logger.error("message get fail", ex);
			status.setCode(ErrorCode.CODE_ReceiveEmail_NoAtt);
			status.setMessage("message get fail");
			objects.add(status);
			return objects;
		} else if (ex.getMessage().equalsIgnoreCase(Constant.PARSE_MAIL_FAIL)) {
			webEmail.setAttach(new WebEmailattachment[] { webAttachment });
			objects.add(webEmail);
			logger.error("message parse fail", ex);
			status.setCode(ErrorCode.CODE_ReceiveEmail_NoAtt);
			status.setMessage("message parse fail");
			objects.add(status);
			return objects;
		} else if (ex.getMessage().equalsIgnoreCase(Constant.WRONG_STATUS)) {
			webEmail.setAttach(new WebEmailattachment[] { webAttachment });
			objects.add(webEmail);
			logger.error("message isn't Exist", ex);
			status.setCode(ErrorCode.CODE_DownLoadEmail_NoExist);
			status.setMessage("message isn't Exist");
			objects.add(status);
			return objects;
		}
		return objects;
	}

	public List<Object> getExpet(DLEmailException ex, WebEmailbody webBody,
			WebEmail webEmail, String id, List<Object> objects, Status status) {
		if (ex.getMessage().equals(Constant.GET_MAIL_FAIL)) {
			webBody.setBodyid(id);
			webEmail.setBody(webBody);
			objects.add(webEmail);
			logger.error("message get fail", ex);
			status.setCode(ErrorCode.CODE_ReceiveEmail_NoBody);
			status.setMessage("message get fail");
			objects.add(status);
			return objects;
		} else if (ex.getMessage().equals(Constant.PARSE_MAIL_FAIL)) {
			webBody.setBodyid(id);
			webEmail.setBody(webBody);
			objects.add(webEmail);
			logger.error("message parse fail", ex);
			status.setCode(ErrorCode.CODE_ReceiveEmail_NoBody);
			status.setMessage("message parse fail");
			objects.add(status);
			return objects;
		} else if (ex.getMessage().equals(Constant.WRONG_STATUS)) {
			webBody.setBodyid(id);
			webEmail.setBody(webBody);
			objects.add(webEmail);
			logger.error("message isn't Exist", ex);
			status.setCode(ErrorCode.CODE_DownLoadEmail_NoExist);
			status.setMessage("message isn't Exist");
			objects.add(status);
			return objects;
		}
		return objects;
	}

	@Override
	public boolean validateUserRole(User_role ur) {
		boolean flag = true;
		if (ur != null) {
			Clickoo_role role = ur.getRole();
			if (Constant.DISABLE.equalsIgnoreCase(role.getTitle())) {
				flag = false;
			}
		} else {
			flag = false;
		}
		return flag;
	}
}
