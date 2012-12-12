package com.dreamail.mercury.netty.server;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.netty.client.SmtpClient;

public class TelnetServerSmtpHandler extends SimpleChannelUpstreamHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(TelnetServerSmtpHandler.class);
	private ChannelFuture future;

	public TelnetServerSmtpHandler() {
	}

	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		if (e instanceof ChannelStateEvent) {
			logger.info(e.toString());
		}
		super.handleUpstream(ctx, e);
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		// Send greeting for a new connection.
		logger.info("connect in the here.............................");
		setFuture(new SmtpClient(ctx).start());
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, final MessageEvent e)
			throws Exception {
		String message = (String) e.getMessage();
		logger.info("client msg:" + message);
		// logger.info("id:"+Thread.currentThread().getId());
		// 添加对client监听 将服务器接收到的消息转发到smtp并把消息返回结果发送给终端.
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture arg0) throws Exception {
				Channel channel = future.getChannel();
				channel.write(e.getMessage() + "\r\n");
				if (!future.isSuccess()) {
					logger.info(future.getCause().getMessage());
				}
			}
		});
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		logger.info("remove Channel............");
		e.getChannel().close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		logger.info("smtp channel is closed : " + e);
		if (ctx.getChannel().isOpen()) {
			ctx.getChannel().close();
		}
	}

	public void setFuture(ChannelFuture future) {
		this.future = future;
	}

	public ChannelFuture getFuture() {
		return future;
	}

}
