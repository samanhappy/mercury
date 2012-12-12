package com.dreamail.mercury.smtp.impl;

import java.util.ArrayList;
import java.util.List;

import com.dreamail.mercury.smtp.SmtpProcessor;
import com.dreamail.mercury.smtp.SmtpSession;

public class QuitProcess implements SmtpProcessor{



	@Override
	public void parser(String command) throws Exception {
		
	}

	@Override
	public List<String> proces(String command, SmtpSession ctx)
			throws Exception {
		List<String> list = new ArrayList<String>();
		if(command.equalsIgnoreCase("QUIT")){
			new SmtpSession().cleanSession(ctx);
			list.add("221 Service Closing transmission");
		}
		return list;
	}

	@Override
	public boolean tag(String command,SmtpSession ctx) {
		if (command == null || "".equals(command.trim())) {
			return false;
		}
		String commandToUpper = command.toUpperCase();
		if(commandToUpper.contains("QUIT")){
			return true;
		}else{
			return false;
		}
	}

}
