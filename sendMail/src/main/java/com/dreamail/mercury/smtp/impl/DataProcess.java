package com.dreamail.mercury.smtp.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.sendMail.sender.SmtpTransmitOperation;
import com.dreamail.mercury.smtp.SmtpProcessor;
import com.dreamail.mercury.smtp.SmtpSession;
import com.dreamail.mercury.smtp.utils.Constant;
import com.dreamail.mercury.smtp.utils.SmtpEmail;
import com.dreamail.mercury.smtp.utils.SmtpEmailBody;
import com.dreamail.mercury.smtp.utils.SmtpEmailattachment;
import com.dreamail.mercury.smtp.utils.Tools;
import com.dreamail.mercury.util.EmailUtils;

public class DataProcess extends AbstractProcess implements SmtpProcessor {

	private static final Logger logger = LoggerFactory
			.getLogger(DataProcess.class);

	private String commandSign;

	@Override
	public void parser(String command) throws Exception {
		if (command.contains("DATA")) {
			String[] commandParse = command.split(" ");
			commandSign = commandParse[0];
		}

	}

	@Override
	public List<String> proces(String command, SmtpSession ctx)
			throws Exception {
		List<String> list = new ArrayList<String>();
		if (commandSign == null || "".equals(commandSign)) {
			commandSign = command;
		}
		if (ctx.getSendState()) {
			if (command.trim().equalsIgnoreCase(".")) {
				logger.debug("recive Date");
				ctx.getCommandList().add(command);
				ctx.setSendState(false);
				boolean b = false;
				logger.info("==================check list: "
						+ ctx.getCommandList().size());
				SmtpEmail smtpEmail = parseCommandList(ctx.getCommandList(),
						ctx);
				logger.info("==================bodycosten:"
						+ smtpEmail.getBody().getData());
				logger.info("======================AddrString:"
						+ smtpEmail.getTo());
				ctx.getCommandList().clear();
				if (smtpEmail != null) {
					b = new SmtpTransmitOperation().createMsgAndSaveToEml(smtpEmail);
				}
				logger.debug("==========================checkSendMAil: " + b);
				if (b) {
					list.add("250 OK , completed");
				} else {
					list
							.add("554 Transaction failed : Cannot send message due to possible abuse;");
				}
			} else {
				ctx.getCommandList().add(command);
			}
		}

		if (commandSign.equalsIgnoreCase("DATA")) {
			ctx.setSendState(true);
			list.add("354 Start Mail. End with CRLF.CRLF");
		}

		return list;
	}

