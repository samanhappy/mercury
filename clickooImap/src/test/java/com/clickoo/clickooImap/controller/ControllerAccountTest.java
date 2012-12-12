package com.clickoo.clickooImap.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import org.junit.Test;
import com.clickoo.clickooImap.domain.IdleMessage;
import com.clickoo.mercury.dal.dao.AccountDao;
import com.clickoo.mercury.pojo.Clickoo_mail_account;

public class ControllerAccountTest {
	@Test
	public void TestgetVipIdlehMailAccounts(){
		ControllerAccount controllerAccount = new ControllerAccount();
		controllerAccount.start();
	}
	@Test
	public void testConcurrentHashMap(){
		ConcurrentHashMap<String , String> map = new ConcurrentHashMap<String, String>();
		map.put("1", "1");
		System.out.println(map);
		map.put("2", "4");
		System.out.println(map);
		map.put("2", "6");
		System.out.println(map);
//		Iterator it = map.keySet().iterator();
		Object[] list =  map.keySet().toArray();
		for (int i = 0; i < list.length; i++) {
			Object object = list[i];
			System.out.println(object);
		}
//		while (it.hasNext()) {
//			String object = (String) it.next();
//			System.out.println(object);
//		}
//		Enumeration<String> enumeration = map.keys();
//		System.out.println(enumeration.toString());
	}
	@Test
	public void testCopyOnWriteArraySet(){
		CopyOnWriteArraySet<IdleMessage> arrayList = new CopyOnWriteArraySet<IdleMessage>();
		IdleMessage idleMessage = new IdleMessage();
		idleMessage.setAid(1);
		IdleMessage idleMessage2 = new IdleMessage();
		idleMessage.setAid(2);
		IdleMessage idleMessage3 = new IdleMessage();
		idleMessage.setAid(1);
		arrayList.add(idleMessage);
		System.out.println(arrayList.size()+"-------------");
		arrayList.add(idleMessage2);
		System.out.println(arrayList.size()+"-------------");
		arrayList.add(idleMessage3);
		System.out.println(arrayList.size()+"-------------");
		arrayList.add(idleMessage2);
		System.out.println(arrayList.size()+"-------------");
		
	}
	@Test
	public void testCopyOnWriteArraySet1(){
		CopyOnWriteArraySet<String> arrayList = new CopyOnWriteArraySet<String>();
		String s1 = "1";
		String s2 = "2";
		String s3 = "1";
		String s4 = "1";
		arrayList.add(s1);
		System.out.println(arrayList.size()+"-------------");
		arrayList.add(s2);
		System.out.println(arrayList.size()+"-------------");
		arrayList.add(s3);
		System.out.println(arrayList.size()+"-------------");
		arrayList.add(s4);
		System.out.println(arrayList.size()+"-------------");
		
	}
	@Test
	public void testIdleMessage(){
		IdleMessage message = new IdleMessage();
		message.setAid(1321312);
		IdleMessage message2 = message;
		message2.setAid(423432234);
		System.out.println(message.getAid()+"---------------"+message2.getAid());
	}
	@Test
	public void testAverage(){
//		System.out.println(14/5);
//		System.out.println(14%5);
//		
//		JSONObject json = new JSONObject();
//		json.put("a", 1);
//		json.put("b", 32423423);
//		int a = json.getInt("a");
//		System.out.println(a);
//		System.out.println(json.get("b"));
		int min =8;
		int num = 6;
		System.out.println(min<num?min:num);
	}
	@Test
	public void testCm(){
		ConcurrentHashMap<String, ConcurrentHashMap<String, IdleMessage>> map = new ConcurrentHashMap<String, ConcurrentHashMap<String,IdleMessage>>();
		ConcurrentHashMap<String, IdleMessage> map1 = new ConcurrentHashMap<String, IdleMessage>();
		map1.put("1", new IdleMessage());
		map1.put("2", new IdleMessage());
		map1.put("3", new IdleMessage());
		map.put("aa", map1);
		ConcurrentHashMap<String, IdleMessage> map2 = new ConcurrentHashMap<String, IdleMessage>();
		map2.put("4", new IdleMessage());
		map2.put("5", new IdleMessage());
		map2.put("6", new IdleMessage());
		map.put("bb", map2);
		System.out.println(map);
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String keyOfServerAccounts = (String) it.next();
			ConcurrentHashMap<String, IdleMessage> tmpMap = map.get(keyOfServerAccounts);
			if(tmpMap.containsKey("6")){
				tmpMap.remove("6");
			}
		}
		System.out.println(map);
	}
	@Test
	public void testGetAllValidAccountListByType(){
		List<Clickoo_mail_account> list = new AccountDao().getAllValidAccountListByType(0);
		System.out.println(list);
	}
	@Test
	public void testString(){
		String a = "String##34234213";
		System.out.println(a.split("String##")[1]);
	}
	
	public enum AdminRole {
		Super, Operator, Normal
	};

	private final static int[] DOMAINS = new int[] { 1, 2, 4 };
	/**
	 * PUSHMAIL = 1
	 * IM = 2
	 * SYNC = 4
	 * 3 = PUSHMAIL + IM
	 * 5 = PUSHMAIL + SYNC
	 * 6 = IM + SYNC
	 * 7 = PUSHMAIL+ IM + SYNC
	 */
	private int domain;
	public int getDomain() {
		return domain;
	}

	public void setDomain(int domain) {
		this.domain = domain;
	}
	public boolean isDomain(int domain) {
		int domainTmp = this.domain;
		for (int i = DOMAINS.length - 1; i >= 0; i--) {
			if ((domainTmp - DOMAINS[i]) >= 0) {
				domainTmp -= DOMAINS[i];
				if (domain == DOMAINS[i]) {
					return true;
				}
			}
		}
		return false;
	}
	@Test
	public void testTime(){
		Calendar c = Calendar.getInstance();
		System.out.println(c.getTime());
		try {
			System.out.println(new SimpleDateFormat("dd-MMMM-yyyy",Locale.ENGLISH).parse("22-Mar-2011"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}
