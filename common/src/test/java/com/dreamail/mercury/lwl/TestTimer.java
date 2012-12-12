package com.dreamail.mercury.lwl;

import java.util.List;

import org.junit.Test;

import com.dreamail.mercury.dal.dao.UserLimitTimesDao;
import com.dreamail.mercury.pojo.Clickoo_user_limittimes;

public class TestTimer {
	private static UserLimitTimesDao userLimitTimesDao = new UserLimitTimesDao();
	@Test
	public List<Clickoo_user_limittimes> testQueryUserLimittimes() {
		List<Clickoo_user_limittimes> list = userLimitTimesDao.queryUserLimittimes();
		System.out.println(list.size()+"==================================");
		return list;
	}
	@Test
	public void testDeleteUserLimittimesById(long id){
	}

}
