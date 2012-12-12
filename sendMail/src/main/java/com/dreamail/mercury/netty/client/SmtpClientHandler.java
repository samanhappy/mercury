package com.dreamail.mercury.netty.client;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmtpClientHandler extends SimpleChannelUpstreamHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(SmtpClientHandler.class);
	private SmtpClient client;

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.channelConnected(ctx, e);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		logger.error("Unexpected exception from downstream.");
		e.getChannel().close();
	}

	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		if (e instanceof ChannelStateEvent) {
			logger.info(e.toString());
		}
		super.handleUpstream(ctx, e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		String message = (String) e.getMessage();
		notifyClient(message + "\r\n");
	}

	/**
	 * 通知消息更新,回调client的update方法.
	 * 
	 * @param message
	 */
	public void notifyClient(String message) {
		client.update(message);
	}

	/**
	 * 注册监听.
	 * 
	 * @param scClient
	 */
	public void registerObserver(SmtpClient client) {
		this.client = client;
	}
}
