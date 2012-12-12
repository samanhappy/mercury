package com.dreamail.mercury.talaria.threadpool;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.talaria.http.AsyncContextManager;
import com.dreamail.mercury.talaria.http.handler.IRequestHandler;
import com.dreamail.mercury.talaria.xstream.PushMailObject;
import com.dreamail.mercury.talaria.xstream.UPEParserObject;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.mercury.util.UPEConstants;
import com.dreamail.talaria.memcached.UPECacheManager;
import com.dreamail.talaria.memcached.UPEMapping;

import EDU.oswego.cs.dl.util.concurrent.Callable;

public class JMSHandleCallable implements Callable{

	private static Logger logger = LoggerFactory.getLogger(JMSHandleCallable.class);
	
	private Object msg;
	
	public JMSHandleCallable(Object msg) {
		this.msg = msg;
	}
	
	@Override
	public Object call() throws Exception {
		// 判断消息类型
				if (!(msg instanceof String)) {
					logger.error("\nmsg instance is " + msg.getClass());
					return null;
				}

				String msgStr = (String) msg;
				logger.info("message jms str is:" + msgStr);

				if (msgStr.startsWith("accountInvalid,")) {

					/*
					 * 打开帐号异常通知 if (true) {
					 * logger.info("not to handle account invalid message now"); return null;
					 * }
					 */

					/**
					 * 处理账号错误消息
					 */
					String[] strs = msgStr.split(",");
					// 判断消息格式
					if (strs.length != 2) {
						logger.error("\njms message content:" + msgStr
								+ " is error!!!!!!!!!");
						return null;
					}

					String aid = strs[1].trim();
					List<Clickoo_user> userList = new AccountDao()
							.selectUidAndIMEIByAid(Long.valueOf(aid));
					// 判断是否存在用户
					if (userList == null || userList.size() == 0) {
						logger.info("there is no user info found for aid:" + aid);
						return null;
					}

					for (Clickoo_user user : userList) {

						// 判断IMEI是否存在
						if (user.getIMEI() == null || "".equals(user.getIMEI().trim())) {
							logger.info("there is no IMEI by uid:" + user.getUid()
									+ " save to db...");
							new AccountService().saveInvalidAid(user.getUid(),
									Long.valueOf(aid));
							continue;
						}

						String IMEI = user.getIMEI();
						logger.info("receive account invalid message for aid:" + aid
								+ " and IMEI:" + IMEI);
						boolean isUserConected = AsyncContextManager.contains(IMEI);
						UPEMapping mapping = UPECacheManager.getMappingObject(IMEI);
						// 判断缓存是否存在以及是否挂载在当前主机
						if ((mapping == null && !isUserConected)
								|| (mapping != null && !IRequestHandler.mac
										.equals(mapping.getMac()))) {
							logger.info("no cache exists or mac-address in cache is not local, save to db...");
							new AccountService().saveInvalidAid(user.getUid(),
									Long.valueOf(aid));
							continue;
						}

						// 判断uid是否一致
						if ((mapping != null && !String.valueOf(user.getUid()).equals(
								mapping.getUid()))) {
							logger.info("uid for IMEI:" + IMEI + " in cache["
									+ mapping.getUid()
									+ "] in not the same as in database["
									+ user.getUid() + "], save to db...");
							new AccountService().saveInvalidAid(user.getUid(),
									Long.valueOf(aid));
							continue;
						}

						// 发送账号异常通知
						AsyncContextManager.getInstance().sendAccountInvalidNotice(
								IMEI, String.valueOf(user.getUid()), aid);

					}
				} else {

					JSONObject json = null;
					try {
						json = JSONObject.fromObject(msgStr);
					} catch (Exception e) {
						logger.error("messge str is not in json format");
						return null;
					}

					if (json != null) {
						String type = json.getString(JMSConstans.JMS_TYPE_KEY);

						if (JMSConstans.JMS_SENDMAIL_TYPE.equals(type)) {
							/**
							 * 处理邮件发送状态通知消息
							 */
							String uid = json.getString(JMSConstans.JMS_UID_KEY);
							String mid = json.getString(JMSConstans.JMS_MID_KEY);
							String hid = json.getString(JMSConstans.JMS_HID_KEY);
							String state = json
									.getString(JMSConstans.JMS_SENDMAIL_STATE_KEY);

							String IMEI = new UserDao().getIMEIByUid(Long.valueOf(uid));

							// 判断IMEI是否存在
							if (IMEI == null || "".equals(IMEI.trim())) {
								logger.info("there is no IMEI by uid:" + uid
										+ " do nothing...");
								return null;
							}

							logger.info("receive send mail state message for uid:"
									+ uid + " and IMEI:" + IMEI);
							boolean isUserConected = AsyncContextManager.contains(IMEI);
							UPEMapping mapping = UPECacheManager.getMappingObject(IMEI);
							// 判断缓存是否存在以及是否挂载在当前主机
							if ((mapping == null && !isUserConected)
									|| (mapping != null && !IRequestHandler.mac
											.equals(mapping.getMac()))) {
								logger.info("no cache exists or mac-address in cache is not local, do nothing...");
								return null;
							}

							// 判断uid是否一致
							if ((mapping != null && !String.valueOf(uid).equals(
									mapping.getUid()))) {
								logger.info("uid for IMEI:" + IMEI + " in cache["
										+ mapping.getUid()
										+ "] in not the same as in database[" + uid
										+ "], do nothing...");
								return null;
							}

							String upeState = null;
							if (JMSConstans.JMS_SENDMAIL_SUCCESS_VALUE
									.equalsIgnoreCase(state)) {
								upeState = UPEConstants.UPE_SENDMAIL_SUCCESS_STATE;
							} else if (JMSConstans.JMS_SENDMAIL_FAIL_VALUE
									.equalsIgnoreCase(state)) {
								upeState = UPEConstants.UPE_SENDMAIL_FAIL_STATE;
							} else {
								logger.error("not recognized send mail state");
								return null;
							}

							String timestamp = String.valueOf(System
									.currentTimeMillis());
							UPEParserObject upeObj = new UPEParserObject();
							upeObj.setPushMail(new PushMailObject());
							upeObj.setIMEI(IMEI);
							upeObj.getPushMail().setUid(uid);
							upeObj.getPushMail().setHid(hid);
							upeObj.getPushMail().setMid(mid);
							upeObj.getPushMail().setTimestamp(timestamp);
							upeObj.getPushMail().setState(upeState);

							// 发送邮件发送状态通知
							AsyncContextManager.getInstance().sendMailSentStateNotice(
									upeObj);

						} else if (JMSConstans.JMS_USERFORCEOFF_TYPE.equals(type)) {
							/**
							 * 处理用户强制下线JMS消息
							 */
							String IMEI = json.getString(JMSConstans.JMS_IMEI_KEY);
							String uid = json.getString(JMSConstans.JMS_UID_KEY);

							logger.info("receive force offline user message by id:"
									+ uid + " for IMEI:" + IMEI);
							boolean isUserConected = AsyncContextManager.contains(IMEI);
							UPEMapping mapping = UPECacheManager.getMappingObject(IMEI);
							// 判断缓存是否存在以及是否挂载在当前主机
							if ((mapping == null && !isUserConected)
									|| (mapping != null && !IRequestHandler.mac
											.equals(mapping.getMac()))) {
								logger.info("no cache exists or mac-address in cache is not local, do nothing...");
								return null;
							}

							// 判断uid是否一致
							if ((mapping != null && !uid.equals(mapping.getUid()))) {
								logger.info("uid for IMEI:" + IMEI + " in cache["
										+ mapping.getUid()
										+ "] in not the same as in message[" + uid
										+ "], do nothing...");
								return null;
							}

							// 判断IMEI是否挂载在当前主机
							AsyncContextManager.getInstance().sendUserForceOffNotice(
									IMEI, uid);

						} else if (JMSConstans.JMS_NEWMAIL_TYPE.equals(type)) {
							/**
							 * 处理新邮件通知JMS消息
							 */

							String uid = null, aid = null;
							if (json.containsKey(JMSConstans.JMS_UID_KEY)) {
								uid = json.getString(JMSConstans.JMS_UID_KEY);
							}
							if (json.containsKey(JMSConstans.JMS_AID_KEY)) {
								aid = json.getString(JMSConstans.JMS_AID_KEY);
							}

							if (uid != null) {
								String IMEI = new UserDao().getIMEIByUid(Long
										.valueOf(uid));
								// 判断IMEI是否存在
								if (IMEI == null || "".equals(IMEI.trim())) {
									logger.info("there is no IMEI by uid:" + uid
											+ " do nothing...");
									return null;
								}
								boolean isUserConected = AsyncContextManager
										.contains(IMEI);
								UPEMapping mapping = UPECacheManager
										.getMappingObject(IMEI);

								// 判断缓存是否存在以及是否挂载在当前主机
								if ((mapping == null && !isUserConected)
										|| (mapping != null && !IRequestHandler.mac
												.equals(mapping.getMac()))) {
									logger.info("no cache exists or mac is not local, do nothing...");
									return null;
								}

								// 判断uid是否一致
								if ((mapping != null && !String.valueOf(uid).equals(
										mapping.getUid()))) {
									logger.info("uids for IMEI are not the same, do nothing...");
									return null;
								}
								// 发送新邮件通知
								AsyncContextManager.getInstance().sendNewMailNotice(
										IMEI, String.valueOf(uid), null);
							} else if (aid != null) {
								List<Clickoo_user> userList = new AccountDao()
										.selectUidAndIMEIByAid(Long.valueOf(aid));
								// 判断是否存在用户
								if (userList == null || userList.size() == 0) {
									logger.info("there is no user info found for aid:"
											+ aid);
									return null;
								}

								for (Clickoo_user user : userList) {

									// 判断是否是IDLE用户
									if (user.getUid() == 0) {
										logger.info("it is a idle user, do nothing...");
										continue;
									}

									// 判断IMEI是否存在
									if (user.getIMEI() == null
											|| "".equals(user.getIMEI().trim())) {
										logger.info("there is no IMEI by uid:"
												+ user.getUid() + " do nothing...");
										continue;
									}

									String IMEI = user.getIMEI();
									logger.info("receive new msg for aid:" + aid
											+ " and IMEI:" + IMEI);
									boolean isUserConected = AsyncContextManager
											.contains(IMEI);
									UPEMapping mapping = UPECacheManager
											.getMappingObject(IMEI);

									// 判断缓存是否存在以及是否挂载在当前主机
									if ((mapping == null && !isUserConected)
											|| (mapping != null && !IRequestHandler.mac
													.equals(mapping.getMac()))) {
										logger.info("no cache exists or mac-address in cache is not local, do nothing...");
										continue;
									}

									// 判断uid是否一致
									if ((mapping != null && !String.valueOf(
											user.getUid()).equals(mapping.getUid()))) {
										logger.info("uid for IMEI:" + IMEI
												+ " in cache[" + mapping.getUid()
												+ "] in not the same as in database["
												+ user.getUid() + "], do nothing...");
										continue;
									}

									// 发送新邮件通知
									AsyncContextManager.getInstance()
											.sendNewMailNotice(IMEI,
													String.valueOf(user.getUid()), aid);
								}
							}

						} else if(JMSConstans.JMS_DELETE_ACCOUNT_TYPE.equals(type)){
							//通知账号之前所属的用户删除账号
							String uid = null, aid = null;
							if (json.containsKey(JMSConstans.JMS_UID_KEY)) {
								uid = json.getString(JMSConstans.JMS_UID_KEY);
							}
							if (json.containsKey(JMSConstans.JMS_AID_KEY)) {
								aid = json.getString(JMSConstans.JMS_AID_KEY);
							}
							logger.info("delete aid is "+aid);
							if (uid != null) {
								String IMEI = new UserDao().getIMEIByUid(Long
										.valueOf(uid));
								// 判断IMEI是否存在
								if (IMEI == null || "".equals(IMEI.trim())) {
									logger.info("there is no IMEI by uid:" + uid
											+ " do nothing...");
									return null;
								}
								boolean isUserConected = AsyncContextManager
										.contains(IMEI);
								UPEMapping mapping = UPECacheManager
										.getMappingObject(IMEI);

								// 判断缓存是否存在以及是否挂载在当前主机
								if ((mapping == null && !isUserConected)
										|| (mapping != null && !IRequestHandler.mac
												.equals(mapping.getMac()))) {
									logger.info("no cache exists or mac is not local, do nothing...");
									return null;
								}

								// 判断uid是否一致
								if ((mapping != null && !String.valueOf(uid).equals(
										mapping.getUid()))) {
									logger.info("uids for IMEI are not the same, do nothing...");
									return null;
								}
								// 发送删除账号通知
								AsyncContextManager.getInstance().sendDeleteAccountNotice(
										IMEI, uid, aid);
							} else if (aid != null) {
								List<Clickoo_user> userList = new AccountDao()
										.selectUidAndIMEIByAid(Long.valueOf(aid));
								// 判断是否存在用户
								if (userList == null || userList.size() == 0) {
									logger.info("there is no user info found for aid:"
											+ aid);
									return null;
								}

								for (Clickoo_user user : userList) {

									// 判断是否是IDLE用户
									if (user.getUid() == 0) {
										logger.info("it is a idle user, do nothing...");
										continue;
									}

									// 判断IMEI是否存在
									if (user.getIMEI() == null
											|| "".equals(user.getIMEI().trim())) {
										logger.info("there is no IMEI by uid:"
												+ user.getUid() + " do nothing...");
										continue;
									}

									String IMEI = user.getIMEI();
									logger.info("receive new msg for aid:" + aid
											+ " and IMEI:" + IMEI);
									boolean isUserConected = AsyncContextManager
											.contains(IMEI);
									UPEMapping mapping = UPECacheManager
											.getMappingObject(IMEI);

									// 判断缓存是否存在以及是否挂载在当前主机
									if ((mapping == null && !isUserConected)
											|| (mapping != null && !IRequestHandler.mac
													.equals(mapping.getMac()))) {
										logger.info("no cache exists or mac-address in cache is not local, do nothing...");
										continue;
									}

									// 判断uid是否一致
									if ((mapping != null && !String.valueOf(
											user.getUid()).equals(mapping.getUid()))) {
										logger.info("uid for IMEI:" + IMEI
												+ " in cache[" + mapping.getUid()
												+ "] in not the same as in database["
												+ user.getUid() + "], do nothing...");
										continue;
									}

									// 发送新邮件通知
									AsyncContextManager.getInstance()
											.sendDeleteAccountNotice(IMEI,
													String.valueOf(user.getUid()), aid);
								}
							}
						}else {
							logger.error("not handle process for jms message type");
						}
					}
				}
		return null;
	}
}
