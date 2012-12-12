package com.dreamail.mercury.mail.jms.timer;

import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.mail.jms.ReceiveMain;
import com.dreamail.mercury.mail.util.EmailConstant;
import com.dreamail.mercury.proces.MailProces;
import com.dreamail.mercury.receiver.mail.threadpool.PutHotmailThreadPool;
import com.dreamail.mercury.receiver.mail.threadpool.PutThreadPool;
import com.dreamail.mercury.receiver.mail.threadpool.PutYahooThreadPool;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JMSConstans;

public class AccountTimer extends TimerTask{
	private static final Logger logger = LoggerFactory
			.getLogger(AccountTimer.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ConcurrentHashMap<String, Context> contextMap = ReceiveMain.contextMap;
		try {
			if(contextMap!=null && contextMap.size()>0){
				logger.info("-------------------Account timer:"+contextMap.size());
				Set<Entry<String,Context>> set = contextMap.entrySet();
				for(Entry<String,Context> accountEntry:set){
					String aid = accountEntry.getKey();
					Context context = accountEntry.getValue();
					if(context!=null){
						logger.info(aid+":1..."+EmailConstant.ACCOUNT_CONNECT.equalsIgnoreCase(context.getConnectStatus()));
						logger.info(aid+":2..."+(System.currentTimeMillis()-context.getDisconnectTime()));
						logger.info(aid+":3..."+context.getConnectStatus());
						logger.info(aid+":4..."+context.getIntervalTime()* 60 * 1000);
						logger.info(aid+":5..."+(System.currentTimeMillis()-context.getDisconnectTime()));
						if(context.getConnectStatus()==null)
							continue;
						if((EmailConstant.ACCOUNT_CONNECT.equalsIgnoreCase(context.getConnectStatus()) && 
								(System.currentTimeMillis()-context.getDisconnectTime()>EmailConstant.DISCONNECT_TIME))
								||(EmailConstant.ACCOUNT_DISCONNECT.equalsIgnoreCase(context.getConnectStatus()) &&
								(System.currentTimeMillis()-context.getDisconnectTime()>context.getIntervalTime()* 60 * 1000))){
							if(System.currentTimeMillis()-context.getDisconnectTime()>context.getIntervalTime()* 2 * 60 * 1000){
								//若时间持续增加 ，清空map中的aid对应信息
								contextMap.get(aid).setAccount(null);
								contextMap.remove(aid);
							}
						int type = context.getAccountType();
						JSONObject msg = new JSONObject();
						msg.put(JMSConstans.JMS_AID_KEY, aid);
						msg.put(JMSConstans.JMS_ACCOUNT_TYPE_KEY, type);
						logger.info("Account timer:"+msg+"type:"+type);
						if(type == Constant.ACCOUNT_YAHOOSNP_TYPE){
							PutYahooThreadPool.putPool(msg.toString());
							logger.info("put yahoo:"+context.getLoginName());
						}else{
							PutThreadPool.putPool(msg.toString());
							logger.info("put common:"+context.getLoginName());
						}
					}
					}
				}
			}
			logger.info("-------------------Account timer end");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}
	}

}
