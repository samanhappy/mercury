package com.dreamail.mercury.mail.receiver;

import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.danga.MemCached.SockIOPool;
import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.dal.service.MessageService;
import com.dreamail.mercury.domain.Body;
import com.dreamail.mercury.domain.Email;
import com.dreamail.mercury.domain.EmailCacheObject;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.domain.WebEmail;
import com.dreamail.mercury.domain.WebEmailattachment;
import com.dreamail.mercury.domain.WebEmailhead;
import com.dreamail.mercury.mail.receiver.DLEMailSupport;
import com.dreamail.mercury.memcached.EmailCacheManager;
import com.dreamail.mercury.pojo.Clickoo_message;
import com.dreamail.mercury.util.Constant;

public class TestDLEMailSupport {
//	private static MemCachedClient mcc = new MemCachedClient();
	public static final String mid ;
	public static final long aid;
	public static final String type;
	
	static{
		mid = "20";
		aid = 1l;
//		type = "imap";
//		mid="1002";
//		aid = 5l;
		type = "pop";
	}
	@Before
	public void init(){
		String[] servers = { "192.168.20.210:11211"};
		Integer[] weights = { 3 };
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);//服务器列表
		pool.setWeights(weights);//是上面服务器的权重，必须数量一致，否则权重无效
		pool.setFailover(false);//表示对于服务器出现问题时的自动修复,true如果连接失败会连到另外一台存在的服务器，为false时，如果连接失败会返回null
	
		pool.setInitConn(30);//初始的时候连接数
		pool.setMinConn(30);//表示最小闲置连接数
		pool.setMaxConn(50); //最大连接数
		pool.setMaxIdle(60000);//最大空闲时间
		
		pool.setMaintSleep(30000);//表示是否需要延时结束
		pool.setNagle(false);// Tcp的规则就是在发送一个包之前，本地机器会等待远程主机对上一次发送的包的确认信息到来；
		                                 //这个方法就可以关闭套接字的缓存，以至这个包准备好了就发；
//		pool.setAliveCheck(true); //表示心跳检查，确定服务器的状态 
		pool.setSocketTO(3000);//是socket连接超时时间
		pool.setSocketConnectTO(0);//连接建立时阻塞时间  
		
		//pool.setFailback(failback);   //  检测集群节点失效后，是否进行重试，true 重试  
		//pool.setHashingAlg(alg);//设置使用的hash算法
		
