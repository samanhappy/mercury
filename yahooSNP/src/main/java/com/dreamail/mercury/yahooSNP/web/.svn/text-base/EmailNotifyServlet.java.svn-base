package com.dreamail.mercury.yahooSNP.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.YahoosnpIdsDao;
import com.dreamail.mercury.dal.dao.YahoosnpMessageDao;
import com.dreamail.mercury.pojo.Clickoo_yahoosnp_ids;
import com.dreamail.mercury.pojo.Clickoo_yahoosnp_message;
import com.dreamail.mercury.util.YahooSNPConstants;
import com.dreamail.mercury.yahooSNP.SNPContext;
import com.dreamail.mercury.yahooSNP.YahooNoticeRequest;
import com.dreamail.mercury.yahooSNP.YahooXStreamParser;
import com.dreamail.mercury.yahooSNP.impl.EmailSubscribeImpl;
import com.dreamail.mercury.yahooSNP.jms.JMSMessageSender;

/**
 * Servlet implementation class EmailNotifyServlet
 */
public class EmailNotifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory
			.getLogger(EmailNotifyServlet.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String requestStr = parseRequestStr(request);
		YahooNoticeRequest ynr = YahooXStreamParser
				.xml2YahooNoticeRequestMessage(requestStr);
		if (ynr != null
				&& ynr.getMailnotification().getMessage_insert() != null) {
			long aid = Long.valueOf(ynr.getMailnotification().getNotifyInfo());
			String uuid = ynr.getMailnotification().getMessage_insert()
					.getMsg().getUid();
			String subId = ynr.getMailnotification().getSubId();
			/*
			 * folder后面可能会用到 String folder =
			 * ynr.getMailnotification().getMessage_insert()
			 * .getMsg().getFolder();
			 */
			Clickoo_yahoosnp_message cym = new YahoosnpMessageDao()
					.selectByAid(aid);
			if (cym == null) {
				logger.info("none exits account[" + aid + "], do nothing");
			} else if (YahooSNPConstants.OFFLINE_STATUS == cym.getStatus()) {
				// 下线 保存uuid
				if (cym.getMaxuuid() == 0) {
					Clickoo_yahoosnp_ids cyi = new Clickoo_yahoosnp_ids();
					cyi.setAid(aid);
					cyi.setUuid(uuid);
					new YahoosnpIdsDao().insertUuid(cyi);
				} else {
					new YahoosnpIdsDao().insertUuids(aid, cym.getMaxuuid() + 1,
							Long.valueOf(uuid) + 1);
				}
				if (cym.getMaxuuid() == 0
						|| Long.valueOf(uuid) > cym.getMaxuuid()) {
					cym.setMaxuuid(Long.valueOf(uuid));
					new YahoosnpMessageDao().updateMaxUuid(cym);
				}
				logger.info("accout is offline, save uuid into database");
			} else if (YahooSNPConstants.ONLINE_STATUS == cym.getStatus()) {
				if (subId.equals(String.valueOf(cym.getSubId()))) {
					// 通知收邮件模块
					if (cym.getMaxuuid() == 0) {
						JMSMessageSender.sendNewMailMessage(aid, uuid);
					} else {
						JMSMessageSender.sendNewMailMessages(aid, cym.getMaxuuid() + 1,
								Long.valueOf(uuid) + 1);
						new YahoosnpIdsDao().insertUuids(aid, cym.getMaxuuid() + 1,
								Long.valueOf(uuid) + 1);
					}
					if (cym.getMaxuuid() == 0
							|| Long.valueOf(uuid) > cym.getMaxuuid()) {
						cym.setMaxuuid(Long.valueOf(uuid));
						new YahoosnpMessageDao().updateMaxUuid(cym);
					}
				} else {
					if (cym.getUserId() != null) {
						SNPContext context = new SNPContext();
						context.setAid(aid);
						context.setSubId(Long.valueOf(subId));
						context.setUserId(cym.getUserId());
						EmailSubscribeImpl.getInstance().unsubscribe(context);// 取消订阅
						logger.info("not same sub id, unsubscribe for this notice");
					}
				}
			}
		} else if (ynr != null
				&& ynr.getMailnotification().getMessage_delete() != null) {
			long aid = Long.valueOf(ynr.getMailnotification().getNotifyInfo());
			String uuid = ynr.getMailnotification().getMessage_delete()
					.getMsg().getUid();
			// 通知IMAP-IDLE模块
			JMSMessageSender.sendDeleteMailMessage(aid, uuid);
		} else if (ynr != null
				&& ynr.getMailnotification().getMessage_read() != null) {
			logger.info("it is read message, no handle now");
		} else {
			logger.error("not handle this location");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	/**
	 * 获取请求内容.
	 * 
	 * @param request
	 * @return
	 */
	private String parseRequestStr(HttpServletRequest request) {
		BufferedReader br = null;
		StringBuffer sb = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					(ServletInputStream) request.getInputStream(), "utf-8"));
			String line = null;
			sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			logger.error("failed to parse request", e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				logger.error("failed to parse request", e);
			}
		}
		if (sb != null) {
			return sb.toString().trim();
		}
		return null;
	}

}
