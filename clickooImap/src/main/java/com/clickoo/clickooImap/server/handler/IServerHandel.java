package com.clickoo.clickooImap.server.handler;

import com.clickoo.clickooImap.domain.Notice;


public interface IServerHandel {
	public void handleMsg(Notice notice);
}
