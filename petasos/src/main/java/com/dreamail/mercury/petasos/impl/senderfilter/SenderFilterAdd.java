package com.dreamail.mercury.petasos.impl.senderfilter;

import java.util.HashMap;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.jms.MessageSender;
import com.dreamail.mercury.dal.dao.SenderFilterDao;
import com.dreamail.mercury.domain.WebSenderFilter;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.petasos.ISenderFilter;
import com.dreamail.mercury.petasos.impl.AbstractFunctionFilterSign;
import com.dreamail.mercury.pojo.Clickoo_sender_filter;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.JMSConstans;

public class SenderFilterAdd extends AbstractFunctionFilterSign implements ISenderFilter{
	private static Logger logger = (Logger) LoggerFactory
	.getLogger(SenderFilterAdd.class);
	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		logger.info("now add sendfilter...");
		String uid = pushMail.getCred().getUuid();	
		HashMap<String, String> meta = qwertCmd.getMeta();
		WebSenderFilter webFilter = getWebFilter(qwertCmd);
		
		String name = webFilter.getName();
		String sign = webFilter.getSign();
		Clickoo_sender_filter senderFilter = new Clickoo_sender_filter();
		senderFilter.setUid(Long.parseLong(uid));
		senderFilter.setName(name);
		SenderFilterDao dao = new SenderFilterDao();
		//黑白名单已存在.
		if(dao.selectSenderFilterByName(senderFilter) != null)
			return failHandle(qwertCmd,SENDERFILTER_EXITS,ErrorCode.CODE_SenderFilter_Existed);
		
		//添加黑白名单成功.
		senderFilter.setSign(Integer.parseInt(sign));
		if(dao.saveSenderFilter(senderFilter)!=-1){
			MessageSender.sendModifySenderFilter(JMSConstans.JMS_USER_ADD_FILTER,senderFilter,uid);
			return sucessHandle(qwertCmd,meta,ADD_SENDERFILETER_SUCESS,SUCESS_CODE);
		}else{
			return failHandle(qwertCmd,SQL_ERROR,ErrorCode.CODE_SenderFilter_Sql_Error);
		}
	}
	@Override
	public String getMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public QwertCmd handleCmd(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		// TODO Auto-generated method stub
		return execute(qwertCmd,pushMail);
	}

}
