package com.dreamail.mercury.mail.receiver.attachment.picture.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.mail.receiver.attachment.picture.IPictureHandle;
import com.dreamail.mercury.mail.receiver.attachment.picture.ScaleByJMagick;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;

public class PictureHandleImpl extends ScaleByJMagick implements IPictureHandle{

	public static final Logger logger = 
		LoggerFactory.getLogger(PictureHandleImpl.class);

	
	/**
	 * 处理图片
	 * @param attachment 附件对象
	 * @param map  附件信息
	 * @param type 图片类型
	 * @param size 设备大小
	 * @param scale 压缩倍数
	 * @throws Exception
	 */
	@Override
	public Clickoo_message_attachment handelPicutre(Clickoo_message_attachment attachment,
			HashMap<String, String> map, String type, double[] size,
			double scale) {
		Clickoo_message_attachment returnAttach = null;
		returnAttach = scale(attachment,map,size,scale);
		return returnAttach;
	}

	/**
	 * 保存图片信息
	 * @param attachment 附件对象
	 * @param map  附件信息
	 * @throws Exception
	 */
	@Override
	public Clickoo_message_attachment savePicutre(Clickoo_message_attachment attachment,
			HashMap<String, String> map) throws Exception {
		Clickoo_message_attachment nAttachment = new Clickoo_message_attachment();
		nAttachment.setName(attachment.getName());
		nAttachment.setType(attachment.getType());
		nAttachment.setLength(attachment.getLength());
		nAttachment.setPath(attachment.getPath());
		nAttachment.setVolume_id(attachment.getVolume_id());
		return nAttachment;
	}

	@Override
	public byte[] handelPicutre(String path, double[] size, double scale)
			throws Exception {
		byte[] pbyte = null;
		pbyte = scale(path, size, scale);
		return pbyte;
	}

	@Override
	public List<Clickoo_message_attachment> format(Clickoo_message_attachment attachment,
			HashMap<String, String> map){
		return null;
	}

}
