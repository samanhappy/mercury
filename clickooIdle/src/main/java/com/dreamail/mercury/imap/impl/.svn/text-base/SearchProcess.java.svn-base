package com.dreamail.mercury.imap.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dreamail.mercury.constant.ImapConstant;
import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.imap.ImapProcessor;
import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.imap.domain.SearchData;
import com.dreamail.mercury.pojo.Clickoo_imap_message;

public class SearchProcess extends AbstractProcess implements ImapProcessor{
	private SearchData searchData;
	@Override
	public void parser(String command) throws Exception{
		String commands[] = command.split("[ ]+");
		this.searchData = new SearchData();
		setSign(commands[0]);
		String condition = null;
		int i = 0;
		
		if(commands[1].toUpperCase().equals("UID")){
			//命令格式A1 UID SEARCH 1
			searchData.setTag(ImapConstant.Search.COMMAND_UID_SEARCH);
			condition = commands[3];
			i = 3 + 1;
		}else{
			//命令格式A1 SEARCH 1
			searchData.setTag(ImapConstant.Search.COMMAND_SEARCH);
			condition = commands[2];
			i = 2 + 1;
		}
		if("uid".equalsIgnoreCase(condition)){
			searchData.setCondition("UID");
			searchData.setRange(commands[i]);
			return;
		}
		if(command.toUpperCase().contains(ImapConstant.Search.FLAGS_NOT_UNDELETED))
			searchData.setFlags(ImapConstant.Search.FLAGS_NOT_UNDELETED);
		
		if(ImapConstant.Search.CONDITION_ALL.equalsIgnoreCase(condition)){
			searchData.setRange(commands[i-1]);
			searchData.setCondition(ImapConstant.Search.CONDITION_ALL);
		}else if(condition.matches("^[1-9][0-9]*$") || condition.matches("^[1-9][0-9]*[:][1-9][0-9]*$")){
			searchData.setRange(commands[i-1]);
			searchData.setCondition(ImapConstant.Search.CONDITION_PART);
		}else if(ImapConstant.Search.CONDITION_BEFORE.equalsIgnoreCase(condition)){
			searchData.setCondition(ImapConstant.Search.CONDITION_BEFORE);
		}else if(ImapConstant.Search.CONDITION_SINCE.equalsIgnoreCase(condition)){
			searchData.setCondition(ImapConstant.Search.CONDITION_SINCE);
		}else if(ImapConstant.Search.CONDITION_ON.equalsIgnoreCase(condition)){
			searchData.setCondition(ImapConstant.Search.CONDITION_ON);
		}else if(ImapConstant.Search.CONDITION_SUBJECT.equalsIgnoreCase(condition)){
			searchData.setCondition(ImapConstant.Search.CONDITION_SUBJECT);
		}else if(ImapConstant.Search.CONDITION_FROM.equalsIgnoreCase(condition)){
			searchData.setCondition(ImapConstant.Search.CONDITION_FROM);
		}else if(ImapConstant.Search.CONDITION_TO.equalsIgnoreCase(condition)){
			searchData.setCondition(ImapConstant.Search.CONDITION_TO);
		}else if(ImapConstant.Search.CONDITION_CC.equalsIgnoreCase(condition)){
			searchData.setCondition(ImapConstant.Search.CONDITION_CC);
		}else if(ImapConstant.Search.CONDITION_BCC.equalsIgnoreCase(condition)){
			searchData.setCondition(ImapConstant.Search.CONDITION_BCC);
		}else if(ImapConstant.Search.CONDITION_BODY.equalsIgnoreCase(condition)){
			searchData.setCondition(ImapConstant.Search.CONDITION_BODY);
		}else if(ImapConstant.Search.CONDITION_TEXT.equalsIgnoreCase(condition)){
			searchData.setCondition(ImapConstant.Search.CONDITION_TEXT);
		}
		if(!ImapConstant.Search.CONDITION_ALL.equals(searchData.getCondition()) && !ImapConstant.Search.CONDITION_PART.equals(searchData.getCondition())){
			searchData.setRange(commands[i+1]);
			searchData.setContent(commands[i]);
		}
		
	}
	

