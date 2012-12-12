package com.dreamail.mercury.mail.receiver.attachment.impl;

import java.util.HashMap;
import java.util.List;

import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;


public class OriginalFileFormatImpl extends AbstractAttachmentFormat{

	@Override
	public List<Clickoo_message_attachment> format(Clickoo_message_attachment attachment,
			HashMap<String, String> map) {
		map.put(attachment.getName(),"attName");
		handleOriginalFile(attachment, map);
		return null;
	}
}
