package com.dreamail.mercury.domain;

public class WebSenderFilter {
	private String name;//黑白名单名称
	
	private String sign;//黑白名单标记 1黑名单 2白名单
	
	private String set;//设置黑白名单生效 0表示不是这个操作 1表示黑名单2白名单3取消设置
	
	private String operation;//1表示添加操作2表示删除操作

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
