package com.dreamail.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.dreamail.config.TaskProperties;
import com.dreamail.mercury.dal.dao.SendXmlDao;

public class ListenerMemoryData{
	
	/**
	 * 定时清理内存表
	 */
	public void start(){
		Timer timer = new Timer(false);
		String strInterval = TaskProperties.getConfigureMap().get("clean_memary_data_time");
		long interval = Integer.parseInt(strInterval);
		timer.schedule(new MemoryDataListener(), 0, interval*60*1000);
	}
	
    //内部类，用于监控内存表数据
	public static class MemoryDataListener extends TimerTask {
		@Override
		public void run() {
			SendXmlDao sendXmlDao = new SendXmlDao();
			sendXmlDao.deleteSendXmlByTime(getOneHoursAgoTime());
		}
	}
	
	/**
	 *获得当前时间的前一小时
	 */
	public static String getOneHoursAgoTime() {
        String oneHoursAgoTime = "";
        Calendar cal = Calendar.getInstance();
        long currentMills = cal.getTimeInMillis() - (60 * 60 * 1000);
        oneHoursAgoTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(currentMills);
        return oneHoursAgoTime;
    }
}
