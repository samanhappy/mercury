package com.dreamail.mercury.talaria.http;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.talaria.http.handler.IMHandler;
import com.dreamail.mercury.talaria.xstream.PushMailObject;
import com.dreamail.mercury.talaria.xstream.UPEParserObject;
import com.dreamail.mercury.talaria.xstream.XStreamParser;
import com.dreamail.mercury.util.UPEConstants;
import com.dreamail.talaria.memcached.AbstractCacheManager;
import com.dreamail.talaria.memcached.UPECacheManager;
import com.dreamail.talaria.memcached.UPECacheObject;
import com.dreamail.talaria.memcached.UPEMapping;

public class AsyncContextManager {
	public static final Logger logger = LoggerFactory
			.getLogger(AsyncContextManager.class);
	private static AsyncContextManager acm = new AsyncContextManager();
	private static ConcurrentHashMap<String, AsyncContext> connectionMap = new ConcurrentHashMap<String, AsyncContext>();

	private AsyncContextManager() {
	}

	public static AsyncContextManager getInstance() {
		if (acm == null) {
			return new AsyncContextManager();
		}
		return acm;
	}

	public void putContext(String IMEI, AsyncContext ac) {
		connectionMap.put(IMEI, ac);
	}

	public AsyncContext getContext(String IMEI) {
		return connectionMap.get(IMEI);
	}

	public void removeContext(String IMEI) {
		if (connectionMap.containsKey(IMEI)) {
			connectionMap.remove(IMEI);
		}
	}

	public static boolean contains(String IMEI) {
		return connectionMap.containsKey(IMEI);
	}

	/**
	 * 发送新邮件通知.
	 * 
	 * @param IMEI
	 * @param uid
	 * @param aid
	 */
	public void sendNewMailNotice(String IMEI, String uid, String aid) {
		AsyncContext ac = connectionMap.remove(IMEI);
		if (ac != null && !isServiceDisable(ac)) {
			try {
				String timestamp = String.valueOf(System.currentTimeMillis());
				String state = UPEConstants.UPE_NEWMAIL_STATE;
				String content = getResponseContent(IMEI, uid, aid, state,
						timestamp);
				logger.info("send new message notice for IMEI:" + IMEI);
				ResponseSender.response2Client(
						(HttpServletResponse) ac.getResponse(), content);
				// 保存当前响应时间戳和状态到缓存
				saveResponseState(uid, timestamp, state, null, null, null);
				ac.complete();
			} catch (Exception e) {
				logger.error("[" + uid + "]sendMessage err!", e);
				if (aid != null) {
					UPECacheManager.getLock(uid);
					UPECacheObject cache = UPECacheManager.getCacheObject(uid);
					if (cache == null) {
						cache = new UPECacheObject();
						cache.setUid(uid);
					}
					// 删除通知时间戳和状态
					cache.setLast_notice_ts(null);
					cache.setLast_notice_state(null);
					AbstractCacheManager.addNewMessageFlag4String(cache, aid);
					UPECacheManager.setCacheObject(cache);
					UPECacheManager.removeLock(uid);
				}
			}
		} else {
			if (aid != null) {
				logger.info("no connection at the moment,load in cache...");
				UPECacheManager.getLock(uid);
				UPECacheManager.addNewMessageFlag(uid, aid);
				UPECacheManager.removeLock(uid);
			}
		}
	}

	/**
	 * 立即发送普通通知.
	 * 
	 * @param uid
	 */
	public void sendCommonNotice(String uid) {
		String IMEI = new UserDao().getIMEIByUid(Long.valueOf(uid));
		// 判断IMEI是否存在
		if (IMEI == null || "".equals(IMEI.trim())) {
			logger.info("there is no IMEI by uid:" + uid + " do nothing...");
			return;
		}
		AsyncContext ac = connectionMap.remove(IMEI);
		if (ac != null) {
			try {
				String timestamp = String.valueOf(System.currentTimeMillis());
				String state = UPEConstants.UPE_COMMON_STATE;
				String content = getResponseContent(IMEI, uid, null, state,
						timestamp);
				logger.info("send common notice for IMEI:" + IMEI);
				ResponseSender.response2Client(
						(HttpServletResponse) ac.getResponse(), content);
			} catch (Exception e) {
				logger.error("[" + uid + "]sendMessage err!", e);
			} finally {
				ac.complete();
			}
		} else {
			logger.info("no connection found, do nothing...");
		}
	}

