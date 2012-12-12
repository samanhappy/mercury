package com.dreamail.mercury.imap.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dreamail.mercury.constant.ImapConstant;
import com.dreamail.mercury.imap.ImapProcessor;
import com.dreamail.mercury.imap.ImapSession;

public class ListProcess extends AbstractProcess implements ImapProcessor {

	private String tag;

	@Override
	public void parser(String command) throws Exception {
		String commands[] = command.split(ImapConstant.List.CONTENT_BLANK);
		if (commands.length > 3) {
			if (StringUtils.isBlank(commands[2].replace("\"", " ").trim())
					&& StringUtils.containsAny(commands[3].replace("\"", " ")
							.trim(), ImapConstant.List.LIST_COMMAND_LASTCHAR)) {
				tag = "1";
			} else if (StringUtils.isBlank(commands[2].replace("\"", " ")
					.trim())
					&& StringUtils.contains(commands[3].toUpperCase(),
							ImapConstant.List.MAILBOX_INBOX)) {
				tag = "2";
			}
		} else {
			tag = null;
		}
		setSign(commands[0]);
	}

	@Override
	public boolean tag(String command) {
		tag = null;
		if (StringUtils.isNotBlank(command)
				&& command.toUpperCase().contains(
						ImapConstant.List.LIST_COMMAND_NAME)) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> proces(String command, ImapSession session)
			throws Exception {
		List<String> list = new ArrayList<String>();
		if (super.isLogin(session) && tag != null) {
			if ("1".equalsIgnoreCase(tag)) {
				// 命令：A3 LIST "" "%";
				// 返回：* LIST (\HasNoChildren) "/" "Inbox" A3 OK LIST completed
				list.add(ImapConstant.List.REPLY_LIST
						+ ImapConstant.List.CONTENT_BLANK + "(\\"
						+ ImapConstant.List.LIST_HASNOCHILDREN + ")"
						+ ImapConstant.List.CONTENT_BLANK + "\"/\""
						+ ImapConstant.List.CONTENT_BLANK + "\""
						+ ImapConstant.List.MAILBOX_INBOX + "\"");
				list.add(proces_ok());
			} else if ("2".equalsIgnoreCase(tag)) {
				// 命令：A3 LIST "" "Inbox"
				// 返回：A3 OK LIST completed
				list.add(ImapConstant.List.REPLY_LIST
						+ ImapConstant.List.CONTENT_BLANK + "(\\"
						+ ImapConstant.List.LIST_HASNOCHILDREN + ")"
						+ ImapConstant.List.CONTENT_BLANK + "\"/\""
						+ ImapConstant.List.CONTENT_BLANK + "\""
						+ ImapConstant.List.MAILBOX_INBOX + "\"");
				list.add(proces_ok());
			} else {
				// 命令：其他
				// 返回:A3 BAD [CLIENTBUG] LIST Command unknown or arguments
				// invalid
				list.add(proces_fail());
			}
		} else {
			list.add(proces_fail());
		}
		return list;
	}

	public String proces_ok() {
		return sign + ImapConstant.List.CONTENT_BLANK
				+ ImapConstant.List.REPLY_LIST_COMPLETE;
	}

	public String proces_fail() {
		return "*" + ImapConstant.List.CONTENT_BLANK
				+ ImapConstant.List.REPLY_LIST_FAIL;
	}
}
