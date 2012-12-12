package com.dreamail.mercury.yahooSNP.snp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.yahooSNP.SNPContext;
import com.dreamail.mercury.yahooSNP.YahooArithmetic;
import com.dreamail.mercury.yahooSNP.impl.SNPLogin;

public class YahooSNPTest {

	String partnerId = "mobile035";
	String username = "kai_li_mind_1@yahoo.com";
	String password = "archermind";
	String secretKey = "ueB7n0Ceb7rDgtqC3J1QdCX0PGhMeFdttWUwUcvygac-";
	String sharedKey = "9P8FSQMvDTW.pOmr6CYgP0Beq1DGc13Gi_hGjrspj1o-";
	String token = "AHZDh01LCaeoOgIMJlM0pazmbX9FfB9PqM6Q7VeF71fwsTyJjgoncbE-";
	String notifyUrl = "http://58.64.174.229:8888/petasos/protocolExchange.do";
	String deviceID = "";
	String wssid = "AuV2ihKc5ME";

	/**
	 * 获取HttpClient实体
	 */
	public DefaultHttpClient getClient() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		// 设置代理对象 ip/代理名称,端口
//		HttpHost proxy = new HttpHost("drproxy001.archermind.com", 3128);
		// 实例化验证
//		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		// 设定验证内容
//		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
//				"000618", "meng,2011");
		// 创建验证
//		credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST,
//				AuthScope.ANY_PORT), creds);
//		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
//				proxy);
//		httpclient.setCredentialsProvider(credsProvider);

