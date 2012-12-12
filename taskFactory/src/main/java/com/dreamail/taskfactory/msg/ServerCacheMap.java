package com.dreamail.taskfactory.msg;

import java.util.concurrent.ConcurrentHashMap;

public class ServerCacheMap {
	public static ConcurrentHashMap<String, ServerInfo> configureMap = 
		                           new ConcurrentHashMap<String, ServerInfo>();
	/**
	 * 添加TF信息列表
	 * @param serverType 服务器类型
	 * @param taskFactoryInfo       服务器信息
	 */
	public static void addTF(String serverType, ServerInfo taskFactoryInfo){
		configureMap.put(serverType, taskFactoryInfo);
	}
	/**
	 * 删除TF信息列表
	 * @param serverType 服务器类型
	 * @param taskFactoryInfo       服务器信息
	 */
	public static void removeTF(String serverType, ServerInfo taskFactoryInfo){
		configureMap.remove(serverType);
	}
	/**
	 * 修改TF信息列表
	 * @param serverType 服务器类型
	 * @param taskFactoryInfo       服务器信息
	 */
	public static void modifyTF(String serverType, ServerInfo taskFactoryInfo){
		configureMap.put(serverType, taskFactoryInfo);
	}
	/**
	 * 查询TF信息列表
	 * @param serverType 服务器类型
	 * @param taskFactoryInfo       服务器信息
	 */
	public static ConcurrentHashMap<String, ServerInfo> selectTF(){
		return configureMap;
	}
}
