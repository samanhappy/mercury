package com.clickoo.clickooImap.netty.client;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.clickoo.clickooImap.domain.IdleMessage;
import com.clickoo.clickooImap.server.cache.ServerAccountsCache;
import com.clickoo.clickooImap.threadpool.impl.ConnectController;
import com.clickoo.clickooImap.utils.CITools;

public class MsgClientHandler extends SimpleChannelUpstreamHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(MsgClientHandler.class);
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		logger.error("-----------Exception-------------"+e.getCause());
		e.getChannel().close();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		String message = (String) e.getMessage();
		logger.info("messageReceived:"+message);
		if(message.startsWith("String##")){
			//接受到master发的消息，并对此进行处理
			JSONObject json = JSONObject.fromObject(message.split("String##")[1]);
			logger.info("-----------------------"+json.toString());
			Iterator it = json.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				JSONObject object2 =  JSONObject.fromObject(json.get(key));
				logger.info("Server ["+CITools.getLocalServerIp()+"] get new IDLE ["+object2.toString()+"] by key["+key+"]");
				IdleMessage idleMessage = new IdleMessage();
				idleMessage.setAid(object2.getLong("aid"));
				idleMessage.setAccountName(object2.getString("accountName"));
				idleMessage.setAccountPwd(object2.getString("accountPwd"));
				idleMessage.setFlag(object2.getInt("flag"));
				idleMessage.setType(object2.getInt("type"));
				logger.info("Receive idleMessage from Master :Aid ["+idleMessage.getAid()+"] accountName ["+idleMessage.getAccountName()+"]");
				ConnectController.addIdleConnect(idleMessage);
			}
		}else if(message.startsWith("Map##")){
			JSONObject json = JSONObject.fromObject(message.split("Map##")[1]);
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
					idleMessage.setAccountPwd(object2.getString("accountPwd"));
					idleMessage.setFlag(object2.getInt("flag"));
					idleMessage.setType(object2.getInt("type"));
					map2.put(idleMessage.getAccountName(), idleMessage);
				}
				map.put(string, map2);
			}
	    	logger.info("Receive accountsMap from Master ["+map+"]");
			ServerAccountsCache.accountsCache = map;
		}
	}
}
