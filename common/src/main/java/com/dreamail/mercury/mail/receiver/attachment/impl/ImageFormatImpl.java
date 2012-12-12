package com.dreamail.mercury.mail.receiver.attachment.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.mail.receiver.attachment.picture.impl.PictureHandleImpl;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;

/**
 * 图片的读取类.
 * 
 * @author 000830
 * 
 */
public class ImageFormatImpl extends PictureHandleImpl {
	/**
	 * 图片处理.
	 * @param attachment 原始附件信息
	 * @param map        当前处理附件信息
	 * @throws Exception Exception
	 * @return List      处理后附件信息
	 */
	@Override
	public List<Clickoo_message_attachment> format(Clickoo_message_attachment attachment,
			HashMap<String, String> map){
		
		
		
		double SCALE = Double.parseDouble(PropertiesDeploy.getConfigureMap()
				.get("image_scale"));
		List<Clickoo_message_attachment> attList = new ArrayList<Clickoo_message_attachment>();
		String mapName = map.get("attName");
		boolean operatorFlag = false;
		//可能出现读写异常
		Clickoo_message_attachment transAtta = transAttachment(attachment);
		
		String[] deviceModelList = map.get("deviceModelList").split("#");
		for(String deviceModel : deviceModelList){
			double height = Double.parseDouble(deviceModel.split(",")[0]);
			double width = Double.parseDouble(deviceModel.split(",")[1]);
			double[] size = {height,width};
			
			Clickoo_message_attachment nAttachment = new Clickoo_message_attachment();
			if(!operatorFlag){
				handleOriginalFile( attachment, map);
				operatorFlag = true;
			}
			map.put("size", height+","+width);
			String attachmentType = attachment.getType();
			Clickoo_message_attachment atm = handelPicutre(transAtta, map, attachmentType,
					size, SCALE);
			nAttachment.setPath(atm.getPath());
			nAttachment.setLength(atm.getLength());
			nAttachment.setVolume_id(atm.getVolume_id());
			nAttachment.setName(mapName);
			nAttachment.setType(attachment.getType());
			nAttachment.setIn(atm.getIn());
			attList.add(nAttachment);
		}
		return attList;
	}
}