	@Override
	public boolean tag(String command, SmtpSession ctx) {
		if (command == null || "".equals(command.trim())) {
			return false;
		}
		String commandToUpper = command.toUpperCase();
		boolean state = false;
		if (ctx.getSendState()) {
			state = ctx.getSendState();
		}
		if (commandToUpper.contains("DATA") || state) {
			commandSign = null;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将邮件的内容从所在List解析出来，并返回一个邮件对象
	 * 
	 * @param commandList
	 * @return
	 */
	public SmtpEmail parseCommandList(List<String> commandList, SmtpSession ctx) {

		SmtpEmail sEmail = new SmtpEmail();
		boolean bodyState = false;
		Map<String, String> mapHeads = new HashMap<String, String>();
		String boundary = null;
		List<String> bodyList = new ArrayList<String>();
		List<String> attchList = new ArrayList<String>();
		List<SmtpEmailattachment> attchmentList = new ArrayList<SmtpEmailattachment>();
		// 1:是body的内容 2：是attachment
		int tag = 0;
		StringBuffer sbAdrr = new StringBuffer();
		for (String command : commandList) {
			if (command == null || "".equals(command)) {
				continue;
			}
			// 取出body的分界线，并加上一个body在取得状态bodyState
			if (command.contains("boundary=")) {
				boundary = StringUtils.substringBetween(command, "\"", "\"");
				bodyState = true;
				continue;
			}

			//
			if (bodyState) {
				// 判断分界线的子类，第一个为 body并标记为tag=1，第二个为附件，标记为tag=2
				if (command.equalsIgnoreCase("--" + boundary)) {

					if (bodyList != null && bodyList.size() > 0) {
						// System.out.println("body" + bodyList);
						// bodyList.clear();
					}
					if (attchList != null && attchList.size() > 0) {
						attchmentList.add(parseAttachment(attchList, ctx));
						attchList.clear();
					}
					tag += 1;
					continue;
				}
				// 当接收到结束符 ------*****-- 时，状态bodystate变为fasle
				if (command.equalsIgnoreCase("--" + boundary + "--")) {
					if (attchList != null && attchList.size() > 0) {
						// System.out.println("attch" + attchList);
						attchmentList.add(parseAttachment(attchList, ctx));
					}
					bodyState = false;
					tag = 0;// 标记返回0
					continue;
				}
				// 文本的内容放bodyList里
				if (tag == 1) {
					if (command != null && !"".equals(command)) {
						bodyList.add(command);
						continue;
					}

				}
				// 附件发到attchList里
				else if (tag > 1) {
					if (command != null && !"".equals(command)) {
						attchList.add(command);
						continue;
					}

				}

			}

			String adrrString = null;
			if (command.contains(":")) {
				String[] commandParse = command.split(":");
				if (commandParse[0].toUpperCase().trim().equalsIgnoreCase("TO")) {
					adrrString = StringUtils
							.substringBetween(command, "<", ">");
					if (adrrString == null || "".equals(adrrString)) {
						adrrString = commandParse[1].trim();
					}
					sbAdrr.append(adrrString);
					if (command.endsWith(",")) {
						ctx.setToIsList(true);
						if (!adrrString.contains(",")) {
							sbAdrr.append(",");
						}

					} else {
						mapHeads.put(commandParse[0].toUpperCase().trim(),
								sbAdrr.toString());
						sbAdrr = new StringBuffer();
						ctx.setToIsList(false);
					}

				} else if (commandParse[0].toUpperCase().trim()
						.equalsIgnoreCase("CC")) {
					adrrString = StringUtils
							.substringBetween(command, "<", ">");
					if (adrrString == null || "".equals(adrrString)) {
						adrrString = commandParse[1].trim();
					}
					sbAdrr.append(adrrString);
					if (command.endsWith(",")) {
						ctx.setCcMoreLine(true);
						if (!adrrString.contains(",")) {
							sbAdrr.append(",");
						}

					} else {
						mapHeads.put(commandParse[0].toUpperCase().trim(),
								sbAdrr.toString());
						sbAdrr = new StringBuffer();
						ctx.setCcMoreLine(false);
					}

				} else {
					mapHeads.put(commandParse[0].toUpperCase().trim(),
							commandParse[1]);
				}

			} else if (ctx.getToIsList()) {
				if (!command.endsWith(",")) {
					adrrString = StringUtils
							.substringBetween(command, "<", ">");
					if (adrrString == null || "".equals(adrrString)) {
						adrrString = command.trim();
					}

					ctx.setToIsList(false);
					sbAdrr.append(adrrString);
					logger.info("======================AddrString:"
							+ sbAdrr.toString());
					mapHeads.put("TO", sbAdrr.toString());
					sbAdrr = new StringBuffer();
				} else {
					adrrString = StringUtils
							.substringBetween(command, "<", ">");
					if (adrrString == null || "".equals(adrrString)) {
						adrrString = command.trim();
					}
					sbAdrr.append(adrrString);
					sbAdrr.append(",");
				}
			} else if (ctx.isCcMoreLine()) {
				if (!command.endsWith(",")) {
					adrrString = StringUtils
							.substringBetween(command, "<", ">");
					if (adrrString == null || "".equals(adrrString)) {
						adrrString = command.trim();
					}

					ctx.setCcMoreLine(false);
					sbAdrr.append(adrrString);
					logger.info("======================CCAddrString:"
							+ sbAdrr.toString());
					mapHeads.put("CC", sbAdrr.toString());
					sbAdrr = new StringBuffer();
				} else {
					adrrString = StringUtils
							.substringBetween(command, "<", ">");
					if (adrrString == null || "".equals(adrrString)) {
						adrrString = command.trim();
					}
					sbAdrr.append(adrrString);
					sbAdrr.append(",");
				}
			} else {
				if (command.equals(".")) {
					bodyList.add("\n.\n");
					;
				} else {
					bodyList.add(command);
				}

			}

		}
		if (mapHeads.containsKey(Constant.Data.BODY_CONTENT_TYPE)) {
			bodyList.add(Constant.Data.BODY_CONTENT_TYPE + ":" + "0");
		}
		if (mapHeads.containsKey(Constant.Data.BODY_CONTENT_TRANSFER_TYPE)) {
			bodyList.add(Constant.Data.BODY_CONTENT_TRANSFER_TYPE + ":"
					+ mapHeads.get(Constant.Data.BODY_CONTENT_TRANSFER_TYPE));
		}
		logger.info("-----------------CC:"
				+ mapHeads.get(Constant.Data.MAIL_CC));
		if (mapHeads.get(Constant.Data.MAIL_CC) != null) {
			sEmail.setCc(mapHeads.get(Constant.Data.MAIL_CC));
		}

		sEmail.setSendTime(mapHeads.get(Constant.Data.MAIL_SEND_TIME));
		sEmail.setFrom(mapHeads.get(Constant.Data.MAIL_FROM));
		String to = mapHeads.get(Constant.Data.MAIL_TO);
		sEmail.setTo(to);
		String subjectBase64 = mapHeads.get(Constant.Data.MAIL_SUBJECT).split(
				"[?]")[3].trim();
		String subject = changeBase64ToString(subjectBase64);
		sEmail.setSubject(subject);
		logger.info("===========================check bodyList "
				+ bodyList.size());
		logger.info("===========================check attchList "
				+ attchmentList.size());
		sEmail.setBody(parseBody(bodyList));
		sEmail.setAttach(attchmentList);
		sEmail.setUser(ctx.getUser());
		sEmail.setMessageId(StringUtils.substringBetween(mapHeads
				.get(Constant.Data.MAIL_MESSAGE_ID), "<", ">"));
		return sEmail;
	}

	/**
	 * 将邮件的附件从所在List中解析出来,并且返回一个附件对象
	 * 
	 * @param attchList
	 * @return
	 */
	public SmtpEmailattachment parseAttachment(List<String> attchList,
			SmtpSession ctx) {
		SmtpEmailattachment seAttchment = new SmtpEmailattachment();
		Map<String, String> mapAttchHeads = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		String nameKey = "";
		// String charString = "";
		for (String attchCommand : attchList) {
			if (attchCommand != null && !"".equals(attchCommand)) {
				// attchCommand.replaceAll("\t", " ");
				// charString =
				// ((Character)(attchCommand.charAt(0))).toString();
				// boolean b = charString.equals("\t");
				if (attchCommand.contains(":")) {
					String[] attchCommandParse = attchCommand.split(":");
					mapAttchHeads.put(
							attchCommandParse[0].toUpperCase().trim(),
							attchCommandParse[1].trim());
				} else if ((((Character) (attchCommand.charAt(0))).toString())
						.equals("\t")
						|| ctx.isCommandMore()) {
					String[] attchCommandParse = null;

					if (attchCommand.contains("?")) {
						attchCommandParse = attchCommand.split("=");
						logger.info("-----------------attchCommand:"
								+ attchCommand);
						if (attchCommand.startsWith("\t")) {
							nameKey = attchCommandParse[0].toUpperCase().trim();
							if (attchCommand.endsWith("\"")) {
								ctx.getSb()
										.append(attchCommand.split("[?]")[3]);
								// strNameValue.append(StringUtils.substringBetween(
								// attchCommand, "\"", "\"").split("[?]")[3]);
								logger.info("-----------------strNameValue:"
										+ ctx.getSb());
								mapAttchHeads.put(nameKey,
										changeBase64ToString(ctx.getSb()
												.toString()));
								ctx.setSb(new StringBuffer());
							} else {
								ctx.getSb()
										.append(attchCommand.split("[?]")[3]);
								ctx.setCommandMore(true);
							}

						} else if (attchCommand.endsWith("\"")) {
							ctx.setCommandMore(false);
							ctx.getSb().append(attchCommand.split("[?]")[3]);
							logger.info("-----------------strNameVale:"
									+ ctx.getSb());
							mapAttchHeads.put(nameKey, changeBase64ToString(ctx
									.getSb().toString()));
							ctx.setSb(new StringBuffer());
						} else {
							ctx.getSb().append(attchCommand.split("[?]")[3]);
						}

					} else {
						attchCommandParse = attchCommand.split("=");
						String fileName = "";
						if (attchCommand.contains("\"")) {
							fileName = StringUtils.substringBetween(
									attchCommandParse[1].trim(), "\"", "\"");
						} else {
							fileName = attchCommandParse[1].trim();
						}
						logger.info("-----------------------fileName:"
								+ fileName);
						mapAttchHeads.put(attchCommandParse[0].toUpperCase()
								.trim(), fileName);

					}

				} else {
					sb.append(attchCommand);
				}
			}

		}
		byte[] b = EmailUtils.base64TochangeByte(sb.toString());
		seAttchment.setSize(b.length);
		seAttchment.setBody(sb.toString());
		logger.debug("------------------" + mapAttchHeads.toString());
		logger.info("-------------------map:"
				+ mapAttchHeads.get(Constant.Data.ATTCH_FILENAME));
		if (mapAttchHeads.get(Constant.Data.ATTCH_FILENAME) == null
				|| "".equals(mapAttchHeads.get(Constant.Data.ATTCH_FILENAME))) {
			mapAttchHeads.put(Constant.Data.ATTCH_FILENAME, "FILENAMENULL.txt");
		}
		seAttchment.setName(mapAttchHeads.get(Constant.Data.ATTCH_FILENAME));
		seAttchment
				.setType(mapAttchHeads.get(Constant.Data.ATTCH_CONTENT_TYPE));
		// seAttchment.setType("txt");
		logger.debug("------------------AttchName:"
				+ mapAttchHeads.get(Constant.Data.ATTCH_FILENAME));
		logger.debug("------------------AttchName:" + seAttchment.getName());
		return seAttchment;
	}

	/**
	 * 将邮件的body从所在的List中解析出来，并且返回一个body对象
	 * 
	 * @param bodyList
	 * @return
	 */
	public SmtpEmailBody parseBody(List<String> bodyList) {

		SmtpEmailBody seBody = new SmtpEmailBody();
		Map<String, String> mapBodyHeads = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		for (String bodyCommand : bodyList) {
			if (bodyCommand.contains(":")) {
				String[] bodyStrings = bodyCommand.split(":");
				mapBodyHeads.put(bodyStrings[0].toUpperCase().trim(),
						bodyStrings[1].trim());
			} else {
				if (!bodyCommand.contains("This is a MIME Message")) {
					sb.append(bodyCommand.trim());
				}

			}
		}
		String data = "";
		logger.info("======================transferType:"
				+ mapBodyHeads.get(Constant.Data.BODY_CONTENT_TRANSFER_TYPE));
		logger.info("======================contentType:"
				+ mapBodyHeads.get(Constant.Data.BODY_CONTENT_TYPE));
		if (mapBodyHeads.get(Constant.Data.BODY_CONTENT_TRANSFER_TYPE).trim()
				.equalsIgnoreCase("quoted-printable")) {
			data = Tools.changeQPToString(sb.toString());
		} else {
			data = sb.toString();
		}
		seBody.setData(data);
		logger.info("-----------------------------data:" + seBody.getData());
		seBody.setDatatype(mapBodyHeads.get(Constant.Data.BODY_CONTENT_TYPE));
		seBody.setSize(sb.length());
		return seBody;

	}

}