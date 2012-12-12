/**
 * 
 */
package com.dreamail.mercury.pojo;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.JsonUtil;
import com.dreamail.mercury.util.MailBoxDispatcher;

/**
 * @author meng.sun
 * 
 */
public class Clickoo_mail_account {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(Clickoo_mail_account.class);

	private long id;// 主键ID

	private String name;// 名称

	private int status;// 状态，是否启用，默认0有效，1无效

	private int type;// 账号类型,0普通账号（任务工厂）,1yahooSNP账号,2Imap-idle账号

	private String inCert;// 收邮件的用户的用户名密码

	private String inPath;// 收邮件的用户pop3和imap的地址

	private String outCert;// 发送邮件的用户帐号和密码和别名

	private String outPath;// 发送邮件的用户smtp地址

	private Date registrationDate;// 注册时间

	private Date recentMessageDate;

	private String deviceModel; // 设备分辨率

	private List<String> deviceModelList; // 设备分辨率列表

	private int roleLevel; // 角色等级

	private String uuid;

	private InPath objInPath;

	private OutPath objOutPath;

	private InCert objInCert;

	private OutCert objOutCert;

	private int failtime;

	private String cuki;

	private int validation;

	private int oldMailNum;// 收取老邮件数量

	private int autoCleanupPeriod;// 自动清理邮件周期

	private boolean isnewmailbox = false;

	private String suffix;

	private List<Clickoo_message> Clickoo_message;

	private List<Clickoo_folder> folderList;

	private String maxuuid;

	private String sign;// 黑白名单标记

	private long uid;

	private String function;

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getMaxuuid() {
		return maxuuid;
	}

	public void setMaxuuid(String maxuuid) {
		this.maxuuid = maxuuid;
	}

	public List<Clickoo_message> getClickoo_message() {
		return Clickoo_message;
	}

	public void setClickoo_message(List<Clickoo_message> clickooMessage) {
		Clickoo_message = clickooMessage;
	}

	public String getCuki() {
		return cuki;
	}

	public boolean isIsnewmailbox() {
		return isnewmailbox;
	}

	public void setIsnewmailbox(boolean isnewmailbox) {
		this.isnewmailbox = isnewmailbox;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setCuki(String cuki) {
		this.cuki = cuki;
	}

	public int getType() {
		return type;
	}

	public int getValidation() {
		return validation;
	}

	public void setValidation(int validation) {
		this.validation = validation;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<String> getDeviceModelList() {
		return deviceModelList;
	}

	public void setDeviceModelList(List<String> deviceModelList) {
		this.deviceModelList = deviceModelList;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InCert getInCert_obj() {
		if (this.objInCert != null) {
			return this.objInCert;
		}
		objInCert = JsonUtil.getInstance().parserInCert(this.inCert);
		return objInCert;
	}

	public void setInCert(String inCert) {
		this.inCert = inCert;
	}

	public InPath getInPath_obj() {
		if (objInPath != null) {
			return this.objInPath;
		}
		try {
			objInPath = JsonUtil.getInstance().parserInPath(this.inPath);
		} catch (Exception e) {
			logger.error("acccount InPath is null", e);
		}
		return objInPath;
	}

	public void setInPath(String inPath) {
		this.inPath = inPath;
	}

	public OutCert getOutCert_obj() {
		if (objOutCert != null) {
			return this.objOutCert;
		}
		try {
			objOutCert = JsonUtil.getInstance().parserOutCert(outCert);
		} catch (Exception e) {
			logger.error("acccount OutCert is null", e);
		}
		return objOutCert;
	}

	public void setOutCert(String outCert) {
		this.outCert = outCert;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public OutPath getOutPath_obj() {
		if (objOutPath != null) {
			return this.objOutPath;
		}
		objOutPath = JsonUtil.getInstance().parserOutPath(this.outPath);
		return objOutPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

	public String getInCert() {
		return inCert;
	}

	public String getInPath() {
		return inPath;
	}

	public String getOutCert() {
		return outCert;
	}

	public String getOutPath() {
		return outPath;
	}

	public int getFailtime() {
		return failtime;
	}

	public void setFailtime(int failtime) {
		this.failtime = failtime;
	}

	public int getOldMailNum() {
		return oldMailNum;
	}

	public void setOldMailNum(int oldMailNum) {
		this.oldMailNum = oldMailNum;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Clickoo_folder> getFolderList() {
		return folderList;
	}

	public void setFolderList(List<Clickoo_folder> folderList) {
		this.folderList = folderList;
	}

	public int getAutoCleanupPeriod() {
		return autoCleanupPeriod;
	}

	public void setAutoCleanupPeriod(int autoCleanupPeriod) {
		this.autoCleanupPeriod = autoCleanupPeriod;
	}

	public Date getRecentMessageDate() {
		return recentMessageDate;
	}

	public void setRecentMessageDate(Date recentMessageDate) {
		this.recentMessageDate = recentMessageDate;
	}

	/**
	 * 更新账号的类型
	 */
	public void updateAccountType() {
		if (name != null && !"".equals(name)) {
			if (MailBoxDispatcher.isYahooSNPSupport(name)) {
				type = Constant.ACCOUNT_YAHOOSNP_TYPE;
			} else if (MailBoxDispatcher.isGmailSupport(name)) {
				type = Constant.ACCOUNT_GMAIL_TYPE;
			} else if (MailBoxDispatcher.isHotmailSupport(name)) {
				type = Constant.ACCOUNT_HOTMAIL_TYPE;
			} else {
				type = Constant.ACCOUNT_COMMON_TYPE;
			}
		}
	}

	public void loadSendConfig(WebAccount account) {
		if (account != null) {
			OutPath outPath = getOutPath_obj();
			account.setSendHost(outPath.getSmtpServer());
			account.setSendProtocolType(Constant.SMTP);
			account.setSendPort(outPath.getSendPort());
			account.setSendTs("".equals(outPath.getType()) ? null : outPath
					.getType());
		}
	}

	public void loadReceiveConfig(WebAccount account) {
		InPath inPath = getInPath_obj();

		account.setReceiveHost(inPath.getInhost());
		account.setReceiveProtocolType(inPath.getProtocolType());
		account.setReceivePort(inPath.getReceivePort());
		account.setReceiveTs("".equals(inPath.getReceiveTs()) ? null : inPath
				.getReceiveTs());
		String compositor = inPath.getCompositor();
		if (compositor != null) {
			account.setCompositor(compositor);
		}
		String supportalluid = inPath.getSupportalluid();
		if (supportalluid != null) {
			account.setSupportalluid(supportalluid);
		}
	}

	public WebAccount getWebAccount4Ping() {
		WebAccount wbAccount = new WebAccount();
		wbAccount.setId(getId());
		wbAccount.setName(getName());
		InCert inCertTmp = getInCert_obj();
		if (inCertTmp != null) {
			wbAccount.setPassword(new String(EmailUtils
					.base64TochangeByte(inCertTmp.getPwd())));
		}
		return wbAccount;
	}
}
