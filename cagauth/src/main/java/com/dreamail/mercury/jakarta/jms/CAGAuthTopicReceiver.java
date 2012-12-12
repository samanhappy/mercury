package com.dreamail.mercury.jakarta.jms;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.jakarta.web.AsyncContextManager;
import com.dreamail.mercury.util.JMSConstans;

public class CAGAuthTopicReceiver {

	Logger logger = LoggerFactory.getLogger(CAGAuthTopicReceiver.class);

	public void onMessage(Object msg) {

		// 判断消息类型
		if (!(msg instanceof String)) {
			logger.error("\nmsg instance is " + msg.getClass());
			return;
		}

		String msgStr = (String) msg;
		logger.info("message jms str is:" + msgStr);

		JSONObject json = JSONObject.fromObject(msgStr);
		CAGParserObject obj = new CAGParserObject();
		obj.setUuid(new UserDao().getUserNameById(Long.valueOf((json
				.getString(JMSConstans.JMS_UID_KEY)))));
		obj.setNotification(json.getString(JMSConstans.JMS_TYPE_KEY));
		obj.setCode(json.getString(JMSConstans.CAG_RESPONSE_CODE));
		obj.setStatus(json.getString(JMSConstans.CAG_RESPONSE_STATUS));
		if (json.containsKey(JMSConstans.CAG_SETTINGS_KEY)) {
			obj.setSettingStr(json.getString(JMSConstans.CAG_SETTINGS_KEY));
		}

		AsyncContextManager.getInstance().reponseCAGMessage(obj);

	}

}
