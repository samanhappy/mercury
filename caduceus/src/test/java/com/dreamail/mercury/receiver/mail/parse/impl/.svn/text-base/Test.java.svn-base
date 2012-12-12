package com.dreamail.mercury.receiver.mail.parse.impl;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Session;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

public class Test {
	public static void main(String[] args) {
		A a1 = new A();
		a1.setBoo(true);
		a1.start();
		try {
			Thread.sleep(10000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		A a2 = new A();
		a2.setBoo(false);
		a2.start();
	}
}

class A extends Thread{
	
	public boolean boo;
	public void run(){
		Properties props = System.getProperties();
    	props.put("mail.smtp.auth", "true");
    	props.setProperty("mail.imap.port", "993");
        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(true);
		if(boo){
			System.out.println("------------------------true");
            try {
				IMAPStore store = (IMAPStore) session.getStore("imaps");
				store.connect("imap.gmail.com", "wpk1901@gmail.com",
						 "8611218773");
				IMAPFolder inbox = (IMAPFolder) store.getDefaultFolder();
				 for(Folder f: inbox.list()){
					 System.out.println("==========="+f.getName());
				 }
				 inbox = (IMAPFolder) store.getFolder("a");
				 inbox.open(Folder.READ_WRITE);
				 System.out.println("------------------------now sleep.");
				 Thread.sleep(80000);
//				 if(!inbox.exists()){
//					 inbox.create(Folder.HOLDS_MESSAGES);
//				 }
				 System.out.println(inbox.getMessageCount());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("------------------------false");
			try {
				IMAPStore store = (IMAPStore) session.getStore("imaps");
				store.connect("imap.gmail.com", "wpk1901@gmail.com",
						 "8611218773");
				IMAPFolder inbox = (IMAPFolder) store.getFolder("a");
				inbox.delete(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public boolean isBoo() {
		return boo;
	}
	public void setBoo(boolean boo) {
		this.boo = boo;
	}
	
	
	
}