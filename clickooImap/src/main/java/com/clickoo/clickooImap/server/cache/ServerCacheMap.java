package com.clickoo.clickooImap.server.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.clickoo.clickooImap.domain.ServerInfo;

public class ServerCacheMap {
	public static ConcurrentHashMap<String, ServerInfo> ConfigureMap = 
		                           new ConcurrentHashMap<String, ServerInfo>();
	/**
	 * 添加clickooImap Server信息列表
	 * @param serverType 服务器类型
	 * @param clickooImapInfo       服务器信息
	 */
	public static void addCIServer(String serverType, ServerInfo clickooImapServerInfo){
		ConfigureMap.put(serverType, clickooImapServerInfo);
	}
	/**
	 * 删除clickooImap Server信息列表
	 * @param serverType 服务器类型
	 * @param clickooImapServerInfo       服务器信息
	 */
	public static void removeCIServer(String serverType, ServerInfo clickooImapServerInfo){
		ConfigureMap.remove(serverType);
	}
	/**
	 * 修改clickooImap Server信息列表
	 * @param serverType 服务器类型
	 * @param clickooImapServerInfo       服务器信息
	 */
	public static void modifyCIServer(String serverType, ServerInfo clickooImapServerInfo){
		ConfigureMap.put(serverType, clickooImapServerInfo);
	}
	/**
	 * 查询clickooImap Server信息列表
	 * @param serverType 服务器类型
	 * @param clickooImapServerInfo       服务器信息
	 */
	public static ConcurrentHashMap<String, ServerInfo> selectCIServer(){
		return ConfigureMap;
	}
	
	
}
