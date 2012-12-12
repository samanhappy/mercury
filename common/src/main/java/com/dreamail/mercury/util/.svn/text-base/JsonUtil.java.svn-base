package com.dreamail.mercury.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.domain.EmailCacheObject;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.pojo.Function;
import com.dreamail.mercury.pojo.InCert;
import com.dreamail.mercury.pojo.InPath;
import com.dreamail.mercury.pojo.OutCert;
import com.dreamail.mercury.pojo.OutPath;

public class JsonUtil {

	public static final JsonUtil util = new JsonUtil();

	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private JsonUtil() {
	}

	public static JsonUtil getInstance() {
		if (util != null) {
			return util;
		}
		return new JsonUtil();
	}

	/**
	 * 根据JSON获取context信息
	 * 
	 * @param jsonString
	 *            JSON
	 * @param keyString
	 *            JSON Key
	 */
	public String getJSONValueByKey(JSONObject obj, String key) {
		Object value = null;
		if (obj != null) {
			value = obj.get(key);
			if (value != null) {
				return (String) value;
			}
		}
		return null;
	}

	/**
	 * 解析sourceStr
	 * @param sourceStr
	 * @param targetStringKey
	 * @return String
	 */
	public String getJsonValueByKey(String sourceStr,String targetStringKey) {
		JSONObject obj = JSONObject.fromObject(sourceStr);
		return obj.get(targetStringKey).toString();
	}
	
	
	/**
	 * 解析inpath
	 * 
	 * @param json
	 * @return
	 */
	public InPath parserInPath(String json) {
		InPath inpath = new InPath();
		JSONObject objInPath = JSONObject.fromObject(json);
		inpath.setInhost(getJSONValueByKey(objInPath, Constant.HOST));
		inpath.setProtocolType(getJSONValueByKey(objInPath,
				Constant.PROTOCOLTYPE));
		inpath
				.setReceivePort(getJSONValueByKey(objInPath,
						Constant.RECEIVEPORT));
		inpath.setReceiveTs(getJSONValueByKey(objInPath, Constant.TYPE));
		inpath.setCompositor(getJSONValueByKey(objInPath, Constant.COMPOSITOR));
		inpath.setSupportalluid(getJSONValueByKey(objInPath,
				Constant.SUPPORTALLUID));
		inpath.setSsl(getJSONValueByKey(objInPath, Constant.SSL));
		return inpath;
	}

	/**
	 * 解析OutPath
	 * 
	 * @param json
	 * @return
	 */
	public OutPath parserOutPath(String json) {
		OutPath outPath = new OutPath();
		JSONObject objOutPath = JSONObject.fromObject(json);

		if (getJSONValueByKey(objOutPath, Constant.SMTPSERVER) != null) {
			outPath.setSmtpServer(getJSONValueByKey(objOutPath,
					Constant.SMTPSERVER));
		} else if (getJSONValueByKey(objOutPath, Constant.HOST) != null) {
			outPath.setSmtpServer(getJSONValueByKey(objOutPath, Constant.HOST));
		} else {
			logger.error("not find send server or host...");
		}

		if (getJSONValueByKey(objOutPath, Constant.SENDPORT) != null) {
			outPath
					.setSendPort(getJSONValueByKey(objOutPath,
							Constant.SENDPORT));
		} else if (getJSONValueByKey(objOutPath, Constant.HOST) != null) {
			if (Constant.USE_SSL.equals(getJSONValueByKey(objOutPath,
					Constant.SSL))) {
				outPath.setSendPort("443");
			} else {
				outPath.setSendPort("80");
			}
		} else {
			logger.error("not find send port...");
		}

		if (getJSONValueByKey(objOutPath, Constant.TYPE) != null) {
			outPath.setType(getJSONValueByKey(objOutPath, Constant.TYPE));
		} else {
			outPath.setType("");
		}
		return outPath;
	}

