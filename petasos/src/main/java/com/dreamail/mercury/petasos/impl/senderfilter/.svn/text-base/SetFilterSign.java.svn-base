package com.dreamail.mercury.petasos.impl.senderfilter;

import java.util.HashMap;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.jms.MessageSender;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.domain.WebSenderFilter;
import com.dreamail.mercury.domain.WebUser;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.petasos.ISenderFilter;
import com.dreamail.mercury.petasos.impl.AbstractFunctionFilterSign;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.ErrorCode;

public class SetFilterSign extends AbstractFunctionFilterSign implements ISenderFilter{
	private static Logger logger = (Logger) LoggerFactory
	.getLogger(SetFilterSign.class);
	@Override
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		logger.info("now set filtersign...");
		String uid = pushMail.getCred().getUuid();	
		HashMap<String, String> meta = qwertCmd.getMeta();
		WebSenderFilter webFilter = getWebFilter(qwertCmd);
		
		String set = webFilter.getSet();
		if(Constant.CANCEL_SET_SENDER_FILTER.equals(set))
			set = CANCEL_SET;
		UserDao userDao = new UserDao();
		Clickoo_user user = new Clickoo_user();
		user.setUid(Long.parseLong(uid));
		user.setSign(Integer.parseInt(set));
		if(userDao.updateUserSign(user)){
			MessageSender.sendSetSenderFilter(user);
			return sucessHandle(qwertCmd,meta,SET_FILETERSIGN_SUCESS,SUCESS_CODE);
		}else{
			return failHandle(qwertCmd,SQL_ERROR,ErrorCode.CODE_SenderFilter_Sql_Error);
		}
	}

	@Override
	public String getMethodName() {
		return null;
	}
	
	/**
	 * 获取WebUser对象.
	 * @param qwertCmd
	 * @return
	 */
	protected WebUser getWebUser(QwertCmd qwertCmd){
		WebUser webUser = null;
		Object[] objects = qwertCmd.getObjects();
		for (int j = 0; j < objects.length; j++) {
			if (objects[j] instanceof WebUser) {
				webUser = (WebUser) objects[j];
			}
		}
		return webUser;
	}
	@Override
	public QwertCmd handleCmd(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		// TODO Auto-generated method stub
		return execute(qwertCmd,pushMail);
	}
}
