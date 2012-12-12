package com.microsoft.ews.client;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.xml.bind.JAXBElement;

import org.apache.commons.httpclient.protocol.Protocol;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.cache.MsgCacheManager;
import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.domain.Body;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.domain.EmailSupport;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.receiver.AbstractProvide;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.microsoft.schemas.exchange.services._2006.messages.AttachmentInfoResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.FindItemResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.FindItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.FindItemType;
import com.microsoft.schemas.exchange.services._2006.messages.GetAttachmentResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.GetAttachmentType;
import com.microsoft.schemas.exchange.services._2006.messages.GetItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.GetItemType;
import com.microsoft.schemas.exchange.services._2006.messages.ItemInfoResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.ResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.types.ArrayOfRealItemsType;
import com.microsoft.schemas.exchange.services._2006.types.ArrayOfRecipientsType;
import com.microsoft.schemas.exchange.services._2006.types.AttachmentIdType;
import com.microsoft.schemas.exchange.services._2006.types.AttachmentResponseShapeType;
import com.microsoft.schemas.exchange.services._2006.types.AttachmentType;
import com.microsoft.schemas.exchange.services._2006.types.BodyType;
import com.microsoft.schemas.exchange.services._2006.types.BodyTypeResponseType;
import com.microsoft.schemas.exchange.services._2006.types.DefaultShapeNamesType;
import com.microsoft.schemas.exchange.services._2006.types.DistinguishedFolderIdNameType;
import com.microsoft.schemas.exchange.services._2006.types.DistinguishedFolderIdType;
import com.microsoft.schemas.exchange.services._2006.types.EmailAddressType;
import com.microsoft.schemas.exchange.services._2006.types.FileAttachmentType;
import com.microsoft.schemas.exchange.services._2006.types.FindItemParentType;
import com.microsoft.schemas.exchange.services._2006.types.ItemIdType;
import com.microsoft.schemas.exchange.services._2006.types.ItemQueryTraversalType;
import com.microsoft.schemas.exchange.services._2006.types.ItemResponseShapeType;
import com.microsoft.schemas.exchange.services._2006.types.ItemType;
import com.microsoft.schemas.exchange.services._2006.types.MessageType;
import com.microsoft.schemas.exchange.services._2006.types.NonEmptyArrayOfBaseFolderIdsType;
import com.microsoft.schemas.exchange.services._2006.types.NonEmptyArrayOfBaseItemIdsType;
import com.microsoft.schemas.exchange.services._2006.types.NonEmptyArrayOfRequestAttachmentIdsType;
import com.microsoft.schemas.exchange.services._2006.types.SingleRecipientType;

public class EWSReceiveClient extends AbstractProvide {

	Logger logger = LoggerFactory.getLogger(EWSReceiveClient.class);

	private ExchangeWebServiceClient client = new ExchangeWebServiceClient();

	private ExchangeServicePortType service;

	private boolean isReceiveOldMailOK = false;

	/**
	 * 收邮件.
	 * 
	 * @param context
	 * @return
	 */
	@Override
	public void receiveMail(Context context) {

		if (String.valueOf(NEW_ACCOUNT_STATUS).equals(context.getStatus())
				&& !String.valueOf(TEMP_ACCOUNT_COMMONMESSAGE_STATUS).equals(
						context.getStatus())) {
			AccountService accountService = new AccountService();
			Clickoo_mail_account account = accountService
					.getAccountByAid(context.getAccountId());

			context.setStatus(String.valueOf(TEMP_ACCOUNT_COMMONMESSAGE_STATUS));
			account.setStatus(TEMP_ACCOUNT_COMMONMESSAGE_STATUS);
			accountService.updateAccountState(account,
					context.getRegistrationDate());
			logger.info("[new user:" + user + "] receive old message begin");
			receiveEmail(context, true, context.isSSL());
			if (isReceiveOldMailOK) {
				logger.info("set status 0.");
				context.setStatus(String.valueOf(COMMON_ACCOUNT_STATUS));
				account.setStatus(COMMON_ACCOUNT_STATUS);
				accountService.updateAccountState(account,
						context.getRegistrationDate());
			}
			return;
		} else if (String.valueOf(COMMON_ACCOUNT_STATUS).equals(
				context.getStatus())) {
			receiveEmail(context, false, context.isSSL());
		}
	}

