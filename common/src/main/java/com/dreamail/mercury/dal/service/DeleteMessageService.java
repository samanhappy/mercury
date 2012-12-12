package com.dreamail.mercury.dal.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.DeleteMessageDao;
import com.dreamail.mercury.pojo.Clickoo_delete_message;

public class DeleteMessageService {
	private static Logger logger = LoggerFactory
	.getLogger(DeleteMessageService.class);
	
	/**
	 * 向数据库添加Clickoo_delete_message.
	 * @param deMessage
	 * @return long
	 */
	public long saveDeleteMessage(Clickoo_delete_message deMessage) {
		if(deMessage==null){
			logger.warn("deMessage is null.");
			return -1;
		}
		return new DeleteMessageDao().saveDeleteMessage(deMessage);
	}
	
	/**
	 * 根据账号名查出所有Clickoo_delete_message.
	 * @param accname
	 * @return List<Clickoo_delete_message>
	 */
	public List<Clickoo_delete_message> selectDeleteMessageByAccname(String accname) {
		if(accname == null && "".equals(accname)){
			logger.warn("accname is null.");
			return null;
		}
		return new DeleteMessageDao().selectDeleteMessageByAccname(accname);
	}
	
	/**
	 * 根据id数组删除邮件.
	 * 
	 * @param ids
	 *            message id
	 * @return boolean
	 */
	public boolean deleteDMessageByDids(Long[] dids) {
		return new DeleteMessageDao().deleteDMessageByDids(dids);
	}
	
	/**
	 * 查出所有删除失败的Clickoo_delete_message.
	 * @param num 
	 * @return List<Clickoo_delete_message>
	 */
	public List<String> getDeleteMessages(int num){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) -num);
		String intime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
		return new DeleteMessageDao().getDeleteMessages(intime);
	}
	
	public static void main(String[] args) {
		Clickoo_delete_message deMessage = new Clickoo_delete_message();
		deMessage.setAccname("a");
		deMessage.setIntime(new Date());
		deMessage.setMid("6");
		deMessage.setUuid("2");
//		System.out.println(new DeleteMessageService().saveDeleteMessage(deMessage));
//		System.out.println(new DeleteMessageService().deleteDMessageByDids(new Long[]{1l,2l,3l}));
//		System.out.println(new DeleteMessageService().getDeleteMessages(0));
		System.out.println(new DeleteMessageService().selectDeleteMessageByAccname("a"));
	}
}
