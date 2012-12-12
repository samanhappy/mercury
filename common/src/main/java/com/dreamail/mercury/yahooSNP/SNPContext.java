package com.dreamail.mercury.yahooSNP;

import org.apache.http.client.CookieStore;

/**
 * @author 001037
 * 
 */
public class SNPContext{

	private long aid;

	private String loginId;

	private String password;

	private String token;

	private String wssid;

	private CookieStore cookieStore;

	private String status;// on /off

	private String userId;

	private long subId;
	
	private String ptid;
	
	private String Y_cookie;
	
	private String T_cookie;

	public String getPtid() {
		return ptid;
	}

	public void setPtid(String ptid) {
		this.ptid = ptid;
	}

	public String getY_cookie() {
		return Y_cookie;
	}

	public void setY_cookie(String yCookie) {
		Y_cookie = yCookie;
	}

	public String getT_cookie() {
		return T_cookie;
	}

	public void setT_cookie(String tCookie) {
		T_cookie = tCookie;
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getWssid() {
		return wssid;
	}

	public void setWssid(String wssid) {
		this.wssid = wssid;
	}

	public CookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getSubId() {
		return subId;
	}

	public void setSubId(long subId) {
		this.subId = subId;
	}

}
