package com.dreamail.mercury.mail.truepush.impl;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.truepush.threadpool.PutActiveSyncThreadPool;
import com.dreamail.mercury.mail.util.EmailReceiveUtil;
import com.dreamail.mercury.util.JMSTypes;

public class HotmailPing implements IHotmailPing {
	private static final Logger logger = LoggerFactory
			.getLogger(HotmailPing.class);
	private final static String INBOXID = "00000000-0000-0000-0000-000000000001";
	private final static int MAX_RETRY_INTERVAL = 2 * 60;
	private final static int INIT_RETRY_INTERVAL = 10;
	private int nextRetryInterval = 10;
	private long nextRetryTime = -1;
	private DefaultHttpAsyncClient httpClient;
	private WebAccount wbAccount;
	private HotmailTruepush hotmailTruepush;
	private boolean hasNewMail = false;
	private boolean isPushTask = false;

	public boolean isPushTask() {
		return isPushTask;
	}

	public void setPushTask(boolean isPushTask) {
		this.isPushTask = isPushTask;
	}

	public boolean isHasNewMail() {
		return hasNewMail;
	}

	public void setHasNewMail(boolean hasNewMail) {
		this.hasNewMail = hasNewMail;
	}

	public HotmailPing(WebAccount wbAccount, HotmailTruepush hotmailTruepush) {
		this.wbAccount = wbAccount;
		this.hotmailTruepush = hotmailTruepush;
		logger.info("[" + toString() + "]new HotmailPing account - "
				+ wbAccount.getName() + " , " + wbAccount.getPassword());
	}

	/**
	 * 初始化httpclient,设置登录名、密码。
	 * 
	 * @throws IOReactorException
	 */
	private void initHttpclient() throws IOReactorException {
		httpClient = new DefaultHttpAsyncClient();
		httpClient.getCredentialsProvider().setCredentials(
				AuthScope.ANY,
				new UsernamePasswordCredentials(wbAccount.getName(), wbAccount
						.getPassword()));
	}

	private HttpPost getPost(String cmdName) {
		String deviceId = EmailReceiveUtil.getDeviceId(wbAccount.getName(), String.valueOf(wbAccount.getId()));
		HttpPost post = new HttpPost(
				"https://m.hotmail.com/Microsoft-Server-ActiveSync?User="+deviceId+"&DeviceId="+deviceId+"&DeviceType=PocketPC&Cmd="
						+ cmdName);
		post.setHeader("Content-Type", "application/vnd.ms-sync.wbxml");
		post.setHeader("MS-ASProtocolVersion", "2.5");
		post.setHeader("X-MS-PolicyKey", "0");
		return post;
	}

	private void ping() {
		logger.info("Start Ping - " + wbAccount.getName());
		HttpPost post = getPost("Ping");
		Ping ping = new Ping();
		ping.setHeartbeatInterval(600);
		Folders folders = new Folders();
		ArrayList<Folder> folderList = new ArrayList<Folder>();
		Folder folder = new Folder();
		folder.setId(INBOXID);
		folder.setClass_name("Email");
		folderList.add(folder);
		folders.setFolders(folderList);
		ping.setFolders(folders);
		post.setEntity(new ByteArrayEntity(ACWBXMLParser.getWBXML(ping)));

		httpClient.execute(post, new FutureCallback<HttpResponse>() {
			@Override
			public void failed(Exception e) {
				logger.error("Ping failed,put to retry list!", e);
				calculateNextRetryTime(System.currentTimeMillis());
				hotmailTruepush.need2Reconnect(wbAccount.getId());
			}

			@Override
			public void completed(HttpResponse response) {
				try{
					//服务器内部异常，重试
					if(response.getStatusLine().getStatusCode() == 503){
						calculateNextRetryTime(System.currentTimeMillis());
						hotmailTruepush.need2Reconnect(wbAccount.getId());
						return;
					}
					if (response.getStatusLine().getStatusCode() != 200) {
						try {
							EntityUtils.consume(response.getEntity());
						} catch (IOException e) {
							logger.error("", e);
						}
						logger.error("Ping error status code,Ping will stop:"
								+ response.getStatusLine());
						calculateNextRetryTime(System.currentTimeMillis());
						hotmailTruepush.need2Stop(wbAccount.getId());
						return;
					}

					Ping ping = null;
					try {
						ping = (Ping) ACWBXMLParser.parseWBXML(response.getEntity()
								.getContent());
					} catch (IllegalStateException e) {
						logger.error("", e);
					} catch (IOException e) {
						logger.error("", e);
					}

					if (ping != null) {
						if (Ping.HEARTBEAT_EXPIRED.equals(ping.getStatus())) {
							logger.info("Ping heartbeat expired.");
							resetReconnectParam();
							ping();
						} else if (Ping.CHANGES_OCCURRED.equals(ping.getStatus())) {
							logger.info("[" + toString() + "]Account - "
									+ wbAccount.getName() + " - has new mail!");
							resetReconnectParam();
							setHasNewMail(true);
							ping();
						} else if(Ping.NEED_RETRY.equals(ping.getStatus())){
							logger.info("Ping Error occured,Need to retry.");
							ping();
						}else {
							logger.error("Ping status code - " + ping.getStatus()
									+ ",ping will stop!");
							calculateNextRetryTime(System.currentTimeMillis());
							hotmailTruepush.need2Stop(wbAccount.getId());
						}
					} else {
						logger.error("ping is null,Account - " + wbAccount.getName());
					}
				}catch(Exception e){
					logger.error("",e);
				}
			}

			@Override
			public void cancelled() {
				logger.error("Ping request cancelled!");
			}
		});
	}

