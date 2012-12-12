package com.dreamail.mercury.receiver.mail.utils;

import java.io.Reader;
import java.io.StringReader;

import org.apache.html.dom.HTMLDocumentImpl;
import org.cyberneko.html.parsers.DOMFragmentParser;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * html转txt
 * 
 * @author pengkai.wang
 */
public class HTMLHelper {
	private static final HTMLHelper helper = new HTMLHelper();
	
	private HTMLHelper(){
	}
	
	public static HTMLHelper getInstance(){
		return helper;
	}
	/**
	 * 将html转化成txt
	 * @param content
	 * @return String
	 */
	public String htmToTxt(String content) {
		try {
			content = content.replaceAll("&nbsp;", " ");
			content = content.replaceAll("&NBSP;", " ");
			DOMFragmentParser parser = new DOMFragmentParser();
			DocumentFragment node = new HTMLDocumentImpl()
					.createDocumentFragment();
			Reader sr = new StringReader(content);
			parser.parse(new InputSource(sr), node);
			StringBuffer newContent = new StringBuffer();
			getText(newContent, node);
			String str = newContent.toString().replaceAll(
					" *[\\r|\\n]{1,} *[\\r|\\s]{1,} *", "\n");
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return htmlToText(content).replaceAll(
					" *[\\r|\\n]{1,} *[\\r|\\s]{1,} *", "\n");
		}
	}

	private void getText(StringBuffer sb, Node node) {
		if ("TITLE".equalsIgnoreCase(node.getNodeName())
				|| "META".equalsIgnoreCase(node.getNodeName())
				|| "SCRIPT".equalsIgnoreCase(node.getNodeName())
				|| "STYLE".equalsIgnoreCase(node.getNodeName())
				|| node.getNodeType() == Node.COMMENT_NODE) {
			return;
		}
		if (node.getNodeType() == Node.TEXT_NODE) {
			if (node.getNodeValue() != null
					&& node.getNodeValue().trim().length() > 0) {
				if (node.getParentNode() == null) {
					sb.append(" "
							+ node.getNodeValue().trim().replaceAll("\\r|\\n",
									" ") + " ");
				} else if (node.getParentNode().hasAttributes()) {
					Node dir = node.getParentNode().getAttributes()
							.getNamedItem("dir");
					if (dir != null
							&& "rtl".equalsIgnoreCase(dir.getNodeValue())) {
						if ("pre".equalsIgnoreCase(node.getParentNode()
								.getNodeName()))
							sb.append(" "
									+ reverseStr(node.getNodeValue()).trim()
									+ " ");
						else
							sb.append(" "
									+ reverseStr(node.getNodeValue()).trim()
											.replaceAll("\\r|\\n", " ") + " ");
					} else {
						if ("pre".equalsIgnoreCase(node.getParentNode()
								.getNodeName()))
							sb.append(node.getNodeValue());
						else
							sb.append(node.getNodeValue().replaceAll("\\r|\\n",
									" "));
					}
				} else {
					if ("pre".equalsIgnoreCase(node.getParentNode()
							.getNodeName()))
						sb.append(node.getNodeValue());
					else
						sb.append(node.getNodeValue()
								.replaceAll("\\r|\\n", " "));
				}
			}
		}

		if ("br".equalsIgnoreCase(node.getNodeName())
				|| "P".equalsIgnoreCase(node.getNodeName())
				|| "span".equalsIgnoreCase(node.getNodeName())
				|| "li".equalsIgnoreCase(node.getNodeName())
				|| "tr".equalsIgnoreCase(node.getNodeName())
				|| "hr".equalsIgnoreCase(node.getNodeName())
				|| "dl".equalsIgnoreCase(node.getNodeName())

		)
			sb.append("\n");

		if (node.getNodeName() != null
				&& node.getNodeName().toLowerCase().matches("^h\\d")) {
			sb.append("\n");
		}

		NodeList children = node.getChildNodes();
		if (children != null) {
			int len = children.getLength();
			for (int i = 0; i < len; i++) {
				getText(sb, children.item(i));
			}
		}

		if ("br".equalsIgnoreCase(node.getNodeName())
				|| "P".equalsIgnoreCase(node.getNodeName())
				|| "span".equalsIgnoreCase(node.getNodeName())
				|| "li".equalsIgnoreCase(node.getNodeName())
				|| "tr".equalsIgnoreCase(node.getNodeName())
				|| "hr".equalsIgnoreCase(node.getNodeName())
				|| "dl".equalsIgnoreCase(node.getNodeName())

		)
			sb.append("\n");

		if (node.getNodeName() != null
				&& node.getNodeName().toLowerCase().matches("^h\\d")) {
			sb.append("\n");
		}

		if ("td".equalsIgnoreCase(node.getNodeName())) {
			if (node.getChildNodes() == null
					|| (node.getChildNodes().getLength() == 1 && node
							.getChildNodes().item(0).getNodeType() == Node.TEXT_NODE))
				sb.append(" | ");
			else
				sb.append("\n");
		}

		if ("dt".equalsIgnoreCase(node.getNodeName()))
			sb.append(":");

	}

	private String reverseStr(String str) {
		char[] stack = new char[str.length()];
		for (int i = 0; i < str.length(); i++) {
			stack[i] = str.charAt(i);
		}
		StringBuffer reverseStr = new StringBuffer("");
		for (int j = stack.length - 1; j >= 0; j--) {
			reverseStr.append(stack[j]);
		}
		return reverseStr.toString();
	}

	private String htmlToText(String orgText) {
		String[] arysplit;
		int i, j, lens;
		StringBuffer str = new StringBuffer();

		arysplit = orgText.split("<");
		if (arysplit[0].length() > 0)
			j = 1;
		else
			j = 0;

		lens = arysplit.length;
		for (i = j; i < lens; i++) {
			if (arysplit[i].indexOf(">") > 0)
				arysplit[i] = arysplit[i]
						.substring(arysplit[i].indexOf(">") + 1);
			else
				arysplit[i] = "<" + arysplit[i];
		}

		for (i = 0; i < lens; i++)
		str.append(arysplit[i]);
		String strOutput = str.toString();
		strOutput = strOutput.substring(1 - j);
		strOutput = strOutput.replaceAll(">", ">");
		strOutput = strOutput.replaceAll("<", "<");
		strOutput = strOutput.replaceAll("&gt;", ">");
		strOutput = strOutput.replaceAll("&lt;", "<");
		strOutput = strOutput.replaceAll("&nbsp;", " ");
		strOutput = strOutput.replaceAll("nbsp;", "");
		strOutput = strOutput.replaceAll("&quot;", "");
		return strOutput;
	}

}
