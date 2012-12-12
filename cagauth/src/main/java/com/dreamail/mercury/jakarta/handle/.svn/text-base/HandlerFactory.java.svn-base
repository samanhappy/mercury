package com.dreamail.mercury.jakarta.handle;

import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.util.CAGConstants;

public class HandlerFactory {

	public static INotificationHandler getHandler(CAGParserObject CAGObject) {
		INotificationHandler handler = null;
		String notification = CAGObject.getNotification();
		if (notification
				.equalsIgnoreCase(CAGConstants.CAG_GETCLIENTCONFIG_NOTIF)) {
			handler = new GetClientConfigNoticeHandler(CAGObject);
		} else if (notification
				.equalsIgnoreCase(CAGConstants.CAG_UPDATECONFIG_NOTIF)) {
			handler = new UpdateConfigNoticeHandler(CAGObject);
		} else if (notification
				.equalsIgnoreCase(CAGConstants.CAG_GETSERVERCONFIG_NOTIF)) {
			handler = new GetServerConfigNoticeHandler(CAGObject);
		} else if (notification
				.equalsIgnoreCase(CAGConstants.CAG_SETSERVERCONFIG_NOTIF)) {
			handler = new SetServerConfigNoticeHandler(CAGObject);
		} else if (notification
				.equalsIgnoreCase(CAGConstants.CAG_SERVICEAUTHENTICATION_NOTIF)) {
			handler = new ServiceAuthenticationNoticeHandler(CAGObject);
		} else {
			handler = new ErrorNoticeHandler(CAGObject);
		}
		return handler;
	}
}
