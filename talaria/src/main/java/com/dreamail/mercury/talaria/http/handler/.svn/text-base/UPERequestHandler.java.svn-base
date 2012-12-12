package com.dreamail.mercury.talaria.http.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.MessageSender;
import com.dreamail.mercury.dal.dao.SendMessageDao;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.dal.service.UARelationService;
import com.dreamail.mercury.talaria.http.AsyncContextManager;
import com.dreamail.mercury.talaria.http.PMAsyncListener;
import com.dreamail.mercury.talaria.http.handler.task.SetDeleteMessageSignsTask;
import com.dreamail.mercury.talaria.xstream.UPEParserObject;
import com.dreamail.mercury.talaria.xstream.XStreamParser;
import com.dreamail.mercury.util.CAGConstants;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.RoleUtil;
import com.dreamail.mercury.util.UPEConstants;
import com.dreamail.talaria.memcached.AbstractCacheManager;
import com.dreamail.talaria.memcached.UPECacheManager;
import com.dreamail.talaria.memcached.UPECacheObject;
import com.dreamail.talaria.memcached.UPEMapping;

public class UPERequestHandler extends IRequestHandler {

	private Logger logger = LoggerFactory.getLogger(UPERequestHandler.class);

	private UPEParserObject upeObj;

	boolean isServiceDisable = false;

	public UPERequestHandler(HttpServletRequest request,
			HttpServletResponse response, String requestStr) {
		super(request, response, requestStr);
	}

	@Override
	public void handle() {
		// 解析请求内容，如果有IM响应并且已经返回，则直接结束请求.
		responseDone = parseRequestStr(requestStr);
		if (responseDone) {
			return;
		}

		// 判断是否有错误信息
		if (isServiceDisable) {
			logger.info("service is disabled");
			// 为了兼容IM的CAG请求，此处应该保存对象到缓存中
			if (IMEI != null && !"".equals(IMEI)) {
				logger
						.info("IMEI is not null, update to cache(for IM request)");

				// 处理之前未响应的请求
				AsyncContext acOld = AsyncContextManager.getInstance()
						.getContext(IMEI);
				if (acOld != null && acOld.getRequest().isAsyncStarted()) {
					logger.info("complete old request......");
					acOld.complete();
				}

				if (mapping == null) {
					mapping = new UPEMapping();
					mapping.setIMEI(IMEI);
				}

				mapping.setMac(mac);
			}
		} else {

			// 处理之前未响应的请求
			AsyncContext acOld = AsyncContextManager.getInstance().getContext(
					IMEI);
			if (acOld != null && acOld.getRequest().isAsyncStarted()) {
				logger.info("complete old request......");
				acOld.complete();
			}

			// 此处不需再获得IMEI的缓存对象，因为处理IM响应的时候已经获取了.

			if (cache == null) {
				cache = new UPECacheObject();
				cache.setUid(uid);
				cache
						.setDisable(RoleUtil
								.getRoleLevelByTitle(CAGConstants.COS_TITLE_DISABLE) == new UserDao()
								.getRolelevelByUid(Long.valueOf(uid)));
				cache.updateAidList();
				cache.updateInvalidAids();
				cache.updateMailSentNotices();

				// 设置用户自动上线
				new UARelationService().setUserAutoOnline(Long.valueOf(uid));
			}

			if (mapping == null) {
				mapping = new UPEMapping();
				mapping.setIMEI(IMEI);
				mapping.setUid(uid);

				// 用户上线.
				cache = new UPECacheObject();
				cache.setUid(uid);
				/*
				 * 此处三次查询数据库可以放在一起实现.
				 */
				cache
						.setDisable(RoleUtil
								.getRoleLevelByTitle(CAGConstants.COS_TITLE_DISABLE) == new UserDao()
								.getRolelevelByUid(Long.valueOf(uid)));
				cache.updateAidList();
				cache.updateInvalidAids();
				cache.updateMailSentNotices();
				// 设置用户自动上线
				new UARelationService().setUserAutoOnline(Long.valueOf(uid));
			} else if (!uid.equals(mapping.getUid())) {
				logger.info("uid in mapping cache:" + mapping.getUid()
						+ ",is not same as uid in request:" + uid + "...");
				/**
				 * 此处没有发送之前用户的下线通知.
				 */
				if (mapping.getUid() != null) {
					UPECacheManager.getLock(mapping.getUid());
					UPECacheManager.deleteCacheObject(mapping.getUid());
					UPECacheManager.removeLock(mapping.getUid());
				}

				mapping.setUid(uid);

				// 设置用户自动上线
				new UARelationService().setUserAutoOnline(Long.valueOf(uid));
			}

			// 保存请求时间戳
			mapping.setRequest_ts(String.valueOf(System.currentTimeMillis()));
			// 更新用户挂载mac地址
			mapping.setMac(mac);

			// 处理删除邮件通知
			responseDone = handleDeleteMessages(response);
			if (responseDone) {
				return;
			}

			// 处理上一次非普通通知，确保客户端收到
			responseDone = handleLastNotice(response);
			if (responseDone) {
				return;
			}

			// 处理CAG通知
			responseDone = handleCAGNotice(response);
			if (responseDone) {
				return;
			}

			// 处理新邮件通知
			responseDone = handleNewMessageNotice(response);
			if (responseDone) {
				return;
			}

			// 处理邮件发送状态通知
			responseDone = handleMailSentStateNotice(response);
			if (responseDone) {
				return;
			}

			// 处理账号异常通知
			responseDone = handleInvalidAccountNotice(response);
			if (responseDone) {
				return;
			}
			
			//处理账号删除通知
			responseDone = handleDeleteAccountNotice(response);
			if (responseDone) {
				return;
			}
		}

		if (IMEI == null) {
			logger.info("IMEI is null, set to a timestamp");
			IMEI = String.valueOf(System.currentTimeMillis());
		}
		if (uid == null) {
			logger.info("uid is null, set to a empty str");
			uid = "";
		}

		// 挂起请求
		logger.info("[" + IMEI + "] start suspend.....");
		AsyncContext ac = request.startAsync(request, response);
		ac.getRequest().setAttribute(UPEConstants.IMEI_KEY, IMEI);
		ac.getRequest().setAttribute(UPEConstants.SERVICE_STATE_KEY,
				isServiceDisable);
		ac.getRequest().setAttribute(UPEConstants.UID_KEY, uid);
		ac.setTimeout(suspendTime);
		ac.addListener(new PMAsyncListener());
		AsyncContextManager.getInstance().putContext(IMEI, ac);
	}

