package com.dreamail.mercury.petasos;

import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.domain.qwert.QwertCmd;

public interface ISenderFilter {
	QwertCmd handleCmd(QwertCmd qwertCmd, PushMail pushMail)throws Exception;
}
