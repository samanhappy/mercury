/**
 * 
 */
package com.dreamail.mercury.pojo;

import java.io.Serializable;


/**
 * @author meng.sun
 *
 */
public class Clickoo_mail_send_server implements Serializable{

	private static final long serialVersionUID = -6883935759949557405L;

	private long id;// 主键ID
	
	private String name;// 名称
	
	private String outPath;// 邮箱配置（SMTP地址）
	
	private int status;// 状态，是否启用

	public Clickoo_mail_send_server(){
		
	}
	
	public Clickoo_mail_send_server(long id, String name, String outPath, int status){
		this.id = id;
		this.name = name;
		this.outPath = outPath;
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

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
