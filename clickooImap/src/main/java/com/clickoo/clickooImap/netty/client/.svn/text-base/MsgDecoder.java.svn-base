package com.clickoo.clickooImap.netty.client;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class MsgDecoder extends FrameDecoder {
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		if (buffer.readableBytes() < 4) {
			return null;
		}
		int dataLength = buffer.getInt(buffer.readerIndex());
		if (buffer.readableBytes() < dataLength + 4) {
			return null;
		}

		buffer.skipBytes(4);
		byte[] decoded = new byte[dataLength];
		
		buffer.readBytes(decoded);
		String msg = new String(decoded);
		return msg;

	}

}