	/**
	 * 收邮件.
	 * 
	 * @param context
	 * @return
	 */
	private void receiveEmail(Context context, boolean receiveOld, boolean isSSL) {
		init(context.getAccountId(), context.getLoginName());
		String username = context.getLoginName();
		String password = context.getPassword();
		String host = context.getReceiveHost();

		if (!connect(host, username, password, isSSL)) {
			return;
		}

		FindItemType findItem = getFindItemType();

		FindItemResponseType res = null;
		try {
			// 获得返回
			res = service.findItem(findItem);
		} catch (Exception e) {
			logger.error("failed to receive Email\n", e);
			return;
		}

		// 解析返回
		List<JAXBElement<? extends ResponseMessageType>> list = res
				.getResponseMessages()
				.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage();

		for (JAXBElement<? extends ResponseMessageType> element : list) {
			if (element.getValue() instanceof FindItemResponseMessageType) {
				FindItemResponseMessageType responseMessage = (FindItemResponseMessageType) element
						.getValue();
				FindItemParentType parentType = responseMessage.getRootFolder();
				ArrayOfRealItemsType items = parentType.getItems();
				List<ItemType> itemList = items
						.getItemOrMessageOrCalendarItem();
				int receiveOldNum = 0;

				ItemIdType itemId = null;
				for (ItemType item : itemList) {
					if (item instanceof MessageType) {
						MessageType message = (MessageType) item;
						String uuid = getUuid(message.getItemId());
						itemId = null;
						if (receiveOld) {
							if (receiveOldNum >= context.getAccount()
									.getOldMailNum()) {
								isReceiveOldMailOK = true;
								return;
							} else {
								itemId = message.getItemId();
								receiveOldNum++;
							}
						} else if (!MsgCacheManager.containsUuid(
								Long.valueOf(context.getAccountId()), uuid)) {
							if (!isTimeout(context)) {
								itemId = message.getItemId();
							} else {
								return;
							}

						}

						if (itemId != null) {
							Email email = getEmailByItemId(itemId,
									context.getRegistrationDate(), receiveOld);
							if (email != null) {
								long mid = new MessageService()
										.saveMessage(new Clickoo_message(email
												.getUuid(), new Date()));
								addUidCache(uuid, mid, context.getAccountId());
								if (context.getEmailList() == null) {
									context.setEmailList(new HashMap<String, Email>());
								}
								context.getEmailList().put(String.valueOf(mid),
										email);
							}
						}

					}
				}
				isReceiveOldMailOK = true;
			}

		}

	}

	/**
	 * 连接服务器.
	 * 
	 * @return
	 */
	private boolean connect(String host, String username, String password,
			boolean isSSL) {
		try {
			// 设置SSL
			if (isSSL) {
				Protocol AlcHttpsProtocol = new Protocol("https",
						new EWSSSLSocketFactory(), 443);
				Protocol.registerProtocol("https", AlcHttpsProtocol);

				// 获取service
				service = client.getExchangeWebPort("https://" + host
						+ "/EWS/Exchange.asmx");

			} else {
				Protocol AlcHttpsProtocol = new Protocol("http",
						new EWSSSLSocketFactory(), 80);
				Protocol.registerProtocol("http", AlcHttpsProtocol);

				// 获取service
				service = client.getExchangeWebPort("http://" + host
						+ "/EWS/Exchange.asmx");

			}

			// 得到不带邮箱后缀的用户名
			String noSuffixName = username;
			if (username.split("@").length == 2) {
				noSuffixName = username.split("@")[0];
			}

			// 设置用户名，密码
			Client xfireClient = Client.getInstance(service);
			xfireClient.setProperty(Channel.USERNAME, "pushmail\\"
					+ noSuffixName);
			xfireClient.setProperty(Channel.PASSWORD, password);
			xfireClient.setProperty("disable.expect-continue", "true");

			return true;
		} catch (Exception e) {
			logger.error("error to connect to ews server\n", e);
			return false;
		}

	}

