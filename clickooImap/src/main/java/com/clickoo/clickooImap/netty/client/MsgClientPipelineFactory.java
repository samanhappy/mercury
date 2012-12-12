package com.clickoo.clickooImap.netty.client;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

import com.clickoo.clickooImap.netty.server.MsgEncoder;

public class MsgClientPipelineFactory implements ChannelPipelineFactory{
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = pipeline();
		pipeline.addLast("encoder", new MsgEncoder());
		pipeline.addLast("decoder", new MsgDecoder());
		pipeline.addLast("handler", new MsgClientHandler());
		return pipeline;
	}
}
