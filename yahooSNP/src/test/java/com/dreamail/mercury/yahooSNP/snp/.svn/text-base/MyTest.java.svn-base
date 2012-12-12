package com.dreamail.mercury.yahooSNP.snp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.junit.Test;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.yahooSNP.SNPContext;
import com.dreamail.mercury.yahooSNP.Token;

public class MyTest
{
	@SuppressWarnings("unchecked")
	@Test
	public void parserXml()
	{
		try
		{
			SAXBuilder builder = new SAXBuilder();
			Document doc = (Document) builder.build(new File("abc.xml")); // 导入xml文件，xml文件名取名为test.xml

			Element root = doc.getRootElement(); // 获得根节点

			List<Element> tempChildren = (List<Element>) root.getChildren();

			Element temp1 = (Element) tempChildren.get(0);
			System.out.println(temp1.getName());
			System.out.println(temp1.getAttributeValue("notifyInfo"));

			Element message_insert = (Element) temp1.getChildren().get(0);
			Element msg = (Element) message_insert.getChildren().get(0);
			System.out.println(msg.getAttributeValue("folder"));

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@Test
	public void Y64Test()
	{
		new PropertiesDeploy().init();

		String timestamp = String.valueOf(Calendar.getInstance()
				.getTimeInMillis() / 1000);
		SNPContext user = new SNPContext();
		user.setLoginId("ss_102698@yahoo.cn");
		user.setPassword("123456");
		user.setAid(7);
		System.out.println(new Token().createHmac(user, timestamp));
	}
}