	/**
	 * 发送账号异常通知.
	 * 
	 * @param IMEI
	 * @param uid
	 * @param aid
	 */
	public void sendAccountInvalidNotice(String IMEI, String uid, String aid) {
		AsyncContext ac = connectionMap.remove(IMEI);
		if (ac != null && !isServiceDisable(ac)) {
			try {
				String timestamp = String.valueOf(System.currentTimeMillis());
				String state = UPEConstants.UPE_CONNECTERROR_STATE;
				String content = getResponseContent(IMEI, uid, aid, state,
						timestamp);
				logger.info("send new message notice for IMEI:" + IMEI);
				ResponseSender.response2Client(
						(HttpServletResponse) ac.getResponse(), content);
				// 保存当前响应时间戳和状态到缓存
				saveResponseState(uid, timestamp, state, aid, null, null);
			} catch (Exception e) {
				logger.error("[" + uid + "]sendMessage err!", e);
				UPECacheManager.getLock(uid);
				UPECacheObject cache = UPECacheManager.getCacheObject(uid);
				if (cache == null) {
					cache = new UPECacheObject();
					cache.setUid(uid);
				}
				// 删除last通知时间戳和状态
				cache.setLast_notice_ts(null);
				cache.setLast_notice_state(null);
				// 添加账号异常aid
				cache.addInvalidAid(aid);
				UPECacheManager.setCacheObject(cache);
				UPECacheManager.removeLock(uid);
			} finally {
				ac.complete();
			}
		} else {
			logger.info("["
					+ uid
					+ "] no connection at the moment or there is error message,load in cache...");
			UPECacheManager.getLock(uid);
			UPECacheManager.addInvalidAid(uid, aid);
			UPECacheManager.removeLock(uid);

		}
	}

	/**
	 * 发送邮件发送状态通知.
	 * 
	 * @param IMEI
	 * @param uid
	 * @param aid
	 */
	public void sendMailSentStateNotice(UPEParserObject obj) {
		String IMEI = obj.getIMEI();
		AsyncContext ac = connectionMap.remove(IMEI);
		String uid = obj.getPushMail().getUid();
		if (ac != null && !isServiceDisable(ac)) {
			try {
				logger.info("send mail sent state notice for IMEI:"
						+ obj.getIMEI());
				ResponseSender.response2Client(
						(HttpServletResponse) ac.getResponse(),
						XStreamParser.UPEObject2Xml(obj));
				// 保存当前响应时间戳和状态到缓存
				saveResponseState(uid, obj.getPushMail().getTimestamp(), obj
						.getPushMail().getState(), null, obj.getPushMail()
						.getMid(), obj.getPushMail().getHid());
			} catch (Exception e) {
				logger.error("[" + uid + "]sendMessage err!", e);
				UPECacheManager.getLock(uid);
				UPECacheObject cache = UPECacheManager.getCacheObject(uid);
				if (cache == null) {
					cache = new UPECacheObject();
					cache.setUid(uid);
				}
				// 删除通知时间戳和状态
				cache.setLast_notice_ts(null);
				cache.setLast_notice_state(null);
				AbstractCacheManager.addMailSentFlag(cache, obj.getPushMail()
						.getMid(), obj.getPushMail().getState(), obj
						.getPushMail().getHid());
				UPECacheManager.setCacheObject(cache);
				UPECacheManager.removeLock(uid);
			} finally {
				ac.complete();
			}
		} else {
			logger.info("["
					+ uid
					+ "] no connection at the moment or there is error message,load in cache...");
			UPECacheManager.getLock(uid);
			UPECacheObject cache = UPECacheManager.getCacheObject(uid);
			if (cache == null) {
				cache = new UPECacheObject();
				cache.setUid(uid);
			}
			AbstractCacheManager.addMailSentFlag(cache, obj.getPushMail()
					.getMid(), obj.getPushMail().getState(), obj.getPushMail()
					.getHid());
			UPECacheManager.setCacheObject(cache);
			UPECacheManager.removeLock(uid);

		}
	}

