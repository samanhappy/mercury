package com.dreamail.mercury.jakarta.web;

import java.io.IOException;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.jakarta.xstream.XStreamParser;
import com.dreamail.mercury.util.CAGConstants;
import com.dreamail.mercury.util.UPEConstants;

public class PMAsyncListener implements AsyncListener {
	public static final Logger logger = LoggerFactory
			.getLogger(PMAsyncListener.class);

	@Override
	public void onComplete(AsyncEvent e) throws IOException {
		removeContext(e);
	}

	@Override
	public void onError(AsyncEvent e) throws IOException {
		removeContext(e);
	}

	@Override
	public void onStartAsync(AsyncEvent e) throws IOException {
	}

	@Override
	public void onTimeout(AsyncEvent e) {
		CAGParserObject CAGObject = new CAGParserObject();
		CAGObject.setUuid(getUidFromContext(e));
		CAGObject.setNotification(getNotificationFromContext(e));
		CAGObject.setCode(CAGConstants.CAG_CLIENT_UNREACHABLE_CODE);
		CAGObject.setStatus("Client Unreachable");
		ResponseSender.response2Client(null, (HttpServletResponse) e
				.getAsyncContext().getResponse(), XStreamParser
				.CAGObject2Xml(CAGObject));
		removeContext(e);
	}

	public void removeContext(AsyncEvent e) {

		String uid = getUidFromContext(e);
		if (uid != null) {
			AsyncContextManager.getInstance().removeCAGContext(uid);
		}
		
		try {
			e.getAsyncContext().getResponse().getOutputStream().close();
		} catch (Exception e1) {
			logger.error("[" + uid + "]close connection err!", e1);
		}
		
		if (e.getAsyncContext().getRequest().isAsyncStarted()) {
			e.getAsyncContext().complete();
			logger.info("invoke complete method...");
		}
		
		if (e.getAsyncContext().getRequest().isAsyncStarted()) {
			e.getAsyncContext().dispatch();
			logger.info("invoke dispatch method...");
		}
		
		
	}

	/**
	 * 获得uid.
	 * 
	 * @param e
	 * @return
	 */
	private String getUidFromContext(AsyncEvent e) {
		String IMEI = (String) e.getAsyncContext().getRequest().getAttribute(
				UPEConstants.UID_KEY);
		return IMEI;
	}

	/**
	 * 获得notification.
	 * 
	 * @param e
	 * @return
	 */
	private String getNotificationFromContext(AsyncEvent e) {
		String type = (String) e.getAsyncContext().getRequest().getAttribute(
				UPEConstants.NOTIFICATION_KEY);
		return type;
	}

}
