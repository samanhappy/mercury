package com.dreamail.mercury.delete;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import org.junit.Test;

public class TestDeleteMail {

	@Test
	public void testDeletePop() {
		Properties pop = new Properties();
		Session session = Session.getDefaultInstance(pop);
		String host = "", username = "", password = "";// 要读取的邮箱的服务器地址，邮箱用户名和密码

		try {
			Store store = session.getStore("pop3");
			store.connect(host, username, password);
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);// 注意这里与读取时的区别
			Message message[] = folder.getMessages();
			System.out.println(message.length);
			for (int i = 0; i < message.length; i++) {
				message[i].setFlag(Flags.Flag.DELETED, true);// 我这里是删除了所有的文件，可根据需要来编写代码
			}
			folder.close(true);// 注意这里与读取的区别，这里是确认删除的操作....
			store.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