	/**
	 * 发送用户强制下线通知.
	 * 
	 * @param IMEI
	 * @param uid
	 */
	public void sendUserForceOffNotice(String IMEI, String uid) {
		AsyncContext ac = connectionMap.remove(IMEI);
		String timestamp = String.valueOf(System.currentTimeMillis());
		if (ac != null && !isServiceDisable(ac)) {
			try {
				String state = UPEConstants.UPE_FORCEOFF_STATE;
				String content = getResponseContent(IMEI, uid, null, state,
						timestamp);
				logger.info("send user forceoff message notice for IMEI:"
						+ IMEI + ",and uid:" + uid);
				ResponseSender.response2Client(
						(HttpServletResponse) ac.getResponse(), content);
			} catch (Exception e) {
				logger.error("[" + uid + "]sendMessage err!", e);
			} finally {
				ac.complete();
				// 用户强制下线保存一个新邮件状态到缓存
				saveResponseState(uid, timestamp,
						UPEConstants.UPE_NEWMAIL_STATE, null, null, null);
			}
		} else {
			logger.info("["
					+ uid
					+ "] no connection at the moment or there is error message,load in cache...");
			// 用户强制下线保存一个新邮件状态到缓存
			saveResponseState(uid, timestamp, UPEConstants.UPE_NEWMAIL_STATE,
					null, null, null);
		}
	}

	/**
	 * 构造返回的协议.
	 * 
	 * @param IMEI
	 * @param uid
	 * @param aid
	 * @param state
	 * @return
	 */
	private String getResponseContent(String IMEI, String uid, String aid,
			String state, String timestamp) {
		UPEParserObject upeObj = new UPEParserObject();
		upeObj.setPushMail(new PushMailObject());
		upeObj.setIMEI(IMEI);
		upeObj.getPushMail().setUid(uid);
		upeObj.getPushMail().setAid(aid);
		upeObj.getPushMail().setTimestamp(timestamp);
		upeObj.getPushMail().setState(state);

		return XStreamParser.UPEObject2Xml(upeObj);
	}

	/**
	 * 保存响应时间戳和响应状态到缓存.
	 * 
	 * @param uid
	 * @param timestamp
	 * @param state
	 */
	public void saveResponseState(String uid, String timestamp, String state,
			String aid, String mid, String hid) {
		UPECacheManager.getLock(uid);
		UPECacheObject cache = UPECacheManager.getCacheObject(uid);
		if (cache == null) {
			cache = new UPECacheObject();
			cache.setUid(uid);
		}
		cache.setLast_notice_ts(timestamp);
		cache.setLast_notice_state(state);
		if (aid != null) {
			cache.setLast_invalid_aid(aid);
		}
		if (mid != null) {
			cache.setLast_mailsent_mid(mid);
		}
		if (hid != null) {
			cache.setLast_mailsent_hid(hid);
		}
		UPECacheManager.setCacheObject(cache);
		UPECacheManager.removeLock(uid);
	}

	/**
	 * 发送CAG通知.
	 * 
	 * @param IMEI
	 * @param uid
	 * @param state
	 * @param account
	 */
	public void sendCAGNotice(String IMEI, String uid, String state,
			String notification) {
		AsyncContext ac = connectionMap.remove(IMEI);
		if (ac != null) {
			String timestamp = String.valueOf(System.currentTimeMillis());
			UPEParserObject upeObj = new UPEParserObject();
			upeObj.setPushMail(new PushMailObject());

			upeObj.setIMEI(IMEI);
			upeObj.getPushMail().setUid(uid);
			upeObj.getPushMail().setTimestamp(timestamp);
			upeObj.getPushMail().setState(state);

			logger.info("send CAG notice for IMEI:" + IMEI);
			try {
				ResponseSender.response2Client(
						(HttpServletResponse) ac.getResponse(),
						XStreamParser.UPEObject2Xml(upeObj));
				// 保存当前响应时间戳和状态到缓存
				saveResponseState(uid, timestamp, state, null, null, null);
			} catch (Exception e) {
				logger.error("send cag notice error, save notice into cache...");
				UPECacheManager.getLock(uid);
				UPECacheObject obj = UPECacheManager.getCacheObject(uid);
				if (obj == null) {
					obj = new UPECacheObject();
					obj.setUid(uid);
				}
				obj.setLast_notice_state(null);
				obj.setLast_notice_ts(null);
				obj.setCAGNotice(state);
				UPECacheManager.setCacheObject(obj);
				UPECacheManager.removeLock(uid);
			} finally {
				ac.complete();
			}
		} else {
			logger.info("["
					+ uid
					+ "] no connection at the moment or there is error message,load in cache...");
			UPECacheManager.getLock(uid);
			UPECacheObject obj = UPECacheManager.getCacheObject(uid);
			if (obj == null) {
				obj = new UPECacheObject();
				obj.setUid(uid);
			}
			obj.setLast_notice_state(null);
			obj.setLast_notice_ts(null);
			obj.setCAGNotice(state);
			UPECacheManager.setCacheObject(obj);
			UPECacheManager.removeLock(uid);
		}

	}