	/**
	 * 解析请求体.
	 * 
	 * @param requestStr
	 * @return
	 */
	private boolean parseRequestStr(String requestStr) {
		try {
			upeObj = XStreamParser.xml2UPEObject(requestStr);
		} catch (Exception e) {
			logger.error("failed to parse upe xml!!!", e);
			isServiceDisable = true;
			return false;
		}

		// 增加uid后的upe协议处理
		if (upeObj != null) {

			// 首先处理IM消息
			if (upeObj.getIM() != null && upeObj.getIM().getUuid() != null) {
				MessageSender.sendIMQueueMessage(XStreamParser
						.IMObject2Xml(upeObj.getIM()));
			}

			IMEI = upeObj.getIMEI();
			// 判断是否有IMEI
			if (IMEI == null || "".equals(IMEI)) {
				logger.info("there is IMEI");
				isServiceDisable = true;
				return false;
			}

			// 取mapping对象,如果有IM响应，返回true直接返回.
			UPECacheManager.getSimpleMappingLock(IMEI);
			mapping = UPECacheManager.getMappingObject(IMEI);
			if (mapping != null && mapping.getIMNotice() != null) {
				logger.info("handset has IM response, send and return...");
				setResponseContent(IMHandler.getIMResponseStr(IMEI, mapping
						.getIMNotice()));
				mapping.setIMNotice(null);
				UPECacheManager.setMappingObject(mapping);
				UPECacheManager.removeMappingLock(IMEI);
				return true;
			}

			if (upeObj.getPushMail() != null) {
				uid = upeObj.getPushMail().getUid();
				// 判断是否有uid
				if (uid == null || "".equals(uid)) {
					logger.info("there is no uid");
					isServiceDisable = true;
					return false;
				} else {
					// 取数据对象
					UPECacheManager.getSimpleLock(uid);
					cache = UPECacheManager.getCacheObject(uid);
					if (cache != null && cache.isDisable()) {
						logger.info("user service is disabled");
						isServiceDisable = true;
						return false;
					}
				}
			}
		} else {
			isServiceDisable = true;
			return false;
		}

		return false;
	}

