package com.dreamail.mercury.util;

import com.dreamail.jms.CacheAccountMessage;
import com.dreamail.jms.Notice;

public class ObjectConverString {
	/**
	 * 任务工厂参数
	 * Notice to String
	 */
	public static String NoticeToString(Notice notice){
		StringBuffer result = new StringBuffer();
		result.append(notice.getMsg()).append(",").append(notice.getIP());
		return result.toString();
	}
	
	/**
	 * 任务工厂参数
	 * String to Notice
	 */
	public static Notice StringToNotice(String msg){
		Notice notice = new Notice();
		String msgs[] = msg.split(",");
		notice.setMsg(msgs[1]);
		notice.setIP(msgs[0]);
		return notice;
	}
	
//	/**
//	 * 任务工厂 下发任务
//	 * TaskMessage to String
//	 */
//	public static String TaskMessageToString(TaskMessage taskMessage){
//		StringBuffer result = new StringBuffer();
//		result.append(taskMessage.getAid()).append(",")
//			  .append(taskMessage.getIntervaltimer()).append(",")
//			  .append(taskMessage.getMsg());
//		return result.toString();
//	}
	
//	/**
//	 * 任务工厂 下发任务
//	 * String to TaskMessage
//	 */
//	public static TaskMessage StringToTaskMessage(String msg){
//		TaskMessage taskMessage = new TaskMessage();
//		String msgs[] = msg.split(",");
//		taskMessage.setAid(Long.parseLong(msgs[0]));
//		taskMessage.setIntervaltimer(Long.parseLong(msgs[1]));
//		taskMessage.setMsg(msgs[2]);
//		return taskMessage;
//	}
	
	/**
	 * state aid  夕陽芳草本無恨,才子佳人空自悲
	 * CacheAccountMessage to String  

	 */
	public static String CacheAccountMessageToString(CacheAccountMessage cacheAccountMessage){
		StringBuffer result = new StringBuffer();
		result.append(cacheAccountMessage.getState()).append(",")
			  .append(cacheAccountMessage.getAid());
		return result.toString();
	}
	
	/**
	 * String to CacheAccountMessage
	 */
	public static CacheAccountMessage StringToCacheAccountMessage(String msg){
		CacheAccountMessage cacheAccountMessage = new CacheAccountMessage();
		String msgs[] = msg.split(",");
		cacheAccountMessage.setState(msgs[0]);
		cacheAccountMessage.setAid(msgs[1]);
		return cacheAccountMessage;
	}
}