		return httpclient;
	}

	@Test
	public void testGetNewMailList() {
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><PushMail><Header><Version>PushMail1.0</Version><Cred><UUID>7</UUID></Cred></Header><Body><Target name=\"Email\" isAtom=\"true\"><Cmd name=\"EmailList\"><Meta size=\"3\"><Item name=\"IMEI\">355629040001635_3556290400016430000000000000000_</Item><Item name=\"TimeDate\">2011-06-24 10:06:46</Item><Item name=\"SeqNo\">1</Item></Meta></Cmd></Target></Body></PushMail>";
		String url = "http://58.64.174.228/petasos/protocolExchange.do";
		HttpClient client = getClient();
		HttpPost post = new HttpPost(url);
		try {
			post.setEntity(new StringEntity(str));
			HttpResponse response = client.execute(post);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String s = br.readLine();
			while (s != null) {
				System.out.println(s);
				s = br.readLine();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLoginUsingToken() {
		new PropertiesDeploy().init();
		SNPContext context = new SNPLogin().getLoginContext(token);
		System.out.println(context.getY_cookie());
		System.out.println(context.getT_cookie());
	}

	@Test
	public void pwtoken_get() {
		String ts = getTs();
		String url = "https://login.yahoo.com/config/pwtoken_get?src="
				+ partnerId + "&ts=" + ts + "&login=" + username + "&passwd="
				+ password + "&sig=" + getTokenSig(ts);
		System.out.println(url);
		HttpClient client = getClient();
		HttpPost post = new HttpPost(url);
		try {
			HttpResponse response = client.execute(post);
			token = getToken(response.getEntity());
			System.out.println(token);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void pwtoken_login() {
		String ts = getTs();
		String url = "https://login.yahoo.com/config/pwtoken_login?src="
				+ partnerId + "&ts=" + ts + "&token=" + token + "&sig="
				+ getLoginSig(ts);
		System.out.println(url);
		DefaultHttpClient client = getClient();
		HttpPost post = new HttpPost(url);
		try {
			HttpResponse response = client.execute(post);
			System.out.println(getResponse(response.getEntity()));
			CookieStore cookie = client.getCookieStore();
			List<org.apache.http.cookie.Cookie> list = cookie.getCookies();
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getName());
				System.out.println(list.get(i).getValue());
			}
			System.out.println(client.getCookieStore());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void subscripetion() {

		String ts = getTs();
		String url = "https://login.yahoo.com/config/pwtoken_login?src="
				+ partnerId + "&ts=" + ts + "&token=" + token + "&sig="
				+ getLoginSig(ts);
		DefaultHttpClient client1 = new DefaultHttpClient();
		DefaultHttpClient client = new DefaultHttpClient();

		HttpPost post = new HttpPost(url);
		try {
			HttpResponse response = client1.execute(post);
			String str = getResponse(response.getEntity());
			System.out.println(str);
			wssid = str.substring(str.indexOf("crumb") + 6);
			System.out.println(wssid);

			client.setCookieStore(client1.getCookieStore());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ts = getTs();
		String content = getSubscripetionContent(ts);
		System.out.println(content);
		url = "http://api.snpedit.mobile.yahoo.com/snp/" + "alcatel"
				+ "/V2/subscribe?sign=" + getSubscripetionSig(content);
		System.out.println(url);

		HttpPost post1 = new HttpPost(url);
		try {
			post1.setEntity(new StringEntity(content));
			System.out.println(client.getCookieStore());
			HttpResponse response = client.execute(post1);
			System.out.println(getResponse(response.getEntity()));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getTs() {
		return String.valueOf(Calendar.getInstance().getTimeInMillis() / 1000);
	}

	private String getTokenSig(String ts) {
		String tokenStr = partnerId + ts + username + password + secretKey;
		String hmacToken = null;
		try {
			hmacToken = YahooArithmetic.yahoo64(YahooArithmetic.md5(tokenStr));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hmacToken;
	}

	private String getLoginSig(String ts) {
		String tokenStr = partnerId + ts + token + secretKey;
		String hmacToken = null;
		try {
			hmacToken = YahooArithmetic.yahoo64(YahooArithmetic.md5(tokenStr));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hmacToken;
	}

	public String getSubscripetionSig(String subContent) {
		String signature = null;
		try {
			signature = YahooArithmetic.yahoo64(YahooArithmetic.md5(subContent
					+ sharedKey));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return signature;

	}

	public static void main(String[] args) {

		String Y = "Y=v=1&n=7chcl23ju0o7n&l=a08_b8_c8d3_rv/o&p=m2gvvbj012000000&iz=&r=ms&lg=en-US&intl=us&np=1";
		String T = " T=z=8zX/NB8H/DOBQ/P82y1iXecNjU3NAY2MzE0MTFOTjcxMTM2ND&a=QAE&sk=DAA.bUE0CU6hjH&ks=EAAgrk5ZRc70x0rL87xpT14FQ--~E&d=c2wBTVRJd013RXhORFl6TmpZNU9UQTJOalF4TXpRMk9RLS0BYQFRQUUBZwFWSk1XNVJLWjdONUJUU1lHUEE0QkFDUE9OWQF6egE4elgvTkJBN0UBdGlwAWRMT0JMQQ--";
		String ts = " ts="
				+ String
						.valueOf(Calendar.getInstance().getTimeInMillis() / 1000);
		String src = " src=mobile035";
		String cid = " cid=";

		System.out.println(EmailUtils
				.changeByteToBase64((Y + T + ts + src + cid).getBytes()));

		System.out
				.println(EmailUtils
						.changeByteToBase64("v=1&n=7chcl23ju0o7n&l=a08_b8_c8d3_rv/o&p=m2gvvbj012000000&iz=&r=ms&lg=en-US&intl=us&np=1"
								.getBytes()));
		System.out
				.println(EmailUtils
						.changeByteToBase64("z=8zX/NB8H/DOBQ/P82y1iXecNjU3NAY2MzE0MTFOTjcxMTM2ND&a=QAE&sk=DAA.bUE0CU6hjH&ks=EAAgrk5ZRc70x0rL87xpT14FQ--~E&d=c2wBTVRJd013RXhORFl6TmpZNU9UQTJOalF4TXpRMk9RLS0BYQFRQUUBZwFWSk1XNVJLWjdONUJUU1lHUEE0QkFDUE9OWQF6egE4elgvTkJBN0UBdGlwAWRMT0JMQQ--"
								.getBytes()));
	}

	/**
	 * 获得响应实体的内容
	 * 
	 * @return
	 */
	public String getToken(HttpEntity responseEntity) {
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = responseEntity.getContent();
			if (is == null) {
				return null;
			} else {
				InputStreamReader in = new InputStreamReader(is, "utf-8");
				br = new BufferedReader(in);

				String str = br.readLine();
				while (str != null) {
					System.out.println(str);
					str = br.readLine();
				}

			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				responseEntity.consumeContent();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获得响应实体的内容
	 * 
	 * @return
	 */
	public String getResponse(HttpEntity responseEntity) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			InputStream is = responseEntity.getContent();
			InputStreamReader in = new InputStreamReader(is, "utf-8");
			br = new BufferedReader(in);

			String str = br.readLine();
			while (str != null) {
				sb.append(str);
				str = br.readLine();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				responseEntity.consumeContent();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public String getSubscripetionContent(String ts) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0'?>");
		sb
				.append("<s:request xmlns:s='urn:yahoo:mob:alert:subscription' wssid='");
		sb.append(wssid);
		sb.append("' ver='2.1' pid='").append(partnerId).append("' ts='")
				.append(ts).append("'>");
		sb.append("<s:mailsubscription>");
		sb.append("<s:subscribe notifyURL='" + notifyUrl
				+ "' notifyInfo='opaque-data'>");
		sb.append("<s:events>");
		sb.append("<s:mailevent eventgroup='grp_newmail' " + "devid='"
				+ deviceID + "'/>");
		sb.append("</s:events>");
		sb.append("</s:subscribe>");
		sb.append("</s:mailsubscription>");
		sb.append("</s:request>");
		return sb.toString();
	}
}
