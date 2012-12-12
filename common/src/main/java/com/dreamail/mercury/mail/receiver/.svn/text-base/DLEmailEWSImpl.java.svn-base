package com.dreamail.mercury.mail.receiver;

import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;

import com.dreamail.mercury.domain.Body;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.domain.EmailSupport;
import com.dreamail.mercury.domain.TimeoutException;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.microsoft.ews.client.EWSReceiveClient;

public class DLEmailEWSImpl implements IDLProvide{

	@Override
	public Clickoo_message_attachment dlAttachment(WebAccount account, String uuid,
			String attName, int size,EmailSupport eSupport) throws MessagingException {
		return new EWSReceiveClient().getAttachmentByUuid(account, uuid, attName, size, eSupport);
	}

	@Override
	public Body dlData(WebAccount account, String uuid)
			throws MessagingException {
		return new EWSReceiveClient().getEmailBodyByUuid(account, uuid);
	}

	@Override
	public Email dlEmail(WebAccount account, String uuid,List<Clickoo_message_attachment> exitAtt,EmailSupport eSupport,boolean dlBody)
			throws MessagingException {
		return new EWSReceiveClient().getEmailByUuid(account, uuid, exitAtt, eSupport);
	}

	@Override
	public void closeConnection(Context context) {
	}

	@Override
	public Properties getProperties(String receiveTs, String port) {
		return null;
	}

	@Override
	public boolean isLargeMsg(int msgSize) throws NumberFormatException,
			MessagingException {
		return false;
	}

	@Override
	public boolean isTimeout(Context context) throws TimeoutException {
		return false;
	}

	@Override
	public void receiveLargeMail(Context context) throws MessagingException {
	}

	@Override
	public void receiveMail(Context context) throws MessagingException,
			TimeoutException {
	}

	@Override
	public List<Clickoo_message_attachment> dlAllAttachment(WebAccount account, String uuid,List<Clickoo_message_attachment> exitAtt,EmailSupport eSupport)
			throws MessagingException {
		return new EWSReceiveClient().getMissingAttListByUuid(account, uuid, exitAtt, eSupport);
	}

}
