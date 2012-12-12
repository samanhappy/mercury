package com.dreamail.mercury.mail.truepush.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import com.dreamail.mercury.util.StreamUtil;

public class HotmailTest {
	private DefaultHttpClient client = new DefaultHttpClient();
	private String useName = "wpk1902@hotmail.com";
	private String password = "8611218773";

	{
		client.getCredentialsProvider().setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(useName, password));
	}

	@Test
	public void testLogin() throws ClientProtocolException, IOException {
		client.getCredentialsProvider().setCredentials(
				AuthScope.ANY,
				new UsernamePasswordCredentials("youlianjie@hotmail.com",
						"19840925"));
		HttpOptions request = new HttpOptions(
				"https://m.hotmail.com/Microsoft-Server-ActiveSync");
		HttpResponse response = client.execute(request);
		echoHeads(response);
		EntityUtils.consume(response.getEntity());
	}

	private void echoHeads(HttpResponse response) {
		System.out.println(response.getStatusLine());
		for (Header header : response.getAllHeaders()) {
			System.out.println(header.getName() + ":" + header.getValue());
		}
	}
	
//	@Test
	public void testAtt(Attachment a1) throws Exception{
		List<Attachment> attachmentsList = new ArrayList<Attachment>();
		attachmentsList.add(a1);
		for(Attachment attachment:attachmentsList){
			HttpPost attachmentPost = new HttpPost("https://m.hotmail.com/Microsoft-Server-ActiveSync?" +
					"User=aaa&DeviceId=aaa&DeviceType=Touchdown&" +
					"Cmd=GetAttachment&AttachmentName="+attachment.getAttName());
//			attachmentPost.setHeader("user-agent", "TouchDown(MSRPC)/7.1.00014/");
//			attachmentPost.setHeader("td-info", "com.nitrodesk.droid20.nitroid/7.1.00014/NON-PCF/");
//			attachmentPost.setHeader("connection", "keep-alive");
			attachmentPost.setHeader("MS-ASProtocolVersion", "2.5");
			attachmentPost.setHeader("X-MS-PolicyKey", "0");
//			attachmentPost.setHeader("authorization", "Basic eW91bGlhbmppZUBob3RtYWlsLmNvbToxOTg0MDkyNQ==");
			DefaultHttpClient newclient = new DefaultHttpClient();
			
			newclient.getCredentialsProvider().setCredentials(AuthScope.ANY,
	                new UsernamePasswordCredentials(useName, password));
			
			HttpResponse attachmentResponse = newclient.execute(attachmentPost);
			if(attachmentResponse != null){
				System.out.println("1------------------");
				echoHeads(attachmentResponse);
				System.out.println("2------------------");
				System.out.println(attachment.getDisplayName());
				System.out.println(attachment.getAttName());
				System.out.println(attachmentResponse.getEntity().getContentLength());
				System.out.println("3------------------");
				String content = new String(
						StreamUtil.getByteByInputStream(attachmentResponse.getEntity().getContent()),"gbk");
				System.out.println(content);
				System.out.println("4------------------");
			}
		}


	}

	@Test
	public void testAC() throws FileNotFoundException, XmlPullParserException,
			IOException {
		HttpPost post;
		HttpResponse response;
		IASResponse acResponse;
		String serverId = "00000000-0000-0000-0000-000000000001";
		post = new HttpPost(
				"https://m.hotmail.com/Microsoft-Server-ActiveSync?User=aaa&DeviceId=aaa&DeviceType=PocketPC&Cmd=Sync");
		Sync sync = new Sync();
		Collections collections = new Collections();
		List<Collection> collectionList = new ArrayList<Collection>();
		Collection collection = new Collection();
		collection.setSyncKey("0");
		collection.setCollectionId(serverId);
		collection.setClass_name("Email");
		collectionList.add(collection);
		collections.setCollections(collectionList);
		sync.setCollections(collections);
		post.setEntity(new ByteArrayEntity(ACWBXMLParser.getWBXML(sync)));
		post.setHeader("Content-Type", "application/vnd.ms-sync.wbxml");
		post.setHeader("MS-ASProtocolVersion", "2.5");
		post.setHeader("X-MS-PolicyKey", "0");
		response = client.execute(post);
		echoHeads(response);
		acResponse = ACWBXMLParser
				.parseWBXML(response.getEntity().getContent());
		System.out.println(acResponse.getName());

		String syncKey = null;
		Sync syncResponse = (Sync) acResponse;
		if (syncResponse != null
				&& syncResponse.getCollections() != null
				&& syncResponse.getCollections().getCollections() != null
				&& syncResponse.getCollections().getCollections().size() > 0
				&& "1".equals(syncResponse.getCollections().getCollections()
						.get(0).getStatus())) {
			syncKey = syncResponse.getCollections().getCollections().get(0)
					.getSyncKey();
		}
		if (syncKey == null) {
			return;
		}
//		String syncKey = "000000000{f1b14b57-1c39-4ee3-9fb3-42b322e40ec2}5";
//		Sync syncResponse = null;

		post = new HttpPost(
				"https://m.hotmail.com/Microsoft-Server-ActiveSync?User=aaa&DeviceId=aaa&DeviceType=PocketPC&Cmd=Sync");
		sync = new Sync();
		collections = new Collections();
		collectionList = new ArrayList<Collection>();
		collection = new Collection();
		System.out.println("11111111111:"+syncKey);
		collection.setSyncKey(syncKey);
		collection.setCollectionId(serverId);
		collection.setClass_name("Email");
		collection.setDeletesAsMoves("");
		collection.setGetChanges("");
		collection.setWindowSize(20);
		Options options = new Options();
		options.setFilterType("2");
		options.setMimeSupport("0");
//		options.setMimeTruncation("5");
		BodyPreference bodyPreference = new BodyPreference();
		bodyPreference.setType("1");
//		bodyPreference.setTruncationSize("10000");
		options.setBodyPreference(bodyPreference);
		collection.setOptions(options);
		collectionList.add(collection);
		collections.setCollections(collectionList);
		sync.setCollections(collections);
		post.setEntity(new ByteArrayEntity(ACWBXMLParser.getWBXML(sync)));
		post.setHeader("Content-Type", "application/vnd.ms-sync.wbxml");
		post.setHeader("MS-ASProtocolVersion", "2.5");
		post.setHeader("X-MS-PolicyKey", "0");
		response = client.execute(post);
		echoHeads(response);
		acResponse = ACWBXMLParser
				.parseWBXML(response.getEntity().getContent());
		System.out.println(acResponse.getName());
		Sync syncEmail = (Sync) acResponse;
		System.out.println("Date----------------"+syncEmail.getCollections().getCollections().get(0)
				.getCommands().getAdds().get(0).getApplicationData()
				.getDateReceived());

		syncKey = null;
		syncResponse = (Sync) acResponse;
		if (syncResponse != null
				&& syncResponse.getCollections() != null
				&& syncResponse.getCollections().getCollections() != null
				&& syncResponse.getCollections().getCollections().size() > 0
				&& "1".equals(syncResponse.getCollections().getCollections()
						.get(0).getStatus())) {
			syncKey = syncResponse.getCollections().getCollections().get(0)
					.getSyncKey();
		}
		if (syncKey == null) {
			return;
		}
		/*ApplicationData data = syncEmail.getCollections().getCollections().get(0).getCommands().getAdds().get(0).getApplicationData();
		System.out.println("--------------");
		System.out.println(data);
		List<Attachment> list = data.getAttachments().getAttachments();
		System.out.println(list);
		try {
			testAtt(list.get(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*post = new HttpPost(
				"https://m.hotmail.com/Microsoft-Server-ActiveSync?User=youlianjie&DeviceId=6F24CAD599A5BF1A690246B8C68FAE8D&DeviceType=PocketPC&Cmd=Sync");
		sync = new Sync();
		collections = new Collections();
		collectionList = new ArrayList<Collection>();
		collection = new Collection();
		System.out.println("22222222:"+syncKey);
		collection.setSyncKey(syncKey);
		collection.setCollectionId(serverId);
		collection.setClass_name("Email");
		collection.setDeletesAsMoves("");
		collection.setGetChanges("");
		collection.setWindowSize(5);
		options = new Options();
		options.setFilterType("1");
		options.setMimeSupport("1");
		options.setMimeTruncation("5");
		collection.setOptions(options);
		collectionList.add(collection);
		collections.setCollections(collectionList);
		sync.setCollections(collections);
		post.setEntity(new ByteArrayEntity(ACWBXMLParser.getWBXML(sync)));
		post.setHeader("Content-Type", "application/vnd.ms-sync.wbxml");
		post.setHeader("MS-ASProtocolVersion", "2.5");
		post.setHeader("X-MS-PolicyKey", "0");
		response = client.execute(post);
		echoHeads(response);
		acResponse = ACWBXMLParser
				.parseWBXML(response.getEntity().getContent());
		System.out.println(acResponse.getName());
		syncEmail = (Sync) acResponse;
		System.out.println(syncEmail.getCollections().getCollections().get(0)
				.getCommands().getAdds().get(0).getApplicationData()
				.getSubject());*/
	}

	private FolderSync folderSync() throws XmlPullParserException,
			FileNotFoundException, IOException, ClientProtocolException {
		HttpPost post = getPost("FolderSync");

		FolderSync fs = new FolderSync();
		fs.setSyncKey("0");
		post.setEntity(new ByteArrayEntity(ACWBXMLParser.getWBXML(fs)));

		HttpResponse response = client.execute(post);
		echoHeads(response);
		IASResponse acResponse = ACWBXMLParser.parseWBXML(response.getEntity()
				.getContent());
		System.out.println(acResponse.getName());

		FolderSync fsResponse = (FolderSync) acResponse;
		return fsResponse;
	}

	private HttpPost getPost(String cmdName) {
		HttpPost post = new HttpPost(
				"https://m.hotmail.com/Microsoft-Server-ActiveSync?User=youlianjie&DeviceId=6F24CAD599A5BF1A690246B8C68FAE8D&DeviceType=PocketPC&Cmd="
						+ cmdName);
		post.setHeader("Content-Type", "application/vnd.ms-sync.wbxml");
		post.setHeader("MS-ASProtocolVersion", "2.5");
		post.setHeader("X-MS-PolicyKey", "0");
		return post;
	}

	@Test
	public void testPing() throws FileNotFoundException,
			ClientProtocolException, XmlPullParserException, IOException {
		FolderSync fs = folderSync();

		Add emailAdd = null;
		if (fs != null && "1".equals(fs.getStatus()) && fs.getChanges() != null
				&& fs.getChanges().getAdd() != null
				&& fs.getChanges().getAdd().size() > 0) {
			for (Add add : fs.getChanges().getAdd()) {
				if ("2".equals(add.getType())) {
					emailAdd = add;
					break;
				}
			}
		}

		if (emailAdd == null) {
			return;
		}

		ping(emailAdd);
	}

	private void ping(final Add emailAdd) throws XmlPullParserException,
			FileNotFoundException, IOException, ClientProtocolException {
		HttpPost post = getPost("Ping");
		Ping ping = new Ping();
		ping.setHeartbeatInterval(600);
		Folders folders = new Folders();
		ArrayList<Folder> folderList = new ArrayList<Folder>();
		Folder folder = new Folder();
		folder.setId(emailAdd.getServerId());
		folder.setClass_name("Email");
		folderList.add(folder);
		folders.setFolders(folderList);
		ping.setFolders(folders);
		post.setEntity(new ByteArrayEntity(ACWBXMLParser.getWBXML(ping)));
		client.execute(post, new ResponseHandler<IASResponse>() {
			public IASResponse handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				echoHeads(response);
				IASResponse acResponse;
				try {
					acResponse = ACWBXMLParser.parseWBXML(response.getEntity()
							.getContent());
					System.out.println(acResponse.getName());

					ping(emailAdd);

					return acResponse;
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	@Test
	public void testParseMime() throws MessagingException {
		MimeMessage mm = new MimeMessage(null, this.getClass().getClassLoader()
				.getResourceAsStream("mailContent.txt"));
		assertNotNull(mm);
		assertEquals("test", mm.getSubject());

		mm = new MimeMessage(null, this.getClass().getClassLoader()
				.getResourceAsStream("mailContent_att.txt"));
		assertNotNull(mm);
		assertEquals("Fw: att_test", mm.getSubject());
		System.out.println(mm.getSize());

		mm = new MimeMessage(null, this.getClass().getClassLoader()
				.getResourceAsStream("mailContent_att_part.txt"));
		assertNotNull(mm);
		assertEquals("Fw: att_test", mm.getSubject());
		System.out.println(mm.getSize());
	}

	public static void main(String[] args) throws XmlPullParserException,
			IOException {

		// HttpPost post = new HttpPost(
		// "https://m.hotmail.com/Microsoft-Server-ActiveSync?User=deviceuser&DeviceId=6F24CAD599A5BF1A690246B8C68FAE8D&DeviceType=PocketPC&Cmd=FolderSync");
		HttpOptions request = new HttpOptions(
				"https://m.hotmail.com/Microsoft-Server-ActiveSync");
		request.setHeader("Content-Type", "application/vnd.ms-sync.wbxml");
		request.setHeader("MS-ASProtocolVersion", "2.5");
		request.setHeader("X-MS-PolicyKey", "0");
		// post.setEntity(new FileEntity(new File("D:/tmp/hotmailprotocol.txt"),
		// "UTF-8"));

		// InputStream byteIn = HotmailTest.class.getClassLoader()
		// .getResourceAsStream("youlianjie/hotmail/byte.txt");
		// int length = byteIn.available();
		// byte[] inb = new byte[length];
		// byteIn.read(inb);

		// request.setEntity(new ByteArrayEntity(KxmlTest.getWBXML()));
		// post.setEntity(new ByteArrayEntity(new
		// byte[]{0x03,0x01,0x6a,0x00,0x07,0x56,0x52,0x03,0x32,0x00,0x01,0x01}));
		// HttpResponse response = null;
		// try {
		// response = client.execute(request);
		// System.out.println(response.getStatusLine());
		// for (Header header : response.getAllHeaders()) {
		// System.out.println(header.getName() + ":" + header.getValue());
		// }
		// KxmlTest.parseWBXML(response.getEntity().getContent());
		// } catch (ClientProtocolException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	public static HttpClient wrapClient(HttpClient base) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = base.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", ssf, 443));
			return new DefaultHttpClient(ccm, base.getParams());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 | _b1);
		return ret;
	}

	public static byte[] HexString2Bytes(String src) {
		src = src.replace(" ", "");
		src = src.replace("\r", "");
		src = src.replace("\n", "");
		src = src.replace("\r\n", "");
		byte[] tmp = src.getBytes();
		byte[] ret = new byte[tmp.length / 2];
		for (int i = 0; i < tmp.length / 2; ++i) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
			System.out.println(new String(new byte[] { ret[i] }) + " "
					+ new String(new byte[] { tmp[i * 2] })
					+ new String(new byte[] { tmp[i * 2 + 1] }));
		}
		return ret;
	}
}
