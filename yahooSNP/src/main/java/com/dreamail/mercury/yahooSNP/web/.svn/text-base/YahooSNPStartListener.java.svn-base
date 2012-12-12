package com.dreamail.mercury.yahooSNP.web;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;

import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.qos.logback.classic.Logger;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.dao.YahoosnpMessageDao;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_yahoosnp_message;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.YahooSNPConstants;
import com.dreamail.mercury.yahooSNP.SNPContext;
import com.dreamail.mercury.yahooSNP.impl.EmailSubscribeImpl;

@WebListener
public class YahooSNPStartListener extends HttpServlet implements
		ServletContextListener {

	private static final long serialVersionUID = -4044398951092991822L;
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(YahooSNPStartListener.class);
	private ClassPathXmlApplicationContext appContext;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("receiveMessage Listener start.....");
		new PropertiesDeploy().init();
		appContext = new ClassPathXmlApplicationContext(
				new String[] { "spring-jms.xml" });
		/*
		 * 暂时不需要
		 */
		// 查询数据库中所有yahoo账号
		List<Clickoo_mail_account> list = new AccountDao()
				.getAllValidAccountListByType(Constant.ACCOUNT_YAHOOSNP_TYPE);
		List<Long> aids = new YahoosnpMessageDao().selectAllSNPAid();
		for (Clickoo_mail_account account : list) {
			if (!aids.contains(Long.valueOf(account.getId()))) {
				SNPContext context = new SNPContext();
				context.setAid(account.getId());
				context.setLoginId(JSONObject.fromObject(account.getInCert())
						.getString(Constant.LOGINID));
				if (JSONObject.fromObject(account.getInCert()).containsKey(
						Constant.PWD)) {
					context.setPassword(JSONObject.fromObject(
							account.getInCert()).getString(Constant.PWD));
				}
				if (JSONObject.fromObject(account.getInCert()).containsKey(
						Constant.TOKEN)) {
					context.setToken(JSONObject.fromObject(account.getInCert())
							.getString(Constant.TOKEN));
				}
				boolean success = EmailSubscribeImpl.getInstance().subscribe(
						context);// 订阅
				if (success) {
					Clickoo_yahoosnp_message cym = null;
					cym = new Clickoo_yahoosnp_message();
					cym.setAid(account.getId());
					cym.setSubId(context.getSubId());
					cym.setUserId(context.getUserId());
					cym.setStatus(YahooSNPConstants.ONLINE_STATUS);// 设置为当前在线
					new YahoosnpMessageDao().insertMessage(cym);// 更新数据库
				} else {
					logger.info("failed to subscribed for account:"
							+ account.getId());
					/*
					 * 此处可能需要一些处理.
					 */
				}
			} else {
				logger.info("account by id:" + account.getId()
						+ " has already been subscripted");
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("receiveMessage Listener end.....");
		appContext.destroy();
		appContext.close();
	}

}
