package com.taskfactory.pressure;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import com.dreamail.data.AccountMessage;
import com.dreamail.jms.SendMessageThread;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.util.JMSConstans;

public class Test {

	
	public void test() {
		
		String[] args = new String[1];
		
		new PropertiesDeploy().init();
		
		ConcurrentHashMap<String, AccountMessage> aidMap = new ConcurrentHashMap<String, AccountMessage>();
		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			AccountMessage am = new AccountMessage(3, 0);
			aidMap.put(String.valueOf(i), am);
		}
		
		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			AccountMessage am = new AccountMessage(3, 3);
			aidMap.put(String.valueOf(args[0] + i), am);
		}
		
		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			AccountMessage am = new AccountMessage(3, 4);
			aidMap.put(String.valueOf(args[0] + args[0] + i), am);
		}
		
		Iterator<String> itor = aidMap.keySet().iterator();
		JSONObject allMessages = new JSONObject();
		while (itor.hasNext()) {
			String key = itor.next();
			AccountMessage am = aidMap.get(key);
				JSONObject json = new JSONObject();
				json.put(JMSConstans.JMS_TYPE_KEY,
						JMSConstans.JMS_RECEIVEMAIL_TYPE);
				json.put(JMSConstans.JMS_AID_KEY, key);
				json.put(JMSConstans.JMS_INTERVAL_KEY, String
						.valueOf("1"));
				json.put(JMSConstans.JMS_ACCOUNT_TYPE_KEY, am.getType());
				allMessages.accumulate(String.valueOf(am.getType()), json
						.toString());
		}

		@SuppressWarnings("unchecked")
		Iterator<String> it = allMessages.keys();
		while (it.hasNext()) {
			String key = it.next();
			Object value = allMessages.get(key);
			new SendMessageThread(key, value).start();
		}
	}
}
