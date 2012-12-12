package com.dreamail.mercury.pojo;

public class InPath {
	private String inhost;
	private String protocolType;
	private String receivePort;
	private String receiveTs;
	private String compositor;
	private String supportalluid;
	private String ssl;

	public String getInhost() {
		return inhost;
	}

	public void setInhost(String inhost) {
		this.inhost = inhost;
	}

	public String getSsl() {
		return ssl;
	}

	public void setSsl(String ssl) {
		this.ssl = ssl;
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
}
