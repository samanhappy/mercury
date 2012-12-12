package com.dreamail.mercury.store;

import org.junit.Test;

import com.dreamail.mercury.store.Volume;
import com.dreamail.mercury.store.Volumes;

public class VolumesTest {

	@Test
	public void testGetVolumeByMeta() {
		Volumes volumes = new Volumes();
		Volume volume = volumes.getVolumeByMeta(
				Volumes.MetaEnum.CURRENT_MESSAGE_VOLUME);
		if(volume != null){
		     System.out.println(volume.getPath());
		}else{
			System.out.println("is null");
		}
	}

	@Test
	public void testGetVolumeById() {
//		HandleStorePathImpl i = new HandleStorePathImpl();
//		String resultpath = i.getPathById(186, "3");
//		System.out.println(resultpath);
	}

}
