package com.dreamail.mercury.imap.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dreamail.mercury.constant.ImapConstant;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.imap.ImapProcessor;
import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.pojo.Clickoo_message;

public class StatusProcess extends AbstractProcess implements ImapProcessor{
	private List<String> conditions;
	@Override
	public void parser(String command) throws Exception {
		String[] str = command.split("\\(");
		String[] cond = str[1].split("\\)")[0].split("[ ]+");
		this.conditions = new ArrayList<String>();
		for(int i=0;i<cond.length;i++){
			conditions.add(cond[i]);
		}
		setSign(str[0].split("[ ]+")[0]);
	}

	@Override
	public List<String> proces(String command, ImapSession ctx)
			throws Exception {
		List<String> list = new ArrayList<String>();
		if(super.isLogin(ctx)){
			MessageDao dao = new MessageDao();
			long exists = 0;
			Clickoo_message message = dao.getMaxUUIDByAID(ctx
					.getUser().getAid());
			if(message!=null){
				exists = message.getMsgCount();
//				exists = exists - (ctx.getExists()==null ||(ctx.getExists().get(ctx.getFolder())==null || 
//						ctx.getExists().get(ctx.getFolder()).getDelUUID()==null) ? 0:ctx.getExists().get(ctx.getFolder()).getDelUUID().size());
			}
			StringBuffer sb = new StringBuffer(ImapConstant.Status.REPLY_STATUS);
			boolean addBlank = false;
			if(command.toUpperCase().contains(ImapConstant.Status.STATUS_MESSAGES)){
				sb.append(ImapConstant.Status.STATUS_MESSAGES);
				sb.append(ImapConstant.Status.CONTENT_BLANK + exists);
				addBlank = true;
			}
			if(command.toUpperCase().contains(ImapConstant.Status.STATUS_UIDNEXT)){
				if(addBlank)
					sb.append(ImapConstant.Status.CONTENT_BLANK);
				sb.append(ImapConstant.Status.STATUS_UIDNEXT);
				sb.append(ImapConstant.Status.CONTENT_BLANK + message.getMaxUUid());
				addBlank = true;
			}
			if(command.toUpperCase().contains(ImapConstant.Status.STATUS_UNSEEN)){
				if(addBlank)
					sb.append(ImapConstant.Status.CONTENT_BLANK);
				sb.append(ImapConstant.Status.STATUS_UNSEEN);
				//此处未实现
				sb.append(ImapConstant.Status.CONTENT_BLANK + 
						getUseenNun(new MessageService().getContentByAccountId(ctx.getUser().getAid(), ImapConstant.Search.FLAGS)));
				addBlank = true;
			}
			if(command.toUpperCase().contains(ImapConstant.Status.STATUS_UIDVALIDITY)){
				if(addBlank)
					sb.append(ImapConstant.Status.CONTENT_BLANK);
				sb.append(ImapConstant.Status.STATUS_UIDVALIDITY);
				sb.append(ImapConstant.Status.CONTENT_BLANK + 1);
				addBlank = true;
			}
			sb.append(")");
			list.add(sb.toString());
			list.add(sign+ImapConstant.Status.REPLY_COMPLETED);
		} else {
			list.add(sign+" BAD [CLIENTBUG] Invalid command or arguments");
		}
		return list;
	}

	@Override
	public boolean tag(String command) {
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(command) && command.toLowerCase().contains("status"))
			return true;
		return false;
	}
	
}
