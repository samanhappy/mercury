package com.dreamail.mercury.cache;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.dreamail.mercury.configure.MemcachedPropertiesDeploy;

public class MemCachedManager {
	private static final MemCachedManager manager = new MemCachedManager();
	private static MemCachedClient mcc = new MemCachedClient();

	public static MemCachedClient getMcc() {
		if (mcc == null) {
			mcc = new MemCachedClient();
		}
		return mcc;
	}

	private MemCachedManager() {
	}

	public static MemCachedManager getInstance() {
		return manager;
	}

	public void init() {
		MemcachedPropertiesDeploy.init();
		String[] servers = MemcachedPropertiesDeploy.getServers();
		Integer[] weights = MemcachedPropertiesDeploy.getWeights();
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);// 服务器列表
		pool.setWeights(weights);// 是上面服务器的权重，必须数量一致，否则权重无效
		pool.setFailover(true);// 表示对于服务器出现问题时的自动修复,true如果连接失败会连到另外一台存在的服务器，为false时，如果连接失败会返回null
		pool.setAliveCheck(true);
		pool.setFailback(true);

		pool.setInitConn(30);// 初始的时候连接数
		pool.setMinConn(30);// 表示最小闲置连接数
		pool.setMaxConn(50); // 最大连接数
		pool.setMaxIdle(60000);// 最大空闲时间

		pool.setMaintSleep(35000);// 表示是否需要延时结束
		pool.setNagle(false);// Tcp的规则就是在发送一个包之前，本地机器会等待远程主机对上一次发送的包的确认信息到来；
		// 这个方法就可以关闭套接字的缓存，以至这个包准备好了就发；
		pool.setSocketTO(3000);// 是socket连接超时时间
		pool.setSocketConnectTO(0);// 连接建立时阻塞时间

		pool.setMaxBusyTime(30000);// 给线程池里面正在用的线程设立最大的占用时间
		pool.initialize();
	}

	/**
	 * 判读memcached是否存活.
	 * 
	 * @return
	 */
	public static boolean isMemcachedLive() {
		return mcc.set("1", "");
	}

}
