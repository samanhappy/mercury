package com.dreamail.mercury.netty.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.dreamail.mercury.User;
import com.dreamail.mercury.imap.ImapSession;

public class StringParserTest {
	Map<String,CopyOnWriteArrayList<ImapSession>> session = new ConcurrentHashMap<String,CopyOnWriteArrayList<ImapSession>>();
	@Test
	public void indexOfTest(){
		/**
		 *  有空值判断
		 *  返回字符串 searchStr 在字符串 str 中第一次出现的位置。
    	 *	如果 str 为 null 或 searchStr 为 null 则返回-1，
    	 *	如果 searchStr 为 "" ,且 str 为不为 null ，则返回0，
    	 *	如果 searchStr 不在 str 中，则返回-1
		 */
		System.out.println(StringUtils.indexOf("abcc", ""));
	}
	
	@Test
	public void containsTest(){
		System.out.println(StringUtils.contains("aa", ""));
		String t = "A142 SELECT INBOX";
		String[] t1 = t.split(" ");
		System.out.println(t1[2]);
		System.out.println("FLAGS (\\Answered \\Flagged \\Draft \\Deleted \\Seen)");
		String[] t2 = "A3 OK [READ-WRITE] inbox selected. (Success)".split(" ");
		System.out.println(t2[2].replace("[", "").replace("]", ""));
	}
	
	
	@Test
	public void listTest(){
		
		ImapSession s1 = new ImapSession();
		User user = new User();
		user.setPassword("1234");
		user.setUserName("123@archermind.com");
		s1.setUser(user);
		
		ImapSession s2 = new ImapSession();
		User user2 = new User();
		user2.setPassword("1234");
		user2.setUserName("1234@archermind.com");
		s2.setUser(user2);
		CopyOnWriteArrayList<ImapSession> list = new CopyOnWriteArrayList<ImapSession>();
		list.add(s1);
		list.add(s2);
		session.put("123@archermind.com", list);
		
		CopyOnWriteArrayList<ImapSession> list2 = session.get("123@archermind.com");
		list2.get(1).setUser(new User());
		ImapSession s3 = session.get("123@archermind.com").get(1);
		System.out.println("name is="+s3.getUser().getUserName());
		
		for(ImapSession se:list2){
			list2.remove(se);
		}
		System.out.println(session.get("123@archermind.com").size());
		
	}
	
	@Test
	public void testEques(){
//		String co = null;
//		if( co!=null && !co.equalsIgnoreCase("") ){
//			System.out.println(1);
//		}else{
//			System.out.println(2);
//		}
	}
	
	@Test
	public void testClean(){
		List<String> list = new LinkedList<String>();
		list.add("test1");
		System.out.println(list.size());
		list.clear();
		System.out.println(list.size());
	}
	
	@Test
	public void testElseif(){
		String t = "1";
		if(t.equalsIgnoreCase("2")){
			System.out.println(1);
		}else if(t.equalsIgnoreCase("1")){
			System.out.println(2);
		}else if(t.equalsIgnoreCase("1")){
			System.out.println(3);
		}
	}
	
	@Test
	public void testList(){
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		list.add("10");
		list.add("11");
		list.add("12");
		
		for(String i:list){
			System.out.println(i);
		}
		
	}
	
	@Test
	public void treeMapTest(){
		Map<String,String> map = new TreeMap<String,String>();
		try{
			String num = map.get("test");
			System.out.println(num);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
