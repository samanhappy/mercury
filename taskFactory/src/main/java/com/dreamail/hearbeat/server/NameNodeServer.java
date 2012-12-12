package com.dreamail.hearbeat.server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dreamail.config.TaskProperties;
import com.dreamail.data.ListenerMemoryData;
import com.dreamail.handle.SendMailFailHandler;
import com.dreamail.handle.UserOfflineHandlerTimer;
import com.dreamail.hearbeat.task.TaskAllocation;

public class NameNodeServer {
	private static final Logger logger = LoggerFactory
			.getLogger(NameNodeServer.class);

	private static int port = 0;

	private static long intervalTime = 0l;

	static {
		new TaskProperties().init();
		port = Integer.parseInt(TaskProperties.getConfigureMap().get(
				"heart_port"));
		intervalTime = Long.parseLong(TaskProperties.getConfigureMap().get(
				"heart_intervalTime"));
		TaskAllocation.allAccountTask();
	}

	private static String ip = null;

	protected static ConcurrentHashMap<String, Heartbeat> serverState = new ConcurrentHashMap<String, Heartbeat>();

	public static void main(String[] args) {

		// 获取本机IP
		initLocalIp();

		// 启动心跳监听
		startHeartListener();

		// 启动心跳检测
		new java.util.Timer(false).schedule(new TimeTaskServer(), 0,
				intervalTime);

		// 启动reload检测
		new Thread(new ReloadTask()).start();

		new ClassPathXmlApplicationContext(new String[] { "quartz-spring.xml",
				"taskspring-jms.xml" });

		// 用户下线账号处理
		new UserOfflineHandlerTimer().start();

		// 删除内存表数据
		new ListenerMemoryData().start();

		// 失败邮件重发任务
		SendMailFailHandler.startSenderTimer();

	}

	private static void startHeartListener() {
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		Timer timer = new HashedWheelTimer();
		bootstrap.setPipelineFactory(new TelnetPipelineFactory(timer));
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.bind(new InetSocketAddress(port));
		logger.info("Netty Listen Port: " + port + "," + " ip is:" + ip);
	}

	/**
	 * 获取本机IP.
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static void initLocalIp() {
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			logger.error("fail to get IP address", e);
		}
	}
}
