package com.dreamail.mercury.timerpush.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.dreamail.mercury.timerpush.domain.ServerInfo;


public class ServerCacheMap {
	public static ConcurrentHashMap<String, ServerInfo> serverCaheMap = 
		                           new ConcurrentHashMap<String, ServerInfo>();
	/**
	 * 添加timerpush Server信息列表
	 * @param serverType 服务器类型
	 * @param timerpushInfo       服务器信息
	 */
	public static void addTimerServer(String serverType, ServerInfo timerpushServerInfo){
		serverCaheMap.put(serverType, timerpushServerInfo);
	}
	/**
	 * 删除timerpush Server信息列表
	 * @param serverType 服务器类型
	 * @param timerpushServerInfo       服务器信息
	 */
	public static void removeTimerServer(String serverType, ServerInfo timerpushServerInfo){
		serverCaheMap.remove(serverType);
	}
	/**
	 * 修改timerpush Server信息列表
	 * @param serverType 服务器类型
	 * @param timerpushServerInfo       服务器信息
	 */
	public static void modifyTimerServer(String serverType, ServerInfo timerpushServerInfo){
		serverCaheMap.put(serverType, timerpushServerInfo);
	}
	/**
	 * 查询timerpush Server信息列表
	 * @param serverType 服务器类型
	 * @param timerpushServerInfo       服务器信息
	 */
	public static ConcurrentHashMap<String, ServerInfo> selectTimerServer(){
		return serverCaheMap;
	}
	
	
}
