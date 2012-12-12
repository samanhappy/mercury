package com.clickoo.clickooImap.netty.client;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.clickoo.clickooImap.config.ClickooImapProperties;
import com.clickoo.clickooImap.utils.CITools;

public class MsgClient {
	private static final Logger logger = LoggerFactory.getLogger(MsgClient.class);
	static HashMap<String, String> clientMap = new HashMap<String, String>();
	public static HashMap<String, String> getClientMap() {
		return clientMap;
	}
	public static void setClientMap(HashMap<String, String> clientMap) {
		MsgClient.clientMap = clientMap;
	}
	/**
	 * 与指定ip的server的socket进行连接
	 */
	public void start(final String host){
		if(clientMap.containsKey(host)){
			logger.error("The Client ["+CITools.getLocalServerIp()+"] has connected the server ["+host+"]");
		}else{
			int port = Integer.parseInt(ClickooImapProperties.getConfigureMap().get("nettyport"));
			ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
			ClientBootstrap bootstrap = new ClientBootstrap(factory);
			bootstrap.setPipelineFactory(new MsgClientPipelineFactory());
			bootstrap.setOption("tcpNodelay", true);
			bootstrap.setOption("keepAlive", true);
			logger.info("The Client ["+CITools.getLocalServerIp()+"] try to connect the server ["+host+"]");
			bootstrap.connect(new InetSocketAddress(host, port)); 
//			ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port)); 
			clientMap.put(host, "connect");
			logger.info("Server ["+CITools.getLocalServerIp()+"] clientMap ["+clientMap+"]");
//	        future.getChannel().getCloseFuture().awaitUninterruptibly(); 
//	        bootstrap.releaseExternalResources(); 
		}
	}
}
