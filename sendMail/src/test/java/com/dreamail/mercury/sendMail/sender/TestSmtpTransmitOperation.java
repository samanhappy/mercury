package com.dreamail.mercury.sendMail.sender;

import java.util.ArrayList;
import java.util.List;

import com.dreamail.mercury.smtp.utils.SmtpEmail;
import com.dreamail.mercury.smtp.utils.SmtpEmailBody;
import com.dreamail.mercury.smtp.utils.SmtpEmailattachment;
import com.dreamail.mercury.smtp.utils.User;

public class TestSmtpTransmitOperation {

	public static void main(String[] args) {

		SmtpEmail email = new SmtpEmail();
		email.setFrom("kai_li_mind_2@yahoo.com");
		User user = new User();
		user.setUserName("kai_li_mind_2@yahoo.com");
		user.setPassword("archermind");
		user.setSuffix("yahoo.*");
		email.setUser(user);

		email.setTo("archermind163@163.com");
		email.setCc("archermind126@126.com");
		email.setReplyTo("kai_li_mind_2@yahoo.com");

		SmtpEmailBody body = new SmtpEmailBody();
		body.setData("中文");
		body.setDatatype("0");
		email.setBody(body);

		SmtpEmailattachment att = new SmtpEmailattachment();
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
		att.setName("a.txt");
		att.setSize(1000);
		att.setType("txt");

		SmtpEmailattachment att1 = new SmtpEmailattachment();
		att1
				.setBody("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"
						+ "q3lQAZUmm7HPLlaxQwAAOw==");
		att1.setName("1.txt");
		att1.setSize(5000);
		att1.setType("gif");
		List<SmtpEmailattachment> list = new ArrayList<SmtpEmailattachment>();
		list.add(att);
		list.add(att1);
		email.setAttach(list);

		try {
			for (int i = 0; i < 2; i++) {
				new SmtpTransmitOperation().createMsgAndSaveToEml(email);
			}
			new SmtpTransmitOperation().sendMail();
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
	}
}
