package com.dreamail.mercury.petasos.impl.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.service.UserService;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;
import com.dreamail.mercury.domain.qwert.Status;
import com.dreamail.mercury.petasos.impl.AbstractFunctionEmail;
import com.dreamail.mercury.pojo.User_role;
import com.dreamail.mercury.util.ErrorCode;
import com.dreamail.mercury.util.MethodName;

/**
 * 该类已废弃
 * @author Administrator
 *
 */
@Deprecated 
public class EmailDelete extends AbstractFunctionEmail {

	@Override
	@Deprecated 
	public QwertCmd execute(QwertCmd qwertCmd, PushMail pushMail)
			throws Exception {
		HashMap<String, String> meta = qwertCmd.getMeta();
		List<Object> objs = new ArrayList<Object>();
		Status status = new Status();
		Cred cred = null;
		String uid = null;
		if (pushMail.getCred() != null) {
			cred = pushMail.getCred();
		}
		if (cred != null) {
			uid = cred.getUuid();
		}
		String IMEI = (String) meta.get("IMEI");
		UserService userService = new UserService();
		User_role ur = userService.getUserRoleById(Long.parseLong(uid));
		if (!validateUserRole(ur)) {
			status.setCode(ErrorCode.CODE_USER_ROLE_FAIL);
			status.setMessage("User has been Disabled");
			Object[] objectss = new Object[1];
			objectss[0] = status;
			qwertCmd.setObjects(new Status[] { (Status) objectss[0] });
			return qwertCmd;
		}
		if (!validateUserIMEI(ur, IMEI)) {
			status.setCode(ErrorCode.CODE_USER_IMEI_FAIL);
			status.setMessage("the user has been offline");
			Object[] objectss = new Object[1];
			objectss[0] = status;
			qwertCmd.setObjects(new Status[] { (Status) objectss[0] });
			return qwertCmd;
		} else {
			meta.remove("IMEI");
		}
		Object[] emails = qwertCmd.getObjects();
		status.setCode("0");
		status.setMessage("emails delete success");
		objs.add(status);
		
		Long[] ids = new Long[emails.length]; 
		for (int i = 0; i < emails.length; i++) {
			if (emails[i] instanceof WebEmail) {
				WebEmail wemail = (WebEmail) emails[i];
				ids[i] = Long.valueOf(wemail.getHead().getMessageId());
			}
		}
		new MessageDao().deleteMessagesByIds(ids);
		
		pushMail.setCred(null);
		Object[] objects = objs.toArray();
		qwertCmd.setObjects(objects);
		return qwertCmd;
	}

	@Override
	@Deprecated 
	public String getMethodName() {
		return MethodName.EMAIL_DELETE;
	}
}
