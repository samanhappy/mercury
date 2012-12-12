package com.dreamail.mercury.talaria.timegetmail;

import java.util.Calendar;
import java.util.Date;

import net.sf.json.JSONObject;

/**
 *  定时收取邮件
 * @author 001211
 */
public class TimeGetMail {
	/*JSON数据格式：{"MonStart" : "" , "MonEnd" : "", "TuesStart" : "","TuesEnd" : "","WedStart" : "","WedEnd" : "",
		 "ThurStart" : "","ThurEnd" : "","FriStart" : "","FriEnd" : "","SatStart" : "",
		 "StaEnd" : "","SunStart" : "","SunEnd" : "" }
		 如果值为空则所有时间段都可以收邮件，如果值不为空就在制定时间内收邮件
	 * */
	public void testTGM(String jsonString){
		String[] weeks = {"MonStart","MonEnd","TuesStart","","WedStart","WedEnd","ThurStart","ThurEnd","FriStart","FriEnd","SatStart","StaEnd","SunStart","SunEnd"};
		for (int i = 0; i < weeks.length; i++) {
			//System.out.println(weeks[i]+":"+JsonUtil.getJSONValueByKey(jsonString, weeks[i]));
//			System.out.println(i+":"+JsonUtil.getJSONValueByKey(jsonString, weeks[i]));
		}
		
	}
	public boolean testTGM1(Date date){
		boolean bl = false;
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
//		int i = cal.get(Calendar.DAY_OF_WEEK)-1;
		//String[] weeks = {"Mon","Tues","Wed","Thur","Fri","Sat","Sun"};
		//String jsonString = "{'1':{'StartHour':'','EndHour':''},'2':{'StartHour':'','EndHour':''},'3':{'StartHour':'','EndHour':''},'4':{'StartHour':'','EndHour':''},'5':{'StartHour':'','EndHour':''},'6':{'StartHour':'','EndHour':''},'7':{'StartHour':'','EndHour':''}}";
		String jsonString = "{limittimes:[{'weekday'：'2','StartHour':'4','EndHour':'5'},{'weekday'：'4','StartHour':'22','EndHour':'3'},{'weekday'：'5','StartHour':'8','EndHour':'16'}]}";
		JSONObject tempJson = null;
		tempJson = JSONObject.fromObject(jsonString);
		System.out.println(tempJson);
		//json.get( String.valueOf(i));
		//System.out.println(json.getJSONArray( String.valueOf(i)));
		
		
		
		return bl;
	}
	
	public static void main(String[] args) {
		TimeGetMail tgm = new TimeGetMail();
		Date tempDate = new Date();
		tgm.testTGM1(tempDate);
	}
	
}
