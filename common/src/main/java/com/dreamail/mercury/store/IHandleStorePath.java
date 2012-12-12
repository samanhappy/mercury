package com.dreamail.mercury.store;

import com.dreamail.mercury.pojo.Clickoo_message_attachment;

public interface IHandleStorePath {
	Clickoo_message_attachment getAttachmentById(Long attaID,String uid,String mid);
}
