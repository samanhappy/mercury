package com.dreamail.mercury.delete;
//package com.archermind.mercury.delete;
//
//import org.junit.Test;
//
//import com.archermind.mercury.mail.delete.EmailDeleteControl;
//import com.archermind.mercury.mail.delete.IDProvide;
//import com.clickoo.mercury.domain.WebAccount;
//
//public class DeleteEmailTest {
//	@Test
//	public void deleteEmail() {
//
//		WebAccount account = new WebAccount();
//
//		// 邮件彻底删除了
//		// account.setReceiveHost("pop.163.com");
//		// account.setReceivePort("110");
//		// account.setReceiveTs("");
//		// account.setReceiveProtocolType("pop");
//		// account.setName("ss_1026@163.com");
//		// account.setPassword("123456");
//		// String[] inpathList = { "pop.163.com", "", "pop" };
//		// account.setInpathList(inpathList);
//		// 
//		// String uuid = "";
//
//		// 已删除文件夹将保存副本
//		account.setReceiveHost("pop.gmail.com");
//		account.setReceivePort("995");
//		account.setReceiveTs("SSL");
//		account.setReceiveProtocolType("pop");
//		account.setName("hbws1026@gmail.com");
//		account.setPassword("wszzwps.123");
//		String[] inpathList = { "pop.gmail.com", "SSL", "pop", "995", "0", "0" };
//		account.setInpathList(inpathList);
//
//		String uuid = "";
//
//		// 已删除文件夹保存副本
//		// account.setReceiveHost("pop3.live.com");
//		// account.setReceivePort("995");
//		// account.setReceiveTs("SSL");
//		// account.setReceiveProtocolType("pop");
//		// account.setName("shuang.weng@hotmail.com");
//		// account.setPassword("wszzwps.123");
//		// String[] inpathList = { "pop3.live.com", "SSL", "pop", "995", "0" };
//		// account.setInpathList(inpathList);
//		// 
//		// String uuid = "599B4242-FB80-11DF-9933-00215AD7B032";
//
//		// 彻底删除
//		// account.setReceiveHost("pop.qq.com");
//		// account.setReceivePort("110");
//		// account.setReceiveProtocolType("pop");
//		// account.setName("924881022@qq.com");
//		// account.setPassword("wszzwps.123");
//		// String[] inpathList = { "pop.qq.com", "", "pop", "110" };
//		// account.setInpathList(inpathList);
//		// 
//		// String uuid = "ZC1429Bh79MH9xSBesHC5b9Cidbg0b";
//
//		// 彻底删除
//		// account.setReceiveHost("imap.mail.yahoo.com");
//		// account.setReceivePort("993");
//		// account.setReceiveTs("SSL");
//		// account.setReceiveProtocolType("imap");
//		// account.setName("ss_102698@yahoo.com");
//		// account.setPassword("123456");
//		// String[] inpathList = { "imap.mail.yahoo.com", "SSL", "imap", "993"
//		// };
//		// account.setInpathList(inpathList);
//		// 
//		// String uuid = "";
//
//		// 移动到已删除文件夹
//		// account.setReceiveHost("admin-h1li7jsv2");
//		// account.setReceiveProtocolType("http");
//		// account.setName("shuangwen");
//		// account.setPassword("apple.123");
//		// 
//		// String uuid =
//		// "CQAAABYAAAC/97XzMW91ToObN/fvJGhhAF5sgtlS,AAAWAHNodWFuZ3dlbkBwdXNobWFpbC5jb20ARgAAAAAAiJtaeWeq1EekMlw6CfkGWAcAv/e18zFvdU6Dmzf37yRoYQAAAuZSKQAAv/e18zFvdU6Dmzf37yRoYQBebIKlbgAA";
//
//		IDProvide p = new EmailDeleteControl();
//		boolean b = p.dEmail(account, uuid);
//		System.out.println(b);
//	}
//
//}
