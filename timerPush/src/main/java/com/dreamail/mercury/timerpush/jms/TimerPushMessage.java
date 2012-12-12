package com.dreamail.mercury.timerpush.jms;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.pojo.Clickoo_user_limittimes;
import com.dreamail.mercury.timerpush.cache.ServerCacheMap;
import com.dreamail.mercury.timerpush.domain.Constants;
import com.dreamail.mercury.timerpush.domain.Notice;
import com.dreamail.mercury.timerpush.domain.ServerInfo;
import com.dreamail.mercury.timerpush.server.ServerMaster;
import com.dreamail.mercury.timerpush.server.ServerSecond;
import com.dreamail.mercury.timerpush.serverstate.Tools;
import com.dreamail.mercury.timerpush.timer.TimerOperator;
import com.dreamail.mercury.util.JMSConstans;

public class TimerPushMessage {
	private static Logger logger = LoggerFactory
			.getLogger(TimerPushMessage.class);

	public void onMessage(Object msg) {
		if (msg instanceof Notice) {
			Notice notice = (Notice) msg;
			String message = notice.getMsg();
			logger.info("------------------receive[" + message + "] request ["
					+ notice + "]");
			// 接受寻找master请求通知
			if (Constants.FINDMASTER.equals(message)) {
				logger.info("---------server[" + notice.getIp()
						+ "]start，who is Master?");
				// 监听server，记录server info，设置Second，通知服务器谁是Master
				ServerMaster serverMaster = new ServerMaster();
				serverMaster.handleMsg(notice);
			}
			// 接受master的通知
			else if (Constants.MASTERNOTICE.equals(message)) {
				// second设置
				ServerSecond serverSecond = new ServerSecond();
				serverSecond.handleMsg(notice);
			}
		} else if (msg instanceof String) {
			logger.info("------------------receive[" + msg + "] request ");
			// 接受帐号的定时信息更改，只有Master对消息进行处理
			ServerInfo currentServerInfo = ServerCacheMap.selectTimerServer()
					.get(Tools.getLocalServerIpKey());
			if (currentServerInfo == null
					|| !Constants.T_SERVER_MASTER.equals(currentServerInfo
							.getServerType())) {
				if(currentServerInfo!=null){
					logger.info("Server [" + currentServerInfo.getIp()
							+ "] is not the MasterServer!");
				}
				return;
			}
			JSONObject json = JSONObject.fromObject(msg);
			if (JMSConstans.JMS_TIMERPUSH_TYPE.equals(json
					.get(JMSConstans.JMS_TYPE_KEY))) {
				int offset = json.getInt(JMSConstans.JMS_OFFSET_KEY);
				if (JMSConstans.JMS_ADDLINE_VALUE.equals(json
						.get(JMSConstans.JMS_TIMER_OPERATE_KEY))) {
					Clickoo_user_limittimes timer = new Clickoo_user_limittimes();
					timer.setAid(json.getLong(JMSConstans.JMS_AID_KEY));
					timer.setUid(json.getLong(JMSConstans.JMS_UID_KEY));
					timer.setTimetype(json
							.getInt(JMSConstans.JMS_TIMER_TYPE_KEY));
					timer.setWeekdays(json
							.getString(JMSConstans.JMS_TIMER_WEEKDAYS_KEY));
					timer.setStarthour(json
							.getInt(JMSConstans.JMS_TIMER_STARTHOUR_KEY));
					timer.setStartminute(json
							.getInt(JMSConstans.JMS_TIMER_STARTMINUTE_KEY));
					timer.setEndhour(json
							.getInt(JMSConstans.JMS_TIMER_ENDHOUR_KEY));
					timer.setEndminute(json
							.getInt(JMSConstans.JMS_TIMER_ENDMINUTE_KEY));

					TimerOperator.timerPushUpdate(timer, null, offset);
				} else if (JMSConstans.JMS_MODIFYLINE_VALUE.equals(json
						.get(JMSConstans.JMS_TIMER_OPERATE_KEY))) {
					Clickoo_user_limittimes oldtimer = new Clickoo_user_limittimes();
					oldtimer.setAid(json.getLong(JMSConstans.JMS_AID_KEY));
					oldtimer.setUid(json.getLong(JMSConstans.JMS_UID_KEY));
					oldtimer.setTimetype(json
							.getInt(JMSConstans.JMS_TIMER_TYPE_KEY));
					oldtimer.setWeekdays(json
							.getString(JMSConstans.JMS_TIMER_OLD_WEEKDAYS_KEY));
					oldtimer.setStarthour(json
							.getInt(JMSConstans.JMS_TIMER_OLD_STARTHOUR_KEY));
					oldtimer.setStartminute(json
							.getInt(JMSConstans.JMS_TIMER_OLD_STARTMINUTE_KEY));
					oldtimer.setEndhour(json
							.getInt(JMSConstans.JMS_TIMER_OLD_ENDHOUR_KEY));
					oldtimer.setEndminute(json
							.getInt(JMSConstans.JMS_TIMER_OLD_ENDMINUTE_KEY));

					Clickoo_user_limittimes timer = new Clickoo_user_limittimes();
					timer.setAid(json.getLong(JMSConstans.JMS_AID_KEY));
					timer.setUid(json.getLong(JMSConstans.JMS_UID_KEY));
					timer.setTimetype(json
							.getInt(JMSConstans.JMS_TIMER_TYPE_KEY));
					timer.setWeekdays(json
							.getString(JMSConstans.JMS_TIMER_WEEKDAYS_KEY));
					timer.setStarthour(json
							.getInt(JMSConstans.JMS_TIMER_STARTHOUR_KEY));
					timer.setStartminute(json
							.getInt(JMSConstans.JMS_TIMER_STARTMINUTE_KEY));
					timer.setEndhour(json
							.getInt(JMSConstans.JMS_TIMER_ENDHOUR_KEY));
					timer.setEndminute(json
							.getInt(JMSConstans.JMS_TIMER_ENDMINUTE_KEY));

					TimerOperator.timerPushUpdate(timer, oldtimer, offset);
				} else if (JMSConstans.JMS_DELETELINE_VALUE.equals(json
						.get(JMSConstans.JMS_TIMER_OPERATE_KEY))) {
					Clickoo_user_limittimes oldtimer = new Clickoo_user_limittimes();
					oldtimer.setAid(json.getLong(JMSConstans.JMS_AID_KEY));
					oldtimer.setUid(json.getLong(JMSConstans.JMS_UID_KEY));
					oldtimer.setTimetype(json
							.getInt(JMSConstans.JMS_TIMER_TYPE_KEY));
					oldtimer.setWeekdays(json
							.getString(JMSConstans.JMS_TIMER_OLD_WEEKDAYS_KEY));
					oldtimer.setStarthour(json
							.getInt(JMSConstans.JMS_TIMER_OLD_STARTHOUR_KEY));
					oldtimer.setStartminute(json
							.getInt(JMSConstans.JMS_TIMER_OLD_STARTMINUTE_KEY));
					oldtimer.setEndhour(json
							.getInt(JMSConstans.JMS_TIMER_OLD_ENDHOUR_KEY));
					oldtimer.setEndminute(json
							.getInt(JMSConstans.JMS_TIMER_OLD_ENDMINUTE_KEY));

					TimerOperator.timerPushUpdate(null, oldtimer, offset);
				}
			}
		}
	}
}
