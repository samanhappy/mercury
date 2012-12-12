package com.dreamail.mercury.imap.impl;

import java.io.File;
import java.util.List;

import org.codehaus.xfire.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.constant.ImapConstant;
import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.imap.domain.EmailUser;
import com.dreamail.mercury.imap.domain.FetchData;
import com.dreamail.mercury.pojo.Clickoo_imap_message;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.store.impl.HandleStorePathImpl;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.StreamUtil;

public class FetchEmailStructure extends AbstractFetchEmail{
	private static final Logger logger = LoggerFactory.getLogger(FetchEmailStructure.class);
	@Override
	public List<String> getProtocol(List<String> protocolList, ImapSession session,
			FetchData fetchData, Clickoo_message message) {
		if(fetchData.getTag().contains(ImapConstant.Fetch.FETCH_UID)){
			List<Clickoo_imap_message> msglist = session.getMessageList();
			fetchData.setId(getSeqNum(msglist, message.getUuid()));
		}
		setSign(fetchData.getId());
		StringBuffer protocol = new StringBuffer();
		protocol.append("* " + sign+ " FETCH ("+ImapConstant.Fetch.FETCH_UID + " "+ message.getUuid() + " ");
		if (fetchData.getContent().contains(ImapConstant.Fetch.FETCH_RFC822_SIZE)) {
			getRFCSize(message, protocol);
		}
		if (fetchData.getContent().contains(ImapConstant.Fetch.FETCH_FLAGS)) {
			getFlags(message, protocol);
		}
		if (fetchData.getContent().contains(ImapConstant.Fetch.FETCH_ENVELOPE)) {
			getEnvelope(message, protocol);
		}
		if (fetchData.getContent().contains(ImapConstant.Fetch.FETCH_BODY_STRUCTURE)) {
			getBodyStructure(message, protocol);
			
		}
		if (fetchData.getContent().contains(ImapConstant.Fetch.FETCH_BODY_PEEK)) {
			getBodyPeek(message, protocol,protocolList,fetchData);
			protocolList.add(")");
		}else{
			protocolList.add(protocol.toString()+")");
		}
		return protocolList;
 	}
	/**
	 * 收件人，抄送人，发件人，密送人名字协议解析
	 * @param user
	 * @param protocol
	 */
	private void getEmailUserProtocol(String user,StringBuffer protocol){
		if(!"".equals(user.trim())){
			String[] emailusers = user.split(",");
			if ( emailusers.length > 0) {
				for (String string : emailusers) {
					EmailUser emailUser = getEmailUser(string);
					if(emailUser.getName().equals("")){
						protocol.append("((NIL NIL \""+ emailUser.getEmailname()+ "\" \""+ emailUser.getEmailtype()+ "\")) ");
					}else{
						protocol.append("((\"" + emailUser.getName() + "\" NIL \""+ emailUser.getEmailname()+ "\" \""+ emailUser.getEmailtype()+ "\")) ");
					}
				}
			}
		}
	}
	/**
	 * 获取邮件RFC Size
	 * @param message
	 * @param protocol
	 */
	private void getRFCSize(Clickoo_message message,StringBuffer protocol){
	}
	/**
	 * 获取邮件flag
	 * @param message
	 * @param protocol
	 */
	private void getFlags(Clickoo_message message,StringBuffer protocol){
	}
	/**
	 * 获取邮件ENVELOPE
	 * @param message
	 * @param protocol
	 */
	private void getEnvelope(Clickoo_message message,StringBuffer protocol){
		protocol.append("ENVELOPE (\""+getTimeByDate(message.getSendtime())+"\" \""+message.getSubject()+"\" ");
		//发送人
		getEmailUserProtocol(message.getEmailFrom(), protocol);
		getEmailUserProtocol(message.getEmailFrom(), protocol);
		getEmailUserProtocol(message.getEmailFrom(), protocol);
		//收件人
		getEmailUserProtocol(message.getEmailTo(), protocol);
		//抄送人
		getEmailUserProtocol(message.getCc(), protocol);
		//密送人
		getEmailUserProtocol(message.getBcc(), protocol);
		protocol.append("NIL NIL NIL \"<1b6bcc3.26a8.12eff8d32c4.Coremail.archermind163@163.com>\") ");
	}

