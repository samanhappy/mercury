package com.dreamail.mercury.mail.receiver.attachment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.mail.receiver.attachment.picture.AttachmentPath;
import com.dreamail.mercury.pojo.Clickoo_message_attachment;

public abstract class AbstractAttachmentFormat {

	public abstract List<Clickoo_message_attachment> format(Clickoo_message_attachment attachment,
			HashMap<String, String> map);
	public static final Logger logger = LoggerFactory.getLogger(AbstractAttachmentFormat.class);
	
	/**
	 * 获取文件的length
	 * @param files 文件对象
	 * @return long 文件大小
	 * @throws IOException 
	 * @throws Exception
	 */
	public long getFileSizes(File file) throws IOException{
		long length = 0;
		FileInputStream fis = null;
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				length = fis.available();
			} catch (Exception e) {
				logger.error("get file size failed!",e);
			}
			finally{
				if (fis != null) {
					fis.close();
				}
			}
		}
		return length;
	}
	
	/**
	 * .txt 文件创建
	 * @param str 转化为String的附件内容
	 * @param map 附件信息
	 * @return HashMap 处理后的附件信息
	 * @throws Exception
	 */
	public HashMap<String, String> ioProces(String str,
			HashMap<String, String> map,String state){
		HashMap<String, String> attachmentMap = null;
		map.put("volume_meta_name","0");
		attachmentMap = this.handleIOProces(str, map ,state);
		if("0".equals(attachmentMap.get("length"))){
			logger.error("MESSAGE_VOLUME FAILED!");	
			map.put("volume_meta_name","1");
			attachmentMap = this.handleIOProces(str, map,state);
		}
		return attachmentMap;
	}
	
	/**
	 * .txt 文件创建
	 * @param str 转化为String的附件内容
	 * @param map 附件信息
	 * @return HashMap 处理后的附件信息
	 * @throws Exception
	 */
	public HashMap<String, String> handleIOProces(String str,
			HashMap<String, String> map,String state){
		HashMap<String, String> attachmentMap = new HashMap<String, String>();
		String attname = map.get("attName");
		File filepath;
		FileChannel dos = null;
		/*****************构造HashMap*********************************/
		map.put("mailstate","0");
		map.put("image_state",state);
		map.put("is_file_name","false");
		map.put("path_flag","true");
		/************************************************************/
		String[] resultPath = AttachmentPath.getPath(map);
		try {
			filepath = new File(resultPath[0]);
			filepath.mkdirs();
			File txt = new File(filepath, File.separator +attname+".txt");
			dos = new FileOutputStream(txt).getChannel();
			dos.write(ByteBuffer.wrap(str.getBytes()));
			map.put("is_file_name","true");
			map.put("path_flag","false");
			attachmentMap.put("volume_id", resultPath[1]);
			map.put("type", "txt");
			attachmentMap.put("path", AttachmentPath.getPath(map)[0]);
			attachmentMap.put("length", String.valueOf(getFileSizes(txt)));
		}catch (Exception e){
			logger.error("failed to HandleIOProces", e);
			attachmentMap.put("volume_id", resultPath[1]);
			attachmentMap.put("path", "");
			attachmentMap.put("length", "0");
			return attachmentMap;
		}
		finally {
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return attachmentMap;
	}
	
	/**
	 * image创建
	 * @param input 流对象
	 * @param map 附件信息
	 * @param fileType 附件类型
	 * @return HashMap 处理后的附件信息
	 * @throws Exception 
	 * @throws Exception
	 */
	public HashMap<String, String> ioProces(String state,byte[] input,
			HashMap<String, String> map,String fileType){
		map.remove("size");
		HashMap<String, String> attachmentMap = null;
			map.put("volume_meta_name","0");
			attachmentMap = handleIOProces(state, input, map, fileType);
			if("0".equals(attachmentMap.get("length"))){
				logger.error("MESSAGE_VOLUME FAILED!");
				map.put("volume_meta_name","1");
				attachmentMap = handleIOProces(state, input, map, fileType);
			}
		return attachmentMap;
	}
	
	/**
	 * image创建
	 * @param input 流对象
	 * @param map 附件信息
	 * @param fileType 附件类型
	 * @return HashMap 处理后的附件信息
	 * @throws Exception 
	 * @throws Exception
	 */
	public HashMap<String, String> handleIOProces(String state,byte[] input,
			HashMap<String, String> map,String fileType){
		HashMap<String, String> attachmentMap = new HashMap<String, String>();
		String attname = map.get("attName");
		File filepath;
		FileChannel fileOut = null;
		/*****************构造HashMap*********************************/
		map.put("mailstate","0");
		map.put("image_state",state);
		map.put("is_file_name","false");
		map.put("path_flag","true");
		/************************************************************/
		String[] resultPath = AttachmentPath.getPath(map);
		try {
			filepath = new File(resultPath[0]);
			filepath.mkdirs();
			File imagePath = new File(filepath,attname+fileType);
			fileOut = new FileOutputStream(imagePath).getChannel();
			fileOut.write(ByteBuffer.wrap(input));
			map.put("path_flag","false");
			attachmentMap.put("volume_id", resultPath[1]);
			attachmentMap.put("path", AttachmentPath.getPath(map)[0]);
			attachmentMap.put("length", String.valueOf(getFileSizes(imagePath)));
		}catch(Exception e){
			logger.error("HandleIOProces exception", e);
			attachmentMap.put("volume_id", resultPath[1]);
			attachmentMap.put("path", "");
			attachmentMap.put("length", "0");
			return attachmentMap;
		}
		finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return attachmentMap;
	} 
	//原始附件存储到服务器
    public void handleOriginalFile(Clickoo_message_attachment attachment,
			HashMap<String, String> map){
		HashMap<String, String> attachmentMap = ioProces("old",attachment.getIn(), map ,"."+attachment.getType());
//		attachment.setLength(Integer.parseInt(attachmentMap.get("length")));
		String currentPath = attachmentMap.get("path");
		if(!"".equals(currentPath)){
			String newPath = currentPath + File.separator + map.get("attName") + "." + attachment.getType();
			attachment.setPath(newPath);
			logger.info("AbstractAttachmentFormat newPath..........."+newPath);
		}else{
			attachment.setPath("");
		}
		attachment.setVolume_id(Long.parseLong(attachmentMap.get("volume_id")));
		logger.info(String.valueOf(attachment.getLength()));
    }
    
    public Clickoo_message_attachment transAttachment(Clickoo_message_attachment attachment)
    {
    	Clickoo_message_attachment atta = new Clickoo_message_attachment();
    	atta.setChildList(attachment.getChildList());
    	atta.setPath(attachment.getPath());
    	atta.setIn(attachment.getIn());
    	atta.setLength(attachment.getLength());
    	atta.setMid(attachment.getMid());
    	atta.setName(attachment.getName());
    	atta.setType(attachment.getType());
    	atta.setVolume_id(attachment.getVolume_id());
    	return atta;
    }
}
