package com.dreamail.mercury.smtp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.sendMail.jms.Start;
import com.dreamail.mercury.smtp.utils.SmtpEmail;
import com.dreamail.mercury.smtp.utils.User;

public class SmtpSession {
	private static final Logger logger = LoggerFactory.getLogger(SmtpSession.class);
	
	/**
	 * 上下文链接
	 */
	private ChannelHandlerContext context;
	
	/**
	 * 当前线程的id(唯一标识)
	 */
	private long threadId = 0;
	
	private boolean sendState = false;
	
	private List<String> commandList = new ArrayList<String>();
	
	private User user;
	
	private int count = 1;
	
	private SmtpEmail smtpEmail;
	
	boolean toIsList = false;
	
	//验证是命令的状态
	boolean authState = false;
	
	//判断头域是不是分多行
	boolean commandMore = false;
	
	boolean ccMoreLine = false;
	
	StringBuffer sb = new StringBuffer();
	

	public ChannelHandlerContext getContext() {
		return context;
	}

	public void setContext(ChannelHandlerContext context) {
		this.context = context;
	}

	public long getThreadId() {
		return threadId;
	}

	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}

	public boolean getSendState() {
		return sendState;
	}

	public void setSendState(boolean sendState) {
		this.sendState = sendState;
	}

	public List<String> getCommandList() {
		return commandList;
	}

	public void setCommandList(List<String> commandList) {
		this.commandList = commandList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setSmtpEmail(SmtpEmail smtpEmail) {
		this.smtpEmail = smtpEmail;
	}

	public SmtpEmail getSmtpEmail() {	
		return smtpEmail;
	}

	public boolean getToIsList() {
		return toIsList;
	}

	public void setToIsList(boolean toIsList) {
		this.toIsList = toIsList;
	}
	
	public boolean getAuthState() {
		return authState;
	}

	public void setAuthState(boolean authState) {
		this.authState = authState;
	}
	
	
	
	public StringBuffer getSb() {
		return sb;
	}

	public void setSb(StringBuffer sb) {
		this.sb = sb;
	}

	public boolean isCommandMore() {
		return commandMore;
	}

	public void setCommandMore(boolean commandMore) {
		this.commandMore = commandMore;
	}

	public boolean isCcMoreLine() {
		return ccMoreLine;
	}

	public void setCcMoreLine(boolean ccMoreLine) {
		this.ccMoreLine = ccMoreLine;
	}

	public void cleanSession(SmtpSession session) {
		if (session != null && session.getUser() != null) {
			if (session.getUser() != null) {
				String userName = session.getUser().getUserName();
				if(userName!=null && !"".equals(userName)){
					if (Start.session.get(userName) != null) {
						CopyOnWriteArrayList<SmtpSession> list = Start.session
								.get(userName);
						if(list!=null){
							long threadId = Thread.currentThread().getId();
							for(SmtpSession smtpSession:list){
								if(smtpSession.getThreadId() == threadId){
									logger.info("remove session by:"+smtpSession.getUser().getUserName());
									list.remove(smtpSession);
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
		}
		session = null;
	}
	
	
	
}
