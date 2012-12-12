package com.dreamail.mercury.petasos.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.qos.logback.classic.Logger;

import com.dreamail.mercury.config.PProperties;

public class TimerListener  extends HttpServlet
        implements ServletContextListener {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = (Logger) LoggerFactory
					.getLogger(TimerListener.class);
	
	private ClassPathXmlApplicationContext appContext;
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Listener start.....");
		new PProperties().init();
		appContext = new ClassPathXmlApplicationContext(new String[] {"petspring-jms.xml" });
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		appContext.destroy();
		appContext.close();
	}
	

}
