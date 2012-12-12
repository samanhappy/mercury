package com.dreamail.mercury.sender.control;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.dal.dao.SendServerDao;
import com.dreamail.mercury.pojo.Clickoo_mail_send_server;

public class ConnectionControl {
	private static final Logger logger = LoggerFactory
			.getLogger(ConnectionControl.class);

	public static ConcurrentHashMap<String, Integer> connectionMaxs = new ConcurrentHashMap<String, Integer>();
	public static BlockingQueue<SmtpQeueObj> smtpSendersQeue = null;
	public static BlockingQueue<QeueObj> yahooQeue = null;
	public static BlockingQueue<QeueObj> gmailQeue = null;
	public static BlockingQueue<QeueObj> hotmailQeue = null;
	public static BlockingQueue<QeueObj> othersQeue = null;
	public static int yahooCurrentConnects = 0;
	public static int gmailCurrentConnects = 0;
	public static int hotmailCurrentConnects = 0;
	public static int othersCurrentConnects = 0;

	/**
	 * 队列初始化
	 */
	public static void initQeue() {
		int count = Integer.parseInt(PropertiesDeploy.getConfigureMap().get(
				"connect_default"));
		logger.info("qeue default=====" + count);
		// SMTP server
		ConnectionControl.smtpSendersQeue = new ArrayBlockingQueue<SmtpQeueObj>(
				count);
		// old sender
		ConnectionControl.yahooQeue = new ArrayBlockingQueue<QeueObj>(count);
		ConnectionControl.gmailQeue = new ArrayBlockingQueue<QeueObj>(count);
		ConnectionControl.hotmailQeue = new ArrayBlockingQueue<QeueObj>(count);
		ConnectionControl.othersQeue = new ArrayBlockingQueue<QeueObj>(count);
	}

	/**
	 * 增加服务器当前连接数
	 * 
	 * @param server
	 * @param connectNum
	 */
	public static synchronized void yahooAdd() {
		yahooCurrentConnects++;
		logger.info(">>>>Add<<<<yahooCurrentConnects [ " + yahooCurrentConnects
				+ " ]");
	}

	public static synchronized void gmailAdd() {
		gmailCurrentConnects++;
		logger.info(">>>>Add<<<<gmailCurrentConnects [ " + gmailCurrentConnects
				+ " ]");
	}

	public static synchronized void hotmailAdd() {
		hotmailCurrentConnects++;
		logger.info(">>>>Add<<<<hotmailCurrentConnects [ "
				+ yahooCurrentConnects + " ]");
	}

	public static synchronized void othersAdd() {
		othersCurrentConnects++;
		logger.info(">>>>Add<<<<othersCurrentConnects [ "
				+ othersCurrentConnects + " ]");
	}

	/**
	 * 减少服务器当前连接数
	 * 
	 * @param server
	 * @param connectNum
	 */
	public static synchronized void yahooReduce() {
		yahooCurrentConnects--;
		logger.info(">>>>Reduce<<<<yahooCurrentConnects[ "
				+ yahooCurrentConnects + "] ");
	}

	public static synchronized void gmailReduce() {
		gmailCurrentConnects--;
		logger.info(">>>>Reduce<<<<gmailCurrentConnects[ "
				+ gmailCurrentConnects + "] ");
	}

	public static synchronized void hotmailReduce() {
		hotmailCurrentConnects--;
		logger.info(">>>>Reduce<<<<hotmailCurrentConnects[ "
				+ hotmailCurrentConnects + "] ");
	}

	public static synchronized void othersReduce() {
		othersCurrentConnects--;
		logger.info(">>>>Reduce<<<<othersCurrentConnects[ "
				+ othersCurrentConnects + "] ");
	}

	/**
	 * 获取各种服务器的最大连接数
	 */
	public static void initConnectionMaxs() {
		List<Clickoo_mail_send_server> sendServerList = new SendServerDao()
				.getAllSendServer();
		if (sendServerList != null) {
			for (Clickoo_mail_send_server sendServer : sendServerList) {
				if (sendServer.getName().startsWith("yahoo")
						|| sendServer.getName().startsWith("ymail")
						|| sendServer.getName().startsWith("rocketmail")) {
					connectionMaxs.put("yahoo", Integer
							.parseInt(PropertiesDeploy.getConfigureMap().get(
									"connect_yahoo")));
				} else if (sendServer.getName().startsWith("hotmail")
						|| sendServer.getName().startsWith("live")
						|| sendServer.getName().startsWith("msn")) {
					connectionMaxs.put("hotmail", Integer
							.parseInt(PropertiesDeploy.getConfigureMap().get(
									"connect_hotmail")));
				} else if (sendServer.getName().startsWith("gmail")) {
					connectionMaxs.put(sendServer.getName().split("[.]")[0],
							Integer.parseInt(PropertiesDeploy.getConfigureMap()
									.get("connect_gmail")));
				} else {
					connectionMaxs.put("others", Integer
							.parseInt(PropertiesDeploy.getConfigureMap().get(
									"connect_others")));
				}
			}
			logger.info("after init connectionMaxs : " + connectionMaxs);
		}else{
			logger.error("initConnectionMaxs error!!!");
			return;
		}
	}

}