	/**
	 * 处理上一次非普通通知.
	 * 
	 * @param response
	 * @return
	 */
	private boolean handleLastNotice(HttpServletResponse response) {
		String ts = cache.getLast_notice_ts();
		String state = cache.getLast_notice_state();
		if (ts != null && !ts.equals("")) {
			if (ts.equals(upeObj.getPushMail().getTimestamp())) {
				if (UPEConstants.UPE_GETCLIENTCONFIG_STATE.equals(state)) {
					if (upeObj.getPushMail().getSettings() != null) {
						JSONObject json = new JSONObject();
						json.put(JMSConstans.JMS_TYPE_KEY,
								CAGConstants.CAG_GETCLIENTCONFIG_NOTIF);
						json.put(JMSConstans.JMS_UID_KEY, uid);
						json.put(JMSConstans.CAG_SETTINGS_KEY, XStreamParser
								.CAGSettingsObject2Xml(upeObj.getPushMail()
										.getSettings()));
						json.put(JMSConstans.CAG_RESPONSE_CODE,
								CAGConstants.CAG_SUCCESS_CODE);
						json.put(JMSConstans.CAG_RESPONSE_STATUS, "Success");
						MessageSender.sendCAGResponseMessage(json.toString());
					} else {
						logger.info("there is no setting info for IMEI[" + IMEI
								+ "] ,send again...");
						upeObj.getPushMail().setState(state);
						upeObj.getPushMail().setTimestamp(ts);

						// 设置响应内容
						setResponseContent(XStreamParser.UPEObject2Xml(upeObj));
						return true;
					}
				} else if (UPEConstants.UPE_UPDATECONFIG_STATE.equals(state)) {
					JSONObject json = new JSONObject();
					json.put(JMSConstans.JMS_TYPE_KEY,
							CAGConstants.CAG_UPDATECONFIG_NOTIF);
					json.put(JMSConstans.JMS_UID_KEY, uid);
					json.put(JMSConstans.CAG_RESPONSE_CODE,
							CAGConstants.CAG_SUCCESS_CODE);
					json.put(JMSConstans.CAG_RESPONSE_STATUS, "Success");
					MessageSender.sendCAGResponseMessage(json.toString());

					// 在客户端收到UpdateConfig后发送新邮件通知
					logger
							.info("send new email notice after UpdateConfig notice been received....");
					upeObj.getPushMail().setState(
							UPEConstants.UPE_NEWMAIL_STATE);
					String timestamp = String.valueOf(System
							.currentTimeMillis());
					upeObj.getPushMail().setTimestamp(timestamp);

					// 保存新邮件时间戳和状态
					cache.setLast_notice_ts(timestamp);
					cache.setLast_notice_state(UPEConstants.UPE_NEWMAIL_STATE);

					// 删除新邮件标记
					cache.setNew_msg_flag_map(null);

					// 设置响应内容
					setResponseContent(XStreamParser.UPEObject2Xml(upeObj));
					return true;

				} else if (UPEConstants.UPE_CONNECTERROR_STATE.equals(state)) {
					// 如果账号异常通知被收到，清空last_invalid_aid字段
					cache.setLast_invalid_aid(null);
				} else if (UPEConstants.UPE_SENDMAIL_FAIL_STATE.equals(state)
						|| UPEConstants.UPE_SENDMAIL_SUCCESS_STATE
								.equals(state)) {

					// 更新数据库发送邮件状态
					new SendMessageDao().updateSentMessageStatus(Long
							.valueOf(cache.getLast_mailsent_mid()),
							Constant.MAIL_SENT_DONE_STATUS);

					// 如果邮件发送状态通知被收到，清空last_mailsent_mid和last_mailsent_hid字段
					cache.setLast_mailsent_mid(null);
					cache.setLast_mailsent_hid(null);
				}

				cache.setLast_notice_ts(null);
				cache.setLast_notice_state(null);
				logger.info("last valid notice for IMEI[" + IMEI
						+ "] has been received by client...");
			} else {
				logger.info("last valid notice for IMEI[" + IMEI
						+ "] has not been received by client,send again...");

				upeObj.getPushMail().setState(state);
				upeObj.getPushMail().setTimestamp(ts);

				if (UPEConstants.UPE_CONNECTERROR_STATE.equals(state)) {
					// 如果是账号异常通知，需要返回aid
					upeObj.getPushMail().setAid(cache.getLast_invalid_aid());
					logger.info("add invalid aid...");
				} else if (UPEConstants.UPE_SENDMAIL_FAIL_STATE.equals(state)
						|| UPEConstants.UPE_SENDMAIL_SUCCESS_STATE
								.equals(state)) {
					// 如果是邮件发送状态通知，需要返回mid和hid
					upeObj.getPushMail().setMid(cache.getLast_mailsent_mid());
					upeObj.getPushMail().setHid(cache.getLast_mailsent_hid());
					logger.info("add mid and hid...");
				}else if(UPEConstants.SYNCH_DELETE_ACCOUNT.equals(state)){
					cache.setDelete_account_map(null);
				}

				// 设置响应内容
				setResponseContent(XStreamParser.UPEObject2Xml(upeObj));

				return true;
			}
		}
		return false;
	}

