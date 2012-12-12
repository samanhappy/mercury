package com.dreamail.mercury.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;

import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_sender_filter;

/**
 * 按流程依次封装相关信息的上下文类
 * 
 * @author pengkai.wang
 */
public class Context{

	private String receiveHost;
	// 接收邮件传输方式 pop3/imap4
	private String receiveProtocoltype;
//	private String mailType;
	// 加密方式
	private String receiveTs;
	private String port;
	// userId
//	private long uid;
	private long accountId;
	private String loginName;
	private String password;
	private Map<String,Message> largeMessageList;
	private Map<String,Email> emailList;
	// private Clickoo_mail_account account;
	private Folder folder;
	private Store store;
	private String compositor;
	private HashMap<String, Message> msgList;
	private String deviceModel;
	private Date registrationDate;
	private String supportAllUid;
	private boolean isSSL = true;
	private Clickoo_mail_account account;
	/**
	 * state: false代表正常邮件 反之代表大邮件
	 */
	private boolean state;
	/**
	 * inpathList 所有host的配置信息
	 */
	private String[] inpathList;
	/**
	 * 账号收取的开始时间
	 */
	private long startMill;
	private String status;//用户状态0：活动，1：不活动，2：新账号，3：收取老邮件
	
	/**
	 * 用户级别
	 */
	private long level;
	
	private int roleLevel; //角色等级
	
	private String uuid;//yahoosnp新邮件的uuid
	
	private String folderName;//yahoosnp新邮件的folder名称
	
	private String HostName;//特殊邮件服务器名称
	
	private long timeout;
	
	private String deviceModelList; //设备分辨率列表
	
	private String server;
	
	private int accountType;
	
	private String token;
	
	private String sign; //黑白名单
	
	private Map<String,List<String>> filterMap; //黑白名单
	
	//收邮件新加参数 间隔时间、断开连接时间、连接状态
	private int intervalTime;
		
	private long disconnectTime;
		
	private String connectStatus;
	
	private String mailType;
	
	private String syncKey;
	
	private boolean isTruncation = false;
	
	public boolean isTruncation() {
		return isTruncation;
	}

	public void setTruncation(boolean isTruncation) {
		this.isTruncation = isTruncation;
	}

	public String getSyncKey() {
		return syncKey;
	}

	public void setSyncKey(String syncKey) {
		this.syncKey = syncKey;
	}

	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public int getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}

	public long getDisconnectTime() {
		return disconnectTime;
	}

	public void setDisconnectTime(long disconnectTime) {
		this.disconnectTime = disconnectTime;
	}

	public String getConnectStatus() {
		return connectStatus;
	}

	public void setConnectStatus(String connectStatus) {
		this.connectStatus = connectStatus;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Map<String, List<String>> getFilterMap() {
		if(filterMap == null){
			filterMap = new HashMap<String,List<String>>();
		}
		return filterMap;
	}

	public void setFilterMap(Map<String, List<String>> filterMap) {
		this.filterMap = filterMap;
	}

	public String getDeviceModelList() {
		return deviceModelList;
	}

	public void setDeviceModelList(String deviceModelList) {
		this.deviceModelList = deviceModelList;
	}
	
	public long getTimeout() {
		return timeout;
	}

	public boolean isSSL() {
		return isSSL;
	}

	public void setSSL(boolean isSSL) {
		this.isSSL = isSSL;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public long getStartMill() {
		return startMill;
	}
	
	public void setStartMill(long startMill) {
		this.startMill = startMill;
	}
	
	public String getSupportAllUid() {
		return supportAllUid;
	}

	public void setSupportAllUid(String supportAllUid) {
		this.supportAllUid = supportAllUid;
	}

	public String[] getInpathList() {
		return inpathList;
	}

	public void setInpathList(String[] inpathList) {
		this.inpathList = inpathList;
	}

//	public String getMailType() {
//		return mailType;
//	}
//
//	public void setMailType(String mailType) {
//		this.mailType = mailType;
//	}

	public HashMap<String, Message> getMsgList() {
		return msgList;
	}

	public void setMsgList(HashMap<String, Message> msgList) {
		this.msgList = msgList;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getReceiveHost() {
		return receiveHost;
	}

	public void setReceiveHost(String receiveHost) {
		this.receiveHost = receiveHost;
	}

	public String getReceiveProtocoltype() {
		return receiveProtocoltype;
	}

	public void setReceiveProtocoltype(String receiveProtocoltype) {
		this.receiveProtocoltype = receiveProtocoltype;
	}

	public String getReceiveTs() {
		return receiveTs;
	}

	public void setReceiveTs(String receiveTs) {
		this.receiveTs = receiveTs;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String,Message> getLargeMessageList() {
		return largeMessageList;
	}

	public void setLargeMessageList(Map<String,Message> largeMessageList) {
		this.largeMessageList = largeMessageList;
	}

	public Map<String,Email> getEmailList() {
		return emailList;
	}

	public void setEmailList(Map<String,Email> emailList) {
		this.emailList = emailList;
	}

	public String getCompositor() {
		return compositor;
	}

	public void setCompositor(String compositor) {
		this.compositor = compositor;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

//	public long getRoleid() {
//		return roleid;
//	}
//
//	public void setRoleid(long roleid) {
//		this.roleid = roleid;
//	}

	public Clickoo_mail_account getAccount() {
		return account;
	}

	public void setAccount(Clickoo_mail_account account) {
		this.account = account;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getHostName() {
		return HostName;
	}

	public void setHostName(String hostName) {
		HostName = hostName;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
