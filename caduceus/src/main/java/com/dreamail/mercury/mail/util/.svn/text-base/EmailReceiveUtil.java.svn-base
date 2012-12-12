package com.dreamail.mercury.mail.util;

import java.util.List;

import net.sf.json.JSONObject;

import com.dreamail.mercury.dal.dao.TaskFactoryDao;
import com.dreamail.mercury.dal.dao.UARelationDao;
import com.dreamail.mercury.dal.dao.UserDao;
import com.dreamail.mercury.domain.Context;
import com.dreamail.mercury.mail.jms.ReceiveMain;
import com.dreamail.mercury.pojo.Clickoo_task_factory;
import com.dreamail.mercury.pojo.Clickoo_user;
import com.dreamail.mercury.util.EmailUtils;
import com.dreamail.mercury.util.JMSConstans;

public class EmailReceiveUtil {
	public static Context changeContext(Context context){
		Context con = new Context();
		con.setAccount(context.getAccount());
		con.setAccountId(context.getAccountId());
		con.setAccountType(context.getAccountType());
		con.setCompositor(context.getCompositor());
		con.setDeviceModel(context.getDeviceModel());
		con.setDeviceModelList(context.getDeviceModelList());
		con.setFilterMap(context.getFilterMap());
		con.setFolder(context.getFolder());
		con.setFolderName(context.getFolderName());
		con.setHostName(context.getHostName());
		con.setInpathList(context.getInpathList());
		con.setIntervalTime(context.getIntervalTime());
		con.setLevel(context.getLevel());
		con.setLoginName(context.getLoginName());
		con.setPassword(context.getPassword());
		con.setPort(context.getPort());
		con.setReceiveHost(context.getReceiveHost());
		con.setReceiveProtocoltype(context.getReceiveProtocoltype());
		con.setReceiveTs(context.getReceiveTs());
		con.setRegistrationDate(context.getRegistrationDate());
		con.setRoleLevel(context.getRoleLevel());
		con.setServer(context.getServer());
		con.setSign(context.getSign());
		con.setSSL(context.isSSL());
		con.setStatus(context.getStatus());
		con.setSupportAllUid(context.getSupportAllUid());
		con.setDisconnectTime(context.getDisconnectTime());
		con.setConnectStatus(context.getConnectStatus());
		return con;
	}
	/**
	 * 更新contextMap
	 * @param key
	 * @param uid
	 */
	public static void updateContext(String key,String uid){
		UserDao userDao = new UserDao();
		if(JMSConstans.JMS_USER_DEVICE_MODIFY.equals(key)){
			Clickoo_user user = userDao.getAidAndDevice(Long.parseLong(uid));
			List<String> aidList = user.getAidList();
			for(String id:aidList){
				if(ReceiveMain.contextMap.containsKey(id)){
					Context context = ReceiveMain.contextMap.get(id);
					context.setDeviceModel(user.getDevicemodel());
					ReceiveMain.contextMap.put(id, context);
				}
			}
		}else{
			UARelationDao uaDao = new UARelationDao();
			List<String> aidList =uaDao.selectAllAid(Long.parseLong(uid));
			JSONObject accountJson = JSONObject.fromObject(key);
			for(String id:aidList){
				if(ReceiveMain.contextMap.containsKey(id)){
					Context context = ReceiveMain.contextMap.get(id);
					String sign = accountJson.getString(JMSConstans.JMS_USER_FILTER_SIGN);
					if(key.contains(JMSConstans.JMS_USER_SET_FILTER)){
						context.setSign(sign);
						ReceiveMain.contextMap.put(id, context);
					}else if(key.contains(JMSConstans.JMS_USER_ADD_FILTER)){
						List<String> filterList = context.getFilterMap().get(sign);
						filterList.add(accountJson.getString(JMSConstans.JMS_USER_FILTER_NAME));
						context.getFilterMap().put(sign, filterList);
					}else if(key.contains(JMSConstans.JMS_USER_DELETE_FILTER)){
						List<String> filterList = context.getFilterMap().get(sign);
						filterList.remove(accountJson.getString(JMSConstans.JMS_USER_FILTER_NAME));
						context.getFilterMap().put(sign, filterList);
					}
				}
			}
		}
	}

	/**
	 * 更新context.
	 * @param accountJson
	 */
	public static void updateContext(JSONObject accountJson){
		String aid = accountJson.getString(JMSConstans.JMS_AID_KEY);
		if(ReceiveMain.contextMap.containsKey(aid)){
			Context context = ReceiveMain.contextMap.get(aid);
			context.setPassword(new String(EmailUtils.base64TochangeByte(accountJson.getString(JMSConstans.JMS_PASSWORD_KEY))));
			ReceiveMain.contextMap.put(aid, context);
		}
	}
	
	/**
	 * 查询任务工厂的ip、port.
	 * @return
	 */
	public static Clickoo_task_factory getTaskFactory(){
		TaskFactoryDao dao = new TaskFactoryDao();
		Clickoo_task_factory taskFactoy = null;
		while(true){
			List<Clickoo_task_factory> factory = dao.getTaskParameterByType(EmailConstant.TaskFactoryConstant.MCTYPE);
			if(factory!=null && factory.size()>0 ){
				taskFactoy = factory.get(0);
				break;
			}
			System.out.println(factory);
			try {
				Thread.sleep(EmailConstant.TaskFactoryConstant.QUERY_INTEVEL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(1);
		}
		return taskFactoy;
	}
	
	public static void main(String[] args) {
		Clickoo_task_factory f = getTaskFactory();
		System.out.println(f.getMckey());
		System.out.println(2);
	}
	
	public static String getDeviceId(String user,String aid){
//		if(user.contains("@"))
//			user = user.split("@")[0];
//		StringBuffer deviceId = new StringBuffer(user);
//		deviceId.append(EmailConstant.ActiveSycn.REQUEST_SYNC);
//		if(aid != null)
//			deviceId.append(aid);
		return aid;
	}
	
	public static String getFirstDeviceId(){
		return String.valueOf(System.currentTimeMillis());
	}
}
