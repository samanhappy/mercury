package com.dreamail.mercury.petasos.protocol;

public class PMString {

	private String pushmail;
	
	public PMString(String str) {
		this.pushmail = str;
	}
	
	public PMString wrap(String wrapper) {
		StringBuffer sb = new StringBuffer();
		sb.append("<").append(wrapper).append(">");
		sb.append(this.pushmail);
		sb.append("</").append(wrapper).append(">");
		this.pushmail = sb.toString();
		return this;
	}
	
	public PMString wrapTarget(String targetName) {
		StringBuffer sb = new StringBuffer();
		sb.append("<Target name=\"").append(targetName).append("\" isAtom=\"true|false\">");
		sb.append(this.pushmail);
		sb.append("</Target>");
		this.pushmail = sb.toString();
		return this;
	}
	
	public PMString wrapCmd(String cmdName) {
		StringBuffer sb = new StringBuffer();
		sb.append("<Cmd name=\"").append(cmdName).append("\">");
		sb.append(this.pushmail);
		sb.append("</Cmd>");
		this.pushmail = sb.toString();
		return this;
	}
	
	public PMString append(String str) {
		this.pushmail = this.pushmail + str;
		return this;
	}
	
	public PMString prefix(String str) {
		this.pushmail =  str + this.pushmail;
		return this;
	}
	
	public String toString() {
		return this.pushmail;
	}
	
}
