package com.dreamail.timer;


import com.dreamail.mercury.timerpush.cache.TimerInfoManager;


public class TreeSetTest {

	
	public static void main(String[] args) {
		
		TimerInfoManager.addTimer(1, 1, 3, "O");

		System.out.println(TimerInfoManager.getFirstTimerInfos());

		TimerInfoManager.addTimer(1, 2, 2, "O");

		System.out.println(TimerInfoManager.getFirstTimerInfos());

		TimerInfoManager.addTimer(1, 2, 1, "F");

		System.out.println(TimerInfoManager.getFirstTimerInfos());

		TimerInfoManager.removeTimer(1, 2, 1, "F");

		System.out.println(TimerInfoManager.getFirstTimerInfos());

		TimerInfoManager.removeTimer(1, 2, 1, "O");

		System.out.println(TimerInfoManager.getFirstTimerInfos());

		TimerInfoManager.removeFirstItem();

		System.out.println(TimerInfoManager.getFirstTimerInfos());

		TimerInfoManager.removeFirstItem();

		System.out.println(TimerInfoManager.getFirstTimerInfos());
	}
}
