package com.dreamail.mercury.petasos.protocol;

import org.junit.Test;

public class ProtocolExchangeTest {

	@Test
	public void test() throws Exception {
		
		try {  
            HttpRequester request = new HttpRequester();  
            HttpRespons hr = request.send("http://192.168.20.210:9080/petasos/protocolExchange.do", 
            		"GET", "<?xml>");  
   
            System.out.println(hr.getUrlString());  
            System.out.println(hr.getProtocol());  
            System.out.println(hr.getHost());  
            System.out.println(hr.getPort());  
            System.out.println(hr.getContentEncoding());  
            System.out.println(hr.getMethod());  
              
            System.out.println(hr.getContent());  
   
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
}
