package com.dreamail.mercury.netty.server;

import javax.net.ssl.SSLEngine;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.ssl.SslHandler;

public class TelnetPipelineHttpsFactory implements ChannelPipelineFactory {

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		// Create a default pipeline implementation.
		ChannelPipeline pipeline = Channels.pipeline();
		// Add the text line codec combination first,
//		pipeline.addFirst("framer", new SslHandler(SSLContext.getInstance("trustword").createSSLEngine(), new SslBufferPool()));
		SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
		engine.setUseClientMode(false);
		pipeline.addLast("ssl", new SslHandler(engine));
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
				Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());

		// and then business logic.
		pipeline.addLast("handler", new TelnetServerHttpsHandler());
		return pipeline;

	}

	public TelnetPipelineHttpsFactory() {
		super();
	}

}