package com.clickoo.clickooImap.testnetty1;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SuppressWarnings("deprecation")
@ChannelPipelineCoverage("all")
public class TimeEncoder extends SimpleChannelHandler{
	private final static Logger logger = LoggerFactory.getLogger(TimeEncoder.class);

	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e){
		logger.info("into TimeEncoder--------------------");
		try{
			UnixTime time = (UnixTime) e.getMessage();
			logger.info("time-----------------"+time);
			ChannelBuffer buf = ChannelBuffers.buffer(4);
			buf.writeInt(time.getValue());
			Channels.write(ctx, e.getFuture(), buf);
		}catch(Exception exception){
			System.out.println(exception);
		}		
	}

}
