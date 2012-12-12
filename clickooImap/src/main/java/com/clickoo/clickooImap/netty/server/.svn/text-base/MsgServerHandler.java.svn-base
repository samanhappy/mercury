package com.clickoo.clickooImap.netty.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clickoo.clickooImap.domain.IdleMessage;
import com.clickoo.clickooImap.domain.ServerInfo;
import com.clickoo.clickooImap.netty.client.MsgClient;
import com.clickoo.clickooImap.server.cache.ServerAccountsCache;
import com.clickoo.clickooImap.server.cache.ServerCacheMap;
import com.clickoo.clickooImap.utils.CIConstants;
import com.clickoo.clickooImap.utils.CITools;

public class MsgServerHandler extends SimpleChannelUpstreamHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(MsgServerHandler.class);
	static HashMap<String, Channel> channelMap = new HashMap<String, Channel>();

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		logger.error("-----------Exception-------------" + e.getCause());
		e.getChannel().close();
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		Channel channel = e.getChannel();
		String ip = channel.getRemoteAddress().toString().replace("/", "")
				.split(":")[0];
		logger.info("New client [" + ip + "] connect ["
				+ CITools.getLocalServerIp() + "]");
		if (channelMap.containsKey(ip)) {
			logger.error("The client [" + ip
					+ "] has already connected the Server ["
					+ channel.getLocalAddress() + "]!");
		} else {
			channelMap.put(ip, channel);
			logger.info("Server [" + CITools.getLocalServerIp()
					+ "] channelMap [" + channelMap + "]");
			ServerInfo currentServerInfo = ServerCacheMap.selectCIServer().get(
					CITools.getCurrentServerIP());
			if (!ip.equals(CITools.getLocalServerIp())
					&& currentServerInfo.getServerType().equals(
							CIConstants.ServerType.CI_SERVER_MASTER)) {
				// 将Master本地缓存同步到新启动的服务器
				logger.info("Begin to sync accountMap to Server [" + ip + "]");
				MsgServerHandler.messageSent(ip,
						ServerAccountsCache.accountsCache);
			}
		}

		HashMap<String, String> clientMap = MsgClient.getClientMap();
		if (!clientMap.containsKey(ip)) {
			new MsgClient().start(ip);
		}
	}

	/**
	 * 向指定客戶端发送消息
	 * 
	 * @param clientSocketAddress
	 * @param message
	 */
	public static void messageSent(String SocketIp, String message)
			throws Exception {
		logger.info("messageSent SocketIp:[" + SocketIp + "]-----message["
				+ message + "]");
		if (channelMap.containsKey(SocketIp)) {
			Channel channel = (Channel) channelMap.get(SocketIp);
			if (channel == null) {
				logger.error("The channel between Server ["
						+ CITools.getLocalServerIp() + "] and Client ["
						+ SocketIp + "] is nill!");
			} else {
				String str = "String##" + message;
				channel.write(str);
				logger.info("Server [" + channel.getLocalAddress()
						+ "] will send message [" + str + "] to Client ["
						+ channel.getRemoteAddress() + "]");
			}
		} else {
			logger.error("ChannelMap [" + channelMap
					+ "] not have this SocketIp:[" + SocketIp + "]");
		}
	}

	/**
	 * 将Master上的账户信息同步到新启动的服务器
	 * 
	 * @param SocketIp
	 * @param map
	 */
	public static void messageSent(
			String SocketIp,
			ConcurrentHashMap<String, ConcurrentHashMap<String, IdleMessage>> map) {
		logger.info("Sync accounts to Server [" + SocketIp + "]");
		if (channelMap.containsKey(SocketIp)) {
			Channel channel = (Channel) channelMap.get(SocketIp);
			try {
				ServerAccountsCache.addServer("CI_" + SocketIp);
				Iterator<String> it = map.keySet().iterator();
				JSONObject json = new JSONObject();
				while (it.hasNext()) {
					String string = (String) it.next();
					json.put(string, map.get(string));
				}
				String message = "Map##" + json.toString();
				logger.info("Server [" + CITools.getLocalServerIp()
						+ "] will send message [" + message + "]");
				channel.write(message);
			} catch (Exception e) {
				logger.error("Fail to sync accounts to Server [" + SocketIp
						+ "]");
			}
		} else {
			logger.error("ChannelMap [" + channelMap
					+ "] not have this SocketIp:[" + SocketIp + "]");
		}
	}
}
