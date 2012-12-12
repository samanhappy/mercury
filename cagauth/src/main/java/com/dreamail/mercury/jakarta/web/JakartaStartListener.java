package com.dreamail.mercury.jakarta.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;

import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.qos.logback.classic.Logger;

import com.dreamail.mercury.configure.CAGPropertiesDeploy;
import com.dreamail.mercury.configure.PropertiesDeploy;

@SuppressWarnings("serial")
@WebListener
public class JakartaStartListener extends HttpServlet implements
		ServletContextListener {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(JakartaStartListener.class);
	
	private ClassPathXmlApplicationContext appContext;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("jakarta Listener start.....");
		appContext = new ClassPathXmlApplicationContext(new String[] {"spring-jms.xml"});
		new PropertiesDeploy().init();
		CAGPropertiesDeploy.init();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		appContext.destroy();
		appContext.close();
		logger.info("jakarta Listener end.....");
	}

}
