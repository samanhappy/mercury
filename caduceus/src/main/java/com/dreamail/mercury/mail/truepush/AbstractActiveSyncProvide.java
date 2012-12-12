package com.dreamail.mercury.mail.truepush;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.mail.MessagingException;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.mail.receiver.AbstractProvide;
import com.dreamail.mercury.mail.truepush.impl.ACWBXMLParser;
import com.dreamail.mercury.mail.truepush.impl.ApplicationData;
import com.dreamail.mercury.mail.truepush.impl.Attachment;
import com.dreamail.mercury.mail.truepush.impl.Collection;
import com.dreamail.mercury.mail.truepush.impl.Collections;
import com.dreamail.mercury.mail.truepush.impl.IASResponse;
import com.dreamail.mercury.mail.truepush.impl.Options;
import com.dreamail.mercury.mail.truepush.impl.Sync;
import com.dreamail.mercury.mail.util.EmailConstant;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.util.StreamUtil;

public abstract class AbstractActiveSyncProvide extends AbstractProvide{
	protected String user;
	protected String password;
	protected String deviceId;
	public String serverId = "00000000-0000-0000-0000-000000000001";
	public String postUrl = "https://m.hotmail.com/Microsoft-Server-ActiveSync?User="+deviceId+"&DeviceId="+deviceId+
			"&DeviceType=PocketPC&Cmd=";
	final static int CONNECTION_TIMEOUT = 1000 * 60 *3;
	final static int SO_TIMEOUT = 1000 * 60 *3;
	protected DefaultHttpClient client = new DefaultHttpClient();
	public static final Logger logger = LoggerFactory.getLogger(AbstractActiveSyncProvide.class);
	private boolean chageTruncation = false;
	private boolean fetchKeyAgain = false;
	
	private static ConcurrentHashMap<String, List<String>> messageCache = new ConcurrentHashMap<String,List<String>>();
	
	public boolean isFetchKeyAgain() {
		return fetchKeyAgain;
	}

	public void setFetchKeyAgain(boolean fetchKeyAgain) {
		this.fetchKeyAgain = fetchKeyAgain;
	}

	public boolean isChageTruncation() {
		return chageTruncation;
	}

	public void setChageTruncation(boolean chageTruncation) {
		this.chageTruncation = chageTruncation;
	}

	protected void initMessageCache(String aid){
		//初始化uuid缓存.
		if(!messageCache.containsKey(aid)){
			List<String> uuidList = new ArrayList<String>();
			List<Clickoo_message> msgList  = new MessageService()
					.getAllReceivedMessageIdByAccountId(Long.parseLong(aid));
			for(Clickoo_message message:msgList){
				uuidList.add(message.getUuid());
			}
			logger.info("init uuid size is :"+uuidList.size());
			messageCache.put(aid, uuidList);
		}
	}
	
	protected void addMessageCache(String aid,String uuid){
		if(messageCache.containsKey(aid)){
			messageCache.get(aid).add(uuid.toUpperCase());
			return;
		}
		List<String> uuidList = new ArrayList<String>();
		uuidList.add(uuid);
		messageCache.put(aid, uuidList);
	}
	
	protected boolean containsMessageCache(String aid,String uuid){
		if(messageCache.containsKey(aid)){
			return messageCache.get(aid).contains(uuid.toUpperCase());
		}
		return false;
	}
	
	protected void removeMessageCache(String aid,String uuid){
		if(messageCache.containsKey(aid)){
			messageCache.get(aid).remove(uuid);
		}
	}

