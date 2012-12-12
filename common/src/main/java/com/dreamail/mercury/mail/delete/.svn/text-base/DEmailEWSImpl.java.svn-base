package com.dreamail.mercury.mail.delete;

import java.util.List;

import org.apache.commons.httpclient.protocol.Protocol;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.microsoft.ews.client.EWSSSLSocketFactory;
import com.microsoft.ews.client.ExchangeServicePortType;
import com.microsoft.ews.client.ExchangeWebServiceClient;
import com.microsoft.schemas.exchange.services._2006.messages.DeleteItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.DeleteItemType;
import com.microsoft.schemas.exchange.services._2006.types.DisposalType;
import com.microsoft.schemas.exchange.services._2006.types.ItemIdType;
import com.microsoft.schemas.exchange.services._2006.types.NonEmptyArrayOfBaseItemIdsType;
import com.microsoft.schemas.exchange.services._2006.types.ResponseClassType;

public class DEmailEWSImpl implements IDProvide {

	public static final Logger logger = LoggerFactory
			.getLogger(DEmailEWSImpl.class);

	private ExchangeWebServiceClient client = new ExchangeWebServiceClient();

	private ExchangeServicePortType service;

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

	// 得到DeleteItemType实例
	private DeleteItemType getDeleteItemType(ItemIdType it) {
		DeleteItemType deleteItem = new DeleteItemType();

		NonEmptyArrayOfBaseItemIdsType baseItemType = new NonEmptyArrayOfBaseItemIdsType();
		baseItemType.getItemIdOrOccurrenceItemIdOrRecurringMasterItemId().add(
				it);

		deleteItem.setItemIds(baseItemType);
		deleteItem.setDeleteType(DisposalType.MOVE_TO_DELETED_ITEMS);// 移动到已删除文件夹

		return deleteItem;
	}

	/**
	 * 连接服务器.
	 * 
	 * @return
	 */
	private void connect(String host, String username, String password) {
		try {
			// 设置SSL
			Protocol AlcHttpsProtocol = new Protocol("https",
					new EWSSSLSocketFactory(), 443);
			Protocol.registerProtocol("https", AlcHttpsProtocol);

			// 获取service
			service = client.getExchangeWebPort("https://" + host
					+ "/EWS/Exchange.asmx");

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

	@Override
	public boolean dEmail(Clickoo_mail_account account, List<String> uuidList) {

		// 连接服务器
		connect(account.getInPath_obj().getInhost(), account.getName(), account
				.getInCert_obj().getPwd());

		boolean b = false;
		int i = 0;
		for (String uuid : uuidList) {
			b = dEmailById(account, uuid);
			if (!b) {
				logger.info("failed to delete email[" + uuid + "]");
				i--;
				continue;
			}
		}
		if (i < 0) {
			logger.info("failed to delete " + (-i) + " email.");
			b = false;
		} else {
			logger.info("successed to delete all email.");
			b = true;
		}
		Client.getInstance(service).close();
		return b;
	}

	public boolean dEmailById(Clickoo_mail_account account, String uuid) {

		DeleteItemType deleteItem = getDeleteItemType(getItemIdType(uuid));

		DeleteItemResponseType deleteResponse = service.deleteItem(deleteItem);

		if (deleteResponse
				.getResponseMessages()
				.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage()
				.get(0).getValue().getResponseClass().compareTo(
						ResponseClassType.SUCCESS) == 0) {
			return true;
		}
		logger.info("none exists email.");
		return false;
	}

}
