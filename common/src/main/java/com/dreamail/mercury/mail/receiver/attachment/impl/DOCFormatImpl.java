package com.dreamail.mercury.mail.receiver.attachment.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.store.Volume;
import com.dreamail.mercury.store.Volumes;
import com.dreamail.mercury.util.StringUtils;

/**
 * 读取Word的实现类.
 * 
 * @author 000830
 * 
 */
public class DOCFormatImpl extends AbstractAttachmentFormat {
	/**
	 * SCALE ACALE.
	 */
	private static int charactersSub = Integer.parseInt(PropertiesDeploy
			.getConfigureMap().get("doc_table_size")) - 2;

	/**
	 * @param attachment
	 *            attachment.
	 * @param map
	 *            map
	 * @author 000830
	 * @throws Exception
	 *             Exception
	 * @return List list
	 * 
	 */

	@Override
	public List<Clickoo_message_attachment> format(Clickoo_message_attachment attachment,
			HashMap<String, String> map) {
		byte[] input = null;
		HWPFDocument doc = null;
		List<Clickoo_message_attachment> nList = new ArrayList<Clickoo_message_attachment>();

		// 可能出现读写异常
		Clickoo_message_attachment transAtta = transAttachment(attachment);
		handleOriginalFile(attachment, map);
		attachment.setName(transAtta.getName());

		Clickoo_message_attachment nAttachment = new Clickoo_message_attachment();
		String mapName = map.get("attName");
		nAttachment.setName(attachment.getName() + "." + attachment.getType()
				+ "_" + mapName);
		nAttachment.setType("txt");
		input = transAtta.getIn();

		// 读取字节流
		InputStream bis = new ByteArrayInputStream(input);
		try {
			doc = new HWPFDocument(bis);
		} catch (IOException e) {
			logger.error("DOCFormatImpl IOException.........");
			Volumes currentVolume = new Volumes();
			Volume volume = currentVolume
					.getVolumeByMeta(Volumes.MetaEnum.CURRENT_MESSAGE_VOLUME);
			nAttachment.setLength(0);
			nAttachment.setPath("");
			nAttachment.setVolume_id(volume.getId());
			nAttachment.setType("doc");
			nList.add(nAttachment);
			return nList;
		}
		Range range = doc.getRange();
		String str = range.text().replaceAll("", "")
								 .replaceAll("", "")
								 .replaceAll("", "")
								 .replaceAll("\r", " \r\n\r\n")
								 .replaceAll(" \r\n\r\n \r\n\r\n", "\r\n")
								 .replaceAll("", "\r\n")
								 .replaceAll(" ", "");
		double[] size = {0,0};
		str = tableInWord(doc, str,size);
		str = handleTitle(str);
		str = handleLink(str);
		
		if (!"".equals(str.trim())) {
			map.put("attName", transAtta.getName() + "." + transAtta.getType()
					+ "_" + mapName);
			HashMap<String, String> attachmentMap = ioProces(str, map, "new");
			nAttachment
					.setLength(Integer.parseInt(attachmentMap.get("length")));
			nAttachment.setPath(attachmentMap.get("path"));
			nAttachment.setVolume_id(Long.parseLong(attachmentMap
					.get("volume_id")));
			try {
				nAttachment.setIn(str.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error("failed to get bytes for utf-8", e);
			}
			nList.add(nAttachment);
		}
		map.put("attName", mapName);
//		List<Attachment> nImgList = this.pictureInWord(doc, map, transAtta);
//		if (nImgList != null && nImgList.size() > 0) {
//			nList.addAll(nImgList);
//		}
		return nList;
	}

	/**
	 * 处理文档中的图片.
	 * 
	 * @param doc
	 *            文档对象
	 * @param map
	 *            附件信息
	 * @param attachment
	 *            attachment
	 * @throws Exception
	 *             Exception
	 * @return List list
	 */
//	public List<Attachment> pictureInWord(HWPFDocument doc,
//			HashMap<String, String> map, Attachment attachment) {
//		List<Attachment> attachmentList = new ArrayList<Attachment>();
//		Attachment atta = new Attachment();
//		atta.setName(attachment.getName());
//		atta.setType(attachment.getType());
//		String attachmentName = atta.getName();
//		String attachmentType = atta.getType();
//		Attachment childAttachment = null;
//		PicturesTable picturestable = doc.getPicturesTable();
//		Range range = doc.getRange();
//		int numChar = range.numCharacterRuns();
//		double[] size = getDeviceModel(map.get("deviceModel"));
//		for (int i = 0; i < numChar; i++) {
//			CharacterRun cRun = range.getCharacterRun(i);
//			// 判断WORD中有没有图片
//			if (picturestable.hasPicture(cRun) && cRun != null) {
//				Picture picture = picturestable.extractPicture(cRun, true);
//				// 保存图到文件夹
//				if (picture != null) {
//					childAttachment = new Attachment();
//					String extName = picture.suggestFileExtension();
//					// 创建文件夹
//					File filepath;
//					/***************** 构造HashMap *********************************/
//					map.put("volume_meta_name", "0");
//					map.put("mailstate", "0");
//					map.put("image_state", "old");
//					map.put("is_file_name", "false");
//					map.put("path_flag", "true");
//					/************************************************************/
//					filepath = new File(AttachmentPath.getPath(map)[0]);
//					filepath.mkdirs();
//					String pictureName = attachmentName + "." + attachmentType
//							+ "_" + UUIDHexGenerator.getUUIDHex();
//					/** 添加图片处理----begin **/
//					atta.setIn(picture.getContent());
//					map.put("type", extName);
//					map.put("attName", pictureName);
//					Attachment resultAttach = handelPicutre(atta, map, extName,
//							size, SCALE);
//					/** 添加图片处理----end **/
//					childAttachment.setFilepath(resultAttach.getFilepath());
//					childAttachment.setName(pictureName);
//					childAttachment.setVolume_id(resultAttach.getVolume_id());
//					childAttachment.setIn(picture.getContent());
//					childAttachment.setLength(picture.getSize());
//					childAttachment.setType(extName);
//					attachmentList.add(childAttachment);
//				}
//			}
//		}
//		return attachmentList;
//	}

	/**
	 * 处理文档中的表格.
	 * 
	 * @param doc文档对象
	 * @throws Exception
	 *             Exception
	 * @return String
	 */
	public String tableInWord(HWPFDocument hwpf, String str,double[] size) {
//		int lieNum = StringUtils.getRows(size,charactersSub+2);
		// 得到文档的读取范围
		Range range = hwpf.getRange();
		TableIterator it = new TableIterator(range);
		// 迭代文档中的表格
		while (it.hasNext()) {
			StringBuffer ftableWord = new StringBuffer();
			StringBuffer tableWord = new StringBuffer();
			Table tb = (Table) it.next();
			// 迭代行，默认从0开始
			for (int i = 0; i < tb.numRows(); i++) {
				TableRow tr = tb.getRow(i);
				// 迭代列，默认从0开始
				for (int j = 0; j < tr.numCells(); j++) {
					TableCell td = tr.getCell(j);// 取得单元格
					// 取得单元格的内容
					for (int k = 0; k < td.numParagraphs(); k++) {
						Paragraph para = td.getParagraph(k);
						String cell = "";
						if (para != null) {
							cell = SubString(para.text().replaceAll("", ""));
							tableWord.append(para.text());
						} else {
							// 19个空白加一个|
							cell = StringUtils.fillblank(charactersSub + 2)
									+ "|";
						}
						// //只记录小于等于lieNum的cell内容
						// if(lieNum >= j+1){
						// ftableWord.append(cell);
						// }
						ftableWord.append(cell);
					}
				}
				//截取字符串
				ftableWord.append("\r\n");
			}
			String word = tableWord.toString().replaceAll("", "");
			String fword = ftableWord.toString().replaceAll("", "");
			str = str.replace(word, fword);
		}
		return str;
	}
	
	/**
	 * 表格中字符长度处理.
	 * @param para 
	 * @return String 
	 */
	private String SubString(String para) {
		String fpara = para.replaceAll("\r\n", "").replaceAll("\r", "")
				.replaceAll("\n", "");
		if (fpara.length() > charactersSub) {
			fpara = StringUtils.getChar(fpara.substring(0, charactersSub),
					"utf-8", charactersSub);
			return fpara + "|";
		} else {
			int chinese = StringUtils.getChineseChar(fpara, "utf-8", 3);
			int space = (fpara.length() - chinese) + 2 * chinese;
			if (charactersSub < space) {
				fpara = StringUtils.getChar(fpara, "utf-8", charactersSub);
			} else {
				fpara = fpara
						+ StringUtils.fillblank(charactersSub + 2 - space);
			}
			return fpara + "|";
		}
	}
	
	/**
	 * 处理doc中目录结构
	 * @param para 
	 * @return String 
	 */
	private String handleTitle(String str){
		int i = 0;
		StringBuffer pageRef = new StringBuffer();
		String[] pageList = str.split(" PAGEREF _");
		if(pageList.length > 1){
			System.out.println(pageList.length);
			for(String page : pageList){
				if(i == 0){
					String[] childePage = page.split(" TOC ");
					String page1 = childePage[0];
					String page2 = childePage[1];
					if(page2.contains(" ")){
						String[] childe2Page = page2.split(" ");
						page2 = childe2Page[2];
					}
					page = page1.replaceAll("", "\r\n") + page2;
					pageRef.append(page + "\r\n");
				}
				else if(i != pageList.length -1 && i != 0){
					String[] childPageList = page.split(" HYPERLINK");
					page = childPageList[1];
					String[] child2PageList = page.split(" ");
					page = child2PageList[1];
					pageRef.append(page + "\r\n");
				}
				else if(i == pageList.length -1 ){
					String[] childpage = page.split("");
					page = childpage[1].replaceAll("", "");
					pageRef.append(page);
				}
				i ++;
			}
		}else{
			pageRef.append(str);
		}
		return pageRef.toString().replaceAll("", "");
	}
	
	/**
	 * 处理doc中链接字段
	 * @param para 
	 * @return String 
	 */
	private String handleLink(String str){
		String result = "";
		StringBuffer linkStr = new StringBuffer();
		String[] linkList = str.split(" HYPERLINK");
		int i = 0;
		if(linkList.length > 1){
			for(String link : linkList){
				if(i == 0){
					linkStr.append(link);
				}
				else if(i != 0 ){
					String re = " \"http://";
					String[] childlink = link.split("");
					if(childlink[0].startsWith(re) && childlink.length == 2){
						linkStr.append(childlink[1]);
					}else{
						int j = 0;
						for(String child2link : childlink){
							if(j != 0){
								linkStr.append(child2link);	
							}
							j++;
						}
					}
				}
				i ++;
			}
		}else{
			linkStr.append(str);
		}
		result = linkStr.toString().replaceAll("", "");
		return result;
	}
}