	/**
	 * 根据ItemId获取邮件.
	 * 
	 * @param service
	 * @param itemId
	 * @param registerDate
	 * @return
	 */
	private Email getEmailByItemId(ItemIdType itemId, Date registerDate,
			boolean receiveOld) {

		Email email = null;

		// 构造GetItemType实例
		GetItemType itemType = new GetItemType();
		NonEmptyArrayOfBaseItemIdsType itemIds = new NonEmptyArrayOfBaseItemIdsType();
		itemIds.getItemIdOrOccurrenceItemIdOrRecurringMasterItemId()
				.add(itemId);
		itemType.setItemIds(itemIds);
		ItemResponseShapeType itemShape = new ItemResponseShapeType();
		// 设置获取所有属性
		itemShape.setBaseShape(DefaultShapeNamesType.ALL_PROPERTIES);
		// 设置获取正文类型
		itemShape.setBodyType(BodyTypeResponseType.TEXT);
		itemType.setItemShape(itemShape);

		GetItemResponseType itemResponse = null;
		try {
			// 获得返回
			itemResponse = service.getItem(itemType);
		} catch (Exception e) {
			logger.error("failed to receive Email\n", e);
			return null;
		}

		List<JAXBElement<? extends ResponseMessageType>> list = itemResponse
				.getResponseMessages()
				.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage();

		// 解析返回
		for (JAXBElement<? extends ResponseMessageType> element : list) {
			if (element.getValue() instanceof ItemInfoResponseMessageType) {
				ItemInfoResponseMessageType itemInfo = (ItemInfoResponseMessageType) element
						.getValue();
				for (ItemType item : itemInfo.getItems()
						.getItemOrMessageOrCalendarItem()) {
					if (item instanceof MessageType) {
						MessageType message = (MessageType) item;
						Date receiveDate = message.getDateTimeReceived();
						// 判断是否收取该邮件
						if (receiveOld
								|| registerDate == null
								|| (registerDate.before(receiveDate) && isOneMonthMessage(receiveDate))) {
							email = getEmail(message);
							logger.info("it is a old message or someone are receiving single message by uuid");
						}
					} else {
						logger.error("this item is not instanceof MessageType");
					}
				}
			}
		}
		return email;
	}

	/**
	 * 解析单封邮件.
	 * 
	 * @param message
	 * @return
	 */
	private Email getEmail(MessageType message) {
		Email email = new Email();
		email.setSubject(message.getSubject());
		email.setBody(getEmailBody(message.getBody()));
		email.setMessageId(message.getInternetMessageId());
		email.setTo(getMailRecipients(message.getToRecipients()));
		email.setCc(getMailRecipients(message.getCcRecipients()));
		email.setBcc(getMailRecipients(message.getBccRecipients()));
		email.setFrom(getMailFrom(message.getFrom()));
		email.setReceiveTime(message.getDateTimeReceived());
		email.setSendTime(message.getDateTimeSent());
		email.setUuid(getUuid(message.getItemId()));

		if (message.isHasAttachments()) {
			List<AttachmentIdType> attIdList = new ArrayList<AttachmentIdType>();
			for (AttachmentType att : message.getAttachments()
					.getItemAttachmentOrFileAttachment()) {
				attIdList.add(att.getAttachmentId());
			}
			email.setAttachList(getAttachmentList(attIdList));
		}
		return email;
	}

