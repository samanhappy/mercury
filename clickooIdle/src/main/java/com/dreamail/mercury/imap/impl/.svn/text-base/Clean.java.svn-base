package com.dreamail.mercury.imap.impl;

import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.Start;
import com.dreamail.mercury.imap.ImapSession;

public class Clean {
	private static final Logger logger = LoggerFactory
	.getLogger(Clean.class);
	public void cleanSession(ImapSession iSession) {
		if (iSession != null && iSession.getUser() != null) {
			if (iSession.getUser() != null) {
				String userName = iSession.getUser().getUserName();
				if(userName!=null && !"".equals(userName)){
					if (Start.session.get(userName) != null) {
						CopyOnWriteArrayList<ImapSession> list = Start.session
								.get(userName);
						if(list!=null){
							long threadId = Thread.currentThread().getId();
							for(ImapSession session:list){
								if(session.getThreadId() == threadId){
									logger.info("remove session by:"+session.getUser().getUserName());
									list.remove(session);
								}
							}
							if(list.size() == 0){
								logger.info("remove session by:"+userName);
								Start.session.remove(userName);
							}
						}else{
							logger.error("public session not in this account:"+userName);
						}
					}
				}else{
					logger.error("no session:"+userName);
				}
			}
			/**
			 * 取消定时器
			 */
			iSession.timer.cancel();
		}
		iSession = null;
	}
}
