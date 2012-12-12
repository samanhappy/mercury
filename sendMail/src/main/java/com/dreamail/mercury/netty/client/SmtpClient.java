package com.dreamail.mercury.netty.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmtpClient {
	public static ClientBootstrap bootstrap;
	private static final Logger logger = LoggerFactory
			.getLogger(SmtpClient.class);
	private ChannelHandlerContext ctx;
	static {
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors
						.newCachedThreadPool()));
	}

	public ChannelFuture start() throws Exception {
		String host = "smtp.mobile.mail.yahoo.com";
		int port = 25;
		// Configure the client.
		// Configure the pipeline factory.
		SmtpClientHandler cHandler = new SmtpClientHandler();
		cHandler.registerObserver(new SmtpClient(ctx));
		SmtpClientPipelineFactory factory = new SmtpClientPipelineFactory(
				cHandler);
		bootstrap.setPipelineFactory(factory);

		// Start the connection attempt.
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(host,
				port));
		return future;
	}

	public void update(String message) {
		logger.info("return message:" + message);
		ctx.getChannel().write(message);
	}

	public SmtpClient(ChannelHandlerContext ctx) {
		super();
		this.ctx = ctx;
	}
}
