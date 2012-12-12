package com.dreamail.mercury.talaria.jms;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.MessageSender;
import com.dreamail.mercury.talaria.http.AsyncContextManager;
import com.dreamail.mercury.talaria.http.handler.IMHandler;
import com.dreamail.mercury.talaria.http.handler.IRequestHandler;
import com.dreamail.mercury.talaria.xstream.IMObject;
import com.dreamail.mercury.talaria.xstream.XStreamParser;
import com.dreamail.talaria.memcached.UPECacheManager;
import com.dreamail.talaria.memcached.UPEMapping;

public class IMUPEReceiver {

	private static Logger logger = LoggerFactory.getLogger(IMUPEReceiver.class);

	@SuppressWarnings("unchecked")
	public void onMessage(Object msg) {

		if (!(msg instanceof Map<?, ?>)) {
			logger.error("msg type is error:" + msg);
			return;
		}

		Map<String, String> map = (Map<String, String>) msg;
		if (map.size() != 1) {
			logger.error("map size is not 1");
			return;
		}

		String IMEI = null;
		String xml = null;
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = iter.next();
			IMEI = (String) entry.getKey();
			xml = (String) entry.getValue();
			break;
		}

		logger.info("receive IM xml: " + xml + ",for IMEI:" + IMEI);
		IMObject im = null;
		try {
			im = XStreamParser.xml2IMObject(xml);
		} catch (Exception e) {
			logger.error("failed to parse im xml", e);
			MessageSender.sendIMQueueMessage(IMHandler
					.getIMJmsErrorResponseStr());
			return;
		}

		UPEMapping mapping = UPECacheManager.getMappingObject(IMEI);

		boolean isUserConected = AsyncContextManager.contains(IMEI);
		
		// 判断缓存是否存在
		if (mapping == null && !isUserConected) {
			logger.info("no cache for IMEI:" + IMEI
					+ " exists, send error jms response...");
			im.setError("404");
			MessageSender.sendIMQueueMessage(XStreamParser.IMObject2Xml(im));
			return;
		}

		// 判断IMEI是否挂载在当前主机
		if (isUserConected || (mapping.getMac() != null
				&& mapping.getMac().equals(IRequestHandler.mac))) {
			AsyncContextManager.getInstance().sendIMNotice(IMEI, xml);
		} else {
			logger.info("upe link is not local, do nothing...");
		}
	}
}
