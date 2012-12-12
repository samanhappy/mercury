package com.microsoft.ews.client;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.httpclient.protocol.Protocol;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailattachment;
import com.microsoft.schemas.exchange.services._2006.messages.ArrayOfResponseMessagesType;
import com.microsoft.schemas.exchange.services._2006.messages.AttachmentInfoResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateAttachmentResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateAttachmentType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateItemType;
import com.microsoft.schemas.exchange.services._2006.messages.ItemInfoResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.ResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.SendItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.SendItemType;
import com.microsoft.schemas.exchange.services._2006.types.ArrayOfRecipientsType;
import com.microsoft.schemas.exchange.services._2006.types.BodyType;
import com.microsoft.schemas.exchange.services._2006.types.BodyTypeType;
import com.microsoft.schemas.exchange.services._2006.types.DistinguishedFolderIdNameType;
import com.microsoft.schemas.exchange.services._2006.types.DistinguishedFolderIdType;
import com.microsoft.schemas.exchange.services._2006.types.EmailAddressType;
import com.microsoft.schemas.exchange.services._2006.types.FileAttachmentType;
import com.microsoft.schemas.exchange.services._2006.types.ItemIdType;
import com.microsoft.schemas.exchange.services._2006.types.MessageDispositionType;
import com.microsoft.schemas.exchange.services._2006.types.MessageType;
import com.microsoft.schemas.exchange.services._2006.types.NonEmptyArrayOfAllItemsType;
import com.microsoft.schemas.exchange.services._2006.types.NonEmptyArrayOfAttachmentsType;
import com.microsoft.schemas.exchange.services._2006.types.NonEmptyArrayOfBaseItemIdsType;
import com.microsoft.schemas.exchange.services._2006.types.ResponseClassType;
import com.microsoft.schemas.exchange.services._2006.types.TargetFolderIdType;

public class EWSSendClient {
	public static final Logger logger = LoggerFactory
			.getLogger(EWSSendClient.class);

	private ExchangeWebServiceClient client = new ExchangeWebServiceClient();

	private ExchangeServicePortType service;

