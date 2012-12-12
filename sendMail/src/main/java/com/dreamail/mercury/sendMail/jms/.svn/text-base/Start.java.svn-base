package com.dreamail.mercury.sendMail.jms;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dreamail.mercury.netty.server.TelnetServerPipelineFactory;
import com.dreamail.mercury.sendMail.configure.SendMailPropertiesDeploy;
import com.dreamail.mercury.sendMail.sender.SmtpTransmitOperation;
import com.dreamail.mercury.sender.control.ConnectionControl;
import com.dreamail.mercury.smtp.SmtpSession;

public class Start {
	private static final Logger logger = LoggerFactory.getLogger(Start.class);
	public static Map<String, CopyOnWriteArrayList<SmtpSession>> session = new ConcurrentHashMap<String, CopyOnWriteArrayList<SmtpSession>>();

	static {
		new SendMailPropertiesDeploy().init();
		ConnectionControl.initQeue();
		ConnectionControl.initConnectionMaxs();
	}

	/**
	 * jar 启动入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				String server = args[i].toString();
				logger.info("Smtp server [" + server + "] is start!");
				if ("yahoo".equals(server)) {
					new ClassPathXmlApplicationContext(
							new String[] { "yahoo-jms.xml" });
				} else if ("gmail".equals(server)) {
					new ClassPathXmlApplicationContext(
							new String[] { "gmail-jms.xml" });
				} else if ("hotmail".equals(server)) {
					new ClassPathXmlApplicationContext(
							new String[] { "hotmail-jms.xml" });
				} else if ("others".equals(server)) {
					new ClassPathXmlApplicationContext(
							new String[] { "smspring-jms.xml" });
				} else if ("smtp".equals(server)) {
					smtpServerStart();
					smtpSenderTimer();
				} else {
					return;
				}
			}
		} else {
			new ClassPathXmlApplicationContext(new String[] { "yahoo-jms.xml" });
			new ClassPathXmlApplicationContext(new String[] { "gmail-jms.xml" });
			new ClassPathXmlApplicationContext(
					new String[] { "hotmail-jms.xml" });
			new ClassPathXmlApplicationContext(
					new String[] { "smspring-jms.xml" });
		}
	}

	/**
	 * Netty服务端初始化
	 */
	private static void smtpServerStart() {
		int port = Integer.parseInt(SendMailPropertiesDeploy.getConfigureMap()
				.get("port"));
		// int smtpport = 25;
		String ip = "";
		// NettyServer start
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new TelnetServerPipelineFactory());
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.bind(new InetSocketAddress(port));

		// NettyServer test start
		// ServerBootstrap bootstrap_smtp = new ServerBootstrap(
		// new NioServerSocketChannelFactory(Executors
		// .newCachedThreadPool(), Executors.newCachedThreadPool()));
		// bootstrap_smtp
		// .setPipelineFactory(new TelnetServerSmtpPipelineFactory());
		// bootstrap_smtp.setOption("child.tcpNoDelay", true);
		// bootstrap_smtp.setOption("child.keepAlive", true);
		// bootstrap_smtp.bind(new InetSocketAddress(smtpport));

		try {
			ip = getIp();
			if (ip == null) {
				InetAddress inet = InetAddress.getLocalHost();
				ip = inet.getHostAddress();
			}
		} catch (SocketException e) {
			logger.error("SocketException" + e);
		} catch (UnknownHostException e) {
			logger.error("UnknownHostException" + e);
		}
		logger.info("Netty Listen Port: " + port + ", ip is:" + ip);
		// logger.info("Netty Listen Port: " + smtpport + ", ip is:" + ip);
	}

	/**
	 * linux getIP
	 * 
	 * @return
	 * @throws SocketException
	 */
	private static String getIp() throws SocketException {
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface
				.getNetworkInterfaces();
		InetAddress ip = null;
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = (NetworkInterface) netInterfaces
					.nextElement();
			ip = (InetAddress) ni.getInetAddresses().nextElement();
			if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
					&& ip.getHostAddress().indexOf(":") == -1) {
				System.out.println("ip=" + ip.getHostAddress());
				return ip.getHostAddress();
			} else {
				ip = null;
			}
		}
		return null;

	}

	/**
	 * 启动定时器开始等待队列中的账号的发送邮件任务
	 */
	private static void smtpSenderTimer() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				// SMTP
				try {
					new SmtpTransmitOperation().sendMail();
				} catch (MessagingException e) {
					ConnectionControl.yahooReduce();
					logger.error("smtpSenderTimer---------" + e);
				}
			}
		}, 0, 1 * 1000);
	}

}
