package com.dreamail.hearbeat.server;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.CharsetUtil;
import org.jboss.netty.util.Timer;

public class TelnetPipelineFactory implements ChannelPipelineFactory {
	// private final ChannelHandler handler;
	private Timer timer;

	public ChannelPipeline getPipeline() throws Exception {
		// Create a default pipeline implementation.
		ChannelPipeline pipeline = Channels.pipeline();
		// Add the text line codec combination first,
		// pipeline.addFirst("framer", new
		// SslHandler(SSLContext.getInstance("trustword").createSSLEngine(), new
		// SslBufferPool()));
		// SSLEngine engine =
		// SecureChatSslContextFactory.getServerContext().createSSLEngine();
		// engine.setUseClientMode(false);
		// pipeline.addLast("ssl", new SslHandler(engine));
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
				Delimiters.lineDelimiter()));
		// pipeline.addLast("decoder", new StringDecoder());
		// pipeline.addLast("encoder", new StringEncoder());
		pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
//		pipeline.addLast("timeout", new IdleStateHandler(timer, 10, 10, 0));// 此两项为添加心跳机制
																			// 10秒查看一次在线的客户端channel是否空闲，IdleStateHandler为netty
																			// jar包中提供的类
//		pipeline.addLast("hearbeat", new HeartbeatTest());// 此类
														// 实现了IdleStateAwareChannelHandler接口
		// netty会定时扫描 空闲的channel

		// and then business logic.
		pipeline.addLast("handler", new TelnetServerHandler());
		return pipeline;
	}

	public TelnetPipelineFactory(Timer timer) {
		this.timer = timer;
	}
}
