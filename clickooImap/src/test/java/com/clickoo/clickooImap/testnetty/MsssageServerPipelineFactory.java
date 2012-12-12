package com.clickoo.clickooImap.testnetty;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

public class MsssageServerPipelineFactory implements ChannelPipelineFactory {

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();
		pipeline.addLast("encoder", new MsssageDecoder());
		pipeline.addLast("decoder", new MsssageEncoder());
		pipeline.addLast("handler", new MsssageServerHandler());
		return pipeline;
	}

}