	private void resetReconnectParam() {
		nextRetryTime = -1;
		nextRetryInterval = INIT_RETRY_INTERVAL;
	}

	protected void calculateNextRetryTime(long nowTime) {
		if (nextRetryInterval >= MAX_RETRY_INTERVAL) {
			nextRetryInterval = MAX_RETRY_INTERVAL;
		}
		nextRetryTime = nowTime + nextRetryInterval * 1000;

		nextRetryInterval *= 2;
	}

	@Override
	public void notifyReceiveMail() {
//		JmsSender.sendQueueMsg("{\"jmstype\":\"newmail\",\"aid\":\""
//				+ wbAccount.getId() + "\"}",
//				JMSTypes.HOTMAIL_NOTIFICATION_QUEUE);
		PutActiveSyncThreadPool.putAcpool(String.valueOf(wbAccount.getId()));
		setHasNewMail(false);
	}

	public static void main(String[] args) throws Exception {
		WebAccount wbAccount = new WebAccount();
		wbAccount.setName("youlianjie@hotmail.com");
		wbAccount.setPassword("198409251");
		HotmailTruepush.getInstance().addAccount(wbAccount);

		// wbAccount = new WebAccount();
		// wbAccount.setName("youlianjie@live.com");
		// wbAccount.setPassword("19840925");
		// HotmailPing ping2 = new HotmailPing(wbAccount);
		// ping2.start();
		//
		// Thread.sleep(5000);
		// ping1.shutdown();
		System.out.println("over");
	}

	@Override
	public boolean start() {
		try {
			initHttpclient();
		} catch (IOReactorException e) {
			logger.error("", e);
			return false;
		}

		// if (!login()) {
		// return false;
		// }

		// FolderSync fs = null;
		// fs = folderSync(wbAccount);
		// Add emailAdd = null;
		// if (fs != null && "1".equals(fs.getStatus()) && fs.getChanges() !=
		// null
		// && fs.getChanges().getAdd() != null
		// && fs.getChanges().getAdd().size() > 0) {
		// for (Add add : fs.getChanges().getAdd()) {
		// if ("2".equals(add.getType())) {
		// emailAdd = add;
		// break;
		// }
		// }
		// }
		//
		// if (emailAdd == null) {
		// logger.error("can not find inbox folder!");
		// shutdown();
		// return false;
		// }

		httpClient.start();
		ping();
		return true;
	}

	@Override
	public void shutdown() {
		try {
			httpClient.shutdown();
		} catch (InterruptedException e) {
			logger.error("", e);
		}
		logger.info("Account Ping for - " + wbAccount.getName()
				+ " - shutdown!");
	}

	@Override
	public void updateAccount(String password) {
		wbAccount.setPassword(password);
		httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(wbAccount.getName(), password));
	}

	@Override
	public boolean reconnect() {
		if (0 < nextRetryTime && nextRetryTime < System.currentTimeMillis()) {
			logger.info(wbAccount.getName() + " will reconnect!");
			ping();
			return true;
		} else {
			return false;
		}
	}

	public int getNextRetryInterval() {
		return nextRetryInterval;
	}

	public void setNextRetryInterval(int nextRetryInterval) {
		this.nextRetryInterval = nextRetryInterval;
	}

	public long getNextRetryTime() {
		return nextRetryTime;
	}

	public void setNextRetryTime(long nextRetryTime) {
		this.nextRetryTime = nextRetryTime;
	}
}
