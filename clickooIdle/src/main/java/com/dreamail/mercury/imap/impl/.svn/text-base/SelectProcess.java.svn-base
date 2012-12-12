package com.dreamail.mercury.imap.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dreamail.mercury.constant.ImapConstant;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.imap.ImapProcessor;
import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.imap.domain.FolderData;
import com.dreamail.mercury.pojo.Clickoo_message;

public class SelectProcess extends AbstractProcess implements ImapProcessor {

	private String folder;

	@Override
	public void parser(String command) throws Exception {
		String commandToUpper = command.toUpperCase();
		String[] c = command.split("[ ]+");
		if (commandToUpper.contains("SELECT")) {
			if (c.length < 2) {
				folder = null;
			} else if (c[2].toUpperCase().contains("INBOX")) {
				folder = "inbox";
			}
		}
		setSign(c[0]);
	}

	@Override
	public boolean tag(String command) {
		folder = null;
		if (command == null || "".equals(command.trim())) {
			return false;
		}
		String commandToUpper = command.toUpperCase();
		if (commandToUpper.contains("SELECT")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<String> proces(String command, ImapSession session)
			throws Exception {
		List<String> list = new ArrayList<String>();
		if(super.isLogin(session)){
				if(folder!=null && "inbox".equalsIgnoreCase(folder)){
					MessageDao dao = new MessageDao();
					long exists = 0;
					long maxUUid = 1;
					Clickoo_message message = dao.getMaxUUIDByAID(session
							.getUser().getAid());
					if(message!=null){
						exists = message.getMsgCount();
						maxUUid = message.getMaxUUid();
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
					list.add("*"+" FLAGS (\\Answered \\Flagged \\Draft \\Deleted \\Seen)");
					list.add("*"+" OK [PERMANENTFLAGS (\\Answered \\Flagged \\Draft \\Deleted \\Seen \\*)]");
					list.add("*"+" OK [UIDVALIDITY 639285512]");
					list.add("*"+" "+exists+" EXISTS");
					list.add("*"+" 0 RECENT");
					list.add("*"+" OK [UIDNEXT "+maxUUid+"]");
					list.add(sign+" OK [READ-WRITE] inbox selected. (Success)");
//					A3 OK [READ-WRITE] inbox selected. (Success)
//					* OK [UIDNEXT 371]
//					list.add(sign+" OK [PERMANENTFLAGS (\\Deleted \\Seen \\*)] Limited");
					session.setFolder(folder);
			}
			// else if (tag.equalsIgnoreCase("1")) {
			// FolderData f = session.getExists().get(session.getFolder());
			// if (f == null) {
			// f = new FolderData();
			// }
			// f.setOpenWay(openWay);
			// }
		} else {
			list.add(sign+" BAD [CLIENTBUG] Invalid command or arguments");
		}
		return list;
	}

}
