package com.dreamail.mercury.netty.server;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class TelnetPipelineFactory implements ChannelPipelineFactory{
//	private final ChannelHandler handler;
	
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		// Create a default pipeline implementation.
		ChannelPipeline pipeline = Channels.pipeline();
		// Add the text line codec combination first,
//		pipeline.addFirst("framer", new SslHandler(SSLContext.getInstance("trustword").createSSLEngine(), new SslBufferPool()));
//		SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
//		engine.setUseClientMode(false);
//		pipeline.addLast("ssl", new SslHandler(engine));
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
				Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());
//		pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
//		pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
		// and then business logic.
		pipeline.addLast("handler", new TelnetServerHandler());
		return pipeline;
	}

	public TelnetPipelineFactory() {
		super();
//		this.handler = handler;
	}
}