	@Override
	public boolean tag(String command) {
		if(StringUtils.isNotBlank(command) && command.toLowerCase().contains("search"))
			return true;
		return false;
	}



	@Override
	public List<String> proces(String command, ImapSession ctx) throws Exception{
		List<String> protocolList = new ArrayList<String>();
		if(super.isLogin(ctx)){
			
			StringBuffer protocolContent = new StringBuffer(ImapConstant.Search.REPLY_SEARCH);
			long aid = ctx.getUser().getAid();
			String numReg = "^[1-9][0-9]*$";
			String numsReg = "^[1-9][0-9]*[:][1-9][0-9]*$";
			String tag = searchData.getTag();
			String range = searchData.getRange();
//			String sign = searchData.getSign();
			List<Clickoo_imap_message> msgList = null;
			MessageService service = new MessageService();
			if(ImapConstant.Search.CONDITION_ALL.equals(searchData.getCondition()) || ImapConstant.Search.CONDITION_PART.equals(searchData.getCondition())){
				if(ImapConstant.Search.FLAGS_NOT_UNDELETED.equals(searchData.getFlags())){
					msgList = service.getContentByAccountId(aid,ImapConstant.Search.FLAGS);
				}else{
					msgList = ctx.getMessageList();
				}
			}else{
				msgList = service.getContentByAccountId(aid,ImapConstant.Search.CONDITION_SUBJECT);
			}
			int size = msgList.size();
			boolean uidSearch = ImapConstant.Search.COMMAND_UID_SEARCH.equals(tag);
			
			int start = 0;
			int end = msgList.size();
			if(ImapConstant.Search.CONTENT_ALL.equalsIgnoreCase(range)){
			}else if(range.matches(numReg)){
				//查出指定序号的对应message的id
				int serial = Integer.parseInt(range);
				if(serial>size)
					serial = size;
				start = serial -1;
				end = serial;
			}else if(range.matches(numsReg)){
				//查出message表从  : 的所有id
				start = Integer.parseInt(range.split(":")[0]);
				end = Integer.parseInt(range.split(":")[1]);
				if(end>size) 
					end = size;
				start = start-1;
			}
			
			List<Integer> idList = getUidList(msgList,start,end,uidSearch);
			for(int i=0;i<idList.size();i++){
				protocolContent.append(ImapConstant.Search.CONTENT_BLANK + idList.get(i));
			}
		
			protocolList.add(protocolContent.toString());
			protocolList.add(sign + (uidSearch ? ImapConstant.Search.REPLY_UID_SEARCH_COMPLETE : ImapConstant.Search.REPLY_SEARCH_COMPLETE));
			}else{
				protocolList.add(sign +" BAD [CLIENTBUG] Invalid command or arguments");
			}
		return protocolList;
	}

	public SearchData getSearchData() {
		return searchData;
	}

	public void setSearchData(SearchData searchData) {
		this.searchData = searchData;
	}
	
