package com.dreamail.mercury.mail.hearbeat;

import java.util.TimerTask;
import net.sf.json.JSONObject;
import org.jboss.netty.channel.Channel;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.mail.jms.ReceiveMain;

import ch.qos.logback.classic.Logger;

public class HearbeatTimerTask extends TimerTask {
	private static final Logger logger = (Logger) LoggerFactory
			.getLogger(HearbeatTimerTask.class);

	// public long counter = 0;
	@Override
	public void run() {
		Channel channel = ReceiveMain.getChannel();
		if(channel == null){
			return;
		}
		JSONObject obj = new JSONObject();
		obj.put("state", ReceiveMain.getClientState());
		obj.put("ip", ReceiveMain.getIp());
		logger.info("state:"+ReceiveMain.getClientState());
//		obj.put("accountNum", "3000");
		channel.write(obj.toString() + "\r\n");
	}

}