	/**
	 * 获取邮件BodyStructure
	 * @param message
	 * @param protocol
	 */
	private void getBodyStructure(Clickoo_message message,StringBuffer protocol){
		List<Clickoo_message_attachment> cmaList = message.getAttachList();
		if (cmaList != null && cmaList.size() > 0) {
			protocol.append("BODYSTRUCTURE (((\"TEXT\" \"PLAIN\" (\"CHARSET\" \"UTF-8\") NIL NIL \"BASE64\" 3 1 NIL NIL NIL)");// 此处base64未知
		}else{
			protocol.append("BODYSTRUCTURE ((\"TEXT\" \"PLAIN\" (\"CHARSET\" \"UTF-8\") NIL NIL \"BASE64\" 3 1 NIL NIL NIL)");// 此处base64未知
		}
		protocol.append("(\"TEXT\" \"HTML\" (\"CHARSET\" \"UTF-8\") NIL NIL \"BASE64\" 7 1 NIL NIL NIL) ");
		protocol.append("\"ALTERNATIVE\" (\"BOUNDARY\" \"001636c92b0a025a06049fe9bc64\") NIL NIL)");
		if (cmaList != null && cmaList.size() > 0) {
			logger.info(cmaList.size()+"------------cmaList.size----------");
			StringBuffer sb = new StringBuffer();
			for (Clickoo_message_attachment cma : cmaList) {
				String contenttype = null;
				if (contenttype != null && !"".equals(contenttype.trim())) {
					String[] str = contenttype.split(";");
					if (str[1].contains("name=")) {
						String name = str[1].split("name=")[1].replace("\"", "");
//						查询邮件附件的实际大小
						HandleStorePathImpl handle = new HandleStorePathImpl();
						String filePath = handle.getPathById(cma);
						byte[] body = null;
						if (new File(filePath).exists()) {
							body = StreamUtil.readFile(filePath);
							if (body!=null) {
								String string = EmailUtils.changeByteToBase64(body).trim();
								sb.append("(\""+str[0].split("/")[0]+"\" \""+str[0].split("/")[1]+"\" (\"CHARSET\" \"UTF-8\" \"NAME\" \""+name+"\") NIL NIL \"BASE64\" "+string.length()+" 2 NIL " +
										"(\"ATTACHMENT\" (\"FILENAME\" \""+name+"\")) NIL) ") ;
							}
						}else{
							logger.error("The filePath of the email attachement is not exist!");
						}
					} 
				} else {
					logger.error("contenttype is null!");
				}
			}
			sb.append("\"MIXED\" (\"BOUNDARY\" \"bcaec52160fb8afe0204a04ea13f\") NIL NIL) ");
			protocol.append(sb);
		}else{
			logger.info("attachement list is null");
		}
		protocol.append(" ");
	}
	/**
	 * 获取邮件BODY.PEEK
	 * @param message
	 * @param protocol
	 * @param fetchData
	 */
	private void getBodyPeek(Clickoo_message message,StringBuffer protocol,List<String> protocolList,FetchData fetchData){
		String fields = fetchData.getContent().split("HEADER.FIELDS")[1].replace("(", "").replace(")", "").replace("]", "").trim();
		logger.info("HEADER.FIELDS contains : "+fields);
		String s = "Date: "+getTimeByDate(message.getSendtime())+"From: "
					+message.getEmailFrom().split("<")[1].replace(">","").trim()
					+"Subject: "+"=?UTF-8?B?" + Base64.encode(message.getSubject().getBytes()) + "?="
					+"X-Priority: 3";
		protocol.append("BODY[HEADER.FIELDS("+fields+")]{"+(s.length()+8)+"} ");
		protocolList.add(protocol.toString());
		returnBodystructureForE63(protocolList,fetchData.getContent(),message);
	}
}
