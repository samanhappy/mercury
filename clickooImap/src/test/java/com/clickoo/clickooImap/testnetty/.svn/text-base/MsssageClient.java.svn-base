package com.clickoo.clickooImap.testnetty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class MsssageClient {
	public static void main(String[] args) throws Exception { 
        String host = "localhost"; 
        int port = 45454; 
        ClientBootstrap bootstrap = new ClientBootstrap( 
                new NioClientSocketChannelFactory( 
                        Executors.newCachedThreadPool(), 
                        Executors.newCachedThreadPool())); 
        bootstrap.setPipelineFactory(new MsssageClientPipelineFactory()); 
        bootstrap.setOption("tcpNoDelay",true);
		bootstrap.setOption("keepAlive",true);
		bootstrap.connect(new InetSocketAddress(host, port)); 
    } 
}
