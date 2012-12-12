package com.dreamail.mercury.proces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.mail.jms.ReceiveMain;
import com.dreamail.mercury.mail.receiver.IProvide;
import com.dreamail.mercury.receiver.mail.impl.GmailProviderImpl;
import com.dreamail.mercury.receiver.mail.impl.HotmailProvideImpl;
import com.dreamail.mercury.receiver.mail.impl.IMAP4ProviderImpl;
import com.dreamail.mercury.receiver.mail.impl.Pop3ProviderImpl;
import com.dreamail.mercury.receiver.mail.impl.YahooProviderImpl;
import com.dreamail.mercury.util.Constant;
import com.microsoft.ews.client.EWSReceiveClient;

public class EmailReceiverProces implements IProces{
	private static final Logger logger = LoggerFactory
	.getLogger(EmailReceiverProces.class);
	public IProvide getProvide() {
		return provide;
	}

	public void setProvide(IProvide provide) {
		this.provide = provide;
	}

	private IProvide provide;
	
	@Override
	public void doProces(Context context) throws Exception {
		String type = context.getReceiveProtocoltype();
		if (Constant.POP3.equalsIgnoreCase(type)) {
			if(ReceiveMain.asControl && Constant.HOTMAIL_HOST_NAME.equalsIgnoreCase(context.getHostName())){
				setProvide(new HotmailProvideImpl());
			}else if(ReceiveMain.idleControl && Constant.GMAIL_HOST_NAME.equalsIgnoreCase(context.getHostName())){
				setProvide(new GmailProviderImpl());
			}else{
				setProvide(new Pop3ProviderImpl());
			}
		} else if (Constant.IMAP.equalsIgnoreCase(type)) {
			if(ReceiveMain.snpControl && Constant.YAHOO_HOST_NAME.equalsIgnoreCase(context.getHostName())){
				setProvide(new YahooProviderImpl());
			}else{
				setProvide(new IMAP4ProviderImpl());
			}
		} else if (Constant.HTTP.equalsIgnoreCase(type)) {
			setProvide(new EWSReceiveClient());
		} 
		
		if(!context.isState()){
			getProvide().receiveMail(context);
		}else{
			logger.info(context.getLoginName()+" receive larger message..");
			getProvide().receiveLargeMail(context);
		}
		
	}
}
