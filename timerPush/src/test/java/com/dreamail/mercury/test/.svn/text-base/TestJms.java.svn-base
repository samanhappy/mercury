package com.dreamail.mercury.test;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.timerpush.serverstate.Tools;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.JMSTypes;

public class TestJms {
	@Test
	public void testSendJmsMessage() {
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMERPUSH_TYPE);
		json.put(JMSConstans.JMS_TIMER_OPERATE_KEY, JMSConstans.JMS_ADDLINE_VALUE);
		json.put(JMSConstans.JMS_AID_KEY, 1);
		json.put(JMSConstans.JMS_UID_KEY, 2);
		json.put(JMSConstans.JMS_TIMER_TYPE_KEY, 1);
		json.put(JMSConstans.JMS_TIMER_WEEKDAYS_KEY, 5);
		json.put(JMSConstans.JMS_TIMER_OLD_STARTHOUR_KEY, 9);
		json.put(JMSConstans.JMS_TIMER_OLD_STARTMINUTE_KEY, 30);
		json.put(JMSConstans.JMS_TIMER_OLD_ENDHOUR_KEY, 15);
		json.put(JMSConstans.JMS_TIMER_OLD_ENDMINUTE_KEY, 28);
		json.put(JMSConstans.JMS_TIMER_STARTHOUR_KEY, 9);
		json.put(JMSConstans.JMS_TIMER_STARTMINUTE_KEY, 30);
		json.put(JMSConstans.JMS_TIMER_ENDHOUR_KEY, 15);
		json.put(JMSConstans.JMS_TIMER_ENDMINUTE_KEY, 28);
		String message = json.toString();
		System.out.println(message);
	}
	@Test
	public void testAddTimer(){
		new PropertiesDeploy().init();
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMERPUSH_TYPE);
		json.put(JMSConstans.JMS_AID_KEY,4);
		json.put(JMSConstans.JMS_UID_KEY,4);
		json.put(JMSConstans.JMS_TIMER_TYPE_KEY,1);
		json.put(JMSConstans.JMS_TIMER_OPERATE_KEY, JMSConstans.JMS_ADDLINE_VALUE);
		json.put(JMSConstans.JMS_TIMER_WEEKDAYS_KEY,"0-1-2-3-4-5-6");
		json.put(JMSConstans.JMS_TIMER_STARTHOUR_KEY,9);
		json.put(JMSConstans.JMS_TIMER_STARTMINUTE_KEY,30);
		json.put(JMSConstans.JMS_TIMER_ENDHOUR_KEY,18);
		json.put(JMSConstans.JMS_TIMER_ENDMINUTE_KEY,59);
		String message = json.toString();
		System.out.println(message);
		JmsSender.sendTopicMsg(message, JMSTypes.CLICKOO_TIMERPUSH_TOPIC);
	}
	@Test
	public void testUpdateTimer(){
		new PropertiesDeploy().init();
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMERPUSH_TYPE);
		json.put(JMSConstans.JMS_AID_KEY,4);
		json.put(JMSConstans.JMS_UID_KEY,4);
		json.put(JMSConstans.JMS_TIMER_TYPE_KEY,1);
		json.put(JMSConstans.JMS_TIMER_OPERATE_KEY, JMSConstans.JMS_MODIFYLINE_VALUE);
		json.put(JMSConstans.JMS_TIMER_OLD_WEEKDAYS_KEY,"0-1-2-3-4-5-6");
		json.put(JMSConstans.JMS_TIMER_OLD_STARTHOUR_KEY,9);
		json.put(JMSConstans.JMS_TIMER_OLD_STARTMINUTE_KEY,30);
		json.put(JMSConstans.JMS_TIMER_OLD_ENDHOUR_KEY,18);
		json.put(JMSConstans.JMS_TIMER_OLD_ENDMINUTE_KEY,59);
		
		json.put(JMSConstans.JMS_TIMER_WEEKDAYS_KEY,"0-1-2-3-4-5-6");
		json.put(JMSConstans.JMS_TIMER_STARTHOUR_KEY,17);
		json.put(JMSConstans.JMS_TIMER_STARTMINUTE_KEY,28);
		json.put(JMSConstans.JMS_TIMER_ENDHOUR_KEY,19);
		json.put(JMSConstans.JMS_TIMER_ENDMINUTE_KEY,38);
		String message = json.toString();
		System.out.println(message);
		JmsSender.sendTopicMsg(message, JMSTypes.CLICKOO_TIMERPUSH_TOPIC);
	}
	@Test
	public void testRemoveTimer(){
		new PropertiesDeploy().init();
		JSONObject json = new JSONObject();
		json.put(JMSConstans.JMS_TYPE_KEY, JMSConstans.JMS_TIMERPUSH_TYPE);
		json.put(JMSConstans.JMS_AID_KEY,4);
		json.put(JMSConstans.JMS_UID_KEY,4);
		json.put(JMSConstans.JMS_TIMER_TYPE_KEY,1);
		json.put(JMSConstans.JMS_TIMER_OPERATE_KEY, JMSConstans.JMS_DELETELINE_VALUE);
		json.put(JMSConstans.JMS_TIMER_OLD_WEEKDAYS_KEY,"0-1-2-3-4-5-6");
		json.put(JMSConstans.JMS_TIMER_OLD_STARTHOUR_KEY,11);
		json.put(JMSConstans.JMS_TIMER_OLD_STARTMINUTE_KEY,28);
		json.put(JMSConstans.JMS_TIMER_OLD_ENDHOUR_KEY,19);
		json.put(JMSConstans.JMS_TIMER_OLD_ENDMINUTE_KEY,38);
		String message = json.toString();
		System.out.println(message);
		JmsSender.sendTopicMsg(message, JMSTypes.CLICKOO_TIMERPUSH_TOPIC);
	}
	@Test
	public void testWeekday(){
		System.out.println(Tools.getWeekday());
	}
}