	private List<Integer> getUidList(List<Clickoo_imap_message> msgList,int start,int end,boolean uidSearch) throws ParseException{
		List<Integer> idList = new ArrayList<Integer>();
		String condition = searchData.getCondition();
		String content = searchData.getContent();
		for(int i=start;i<end;i++){
			if(ImapConstant.Search.CONDITION_ALL.equals(condition) || ImapConstant.Search.CONDITION_PART.equals(condition)){
				if(ImapConstant.Search.FLAGS_NOT_UNDELETED.equals(searchData.getFlags())&&((Integer.parseInt(msgList.get(i).getContent())&8)!=0)){
					idList.add(uidSearch ? Integer.parseInt(msgList.get(i).getUid()) : i+1);
				}else if(searchData.getFlags()==null){
					idList.add(uidSearch ? Integer.parseInt(msgList.get(i).getUid()) : i+1);
				}
			}else if("UID".equalsIgnoreCase(searchData.getCondition())){
				String range = searchData.getRange();
				String numReg = "^[1-9][0-9]*$";
				String numsReg = "^[1-9][0-9]*[:][1-9][0-9]*$";
				if(range.matches(numReg) && range.equals(msgList.get(i).getUid())){
					idList.add(uidSearch ? Integer.parseInt(msgList.get(i).getUid()) : i+1);
				}else if(range.matches(numsReg)){
					String[] regs = range.split(":");
					for(int j=Integer.parseInt(regs[0]);j<=Integer.parseInt(regs[1]);j++){
						if(String.valueOf(j).equals(msgList.get(i).getUid()))
							idList.add(uidSearch ? Integer.parseInt(msgList.get(i).getUid()) : i+1);
					}
				}else if(range.contains(",")){
					String[] regs = range.split(",");
					for(int j=0;j<regs.length;j++){
						if(regs[j].equals(msgList.get(i).getUid()))
							idList.add(uidSearch ? Integer.parseInt(msgList.get(i).getUid()) : i+1);
					}
				}
			}
		
			/*else if(ImapConstant.Search.CONDITION_BEFORE.equals(condition)){
				Date sendTime = msgList.get(i).getSendtime();
				Date d1 = sdf.parse(content);
				String d = sdf.format(sendTime);
				Date d2 =  sdf.parse(d);
				if(d2.before(d1))
					idList.add(uidSearch ? msgList.get(i).getId() : i+1);
			}else if(ImapConstant.Search.CONDITION_SINCE.equalsIgnoreCase(condition)){
				Date sendTime = msgList.get(i).getSendtime();
				Date d1 = sdf.parse(content);
				String d = sdf.format(sendTime);
				Date d2 =  sdf.parse(d);
				if(d2.after(d1))
					idList.add(uidSearch ? msgList.get(i).getId() : i+1);
			}else if(ImapConstant.Search.CONDITION_ON.equalsIgnoreCase(condition)){
				Date sendTime = msgList.get(i).getSendtime();
				Date d1 = sdf.parse(content);
				String d = sdf.format(sendTime);
				Date d2 =  sdf.parse(d);
				if(d2.equals(d1))
					idList.add(uidSearch ? msgList.get(i).getId() : i+1);
			}*/
			else if(ImapConstant.Search.CONDITION_SUBJECT.equalsIgnoreCase(condition)){
				String subject = msgList.get(i).getContent();
				if(StringUtils.isNotBlank(subject) && subject.contains(content))
					idList.add(uidSearch ? Integer.parseInt(msgList.get(i).getUid()) : i+1);
			}
			/*else if(ImapConstant.Search.CONDITION_FROM.equalsIgnoreCase(condition)){
				String from = JSONObject.fromObject(msgList.get(i).getTitle()).getString("from");
				if(StringUtils.isNotBlank(from) && from.contains(content))
					idList.add(uidSearch ? msgList.get(i).getId() : i+1);
			}else if(ImapConstant.Search.CONDITION_TO.equalsIgnoreCase(condition)){
				String to = JSONObject.fromObject(msgList.get(i).getTitle()).getString("to");
				if(StringUtils.isNotBlank(to) && to.contains(content))
					idList.add(uidSearch ? msgList.get(i).getId() : i+1);
			}else if(ImapConstant.Search.CONDITION_CC.equalsIgnoreCase(condition)){
				String cc = JSONObject.fromObject(msgList.get(i).getTitle()).getString("cc");
				if(StringUtils.isNotBlank(cc) && cc.contains(content))
					idList.add(uidSearch ? msgList.get(i).getId() : i+1);
			}else if(ImapConstant.Search.CONDITION_BCC.equalsIgnoreCase(condition)){
				String bcc = JSONObject.fromObject(msgList.get(i).getTitle()).getString("bcc");
				if(StringUtils.isNotBlank(bcc) && bcc.contains(content))
					idList.add(uidSearch ? msgList.get(i).getId() : i+1);
			}*/
//			else if(ImapConstant.Search.CONDITION_BODY.equalsIgnoreCase(condition)){
//				searchData.setCondition(ImapConstant.Search.CONDITION_BODY);
//			}else if(ImapConstant.Search.CONDITION_TEXT.equalsIgnoreCase(condition)){
//				searchData.setCondition(ImapConstant.Search.CONDITION_TEXT);
//			}
		}
		Collections.sort(idList);
		return idList;
	}
	
}
