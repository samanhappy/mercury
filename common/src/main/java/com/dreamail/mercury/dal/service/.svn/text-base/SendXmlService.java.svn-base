package com.dreamail.mercury.dal.service;

import java.util.Date;
import java.util.List;

import com.dreamail.mercury.cache.MemCachedManager;
import com.dreamail.mercury.dal.dao.SendXmlDao;
import com.dreamail.mercury.pojo.Clickoo_send_xml;

public class SendXmlService {
	public static boolean addXml(String uid,String xml){
		SendXmlDao sendXmlDao = new SendXmlDao();
		Clickoo_send_xml clickoo_send_xml = new Clickoo_send_xml();	
		clickoo_send_xml.setSxKey("cutdata_"+uid);
		clickoo_send_xml.setSxValue(xml);	
		clickoo_send_xml.setSxDate(new Date());
		return sendXmlDao.addSendXml(clickoo_send_xml);
	}
	
	public static boolean updateXml(String uid,String xml){
		SendXmlDao sendXmlDao = new SendXmlDao();
		Clickoo_send_xml clickoo_send_xml = new Clickoo_send_xml();
		if(xml!=null &&!"".equals(xml)){
			String sb = getXml(uid);
			if(sb!=null){
				sb += xml;
				clickoo_send_xml.setSxKey("cutdata_"+uid);
				clickoo_send_xml.setSxValue(sb);
				return sendXmlDao.updateSendXmlBysxKey(clickoo_send_xml);
			}
		}
		return false;
	}
	
	public static String getXml(String uid){
		SendXmlDao sendXmlDao = new SendXmlDao();
		StringBuffer send_xml = new StringBuffer();
		List<Clickoo_send_xml> clickoo_send_xmls = sendXmlDao.getSenXmlBysxKey("cutdata_"+uid);
		for(Clickoo_send_xml sx:clickoo_send_xmls){
			send_xml.append(sx.getSxValue());
		}
		return send_xml.toString();
	}
	
	public static boolean emptyXml(String uid){
		SendXmlDao sendXmlDao = new SendXmlDao();
		return  sendXmlDao.deleteSendXmlBysxKey("cutdata_"+uid);
	}
	
	public static boolean containsUid(String uid){
		return MemCachedManager.getMcc().keyExists("cutdata_"+uid);
	}
	
//	public static void main(String[] args) {
//		SendXmlService sxs =  new SendXmlService();
//		sxs.addXml("1", "a");
//		sxs.addXml("1", "s");
//		sxs.addXml("1", "d");
//		sxs.addXml("1", "f");
//		String s = sxs.getXml("1");
//		System.out.println(s);
////		sxs.emptyXml("1");
//	}

}
