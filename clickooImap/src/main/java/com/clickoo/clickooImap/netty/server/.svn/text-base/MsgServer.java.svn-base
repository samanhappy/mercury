package com.clickoo.clickooImap.netty.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import com.clickoo.clickooImap.config.ClickooImapProperties;

public class MsgServer{
	/**
	 * 每个服务器启动的时候同事启动Scoket端口
	 */
	public void start(){
		int port = Integer.parseInt(ClickooImapProperties.getConfigureMap().get("nettyport"));
		ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(new MsgServerPipelineFactory());
		bootstrap.setOption("child.tcpNoDelay",	true);
		bootstrap.setOption("child.keepAlive",	true);
		bootstrap.bind(new InetSocketAddress(port));
	}
}
//public class MsgServer implements Runnable {
//	/**
//	 * 每个服务器启动的时候同事启动Scoket端口
//	 */
//	public void run(){
////		int port = Integer.parseInt(ClickooImapProperties.getConfigureMap().get("nettyport"));
//		int port =8080;
//		ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
//		ServerBootstrap bootstrap = new ServerBootstrap(factory);
//		bootstrap.setPipelineFactory(new MsgServerPipelineFactory());
////		bootstrap.setOption("child.tcpNoDelay",	true);
////		bootstrap.setOption("child.keepAlive",	true);
//		bootstrap.bind(new InetSocketAddress(port));
//	}
//}
