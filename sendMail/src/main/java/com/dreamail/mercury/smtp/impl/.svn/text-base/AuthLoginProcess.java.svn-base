package com.dreamail.mercury.smtp.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.sendMail.jms.Start;
import com.dreamail.mercury.smtp.SmtpProcessor;
import com.dreamail.mercury.smtp.SmtpSession;
import com.dreamail.mercury.smtp.utils.User;

public class AuthLoginProcess extends AbstractProcess implements SmtpProcessor{
	private static final Logger logger = LoggerFactory
	.getLogger(AuthLoginProcess.class);
	private String commandSign;
	private String parameter;

	@Override
	public void parser(String command) throws Exception {

		String[] commandParse = command.split(" ");
		if(command.toUpperCase().contains("AUTH LOGIN")||command.toUpperCase().contains("AUTH PLAIN")){
			commandSign = commandParse[0]+" "+commandParse[1];
		}
		if(commandParse.length>2){
			parameter = commandParse[2];
		}else{
			parameter = null;
		}
		
	}

	@Override
	public List<String> proces(String command, SmtpSession ctx)
			throws Exception {
		
		List<String> list = new ArrayList<String>();
		String userNameUTF8 = null;
		if(commandSign!= null&&commandSign.equalsIgnoreCase("AUTH PLAIN")){
			//验证用户账号，成功则传入缓存
			list.add("235 OK, go ahead");
			User user = new User();
			logger.debug("=============================="+parameter);
			userNameUTF8 = changeBase64ToString(parameter);
			logger.debug("=============================="+userNameUTF8);
			if(userNameUTF8.contains("@yahoo.com")){
				String[] type = userNameUTF8.split("@yahoo.com");
				user.setUserName(type[0].trim()+"@yahoo.com");
				user.setPassword(type[1].trim());
				}
			user.setUserName(userNameUTF8);
			ctx.setUser(user);
			if(Start.session.containsKey(ctx.getUser().getUserName())){
				Start.session.get(ctx.getUser().getUserName()).add(ctx);
			}else{
				CopyOnWriteArrayList<SmtpSession> cowList =  new CopyOnWriteArrayList<SmtpSession>();
				Start.session.put(ctx.getUser().getUserName(),cowList);
			}
		}
		else if(command.equalsIgnoreCase("AUTH LOGIN")){
			ctx.setAuthState(true);
			ctx.setCount(1);
			list.add("334 VXNlcm5hbWU6");
			logger.debug("============================"+ctx.getAuthState());
		}
		else if(ctx.getAuthState()){
			if(ctx.getCount()==0){
				String passwordUTF8 = changeBase64ToString(command.trim());
				ctx.getUser().setPassword(passwordUTF8);
				logger.info("----------------------username:"+ctx.getUser().getUserName());
				logger.info("----------------------password:"+ctx.getUser().getPassword());
				logger.info("----------------------houzui:"+ctx.getUser().getSuffix());
				list.add("235 OK, go ahead");
				ctx.setAuthState(false);
				logger.info("============================sendStae:"+ctx.getAuthState());
			}else if(ctx.getCount()==1){
				User user = new User();
				userNameUTF8 = changeBase64ToString(command.trim());
				user.setUserName(userNameUTF8);
				ctx.setUser(user);
				if(Start.session.containsKey(ctx.getUser().getUserName())){
					Start.session.get(ctx.getUser().getUserName()).add(ctx);
				}else{
					CopyOnWriteArrayList<SmtpSession> cowList =  new CopyOnWriteArrayList<SmtpSession>();
					Start.session.put(ctx.getUser().getUserName(),cowList);
				}
				ctx.setCount(0);
				list.add("334 UGFzc3dvcmQ6");
			}
		}
		else{
			list.add("501 malformed auth input (#5.5.4)");
		}
		
		return list;	
	}

	@Override
	public boolean tag(String command, SmtpSession ctx) {

		if (command == null || "".equals(command.trim())) {
			return false;
		}
		String commandToUpper = command.toUpperCase();
		if(commandToUpper.contains("AUTH LOGIN")||commandToUpper.contains("AUTH PLAIN")||ctx.getAuthState()){
			commandSign = null;
			parameter = null;
			return true;
		}else{
			return false;
		}
	}

}
