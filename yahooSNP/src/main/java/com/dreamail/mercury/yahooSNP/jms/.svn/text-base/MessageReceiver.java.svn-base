package com.dreamail.mercury.yahooSNP.jms;

import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.YahoosnpIdsDao;
import com.dreamail.mercury.dal.dao.YahoosnpMessageDao;
import com.dreamail.mercury.pojo.Clickoo_yahoosnp_message;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.YahooSNPConstants;
import com.dreamail.mercury.yahooSNP.SNPContext;
import com.dreamail.mercury.yahooSNP.impl.EmailSubscribeImpl;

public class MessageReceiver {

	private static Logger logger = LoggerFactory
			.getLogger(MessageReceiver.class);

	public void onMessage(Object msg) {

		String msgStr = msg.toString();
		logger.info("message jms str is:" + msgStr);

		JSONObject json = null;

		try {
			json = JSONObject.fromObject(msgStr);
		} catch (Exception e) {
			logger.error("failed to parse json str");
			return;
		}

		if (!json.getString(JMSConstans.JMS_TYPE_KEY).equals(
				JMSConstans.JMS_YAHOOSNP_TYPE)) {
			logger.error("jms message type is error");
			return;
		}

		long aid = Long.valueOf(json.getString(JMSConstans.JMS_AID_KEY));
		String line = json.getString(JMSConstans.JMS_LINE_KEY);
		// 注册
		if (JMSConstans.JMS_ADDLINE_VALUE.equals(line)) {
			Clickoo_yahoosnp_message snpmsg = new YahoosnpMessageDao().selectByAid(aid);
			if (snpmsg != null) {
				logger.info("account has been subscribed, do nothing...");
				boolean update_flag = false;
				if (json.containsKey(JMSConstans.JMS_PTID_KEY) && snpmsg.getUserId() == null) {
					snpmsg.setUserId(json.getString(JMSConstans.JMS_PTID_KEY));
					logger.info("update ptid to database");
					update_flag = true;
				}
				if (snpmsg.getStatus() == YahooSNPConstants.OFFLINE_STATUS) {
					List<String> uuids = new YahoosnpIdsDao().getAllUuidByAid(aid);
					JMSMessageSender.sendNewMailMessage(aid, uuids);
					snpmsg.setStatus(YahooSNPConstants.ONLINE_STATUS);
					logger.info("set online status");
					update_flag = true;
				}
				if (update_flag) {
					new YahoosnpMessageDao().updateMessage(snpmsg);
				}
			} else {
				SNPContext context = new SNPContext();
				context.setAid(aid);
				context.setLoginId(json.getString(JMSConstans.JMS_LOGINID_KEY));
				if (json.containsKey(JMSConstans.JMS_TOKEN_KEY)
						&& json.containsKey(JMSConstans.JMS_PTID_KEY)) {
					context.setToken(json.getString(JMSConstans.JMS_TOKEN_KEY));
					context.setUserId(json.getString(JMSConstans.JMS_PTID_KEY));
				} else if (json.containsKey(JMSConstans.JMS_PASSWORD_KEY)) {
					// base64解密
					context.setPassword(new String(EmailUtils
							.base64TochangeByte(json
									.getString(JMSConstans.JMS_PASSWORD_KEY))));
				} else {
					logger.error("error account info");
					return;
				}

				boolean success = EmailSubscribeImpl.getInstance().subscribe(
						context);// 订阅
				if (success) {
					Clickoo_yahoosnp_message cym = null;
					cym = new Clickoo_yahoosnp_message();
					cym.setAid(aid);
					cym.setSubId(context.getSubId());
					cym.setUserId(context.getUserId());
					cym.setStatus(YahooSNPConstants.ONLINE_STATUS);// 设置为当前在线
					new YahoosnpMessageDao().insertMessage(cym);// 更新数据库
				} else {
					logger.info("failed to subscribed");
					/*
					 * p 此处可能需要一些处理.
					 */
				}
			}
		} else if (JMSConstans.JMS_ONLINE_VALUE.equals(line)) {

			Clickoo_yahoosnp_message cym = new YahoosnpMessageDao()
					.selectByAid(aid);
			// 如果用户上次为下线状态，把所有收到的uuid发送给收邮件模块
			if (cym != null
					&& cym.getStatus() == YahooSNPConstants.OFFLINE_STATUS) {
				List<String> uuids = new YahoosnpIdsDao().getAllUuidByAid(aid);
				JMSMessageSender.sendNewMailMessage(aid, uuids);
			}
			cym = new Clickoo_yahoosnp_message();
			cym.setAid(aid);
			cym.setStatus(YahooSNPConstants.ONLINE_STATUS);// 设置为上线
			new YahoosnpMessageDao().updateMessageStatus(cym);// 更新数据库

		} else if (JMSConstans.JMS_OFFLINE_VALUE.equals(line)) {
			Clickoo_yahoosnp_message cym = new Clickoo_yahoosnp_message();
			cym.setAid(aid);
			cym.setStatus(YahooSNPConstants.OFFLINE_STATUS);// 设置为下线
			new YahoosnpMessageDao().updateMessageStatus(cym);
		} else if (JMSConstans.JMS_DELETELINE_VALUE.equals(line)) {

			Clickoo_yahoosnp_message cym = new YahoosnpMessageDao()
					.selectByAid(aid);
			SNPContext context = new SNPContext();
			context.setAid(aid);
			context.setSubId(cym.getSubId());
			context.setUserId(cym.getUserId());
			EmailSubscribeImpl.getInstance().unsubscribe(context);// 取消订阅

			// 删除所有记录
			new YahoosnpMessageDao().deleteAllInfoByAid(aid);

		} else if (JMSConstans.JMS_MODIFYLINE_VALUE.equals(line)) {

			Clickoo_yahoosnp_message cym = new YahoosnpMessageDao()
					.selectByAid(aid);

			if (cym == null) {
				logger.error("not find aid");
				return;
			}

			// 取消订阅
			SNPContext context = new SNPContext();
			context.setAid(aid);
			context.setSubId(cym.getSubId());
			context.setUserId(cym.getUserId());
			EmailSubscribeImpl.getInstance().unsubscribe(context);

			
			if (json.containsKey(JMSConstans.JMS_TOKEN_KEY)
					&& json.containsKey(JMSConstans.JMS_PTID_KEY)) {
				context.setToken(json.getString(JMSConstans.JMS_TOKEN_KEY));
				context.setUserId(json.getString(JMSConstans.JMS_PTID_KEY));
			} else if (json.containsKey(JMSConstans.JMS_PASSWORD_KEY)) {
				// base64解密
				context.setPassword(new String(EmailUtils
						.base64TochangeByte(json
								.getString(JMSConstans.JMS_PASSWORD_KEY))));
			} else {
				logger.error("error account info");
				return;
			}
			// 重新订阅
			boolean success = EmailSubscribeImpl.getInstance().subscribe(
					context);
			if (success) {
				cym.setSubId(context.getSubId());
				cym.setUserId(context.getUserId());
				new YahoosnpMessageDao().updateMessage(cym);// 更新数据库
			}
		} else {
			logger.error("jms line value is error");
		}
	}
}
