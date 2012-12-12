package com.dreamail.mercury.util;

import java.io.UnsupportedEncodingException;

import com.dreamail.mercury.configure.PropertiesDeploy;

public class StringUtils {
	/**
	 * 根据blank获得空格数
	 * @param blank 空格数量
	 * @param String 空格字符串
	 */
	public static String fillblank(int blank) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < blank; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * 根据encodingFormat和size得到String包含多少个汉字
	 * @param String str 目标字符串
	 * @param String encodingFormat 编码方式
	 * @param int size 指定一个汉字为几个字节    
	 * @return int 返回结果 
	 */
	public static int getChineseChar(String str, String encodingFormat, int size) {
		String tempStr;
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			tempStr = String.valueOf(str.charAt(i));
			try {
				if (tempStr.getBytes(encodingFormat).length == size) {
					count++;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	/**
	 * 根据size(byte)截取字符串
	 * @param String str  字符串
	 * @param String encodingFormat 编码方式
	 * @param int size byte数
	 * @param String 处理后字符串
	 */
	public static String getChar(String str, String encodingFormat, int size) {
		String tempStr;
		boolean flag = false;
		// 字符的第多少个位置达到size个字符
		String result = "";
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			tempStr = String.valueOf(str.charAt(i));
			try {
				int charLenth = tempStr.getBytes(encodingFormat).length;
				if (charLenth == 1) {
					size = size - 1;
				} else if (charLenth == 3) {
					size = size - 2;
				}
				if (size >= 0) {
					count++;
				} else {
					if (charLenth == 3) {
						flag = true;
					}
					break;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		result = str.substring(0, count);
		if (flag && size == -1) {
			result += ".";
		}
		return result + "..";
	}
	/**
	 * 粗略计算手机屏幕可以显示几列
	 * @param double[] size  手机model
	 * @param int doc_table_size  byte像素点
	 * @return int 列数
	 */
	public static int getRows(double[] size,int table_size){
		int unit = Integer.parseInt(PropertiesDeploy.getConfigureMap().get("e_unit"));
		int lieNum = (int) (size[0]/(unit*table_size));
		if(lieNum == 0){
			lieNum=1;
		}
		return lieNum;
	}
	
//	public static void main(String[] args) {
//		PropertiesDeploy p = new PropertiesDeploy();
//		p.init();
//		double[] size = {240,50};
//		int i = getRows(size,9);
//	}
}
