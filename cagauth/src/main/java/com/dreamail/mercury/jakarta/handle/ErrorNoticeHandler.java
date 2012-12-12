package com.dreamail.mercury.jakarta.handle;

import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.util.CAGConstants;

public class ErrorNoticeHandler extends INotificationHandler{

	protected ErrorNoticeHandler(CAGParserObject CAGObject) {
		super(CAGObject);
	}

	@Override
	public boolean handle() {
		logger.error("notification type is error.");
		CAGObject.setCode(CAGConstants.CAG_BAD_REQUEST_CODE);
		CAGObject.setStatus("Bad Request");
		return true;
	}
}
