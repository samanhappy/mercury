package com.dreamail.mercury.xfire;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.Channel;
import org.junit.Test;

import com.microsoft.ews.client.ExchangeServicePortType;
import com.microsoft.ews.client.ExchangeWebServiceClient;
import com.microsoft.schemas.exchange.services._2006.messages.ArrayOfResponseMessagesType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateItemType;
import com.microsoft.schemas.exchange.services._2006.messages.FindItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.FindItemType;
import com.microsoft.schemas.exchange.services._2006.messages.ItemInfoResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.ResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.types.ArrayOfRecipientsType;
import com.microsoft.schemas.exchange.services._2006.types.BodyType;
import com.microsoft.schemas.exchange.services._2006.types.BodyTypeType;
import com.microsoft.schemas.exchange.services._2006.types.ConnectingSIDType;
import com.microsoft.schemas.exchange.services._2006.types.DefaultShapeNamesType;
import com.microsoft.schemas.exchange.services._2006.types.DistinguishedFolderIdNameType;
import com.microsoft.schemas.exchange.services._2006.types.DistinguishedFolderIdType;
import com.microsoft.schemas.exchange.services._2006.types.EmailAddressType;
import com.microsoft.schemas.exchange.services._2006.types.ExchangeImpersonationType;
import com.microsoft.schemas.exchange.services._2006.types.ItemQueryTraversalType;
import com.microsoft.schemas.exchange.services._2006.types.ItemResponseShapeType;
import com.microsoft.schemas.exchange.services._2006.types.MessageDispositionType;
import com.microsoft.schemas.exchange.services._2006.types.MessageType;
import com.microsoft.schemas.exchange.services._2006.types.NonEmptyArrayOfAllItemsType;
import com.microsoft.schemas.exchange.services._2006.types.NonEmptyArrayOfBaseFolderIdsType;

public class WSDL2ClientTest {

	@Test
	public void testSendEmail() {
		ExchangeWebServiceClient client = new ExchangeWebServiceClient();

		ExchangeServicePortType service = client
				.getExchangeWebPort("https://admin-h1li7jsv2/EWS/Exchange.asmx");

		Client client1 = Client.getInstance(service);
		client1.setProperty(Channel.USERNAME, "pushmail\\danwang");
		client1.setProperty(Channel.PASSWORD, "honey@123");
		client1.setProperty("disable.expect-continue", "true");

		CreateItemType createItem = new CreateItemType();

		BodyType body = new BodyType();
		body.setValue("body string11");
		body.setBodyType(BodyTypeType.HTML);

		MessageType itemType = new MessageType();
		itemType.setSubject("xfire test111");
		itemType.setBody(body);

		ArrayOfRecipientsType recipients = new ArrayOfRecipientsType();
		EmailAddressType address = new EmailAddressType();
		address.setEmailAddress("Administrator@pushmail.com");
		recipients.getMailbox().add(address);
		itemType.setToRecipients(recipients);

		NonEmptyArrayOfAllItemsType items = new NonEmptyArrayOfAllItemsType();
		items.getItemOrMessageOrCalendarItem().add(itemType);

		createItem.setItems(items);

		createItem
				.setMessageDisposition(MessageDispositionType.SEND_AND_SAVE_COPY);

		ExchangeImpersonationType imperson = new ExchangeImpersonationType();
		ConnectingSIDType sid = new ConnectingSIDType();
		sid.setPrimarySmtpAddress("");
		sid.setPrincipalName("");
		sid.setSID("");
		imperson.setConnectingSID(sid);

		CreateItemResponseType response = service.createItem(createItem);
		ArrayOfResponseMessagesType array = response.getResponseMessages();
		List<JAXBElement<? extends ResponseMessageType>> list = array
				.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage();
		for (JAXBElement<? extends ResponseMessageType> element : list) {
			if (element.getValue() instanceof ItemInfoResponseMessageType) {
				System.out.println(element.getDeclaredType());
				System.out.println(element.getName());
				System.out.println(element.getScope());
				ItemInfoResponseMessageType item = (ItemInfoResponseMessageType) element
						.getValue();
				System.out.println(item.getItems()
						.getItemOrMessageOrCalendarItem().size());
			}
		}
	}

	@Test
	public void testReceiveEmail() {
		ExchangeWebServiceClient client = new ExchangeWebServiceClient();

		ExchangeServicePortType service = client
				.getExchangeWebPort("https://admin-h1li7jsv2/EWS/Exchange.asmx");

		Client client1 = Client.getInstance(service);
		client1.setProperty(Channel.USERNAME, "pushmail\\danwang");
		client1.setProperty(Channel.PASSWORD, "honey@123");
		client1.setProperty("disable.expect-continue", "true");

		FindItemType findItem = new FindItemType();

		NonEmptyArrayOfBaseFolderIdsType folders = new NonEmptyArrayOfBaseFolderIdsType();
		DistinguishedFolderIdType id = new DistinguishedFolderIdType();
		id.setId(DistinguishedFolderIdNameType.INBOX);

		folders.getFolderIdOrDistinguishedFolderId().add(id);
		findItem.setParentFolderIds(folders);

		findItem.setTraversal(ItemQueryTraversalType.SHALLOW);

		ItemResponseShapeType shape = new ItemResponseShapeType();
		shape.setBaseShape(DefaultShapeNamesType.ALL_PROPERTIES);
		findItem.setItemShape(shape);

		FindItemResponseType res = service.findItem(findItem);
		List<JAXBElement<? extends ResponseMessageType>> list = res
				.getResponseMessages()
				.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage();

		for (JAXBElement<? extends ResponseMessageType> element : list) {
			System.out.println(element.getDeclaredType());
			System.out.println(element.getName());
			System.out.println(element.getScope());
			System.out.println(element.getValue());
		}
	}
}