		pool.setMaxBusyTime(30000);//给线程池里面正在用的线程设立最大的占用时间
//		pool.setAliveCheck(true);
		//mcc.setSanitizeKeys(sanitizeKeys)设置可否存入像url这样的特殊字符，false为可以
	   // mcc.setPrimitiveAsString(primitiveAsString) 原本支持阻塞获取和可靠获取，并且将在存储的数据之前加上4个字节的flag(整型)，因此可以支持存储任意可序列化类型。但是有一些应用只需要存储字符串类型和原生类型，这是为了在不同语言的client 之间保持可移植（如存储json数据），那么就不希望在数据之前加上这个flag，把这个值改成true就可疑了
		pool.initialize();
//		mcc.setCompressEnable(true);//超过指定大小的压缩
//		mcc.setCompressThreshold(64 * 1024);  //文件大小超过了这么大就压缩

	}
	/**
	 * 模拟account改变type、mid、aid的初始化值分别测试pop、imap.
	 * @param type
	 * @param account
	 */
	public void setWebAccount(String type,WebAccount account){
		if("pop".equals(type)){
			account.setName("recent:wpk1902@gmail.com");
			account.setPassword("8611218773");
			account.setReceivePort("995");
			account.setReceiveTs("SSL");
			account.setSupportalluid("0");
			account.setId(aid);
			account.setReceiveProtocolType(Constant.POP3);
			account.setInpathList(new String[] { "pop.gmail.com" });
		}else if("imap".equals(type)){
			account.setName("mansitanyin@yahoo.com");
			account.setPassword("mansitanyin123");
			account.setReceivePort("993");
			account.setId(aid);
			account.setReceiveTs("SSL");
			account.setReceiveProtocolType(Constant.IMAP);
			account.setInpathList(new String[] {"124.108.115.241","212.82.96.94","212.82.111.223","imap.mail.yahoo.com" });
		}
	}
	@After
	public void tearDown(){
		if(EmailCacheManager.get(mid)!=null){
			System.out.println(EmailCacheManager.get(mid).getData_size());
			System.out.println(EmailCacheManager.get(mid).getAttachmentJson());
		}
	}
	/**
	 * 测试下载正文.
	 * @throws MessagingException
	 */
	@Test
	public void testDlBody() throws MessagingException {
		System.out.println(EmailCacheManager.removeEmail(mid));
		PropertiesDeploy propertiesDeploy = new PropertiesDeploy();
		propertiesDeploy.init();

		WebAccount account = new WebAccount();
		setWebAccount(type,account);
		Clickoo_message message = new MessageService().selectMessageById(Long.parseLong(mid));
		Body body = (Body) new DLEMailSupport().dlMail(null, Constant.DOWNLOAD_BODY, message, account, null, null, mid);
		System.out.println(body);
	}
	/**
	 * 测试下载单封附件.
	 * @throws MessagingException
	 */
	@Test
	public void testDlAttachment() throws MessagingException {
		System.out.println(EmailCacheManager.removeEmail(mid));
		PropertiesDeploy propertiesDeploy = new PropertiesDeploy();
		propertiesDeploy.init();

		WebAccount account = new WebAccount();
		setWebAccount(type,account);
//		Clickoo_message message = new MessageService().selectMessageById(Long.parseLong(mid));
		new PropertiesDeploy().init();
		WebEmail webEmail = new WebEmail();
		WebEmailhead header = new WebEmailhead();
		header.setMessageId(mid);
		webEmail.setHead(header);
		WebEmailattachment attach = new WebEmailattachment();
		attach.setName("373_193_test");
		attach.setSize(166736);
		attach.setType("txt");
		attach.setIsdown("0");
		webEmail.setAttach(new WebEmailattachment[]{attach});
//		Clickoo_message_attachment a = (Clickoo_message_attachment) new DLEMailSupport().dlMail(null, Constant.DOWNLOAD_ATT, message, account, webEmail, "2", mid);
//		System.out.println(a.getIn());
	}
	/**
	 * 测试下载多封附件.
	 * @throws MessagingException
	 */
	@Test
	public void testDlAttachments() throws MessagingException {
		PropertiesDeploy propertiesDeploy = new PropertiesDeploy();
		propertiesDeploy.init();
		WebAccount account = new WebAccount();
		setWebAccount(type,account);
		Clickoo_message message = new MessageService().selectMessageById(Long.parseLong(mid));
		new PropertiesDeploy().init();
		EmailCacheObject emailCache = EmailCacheManager.get(mid);
		if(emailCache == null){
			emailCache = new EmailCacheObject();
		}
		new DLEMailSupport().dlMail(emailCache, Constant.DOWNLOAD_ALL_ATT, message, account, null, "2", mid);
	}
	
	/**
	 * 测试下载整封邮件.
	 * @throws MessagingException
	 */
	@Test
	public void testDlEmail() throws MessagingException {
		PropertiesDeploy propertiesDeploy = new PropertiesDeploy();
		propertiesDeploy.init();
		WebAccount account = new WebAccount();
		setWebAccount(type,account);
		Clickoo_message message = new MessageService().selectMessageById(Long.parseLong(mid));
		new PropertiesDeploy().init();
		EmailCacheObject emailCache = EmailCacheManager.get(mid);
		if(emailCache == null){
			emailCache = new EmailCacheObject();
		}else if(emailCache.getData()!=null){
			emailCache.setData(null);
			emailCache.setData_size(0);
		}
		Email  email = (Email) new DLEMailSupport().dlMail(emailCache, Constant.DOWNLOAD_EMAIL, message, account, null, "2", mid);
		System.out.println(email.getFrom());
//		System.out.println(email.getBody());
		System.out.println(email.getAttachList().size());
	}
}
