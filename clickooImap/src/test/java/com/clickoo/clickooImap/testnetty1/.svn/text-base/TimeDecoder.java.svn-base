package com.clickoo.clickooImap.testnetty1;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 这里不再需要使用ChannelPipelineCoverage 的注解，因为FrameDecoder 总是被注解为“one”。
 * @author lwl
 *
 */
public class TimeDecoder extends FrameDecoder{
	private final static Logger logger = LoggerFactory.getLogger(TimeDecoder.class);
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		logger.info("into TimeDecoder-----------------");
		if(buffer.readableBytes()<4){
			return null;
		}
		UnixTime time = new UnixTime(buffer.readInt());
		System.out.println(time);
		return time;
	}
	
}
//public class TimeDecoder extends ReplayingDecoder {
//
//	@Override
//	protected Object decode(ChannelHandlerContext ctx, Channel channel,
//			ChannelBuffer buffer, Enum state) throws Exception {
//		return new UnixTime(buffer.readInt());
//	}
//	
//}
