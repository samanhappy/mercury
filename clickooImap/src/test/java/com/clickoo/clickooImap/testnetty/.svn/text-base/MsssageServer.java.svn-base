package com.clickoo.clickooImap.testnetty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class MsssageServer{
	public static void main(String[] args) throws Exception { 
        ServerBootstrap bootstrap = new ServerBootstrap( 
                new NioServerSocketChannelFactory( 
                        Executors.newCachedThreadPool(), 
                        Executors.newCachedThreadPool())); 

        bootstrap.setPipelineFactory(new MsssageServerPipelineFactory()); 
        bootstrap.setOption("child.tcpNoDelay",	true);
		bootstrap.setOption("child.keepAlive",	true);
        bootstrap.bind(new InetSocketAddress(45454)); 
    } 
}