	/**
	 * 根据附件的id list获取附件.
	 * 
	 * @param service
	 * @param attIdType
	 * @return
	 */
	private List<Clickoo_message_attachment> getAttachmentList(
			List<AttachmentIdType> attIdTypeList) {

		List<Clickoo_message_attachment> attachmentList = new ArrayList<Clickoo_message_attachment>();

		// 构造GetAttachmentType实例
		GetAttachmentType itemType = new GetAttachmentType();
		NonEmptyArrayOfRequestAttachmentIdsType itemIds = new NonEmptyArrayOfRequestAttachmentIdsType();
		itemIds.getAttachmentId().addAll(attIdTypeList);
		itemType.setAttachmentIds(itemIds);
		AttachmentResponseShapeType itemShape = new AttachmentResponseShapeType();
		// 附件内容选择最佳类型
		itemShape.setBodyType(BodyTypeResponseType.BEST);
		itemType.setAttachmentShape(itemShape);

		GetAttachmentResponseType itemResponse = null;
		try {
			// 获得返回
			itemResponse = service.getAttachment(itemType);
		} catch (Exception e) {
			logger.error("failed to receive Email\n", e);
			return null;
		}

		// 解析
		List<JAXBElement<? extends ResponseMessageType>> list = itemResponse
				.getResponseMessages()
				.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage();
		for (JAXBElement<? extends ResponseMessageType> element : list) {
			if (element.getValue() instanceof AttachmentInfoResponseMessageType) {
				AttachmentInfoResponseMessageType attInfo = (AttachmentInfoResponseMessageType) element
						.getValue();
				for (AttachmentType att : attInfo.getAttachments()
						.getItemAttachmentOrFileAttachment()) {
					attachmentList.add(getAttachment(att));
				}
			} else {
				logger.error("failed to get attachments......\n");
			}
		}

		return attachmentList;
	}

	/**
	 * 根据uuid获取单个邮件.
	 * 
	 * @param account
	 * @param uuid
	 * @return
	 */
	public Email getEmailByUuid(WebAccount account, String uuid,
			List<Clickoo_message_attachment> exitAttList, EmailSupport eSupport) {
		ItemIdType idType = getItemIdType(uuid);
		if (idType == null) {
			logger.error("failed to get Email item id by uuid");
			return null;
		}
		if (!connect(account.getReceiveHost(), account.getName(),
				account.getPassword(), account.isSsl())) {
			return null;
		}
		Email email = getEmailByItemId(idType, null, false);
		if (email != null) {

			// 设置附件个数
			if (email.getAttachList() != null && eSupport != null) {
				eSupport.setAttNum(email.getAttachList().size());
			}

			if (exitAttList != null && exitAttList.size() > 0
					&& email.getAttachList() != null
					&& email.getAttachList().size() > 0) {
				List<Clickoo_message_attachment> allList = email
						.getAttachList();
				List<Clickoo_message_attachment> returnList = new ArrayList<Clickoo_message_attachment>();

				for (Clickoo_message_attachment att : allList) {
					if (!inList(exitAttList, att)) {
						returnList.add(att);
					}
				}
				email.setAttachList(returnList);
			}

			return email;
		} else {
			logger.error("failed to get Email by uuid");
			return null;
		}
	}

	/**
	 * 根据uuid获取单个邮件.
	 * 
	 * @param account
	 * @param uuid
	 * @return
	 */
	public List<Clickoo_message_attachment> getMissingAttListByUuid(
			WebAccount account, String uuid,
			List<Clickoo_message_attachment> exitAttList, EmailSupport eSupport) {
		ItemIdType idType = getItemIdType(uuid);
		if (idType == null) {
			logger.error("failed to get Email item id by uuid");
			return null;
		}
		if (!connect(account.getReceiveHost(), account.getName(),
				account.getPassword(), account.isSsl())) {
			return null;
		}
		Email email = getEmailByItemId(idType, null, false);
		if (email != null) {

			// 设置附件个数
			if (email.getAttachList() != null && eSupport != null) {
				eSupport.setAttNum(email.getAttachList().size());
			}

			if (email.getAttachList() != null
					&& email.getAttachList().size() > 0 && exitAttList != null
					&& exitAttList.size() > 0) {
				List<Clickoo_message_attachment> allList = email
						.getAttachList();
				List<Clickoo_message_attachment> returnList = new ArrayList<Clickoo_message_attachment>();

				for (Clickoo_message_attachment att : allList) {
					if (!inList(exitAttList, att)) {
						returnList.add(att);
					}
				}
				return returnList;
			} else {
				return email.getAttachList();
			}

		} else {
			logger.error("failed to get Email by uuid");
			return null;
		}
	}

