package com.dreamail.mercury.talaria.xstream;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dreamail.mercury.cag.AccountSettingsObject;
import com.dreamail.mercury.cag.CAGSettingObject;
import com.dreamail.mercury.cag.COSSettingsObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;

public class XStreamParser {

	private static XStream xstream = new XStream(new Dom4JDriver());

	static {
		xstream.alias("UPE", UPEParserObject.class);
		xstream.alias("pushMail", PushMailObject.class);
		xstream.alias("im", IMObject.class);
		xstream.alias("msn", MSNObject.class);
		
		xstream.alias("email", CAGParserObject.class);
		xstream.alias("settings", CAGSettingObject.class);
		xstream.alias("settings", IMSettingsObject.class);
		xstream.alias("account", AccountSettingsObject.class);
		xstream.alias("cos", COSSettingsObject.class);
		xstream.alias("mid", String.class);
		xstream.processAnnotations(new Class[]{CAGSettingObject.class,IMSettingsObject.class});
	}

	/**
	 * 把xml字符串转换成UPE对象.
	 * @param xml
	 * @return
	 */
	public static UPEParserObject xml2UPEObject(String xml) {
		UPEParserObject obj = (UPEParserObject) xstream.fromXML(xml);
		return obj;
	}

	/**
	 * 把UPE对象转换成xml字符串.
	 * @param obj
	 * @return
	 */
	public static String UPEObject2Xml(UPEParserObject obj) {
		return replaceBlank(xstream.toXML(obj));
	}
	
	/**
	 * 把xml字符串转换成CAG对象.
	 * @param xml
	 * @return
	 */
	public static CAGParserObject xml2CAGObject(String xml) {
		CAGParserObject obj = (CAGParserObject) xstream.fromXML(xml);
		return obj;
	}

	/**
	 * 把CAG对象转换成xml字符串.
	 * @param obj
	 * @return
	 */
	public static String CAGObject2Xml(CAGParserObject obj) {
		return replaceBlank(xstream.toXML(obj));
	}
	
	/**
	 * 把CAG设置信息对象转换成xml字符串.
	 * @param obj
	 * @return
	 */
	public static String CAGSettingsObject2Xml(CAGSettingObject obj) {
		return replaceBlank(xstream.toXML(obj));
	}
	
	/**
	 * 把IM对象转换成xml字符串.
	 * @param obj
	 * @return
	 */
	public static String IMObject2Xml(IMObject obj) {
		return replaceBlank(xstream.toXML(obj));
	}
	
	/**
	 * 把xml字符串转换成IM对象.
	 * @param obj
	 * @return
	 */
	public static IMObject xml2IMObject(String xml) {
		return (IMObject) xstream.fromXML(xml);
	}

	/**
	 * 去掉标签之间的控制字符.
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		Pattern p = Pattern.compile(">(\\s*|\t|\r|\n)<");
		Matcher m = p.matcher(str);
		return m.replaceAll("><");
	}
}
