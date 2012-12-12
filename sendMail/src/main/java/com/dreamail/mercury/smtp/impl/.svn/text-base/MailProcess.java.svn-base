package com.dreamail.mercury.smtp.impl;

import java.util.ArrayList;
import java.util.List;

import com.dreamail.mercury.smtp.SmtpProcessor;
import com.dreamail.mercury.smtp.SmtpSession;

public class MailProcess implements SmtpProcessor{
	
	private String commandSign;
	@Override
	public void parser(String command) throws Exception {
		String[] commandParse = command.split(" ");
		commandSign = commandParse[0];
		
	}

	@Override
	public List<String> proces(String command, SmtpSession ctx)
			throws Exception {
		List<String> list = new ArrayList<String>();
		if(commandSign.equalsIgnoreCase("MAIL")){
			list.add("250 OK , completed");
		}
		return list;
	}

	@Override
	public boolean tag(String command,SmtpSession ctx) {
		if (command == null || "".equals(command.trim())) {
			return false;
		}
		String commandToUpper = command.toUpperCase();
		if(commandToUpper.contains("MAIL")){
			commandSign = null;
			return true;
		}else{
			return false;
		}
	}

}
