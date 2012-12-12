package com.dreamail.mercury.imap.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dreamail.mercury.constant.ImapConstant;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.imap.ImapProcessor;
import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.imap.domain.FolderData;
import com.dreamail.mercury.pojo.Clickoo_message;

public class ExamineProcess extends AbstractProcess implements ImapProcessor {
	private String folder;

	@Override
	public void parser(String command) throws Exception {
		String[] commands = command.split("[ ]+");
		if (commands.length < 2) {
			folder = null;
		} else if (commands[2].toUpperCase().contains("INBOX")) {
			folder = "INBOX";
		}
		setSign(commands[0]);
	}

	@Override
	public boolean tag(String command) {
		folder = null;
		if (StringUtils.isNotBlank(command)
				&& command.toUpperCase().contains("EXAMINE")) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> proces(String command, ImapSession session)
			throws Exception {
		List<String> list = new ArrayList<String>();
		if (super.isLogin(session)) {
			if (folder != null && "inbox".equalsIgnoreCase(folder)) {
				MessageDao dao = new MessageDao();
				long exists = 0;
				long nextid = 1;
				Clickoo_message message = dao.getMaxUUIDByAID(session.getUser()
						.getAid());
				if (message != null) {
					exists = message.getMsgCount();
					nextid = message.getMaxUUid();
				}
				if(session.getExists()==null || session.getExists().get(session.getFolder())==null){
					Map<String,FolderData> map = session.getExists();
					if(map==null){
						map = new HashMap<String,FolderData>();
					}
					FolderData f = new FolderData();
					f.setMailNum(exists);
					f.setOpenWay(ImapConstant.Folder.FOLDER_READ_WRITE);
					map.put(folder, f);
					session.setExists(map);
				}
				list.add(ImapConstant.Folder.REPLY_FOLDER + " "
						+ ImapConstant.Folder.FOLDER_FLAGS_ALL);
				list.add(ImapConstant.Folder.REPLY_FOLDER
						+ " OK [PERMANENTFLAGS ()]");
				list.add(ImapConstant.Folder.REPLY_FOLDER
						+ " OK [UIDVALIDITY 639285512]");
				list.add(ImapConstant.Folder.REPLY_FOLDER + " " + exists
						+ " EXISTS");
				list.add(ImapConstant.Folder.REPLY_FOLDER + " "
						+ ImapConstant.Folder.FOLDER_RECENT);
				list.add(ImapConstant.Folder.REPLY_FOLDER + " OK [UIDNEXT "
						+ nextid + "]");
				list.add(sign + " OK [READ-ONLY] INBOX selected. (Success)");
				session.setFolder(folder);
			} else {
				list.add("* BAD [CLIENTBUG] Invalid command or arguments");
			}
		} else {
			list.add("* BAD [CLIENTBUG] Invalid command or arguments");
		}
		return list;

	}
}
