package com.dreamail.mercury.netty.server;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class TelnetServerSmtpPipelineFactory implements ChannelPipelineFactory {

	@Override
	public ChannelPipeline getPipeline() {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = pipeline();
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
				Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());
		pipeline.addLast("handler", new TelnetServerSmtpHandler());
		return pipeline;
	}

	public TelnetServerSmtpPipelineFactory() {
		super();
	}

}
