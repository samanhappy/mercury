package com.dreamail.mercury.imap.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.xfire.util.Base64;

import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.imap.domain.EmailUser;
import com.dreamail.mercury.imap.domain.FetchData;
import com.dreamail.mercury.pojo.Clickoo_message;

public abstract class AbstractFetchEmail extends AbstractProcess{
	public abstract List<String> getProtocol(List<String> list,ImapSession session,FetchData fetchData,Clickoo_message message);

	public void returnBodystructureForE63(List<String> protocolList,String command,Clickoo_message message){
		if(!(command.toUpperCase().contains("RECEIVED")))
			return;
		String args = StringUtils.substringBetween(command.toUpperCase().split("HEADER.FIELDS")[1], "(", ")").toUpperCase();
		if(args.contains("DATE"))
			protocolList.add("Date: "+getTimeByDate(message.getSendtime()));
		if(args.contains("FROM"))
			protocolList.add("From: "+message.getEmailFrom().split("<")[1].replace(">","").trim());
		if(args.contains("SUBJECT"))
//			protocolList.add("Subject: =?UTF-8?B?aWlpaWk=?=");
			protocolList.add("Subject: "+"=?UTF-8?B?" + Base64.encode(message.getSubject().getBytes()) + "?=");
		if(args.contains("X-PRIORITY"))
			protocolList.add("X-Priority: 3");
	}
	/**
	 * 收件人，抄送人，发件人，密送人名字解析
	 * @param str
	 * @return
	 */
	protected EmailUser getEmailUser(String str){
		EmailUser emailUser = new EmailUser();
//		logger.info("getEmailUser ["+str+"]");
		emailUser.setName(str.split("<")[0].trim());
		emailUser.setEmailname(str.split("<")[1].replace(">","").trim().split("@")[0]);
		emailUser.setEmailtype(str.split("<")[1].replace(">","").trim().split("@")[1]);
//		logger.info(emailUser.toString());
		return emailUser;
	}
	/**
	 * 协议长度处理
	 * @param string
	 * @param length
	 * @return
	 */
	protected  List<String> getStrArray(String string,int length){
		if (string != null && !"".equals(string)) {
			List<String> strArray = new ArrayList<String>();
			if (string.length() <= length) {
				strArray.add(string);
			} else {
				while (string.length() > length) {
					strArray.add(string.substring(0, length));
					string = string.substring(length);
				}
				strArray.add(string);
			}
			return strArray;
		}
		return null;
	}
}
