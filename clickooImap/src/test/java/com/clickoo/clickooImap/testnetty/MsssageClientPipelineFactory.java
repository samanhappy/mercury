package com.clickoo.clickooImap.testnetty;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

public class MsssageClientPipelineFactory implements ChannelPipelineFactory{
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = pipeline();
		pipeline.addLast("encoder", new MsssageDecoder());
		pipeline.addLast("decoder", new MsssageEncoder());
		pipeline.addLast("handler", new MsssageClientHandler());
		return pipeline;
	}
}
