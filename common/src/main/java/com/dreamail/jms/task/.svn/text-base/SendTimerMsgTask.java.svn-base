package com.dreamail.jms.task;

import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.JmsSender;

public class SendTimerMsgTask extends TimerTask{
	public static final Logger logger = LoggerFactory.getLogger(SendTimerMsgTask.class);
	
	@Override
	public void run() {
		Object obj = null;
		String target = "";
		String currentKey = "";
		String targetType = "";
		String[] targetSplit = null;
		Map<String, Object> failureMsg = JmsSender.failureMsg;
		if(failureMsg != null && failureMsg.size() > 0){
			Entry<String, Object> entry = null;
			Iterator<Entry<String, Object>> iterator = failureMsg.entrySet().iterator();
			while(iterator.hasNext()){
				entry = iterator.next();
				currentKey = entry.getKey();
				obj = entry.getValue();
				targetSplit = currentKey.split("-");
				targetType = targetSplit[0];
				target = targetSplit[2];
				try {
					JmsSender.sent(target, obj, targetType);
				} catch (Exception e) {
					logger.info("hashmap jms message send fail");
				}
			}
		}
	}
	
}
