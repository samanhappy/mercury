package com.dreamail.mercury.jakarta.web;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.jakarta.xstream.XStreamParser;
import com.dreamail.mercury.util.CAGConstants;


public class AsyncContextManager {
	public static final Logger logger = LoggerFactory
			.getLogger(AsyncContextManager.class);
	private static final AsyncContextManager acm = new AsyncContextManager();
	private static  ConcurrentHashMap<String, AsyncContext> connectionMap = new ConcurrentHashMap<String, AsyncContext>();

	private AsyncContextManager() {
	}

	public static AsyncContextManager getInstance() {
		if(acm == null){
			return new AsyncContextManager();
		}
		return acm;
	}

	public void putCAGContext(String uid, AsyncContext ac) {
		connectionMap.put(uid, ac);
	}
	
	public AsyncContext getCAGContext(String uid) {
		if (uid != null) {
			return connectionMap.get(uid);
		} else {
			return null;
		}
		
	}

	public void removeCAGContext(String uid) {
		if (connectionMap.containsKey(uid)) {
			connectionMap.remove(uid);
		}
	}
	
	public void reponseCAGMessage(CAGParserObject obj) {
		AsyncContext ac = connectionMap.get(obj.getUuid());
		if (ac != null) {
			logger.info("response CAG notice for uid:" + obj.getUuid());
			String notification = (String) ac.getRequest().getAttribute(CAGConstants.KEY_OF_NOTIFICATION);
			String uid = (String) ac.getRequest().getAttribute(CAGConstants.KEY_OF_UID);
			if (obj.getUuid().equalsIgnoreCase(uid) && obj.getNotification().equalsIgnoreCase(notification)) {
				//如果有设置信息，把设置信息保存用于返回
				if (obj.getSettingStr() != null) {
					obj.setSettings(XStreamParser.xml2CAGSettingsObject(obj.getSettingStr()));
					obj.setSettingStr(null);
				}
				try {
					ResponseSender.response2Client(null, (HttpServletResponse) ac
							.getResponse(), XStreamParser.CAGObject2Xml(obj));
				} catch (Exception e) {
					logger.error("[" + obj.getUuid() + "]sendMessage err!", e);
				} finally {
					ac.complete();
					connectionMap.remove(obj.getUuid());
				}
			} else {
				logger.info("uid in context:" + uid + ",uid in obj:" + obj.getUuid());
				logger.info("notification in context:" + notification + ",notification in obj:" + obj.getNotification());
				logger.info("info in context are not same as in obj, do noting");
			}
		} else {
			logger.info("[" + obj.getUuid()
					+ "] no connection at the moment,do nothing...");

		}
		
	}
}
