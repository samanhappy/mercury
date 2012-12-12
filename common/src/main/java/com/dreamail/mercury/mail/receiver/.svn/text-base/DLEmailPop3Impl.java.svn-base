package com.dreamail.mercury.mail.receiver;

import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;

import com.dreamail.mercury.domain.Body;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.domain.EmailSupport;
import com.dreamail.mercury.domain.TimeoutException;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserImpl;
import com.dreamail.mercury.receiver.mail.parser.impl.EmailParserProvide;
import com.dreamail.mercury.util.Constant;

public class DLEmailPop3Impl extends AbstractPop3Provide implements IDLProvide
{

	public void receiveLargeMail(Context context) throws MessagingException
	{
		// TODO Auto-generated method stub

	}

	public void receiveMail(Context context) throws MessagingException,
			TimeoutException
	{
		// TODO Auto-generated method stub

	}
	 /**
	  * pop3方式根据传入参数下载邮件.
	  * @param account
      * @param uuid
      * @param exitAtt
      * @param eSupport
      * @param dlBody
	  * @return Email
	  */
	public Email dlEmail(WebAccount account, String uuid,List<Clickoo_message_attachment> exitAtt,EmailSupport eSupport,boolean dlBody) throws MessagingException
			
	{
		Message message = null;
		Email email = null;
		message = getPop3MessageByUuid(account, uuid);
		EmailParserProvide parser = new EmailParserProvide();
		try {
			email = parser.getEmail(user, message, uuid,exitAtt,eSupport,dlBody);
		} catch(MessagingException e) {
			DLEmailException exception = new DLEmailException();
			exception.setMessage(Constant.PARSE_MAIL_FAIL);
			throw exception;
		}
		
		return email;
	}
	 /**
	  * pop3方式根据传入参数下载邮件附件.
	  * @param account
      * @param uuid
      * @param attName
      * @param size
      * @param eSupport
	  * @return Attachment
	  */
	@Override
	public Clickoo_message_attachment dlAttachment(WebAccount account, String uuid,
			String attName, int size,EmailSupport eSupport) throws MessagingException
	{
		Clickoo_message_attachment attachment = new Clickoo_message_attachment();
		attachment.setName(attName);
		String type = null;
		if(attName.indexOf(".")!=-1){
			type = attName.substring(attName.lastIndexOf(".") + 1).toLowerCase();
		}
		attachment.setType(type);
		attachment.setLength(size);
		Message message = null;
		message = getPop3MessageByUuid(account, uuid);
		
		try
		{
			EmailParserImpl.getInstance().doloadAttach((Part)message, attachment,eSupport);
		} catch (Exception e)
		{
			logger.error("Download exception,",e);
			DLEmailException exception = new DLEmailException();
			exception.setMessage(Constant.PARSE_MAIL_FAIL);
			throw exception;
		}
		return attachment;
	}
	 /**
	  * pop3方式根据传入参数下载邮件正文.
	  * @param account
      * @param uuid
	  * @return Body
	  */
	@Override
	public Body dlData(WebAccount account, String uuid)
			throws MessagingException
	{
		Body body = null;
		StringBuffer sbData = new StringBuffer();
		Message message = null;
		message = getPop3MessageByUuid(account, uuid);
		try
		{
			String bodyText = EmailParserImpl.getInstance()
					.getBodyText(message);
			sbData.append(bodyText);
			/*List<Attachment> attList = EmailParserImpl.getInstance()
					.getAttachList(message);
			for (Attachment att : attList)
			{
				if ("txt".equalsIgnoreCase(att.getType()))
				{
					sbData
							.append(
									"_________The following is the contents of the annex[")
							.append(att.getName()).append("]:");
					sbData.append(new String(att.getIn(), "UTF-8"));
					break;
				}

				if ("doc".equalsIgnoreCase(att.getType())
						|| "pdf".equalsIgnoreCase(att.getType())
						|| "xls".equalsIgnoreCase(att.getType()))
				{

					List<Clickoo_message_attachment> nAttList = new AttachmentService()
							.selectAttachmentListByMid(att.getMid());
					// problem point
					sbData
							.append(
									"_________The following is the contents of the annex[")
							.append(att.getName()).append("]:");

					for (Clickoo_message_attachment clickoo_att : nAttList)
					{
						if (att.getId() == clickoo_att.getParent()
								&& "txt"
										.equalsIgnoreCase(clickoo_att.getType()))
						{
							File f = new File(clickoo_att.getPath());
							FileReader fr = new FileReader(f);
							BufferedReader br = new BufferedReader(fr);
							String str = br.readLine();
							while (str != null)
							{
								sbData.append(str);
								str = br.readLine();
							}
							br.close();

						}
					}
					break;
				}

			}*/
		} catch (Exception e)
		{
			DLEmailException exception = new DLEmailException();
			exception.setMessage(Constant.PARSE_MAIL_FAIL);
			throw exception;
		}
		body = new Body();
		body.setData(sbData.toString());
		body.setType("txt");
		body.setSize(sbData.toString().length());
		return body;
	}
	
	 /**
	  * pop3方式根据传入参数下载邮件所有缺失附件.
	  * @param account
	  * @param uuid
	  * @param exitAtt
	  * @param eSupport
	  * @return List<Clickoo_message_attachment>
	  */
	@Override
	public List<Clickoo_message_attachment> dlAllAttachment(WebAccount account, String uuid,List<Clickoo_message_attachment> exitAtt,EmailSupport eSupport)
			throws MessagingException {
		List<Clickoo_message_attachment> attList = null;
		Message message = getPop3MessageByUuid(account, uuid);
		try
		{
			attList = EmailParserImpl.getInstance().getDownAttachList(message,exitAtt,eSupport);
		} catch (Exception e)
		{
			DLEmailException exception = new DLEmailException();
			exception.setMessage(Constant.PARSE_MAIL_FAIL);
			throw exception;
		}
		return attList;
	}
}
