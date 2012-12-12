package com.clickoo.clickooImap.testnetty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsssageDecoder extends FrameDecoder {
	private static final Logger logger = LoggerFactory
			.getLogger(MsssageDecoder.class);

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
		logger.info(dataLength+"---------------------------"+msg);
		return msg;

	}

}
