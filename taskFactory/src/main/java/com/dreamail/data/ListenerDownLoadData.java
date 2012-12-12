package com.dreamail.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.config.TaskProperties;
import com.dreamail.mercury.dal.service.DeleteMessageService;
import com.dreamail.mercury.dal.service.DownloadMessageService;

/**
 * 定时发送下载ID
 */
public class ListenerDownLoadData implements Runnable{
	private static Logger logger = LoggerFactory
		 .getLogger(ListenerDownLoadData.class);
	private static ConcurrentHashMap<String, String> midMap = 
						new ConcurrentHashMap<String, String>();
	private static String strInterval = TaskProperties.getConfigureMap().
							get("listener_download_data");
	
	/**
	 * 定时发送下载ID
	 */
	@Override
	public void run() {
		DownloadMessageService downloadMessageService = 
			new DownloadMessageService();
		List<String> mids = downloadMessageService.
				getDownloadMessages(Integer.parseInt(strInterval));
		StringBuffer sendMids = new StringBuffer();
		StringBuffer deleteMids = new StringBuffer();
		String maxTime = TaskProperties.getConfigureMap()
				.get("max_download_times");
		String sendTime = TaskProperties.getConfigureMap()
				.get("send_times");
		List<Long> list = new ArrayList<Long>();
		long flag = 0;
		for(String mid : mids){
			if(!midMap.containsKey(mid)){
				midMap.put(mid, "1");
				sendMids.append(mid).append(",");
				++ flag;
			}else{
				long currentNum = Long.parseLong(midMap.get(mid)) + 1;
				midMap.put(mid, String.valueOf(currentNum));
				if(currentNum > Long.parseLong(maxTime)){
					deleteMids.append(mid).append(",");
					list.add(Long.parseLong(mid));
				}else{
					sendMids.append(mid).append(",");
					++ flag;
				}
			}
			if(flag % Long.parseLong(sendTime) == 0){
				sendMids.append("|");
			}
		}
//		if(flag > 0){
//			String[] idList = sendMids.toString().split("|");
//			for(String ids : idList){
//				ids = sendMids.substring(0,ids.length()-1);
//				//JmsSender.sendTopicMsg(ids, "receiveEmailList");
//				logger.info("---------download mids:[" + ids+ "]---------");
//			}
//		}
		if(deleteMids.length() > 2){
			//删除数据库操作
			Long[] dids = new Long[list.size()];
			for(int i =0; i < list.size(); i++){
				dids[i] = list.get(i);
			}
			new DeleteMessageService().deleteDMessageByDids(dids);
			logger.info("---------delete download mids:[" + deleteMids.toString()+ "]---------");
		}
	}
}
