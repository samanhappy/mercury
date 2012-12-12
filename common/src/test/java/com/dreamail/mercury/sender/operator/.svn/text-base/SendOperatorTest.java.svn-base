/**
 * 
 */
package com.dreamail.mercury.sender.operator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;

import org.junit.Test;

import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailattachment;
import com.dreamail.mercury.domain.WebEmailbody;
import com.dreamail.mercury.domain.WebEmailhead;
import com.dreamail.mercury.sender.operator.SendOperator;
import com.dreamail.mercury.util.EmailUtils;

/**
 * @author meng.sun
 * 
 */
public class SendOperatorTest {

	@Test
	public void testSendMail() throws Exception {

		WebEmailhead head = new WebEmailhead();
		WebAccount account = new WebAccount();
		account.setAlias("sam");

		// archermind
		account.setId(1);
		account.setName("wpk1905@163.com");
		account.setPassword("8611218773");
		account.setSendHost("smtp.163.com");
		account.setSendPort("25");
		// account.setSendTs("tls");
		head.setFrom("wpk1905@163.com");

		// 126
		// account.setName("saman_love@126.com");
		// account.setPassword("sm6051199");
		// account.setSendHost("smtp.126.com");
		// account.setSendPort("25");
		// account.setSendTs("");
		// head.setFrom("saman_love@126.com");

		// yahoo
		// account.setName("kai_li_mind_18@yahoo.com");
		// account.setPassword("archermind");
		// account.setSendHost("smtp.mail.yahoo.com");
		// account.setSendPort("25");
		// // account.setSendTs("ssl");
		// head.setFrom("kai_li_mind_18@yahoo.com");

		// yahoo izymail
		// account.setName("kai_li_mind@yahoo.com");
		// account.setPassword("85122971");
		// account.setSendHost("out.izymail.com");
		// account.setSendPort("25");
		// account.setSendTs("tls");
		// head.setFrom("kai_li_mind@yahoo.com");

		// gmail
		// account.setName("samanhappy@gmail.com");
		// account.setPassword("sm6051199");
		// account.setSendHost("smtp.gmail.com");
		// account.setSendPort("465");
		// account.setSendTs("ssl");
		// head.setFrom("samanhappy@gmail.com");
		head.setMid("1");
		head.setTo("wpk1905@163.com");
		head.setSubject("测试邮件");
		SendOperator operator = new SendOperator();
		WebEmailbody body = new WebEmailbody();
		body.setData("中文");
		body.setDatatype("0");
		// head.setReply("<201007211451011692991@archermind.com>");

		WebEmail email = new WebEmail();
		email.setBody(body);
		email.setHead(head);
		WebEmailattachment att = new WebEmailattachment();
		att.setAttid("1");
		att
				.setBody("MS608r+qY21kw/zB7rSwv9oNCjIuY2S1vc/uxL+4+cS/wrwNCjMubXZuIGNsZWFuDQo0Lm12biAt"
						+ "RG1hdmVuLnRlc3Quc2tpcD10cnVlIHBhY2thZ2UNCjUu1tjD/MP7d2FysPxtZXJjdXJ5LXRhbGFy"
						+ "aWEud2FyoaJtZXJjdXJ5LXBldGFzb3Mud2Fyzqp0YWxhcmlhLndhcqGicGV0YXNvcy53YXINCjYu"
						+ "5K/AwMb3tPK/qmh0dHA6Ly8xOTIuMTY4LjIwLjIxMDo0ODQ4DQo3LnVuZGVwbG950rPD5kFwcGxp"
						+ "Y2F0aW9uz8K1xLbU06bP7sS/DQo4LmRlcGxveeSvwMDJz7SrttTTprXEz+7Ev3dhcrD8o6y147v3"
						+ "T0sNCg0KIGphcrD8sr/K8KO6DQogDQogICAgIDAubWF2ZW4gtPJqYXIgsPwgDQogDQogICAgICAg"
						+ "IDEuyM7O8bmks6ejrL2rdGFza2ZhY3RvcnkgamFysPwgt8W1vcS/serOxLz+IC9ob21lL2FyY2hl"
						+ "cm1pbmQvdGFza2ZhY3Rvcnkgz8INCiANCiAgICAgICAgICAgY2QgL2hvbWUvYXJjaGVybWluZC90"
						+ "YXNrZmFjdG9yeS9qYXIgLWphciB0YXNrRmFjdG9yeS0xLjAtU05BUFNIT1QuamFyIHRydWUgJg0K"
						+ "IA0KICAgICAgICAgICAgKLLOyv3LtcP3o7p0cnVltPqx7W1hc3Rlcrf+zvHG96O6ZmFsc2UgtPqx"
						+ "7WNvbW1vbrf+zvHG9yApDQogICAgICAgICAgIKOoJiC0+rHt1Nq688yoz9TKvsjV1r6jqQ0KIA0K"
						+ "ICAgICAgMi7K1dPKvP6yv8rwDQogICAgICAgICAgICC9q2NhZHVjZXVzICBqYXKw/CC3xbW9xL+x"
						+ "6s7EvP4gL2hvbWUvYXJjaGVybWluZC90YXNrZmFjdG9yeSDPwiANCiAgICAgICAgICAgY2QgL2hv"
						+ "bWUvYXJjaGVybWluZC90YXNrZmFjdG9yeS9qYXIgLWphciBjYWR1Y2V1cy0xLjAtU05BUFNIT1Qu"
						+ "amFyICYNCg==");
		att.setName("a");
		att.setSize(1000);
		att.setType("txt");
		WebEmailattachment[] atts = new WebEmailattachment[] { att };
		email.setAttach(atts);
		email.setAccount(account);

		operator.sendMail(email, "1");
	}