	/**
	 * 判断附件att是否在附件列表list中.
	 * 
	 * @param list
	 * @param att
	 * @return
	 */
	private boolean inList(List<Clickoo_message_attachment> list,
			Clickoo_message_attachment att) {
		for (Clickoo_message_attachment tmp : list) {
			if (tmp.getName().equals(att.getName())
					&& tmp.getLength() == att.getLength()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证账号通过EWS方式收邮件.
	 * 
	 * @param account
	 * @return
	 */
	public boolean validate(WebAccount account) {

		// 处理host
		account.setReceiveHost(handleHost(account.getReceiveHost()));

		if (!connect(account.getReceiveHost(), account.getName(),
				account.getPassword(), account.isSsl())) {
			return false;
		}
		FindItemType findItem = getFindItemType();
		try {
			// 获得返回
			service.findItem(findItem);
			logger.info("success to validate account receive message via ews");
			return true;
		} catch (Exception e) {
			logger.error(
					"failed to validate account receive message via ews\n", e);
			return false;
		}
	}

	/**
	 * 处理host.
	 * 
	 * @param host
	 * @return
	 */
	private String handleHost(String host) {
		String return_host = host;
		if (host.startsWith("http://")) {
			return_host = host.substring(7);
		} else if (host.startsWith("https://")) {
			return_host = host.substring(8);
		}

		int index = return_host.indexOf('/');
		if (index != -1) {
			return_host = return_host.substring(0, index);
		}

		return return_host;
	}

	/**
	 * 得到FindItemType实例.
	 * 
	 * @return
	 */
	private FindItemType getFindItemType() {
		// 构造FindItemType实例
		FindItemType findItem = new FindItemType();
		NonEmptyArrayOfBaseFolderIdsType folders = new NonEmptyArrayOfBaseFolderIdsType();
		DistinguishedFolderIdType id = new DistinguishedFolderIdType();
		// 选择收件箱
		id.setId(DistinguishedFolderIdNameType.INBOX);
		folders.getFolderIdOrDistinguishedFolderId().add(id);
		findItem.setParentFolderIds(folders);
		// 设置为不改变邮件标记
		findItem.setTraversal(ItemQueryTraversalType.SHALLOW);
		ItemResponseShapeType shape = new ItemResponseShapeType();
		// 设置只选择邮件ID
		shape.setBaseShape(DefaultShapeNamesType.ID_ONLY);
		findItem.setItemShape(shape);

		return findItem;
	}

	/**
	 * 获取邮件正文.
	 * 
	 * @param body
	 * @return
	 */
	private byte[] getEmailBody(BodyType body) {
		byte[] bytes = null;
		if (body != null && body.getValue() != null) {
			try {
				bytes = body.getValue().getBytes("utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("unsupport utf-8 encode\n", e);
			}
		} else {
			logger.warn("failed to get body ,body is null......");
		}
		return bytes;
	}

	/**
	 * 转换email收件人地址,处理别名和邮箱名.
	 * 
	 * @param addressList
	 * @return
	 */
	private String getMailRecipients(ArrayOfRecipientsType recipients) {
		if (recipients != null && recipients.getMailbox() != null) {
			StringBuffer sb = new StringBuffer();
			for (EmailAddressType address : recipients.getMailbox()) {
				String name = address.getName();
				String addr = address.getEmailAddress();
				if (name != null) {
					sb.append(name + "<" + addr + ">,");
				} else {
					sb.append("<" + addr + ">,");
				}
			}
			return sb.substring(0, sb.length() - 1);
		} else {
			return null;
		}
	}

	/**
	 * 转换收件人地址.
	 * 
	 * @param from
	 * @return
	 */
	private String getMailFrom(SingleRecipientType from) {
		String str = null;
		if (from != null && from.getMailbox() != null) {
			EmailAddressType address = from.getMailbox();
			String name = address.getName();
			String addr = address.getEmailAddress();
			if (name != null) {
				str = (name + "<" + addr + ">");
			} else {
				str = ("<" + addr + ">");
			}
		}
		return str;
	}

	/**
	 * 拼装得到uuid.
	 * 
	 * @param idType
	 * @return
	 */
	private String getUuid(ItemIdType idType) {
		return idType.getChangeKey() + "," + idType.getId();
	}

	/**
	 * 根据uuid转换得到ItemIdType.
	 * 
	 * @param uuid
	 * @return
	 */
	private ItemIdType getItemIdType(String uuid) {
		String[] strs = uuid.split(",");
		if (strs.length == 2) {
			ItemIdType idType = new ItemIdType();
			idType.setChangeKey(strs[0]);
			idType.setId(strs[1]);
			return idType;
		} else {
			logger.error("uuid is error......");
			return null;
		}
	}

	/**
	 * 获得单个附件.
	 * 
	 * @param att
	 * @return
	 */
	private Clickoo_message_attachment getAttachment(AttachmentType att) {
		Clickoo_message_attachment at = new Clickoo_message_attachment();
		String name = att.getName();
		if (name.lastIndexOf('.') != -1) {
			at.setName(name.substring(0, name.lastIndexOf('.')));
			at.setType(name.substring(name.lastIndexOf('.') + 1, name.length()));
		} else {
			at.setName(name);
			at.setType("");
		}
		if (att instanceof FileAttachmentType) {
			FileAttachmentType file = (FileAttachmentType) att;
			byte[] bytes = file.getContent();
			at.setIn(bytes);
			if (bytes != null) {
				at.setLength(bytes.length);
			} else {
				at.setLength(0);
			}
		} else {
			logger.warn("this attachment is not instanceof FileAttachmentType");
			return null;
		}
		return at;
	}

	@Override
	public void receiveLargeMail(Context context) throws MessagingException {
		logger.error("not support receive larger mail for http");
	}

	/**
	 * 获取单封邮件内容.
	 * 
	 * @param account
	 * @param uuid
	 * @return
	 */
	public Body getEmailBodyByUuid(WebAccount account, String uuid) {
		ItemIdType idType = getItemIdType(uuid);
		if (idType == null) {
			logger.error("failed to get Email item id by uuid");
			return null;
		}
		if (!connect(account.getReceiveHost(), account.getName(),
				account.getPassword(), account.isSsl())) {
			return null;
		}
		Email email = getEmailByItemId(idType, null, false);
		if (email != null && email.getBody() != null) {
			Body body = new Body();
			try {
				body.setData(new String(email.getBody(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			body.setSize(email.getBody().length);
			body.setType("text");
			return body;
		} else {
			logger.error("failed to get Email by uuid");
			return null;
		}
	}

	/**
	 * 根据email uuid获取单封附件.
	 * 
	 * @param account
	 * @param uuid
	 * @param attName
	 * @param size
	 * @return
	 */
	public Clickoo_message_attachment getAttachmentByUuid(WebAccount account,
			String uuid, String attName, int size, EmailSupport eSupport) {
		ItemIdType idType = getItemIdType(uuid);
		if (idType == null) {
			logger.error("failed to get Email item id by uuid");
			return null;
		}
		if (!connect(account.getReceiveHost(), account.getName(),
				account.getPassword(), account.isSsl())) {
			return null;
		}
		Email email = getEmailByItemId(idType, null, false);
		if (email != null && email.getAttachList() != null) {
			for (Clickoo_message_attachment att : email.getAttachList()) {
				if (att.getName().equals(attName) && att.getLength() == size) {
					return att;
				}
			}

			// 设置附件个数
			if (eSupport != null) {
				eSupport.setAttNum(email.getAttachList().size());
			}
			return null;
		} else {
			eSupport.setAttNum(0);
			return null;
		}
	}

}
