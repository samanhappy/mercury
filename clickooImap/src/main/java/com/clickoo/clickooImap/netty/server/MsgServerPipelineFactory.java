package com.clickoo.clickooImap.netty.server;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

import com.clickoo.clickooImap.netty.client.MsgDecoder;

public class MsgServerPipelineFactory implements ChannelPipelineFactory {

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();
		pipeline.addLast("encoder", new MsgEncoder());
		pipeline.addLast("decoder", new MsgDecoder());
		pipeline.addLast("handler", new MsgServerHandler());
		return pipeline;
	}

}
