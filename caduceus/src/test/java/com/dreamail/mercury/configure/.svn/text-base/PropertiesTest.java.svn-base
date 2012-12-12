package com.dreamail.mercury.configure;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.Test;

public class PropertiesTest {
	@Test
	public void loadPropertiesTest(){
		PropertiesDeploy p = new PropertiesDeploy();
		p.init();
		Map<String, String> map = PropertiesDeploy.getConfigureMap();
		Set<Entry<String, String>> set = map.entrySet();
		Iterator<Entry<String, String>> it = set.iterator();
		while (it.hasNext())
        {
            Entry<String, String> e = it.next();
            System.out.println("key = "+e.getKey()+" value="+e.getValue());
        }
	}
}
