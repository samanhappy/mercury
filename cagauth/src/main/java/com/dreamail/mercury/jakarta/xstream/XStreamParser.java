package com.dreamail.mercury.jakarta.xstream;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dreamail.mercury.cag.AccountSettingsObject;
import com.dreamail.mercury.cag.CAGParserObject;
import com.dreamail.mercury.cag.CAGSettingObject;
import com.dreamail.mercury.cag.COSSettingsObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;

public class XStreamParser {

	private static XStream xstream = new XStream(new Dom4JDriver());

	static {
		xstream.alias("email", CAGParserObject.class);
		xstream.alias("settings", CAGSettingObject.class);
		xstream.alias("account", AccountSettingsObject.class);
		xstream.alias("cos", COSSettingsObject.class);
		xstream.processAnnotations(CAGSettingObject.class);
	}

	/**
	 * 把xml字符串转换成CAG对象.
	 * 
	 * @param xml
	 * @return
	 */
	public static CAGParserObject xml2CAGObject(String xml) {
		CAGParserObject obj = (CAGParserObject) xstream.fromXML(xml);
		return obj;
	}

	/**
	 * 把xml字符串转换成CAG设置信息对象.
	 * 
	 * @param xml
	 * @return
	 */
	public static CAGSettingObject xml2CAGSettingsObject(String xml) {
		CAGSettingObject obj = (CAGSettingObject) xstream.fromXML(xml);
		return obj;
	}

	/**
	 * 把CAG对象转换成xml字符串.
	 * 
	 * @param obj
	 * @return
	 */
	public static String CAGObject2Xml(CAGParserObject obj) {
		return replaceBlank(xstream.toXML(obj));
	}

	/**
	 * 去掉标签之间的控制字符.
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		Pattern p = Pattern.compile(">(\\s*|\t|\r|\n)<");
		Matcher m = p.matcher(str);
		return m.replaceAll("><");
	}
}
