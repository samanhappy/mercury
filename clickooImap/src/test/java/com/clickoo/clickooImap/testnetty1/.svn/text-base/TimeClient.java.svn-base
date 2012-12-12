package com.clickoo.clickooImap.testnetty1;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class TimeClient {
	public static void main(String[] args) {
//		String host = args[0];
//		int port = Integer.parseInt(args[1]);
		String host ="127.0.0.1";
		int port = 4567;
		ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),Executors.newCachedThreadPool());
		//客户端的ClientBootstrap 对应ServerBootstrap
		ClientBootstrap bootstrap = new ClientBootstrap(factory);
		
		ChannelPipeline pipeline = bootstrap.getPipeline();
		TimeClientHandler handler = new TimeClientHandler();
		TimeDecoder decoder = new TimeDecoder();
		pipeline.addLast("handler", handler);
		pipeline.addLast("decoder", decoder);
		
		//这里不存在使用“child.”前缀的配置项，客户端的SocketChannel 实例不存在父级Channel对象。
		bootstrap.setOption("tcpNodelay", true);
		bootstrap.setOption("keepAlive", true);
		bootstrap.connect(new InetSocketAddress(host,port));
	}

}
