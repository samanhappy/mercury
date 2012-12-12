/**
 * 
 */
package com.dreamail.mercury.pojo;

import java.io.Serializable;

import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.ZipUtil;

/**
 * @author meng.sun
 * 
 */
public class Clickoo_message_data implements Serializable {

	private static final long serialVersionUID = -8129080496167520204L;

	private long id;// 主键ID

	private String data;// 正文

	private long size;// 内容大小

	private byte[] content;// 压缩后的字节数组入库用

	public Clickoo_message_data() {

	}

	/**
	 * @param id
	 * @param data
	 */
	public Clickoo_message_data(long id, String data, long size) {
		super();
		this.id = id;
		this.data = data;
		this.size = size;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 从数据库中查询出后解压
	 * 
	 * @return
	 */
	public String getData(boolean isCompress) {
		byte[] decomDataBytes = ZipUtil.decompress(content);
		byte[] base64Data = EmailUtils.sunChangeBase64ToByte(new String(
				decomDataBytes));
		return new String(base64Data);
	}

	/**
	 * 压缩入库用
	 * 
	 * @param data
	 */
	public void setData(String data,boolean isCompress) {
		String base64Data = EmailUtils.changeByteToBase64(data.getBytes());
		byte[] dataBytes = ZipUtil.compress(base64Data.getBytes());
		setContent(dataBytes);
		this.data = data;
	}

}
