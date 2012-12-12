package com.dreamail.mercury.talaria.upe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

import com.dreamail.jms.JmsSender;
import com.dreamail.mercury.cag.AccountSettingsObject;
import com.dreamail.mercury.cag.CAGSettingObject;
import com.dreamail.mercury.cag.COSSettingsObject;
import com.dreamail.mercury.talaria.xstream.PushMailObject;
import com.dreamail.mercury.talaria.xstream.UPEParserObject;
import com.dreamail.mercury.talaria.xstream.XStreamParser;

public class TestUPE {

	String url = "http://10.11.1.116:8080/talaria/LongPullServlet";
	
//	String url1 = "https://192.168.20.216:8181/talaria/LongPullServlet";

	@Test
	public void testUPE() throws HttpException, IOException,
			InterruptedException {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestEntity(new StringRequestEntity(getIMContent()));
		int statusLine = httpClient.executeMethod(postMethod);
		System.out.println(statusLine);
		String response = getResponseString(postMethod
				.getResponseBodyAsStream());
		System.out.println(response);
		
		/*while (statusLine == 200) {
			String response = getResponseString(postMethod
					.getResponseBodyAsStream());
			System.out.println(response);

			String ts = null;
			try {
				ts = response.substring(response.indexOf("timestamp") + 10,
						response.indexOf("</timestamp>"));
			} catch (Exception e) {
				ts = String.valueOf(System.currentTimeMillis());
			}
			
			String state = null;
			try {
				state = response.substring(response.indexOf("<state>") + 7,
						response.indexOf("</state>"));
			} catch (Exception e) {
				System.out.println("exception...");
			}
			
			httpClient = new HttpClient();
			postMethod = new PostMethod(url);
			postMethod
					.setRequestEntity(new StringRequestEntity(getContent(ts,state)));
			statusLine = httpClient.executeMethod(postMethod);
			System.out.println(statusLine);
		}*/

	}

	@Test
	public void testMessageJMS() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("192.168.20.43", "<IM/>");
		JmsSender.sendTopicMsg(map, "imupe");
	}

	@Test
	public void testTimerJMS() {
		JmsSender.sendTopicMsg("accountAdd,sunmeng,1", "timerTopic");
	}

	public String getContent(String ts, String state) {
		UPEParserObject obj = new UPEParserObject();
		obj.setPushMail(new PushMailObject());
		obj.setIMEI("test");
		obj.getPushMail().setTimestamp(ts);
		obj.getPushMail().setUid("1000");
		
		if ("6".equals(state)) {
			
			AccountSettingsObject account = new AccountSettingsObject();
			account.setName("samanhappy@gmail.com");
			account.setServiceActivation("No");
			account.setCuki("ODDFDflef8234dfsdf");
			List<AccountSettingsObject> accoutSettings = new ArrayList<AccountSettingsObject>();
			accoutSettings.add(account);
			
			CAGSettingObject settings = new CAGSettingObject();
			COSSettingsObject cos = new COSSettingsObject();
			cos.setAllowedEmailAccount("10");
			cos.setStorageLocationOption("Yes");
			
			settings.setAccounts(accoutSettings);
			settings.setCos(cos);
			obj.getPushMail().setSettings(settings);
		}
		
		String xml = XStreamParser.UPEObject2Xml(obj);
		System.out.println(xml);
		return xml;
	}

	/**
	 * 得到IM的测试字符串.
	 * 
	 * @return
	 */
	public String getIMContent() {
		StringBuffer sb = new StringBuffer();
//		sb.append("<UPE><im>");
//		sb.append("<uuid>imok001@yahoo.com</uuid><timestamp>23421366655</timestamp>");
//		sb.append("<settings><accounts><account><cuki>11111111111111111111111</cuki></account> </accounts></settings>");
//		sb.append("</im></UPE>");
		sb.append("<UPE><IMEI>123</IMEI><pushMail>");
		sb.append("<uid>3</uid><state>600</state><timestamp>"+String.valueOf(System.currentTimeMillis())+"</timestamp>");
		sb.append("<deleteMessageIds>");
		sb.append("<mid>1</mid>");
		sb.append("<mid>2</mid>");
		sb.append("</deleteMessageIds></pushMail></UPE>");
		return sb.toString();
	}

	
	/**
	 * 解析服务器返回协议.
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	String getResponseString(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new GZIPInputStream(is)));
		StringBuffer sb = new StringBuffer();
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
			str = br.readLine();
		}
		return sb.toString();
	}

}
