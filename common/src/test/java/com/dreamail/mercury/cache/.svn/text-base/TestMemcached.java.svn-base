package com.dreamail.mercury.cache;

import org.junit.Before;
import org.junit.Test;

import com.danga.MemCached.SockIOPool;
import com.dreamail.mercury.memcached.EmailCacheManager;

public class TestMemcached {
//	private static MemCachedClient mcc = new MemCachedClient();
	
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
	
	@Test
	public void test(){
//		List<String> uuidList = new ArrayList<String>();
//		uuidList.add("1");
//		mcc.add("1", "hello2!!");
//		mcc.add("mid", uuidList);
//		for(int i=6;i<=10;i++){
//			System.out.println("=================:"+EmailListCacheManager.getEmailList("2"));
//		EmailCacheObject emailCache = EmailCacheManager.get(""+i);
//		emailCache.setAid(null);
//		emailCache.setAttachmentJson(null);
//		EmailCacheManager.addEmail("90", emailCache, -1);
//		if(emailCache!=null){
//			System.out.println(i+":"+emailCache.getSubject()+"======="+emailCache.getAttachmentJson()+"===="+emailCache.getAid());
//		}
//			System.out.println("=================:"+EmailCacheManager.get(""+i));
//		}
//		if(EmailCacheManager.get(""+i)!=null){
//			System.out.println(i+"=================:"+EmailCacheManager.get(i+"").getSubject());
//		}
//		for(int i=44;i<=55;i++){
//			System.out.println(EmailCacheManager.removeEmail(""+i));
			System.out.println(EmailCacheManager.get(""+79).getSubject());
//		}
//	}
//		System.out.println(EmailCacheManager.get("41").getAttachmentJson());
//		System.out.println(new String(ZipUtil.decompress(EmailCacheManager.get(""+633).getData())));
//		System.out.println(EmailCacheManager.get(""+633).getData_size());
//		System.out.println(EmailCacheManager.get(""+633).getAid());
//		System.out.println(EmailCacheManager.get(""+633).getAttachmentJson());
	}
	
	@Test
	public void test2(){
//		mcc.set("1", "hello3!!");
//		List<String> uuidList = (List<String>)mcc.get("mid");
//		System.out.println(uuidList.contains("1"));
//		System.out.println(mcc.get("1"));
		
	}
	
	
	@Test
	public void test3(){
//		EmailCacheObject obj = new EmailCacheObject();
//		obj.setData(null);
//		mcc.set("test_likai_111", obj);
//		EmailCacheObject abc = (EmailCacheObject)mcc.get("test_likai_111");
//		if(abc!=null){
//			System.out.println("not null!");
//		}
//		System.out.println(mcc.delete("test_likai_111"));
		
	}
	
}
