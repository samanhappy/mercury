package com.dreamail.mercury.talaria.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;

import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.qos.logback.classic.Logger;

import com.dreamail.mercury.cache.MemCachedManager;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.talaria.threadpool.JMSHandleThreadPool;
import com.dreamail.mercury.util.SystemMessageUtil;

@SuppressWarnings("serial")
@WebListener
public class ReceiveMessageListener extends HttpServlet implements
		ServletContextListener {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(ReceiveMessageListener.class);
	private ClassPathXmlApplicationContext appContext;
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("receiveMessage Listener start.....");
		logger.info("local MAC is:" + SystemMessageUtil.getLocalMac());
		MemCachedManager.getInstance().init();
		new PropertiesDeploy().init();
		JMSHandleThreadPool.init();
		appContext = new ClassPathXmlApplicationContext(new String[] {"spring-jms.xml"});
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("receiveMessage Listener end.....");
		JMSHandleThreadPool.shutdown();
		appContext.destroy();
		appContext.close();
	}

}
