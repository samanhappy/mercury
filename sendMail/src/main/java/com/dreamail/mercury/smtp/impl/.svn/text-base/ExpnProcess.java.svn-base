package com.dreamail.mercury.smtp.impl;

import java.util.ArrayList;
import java.util.List;

import com.dreamail.mercury.smtp.SmtpProcessor;
import com.dreamail.mercury.smtp.SmtpSession;

public class ExpnProcess implements SmtpProcessor{

	private String commandSign;
	
	@Override
	public void parser(String command) throws Exception {
		// TODO Auto-generated method stub
		String[] commandParse = command.split(" ");
		commandSign = commandParse[0];
	}

	@Override
	public List<String> proces(String command, SmtpSession ctx)
			throws Exception {
		
		List<String> list = new ArrayList<String>();
		if(commandSign.equalsIgnoreCase("EXPAN")){
			list.add("");
		}
		return list;
	}

	@Override
	public boolean tag(String command,SmtpSession ctx) {

		if (command == null || "".equals(command.trim())) {
			return false;
		}
		String commandToUpper = command.toUpperCase();
		if(commandToUpper.contains("EXPN")){
			commandSign = null;
			return true;
		}else{
			return false;
		}
	}

}
