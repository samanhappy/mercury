package com.dreamail.mercury.mail.jms;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.mail.truepush.impl.HotmailTruepush;
import com.dreamail.mercury.mail.util.EmailConstant;
import com.dreamail.mercury.util.JMSConstans;

public class ReceiveStatus {
	public static final Logger logger = LoggerFactory
			.getLogger(ReceiveStatus.class);

	public void onMessage(Object msg) {
		try {
			if (msg instanceof String) {
				JSONObject statusJSON = JSONObject.fromObject(msg);
				String status = statusJSON.getString(JMSConstans.JMS_HEARBEAT_KEY);
				logger.info("client jms receive:"+msg);
				if(EmailConstant.StatusConstant.STATUS_RELOAD.equalsIgnoreCase(status)){
					logger.info("client account clear.............");
					clearMap();
					ReceiveMain.contextMap.clear();//清理掉当前dataNode的所有账号
					
					HotmailTruepush.getInstance().clear();
					
					ReceiveMain.setClientState(EmailConstant.StatusConstant.STATUS_RELOAD);
				}else if(EmailConstant.StatusConstant.STATUS_RUN.equalsIgnoreCase(status)){
					ReceiveMain.setClientState(EmailConstant.StatusConstant.STATUS_RUN);
				}
			} else {
				logger.error("msg class:" + msg.getClass());
			}
		} catch (Exception e) {
			logger.error("Error receiveMSG.", e);
		}
	}
	
	private void clearMap(){
		ConcurrentHashMap<String, Context> contextMap = ReceiveMain.contextMap;
		Set<Entry<String,Context>> set = contextMap.entrySet();
		for(Entry<String,Context> entry:set){
			entry.getValue().setAccount(null);
		}
	}
}
