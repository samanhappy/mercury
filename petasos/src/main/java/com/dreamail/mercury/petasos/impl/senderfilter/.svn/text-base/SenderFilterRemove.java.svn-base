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

public class SenderFilterRemove extends AbstractFunctionFilterSign implements ISenderFilter{
	private static Logger logger = (Logger) LoggerFactory
	.getLogger(SenderFilterRemove.class);
	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		logger.info("now remove sendfilter...");
		
		String uid = pushMail.getCred().getUuid();	
		HashMap<String, String> meta = qwertCmd.getMeta();
		WebSenderFilter webFilter = getWebFilter(qwertCmd);
		
		//删除黑白名单.
		String name = webFilter.getName();
		String sign = webFilter.getSign();
		Clickoo_sender_filter senderFilter = new Clickoo_sender_filter();
		senderFilter.setUid(Long.parseLong(uid));
		senderFilter.setName(name);
		senderFilter.setSign(Integer.parseInt(sign));
		SenderFilterDao dao = new SenderFilterDao();
		if(dao.deleteSenderFilterByName(senderFilter)){
			MessageSender.sendModifySenderFilter(JMSConstans.JMS_USER_DELETE_FILTER,senderFilter,uid);
			return sucessHandle(qwertCmd,meta,REMOVE_SENDERFILETER_SUCESS,SUCESS_CODE);
		}else{
			return failHandle(qwertCmd,SQL_ERROR,ErrorCode.CODE_SenderFilter_Sql_Error);
		}
	}

	@Override
	public String getMethodName() {
		return null;
	}
	@Override
	public QwertCmd handleCmd(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		// TODO Auto-generated method stub
		return execute(qwertCmd,pushMail);
	}
}
