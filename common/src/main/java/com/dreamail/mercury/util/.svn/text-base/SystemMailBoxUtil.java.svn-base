package com.dreamail.mercury.util;

import net.sf.json.JSONObject;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.pojo.Clickoo_mail_account;

public class SystemMailBoxUtil {

	private static String suffix = PropertiesDeploy.getConfigureMap().get(
			"system_mailbox_suffix");

	private static String inpath = PropertiesDeploy.getConfigureMap().get(
			"system_mailbox_inpath");

	private static String outpath = PropertiesDeploy.getConfigureMap().get(
			"system_mailbox_outpath");

	private static String receiveserver = PropertiesDeploy.getConfigureMap()
			.get("system_mailbox_receiveserver");

	private static String sendserver = PropertiesDeploy.getConfigureMap().get(
			"system_mailbox_sendserver");

	private static String receiveport = PropertiesDeploy.getConfigureMap().get(
			"system_mailbox_receiveport");

	private static String sendport = PropertiesDeploy.getConfigureMap().get(
			"system_mailbox_sendport");

	private static String receivets = PropertiesDeploy.getConfigureMap().get(
			"system_mailbox_receivets");

	private static String sendts = PropertiesDeploy.getConfigureMap().get(
			"system_mailbox_sendts");

	public static Clickoo_mail_account getMailBoxAccount(String username,
			String password) {
		String accountName = new StringBuffer(username).append(suffix)
				.toString();
		JSONObject cert = new JSONObject();
		cert.put(Constant.LOGINID, accountName);
		cert.put(Constant.PWD, EmailUtils.changeByteToBase64(password
				.getBytes()));

		Clickoo_mail_account account = new Clickoo_mail_account();
		account.setName(accountName);
		account.setInCert(cert.toString());
		account.setInPath(inpath);
		account.setOutCert(cert.toString());
		account.setOutPath(outpath);

		return account;
	}

	public static WebAccount getMailBoxWebAccount(long id, String username) {
		WebAccount account = new WebAccount();
		account.setId(id);
		account.setName(new StringBuffer(username).append(suffix).toString());
		account.setReceiveHost(receiveserver);
		account.setReceivePort(receiveport);
		account.setSendHost(sendserver);
		account.setSendPort(sendport);
		account.setReceiveTs(receivets);
		account.setSendTs(sendts);
		return account;
	}
}
