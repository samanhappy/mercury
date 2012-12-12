package com.dreamail.mercury.imap.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.Start;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.dao.UARelationDao;
import com.dreamail.mercury.imap.ImapProcessor;
import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.memcached.IdleCacheManager;
import com.dreamail.mercury.pojo.Clickoo_imap_message;

public class OtherProcess extends AbstractProcess implements ImapProcessor{
	
	private static final Logger logger = LoggerFactory
	.getLogger(OtherProcess.class);
	
	@Override
	public void parser(String command)throws Exception {
		// TODO Auto-generated method stub
		String[] c = command.split("[ ]+");
		setSign(c[0]);
	}

	

	@Override
	public boolean tag(String command) {
		if(command==null || "".equals(command.trim())){
			return false;
		}
		String commandToUpper = command.toUpperCase();
		if(commandToUpper.contains("NOOP") ||
				commandToUpper.contains("LOGOUT")||
				commandToUpper.contains("CLOSE")){
			return true;
		}
		return false;
	}



	@Override
	public List<String> proces(String command, ImapSession session)throws Exception {
		String commandToUpper = command.toUpperCase();
		List<String> list = new ArrayList<String>();
		if(commandToUpper.contains("NOOP")){
			String mailNumCache = IdleCacheManager.getMessageCount(session.getUser().getAid());
			if(mailNumCache!=null){
				if(session.getExists().get(session.getFolder())!=null &&
						!(session.getExists().get(session.getFolder()).getMailNum()+"").equals(mailNumCache)){
					list.add("* "+ mailNumCache +" EXISTS");
				}
			}else{
				long mailNum = new MessageDao().getMaxUUIDByAID(session
						.getUser().getAid()).getMsgCount();
				list.add("* "+ mailNum +" EXISTS");
			}
			list.add(sign+" OK SUCCESS");
		}
		/**
		 * 需要发送消息给其他服务器和在本机找到其他手机的连接
		 * 告诉手机有邮件被删除
		 */
		else if(commandToUpper.contains("LOGOUT")){
			if(super.isLogin(session)){
				/**
				 * 此处删除session中含有删除标记的邮件(当前文件夹),并清空session
				 */
				deleteMessage(session);
				new Clean().cleanSession(session);
			}
			list.add("* BYE LOGOUT Requested");
			list.add(sign+" OK LOGOUT completed");
		}
		/**
		 * 需要发送消息给其他服务器和在本机找到其他手机的连接
		 * 告诉手机有邮件被删除
		 */
		else if(commandToUpper.contains("CLOSE")){
			/**
			 * 此处删除session中含有删除标记的邮件(当前文件夹),并清空session中的当前folder
			 */
			if(super.isLogin(session) && session.getFolder()!=null && !"".equals(session.getFolder())){
				deleteMessage(session);
				session.getExists().remove(session.getFolder());
				list.add(sign+" OK Returned to authenticated state. (Success)");
			}else{
				list.add(sign+" BAD [CLIENTBUG] Invalid command or arguments");
			}
		}
		return list;
	}
	
	
	public void deleteMessage(ImapSession session){
		if(session.getExists()==null || session.getExists().get(session.getFolder())==null)
			return;
		List<String> delUUID = session.getExists().get(session.getFolder()).getDelUUID();
		try{
			if(delUUID!=null && delUUID.size()!=0){
				UARelationDao uaDao = new UARelationDao();
				MessageDao dao = new MessageDao();
				//查询被删除的邮件序号
				if(uaDao.isOtherAccount(session.getUser().getAid())){
					//逻辑删除，+flag
					//tag 增加是1，0是删除
					super.setFlags(session.getUser().getAid(), delUUID, 8, 1);
				}else{
					//物理删除
					dao.deleteMessagesByuuid(delUUID);
				}
				long threadId = session.getThreadId();
				CopyOnWriteArrayList<ImapSession> sessionList = Start.session.get(session.getUser().getUserName());
				boolean flag = false;
				logger.info(""+session.getUser().getUserName());
				logger.info(""+sessionList);
				if(sessionList!=null){
					for(ImapSession s : sessionList){
						if(s.getThreadId() != threadId){
							flag = true;
							break;
						}
					}
				}
				if(flag){
					List<Clickoo_imap_message> list = session.getMessageList();
					long count = dao.getMaxUUIDByAID(session.getUser().getAid()).getMsgCount();
					for(String uuid : delUUID){
						for(ImapSession s : sessionList){
							if(s.getThreadId() != threadId){
								String index = super.getSeqNum(list, uuid);
								s.getContext().getChannel().write("* "+index+" EXPUNGE"+"\r\n");
							}
						}
						for(ImapSession s : sessionList){
							if(s.getThreadId() != threadId){
								s.getContext().getChannel().write("* "+count+" EXISTS"+"\r\n");
							}
						}
					}
				}
//				AccountService accountService = new AccountService();
//				Clickoo_mail_account account = accountService.getAccountByAid(session.getUser().getAid());
//				IDProvide pro = new DEmailImapImpl();
				
			}else{
				logger.info("delete mail fail...delUUID is null");
			}
		}catch (Exception e) {
			logger.error("delete msg err...",e);
		}
		finally{
			session.getExists().remove(session.getFolder());
		}
	}
	
}
