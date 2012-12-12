package com.dreamail.mercury.smtp.utils;

public class SmtpEmailBody {

	private long size; // 正文的长度
	private String datatype; // 正文的类型
	private String data; // 正文

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
