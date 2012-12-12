package com.dreamail.mercury.imap.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.constant.ImapConstant;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.imap.ImapProcessor;
import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.imap.domain.FetchData;
import com.dreamail.mercury.pojo.Clickoo_imap_message;
import com.dreamail.mercury.pojo.Clickoo_message;

public class FetchProcess extends AbstractProcess implements ImapProcessor {
	private static final Logger logger = LoggerFactory
			.getLogger(FetchProcess.class);

	private FetchData fetchData;

	public FetchData getFetchData() {
		return fetchData;
	}

	public void setFetchData(FetchData fetchData) {
		this.fetchData = fetchData;
	}

	@Override
	public boolean tag(String command) {
		fetchData = null;
		if (command == null || "".equals(command.trim())) {
			return false;
		}
		String commandToUpper = command.toUpperCase();
		if (commandToUpper.contains(ImapConstant.Fetch.FETCH_COMMAND_NAME)) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public void parser(String command) throws Exception {
		String[] str = command.split("[ ]+");
		this.fetchData = new FetchData();
		String x = null;
		if (ImapConstant.Fetch.FETCH_UID.equalsIgnoreCase(str[1])) {
			//如果包含UID则参数是UID
			fetchData.setUid(str[3]);
			fetchData.setTag(str[1] + " " + str[2]);
			x = str[0] + " " + str[1] + " " + str[2] + " " + str[3] + " ";
		} else if (ImapConstant.Fetch.FETCH_COMMAND_NAME.equalsIgnoreCase(str[1])) {
			//此处是序号
			fetchData.setId(str[2]);
			fetchData.setTag(str[1]);
			x = str[0] + " " + str[1] + " " + str[2] + " ";
		} else {
			logger.error("command is error :" + command);
			return;
		}
		fetchData.setSign(str[0]);
		if(x.contains("*")){
			x = x.replace("*", "[*]");
		}
//		logger.info("x is ------------" + x);
		fetchData.setContent(command.split(x)[1]);
		logger.info("------------parser-------------"+fetchData.toString());
	}
	@Override
	public List<String> proces(String command, ImapSession session)throws Exception {
		if (!isLogin(session)) {
			logger.info("user doesn't login!");
			return null;
		}
		List<String> list = new ArrayList<String>();
		FetchProcessFactory factory = new FetchProcessFactory(fetchData);
		setSign(fetchData.getSign());
		//判断传来的参数类型是UID还是ID
		if(fetchData.getTag().toUpperCase().contains(ImapConstant.Fetch.FETCH_UID)){
			if(fetchData.getUid().matches(numReg)){
				logger.info("method [1]");
				Clickoo_message message = new MessageDao().getMsgByUuidAid(fetchData.getUid(), String.valueOf(session.getUser().getAid()));
				if(message==null){
					logger.error("the message is null!");
					return null;
				}else{
					logger.info("the message :["+message+"]");
					factory.getFactory().getProtocol(list, session, fetchData, message);
				}
			}else if(fetchData.getUid().matches(numsReg)){
				logger.info("method [2]");
				FetchData temp = new FetchData();
				temp.setTag(fetchData.getTag());
				temp.setContent(fetchData.getContent());
				temp.setSign(fetchData.getSign());
				List<Clickoo_imap_message> msglist = session.getMessageList();
				int start = Integer.parseInt(fetchData.getUid().split(":")[0]);
				int end = Integer.parseInt(fetchData.getUid().split(":")[1]);
				if (msglist != null) {
					for (Clickoo_imap_message object : msglist) {
						if (Integer.parseInt(object.getUid())>=start && Integer.parseInt(object.getUid())<=end) {
							Clickoo_message message = new MessageDao().getMsgByUuidAid(object.getUid(), String.valueOf(session.getUser().getAid()));
							logger.info("the message :["+message+"]");
							temp.setUid(object.getUid());
							factory.getFactory().getProtocol(list, session, temp, message);
						}
					}
				}
			}else if(fetchData.getUid().matches(allnumsReg)){
				//E71手机
				List<Clickoo_imap_message> msglist = session.getMessageList();
				FetchData temp = new FetchData();
				temp.setTag(fetchData.getTag());
				temp.setContent(fetchData.getContent());
				temp.setSign(fetchData.getSign());
				if (msglist != null) {
					for (Clickoo_imap_message object : msglist) {
						Clickoo_message message = new MessageDao().getMsgByUuidAid(object.getUid(), String.valueOf(session.getUser().getAid()));
						logger.info("the message :["+message+"]");
						temp.setUid(object.getUid());
						factory.getFactory().getProtocol(list, session, temp, message);
					}
				}
			}else if(fetchData.getUid().contains(",")){
				logger.info("method [3]");
				String[] str = fetchData.getUid().split(",");
				FetchData temp = new FetchData();
				temp.setTag(fetchData.getTag());
				temp.setContent(fetchData.getContent());
				temp.setSign(fetchData.getSign());
				if(str!=null&&str.length>0){
					for (int i = 0; i < str.length; i++) {
						if(str[i].matches(numReg)){
							Clickoo_message message = new MessageDao().getMsgByUuidAid(String.valueOf(Integer.parseInt(str[i])), String.valueOf(session.getUser().getAid()));
							logger.info("the message :["+message+"]");
							temp.setId(str[i]);
							if(message==null){
								logger.error("message is null!");
							}else{
								temp.setUid(message.getUuid());
								factory.getFactory().getProtocol(list, session, temp, message);
							}
						}else{
							logger.error("uid ["+str[i]+"] is error");
						}
					}
				}
			}
		}else{
			List<Clickoo_imap_message> msglist = session.getMessageList();
			//id转UID
			if(fetchData.getId().matches(numReg)){
				logger.info("method [4]");
				Clickoo_imap_message cim = msglist.get(Integer.parseInt(fetchData.getId())-1);
				Clickoo_message message = new MessageDao().getMsgByUuidAid(cim.getUid(), String.valueOf(session.getUser().getAid()));
				if(message==null){
					logger.error("the message is null!");
					return null;
				}else{
					logger.info("the message :["+message+"]");
					factory.getFactory().getProtocol(list, session, fetchData, message);
				}
			}else if(fetchData.getId().matches(numsReg)){
				logger.info("method [5]");
			}else if(fetchData.getId().contains(",")){
				logger.info("method [6]");
				String[] str = fetchData.getId().split(",");
				FetchData temp = new FetchData();
				temp.setTag(fetchData.getTag());
				temp.setContent(fetchData.getContent());
				temp.setSign(fetchData.getSign());
				if (str != null && str.length > 0) {
					for (int i = 0; i < str.length; i++) {
						if(str[i].matches(numReg)){
							logger.info("msglist get : "+(Integer.parseInt(str[i])-1)+"-------------------size:"+msglist.size());
							Clickoo_imap_message cim = msglist.get(Integer.parseInt(str[i])-1);
							Clickoo_message message = new MessageDao().getMsgByUuidAid(cim.getUid(), String.valueOf(session.getUser().getAid()));
							temp.setUid(cim.getUid());
							temp.setId(str[i]);
							if(message==null){
								logger.error("message is null!");
							}else{
								factory.getFactory().getProtocol(list, session, temp, message);
							}
						}else{
							logger.error("uid ["+str[i]+"] is error");
						}
					}
				}
			}
		}
		list.add(sign + " OK Success");
		return list;
	}
}
