package com.dreamail.mercury.netty.test;

import java.util.List;

import org.junit.Test;

import com.dreamail.mercury.User;
import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.imap.impl.ExamineProcess;
import com.dreamail.mercury.imap.impl.ListProcess;

public class ProcessTest {

	@Test
	public void listProcess() throws Exception {
		String command = "8 LIST \"\" \"%\"";
		ImapSession ctx = new ImapSession();
		User user = new User();
		user.setUserName("ss_102698@yahoo.com");
		user.setPassword("000000");
		user.setSuffix("yahoo.com");
		ctx.setUser(user);

		ListProcess lp = new ListProcess();
		lp.tag(command);
		lp.parser(command);
		List<String> strList = lp.proces(command, ctx);
		for (String str : strList) {
			System.out.println(str);
		}
	}

	@Test
	public void examineProcess() throws Exception {
		String command = "8 examine \"inbox\" ";
		ImapSession ctx = new ImapSession();
		User user = new User();
		user.setUserName("ss_102698@yahoo.com");
		user.setPassword("000000");
		user.setSuffix("yahoo.com");
		ctx.setUser(user);

		ExamineProcess lp = new ExamineProcess();
		lp.tag(command);
		lp.parser(command);
		List<String> strList = lp.proces(command, ctx);
		for (String str : strList) {
			System.out.println(str);
		}
	}

//	public static void main(String[] args) {
//		String command = "8 LIST \"\" \"%\"";
//		String commands[] = command.split(ImapConstant.List.CONTENT_BLANK);
//		String commandLast = commands[3].replace("\"", " ").trim();
//		boolean isContains = StringUtils.containsOnly(commandLast, "%")
//				|| StringUtils.containsOnly(commandLast, "*");
//		System.out.println(StringUtils.containsAny(commandLast,
//				ImapConstant.List.LIST_COMMAND_LASTCHAR));
//		System.out.println(isContains);
//	}
}
