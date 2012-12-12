package com.dreamail.mercury.yahooSNP;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
public class PropertiesParse {
	  public HashMap<String, String> parseProperties(String properties){
		  	HashMap<String, String> hm = new HashMap<String, String>();
	        InputStream inputStream = this.getClass().getClassLoader()
	                .getResourceAsStream(properties);
	        Properties p = new Properties();
	        try
	        {
	            p.load(inputStream);
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	        Set<Map.Entry<Object, Object>> set = p.entrySet();
	        Iterator<Entry<Object, Object>> it = set.iterator();
	        while (it.hasNext())
	        {
	            Map.Entry<Object, Object> e = it.next();
	            hm.put(e.getKey().toString(), e.getValue().toString());
	        }
	        if(inputStream!=null){
	        	try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        return hm;
	  }
}
