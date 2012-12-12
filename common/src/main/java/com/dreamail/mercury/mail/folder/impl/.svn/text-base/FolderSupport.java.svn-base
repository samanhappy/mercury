package com.dreamail.mercury.mail.folder.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Folder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dreamail.mercury.dal.dao.FolderDao;
import com.dreamail.mercury.domain.WebAccount;
import com.dreamail.mercury.mail.folder.IFolderProvider;
import com.dreamail.mercury.pojo.Clickoo_folder;

public class FolderSupport {
	public static final Logger logger = LoggerFactory
	.getLogger(FolderSupport.class);
	/**
	 * 根据账号返回文件夹名称列表.
	 * @param account
	 * @return
	 */
	public List<String> listFolder(WebAccount account){
		IFolderProvider folderProvider = new FolderProviderImpl();
		List<String> folderList = null;
		Folder[] folders = folderProvider.listFolder(account);
		if(folders!=null){
			folderList = new ArrayList<String>();
			for(Folder folder:folders){
				folderList.add(folder.getName());
			}
		}
		return folderList;
	}
	/**
	 * 控制用户为账号添加文件夹.
	 * @param account
	 * @param folderName
	 * @param uid
	 * @return
	 */
	public boolean addFolder(WebAccount account,String folderName,long uid){
		boolean addSuccess = false;
		FolderDao folderDao = new FolderDao();
		List<Clickoo_folder> folderList = folderDao.selectFoldersByAidAndName(account.getId(), folderName);
		if(folderList!=null){
			for(Clickoo_folder folder:folderList){
				if(folderName.equals(folder.getName()) && uid==folder.getUid()){
					logger.info("uid:"+uid+",aid:"+account.getId()+",folderName:"+folderName+" is exist.");
					return addSuccess;
				}
			}
		}
		Clickoo_folder folder = new Clickoo_folder();
		folder.setName(folderName);
		folder.setAid(account.getId());
		folder.setUid(uid);
		folder.setAddDate(new Date());
		folder.setReceiveStatus(2);
		if(folderList==null || folderList.size()==0){
			folder.setListStatus(2);
			folder.setRegisterDate(folder.getAddDate());
		}
		if(folderDao.createFolder(folder)!=-1)
			addSuccess = true;
		return addSuccess;
	}
	/**
	 * 删除文件夹.
	 * @param account
	 * @param folderName
	 * @param uid
	 * @return
	 */
	public boolean deleteFolder(WebAccount account,String folderName,long uid){
		return false;
	}
	/**
	 * 为文件夹改名.
	 * @param account
	 * @param folderName1
	 * @param folderName2
	 * @param uid
	 * @return
	 */
	public boolean modifyFolder(WebAccount account,String folderName1,String folderName2,long uid){
		return false;
	}
	/**
	 * 拷贝邮件.
	 * @param account
	 * @param folderName1
	 * @param folderName2
	 * @param uid
	 * @param uuids
	 * @return
	 */
	public boolean copyMessage(WebAccount account,String folderName1,String folderName2,long uid,String[] uuids){
		return false;
	}
	/**
	 * 移动邮件.
	 * @param account
	 * @param folderName1
	 * @param folderName2
	 * @param uid
	 * @param uuids
	 * @return
	 */
	public boolean moveMessage(WebAccount account,String folderName1,String folderName2,long uid,String[] uuids){
		return false;
	}
	
}
