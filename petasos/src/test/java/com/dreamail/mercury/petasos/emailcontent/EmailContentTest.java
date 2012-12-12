package com.dreamail.mercury.petasos.emailcontent;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Test;

import com.dreamail.mercury.cache.MemCachedManager;
import com.dreamail.mercury.domain.qwert.PushMail;
import com.dreamail.mercury.petasos.IFunctionDispatcher;
import com.dreamail.mercury.petasos.EmailList.EmailListTest;
import com.dreamail.mercury.petasos.impl.HandlerDispatcher;


public class EmailContentTest {
	/**
     * Rigourous Test :-)
     */
    @Test
    public void testEmailContent() throws JiBXException, FileNotFoundException {
        java.util.logging.Logger.getLogger(EmailListTest.class.getName()).log(Level.INFO, (this.getClass().getResource("/").getPath()));

        IBindingFactory bfact = BindingDirectory.getFactory(PushMail.class);
        // unmarshal customer information from file
        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
        FileInputStream in = new FileInputStream(this.getClass().getResource("/").getPath() + File.separator + "EmailContent.xml");
        PushMail customer = (PushMail) uctx.unmarshalDocument(in, null);
        IFunctionDispatcher fd = new HandlerDispatcher();
        try {
			customer  = fd.dispatch(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        IMarshallingContext mctx = bfact.createMarshallingContext();
        mctx.setIndent(2);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();        
        mctx.marshalDocument(customer, "UTF-8", null, baos);
        String s = baos.toString();
        System.out.println("===="+s);
    }
    //获取文本内容（正确逻辑应为 正确获取内容，在缓存未失效时不下载，在缓存失效时不下载其他无关的东西）
    
    
    @Test
	public void testGetEmailContent() {
		String url = null;
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = getMethod.getResponseBody();
			// 处理内容
			System.out.println(new String(responseBody));
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
	}
    @SuppressWarnings("deprecation")
	@Test
    public void testGetEmailContent1() {
    	String url = "http://192.168.20.210:9080/petasos/protocolExchange.do";
//    	String url = "http://58.64.174.228:8080/petasos/protocolExchange.do";
    	String response = null;
    	HttpClient httpClient = new HttpClient();
    	PostMethod postMethod = new PostMethod(url);
    	postMethod.setRequestBody(getPushMailString());
    	int statusCode = -1;
		try {
			statusCode = httpClient.executeMethod(postMethod);
			assertEquals(200, statusCode);
			response = getResponseString(postMethod.getResponseBodyAsStream());
			System.out.println(response);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    @Test
    public void removeEmailCacheById(){
    	MemCachedManager.getInstance().init();
    	System.out.println(MemCachedManager.getMcc().get("emailCacheObject_"+246)+"------------------");
    	boolean flag = MemCachedManager.getMcc().delete("emailCacheObject_"+246);
    	System.out.println(flag+"--------------------------");
    	System.out.println(MemCachedManager.getMcc().get("emailCacheObject_"+246)+"------------------");
    }
    public String getPushMailString(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("<?xml version='1.0' encoding='UTF-8'?>");
    	sb.append("<PushMail>");
    	sb.append("<Header>");
    	sb.append("<Version>PushMail1.0</Version>");
    	sb.append("<Cred>");
    	sb.append("<UUID>2</UUID>");
    	sb.append("</Cred>");
    	sb.append("</Header>");
    	sb.append("<Body>");
    	sb.append("<Target name='Email' isAtom='true'>");
    	sb.append("<Cmd name='EmailContent'>");
    	sb.append("<Meta size='2'>");
    	sb.append("<Item name='IMEI'>192.168.20.12</Item>");
    	sb.append("<Item name='SeqNo'>1</Item>");
    	sb.append("</Meta>");
    	sb.append("<Account>");
    	sb.append("<ID>21</ID>");
    	sb.append("</Account>");
    	sb.append("<Mail>");
    	sb.append("<Mail-head>");
    	sb.append("<MessageId>246</MessageId>");
    	sb.append("</Mail-head>");
    	sb.append("</Mail>");
    	sb.append("</Cmd>");
    	sb.append("</Target>");
    	sb.append("</Body>");
    	sb.append("</PushMail>");
    	System.out.println(sb.toString()+"-------------------");
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
