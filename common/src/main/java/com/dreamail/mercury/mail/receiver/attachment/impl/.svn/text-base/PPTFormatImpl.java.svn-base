package com.dreamail.mercury.mail.receiver.attachment.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.store.Volume;
import com.dreamail.mercury.store.Volumes;

public class PPTFormatImpl extends AbstractAttachmentFormat {

	private static Logger logger = LoggerFactory.getLogger(PPTFormatImpl.class);

	@Override
	public List<Clickoo_message_attachment> format(
			Clickoo_message_attachment attachment, HashMap<String, String> map) {
		logger.info("start ppt handlering...");
		byte[] input = null;
		SlideShow ss = null;
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
		StringBuffer content = new StringBuffer();
		try {
			ss = new SlideShow(new HSLFSlideShow(bis));// 建立SlideShow
			Slide[] slides = ss.getSlides();// 获得每一张幻灯片
			for (int i = 0; i < slides.length; i++) {
				TextRun[] t = slides[i].getTextRuns();// 为了取得幻灯片的文字内容，建立TextRun
				for (int j = 0; j < t.length; j++) {
					if (t[j].getText() != null) {
						content.append(t[j].getText());// 这里会将文字内容加到content中去
					}
				}
				if (slides[i].getTitle() != null) {
					content.append(slides[i].getTitle()); // 这里会将标题加到content中去
				}
			}
			String str = content.toString();
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
		} catch (IOException e) {
			logger.error("PPTFormatImpl IOException.........");
			Volumes currentVolume = new Volumes();
			Volume volume = currentVolume
					.getVolumeByMeta(Volumes.MetaEnum.CURRENT_MESSAGE_VOLUME);
			nAttachment.setLength(0);
			nAttachment.setPath("");
			nAttachment.setVolume_id(volume.getId());
			nAttachment.setType("ppt");
			nList.add(nAttachment);
			return nList;
		}
		map.put("attName", mapName);
		logger.info("end ppt handlering...");
		return nList;
	}

	/**
	 * 将PPT文件转换成txt文本文件
	 * 
	 * @author annlee
	 * 
	 * @param fis
	 *            ,源文件的文件输入流
	 * @param outputPath
	 *            ,输出文件的路径,这里指文件夹的路径
	 * @param outputFileName
	 *            ,输出文件的文件名,包括文件后缀名
	 * @return 转换成功返回字符串"OK";转换失败返回失败原因.
	 */
	public static String convertPptToTxt(FileInputStream fis,
			String outputPath, String outputFileName) {

		StringBuffer content = new StringBuffer();
		try {
			SlideShow ss = new SlideShow(new HSLFSlideShow(fis));// 建立SlideShow
			Slide[] slides = ss.getSlides();// 获得每一张幻灯片
			for (int i = 0; i < slides.length; i++) {
				TextRun[] t = slides[i].getTextRuns();// 为了取得幻灯片的文字内容，建立TextRun
				for (int j = 0; j < t.length; j++) {
					if (t[j].getText() != null) {
						content.append(t[j].getText());// 这里会将文字内容加到content中去
					}
				}
				if (slides[i].getTitle() != null) {
					content.append(slides[i].getTitle()); // 这里会将标题加到content中去
				}
			}
			String outputFile = outputPath + outputFileName + ".txt"; // 组装输出TXT文件的绝对路径

			FileOutputStream fos = new FileOutputStream(outputFile);
			fos.write(content.toString().getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			return "文件不存在!";
		} catch (IOException e) {
			return "文件读写错误!";
		}
		return "OK";
	}

	public static void main(String[] args) throws FileNotFoundException {
		PPTFormatImpl.convertPptToTxt(new FileInputStream(
				new File("C://测试.ppt")), "C://", "1");
	}

}
