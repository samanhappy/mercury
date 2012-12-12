package com.dreamail.mercury.smtp.utils;

public class User {
	private String userName;
	private String password;
	private String suffix;
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName.trim();	
		String[] type = userName.split("@");
		if("yahoo.com".equals(type[1])){
			this.setSuffix("yahoo.*");
		}
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password.trim();
	}
}
