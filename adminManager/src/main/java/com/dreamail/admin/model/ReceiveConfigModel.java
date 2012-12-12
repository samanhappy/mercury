package com.dreamail.admin.model;

public class ReceiveConfigModel {

	private long id;// 主键ID

	private String name; // 名称

	private int status;// 状态，是否启用

	private int intervaltime;// 收邮件间隔时间

	private int retrytime;// 收邮件重试时间

	private int connections;// 单台server支持每个mail server的账号连接数量

	private String inhost;
	
	private String protocolType;
	
	private String receivePort;
	
	private String receiveTs;
	
	private String compositor;
	
	private String supportalluid;
	
	private String ssl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIntervaltime() {
		return intervaltime;
	}

	public void setIntervaltime(int intervaltime) {
		this.intervaltime = intervaltime;
	}

	public int getRetrytime() {
		return retrytime;
	}

	public void setRetrytime(int retrytime) {
		this.retrytime = retrytime;
	}

	public int getConnections() {
		return connections;
	}

	public void setConnections(int connections) {
		this.connections = connections;
	}

	public String getInhost() {
		return inhost;
	}

	public void setInhost(String inhost) {
		this.inhost = inhost;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public String getReceivePort() {
		return receivePort;
	}

	public void setReceivePort(String receivePort) {
		this.receivePort = receivePort;
	}

	public String getReceiveTs() {
		return receiveTs;
	}

	public void setReceiveTs(String receiveTs) {
		this.receiveTs = receiveTs;
	}

	public String getCompositor() {
		return compositor;
	}

	public void setCompositor(String compositor) {
		this.compositor = compositor;
	}

	public String getSupportalluid() {
		return supportalluid;
	}

	public void setSupportalluid(String supportalluid) {
		this.supportalluid = supportalluid;
	}

	public String getSsl() {
		return ssl;
	}

	public void setSsl(String ssl) {
		this.ssl = ssl;
	}
	
}
