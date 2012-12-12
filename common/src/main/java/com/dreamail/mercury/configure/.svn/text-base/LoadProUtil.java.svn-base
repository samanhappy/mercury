package com.dreamail.mercury.configure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class LoadProUtil {
	public static Map<String,String> map = new HashMap<String,String>();
	
	private static Logger logger = (Logger) LoggerFactory.getLogger(LoadProUtil.class);
	
	/**
	 * true is Cover
	 * false is inexecution Cover
	 */
	private boolean isCover = true;
	
	public boolean isCover() {
		return isCover;
	}

	public void setCover(boolean isCover) {
		this.isCover = isCover;
	}
	/**
	 * oio实现文件探索并加载配置文件至内存，目前仅支持单线程(不支持向上探索)
	 * @param path 从该路径开始向下探索
	 */
	public  void loadPro(String path) {
		List<String> list = new ArrayList<String>();
		URI url = null;
		File root = null;
		if (path == null || "".equals(path)) {
			try {
				url = ClassLoader.getSystemResource("").toURI();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			root = new File(url);
		}else{
			root = new File(path);
		}
		if (root != null) {
			File file[] = root.listFiles();
			for (File element : file) {
				if (element.isDirectory()) {
					 loadPro(element.getAbsolutePath());
				} else {
					list.add(element.getAbsolutePath());
					if(element.getAbsolutePath().toLowerCase().endsWith(".properties")){
						logger.info("load Properties:"+element.getAbsolutePath().toLowerCase());
						File pro = new File(element.getAbsolutePath());
						Properties p = new Properties();
						InputStream in = null;
						try {
							in = new FileInputStream(pro);
							p.load(in);
							propertiesParse(p);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						finally{
							if(p!=null){
								p.clear();
							}
							if(in!=null){
								try {
									in.close();
								} catch (IOException e) {
									logger.error("close inputStream err.",e);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void propertiesParse(Properties p){
		Set<Map.Entry<Object, Object>> set = p.entrySet();
		Iterator<Entry<Object, Object>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<Object, Object> e = it.next();
			if(map.containsKey(e.getKey().toString())){
				logger.info("Repeat existing:"+e.getKey().toString());
				if(isCover){
					logger.info("Executive cover.");
				}
				else{
					logger.info("inexecution.");
					continue;
				}
			}
			map.put(e.getKey().toString(), e.getValue().toString());
		}
	}
}