	/**
	 * 解析InCert
	 * 
	 * @param json
	 * @return
	 */
	public InCert parserInCert(String json) {
		InCert inCert = new InCert();
		JSONObject objInCert = JSONObject.fromObject(json);
		inCert.setLoginID(getJSONValueByKey(objInCert, Constant.LOGINID));
		inCert.setPwd(getJSONValueByKey(objInCert, Constant.PWD));
		inCert.setToken(getJSONValueByKey(objInCert, Constant.TOKEN));
		return inCert;
	}

	public OutCert parserOutCert(String json) {
		if (json == null) {
			return null;
		}
		OutCert out = new OutCert();
		JSONObject objOutCert = JSONObject.fromObject(json);
		out.setAlias(getJSONValueByKey(objOutCert, Constant.ALIAS));
		out.setLoginID(getJSONValueByKey(objOutCert, Constant.LOGINID));
		out.setPwd(getJSONValueByKey(objOutCert, Constant.PWD));
		out.setToken(getJSONValueByKey(objOutCert, Constant.TOKEN));
		return out;
	}

	public Function parserFunction(String json) {
		Function function = new Function();
		JSONObject objfunction = JSONObject.fromObject(json);
		function.setAccountNumber(getJSONValueByKey(objfunction,
				CAGConstants.COS_ACOUNT_NUMBER));
		function.setEncryptionOption(getJSONValueByKey(objfunction,
				CAGConstants.COS_ENCRYPTION_OPTION));
		function.setLatestEmailNumber(getJSONValueByKey(objfunction,
				CAGConstants.COS_LATESTEMAILNUMBER));
		function.setRetrievalEmailInterval(getJSONValueByKey(objfunction,
				CAGConstants.COS_RETRIEVAL_EMAIL_INTERVAL));
		function.setStorageOption(getJSONValueByKey(objfunction,
				CAGConstants.COS_STORAGE_OPTION));
		function.setSynchronizeOption(getJSONValueByKey(objfunction,
				CAGConstants.COS_SYNCHRONIZE_OPTION));
		function.setAllowAttachmentNumber(getJSONValueByKey(objfunction,
				CAGConstants.COS_ATTCHMENT_NUMBER));
		function.setAutoCleanupPeriod(getJSONValueByKey(objfunction,
				CAGConstants.COS_AUTOCLEANUP_PERIOD));
		function.setSaveOriginalAttachmentOption(getJSONValueByKey(objfunction,
				CAGConstants.COS_SAVEATTAHMENT_OPTION));
		function.setScheduledPushOption(getJSONValueByKey(objfunction,
				CAGConstants.COS_SCHEDULEPUSH_OPTION));
		function.setUseMailAccountOption(getJSONValueByKey(objfunction,
				CAGConstants.COS_MAILACCOUNT_OPTION));

		return function;
	}

	/**
	 * 把Email封装成EmailCacheObjcet.
	 * 
	 * @param aid
	 * @param email
	 * @return
	 */
	public EmailCacheObject parseEmail(String aid, Email email) {
		EmailCacheObject emailCache = new EmailCacheObject();
		emailCache.setAid(aid);
		emailCache.setEmail_from(email.getFrom());
		emailCache.setEmail_to(email.getTo());
		emailCache.setCc(email.getCc());
		emailCache.setBcc(email.getBcc());
		emailCache.setSubject(email.getSubject());
		emailCache.setMail_date(email.getSendTime());
		emailCache.setUuid(email.getUuid());
		if (email.getBody() != null) {
			emailCache.setData(ZipUtil.compress(email.getBody()));
			emailCache.setData_size(email.getBody().length);
		} else {
			emailCache.setData(ZipUtil.compress("".getBytes()));
			emailCache.setData_size(0);
		}
		List<Clickoo_message_attachment> attList = email.getAttachList();
		JSONObject jsonParent = new JSONObject();
		if (attList != null) {
			emailCache.setAttachNums(attList.size());
			for (Clickoo_message_attachment att : attList) {
				handleAttatchment(jsonParent, att, 0);
			}
		}
		emailCache.setAttachmentJson(jsonParent.toString());
		return emailCache;
	}

