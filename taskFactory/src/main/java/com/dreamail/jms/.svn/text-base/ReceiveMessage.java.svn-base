package com.dreamail.jms;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dreamail.hearbeat.task.TaskAllocation;
import com.dreamail.mercury.util.JMSConstans;

public class ReceiveMessage {

	public static final Logger logger = LoggerFactory.getLogger(ReceiveMessage.class);

	public void onMessage(Object msg) {
		try {
			if(msg instanceof String){
				logger.info("receive msg:"+msg);
				JSONObject json = JSONObject.fromObject(msg);
				if(null != json.get(JMSConstans.JMS_TYPE_KEY)){
					String state = json.getString(JMSConstans.JMS_TYPE_KEY);
					String aID = json.getString(JMSConstans.JMS_AID_KEY);
					String aType = json.getString(JMSConstans.JMS_ACCOUNT_TYPE_KEY);
					//账号添加，用户上线
					if(JMSConstans.JMS_ACCOUNT_ON_LINE.equals(state) 
							|| JMSConstans.JMS_ADD_ACCOUNT_TYPE.equals(state)){
						TaskAllocation.addOrOnlineAccount(aID, aType);
					}
					//账号删除，用户下线
					else if(JMSConstans.JMS_ACCOUNT_OFF_LINE.equals(state) 
							|| JMSConstans.JMS_DELETE_ACCOUNT_TYPE.equals(state)){
						TaskAllocation.removeOrOfflineAccount(aID);
					}
					//错误信息
					else{
						logger.info(" taskfactory message receive wrong");
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error receiveMSG.", e);
		}
	}
}
