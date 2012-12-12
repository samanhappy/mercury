package com.dreamail.mercury.ews;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.xml.datatype.XMLGregorianCalendar;
//
//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.NTCredentials;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.params.AuthPolicy;
//import org.apache.http.client.protocol.ClientContext;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.protocol.BasicHttpContext;
//import org.apache.http.protocol.HttpContext;
//import org.apache.http.util.EntityUtils;
//
//import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
//
//public class EWSTest {
//	public EWSTest() {
//	}
//
//	private static final String BASIC_AUTHENTICATION = "basic";
//
//	public String createCalendarItem(String server, final String user,
//			final String password, String description, String location,
//			XMLGregorianCalendar startDate, XMLGregorianCalendar endDate) {
//
//		// System.setProperty("http.auth.preference", BASIC_AUTHENTICATION);
//		// System.setProperty("http.auth.digest.validateServer", "false");
//		// System.setProperty("http.auth.digest.validateProxy", "false");
//		//
//		// Authenticator.setDefault(new Authenticator() {
//		//
//		// @Override
//		// protected PasswordAuthentication getPasswordAuthentication() {
//		// System.out.println("Authentication...");
//		// System.out.print("Requesting Prompt: ");
//		// System.out.println(this.getRequestingPrompt());
//		// System.out.print("Requesting Scheme: ");
//		// System.out.println(this.getRequestingScheme());
//		// System.out.print("Requestor Type: ");
//		// System.out.println(this.getRequestorType());
//		//
//		// return new PasswordAuthentication(user, password.toCharArray());
//		// }
//		//
//		// });
//
//		StringBuffer URL = new StringBuffer();
//		URL.append("https://");
//		URL.append(server);
//		URL.append("/ews/Exchange.asmx");
//		String result = null;
//		StringBuffer request = new StringBuffer();
//		request.append("<CreateItem \n");
//		request
//				.append("               SendMeetingInvitations=\"SendToNone\"\n");
//		request
//				.append("               xmlns=\"http://schemas.microsoft.com/exchange/services/2006/messages\"\n");
//		request
//				.append("               xmlns:t=\"http://schemas.microsoft.com/exchange/services/2006/types\">\n");
//		request.append("    <Items>\n");
//		request.append("        <t:CalendarItem>\n");
//		request.append("            <t:Subject>");
//		request.append(description);
//		request.append("</t:Subject>\n");
//		if (startDate != null) {
//			request.append("            <t:Start>");
//			request.append(startDate.toXMLFormat());
//			System.out.println(startDate.toXMLFormat());
//			request.append("</t:Start>\n");
//		}
//		if (endDate != null) {
//			request.append("            <t:End>");
//			request.append(endDate.toXMLFormat());
//			request.append("</t:End>\n");
//		}
//		if (location != null) {
//			request.append("            <t:Location>");
//			request.append(location);
//			request.append("</t:Location>\n");
//		}
//		request.append("        </t:CalendarItem>\n");
//		request.append("    </Items>\n");
//		request.append("</CreateItem>\n");
//
//		try {
//			// Now we create the output document.
//			result = rawRawSoapRequest(URL.toString(), request, user, password);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return result;
//
//	}
//
//	private String rawRawSoapRequest(String url, StringBuffer requestString,
//			String user, String password) throws Exception {
//		// Build the soap envelope.
//		StringBuilder builder = new StringBuilder();
//		builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
//		builder
//				.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
//		builder
//				.append("               xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n");
//		builder
//				.append("               xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"\n");
//		builder
//				.append("               xmlns=\"http://schemas.microsoft.com/exchange/services/2006/messages\"\n");
//		builder
//				.append("               xmlns:t=\"http://schemas.microsoft.com/exchange/services/2006/types\">\n");
//		builder.append("<soap:Body>\n");
//		builder.append(requestString);
//		builder.append("</soap:Body>\n");
//		builder.append("</soap:Envelope>\n");
//		System.out.println(builder.toString());
//
//		/*
//		 * String currentScheme = System.getProperty("http.auth.preference"); if
//		 * (currentScheme != null) { System.out.print("Current Scheme: ");
//		 * System.out.println(currentScheme); } else {
//		 * System.out.print("Current Scheme: "); System.out.println("not set");
//		 * }
//		 * 
//		 * URL ewsURL = new URL(url); HttpsURLConnection ewsConn =
//		 * (HttpsURLConnection) ewsURL .openConnection();
//		 * ewsConn.setRequestMethod("POST");
//		 * ewsConn.setRequestProperty("Content-type", "text/xml;utf-8");
//		 * ewsConn.setDoInput(true); ewsConn.setDoOutput(true);
//		 * 
//		 * PrintWriter pout = new PrintWriter(new OutputStreamWriter(ewsConn
//		 * .getOutputStream(), "UTF-8"), true); pout.print(builder.toString());
//		 * pout.flush(); pout.close();
//		 * 
//		 * if (ewsConn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
//		 * BufferedReader bin = new BufferedReader(new InputStreamReader(
//		 * ewsConn.getInputStream()));
//		 * 
//		 * StringBuilder result = new StringBuilder(); String line; while ((line
//		 * = bin.readLine()) != null) result.append(line);
//		 * 
//		 * return result.toString(); } else { StringBuilder result = new
//		 * StringBuilder(); result.append("Bad post... Code: ");
//		 * result.append(ewsConn.getResponseCode()); result.append(" - ");
//		 * result.append(ewsConn.getResponseMessage()); return
//		 * result.toString(); }
//		 */
//
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//		httpclient.getAuthSchemes().register("ntlm", new NTLMSchemeFactory());
//		httpclient.getCredentialsProvider().setCredentials(
//				new AuthScope("192.168.20.11", -1),  
//			    new NTCredentials(user,password, "192.168.20.11", "pushmail"));
////		NTCredentials ntcred = new NTCredentials(user, password, null, null);
////		CredentialsProvider cred = new BasicCredentialsProvider();
////		cred.setCredentials(AuthScope.ANY, ntcred);
////		// cred.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
////		// user, password));
////		httpclient.setCredentialsProvider(cred);
//
////		HttpContext context = new BasicHttpContext();
////		CredentialsProvider credsProvider = new BasicCredentialsProvider();
////		credsProvider.setCredentials(AuthScope.ANY, new NTCredentials(user,
////				password, "", ""));
////		context.setAttribute(ClientContext.CREDS_PROVIDER, credsProvider);
//
//		HttpResponse response = null;
//		HttpEntity entity = null;
//		HttpPost httppost = new HttpPost(url);
//		httppost.setHeader("Content-Type", "text/xml;utf-8");
////		httppost.setHeader("Depth", "0");
////		httppost.setHeader("Brief", "t");
//		httppost.setEntity(new StringEntity(builder.toString()));
//
//		response = httpclient.execute(httppost);
////		response.setHeader("Content-Type", "text/xml;utf-8");
//		entity = response.getEntity();
//		try {
//			System.out.println("----------------------------------------");
//			System.out.println(new String(response.getStatusLine().toString()
//					.getBytes("iso8859-1"), "utf-8"));
//			Header[] headers = response.getAllHeaders(); // 返回的HTTP头信息
//			for (int i = 0; i < headers.length; i++) {
//				System.out.println(headers[i]);
//			}
//			System.out.println("----------------------------------------");
//			String responseString = null;
//			if (response.getEntity() != null) {
//				responseString = EntityUtils.toString(response.getEntity()); // 返回服务器响应的HTML代码
//				System.out.println(new String(responseString
//						.getBytes("iso8859-1"), "gb2312")); // 打印出服务器响应的HTML代码
//			}
//		} finally {
//			if (entity != null)
//				entity.consumeContent();
//		}
//		System.out.println("Login form get: "
//				+ new String(response.getStatusLine().toString().getBytes(
//						"iso8859-1"), "utf-8"));
//		if (entity != null) {
//			entity.consumeContent();
//		}
//
//		return null;
//
//	}
//
//	public static void main(String[] args) {
//		XMLGregorianCalendar c1 = new XMLGregorianCalendarImpl();
//		c1.setYear(2010);
//		c1.setMonth(10);
//		c1.setDay(21);
//		c1.setHour(9);
//		c1.setMinute(30);
//		c1.setSecond(0);
//
//		XMLGregorianCalendar c2 = new XMLGregorianCalendarImpl();
//		c2.setYear(2010);
//		c2.setMonth(10);
//		c2.setDay(21);
//		c2.setHour(10);
//		c2.setMinute(30);
//		c2.setSecond(0);
//
//		new EWSTest().createCalendarItem("admin-h1li7jsv2", "danwang",
//				"honey@123", "there is a domo", "nanjing archermind office",
//				c1, c2);
//	}
//
//}
