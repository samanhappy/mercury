package com.dreamail.mercury.imap.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.constant.ImapConstant;
import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.imap.domain.FetchData;
import com.dreamail.mercury.pojo.Clickoo_imap_message;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.store.impl.HandleStorePathImpl;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.StreamUtil;

public class FetchEmailDetail extends AbstractFetchEmail{
	private static final Logger logger = LoggerFactory.getLogger(FetchEmailDetail.class);
	@Override
	public List<String> getProtocol(List<String> protocolList, ImapSession session,
			FetchData fetchData, Clickoo_message message) {
		if(fetchData.getTag().contains(ImapConstant.Fetch.FETCH_UID)){
			List<Clickoo_imap_message> msglist = session.getMessageList();
			fetchData.setId(getSeqNum(msglist, message.getUuid()));
		}
		setSign(fetchData.getId());
		String[] bodypeek = fetchData.getContent().replace("(", "").replace(")", "").split("[ ]+");
//		logger.info("--------------"+bodypeek);
		String num = bodypeek[0].replace("BODY.PEEK[", "").replace("]", "");
		if (num != null && !"".equals(num)) {
			if (num.contains("<")) {
				num = num.split("<")[0];
			}
			logger.info("get part [" + num + "] from email!");
			StringBuffer protocol = new StringBuffer();
			protocol.append("* " + sign + " FETCH ("+ImapConstant.Fetch.FETCH_UID + " "+ message.getUuid() + " BODY["+num+"]");
			if(bodypeek[0].contains("<")){
				protocol.append("<"+StringUtils.substringBetween(bodypeek[0], "<", ">").split("[.]")[0]+">");
			}
			if (message.getAttachList() != null	&& message.getAttachList().size() > 0) {
				//此时有附件
				if (num.startsWith("1")) {
					getBody(message, protocolList, protocol, fetchData,	bodypeek);
				} else {
					getAttachement(message, protocolList, protocol, fetchData, num, bodypeek);
				}
			} else {
				getBody(message, protocolList, protocol, fetchData,	bodypeek);
			}
		} else {
			logger.error("this part [" + num+ "] of email does not exist!");
		}
		return protocolList;
	}
	/**
	 * 获取邮件正文
	 * @param message
	 * @param protocolList
	 * @param protocol
	 * @param fetchData
	 */
	private void getBody(Clickoo_message message,List<String> protocolList,StringBuffer protocol,FetchData fetchData,String[] bodypeek){
		logger.info("peek body from email!");
		try {
			if(message.getMessageData().getSize()==0){
				logger.info("peek the body of the email is null!");
				protocol.append(" {2}");
				protocolList.add(protocol.toString());
				protocolList.add("\r");
			}else{
//				logger.info("peek the body detail of the email is :"+message.getMessageData().getData());
				String str = EmailUtils.changeByteToBase64(message.getMessageData().getData(true).getBytes("UTF-8"));
				if(!bodypeek[0].contains("<")){
					//此时为阿三手机
					protocol.append(" {"+str.length()+"}");
					protocolList.add(protocol.toString());
					protocolList.add(str);
				}else{
					//此时为 E63手机
					int start = Integer.parseInt(StringUtils.substringBetween(bodypeek[0], "<", ">").split("[.]")[0]);
					int end = Integer.parseInt(StringUtils.substringBetween(bodypeek[0], "<", ">").split("[.]")[1]);
					logger.info("start : "+start+" end : "+end + " str.length : "+ str.length());
					if(str.length()<end){
						end = str.length();
					}
					protocol.append(" {"+String.valueOf(end)+"}");
					protocolList.add(protocol.toString());
					protocolList.add(" "+str.subSequence(start, start+end)+bodypeek[1].replace(ImapConstant.Fetch.FETCH_BODY_PEEK, ImapConstant.Fetch.FETCH_BODY)+"{78}");
				}
				protocolList.add("Content-Type: text/plain; charset=UTF-8");
				protocolList.add("Content-Transfer-Encoding: base64");
				protocolList.add("\r");
			}
			protocolList.add(")");
		} catch (UnsupportedEncodingException e) {
			logger.error("body peek error!");
		}
	}
	/**
	 * 获取附件
	 * @param message
	 * @param protocolList
	 * @param protocol
	 * @param fetchData
	 * @param num
	 */
	private void getAttachement(Clickoo_message message,List<String> protocolList,StringBuffer protocol,FetchData fetchData,String num,String[] bodypeek){
		int n = Integer.parseInt(num);
		List<Clickoo_message_attachment> cmaList = message.getAttachList();
		if (cmaList != null && cmaList.size() > 0) {
			Clickoo_message_attachment attachment = cmaList.get(n-2);
			if(attachment!=null){
				HandleStorePathImpl handle = new HandleStorePathImpl();
				String filePath = handle.getPathById(attachment);
				byte[] body = null;
//				logger.info("The filePath of the attachement-----------"+filePath);
				if (new File(filePath).exists()) {
					body = StreamUtil.readFile(filePath);
					if (body!=null) {
						String str = EmailUtils.changeByteToBase64(body).trim();
						if(bodypeek[0].contains("<")){
							int start = Integer.parseInt(StringUtils.substringBetween(bodypeek[0], "<", ">").split("[.]")[0]);
							int end = Integer.parseInt(StringUtils.substringBetween(bodypeek[0], "<", ">").split("[.]")[1]);
							logger.info("start : "+start+" end : "+end + " str.length : "+ str.length());
							if(str.length() < end){
								end = str.length();
							}
							protocol.append(" {"+String.valueOf(end)+"}");
							protocolList.add(protocol.toString());
							protocolList.add(str.substring(start, start+end)+")");
						}else{
							protocol.append(" {"+str.length()+"}");
							protocolList.add(protocol.toString());
							protocolList.add(str+")");
						}
					}
				}else{
					logger.info("this file does not exist!");
				}
			}
		}
	}
}
