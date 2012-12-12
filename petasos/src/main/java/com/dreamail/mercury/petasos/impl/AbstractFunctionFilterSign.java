package com.dreamail.mercury.petasos.impl;

import java.util.HashMap;

import com.dreamail.mercury.domain.WebSenderFilter;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.ErrorCode;

public abstract class AbstractFunctionFilterSign extends AbstractFunctionUser{
	protected static final String SUCESS_CODE = "0";
	protected static final String TARGET_IMEI = "IMEI";
	
	//协议中响应的信息提示.
	
	protected static final String SET_FILETERSIGN_SUCESS = "Set Filtersign Sucess";
	protected static final String ADD_SENDERFILETER_SUCESS= "Add SenderFilter Sucess";
	protected static final String REMOVE_SENDERFILETER_SUCESS = "Remove SenderFilter Sucess";
	
	protected static final String USER_OFFLINE = "the user has been offline";
	protected static final String SENDERFILTER_EXITS = "SenderFilter exists";
	protected static final String ERROR_XML = "SenderFilter Xml Style Error";
	protected static final String SQL_ERROR = "Error Operation";
	
	protected static final String BLACK_SIGN = "1";
	protected static final String WHITE_SIGN = "2";
	
	protected static final String CANCEL_SET = "0";
	/**
	 * 成功的处理请求.
	 * @param qwertCmd
	 * @param message
	 * @return
	 */
	protected QwertCmd sucessHandle(QwertCmd qwertCmd,HashMap<String, String> meta,
			String message,String code){
		meta.remove(TARGET_IMEI);
		qwertCmd.setMeta(meta);
		return failHandle(qwertCmd,message,code);
	}
	
	/**
	 * 失败的处理请求.
	 * @param qwertCmd
	 * @param message
	 * @return
	 */
	protected QwertCmd failHandle(QwertCmd qwertCmd,String message,String code){
		Status status = new Status();
		Object[] objects= new Object[1];	
		status.setCode(code);
		status.setMessage(message);
		objects[0] = status;
		qwertCmd.setObjects(new Status[] { (Status) objects[0] });
		return qwertCmd;
	}
	/**
	 * 获取WebSenderFilter对象.
	 * @param qwertCmd
	 * @return
	 */
	protected WebSenderFilter getWebFilter(QwertCmd qwertCmd){
		WebSenderFilter webFilter = null;
		Object[] objects = qwertCmd.getObjects();
		for (int j = 0; j < objects.length; j++) {
			if (objects[j] instanceof WebSenderFilter) {
				webFilter = (WebSenderFilter) objects[j];
			}
		}
		return webFilter;
	}
	
	/**
	 * 检查错误的协议格式.
	 * @param qwertCmd
	 * @param webFilter
	 * @return
	 */
	protected boolean checkWebFilter(QwertCmd qwertCmd,WebSenderFilter webFilter){
		Boolean isReturn = false;
		if(webFilter!=null){
			String name = webFilter.getName();
			String sign = webFilter.getSign();
			String set = webFilter.getSet();
			String operation = webFilter.getOperation();
			if(!Constant.NOT_SET_SENDER_FILTER.equals(set) && !Constant.CANCEL_SET_SENDER_FILTER.equals(set)
				&&!Constant.SET_BLACK_SENDER_FILTER.equals(set) && !Constant.SET_WHITE_SENDER_FILTER.equals(set)){
				isReturn = true;
			}else if(Constant.NOT_SET_SENDER_FILTER.equals(set)){
				if(!Constant.ADD_SENDER_FILTER.equals(operation) && !Constant.REMOVE_SENDER_FILTER.equals(operation))
					isReturn = true;
				if(name == null || "".equals(name.trim()))
					isReturn = true;
				if(!BLACK_SIGN.equals(sign) && !WHITE_SIGN.equals(sign))
					isReturn = true;
			}
			if(isReturn)
				qwertCmd = failHandle(qwertCmd,ERROR_XML,ErrorCode.CODE_SenderFilter_Error_Filter);
		}else{
			qwertCmd = failHandle(qwertCmd,ERROR_XML,ErrorCode.CODE_SenderFilter_Error_Filter);
			isReturn = true;
		}
		return isReturn;
	}
}
