package com.dreamail.mercury.petasos.impl.email;

import java.util.HashMap;
import java.util.List;

import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.qwert.Status;

public interface IEmailAttachment {
	/**
	 * 读取文本类型的附件
	 */
	public void readTxTAttachment(WebEmail email, Status status,
			List<Object> objects, HashMap<String, String> meta) ;
	
	/**
	 * 读取图片类型的附件
	 */
	public void readPicAttachment(WebAccount account,WebEmail email,String uid,Status status,List<Object> objects, HashMap<String, String> meta);
	
	/**
	 * 截取文本内容
	 */
	public String subText(byte[] body);
}
