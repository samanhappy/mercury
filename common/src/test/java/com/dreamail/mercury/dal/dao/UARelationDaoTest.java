package com.dreamail.mercury.dal.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dreamail.mercury.dal.dao.UARelationDao;

public class UARelationDaoTest {

	public final static int AUTO_OFFLINE_STATUS = Integer.parseInt("10000", 2);

	public final static int AUTO_ONLINE_STATUS = Integer.parseInt("01111", 2);

	public final static int USER_PUSH_OFFLINE_STATUS = Integer.parseInt(
			"01000", 2);

	public final static int USER_PUSH_ONLINE_STATUS = Integer.parseInt("10111",
			2);

	public final static int ACCOUNT_PUSH_OFFLINE_STATUS = Integer.parseInt(
			"00100", 2);

	public final static int ACCOUNT_PUSH_ONLINE_STATUS = Integer.parseInt(
			"11011", 2);

	public final static int ACCOUNT_SCHEDULE_OFFLINE_STATUS = Integer.parseInt(
			"00010", 2);

	public final static int ACCOUNT_SCHEDULE_ONLINE_STATUS = Integer.parseInt(
			"11101", 2);
	
	@Test
	public void testSetUserPushOffline() {
		new UARelationDao().setOffline(1, USER_PUSH_OFFLINE_STATUS,
				-1, null);
	}

	@Test
	public void testSetUserPushOnline() {
		new UARelationDao().setOnline(1, USER_PUSH_ONLINE_STATUS, -1,
				null);
	}
	
	@Test
	public void testSetUserAutoOffline() {
		new UARelationDao().setOffline(1, AUTO_OFFLINE_STATUS,
				-1, null);
	}
	
	@Test
	public void testSetUsersAutoOffline() {
		List<String>  uidlist = new ArrayList<String>();
		uidlist.add("2");
		uidlist.add("1");
		new UARelationDao().setAutoOfflineUsers(uidlist, AUTO_OFFLINE_STATUS);
	}

	@Test
	public void testSetUserAutoOnline() {
		new UARelationDao().setOnline(1, AUTO_ONLINE_STATUS, -1,
				null);
	}
	
	
	@Test
	public void testSetAccountPushOffline() {
		List<String>  aidlist = new ArrayList<String>();
		aidlist.add("1");
		aidlist.add("3");
		new UARelationDao().setOffline(1, ACCOUNT_PUSH_OFFLINE_STATUS,
				-1, aidlist);
	}

	@Test
	public void testSetAccountPushOnline() {
		new UARelationDao().setOnline(1, ACCOUNT_PUSH_ONLINE_STATUS, 1,
				null);
	}
	
	
	@Test
	public void testSetAccountScheduleOffline() {
		new UARelationDao().setOffline(1, ACCOUNT_SCHEDULE_OFFLINE_STATUS,
				1, null);
	}

	@Test
	public void testSetAccountScheduleOnline() {
		new UARelationDao().setOnline(1, ACCOUNT_SCHEDULE_ONLINE_STATUS, 1,
				null);
	}
	
	@Test
	public void testState() {
		System.out.println(USER_PUSH_OFFLINE_STATUS);
	}

}
