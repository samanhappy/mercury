package com.dreamail.mercury.imap;

import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.dreamail.mercury.User;
import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.imap.domain.FolderData;
import com.dreamail.mercury.pojo.Clickoo_imap_message;

public class ImapSession {
	private User user;
	
	/**
	 * 上下文链接
	 */
	private ChannelHandlerContext context;
	
	/**
	 * 当前线程的id(唯一标识)
	 */
	private long threadId = 0;
	
	/**
	 * key：文件夹名称
	 * value：文件夹对象
	 */
	private Map<String,FolderData> exists;
	
	private List<Clickoo_imap_message> messageList;
	
	/**
	 * 当前所在的文件夹
	 */
	private String folder;
	
	private boolean isLogin = false;
	
	public java.util.Timer timer = new Timer(true);
	
	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public Map<String, FolderData> getExists() {
		return exists;
	}

	public void setExists(Map<String, FolderData> exists) {
		this.exists = exists;
	}

	public long getThreadId() {
		return threadId;
	}

	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ChannelHandlerContext getContext() {
		return context;
	}

	public void setContext(ChannelHandlerContext context) {
		this.context = context;
	}

	public List<Clickoo_imap_message> getMessageList() {
		if(messageList == null || messageList.size()==0){
			messageList = new MessageService().getUidsByAccountId(user.getAid());
//			Collections.sort(messageList);
			setMessageList(messageList);
		}
		return messageList;
	}

	private void setMessageList(List<Clickoo_imap_message> messageList) {
		this.messageList = messageList;
	}
	
	/*public void updateMessageList(){
		messageList = new MessageService().getUidsByAccountId(user.getAid());
		setMessageList(messageList);
	}*/
}
