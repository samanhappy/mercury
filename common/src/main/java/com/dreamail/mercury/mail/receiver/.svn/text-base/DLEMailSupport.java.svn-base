package com.dreamail.mercury.mail.receiver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.domain.Body;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.domain.EmailCacheObject;
import com.dreamail.mercury.domain.EmailSupport;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailattachment;
import com.dreamail.mercury.mail.connection.CloseConnectionImpl;
import com.dreamail.mercury.mail.receiver.attachment.impl.AttachmentFormatControl;
import com.dreamail.mercury.memcached.EmailCacheManager;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserProvide;
import com.dreamail.mercury.store.Volume;
import com.dreamail.mercury.store.Volumes;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JsonUtil;
import com.dreamail.mercury.util.ZipUtil;

public class DLEMailSupport implements DLSupport {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(DLEMailSupport.class);

	/**
	 * 根据类型获取IDLProvide.
	 * 
	 * @param type
	 * @return IDLProvide
	 */
	private IDLProvide getIDLProvide(String type) {
		IDLProvide dlprovide = null;
		if (Constant.POP3.equals(type)) {
			dlprovide = new DLEmailPop3Impl();
		} else if (Constant.IMAP.equals(type)) {
			dlprovide = new DLEmailImapImpl();
		} else if (Constant.HTTP.equals(type)) {
			dlprovide = new DLEmailEWSImpl();
		}
		return dlprovide;
	}
	
	/**
	 * 根据传入参数下载附件或处理后的附件.
	 * 
	 * @param account
	 * @param webEmail
	 * @param uid
	 * @return Attachment
	 */
	public Clickoo_message_attachment downLoadAttachment(WebAccount account, WebEmail webEmail,
			String uid,EmailSupport eSupport,Clickoo_message message) throws MessagingException {
		WebEmailattachment attach = webEmail.getAttach()[0];
		String attachname = attach.getName() + "." + attach.getType();
		IDLProvide dlprovide = getIDLProvide(account.getReceiveProtocolType());
		String mid = webEmail.getHead().getMessageId();
		if (message == null) {
			logger.info("[mid:" + mid
					+ "] has been removed.cant't download Attachment.");
			return null;
		}
		if (account.getName().indexOf("@") != -1
				&& "gmail.com"
						.equalsIgnoreCase(account.getName().split("@")[1])) {
			if (!account.getName().contains("recent:")) {
				account.setName("recent:" + account.getName());
			}
		}
		//原始附件
		Clickoo_message_attachment attachment = dlprovide.dlAttachment(account, message
				.getUuid(), attachname, (int) attach.getSize(),eSupport);
//		String deviceModel = new UserDao().getDeviceModelByUid(uid);
		if(attachment.getIn()==null){
			logger.info("Attachment.getIn is null.attachment is "+attachment.getName()+" size is "+attachment.getLength());
//			return attachment;
		}
		
		List<String> deviceList = new MessageDao().getDeviceByMid(mid);
		StringBuffer deviceModel = new StringBuffer();
		if (deviceList != null) {
			for (int i = 0; i < deviceList.size(); i++) {
				deviceModel.append(deviceList.get(i) + "#");
			}
		}
		//处理附件
		new AttachmentFormatControl().getSourceAttachment(
				attachment, String.valueOf(account.getId()), deviceModel.toString());
		return attachment;
	}

	/**
	 * 根据传入参数下载邮件正文.
	 * 
	 * @param account
	 * @param mid
	 * @return Body
	 */
	public Body downLoadBody(WebAccount account, String mid,Clickoo_message message)
			throws MessagingException {
		IDLProvide dlprovide = getIDLProvide(account.getReceiveProtocolType());
		if (message == null) {
			logger.info("[mid:" + mid
					+ "] has been removed.cant't download Body.");
			return null;
		}
		if (account.getName().indexOf("@") != -1
				&& "gmail.com"
						.equalsIgnoreCase(account.getName().split("@")[1])) {
			if (!account.getName().contains("recent:")) {
				account.setName("recent:" + account.getName());
			}
		}
		logger.info(account.getName());
		logger.info(account.getPassword());
		logger.info(account.getReceivePort());
		logger.info(account.getReceiveTs());
		Body body = dlprovide.dlData(account, message.getUuid());
		return body;
	}

