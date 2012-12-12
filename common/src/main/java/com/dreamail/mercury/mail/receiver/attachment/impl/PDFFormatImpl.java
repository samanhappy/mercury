package com.dreamail.mercury.mail.receiver.attachment.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.store.Volume;
import com.dreamail.mercury.store.Volumes;

/**
 * Type为PDF的附件读取类.
 * 
 * @author 000830
 */
public class PDFFormatImpl extends AbstractAttachmentFormat {
	/**
	 * PDF处理.
	 * 
	 * @param attachment
	 *            原始附件信息
	 * @param map
	 *            当前处理附件信息
	 * @return List 处理后附件信息
	 */
	@SuppressWarnings("finally")
	public List<Clickoo_message_attachment> format(
			Clickoo_message_attachment attachment, HashMap<String, String> map) {
		boolean pathFlag = false;
		List<Clickoo_message_attachment> nList = new ArrayList<Clickoo_message_attachment>();

		// 可能出现读写异常
		String mapName = map.get("attName");
		Clickoo_message_attachment transAtta = transAttachment(attachment);
		handleOriginalFile(attachment, map);

		Clickoo_message_attachment nAttachment = new Clickoo_message_attachment();
		nAttachment.setType("txt");
		String result = null;
		PDDocument document = null;
		HashMap<String, String> attachmentMap = null;
		byte[] input = transAtta.getIn();
		InputStream bis = new ByteArrayInputStream(input);
		try {
			PDFParser parser = new PDFParser(bis);
			parser.parse();
			document = parser.getPDDocument();
			PDFTextStripper stripper = new PDFTextStripper();
			result = stripper.getText(document);
			pathFlag = true;
			logger.info("PDF go on ...............");
			// 取得读取后文件路径和length
			if (!"".equals(result.trim())) {
				map.put("attName", transAtta.getName() + "."
						+ transAtta.getType() + "_" + mapName);
				attachmentMap = ioProces(result, map, "new");
				nAttachment.setName(transAtta.getName() + "."
						+ transAtta.getType() + "_" + mapName);
				nAttachment.setLength(Integer.parseInt(attachmentMap
						.get("length")));
				nAttachment.setPath(attachmentMap.get("path"));
				nAttachment.setVolume_id(Long.parseLong(attachmentMap
						.get("volume_id")));
				try {
					nAttachment.setIn(result.getBytes("utf-8"));
				} catch (UnsupportedEncodingException e) {
					logger.error("failed to get bytes for utf-8", e);
				}
				nList.add(nAttachment);
			}
		} catch (Exception e) {
			logger.error("PDFFormatImpl Exception.........", e);
			pathFlag = true;
			nAttachment = errorAttachment();
			nList.add(nAttachment);
		} finally {
			if (!pathFlag) {
				logger.error("finally PDFFormatImpl Exception.........");
				nAttachment = errorAttachment();
				nList.add(nAttachment);
			}
			try {
				if (document != null) {
					document.close();
				}
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return nList;
		}
	}

	/**
	 * 处理错误附件信息
	 * 
	 * @return Attachment 附件信息
	 */
	public static Clickoo_message_attachment errorAttachment() {
		Clickoo_message_attachment nAttachment = new Clickoo_message_attachment();
		Volumes currentVolume = new Volumes();
		Volume volume = currentVolume
				.getVolumeByMeta(Volumes.MetaEnum.CURRENT_MESSAGE_VOLUME);
		nAttachment.setType("pdf");
		nAttachment.setLength(0);
		nAttachment.setPath("");
		nAttachment.setVolume_id(volume.getId());
		return nAttachment;
	}
}
