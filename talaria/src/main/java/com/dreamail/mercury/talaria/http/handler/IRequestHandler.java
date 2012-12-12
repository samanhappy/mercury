package com.dreamail.mercury.talaria.http.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dreamail.mercury.talaria.http.ResponseSender;
import com.dreamail.mercury.util.SystemMessageUtil;
import com.dreamail.talaria.memcached.UPECacheManager;
import com.dreamail.talaria.memcached.UPECacheObject;
import com.dreamail.talaria.memcached.UPEMapping;

public abstract class IRequestHandler {

	protected HttpServletRequest request;

	protected HttpServletResponse response;
	
	protected String IMEI;
	
	protected String uid;

	protected UPECacheObject cache;

	protected UPEMapping mapping;
	
	protected String requestStr;
	
	private String responseContent;
	
	protected static final String properties = "suspend.properties";

	public static final String mac = SystemMessageUtil.getLocalMac();

	protected static long suspendTime;
	
	protected static long CAGSuspendTime;
	
	protected boolean responseDone = false;
	
	/**
	 * 加载配置信息.
	 */
	static {
		InputStream inputStream = IRequestHandler.class.getClassLoader()
				.getResourceAsStream(properties);
		Properties p = new Properties();
		try {
			p.load(inputStream);
			suspendTime = Long.parseLong((String) p.get("suspendTime"));
			CAGSuspendTime = Long.parseLong((String) p.get("CAGSuspendTime"));
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public IRequestHandler(HttpServletRequest request,
			HttpServletResponse response, String requestStr) {
		this.request = request;
		this.response = response;
		this.requestStr = requestStr;
	}

	/**
	 * 处理请求
	 */
	public abstract void handle();
	
	/**
	 * 响应请求
	 */
	public void response() {
		
		// 更新UPE缓存
		if (cache != null && uid != null) {
			UPECacheManager.setCacheObject(cache);
			UPECacheManager.removeLock(uid);
		}
		
		if (mapping != null && IMEI != null) {
			UPECacheManager.setMappingObject(mapping);
			UPECacheManager.removeMappingLock(IMEI);
		}
		
		if (responseDone) {
			ResponseSender.response2Client(response, responseContent);
		}
		
	}
	
	/**
	 * 设置响应内容.
	 * 
	 * @param content
	 */
	public void setResponseContent(String content) {
		this.responseContent = content;
	}
}
