package com.dreamail.hearbeat.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.jms.JMSException;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.config.TaskProperties;
import com.dreamail.hearbeat.task.TaskAllocation;

public class ReloadTask implements Runnable {

	private static final Logger logger = LoggerFactory
			.getLogger(ReloadTask.class);
	private static long reloadTime = Long.parseLong(TaskProperties.getConfigureMap().get("reload_time"));
	public void run() {
		while (true) {
			if (NameNodeServer.serverState.size() > 0) {
				boolean isReload = false;
				boolean isRun = false;
				Iterator<Entry<String, Heartbeat>> it = NameNodeServer.serverState
						.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, Heartbeat> entry = (Entry<String, Heartbeat>) it
							.next();
					Heartbeat beat = entry.getValue();
					if (beat.getState().equalsIgnoreCase("reload")) {
						isReload = true;
					} else if (beat.getState().equalsIgnoreCase("run")) {
						isRun = true;
					}
				}
				if (isReload && !isRun) {
					
					/**
					 * 全部都是reload状态,说明所有dataNode全部reload完成 NameNode分配任务
					 */
					logger.info("dataNode all reload of the end, start task distribution.");
					
					TaskAllocation.taskReload();
					JSONObject json = new JSONObject();
					json.put("state", "run");
					com.dreamail.jms.JmsSender.sendTopicMsg(json.toString(), "stateTopic");
					changeReload2Run();
				}

			} else {
				logger.info("no client...");
			}
			try {
				Thread.sleep(reloadTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void changeReload2Run(){
		if (NameNodeServer.serverState.size() > 0) {
			Iterator<Entry<String, Heartbeat>> it = NameNodeServer.serverState
			.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Heartbeat> entry = (Entry<String, Heartbeat>) it
				.next();
				Heartbeat beat = entry.getValue();
				if(beat.getState().equalsIgnoreCase("reload")){
					beat.setState("run");
					entry.setValue(beat);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Heartbeat beat = new Heartbeat();
		beat.setIp("10.11.1.112");
		beat.setState("reload");
		NameNodeServer.serverState.put("10.11.1.112", beat);
		ReloadTask task = new ReloadTask();
		task.changeReload2Run();
		Iterator<Entry<String, Heartbeat>> it = NameNodeServer.serverState
		.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Heartbeat> entry = (Entry<String, Heartbeat>) it
			.next();
			Heartbeat beat1 = entry.getValue();
			System.out.println(beat1.getState());
		}
	}
}
