package com.dreamail.mercury.talaria.xstream;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;

public class TestXStream {

	@Test
	public void test() {
		XStream xstream = new XStream(new Dom4JDriver());
		
		xstream.alias("person", Person.class);
		xstream.alias("phonenumber", PhoneNumber.class);
		
		Person joe = new Person("Joe", "Walnes");
		joe.setPhone(new PhoneNumber(123, "1234-456"));
		joe.setFax(new PhoneNumber(123, "9999-999"));
		
		String xml = xstream.toXML(joe);
		System.out.println(xml);
		
		Person newJoe = (Person)xstream.fromXML(xml);
		System.out.println(newJoe.getFirstname() + " " + newJoe.getLastname());
	}
	
	@Test
	public void testUPE() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><UPE><pushMail><uid>23</uid></pushMail><IM></IM><MSN></MSN></UPE>";
		UPEParserObject obj = XStreamParser.xml2UPEObject(xml);
		System.out.println(obj.getIMEI());
		System.out.println(obj.getPushMail().getState());
		System.out.println(obj.getPushMail().getUid());
		
		obj.getPushMail().setState("1");
		System.out.println(XStreamParser.UPEObject2Xml(obj));
	}
	
	@Test
	public void test2Xml() {
		UPEParserObject upeObj = new UPEParserObject();
		System.out.println(XStreamParser.UPEObject2Xml(upeObj));
		
		System.out.println(XStreamParser.UPEObject2Xml(upeObj));
		
		upeObj.setPushMail(new PushMailObject());
		System.out.println(XStreamParser.UPEObject2Xml(upeObj));
		
		upeObj.getPushMail().setUid("2");
		upeObj.getPushMail().setState("3");
		System.out.println(XStreamParser.UPEObject2Xml(upeObj));
	}
}
