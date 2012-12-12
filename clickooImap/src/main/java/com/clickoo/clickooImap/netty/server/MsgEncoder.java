package com.clickoo.clickooImap.netty.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
@SuppressWarnings("deprecation")
@ChannelPipelineCoverage("all")
public class MsgEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		if (!(msg instanceof String)) {
			return msg;
		}
		String res = (String) msg;
		byte[] data = res.getBytes();
		int dataLength = data.length;
		ChannelBuffer buf = ChannelBuffers.dynamicBuffer();
		buf.writeInt(dataLength);
		buf.writeBytes(data);
		return buf;
	}

}