	/**
	 * 处理新邮件通知.
	 * 
	 * @param currentTime
	 * @param response
	 * @return
	 */
	private boolean handleNewMessageNotice(HttpServletResponse response) {
		/**
		 * 此处MessageCache应该是共享缓存
		 */
		if (UPECacheManager.hasNewMessageFlag(cache)) {
			logger.info("IMEI[" + IMEI
					+ "]cache hava message,send new email notice....");
			upeObj.getPushMail().setState(UPEConstants.UPE_NEWMAIL_STATE);
			String timestamp = String.valueOf(System.currentTimeMillis());
			upeObj.getPushMail().setTimestamp(timestamp);

			// 保存新邮件时间戳和状态
			cache.setLast_notice_ts(timestamp);
			cache.setLast_notice_state(UPEConstants.UPE_NEWMAIL_STATE);

			// 删除新邮件标记
			cache.setNew_msg_flag_map(null);

			// 设置响应内容
			setResponseContent(XStreamParser.UPEObject2Xml(upeObj));
			return true;
		}
		return false;
	}
	
	/**
	 * 处理账号删除通知.
	 * 
	 * @param currentTime
	 * @param response
	 * @return
	 */
	private boolean handleDeleteAccountNotice(HttpServletResponse response) {
		/**
		 * 此处MessageCache应该是共享缓存
		 */
		if (UPECacheManager.hasDeleteAccountFlag(cache)) {
			logger.info("IMEI[" + IMEI
					+ "]cache hava delete account,send  notice....");
			upeObj.getPushMail().setState(UPEConstants.SYNCH_DELETE_ACCOUNT);
			String timestamp = String.valueOf(System.currentTimeMillis());
			upeObj.getPushMail().setTimestamp(timestamp);
			upeObj.getPushMail().setAid(cache.getDelete_account_map());

			// 保存新邮件时间戳和状态
			cache.setLast_notice_ts(timestamp);
			cache.setLast_notice_state(UPEConstants.SYNCH_DELETE_ACCOUNT);
			

//			// 删除新邮件标记
//			cache.setDelete_account_map(null);

			// 设置响应内容
			setResponseContent(XStreamParser.UPEObject2Xml(upeObj));
			return true;
		}
		return false;
	}