	@Test
	public void sendSimpleEmail() throws Exception {

		WebEmailhead head = new WebEmailhead();
		head.setFrom("saman_love@126.com");
		head.setTo("kai_li_mind_1@yahoo.com");

		WebAccount account = new WebAccount();
		account.setAlias("sam");
		account.setName("saman_love@126.com");
		account.setPassword("sm6051199");
		account.setSendHost("smtp.126.com");
		account.setSendPort("25");
		// account.setUid("1");

		WebEmailbody body = new WebEmailbody();
		body.setData("简单邮件内容hello");
		body.setDatatype("");

//		SendOperator operator = new SendOperator();

		for (int i = 1; i < 4; i++) {
			head.setSubject("普通混合邮件" + i);
			body.setData("简单邮件内容hello" + i);
			WebEmail email = new WebEmail();
			email.setBody(body);
			email.setHead(head);
			email.setAccount(account);
			// operator.sendMail(email);
		}
	}

	@Test
	public void testSendAttachEmail() throws Exception {
		WebEmailhead head = new WebEmailhead();
		head.setFrom("saman_love@126.com");
		head.setTo("kai_li_mind_2@yahoo.com");

		WebAccount account = new WebAccount();
		account.setAlias("sam");
		account.setName("saman_love@126.com");
		account.setPassword("sm6051199");
		account.setSendHost("smtp.126.com");
		account.setSendPort("25");
		// account.setUid("1");

		WebEmailbody body = new WebEmailbody();
		body.setData("简单邮件内容hello");
		body.setDatatype("");

		WebEmailattachment attach1 = new WebEmailattachment();
		attach1.setName("POP3与IMAP协议.doc");
		byte[] b = "附件1".getBytes();
		String s = EmailUtils.changeByteToBase64(b);
		attach1.setBody(s);
		WebEmailattachment[] attachs = new WebEmailattachment[1];
		attachs[0] = attach1;

//		SendOperator operator = new SendOperator();

		for (int i = 1; i < 4; i++) {
			head.setSubject("普通混合邮件" + i);
			body.setData("简单邮件内容hello" + i);
			WebEmail email = new WebEmail();
			email.setBody(body);
			email.setHead(head);
			email.setAccount(account);
			email.setAttach(attachs);
			// operator.sendMail(email);
		}
	}

