package com.dreamail.mercury.mail.truepush.impl.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import com.dreamail.mercury.dal.dao.AccountDao;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.mail.receiver.attachment.impl.AttachmentFormatControl;
import com.dreamail.mercury.mail.truepush.AbstractActiveSyncProvide;
import com.dreamail.mercury.mail.truepush.impl.ACWBXMLParser;
import com.dreamail.mercury.mail.truepush.impl.Add;
import com.dreamail.mercury.mail.truepush.impl.ApplicationData;
import com.dreamail.mercury.mail.truepush.impl.HotmailTruepush;
import com.dreamail.mercury.mail.truepush.impl.IASResponse;
import com.dreamail.mercury.mail.truepush.impl.Sync;
import com.dreamail.mercury.mail.truepush.threadpool.PutActiveSyncThreadPool;
import com.dreamail.mercury.mail.util.EmailConstant;
import com.dreamail.mercury.mail.util.EmailReceiveUtil;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.proces.SaveDataProces;
import com.dreamail.mercury.receiver.config.CaduceusPropertiesDeploy;

public class ActiveSyncProvideImpl extends AbstractActiveSyncProvide {

	@Override
	public void receiveMail(Context context) {
		logger.info("active sync start.....");
		initMessageCache(String.valueOf(context.getAccountId()));
		user = context.getLoginName();
		password = context.getPassword();
		deviceId = EmailReceiveUtil.getDeviceId(user,
				String.valueOf(context.getAccountId()));
		authClient(user, password);
		String syncKey = context.getSyncKey();
		HotmailTruepush.updatePushTask(false, context.getAccountId());
		if (syncKey == null || "".equals(syncKey.trim()) || "0".equals(syncKey)) {
			deviceId = EmailReceiveUtil.getFirstDeviceId();
			logger.info("user:"+user+" deviceid:"+deviceId);
			context.setTruncation(true);
			fetchFirtMails(syncKey,context);
			return;
		}
		logger.info("user:"+user+" deviceid:"+deviceId+"active sync start.....get response ");
		try {
			fetchNewMails(syncKey,context);
		} catch (ClientProtocolException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	/**
	 * 
	 * @param syncKey
	 * @param context
	 */
	public void fetchFirtMails(String syncKey, Context context) {
		try {
			syncKey = fetchFirstSyncKey();
		} catch (ClientProtocolException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
		if (syncKey == null || "".equals(syncKey.trim()) || "0".equals(syncKey)){
			HotmailTruepush.updatePushTask(true, context.getAccountId());
			return;
		}
		logger.info("account:" + user + " syncKey:" + syncKey+ " get key first time.");
		try {
			fetchNewMails(syncKey,context);
		} catch (IllegalStateException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		}
	}

	/**
	 * 根据key取邮件.
	 * @param syncKey
	 * @param truncation
	 * @param context
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private void fetchNewMails(String syncKey,Context context) throws IllegalStateException, IOException{
		HttpResponse response = getSyncResponse(syncKey,context.isTruncation());
		IASResponse acResponse = ACWBXMLParser.parseWBXML(response
				.getEntity().getContent());
		Sync syncEmail = (Sync) acResponse;
		// 如果协议返回空
		if (syncEmail.getCollections() == null
				|| syncEmail.getCollections().getCollections() == null
				|| syncEmail.getCollections().getCollections().get(0) == null) {
			logger.error("account:" + user + " syncKey:" + syncKey
					+ " response error...");
			HotmailTruepush.updatePushTask(true, context.getAccountId());
			return;
		}
		String status = syncEmail.getCollections().getCollections().get(0).getStatus();
		if(!EmailConstant.ActiveSycn.RESPONSE_RIGHT_STATUS.equals(status) && !isFetchKeyAgain()){
			context.setTruncation(true);
			fetchFirtMails(EmailConstant.ActiveSycn.INIT_SYNCKEY,context);
			setFetchKeyAgain(true);
			return;
		}
		syncKey = handleResponse(syncEmail,context,syncKey);
		String moreAvailable = syncEmail.getCollections().getCollections().get(0).getMoreAvailable();
		if(moreAvailable != null || isChageTruncation()){
			setChageTruncation(false);
			logger.info("get more mails...key is "+syncKey);
			fetchNewMails(syncKey,context);
			return;
		}
		if(!context.getSyncKey().equals(syncKey)){
			Clickoo_mail_account account = context.getAccount();
			account.setMaxuuid(syncKey);
			//更新syncKey.
			new AccountDao().updateMaxUuid(account);
		}
		HotmailTruepush.updatePushTask(true, context.getAccountId());
	}
	
	/**
	 * 
	 * @param syncEmail
	 * @param context
	 * @param syncKey
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	private String handleResponse(Sync syncEmail,Context context,String syncKey) throws IllegalStateException, IOException{
		if (syncEmail.getCollections().getCollections().get(0).getCommands() != null) {
			logger.info("active sync start.....new mail " + "account:"+ context.getLoginName());
			Map<String, Email> emailList = new HashMap<String, Email>();
			Clickoo_mail_account account = context.getAccount();
//			account.setMaxuuid(syncKey);
//			new AccountDao().updateMaxUuid(account);
			List<Add> addList = syncEmail.getCollections().getCollections()
					.get(0).getCommands().getAdds();
			if (addList != null) {
				for (Add add : addList) {
					ApplicationData data = add.getApplicationData();
					String uuid = add.getServerId();
					if ((data.getDateReceived() != null&& account.getRegistrationDate() != null
							&& data.getDateReceived().before(account.getRegistrationDate())) 
							|| containsMessageCache(String.valueOf(context.getAccountId()),uuid) || uuid == null) {
						continue;
					}
					if(context.isTruncation()){
						context.setTruncation(false);
						setChageTruncation(true);
						logger.info("change Tuncation :"+context.isTruncation());
						return syncKey;
					}
					// 封装Email
					
					addMessageCache(String.valueOf(context.getAccountId()),uuid);
					Email mail = getEmail(data, uuid);
					logger.info("mail subject::" + mail.getSubject());
					if (mail != null) {
						String mid = String.valueOf(new MessageDao()
								.getNextMessageId(String.valueOf(context
										.getAccountId()), uuid));
						emailList.put(mid, mail);
					}
				}
				if(emailList.size()>0){
					context.setEmailList(emailList);
					new AttachmentFormatControl().doProces(context);
					new SaveDataProces().doProces(context);
					sendMessage(context);
				}
			}
		}
		syncKey = syncEmail.getCollections().getCollections().get(0)
				.getSyncKey();
		return syncKey;
	}
	
	public static void main(String[] args) throws Exception {
		CaduceusPropertiesDeploy cpd = new CaduceusPropertiesDeploy();
		cpd.init();
		PutActiveSyncThreadPool.putAcpool("14");
	}

}
