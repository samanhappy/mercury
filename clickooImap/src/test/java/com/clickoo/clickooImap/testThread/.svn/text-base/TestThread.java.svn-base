package com.clickoo.clickooImap.testThread;

import java.util.List;

import org.junit.Test;

import com.clickoo.clickooImap.domain.IdleMessage;
import com.clickoo.clickooImap.threadpool.impl.ConnectController;
import com.clickoo.clickooImap.utils.CIConstants;
import com.clickoo.mercury.dal.dao.AccountDao;
import com.clickoo.mercury.pojo.Clickoo_mail_account;

public class TestThread {
	@Test
	public void test1(){
		List<Clickoo_mail_account> list = new AccountDao().getAllValidAccountListByType(0);
//		System.err.println(list.size());
		if (list != null) {
			for (Clickoo_mail_account account : list) {
				if ("gmail.com".equals(account.getName().split("@")[1])) {
					IdleMessage idleMessage = new IdleMessage();
					idleMessage.setMsg(CIConstants.NoticeType.CI_NOTICEIDLE);
					idleMessage.setAid(account.getId());
					idleMessage.setAccountName(account.getName());
					idleMessage.setAccountPwd(account.getInCert_obj().getPwd());
					idleMessage.setType(2);
					ConnectController.addIdleConnect(idleMessage);
				}
			}
		}
	}
	public static void main(String[] args) {
		List<Clickoo_mail_account> list = new AccountDao().getAllValidAccountListByType(0);
//		System.err.println(list.size());
		if (list != null) {
			for (Clickoo_mail_account account : list) {
				if ("gmail.com".equals(account.getName().split("@")[1])) {
					IdleMessage idleMessage = new IdleMessage();
					idleMessage.setMsg(CIConstants.NoticeType.CI_NOTICEIDLE);
					idleMessage.setAid(account.getId());
					idleMessage.setAccountName(account.getName());
					idleMessage.setAccountPwd(account.getInCert_obj().getPwd());
					idleMessage.setType(2);
					ConnectController.addIdleConnect(idleMessage);
				}
			}
		}
	}
}
