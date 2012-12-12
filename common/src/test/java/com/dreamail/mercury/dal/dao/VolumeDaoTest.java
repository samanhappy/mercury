package com.dreamail.mercury.dal.dao;

import junit.framework.TestCase;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.store.Volume;

public class VolumeDaoTest extends TestCase{
	
	@Test
	public void testGetCurrentVolume() {
		Volume volume = null;
		int type = 0;
		try {
			SqlSession sqlSession = SessionFactory.getSession();
			volume = (Volume) sqlSession.
			     selectOne("com.clickoo.mercury.domain.VolumeMapper.getCurrentVolume",type);
			if(volume==null){
				System.out.println("volume is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			SessionFactory.closeSession();
		}
	}
	
//	@Test 
//	public void testGetAllVolume() {
//		System.out.println(new UARelationDao().getAllAidAfterChangeOfflineStatus(1).get(0));
//	}

}
