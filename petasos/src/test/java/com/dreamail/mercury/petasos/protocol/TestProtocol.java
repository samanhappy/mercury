package com.dreamail.mercury.petasos.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

public class TestProtocol extends TestCase {

	String xmlVersion = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	String ip = "58.64.174.228";

	String port = "8080";

	HttpClient httpClient = new HttpClient();

	PostMethod postMethod;

	String uid = null;

	String aid = null;
	
	String IMEI = null;

	PMString pmstr = null;

	int stateLine = -1;

	String response = null;

	@Test
	public void testTmp() throws HttpException, IOException {
		IMEI = "sunmeng";
		uid = "27";
		userLogin("aa", " ");
		userRemove();
		assertTrue(response.indexOf("User successfully deleted!") != -1);
	}
	
	/**
	 * 测试单独用户注册、修改和删除.
	 * 
	 * @throws HttpException
	 * @throws IOException
	 */
	@Test
	public void testUserOperate() throws HttpException, IOException {

		userRegister();
		assertTrue(response.indexOf("<Code>0</Code>") != -1);

		userModify();
		assertTrue(response.indexOf("<Code>0</Code>") != -1);

		userRemove();
		assertTrue(response.indexOf("<Code>0</Code>") != -1);

	}

	/**
	 * 测试用户账号同时注册,均注册成功.
	 * 
	 * @throws HttpException
	 * @throws IOException
	 */
	@Test
	public void testUserAndAccountRegister() throws HttpException, IOException {

		userAndAccountRegister("danwang@pushmail.com", "honey.123");
		assertTrue(response.indexOf("<Code>0</Code>") != -1);
		assertTrue(response.indexOf("<Account><ID>") != -1);

		userRemove();
		assertTrue(response.indexOf("<Code>0</Code>") != -1);
	}

	/**
	 * 测试用户账号同时注册,用户注册成功,账号注册失败.
	 * 
	 * @throws HttpException
	 * @throws IOException
	 */
	@Test
	public void testUserAndErrorAccountRegister() throws HttpException,
			IOException {

		userAndAccountRegister("error@gmail.com", "error");
		assertTrue(response.indexOf("<UUID>") == -1);
		assertTrue(response.indexOf("<Account><ID>") == -1);

	}

	/**
	 * 测试添加服务器没有提供配置信息的邮箱帐号注册,服务器应该返回10516的错误码.
	 * 
	 * @throws HttpException
	 * @throws IOException
	 */
	@Test
	public void testUserAndNonrecognitionAccountRegister()
			throws HttpException, IOException {

		userAndAccountRegister("error@error.com", "error");
		assertTrue(response.indexOf("<UUID>") == -1);
		assertTrue(response.indexOf("<Code>10516</Code>") != -1);

	}

	/**
	 * 测试添加重复邮箱帐号注册,服务器应该返回10520的错误码.
	 * 
	 * @throws HttpException
	 * @throws IOException
	 */
	@Test
	public void testUserAndRepeatAccountRegister() throws HttpException,
			IOException {

		userAndAccountRegister("wpk1902@gmail.com", "8611218773");
		assertTrue(response.indexOf("<Code>0</Code>") != -1);
		assertTrue(response.indexOf("<Account><ID>") != -1);

		accountAdd("wpk1902@gmail.com", "8611218773");
		assertTrue(response.indexOf("<Code>10520</Code>") != -1);
		
		userRemove();
		assertTrue(response.indexOf("<Code>0</Code>") != -1);

	}

	/**
	 * 测试账号添加、修改和删除.
	 * 
	 * @throws HttpException
	 * @throws IOException
	 */
	@Test
	public void testAccountOperate() throws HttpException, IOException {
		
		userRegister();
		assertTrue(response.indexOf("<Code>0</Code>") != -1);

		accountAdd("wpk1902@gmail.com", "8611218773");
		assertTrue(response.indexOf("<Code>0</Code>") != -1);
		assertTrue(response.indexOf("<Account><ID>") != -1);

		accountModify("wpk1902@gmail.com", "8611218773");
		assertTrue(response.indexOf("<Code>0</Code>") != -1);
		assertTrue(response.indexOf("<Account><ID>") != -1);

		accountRemove("wpk1902@gmail.com");
		assertTrue(response.indexOf("<Code>0</Code>") != -1);

		userRemove();
		assertTrue(response.indexOf("<Code>0</Code>") != -1);

	}
	
