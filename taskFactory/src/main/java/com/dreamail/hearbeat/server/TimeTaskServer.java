package com.dreamail.hearbeat.server;

import java.util.Date;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Map.Entry;
import javax.jms.JMSException;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.JmsSender;

public class TimeTaskServer extends TimerTask{
	
	private static final Logger logger = LoggerFactory
			.getLogger(TimeTaskServer.class);
	
	/**
	 * 心跳检测，将超时的DataNode设成stop状态 
	 */
	@Override
	public void run() {
		Iterator<Entry<String,Heartbeat>> it = NameNodeServer.serverState.entrySet().iterator();
		boolean ifReload = false;
		logger.info("check dataNode state...");
		while(it.hasNext()){
			Entry<String,Heartbeat> entry = (Entry<String,Heartbeat>) it.next();
			Heartbeat beat = entry.getValue();
			
			logger.info("-----------------------------------");
			logger.info("ip:"+beat.getIp());
			logger.info("state:"+beat.getState());
			logger.info("-----------------------------------");
			//如果已经是stop状态，则跳过
			if(beat.getState().equalsIgnoreCase("stop")){
				continue;
			}
			long t = new Date().getTime() - beat.getTime().getTime();
			if(t>=10000){
				logger.info("one server lose...IP: "+beat.getIp());
				beat.setState("stop");
				NameNodeServer.serverState.put(beat.getIp(), beat);
				ifReload = true;
			}
		}
		if(ifReload){
			JSONObject json = new JSONObject();
			json.put("state", "reload");
			JmsSender.sendTopicMsg(json.toString(), "stateTopic");
		}
	}
	
	public static void main(String[] args) {
		System.out.println(new Date().getTime());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(new Date().getTime());
	}
}
