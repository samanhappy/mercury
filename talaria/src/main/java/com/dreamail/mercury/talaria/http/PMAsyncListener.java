package com.dreamail.mercury.talaria.http;

import java.io.IOException;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.talaria.xstream.PushMailObject;
import com.dreamail.mercury.talaria.xstream.UPEParserObject;
import com.dreamail.mercury.talaria.xstream.XStreamParser;
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
		String uid = getUidFromContext(e);
		String timestamp = String.valueOf(System.currentTimeMillis());
		UPEParserObject upeObj = new UPEParserObject();
		upeObj.setPushMail(new PushMailObject());
		String IMEI = getIMEIFromContext(e);
		upeObj.setIMEI(IMEI);
		upeObj.getPushMail().setUid(uid);
		upeObj.getPushMail().setTimestamp(timestamp);
		upeObj.getPushMail().setState(UPEConstants.UPE_COMMON_STATE);
		String xml = XStreamParser.UPEObject2Xml(upeObj);
		ResponseSender.response2Client((HttpServletResponse) e
				.getAsyncContext().getResponse(), xml);

		removeContext(e);
	}

	public void removeContext(AsyncEvent e) {
		String IMEI = getIMEIFromContext(e);
		if (IMEI != null) {
			AsyncContextManager.getInstance().removeContext(IMEI);
		}

		try {
			e.getAsyncContext().getResponse().getOutputStream().close();
		} catch (Exception e1) {
			logger.error("[" + IMEI + "]close connection err!", e1);
		}

		if (e.getAsyncContext().getRequest().isAsyncStarted()) {
			e.getAsyncContext().complete();
		}

		if (e.getAsyncContext().getRequest().isAsyncStarted()) {
			e.getAsyncContext().dispatch();
		}
	}

	/**
	 * 获得IMEI.
	 * 
	 * @param e
	 * @return
	 */
	private String getIMEIFromContext(AsyncEvent e) {
		String IMEI = (String) e.getAsyncContext().getRequest().getAttribute(
				UPEConstants.IMEI_KEY);
		return IMEI;
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

}
