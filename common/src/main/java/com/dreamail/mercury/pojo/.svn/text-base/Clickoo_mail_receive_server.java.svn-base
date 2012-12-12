/**
 * 
 */
package com.dreamail.mercury.pojo;

import java.io.Serializable;

/**
 * @author meng.sun
 * 
 */
public class Clickoo_mail_receive_server implements Serializable{

	private static final long serialVersionUID = -3856296819044310738L;

	private long id;// 主键ID

	private String name; // 名称

	private String inPath;// 邮箱配置（POP地址等）

	private int status;// 状态，是否启用

	private int intervaltime;// 收邮件间隔时间

	private int retrytime;// 收邮件重试时间
	
	private int connections;//单台server支持每个mail server的账号连接数量

	public Clickoo_mail_receive_server(long id, String name, String inPath,
			int status) {
		this.id = id;
		this.name = name;
		this.inPath = inPath;
		this.status = status;
	}

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

	public String getInPath() {
		return inPath;
	}

	public void setInPath(String inPath) {
		this.inPath = inPath;
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

	public Clickoo_mail_receive_server() {

	}

	public int getConnections() {
		return connections;
	}

	public void setConnections(int connections) {
		this.connections = connections;
	}

}
