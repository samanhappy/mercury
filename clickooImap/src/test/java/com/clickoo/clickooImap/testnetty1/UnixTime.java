package com.clickoo.clickooImap.testnetty1;

import java.sql.Date;

public class UnixTime {
	private final int value;
	public UnixTime(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return new Date(value*1000L).toString();
	}
	
	

}
