package com.dreamail.mercury.petasos.impl.senderfilter;

import java.util.HashMap;

import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.WebSenderFilter;
import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.petasos.ISenderFilter;
import com.dreamail.mercury.petasos.impl.AbstractFunctionFilterSign;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.MethodName;

public class SenderFilter extends AbstractFunctionFilterSign{
	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		Cred cred = pushMail.getCred();
		UserService userService = new UserService();
		String uid = null;
		if (cred != null) 
			uid = cred.getUuid();
		
		HashMap<String, String> meta = qwertCmd.getMeta();
		String IMEI = (String) meta.get("IMEI");
		//用户强制下线.
		if (!validateUserIMEI(userService.getUserRoleById(Long.parseLong(uid)), IMEI)) 
			return failHandle(qwertCmd,USER_OFFLINE,ErrorCode.CODE_USER_IMEI_FAIL);
		
		WebSenderFilter webFilter = getWebFilter(qwertCmd);
		//错误的协议格式
		 if(checkWebFilter(qwertCmd,webFilter))
			 return qwertCmd;
		return getFilterImpl(webFilter).handleCmd(qwertCmd, pushMail);
	}

	@Override
	public String getMethodName() {
		// TODO Auto-generated method stub
		return MethodName.SENDERFILTER;
	}
	
	private ISenderFilter getFilterImpl(WebSenderFilter webFilter){
		ISenderFilter filterImpl = null;
		String set = webFilter.getSet();
		 String operation = webFilter.getOperation();
		 if(!Constant.NOT_SET_SENDER_FILTER.equals(set)){
			 filterImpl = new SetFilterSign();
		 }else if(Constant.ADD_SENDER_FILTER.equals(operation)){
			 filterImpl = new SenderFilterAdd();
		 }else if(Constant.REMOVE_SENDER_FILTER.equals(operation)){
			 filterImpl = new SenderFilterRemove();
		 }
		 return filterImpl;
	}

}
