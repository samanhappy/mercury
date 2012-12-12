package com.dreamail.mercury.petasos.impl.email;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dreamail.mercury.domain.EmailCacheObject;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.qwert.Status;

public interface IEmailContent {
	/**
	 * 获取正文附件
	 * @param parseLong
	 * @param uid
	 * @return
	 */
	public String getAttachmentBody(long parseLong, String uid,EmailCacheObject emailCache);
	/**
	 * 分包获取邮件正文
	 * @param account
	 * @param wemails
	 * @param uid
	 * @param status
	 * @param objects
	 * @param meta
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public List<Object> putBodyContent(WebAccount account,ArrayList<WebEmail> wemails, String uid, Status status,List<Object> objects, HashMap<String,String> meta)throws UnsupportedEncodingException ;
}
