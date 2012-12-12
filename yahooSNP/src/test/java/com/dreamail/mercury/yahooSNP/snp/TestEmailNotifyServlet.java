package com.dreamail.mercury.yahooSNP.snp;
//package com.dreamail.mercury.yahooSNP.snp;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.Writer;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ContentProducer;
//import org.apache.http.entity.EntityTemplate;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.junit.Test;
//
//public class TestEmailNotifyServlet {
//	@Test
//	public void notifyTest() {
//		String fn = "E:/protocol/abc.xml";
//		printResult(fn);
//	}
//
//	public void printResult(String fn) {
//		String result = getContent(getXmlString(fn));
//		System.out.println("result:\r\n" + result);
//	}
//
//	public String getContent(String msg) {
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpPost post = new HttpPost(
//				"http://192.168.20.210:8888/yahooSNP/emailNotify.do");
//		post.setEntity(createEntity(msg));
//		StringBuffer sb = new StringBuffer();
//		HttpEntity entity = null;
//		BufferedReader br = null;
//
//		try {
//			HttpResponse response = httpClient.execute(post);
//			entity = response.getEntity();
//			InputStream is = entity.getContent();
//			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
//			br = new BufferedReader(isr);
//
//			String str = br.readLine();
//			while (str != null) {
//				sb.append(str + "\r\n");
//				str = br.readLine();
//			}
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (br != null) {
//					br.close();
//				}
//				if (entity != null) {
//					entity.consumeContent();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return sb.toString();
//
//	}
//
//	public static String getXmlString(String fileName) {
//		StringBuffer sb = new StringBuffer();
//		File file = new File(fileName);
//		BufferedReader br = null;
//		System.out.println(file.getPath());
//		
//		try {
//			FileReader fr  = new FileReader(file);
//			br = new BufferedReader(fr);
//			String str = br.readLine();
//			while (str != null) {
//				sb.append(str + "\r\n");
//				str = br.readLine();
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//
//			try {
//				
//				if (br != null) {
//					br.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//		}
//
//		System.out.println("requestXml:\r\n" + sb.toString());
//		return sb.toString();
//	}
//
//	public HttpEntity createEntity(final String msg) {
//		ContentProducer cp = new ContentProducer() {
//			public void writeTo(OutputStream outstream) throws IOException {
//				Writer writer = new OutputStreamWriter(outstream, "UTF-8");
//				writer.write(msg);
//				writer.flush();
//				writer.close();
//			}
//		};
//		return new EntityTemplate(cp);
//	}
//
//}