	/**
	 * 
	 * @param name
	 * @param password
	 */
	protected void authClient(String name,String password){
		client.getCredentialsProvider().setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(name, password));
	}
	/**
	 * http头信息
	 * @param post
	 */
	protected void setHeader(HttpPost post){
		post.setHeader("Content-Type", "application/vnd.ms-sync.wbxml");
		post.setHeader("MS-ASProtocolVersion", "2.5");
		post.setHeader("X-MS-PolicyKey", "0");
	}
	
	/**
	 * 封装Email
	 * @param data
	 * @return
	 */
	protected Email getEmail(ApplicationData data,String uuid){
		Email email = new Email();
		//pop收取uuid大写字母,此处保持一致.
		email.setUuid(uuid==null ? uuid : uuid.toUpperCase());
		email.setSubject(data.getSubject());
		email.setFrom(formatAddress(data.getFrom()));
		email.setTo(formatAddress(data.getTo()));
		email.setCc(formatAddress(data.getCc()));
		email.setBcc(formatAddress(data.getBcc()));
		email.setSendTime(data.getDateReceived());
		email.setReceiveTime(new Date());
		try {
			email.setBody(data.getInternetCPID_1().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("",e);
		}
		if(data.getAttachments() != null){
			List<Attachment> attList = data.getAttachments().getAttachments();
			email.setAttachList(fetchAttachments(attList));
		}
//		email.setSize(size);
		return email;
	}
	
	private static String formatAddress(String address){
		StringBuffer addressBuffer = new StringBuffer();
		if(address !=null && !address.trim().equals("")){
			String[] addressArr = address.split(",");
			for(String perAddress : addressArr){
				if(perAddress==null || perAddress.trim().equals(""))
					continue;
				if(perAddress.contains("<")){
					addressBuffer.append(perAddress);
				}else{
					addressBuffer.append("<");
					addressBuffer.append(perAddress);
					addressBuffer.append(">");
				}
				addressBuffer.append(",");
			}
		}
		String formatAddress = addressBuffer.toString();
		if(formatAddress!=null && formatAddress.contains(",")){
			formatAddress = formatAddress.substring(0,formatAddress.length()-1);
		}
		return formatAddress;
	}
	
	/**
	 * 封装attachment.
	 * @param attList
	 * @return
	 */
	protected List<Clickoo_message_attachment> fetchAttachments(List<Attachment> attList){
		List<Clickoo_message_attachment> msgAttachment = new ArrayList<Clickoo_message_attachment>();
		
		for(Attachment attachment:attList){
			Clickoo_message_attachment att = new Clickoo_message_attachment();
			String name = attachment.getDisplayName();
			String type = null;
			if (name.indexOf(".") != -1) {
				type = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
				name = name.substring(0, name.lastIndexOf("."));
			}
			att.setName(name);
			att.setType(type);
			//如果附件超出限制 不收取
			if(Integer.parseInt(attachment.getAttSize()) > EmailConstant.ActiveSycn.REQUEST_MIME_SIZE)
				continue;
			HttpPost attachmentPost = new HttpPost(new StringBuffer(postUrl).append(EmailConstant.ActiveSycn.REQUEST_GET_ATTACHMENT).
					append("&AttachmentName=").append(attachment.getAttName()).toString());
			attachmentPost.setHeader("MS-ASProtocolVersion", "2.5");
			attachmentPost.setHeader("X-MS-PolicyKey", "0");
			HttpResponse attachmentResponse = null;
			DefaultHttpClient attachmentClient = new DefaultHttpClient();
			attachmentClient.getCredentialsProvider().setCredentials(AuthScope.ANY,
							new UsernamePasswordCredentials(user, password));
			//设置连接超时时间(单位毫秒) 
			attachmentClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
			//设置读数据超时时间(单位毫秒) 
			attachmentPost.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SO_TIMEOUT);
			try {
				attachmentResponse = attachmentClient.execute(attachmentPost);
			} catch (SocketTimeoutException e) {
				try {
					attachmentResponse = attachmentClient.execute(attachmentPost);
					try{
						if(attachmentResponse != null){
							byte[] in = StreamUtil.getByteByInputStream(attachmentResponse.getEntity().getContent());
							att.setIn(in);
							att.setLength(att.getIn()==null?0:in.length);
							logger.info("att name:::"+att.getName()+"  att size:::"+att.getLength());
							msgAttachment.add(att);
						}
					}catch(Exception ex){
						logger.error("attachmentResponse.getEntity.getContent exception",ex);
					}
					logger.error("SocketTimeoutException reconection",e);
					continue;
				}catch (Exception exception) {
					logger.error("",e);
					continue;
				}
			}catch(ClientProtocolException clientProtocolException) {
				logger.error("",clientProtocolException);
			} catch (IOException ioException) {
				logger.error("",ioException);
			}
			try{
				if(attachmentResponse != null){
					byte[] in = StreamUtil.getByteByInputStream(attachmentResponse.getEntity().getContent());
					att.setIn(in);
					att.setLength(att.getIn()==null?0:in.length);
					logger.info("att name:::"+att.getName()+"  att size:::"+att.getLength());
					msgAttachment.add(att);
				}
			}catch(Exception e){
				logger.error("attachmentResponse.getEntity.getContent exception",e);
			}
		}
		return msgAttachment;

	}
	
	@Override
	public void receiveLargeMail(Context context) throws MessagingException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	protected String fetchFirstSyncKey() throws ClientProtocolException, IOException{
		HttpPost post = new HttpPost(new StringBuffer(postUrl).append(EmailConstant.ActiveSycn.REQUEST_SYNC).toString());
		Sync sync = new Sync();
		Collections collections = new Collections();
		List<Collection> collectionList = new ArrayList<Collection>();
		Collection collection = new Collection();
		collection.setSyncKey(EmailConstant.ActiveSycn.INIT_SYNCKEY);
		collection.setCollectionId(serverId);
		collection.setClass_name(EmailConstant.ActiveSycn.COLLECTION_CLASS_NAME);
		collectionList.add(collection);
		collections.setCollections(collectionList);
		sync.setCollections(collections);
		post.setEntity(new ByteArrayEntity(ACWBXMLParser.getWBXML(sync)));
		setHeader(post);
		HttpResponse response = null;
		//设置连接超时时间(单位毫秒) 
		client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
		 //设置读数据超时时间(单位毫秒) 
		post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SO_TIMEOUT);
		try{
			response = client.execute(post);
		}catch(SocketTimeoutException e){
			logger.info("response time out,try again.");
			response = client.execute(post);
		}
		IASResponse acResponse = ACWBXMLParser
				.parseWBXML(response.getEntity().getContent());
		String syncKey = null;
		Sync syncResponse = (Sync) acResponse;
		if (syncResponse != null
				&& syncResponse.getCollections() != null
				&& syncResponse.getCollections().getCollections() != null
				&& syncResponse.getCollections().getCollections().size() > 0
				&& EmailConstant.ActiveSycn.RESPONSE_RIGHT_STATUS.equals(syncResponse.getCollections()
						.getCollections().get(0).getStatus())) {
			syncKey = syncResponse.getCollections().getCollections().get(0)
					.getSyncKey();
		}
		return syncKey;
	}
	
	/**
	 * 
	 * @param syncKey
	 * @param truncation
	 * @return
	 */
	protected HttpResponse getSyncResponse(String syncKey,boolean truncation){
		Sync sync = new Sync();
		Collections collections = new Collections();
		List<Collection> collectionList = new ArrayList<Collection>();
		Collection collection = new Collection();
		HttpPost post = new HttpPost(new StringBuffer(postUrl).append(EmailConstant.ActiveSycn.REQUEST_SYNC).toString());
		sync = new Sync();
		collections = new Collections();
		collectionList = new ArrayList<Collection>();
		collection = new Collection();
		collection.setSyncKey(syncKey);
		collection.setCollectionId(serverId);
		collection.setClass_name(EmailConstant.ActiveSycn.COLLECTION_CLASS_NAME);
		collection.setDeletesAsMoves("");
		collection.setGetChanges("");
		collection.setWindowSize(Integer.parseInt(EmailConstant.ActiveSycn.REQUEST_WINDOW_SIZE));
		Options options = new Options();
		options.setFilterType(EmailConstant.ActiveSycn.REQUEST_FILTER_TYPE);
		options.setMimeSupport(EmailConstant.ActiveSycn.REQUEST_MIME_SUPPORT);
		options.setMimeTruncation("5");
		//true,截取最小正文以快速接受服务器响应  false正常收取完整正文.
		if(truncation)
			options.setTruncation("1");
		collection.setOptions(options);
		collectionList.add(collection);
		collections.setCollections(collectionList);
		sync.setCollections(collections);
		post.setEntity(new ByteArrayEntity(ACWBXMLParser.getWBXML(sync)));
		setHeader(post);
		HttpResponse response = null;
		//设置连接超时时间(单位毫秒) 
		client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
		//设置读数据超时时间(单位毫秒) 
		post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SO_TIMEOUT);
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			logger.error("",e);
		} catch (SocketTimeoutException e) {
			logger.error("SocketTimeoutException reconection",e);
			try {
				return response = client.execute(post);
			}catch (Exception e1) {
				logger.error("",e);
			}
		}catch (IOException e) {
			logger.error("",e);
		}
		return response;
	}
}