	/**
	 * 处理邮件发送状态通知.
	 * 
	 * @param currentTime
	 * @param response
	 * @return
	 */
	private boolean handleMailSentStateNotice(HttpServletResponse response) {

		Map.Entry<String, String> entry = AbstractCacheManager
				.getFirstMailSentId(cache);
		if (entry != null) {
			logger.info("IMEI[" + IMEI
					+ "]cache hava mail sent state notice ,send....");
			String mid = entry.getKey();
			String[] strs = entry.getValue().split(",");
			if (strs.length != 2) {
				logger.error("mail set state and hid is error...");
				return false;
			}
			String upeState = strs[0];
			String hid = strs[1];

			String timestamp = String.valueOf(System.currentTimeMillis());
			upeObj.getPushMail().setHid(hid);
			upeObj.getPushMail().setMid(mid);
			upeObj.getPushMail().setTimestamp(timestamp);
			upeObj.getPushMail().setState(upeState);

			// 保存通知状态
			cache.setLast_notice_ts(timestamp);
			cache.setLast_notice_state(upeState);
			cache.setLast_mailsent_mid(mid);
			cache.setLast_mailsent_hid(hid);

			// 设置响应内容
			setResponseContent(XStreamParser.UPEObject2Xml(upeObj));
			return true;
		}
		return false;
	}

	/**
	 * 处理账号异常通知.
	 * 
	 * @param currentTime
	 * @param response
	 * @return
	 */
	private boolean handleInvalidAccountNotice(HttpServletResponse response) {
		/**
		 * 此处MessageCache应该是共享缓存
		 */
		if (cache.getInvalidAidSet() != null
				&& cache.getInvalidAidSet().size() > 0) {
			logger
					.info("IMEI["
							+ IMEI
							+ "]cache hava invalid aid message,send invalid notice....");

			String state = UPEConstants.UPE_CONNECTERROR_STATE;
			String aid = (String) cache.getInvalidAidSet().toArray()[0];

			upeObj.getPushMail().setState(state);
			upeObj.getPushMail().setAid(aid);
			String timestamp = String.valueOf(System.currentTimeMillis());
			upeObj.getPushMail().setTimestamp(timestamp);

			// 保存新邮件时间戳和状态
			cache.setLast_notice_ts(timestamp);
			cache.setLast_notice_state(state);
			cache.setLast_invalid_aid(aid);

			cache.removeInvalidAid(aid);

			// 设置响应内容
			setResponseContent(XStreamParser.UPEObject2Xml(upeObj));
			return true;
		}
		return false;
	}

	/**
	 * 处理CAG通知.
	 * 
	 * @param response
	 * @return
	 */
	private boolean handleCAGNotice(HttpServletResponse response) {
		String state = cache.getCAGNotice();
		if (state != null) {
			logger.info("IMEI[" + IMEI + "] has CAG notice:"
					+ cache.getCAGNotice() + ",send notice...");
			upeObj.getPushMail().setState(state);
			String timestamp = String.valueOf(System.currentTimeMillis());
			upeObj.getPushMail().setTimestamp(timestamp);

			// 保存请求时间戳和状态
			cache.setLast_notice_ts(timestamp);
			cache.setLast_notice_state(state);

			// 删除CAG通知
			cache.setCAGNotice(null);
			cache.setCAGAccount(null);

			// 设置响应内容
			setResponseContent(XStreamParser.UPEObject2Xml(upeObj));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 处理清理邮件的请求.
	 * 
	 * @param response
	 * @return
	 */
	private boolean handleDeleteMessages(HttpServletResponse

	response) {
		if (UPEConstants.SYNCH_DELETE_MESSAGES.equals

		(upeObj.getPushMail().getState())) {
			logger.info("IMEI[" + IMEI + "] has deleteMessages notice:");
			List<String> mids = upeObj.getPushMail

			().getDeleteMessageIds();
			upeObj.getPushMail().setDeleteMessageIds(null);

			// 保存请求时间戳和状态
			// cache.setLast_notice_ts(timestamp);
			// cache.setLast_notice_state(state);

			// 将要删除的mids做标记
			logger.info("mids:" + mids.toString());
			new Thread(new SetDeleteMessageSignsTask(mids)).start();
			// 设置响应内容
			setResponseContent(XStreamParser.UPEObject2Xml

			(upeObj));
			return true;
		} else {
			return false;
		}
	}

}