	/**
	 * 把Email封装成EmailCacheObjcet.
	 * 
	 * @param aid
	 * @param email
	 * @return
	 */
	public EmailCacheObject parseDatabaseEmail(String aid, Email email) {
		EmailCacheObject emailCache = new EmailCacheObject();
		emailCache.setAid(aid);
		emailCache.setEmail_from(email.getFrom());
		emailCache.setEmail_to(email.getTo());
		emailCache.setCc(email.getCc());
		emailCache.setBcc(email.getBcc());
		emailCache.setSubject(email.getSubject());
		emailCache.setMail_date(email.getSendTime());
		emailCache.setUuid(email.getUuid());
		if (email.getBody() != null) {
			emailCache.setData(email.getBody());
			emailCache.setData_size(email.getBody().length);
		} else {
			emailCache.setData("".getBytes());
			emailCache.setData_size(0);
		}
		List<Clickoo_message_attachment> attList = email.getAttachList();
		JSONObject jsonParent = new JSONObject();
		if (attList != null) {
			emailCache.setAttachNums(attList.size());
			for (Clickoo_message_attachment att : attList) {
				packageAttatchment(jsonParent, att);
			}
		}
		emailCache.setAttachmentJson(jsonParent.toString());
		return emailCache;
	}

	/**
	 * 把Clickoo_message封装成Email.
	 * 
	 * @param aid
	 * @param email
	 * @return
	 */
	public Email parseEmail(Clickoo_message clickoo_message) {
		Email email = new Email();
		email.setUuid(clickoo_message.getUuid());
		email.setMessageId(String.valueOf(clickoo_message.getId()));
		email.setSubject(clickoo_message.getSubject());
		email.setFrom(clickoo_message.getEmailFrom());
		email.setTo(clickoo_message.getEmailTo());
		email.setCc(clickoo_message.getCc());
		email.setBcc(clickoo_message.getBcc());
		// 是否需要回执 不需要回执为0、需要为1
//		email.setReply(clickoo_message.getReply());
		email.setSendTime(clickoo_message.getSendtime());
		email.setReceiveTime(clickoo_message.getIntime());
		if (null != clickoo_message.getMessageData()) {
			email.setBody(ZipUtil.compress(clickoo_message.getMessageData()
					.getData(true).getBytes()));
		}else{
			email.setBody(ZipUtil.compress("".getBytes()));
		}
		email.setAttachList(clickoo_message.getAttachList());
		return email;
	}

	/**
	 * 封装附件list.
	 * 
	 * @param jsonParent
	 * @param att
	 * @param parent
	 * @return
	 */
	public JSONObject handleAttatchment(JSONObject jsonParent,
			Clickoo_message_attachment att, long parent) {
		String attId = null;
		attId = String.valueOf(att.getId());
		JSONObject jsonChild = new JSONObject();
		jsonChild.put(Constant.ATTACHMENT_ID, attId);
		jsonChild.put(Constant.ATTACHMENT_PARENT, parent);
		jsonChild.put(Constant.ATTACHMENT_NAME, att.getName() == null ? ""
				: att.getName());
		jsonChild.put(Constant.ATTACHMENT_TYPE, att.getType() == null ? ""
				: att.getType());
		jsonChild.put(Constant.ATTACHMENT_LENGTH, att.getLength());
		jsonChild.put(Constant.ATTACHMENT_PATH, att.getPath() == null ? ""
				: att.getPath());
		jsonChild.put(Constant.ATTACHMENT_VOLUME_ID, att.getVolume_id());
		jsonParent.put(attId, jsonChild);
		List<Clickoo_message_attachment> attList = att.getChildList();
		if (attList != null) {
			for (Clickoo_message_attachment at : attList) {
				try {
					handleAttatchment(jsonParent, at, Long.parseLong(attId));
				} catch (Exception e) {
					logger.warn("attmentId error！！！", e);
				}
			}
			SessionFactory.closeSession();
		}
		return jsonParent;
	}