	/**
	 * 根据传入参数下载邮件.
	 * 
	 * @param account
	 * @param mid
	 * @return Email
	 */
	public Email downLoadEmail(WebAccount account, String mid, List<Clickoo_message_attachment> exitAtt,EmailSupport eSupport,Clickoo_message message,boolean dlBody)
			throws MessagingException {
		IDLProvide dlprovide = getIDLProvide(account.getReceiveProtocolType());
		if (message == null) {
			logger.info("[mid:" + mid
					+ "] has been removed.cant't download Email.");
			return null;
		}
		if (account.getName().indexOf("@") != -1
				&& "gmail.com"
						.equalsIgnoreCase(account.getName().split("@")[1])) {
			if (!account.getName().contains("recent:")) {
				account.setName("recent:" + account.getName());
			}
		}
		Email email = dlprovide.dlEmail(account, message.getUuid(),exitAtt,eSupport,dlBody);
		List<Clickoo_message_attachment> attList = new ArrayList<Clickoo_message_attachment>();
		if (email.getAttachList() != null) {
			for (Clickoo_message_attachment att : email.getAttachList()) {
				if (att.getName().indexOf(".") != -1) {
					att.setName(att.getName().substring(0,
							att.getName().lastIndexOf(".")));
				}
				attList.add(att);
			}
			email.setAttachList(attList);
		}
		return email;
	}

	/**
	 * 根据传入参数下载邮件所有缺失附件.
	 * 
	 * @param account
	 * @param mid
	 * @return Email
	 */
	public List<Clickoo_message_attachment> downLoadAllAttachment(WebAccount account,
			String mid, List<Clickoo_message_attachment> exitAtt,EmailSupport eSupport,Clickoo_message message) throws MessagingException {
		IDLProvide dlprovide = getIDLProvide(account.getReceiveProtocolType());
		if (message == null) {
			logger.info("[mid:" + mid
					+ "] has been removed.cant't download Email.");
			return null;
		}
		if (account.getName().indexOf("@") != -1
				&& "gmail.com"
						.equalsIgnoreCase(account.getName().split("@")[1])) {
			if (!account.getName().contains("recent:")) {
				account.setName("recent:" + account.getName());
			}
		}
		List<Clickoo_message_attachment> attList = dlprovide.dlAllAttachment(account, message
				.getUuid(), exitAtt,eSupport);
		return attList;
	}

