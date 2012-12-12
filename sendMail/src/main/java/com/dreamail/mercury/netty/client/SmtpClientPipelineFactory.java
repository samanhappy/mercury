package com.dreamail.mercury.netty.client;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmtpClientPipelineFactory implements ChannelPipelineFactory {
	private static final Logger logger = LoggerFactory
			.getLogger(SmtpClientPipelineFactory.class);
	private SmtpClientHandler cHandler;

	@Override
	public ChannelPipeline getPipeline() {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = pipeline();
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
				Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());

		// and then business logic.
		// SecureChatClientHandler scHandler = new SecureChatClientHandler();
		logger.info("factory:" + getcHandler());
		pipeline.addLast("handler", getcHandler());

		return pipeline;
	}

	public SmtpClientPipelineFactory(SmtpClientHandler cHandler) {
		super();
		this.cHandler = cHandler;
	}

	public SmtpClientPipelineFactory() {
		super();
	}

	public SmtpClientHandler getcHandler() {
		return cHandler;
	}

	public void setcHandler(SmtpClientHandler cHandler) {
		this.cHandler = cHandler;
	}

}
