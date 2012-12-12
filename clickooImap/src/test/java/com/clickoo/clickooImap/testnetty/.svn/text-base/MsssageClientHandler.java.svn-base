package com.clickoo.clickooImap.testnetty;


import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.clickoo.clickooImap.domain.IdleMessage;
@SuppressWarnings("deprecation")
@ChannelPipelineCoverage("all")
public class MsssageClientHandler extends SimpleChannelUpstreamHandler { 
    @Override 
    public void channelConnected( 
            ChannelHandlerContext ctx, ChannelStateEvent e) { 
    	ConcurrentHashMap<String, ConcurrentHashMap<String, IdleMessage>> map = new ConcurrentHashMap<String, ConcurrentHashMap<String,IdleMessage>>();
		ConcurrentHashMap<String, IdleMessage> map1 = new ConcurrentHashMap<String, IdleMessage>();
		IdleMessage idleMessage = new IdleMessage();
		idleMessage.setAid(1);
		map1.put("1", idleMessage);
		IdleMessage idleMessage2 = new IdleMessage();
		idleMessage2.setAid(2);
		map1.put("2", idleMessage2);
		IdleMessage idleMessage3 = new IdleMessage();
		idleMessage3.setAid(3);
		map1.put("3", idleMessage3);
		map.put("aa", map1);
		ConcurrentHashMap<String, IdleMessage> map2 = new ConcurrentHashMap<String, IdleMessage>();
		IdleMessage idleMessage4 = new IdleMessage();
		idleMessage4.setAid(4);
		map2.put("4", idleMessage4);
		IdleMessage idleMessage5 = new IdleMessage();
		idleMessage5.setAid(5);
		map2.put("5", idleMessage5);
		IdleMessage idleMessage6 = new IdleMessage();
		idleMessage6.setAid(6);
		map2.put("6", idleMessage6);
		map.put("bb", map2);
		Iterator<String> it = map.keySet().iterator();
		JSONObject json = new JSONObject();
		while (it.hasNext()) {
			String string = (String) it.next();
			json.put(string, map.get(string));
		}
		String message = json.toString();
		System.err.println(message);
		e.getChannel().write(message); 
    } 

    @Override 
    public void messageReceived( 
            ChannelHandlerContext ctx, MessageEvent e) { 
        // Send back the received message to the remote peer. 
        System.err.println("messageReceived send message "+e.getMessage()); 
        try { 
            Thread.sleep(1000*3); 
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        } 
        e.getChannel().write(e.getMessage()); 
    } 

    @Override 
    public void exceptionCaught( 
            ChannelHandlerContext ctx, ExceptionEvent e) { 
        // Close the connection when an exception is raised. 
        e.getChannel().close(); 
    } 
}
