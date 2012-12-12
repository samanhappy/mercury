package com.dreamail.mercury.receiver.mail.parse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.mail.MessagingException;
import javax.net.ssl.SSLSocketFactory;

public class Test {
//	private byte[] b;
	public static final String key = "clickoo";

	/**
	 * @param args
	 * @throws MessagingException
	 */
	public static void main(String[] args) throws Exception {
		/*
		 * Properties props = System.getProperties();
		 * props.put("mail.smtp.auth", "true"); Session session =
		 * Session.getDefaultInstance(props, null); // TODO Auto-generated
		 * method stub FileInputStream fis = new
		 * FileInputStream("D:\\eml\\1\\1.eml"); MimeMessage msg = new
		 * MimeMessage(session, fis); System.out.println(msg.getContent());
		 * Email email = new EmailParserProvide().getEmail(null,
		 * "D:\\eml\\1\\1.eml"); System.out.println(email.getMessageId());
		 * System.out.println(email.getSendTime());
		 * System.out.println(email.getSubject());
		 * System.out.println(email.getFrom());
		 * System.out.println(email.getBody().length); System.out.println(new
		 * String(email.getBody()));
		 * System.out.println(email.getAttachList().get(1).getName());
		 * FileOutputStream fos = new FileOutputStream("D:\\2.gif");
		 * fos.write(email.getAttachList().get(1).getIn());
		 */
		// JSONObject j = new JSONObject();
		// System.out.println(j.get("1").toString());
		// Test t = new Test();
		// t.setB(null);
		// System.out.println(t.getB());
		// @SuppressWarnings("unused")
		// Clickoo_message message = new AccountDao().getAccountAndMessage("3",
		// "38").getClickoo_message().get(0);

		// System.out.println(new AccountDao().getAccountAndMessage("3",
		// "38").getClickoo_message().get(0).getId());
		Socket socket = SSLSocketFactory.getDefault().createSocket();

		
		socket.connect(new InetSocketAddress("127.0.0.1", 993));
		OutputStream os = socket.getOutputStream();
		PrintWriter sockout = new PrintWriter(os, true);
		ThreadA t = new ThreadA();
		t.setSockout(sockout);
		t.start();
		BufferedReader br = new BufferedReader(new InputStreamReader(socket
				.getInputStream()));
		String msg = null;
		while ((msg = br.readLine()) != null) {
			System.out.println(msg);
		}
		
	}

}

class ThreadA extends Thread{
	public PrintWriter sockout;
	
	public PrintWriter getSockout() {
		return sockout;
	}

	public void setSockout(PrintWriter sockout) {
		this.sockout = sockout;
	}

	public void run(){
		String CRLF = "\r\n";
		String cmd1 = "A0 CAPABILITY";
		String cmd2 = "1 LOGIN wpk1902@gmail.com \"8611218773\"";
//		String cmd3 = "2 LIST \"\" \"%\"";
		String cmd4 = "3 SELECT inbox";
		String cmd5 = "4 UID FETCH 81,82 FLAGS";
		String cmd6 = "5 UID FETCH 86 (FLAGS UID ENVELOPE RFC822.SIZE BODYSTRUCTURE BODY.PEEK[HEADER.FIELDS (X-PRIORITY)])";
		String cmd7 = "6 UID FETCH 86 BODY.PEEK[1]";
		String[] str = new String[]{cmd1,cmd2,cmd4,cmd5,cmd6,cmd7};
		for (int i = 0; i < str.length; i++) {
			System.out.println("-----------");
			sockout.write(str[i] + CRLF);
			sockout.flush();
			System.out.println(str[i]);
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
