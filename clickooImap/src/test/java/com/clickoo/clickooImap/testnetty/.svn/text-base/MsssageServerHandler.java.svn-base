package com.clickoo.clickooImap.testnetty;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.clickoo.clickooImap.domain.IdleMessage;

@SuppressWarnings("deprecation")
@ChannelPipelineCoverage("all")
public class MsssageServerHandler extends SimpleChannelUpstreamHandler { 
    @SuppressWarnings("unchecked")
	@Override 
    public void messageReceived( 
            ChannelHandlerContext ctx, MessageEvent e) { 
        if (!(e.getMessage() instanceof String)) { 
            return;
        } 
        String msg = (String) e.getMessage(); 
    	JSONObject json = JSONObject.fromObject(msg);
    	ConcurrentHashMap<String, ConcurrentHashMap<String, IdleMessage>> map = new ConcurrentHashMap<String, ConcurrentHashMap<String,IdleMessage>>();
    	Iterator<String> it = json.keySet().iterator();
    	while (it.hasNext()) {
			String string = (String) it.next();
			ConcurrentHashMap<String, IdleMessage> map2 = new ConcurrentHashMap<String, IdleMessage>();
			JSONObject object = JSONObject.fromObject(json.get(string));
			Iterator<String> iterator = object.keySet().iterator();
			while (iterator.hasNext()) {
				String string2 = (String) iterator.next();
				JSONObject object2 =  JSONObject.fromObject(object.get(string2));
				IdleMessage idleMessage = new IdleMessage();
				idleMessage.setAid(object2.getLong("aid"));
				idleMessage.setAccountName(object2.getString("accountName"));
				idleMessage.setAccountName(object2.getString("accountPwd"));
				map2.put(String.valueOf(idleMessage.getAid()), idleMessage);
			}
			map.put(string, map2);
		}
		System.err.println(map);
        e.getChannel().write(map.toString());//(2) 
    } 

    @Override 
    public void exceptionCaught( 
            ChannelHandlerContext ctx, ExceptionEvent e) { 
        e.getChannel().close(); 
    } 
} 

