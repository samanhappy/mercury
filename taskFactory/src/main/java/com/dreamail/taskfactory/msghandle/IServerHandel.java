package com.dreamail.taskfactory.msghandle;

import com.dreamail.jms.Notice;

public interface IServerHandel {
	public void handleMsg(Notice notice);
}
