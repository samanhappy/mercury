package com.dreamail.mercury.imap.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dreamail.mercury.constant.ImapConstant;
import com.dreamail.mercury.dal.dao.MessageDao;
import com.dreamail.mercury.imap.ImapProcessor;
import com.dreamail.mercury.imap.ImapSession;
import com.dreamail.mercury.pojo.Clickoo_message;

public class StoreProcess extends AbstractProcess implements ImapProcessor {

	private String commandSign = null;
	private String commandTag = null; // 输入的命令符

	private long mailTotal;// 邮件的总数

	private int commandMailIdMin; // 命令中邮件的起始ID

	private int commandMailIdMax; // 命令中邮件的结束ID

	private String commandMailIdWhole; // 命令中邮件整个ID

	private String commandMailProper = null; // 需要给邮件添加的标记

	private int commandMailOp = 0; // 需要给邮件添加的操作 0：删除一个标记 1：标记

	@Override
	public void parser(String command) throws Exception {
		// TODO Auto-generated method stub

		// 解析STORE字符串并放入数组，数组的长度为5
		String[] str = command.split("[ ]+");
		setSign(str[0]);
		commandSign =  sign;
		// 将STORE命令符存入commandTag
		commandTag = str[2];

		// 邮件的整个ID赋值给commandMailIdWhole
		commandMailIdWhole = str[3];
		if (commandMailIdWhole.contains(":")) {
			// 最小邮件ID赋值给commandMailIdMin
			commandMailIdMin = Integer.parseInt(str[3].split(":")[0]);
			// 最小邮件大赋值给commandMailIdMax
			commandMailIdMax = Integer.parseInt(str[3].split(":")[1]);
		} else {
			commandMailIdMin = Integer.parseInt(commandMailIdWhole);
			commandMailIdMax = commandMailIdMin;
		}

		// 取出命令里的需要操作的标记，赋值给commandMailProper
		commandMailProper = StringUtils.substringBetween(str[5], "(", ")");
		// 判断是添加标记还是删除标记
		if (str[4].toUpperCase().contains("-FLAGS")) {
			commandMailOp = 0;
		} else if (str[4].toUpperCase().contains("+FLAGS")) {
			commandMailOp = 1;
		}

	}

	@Override
	public boolean tag(String command) {
		// TODO Auto-generated method stub
		// 判断获取Store命令
		if (command == null || "".equals(command.trim())) {
			return false;
		}
		String commandToUpper = command.toUpperCase();
		if (commandToUpper.contains("STORE") && command.split(" ").length == 6) {
			// 全局属性清空
			commandSign = null;
			commandTag = null;
			commandMailIdWhole = null;
			mailTotal = 0;
			commandMailIdMin = 0;
			commandMailIdMax = 0;

			commandMailProper = null;

			commandMailOp = 0;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<String> proces(String command, ImapSession ctx)
			throws Exception {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		List<String> uuidCount = new ArrayList<String>();
		int commandPropToInt = parseCommand(commandMailProper);
		if (isLogin(ctx) && ctx.getFolder() != null) {
			MessageDao md = new MessageDao();
			StoreProcess sp = new StoreProcess();
			// 取出数据库中未含有\Deleted标记邮件的总数
			Clickoo_message cm = md.getMessageCountByAID(ctx.getUser().getAid());
			if (cm != null) {
				mailTotal = cm.getMsgCount();
			}
			// 如果打开方式是read-only且是添加删除标记的时候 ，Failure
			if ((ctx.getExists().get(ctx.getFolder())).getOpenWay()
					.equalsIgnoreCase(ImapConstant.Folder.FOLDER_READ_ONLY)
					&& (sp.parseCommand(commandMailProper)&8) != 0) {
				list.add(commandSign
						+ " NO STORE attempt on READ-ONLY folder (Failure)");
				return list;
			}

			// 修改标记
			
			else if (commandTag.equalsIgnoreCase("STORE")&&commandPropToInt!=0) {

				for (int uuid = commandMailIdMin; uuid < commandMailIdMax + 1; uuid++) {
					String seqNum = getSeqNum(ctx.getMessageList(), ((Integer)uuid).toString());
					if (uuid == 0) {
						list
								.add(commandSign+" BAD [CLIENTBUG] Invalid command or arguments");
						return list;
					}
					uuidCount.add(((Integer) uuid).toString());
					if (commandPropToInt == 8
							&& commandMailOp == 1) {
						if(ctx.getExists().get(ctx.getFolder()).getDelUUID()==null){
							List<String> deleteUuidCount = new ArrayList<String>();
							ctx.getExists().get(ctx.getFolder()).setDelUUID(deleteUuidCount);
						}
						ctx.getExists().get(ctx.getFolder()).getDelUUID().add(
								((Integer) uuid).toString());
						long mailCount = mailTotal
								- ctx.getExists().get(ctx.getFolder())
										.getDelUUID().size();
						list.add("* " + seqNum + " EXPUNGE");
						list.add("*" + " " + mailCount + " EXISTS");
					}

				}
				if (commandPropToInt != 8
						|| commandMailOp == 0) {
					HashMap<String, Integer> hm = setFlags(ctx.getUser().getAid(), uuidCount,
							commandPropToInt,
							commandMailOp);
					StringBuffer tagWhole = new StringBuffer();
					// 将修改后的标记黏贴为字符窜
					for (String uidSingle : uuidCount) {
						for (String tagSigle : sp.printFlags(hm.get(uidSingle))) {
							tagWhole.append(tagSigle);
						}
						list.add("*" + " FETCH FLAGS (" + tagWhole
								+ ")" + " UID " + uidSingle);

					}
				}
				list.add(commandSign + " OK STORE completed");
				return list;
			} else {

				// 不符合要求的返回null
				list.add(commandSign+" BAD [CLIENTBUG] Invalid command or arguments");
				return list;
			}
		} else {
			// 未登入的或者文件夹为空的返回null
			list.add(commandSign+" BAD [CLIENTBUG] Invalid command or arguments");
			return list;
		}

	}

	/**
	 * 将修改以后邮件的所有标记解析出来，然后放入List
	 * 
	 * @param mFlags
	 * @return
	 */
	public List<String> printFlags(int mFlags) {
		List<String> printList = new ArrayList<String>();
		// 打印出该数字所包含的标记
		for (int i = 0; i < 5; i++) {
			int isEqualFlags = (1 << i) & mFlags;
			if (isEqualFlags != 0) {
				printList.add(flagsMap.get(isEqualFlags));
			}
		}
		return printList;
	}

}