	/**
	 * 连接服务器.
	 * 
	 * @return
	 */
	private void connect(String host, String username, String password,
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
			Client client1 = Client.getInstance(service);
			client1.setProperty(Channel.USERNAME, "pushmail\\" + noSuffixName);
			client1.setProperty(Channel.PASSWORD, password);
			client1.setProperty("disable.expect-continue", "true");

		} catch (Exception e) {
			logger.error("error to connect to ews server\n", e);
		}

	}

	/**
	 * 解析邮件的发送地址
	 * 
	 * @param address
	 * @return
	 */
	public ArrayOfRecipientsType createRecipients(String address) {
		ArrayOfRecipientsType recipients = new ArrayOfRecipientsType();
		String to[] = address.split(";");
		for (int i = 0; i < to.length; i++) {
			EmailAddressType addressType = new EmailAddressType();
			addressType.setEmailAddress(to[i]);
			recipients.getMailbox().add(addressType);
		}
		return recipients;
	}

	/**
	 * 邮件正文：构造MessageType实例
	 * 
	 * @param webEmail
	 * @return
	 */
	public MessageType messageType(WebEmail webEmail) {
		MessageType msgType = new MessageType();

		BodyType body = new BodyType();
		body.setBodyType(BodyTypeType.TEXT);
		modifyline(webEmail);
		body.setValue(webEmail.getBody().getData());

		msgType.setBody(body);
		msgType.setSubject(webEmail.getHead().getSubject());

		if (webEmail.getHead().getTo() == null
				&& webEmail.getHead().getCc() == null
				&& webEmail.getHead().getBcc() == null) {
			logger.error("No receiver.");
		} else {
			if (webEmail.getHead().getTo() != null
					&& webEmail.getHead().getTo().trim().length() != 0) {
				msgType.setToRecipients(createRecipients(webEmail.getHead()
						.getTo()));
			}
			if (webEmail.getHead().getCc() != null
					&& webEmail.getHead().getCc().length() != 0) {
				msgType.setCcRecipients(createRecipients(webEmail.getHead()
						.getCc()));
			}
			if (webEmail.getHead().getBcc() != null
					&& webEmail.getHead().getBcc().length() != 0) {
				msgType.setBccRecipients(createRecipients(webEmail.getHead()
						.getBcc()));
			}
		}
		return msgType;
	}

	/**
	 * 邮件正文：构造CreateItemType实例
	 * 
	 * @param webEmail
	 * @return
	 */
	public CreateItemType createItemType(WebEmail webEmail) {
		CreateItemType request = new CreateItemType();
		request.setMessageDisposition(MessageDispositionType.SAVE_ONLY);

		NonEmptyArrayOfAllItemsType items = new NonEmptyArrayOfAllItemsType();
		items.getItemOrMessageOrCalendarItem().add(messageType(webEmail));

		request.setItems(items);
		return request;
	}

	/**
	 * 保存邮件正文部分
	 * 
	 * @param webEmail
	 * @return
	 */
	public ItemIdType createMessage(WebEmail webEmail) {

		ItemIdType msgItem = new ItemIdType();

		try {
			CreateItemResponseType msgResponse = service
					.createItem(createItemType(webEmail));
			ArrayOfResponseMessagesType array = msgResponse
					.getResponseMessages();

			List<JAXBElement<? extends ResponseMessageType>> list = array
					.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage();
			for (JAXBElement<? extends ResponseMessageType> element : list) {
				ItemInfoResponseMessageType item = (ItemInfoResponseMessageType) element
						.getValue();
				msgItem.setId(item.getItems().getItemOrMessageOrCalendarItem()
						.get(0).getItemId().getId());
				msgItem.setChangeKey(item.getItems()
						.getItemOrMessageOrCalendarItem().get(0).getItemId()
						.getChangeKey());
			}
		} catch (Exception e) {
			msgItem = null;
		}
		return msgItem;
	}

	/**
	 * 邮件附件：构造CreateAttachmentType实例
	 * 
	 * @param att
	 * @param msgItem
	 * @return
	 */
	public CreateAttachmentType createAttachmentType(WebEmailattachment att,
			ItemIdType msgItem) {
		CreateAttachmentType attRequest = new CreateAttachmentType();
		FileAttachmentType attType = new FileAttachmentType();
		attType.setContent(att.getBody().getBytes());
		if (att.getType() != null && !"".equals(att.getType().trim())) {
			attType.setName(att.getName() + "." + att.getType());
		} else {
			attType.setName(att.getName());
		}

		NonEmptyArrayOfAttachmentsType arrayAtt = new NonEmptyArrayOfAttachmentsType();
		arrayAtt.getItemAttachmentOrFileAttachment().add(attType);

		attRequest.setAttachments(arrayAtt);
		attRequest.setParentItemId(msgItem);
		return attRequest;
	}

	/**
	 * 保存邮件附件部分
	 * 
	 * @param att
	 * @param msgItem
	 * @return
	 */
	public ItemIdType createAttachment(WebEmailattachment att,
			ItemIdType msgItem) {
		ItemIdType attachmentItem = new ItemIdType();

		try {
			CreateAttachmentResponseType attResponse = service
					.createAttachment(createAttachmentType(att, msgItem));
			ArrayOfResponseMessagesType attArray = attResponse
					.getResponseMessages();
			List<JAXBElement<? extends ResponseMessageType>> list = attArray
					.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage();
			for (JAXBElement<? extends ResponseMessageType> element : list) {
				AttachmentInfoResponseMessageType item = (AttachmentInfoResponseMessageType) element
						.getValue();
				attachmentItem.setId(item.getAttachments()
						.getItemAttachmentOrFileAttachment().get(0)
						.getAttachmentId().getRootItemId());
				attachmentItem.setChangeKey(item.getAttachments()
						.getItemAttachmentOrFileAttachment().get(0)
						.getAttachmentId().getRootItemChangeKey());
			}
		} catch (Exception e) {
			attachmentItem = null;
		}

		return attachmentItem;
	}

	/**
	 * 邮件：构造SendItemType实例
	 * 
	 * @param sourceItem
	 * @return
	 */
	public SendItemType sendItemType(ItemIdType sourceItem) {
		SendItemType sendRequest = new SendItemType();

		DistinguishedFolderIdType sendItemFolder = new DistinguishedFolderIdType();
		sendItemFolder.setId(DistinguishedFolderIdNameType.SENTITEMS);

		sendRequest.setItemIds(new NonEmptyArrayOfBaseItemIdsType());
		sendRequest.setSavedItemFolderId(new TargetFolderIdType());
		sendRequest.getSavedItemFolderId().setDistinguishedFolderId(
				sendItemFolder);
		sendRequest.setSaveItemToFolder(true);
		sendRequest.getItemIds()
				.getItemIdOrOccurrenceItemIdOrRecurringMasterItemId().add(
						sourceItem);
		return sendRequest;
	}

	/**
	 * 保存邮件
	 * 
	 * @param sourceItem
	 * @return
	 */
	public int createEmail(ItemIdType sourceItem) {
		int flag = 1;

		SendItemResponseType sendResponse = service
				.sendItem(sendItemType(sourceItem));
		flag = sendResponse
				.getResponseMessages()
				.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage()
				.get(0).getValue().getResponseClass().compareTo(
						ResponseClassType.SUCCESS);

		return flag;
	}

	/**
	 * 校验email
	 * 
	 * @param webEmail
	 * @return
	 */
	public boolean validateEmail(WebEmail webEmail, String uid) {
		if (webEmail.getHead() == null || webEmail.getBody() == null
				|| webEmail.getAccount() == null) {
			logger.error("Required property of email is missing.");
			logger.info("Email send failed.");
			return false;
		}
		if (webEmail.getAccount().getName() == null
				|| webEmail.getAccount().getName().trim().length() == 0
				|| webEmail.getAccount().getSendHost() == null
				|| webEmail.getAccount().getSendHost().trim().length() == 0
				|| webEmail.getAccount().getPassword() == null
				|| webEmail.getAccount().getPassword().trim().length() == 0) {
			logger.error("Required property of account is missing.");
			logger.info("Email send failed.");
			return false;
		}
		return true;
	}

	/**
	 * 发送邮件
	 * 
	 * @param webEmail
	 * @return
	 */
	public boolean sendEmail(WebEmail webEmail, String uid) {
		if (!validateEmail(webEmail, uid)) {
			return false;
		}
		// 连接服务器
		connect(webEmail.getAccount().getSendHost(), webEmail.getAccount()
				.getName(), webEmail.getAccount().getPassword(), webEmail
				.getAccount().isSsl());

		// 正文
		ItemIdType itemId = createMessage(webEmail);
		if (itemId == null) {
			logger
					.error("Failure authenticating with NTLM. Error serverhost or username or password.");
			logger.info("Email send failed.");
			Client.getInstance(service).close();
			return false;
		}
		// 附件
		if (webEmail.getAttach() != null) {
			for (WebEmailattachment att : webEmail.getAttach()) {
				if (att.getBody() == null || att.getBody().trim().length() == 0
						|| att.getName() == null
						|| att.getName().trim().length() == 0) {
					logger.error("Required property of attachment is missing.");
					logger.info("Email send failed.");
					Client.getInstance(service).close();
					return false;
				}
				itemId = createAttachment(att, itemId);
			}
		}
		// 完整的邮件
		int flag = createEmail(itemId);
		if (flag != 0) {
			logger.info("Email send failed.");
			Client.getInstance(service).close();
			return false;
		}
		logger.info("Email send success.");
		Client.getInstance(service).close();
		return true;

	}

	public void modifyline(WebEmail webEmail) {
		if (null != webEmail.getBody().getData()) {
			webEmail.getBody().getData().replaceAll("\r\n", "<br/>");
			webEmail.getBody().getData().replaceAll("\n", "<br/>");
			webEmail.getBody().getData().replaceAll("\r", "<br/>");
			webEmail.getBody().getData().replaceAll(" ", "&nbsp;");
		}
	}
}
