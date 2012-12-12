package com.dreamail.mercury.mail.receiver.attachment.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.store.Volume;
import com.dreamail.mercury.store.Volumes;
import com.dreamail.mercury.util.StringUtils;

/**
 * 对XLS格式的附件进行处理
 * 
 * @author 000830
 * 
 */
public class XLSFormatImpl extends AbstractAttachmentFormat
{

	// 图片处理的压缩倍数,默认为1
	public static double SCALE = 1;

	// excel中每个单元格显示的byte数
	private int charactersSub = Integer.parseInt(PropertiesDeploy
			.getConfigureMap().get("xls_table_size")) - 2;

	/**
	 * xls附件处理.
	 * 
	 * @param attachment
	 *            原始附件信息
	 * @param map
	 *            当前处理附件信息
	 * @return List 处理后附件信息
	 */
	public List<Clickoo_message_attachment> format(Clickoo_message_attachment attachment,
			HashMap<String, String> map)
	{
		List<Clickoo_message_attachment> nList = new ArrayList<Clickoo_message_attachment>();
		String mapName = map.get("attName");
		// 可能出现读写异常
		Clickoo_message_attachment transAtta = transAttachment(attachment);
		handleOriginalFile(attachment, map);
		attachment.setName(transAtta.getName());

		HashMap<String, String> attachmentMap = null;
		String attaFile = attachment.getName();
		String attaType = attachment.getType();
		byte[] input = attachment.getIn();
		// 读取字节流
		InputStream bis = new ByteArrayInputStream(input);
		// 创建对Excel工作簿文件的引用
		HSSFWorkbook workbook = null;
		try
		{
			workbook = new HSSFWorkbook(bis);
		} catch (IOException e)
		{
			logger.error("XLSFormatImpl IOException.........");
			Clickoo_message_attachment nAttachment = new Clickoo_message_attachment();
			Volumes currentVolume = new Volumes();
			Volume volume = currentVolume
					.getVolumeByMeta(Volumes.MetaEnum.CURRENT_MESSAGE_VOLUME);
			nAttachment.setName(attaFile);
			nAttachment.setType("xls");
			nAttachment.setLength(0);
			nAttachment.setPath("");
			nAttachment.setVolume_id(volume.getId());
			nList.add(nAttachment);
			return nList;
		}
		FormulaEvaluator evaluator = workbook.getCreationHelper()
				.createFormulaEvaluator();
		
		String[] deviceModelList = map.get("deviceModelList").split("#");
		for(String deviceModel : deviceModelList){
			double height = Double.parseDouble(deviceModel.split(",")[0]);
			double width = Double.parseDouble(deviceModel.split(",")[1]);
			double[] size = {height,width};
			map.put("size", height+","+width);
			// 创建对工作表的引用。
			String outputWord = "";
			StringBuffer words = new StringBuffer();
			for (int i = 0; i < workbook.getNumberOfSheets(); i++){
				// 循环sheet
				HSSFSheet childSheet = workbook.getSheetAt(i);
				outputWord = handelSheet(childSheet, evaluator, size);
				if(!outputWord.equals("")){
					words.append(outputWord);
					break;
				}
			}
			if (words != null && !"".equals(words.toString())){
				String name = attaFile + "." + attaType;
				map.put("attName", name + "_" + mapName);
				attachmentMap = ioProces(words.toString(), map, "new");
				Clickoo_message_attachment nAttachment = new Clickoo_message_attachment();
				nAttachment.setName(name + "_" + mapName);
				nAttachment.setType("txt");
				nAttachment.setLength(Integer.parseInt(attachmentMap
						.get("length")));
				nAttachment.setPath(attachmentMap.get("path"));
				try{
					nAttachment.setIn(words.toString().getBytes("utf-8"));
				} catch (UnsupportedEncodingException e) {
					logger.error("failed to get bytes for utf-8", e);
				}
				nAttachment.setVolume_id(Long.parseLong(attachmentMap
						.get("volume_id")));
				nList.add(nAttachment);
			}
		}
		return nList;
	}

