package com.dreamail.mercury.pojo;

public class Clickoo_imap_message  implements Comparable<Clickoo_imap_message>{
	private String uid;
	private String content;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public int compareTo(Clickoo_imap_message o) {
			int oUid = Integer.parseInt(o.getUid());
			if(Integer.parseInt(uid)> oUid){
				return 1;
			}else if(Integer.parseInt(uid)== oUid){
				return 0;
			}
		return -1;
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
