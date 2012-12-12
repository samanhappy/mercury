package com.dreamail.jms.loop;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.data.AccountMessage;
import com.dreamail.jms.Notice;
import com.dreamail.jms.SendMessageThread;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JMSConstans;
import com.dreamail.taskfactory.msghandle.AccountTaskHandle;
import com.dreamail.utils.TFTools;

public class LoopReceiveHandle {

	public static final Logger logger = LoggerFactory
			.getLogger(LoopReceiveHandle.class);
	private static ConcurrentHashMap<String, String> timerMap = new ConcurrentHashMap<String, String>();
	private static ConcurrentHashMap<String, AccountMessage> aidMap = new ConcurrentHashMap<String, AccountMessage>();
	private static List<Timer> runList = new CopyOnWriteArrayList<Timer>();

	/**
	 * 启动定时器
	 */
	public static void handelMsg(String taskMessage) {

		logger.info("taskMessage:" + taskMessage);

		String[] accountList = taskMessage.replace(Constant.NOTICELOOP + ",",
				"").split(",");
		for (String account : accountList) {
			String[] accountInfo = account.split("-");
			AccountMessage am = new AccountMessage(
					Long.valueOf(accountInfo[1]),
					Integer.valueOf(accountInfo[2]));
			aidMap.put(accountInfo[0], am);
			boolean isNew = isNewTimer(String.valueOf(accountInfo[1]));
			if (isNew) {
				long inertvalTimer = am.getInertval();
				timerMap.put(String.valueOf(inertvalTimer),
						String.valueOf(inertvalTimer));
				Timer timer = new Timer(false);
				LoopReceiveHandle handel = new LoopReceiveHandle();
				timer.schedule(handel.getTimerTask(inertvalTimer), 2 * 1000,
						inertvalTimer * 60 * 1000);
				runList.add(timer);
			}
		}
	}

	/**
	 * 定时器停止
	 */
	public static void handelMsg(Notice notice) {
		if (!notice.getIP().equals(TFTools.getCurrentServerIP())
				|| notice.getIP().equals("")) {
			logger.info("-------------loopstop--------------");
			timerMap.clear();
			aidMap.clear();
			for (Timer timer : runList) {
				timer.cancel();
			}
		}
	}

	/**
	 * 是否需要启动一个timer
	 */
	public static boolean isNewTimer(String timerStr) {
		boolean timerFlag = true;
		Iterator<String> itor = timerMap.keySet().iterator();
		while (itor.hasNext()) {
			String key = itor.next();
			if (key.equals(timerStr)) {
				timerFlag = false;
				break;
			}
		}
		return timerFlag;
	}

	/**
	 * 获得监听类
	 */
	public TimerTask getTimerTask(long inertvalTimer) {
		return new MasterListener(inertvalTimer);
	}

	public static class MasterListener extends TimerTask {
		private long inertvalTimer;

		public MasterListener(long inertvalTimer) {
			this.inertvalTimer = inertvalTimer;
		}

		@Override
		public void run() {
			Iterator<String> itor = aidMap.keySet().iterator();
			JSONObject allMessages = new JSONObject();
			while (itor.hasNext()) {
				String key = itor.next();
				AccountMessage am = aidMap.get(key);
				if (am != null && am.getInertval() == inertvalTimer) {
					JSONObject json = new JSONObject();
					json.put(JMSConstans.JMS_TYPE_KEY,
							JMSConstans.JMS_RECEIVEMAIL_TYPE);
					json.put(JMSConstans.JMS_AID_KEY, key);
					json.put(JMSConstans.JMS_INTERVAL_KEY,
							String.valueOf(inertvalTimer));
					json.put(JMSConstans.JMS_ACCOUNT_TYPE_KEY, am.getType());
					allMessages.accumulate(String.valueOf(am.getType()),
							json.toString());
				}
			}

			@SuppressWarnings("unchecked")
			Iterator<String> it = allMessages.keys();
			while (it.hasNext()) {
				String key = it.next();
				Object value = allMessages.get(key);
				new SendMessageThread(key, value).start();
			}
		}
	}

	/**
	 * 删除账号
	 */
	public static void handleContextMap(String aid) {
		String[] aidlist = aid.split(",");
		StringBuffer aids = new StringBuffer();
		for (String ids : aidlist) {
			if (aidMap.containsKey(ids)) {
				aidMap.remove(aid);
				if (aidlist.length > 1) {
					AccountMessage am = AccountTaskHandle
							.getLoopTimerByAid(Long.parseLong(aid));
					aids.append(aid).append("-").append(am.getInertval())
							.append("-").append(am.getType()).append(",");
				}
			}
		}
		if (aidlist.length > 1) {
			String sendAids = aids.toString().substring(0,
					aids.toString().length() - 1);
			handelMsg(sendAids);
		}
	}
}
