package com.dreamail.mercury.cache;

import org.junit.Test;

import com.danga.MemCached.MemCachedClient;
import com.dreamail.mercury.cache.MemCachedManager;

public class MemcachedTest {

	
	@Test
	public void test () {
		
		MemCachedManager.getInstance().init();
		
		MemCachedClient client = MemCachedManager.getMcc();
		int i = 0;
		while (true) {
			System.out.println(client.get("key"));
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(client.set("key", i++));
		}
	}
}
