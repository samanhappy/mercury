package com.dreamail.mercury.mail.truepush.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.truepush.ITruepush;
import com.dreamail.mercury.mail.truepush.threadpool.PutActiveSyncThreadPool;
import com.dreamail.mercury.pojo.Clickoo_mail_account;

public class HotmailTruepush implements ITruepush {
	private static final Logger logger = LoggerFactory
			.getLogger(HotmailTruepush.class);
	private static ITruepush hotmailTruepush = new HotmailTruepush();
	private static ConcurrentHashMap<Long, HotmailPing> pingMap = new ConcurrentHashMap<Long, HotmailPing>();

	private static List<Long> need2StopList = new ArrayList<Long>();
	private static List<Long> need2ReconnectList = new ArrayList<Long>();

	public static synchronized void updatePushTask(boolean isPush,long aid){
		HotmailPing ping = HotmailTruepush.pingMap.get(aid);
		if(ping != null){
			ping.setPushTask(isPush);
		}
	}
	
	@Override
	public void addAccount(WebAccount wbAccount) {
		removeAccount(wbAccount.getId());
		HotmailPing ping = new HotmailPing(wbAccount, this);
		if (ping.start()) {
			pingMap.put(wbAccount.getId(), ping);
			PutActiveSyncThreadPool.putAcpool(String.valueOf(wbAccount.getId()));
		}
	}

	private HotmailTruepush() {
		Thread shutDownThread = new Thread() {
			@Override
			public void run() {
				List<Long> stopedList = new ArrayList<Long>();
				while (true) {
					shutdownAllDeadPing(stopedList);
					try {
						sleep(5000);
					} catch (InterruptedException e) {
						logger.error("", e);
					}
				}
			}

		};
		shutDownThread.start();

		Thread need2ReconnectThread = new Thread() {
			public void run() {
				while (true) {
					reconnectAllPing();
					try {
						sleep(5000);
					} catch (InterruptedException e) {
						logger.error("", e);
					}
				}
			}

		};
		need2ReconnectThread.start();

		Thread notifyReceiveMailThread = new Thread() {
			public void run() {
				while (true) {
					HotmailPing hp = null;
					for (Long aid : pingMap.keySet()) {
						hp = pingMap.get(aid);
						logger.info("isHasNewMail:"+hp.isHasNewMail() +" isPushTask:"+hp.isPushTask());
						if (hp  != null
								&& hp.isHasNewMail() && hp.isPushTask()) {
							hp.notifyReceiveMail();
						}
					}

					try {
						sleep(30000);
					} catch (InterruptedException e) {
						logger.error("", e);
					}
				}
			}
		};
		notifyReceiveMailThread.start();
	}

	private void shutdownAllDeadPing(List<Long> stopedList) {
		for (Long aid : need2StopList) {
			removeAccount(aid);
			stopedList.add(aid);
		}

		synchronized (need2StopList) {
			need2StopList.removeAll(stopedList);
		}
		stopedList.clear();
	}

	private void reconnectAllPing() {
		List<Long> reconnectList = new ArrayList<Long>();
		for (Long aid : need2ReconnectList) {
			if (pingMap.get(aid) != null) {
				if (!pingMap.get(aid).reconnect()) {
					continue;
				}
			}
			reconnectList.add(aid);
		}

		synchronized (need2ReconnectList) {
			need2ReconnectList.removeAll(reconnectList);
		}
		reconnectList.clear();
	}

	public void need2Stop(Long aid) {
		synchronized (need2StopList) {
			need2StopList.add(aid);
		}
	}

	public void need2Reconnect(Long aid) {
		synchronized (need2ReconnectList) {
			need2ReconnectList.add(aid);
		}
	}

	public static ITruepush getInstance() {
		return hotmailTruepush;
	}

	@Override
	public void removeAccount(Long aid) {
		if (pingMap.contains(aid) || pingMap.get(aid) != null) {
			pingMap.get(aid).shutdown();
			pingMap.remove(aid);
		}
	}

	@Override
	public void updateAccount(Long aid, String password) {
		if (pingMap.contains(aid) || pingMap.get(aid) != null) {
			pingMap.get(aid).updateAccount(password);
		} else {
			logger.info("updateAccount but aid isn't in map.will add to map!");
			Clickoo_mail_account mailAccount = new AccountService()
					.getAccountByAid(aid);
			if (mailAccount != null) {
				addAccount(mailAccount.getWebAccount4Ping());
			} else {
				logger.error("aid - " + aid + " - isn't in DB.");
			}
		}
	}

	@Override
	public void clear() {
		Enumeration<HotmailPing> en = pingMap.elements();
		HotmailPing ping = null;
		while (en.hasMoreElements()) {
			ping = en.nextElement();
			ping.shutdown();
		}
		pingMap.clear();
	}
}
