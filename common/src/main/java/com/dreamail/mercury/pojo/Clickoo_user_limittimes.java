package com.dreamail.mercury.pojo;

import java.io.Serializable;

/**
 * 收邮件限制时间
 * 
 * @author 001211
 * 
 */
public class Clickoo_user_limittimes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id; // 主键ID
	private long uid; // 用户ID
	private long aid; // 邮件账户ID
	private Integer starthour; // （1~24）对应24小时
	private Integer endhour; // （1~24）对应24小时
	private Integer startminute;
	private Integer endminute;
	private int timetype; // 1表示push,0表示不push
	private String weekdays;// 星期： 1~7 对应周一到周日

	public String getWeekdays() {
		return weekdays;
	}

	public void setWeekdays(String weekdays) {
		this.weekdays = weekdays;
	}

	public Integer getStartminute() {
		return startminute;
	}

	public void setStartminute(Integer startminute) {
		this.startminute = startminute;
	}

	public Integer getEndminute() {
		return endminute;
	}

	public void setEndminute(Integer endminute) {
		this.endminute = endminute;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}

	public Integer getStarthour() {
		return starthour;
	}

	public void setStarthour(Integer starthour) {
		this.starthour = starthour;
	}

	public Integer getEndhour() {
		return endhour;
	}

	public void setEndhour(Integer endhour) {
		this.endhour = endhour;
	}

	public int getTimetype() {
		return timetype;
	}

	public void setTimetype(int timetype) {
		this.timetype = timetype;
	}

}