	/**
	 * 根据传入参数下载正文、附件等并存入memcached.
	 * 
	 * @param constant
	 * @param account
	 * @param webEmail
	 * @param uid
	 * @param mid
	 * @return Object
	 * @throws MessagingException
	 */
	@Override
	public Object dlMail(EmailCacheObject emailCache,String constant,Clickoo_message message, WebAccount account,
			WebEmail webEmail, String uid, String mid)
			throws MessagingException {
		//下载邮件模块当找不到该邮件时，应抛出找不到邮件的异常，所有调用模块扑捉到之后返回客户端此邮件未找到的错误代码
		//下载时，寻找邮件时应设置超时时间，如在时间内未找到邮件，请抛出超时异常
		logger.info("Current action "+constant+" begins.");
		Object obj = null;
		if(message==null){
			if(mid!=null){
				MessageService messageService = new MessageService();
				message = messageService.selectMessageById(Long
						.parseLong(mid));
			}else{
				logger.error("mid is null, get Message error.");
			}
		}
		if(emailCache == null){
			emailCache = EmailCacheManager.get(mid);
		}
		logger.info("message Id is "+message.getId());
		try {
			if (Constant.DOWNLOAD_EMAIL.equalsIgnoreCase(constant)
					|| Constant.DOWNLOAD_EMAIL_NODEAL.equalsIgnoreCase(constant)) {
				obj = dlEmail(emailCache, account, mid, message,uid);
			} else if (Constant.DOWNLOAD_BODY.equalsIgnoreCase(constant)) {
				if(emailCache == null){
					emailCache = new EmailCacheObject();
				}
				obj = dlBody(emailCache, account, mid, message);
			} else if (Constant.DOWNLOAD_ATT.equalsIgnoreCase(constant)) {
				if(emailCache == null){
					emailCache = new EmailCacheObject();
				}
				obj = dlAttachment(emailCache, account, webEmail, uid, message, mid);
				
			} else if (Constant.DOWNLOAD_ALL_ATT.equalsIgnoreCase(constant)
					|| Constant.DOWNLOAD_ALL_ATT_NODEAL.equalsIgnoreCase(constant)) {
				obj = dlAttachments(emailCache,account,mid,message,uid);
			}
			log(obj);
			new CloseConnectionImpl().closeConnection(account.getStore(), account
					.getFolder());
		}catch(DLEmailException e1){
			if(Constant.GET_MAIL_FAIL.equals(e1.getMessage())){
				logger.error("[account:"+account.getName()+"]"+Constant.GET_MAIL_FAIL+",maybe cause by message dosen't exit.mid is "+message.getId());
			}else if(Constant.PARSE_MAIL_FAIL.equals(e1.getMessage())){
				logger.error("[account:"+account.getName()+"] mid is "+message.getId()+", "+Constant.PARSE_MAIL_FAIL);
			}
			DLEmailException exc = new DLEmailException();
			exc.setMessage(e1.getMessage());
			throw exc;
		}catch (Exception e) {
			if(e instanceof AuthenticationFailedException){
				logger.error("[account:"+account.getName()+"] connection fail.",e);
			}
			logger.error("Download exception,",e);
			DLEmailException exc = new DLEmailException();
			exc.setMessage(e.getMessage());
			throw exc;
		}
		logger.info("Current action "+constant+" ends.");
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	private void log(Object obj){
		if(obj instanceof Body){
			Body body = (Body) obj;
			if(body!=null){
				logger.info("Download email body success. content is:"+ body.getData());
			}
		}else if(obj instanceof Clickoo_message_attachment){
			Clickoo_message_attachment attachment = (Clickoo_message_attachment) obj;
			if(attachment!=null){
				logger.info("Download email attachment success. attName is:"+ attachment.getName()+", attSize is:"+attachment.getLength());
				if(attachment.getChildList()!=null && attachment.getChildList().size()>0){
					logger.info("Handled attName is:"+ attachment.getChildList().get(0).getName()+", attSize is:"+attachment.getChildList().get(0).getLength());
				}
			}
		}else if(obj instanceof List){
			List<Clickoo_message_attachment> list = (List<Clickoo_message_attachment>) obj;
			if(list!=null && list.size()>0){
				for(Clickoo_message_attachment att:list){
					logger.info("Download email attachment success. attName is:"+ att.getName()+", attSize is:"+att.getLength());
					if(att.getChildList()!=null && att.getChildList().size()>0){
						logger.info("Handled attName is:"+ att.getChildList().get(0).getName()+", attSize is:"+att.getChildList().get(0).getLength());
					}
				}
			}
		}else if(obj instanceof Email){
			Email email = (Email) obj;
			if(email!=null){
				logger.info("Download email success. subject is:"+ email.getSubject());
				logger.info("email contnet is "+ new String(email.getBody()));
				if(email.getAttachList()!=null && email.getAttachList().size()>0){
					for(Clickoo_message_attachment att:email.getAttachList()){
						logger.info("Download email attachment success. attName is:"+ att.getName()+", attSize is:"+att.getLength());
						if(att.getChildList()!=null && att.getChildList().size()>0){
							logger.info("Handled attName is:"+ att.getChildList().get(0).getName()+", attSize is:"+att.getChildList().get(0).getLength());
						}
					}
				}
			}
		}
	}
	/**
	 * 封装重新下载邮件的逻辑.
	 * @param emailCache
	 * @param account
	 * @param mid
	 * @param message
	 * @return
	 * @throws MessagingException
	 */
	@SuppressWarnings("unchecked")
	private Email dlEmail(EmailCacheObject emailCache,WebAccount account,String mid,Clickoo_message message,String uid) throws MessagingException{
		List<Clickoo_message_attachment> exitAtt = null;
		EmailSupport eSupport = new EmailSupport();
		JSONObject jsonObj = null;
		String attJson = null;
		boolean dlBody = true;
		if(emailCache!=null){
			attJson = emailCache.getAttachmentJson();
			if(attJson!=null){
				jsonObj = JSONObject.fromObject(attJson);
				exitAtt = getExitAttList(attJson,jsonObj);
			}
			if(emailCache.getData()!=null){
				dlBody = false;
			}
		}
		Email email = downLoadEmail(account, mid,exitAtt,eSupport,message,dlBody);
		if(emailCache!=null && emailCache.getData()!=null){
			email.setBody(ZipUtil.decompress(emailCache.getData()));
		}
//		if(Constant.DOWNLOAD_EMAIL.equalsIgnoreCase(constant)){
			new EmailParserProvide().dealAttachment(account, mid, email);
//		}
		if(exitAtt!=null && exitAtt.size()>0){
			List<Clickoo_message_attachment> dlAtts = email.getAttachList();
			if (dlAtts != null && dlAtts.size() > 0) {
				removeAttJson(dlAtts,attJson,jsonObj,emailCache);
			}
		}
		emailCache = JsonUtil.getInstance().parseEmail(
				String.valueOf(account.getId()), email);
		if(jsonObj!=null){
			String cacheJson = emailCache.getAttachmentJson();
			JSONObject attJsonObj = null;
			if(cacheJson!=null){
				attJsonObj = JSONObject.fromObject(cacheJson);
				Set<String> set = jsonObj.keySet();
				if (set != null) {
					for(String id:set){
						attJsonObj.put(id, jsonObj.get(id));
					}
				}
				emailCache.setAttachmentJson(attJsonObj.toString());
			}else{
				emailCache.setAttachmentJson(jsonObj.toString());
			}
		}
		emailCache.setAttachNums(eSupport.getAttNum());
		EmailCacheManager.addEmail(mid, emailCache, -1);
		return email;
	}
	/**
	 * 封装重新下载正文的逻辑.
	 * @param emailCache
	 * @param account
	 * @param mid
	 * @param message
	 * @return
	 * @throws MessagingException
	 */
	private Body dlBody(EmailCacheObject emailCache,WebAccount account,String mid,Clickoo_message message) throws MessagingException{
		Body body = downLoadBody(account, mid,message);
		if (body != null && body.getData() != null) {
			emailCache.setData(ZipUtil.compress(body.getData().getBytes()));
			emailCache.setData_size(body.getData().getBytes().length);
		} else {
			emailCache.setData("".getBytes());
			emailCache.setData_size(0);
		}
		EmailCacheManager.addEmail(mid, emailCache, -1);
		return body;
	}
	/**
	 * 封装重新下载单封附件的逻辑.
	 * @param emailCache
	 * @param account
	 * @param webEmail
	 * @param uid
	 * @param message
	 * @param mid
	 * @return
	 * @throws MessagingException
	 */
	private Clickoo_message_attachment dlAttachment(EmailCacheObject emailCache,WebAccount account,WebEmail webEmail,String uid,Clickoo_message message,String mid) throws MessagingException{
		EmailSupport eSupport = new EmailSupport();
		Clickoo_message_attachment att = downLoadAttachment(account, webEmail, uid,eSupport,message);
		WebEmailattachment attach = webEmail.getAttach()[0];
		String attJson = emailCache.getAttachmentJson();
		JSONObject jsonObj = null;
		if (attJson != null) {
			jsonObj = JSONObject.fromObject(attJson);
			JSONObject jsonAtt = JsonUtil.getInstance().getAttachmentJsonByName(jsonObj, attach.getName(), (int) attach.getSize());
			if(jsonAtt!=null){
				JSONObject jsonDlAtt = JsonUtil.getInstance().getAttachmentJsonByParent(jsonObj, (String) jsonAtt.get(Constant.ATTACHMENT_ID));
				if(jsonDlAtt!=null){
					jsonObj.remove(jsonDlAtt.get(Constant.ATTACHMENT_ID));
				}
				jsonObj.remove(jsonAtt.get(Constant.ATTACHMENT_ID));
			}
		} else {
			jsonObj = new JSONObject();
		}
		JsonUtil.getInstance().handleAttatchment(jsonObj, att, 0);
		emailCache.setAttachNums(eSupport.getAttNum());
		emailCache.setAttachmentJson(jsonObj.toString());
		EmailCacheManager.addEmail(mid, emailCache, -1);
		if("0".equals(attach.getIsdown()) && att.getChildList()!=null && att.getChildList().size()==1){
			return att.getChildList().get(0);
		}else{
			return att;
		}
	}
	/**
	 * 封装重新下载所有缺失附件的逻辑.
	 * @param emailCache
	 * @param account
	 * @param mid
	 * @param message
	 * @return
	 * @throws MessagingException
	 */
	private List<Clickoo_message_attachment> dlAttachments(EmailCacheObject emailCache,WebAccount account,String mid,Clickoo_message message,String uid) throws MessagingException{
		EmailSupport eSupport = new EmailSupport();
		String attJson = emailCache.getAttachmentJson();
		JSONObject jsonObj = null;
		if(attJson!=null){
			jsonObj = JSONObject.fromObject(attJson);
		}
		List<Clickoo_message_attachment> exitAtt = getExitAttList(attJson,jsonObj);
		List<Clickoo_message_attachment> dlAtts = downLoadAllAttachment(account, mid,
				exitAtt,eSupport,message);
		List<Clickoo_message_attachment> dlAttCache = new ArrayList<Clickoo_message_attachment>();
		if (dlAtts != null && dlAtts.size() > 0) {
//			Email email = new Email();
//			email.setAttachList(dlAtts);
//			new EmailParserProvide().dealAttachment(account, mid, email);
//			String deviceModel = new UserDao().getDeviceModelByUid(uid);
			
			List<String> deviceList = new MessageDao().getDeviceByMid(mid);
			StringBuffer deviceModel = new StringBuffer();
			if (deviceList != null) {
				for (int i = 0; i < deviceList.size(); i++) {
					deviceModel.append(deviceList.get(i) + "#");
				}
			}
			
			for(Clickoo_message_attachment dlAtt:dlAtts){
				new AttachmentFormatControl().getSourceAttachment(
						dlAtt, String.valueOf(account.getId()), deviceModel.toString());
				dlAttCache.add(dlAtt);
			}
			
			setAttJson(dlAttCache,attJson,jsonObj,emailCache);
		}
		emailCache.setAttachNums(eSupport.getAttNum());
		EmailCacheManager.addEmail(mid, emailCache, -1);
		return dlAtts;
	}
	
	/**
	 * 获取缓存和文件服务器上都存在的附件.
	 * @param attJson
	 * @param jsonObj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Clickoo_message_attachment> getExitAttList(String attJson,JSONObject jsonObj){
		List<Clickoo_message_attachment> exitAtt = null;
		if (attJson != null) {
			Set<String> set = jsonObj.keySet();
			if (set != null) {
				exitAtt = new ArrayList<Clickoo_message_attachment>();
				for (String id : set) {
					JSONObject jsonChild = jsonObj.getJSONObject(id);
					if(jsonChild == null || (Integer) jsonChild.get(Constant.ATTACHMENT_PARENT)!=0){
						continue;
					}
					Clickoo_message_attachment att = new Clickoo_message_attachment();
					att.setId(Long.parseLong(id));
					att.setName((String) jsonChild.get(Constant.ATTACHMENT_NAME));
					att.setType((String) jsonChild.get(Constant.ATTACHMENT_TYPE));
					att.setParent((Integer) jsonChild.get(Constant.ATTACHMENT_PARENT));
					att.setPath((String) jsonChild.get(Constant.ATTACHMENT_PATH));
					att.setLength((Integer) jsonChild.get(Constant.ATTACHMENT_LENGTH));
					Volumes volumeService = new Volumes();
					Volume volume = volumeService.getVolumeByMeta(Volumes.MetaEnum.CURRENT_MESSAGE_VOLUME);
					StringBuffer path = new StringBuffer(volume.getPath());
					path.append(File.separator);
					path.append(att.getPath());
					File f = new File(path.toString());
					if(f.exists()){
						exitAtt.add(att);
					}
				}
			}
		}
		return exitAtt;
	}
	/**
	 * 移除重新下载的附件的原始缓存.
	 * @param dlAtts
	 * @param attJson
	 * @param jsonObj
	 * @param emailCache
	 */
	private void removeAttJson(List<Clickoo_message_attachment> dlAtts,String attJson,JSONObject jsonObj,EmailCacheObject emailCache){
		if (attJson == null) {
			return;
//			jsonObj = new JSONObject();
		}
		for (Clickoo_message_attachment dlAtt : dlAtts) {
			JSONObject jsonAtt = JsonUtil.getInstance().getAttachmentJsonByName(jsonObj, dlAtt.getName(), dlAtt.getLength());
			if(jsonAtt!=null){
				JSONObject jsonDlAtt = JsonUtil.getInstance().getAttachmentJsonByParent(jsonObj, (String) jsonAtt.get(Constant.ATTACHMENT_ID));
				if(jsonDlAtt!=null){
					jsonObj.remove(jsonDlAtt.get(Constant.ATTACHMENT_ID));
				}
				jsonObj.remove(jsonAtt.get(Constant.ATTACHMENT_ID));
			}
		}
	}
	/**
	 * 移除重新下载的附件的原始缓存并将重新下载的附件存入缓存.
	 * @param dlAtts
	 * @param attJson
	 * @param jsonObj
	 * @param emailCache
	 */
	private void setAttJson(List<Clickoo_message_attachment> dlAtts,String attJson,JSONObject jsonObj,EmailCacheObject emailCache){
		if (attJson == null) {
			jsonObj = new JSONObject();
		}
		for (Clickoo_message_attachment dlAtt : dlAtts) {
			JSONObject jsonAtt = JsonUtil.getInstance().getAttachmentJsonByName(jsonObj, dlAtt.getName(), dlAtt.getLength());
			if(jsonAtt!=null){
				JSONObject jsonDlAtt = JsonUtil.getInstance().getAttachmentJsonByParent(jsonObj, (String) jsonAtt.get(Constant.ATTACHMENT_ID));
				if(jsonDlAtt!=null){
					jsonObj.remove(jsonDlAtt.get(Constant.ATTACHMENT_ID));
				}
				jsonObj.remove(jsonAtt.get(Constant.ATTACHMENT_ID));
			}
			JsonUtil.getInstance().handleAttatchment(jsonObj, dlAtt, 0);
		}
		emailCache.setAttachmentJson(jsonObj.toString());
	}
}
