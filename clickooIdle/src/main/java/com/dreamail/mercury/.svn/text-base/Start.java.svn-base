package com.dreamail.mercury;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dreamail.mercury.cache.VolumeCacheManager;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.netty.server.TelnetPipelineFactory;
import com.dreamail.mercury.netty.server.TelnetPipelineHttpsFactory;

public class Start {
	public static String ip = "";
	private static final Logger logger = LoggerFactory
	.getLogger(Start.class);
	
	private static final int httpsProt = 993;
	
	private static final int prot = 143;
	
	/**
	 * key ：帐号名称
	 * value ：保存会话的session
	 */
	public static  Map<String,CopyOnWriteArrayList<ImapSession>> session = new ConcurrentHashMap<String,CopyOnWriteArrayList<ImapSession>>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new PropertiesDeploy().init();
		new VolumeCacheManager().init();
		new ClassPathXmlApplicationContext(new String[] { "spring-jms.xml" });
		/**
		 * https 993 start
		 */
		ServerBootstrap bootstrap_Https = new ServerBootstrap(
				new NioServerSocketChannelFactory(Executors
						.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap_Https.setPipelineFactory(new TelnetPipelineHttpsFactory());
		bootstrap_Https.setOption("child.tcpNoDelay", true);
		bootstrap_Https.setOption("child.keepAlive", true);
		bootstrap_Https.bind(new InetSocketAddress(httpsProt));
		
		/**
		 * 8080 start
		 */
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(Executors
						.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new TelnetPipelineFactory());
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.bind(new InetSocketAddress(prot));
		try {
			ip = getIp();
			if (ip == null) {
				InetAddress inet = InetAddress.getLocalHost();
				ip = inet.getHostAddress();
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		logger.info("Netty Listen Port: "+httpsProt+"," + " ip is:" + ip);
		logger.info("Netty Listen Port: "+prot+"," + " ip is:" + ip);
	}

	/**
	 * linux getIP
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static String getIp() throws SocketException {
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
}
