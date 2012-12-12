package com.dreamail.admin.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.AdminUserDao;
import com.dreamail.mercury.pojo.AdminUser;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(LoginAction.class);

	private String username;

	private String password;

	private String message;
	
	private String authority;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String execute() {
		AdminUser user = new AdminUser();
		user.setUsername(username);
		user.setPassword(password);
		authority = new AdminUserDao().getAuthorityByNameAndPassword(user);
		if (authority != null) {
			return SUCCESS;
		} else {
			message = "用户名密码无效";
			return ERROR;
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(LoginAction.class);
		logger.info("1");
	}

}
