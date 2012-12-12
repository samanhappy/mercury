package com.dreamail.mercury.receiver.attachment.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.mail.receiver.attachment.impl.AttachmentFormatControl;
import com.dreamail.mercury.pojo.Clickoo_mail_account;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;

public class AttachmentControlTestCase {
	AttachmentFormatControl afc = new AttachmentFormatControl();

	@Test
	public void attachmentFormatControlTest() {
		PropertiesDeploy pd = new PropertiesDeploy();
		pd.init();
		Clickoo_message_attachment att = new Clickoo_message_attachment();
		InputStream input = null;

		byte[] by;
		try {
			input = new FileInputStream("D://test.gif");
			by = inputStreamToByte(input);
			input.close();
			att.setIn(by);
			att.setName("test.gif");
			att.setType("gif");
			List<Clickoo_message_attachment> list = new ArrayList<Clickoo_message_attachment>();
			list.add(att);
			Email mail = new Email();
			mail.setUuid("2000");
			mail.setAttachList(list);
			mail.setMessageId("0000123");

			ArrayList<Email> maillist = new ArrayList<Email>();
			maillist.add(mail);
			Clickoo_mail_account cma = new Clickoo_mail_account();
			cma.setName("zhangsan@163.com");

			Context context = new Context();
			// context.setUid(1);

			// context.setEmailList(maillist);

			try {
				afc.doProces(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public byte[] inputStreamToByte(InputStream iStrm) throws IOException {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = iStrm.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}

	// @Test
	// public void testName(){
	// String name = "text.ppt";
	// if(name.lastIndexOf(".")>0){
	// name = name.substring(0, name.lastIndexOf("."));
	// }
	// System.out.println(name);
	//		
	// }
	//	
	// @Test
	// public void testLength(){
	// File f = new
	// File("C:/Documents and Settings/kai.li/Local Settings/Temp/pushMail/zhangsan@163.com/2000/OutImage/Blue hills.jpg_eoooasdasd.jpg");
	// System.out.println(f.length());
	// }

}
