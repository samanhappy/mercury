package com.dreamail.mercury.netty.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class TestNetty {
	public static void main(String[] args) throws IOException {
		String host = "localhost";
		int port = 25;
		ClientBootstrap bootstrap = new ClientBootstrap(
				new NioClientSocketChannelFactory(Executors
						.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new TelnetClientPipelineFactory());
		bootstrap.connect(new InetSocketAddress(host,
				port));
	}
}