	/**
	 * 封装单封附件.
	 * 
	 * @param jsonParent
	 * @param att
	 * @param parent
	 * @return
	 */
	public JSONObject handleAttatchment(Clickoo_message_attachment att) {
		String attId = null;
		attId = String.valueOf(att.getId());
		JSONObject jsonChild = new JSONObject();
		jsonChild.put(Constant.ATTACHMENT_ID, attId);
		jsonChild.put(Constant.ATTACHMENT_PARENT, 0);
		jsonChild.put(Constant.ATTACHMENT_NAME, att.getName() == null ? ""
				: att.getName());
		jsonChild.put(Constant.ATTACHMENT_TYPE, att.getType() == null ? ""
				: att.getType());
		jsonChild.put(Constant.ATTACHMENT_LENGTH, att.getLength());
		jsonChild.put(Constant.ATTACHMENT_PATH, att.getPath() == null ? ""
				: att.getPath());
		jsonChild.put(Constant.ATTACHMENT_VOLUME_ID, att.getVolume_id());
		return jsonChild;
	}

	public static String mapToString(Map<String, String> map) {
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> stringToMap(String mapStr) {
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
		JSONObject json = JSONObject.fromObject(mapStr);
		Iterator<String> keys = json.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			map.put(key, json.get(key).toString());
		}
		return map;
	}

	/**
	 * 封装单封附件.
	 * 
	 * @param jsonParent
	 * @param att
	 * @param parent
	 * @return
	 */
	public JSONObject packageAttatchment(JSONObject jsonParent,
			Clickoo_message_attachment att) {
		String attId = null;
		attId = String.valueOf(att.getId());
		JSONObject jsonChild = new JSONObject();
		jsonChild.put(Constant.ATTACHMENT_ID, attId);
		jsonChild.put(Constant.ATTACHMENT_PARENT, att.getParent());
		jsonChild.put(Constant.ATTACHMENT_NAME, att.getName() == null ? ""
				: att.getName());
		jsonChild.put(Constant.ATTACHMENT_TYPE, att.getType() == null ? ""
				: att.getType());
		jsonChild.put(Constant.ATTACHMENT_LENGTH, att.getLength());
		jsonChild.put(Constant.ATTACHMENT_PATH, att.getPath() == null ? ""
				: att.getPath());
		jsonChild.put(Constant.ATTACHMENT_VOLUME_ID, att.getVolume_id());
		jsonParent.put(attId, jsonChild);
		return jsonParent;
	}

	/**
	 * 根据名称大小获取附件JSON.
	 * 
	 * @param attachmentParent
	 * @param name
	 * @param size
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getAttachmentJsonByName(JSONObject attachmentParent,
			String name, long size) {
		JSONObject jsonObj = null;
		Set<String> set = attachmentParent.keySet();
		if (set != null) {
			for (String attId : set) {
				JSONObject attachmentChild = (JSONObject) attachmentParent
						.get(String.valueOf(attId));
				if (name != null
						&& name.equals(attachmentChild
								.getString(Constant.ATTACHMENT_NAME))
						&& size == Long.parseLong(attachmentChild
								.getString(Constant.ATTACHMENT_LENGTH))) {
					jsonObj = attachmentChild;
					break;
				}
			}
		}
		return jsonObj;
	}

	/**
	 * 根据parentid获取附件JSON.
	 * 
	 * @param attachmentParent
	 * @param parent
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getAttachmentJsonByParent(JSONObject attachmentParent,
			String parent) {
		JSONObject jsonObj = null;
		Set<String> set = attachmentParent.keySet();
		if (set != null) {
			for (String attId : set) {
				JSONObject attachmentChild = (JSONObject) attachmentParent
						.get(String.valueOf(attId));
				if (parent != null
						&& parent.equals(attachmentChild
								.get(Constant.ATTACHMENT_PARENT))) {
					jsonObj = attachmentChild;
					break;
				}
			}
		}
		return jsonObj;
	}
}
