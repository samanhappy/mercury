package com.dreamail.mercury.xfire;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;

import org.junit.Test;

public class DynamicClientTest {

	@Test
	public void test() throws MalformedURLException, Exception {
		
		System.setProperty("http.auth.preference", "basic");
		System.setProperty("http.auth.digest.validateServer", "false");
		System.setProperty("http.auth.digest.validateProxy", "false");

		Authenticator.setDefault(new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				System.out.println("Authentication...");
				System.out.print("Requesting Prompt: ");
				System.out.println(this.getRequestingPrompt());
				System.out.print("Requesting Scheme: ");
				System.out.println(this.getRequestingScheme());
				System.out.print("Requestor Type: ");
				System.out.println(this.getRequestorType());

				return new PasswordAuthentication("danwang","honey@123".toCharArray());
			}

		});
		
		System.setProperty("javax.wsdl.factory.WSDLFactory", "com.ibm.wsdl.factory.WSDLFactoryImpl");
//		Client client = new Client(new URL("http://localhost:8080/1/WeatherWS.asmx"));
		
		
//		Client client = new Client(new URL("http://localhost:8080/1/Services.wsdl"));
		
		
//       Object[] results = client.invoke("SendItem", new Object[] {"", "", "", "", "", "", ""});
//        System.out.println((Double) results[0]);
	}
}
