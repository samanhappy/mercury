package com.dreamail.mercury.petasos.register;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

public class ProtocolTest {
	// 用户注册
	@Test
	public void userReg() {
		String fn = "E://protocol/registerUser.xml";
		printResult(fn);
	}

	// 用户登录
	@Test
	public void userLogin() {
		String fn = "E://protocol/userLogin.xml";
		printResult(fn);
	}

	// 删除用户
	@Test
	public void userRemove() {
		String fn = "E://protocol/removeUser.xml";
		printResult(fn);
	}

	// 同步用户信息
	@Test
	public void sync() {
		String fn = "E://protocol/sync.xml";
		printResult(fn);
	}

	// 增加账号
	@Test
	public void accReg() {
		String fn = "E://protocol/addAccount.xml";
		printResult(fn);
	}

	// 修改账号
	@Test
	public void accModify() {
		String fn = "E://protocol/AccountModify.xml";
		printResult(fn);
	}

	// 删除账号
	@Test
	public void accRemove() {
		String fn = "E://protocol/removeAccount.xml";
		printResult(fn);
	}

	// 获取用户的邮件列表
	@Test
	public void getEmailList() {
		String fn = "E://protocol/EmailList.xml";
		printResult(fn);
	}

	// 获取邮件正文
	@Test
	public void getEmailContent() {
		String fn = "E://protocol/EmailContent.xml";
		printResult(fn);
	}

	// 获取单封附件内容
	@Test
	public void getAttContent() {
		String fn = "E://protocol/EmailAttachment.xml";
		printResult(fn);
	}

	// 发送单封邮件
	@Test
	public void sendMail() {
		String fn = "E://protocol/sendEMail.xml";
		printResult(fn);
	}

	// 回复邮件
	@Test
	public void reMail() {
		String fn = "E://protocol/reMail.xml";
		printResult(fn);
	}
	
	// 转发收件箱邮件
	@Test
	public void forwardMail_inbox() {
		String fn = "E://protocol/forwardMail_inbox.xml";
		printResult(fn);
	}

	// 转发已发送邮件
	@Test
	public void forwardMail_send() {
		String fn = "E://protocol/forwardMail_send.xml";
		printResult(fn);
	}

	public void printResult(String fn) {
		String result = getContent(getXmlString(fn));
		System.out.println("result:\r\n" + result);
	}

	public HttpEntity createEntity(final String msg) {
		ContentProducer cp = new ContentProducer() {
			public void writeTo(OutputStream outstream) throws IOException {
				Writer writer = new OutputStreamWriter(outstream, "UTF-8");
				writer.write(msg);
				writer.flush();
				writer.close();
			}
		};
		return new EntityTemplate(cp);
	}

	public String getContent(String msg) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(
				"http://192.168.20.210:9080/petasos/protocolExchange.do");

		post.setEntity(createEntity(msg));
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		InputStreamReader isr = null;
		HttpEntity entity = null;
		BufferedReader br = null;

		try {
			HttpResponse response = httpClient.execute(post);
			entity = response.getEntity();
			is = entity.getContent();
			GZIPInputStream gzin = new GZIPInputStream(is);
			isr = new InputStreamReader(gzin, "UTF-8");
			br = new BufferedReader(isr);

			String str = br.readLine();
			while (str != null) {
				sb.append(str + "\r\n");
				str = br.readLine();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null){
					br.close();
				}
				if(isr!=null){					
					isr.close();
				}
				if(is!=null){
					
					is.close();
				}
				if(entity!=null){
					
					entity.consumeContent();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();

	}

	public static String getXmlString(String fileName) {
		StringBuffer sb = new StringBuffer();
		File file = new File(fileName);
		System.out.println(file.getPath());
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String str = br.readLine();
			while (str != null) {
				sb.append(str + "\r\n");
				str = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("requestXml:\r\n" + sb.toString());
		return sb.toString();
	}

}
