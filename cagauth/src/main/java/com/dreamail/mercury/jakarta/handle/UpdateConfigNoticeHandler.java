package com.dreamail.mercury.jakarta.handle;

import com.dreamail.mercury.cag.CAGParserObject;

public class UpdateConfigNoticeHandler extends INotificationHandler{

	
	protected UpdateConfigNoticeHandler(CAGParserObject CAGObject) {
		super(CAGObject);
	}

	@Override
	public boolean handle() {
		logger.info("handle for GetClientConfig or UpdateConfig notice ...");
		if (!validateUid() || !validateUserLogin()) {
			return true;
		} else {
			// 处理并等待与客户端交互结果，不返回等待请求挂起
			handleNoticeAboutClient();
		}
		return false;
	}
}
