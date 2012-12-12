package com.dreamail.mercury.pojo;

import com.dreamail.mercury.util.JsonUtil;

public class Clickoo_role {

	private long id;// 主键ID

	private String title;// 题头
	
	private String remark;// 描述
	
	private String function;//功能
	
	private Function objfunction;
	
	private int priority;//角色级别

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public Function getObjfunction() {
		if(objfunction == null){
			objfunction = JsonUtil.getInstance().parserFunction(function);
		}
		return objfunction;
	}

	public void setObjfunction(Function objfunction) {
		this.objfunction = objfunction;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
