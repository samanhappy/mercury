package com.dreamail.mercury.mail.receiver.attachment.picture;

import java.io.File;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.store.Volume;
import com.dreamail.mercury.store.Volumes;

public class AttachmentPath {
	private static Logger logger = LoggerFactory
			.getLogger(AttachmentPath.class);

	/**
	 * 获取路径
	 * 
	 * @param map
	 *            附件信息
	 * @param state
	 *            目录标识
	 * @return String 完整路径
	 */
	public static String[] getPath(HashMap<String, String> map) {
		String volumeid = "";
		String filePath = "";
		String size = null;
		String attachmentID = map.get("attachmentID");

		/*
		 * 虽以uid为key，实际上存放的是帐号ID，WTF; 参照AttachmentFormatControl类
		 * map.put("uid",String.valueOf(context.getAccountId()));
		 */
		String aid = map.get("uid");

		// 附件名称
		String attachName = map.get("attName");
		// 附件类型
		String attachType = map.get("type");
		// volume 类型 0. 当前 1.备份 2.INDEX
		String volume_meta_name = map.get("volume_meta_name");
		// 邮件状态 （发送,接收）0： pushMail 1: sendMail
		String mailState = map.get("mailstate");
		// 图片路径 new: 压缩后图片位置 old:其他
		String imageState = map.get("image_state");
		// true 文件创建 false 文件夹创建
		String isFileName = map.get("is_file_name");
		// 全部路径true 附件表路径 false
		boolean pathFlag = Boolean.parseBoolean(map.get("path_flag"));
		boolean isFile = Boolean.parseBoolean(isFileName);
		String mailType = "0".equals(mailState) == true ? "receiveMail"
				: "sendMail";
		// VolumeService volumeService = new VolumeService();
		size = map.get("size");

		Volumes volumeService = new Volumes();
		Volume volume = null;
		if ("0".equals(volume_meta_name)) {
			volume = volumeService
					.getVolumeByMeta(Volumes.MetaEnum.CURRENT_MESSAGE_VOLUME);
		} else if ("1".equals(volume_meta_name)) {
			volume = volumeService
					.getVolumeByMeta(Volumes.MetaEnum.SECONDARY_MESSAGE_VOLUME);
		} else if ("2".equals(volume_meta_name)) {
			volume = volumeService
					.getVolumeByMeta(Volumes.MetaEnum.INDEX_VOLUME);
		} else {
			logger.error("there is no volume data!");
			return null;
		}
		// VolumeCacheObject volume =
		// volumeService.getCurrentVolumeByName(volume_meta_name);
		if (pathFlag) {
			filePath = volume.getPath();
		}
		volumeid = String.valueOf(volume.getId());
		filePath += File.separator + "pushMail" + File.separator
				+ Integer.parseInt(aid) % volume.getFile_bits()
				+ File.separator + aid
				+ File.separator
				+ mailType // +
				// File.separator + accountName //+
				// File.separator + mailName
				+ File.separator + Integer.parseInt(attachmentID)
				% volume.getFile_bits();
		// 图片压缩路径添加 100,50
		if (size != null) {
			filePath = filePath + File.separator + size;
		}
		if (imageState.equalsIgnoreCase("new")) {
			filePath = filePath + File.separator + "Treated";
			if (isFile) {
				filePath = filePath + File.separator + attachName + "."
						+ attachType;
			}
		} else {
			if (isFile) {
				filePath = filePath + File.separator + attachName + "."
						+ attachType;
			}
		}
		File file = new File(filePath);
		String[] resultPath = new String[] { file.getPath(), volumeid };
		return resultPath;
	}
}
