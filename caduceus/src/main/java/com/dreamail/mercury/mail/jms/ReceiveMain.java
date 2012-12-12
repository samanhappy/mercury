package com.dreamail.mercury.mail.jms;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.mail.hearbeat.HearbeatTimer;
import com.dreamail.mercury.mail.hearbeat.TelnetClientPipelineFactory;
import com.dreamail.mercury.mail.jms.timer.AccountTaskThread;
import com.dreamail.mercury.mail.util.EmailConstant;
import com.dreamail.mercury.mail.util.EmailReceiveUtil;
import com.dreamail.mercury.pojo.Clickoo_task_factory;
import com.dreamail.mercury.receiver.config.CaduceusPropertiesDeploy;

public class ReceiveMain {
	
	public static final Logger logger = LoggerFactory
			.getLogger(ReceiveMain.class);
	public static Channel channel = null;

	//服务器IP
	public static String serverHost = null;
	//服务器端口
	public static int port;
	// 断线重链接口
	public static ClientBootstrap bootstrap;
    // 链接失败次数（暂时没有用）
	public static long counter = 0;
	// 本机IP地址
	public static String ip = null;
	// 客户端状态 stop 表示客户端死亡状态 reload 表示服务器要求客户端重启 run 表示客户端状态正常
	public static String clientState = EmailConstant.StatusConstant.STATUS_RUN;
	public static ConcurrentHashMap<String, Context> contextMap = null;//账号连接失败或可放到内存中操作，替代数据库更新。
	//密码、设备、hostname、黑白名单、角色变更都要得到通知 并更新
	
	public static boolean snpControl = false;
	
	public static boolean asControl = false;
	
	public static boolean idleControl = false;
	
	public static int keepTime;
	
	public static String getClientState() {
		return clientState;
	}

	public static synchronized void setClientState(String clientState) {
		ReceiveMain.clientState = clientState;
	}
	
	static {
		// common配置文件
		CaduceusPropertiesDeploy cpd = new CaduceusPropertiesDeploy();
		cpd.init();
		contextMap = new ConcurrentHashMap<String,Context>();
	}

	public synchronized static Channel getChannel() {
		if (channel != null && channel.isOpen() && channel.isConnected()) {
			return channel;
		} else {
			return getNChannel();
		}
	}

	public static void setChannel(Channel channel) {
		ReceiveMain.channel = channel;
	}

	public static Channel getNChannel() {
		logger.info("reload a new Channel....");
		// if(channel!=null){
		// channel.close();
		// }
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(
				serverHost, port));
		Channel ch = future.awaitUninterruptibly().getChannel();
		if (ch != null && ch.isOpen() && ch.isConnected()) {
			setChannel(ch);
		}
		return ch;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args!=null && args[0].equalsIgnoreCase("snp")){
			snpControl = true;
		}
		
		if(args!=null && args[1].equalsIgnoreCase("as")){
			asControl = true;
		}
		
		if(args!=null && args[2].equalsIgnoreCase("idle")){
			idleControl = true;
		}
//		keepTime = Integer.parseInt(args[2]);
		/*if (args != null && args.length > 0) {
			for (String arg : args) {
				initJmsReceiver(arg);
			}
		} else {
			new ClassPathXmlApplicationContext(
					new String[] { "cspring-jms.xml" });
		}*/
//		String serverHost = args[0];
//		serverHost = "10.11.1.112";
		new ClassPathXmlApplicationContext(
				new String[] { "cspring-jms.xml" });
		Clickoo_task_factory f = EmailReceiveUtil.getTaskFactory();
		serverHost = f.getMcvalue();
		port = Integer.parseInt(f.getMckey());
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newFixedThreadPool(1),
				Executors.newFixedThreadPool(1)));
		bootstrap.setPipelineFactory(new TelnetClientPipelineFactory());
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(
				serverHost, port));
		setChannel(future.awaitUninterruptibly().getChannel());
		new Thread(new HearbeatTimer()).start();
		new Thread(new AccountTaskThread()).start();
//		new Thread(new ConnectThread()).start();
	}
	
	/**
	 * 根据不同的参数启动不同的邮件收取服务.
	 * 
	 * @param arg
	 */
	public static void initJmsReceiver(String arg) {
		if ("yahoo".equals(arg)) {
			new ClassPathXmlApplicationContext(new String[] { "yahoo-jms.xml" });
		} else if ("gmail".equals(arg)) {
			new ClassPathXmlApplicationContext(new String[] { "gmail-jms.xml" });
		} else if ("hotmail".equals(arg)) {
			new ClassPathXmlApplicationContext(
					new String[] { "hotmail-jms.xml" });
		} else if ("other".equals(arg)) {
			new ClassPathXmlApplicationContext(new String[] { "other-jms.xml" });
		} else {
			logger.error("error argument:" + arg);
		}
	}
	
	/**
	 * linux getIP
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static String getIp() {

		if (ip != null) {
			return ip;
		}

		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();

			InetAddress address = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces
						.nextElement();
				address = (InetAddress) ni.getInetAddresses().nextElement();
				if (!address.isSiteLocalAddress()
						&& !address.isLoopbackAddress()
						&& address.getHostAddress().indexOf(":") == -1) {
					ip = address.getHostAddress();
				}
			}

			if (ip == null) {
				InetAddress inet = InetAddress.getLocalHost();
				ip = inet.getHostAddress();
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return ip;
	}
}
