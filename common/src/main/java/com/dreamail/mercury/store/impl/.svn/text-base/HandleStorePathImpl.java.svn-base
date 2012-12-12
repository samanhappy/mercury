package com.dreamail.mercury.store.impl;

import com.dreamail.mercury.dal.service.AttachmentService;
import com.dreamail.mercury.dal.service.VolumeService;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.pojo.Clickoo_volume;
import com.dreamail.mercury.store.IHandleStorePath;

public class HandleStorePathImpl implements IHandleStorePath{

	@Override
	public Clickoo_message_attachment getAttachmentById(Long attaID,String uid,String mid) {
		AttachmentService attachmentService = new AttachmentService();
		return attachmentService.selectAttachmentById(mid,attaID);
	}
	/**
	 * TODO 获取附件
	 * @param long attaID 附件ID
	 * @param String uid  用户ID
	 * @return String
	 */
	public String getPathById(long attaID,String uid,String mid) {
		AttachmentService attachmentService = new AttachmentService();
		Clickoo_message_attachment pojo = attachmentService.selectAttachmentById(mid,attaID);
		return getPathById(pojo);
	}
	
	public String getPathById(Clickoo_message_attachment pojo) {
		String path = "";
		if(pojo.getPath()==null || pojo.getPath().equals("")){
			return null;
		}
		Clickoo_volume volume = new VolumeService().getVolumeById(pojo.getVolume_id());
		if(!pojo.getPath().equals("") && pojo.getPath() != null){
			path = volume.getPath()  + pojo.getPath();
		}
		return path;
	}
}
