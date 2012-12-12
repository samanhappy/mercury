package com.dreamail.mercury.imap.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.pojo.Clickoo_imap_message;
import com.dreamail.mercury.pojo.Clickoo_message;

public class AbstractProcess extends Clean{
	private static final Logger logger = LoggerFactory.getLogger(StoreProcess.class);
	public String sign;
	
	public static Map<Integer, String> flagsMap = new HashMap<Integer, String>();
	
	static{
		flagsMap.put(1, "\\Seen");
		flagsMap.put(2, "\\Answered");
		flagsMap.put(4, "\\Flagged");
		flagsMap.put(8, "\\Deleted");
		flagsMap.put(16, "\\Draft");
	}
	protected static final String numReg = "^[1-9][0-9]*$";
	protected static final String numsReg = "^[1-9][0-9]*[:][1-9][0-9]*$";
	protected static final String allnumsReg = "^[1-9][0-9]*[:][*]$";
	
	public boolean isLogin(ImapSession session){
		return session.isLogin();
	}
	
	public void setSign(String commandSign){
		if(commandSign!=null &&!"".equals(commandSign)){
			this.sign = commandSign;
		}else{
			this.sign = "*";
		}
	}
	
	/**
	 * 给数据库加或减标记
	 * @param aid
	 * @param uuid
	 * @param type
	 * @param flagsMap
	 * @param tag   1：添加   0：删除
	 * @return
	 */
	public HashMap<String,Integer> setFlags(long aid,List<String> uuid,int type,int tag){
		logger.info("start update...............");
		HashMap<String,Integer> flagsCountMap= new HashMap<String,Integer>();
		MessageDao md = new MessageDao();
		Clickoo_message cm = new Clickoo_message();
		for(String uuidSingle : uuid){
			String checkFlags = md.getFlagsByUuid(((Long)aid).toString(), uuidSingle);
			if (checkFlags == null) {
				checkFlags = "0";
			}
			int oldFlags = Integer.parseInt(checkFlags);
			int countFlags=oldFlags;
			if(tag ==1){
				countFlags = type| oldFlags;
			}else if(tag ==0){
				countFlags = oldFlags- type & oldFlags;
			}
			flagsCountMap.put(uuidSingle, countFlags);
			cm.setAid(((Long)aid).toString());
			cm.setUuid(uuidSingle);
			md.updateFlagsByUuid(cm);
			
		}
		
		return flagsCountMap;
	}
	
	/**
	 * 解析输入命令中需要修改的标记
	 * 
	 * @param command
	 * @return
	 */
	public int parseCommand(String command) {
		for (int i = 0; i < 5; i++) {
			if (command.equalsIgnoreCase(flagsMap.get(1 << i))) {
				return (1 << i);
			}
		}
		return 0;
	}
	protected String getSeqNum(List<Clickoo_imap_message> msgList,String uid){
		int seq = -1;
		for(int i=0;i<msgList.size();i++){
			if(uid.equals(msgList.get(i).getUid())){
				seq = i+1;
			}
		}
		return String.valueOf(seq);
	}
	
	protected int getUseenNun(List<Clickoo_imap_message> msgList){
		int i = 0;
		for(Clickoo_imap_message imapMessage:msgList){
			if(imapMessage.getContent()!=null && ((Integer.parseInt(imapMessage.getContent()) & 1) ==0)){
				i++;
			}
		}
		return i;
	}
	/**
	 * 常用时间转换
	 * @param date
	 * @return
	 */
	protected String getTimeByDate(Date date){
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));;
		return new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z", Locale.US).format(date);
	}
}