	/**
	 * 发送IM通知.
	 * 
	 * @param IMEI
	 * @param uid
	 * @param aid
	 */
	public void sendIMNotice(String IMEI, String xml) {
		AsyncContext ac = connectionMap.remove(IMEI);
		if (ac != null) {
			try {
				logger.info("send IM message notice for IMEI:" + IMEI);
				ResponseSender.response2Client(
						(HttpServletResponse) ac.getResponse(),
						IMHandler.getIMResponseStr(IMEI, xml));
			} catch (Exception e) {
				logger.error("[" + IMEI + "]sendMessage err!", e);
				UPECacheManager.getMappingLock(IMEI);
				UPEMapping mapping = UPECacheManager.getMappingObject(IMEI);
				if (mapping == null) {
					mapping = new UPEMapping();
					mapping.setIMEI(IMEI);
				}
				mapping.setIMNotice(xml);
				UPECacheManager.setMappingObject(mapping);
				UPECacheManager.removeMappingLock(IMEI);
			} finally {
				ac.complete();
			}
		} else {
			logger.info("[" + IMEI
					+ "] no connection at the moment,load in cache...");
			UPECacheManager.getMappingLock(IMEI);
			UPEMapping mapping = UPECacheManager.getMappingObject(IMEI);
			if (mapping == null) {
				mapping = new UPEMapping();
				mapping.setIMEI(IMEI);
			}
			mapping.setIMNotice(xml);
			UPECacheManager.setMappingObject(mapping);
			UPECacheManager.removeMappingLock(IMEI);
		}
	}

	public void putCAGContext(String uid, AsyncContext ac) {
		connectionMap.put("CAG_" + uid, ac);
	}

	public AsyncContext getCAGContext(String uid) {
		return connectionMap.get("CAG_" + uid);
	}

	public void removeCAGContext(String uid) {
		if (connectionMap.containsKey("CAG_" + uid)) {
			connectionMap.remove("CAG_" + uid);
		}
	}

	/**
	 * 判断Service是否可用.
	 * 
	 * @param e
	 * @return
	 */
	private boolean isServiceDisable(AsyncContext context) {
		return (Boolean) context.getRequest().getAttribute(
				UPEConstants.SERVICE_STATE_KEY);
	}
	/**
	 * 当账号被另一个用户注册，通知上一个用户删除账号.
	 * @param IMEI
	 * @param uid
	 * @param aid
	 */
	public void sendDeleteAccountNotice(String IMEI, String uid, String aid) {
		AsyncContext ac = connectionMap.remove(IMEI);
		if (ac != null && !isServiceDisable(ac)) {
			try {
				String timestamp = String.valueOf(System.currentTimeMillis());
				String state = UPEConstants.SYNCH_DELETE_ACCOUNT;
				String content = getResponseContent(IMEI, uid, aid, state,
						timestamp);
				logger.info("send DeleteAccountNotice for IMEI:" + IMEI);
				logger.info("content is:" + content);
				ResponseSender.response2Client(
						(HttpServletResponse) ac.getResponse(), content);
				// 保存当前响应时间戳和状态到缓存
				saveResponseState(uid, timestamp, state, aid, null, null);
				ac.complete();
			} catch (Exception e) {
				logger.error("[" + uid + "]sendMessage err!", e);
				if (aid != null) {
					UPECacheManager.getLock(uid);
					UPECacheManager.addDeleteAccountFlag(uid, aid);
					UPECacheManager.removeLock(uid);
				}
			}
		} else {
			if (aid != null) {
				logger.info("no connection at the moment,load in cache...");
				UPECacheManager.getLock(uid);
				UPECacheManager.addDeleteAccountFlag(uid, aid);
				UPECacheManager.removeLock(uid);
			}
		}
	}

}
