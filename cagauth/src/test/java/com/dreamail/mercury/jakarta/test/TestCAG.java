package com.dreamail.mercury.jakarta.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.cag.CAGSettingObject;
import com.dreamail.mercury.cag.COSSettingsObject;
import com.dreamail.mercury.jakarta.xstream.XStreamParser;
import com.dreamail.mercury.util.CAGConstants;

public class TestCAG {

	String url1 = "http://58.64.174.227/cagauth/cagapi";

	String uid = "eva";
	
	String role = "Premium";

	@Test
	public void testCAG1() throws HttpException, IOException,
			InterruptedException {
		setServerConfig();
		updateConfig();
	}
	
	@Test
	public void testCAG2() throws HttpException, IOException {
		while (true) {
			updateConfig();
		}
	}
	
	@Test
	public void testCAG3() throws HttpException, IOException {
		getClientConfig();
		setServerConfig();
		updateConfig();
	}
	
	@Test
	public void testCAG4() throws HttpException, IOException {
		getServerConfig();
	}
	
	/**
	 * SetServerConfig.
	 * 
	 * @throws HttpException
	 * @throws IOException
	 */
	public void setServerConfig() throws HttpException, IOException {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url1);
		postMethod.setRequestEntity(new StringRequestEntity(
				getSetServerConfigContent()));
		int statusLine = httpClient.executeMethod(postMethod);
		System.out.println(statusLine);
		String response = postMethod.getResponseBodyAsString();
		System.out.println(response);
	}
	
	/**
	 * UpdateConfig.
	 * 
	 * @throws IOException
	 */
	public void updateConfig() throws IOException {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url1);
		postMethod.setRequestEntity(new StringRequestEntity(
				getUpdateConfigContent()));
		int statusLine = httpClient.executeMethod(postMethod);
		System.out.println(statusLine);
		String response = postMethod.getResponseBodyAsString();
		System.out.println(response);
	}
	
	/**
	 * GetClientConfig.
	 * 
	 * @throws IOException
	 */
	public void getClientConfig() throws IOException {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url1);
		postMethod.setRequestEntity(new StringRequestEntity(
				getGetClientConfigContent()));
		int statusLine = httpClient.executeMethod(postMethod);
		System.out.println(statusLine);
		String response = postMethod.getResponseBodyAsString();
		System.out.println(response);
	}
	
	/**
	 * GetServerConfig.
	 * 
	 * @throws HttpException
	 * @throws IOException
	 */
	public void getServerConfig() throws HttpException, IOException {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url1);
		postMethod.setRequestEntity(new StringRequestEntity(
				getGetServerConfigContent()));
		int statusLine = httpClient.executeMethod(postMethod);
		System.out.println(statusLine);
		String response = postMethod.getResponseBodyAsString();
		System.out.println(response);
	}

	/**
	 * 得到SetServerConfig内容.
	 * 
	 * @return
	 */
	public String getSetServerConfigContent() {
		CAGParserObject obj = new CAGParserObject();
		obj.setUuid(uid);

		COSSettingsObject cos = new COSSettingsObject();
		cos.setLevel(role);

		CAGSettingObject cag = new CAGSettingObject();
		cag.setCos(cos);
		obj.setSettings(cag);

		obj.setNotification(CAGConstants.CAG_SETSERVERCONFIG_NOTIF);
		String xml = XStreamParser.CAGObject2Xml(obj);
		System.out.println(xml);
		return xml;
	}
	
	/**
	 * 得到GetServerConfig内容.
	 * 
	 * @return
	 */
	public String getGetServerConfigContent() {
		CAGParserObject obj = new CAGParserObject();
//		obj.setUuid(uid);
		obj.setCoslevel(role);

		obj.setNotification(CAGConstants.CAG_GETSERVERCONFIG_NOTIF);
		String xml = XStreamParser.CAGObject2Xml(obj);
		System.out.println(xml);
		return xml;
	}

	/**
	 * 得到UpdateConfig内容.
	 * 
	 * @return
	 */
	public String getUpdateConfigContent() {
		CAGParserObject obj = new CAGParserObject();
		obj.setUuid(uid);
		obj.setNotification(CAGConstants.CAG_UPDATECONFIG_NOTIF);
		String xml = XStreamParser.CAGObject2Xml(obj);
		System.out.println(xml);
		return xml;
	}
	
	/**
	 * 得到GetClientConfig内容.
	 * 
	 * @return
	 */
	public String getGetClientConfigContent() {
		CAGParserObject obj = new CAGParserObject();
		obj.setUuid(uid);
		obj.setNotification(CAGConstants.CAG_UPDATECONFIG_NOTIF);
		String xml = XStreamParser.CAGObject2Xml(obj);
		System.out.println(xml);
		return xml;
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
