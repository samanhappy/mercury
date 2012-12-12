package com.dreamail.mercury.mail.receiver.attachment.picture;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.configure.PropertiesDeploy;
import com.dreamail.mercury.dal.service.VolumeService;
import com.dreamail.mercury.mail.receiver.attachment.AbstractAttachmentFormat;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;
import com.dreamail.mercury.pojo.Clickoo_volume;
import com.dreamail.mercury.util.AttachmentSupport;
import com.dreamail.mercury.util.StreamUtil;

public abstract class ScaleByJMagick extends AbstractAttachmentFormat {

	public static final Logger logger = LoggerFactory
			.getLogger(ScaleByJMagick.class);

	/**
	 * TODO 邮件图片的压缩方法
	 * 
	 * @param imagePath
	 * @param wh
	 * @param scale
	 * @throws IM4JavaException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws Exception
	 */
	public static boolean scale(String[] imagePath, double[] wh, double scale) throws Exception{
		logger.info("system properties:" + System.getProperties());
		logger.info("system env:" + System.getenv());
//		try {
			IMOperation op = new IMOperation(); // 创建IM操作对象
			op.addImage();
			Info imageInfo = new Info(imagePath[0]);
			String prop = imageInfo.getProperty("Geometry");
			int originalWideth;
			int originalHeight;
			if (prop.indexOf("+") != -1) {
				int start = prop.indexOf("x");
				int end = prop.indexOf("+");
				originalWideth = Integer.parseInt(prop.substring(0, start));
				originalHeight = Integer.parseInt(prop
						.substring(start + 1, end));
			} else {
				int start = prop.indexOf("x");
				originalWideth = Integer.parseInt(prop.substring(0, start));
				originalHeight = Integer.parseInt(prop.substring(start + 1));
			}
			scale = getScale(wh, originalWideth, originalHeight);
			int scaledWideth = (int) (originalWideth * scale);
			int scaledHeight = (int) (originalHeight * scale);
			op.resize(scaledWideth, scaledHeight, "!");
			// 降低图片的质量
			op.quality(70.0);
			op.addImage(imagePath[1]);
			String[] images = new String[] { imagePath[0] };
			ConvertCmd convert = new ConvertCmd();
			convert.run(op, (Object[]) images);
//		} catch (Exception e) {
//			logger.error("failed to scale", e);
//		} 
		return true;
	}

	/**
	 * TODO 邮件图片的压缩方法
	 * 
	 * @param imagePath
	 *            图片保存的路径
	 * @param imagePath
	 * @param wh
	 * @param scale
	 * @throws Exception
	 * @throws Exception
	 */
	public Clickoo_message_attachment scale(
			Clickoo_message_attachment attachment, HashMap<String, String> map,
			double[] size, double scale) {
		
		
		Clickoo_message_attachment returnAttach = new Clickoo_message_attachment();
		// 文件字节大小
		long longLength = 0;
		/***************** 构造HashMap *********************************/
		String sizeString = String.valueOf(size[0]) + ","
				+ String.valueOf(size[1]);
		// 图片处理多出100,50路径
		map.put("size", sizeString);
		map.put("volume_meta_name", "0");
		map.put("mailstate", "0");
		map.put("image_state", "new");
		map.put("is_file_name", "false");
		map.put("path_flag", "true");
		String[] resultPath = AttachmentPath.getPath(map);
		String catalog = resultPath[0];
		map.put("is_file_name", "true");
		String realPath = AttachmentPath.getPath(map)[0];
		map.put("image_state", "old");
		String oldPath = AttachmentPath.getPath(map)[0];
		/************************************************************/
		String originalType = attachment.getType();
		String currentType = map.get("type");
		
		try {
			File file = new File(catalog);
			file.mkdirs();
			// 将原始图片写到服务器
			if (!AttachmentSupport.isImageType(originalType)) {
				ioProces("old", attachment.getIn(), map, "." + currentType);
			}
			oldPath = oldPath.replace(sizeString + File.separator, "");
			String[] imagePath = new String[] { oldPath, realPath };
			// 压缩图片处理
			logger.info("ScaleByJMagick oldPath............." + oldPath);
			logger.info("ScaleByJMagick realPath............" + realPath);
			
			scale(imagePath, size, scale);
			/**************************************************************/
			map.put("image_state", "new");
			map.put("is_file_name", "true");
			map.put("path_flag", "false");
			/**************************************************************/
			String[] scalePath = AttachmentPath.getPath(map);
			returnAttach.setPath(scalePath[0]);
			Clickoo_volume volume = new VolumeService().getVolumeById(Long
					.parseLong(scalePath[1]));
			logger.info("volume path:" + volume.getPath());
			logger.info("attachment path:" + scalePath[0]);
			returnAttach.setIn(StreamUtil.readFile(volume.getPath()
					+ scalePath[0]));
			longLength = getFileSizes(new File(realPath));
			returnAttach.setVolume_id(Long.parseLong(resultPath[1]));
			returnAttach.setLength((int) (longLength));
			
		} catch (Exception e) {
			logger.info("image handle wrong,save part of  message...........",
					e);
			returnAttach.setPath("");
			returnAttach.setVolume_id(Long.parseLong(resultPath[1]));
			returnAttach.setLength(0);
			return returnAttach;
		}
		return returnAttach;
	}

