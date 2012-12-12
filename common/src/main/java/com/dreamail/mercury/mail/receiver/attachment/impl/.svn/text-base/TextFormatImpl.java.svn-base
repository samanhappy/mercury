package com.dreamail.mercury.mail.receiver.attachment.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.util.StreamUtil;


/**
 * txt附件处理
 * @author 000830
 * 
 */
public class TextFormatImpl extends AbstractAttachmentFormat {
	/**
	 * txt处理.
	 * @param attachment 原始附件信息
	 * @param map        当前处理附件信息
	 * @return List      处理后附件信息
	 */
	@Override
	public List<Clickoo_message_attachment> format(Clickoo_message_attachment attachment,
			HashMap<String, String> map) {
		List<Clickoo_message_attachment> nList = new ArrayList<Clickoo_message_attachment>();
		String content = StreamUtil.getString(attachment.getIn());
		try {
			attachment.setIn(content.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		handleOriginalFile(attachment, map);
		return nList;
	}
}