	@Test
	public void testSendLargeAttachEmail() throws Exception {
		WebEmailhead head = new WebEmailhead();
		head.setFrom("saman_love@126.com");
		head.setTo("kai_li_mind_2@yahoo.com");

		WebAccount account = new WebAccount();
		account.setAlias("sam");
		account.setName("saman_love@126.com");
		account.setPassword("sm6051199");
		account.setSendHost("smtp.126.com");
		account.setSendPort("25");
		// account.setUid("1");

		WebEmailbody body = new WebEmailbody();

		WebEmailattachment attach1 = new WebEmailattachment();
		attach1.setName("POP3与IMAP协议.doc");
		File file = new File(
				"src/test/resources/com/archermind/mercury/sender/operator/POP3与IMAP协议.doc");
		StringBuffer attachBody = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String str = br.readLine();
			while (str != null) {
				attachBody.append(str);
				str = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		attach1.setBody(EmailUtils.changeByteToBase64(attachBody.toString()
				.getBytes()));
		WebEmailattachment[] attachs = new WebEmailattachment[1];
		attachs[0] = attach1;

		// SendOperator operator = new SendOperator();

		head.setSubject("大附件邮件");
		body.setData("大附件邮件内容");
		WebEmail email = new WebEmail();
		email.setBody(body);
		email.setHead(head);
		email.setAccount(account);
		email.setAttach(attachs);
		// operator.sendMail(email);
	}

	@Test
	public void testManyMessage() throws Exception {
		WebEmailhead head = new WebEmailhead();
		head.setFrom("samanhappy@qq.com");

		WebAccount account = new WebAccount();
		account.setAlias("sam");
		account.setName("samanhappy@qq.com");
		account.setPassword("workhard1314");
		account.setSendHost("smtp.qq.com");
		account.setSendPort("25");
		// account.setUid("1");

		WebEmailbody body = new WebEmailbody();

		List<String> filenameList = new ArrayList<String>();
		filenameList.add("图片.jpg");
		filenameList.add("图片.gif");

//		SendOperator operator = new SendOperator();
		WebEmail email = new WebEmail();

		for (int j = 0; j < filenameList.size(); j++) {
			WebEmailattachment attach1 = new WebEmailattachment();
			attach1.setName(filenameList.get(j));
			byte[] bodybyte = new byte[0];
			try {
				byte[] buffer = new byte[1024];
				DataInputStream dis = new DataInputStream(new FileInputStream(
						new File(
								"src/test/resources/com/archermind/mercury/sender/operator/"
										+ filenameList.get(j))));
				int count = dis.read(buffer);
				while (count != -1) {
					bodybyte = addbyte(bodybyte, buffer);
					count = dis.read(buffer);
				}
				dis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			attach1.setBody(EmailUtils.changeByteToBase64(bodybyte));
			WebEmailattachment[] attachs = new WebEmailattachment[1];
			attachs[0] = attach1;

			email.setBody(body);
			email.setHead(head);
			email.setAccount(account);
			email.setAttach(attachs);

			head.setTo("meng.sun@archermind.com");
			head.setSubject("测试邮件");
			body.setData("邮件内容");
			head.setSendTime("19871214");
			// operator.sendMail(email);
		}

	}

	public static byte[] addbyte(byte[] b1, byte[] b2) {
		byte[] b = new byte[b1.length + b2.length];
		for (int i = 0; i < b1.length; i++) {
			b[i] = b1[i];
		}
		for (int i = 0; i < b2.length; i++) {
			b[b1.length + i] = b2[i];
		}
		return b;
	}

	@Test
	public void testValidate() {
		WebAccount account = new WebAccount();
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(
					"src/main/resources/sendmail.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		account.setName(props.getProperty("name"));
		account.setPassword(props.getProperty("password"));
		account.setSendHost(props.getProperty("sendHost"));
		account.setSendPort(props.getProperty("sendPort"));
		WebEmailhead head = new WebEmailhead();
		head.setFrom(account.getName());
		head.setTo("meng.sun@archermind.com");
		SendOperator operator = new SendOperator();
		head.setSubject("smtp test");

		WebEmailbody body = new WebEmailbody();
		body.setData("hello this is a smtp test mail");
		body.setDatatype("");
		WebEmail email = new WebEmail();
		email.setBody(body);
		email.setHead(head);
		email.setAccount(account);

		try {
			operator.sendMailWithoutSave(email);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
