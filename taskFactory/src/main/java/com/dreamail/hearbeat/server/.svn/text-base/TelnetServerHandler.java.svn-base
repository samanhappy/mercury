package com.dreamail.hearbeat.server;

import java.util.Date;
import net.sf.json.JSONObject;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.util.JMSTypes;


public class TelnetServerHandler extends SimpleChannelUpstreamHandler{
	private static final Logger logger = LoggerFactory
	.getLogger(TelnetServerHandler.class);
	
	static final ChannelGroup channels = new DefaultChannelGroup();
	
	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		if (e instanceof ChannelStateEvent) {
			logger.info("ChannelStateEvent---------" + e.toString());
		}
		super.handleUpstream(ctx, e);
	}
	
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e){
		logger.info("new connected....");
//		ctx.getChannel().write("is ok" + "\r\n");
	}
	
	@Override
	public void messageReceived(final ChannelHandlerContext ctx,
			final MessageEvent e) {
		logger.info("in the received...");
		logger.info("channel's state : " + ctx.getChannel().isOpen());
		String command = (String) e.getMessage();
		logger.info("server receive:"+command);
		boolean isPush = false;
		if(command!=null && !"".equals(command)){
			JSONObject obj = JSONObject.fromObject(command);
			String ip = obj.get("ip").toString();
			String state = obj.getString("state").toString();
			if (ip != null && state != null && !"".equals(ip) && !"".equals(state)){
				Heartbeat beart = new Heartbeat();
				String NState = "stop";
				if(NameNodeServer.serverState.get(ip)!=null){
					if(NameNodeServer.serverState.get(ip).getState().equalsIgnoreCase("reload")){
						//如果是reload状态，则只更新时间，服务器将新的任务全部分配完成后，将所有reload状态改成run状态
						NState = "reload";
					}else if(NameNodeServer.serverState.get(ip).getState().equalsIgnoreCase("stop")){
						NState = "run";
						isPush = true;
					}else{
						NState = state;
					}
				}else{
					isPush = true;
					NState = state;
				}
				beart.setState(NState);
				beart.setIp(ip);
				beart.setTime(new Date());
				NameNodeServer.serverState.put(ip, beart);
				if(isPush){
					//向客户端发送reload请求
					logger.info("isPush is true......................");
					JSONObject json = new JSONObject();
					json.put("state", "reload");
					JmsSender.sendTopicMsg(json.toString(), JMSTypes.HB_STATE_TOPIC);
				}
			}
		}
		ctx.getChannel().write("server response" + "\r\n");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();
		logger.info("connect err...");
		e.getChannel().close();
		channels.remove(e.getChannel());
	}
	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		logger.info("close connect....");
		e.getChannel().close();
		channels.remove(e.getChannel());
	}
}