	/**
	 * 处理Excel的sheet
	 * 
	 * @param HSSFSheet
	 *            childSheet excel工作空间
	 * @paramFormulaEvaluator evaluator 单元格处理对象
	 * @return String 处理后xlsString对象
	 */
	@SuppressWarnings("deprecation")
	public String handelSheet(HSSFSheet childSheet, FormulaEvaluator evaluator,
			double[] size)
	{
		int mark = 0;
		String result = "";
		int lieNum = StringUtils.getRows(size, charactersSub + 2);
		StringBuffer sheetWord = new StringBuffer();
		for (int r = 0; r < childSheet.getLastRowNum() + 1; r++)
		{
			HSSFRow row = childSheet.getRow(r);
			if (row != null)
			{
				StringBuffer hangContext = new StringBuffer();
				for (short c = 0; c < childSheet.getRow(r).getLastCellNum(); c++)
				{
					Object rowc = row.getCell(c);
					if (rowc != null)
					{
						CellValue cellValue = evaluator
								.evaluate(row.getCell(c));
						rowc = SubString(rowc.toString(), cellValue);
						mark = 1;
					} else
					{
						rowc = StringUtils.fillblank(charactersSub + 2) + "|";
					}
					String hang = rowc + "";
					hangContext.append(hang);
					if(lieNum < c+1){
						break;
					}
				}
				String currentHangContext = hangContext.toString().replaceAll(" ", "");
				if(currentHangContext.length() != lieNum){
					sheetWord.append(hangContext);
					sheetWord.append("\r\n");
				}
			}
		}
		if(mark == 1){
			result = sheetWord.toString();
		}
		return result;
	}

	/**
	 * Excel中cell处理
	 * 
	 * @param String
	 *            para 单元格内容
	 * @param CellValue
	 *            cellValue 单元格处理对象
	 * @return String 处理后单元格内容
	 */
	private String SubString(String para, CellValue cellValue)
	{

		if (para.startsWith("SUM(") || para.startsWith("MAX(")
				|| para.startsWith("COUNT(") || para.startsWith("MIN(")
				|| para.startsWith("AVERAGE(") || para.startsWith("IF(")
				|| para.startsWith("SIN(") || para.startsWith("SUMIF(")
				|| para.startsWith("PMT(") || para.startsWith("STDEV("))
		{
			para = cellValue.getNumberValue() + "";
		}
		String fpara = para.replaceAll("\r\n", "").replaceAll("\r", "")
				.replaceAll("\n", "");
		if (fpara.length() > charactersSub)
		{
			fpara = StringUtils.getChar(fpara.substring(0, charactersSub),
					"utf-8", charactersSub);
			return fpara + "|";
		} else
		{
			int chinese = StringUtils.getChineseChar(fpara, "utf-8", 3);
			int space = (fpara.length() - chinese) + 2 * chinese;
			if (charactersSub < space)
			{
				fpara = StringUtils.getChar(fpara, "utf-8", charactersSub);
			} else
			{
				fpara = fpara
						+ StringUtils.fillblank(charactersSub + 2 - space);
			}
			return fpara + "|";
		}
	}

	/**
	 * Excel图片处理
	 * 
	 * @param workBook
	 *            excel工作空间对象
	 * @param map
	 *            附件信息
	 * @return List 处理后附件信息
	 */
//	public List<Attachment> pictureInExcel(HSSFWorkbook workBook,
//			HashMap<String, String> map, Attachment attachment)
//	{
//		Attachment atta = new Attachment();
//		atta.setName(attachment.getName());
//		atta.setType(attachment.getType());
//		List<Attachment> listAttachment = new ArrayList<Attachment>();
//		Attachment childAttachment = null;
//		String childPath = "";
//		String attachmentName = atta.getName();
//		String attachmentType = atta.getType();
//		List<HSSFPictureData> lst = workBook.getAllPictures();
//		Iterator<HSSFPictureData> it = lst.iterator();
//		while (it.hasNext())
//		{
//			PictureData pict = it.next();
//			if (pict != null)
//			{
//				childAttachment = new Attachment();
//				String ext = pict.suggestFileExtension();
//				double[] size = null;
//				size = getDeviceModel(map.get("deviceModel"));
//				byte[] data = pict.getData();
//				File filepath;
//				/***************** 构造HashMap *********************************/
//				map.put("volume_meta_name", "0");
//				map.put("mailstate", "0");
//				map.put("image_state", "old");
//				map.put("is_file_name", "false");
//				map.put("path_flag", "true");
//				/************************************************************/
//				filepath = new File(AttachmentPath.getPath(map)[0]);
//				filepath.mkdirs();
//				String pictureName = attachmentName + "." + attachmentType
//						+ "_" + UUIDHexGenerator.getUUIDHex();
//				atta.setIn(data);
//				map.put("type", ext);
//				map.put("attName", pictureName);
//				Attachment resultAttach = handelPicutre(atta, map, ext, size,
//						SCALE);
//				childPath = resultAttach.getFilepath();
//				childAttachment.setVolume_id(resultAttach.getVolume_id());
//				childAttachment.setFilepath(childPath);
//				childAttachment.setName(pictureName);
//				childAttachment.setIn(pict.getData());
//				childAttachment.setLength(pict.getData().length);
//				childAttachment.setType(ext);
//				listAttachment.add(childAttachment);
//			}
//		}
//		return listAttachment;
//	}
}
