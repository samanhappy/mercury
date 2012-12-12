package com.dreamail.mercury.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.configure.PropertiesDeploy;

public class AttachmentSupport {
	public static final Logger logger = 
		LoggerFactory.getLogger(AttachmentSupport.class);
	public static boolean isImageType(String type){
		return isUnKnownType(type,"image");
	}
	
	public static boolean isPDFType(String type){
		return isUnKnownType(type,"pdf");
	}
	
	public static boolean isDOCType(String type){
		return isUnKnownType(type,"doc");
	}
	
	public static boolean isXLSType(String type){
		return isUnKnownType(type,"xls");
	}
	
	public static boolean isPPTType(String type){
		return isUnKnownType(type,"ppt");
	}
	
	public static boolean isTXTType(String type){
		return isUnKnownType(type,"txt");
	}
	
	private static boolean isUnKnownType(String type,String unknown){
		String unknownType = PropertiesDeploy.getConfigureMap().get(unknown);
		boolean bool = false;
		if (type == null || unknownType==null||"".equals(unknownType)){
			return bool;
		}
		String[] imageStr = unknownType.split(",");
		for(String s:imageStr){
			if(s!=null && s.equalsIgnoreCase(type)){
				bool = true;
			}
		}
		return bool;
	}
	
	public static boolean isSupportType(String type){
		return isImageType(type) || isPDFType(type) || isDOCType(type)
			|| isXLSType(type) || isTXTType(type);
	}
}
