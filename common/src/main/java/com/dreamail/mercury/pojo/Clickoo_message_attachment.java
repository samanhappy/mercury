/**
 * 
 */
package com.dreamail.mercury.pojo;

import java.util.List;


/**
 * @author meng.sun
 *
 */
public class Clickoo_message_attachment{

	private long id;// 主键ID
	
	private String name;// 名称
	
	private String type;// 附件类型
	
	private long length;// 附件大小
	
	private String path;// 附件所在路径
	
	private long volume_id; //附件ID
	
	private byte[] in;
	
	private long parent; //parent ID
	
	private long mid;
	
	private Clickoo_volume volume;
	
	private List<Clickoo_message_attachment> childList;
	
	public List<Clickoo_message_attachment> getChildList() {
		return childList;
	}

	public void setChildList(List<Clickoo_message_attachment> childList) {
		this.childList = childList;
	}

	public Clickoo_message_attachment(){
		
	}

	
	
	/**
	 * @param id
	 * @param name
	 * @param type
	 * @param length
	 * @param path
	 * @param volumeId
	 * @param uuid
	 */
	public Clickoo_message_attachment(long id, String name, String type,
			long length, String path, long volumeId, long mid) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.length = length;
		this.path = path;
		volume_id = volumeId;
		this.mid = mid;
	}
	
	public long getParent() {
		return parent;
	}

	public void setParent(long parent) {
		this.parent = parent;
	}

	public Clickoo_volume getVolume() {
		return volume;
	}

	public void setVolume(Clickoo_volume volume) {
		this.volume = volume;
	}

	public long getVolume_id() {
		return volume_id;
	}

	public void setVolume_id(long volumeId) {
		volume_id = volumeId;
	}

	

	public long getMid() {
		return mid;
	}



	public void setMid(long mid) {
		this.mid = mid;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public byte[] getIn() {
		return in;
	}

	public void setIn(byte[] in) {
		this.in = in;
	}

}
