/**
 * 
 */
package com.dreamail.mercury.dal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.domain.EmailCacheObject;
import com.dreamail.mercury.memcached.EmailCacheManager;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.util.Constant;
import com.dreamail.mercury.util.JsonUtil;

/**
 * @author meng.sun
 *
 */
public class AttachmentService {
	private static Logger logger = LoggerFactory.getLogger(AttachmentService.class);
	/**
	 * 根据id查询邮件附件.
	 * @param mid
	 * @param attId
	 * @return Clickoo_message_attachment
	 */
	public Clickoo_message_attachment selectAttachmentById(String mid,Long attId) {
		Clickoo_message_attachment attachment = null;
		EmailCacheObject emailCache = EmailCacheManager.get(mid);
		if(emailCache == null){
			//从数据库模拟一个email对象，存入到缓存
			emailCache = new MessageService().produceEmailCacheObject(mid);
			EmailCacheManager.addEmail(String.valueOf(mid), emailCache,-1);
		}
		attachment = selectAttachmentById(emailCache,mid,attId);
		return attachment;
	}
	
	public Clickoo_message_attachment selectAttachmentById(EmailCacheObject emailCache,String mid,Long attId) {
		Clickoo_message_attachment attachment = null;
		if(emailCache!=null){
			JSONObject attachmentParent = JSONObject.fromObject(emailCache.getAttachmentJson());
			JSONObject attachmentChild = (JSONObject) attachmentParent.get(String.valueOf(attId));
			if(attachmentChild!=null){
				attachment = transAttachmentJson(attachmentChild,mid);
			}
		}
		return attachment;
	}
	
	
	/**
	 * 根据名称和大小查询邮件附件,根据状态判断获取原始附件还是处理后的附件.
	 * @param mid
	 * @param name
	 * @param size
	 * @param status
	 * @return Clickoo_message_attachment
	 */
	public Clickoo_message_attachment selectAttachmentById(String mid,String name,long size,int isDown) {
		EmailCacheObject emailCache = EmailCacheManager.get(mid);
		return selectAttachmentById(emailCache,mid,name,size,isDown);
	}
	
	public Clickoo_message_attachment selectAttachmentById(EmailCacheObject emailCache,String mid,String name,long size,int isDown) {
		Clickoo_message_attachment attachment = null;
		if(emailCache!=null){
			JSONObject attachmentParent = JSONObject.fromObject(emailCache.getAttachmentJson());
				if(isDown==1){
					attachment = transAttachmentJson(JsonUtil.getInstance().getAttachmentJsonByName(attachmentParent, name, size),mid);
				}else if(isDown==0){
					JSONObject obj = JsonUtil.getInstance().getAttachmentJsonByName(attachmentParent, name, size);
					if(obj!=null){
						String id = obj.getString(Constant.ATTACHMENT_ID);
						logger.info("attachment id is :"+id);
						attachment = transAttachmentJson(JsonUtil.getInstance().getAttachmentJsonByParent(attachmentParent, id),mid);
					}
				}else{
					logger.warn("get attachment with wrong satus.");
				}
			}
		return attachment;
	}
	
	
	
	
	/**
	 * 解析json对象为attachment对象.
	 * @param jsonObj
	 * @param mid
	 * @return Clickoo_message_attachment
	 */
	public Clickoo_message_attachment transAttachmentJson(JSONObject jsonObj,String mid){
		Clickoo_message_attachment attachment = new Clickoo_message_attachment();
		if(jsonObj!=null){
			attachment.setId(Long.parseLong(jsonObj.getString(Constant.ATTACHMENT_ID)));
			if(jsonObj.getString(Constant.ATTACHMENT_PATH).equals("")){
				attachment.setName("");
			}else{
				attachment.setName(jsonObj.getString(Constant.ATTACHMENT_NAME));
			}
			attachment.setType(jsonObj.getString(Constant.ATTACHMENT_TYPE));
			attachment.setPath(jsonObj.getString(Constant.ATTACHMENT_PATH));
			attachment.setParent(Long.parseLong(jsonObj.getString(Constant.ATTACHMENT_PARENT)));
			attachment.setMid(Long.parseLong(mid));
			attachment.setVolume_id(Long.parseLong(jsonObj.getString(Constant.ATTACHMENT_VOLUME_ID)));
			attachment.setLength(Long.parseLong(jsonObj.getString(Constant.ATTACHMENT_LENGTH)));
		}else{
			return null;
		}
		return attachment;
	}
	
	
	/**
	 * 根据message id查询附件列表.
	 * @param id attachment id
	 * @return Clickoo_message_data
	 */
	public List<Clickoo_message_attachment> selectAttachmentListByMid(long mid) {
		EmailCacheObject emailCache = EmailCacheManager.get(String.valueOf(mid));
		return selectAttachmentListByMid(mid,emailCache);
	}
	
	@SuppressWarnings("unchecked")
	public List<Clickoo_message_attachment> selectAttachmentListByMid(long mid,EmailCacheObject emailCache) {
		if(emailCache == null)
			return null;
		List<Clickoo_message_attachment> attList = new ArrayList<Clickoo_message_attachment>();
		JSONObject attachmentParent = JSONObject.fromObject(emailCache.getAttachmentJson());
		Set<String> set = attachmentParent.keySet();
		if(set!=null){
			for(String id:set){
				Object obj = attachmentParent.get(id);
				if(obj==null) {continue;}
				JSONObject attachmentChild = (JSONObject)obj ;
				try {
					attList.add(transAttachmentJson(attachmentChild,String.valueOf(mid)));
				} catch (Exception e) {
					logger.info("This Attachment Json is "+attachmentChild.toString());
					logger.error("this mail jsonObj is error",e);
				}
			}
		}
		return attList;
	}
	
	
	/**
	 * 根据message id查询原始附件列表.
	 * @param id attachment id
	 * @return Clickoo_message_data
	 */
	public List<Clickoo_message_attachment> selectOriginalAttachmentListByMid(long mid) {
		EmailCacheObject emailCache = EmailCacheManager.get(String.valueOf(mid));
		return selectOriginalAttachmentListByMid(emailCache,mid);
	}
	
	@SuppressWarnings("unchecked")
	public List<Clickoo_message_attachment> selectOriginalAttachmentListByMid(EmailCacheObject emailCache,long mid) {
		List<Clickoo_message_attachment> attList = new ArrayList<Clickoo_message_attachment>();
		JSONObject attachmentParent = JSONObject.fromObject(emailCache.getAttachmentJson());
		Set<String> set = attachmentParent.keySet();
		if(set!=null){
			for(String id:set){
				JSONObject attachmentChild = (JSONObject) attachmentParent.get(id);
				if("0".equals(attachmentChild.getString(Constant.ATTACHMENT_PARENT))){
					attList.add(transAttachmentJson(attachmentChild,String.valueOf(mid)));
				}
			}
		}
		return attList;
	}
	
	
	/**
	 * 根据附件id查询设备分辨率.
	 * @param id attachment id
	 * @return Clickoo_device(model)
	 */
/*	public String selectDeviceByAttachId(Long id,String uid){
		return new UserDao().getDeviceModelByUid(uid);
	}*/
	
}
