package com.dreamail.mercury.util;

import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.InCert;
import com.dreamail.mercury.pojo.InPath;
import com.dreamail.mercury.pojo.OutPath;

public class WebAccountUtil {
	
	public static WebAccount getAccountByContext(
			Clickoo_mail_account mail_account, WebAccount account) {
		WebAccount waccount = new WebAccount();
		InPath inPath = mail_account.getInPath_obj();
		OutPath outPath = mail_account.getOutPath_obj();
		InCert inCert = mail_account.getInCert_obj();
		String inhost = inPath.getInhost();
		waccount.setReceiveHost(inhost);
		String rpt = inPath.getProtocolType();
		waccount.setReceiveProtocolType(rpt);
		waccount.setReceivePort(inPath.getReceivePort());
		String receiveTs = "".equals(inPath.getReceiveTs()) ? null : inPath.getReceiveTs();
		waccount.setReceiveTs(receiveTs);
		waccount.setSendHost(outPath.getSmtpServer());
		if (Constant.USE_SSL.equals(inPath.getSsl())) {
			waccount.setSsl(true);
		} else if (Constant.NOT_USE_SSL.equals(inPath.getSsl())) {
			waccount.setSsl(false);
		}
		
		if (rpt.equals(Constant.HTTP)) {
			waccount.setSendProtocolType(Constant.HTTP);
		} else {
			waccount.setSendProtocolType(Constant.SMTP);
		}
		waccount.setSendPort(outPath.getSendPort());
		String sendTs = "".equals(outPath.getType()) ? null : outPath.getType();
		waccount.setSendTs(sendTs);
		String name = inCert.getLoginID();
		waccount.setId(account.getId());
		waccount.setName(name);
		if(account.getPassword() != null){
			waccount.setPassword(new String(EmailUtils.base64TochangeByte(account.getPassword())));
		} else{
			String pwd = inCert.getPwd();
			waccount.setPassword(new String(EmailUtils.base64TochangeByte(pwd)));
		}
		
		if (inCert.getToken() != null) {
			waccount.setToken(inCert.getToken());
		} else if (account.getToken() != null) {
			waccount.setToken(account.getToken());
		} else if (mail_account.getOutCert_obj().getToken() != null) {
			waccount.setToken(mail_account.getOutCert_obj().getToken());
		}
		
		String compositor = inPath.getCompositor();
		if (compositor != null) {
			waccount.setCompositor(compositor);
		}
		String supportalluid = inPath.getSupportalluid();
		if (supportalluid != null) {
			waccount.setSupportalluid(supportalluid);
		}
		String[] inpathList = inhost.split(",");
		waccount.setInpathList(inpathList);
		waccount.setProxyName(account.getProxyName());
		waccount.setProxyPassword(account.getProxyPassword());
		waccount.setType(mail_account.getType());
		return waccount;
	}

	public static WebAccount getAccountByServer(
			Clickoo_mail_account receive_account,
			Clickoo_mail_account send_account, WebAccount account) {
		WebAccount waccount = new WebAccount();
		InPath inPath = receive_account.getInPath_obj();
		OutPath outPath = send_account.getOutPath_obj();
		String inhost = inPath.getInhost();
		waccount.setReceiveHost(inhost);
		// String rpt = "";
		// if (inhost.toLowerCase().contains(Constant.POP3.toLowerCase())) {
		// rpt = Constant.POP3;
		// } else if
		// (inhost.toLowerCase().contains(Constant.IMAP.toLowerCase())) {
		// rpt = Constant.IMAP;
		// }
		String rpt = inPath.getProtocolType();
		waccount.setReceiveProtocolType(rpt);
		waccount.setReceivePort(inPath.getReceivePort());
		String receiveTs = "".equals(inPath.getReceiveTs()) ? null : inPath.getReceiveTs();
		waccount.setReceiveTs(receiveTs);
		waccount.setSendHost(outPath.getSmtpServer());
		if (rpt.equals(Constant.HTTP)) {
			waccount.setSendProtocolType(Constant.HTTP);
		} else {
			waccount.setSendProtocolType(Constant.SMTP);
		}
		waccount.setSendPort(outPath.getSendPort());
		String sendTs = "".equals(outPath.getType()) ? null : outPath.getType();
		waccount.setSendTs(sendTs);
		// waccount.setUid(String.valueOf(account.getUid()));
		waccount.setName(account.getName());
		waccount.setPassword(new String(EmailUtils.base64TochangeByte(account.getPassword())));
		waccount.setAlias(account.getAlias());
		waccount.setComment(account.getComment());
		waccount.setStatus(account.getStatus());
		return waccount;
	}
}
