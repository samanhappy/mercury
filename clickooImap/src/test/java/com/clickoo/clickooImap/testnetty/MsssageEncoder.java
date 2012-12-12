package com.clickoo.clickooImap.testnetty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SuppressWarnings("deprecation")
@ChannelPipelineCoverage("all")
public class MsssageEncoder extends OneToOneEncoder {
	private static final Logger logger = LoggerFactory
			.getLogger(MsssageEncoder.class);

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		logger.info("MsgEncoder---------------------"+msg.toString());
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
