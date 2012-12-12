package com.dreamail.mercury.timerpush.serverstate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.util.SystemMessageUtil;

public class Tools {
	private static final Logger logger = LoggerFactory.getLogger(Tools.class);
	/**
	 * 获得服务器IP地址
	 * @return
	 */
//	public static String getLocalServerIp(){
////		return SystemMessageUtil.getLocalIp();
//		return SystemMessageUtil.getLocalMac();
//	}
	/**
	 * 获得服务器IP地址key
	 * @return
	 */
	public static String getLocalServerIpKey(){
//		return "Timer_"+SystemMessageUtil.getLocalIp();
		return "Timer_"+SystemMessageUtil.getLocalMac();
	}
	/**
	 * 获得服务器时间
	 */
	public static String getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(cal.getTime());
	}
	
	public static int getWeekday(){
		Calendar cal = Calendar.getInstance();
		return (cal.get(Calendar.DAY_OF_WEEK) + 6) % 7;
	}
	
	public static int getNowMinutes(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
	}
	
	public static long getNowSeconds(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR_OF_DAY) * 3600 + cal.get(Calendar.MINUTE) * 60 + cal.get(Calendar.SECOND);
	}
	/**
	 * 计算时间差
	 * 
	 * @param beginTime
	 * @param endTime
	 * @param keepTime
	 * @return true：存活 false：死亡
	 */
	public static boolean isFailState(String beginTime, String endTime,
			long keepTime) {
		boolean flag = true;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate;
		Date endDate;
		try {
			long result;
			beginDate = dateFormat.parse(beginTime);
			endDate = dateFormat.parse(endTime);
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(beginDate);
			c2.setTime(endDate);
			logger.info("------"+beginTime+"------"+endTime);
			result = c2.getTimeInMillis() - c1.getTimeInMillis();
			logger.info("----------time result"+result);
			if (result > keepTime*60*1000) {
				flag = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
