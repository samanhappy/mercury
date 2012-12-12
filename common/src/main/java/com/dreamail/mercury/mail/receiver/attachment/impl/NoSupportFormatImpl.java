package com.dreamail.mercury.mail.receiver.attachment.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;

/**
 * 对不支持的附件处理.
 * @author 000830
 */
public class NoSupportFormatImpl extends AbstractAttachmentFormat {
	/**
	 * 附件不支持类型处理.
	 * @param attachment 原始附件信息
	 * @param map        当前处理附件信息
	 * @return List      处理后附件信息
	 */
	public List<Clickoo_message_attachment> format(Clickoo_message_attachment attachment,
			HashMap<String, String> map){
		List<Clickoo_message_attachment> nList = new ArrayList<Clickoo_message_attachment>();
		HashMap<String, String> attachmentMap =
			ioProces("old",attachment.getIn(),map,"."+attachment.getType());
		attachment.setPath(attachmentMap.get("path")+File.separator + map.get("attName")+"."+attachment.getType());
		attachment.setVolume_id(Long.parseLong(attachmentMap.get("volume_id")));
		//attachment.setLength(Integer.parseInt(attachmentMap.get("length")));
		return nList;
	}

}