	/**
	 * 描述：图片压缩算法的实现。 根据原图宽高和设备宽高的比例关系确定压缩率。
	 * 
	 * @param w_h
	 *            ，设备宽和高
	 * @param iW
	 *            ，原图宽度
	 * @param iH
	 *            ，原图高度
	 * 
	 * @return scale，压缩率
	 */
	public static double getScale(double[] w_h, double iW, double iH) {
		double SCALE_NUM_H = Double.parseDouble(PropertiesDeploy
				.getConfigureMap().get("scale_num_h"));
		double SCALE_NUM_L = Double.parseDouble(PropertiesDeploy
				.getConfigureMap().get("scale_num_l"));
		double width = w_h[0];
		double height = w_h[1];
		double imageWidth = iW;
		double imageHeight = iH;
		double scale = 1.0;
		double wh = width / height;
		double image_wh = imageWidth / imageHeight;

		/*
		 * 如果原图片的宽高比例大于或等于设备的宽高比例 ， 则以宽度为准进行压缩。
		 */
		if (image_wh >= wh) {
			if (imageWidth > width) // 原图片的宽度大于设备的宽度,则按照设备宽度压缩
			{
				scale = width / imageWidth;
			} else {
				if (imageWidth > width / 2) // 原图宽度大于设备宽度的一半，则按高压缩率0.8压缩
				{
					scale = SCALE_NUM_L;
				} else // 原图宽度小于设备宽度的一半，则按低压缩率0.9压缩
				{
					scale = SCALE_NUM_H;
				}
			}
			if (imageWidth * scale >= (width - 5))// 压缩后图宽大于设备宽，则按设备宽-15修正
			{
				scale = (width - 15) / imageWidth;
			}
			if (imageHeight * scale > height) // 压缩后的图高大于设备高，则按设备高压缩
			{
				scale = scale * (height / (imageHeight * scale));
			}
		}
		/*
		 * 如果原图的宽高比小于设备宽高比， 则以高度为准进行压缩。
		 */
		else {
			if (imageHeight > height) // 原图高大于设备高，则按设备高压缩
			{
				scale = height / imageHeight;
			} else {
				if (imageHeight > height / 2) // 原图高大于设备高的一半，则按高压缩率0.8压缩
				{
					scale = SCALE_NUM_L;
				} else // 原图高小于设备高的一半，则按低压缩率0.9压缩
				{
					scale = SCALE_NUM_H;
				}
			}
			if (imageWidth * scale >= (width - 5))// 压缩后的图宽大于设备宽，则按设备宽-15修正
			{
				scale = (width - 15) / imageWidth;
			}
		}
		if (scale > 1.0) {
			scale = 1.0;
		}
		return scale;
	}

	/**
	 * 获得终端屏幕size
	 * 
	 * @param uid
	 * @return long []
	 * @throws Exception
	 */
	public double[] getDeviceModel(String model) {
		String[] modelSplit;
		double width = 0;
		double height = 0;
		double[] size = new double[2];
		if (!"".equals(model) && model != null && model.contains(",")) {
			modelSplit = model.split(",");

			width = Long.parseLong(modelSplit[0]) > 0 ? Long
					.parseLong(modelSplit[0]) : 0;

			height = Long.parseLong(modelSplit[1]) > 0 ? Long
					.parseLong(modelSplit[1]) : 0;

			if (width > 0 && height > 0) {
				size[0] = width;
				size[1] = height;
			}
		} else {
			logger.info("Device Date is wrong!");
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return size;
	}

	/**
	 * TODO 重新找回图片压缩方法
	 * 
	 * @param imagePath
	 * @param wh
	 * @param scale
	 * @throws Exception
	 */
	public static byte[] scale(String path, double[] size, double scale)
			throws Exception {
		File file = new File(path);
		if (file.exists()) {
			String treated = File.separator + "Treated" + File.separator;
			// 获取处理后图片
			if (path.contains(treated)) {
				String currentS = path.replace(File.separator, "@#");
				String[] filebits = currentS.split("@#");
				if (filebits.length - 3 > 0) {
					String oldDeviceCode = filebits[filebits.length - 3];
					// 宽 高
					double oldDeviceHeight = 0;
					double oldDeviceWidth = 0;
					if (oldDeviceCode.contains(",")) {
						String[] hw = oldDeviceCode.split(",");
						oldDeviceWidth = Double.parseDouble(hw[0]);
						oldDeviceHeight = Double.parseDouble(hw[1]);
						logger.info(size[0] + "-----" + oldDeviceWidth                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
								+ "--|||--" + size[1] + "-----"
								+ oldDeviceHeight);
						if (size[0] != oldDeviceWidth
								|| size[1] != oldDeviceHeight) {
							String newSizeString = String.valueOf(size[0])
									+ "," + String.valueOf(size[1]);
							logger.info(" device change........");
							// file.delete();
							String oldPath = path.replace(File.separator
									+ oldDeviceCode + File.separator
									+ "Treated", "");
							path = path.replace(oldDeviceCode, newSizeString);
							String parentPath = path.substring(0,
									path.lastIndexOf(File.separator));
							File newParentPath = new File(parentPath);
							if (!newParentPath.exists()) {
								newParentPath.mkdirs();
							}
							String[] imagePath = new String[] { oldPath, path };
							scale(imagePath, size, scale);
						}
					} else {
						logger.info(" There is no device find .........");
					}
				} else {
					logger.info(" There is no image find .........");
				}
			}
			return StreamUtil.readFile(path);
		} else {
			logger.info(" There is no image find .........");
			return null;
		}
	}

}