	/**
	 * 测试HTTP方式账号注册.
	 * @throws IOException 
	 * @throws HttpException 
	 */
	@Test
	public void testHttpAccountOperate() throws HttpException, IOException {
		
		userRegister();
		assertTrue(response.indexOf("<Code>0</Code>") != -1);

		httpAccountAdd("mengsun@pushmail.com", "honey@123", "192.168.20.53");
		assertTrue(response.indexOf("<Code>0</Code>") != -1);
		assertTrue(response.indexOf("<Account><ID>") != -1);
		
	}
	

	
	
	/**
	 * 得到用户测试字符串.
	 * 
	 * @return
	 */
	String getUserString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<User>");
		sb.append("<Name>12345</Name>");
		sb.append("<Passwd>12345</Passwd>");
		sb.append("<Gender>0</Gender>");
		sb.append("<Birthday>2008-05-06</Birthday>");
		sb.append("<Declaration>自动化测试</Declaration>");
		sb.append("<IMEI>sunmeng</IMEI>");
		sb.append("<DeviceModel>100,50</DeviceModel>");
		sb.append("<Signature>12qwqwqwqw</Signature>");
		sb.append("</User>");
		return sb.toString();
	}

	/**
	 * 得到账号测试字符串.
	 * 
	 * @param id
	 * @param username
	 * @param password
	 * @return
	 */
	String getAccountString(String id, String username, String password) {
		StringBuffer sb = new StringBuffer();
		sb.append("<Account>");

		if (id != null) {
			sb.append("<ID>");
			sb.append(id);
			sb.append("</ID>");
		}

		if (password != null) {
			sb.append("<Alias>自动化测试</Alias>");
			sb.append("<Name>");
			sb.append(username);
			sb.append("</Name>");
			sb.append("<Password>");
			sb.append(password);
			sb.append("</Password>");
		} else {
			sb.append("<Name>");
			sb.append(username);
			sb.append("</Name>");
		}
		sb.append("</Account>");
		return sb.toString();
	}
	
	/**
	 * 得到发送邮件测试字符串.
	 * @param id
	 * @param username
	 * @return
	 */
	String getSendEmailString(String from, String to, String messageId) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<Mail><Mail-head>");
		if (messageId != null) {
			sb.append("<MessageId>" + messageId + "</MessageId>");
		}
		sb.append("<Subject>dGVzdA==</Subject>");
		sb.append("<From>" + from + "</From>");
		sb.append("<To>" + to + "</To>");
		sb.append("<Forward>0</Forward></Mail-head>");
		sb.append("<Mail-body><Size>103456</Size>");
		sb.append("<Data-type></Data-type>");
		sb.append("<Data>dGVzdA==</Data>");
		sb.append("</Mail-body></Mail>");
			
		return sb.toString();
	}
	
	/**
	 * 得到http方式注册账号字符串.
	 * @return
	 */
	String getHttpAccountString(String name, String password, String host) {
		StringBuffer sb = new StringBuffer();

		sb.append("<Account>");
		sb.append("<Alias>ews</Alias>");
		sb.append("<Name>" + name + "</Name>");
		sb.append("<Password>" + password + "</Password>");
		sb.append("<SendHost>" + host + "</SendHost>");
		sb.append("<SendProtocolType>http</SendProtocolType>");
		sb.append("<ReceiveHost>" + host + "</ReceiveHost>");
		sb.append("<ReceiveProtocolType>http</ReceiveProtocolType>");
		sb.append("</Account>");
		
		return sb.toString();
	}

	/**
	 * 得到测试协议Header内容.
	 * 
	 * @param uid
	 * @return
	 */
	String getHeader(String uid) {
		StringBuffer sb = new StringBuffer();
		sb.append("<Header><Version>PushMail1.0</Version>");
		if (uid != null) {
			sb.append("<Cred><UUID>");
			sb.append(uid);
			sb.append("</UUID></Cred>");
		}
		sb.append("</Header>");
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

		String ss = sb.toString();
		if (ss.indexOf("<UUID>") != -1) {
			uid = ss.substring(ss.indexOf("<UUID>") + 6, ss.indexOf("</UUID>"));
		}
		System.out.println("uid is " + uid);

		if (ss.indexOf("<ID>") != -1) {
			aid = ss.substring(ss.indexOf("<ID>") + 4, ss.indexOf("</ID>"));
		}
		System.out.println("aid is " + aid);

		return sb.toString();
	}

	/**
	 * 单独用户注册.
	 * 
	 * @throws HttpException
	 * @throws IOException
	 */
	public void userRegister() throws HttpException, IOException {

		System.out.println("\nUser Register Test ---------------");

		pmstr = new PMString(getUserString());
		pmstr.wrapCmd("UserRegister").wrapTarget("User").wrap("Body").prefix(
				getHeader(null)).wrap("PushMail").prefix(xmlVersion);
		System.out.println(pmstr.toString());

		postMethod = new PostMethod("http://" + ip + ":" + port
				+ "/petasos/protocolExchange.do");
		postMethod.setRequestEntity(new StringRequestEntity(pmstr.toString()));
		stateLine = httpClient.executeMethod(postMethod);
		assertEquals(200, stateLine);
		response = getResponseString(postMethod.getResponseBodyAsStream());
		System.out.println(response);

	}

	/**
	 * 用户信息修改.
	 * 
	 * @throws HttpException
	 * @throws IOException
	 */
	public void userModify() throws HttpException, IOException {
		System.out.println("\nUser Modify Test ---------------");
		pmstr = new PMString(getUserString());
		pmstr.wrapCmd("UserModify").wrapTarget("User").wrap("Body").prefix(
				getHeader(uid)).wrap("PushMail").prefix(xmlVersion);
		System.out.println(pmstr.toString());

		postMethod = new PostMethod("http://" + ip + ":" + port
				+ "/petasos/protocolExchange.do");
		postMethod.setRequestEntity(new StringRequestEntity(pmstr.toString()));
		stateLine = httpClient.executeMethod(postMethod);
		assertEquals(200, stateLine);
		response = getResponseString(postMethod.getResponseBodyAsStream());
		System.out.println(response);
	}

	/**
	 * 用户删除.
	 * 
	 * @throws HttpException
	 * @throws IOException
	 */
	public void userRemove() throws HttpException, IOException {
		System.out.println("\nUser Remove Test ---------------");
		pmstr = new PMString("<Meta size=\"1\"><Item name=\"IMEI\">" + IMEI + "</Item></Meta>");
		pmstr.wrapCmd("AccountRemove").wrapTarget("User").wrap("Body").prefix(
				getHeader(uid)).wrap("PushMail").prefix(xmlVersion);
		System.out.println(pmstr.toString());

		postMethod = new PostMethod("http://" + ip + ":" + port
				+ "/petasos/protocolExchange.do");
		postMethod.setRequestEntity(new StringRequestEntity(pmstr.toString()));
		stateLine = httpClient.executeMethod(postMethod);
		assertEquals(200, stateLine);
		response = getResponseString(postMethod.getResponseBodyAsStream());
		System.out.println(response);
		
	}

	
	public void userLogin(String name, String password) throws HttpException, IOException {
		System.out.println("\nUser Login Test ---------------");
		pmstr = new PMString("<Name>" + name + "</Name><Passwd>" + password + "</Passwd><IMEI>" + IMEI + "</IMEI>");
		pmstr.wrap("User").wrapCmd("UserLogin").wrapTarget("User").wrap("Body").prefix(getHeader(null)).wrap("PushMail").prefix(xmlVersion);
		System.out.println(pmstr.toString());
		
		postMethod = new PostMethod("http://" + ip + ":" + port
				+ "/petasos/protocolExchange.do");
		postMethod.setRequestEntity(new StringRequestEntity(pmstr.toString()));
		stateLine = httpClient.executeMethod(postMethod);
		assertEquals(200, stateLine);
		response = getResponseString(postMethod.getResponseBodyAsStream());
		System.out.println(response);
	}
	
	/**
	 * 用户和账号同时注册
	 * 
	 * @throws HttpException
	 * @throws IOException
	 */
	public void userAndAccountRegister(String username, String password)
			throws HttpException, IOException {
		System.out.println("\nUser And Account Register Test ---------------");

		pmstr = new PMString(getUserString());
		pmstr.append(getAccountString(null, username, password));
		pmstr.wrapCmd("UserRegister").wrapTarget("User").wrap("Body").prefix(
				getHeader(null)).wrap("PushMail").prefix(xmlVersion);
		System.out.println(pmstr.toString());
		postMethod = new PostMethod("http://" + ip + ":" + port
				+ "/petasos/protocolExchange.do");
		postMethod.setRequestEntity(new StringRequestEntity(pmstr.toString()));
		stateLine = httpClient.executeMethod(postMethod);
		assertEquals(200, stateLine);
		response = getResponseString(postMethod.getResponseBodyAsStream());
		System.out.println(response);
	}

	/**
	 * 添加账号.
	 * 
	 * @param username
	 * @param password
	 * @throws HttpException
	 * @throws IOException
	 */
	public void accountAdd(String username, String password)
			throws HttpException, IOException {
		System.out.println("\nAccount Register Test ---------------");

		pmstr = new PMString(getAccountString(null, username, password));
		pmstr.wrapCmd("AccountAdd").wrapTarget("User").wrap("Body").prefix(
				getHeader(uid)).wrap("PushMail").prefix(xmlVersion);
		System.out.println(pmstr.toString());
		postMethod = new PostMethod("http://" + ip + ":" + port
				+ "/petasos/protocolExchange.do");
		postMethod.setRequestEntity(new StringRequestEntity(pmstr.toString()));
		stateLine = httpClient.executeMethod(postMethod);
		assertEquals(200, stateLine);
		response = getResponseString(postMethod.getResponseBodyAsStream());
		System.out.println(response);
	}
	
	/**
	 * 添加HTTP账号.
	 * 
	 * @param username
	 * @param password
	 * @throws HttpException
	 * @throws IOException
	 */
	public void httpAccountAdd(String username, String password, String host)
			throws HttpException, IOException {
		System.out.println("\nAccount Register Test ---------------");

		pmstr = new PMString(getHttpAccountString(username, password, host));
		pmstr.wrapCmd("AccountAdd").wrapTarget("User").wrap("Body").prefix(
				getHeader(uid)).wrap("PushMail").prefix(xmlVersion);
		System.out.println(pmstr.toString());
		postMethod = new PostMethod("http://" + ip + ":" + port
				+ "/petasos/protocolExchange.do");
		postMethod.setRequestEntity(new StringRequestEntity(pmstr.toString()));
		stateLine = httpClient.executeMethod(postMethod);
		assertEquals(200, stateLine);
		response = getResponseString(postMethod.getResponseBodyAsStream());
		System.out.println(response);
	}
	
	/**
	 * 修改账号
	 * @param username
	 * @param password
	 * @throws HttpException
	 * @throws IOException
	 */
	public void accountModify(String username, String password) throws HttpException, IOException {
		System.out.println("\nAccount Modify Test ---------------");
		pmstr = new PMString(getAccountString(aid, username, password));
		pmstr.wrapCmd("AccountModify").wrapTarget("User").wrap("Body").prefix(
				getHeader(uid)).wrap("PushMail").prefix(xmlVersion);
		System.out.println(pmstr.toString());

		postMethod = new PostMethod("http://" + ip + ":" + port
				+ "/petasos/protocolExchange.do");
		postMethod.setRequestEntity(new StringRequestEntity(pmstr.toString()));
		stateLine = httpClient.executeMethod(postMethod);
		assertEquals(200, stateLine);
		response = getResponseString(postMethod.getResponseBodyAsStream());
		System.out.println(response);
	}
	
	/**
	 * 删除账号.
	 * @param username
	 * @throws HttpException
	 * @throws IOException
	 */
	public void accountRemove(String username) throws HttpException, IOException {
		System.out.println("\nAccount Remove Test ---------------");
		pmstr = new PMString(getAccountString(aid, username, null));
		pmstr.wrapCmd("AccountRemove").wrapTarget("User").wrap("Body").prefix(
				getHeader(uid)).wrap("PushMail").prefix(xmlVersion);
		System.out.println(pmstr.toString());

		postMethod = new PostMethod("http://" + ip + ":" + port
				+ "/petasos/protocolExchange.do");
		postMethod.setRequestEntity(new StringRequestEntity(pmstr.toString()));
		stateLine = httpClient.executeMethod(postMethod);
		assertEquals(200, stateLine);
		response = getResponseString(postMethod.getResponseBodyAsStream());
		System.out.println(response);
	}
	
	/**
	 * 发送邮件.
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public void sendEmail(String username, String to, String messageId) throws HttpException, IOException {
		System.out.println("\nSend Email Test -------------");
		pmstr = new PMString(getSendEmailString(username, to, messageId));
		pmstr.prefix(getAccountString(aid, username, null)).wrapCmd("EmailSend").wrapTarget("Email").wrap("Body").prefix(getHeader(uid)).wrap("PushMail").prefix(xmlVersion);
		System.out.println(pmstr.toString());
		
		postMethod = new PostMethod("http://" + ip + ":" + port
				+ "/petasos/protocolExchange.do");
		postMethod.setRequestEntity(new StringRequestEntity(pmstr.toString()));
		stateLine = httpClient.executeMethod(postMethod);
		assertEquals(200, stateLine);
		response = getResponseString(postMethod.getResponseBodyAsStream());
		System.out.println(response);
	}
}
