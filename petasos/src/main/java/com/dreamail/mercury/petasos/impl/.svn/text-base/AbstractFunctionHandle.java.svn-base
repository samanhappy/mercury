package com.dreamail.mercury.petasos.impl;

import com.dreamail.mercury.domain.qwert.Cred;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertTarget;
import com.dreamail.mercury.domain.qwert.Version;
import com.dreamail.mercury.pojo.Clickoo_user;

public abstract class AbstractFunctionHandle{
	
	boolean isJ2EE = true ;
	QwertTarget target =null ;
	Cred cred = null ;
	Version version= null;	
	protected Clickoo_user user ;
	
	public abstract QwertTarget handle(QwertTarget target,PushMail pushMail) throws Exception;
	
	public abstract String getTypename();

	public QwertTarget getTarget() {
		return target;
	}

	public void setTarget(QwertTarget target) {
		this.target = target;
	}

	public Cred getCred() {
		return cred;
	}

	public void setCred(Cred cred) {
		this.cred = cred;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public Clickoo_user getUser() {
		return user;
	}

	public void setUser(Clickoo_user user) {
		this.user = user;
	}
	
	
}
