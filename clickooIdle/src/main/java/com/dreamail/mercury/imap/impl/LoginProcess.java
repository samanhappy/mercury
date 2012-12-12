package com.dreamail.mercury.imap.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.Start;
import com.dreamail.mercury.User;
import com.dreamail.mercury.constant.ImapConstant;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.service.AccountService;
import com.dreamail.mercury.imap.ImapProcessor;
import com.dreamail.mercury.imap.ImapSession;

public class LoginProcess extends AbstractProcess implements ImapProcessor {
	
	private static final Logger logger = LoggerFactory
	.getLogger(LoginProcess.class);
	
	private User user = null;

	private boolean isLogin = false;

	@Override
	public void parser(String command) throws Exception {
		// TODO Auto-generated method stub
		String[] str = command.split("[ ]+");
		if (command.toUpperCase().contains(ImapConstant.Login.LOGIN)) {
			// 以空格为分割点 解析有效的LOGIN命令
			
			// 将结果装入到User类
			user = new User();
			user.setUserName(str[2].replace("\"", ""));
			user.setPassword(str[3].replace("\"", ""));
			user.setSuffix((str[2].split("@"))[1]);
		}
		setSign(str[0]);
	}

	@Override
	public List<String> proces(String command, ImapSession session)throws Exception {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		// 判断命令是不是为CAPABILITY
		if (command.toUpperCase().contains(ImapConstant.Login.CAPABILITY) && !session.isLogin()) {
			list
					.add("* CAPABILITY IMAP4rev1 UNSELECT IDLE NAMESPACE QUOTA XLIST CHILDREN XYZZY SASL-IR AUTH=XOAUTH");
			list.add(sign+" OK Thats all she wrote! i18if8316966qcm.51");
			return list;
		} else if (command.toUpperCase().contains(ImapConstant.Login.CAPABILITY) && session.isLogin()) {
			list
					.add("* CAPABILITY IMAP4rev1 UNSELECT LITERAL+ IDLE NAMESPACE QUOTA ID XLIST CHILDREN X-GM-EXT-1 UIDPLUS COMPRESS=DEFLATE");
			list.add(sign+" OK Success");
			return list;
		}
		else if(command.toUpperCase().contains(ImapConstant.Login.DONE)){
			list.add("1 OK IDLE terminated (Success)");
			return list;
		}
		// 判断命令是不是为IDLE
		else if (command.toUpperCase().contains(ImapConstant.Login.IDLE)) {
			/**
			 * 应该在idle命令过后才加入全局session
			 */
			if(super.isLogin(session)){
				if (Start.session.containsKey(session.getUser().getUserName())) {
					Start.session.get(session.getUser().getUserName()).add(session);
				} else {
					CopyOnWriteArrayList<ImapSession> coWriteList = new CopyOnWriteArrayList<ImapSession>();
					coWriteList.add(session);
					Start.session.put(session.getUser().getUserName(), coWriteList);
				}
				list.add("+ idling");
			}else{
				list.add(sign+" BAD [CLIENTBUG] Invalid command or arguments");
			}
			final long id = session.getUser().getAid();
			final Channel c = session.getContext().getChannel();
			session.timer.schedule(
					new java.util.TimerTask() { public void run() {
						if(c.isConnected()){
							MessageDao dao = new MessageDao();
							long count = dao.getMessageCountByAID(id).getMsgCount();
							if(c.isConnected()){
								try{
									logger.info("login return message: "+count+" EXISTS");
									c.write("* "+count+" EXISTS"+"\r\n");
								}
								catch (Exception e) {
									logger.error("timer send exists err...",e);
									/**
									 * 这里可能要取消定时发送，需要设置重试次数
									 */
								}
							}else{
								logger.info("send EXISTS find channel is colsed...................");
								this.cancel();
							}
						}
					}},5*60*1000, 5*60*1000);
			return list;
		}
		// 判断命令是不是为LOGIN
		else if (command.toUpperCase().contains(ImapConstant.Login.LOGIN)) {
			AccountService service = new AccountService();
			long aid = service.addNewIdleAccount(user.getUserName(), user.getPassword());
			if(aid!=-1){
				// 讲验证成功的数据装入ImapSession类中，然后传如session中
				user.setAid(aid);
				session.setUser(user);
				list.add("* CAPABILITY IMAP4rev1 UNSELECT LITERAL+ IDLE NAMESPACE QUOTA ID XLIST CHILDREN X-GM-EXT-1 UIDPLUS COMPRESS=DEFLATE");
				list.add(sign+" OK " + user.getUserName()
						+ " authenticated (Success)");
				isLogin = true;
				session.setLogin(isLogin);
				return list;
			}else{
				user = null;
				list.add(sign+" NO Invalid credentials df5if1133779vdc.74");
				return list;
			}
		}
		list.add(sign+" BAD [CLIENTBUG] Invalid command or arguments");
		return list;
	}

	@Override
	public boolean tag(String command) {
		// TODO Auto-generated method stub
		// 判断获取的命令是否包LOGIN的命令符
		user = null;
		isLogin = false;
		if (command == null || "".equals(command.trim())) {
			return false;
		}
		String commandToUpper = command.toUpperCase();
		if (commandToUpper.contains(ImapConstant.Login.CAPABILITY)
				|| commandToUpper.contains(ImapConstant.Login.LOGIN)
				|| commandToUpper.contains(ImapConstant.Login.IDLE)
				|| commandToUpper.contains(ImapConstant.Login.DONE)) {
			return true;
		} else {
			return false;
		}
	}

}
